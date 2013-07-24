package com.crazypillow.crazyminer.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.utils.XmlReader.Element;


public class World {

	/** Our player controlled hero **/
	Miner miner;
	/** A world has a level through which Bob needs to go through **/
	Level level;
	
	FileHandle handle;
	
	private int width = 50;
	private int height = 50;
	private Block[][] blocks;
	
	//---------Used for Map Creating and generation
	private int x, y, energy, money, armor;
	private int durability, price;
	private String type;
	private BlockType blockType;
	//---------END------
	
	/** The collision boxes **/
	Array<Rectangle> collisionRects = new Array<Rectangle>();

	// Debug
	int count;
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}
	public Miner getMiner() {
		return miner;
	}
	/** Return only the blocks that need to be drawn 
	 * @author Tim Christensen
	 * @return the blocks that should be drawn
	 * @param width of the viewport, height of the viewport
	 * **/
	public List<Block> getDrawableBlocks(int width, int height) {
		int x = (int)miner.getPosition().x;
		int y = (int)miner.getPosition().y;
		
		int lowX = x - width;
		int highX = x + width;
		int lowY = y - height;
		int highY = y + height;
		
		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for (int col = lowX; col < highX; col++) {
			for (int row = lowY; row <= highY; row++) {
				if(col < 0 || col > getWidth()-1) {
					
				}else if(row < 0 || row > getHeight()-1) {
					
				}else {
					block = getBlocks()[col][row];
					if (block != null) {
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}

	// --------------------
	public World(FileHandle handle) {
		this.handle = handle;
		if(handle.exists()) {
			loadWorld(handle);
		}else {
			try {
				System.out.println("creating the file");
				handle.file().createNewFile(); //Creates new File
			} catch (IOException e) {
				e.printStackTrace();
			}
			createWorld(handle);
		}
		//createDemoWorld();
	}

	/**
	 * Loads world from specified file
	 * @param handle location of the world
	 */
	public void loadWorld(FileHandle handle) {
		XmlReader xml = new XmlReader();
		try {
			XmlReader.Element xml_element = xml.parse(handle);
			Element e = xml_element.getChildByName("width");
			width = Integer.parseInt(e.getText());
			e = xml_element.getChildByName("height");
			height = Integer.parseInt(e.getText());
			blocks = new Block[width][height];
			//-----LOADS PLAYER-----
			e = xml_element.getChildByName("player");
			Element e2 = e.getChildByName("x");
			x = Integer.parseInt(e2.getText());
			e2 = e.getChildByName("y");
			y = Integer.parseInt(e2.getText());
			e2 = e.getChildByName("energy");
			energy = Integer.parseInt(e2.getText());
			e2 = e.getChildByName("money");
			money = Integer.parseInt(e2.getText());
			e2 = e.getChildByName("armor");
			armor = Integer.parseInt(e2.getText());
			miner = new Miner(new Vector2(x, y), energy, money, armor);
			//------END LOAD PLAYER-------
			
			Iterator<Element> iterator_block = xml_element.getChildrenByName("block").iterator();
			while(iterator_block.hasNext()) {
				XmlReader.Element block = (XmlReader.Element)iterator_block.next();
				type = block.getChildByName("type").getText();
				if(type.equals("bronze")) {
					blockType = BlockType.BRONZE;
				}else if(type.equals("iron")) {
					blockType = BlockType.IRON;

				}else if(type.equals("silver")) {
					blockType = BlockType.SILVER;

				}else if(type.equals("gold")) {
					blockType = BlockType.GOLD;

				}else if(type.equals("diamond")) {
					blockType = BlockType.DIAMOND;

				}else {
					blockType = BlockType.DIRT;
				}
				durability = Integer.parseInt(block.getChildByName("durability").getText());
				price = Integer.parseInt(block.getChildByName("price").getText());
				Iterator<Element> iterator_location = block.getChildrenByName("location").iterator();
				while(iterator_location.hasNext()) {
					Element position = (Element)iterator_location.next();
					x = Integer.parseInt(position.getAttribute("x"));
					y = Integer.parseInt(position.getAttribute("y"));
					blocks[x][y] = new Block(new Vector2(x, -y), blockType, durability, price);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates a new world with default size
	 * @param handle file to write the world to
	 **/
	public void createWorld(FileHandle handle) {
		miner = new Miner(new Vector2(10, 27));
		blocks = new Block[width][height];
		for(int col = 0; col < width; col++) {
			for(int row = 0; row < height; row++) {
				int i = MathUtils.random(4);
				
				//TODO: Change this to not have so much work needed.
				switch(i) {
				case 0: //Dirt
					blocks[col][row] = new Block(new Vector2(col, -row), BlockType.DIRT, BlockType.DIRT.getDurability(), BlockType.DIRT.getValue());
					break;
				case 1: //Bronze
					blocks[col][row] = new Block(new Vector2(col, -row), BlockType.BRONZE, BlockType.BRONZE.getDurability(), BlockType.BRONZE.getValue());
					break;
				case 2: //Silver
					blocks[col][row] = new Block(new Vector2(col, -row), BlockType.SILVER, BlockType.SILVER.getDurability(), BlockType.SILVER.getValue());
					break;
				case 3: //Gold
					blocks[col][row] = new Block(new Vector2(col, -row), BlockType.GOLD, BlockType.GOLD.getDurability(), BlockType.GOLD.getValue());
					break;
				case 4: //Diamond
					blocks[col][row] = new Block(new Vector2(col, -row), BlockType.DIAMOND, BlockType.DIAMOND.getDurability(), BlockType.DIAMOND.getValue());
					break;
				default: //Dirt
					blocks[col][row] = new Block(new Vector2(col, -row), BlockType.DIRT, BlockType.DIRT.getDurability(), BlockType.DIRT.getValue());
					break;

				}
			}
		}
	}
	
	/**
	 * Saves the current state of the world into the xml file specified
	 * 
	 * @param handle file to save to (Will overwrite everything)
	 */
	public void saveWorld(FileHandle handle) {
		StringWriter writer = new StringWriter();
		XmlWriter xml = new XmlWriter(writer);
		try {
			xml.element("world")
				 .element("width")
					.text(width)
				.pop()
				.element("height")
					.text(height)
				.pop()
				.element("player")
					.element("x")
						.text((int)miner.position.x)
					.pop()
					.element("y")
						.text((int)miner.position.y)
					.pop()
					.element("energy")
						.text(miner.energy)
					.pop()
					.element("armor")
						.text(miner.armor)
					.pop()
					.element("money")
						.text(miner.money)
					.pop()
				.pop();
				//Dirt Block
				xml.element("block")
					.element("type")
						.text("dirt")
					.pop()
					.element("durability")
						.text(BlockType.DIRT.getDurability())
					.pop()
					.element("price")
						.text(BlockType.DIRT.getValue())
					.pop();
					for(int col = 0; col < width; col++) {
						for(int row = 0; row < height; row++) {
							if(blocks[col][row] == null) {
								
							}else if (blocks[col][row].getType().equals(BlockType.DIRT)) {
								
								xml.element("location")
									.attribute("x", col)
									.attribute("y", row)
								.pop();
							}
						}
					}
				xml.pop();
				//Bronze Block
				xml.element("block")
					.element("type")
						.text("bronze")
					.pop()
					.element("durability")
						.text(BlockType.BRONZE.getDurability())
					.pop()
					.element("price")
						.text(BlockType.BRONZE.getValue())
					.pop();
				for(int col = 0; col < width; col++) {
					for(int row = 0; row < height; row++) {
						if(blocks[col][row] == null) {
							
						}else if(blocks[col][row].getType().equals(BlockType.BRONZE)) {
							xml.element("location")
								.attribute("x", col)
								.attribute("y", row)
							.pop();
						}
					}
				}
				xml.pop();
				//Silver Block
				xml.element("block")
					.element("type")
						.text("silver")
					.pop()
					.element("durability")
						.text(BlockType.SILVER.getDurability())
					.pop()
					.element("price")
						.text(BlockType.SILVER.getValue())
					.pop();
				for(int col = 0; col < width; col++) {
					for(int row = 0; row < height; row++) {
						if(blocks[col][row] == null) {
							
						}else if(blocks[col][row].getType().equals(BlockType.SILVER)) {
							xml.element("location")
								.attribute("x", col)
								.attribute("y", row)
							.pop();
						}
					}
				}
				xml.pop();
				//Gold Block
				xml.element("block")
					.element("type")
						.text("gold")
					.pop()
					.element("durability")
						.text(BlockType.GOLD.getDurability())
					.pop()
					.element("price")
						.text(BlockType.GOLD.getValue())
					.pop();
				for(int col = 0; col < width; col++) {
					for(int row = 0; row < height; row++) {
						if(blocks[col][row] == null) {
							
						}else if(blocks[col][row].getType().equals(BlockType.GOLD)) {
							xml.element("location")
								.attribute("x", col)
								.attribute("y", row)
							.pop();
						}
					}
				}
				xml.pop();
				//Diamond Block
				xml.element("block")
					.element("type")
						.text("diamond")
					.pop()
					.element("durability")
						.text(BlockType.DIAMOND.getDurability())
					.pop()
					.element("price")
						.text(BlockType.DIAMOND.getValue())
					.pop();
				for(int col = 0; col < width; col++) {
					for(int row = 0; row < height; row++) {
						if(blocks[col][row] == null) {
							
						}else if(blocks[col][row].getType().equals(BlockType.DIAMOND)) {
							xml.element("location")
								.attribute("x", col)
								.attribute("y", row)
							.pop();
						}
					}
				}
				xml.pop();
				
			xml.pop();
			
			handle.writeString(writer.toString(), false);
			System.out.println("Saved world");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates a block of 50 x 50 blocks and expands the array accordingly
	 * Note: Verrrrrry Verrrrry Slow way of doing this, probably a faster way
	 */
	public void createBlock() {
		int tempX = width + 50;
		int tempY = height + 50;
		Block[][] newBlocks = new Block[tempX][tempY];
		for(int row = 0; row < width; row++) {
			for(int col = 0; col < height; col++) {
				newBlocks[row][col] = blocks[row][col];
			}
		}
		for(int row = width; row < tempX; row++) {
			for(int col = height; col < tempY; col++) {
				int i = MathUtils.random(4);
				//TODO: Change this to not have so much work needed.
				switch(i) {
				case 0: //Dirt
					newBlocks[col][row] = new Block(new Vector2(col, -row), BlockType.DIRT, BlockType.DIRT.getDurability(), BlockType.DIRT.getValue());
					break;
				case 1: //Bronze
					newBlocks[col][row] = new Block(new Vector2(col, -row), BlockType.BRONZE, BlockType.BRONZE.getDurability(), BlockType.BRONZE.getValue());
					break;
				case 2: //Silver
					newBlocks[col][row] = new Block(new Vector2(col, -row), BlockType.SILVER, BlockType.SILVER.getDurability(), BlockType.SILVER.getValue());
					break;
				case 3: //Gold
					newBlocks[col][row] = new Block(new Vector2(col, -row), BlockType.GOLD, BlockType.GOLD.getDurability(), BlockType.GOLD.getValue());
					break;
				case 4: //Diamond
					newBlocks[col][row] = new Block(new Vector2(col, -row), BlockType.DIAMOND, BlockType.DIAMOND.getDurability(), BlockType.DIAMOND.getValue());
					break;
				default: //Dirt
					newBlocks[col][row] = new Block(new Vector2(col, -row), BlockType.DIRT, BlockType.DIRT.getDurability(), BlockType.DIRT.getValue());
					break;
				}
			}
		}
		blocks = new Block[tempX][tempY];
		blocks = newBlocks;
		width = tempX;
		height = tempY;
	}
	
	
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
	public Block get(int x, int y) {
		return blocks[x][y];
	}
	public void setNull(int x, int y) {
		blocks[x][y] = null;
	}
	
}
