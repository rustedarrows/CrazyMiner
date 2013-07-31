package com.crazypillow.crazyminer.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.crazypillow.crazyminer.model.Miner;

public class HUD extends Actor {

    BitmapFont font;
    Miner miner;

    public HUD(Miner miner){
    	this.miner = miner;
        font = new BitmapFont();
            font.setColor(Color.WHITE);   
    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
    	int height = Gdx.graphics.getHeight();
    	int width = Gdx.graphics.getWidth();
    	//TODO: Make the HUD look nicer
         font.draw(batch, "Money: " + miner.getMoney(), width - (width - 10), height - 10);
         font.draw(batch, "Energy: " + miner.getEnergy(), width / 2, height - 10);
         
    }

 

}