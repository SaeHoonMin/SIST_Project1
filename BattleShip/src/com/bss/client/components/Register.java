package com.bss.client.components;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import com.bss.client.*;

import javax.swing.*;
import com.bss.common.BssProtocol;
import com.bss.server.*;

import resources.BssColor;
import resources.BssFont;


public class Register extends JFrame implements ActionListener {
	
	StyleTextField tf1_id,tf2_email;
	StylePasswordField pf1_pwd,pf2_pwd;
	JLabel la_information,la1_id,la2_pwd,la3_pwd2,la4_email;
	JTextArea ta1_register;
	
	StyleButton b_ok,b_cancel,b_check;
	String idcheck="";
	static Boolean id_check;
	
	private JPanel contentPane;
	
	
	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	
	public Register(){
		
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
		
		
		setTitle("Sign up");
		setSize(350,450);
		setResizable(false);
		setAlwaysOnTop(true);
	
		
		
		ta1_register = new JTextArea("Sign up");
		ta1_register.setFont(new Font("dialog",Font.BOLD,30));
		ta1_register.setForeground(BssColor.TURQUOISE_MID);
		ta1_register.setOpaque(false);
		ta1_register.setEnabled(false);

		la_information = new JLabel("<html>Awesome fun is only one step away!<br/>Enter your details below and get playing. <html>");
		la_information.setFont(new Font("Arial",Font.TRUETYPE_FONT,15));
		
		la_information.setBounds(70,30 ,240, 80);
		la_information.setForeground(BssColor.TURQUOISE_MID);
		contentPane.add(la_information);
	
		b_check = new StyleButton("Check");
		b_check.setBounds(240,135,60,25);
		b_check.setFontSize(15);
		b_check.setBigFont(19);
		tf1_id = new StyleTextField(120,25);
		tf1_id.setBounds(115,135,120,25);//160
	
		la1_id = new JLabel("Username*");
		la1_id.setBounds(40, 135, 65, 25);
		la1_id.setForeground(BssColor.TURQUOISE_MID);
		contentPane.add(la1_id);contentPane.add(tf1_id); contentPane.add(b_check);
		
		pf1_pwd = new StylePasswordField(120,25);
		pf1_pwd.setBounds(115,180,120,25);
		
		la2_pwd = new JLabel("Password*");
		la2_pwd.setBounds(40, 180, 65, 25);
		la2_pwd.setForeground(BssColor.TURQUOISE_MID);
		contentPane.add(la2_pwd);contentPane.add(pf1_pwd);
		
		pf2_pwd = new StylePasswordField(120,25);
		pf2_pwd.setBounds(115,215,120,25);
		
		la3_pwd2 = new JLabel("<html>Password<br/>  Confirm*<html>");
		la3_pwd2.setBounds(40, 185, 75, 77);
		la3_pwd2.setForeground(BssColor.TURQUOISE_MID);
		contentPane.add(la3_pwd2);contentPane.add(pf2_pwd);
		
		tf2_email = new StyleTextField(160, 27);
		tf2_email.setLength(16);
		tf2_email.setBounds(115,255,160,27);
	
		la4_email = new JLabel("Email*");
		la4_email.setBounds(40, 255, 65, 27);
		la4_email.setForeground(BssColor.TURQUOISE_MID);
		contentPane.add(la4_email);contentPane.add(tf2_email);
		
		
		
		/*b1.setIcon(new ImageIcon(setImage("./img/10.gif",20,20)));
		b1.setRolloverIcon(new ImageIcon(setImage("./img/1.png",20,20)));*/
		
		b_ok  = new StyleButton("Sign Up");
		b_ok.setBounds(90, 320, 80, 40);
		
		b_cancel = new StyleButton("Cancle");
		b_cancel.setBounds(190, 320,80,40);
		
		b_ok.addActionListener(this);
		b_cancel.addActionListener(this);
		b_check.addActionListener(this);
		contentPane.add(b_ok); contentPane.add(b_cancel);
		
		contentPane.add(ta1_register);
		

		
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의 해상도 얻기
		Dimension mf_size = super.getSize();
		int left = (screen.width / 2) - (mf_size.width / 2);
		int top = (screen.height / 2) - (mf_size.height /2 );
		super.setLocation(left,  top); 
		

		setVisible(true);
		repaint();
	}
	public static void id_Check(Boolean id_check){
		Register.id_check=id_check;
		
	}
	
