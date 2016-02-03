package com.bss.common;

import java.io.Serializable;

public class BssProtocol{
	
	public static final int HOST_CONNECTION		= 110;
	public static final int WELCOME				= 112;
	
	public static final int NULL = 0;
	
	public static final int MATCH_QUE_REQ		= 100;		// ��ġ Ž�� ��û 
	public static final int MATCH_QUE_FOUND		= 101;		// ��ġ Ž�� ����
	public static final int MATCH_QUE_CANCLED	= 102;		// ��ġ Ž�� ���
	
	public static final int MATCH_READY			= 200;
	public static final int MATCH_START			= 201;
	public static final int MATCH_ENDS			= 202;
	public static final int MATCH_CANCLED		= 203;
	
	
	public static final int ATTACK_PERFORMED	= 300;
	public static final int ATTACK_DONE			= 301;
	
	public static final int TURN_START			= 305;
	public static final int TURN_ENDS			= 306;
	

}
