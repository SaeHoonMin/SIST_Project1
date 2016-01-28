package com.bss.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BssServer extends JFrame implements Runnable{
	
	JTextArea logConsole;
	JScrollPane scrollPanel;
	
	
    ServerSocket ss;
    Vector<Client> waitVc=new Vector<Client>();//������ ���� ����
    
    
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
    //������ �޴´� 
    
    
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
    	 String id,name,sex,pos;
    	 int avata;
    	 Socket s;
    	 BufferedReader in;//�б�
    	 OutputStream out;//���� TCP
    	 
    	 public Client(Socket s)
    	 {
    		  try
    		  {
    			   this.s=s;
    			   in=new BufferedReader(new InputStreamReader(s.getInputStream()));
    			   out=s.getOutputStream();
    		  }catch(Exception ex){}
    	 }
    	 //��� 
    	 public void run()
    	 {
    		   try
    		   {
    			   while(true)
    			   {
    				    //��û �޴´� 
    				   String msg=in.readLine();
    				   // 100|id|name|sex|avata
    				   System.out.println(msg);
    				   StringTokenizer st=new StringTokenizer(msg,"|");
    				   int no=Integer.parseInt(st.nextToken());
    				    //ó��
    				   
    			   }
    		   }catch(Exception ex){}
    	 }
    	 // ����  ( ���� , ��ü )
    	 // �ߺ����� ==> ��� ==> �޼ҵ� ==> Ŭ����
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
