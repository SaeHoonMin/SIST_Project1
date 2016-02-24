package com.bss.client.gameObjects;

import java.io.Serializable;

public class UserInfo implements Serializable{
	
	private static UserInfo inst = null;
	
	private String userID;
	private String pwd;
	private String nickName;
	
	public UserInfo(String id){
		userID=id;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return userID;
	}
	
	/*public static UserInfo getInst(){
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
		
		
	}*/

}
