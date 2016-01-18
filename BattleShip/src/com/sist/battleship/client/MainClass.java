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
		
		
		// getClassLoader().getResource 와  getClassLoader 제외하고 사용하는 경우의 차이????
		//아래와 같이 사용할 때 불러오기 가능...
		
		// todo : jpanel 상속한 클래스 빼기.
		//		   리소스로더 클래스 만들기.
		//        frame도  마찬가지로 클래스 빼기.
		//        style 버튼 ( 일단은 확장가능한 버튼만) 만들어서 main에 "로그인" 버튼에 적용
		
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
