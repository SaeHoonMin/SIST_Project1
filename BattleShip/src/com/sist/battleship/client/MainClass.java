package com.sist.battleship.client;
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	//	MainClass inst = new MainClass();
		
		JFrame jf = new JFrame("test");
	
		
		jf.setSize(1280, 1024);
		
		
		// getClassLoader().getResource ��  getClassLoader �����ϰ� ����ϴ� ����� ����????
		//�Ʒ��� ���� ����� �� �ҷ����� ����...
		
		// todo : jpanel ����� Ŭ���� ����.
		//		   ���ҽ��δ� Ŭ���� �����.
		//        frame��  ���������� Ŭ���� ����.
		//        style ��ư ( �ϴ��� Ȯ�尡���� ��ư��) ���� main�� "�α���" ��ư�� ����
		
		final ImageIcon icon11 = new ImageIcon(MainClass.class.getClassLoader().getResource("resources/1.jpg"));
		JPanel p1 = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon11.getImage(), 0, 0,this.getWidth(),this.getHeight(), null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		jf.add(p1);
		p1.setLayout(new BorderLayout());
		//p1.setBackground(Color.white);
		
		jf.setVisible(true);
		
		
	
		
		
	}

}
