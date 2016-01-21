package com.bss.client.GuiComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class StyleButton extends JButton implements MouseListener{
	
	int w, h;
	int lx,ly;
	int fontSize;
	Color color;
	
	public StyleButton(String text)
	{	
		setText(text);
		setPreferredSize(new Dimension(150,50));
		
		setFont(new Font("Arial", Font.PLAIN,16));
		fontSize = getFont().getSize();
		w = getWidth();
		h = getHeight();
		
		setFocusable(false);
		
		setBorderPainted(false);
		setContentAreaFilled(false);
	//	setForeground(new Color(0xff,0xf8,0xdc));
		
		addMouseListener(this);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouse entered");
		System.out.println("btn x : "+getX()+" btn y : "+getY());
		
		new Thread(new Runnable() {
			public  void run() {
				
				int fSize = fontSize;
				
				while (true) {
					fSize ++;
					setFont(new Font("Arial", Font.PLAIN, fSize));
					try {
						Thread.sleep(10);
					} catch (InterruptedException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
					
					if (fSize >= fontSize * 1.3)
						break;
				}
			}
		}).start();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouse exited");
		
		new Thread(new Runnable() {
			public void run() {
				

				int fSize = getFont().getSize();
				
				while (true) {
					fSize --;
					setFont(new Font("Arial", Font.PLAIN, fSize));
					try {
						Thread.sleep(10);
					} catch (InterruptedException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
					
					if (fSize <= fontSize )
						break;
				}
			}
		}).start();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
