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

public class Filter_SmoothingInteger implements PlugInFilter {

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
		
		int[][] FilterMatrix = {
									{3, 5, 3},
									{5,8,5},
									{3,5,3}
								 };
		// original pixels
		byte[] origpixels = (byte[])orig.getPixels();
		
        ImageProcessor copy = orig.duplicate();
		// copy pixels
		byte[] copypixels = (byte[])copy.getPixels();
		
        for (int v=1; v<=h-2; v++) {
            for (int u=1; u<=w-2; u++) {
                //compute filter result for position (u,v)
                int sum = 0;
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
                        //int p = copy.getPixel(u+i,v+j);
						int p = 0xff & copypixels[(v+j) * w + (u+i)];
						int c = FilterMatrix[i+1][j+1];
                        sum = sum + c*p;
                    }
                }
                int q = (int) (sum / 40.0);
				//int q = (int) sum;
				origpixels[v * w + u] = (byte)(0xff&q);
            }
        }
		copy=null;
		
		
    }

    void showAbout() {
        String cn = getClass().getName();
        IJ.showMessage("About "+cn+" ...",
            "3x3 Average Filter."
        );
    }
}
