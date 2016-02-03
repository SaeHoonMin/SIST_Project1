package com.bss.server;

import com.bss.common.BssProtocol;

public class Match implements Runnable{
	
	int turn = 0;
	
	MatchState state ;
	
	BssServer.Client c1;
	BssServer.Client c2;
	
	public Match(BssServer.Client c1, BssServer.Client c2)
	{
		this.c1 = c1;
		this.c2 = c2;
		
		c1.match = this;
		c2.match = this;
		
		state = MatchState.READY;
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println("match thread start");
		
		while(true)
		{
			if(c1.match_ready == true && c2.match_ready == true
					&& state == MatchState.READY)
			{
				System.out.println("match start");
				c1.messageTo(BssProtocol.MATCH_START+"|");
				c2.messageTo(BssProtocol.MATCH_START+"|");
				state = MatchState.START;
			}
		
			
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}
