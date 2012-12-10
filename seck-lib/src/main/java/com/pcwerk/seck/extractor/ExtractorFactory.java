package com.pcwerk.seck.extractor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;

import com.pcwerk.seck.store.WebDocument;
public class ExtractorFactory {
	
	public ExtractorFactory()
	{	
		//do nothing
	}

	public Extractor getExtractor(File file)
	{		
		Tika tika = new Tika();
		String mimeType = "";
		try {
			mimeType = tika.detect(file);			
			if(mimeType.equals("text/html")) 
				return new HtmlExtractor(file);
			else if(mimeType.startsWith("audio") || 
					mimeType.startsWith("image") ||
					mimeType.startsWith("video")) 
				return new MediaExtractor(file);
			else if(mimeType.startsWith("application") ||
					mimeType.startsWith("text/"))
				return new DocumentExtractor(file);	
			return null;
		} 
		catch (Exception e)
		{
			System.out.println("ExtractorFactory-getExtractor-Error: " + e.getMessage());
		}
		return null;	
	}	
	
	

/*	
	public static void main( String[] args )
	{		
		System.out.println("start");
		ExtractorFactory efactory = new ExtractorFactory();

		String fileName =  "C:\\MyData\\School\\CS454\\workspace\\seck-0.0.4-src-v10-beta\\seck-0.0.4\\seck-app\\target\\temp internet files\\ID_1\\8ffdefbdec956b595d257f0aaeefd623.html";//args[0];
		
		String sourceURL = "http://newspaper.li/static/ba4bffea061eb7df9a93f937b31376e5.jpg";// args[1];
		
		if (fileName==null) {
			System.out.println("Null file argument.");
			System.exit(0);
		}
		if (sourceURL == null) {
			System.out.println("Null source URL argumen.");
			System.exit(0);
		}
		
		File file = new File(fileName);		
		if(file.exists())
		{		
			Extractor ex = efactory.getExtractor(file);	
			try {
				if(ex!=null)
				{ 
					WebDocument webDoc = ex.extract(new URL(sourceURL));	
					if(webDoc.isHTML())
					{
						List<Link> links = webDoc.getLinks();

						for(Link link : links)
						{
							if (link.isAnchor())
							{
								System.out.println("HREF: text:" + link.getText() + " -- url: " + link.getUri());
							}
							else if (link.isImage())
							{
								System.out.println("IMG: text:" + link.getText() + " -- url: " + link.getUri());
							}
						}
						Metadata m =  webDoc.getMetadata();
						for (String name : m.names()) {
						    System.out.println( "key:" + name + ",value:" + m.get(name));
						}
					}
					else
					{
						Metadata m =  webDoc.getMetadata();
						for (String name : m.names()) {
							System.out.println( "key:" + name + ",value:" + m.get(name));
						}
					}
				}
			} catch (IOException e) {
			}
		}
		else
		{
			System.out.println("File Not found.");
		}
		System.out.println("end");
	}
*/	
}
