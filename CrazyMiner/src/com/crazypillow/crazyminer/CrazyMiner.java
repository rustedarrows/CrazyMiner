package com.crazypillow.crazyminer;


import com.badlogic.gdx.Game;
import com.crazypillow.crazyminer.screens.GameScreen;
import com.crazypillow.crazyminer.screens.MenuScreen;

public class CrazyMiner extends Game {

	@Override
	public void create() {		
		setScreen(new MenuScreen(this));
	}


}
