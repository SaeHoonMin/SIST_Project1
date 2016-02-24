package com.bss.client.components;

import java.net.InetAddress;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.net.*;

import com.bss.client.*;
import com.bss.client.container.MainFrame;
import com.bss.server.Database;

import javafx.scene.control.TitledPane;
import resources.BssColor;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BssSettings extends JFrame implements ActionListener {

	public String ip_Num;
	public String port_Num;
	public Dimension 	resolution; //크기
	public InetAddress serverAddress; //서버아이피주소
	public int			portNumber; // 포트넘버

	BufferedWriter bw;
	FileWriter fw;
	TitledBorder tb;
	public static ButtonGroup bg;
	public static JTextArea ta_ip ,ta_port;
	
	public static StyleTextField tf_ip;
	public static StyleTextField tf_port;

	public JPanel contentPane;
	public static  StyleButton b1_ok;
	public static StyleButton b2_cancel;
/* 파일읽어오기 불러들이기
	FileWriter fw = null;
	try{
		fw = new FileWriter("./text/temp.txt");
		BufferedWriter bw = new BufferedWriter(fw, 1024);
		bw.write("정현");;
		
		bw.close();
		fw.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	*/
	/* textfield 에있는 것을 받아오기
	String strNum=tf.getText().trim();
	System.out.println(strNum);*/
	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public BssSettings() {
		
		setSize(300,250);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		
		
		contentPane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);
		contentPane.setBackground(BssColor.BLACK_T0);
	
		JPanel p2 = new JPanel();
		p2.setOpaque(false);
		p2.setLayout(null);
		tb = new TitledBorder(new LineBorder(BssColor.TURQUOISE_MID),"Server IP",TitledBorder.LEADING,TitledBorder.TOP,new Font("Arial",Font.TRUETYPE_FONT,15),BssColor.TURQUOISE_MID);
		ta_ip = new JTextArea("IP"){
			{setOpaque( false );}
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(20,27,40,20);
				setEnabled(false);
			};
		};
		tf_ip = new StyleTextField(150,20);
		tf_ip.setLocation(45,27);
		tf_ip.setLength(14);
		p2.setBorder(tb);
		p2.setToolTipText("");	
		p2.add(tf_ip);
		p2.add(ta_ip);
		p2.setBounds(10, 10, 230, 70);
		
		contentPane.add(p2);
		
		JPanel p3 = new JPanel();
		p3.setOpaque(false);
		p3.setLayout(null);
		tb = new TitledBorder(new LineBorder(BssColor.TURQUOISE_MID),"Port Number",TitledBorder.LEADING,TitledBorder.TOP,new Font("Arial",Font.TRUETYPE_FONT,15),BssColor.TURQUOISE_MID);
		ta_port = new JTextArea("Port"){{setOpaque(false);}
		@Override
			public void setBounds(int x, int y, int width, int height) {
				// TODO Auto-generated method stub
				super.setBounds(10, 27, 40, 20);
				setEnabled(false);
			}
			
		};
		tf_port = new StyleTextField(150,20);
		tf_port.setLocation(45,27);
		tf_port.setLength(15);
		p3.setBorder(tb);
		p3.setToolTipText("");
		p3.setBounds(12, 90, 230, 70);
		p3.add(ta_port);
		p3.add(tf_port);
		
		contentPane.add(p3);
		
	
		b1_ok = new StyleButton("Confirm");
		b1_ok.setSize(getWidth()/2-1,70);
		b1_ok.setLocation(0, getHeight()-b1_ok.getHeight());
		
		b2_cancel = new StyleButton("Cancle");
		b2_cancel.setSize(getWidth()/2-1,70);
		b2_cancel.setLocation(getWidth()/2+1, getHeight()-b2_cancel.getHeight());
		
		System.out.println("b2 location : "+ b2_cancel.getLocation());
		
		contentPane.add(b1_ok);contentPane.add(b2_cancel);
		
		//프레임 설정
		setTitle("Settings");
	
		setUndecorated(true);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		b1_ok.addActionListener(this);
		b2_cancel.addActionListener(this);
		//화면 중앙배치
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의 해상도 얻기
		Dimension mf_size = this.getSize();
		int left = (screen.width / 2) - (mf_size.width / 2);
		int top = (screen.height / 2) - (mf_size.height /2 );
		this.setLocation(left,  top);

		
		
		
		//IP,Port넘버 초기값.
				File file = new File("./settings/info.txt");
				if(!file.exists())
				{
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 try{
				     BufferedReader br = new BufferedReader(new FileReader(file));
				     String sLine = null;
				 
				    while( (sLine = br.readLine()) != null ){
				    	StringTokenizer st=new StringTokenizer(sLine,"|");
				    	ip_Num=st.nextToken();
				    	port_Num=st.nextToken();
				    	System.out.println(ip_Num);
				    	System.out.println(port_Num);
				    	
				    	tf_ip.field.setText(ip_Num);
				    	tf_port.field.setText(port_Num);
				     }
				 }catch(Exception ex){ 
					 ex.printStackTrace();
				 }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(b1_ok)){
			try{	
				if(tf_ip.getText().length()<1 || tf_port.getText().length()<1)
				{
					MessageDialog.Show("Please fill out settings.");
					return;
				}
				
				//입력한 ip값과 port값
				FileWriter fw = null;
				fw=new FileWriter("./settings/info.txt");
				BufferedWriter bw = new BufferedWriter(fw, 1024);
				bw.write(tf_ip.getText().trim()+"|");
				bw.write(tf_port.getText().trim()+"|");
				bw.close();
				fw.close();
				setVisible(false);
				MessageDialog.Show("Your Settings\nHave Been Saved.");
			
			}catch(Exception ex){
				ex.getMessage();
			}
		}
		else if(e.getSource()==b2_cancel){
			dispose();
		}
	}
}
