package com.crazypillow.crazyminer.controller;


import com.crazypillow.crazyminer.model.Block;
import com.crazypillow.crazyminer.model.Miner;
import com.crazypillow.crazyminer.model.Miner.State;
import com.crazypillow.crazyminer.model.World;
import com.crazypillow.crazyminer.view.WorldRenderer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class MinerController {

	enum Dir {
		X, Y
	}
	
	private boolean shopSignVis = false;
	
	
	


	private Table table;
	private World 	world;
	private Miner 	miner;
	
	float yPerc, xPerc;
	

	// This is the rectangle pool used in collision detection
	// Good to avoid instantiation each frame
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	

	// Blocks that Bob can collide with any given frame
	private Array<Block> collidable = new Array<Block>();
	private WorldRenderer renderer;
	
	public MinerController(World world, WorldRenderer renderer) {
		this.world = world;
		this.miner = world.getMiner();
		this.renderer = renderer;
	}


	
	
	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of Bob
		processInput();
		
		
		
		// Setting initial vertical acceleration 
		miner.getAcceleration().y = miner.GRAVITY;
		
		// Convert acceleration to frame time
		miner.getAcceleration().scl(delta);
		
		// apply acceleration to change velocity
		miner.getVelocity().add(miner.getAcceleration().x, miner.getAcceleration().y);

		// checking collisions with the surrounding blocks depending on Bob's velocity
		checkCollisionWithBlocks(delta);

		// apply damping to halt Bob nicely 
		miner.getVelocity().x *= miner.DAMP;
		
		// ensure terminal velocity is not exceeded
		if (miner.getVelocity().x > miner.MAX_VEL) {
			miner.getVelocity().x = miner.MAX_VEL;
		}
		if (miner.getVelocity().x < -miner.MAX_VEL) {
			miner.getVelocity().x = -miner.MAX_VEL;
		}
		
		// update energy drain
		miner.update(delta);

	}

	/** Collision checking **/
	private void checkCollisionWithBlocks(float delta) {
		// scale velocity to frame units 
		miner.getVelocity().scl(delta);
		
		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle minerRect = rectPool.obtain();
		// set the rectangle to bob's bounding box
		minerRect.set(miner.getBounds().x, miner.getBounds().y, miner.getBounds().width, miner.getBounds().height);
		
		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) miner.getBounds().y;
		int endY = (int) (miner.getBounds().y + miner.getBounds().height);
		// if Bob is heading left then we check if he collides with the block on his left
		// we check the block on his right otherwise
		if (miner.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(miner.getBounds().x + miner.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(miner.getBounds().x + miner.getBounds().width + miner.getVelocity().x);
		}

		// get the block(s) bob can collide with
		populateCollidableBlocks(startX, startY, endX, endY);

		// simulate bob's movement on the X
		minerRect.x += miner.getVelocity().x;
		
		
		// if bob collides, make his horizontal velocity 0
		for (Block block : collidable) {
			if (block == null) continue;
			if (minerRect.overlaps(block.getBounds())) {
				mineBlock(block);
				miner.getVelocity().x = 0;
				break;
			}
		}

		// reset the x position of the collision box
		minerRect.x = miner.getPosition().x;
		
		//---------------------    Y AXIS COLLISION DETECTION   -------------------------
		
		// the same thing but on the vertical Y axis
		startX = (int) miner.getBounds().x;
		endX = (int) (miner.getBounds().x + miner.getBounds().width);
		if (miner.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(miner.getBounds().y + miner.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(miner.getBounds().y + miner.getBounds().height + miner.getVelocity().y);
		}
		
		populateCollidableBlocks(startX, startY, endX, endY);
		
		minerRect.y += miner.getVelocity().y;
		//System.out.println("Vel: " + miner.getVelocity().y);
		for (Block block : collidable) {
			if (block == null) continue;
			if (minerRect.overlaps(block.getBounds())) {
				mineBlock(block);
				
				if(miner.getVelocity().y < -0.4) {
					System.out.println("We took damage");
				}
				miner.getVelocity().y = 0;
				break;
			}
		}
		
		// reset the collision box's position on Y
		minerRect.y = miner.getPosition().y;
		
		if(miner.getPosition().x < 5.1) {
			if(yPerc < .50) {
				miner.getVelocity().x = 0;
			}
		}
		if(miner.getPosition().x > 44.5) {
			if(yPerc > .50) {
				miner.getVelocity().x = 0;
			}
		}
		if(minerRect.overlaps(renderer.getShopSprite().getBoundingRectangle()) ){
			if(shopSignVis) {
				
			}else {
				shopSignVis = true;
				renderer.showShopSign();
			}
		} else {
			shopSignVis = false;
			renderer.hideShopSign();
		}
		if(renderer.isShopOpen()) {
			miner.getVelocity().x = 0;
			miner.getVelocity().y = 0;
		}
		// update Bob's position
		miner.getPosition().add(miner.getVelocity());
		miner.getBounds().x = miner.getPosition().x;
		miner.getBounds().y = miner.getPosition().y;
		
		// un-scale velocity (not in frame time)
		miner.getVelocity().scl(1 / delta);
		
	}

	/** populate the collidable array with the blocks found in the enclosing coordinates **/
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getWidth() && y >=0 && y < world.getHeight()) {
					collidable.add(world.get(x, y));
				}
			}
		}
	}
	public void mineBlock(Block block) {
		if((xPerc == 0 && yPerc == 0)) {
			//Do Not mine the blocks, as we aren't actually moving
			}else {
				block.mine(miner.getDrillStrength()); 
				if(block.getMined() > block.getDurability()) {
					miner.addMoney(block.getValue());
					world.setNull((int)block.getBounds().x, (int)block.getBounds().y);
					miner.setEnergy(miner.getEnergy() - 1);
				
				}
			}
	}

	
	private boolean processInput() {
		yPerc = miner.getYPerc();
		xPerc = miner.getXPerc();
		
			if(yPerc == 0 && xPerc == 0) {
				miner.setState(State.IDLE);
			}
			if(yPerc > 0) {
				if(xPerc < 0) {
					miner.getVelocity().y = miner.MAX_VERTICAL_VELOCITY*xPerc;
				}else if(xPerc > 0) {
					miner.getVelocity().y = miner.MAX_VERTICAL_VELOCITY*xPerc;
				}
				miner.getAcceleration().x = miner.ACCELERATION*yPerc;
			}
			if(yPerc < 0) {
				if(xPerc < 0) {
					miner.getVelocity().y = miner.MAX_VERTICAL_VELOCITY*xPerc;
				}else if(xPerc > 0) {
					miner.getVelocity().y = miner.MAX_VERTICAL_VELOCITY*xPerc;
				}else {
					
				}
				miner.getAcceleration().x = miner.ACCELERATION*yPerc;
			}
		
		
		return false;
	}


}
