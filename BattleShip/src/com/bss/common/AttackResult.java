package com.bss.common;

import java.awt.Point;
import java.io.Serializable;

import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.ShipType;

public class AttackResult implements Serializable{

	public int row;
	public int col;
	public boolean isHit;
	public ShipType type;
	public int headRow;
	public int headCol;
	public int tailRow;
	public int tailCol;
	public ShipAngle ang;
	
	public AttackResult(int row, int col, boolean isHit)
	{
		this.row = row;
		this.col = col;
		this.isHit = isHit;
	}
	public AttackResult(int row, int col, boolean isHit, ShipType type, 
			int headRow, int headCol,int tailRow, int tailCol, ShipAngle ang)
	{
		this.row = row;
		this.col = col;
		this.isHit = isHit;
		this.type = type;
		this.headRow = headRow;
		this.headCol = headCol;
		this.tailRow = tailRow;
		this.tailCol = tailCol;
		this.ang = ang;
	}

}
