package com.bss.client.container;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import com.bss.client.*;
import javax.swing.*;
import com.bss.common.BssProtocol;
import com.bss.server.*;


public class Register extends JFrame implements ActionListener {
	JTextField tf1_id,tf2_email;
	JPasswordField pf1_pwd,pf2_pwd;
	JLabel la_information,la1_id,la2_pwd,la3_pwd2,la4_email;
	JTextArea ta1_register;
	JButton b_ok,b_cancel,b_check;
	String idcheck="";
	static Boolean id_check;
	
	public Register(){
		
		ta1_register = new JTextArea("Sign up");
		ta1_register.setFont(new Font("dialog",Font.BOLD,30));
		ta1_register.setForeground(Color.gray);
		ta1_register.setOpaque(false);
		ta1_register.setEnabled(false);

		la_information = new JLabel("<html>Awesome fun is only one step away!<br/>Enter your details below and get playing. <html>");
		la_information.setBounds(85,30 ,240, 80);
		la_information.setForeground(Color.gray);
		add(la_information);
	
		b_check = new JButton("중복확인");
		b_check.setBounds(240,135,60,25);
		b_check.setFont(new Font("HY견고딕",Font.TRUETYPE_FONT,10));
		tf1_id = new JTextField();
		tf1_id.setBounds(115,135,120,25);//160
		tf1_id.setBorder(null);
	
		la1_id = new JLabel("Username*");
		la1_id.setBounds(40, 135, 65, 25);
		la1_id.setForeground(Color.gray);
		add(la1_id);add(tf1_id); add(b_check);
		
		pf1_pwd = new JPasswordField();
		pf1_pwd.setBounds(115,180,120,25);
		pf1_pwd.setBorder(null);
		
		la2_pwd = new JLabel("Password*");
		la2_pwd.setBounds(40, 180, 65, 25);
		la2_pwd.setForeground(Color.gray);
		add(la2_pwd);add(pf1_pwd);
		
		pf2_pwd = new JPasswordField();
		pf2_pwd.setBounds(115,215,120,25);
		pf2_pwd.setBorder(null);
		
		la3_pwd2 = new JLabel("<html>Password<br/>  Confirm*<html>");
		la3_pwd2.setBounds(40, 185, 75, 77);
		la3_pwd2.setForeground(Color.gray);
		add(la3_pwd2);add(pf2_pwd);
		
		tf2_email = new JTextField();
		
		tf2_email = new JTextField();
		tf2_email.setBounds(115,255,160,27);
		tf2_email.setBorder(null);
	
		la4_email = new JLabel("Email*");
		la4_email.setBounds(40, 255, 65, 27);
		la4_email.setForeground(Color.gray);
		add(la4_email);add(tf2_email);
		
		
		
		/*b1.setIcon(new ImageIcon(setImage("./img/10.gif",20,20)));
		b1.setRolloverIcon(new ImageIcon(setImage("./img/1.png",20,20)));*/
		
		b_ok  = new JButton("회원가입");
		b_ok.setBorder(null);
		b_ok.setBounds(90, 320, 80, 40);
		
		b_cancel = new JButton("나가기");
		b_cancel.setBorder(null);
		b_cancel.setBounds(190, 320,80,40);
		
		b_ok.addActionListener(this);
		b_cancel.addActionListener(this);
		b_check.addActionListener(this);
		add(b_ok); add(b_cancel);
		
		add(ta1_register);
		
		setTitle("Sign up");
		setSize(350,450);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
		
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의 해상도 얻기
		Dimension mf_size = super.getSize();
		int left = (screen.width / 2) - (mf_size.width / 2);
		int top = (screen.height / 2) - (mf_size.height /2 );
		super.setLocation(left,  top); 
		
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
//				if(member.idCheck(tf1_id.getText())){
				idcheck=tf1_id.getText();
				BssNetWork.getInst().sendMessage(BssProtocol.ID_CHECK,idcheck);
				
				while(true){
					if(id_check!=null){
						break;
					}
					Thread.sleep(1);
				}
				
				if(id_check){
					
					JOptionPane.showMessageDialog(this, "중복된 아이디가 존재합니다.");
					tf1_id.requestFocus();
					
				}
				else if(tf1_id.getText().length()<5 || tf1_id.getText().length()>12){
				
					JOptionPane.showMessageDialog(this, "아이디는 5~12글자 사이로 입력하세요.");
					tf1_id.requestFocus();
				}
				else {
					
					JOptionPane.showMessageDialog(this, "사용할 수 있는 아이디입니다.");
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
				JOptionPane.showMessageDialog(this, "중복확인을 해주세요.");
				tf1_id.requestFocus();
			}
			
			else if(tf1_id.getText().equals("")||pf1_pwd.equals("")||
			    		pf2_pwd.equals("")||tf2_email.getText().equals("")){
			    	JOptionPane.showMessageDialog(this,"공백이 있습니다.");
			    	return;
			}
			
			else if(!pwd1.equals(pwd2)){
				JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
				
				pf2_pwd.setText("");
				pf2_pwd.requestFocus();
			}
			else if(pwd1.length()<8){
				JOptionPane.showMessageDialog(this, "비밀번호는 8글자 이상 입력하세요.");
				pf1_pwd.setText(""); 
				pf2_pwd.setText(""); 
				pf1_pwd.requestFocus();
			}
	
			else if((!tf2_email.getText().contains("@"))||(!tf2_email.getText().contains("."))){
				JOptionPane.showMessageDialog(this, "이메일 형식이 올바르지 않습니다.");
				tf2_email.requestFocus();
			}
	
			else{
			
			BssNetWork inst = BssNetWork.getInst();
			inst.connection();
			info= tf1_id.getText()+"|"+pwd1+"|"+tf2_email.getText();
			
			BssNetWork.getInst().sendMessage(BssProtocol.REGISTER, info);
			
			
			
			
			
			JOptionPane.showMessageDialog(this, "회원가입 완료");
			
			
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
