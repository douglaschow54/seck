package com.pcwerk.seck.util;

import java.net.MalformedURLException;
import java.net.URL;

public class URLNormalizationUtil {

	public static String getNormalizedUrl(URL baseUrl, String uri)
	{
		if(uri.toLowerCase().startsWith("javascript")||
				uri.toLowerCase().startsWith("mailto"))
		{
		     return uri;
		}
		else
		{
			try {
	 		   URL normalizedUrl = new URL(baseUrl, uri);
                 	           return normalizedUrl.toString();
			} catch (MalformedURLException e) {
			   return uri;
			}
		}
	}
	
	public static URL getBaseUrl(URL pageUrl) 
	{	
		URL baseUrl;
		try {
			baseUrl = new URL( pageUrl.getProtocol() + "://" + pageUrl.getHost() );
		} catch (MalformedURLException e) {
			return pageUrl;
		}
		return baseUrl;
	}
}
