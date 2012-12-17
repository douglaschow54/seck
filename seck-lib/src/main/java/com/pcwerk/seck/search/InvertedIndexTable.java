package com.pcwerk.seck.search;

import com.pcwerk.seck.store.WebDocument;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.index.TermVectorOffsetInfo;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jruby.org.objectweb.asm.tree.analysis.Analyzer;

public class InvertedIndexTable {

    private IndexWriter indexWriter = null;
    private IndexReader indexReader = null;

    public InvertedIndexTable() {
    }

    public IndexReader getIndexReader() throws IOException {
        if (indexReader == null) {
            indexReader = IndexReader.open(FSDirectory.open(new File("index-directory")));
        }
        return indexReader;
    }

    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (create) {
            indexWriter = new IndexWriter(FSDirectory.open(
                    new File("index-directory")), new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
        }
        return indexWriter;
    }

    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    public void indexWebContent(WebDocument webContent, String id) throws IOException {

        System.out.println("Indexing webContent : " + webContent.getUrl());
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();
        doc.add(new Field("id", id, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("url", webContent.getUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("content", webContent.getContent(), Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
        writer.addDocument(doc);
    }

    @SuppressWarnings("unchecked")
    public void index(String fileName) {
        try {
            //
            // Erase existing index
            //
            getIndexWriter(true);

            // Load file containing results from Crawler into memory
            // as a POJO, List<WebDocument>
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            List<WebDocument> webContentList = (List<WebDocument>) input.readObject();

            // Iterate through each item and index it
            int id = 0;
            for (WebDocument webContent : webContentList) {
                indexWebContent(webContent, Integer.toString(id++));
            }

            input.close();
            //
            // Don't forget to close the index writer when done
            //
            closeIndexWriter();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void indexInfo() {
        IndexReader reader;
        try {
            /////////////////////////////////////
            //////////////// Indexes  ///////////
            /////////////////////////////////////
            reader = getIndexReader();

            TermEnum terms = reader.terms();
            do {
                if (terms.term() != null && terms.term().field().equals("content")) {

                    // Term count + position + offset			
                    System.out.println("\"" + terms.term().text() + "\" appears in " + terms.docFreq() + " docs!");
                    TermDocs td = reader.termDocs(terms.term());
                    do {
                        if (td.freq() != 0) {
                            System.out.println("  In doc id: " + td.doc() + " it appears: "
                                    + td.freq() + " times");

                            TermFreqVector tfvector = reader.getTermFreqVector(td.doc(), terms.term().field());
                            TermPositionVector tpvector = (TermPositionVector) tfvector;

                            int termidx = tfvector.indexOf(terms.term().text());
                            int[] termposx = tpvector.getTermPositions(termidx);
                            TermVectorOffsetInfo[] tvoffsetinfo = tpvector.getOffsets(termidx);

                            for (int i = 0; i < termposx.length; i++) {
                                System.out.println("    Position : " + termposx[i]);
                                int offsetStart = tvoffsetinfo[i].getStartOffset();
                                int offsetEnd = tvoffsetinfo[i].getEndOffset();
                                System.out.println("      Offsets : " + offsetStart + " " + offsetEnd);
                            }
                        }
                    } while (td.next());
                }

            } while (terms.next());

            reader.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
