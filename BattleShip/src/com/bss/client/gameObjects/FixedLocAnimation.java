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
		//�� ������ �� ��ġ ����
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
	
	//�ڱ��ڽ� �迭�� ���°� �����ؼ� ������� �����Ѵٸ�? -> � �ִϸ��̼��� �������� animname ���� ã�� �� ������?
	//  no .. �̸����� ã�°��� �����̸� �ִϸ��̼��� �������� ��ã�´�
	
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
