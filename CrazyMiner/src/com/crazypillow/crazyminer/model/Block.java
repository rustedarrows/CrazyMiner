package com.crazypillow.crazyminer.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public static final float SIZE = 1f;
	
	Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	private boolean mined;
	private int touch; //number of times it has been touched by the miner
	private int durability; //Number of touches it needs to break
	
	public Block(Vector2 pos) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	public Block(Vector2 pos, int durability) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.durability = durability;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	public void mine(boolean val) {
		this.mined = val;
	}
	public boolean getMinded() {
		return mined;
	}
	public void touch() {
		touch++;
	}
	public int getTouch() {
		return touch;
	}
	public void setDurability(int durability) {
		this.durability = durability;
	}
	public int getDurability() {
		return durability;
	}
}
