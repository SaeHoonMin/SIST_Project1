package com.bss.server;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
import com.bss.common.AttackResult;
import com.bss.common.BssMsg;
import com.bss.common.BssProtocol;

public class BssServer extends JFrame implements Runnable {

	JTextArea logConsole;
	JScrollPane scrollPanel;

	ServerSocket ss;
	Database member = new Database();
	Vector<Client> waitVc = new Vector<Client>();// 접속자 정보 저장
	ArrayList<Client> matchQueue = new ArrayList<Client>();

	public BssServer() {

		/* 
		 * Database Table Init. 
		 * 
		 * but if there is no oracleDB server to connect, 
		 * 
		 * should not connect and allows only guest mode...
		 * 
		 */
		
		member.battleshipTable();
		
		
		logConsole = new JTextArea();
		scrollPanel = new JScrollPane(logConsole);

		scrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});

		setSize(300, 400);
		add(scrollPanel, "Center");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			ss = new ServerSocket(3355);
			printLog("Start Server...");
			printLog("this Server's IP : " + InetAddress.getLocalHost().getHostAddress());
			printLog("this Server's Port : " + ss.getLocalPort());
		} catch (Exception ex) {
		}
	}
	// 접속을 받는다

	public void run() {
		try {
			while (true) {
				Socket s = ss.accept();

				printLog("Client " + s.getInetAddress() + " has Connected");

				Client client = new Client(s);
				client.start();

				waitVc.add(client);

			}
		} catch (Exception ex) {
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BssServer server = new BssServer();
		new Thread(server).start();

		server.createMatchThread();
	}

	public void createMatchThread() {
		MatchThread mt = new MatchThread();
		mt.start();
	}

	class MatchThread extends Thread {

		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			printLog("matchThread Start");
			while (true) {
				if (matchQueue.size() >= 2) {
					// 2명 이상이 큐에 들어오면
					printLog("Match Found");
					Client c1 = matchQueue.get(0);
					Client c2 = matchQueue.get(1);

					c1.messageTo(BssProtocol.MATCH_QUE_FOUND);
					c2.messageTo(BssProtocol.MATCH_QUE_FOUND);

					new Match(c1, c2);

					c1.opponent = c2;
					c2.opponent = c1;

					matchQueue.remove(1);
					matchQueue.remove(0);

					printLog("MatchQue size : " + matchQueue.size());
				} else {
					// 이거 안하면 제대로 안먹히는거
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

	class Client extends Thread {
		String userId = "";
		String nickName = "";
		Socket s;
		ObjectInputStream in;// 읽기
		ObjectOutputStream out;// 쓰기 TCP

		boolean match_ready;
		Client opponent = null;

		BssMsg recvMsg = new BssMsg();
		BssMsg sendMsg = new BssMsg();
		Match match;

		public Client(Socket s) {
			try {
				this.s = s;
				in = new ObjectInputStream(s.getInputStream());
				out = new ObjectOutputStream(s.getOutputStream());
			} catch (Exception ex) {
			}
		}

		// 통신
		public void run() {
			Database member = new Database();
			printLog("A Client Thread starts");

			try {
				while (true) {
					// 요청 받는다
					BssMsg recvMsg = (BssMsg) in.readObject();

					printLog("msgtype : " + recvMsg.msgType);

					// 처리
					switch (recvMsg.msgType) {

					case HOST_CONNECTION:
						messageTo(BssProtocol.WELCOME);
						messageAll(new BssMsg(BssProtocol.CLIENT_COUNT, String.valueOf(waitVc.size())));
						break;

					case REGISTER:

						String info = (String) recvMsg.msgObj;
						member.insertMember(info);
						s.close();
						break;
						
					case GUEST_LOGIN : 
						
						messageTo(BssProtocol.LOGIN_TRUE);
						
						break;

					case LOGIN_CHECK: {
						String id, pwd;
						StringTokenizer st = new StringTokenizer((String) recvMsg.msgObj, "|");
						id = st.nextToken();
						pwd = st.nextToken();

						if (member.login(id, pwd))
						{	
							messageTo(BssProtocol.LOGIN_TRUE);
						}
						else {
							messageTo(BssProtocol.LOGIN_FALSE);
							s.close();
						}
						break;
					}

					case ID_CHECK: {
						String id;
						id = (String) recvMsg.msgObj;
						if (member.idCheck(id)) {
							messageTo(BssProtocol.ID_TRUE);
							s.close();
						} else {
							messageTo(BssProtocol.ID_FALSE);
							s.close();
						}
						break;
					}

					case USERINFO:
						// userVc.add((UserInfo) recvMsg.msgObj); //유저아이디담기
						// System.out.println(recvMsg.msgObj);//접속한 유저아이디 출력
						for (int i = 0; i < waitVc.size(); i++) {
							if (waitVc.elementAt(i).userId.equals(recvMsg.msgObj.toString())) {
								System.out.println(waitVc.elementAt(i).userId+ " aaaand " +recvMsg.msgObj.toString());
								messageTo(BssProtocol.EXIT);
								break;
							}
						}

						userId = (String) recvMsg.msgObj;

						
						printLog("접속자 수:"+String.valueOf(waitVc.size()));
						break;

					case MATCH_QUE_REQ:
						printLog("Match Que requested");
						synchronized (this) {
							matchQueue.add(this);
							printLog("matchQueue Size : " + matchQueue.size());
						}
						break;
					case MATCH_QUE_CANCLED:
						matchQueue.remove(this);
						if (opponent != null)
							opponent.messageTo(BssProtocol.MATCH_QUE_CANCLED);
						break;

					case MATCH_READY:
						// 매치 상태에 있는 두 플레이어중 하나가 준비 완료
						// -> 상태를 바꾸고, 두명이 다 래디면 게임 시작 메세지 전송
						synchronized (this) {
							match_ready = true;
							printLog("player has pressed ready button");
							opponent.messageTo(BssProtocol.OPPONENT_READY);
						}
						break;
						
					case ID_REQ:
						if(opponent!=null)
							messageTo(new BssMsg(BssProtocol.ID_REQ,opponent.userId));
						
						break;

					case ATTACK_PERFORMED:

						printLog("attack_performed");

						opponent.messageTo(recvMsg);

						break;

					case ATTACK_DONE:

						opponent.messageTo(recvMsg);

						AttackResult ar = (AttackResult) recvMsg.msgObj;

						if (!ar.isHit) {
							opponent.messageTo(BssProtocol.TURN_ENDS);
							messageTo(BssProtocol.TURN_START);
						}

						break;

					case MATCH_ENDS:
						opponent = null;
						match = null;
						match_ready = false;
						break;
					}
				}
			} catch (Exception ex) {

				printLog("Exception : "+ex.getMessage());
				
				printLog("A Client has been disconnected");

				if (match != null) {
					opponent.messageTo(BssProtocol.MATCH_CANCLED);
					opponent.match = null;
					opponent.match_ready = false;
					opponent = null;
				}
				matchQueue.remove(this);
				waitVc.remove(this);
			
				printLog("접속자 수:"+String.valueOf(waitVc.size()));
				
				messageAll(new BssMsg(BssProtocol.CLIENT_COUNT, waitVc.size()));
			}
		}

		public void messageTo(BssProtocol type) {
			try {
				out.writeObject(new BssMsg(type, null));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public void messageTo(BssMsg msg) {
			try {
				out.writeObject(msg);
			} catch (Exception ex) {
			}
		}

		public void messageAll(BssMsg msg) {
			printLog(msg.msgType + " " + msg.msgObj.toString());
			try {
				for (int i = 0; i < waitVc.size(); i++) {
					Client c = waitVc.elementAt(i);
					c.messageTo(msg);
				}
			} catch (Exception ex) {
			}
		}
	}

	private void printLog(String str) {
		if (str.charAt(str.length() - 1) != '\n')
			logConsole.append(str + "\n");
		else
			logConsole.append(str);
	}
}
