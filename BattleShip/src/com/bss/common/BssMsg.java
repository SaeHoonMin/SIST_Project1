package com.bss.common;

import java.io.Serializable;

public class BssMsg implements Serializable {
	
	public BssProtocol msgType;
	
	public Object msgObj;
	public String userinfo;
	public BssMsg()
	{
	}
	public BssMsg(BssProtocol type, Object obj)
	{
		msgObj = obj;
		msgType =type;
	}

}
