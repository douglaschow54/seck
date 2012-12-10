package com.pcwerk.seck.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;

import com.pcwerk.seck.extractor.Extractor;
import com.pcwerk.seck.extractor.ExtractorFactory;
import com.pcwerk.seck.store.WebDocument;

public class ExtractorTest {
    public ExtractorTest() {
    }
	
	public void run(String fileName, String sourceURL)
	{		
		System.out.println("run():enter");
		ExtractorFactory efactory = new ExtractorFactory();		
		File file = new File(fileName);		
		if(file.exists())
		{		
			Extractor ex = efactory.getExtractor(file);	
			try {
				if(ex!=null)
				{ 
					URL url = sourceURL == "" ? null : new URL(sourceURL);
					WebDocument webDoc = ex.extract(url);	
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
					}
					
					System.out.println("Content:" + webDoc.getContent());
					System.out.println("");
					Metadata m =  webDoc.getMetadata();
					for (String name : m.names()) {
						System.out.println( name + "->" + m.get(name));
					}
				}
			} catch (IOException e) {
				System.out.println("run()-error:" + e.toString());
			}
		}
		else
		{
			System.out.println("File Not found.");
		}
		System.out.println("run():exit");
	}
  
  public static void main(String[] args) {
    try {
      String fileName =  args.length > 0 ? args[0] : "";
      String url = args.length > 1 ? args[1] : "";	
      new ExtractorTest().run(fileName, url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
