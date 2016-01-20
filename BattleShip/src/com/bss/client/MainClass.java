package com.bss.client;
/*
 * 	Created by Sehoon Min.
 * 
 *  First Creation : 2016-01-19
 *  First Comment
 *  - The early version of this class is for testing client's 
 *    Basic GUI such following things.
 *    : Login window
 *    : Wait Room
 *    : Game Ready Window
 *    : GamePlay Window
 *    : Setting panel
 *    : etc ..
 * 
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.GuiComponents.LoginWindowFrame;

import resources.ResourceLoader;
import sun.font.CreatedFontTracker;

public class MainClass {

	
	static Image img;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame jf = new JFrame();
		
	
		try {
			img = ImageIO.read(ResourceLoader.getResURL("images/SpaceShip.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JPanel jp = new JPanel(){
			public void paintComponent(Graphics g)
			{
				g.drawImage(img, 0, 0,img.getWidth(null),img.getHeight(null), null);
			}
		};
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) screenSize.getWidth();
		int h = (int) screenSize.getHeight();
		int iw = img.getWidth(null);
		int ih = img.getHeight(null);
		jf.setLocation(w/2 - iw/2,h/2 - ih/2 );
		
		jf.add(jp);
		jf.setSize(img.getWidth(null), img.getHeight(null));
		jf.setUndecorated(true);
		jf.setVisible(true);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).run();
		jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
		
		
		LoginWindowFrame loginWindow = new LoginWindowFrame();
		
		
		
		
	}
	


}
