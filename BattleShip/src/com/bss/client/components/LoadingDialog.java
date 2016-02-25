package com.bss.client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bss.client.BssNetWork;
import com.bss.client.container.MainFrame;
import com.bss.client.container.WaitRoomPanel;
import com.bss.client.gameObjects.AnimName;
import com.bss.common.BssProtocol;

import resources.BssColor;
import resources.BssFont;
import resources.BssSprites;
import resources.ResContainer;

public class LoadingDialog extends JFrame{
	
	JLabel msg;
	StyleButton startBtn;
	MainFrame mainFrame = MainFrame.getInst();
	JPanel pane;
	
	ArrayList <ImageIcon> icons = new ArrayList<ImageIcon>();
	
	private boolean running = true;

	public static LoadingDialog Show()
	{
		return new LoadingDialog();
	}

	private LoadingDialog() {
		
	//	mainFrame.setEnabled(false);
		setSize(100,100);
		setUndecorated(true);
		setBackground(BssColor.BLACK_T2);
		
		JLabel msg = new JLabel();
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setIcon(new ImageIcon(ResContainer.loading_gif));
		msg.setText("Wait..");
		setAlwaysOnTop(true);
	
		pane = new JPanel();
		pane.setOpaque(false);
		pane.setSize(getSize());
		System.out.println(pane.getSize());
		pane.setLayout(new BorderLayout());
		
		pane.add("Center",msg);
		
		add(pane);
		
		setVisible(true);
		setAutoRequestFocus(true);
		setLocation(mainFrame.getX() + mainFrame.getWidth()/2- getWidth()/2, 
				 mainFrame.getY() + mainFrame.getHeight()/2 - getHeight()/2);
		
		
		icons = BssSprites.getAnimSprites(AnimName.LOADING_GIF);
		
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				int i=0;
//				while(running){
//				
//					msg.setIcon(icons.get(i));
//					msg.update(msg.getGraphics());
//					i++;
//					if(i>=icons.size())
//						i=0;
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			
//		}).start();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		running=false;
		super.dispose();
	}
}
