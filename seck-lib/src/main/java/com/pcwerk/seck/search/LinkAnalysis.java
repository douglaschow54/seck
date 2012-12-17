package com.pcwerk.seck.search;

import com.pcwerk.seck.store.WebDocument;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkAnalysis implements RankingMechanism {

    public static DatabaseDao db;
    
    static {
        try
        {
            db = DatabaseFactory.getDatabase(DatabaseFactory.Type.HBASE);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public LinkAnalysis() {
    }

    @SuppressWarnings("unchecked")
    public void linkAnalysis(float lambda, String fileName) {


        try {
            InputStream file;
            file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            List<WebDocument> webContentList = (List<WebDocument>) input.readObject();

            List<PageRank> pageRankList = new ArrayList<PageRank>();
            Map<String, String> linkMap = new HashMap<String, String>(); 		// <url, id>
            Map<String, PageRank> pageMap = new HashMap<String, PageRank>();	// <id, pageRank>

            // Initialize pageRank
            int id = 0;
            for (WebDocument webContent : webContentList) {
                PageRank pageRank = new PageRank();
                pageRank.setID(Integer.toString(id++));
                pageRank.setURL(webContent.getUrl());
                pageRank.setLinkOuts(webContent.getLinkURIs());
                pageRank.setScore((float) 1 / (float) webContentList.size());
                pageRankList.add(pageRank);
                linkMap.put(pageRank.getURL(), pageRank.getID());
            }

            // Set up linkOuts (replace url with id)
            for (int i = 0; i < pageRankList.size(); i++) {
                List<String> linkOuts = new ArrayList<String>();
                List<String> links = pageRankList.get(i).getLinkOuts();

                for (String link : links) {
                    String linkId = linkMap.get(link);
                    linkOuts.add(linkId);
                }

                pageRankList.get(i).getLinkOuts().clear();
                pageRankList.get(i).setLinkOuts(linkOuts);
            }

            // Set up linkIns
            for (int i = 0; i < pageRankList.size(); i++) {
                List<String> linkIns = new ArrayList<String>();
                String linkId = pageRankList.get(i).getID();

                for (int j = 0; j < pageRankList.size(); j++) {
                    if (j != i) {
                        String linkId1 = pageRankList.get(j).getID();
                        List<String> linkOuts = pageRankList.get(j).getLinkOuts();
                        if (linkOuts.contains(linkId)) {
                            linkIns.add(linkId1);
                        }
                    }
                }

                pageRankList.get(i).setLinkIns(linkIns);
            }

            // Set up pageMap
            for (PageRank pageRank : pageRankList) {
                pageMap.put(pageRank.getID(), pageRank);
            }

            // Calculate page rank
            float e = 0.0001f;
            boolean stop = false;
            int iter = 0;
            int iterMax = 1000;

            do {
                stop = true;
                float newScores[] = new float[pageRankList.size()];
                for (int i = 0; i < pageRankList.size(); i++) {
                    float oldScore = pageRankList.get(i).getScore();
                    float newScore = lambda / (float) webContentList.size();

                    for (String linkIn : pageRankList.get(i).getLinkIns()) {
                        PageRank pageRank = pageMap.get(linkIn);
                        newScore += (1 - lambda) * (pageRank.getScore() / (float) pageRank.getLinkOuts().size());
                    }

                    newScores[i] = newScore;
                    stop &= (Math.abs(newScore - oldScore) < e);
                }
                // Update score
                for (int i = 0; i < pageRankList.size(); i++) {
                    pageRankList.get(i).setScore(newScores[i]);
                    // Update pageMap
                    pageMap.put(pageRankList.get(i).getID(), pageRankList.get(i));
                }
            } while (!stop && iter++ < iterMax);

            // Save pageRankList
            db.put(pageRankList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public void linkAnalysisInfo() {
        System.out.println("PageRank");
        List<PageRank> pageRankList = db.getPageRankList();

        // Print out score
        for (PageRank pageRank : pageRankList) {
            System.out.printf("%s\t%s\t%f\n", pageRank.getID(), pageRank.getURL(), pageRank.getScore());
        }
    }

    public HashMap<String, Double> rank(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
