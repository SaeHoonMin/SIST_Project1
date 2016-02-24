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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.bss.client.components.UIDebugWindow;
import com.bss.client.container.MainFrame;

import resources.BssSprites;
import resources.ResContainer;
import resources.ResLoader;

public class BssMain {
	
	static Image img;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval"); 
		
		/* 로딩화면. */
		
		// ResContainer를 가장 처음에 생성하자.
		ResContainer resc = new ResContainer();
		new BssSprites();
		
		JFrame jf = new JFrame();
		
		img =  Toolkit.getDefaultToolkit().getImage(ResLoader.getResURL("images/SpaceShip.png"));
	
		JPanel jp = new JPanel();
		jp.setBackground(new Color(0,0,0,0));
		JLabel lb = new JLabel();
		lb.setIcon(new ImageIcon(img));
		jp.add(lb);
		
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
		jf.setBackground(new Color(255,255,255,0));
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).run();
		
		jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
		
		/*try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel"); 
		} catch (Exception e) {} 
		*//*********/ 
		
		//mainFrame Start... main game window. 
		//Includes login, waitroom, game scenes..
		
		MainFrame mainFrame = new MainFrame(1280,800);
		
	}
	
	
}
