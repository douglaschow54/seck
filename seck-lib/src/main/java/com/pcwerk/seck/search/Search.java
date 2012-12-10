package com.pcwerk.seck.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Search {

    private InvertedIndexTable iti;
    private DatabaseDao databaseDao;
    private RankingMechanism rankingMechanism;

    public Search() {
        iti = new InvertedIndexTable();
        try {
            databaseDao = new HBaseImpl();
        } catch (IOException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        rankingMechanism = new LinkAnalysis();
    }

    public Search(InvertedIndexTable iti) {
        this.iti = iti;
        try {
            databaseDao = new HBaseImpl();
        } catch (IOException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        rankingMechanism = new LinkAnalysis();
    }

    public Map<String, Double> score() {


        //Example of ranking by LinkAnalysis(), which is instantiated in constructor.
        //We must pass in some object to the rank method.  Still undecided.

        //Get the score from link analysis, which is the map's value.
        HashMap<String, Double> linkAnalysisRank = rankingMechanism.rank(new Object());

        return linkAnalysisRank;


    }

    public InvertedIndexTable getInvertedIndexTable() {
        return iti;
    }

    public DatabaseDao getDatabase() {
        return databaseDao;
    }

    public RankingMechanism getRankingMechanism() {
        return rankingMechanism;
    }

    public void setDatabaseDao(DatabaseDao dao) {
        this.databaseDao = dao;
    }

    public void setRankingMechanism(RankingMechanism rm) {
        this.rankingMechanism = rm;
    }

    public void setInvertedTableIndex(InvertedIndexTable iti) {
        this.iti = iti;
    }
}
