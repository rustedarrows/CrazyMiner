package com.crazypillow.crazyminer.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Miner {

	private float xPerc, yPerc;
	
	public enum State {
		IDLE, MOVING, MINING
	}
	
	
	public static final float SIZE = 0.50f; // half a unit

	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.IDLE; 
	boolean		facingLeft = true;
	boolean 	dead = false;
	int energy;
	int money;
	int armor;
	float energyTick = 500l; //Rate at which the energy decreases
	long lastEnergyTick;

	public Miner(Vector2 position) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	public Miner(Vector2 position, int energy, int money, int armor) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.energy = energy;
		this.money = money;
		this.armor = armor;
	}
	
	
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public void reEnergize() {
		energy = 100;
	}
	public void changeTick(float tick) {
		this.energyTick = tick;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	/**
	 * Adds specified amount to money of Miner
	 * @param amount the amount to add to the current cash pile
	 */
	public void addMoney(int amount) {
		this.money += amount;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public float getXPerc() {
		return xPerc;
	}
	public float getYPerc() {
		return yPerc;
	}
	public void setXPerc(float val) {
		this.yPerc = val;
	}
	public void setYPerc(float val) {
		this.xPerc = val;
	}
	
	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	

	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}


	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}


	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public boolean isDead() {
		return dead;
	}

	public void update(float delta) {
		if(armor <= 0) {
			dead = true;
		}
		if(System.currentTimeMillis() - lastEnergyTick > energyTick) {
			if(energy <= 0) {
				dead = true;
			}else {
				energy -= 1;
				lastEnergyTick = System.currentTimeMillis();
			}
		}
	
	}
	
}
