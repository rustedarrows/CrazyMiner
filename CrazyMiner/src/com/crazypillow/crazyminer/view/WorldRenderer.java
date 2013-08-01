package com.crazypillow.crazyminer.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.crazypillow.crazyminer.actors.Dead;
import com.crazypillow.crazyminer.actors.HUD;
import com.crazypillow.crazyminer.actors.Shop;
import com.crazypillow.crazyminer.actors.ShopButton;
import com.crazypillow.crazyminer.model.Block;
import com.crazypillow.crazyminer.model.Miner;
import com.crazypillow.crazyminer.model.World;

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
    private Skin skin;
    private Table table;
    BitmapFont font = new BitmapFont();
    public Game game;
    
    
    private Texture shop;
    Sprite shopSprite;
    
    private Actor shopButton;
    private Actor shopActor;
    private Actor deadButton;
	private Stage stage;

	/** Textures **/
	private TextureRegion minerFrame;
	private TextureRegion minerLeft;
	private TextureRegion minerRight;
	private TextureRegion minerUp;
	private TextureRegion minerDown;
	private TextureRegion blockTexture;
	private TextureRegion dirtBlock;
	private TextureRegion bronzeBlock;
	private TextureRegion silverBlock;
	private TextureRegion goldBlock;
	private TextureRegion diamondBlock;
	
	private SpriteBatch spriteBatch;

	private int width;
	private int height;
	
	Miner miner;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		//this.cam.setToOrtho(false, width, height);
	}

	public WorldRenderer(World world, Game game) {
		this.game = game;
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
		dirtBlock = atlas.findRegion("block");
		bronzeBlock = atlas.findRegion("block-bronze");
		silverBlock = atlas.findRegion("block-silver");
		goldBlock = atlas.findRegion("block-gold");
		diamondBlock = atlas.findRegion("block-diamond");
		
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
        touchpad = new Touchpad(5, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 128, 128);

        //Create a Stage and add TouchPad
        stage.addActor(touchpad);   
        stage.addActor(new HUD(this.miner));
        
        shop = new Texture(Gdx.files.internal("store.png"));
        shopSprite = new Sprite(shop);
        shopSprite.setBounds(15, 1000, 4f, 4f);
        Gdx.input.setInputProcessor(stage);
		
	}
	
	
	public void render(float delta) {
		if(miner.isDead() && !stage.getActors().contains(deadButton, true)) {
			deadButton = new Dead(world, this);
			stage.addActor(deadButton);
			touchpad.remove();
		}
		stage.act(delta);
		miner.setXPerc(touchpad.getKnobPercentX());
		miner.setYPerc(touchpad.getKnobPercentY());
		if(miner.getPosition().x < 10) {
			moveCamera(10, miner.getPosition().y);
		}else if(miner.getPosition().x > 40) {
			moveCamera(40, miner.getPosition().y);	
		}else {
			moveCamera(miner.getPosition().x, miner.getPosition().y);
		}
		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
			drawShops();
			drawBlocks();
			drawMiner();
		spriteBatch.end();
		stage.draw();
	}
	public Sprite getShopSprite() {
		return shopSprite;
	}

	public static float getCameraWidth() {
		return CAMERA_WIDTH;
	}

	public static float getCameraHeight() {
		return CAMERA_HEIGHT;
	}

	public Stage getStage() {
		return stage;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	private void drawShops() {
		shopSprite.draw(spriteBatch);
		
	}

	public void moveCamera(float x,float y){
	        cam.position.set(x, y, 0);
	        cam.update();

	}
	
	

	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
				blockTexture = dirtBlock;
				switch(block.getType()) {
				case DIRT:
					blockTexture = dirtBlock;
					break;
				case BRONZE:
					blockTexture = bronzeBlock;
					break;
				case SILVER:
					blockTexture = silverBlock;
					break;
				case GOLD:
					blockTexture = goldBlock;
					break;
				case DIAMOND:
					blockTexture = diamondBlock;
					break;
				default:
					blockTexture = dirtBlock;
					break;
					
				}
				spriteBatch.draw(blockTexture, block.getPosition().x, block.getPosition().y, Block.SIZE, Block.SIZE);
		}
	}

	private void drawMiner() {
		minerFrame = minerLeft;
		/*if(miner.getState().equals(State.LEFT) || miner.getState().equals(State.IDLE)) {
			minerFrame = minerLeft;
		} else if (miner.getState().equals(State.RIGHT)) {
			minerFrame = minerRight;
		}else if (miner.getState().equals(State.UP)) {
			minerFrame = minerUp;
		}else if (miner.getState().equals(State.DOWN)) {
			minerFrame = minerDown;
		}*/
		float x = miner.getXPerc();
		float y = miner.getYPerc();
		if(x == 0 && y == 0) {
			minerFrame = minerRight;
		}
		if(y > 0) {
			minerFrame = minerRight;
		}else {
			minerFrame = minerLeft;
		}
		if(x > 0.20) {
			minerFrame = minerUp;
		}else if(x < -0.20) {
			minerFrame = minerDown;
		}
		
		spriteBatch.draw(minerFrame, miner.getPosition().x , miner.getPosition().y , Miner.SIZE , Miner.SIZE );
	}
    public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }

    public Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }

    public Table getTable()
    {
        if( table == null ) {
            table = new Table( getSkin() );
            table.setFillParent( true );
            stage.addActor( table );
        }
        return table;
    }

    
    /**
     * Shows the Shop Screen
     */
	public void showShop() {
		if(!stage.getActors().contains(shopActor, false)) {
			shopActor = new Shop(this);
			stage.addActor(shopActor);
		}
		
	}
	/**
	 * Hides Shop Screen
	 */
	public void hideShop() {
		if(stage.getActors().contains(shopButton, false)) {
			shopActor.remove();
			table.clear();
		}
	}
	
	/**
	 * Shows the Shop Screen button
	 */
	public void showShopSign() {
			if(!stage.getActors().contains(shopButton, false)) {
				shopButton = new ShopButton(this);
				stage.addActor(shopButton);
		}
	}
	/**
	 * Hides the Shop Screen Button
	 */
	public void hideShopSign() {
		if(stage.getActors().contains(shopButton, false)) {
			shopButton.remove();
			table.clear();
		}
		
	}
	/**
	 * Removes touchpad from stage
	 */
	public void removeTouchpad() {
		touchpad.remove();
	}
	/**
	 * Adds touchpad to Stage
	 */
	public void addTouchpad() {
		stage.addActor(touchpad);
	}
		


	
}
