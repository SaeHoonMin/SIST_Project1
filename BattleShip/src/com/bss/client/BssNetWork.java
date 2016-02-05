package com.bss.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import com.bss.client.container.GamePlayPanel;
import com.bss.client.container.GameReadyPanel;
import com.bss.client.container.MainFrame;
import com.bss.client.container.WaitRoomPanel;
import com.bss.client.gameObjects.AttackResult;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Tile;
import com.bss.client.gameObjects.TileState;
import com.bss.common.BssProtocol;

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
	private OutputStream out;
	private ObjectOutputStream objOut;
	private BufferedReader in;
	
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
		try {
			if (isConnected == true) {
				switch (MSGTYPE) {
				case BssProtocol.HOST_CONNECTION:
					out.write((BssProtocol.HOST_CONNECTION + "|" + "\n").getBytes());
					break;

				case BssProtocol.MATCH_QUE_REQ:

					System.out.println(MSGTYPE);
					out.write((MSGTYPE + "\n").getBytes());
					if (obj instanceof WaitRoomPanel)
						waitRoom = (WaitRoomPanel) obj;
					break;

				case BssProtocol.MATCH_READY:

					out.write((MSGTYPE + "\n").getBytes());
					if (obj instanceof GameReadyPanel)
						readyRoom = (GameReadyPanel) obj;
					break;

				case BssProtocol.ATTACK_PERFORMED:

					Tile t = (Tile) obj;
					enemyGrid = t.getGrid();
					out.write((MSGTYPE + "|" + t.getRow() + "|" + t.getCol() + "\n").getBytes());
					break;

				case BssProtocol.ATTACK_DONE:

					AttackResult info = (AttackResult) obj;
					out.write((MSGTYPE + "|" + info.getRow() + "|" + info.getCol() + "|" + info.isHit() + "|"
							+ info.getType() + "\n").getBytes());
					break;

				}
			} else {
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
					waitRoom.gameStart();
					break;
					
				case BssProtocol.MATCH_START:
					readyRoom.gameStart();
					break;
					
				case BssProtocol.ATTACK_PERFORMED:
					
					int row = Integer.parseInt(strTokens.nextToken());
					int col = Integer.parseInt(strTokens.nextToken());
					
					AttackResult info = GamePlayPanel.getInst().Attacked(row, col);
					sendMessage(BssProtocol.ATTACK_DONE, info);
					
					break;
				
				case BssProtocol.ATTACK_DONE:
					
					int row1 = Integer.parseInt(strTokens.nextToken());
					int col1 = Integer.parseInt(strTokens.nextToken());
					String isHit = strTokens.nextToken();
					
					if(isHit.equals("true"))
					{
						enemyGrid.getTileByRC(row1, col1).setIcon(ResContainer.tile_invalid_icon);
						enemyGrid.getTileByRC(row1, col1).setState(TileState.INVALID);
					}
					else
					{
						enemyGrid.getTileByRC(row1, col1).setIcon(ResContainer.tile_reserved_icon);
						enemyGrid.getTileByRC(row1, col1).setState(TileState.RESERVED);
					}
					System.out.println(row1 + " " + col1 + " " + isHit);
					
					GamePlayPanel.getInst().setActionAllowed(true);
					
					break;
				
				case BssProtocol.TURN_START:
					GamePlayPanel.getInst().showMyTurn();
					GamePlayPanel.getInst().setMyTurn(true);
					break;
				case BssProtocol.TURN_ENDS:
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
