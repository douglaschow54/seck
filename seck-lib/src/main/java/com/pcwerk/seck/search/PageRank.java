package com.pcwerk.seck.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageRank implements Serializable {

    private static final long serialVersionUID = 1L;
    String id;
    String url;
    List<String> linkIns;		//	List of documents' id
    List<String> linkOuts;		//	List of documents' id
    float score;

    public PageRank() {
        linkOuts = new ArrayList<String>();
        linkIns = new ArrayList<String>();
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setLinkIns(List<String> linkIns) {
        this.linkIns.addAll(linkIns);
    }

    public void setLinkOuts(List<String> linkOuts) {
        this.linkOuts.addAll(linkOuts);
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getID() {
        return id;
    }

    public String getURL() {
        return url;
    }

    public List<String> getLinkIns() {
        return linkIns;
    }

    public List<String> getLinkOuts() {
        return linkOuts;
    }

    public float getScore() {
        return score;
    }
}
