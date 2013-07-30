package com.crazypillow.crazyminer.actors;

import com.badlogic.gdx.Gdx;
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
            font.setColor(255,255,255,1);   
    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
    	int height = Gdx.graphics.getHeight();
    	int width = Gdx.graphics.getWidth();
    	
         font.draw(batch, "" + miner.getMoney(), width - (width - 10), height - 10);
         font.draw(batch, "" + miner.getEnergy(), width / 2, height - 10);
         //Also remember that an actor uses local coordinates for drawing within
         //itself!
    }

 

}