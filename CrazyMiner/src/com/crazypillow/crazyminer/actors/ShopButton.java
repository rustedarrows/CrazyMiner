package com.crazypillow.crazyminer.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crazypillow.crazyminer.model.Miner;
import com.crazypillow.crazyminer.view.WorldRenderer;

public class ShopButton extends Actor{

	private BitmapFont font;
	private WorldRenderer renderer;
	private Table table;


	public ShopButton(final WorldRenderer renderer){
		this.renderer = renderer;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        this.table = renderer.getTable();
        table.clear();
        final TextButton shopButton = new TextButton("Shop", renderer.getSkin());
        shopButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		renderer.showShop();
        		shopButton.clearActions();
        	}
        });
        table.add(shopButton).size(Gdx.graphics.getWidth() / 5, Gdx.graphics.getWidth() / 5).center();

    }
}