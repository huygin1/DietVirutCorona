package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Entity.Enemy;
import Handlers.Content;
import TileMap.TileMap;


public class VirutBay extends Enemy {
	
	private BufferedImage[] idleSprites;
	
	private int tick;
	private double a;
	private double b;
	
	public VirutBay(TileMap tm) {
		
		super(tm);
		
		health = maxHealth = 25;
		
		width = 100;
		height = 100;
		cwidth = 90;
		cheight = 90;
		
		damage = 1;
		moveSpeed = 5;
		
		idleSprites = Content.VirutBay[0];
		
		animation.setFrames(idleSprites);
		animation.setDelay(8);
		
		tick = 0;
		a = Math.random() * 0.16 + 0.07;
		b = Math.random() * 0.16 + 0.07;
		
	}
	
	public void update() {
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		tick++;
		x = Math.sin(a * tick) + x;
		y = Math.sin(b * tick) + y;
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}
	
}
