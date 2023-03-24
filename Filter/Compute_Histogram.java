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

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.*;

public class Compute_Histogram implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G + NO_CHANGES; 
	}
    
	public void run(ImageProcessor ip) {
		int[] H = new int[256]; // histogram array
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				int i = ip.getPixel(u, v);
				H[i] = H[i] + 1;
			}
		}
		ShowHistogram(H);
		
	}
	public void ShowHistogram(int[] H)
	{
		int w = 256;
		int h = 100;
		ImageProcessor histIp = new ByteProcessor(w,h);
		histIp.setValue(255); // white = 255
		histIp.fill(); //clear this image
		int maxi = 0;
		for(int i = 0;i < w;i++)
		{
			maxi = Math.max(maxi,H[i]);
		}
		for(int i = 0;i < w;i++)
		{
			double percentage = (double)H[i] / (double)maxi * (double) h;
			for(int j = 0;j < h;j++)
			{				
				if(percentage >= h-j)
				{
					histIp.putPixel(i,j,(int)((double)(h-j)/(double)h * 255.0));
				}
			}
		}
		// display the histogram image:
		String hTitle = "Histogram result";
		ImagePlus histIm = new ImagePlus(hTitle, histIp);
		histIm.updateAndDraw();
		histIm.show();				
	}
	
}