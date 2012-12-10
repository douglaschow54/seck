package com.pcwerk.seck.extractor.imagecolorfrequencyextractor;

import java.util.HashMap;
import java.util.Map;

import com.pcwerk.seck.extractor.MediaExtractor;

public class MostFrequentColorExtractor {

    private Picture p;
    private Map<Pixel,String> colorNameMap = new HashMap<Pixel,String>();
    private MediaExtractor me;
    
    public MostFrequentColorExtractor(String imageFile, MediaExtractor me){
        addingColorNames();
        p = new Picture(new Picture(imageFile));
        this.me = me;
    }

    public void extractMostFrequentColor(){

        Pixel[][] bmp = p.getBitmap();

        ColorMap cMap = new ColorMap();
       
        for(int h=0; h<p.getHeight(); h++){
            for(int w=0; w<p.getHeight(); w++){
                cMap.put( bmp[h][w]);
            }       
        }

        Pixel frequentColor = cMap.getFrequentColor();

        int min = -1;
        String colorName = "";

        for (Map.Entry<Pixel,String> entry : colorNameMap.entrySet()){
            int distance = frequentColor.distance( entry.getKey() );

            if ( min == -1 || min > distance){
                min = distance;
                colorName = entry.getValue();
            }    
        }

//        System.out.println("The most frequent color is within a distance of "+ min + " to the color " + colorName);
//        System.out.println("and the frequency is: " + cMap.getValue(frequentColor));
//        System.out.println("-----------------------");
        

        me.addMetadata( "Most Frequent Color is close to", colorName );
        me.addMetadata( "The Most Frequent Color is close to " + colorName + " within a distance of", min+"" );
        me.addMetadata( "The frequency of the Most Frequent Color", cMap.getValue(frequentColor) + "");

    }


    public void addingColorNames(){

        colorNameMap.put( new Pixel(255,255,255), "White" );        

        colorNameMap.put( new Pixel(0,0,0) , "Black" ); 

        colorNameMap.put( new Pixel(0,0,255) , "Blue" );

        colorNameMap.put( new Pixel(0,128,0) , "Green" ); 

        colorNameMap.put( new Pixel(255,255,0), "Yellow" );         

        colorNameMap.put( new Pixel(255,0,0), "Red" );    
        
        colorNameMap.put( new Pixel(128,0,128), "Purple" );

    }

}