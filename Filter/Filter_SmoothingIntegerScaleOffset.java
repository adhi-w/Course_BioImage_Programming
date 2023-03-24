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

public class Filter_SmoothingIntegerScaleOffset implements PlugInFilter {

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
									{0, -1, -2,-1,0},
									{-1, -2, 16,-2,-1},
									{0, -1, -2,-1,0}
								 };
								 

		//calculate scale
		int scale = 0;
		for(int i = 0; i < FilterMatrix.length;i++)
		{
				for(int j = 0;j < FilterMatrix[i].length;j++)
				{
						scale += FilterMatrix[i][j];
				}
		}
		
		// original pixels
		byte[] origpixels = (byte[])orig.getPixels();
		
        ImageProcessor copy = orig.duplicate();
		// copy pixels
		byte[] copypixels = (byte[])copy.getPixels();
		
		int offset = 0;
        for (int v=1; v<=h-2; v++) {
            for (int u=2; u<=w-3; u++) {
                //compute filter result for position (u,v)
                int sum = 0;
                for (int j=-1;j<=1; j++) {
                    for (int i=-2; i<=2; i++) {
                        //int p = copy.getPixel(u+i,v+j);
						int p = 0xff & copypixels[(v+j) * w + (u+i)];
						int c = FilterMatrix[j+1][i+2];
                        sum = sum + c*p;
                    }
                }
                int q = offset + (int) (sum / scale);
				q = Math.min(Math.max(q,0),255);
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
