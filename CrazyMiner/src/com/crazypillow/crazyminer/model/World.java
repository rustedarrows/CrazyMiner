package com.crazypillow.crazyminer.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	/** Our player controlled hero **/
	Miner miner;
	/** A world has a level through which Bob needs to go through **/
	Level level;
	
	/** The collision boxes **/
	Array<Rectangle> collisionRects = new Array<Rectangle>();

	// Getters -----------
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}
	public Miner getMiner() {
		return miner;
	}
	public Level getLevel() {
		return level;
	}
	/** Return only the blocks that need to be drawn **/
	public List<Block> getDrawableBlocks(int width, int height) {
		int x = (int)miner.getPosition().x;
		int y = (int)miner.getPosition().y;
		
		int lowX = x - width;
		int highX = x + width;
		int lowY = y - height;
		int highY = y + height;
		System.out.println(lowX + " : " + highX + " : " + lowY + " : " + highY);
		
		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for (int col = lowX; col < highX; col++) {
			for (int row = lowY; row <= highY; row++) {
				if(col < 0 || col > level.getWidth()-1) {
					
				}else if(row < 0 || row > level.getHeight()-1) {
					
				}else {
					block = level.getBlocks()[col][row];
					if (block != null) {
						System.out.println("added " + col + " : " + row);
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}

	// --------------------
	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		miner = new Miner(new Vector2(7, 2));
		level = new Level();
	}
}