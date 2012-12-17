package com.pcwerk.seck.search;

public class IndexedTerm {

    private String docId;
    private String url;
    private int count;
    private int position[];
    private int startOffset[];
    private int endOffset[];
    float pageRank;

    public IndexedTerm() {
    }

    ;

	public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPosition(int position[]) {
        this.position = position;
    }

    public void setStartOffset(int startOffset[]) {
        this.startOffset = startOffset;
    }

    public void setEndOffset(int endOffset[]) {
        this.endOffset = endOffset;
    }

    public void setPageRank(float pageRank) {
        this.pageRank = pageRank;
    }

    public String getDocId() {
        return docId;
    }

    public String getURL() {
        return url;
    }

    public int getCount() {
        return count;
    }

    public int[] getPosition() {
        return position;
    }

    public int[] getStartOffset() {
        return startOffset;
    }

    public int[] getEndOffset() {
        return endOffset;
    }

    public float getPageRank() {
        return pageRank;
    }
}
