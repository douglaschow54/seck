package com.pcwerk.seck.search;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseImpl implements DatabaseDao {

    private static Configuration config;
    private static HTable table;
    private static final byte[] id = Bytes.toBytes("id");
    private static final byte[] url = Bytes.toBytes("url");
    private static final byte[] lin = Bytes.toBytes("lin");
    private static final byte[] lout = Bytes.toBytes("lout");
    private static final byte[] score = Bytes.toBytes("score");
    private static final byte[] empty = Bytes.toBytes("");

    public HBaseImpl() throws IOException {
        config = HBaseConfiguration.create();

        config.set("hbase.zookeeper.quorum", "hbase-demo");

        table = new HTable(config, "pagerank");
    }

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

    public void put(List<PageRank> pageRank) {
        for (PageRank p : pageRank) {
            try {
                put(p);
            } catch (IOException ex) {
                Logger.getLogger(HBaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<PageRank> getPageRankList() {

        List<PageRank> pageRankList = new LinkedList<PageRank>();
        try {
            Scan s = new Scan();
            s.addColumn(id, empty);
            s.addColumn(url, empty);
            s.addColumn(lin, empty);
            s.addColumn(lout, empty);
            s.addColumn(score, empty);

            ResultScanner scanner = null;
            try {
                scanner = table.getScanner(s);
            } catch (IOException ex) {
                Logger.getLogger(HBaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Result result = scanner.next(); result != null; result = scanner.next()) {

                String rId = Bytes.toString(result.getValue(id, empty));
                String rURL = Bytes.toString(result.getValue(url, empty));
                String rLinksIn = Bytes.toString(result.getValue(lin, empty));
                String rLinksOut = Bytes.toString(result.getValue(lout, empty));
                float rScore = (result.getValue(score, empty) != null ? Bytes.toFloat(result.getValue(score, empty)) : Float.valueOf("0.0"));

                PageRank r = new PageRank();
                r.setID(rId);
                r.setScore(rScore);
                r.setURL(rURL);
                r.setLinkIns(getAsList(rLinksIn));
                r.setLinkOuts(getAsList(rLinksOut));

                // System.out.println("Found row: " + result);

                pageRankList.add(r);
            }

        } catch (IOException ex) {
            Logger.getLogger(HBaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pageRankList;
    }

    private List<String> getAsList(String list) {

        if (list == null) {
            return new LinkedList<String>();
        }

        if (list.startsWith("[")) {
            list = list.substring(1, list.length() - 1);
        }
        if (list.endsWith("]")) {
            list = list.substring(list.length() - 1);
        }

        String[] listArray = list.split(",");
        List<String> result = new LinkedList<String>();

        if (listArray == null) {
            return result;
        }

        for (String listItem : listArray) {
            result.add(listItem.trim());
        }

        return result;

    }

    public void put(PageRank pageRank) throws IOException {
        // Intantiate with row name
        Put put = new Put(Bytes.toBytes(pageRank.getID()));

        // Populate value for each column: column family, column attribute, column value
        put.add(id, empty, Bytes.toBytes(pageRank.getID()));
        put.add(url, empty, Bytes.toBytes(pageRank.getURL()));
        put.add(lin, empty, Bytes.toBytes(pageRank.getLinkIns().toString()));
        put.add(lout, empty, Bytes.toBytes(pageRank.getLinkOuts().toString()));
        put.add(score, empty, Bytes.toBytes(pageRank.getScore()));

        System.out.println("Write doc for url: " + pageRank.getURL() + " : " + pageRank.getLinkIns().toString());
        table.put(put);
    }

    public PageRank getPageRank(String docId) {

        Get get = new Get(Bytes.toBytes(docId));

        get.addColumn(id, empty);
        get.addColumn(url, empty);
        get.addColumn(lin, empty);
        get.addColumn(lout, empty);
        get.addColumn(score, empty);

        Result result = null;
        try {
            result = table.get(get);
        } catch (IOException ex) {
            Logger.getLogger(HBaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }


        String rId = Bytes.toString(result.getValue(id, empty));
        String rURL = Bytes.toString(result.getValue(url, empty));
        String rLinksIn = Bytes.toString(result.getValue(lin, empty));
        String rLinksOut = Bytes.toString(result.getValue(lout, empty));
        float rScore = (result.getValue(score, empty) != null ? Bytes.toFloat(result.getValue(score, empty)) : Float.valueOf("0.0"));

        PageRank r = new PageRank();
        r.setID(rId);
        r.setScore(rScore);
        r.setURL(rURL);
        r.setLinkIns(getAsList(rLinksIn));
        r.setLinkOuts(getAsList(rLinksOut));


        return r;

    }
}