	public Image setImage(String filename,int w, int h){
		ImageIcon ii=new ImageIcon(filename);
		Image i=ii.getImage().getScaledInstance(w,h,Image.SCALE_SMOOTH);
		return i;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		  //객체생성해줘야한다.
	
		
		if(e.getSource()==b_check){
			try {
				BssNetWork inst = BssNetWork.getInst();
				inst.connection();
				
				if(inst.isConnected()==false)
				{
					MessageDialog.Show("Cannot Connect To Server.");
					return;
				}
				
//				if(member.idCheck(tf1_id.getText())){
				idcheck=tf1_id.getField().getText();
				BssNetWork.getInst().sendMessage(BssProtocol.ID_CHECK,idcheck);
				
				while(true){
					if(id_check!=null){
						break;
					}
					Thread.sleep(1);
				}
				
				if(id_check){
					
					JOptionPane.showMessageDialog(this, "This ID has already been taken.");
					tf1_id.requestFocus();
					
				}
				else if(tf1_id.getText().length()<5 || tf1_id.getText().length()>12){
				
					MessageDialog.Show( "ID must be 5 to 12 characters.");
					tf1_id.requestFocus();
				}
				else {
					
					MessageDialog.Show("You can use this ID.");
					idcheck=tf1_id.getText();
					pf1_pwd.requestFocus();
				}
				id_check=null;
			} catch (Exception e1) {}
		}
		
		
		if(e.getSource()==b_ok){
	
		
			String info = null;
			char[] a = pf1_pwd.getPassword();
			char[] b= pf2_pwd.getPassword();
			String pwd1 = new String(a);
			String pwd2 = new String(b);
			
			try{
				
			/*if(member.idCheck(tf1_id.getText())){
				JOptionPane.showMessageDialog(this, "중복된 아이디가 존재합니다.");
				tf1_id.requestFocus();
			}
			else if(tf1_id.getText().length()<4 || tf1_id.getText().length()>12){
				JOptionPane.showMessageDialog(this, "아이디는 5~12글자 사이로 입력하세요.");
				tf1_id.requestFocus();
			}*/
			if(!idcheck.equals(tf1_id.getText())){
				MessageDialog.Show("Please check your id.");
				tf1_id.requestFocus();
			}
			
			else if(tf1_id.getText().equals("")||pf1_pwd.equals("")||
			    		pf2_pwd.equals("")||tf2_email.getText().equals("")){
				MessageDialog.Show("Whitspace not allowed.");
			    	return;
			}
			
			else if(!pwd1.equals(pwd2)){
				MessageDialog.Show("Please confirm password again.");
				
				pf2_pwd.setText("");
				pf2_pwd.requestFocus();
			}
			else if(pwd1.length()<8){
				MessageDialog.Show("Password must be 8 or more characters.");
				pf1_pwd.setText(""); 
				pf2_pwd.setText(""); 
				pf1_pwd.requestFocus();
			}
	
			else if((!tf2_email.getText().contains("@"))||(!tf2_email.getText().contains("."))){
				MessageDialog.Show( "Incorrect Email addres.");
				tf2_email.requestFocus();
			}
	
			else{
			
			BssNetWork inst = BssNetWork.getInst();
			inst.connection();
			info= tf1_id.getText()+"|"+pwd1+"|"+tf2_email.getText();
			
			BssNetWork.getInst().sendMessage(BssProtocol.REGISTER, info);
			
			
			
			
			
			MessageDialog.Show("Sign up complete!");
			
			
//			member.allUserInfo(); //테이블 데이터 읽어오기

			dispose();
			}
			}catch(Exception ex){}
		}
		
		else if(e.getSource()==b_cancel){
			dispose();
		}
		
	}
	
}
