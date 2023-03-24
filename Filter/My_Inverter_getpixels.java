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

public class My_Inverter implements PlugInFilter {

	public int setup(String arg, ImagePlus im) {
		return DOES_8G; // this plugin accepts 8-bit grayscale images 
	}

	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		if (ip.getPixels() instanceof byte[])
		{
			byte[] pixels = (byte[])ip.getPixels();
			// iterate over all image coordinates
			for (int u = 0; u < w; u++) {
				for (int v = 0; v < h; v++) {
					int value = pixels[u + w * v] & 0xff;
					value = 255-value;
					pixels[u+w*v] = (byte)(value);
					//int p = ip.getPixel(u, v);					
					//ip.putPixel(u, v, 255 - p); // invert
				}
			}
		}
		if (ip.getPixels() instanceof short[])
		{
			short[] pixels = (short[])ip.getPixels();
			// iterate over all image coordinates
			for (int u = 0; u < w; u++) {
				for (int v = 0; v < h; v++) {
					//int p = ip.getPixel(u, v);
					//ip.putPixel(u, v, 255 - p); // invert
					int value = pixels[u + w * v];
					value = 255-value;
					pixels[u+w*v] = (short)value;					
				}
			}
		}
				
		
		
	}

} // end of class
