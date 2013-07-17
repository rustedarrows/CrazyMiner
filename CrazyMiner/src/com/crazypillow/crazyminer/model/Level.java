package com.crazypillow.crazyminer.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlWriter;

public class Level {

	private int width;
	private int height;
	private Block[][] blocks;
	BufferedReader reader;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public Level() {
		writeLevel();
		loadDemoLevel();
	}
	
	private void writeLevel() {
		boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
		if(isLocAvailable) {
			String locRoot = Gdx.files.getLocalStoragePath();
		}
		width = 50;
		height = 25;
		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		try {
			xml.element("world")
				 .element("width")
					.text("50")
				.pop()
				.element("height")
					.text("25")
				.pop()
				.element("player")
					.element("x")
						.text("10")
					.pop()
					.element("y")
						.text("25")
					.pop()
					.element("energy")
						.text("100")
					.pop()
					.element("money")
						.text("500")
					.pop()
				.pop()
				
				.element("block")
					.element("type")
						.text("dirt")
					.pop()
					.element("durability")
						.text("50")
					.pop()
					.element("price")
						.text("500")
					.pop();
					for(int col = 0; col < width; col++) {
						for(int row = 0; row < height; row++) {
							xml.element("location");
							xml.attribute("x", col);
							xml.attribute("y", row);
							xml.pop();
						}
					}
					
				xml.pop();
			xml.pop();
			FileHandle handle = Gdx.files.local("world.xml");
			
			if(handle.exists()) {
				handle.writeString(writer.toString(), false);
			}else {
				handle.file().createNewFile();
				handle.writeString(writer.toString(), false);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Block get(int x, int y) {
		return blocks[x][y];
	}
	public void setNull(int x, int y) {
		blocks[x][y] = null;
	}

	private void loadDemoLevel() {
		boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
		if(isLocAvailable) {
			String locRoot = Gdx.files.getLocalStoragePath();
		}
		width = 50;
		height = 25;
		blocks = new Block[width][height];
		
		
		
		for(int col = 0; col < width; col++) {
			for(int row = 0; row < height; row++) {
				blocks[col][row] = new Block(new Vector2(col, row));
			}
		}
		/*for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}
		
		for (int col = 0; col < 10; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
			blocks[col][6] = new Block(new Vector2(col, 6));
			if (col > 2) {
				blocks[col][1] = new Block(new Vector2(col, 1));
			}
		}
		blocks[9][2] = new Block(new Vector2(9, 2));
		blocks[9][3] = new Block(new Vector2(9, 3));
		blocks[9][4] = new Block(new Vector2(9, 4));
		blocks[9][5] = new Block(new Vector2(9, 5));

		blocks[6][3] = new Block(new Vector2(6, 3));
		blocks[6][4] = new Block(new Vector2(6, 4));
		blocks[6][5] = new Block(new Vector2(6, 5));
		*/
		
	}
}
