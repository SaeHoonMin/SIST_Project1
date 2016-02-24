package com.bss.client.gameObjects;

import java.net.InetAddress;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.net.*;

import com.bss.client.*;
import com.bss.client.container.MainFrame;
import com.bss.server.DBA;

import javafx.scene.control.TitledPane;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BssSettings extends JDialog implements ActionListener {

	public String ip_Num;
	public String port_Num;
	public Dimension 	resolution; //ũ��
	public InetAddress serverAddress; //�����������ּ�
	public int			portNumber; // ��Ʈ�ѹ�
	public boolean 	windowed; //â���,��üȭ��

	BufferedWriter bw;
	FileWriter fw;
	TitledBorder tb;
	public static ButtonGroup bg;
	public static JTextArea ta_ip ,ta_port;
	public static JTextField tf_ip;
	public static JTextField tf_port;
	public JRadioButton rb1_full,rb2_win,rb3,rb4;
	public JPanel contentPane;
	public static  JButton b1_ok;
	public static JButton b2_cancel;
	int rb = 2;
/* �����о���� �ҷ����̱�
	FileWriter fw = null;
	try{
		fw = new FileWriter("./text/temp.txt");
		BufferedWriter bw = new BufferedWriter(fw, 1024);
		bw.write("����");;
		
		bw.close();
		fw.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	*/
	/* textfield ���ִ� ���� �޾ƿ���
	String strNum=tf.getText().trim();
	System.out.println(strNum);*/
	public BssSettings() {
		
		//IP,Port�ѹ� �ʱⰪ.
		File file = new File("./settings/info.txt");
		 try{
		     BufferedReader br = new BufferedReader(new FileReader(file));
		     String sLine = null;
		 
		    while( (sLine = br.readLine()) != null ){
		    	StringTokenizer st=new StringTokenizer(sLine,"|");
		    	ip_Num=st.nextToken();
		    	port_Num=st.nextToken();
		    	System.out.println(ip_Num);
		    	System.out.println(port_Num);

		       
		     }
		 }catch(Exception ex){ }
		
		//�ؽ�Ʈ�ʵ�, üũ�ڽ� ����
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel p = new JPanel();
		tb = new TitledBorder(new LineBorder(Color.white),"ȭ�� ũ������",TitledBorder.LEADING,TitledBorder.TOP,null);
		tb.setTitleColor(Color.white);
		bg= new ButtonGroup();
		rb2_win = new JRadioButton("â���");
		rb1_full = new JRadioButton("��ü���");
		rb2_win.setSelected(true);
		
		bg.add(rb2_win); bg.add(rb1_full); 
		p.add(rb2_win);p.add(rb1_full);
		p.setBorder(tb);
		p.setToolTipText("");
		p.setBounds(12, 26, 230, 70);
		contentPane.add(p);
	
	
		JPanel p2 = new JPanel();
		tb = new TitledBorder(new LineBorder(Color.white),"���� IP����",TitledBorder.LEADING,TitledBorder.TOP,null);
		tb.setTitleColor(Color.white);

		ta_ip = new JTextArea("IP"){
			{setOpaque( false );}
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(20,27,40,20);
				setEnabled(false);
			};
		};
		tf_ip = new JTextField(ip_Num){
			@Override
			public void setBounds(int x, int y, int width, int height) {
				// TODO Auto-generated method stub
				super.setBounds(45,27,150,20);
				setForeground(Color.YELLOW);
				
			}
			
		};
		p2.setBorder(tb);
		p2.setToolTipText("");	
		p2.add(tf_ip);
		p2.add(ta_ip);
		p2.setBounds(12, 106, 230, 70);
		
		contentPane.add(p2);
		
		JPanel p3 = new JPanel();
		tb = new TitledBorder(new LineBorder(Color.white),"��Ʈ��ȣ ����",TitledBorder.LEADING,TitledBorder.TOP,null);
		tb.setTitleColor(Color.white);
		ta_port = new JTextArea("Port"){{setOpaque(false);}
		@Override
			public void setBounds(int x, int y, int width, int height) {
				// TODO Auto-generated method stub
				super.setBounds(10, 27, 40, 20);
				setEnabled(false);
			}
			
		};
		tf_port = new JTextField(port_Num){
			@Override
			public void setBounds(int x, int y, int width, int height) {
				// TODO Auto-generated method stub
				super.setBounds(45, 27, 150, 20);
				setForeground(Color.YELLOW);
			}
		};
		p3.setBorder(tb);
		p3.setToolTipText("");
		p3.setBounds(12, 186, 230, 70);
		p3.add(ta_port);
		p3.add(tf_port);
		
		contentPane.add(p3);
		
		b1_ok = new JButton("Ȯ��"){
			@Override
			public void setSize(int width, int height) {
				// TODO Auto-generated method stub
				super.setSize(50,40);
			}
		};
		
		b2_cancel = new JButton("���"){
			@Override
			public void setSize(int width, int height) {
				// TODO Auto-generated method stub
				super.setSize(50, 40);
			}
		};
		JPanel p4 = new JPanel();
		p4.setBounds(25, 300, 230, 70);
		
		p4.add(b1_ok);p4.add(b2_cancel);
		contentPane.add(p4);
		
		//������ ����
		setTitle("Settings");
		setSize(300,400);
		setUndecorated(true);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		rb1_full.addActionListener(this);
		rb2_win.addActionListener(this);
		b1_ok.addActionListener(this);
		b2_cancel.addActionListener(this);
		//ȭ�� �߾ӹ�ġ
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize(); // �����ȭ���� �ػ� ���
		Dimension mf_size = this.getSize();
		int left = (screen.width / 2) - (mf_size.width / 2);
		int top = (screen.height / 2) - (mf_size.height /2 );
		this.setLocation(left,  top);


		
		
	}
	/*
	@Override
	public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getX()>300||e.getY()>400){
			
		}
	
	
}
*/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==rb2_win)
			rb=2;
		else if(e.getSource()==rb1_full)
			rb=1;
		
		
		if(e.getSource().equals(b1_ok)){
			try{	
				//�Է��� ip���� port��
				FileWriter fw = null;
				fw=new FileWriter("./settings/info.txt");
				BufferedWriter bw = new BufferedWriter(fw, 1024);
				bw.write(tf_ip.getText().trim()+"|");
				bw.write(tf_port.getText().trim()+"|");
				bw.close();
				fw.close();
				setVisible(false);
				JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�.");
				if(rb==1)
					MainFrame.getInst().settingsfull();
				else if(rb==2)
					MainFrame.getInst().settingsWin();	
			
			}catch(Exception ex){
				ex.getMessage();
			}
		}
		else if(e.getSource()==b2_cancel){
			dispose();
		}
	}
}
