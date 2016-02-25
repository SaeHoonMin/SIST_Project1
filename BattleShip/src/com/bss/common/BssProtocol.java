package com.bss.common;

import java.io.Serializable;

public enum BssProtocol implements Serializable{
	
	HOST_CONNECTION,
	WELCOME,
	INSERTUSERINFO,   //���� ��������
	ID_OVERLAP,//id�ߺ�
	
	ID_CHECK,
	ID_TRUE,
	ID_FALSE,
	ID_REQ,			//��� ���̵� ��û
	LOGIN_CHECK,
	LOGIN_TRUE,
	LOGIN_FALSE,
	GUEST_LOGIN,
	USERINFO, // ������ ��������
	EXIT,
	
	CHAT_MSG,
	
	REGISTER,
	MATCH_QUE_REQ,
	MATCH_QUE_FOUND,
	MATCH_QUE_CANCLED,
	
	MATCH_READY,
	MATCH_START,
	MATCH_ENDS	,
	MATCH_CANCLED,
	
	OPPONENT_READY,
	
	ATTACK_PERFORMED,
	ATTACK_DONE,
	
	TURN_START,
	TURN_ENDS,
	
	CLIENT_COUNT

}
