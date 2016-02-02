package com.bss.client.gameObjects;

public class UserInfo {
	
	private static UserInfo inst = null;
	
	private String userID;
	private String nickName;
	
	
	public static UserInfo getInst()
	{
		if(inst == null)
		{
			System.out.println("create Instance Fisrt!");
			return null;
		}
		else
			return inst;
	}
	
	public UserInfo()
	{
		inst = this;
		
		
	}

}
