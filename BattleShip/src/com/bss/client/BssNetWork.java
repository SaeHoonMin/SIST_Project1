package com.bss.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import com.bss.client.container.GameReadyPanel;
import com.bss.client.container.WaitRoomPanel;
import com.bss.common.BssProtocol;

/*
on the sender's side:
	CustomObject objectToSend=new CustomObject();
	Socket s = new Socket("yourhostname", 1234);
	ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
	out.writeObject(objectToSend);
	out.flush();

and on the receiving end:
	ServerSocket server = new ServerSocket(1234);
	Socket s = server.accept();
	ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	CustomObject objectReceived = (CustomObject) in.readObject();
*/

public class BssNetWork extends Thread{

	private static BssNetWork inst;
	
	private Socket s;
	private OutputStream out;
	private ObjectOutputStream objOut;
	private BufferedReader in;
	
	private boolean isConnected=false;
	
	WaitRoomPanel waitRoom;
	GameReadyPanel readyRoom;
	
	public boolean isConnected()
	{
		return isConnected;
	}
	
	
	public static BssNetWork getInst()
	{
		if( inst == null)
			inst = new BssNetWork();
		
		return inst;
	}
	
	public BssNetWork()
	{
		isConnected=false;
		inst = this;
	}

	public void connection() {
		
		try {
			s = new Socket("localhost", 3355);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			out = s.getOutputStream();
			//objOut = new ObjectOutputStream(s.getOutputStream());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		}

		// 통신 시작
		isConnected = true;
		new Thread(this).start();
		
		sendMessage(BssProtocol.HOST_CONNECTION,null);
	}
	
	public void sendMessage(int MSGTYPE, Object obj)
	{
		try{
		if(isConnected==true)
		{
			switch(MSGTYPE)
			{
			case BssProtocol.HOST_CONNECTION:
				out.write((BssProtocol.HOST_CONNECTION+"|"+"\n").getBytes());
				break;
			
			case BssProtocol.MATCH_QUE_REQ:
				
				System.out.println(MSGTYPE);
				out.write((MSGTYPE+"\n").getBytes());
				if(obj instanceof WaitRoomPanel)
					waitRoom = (WaitRoomPanel) obj;
				break;
				
			case BssProtocol.MATCH_READY:
				
				out.write((MSGTYPE+"\n").getBytes());
				if(obj instanceof GameReadyPanel)
					readyRoom = (GameReadyPanel) obj;
				break;
				
			
			}
		}
		else
		{
			System.out.println("not connected to server.");
		}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
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
				
				switch(no)
				{
				case BssProtocol.WELCOME:
					System.out.println(strTokens.nextToken());
					break;
				
				case BssProtocol.MATCH_QUE_FOUND:
					System.out.println("match found received");
					waitRoom.gameStart();
					break;
				case BssProtocol.MATCH_START:
					readyRoom.gameStart();
					break;
				}
			}
		}catch(Exception ex){}
	}
	
}
