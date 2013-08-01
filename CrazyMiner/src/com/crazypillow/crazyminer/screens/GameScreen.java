package com.crazypillow.crazyminer.screens;

import  com.crazypillow.crazyminer.controller.MinerController;
import  com.crazypillow.crazyminer.model.World;
import  com.crazypillow.crazyminer.view.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen {

	private World 			world;
	private WorldRenderer 	renderer;
	private MinerController	controller;
	private FileHandle handle;
	public Game game;
	
	private FPSLogger fps = new FPSLogger();

	public GameScreen(Game game, FileHandle handle, boolean newGame) {
		this.game = game;
		if(newGame) {
			handle.delete();
		}
		this.handle = handle;
	}
	private int width, height;
	
	@Override
	public void show() {
		world = new World(handle);
		renderer = new WorldRenderer(world, game);
		controller = new MinerController(world, renderer);
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		controller.update(delta);
		renderer.render(delta);
		fps.log();
	}
	
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		dispose();
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
