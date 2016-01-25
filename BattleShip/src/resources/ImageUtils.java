package resources;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;




/*
 * 
 * 
 * 
 * 
 * 
 * 	Currently this class is not used.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public final class ImageUtils implements ImageObserver {

	static ImageUtils inst;
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static ImageUtils getInst()
	{
		if(inst == null )
			inst = new ImageUtils();
		return inst;
	}
	
	public static BufferedImage toBufferedImage(Image img, int width, int height) {
	
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		
		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	


	/**
	 * Creates a transparent image. These can be used for aligning menu items.
	 *
	 * @param width
	 *            the width.
	 * @param height
	 *            the height.
	 * @return the created transparent image.
	 */

	public static BufferedImage createTransparentImage(final int width, final int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * Creates a transparent icon. The Icon can be used for aligning menu items.
	 *
	 * @param width
	 *            the width of the new icon
	 * @param height
	 *            the height of the new icon
	 * @return the created transparent icon.
	 */
	public static Icon createTransparentIcon(final int width, final int height) {
		return new ImageIcon(createTransparentImage(width, height));
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}