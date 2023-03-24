/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2015/01/27
 */

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import java.util.Arrays;

public class Filter_Median3x3_getpixels implements PlugInFilter {
	
	final int K = 3*3/2;

    public int setup(String arg, ImagePlus imp) {
        if (arg.equals("about")) {
            showAbout();
            return DONE;
        }
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        
		int w = orig.getWidth();
        int h = orig.getHeight();
        ImageProcessor copy = orig.duplicate();
        
        //vector to hold pixels from 3x3 neighborhood
        int[] P = new int[2*K+1];
        byte[] copypixels = (byte[])copy.getPixels();
		byte[] originalpixels = (byte[]) orig.getPixels();
		
        for (int v=1; v<=h-2; v++) {
            for (int u=1; u<=w-2; u++) {
                
                //fill the pixel vector P for filter position (u,v)
                int k = 0;
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
						P[k] = 0xff & copypixels[(v+j)*w+(u+i)];
                        //P[k] = copy.getPixel(u+i,v+j);
                        k++;
                    }
                }
                //sort the pixel vector and take center element
                Arrays.sort(P);
                //orig.putPixel(u,v,P[K]);
				originalpixels[v*w+u] = (byte)(0xff & P[K]);
            }
        }
    }

    void showAbout() {
        String cn = getClass().getName();
        IJ.showMessage("About " + cn + " ...",
            "3x3 median filter."
        );
    }
}
