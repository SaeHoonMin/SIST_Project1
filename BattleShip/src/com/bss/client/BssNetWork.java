package com.bss.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;


public class BssNetWork extends Thread{

	Socket s;
	OutputStream out;
	BufferedReader in;

	
	
	public void connection()
	{
		  try
		  {
			   s=new Socket("localhost",3355);
			   in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			   out=s.getOutputStream();
			   
			  
		  }catch(Exception ex){}
		  
		  //통신 시작
		  new Thread(this).start();
	}
	
	@Override
	public void run() {
		
		String msg;
		StringTokenizer strTokens ;
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				msg = in.readLine();
				
				System.out.println(msg);
				
				strTokens = new StringTokenizer(msg,"|");
				
				int no=Integer.parseInt(strTokens.nextToken());
				
			}
		}catch(Exception ex){}
	}
	
}
