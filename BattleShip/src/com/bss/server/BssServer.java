package com.bss.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bss.common.BssProtocol;

public class BssServer extends JFrame implements Runnable{
	
	JTextArea logConsole;
	JScrollPane scrollPanel;
	
	
    ServerSocket ss;
    Vector<Client> waitVc=new Vector<Client>();//접속자 정보 저장
    
    
    public BssServer()
    {
    	logConsole = new JTextArea();
    	scrollPanel = new JScrollPane(logConsole);
    	
    	setSize(300,400);
    	add(scrollPanel,"Center");
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	
    	try
    	{
    		ss=new ServerSocket(3355);
    		printLog("Start Server...");
    	}catch(Exception ex){}
    }
    //접속을 받는다 
    
    
    public void run()
    {
    	  try
    	  {
    		  while(true)
    		  {
    			  Socket s=ss.accept();
    			  
    			  printLog("Client "+s.getInetAddress()+" has Connected");
    			  
    			  Client client=new Client(s);
    			  client.start();
    			
    		  }
    	  }catch(Exception ex){}
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        BssServer server=new BssServer();
        new Thread(server).start();
	}
	
	private void printLog(String str)
	{
		if(str.charAt(str.length()-1)!='\n')
			logConsole.append(str+"\n");
		else
			logConsole.append(str);
	}
	
	
	
    class Client extends Thread
    {
    	 String nickName;
    	 Socket s;
    	 BufferedReader in;//읽기
    	 OutputStream out;//쓰기 TCP
    	 ObjectOutputStream objOut;
    	 
    	 public Client(Socket s)
    	 {
    		  try
    		  {
    			   this.s=s;
    			   in=new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF8"));
    			   out=s.getOutputStream();
    		  }catch(Exception ex){}
    	 }
    	 //통신 
    	 public void run()
    	 {

			   printLog("Client Thread starts");
			   
    		   try
    		   {
    			   while(true)
    			   {
    				   //요청 받는다 
    				   String msg=in.readLine();
    				   
    				   printLog(msg);
    				   
    				   StringTokenizer st=new StringTokenizer(msg,"|");
    				   
    				   int no=Integer.parseInt(st.nextToken());
    				    //처리
    				   printLog("no : "+no);
    				   
    				   switch(no)
    				   {
    				   case BssProtocol.HOST_CONNECTED:
    					   messageTo(BssProtocol.WELCOME +"|"+"Welcome to the BattleShip in Space.");
    					   break;
    				   }
    				   
    			   }
    		   }catch(Exception ex){}
    	 }
    	 // 응답  ( 개인 , 전체 )
    	 // 중복제거 ==> 제어문 ==> 메소드 ==> 클래스
    	 public void messageTo(String str)
    	 {
    		  try
    		  {
    			   out.write((str+"\n").getBytes());
    		  }catch(Exception ex){}
    	 }
    	 public void messageAll(String str)
    	 {
    		  try
    		  {
    			   for(int i=0;i<waitVc.size();i++)
    			   {
    				   Client c=waitVc.elementAt(i);
    				   c.messageTo(str);
    			   }
    		  }catch(Exception ex){}
    	 }
    }
}
