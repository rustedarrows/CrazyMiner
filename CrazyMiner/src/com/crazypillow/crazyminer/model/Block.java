package com.crazypillow.crazyminer.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
	
	
	public static final float SIZE = 1f;
	
	Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	private int touch; //number of times it has been touched by the miner
	private int durability; //Number of touches it needs to break
	private int value;
	private BlockType type; //Type of block
	
	public Block(Vector2 pos) { //Creates a default dirt block when given a position
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.type = BlockType.DIRT;
		this.durability = 10;
		this.value = 0;
	}
	public Block(Vector2 pos, BlockType type, int durability, int price) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.durability = durability;
		this.value = price;
		this.type = type;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public void mine(int strength) {
		touch += strength;
	}
	public int getMined() {
		return touch;
	}
	public void setDurability(int durability) {
		this.durability = durability;
	}
	public int getDurability() {
		return durability;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int price) {
		this.value = price;
	}
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
		this.type = type;
	}
}
