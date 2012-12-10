package com.pcwerk.seck.search;

import java.util.List;

public class MySQLImpl implements DatabaseDao {

    public void put(String url, String body) {
        // TODO Auto-generated method stub
    }

    public void put(String url, String body, List<String> classifications) {
        // TODO Auto-generated method stub
    }

    public void update(String url, String body) {
        // TODO Auto-generated method stub
    }

    public void update(String url, List<String> classifications) {
        // TODO Auto-generated method stub
    }

    public void update(String url, String body, List<String> classifications) {
        // TODO Auto-generated method stub
    }

    public void delete(String url) {
        // TODO Auto-generated method stub
    }

    public String getBody(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getClassications(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Write to MySQL
     * @param pageRank 
     */
    public void put(List<PageRank> pageRank) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get from MySQL
     * @return 
     */
    public List<PageRank> getPageRankList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PageRank getPageRank(String docId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
