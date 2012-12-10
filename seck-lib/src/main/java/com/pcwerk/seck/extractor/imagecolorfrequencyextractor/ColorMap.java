package com.pcwerk.seck.extractor.imagecolorfrequencyextractor;


import java.util.Map;
import java.util.TreeMap;


public class ColorMap {
    // Maps pixel with frequency
    private Map<Pixel, Integer> cMap = new TreeMap<Pixel, Integer>();

    public void put(Pixel p) {
        if (contains(p))
            cMap.put(p, cMap.get(p) + 1);
        else
            cMap.put( p, 1 );
    }

    public boolean contains(Pixel p) {
        return cMap.containsKey(p);
    }

    // Returns the frequency of a given pixel if it exits in the map.
    public Integer getValue(Pixel p) {
        return cMap.get(p);
    }

    public int size() {
        return cMap.size();
    }

    public Pixel getFrequentColor(){
        Integer max = -1;
        Pixel frequentPixel = new Pixel();
        for (Map.Entry<Pixel,Integer> entry : cMap.entrySet()){
            if ( max<entry.getValue() ){
//                System.out.println( "More Frequent Color " + entry.getKey() + " with frequency " +  entry.getValue() );
                max = entry.getValue();
                frequentPixel = entry.getKey();
            }
        }
        
        // Adding very close pixels to the most frequent color to the frequency of the most frequent color
        for (Map.Entry<Pixel,Integer> entry : cMap.entrySet()){
            if (frequentPixel.distance( entry.getKey() ) <= 5 ){
                cMap.put(frequentPixel, cMap.get(frequentPixel) + entry.getValue() );
            }
            
        }
        

        return frequentPixel;
    }

}