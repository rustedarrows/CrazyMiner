package com.crazypillow.crazyminer.screens;

import  com.crazypillow.crazyminer.controller.MinerController;
import  com.crazypillow.crazyminer.model.World;
import  com.crazypillow.crazyminer.view.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen {

	private World 			world;
	private WorldRenderer 	renderer;
	private MinerController	controller;
	private FileHandle handle;
	public Game game;
	
	public GameScreen(Game game) {
		this.game = game;
	}
	private int width, height;
	
	@Override
	public void show() {
		handle = Gdx.files.local("world.xml");
		world = new World(handle);
		renderer = new WorldRenderer(world);
		controller = new MinerController(world);
		
		
	}

	@Override
	public void render(float delta) {
		if(world.getMiner().isDead()) {
			//game.setScreen(new DeadScreen(game));
		}
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		controller.update(delta);
		renderer.render(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		dispose();
	}

	@Override
	public void resume() {
		show();
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		System.out.println("Attempting to save world...");
		world.saveWorld(handle);
		
	}

	


}
