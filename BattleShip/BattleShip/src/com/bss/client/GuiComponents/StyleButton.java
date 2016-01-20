package com.bss.client.GuiComponents;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class StyleButton extends JButton implements MouseListener{
	
	int w, h;
	int lx,ly;
	int fontSize;
	
	public StyleButton(String text)
	{	
		setText(text);
		setPreferredSize(new Dimension(100,50));
		
		setFont(new Font("Arial", Font.PLAIN,16));
		fontSize = getFont().getSize();
		w = getWidth();
		h = getHeight();
		setVisible(true);
		
		setBorderPainted(false);
		setContentAreaFilled(false);
		
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
		new Thread(new Runnable() {
			public synchronized void run() {
				
		
				int cw = w;
				int ch = h;
				
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
					
					if (fSize >= fontSize * 1.4)
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
			public synchronized void run() {
				
				
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
