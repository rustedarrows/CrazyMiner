package com.crazypillow.crazyminer.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Miner {

	private float xPerc, yPerc;
	
	public enum State {
		IDLE, MOVING, MINING
	}
	
	public static float ACCELERATION 	= 20f;
	public static float GRAVITY 			= -20f;
	public static final float MAX_VERTICAL_VELOCITY	= 7f;
	public static final float DAMP 			= 0.90f;
	public static float MAX_VEL 			= 10f;
	
	public static final float SIZE = 0.50f; // half a unit

	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.IDLE; 
	boolean		facingLeft = true;
	boolean 	dead = false;
	boolean 	pauseEnergyTick = false;
	int engineUpgrade, armorUpgrade, fuelUpgrade, drillUpgrade;
	int energy;
	int maxEnergy = 100;
	int money;
	int armor;
	int drillStrength = 1;
	int maxArmor = 100;
	float energyTick = 1000l; //Rate at which the energy decreases
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
	public Miner(Vector2 position, int energy, int money, int armor, int fuelUpgrade, int drillUpgrade, int engineUpgrade) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.energy = energy;
		this.maxEnergy = 100 + fuelUpgrade*5;
		this.money = money;
		this.armor = armor;
		this.fuelUpgrade = fuelUpgrade;
		this.drillUpgrade = drillUpgrade;
		this.drillStrength = drillUpgrade + 1;
		this.engineUpgrade = engineUpgrade;
		this.ACCELERATION = 20f + engineUpgrade;
		this.MAX_VEL = ACCELERATION / 2;
	}
	public void stopMoving() {
		getVelocity().x = 0;
	}
	
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public void reEnergize() {
		energy = maxEnergy;
	}
	public void repair() {
		this.armor = maxArmor;
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
				if(pauseEnergyTick) {
					
				}else {
					energy -= 1;
					lastEnergyTick = System.currentTimeMillis();
				}
			}
		}
	
	}
	public int getEngineUpgrade() {
		return engineUpgrade;
	}
	public int getArmorUpgrade() {
		return armorUpgrade;
	}
	public int getFuelUpgrade() {
		return fuelUpgrade;
	}
	public boolean isPauseEnergyTick() {
		return pauseEnergyTick;
	}
	public void setPauseEnergyTick(boolean pauseEnergyTick) {
		this.pauseEnergyTick = pauseEnergyTick;
	}
	public void upgradeEngine() {
		engineUpgrade++;
		this.ACCELERATION = 20f + engineUpgrade;
		this.MAX_VEL = ACCELERATION / 2;
	}
	public void upgradeDrill() {
		drillStrength++;
		drillUpgrade++;
	}
	public void upgradeArmor() {
		armorUpgrade++;
		maxArmor += 10;
	}
	
	public void upgradeFuel() {
		fuelUpgrade++;
		maxEnergy += 10;
	}
	public int getDrillStrength() {
		return drillStrength;
	}
	public int getDrillUpgrade() {
		return drillUpgrade;
	}
}
