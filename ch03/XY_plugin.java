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

public class XY_plugin implements PlugInFilter {

	ImagePlus im; // instance variable of this plugin object

	public int setup(String arg, ImagePlus im) {
		this.im = im; // keep a reference to the image im
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		//...				// use ip to modify the image 
		im.updateAndDraw(); // use im to redisplay the image w}
		// ...
	}

} // end of class