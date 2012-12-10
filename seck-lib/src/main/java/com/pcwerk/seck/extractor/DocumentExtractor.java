package com.pcwerk.seck.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import com.pcwerk.seck.store.WebDocument;

public class DocumentExtractor extends Extractor {

	public DocumentExtractor(File file) {
		super(file);
	}
	
	public WebDocument extract(URL sourceUrl) throws IOException {
		FileInputStream is = null;
		WebDocument webDoc = new WebDocument();
		int writeLimit = 10 * 1024 * 1024;
	    try {
	      is = new FileInputStream(this.file);
	      ContentHandler contenthandler = new BodyContentHandler(writeLimit);
	      Metadata metadata = new Metadata();
	      metadata.set(Metadata.RESOURCE_NAME_KEY, this.file.getName());
	      ParseContext parseContext = new ParseContext();
	      Parser parser = new AutoDetectParser();
	      parser.parse(is, contenthandler, metadata, parseContext);			
	      webDoc.setMetadata(metadata);
		  webDoc.setContent(contenthandler.toString());
		  webDoc.setUrl(sourceUrl.toString());
	    }catch(Exception e)
	    {
	    	System.out.println("DocumentExtractor-extract()-Error:" + e.getMessage());
	    }
		return webDoc;
	}	
}
