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
	final TextButton fullRefuel;
	final TextButton fuelSize;
	final TextButton engine;
	final TextButton drill;
	final TextButton exit;
	final TextButton fuelSizes;
	final TextButton upgrades;
	final TextButton back;
	final TextButton refuel5;
	final TextButton refuel10;
	final TextButton refuel25;
	final TextButton refuel50;

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
        exit = new TextButton("Exit", renderer.getSkin());
        exit.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		renderer.addTouchpad();
        		renderer.hideShop();
        		background.remove();
        		exit.clearActions();
        		
        	}
        });
        
        fullRefuel = new TextButton("Full Refuel Cost: " + ((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()), renderer.getSkin());
        fullRefuel.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		if(((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()) < miner.getMoney()) {
        			miner.setMoney(miner.getMoney() - ((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()));
        			miner.reEnergize();
        		}
        		fullRefuel.setText("Full Refuel Cost: " + ((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()));
        	}
        });
        final TextButton armor = new TextButton("Upgrade Armor ", renderer.getSkin());
        armor.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeArmor();
        	}
        });
        fuelSize = new TextButton("Increase Fuel Capacity \n " + "Lvl: " + miner.getFuelUpgrade() + " Cost: " + miner.getFuelUpgrade() * 1000, renderer.getSkin());
        fuelSize.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeFuel( miner.getFuelUpgrade() * 1000);
        		fuelSize.setText("Increase Fuel Capacity \n " + "Lvl: " + miner.getFuelUpgrade() + " Cost: " + miner.getFuelUpgrade() * 1000);
        	}
        });
        engine = new TextButton("Upgrade Engine \n " + "Lvl: " + miner.getEngineUpgrade() + " Cost: " + miner.getEngineUpgrade() * 5000, renderer.getSkin());
        engine.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeEngine(miner.getEngineUpgrade() * 5000);
        		engine.setText("Upgrade Engine \n " + "Lvl: " + miner.getEngineUpgrade() + " Cost: " + miner.getEngineUpgrade() * 5000);
        	}
        });
        drill = new TextButton("Upgrade Drill Strength \n " + "Lvl: " + miner.getDrillUpgrade() + " Cost: " + miner.getDrillUpgrade() * 2000, renderer.getSkin());
        drill.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		miner.upgradeDrill(miner.getDrillUpgrade() * 2000);
        		drill.setText("Upgrade Drill Strength \n " + "Lvl: " + miner.getDrillUpgrade() + " Cost: " + miner.getDrillUpgrade() * 2000);
        	}
        });
        upgrades = new TextButton("Upgrades", renderer.getSkin());
        upgrades.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		showUpgrades();
        	}
        });
        fuelSizes = new TextButton("Refuel", renderer.getSkin());
        fuelSizes.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		showRefuel();
        	}
        });
        back = new TextButton("Back", renderer.getSkin());
        back.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		showMainShop();
        	}
        });
        refuel5 = new TextButton("Refuel 5", renderer.getSkin());
        refuel5.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		if(miner.getMoney() > 5) {
        			miner.reEnergize(5);
        			miner.setMoney(miner.getMoney() - 5);
        		}
        	}
        });
       
        refuel10 = new TextButton("Refuel 10", renderer.getSkin());
        refuel10.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		if(miner.getMoney() > 10) {
        			miner.reEnergize(10);
        			miner.setMoney(miner.getMoney() - 10);
        		}
        	}
        });
        refuel25 = new TextButton("Refuel 25", renderer.getSkin());
        refuel25.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		if(miner.getMoney() > 25) {
        			miner.reEnergize(25);
        			miner.setMoney(miner.getMoney() - 25);
        		}
        	}
        });
        refuel50 = new TextButton("Refuel 50", renderer.getSkin());
        refuel50.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		if(miner.getMoney() > 50) {
        			miner.reEnergize(50);
        			miner.setMoney(miner.getMoney() - 50);
        		}
        	}
        });
        
       showMainShop();
        

    }
	
	@Override
	public void act(float delta) {
		fullRefuel.setText("Refuel Cost: " + ((100 + miner.getFuelUpgrade()*10) -  miner.getEnergy()));
	}
	public void showMainShop() {
			table.clear();
			table.defaults().padBottom(5).width(width / 2).height(height / 7);
	        table.add("Money: " + miner.getMoney());
	        table.row();
	       // table.add(armor);
	       // table.row();
	       // table.add(engine);
	        //table.row();
	        //table.add(fuelSize);
	        //table.row();
	        //table.add(drill);
	        //table.row();
	        //table.add(fullRefuel);
	        table.add(upgrades);
	        table.row();
	        table.add(fuelSizes);
	        table.row();
	        table.add(exit);
	        table.row();
	      
	}
	public void showUpgrades() {
		table.clear();
		table.defaults().padBottom(5).width(width / 2).height(height / 7);
		table.add("Money: " + miner.getMoney());
        table.row();
		table.add(engine);
		table.row();
		table.add(drill);
		table.row();
		table.add(fuelSize);
		table.row();
		table.add(back);
		table.row();
		table.add(exit);
	}
	public void showRefuel() {
		table.clear();
		table.defaults().padBottom(5).width(width / 2).height(height / 9);
		table.add("Money: " + miner.getMoney());
		table.row();
		table.add(refuel5);
		table.row();
		table.add(refuel10);
		table.row();
		table.add(refuel25);
		table.row();
		table.add(refuel50);
		table.row();
		table.add(fullRefuel);
		table.row();
		table.add(back);
		table.row();
		table.add(exit);
	}





}
