import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import java.lang.Math;

public class AutoContrast implements PlugInFilter{
	String title = null;
	int amax, amin,ahigh,alow = 0;
	
	public int setup(String arg, ImagePlus im)
	{
	
		title = im.getTitle();
		return DOES_8G;
	}

	public void run(ImageProcessor ip){
		int w = ip.getWidth();
		int h = ip.getHeight();
		amax = 255;
		amin = 0;
		ahigh = 0;
		alow = 255;
		
		ImageProcessor newImage = new ByteProcessor(w,h);
		
		// iterate over all image coordinates
		for(int u = 0;u < w;u++)
		{
			for(int v = 0;v < h;v++)
			{
				int p = ip.getPixel(u,v);
				ahigh = Math.max(ahigh,p);
				alow = Math.min(alow,p);
			}
		}

		for(int u = 0;u < w;u++)
		{
			for(int v = 0;v < h;v++)
			{
				int p = ip.getPixel(u,v);				
				newImage.putPixel(u,v,calculateAutoContrast(p));
			}
		}
		
		String hTitle = "Auto-Contrast "  + title;
		ImagePlus ipnew = new ImagePlus(hTitle, newImage);
		ipnew.updateAndDraw();
		ipnew.show();		
		GetHistogram(ip, "Histogram original");
		GetHistogram(newImage, "Histogram AutoContrast");

	}
	
	private int calculateAutoContrast(int pixel)
	{
		return amin + (int)((double)(pixel - alow) * (double)(amax-amin)/(double)(ahigh-alow));
	}
	
	private void GetHistogram(ImageProcessor ip,String HistogramTitle)
	{
		int w = 256;
		int h = 100;
		int[] hist = ip.getHistogram();
		
		//create the histogram image:
		ImageProcessor histIp = new ByteProcessor(w,h);
		histIp.setValue(255); // white = 255
		histIp.fill(); //clear this image
		
		int maxi = 0;
		for(int i = 0;i < w;i++)
		{
			maxi = Math.max(maxi,hist[i]);
		}
		
		for(int i = 0;i < w;i++)
		{
			double percentage = (double)hist[i] / (double)maxi * (double) h;
			for(int j = 0;j < h;j++)
			{				
				if(percentage >= h-j)
				{
					histIp.putPixel(i,j,(int)((double)(h-j)/(double)h * 255.0));
				}
			}
		}

		// display the histogram image:
		String hTitle = "Histogram of "  + HistogramTitle;
		ImagePlus histIm = new ImagePlus(hTitle, histIp);
		histIm.updateAndDraw();
		histIm.show();
		// histIm.updateAndDraw();	
	}
}