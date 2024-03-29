package com.crazypillow.crazyminer;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CrazyMiner";
		cfg.useGL20 = false;
		cfg.width = 480; //480
		cfg.height = 320; //320
		
		new LwjglApplication(new CrazyMiner(), cfg);
	}
}
