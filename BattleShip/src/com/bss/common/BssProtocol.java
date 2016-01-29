package com.bss.common;

import java.io.Serializable;

public class BssProtocol{
	
	public static final int HOST_CONNECTED		= 110;
	public static final int WELCOME				= 112;
	
	public static final int NULL = 0;
	
	public static final int MATCH_QUE_REQ		= 100;		// 매치 탐색 요청 
	public static final int MATCH_QUE_FOUND		= 101;		// 매치 탐색 시작
	public static final int MATCH_QUE_CANCLED	= 102;		// 매치 탐색 취소
	
	public static final int MATCH_READY			= 200;
	public static final int MATCH_START			= 201;
	public static final int MATCH_ENDS			= 202;
	
	public static final int ATTACKED				= 300;
	public static final int TURN_START			= 301;
	public static final int TURN_ENDS			= 302;
	

}
