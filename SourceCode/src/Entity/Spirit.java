package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemies.DarkEnergy;
import TileMap.TileMap;

public class Spirit extends Enemy {
	
	public BufferedImage[] sprites;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private boolean active;
	private boolean finalAttack;
	
	private int step;
	private int stepCount;
	
	// attack pattern
	private int[] steps = {0, 1, 0, 1, 2, 1, 0, 2, 1, 2};
	
	
	private DarkEnergy[] shield;
	private double ticks;
	
	public Spirit(TileMap tm, Player p, ArrayList<Enemy> enemies, ArrayList<Explosion> explosions) {
		
		super(tm);
		player = p;
		this.enemies = enemies;
		this.explosions = explosions;
		
		width = 300;
		height = 300;
		cwidth = 270;
		cheight = 270;
		
		health = maxHealth = 2000;
		
		moveSpeed = 3;
		
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Sprites/Enemies/boss.png")
			);
			sprites = new BufferedImage[5];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		damage = 1;
		
		animation.setFrames(sprites);
		animation.setDelay(1);
		
		shield = new DarkEnergy[2];
		
		step = 0;
		stepCount = 0;
		
	}
	
	public void setActive() { active = true; }
	
	public void update() {
		
		if(health == 0) return;
		
		// restart attack pattern
		if(step == steps.length) {
			step = 0;
		}
		
		ticks++;
		
		if(flinching) {
			flinchCount++;
			if(flinchCount == 8) flinching = false;
		}
		
		x += dx;
		y += dy;
		
		animation.update();
		
		if(!active) return;
		
		////////////
		// special
		////////////
		if(health <= maxHealth / 2) {
			if(shield[0] == null) {
				shield[0] = new DarkEnergy(tileMap);
				shield[0].setPermanent(true);
				enemies.add(shield[0]);
			}
			if(shield[1] == null) {
				shield[1] = new DarkEnergy(tileMap);
				shield[0].setPermanent(true);
				enemies.add(shield[1]);
			}
			double pos = ticks / 32;
			shield[0].setPosition(x + 120 * Math.sin(pos), y + 120 * Math.cos(pos));
			pos += 3.1415;
			shield[1].setPosition(x + 120 * Math.sin(pos), y + 120 * Math.cos(pos));
		}
		
		if(!finalAttack && health <= maxHealth / 4) {
			stepCount = 0;
			finalAttack = true;
		}
		
		if(finalAttack) {
			stepCount++;
			if(stepCount == 1) {
				explosions.add(new Explosion(tileMap, (int)x, (int)y));
				x = -9000;
				y = 9000;
				dx = dy = 0;
			}
			if(stepCount == 60) {
				x = tileMap.getWidth() / 2;
				y = tileMap.getHeight() / 2;
				explosions.add(new Explosion(tileMap, (int)x, (int)y));
			}
			if(stepCount >= 90 && stepCount % 30 == 0) {
				DarkEnergy de = new DarkEnergy(tileMap);
				de.setPosition(x, y);
				de.setVector(3 * Math.sin(stepCount / 32), 3 * Math.cos(stepCount / 32));
				de.setType(DarkEnergy.BOUNCE);
				enemies.add(de);
			}
			return;
		}
		
		////////////
		// attacks
		////////////
		
		// fly around dropping bombs
		if(steps[step] == 0) {
			stepCount++;
			if(y > 240) {
				dy = -4;
			}
			if(y < 240) {
				dy = 0;
				y = 240;
				dx = -1;
			}
			if(y == 240) {
				if(dx == -1 && x < 240) {
					dx = 1;
				}
				if(dx == 1 && x > tileMap.getWidth() - 240) {
					dx = -1;
				}
			}
			if(stepCount % 240 == 0) {
				DarkEnergy de = new DarkEnergy(tileMap);
				de.setType(DarkEnergy.GRAVITY);
				de.setPosition(x, y);
				int dir = Math.random() < 0.5 ? 1 : -1;
				de.setVector(dir, 0);
				enemies.add(de);
			}
			if(stepCount == 3000) {
				step++;
				stepCount = 0;
				right = left = false;
			}
		}
		// floor sweep
		else if(steps[step] == 1) {
			stepCount++;
			if(stepCount == 1) {
				explosions.add(new Explosion(tileMap, (int)x, (int)y));
				x = -9000;
				y = 9000;
				dx = dy = 0;
			}
			if(stepCount == 240) {
				if(player.getx() > tileMap.getWidth() / 2) {
					x = 120;
					y = tileMap.getHeight() - 240;
					dx = 4;
				}
				else {
					x = tileMap.getWidth() - 120;
					y = tileMap.getHeight() - 240;
					dx = -4;
				}
				explosions.add(new Explosion(tileMap, (int)x, (int)y));
			}
			if((dx == -4 && x < 120) || (dx == 4 && x > tileMap.getWidth() - 120)) {
				stepCount = 0;
				step++;
				dx = dy = 0;
			}
			
		}
		// shockwave
		else if(steps[step] == 2) {
			stepCount++;
			if(stepCount == 1) {
				x = tileMap.getWidth() / 2;
				y = 160;
			}
			if(stepCount == 240) {
				dy = 7;
			}
			if(y >= tileMap.getHeight() - 120) {
				dy = 0;
			}
			if(stepCount > 240 && stepCount < 480 && stepCount % 5 == 0 && dy == 0) {
				DarkEnergy de = new DarkEnergy(tileMap);
				de.setPosition(x, y);
				de.setVector(-3, 0);
				enemies.add(de);
				de = new DarkEnergy(tileMap);
				de.setPosition(x, y);
				de.setVector(3, 0);
				enemies.add(de);
			}
			if(stepCount == 120) {
				stepCount = 0;
				step++;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		if(flinching) {
			if(flinchCount % 4 < 2) return;
		}
		super.draw(g);
	}

}
