package com.crazypillow.crazyminer.model;

public enum BlockType {
	DIRT(20, 0), 
	BRONZE(25, 5), 
	IRON(35, 10), 
	SILVER(50, 50), 
	GOLD(65, 100),
	DIAMOND(200, 500);
	
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
