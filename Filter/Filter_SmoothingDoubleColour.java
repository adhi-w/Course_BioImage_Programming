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

public class Filter_SmoothingDoubleColour implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        if (arg.equals("about")) {
            showAbout();
            return DONE;
        }
        return DOES_RGB;
    }

    public void run(ImageProcessor orig) {
        int w = orig.getWidth();
        int h = orig.getHeight(); 
		
		double[][] FilterMatrix = {
									{0.075, 0.125, 0.075},
									{0.125,0.200,0.125},
									{0.075,0.0125,0.075}
								 };
		// original pixels
		int[] origpixels = (int[])orig.getPixels();
		//IJ.log("w : " + w + "h : " + h);
		//IJ.log("length " + origpixels.length);
		
        ImageProcessor copy = orig.duplicate();
		// copy pixels
		int[] copypixels = (int[])copy.getPixels();
		
        for (int v=1; v<=h-2; v++) {
            for (int u=1; u<=w-2; u++) {
                //compute filter result for position (u,v)
                double sumR = 0.0;
				double sumG = 0.0;
				double sumB = 0.0;
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
                        //int p = copy.getPixel(u+i,v+j);
						
						//int p = 0xff & copypixels[(v+j) * w + (u+i)];
						int p = copypixels[(v+j) * w + (u+i)];
						
						int r = (p&0xff0000) >> 16;
						int g = (p&0xff00) >> 8;
						int b = p&0xff;
						
						double c = FilterMatrix[i+1][j+1];
                        sumR = sumR + c*(double)r;
						sumG = sumG + c*(double)g;
						sumB = sumB + c*(double)b;
                    }
                }
                //int q = (int) (sum / 9.0);
				//int q = (int) sum;
				origpixels[v * w + u] = getIntFromColor((int)sumR,(int)sumG, (int)sumB);
            }
        }
		copy=null;
		
		
    }

	public int getIntFromColor(int Red, int Green, int Blue){
		Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
		Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
		Blue = Blue & 0x000000FF; //Mask out anything not blue.

		return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}
	
    void showAbout() {
        String cn = getClass().getName();
        IJ.showMessage("About "+cn+" ...",
            "3x3 Average Filter."
        );
    }
}
