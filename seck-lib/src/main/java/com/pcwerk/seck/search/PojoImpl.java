package com.pcwerk.seck.search;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class PojoImpl implements DatabaseDao {

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
     * Save to disk
     *
     * @param pageRank
     */
    public void put(List<PageRank> pageRank) {
        // Save pageRankList
        try {
            FileOutputStream fout = new FileOutputStream("pageRank");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(pageRank);
            oos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get from disk
     *
     * @return
     */
    public List<PageRank> getPageRankList() {
        InputStream file = null;
        InputStream buffer = null;
        ObjectInput input = null;
        List<PageRank> pageRankList = null;
        try {
            file = new FileInputStream("pageRank");
            buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);

            pageRankList = (List<PageRank>) input.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (input != null) {
                    input.close();
                }
                if (buffer != null) {
                    buffer.close();
                }
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pageRankList;
    }

    public PageRank getPageRank(String docId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
