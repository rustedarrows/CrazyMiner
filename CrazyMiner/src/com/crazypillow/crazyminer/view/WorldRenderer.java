package com.crazypillow.crazyminer.view;

import com.crazypillow.crazyminer.model.Block;
import com.crazypillow.crazyminer.model.Miner;
import com.crazypillow.crazyminer.model.Miner.State;
import com.crazypillow.crazyminer.model.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	
	private World world;
	private OrthographicCamera cam;

	private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    
    private Stage stage;

	/** Textures **/
	private TextureRegion minerFrame;
	private TextureRegion minerLeft;
	private TextureRegion minerRight;
	private TextureRegion minerUp;
	private TextureRegion minerDown;
	private TextureRegion blockTexture;
	
	
	private SpriteBatch spriteBatch;
	private int width;
	private int height;
	
	Miner miner;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		//this.cam.setToOrtho(false, width, height);
	}

	public WorldRenderer(World world) {
		this.world = world;
		this.miner = this.world.getMiner();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		loadTextures();
	}
	
	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		minerRight = atlas.findRegion("miner-right");
		minerLeft = atlas.findRegion("miner-left");
		minerUp = atlas.findRegion("miner-up");
		minerDown = atlas.findRegion("miner-down");
		blockTexture = atlas.findRegion("block");
		
		//Create a touchpad skin    
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 64, 64);
 
        //Create a Stage and add TouchPad

        stage.addActor(touchpad);            
        Gdx.input.setInputProcessor(stage);
		
	}
	
	
	public void render(float delta) {
		stage.act(delta);
		miner.setXPerc(touchpad.getKnobPercentX());
		miner.setYPerc(touchpad.getKnobPercentY());
		moveCamera(miner.getPosition().x, miner.getPosition().y);
		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
			drawBlocks();
			drawMiner();
		spriteBatch.end();
		stage.draw();
	}
	public void moveCamera(float x,float y){
	        cam.position.set(x, y, 0);
	        cam.update();

	}
	
	

	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			spriteBatch.draw(blockTexture, block.getPosition().x, block.getPosition().y, Block.SIZE, Block.SIZE);
		}
	}

	private void drawMiner() {
		minerFrame = minerLeft;
		if(miner.getState().equals(State.LEFT)) {
			minerFrame = minerLeft;
		} else if (miner.getState().equals(State.RIGHT)) {
			minerFrame = minerRight;
		}else if (miner.getState().equals(State.UP)) {
			minerFrame = minerUp;
		}else if (miner.getState().equals(State.DOWN)) {
			minerFrame = minerDown;
		}
		spriteBatch.draw(minerFrame, miner.getPosition().x , miner.getPosition().y , Miner.SIZE , Miner.SIZE );
	}

	

	
	
	
}
