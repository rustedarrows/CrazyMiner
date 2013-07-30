package com.crazypillow.crazyminer.model;

public enum BlockType {
	DIRT(10, 0), 
	BRONZE(15, 5), 
	IRON(25, 10), 
	SILVER(30, 50), 
	GOLD(40, 100),
	DIAMOND(50, 500);
	
	private final int durability;
	private final int value;
	
	BlockType(int durability, int value) {
		this.durability = durability;
		this.value = value;
	}

	public int getDurability() {
		return durability;
	}

	public int getValue() {
		return value;
	}
	
}
