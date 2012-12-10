package com.pcwerk.seck.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;

public class WebDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    private String url;
    private LinkedList<String> linkURIs;
    private List<Link> links;
    private String content;
    
    private String hash;
    private String location;
    private Metadata metadata;    

    public WebDocument() {
        url = null;
        linkURIs = new LinkedList<String>();
        links = new ArrayList<Link>();
        content = null;
        
        hash = "";
        location = "";
        metadata = null;        
    }

    public void setUrl(String URL) {
        this.url = URL;
    }

    public void setLinkURIs(LinkedList<String> links) {
        for (String str : links) {
            if (!this.linkURIs.contains(str)) {
                this.linkURIs.add(str);
                this.links.add(new Link(null, str , null, null, null) );
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public LinkedList<String> getLinkURIs() {
        return linkURIs;
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
