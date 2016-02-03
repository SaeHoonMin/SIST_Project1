package com.bss.client.gameObjects;

public class AttackResult {

	int row, col;
	boolean isHit;
	ShipType type;
	
	public AttackResult(int row, int col, boolean isHit)
	{
		this.row = row;
		this.col = col;
		this.isHit = isHit;
	}
	public AttackResult(int row, int col, boolean isHit, ShipType type)
	{
		this.row = row;
		this.col = col;
		this.isHit = isHit;
		this.type = type;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public boolean isHit() {
		return isHit;
	}
	public ShipType getType() {
		return type;
	}
}
