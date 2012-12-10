package com.pcwerk.seck.extractor.imagecolorfrequencyextractor;

import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import org.imgscalr.Scalr;

public class Picture {

    private BufferedImage bufferedImage;
    private WritableRaster raster;

    public Picture(Picture image) {	    
        bufferedImage = 
            new BufferedImage(image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        raster = bufferedImage.getRaster();
        raster.setRect(image.bufferedImage.getRaster());		
    }

    // Creates a Picture by loading the given file or URL.
    public Picture(String filename){
        load(filename);
    }

    // Resizing images using ImageSclr
    public void resizeImage(int w, int h){
        bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT,
            w, h, Scalr.OP_ANTIALIAS);
    }


    public int getWidth() { return bufferedImage.getWidth(); }

    public int getHeight() { return bufferedImage.getHeight(); }


    private void load(String filename){
        ImageIcon icon;

        try
        {
            if ((new File(filename)).exists())
                icon = new ImageIcon(filename);
            else {
                java.net.URL u = new java.net.URL(filename);
                icon = new ImageIcon(u);
            }
            Image image = icon.getImage();

            bufferedImage = 
                new BufferedImage(image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);

            resizeImage(100, 100);

            Graphics g = bufferedImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            raster = bufferedImage.getRaster();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }


    // The bitmap is in a left-to-right, top-to-bottom order. The first index is the row, the second index is the column.
    public Pixel[][] getBitmap() {

        int w = getWidth();
        int h = getHeight();

        Pixel[][] bmp = new Pixel[w][h];

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                bmp[x][y] = new Pixel(raster.getPixel(x, y, (int[]) null));
            }
        }

        return bmp;
    }

}
