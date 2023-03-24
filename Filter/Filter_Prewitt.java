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
import java.lang.Math.*;

public class Filter_Prewitt implements PlugInFilter {

	int w,h;
    public int setup(String arg, ImagePlus imp) {
        if (arg.equals("about")) {
            showAbout();
            return DONE;
        }
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        w = orig.getWidth();
        h = orig.getHeight(); 
		
		int[][] FilterMatrix = {
									{-1, 0, 1},
									{-1, 0, 1},
									{-1, 0, 1},
								 };

		int[][] FilterMatrix2 = {
									{-1, -1, -1},
									{0, 0, 0},
									{1, 1, 1},
								 };								 
		// original pixels
		byte[] origpixels = (byte[])orig.getPixels();
		
        ImageProcessor copy = orig.duplicate();
		ImagePlus CopyImp = new ImagePlus("horz", copy);
		
		// copy pixels
		byte[] copypixels = (byte[])copy.getPixels();

        ImageProcessor copy2 = orig.duplicate();
		ImagePlus Copy2Imp = new ImagePlus("vert", copy2);
		// copy pixels
		byte[] copypixels2 = (byte[])copy2.getPixels();		

		ApplyMatrix(origpixels, copypixels, FilterMatrix, 1);
		ApplyMatrix(origpixels, copypixels2, FilterMatrix2, 1);

        ImageProcessor magIp = orig.duplicate();
		ImagePlus magImp = new ImagePlus("Magnitude", magIp);
		// copy pixels
		byte[] magpixels = (byte[])magIp.getPixels();		
		
		for (int v=0; v<h; v++) {
            for (int u=0; u<w; u++) {
				int pos = v * w + u;
				double horz = (double)copypixels[pos];
				double vert = (double)copypixels2[pos];
				int res = (int)Math.sqrt((horz*horz)+(vert*vert));
				magpixels[pos] = (byte)(0xff&res);
			}
		}
		CopyImp.show();
		Copy2Imp.show();
		magImp.show();
		
		copy=null;
		
		
    }

	void ApplyMatrix(byte[] origpixels, byte[] copypixels, int[][] FilterMatrix, int scale)
	{
        for (int v=1; v<=h-2; v++) {
            for (int u=1; u<=w-2; u++) {
                //compute filter result for position (u,v)
                int sum = 0;
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
                        //int p = copy.getPixel(u+i,v+j);
						int p = 0xff & origpixels[(v+j) * w + (u+i)];
						int c = FilterMatrix[i+1][j+1];
                        sum = sum + c*p;
                    }
                }
                int q = (int) (sum / (double)scale);
				copypixels[v * w + u] = (byte)(0xff&q);
            }
        }		
	}
	
    void showAbout() {
        String cn = getClass().getName();
        IJ.showMessage("About "+cn+" ...",
            "3x3 Average Filter."
        );
    }
}
