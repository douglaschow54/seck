package com.pcwerk.seck.extractor.imagecolorfrequencyextractor;

public class Pixel implements Comparable<Pixel> {

    // Component is an array of 3 RGB colors.  
    private int[] component;

    public Pixel(){
        component = new int[3];
        component[0] = 0;
        component[1] = 0;
        component[2] = 0; 
    }
    
   // creates a new pixel by passing 3 color values
    public Pixel(int r, int g, int b) {
        component = new int[3];
        component[0] = r;
        component[1] = g;
        component[2] = b;
    }

    // creates a new pixel by passing array of color values 
    Pixel(int[] c) {
        component = new int[c.length];
        for (int i = 0; i < c.length; i++) 
            component[i] = c[i];
    }

     
    public int getRed() { 
        return component[0];
    }

   
    public int getGreen() { 
        return component[1];
    }

   
    public int getBlue() { 
        return component[2];
    }

    public int[] getComponents() {
        return component;
    }

    
    // Determines how similar this pixel is to another.
    public int distance(Pixel px) {
        return
            Math.abs(getRed() - px.getRed()) +
            Math.abs(getBlue() - px.getBlue()) +
            Math.abs(getGreen() - px.getGreen());
    }

   
    public String toString() {
        String s = "(";
        for (int k = 0; k < component.length; k++) {
            s += component[k];
            if (k != component.length - 1) { s += ","; }
        }
        return s + ")";
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof Pixel) {	
            Pixel o = (Pixel) other;

            if (o.component.length == component.length) {

                for (int k = 0; k < component.length; k++) {
                    if (o.component[k] != component[k])
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    public int compareTo(Pixel o) {
        int rc = getRed() - o.getRed();
        int gc = getGreen() - o.getGreen();
        int bc = getBlue() - o.getBlue();

        if (rc != 0) {
            return rc;
        } else if (gc != 0) {
            return gc;
        } else {
            return bc;
        }
    }

}