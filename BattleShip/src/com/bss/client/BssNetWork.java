package com.bss.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import com.bss.client.container.GamePlayPanel;
import com.bss.client.container.GameReadyPanel;
import com.bss.client.container.MainFrame;
import com.bss.client.container.WaitRoomPanel;
import com.bss.client.gameObjects.AnimName;
import com.bss.client.gameObjects.FixedLocAnimation;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Tile;
import com.bss.client.gameObjects.TileState;
import com.bss.common.AttackResult;
import com.bss.common.BssMsg;
import com.bss.common.BssProtocol;

import javafx.animation.Animation;
import resources.ResContainer;


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
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BssMsg msg = new BssMsg();
	private boolean isConnected=false;
	
	WaitRoomPanel waitRoom;
	GameReadyPanel readyRoom;
	Grid	enemyGrid;
	
	public boolean isConnected()
	{
		return isConnected;
	}
	
	
	public static BssNetWork getInst()
	{
		if( inst == null)
		{
			System.out.println("BssNetWork inst is null");
			inst = new BssNetWork();
		}
		
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
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		}

		// 통신 시작
		isConnected = true;
		new Thread(this).start();
		sendMessage(BssProtocol.HOST_CONNECTION,null);
	}
	
	public void sendMessage(BssProtocol type, Object obj)
	{
		BssMsg msg = new BssMsg();
		
		msg.msgObj = null;
		msg.msgType = type;
		
		try {
			if (isConnected == true) {
				switch (type) {
				case HOST_CONNECTION:
					
					
					break;

				case MATCH_QUE_REQ:
					
					if (obj instanceof WaitRoomPanel)
						waitRoom = (WaitRoomPanel) obj;
					break;
					
				case MATCH_QUE_CANCLED:
					
					break;

				case MATCH_READY:
					
					if (obj instanceof GameReadyPanel)
						readyRoom = (GameReadyPanel) obj;
					break;

				case ATTACK_PERFORMED:

					Tile t = (Tile) obj;
					enemyGrid = t.getGrid();
					System.out.println("msg send");
					AttackResult atk = new AttackResult(t.getRow(), t.getCol(), false);
					msg.msgObj = atk;
					break;

				case ATTACK_DONE:

					System.out.println("attackdone send");
					AttackResult info = (AttackResult) obj;
					msg.msgObj = obj;
					break;
				
				}
			} else {
				System.out.println("not connected to server.");
			}
			out.writeObject(msg);
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}

	
	
	@Override
	public void run() {
		
		BssMsg recvMsg = new BssMsg();
		BssProtocol type;
		try
		{
			while(true)
			{
				recvMsg = (BssMsg) in.readObject();
				
				switch(recvMsg.msgType)
				{
				case WELCOME:
					System.out.println("Welcome to BattleShip In Space..");
					break;
				
				case MATCH_QUE_FOUND:
					waitRoom.gameStart();
					break;
					
				case MATCH_START:
					readyRoom.gameStart();
					break;
					
				case ATTACK_PERFORMED:
					
					System.out.println("attack_performed received");
					
					AttackResult atk = (AttackResult) recvMsg.msgObj;
					
					AttackResult info = GamePlayPanel.getInst().Attacked(atk.row, atk.col);
					sendMessage(BssProtocol.ATTACK_DONE, info);
					
					break;
				
				case ATTACK_DONE:
					
					GamePlayPanel.getInst().AttackDone((AttackResult) recvMsg.msgObj);
					
					break;
				
				case TURN_START:
					GamePlayPanel.getInst().showMyTurn();
					GamePlayPanel.getInst().setMyTurn(true);
					break;
				case TURN_ENDS:
					GamePlayPanel.getInst().showEnemyTurn();
					GamePlayPanel.getInst().setMyTurn(false);
					break;
				}
			}
		}catch(Exception ex){
			System.out.println("error : " +ex.getMessage());
			ex.printStackTrace();
		}
	}
	
}
