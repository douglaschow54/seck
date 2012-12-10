package com.pcwerk.seck.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;

public class WebDocument {
	private String url;
	private String hash;
	private String location;
	private String content;
	private List<Link> links;
	private Metadata metadata;
	
	public WebDocument()
	{
		url = "";
		hash = "";
		location = "";
		links = new ArrayList<Link>();
	    metadata = null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isHTML(){
		String contentType = this.getMetadata()!=null? 
				this.getMetadata().get("Content-Type"):"";

		if (contentType!= null &&
			!contentType.isEmpty() &&  
			contentType.startsWith("text/html"))
			return true;
		return false;
	}
	
}
