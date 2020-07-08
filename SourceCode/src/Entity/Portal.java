package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Portal extends MapObject {
	
	private BufferedImage[] closedSprites;
	private BufferedImage[] openingSprites;
	private BufferedImage[] openedSprites;
	
	private boolean opened;
	private boolean opening;
	
	public Portal(TileMap tm) {
		
		super(tm);
		
		width = 536;
		height = 453;
		
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Other/wuhan.png")
			);
			
			closedSprites = new BufferedImage[1];
			closedSprites[0] = spritesheet.getSubimage(0, 0, width, height);
			
			openingSprites = new BufferedImage[2];
			openingSprites[0] = spritesheet.getSubimage(0, 0, width, height);
			openingSprites[1] = spritesheet.getSubimage(width, 0, width, height);
			openedSprites = new BufferedImage[2];
			openedSprites[0] = spritesheet.getSubimage(0, 0, width, height);
			openedSprites[1] = spritesheet.getSubimage(width, 0, width, height);
			
			animation.setFrames(closedSprites);
			animation.setDelay(-1);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setClosed() {
		animation.setFrames(closedSprites);
		animation.setDelay(-1);
		opened = false;
	}
	
	public void setOpening() {
		opening = true;
		animation.setFrames(openingSprites);
		animation.setDelay(200);
	}
	
	public void setOpened() {
		if(opened) return;
		animation.setFrames(openedSprites);
		animation.setDelay(200);
		opened = true;
	}
	
	public boolean isOpened() { return opened; }
	
	public void update() {
		animation.update();
		if(opening && animation.hasPlayedOnce()) {
			opened = true;
			animation.setFrames(openedSprites);
			animation.setDelay(100);
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}