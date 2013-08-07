package com.crazypillow.crazyminer.screens;

import java.io.IOException;
import java.util.Iterator;

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
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;


public class MenuScreen implements Screen{
	
	public enum Menu {
		MAIN, LOAD, SETTINGS, SELECT
	}

	
	private Stage stage;
	private Table table;
	private Game game;
	private SpriteBatch spriteBatch;
	private Skin skin;
	private FileHandle handle;
	private String saveName;
	private String worldName;
	private FileHandle world1;
	private FileHandle world2;
	private FileHandle world3;
	
	
	/**
	 * 
	 * Creates the various menus for the game
	 * @param game the main game class so we can switch screens
	 */
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
			 createMainMenu();
			 break;
		 case LOAD:
			showWorlds(false);
			 break;
		 case SETTINGS:
			 createSettingsMenu();
			 break;
		 default:
			 createMainMenu();
			 break;
		 }
	}
	private void showWorlds(final boolean newWorld) {
		table.clear();
		addOptions();
		world1 = Gdx.files.local("world1.xml");
		world2 = Gdx.files.local("world2.xml");
		world3 = Gdx.files.local("world3.xml");
		if(newWorld) {
			table.add("Select Save Spot").center();
			table.row();
			table.add("(Note: Previous games will be overwrittern)").center();
			table.row();
		}else {
			table.add("Select Save: ").center();
			table.row();
		}
			TextButton world1Button = new TextButton("World  1",  getSkin());
			world1Button.addListener(new ClickListener() {
	        	@Override
	        	public void clicked(InputEvent event, float x, float y) {
	        		FileHandle h = Gdx.files.local("world1.xml");
	        		game.setScreen(new GameScreen(game, h, newWorld));
	        	}
	        });
			TextButton world2Button = new TextButton("World  2",  getSkin());
			world2Button.addListener(new ClickListener() {
	        	@Override
	        	public void clicked(InputEvent event, float x, float y) {
	        		FileHandle h = Gdx.files.local("world2.xml");
	        		game.setScreen(new GameScreen(game, h, newWorld));
	        	}
	        });
			TextButton world3Button = new TextButton("World  3",  getSkin());
			world3Button.addListener(new ClickListener() {
	        	@Override
	        	public void clicked(InputEvent event, float x, float y) {
	        		FileHandle h = Gdx.files.local("world3.xml");
	        		game.setScreen(new GameScreen(game, h, newWorld));
	        	}
	        });
			table.defaults().size(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 5);
			if(!newWorld) {
				
				if(world1.exists()) {
					table.add(world1Button).center();
					table.row();
				}
				if(world2.exists()) {
					table.add(world2Button).center();
					table.row();
				}
				if(world3.exists()) {
					table.add(world3Button).center();
					table.row();
				}
			}else {
				table.add(world1Button).center();
				table.row();
				table.add(world2Button).center();
				table.row();
				table.add(world3Button).center();
				table.row();
			}
		
		
	}
	private void addOptions() {
		TextButton mainMenu = new TextButton("Main Menu",  getSkin());
		mainMenu.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		game.setScreen(new MenuScreen(game));
        	}
        });
		table.add(mainMenu).size(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10).left();
		table.row();
		
	}
	private void createSettingsMenu() {
		// TODO Auto-generated method stub
		
	}
	/*private void createLoadGameMenu(FileHandle handle) {
		table.clear();
		table.add("Select Save: ").center();
		table.row();
		TextButton world1Button = new TextButton("World  1",  getSkin());
		world1Button.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		FileHandle h = Gdx.files.local("world1.xml");
        		game.setScreen(new GameScreen(game, h, false));
        	}
        });
		TextButton world2Button = new TextButton("World  2",  getSkin());
		world2Button.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		FileHandle h = Gdx.files.local("world2.xml");
        		game.setScreen(new GameScreen(game, h, false));
        	}
        });
		TextButton world3Button = new TextButton("World  3",  getSkin());
		world3Button.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		FileHandle h = Gdx.files.local("world3.xml");
        		game.setScreen(new GameScreen(game, h, false));
        	}
        });
		
		table.add(world1Button).size(300, 60).center();
		table.row();
		table.add(world2Button).size(300, 60).center();
		table.row();
		table.add(world3Button).size(300, 60).center();
		table.row();
			
		
	}*/
	public void createMainMenu() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		table.clear();
		table.add("Crazy Miner").center();
		table.row();
		
		TextButton newGameButton = new TextButton("New Game", getSkin());
        newGameButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		showWorlds(true);
        	}
        });
        table.add(newGameButton).size(width / 2, height / 5).center();
        table.row();
        TextButton loadGameButton = new TextButton("Load Game", getSkin());
        loadGameButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		showWorlds(false);
        	}
        });
        table.add(loadGameButton).size(width / 2, height / 5).center();
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
