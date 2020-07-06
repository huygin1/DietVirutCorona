package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Font;

public class VirutCon extends Enemy {
	
	public BufferedImage[] sprites;
	private Player player;
	private boolean active;
	
	public VirutCon(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 10;
		
		width = 100;
		height = 100;
		cwidth = 90;
		cheight = 88;
		
		damage = 1;
		moveSpeed = 1;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		
		sprites = Content.VirutCon[0];
		
		animation.setFrames(sprites);
		animation.setDelay(8);
		
		left = true;
		facingRight = false;
		
	}
	
	private void getNextPosition() {
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
	}
	
	public void update() {
		
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
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
