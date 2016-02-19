package com.bss.common;

import java.io.Serializable;

public enum BssProtocol implements Serializable{
	
	HOST_CONNECTION,
	WELCOME,
	INSERTUSERINFO,   //유저 가입정보
	USERINFO, // 접속한 유저정보
	EXIT,
	
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
