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

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.GuiComponents.StyleButton;

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
		p1.setLayout(new FlowLayout());
		//p1.setBackground(Color.white);
		
		StyleButton btn1 = new StyleButton("Login");
		StyleButton btn2 = new StyleButton("Exit Game");
		btn1.setBounds(100,100,100,50);
		btn2.setBounds(100,210,100,50);
		
		p1.add(btn1);
		p1.add(btn2);
		
		jf.setVisible(true);
		
		
		
	}

}
