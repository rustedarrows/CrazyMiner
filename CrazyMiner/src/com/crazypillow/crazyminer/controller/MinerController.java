package com.crazypillow.crazyminer.controller;

import java.util.HashMap;
import java.util.Map;

import com.crazypillow.crazyminer.model.Block;
import com.crazypillow.crazyminer.model.Miner;
import com.crazypillow.crazyminer.model.Miner.State;
import com.crazypillow.crazyminer.model.World;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class MinerController {

	enum Dir {
		X, Y
	}

	private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_JUMP_SPEED	= 7f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 6f;
	
	private World 	world;
	private Miner 	miner;
	private boolean grounded = false;
	

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
	
	public MinerController(World world) {
		this.world = world;
		this.miner = world.getMiner();
	}


	
	
	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of Bob
		processInput();
		
		// If Bob is grounded then reset the state to IDLE 
		
		// Setting initial vertical acceleration 
		miner.getAcceleration().y = GRAVITY;
		
		// Convert acceleration to frame time
		miner.getAcceleration().scl(delta);
		
		// apply acceleration to change velocity
		miner.getVelocity().add(miner.getAcceleration().x, miner.getAcceleration().y);

		// checking collisions with the surrounding blocks depending on Bob's velocity
		checkCollisionWithBlocks(delta);

		// apply damping to halt Bob nicely 
		miner.getVelocity().x *= DAMP;
		
		// ensure terminal velocity is not exceeded
		if (miner.getVelocity().x > MAX_VEL) {
			miner.getVelocity().x = MAX_VEL;
		}
		if (miner.getVelocity().x < -MAX_VEL) {
			miner.getVelocity().x = -MAX_VEL;
		}
		
		// simply updates the state time
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
		
		// clear collision boxes in world
		world.getCollisionRects().clear();
		
		// if bob collides, make his horizontal velocity 0
		for (Block block : collidable) {
			if (block == null) continue;
			if (minerRect.overlaps(block.getBounds())) {
				miner.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}

		// reset the x position of the collision box
		minerRect.x = miner.getPosition().x;
		
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
		
		for (Block block : collidable) {
			if (block == null) continue;
			if (minerRect.overlaps(block.getBounds())) {
				if (miner.getVelocity().y < 0) {
					grounded = true;
				}
				miner.getVelocity().y = 0;
				break;
			}
		}
		
		// reset the collision box's position on Y
		minerRect.y = miner.getPosition().y;
		
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
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}

	
	private boolean processInput() {
		float y = miner.getYPerc();
		float x = miner.getXPerc();
		if(y == 0 && x == 0) {
			miner.setState(State.LEFT);
		}
		if(y > 0) {
			miner.setState(State.RIGHT);
			if(x< 0) {
				miner.setState(State.DOWN);
				miner.getVelocity().y = MAX_JUMP_SPEED*x;
			}else if(x > 0) {
				miner.setState(State.UP);
				miner.getVelocity().y = MAX_JUMP_SPEED*x;
			}else {
				miner.setState(State.RIGHT);
			}
			
			miner.getAcceleration().x = ACCELERATION*y;
		}
		if(y < 0) {
			miner.setState(State.LEFT);
			if(x < 0) {
				miner.setState(State.DOWN);
				miner.getVelocity().y = MAX_JUMP_SPEED*x;
			}else if(x > 0) {
				miner.setState(State.UP);
				miner.getVelocity().y = MAX_JUMP_SPEED*x;
			}else {
				
			}
			miner.getAcceleration().x = ACCELERATION*y;
		}
		
		
		return false;
	}

}