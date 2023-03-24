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

public class Filter_WeightLaplacian3x3_getpixels implements PlugInFilter {
	
	int[][] weight = { {1,1,1},
		               {1,-8,1},
					   {1,1,1} };
					  
	int p_length = 0;	
	int K = 0;	

    public int setup(String arg, ImagePlus imp) {
        if (arg.equals("about")) {
            showAbout();
            return DONE;
        }
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        
		double weightVal = 1.0;
		int w = orig.getWidth();
        int h = orig.getHeight();
        ImageProcessor copy = orig.duplicate();
        ImageProcessor LaplaceRes = orig.duplicate();
		ImageProcessor Result = orig.duplicate();
		
        //vector to hold pixels from 3x3 neighborhood
        
        byte[] copypixels = (byte[])copy.getPixels();
		byte[] Laplacepixels = (byte[])LaplaceRes.getPixels();
		byte[] originalpixels = (byte[]) orig.getPixels();
		byte[] resultpixels = (byte[]) Result.getPixels();
		
		
        for (int v=1; v<=h-2; v++) {
            for (int u=1; u<=w-2; u++) {
                
                //fill the pixel vector P for filter position (u,v)
                int k = 0;
				
				int total = 0;
				
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
						int tmpVal = 0xff & copypixels[(v+j)*w+(u+i)];
						total += (tmpVal*weight[j+1][i+1]);
						k++;					
                    }
                }
				

				total = Math.min(Math.max(total,0),255);
				Laplacepixels[v*w+u] = (byte)(0xff & total);
				
				double totalWithWeight = (double)total * weightVal;
				totalWithWeight = Math.min(Math.max(totalWithWeight,0),255);
				
				int tmpVal = 0xff & copypixels[v*w+u];
				int curValue = tmpVal - (int)totalWithWeight;
				curValue = Math.min(Math.max(curValue,0),255);
				
				resultpixels[v*w+u] = (byte)(0xff & curValue);
            }
        }
		
		ImagePlus LaplaceIm = new ImagePlus("Laplace Filter", LaplaceRes);
		LaplaceIm.show();

		ImagePlus ResultIm = new ImagePlus("Result Filter", Result);
		ResultIm.show();		
		
    }

    void showAbout() {
        String cn = getClass().getName();
        IJ.showMessage("About " + cn + " ...",
            "3x3 median filter."
        );
    }
}