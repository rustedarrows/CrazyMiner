package com.crazypillow.crazyminer.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crazypillow.crazyminer.model.World;
import com.crazypillow.crazyminer.screens.MenuScreen;
import com.crazypillow.crazyminer.view.WorldRenderer;

public class Dead extends Actor{

	private BitmapFont font;
	private World world;
	private WorldRenderer renderer;
	private Table table;


	public Dead(final World world, final WorldRenderer renderer){
		this.renderer = renderer;
    	this.world = world;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        this.table = renderer.getTable();
        table.clear();
		table.add("You Died").center();
        table.row();
        
        final TextButton startOver = new TextButton("Start Over", renderer.getSkin());
        startOver.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		renderer.game.setScreen(new MenuScreen(renderer.game));
        		startOver.clearActions();
        	}
        });
        table.add(startOver).size(300, 60).center();

    }
	





}
