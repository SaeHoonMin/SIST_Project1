package com.bss.server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class DBA{
	   Connection con;
	   PreparedStatement pstmt;
	   ResultSet rs;
	   String info;
	   String query; 

	
	   //�ڹٿ� ����Ŭ ����ҽ�
	 public void dbConnect(){ 
	      try{
	   Class.forName("oracle.jdbc.driver.OracleDriver");
	  }catch(ClassNotFoundException cnfe){
	   cnfe.printStackTrace();
	   System.out.println("����̹� �ε��� ����");
	  }
	   try{
	   String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	         String userId = "scott"; 
	         String userPass = "tiger";          
	         con =DriverManager.getConnection(url,userId,userPass);
	      
	  }catch(SQLException e){
	   System.out.println("Ŀ�ؼ� ������ ����");
	  }
	   }
	 
	 //ȸ������
   public void insertMember(String info){
	  	System.out.println(info); 
	   	dbConnect();
	 
	   	StringTokenizer st = new StringTokenizer(info, "|");
	   	System.out.println(st.nextToken());
	    String id="114|123|123|123@";		
		String[] split = info.split("|");
		   
	    String sql ="insert into BattleShip values(?,?,?)";  //ȸ�� �߰�
	    
	    
	    try{
	    pstmt = con.prepareStatement(sql);
	    pstmt.setString(1,st.nextToken()); //1��° ?��  id����
	    pstmt.setString(2,st.nextToken());  // 2��° ?��  pwd����
	    pstmt.setString(3,st.nextToken());  // 3��° �̸�����
	
	    rs=pstmt.executeQuery();  //���� ����
	   
	    
	       }catch(SQLException e){
	   e.printStackTrace();
	   System.out.println("���ο� ���ڵ� �߰��� ����");
	  }
	    }
   
 //���� ����� �ڵ����� ��Ʋ�� ���̺��� �������.
   public void battleshipTable(){
	   dbConnect();
	   String sql ="create table Battleship(id varchar(20) not null,"
	   		+ "pwd varchar2(20) not null,email varchar2(30))"; 
	   try{
		   pstmt = con.prepareStatement(sql);
		   rs=pstmt.executeQuery();
		   
	   }catch(SQLException e){
		   System.out.println("���̺��� �����մϴ�.");
	   }
	   
   }
   
   //////////////////////////////////////////
   public void deleteAlluser(){
	   dbConnect();
	   query="delete from battleship";
	   try{
		   pstmt = con.prepareStatement(query);
		   rs=pstmt.executeQuery();
		   System.out.println("���ڵ����");
	   }catch(Exception ex){}
   }
   	/////////////////////////////////////////////�ʿ���� �޼ҵ�

   
   ////////////////////////////////////////////////////
   
   	// id pwd email ��������
	   public void allUserInfo(){
		   dbConnect();
		   String alluser="";
		   query="select id,pwd,email from BattleShip";
		   try{
			   System.out.println("���ڵ� �˻�����");
		   pstmt = con.prepareStatement(query);
		   rs=pstmt.executeQuery();
		   
		   while(rs.next()){
			   alluser+=rs.getString("id")+"|";
		   }
		   System.out.println(alluser);
		 
		   }catch(Exception ex){}
	   }///////////////////////////////�ʿ���� �޼ҵ�
	

	   //���̵� �ߺ�üũ
	   public Boolean idCheck(String id) throws SQLException{
		    dbConnect();
		    ResultSet rs = null;
		    System.out.println(id);
		    int Cnt = 0;
		    String SQL1 = "SELECT * FROM BattleShip WHERE ID=?";
		    pstmt = con.prepareStatement(SQL1);
		    pstmt.setString(1,id);
		    rs=pstmt.executeQuery();
		    /*rs = pstmt.executeQuery();*/
		    
		    while(rs.next()){
		     Cnt++;
		    }
		    if(Cnt > 0){
		    System.out.println("���̵�����");
		    return true;
		    }else{
		    System.out.println("���̵����");
		    return false;
		    }
	   }
	   
	   public Boolean login(String id,String pwd) throws SQLException{
		    dbConnect();
		    ResultSet rs = null;

		    String SQL1 = "SELECT id,pwd FROM battleship WHERE id like ?";
		    pstmt = con.prepareStatement(SQL1);
		    pstmt.setString(1,id);
		    rs=pstmt.executeQuery();
		    
		    while(rs.next()){
		    	if(rs.getString("id").equals(id)&&rs.getString("pwd").equals(pwd)){
		    		System.out.println("�α��� ����");
		    		return true;
		    	}
		    }
		    return false;
	   }
	   
	   
}
