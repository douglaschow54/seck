package com.pcwerk.seck.search;

import com.pcwerk.seck.search.DatabaseDao;
import com.pcwerk.seck.search.PageRank;
import com.pcwerk.seck.search.IndexedTerm;
import com.pcwerk.seck.search.LinkAnalysis;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.index.TermVectorOffsetInfo;
import org.apache.lucene.store.FSDirectory;

public class GetIndexData {

    private static DatabaseDao db = LinkAnalysis.db;
    
    public GetIndexData() {
    }

    ;
	
	@SuppressWarnings("unchecked")
    public List<PageRank> pageRankList() {
        try {
            InputStream file = new FileInputStream("pageRank");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            List<PageRank> pageRankList = (List<PageRank>) input.readObject();

            return pageRankList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Returns indexed document terms
     * @param query
     * @return 
     */
    public List<IndexedTerm> getIndexedTermList(String query) {

        IndexReader reader;
        try {
            reader = IndexReader.open(FSDirectory.open(new File("index-directory")));

            List<IndexedTerm> indexedTermList = new ArrayList<IndexedTerm>();

            // Term count + position + offset + page rank
            Term term = new Term("content", query);
            TermDocs td = reader.termDocs(term);
            do {
                if (td.freq() != 0) {
                    IndexedTerm indexedTerm = new IndexedTerm();
                    indexedTerm.setDocId(Integer.toString(td.doc()));
                    Document document = reader.document(td.doc());
                    indexedTerm.setURL(document.get("url"));
                    indexedTerm.setCount(td.freq());

                    TermFreqVector tfvector = reader.getTermFreqVector(td.doc(), term.field());
                    TermPositionVector tpvector = (TermPositionVector) tfvector;

                    int termidx = tfvector.indexOf(term.text());
                    int[] termposx = tpvector.getTermPositions(termidx);
                    TermVectorOffsetInfo[] tvoffsetinfo = tpvector.getOffsets(termidx);

                    indexedTerm.setPosition(termposx);
                    int startOffset[] = new int[termposx.length];
                    int endOffset[] = new int[termposx.length];
                    for (int i = 0; i < termposx.length; i++) {
                        startOffset[i] = tvoffsetinfo[i].getStartOffset();
                        endOffset[i] = tvoffsetinfo[i].getEndOffset();
                    }
                    indexedTerm.setStartOffset(startOffset);
                    indexedTerm.setEndOffset(endOffset);
                    indexedTerm.setPageRank(getPageRank(Integer.toString(td.doc())));

                    indexedTermList.add(indexedTerm);
                }
            } while (td.next());

            reader.close();

            return indexedTermList;

        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns pagerank for a given documentId
     * @param docId
     * @return 
     */
    public float getPageRank(String docId) {
        
        PageRank p = null;
        
        try {
            p = db.getPageRank(docId);
        }
        catch (Exception e)
        {
            return (float) 0.0;
        }
        
        return p.getScore();
    }
}
