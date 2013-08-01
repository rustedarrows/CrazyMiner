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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crazypillow.crazyminer.model.World;
import com.crazypillow.crazyminer.screens.MenuScreen;
import com.crazypillow.crazyminer.view.WorldRenderer;

public class Shop extends Actor{

	private BitmapFont font;
	private WorldRenderer renderer;
	private Table table;
	private Texture shopBackgroundTexture;
	private Image background;


	public Shop(final WorldRenderer renderer){
		this.renderer = renderer;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        this.table = renderer.getTable();
        table.setColor(Color.GREEN);
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
        table.add(exit).size(60, 60);

    }





}
