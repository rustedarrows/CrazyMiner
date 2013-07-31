package com.crazypillow.crazyminer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MenuScreen implements Screen{
	
	public enum Menu {
		MAIN, LOAD, SETTINGS
	}

	
	private Stage stage;
	private Table table;
	private Game game;
	private SpriteBatch spriteBatch;
	private Skin skin;
	
	public MenuScreen(Game game) {
		this.game = game;
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		 if( table == null ) {
	            table = new Table( getSkin() );
	            table.setFillParent( true );
	            stage.addActor( table );
	        }
		 createMainMenu();
	}
	public MenuScreen(Game game, Menu menu) {
		this.game = game;
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		 if( table == null ) {
	            table = new Table( getSkin() );
	            table.setFillParent( true );
	            stage.addActor( table );
	        }
		 switch(menu) {
		 case MAIN:
			 break;
		 case LOAD:
			 break;
		 case SETTINGS:
			 break;
		 default:
			 break;
		 }
	}
	public void createMainMenu() {
		table.add("Crazy Miner").center();
		table.row();
		
		TextButton newGameButton = new TextButton("New Game", getSkin());
        newGameButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		game.setScreen(new GameScreen(game));
        	}
        });
        table.add(newGameButton).size(300, 60).center();
        table.row();
        TextButton loadGameButton = new TextButton("Load Game", getSkin());
        loadGameButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		game.setScreen(new MenuScreen(game, Menu.LOAD));
        	}
        });
        table.add(loadGameButton).size(300, 60).center();
        table.row();
	}
	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	     Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void resume() {
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		
	}
	 public Skin getSkin()
	    {
	        if( skin == null ) {
	            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
	            skin = new Skin( skinFile );
	        }
	        return skin;
	    }

}
