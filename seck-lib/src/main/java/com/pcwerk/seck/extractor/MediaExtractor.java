package com.pcwerk.seck.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import com.pcwerk.seck.extractor.imagecolorfrequencyextractor.MostFrequentColorExtractor;
import com.pcwerk.seck.store.WebDocument;;

public class MediaExtractor extends Extractor {	

    protected static final Logger log = Logger.getLogger(Extractor.class.getName());

    private Metadata metadata = new Metadata();

    public MediaExtractor(File file) throws FileNotFoundException
    {
        super(file);
    }

    public WebDocument extract(URL sourceUrl) throws IOException {
        WebDocument docInfo = new WebDocument();

        Tika tika = new Tika();
        String mimeType = "";
        mimeType = tika.detect(this.file);
        mimeType = tika.detect(file);           

        // If the media File is an image then detect the most frequent color
        if(mimeType.startsWith("image")) {
            MostFrequentColorExtractor mfce = new MostFrequentColorExtractor(this.file.getAbsolutePath(),this); 
            mfce.extractMostFrequentColor(); // Adds 3 new metadata for color frequency
        }

        
        try{
            InputStream inputStream = new FileInputStream(this.file); 
            Parser parser = new AutoDetectParser();            
            ParseContext parseContext = new ParseContext();

            ContentHandler handler = new BodyContentHandler();
            parser.parse(inputStream, handler, metadata, parseContext);

        } catch (Exception e) {

            e.printStackTrace();
        }

        docInfo.setMetadata(metadata);
        docInfo.setUrl(sourceUrl.toString());
        return docInfo;
    }

    public void addMetadata(String propertyName, String propertyValue){
        metadata.add( propertyName, propertyValue ); 
    }

}