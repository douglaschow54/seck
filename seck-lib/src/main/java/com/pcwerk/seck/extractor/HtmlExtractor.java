package com.pcwerk.seck.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;

import com.pcwerk.seck.store.WebDocument;
import com.pcwerk.seck.util.URLNormalizationUtil;

public class HtmlExtractor extends Extractor {
	protected static final Logger log = Logger.getLogger(Extractor.class.getName());
	public HtmlExtractor(File file) throws FileNotFoundException
	{	
		super(file);
	}	

	
	public WebDocument extract(URL sourceUrl)
	{	
	WebDocument docInfo = new WebDocument();
        LinkContentHandler linkHandler = new LinkContentHandler();
        int writeLimit = 10 * 1024 * 1024;
        ContentHandler textHandler = new BodyContentHandler(writeLimit);

        ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
                        
        TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();
        Parser parser = new HtmlParser();
        try {
        	InputStream inputStream = new FileInputStream(this.file); 
			parser.parse(inputStream, teeHandler, metadata, parseContext);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
        
       //normalize the links found from page
        List<Link> normalizedLinks = new ArrayList<Link>();
 		URL baseUrl = URLNormalizationUtil.getBaseUrl(sourceUrl);
        for(Link aLink : linkHandler.getLinks() )
        {
    	   if(aLink.isAnchor())
    	   {    	
    		   if((aLink.getUri().toLowerCase().startsWith("javascript")||
    			   aLink.getUri().toLowerCase().startsWith("mailto")))
    		   {
    			   normalizedLinks.add(aLink);   
    		   }
    		   else
    		   {
    			   String normalizedUrl = URLNormalizationUtil.getNormalizedUrl(baseUrl, aLink.getUri());
    			   Link normalizedLink = new Link(aLink.getType(), normalizedUrl.toString(), aLink.getTitle(), aLink.getText());
    			   normalizedLinks.add(normalizedLink);  
		    	}
    	   }
    	   else if(aLink.isImage())
    	   {
			   String normalizedUrl = URLNormalizationUtil.getNormalizedUrl(baseUrl, aLink.getUri());
			   Link normalizedLink = new Link(aLink.getType(), normalizedUrl.toString(), aLink.getTitle(), aLink.getText());
			   normalizedLinks.add(normalizedLink);      		   
    	   }
       }        
        docInfo.setLinks(normalizedLinks);
        docInfo.setMetadata(metadata);
        docInfo.setContent(textHandler.toString());
        docInfo.setUrl(sourceUrl.toString());
        return docInfo;
	}
}
