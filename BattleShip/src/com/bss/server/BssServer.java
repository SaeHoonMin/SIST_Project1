package com.bss.server;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bss.client.gameObjects.ShipType;
import com.bss.common.BssProtocol;

public class BssServer extends JFrame implements Runnable{
	
	JTextArea logConsole;
	JScrollPane scrollPanel;
	
    ServerSocket ss;
    Vector<Client> waitVc=new Vector<Client>();//접속자 정보 저장
    
    ArrayList<Client> matchQueue = new ArrayList<Client>();
    
    
    public BssServer()
    {
    	logConsole = new JTextArea();
    	scrollPanel = new JScrollPane(logConsole);

		scrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
    	
    	setSize(300,400);
    	add(scrollPanel,"Center");
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	
    	try
    	{
    		ss=new ServerSocket(3355);
    		printLog("Start Server...");
    		printLog("this Server's IP : " +InetAddress.getLocalHost().getHostAddress());
    		printLog("this Server's Port : "+ss.getLocalPort());
    	}catch(Exception ex){}
    }
    //접속을 받는다 
    
    
    public void run()
    {
		try {
			while (true) {
				Socket s = ss.accept();

				printLog("Client " + s.getInetAddress() + " has Connected");

				Client client = new Client(s);
				client.start();

			}
		} catch (Exception ex) {
		}
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        BssServer server=new BssServer();
        new Thread(server).start();
   
        server.createMatchThread();
	}
	
	public void createMatchThread()
	{
		MatchThread mt = new MatchThread();
		mt.start();
	}

	class MatchThread extends Thread
	{
		 
		@Override
		public synchronized void  run() {
			// TODO Auto-generated method stub
			printLog("matchThread Start");
			while(true)
			{
				if(matchQueue.size()>=2)
				{
					//2명 이상이 큐에 들어오면
					printLog("Match Found");
					Client c1 = matchQueue.get(0);
					Client c2 = matchQueue.get(1);
					
					c1.messageTo(BssProtocol.MATCH_QUE_FOUND+"|"+c2.nickName);
					c2.messageTo(BssProtocol.MATCH_QUE_FOUND+"|"+c1.nickName);
					
					new Match(c1,c2);
					
					c1.opponent = c2;
					c2.opponent = c1;
					
					matchQueue.remove(1);
					matchQueue.remove(0);
										
					printLog("MatchQue size : " + matchQueue.size());
				}
				else
				{
					//이거 안하면 제대로 안먹히는거 수정해야함
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
    class Client extends Thread
    {
    	
    	 String nickName = "temp";
    	 Socket s;
    	 BufferedReader in;//읽기
    	 OutputStream out;//쓰기 TCP
    	
    	 boolean match_ready;
    	 Client opponent = null;
    	 
    	 Match match;
    	 
    	 
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
		public void run() {

			printLog("A Client Thread starts");

			try {
				while (true) {
					// 요청 받는다
					String msg = in.readLine();

					printLog(msg);

					StringTokenizer st = new StringTokenizer(msg, "|");

					int no = Integer.parseInt(st.nextToken());

					// 처리
					switch (no) {
					case BssProtocol.HOST_CONNECTION:
						messageTo(BssProtocol.WELCOME + "|" + "Welcome to the BattleShip in Space.");
						break;
					case BssProtocol.MATCH_QUE_REQ:
						printLog("Match Que requested");
						synchronized (this) {
							matchQueue.add(this);
							printLog("matchQueue Size : " + matchQueue.size());
						}
						break;
					case BssProtocol.MATCH_QUE_CANCLED:

						break;

					case BssProtocol.MATCH_READY:
						// 매치 상태에 있는 두 플레이어중 하나가 준비 완료
						// -> 상태를 바꾸고, 두명이 다 래디면 게임 시작 메세지 전송
						synchronized(this)
						{
							match_ready = true;
							printLog("player has pressed ready button");
						}
						break;
						
					case BssProtocol.ATTACK_PERFORMED:
						
						int row = Integer.parseInt(st.nextToken());
						int col = Integer.parseInt(st.nextToken());
						
						System.out.println("send attack_performed to opponent");
						
						opponent.messageTo(BssProtocol.ATTACK_PERFORMED+"|"+row+"|"+col);
						
						break;
					
					case BssProtocol.ATTACK_DONE :
						
						int row1 = Integer.parseInt(st.nextToken());
						int col1 = Integer.parseInt(st.nextToken());
						String isHit = st.nextToken();
						String type = st.nextToken();
						
						opponent.messageTo(BssProtocol.ATTACK_DONE+"|"
								+row1+"|"+col1+"|"+isHit+"|"+type);
						
						break;
					}

				}
			} catch (Exception ex) {

				printLog("A Client has been disconnected");
				
				// 매치가 널이 아니면 상대방에게 캔슬 메세지 보내고
				// 메치 쓰레드 스탑되게 ...
				
				matchQueue.remove(this);

			}
		}
    	 // 응답  ( 개인 , 전체 )
    	 // 중복제거 ==> 제어문 ==> 메소드 ==> 클래스
    	 public void messageTo(String str)
    	 {
    		  try
    		  {
    			  if(str.charAt(str.length()-1)=='\n')
    				  out.write(str.getBytes());
    			  else
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
    
	private void printLog(String str)
	{
		if(str.charAt(str.length()-1)!='\n')
			logConsole.append(str+"\n");
		else
			logConsole.append(str);
		
	}
}
