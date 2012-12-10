package com.pcwerk.seck.search;

import java.util.List;

public interface DatabaseDao {

    public void put(String url, String body);

    public void put(String url, String body, List<String> classifications);

    public void update(String url, String body);

    public void update(String url, List<String> classifications);

    public void update(String url, String body, List<String> classifications);

    public void delete(String url);

    public String getBody(String url);

    public List<String> getClassications(String url);
    
    public void put(List<PageRank> pageRank);
    
    public List<PageRank> getPageRankList();
    
    public PageRank getPageRank(String docId);
}
