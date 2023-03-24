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

public class Filter_WeightMedian3x3_getpixels implements PlugInFilter {
	
	int[][] weight = { {1,1,1},
		               {1,1,1},
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
        
		int w = orig.getWidth();
        int h = orig.getHeight();
        ImageProcessor copy = orig.duplicate();
        
        //vector to hold pixels from 3x3 neighborhood
        
        byte[] copypixels = (byte[])copy.getPixels();
		byte[] originalpixels = (byte[]) orig.getPixels();
		
		for(int v=0; v<weight.length; v++){
		   for(int u=0;u<weight[v].length;u++){
			    p_length = p_length + weight[v][u]; 
		   }		
	    }
	    int[] P = new int[p_length];
	    //IJ.log(p_length+"");
	    K = p_length/2;	
		
        for (int v=1; v<=h-2; v++) {
            for (int u=1; u<=w-2; u++) {
                
                //fill the pixel vector P for filter position (u,v)
                int k = 0;
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
						for(int z=0; z<weight[i+1][j+1]; z++){
							//IJ.log((u+i)+" , "+(v+j)+"");
						    P[k] = 0xff & copypixels[(v+j)*w+(u+i)];
							//IJ.log("k :"+k+"");
                            k++;							
						}						
                        //P[k] = copy.getPixel(u+i,v+j);                        
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
