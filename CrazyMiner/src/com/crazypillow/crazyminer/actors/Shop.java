package com.crazypillow.crazyminer.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crazypillow.crazyminer.model.Miner;
import com.crazypillow.crazyminer.model.World;
import com.crazypillow.crazyminer.screens.MenuScreen;
import com.crazypillow.crazyminer.view.WorldRenderer;

public class Shop extends Actor{

	private BitmapFont font;
	private WorldRenderer renderer;
	private Table table;
	private Texture shopBackgroundTexture;
	private Image background;
	private Miner miner;
	float width, height;

	public Shop(final WorldRenderer renderer, final Miner miner){
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.miner = miner;
		this.renderer = renderer;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        this.table = renderer.getTable();
        table.clear();
        shopBackgroundTexture = new Texture(Gdx.files.internal("storeBackground.png"));
        background = new Image(shopBackgroundTexture);
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer.getStage().addActor(background);
        background.toBack();
        renderer.removeTouchpad();
        final TextButton exit = new TextButton("Exit", renderer.getSkin());
        exit.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		renderer.addTouchpad();
        		renderer.hideShop();
        		background.remove();
        		exit.clearActions();
        		
        	}
        });
        
        final TextButton refuel = new TextButton("Refuel Cost: " + ((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()), renderer.getSkin());
        refuel.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.reEnergize();
        		refuel.setText("Refuel Cost: " + ((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()));
        	}
        });
        final TextButton armor = new TextButton("Upgrade Armor ", renderer.getSkin());
        armor.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeArmor();
        	}
        });
        final TextButton fuelSize = new TextButton("Increase Fuel Capacity \n " + "Lvl: " + miner.getFuelUpgrade() + " Cost: " + miner.getFuelUpgrade() * 1000, renderer.getSkin());
        fuelSize.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeFuel();
        		fuelSize.setText("Increase Fuel Capacity \n " + "Lvl: " + miner.getFuelUpgrade() + " Cost: " + miner.getFuelUpgrade() * 1000);
        	}
        });
        final TextButton engine = new TextButton("Upgrade Engine \n " + "Lvl: " + miner.getEngineUpgrade() + " Cost: " + miner.getEngineUpgrade() * 5000, renderer.getSkin());
        engine.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeEngine();
        		engine.setText("Upgrade Engine \n " + "Lvl: " + miner.getEngineUpgrade() + " Cost: " + miner.getEngineUpgrade() * 5000);
        	}
        });
        final TextButton drill = new TextButton("Upgrade Drill Strength \n " + "Lvl: " + miner.getDrillUpgrade() + " Cost: " + miner.getDrillUpgrade() * 2000, renderer.getSkin());
        drill.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeDrill();
        		drill.setText("Upgrade Drill Strength \n " + "Lvl: " + miner.getDrillUpgrade() + " Cost: " + miner.getDrillUpgrade() * 2000);
        	}
        });
        table.defaults().padBottom(5).width(width / 2);
        table.add("Money: " + miner.getMoney());
        table.row();
       // table.add(armor);
       // table.row();
        table.add(engine);
        table.row();
        table.add(fuelSize);
        table.row();
        table.add(drill);
        table.row();
        table.add(refuel);
        table.row();
        table.add(exit);
        table.row();
      
        

    }





}
