package com.crazypillow.crazyminer;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.crazypillow.crazyminer.screens.GameScreen;
import com.crazypillow.crazyminer.screens.MenuScreen;

public class CrazyMiner extends Game {
	public Screen menu; 
	@Override
	public void create() {		
		menu = new MenuScreen(this);
		Gdx.input.setCatchBackKey(true);
		setScreen(menu);
	}
	public void showMainMenu() {
		setScreen(menu);
	}


}
