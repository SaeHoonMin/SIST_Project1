package com.bss.client.gameObjects;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.javafx.font.Disposer;

import resources.BssSprites;

public class FixedLocAnimation extends Thread{

	private JPanel parent;
	private JLabel label;
	private AnimName name;
	private boolean isOver = false;
	private ArrayList<ImageIcon> sprites;
	
	private int x, y;
	
	private FixedLocAnimation(AnimName name, ArrayList<ImageIcon> list, JPanel panel, int x, int y)
	{
		label = new JLabel();
		//라벨 사이즈 및 위치 선정
		panel.add(label);
		
		int h = list.get(0).getIconHeight();
		int w = list.get(0).getIconWidth();
		label.setSize(w,h);
		label.setLocation(x-w/2,y-h/2);
		
		panel.setComponentZOrder(label, 0);
		
		parent = panel;
		this.name = name;
		sprites = list;
		this.x = x;
		this.y = y;
	}
	
	//자기자신 배열을 갖는거 선언해서 쓰레드로 관리한다면? -> 어떤 애니메이션이 끝났는지 animname 으로 찾을 수 있을것?
	//  no .. 이름으로 찾는것은 같은이름 애니메이션이 여러개면 못찾는다
	
	public static void Play( AnimName name , JPanel panel, int x, int y)
	{
		new FixedLocAnimation(name, BssSprites.getAnimSprites(name),panel, x, y).start();
	}
	
		
	@Override
	public void run() {
		int i=0;
		int size = sprites.size();
		int time = (int)(1.0/size*1000);

		try
		{
			while(true)
			{
				label.setIcon(sprites.get(i));
				i++;
				if(i>=size)
				{
					label.setIcon(null);
					break;
				}
				Thread.sleep(time);
			}
			isOver=true;
			label.setIcon(null);
			label.setVisible(false);
			parent.remove(label);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
