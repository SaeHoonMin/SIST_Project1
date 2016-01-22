package com.bss.client.scene;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bss.client.GuiComponents.StyleButton;


public class WaitRoomPanel extends JPanel{
	
    JTable table1,table2;
    DefaultTableModel model1,model2;
    JTextArea ta;
    JTextField tf;
    JComboBox box;
    StyleButton b1;
    JButton b2,b3,b4,b5,b6;
    
    WaitRoomPanel(JFrame parent) 
    {
    	
    	setSize(1280,1024);
    	
		String[] col1={"방이름","공개/비공개","인원"};
		String[][] row1=new String[0][3];
		model1=new DefaultTableModel(row1,col1);
		
		
		table1=new JTable(model1);
		
		JScrollPane js1=new JScrollPane(table1);
		
		String[] col2={"ID","대화명","성별","위치"};
		String[][] row2=new String[0][4];
		model2=new DefaultTableModel(row2,col2);
		table2=new JTable(model2);
		JScrollPane js2=new JScrollPane(table2);
		
		ta = new JTextArea();
		JScrollPane js3 = new JScrollPane(ta);
		
		tf = new JTextField();
		box = new JComboBox();
		box.addItem("black");
		box.addItem("blue");
		box.addItem("green");
		box.addItem("cyan");
		box.addItem("pink");
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3,2,5,5));
		
		p.add(b1 = new StyleButton("Create Room"));p.add(b2 = new JButton("방들어가기"));
		p.add(b3 = new JButton("1:1게임"));p.add(b4 = new JButton("쪽지보내기"));
		p.add(b5 = new JButton("아무것도 안하기"));p.add(b6 = new JButton("종료하기"));
		
		
		b1.addActionListener((ActionListener)parent);
		
		
		setLayout(null);
		js1.setBounds(10, 15, 500, 350);
		js2.setBounds(10, 370, 500, 180);
		js3.setBounds(515, 15, 265, 300);
		tf.setBounds(515, 320, 160, 30);
		box.setBounds(680,320,90,30);
		p.setBounds(515,470	,265,80);
		add(js1);
		add(js2);
		add(js3);
		add(tf);
		add(box);
		add(p);
		
	 }
}
