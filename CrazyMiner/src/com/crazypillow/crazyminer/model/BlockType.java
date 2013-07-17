package com.crazypillow.crazyminer.model;

public enum BlockType {
	DIRT(10, 0), 
	BRONZE(15, 0), 
	IRON(25, 0), 
	SILVER(30, 0), 
	GOLD(40, 0),
	DIAMOND(50, 0);
	
	private final int durability;
	private final int price;
	
	BlockType(int durability, int price) {
		this.durability = durability;
		this.price = price;
	}

	public int getDurability() {
		return durability;
	}

	public int getPrice() {
		return price;
	}
	
}
