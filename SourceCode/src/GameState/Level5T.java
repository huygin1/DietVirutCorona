package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

//import Audio.JukeBox;
import Entity.Enemy;
import Entity.EnergyParticle;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.PlayerSave;
import Entity.Portal;
import Entity.Spirit;
import Entity.Artfact.BottomLeftPiece;
import Entity.Artfact.BottomRightPiece;
import Entity.Artfact.TopLeftPiece;
import Entity.Artfact.TopRightPiece;
import Entity.Enemies.DarkEnergy;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import amThanh.amThanh;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

public class Level5T extends GameState {
	
	 private amThanh at = new amThanh();
	 int demkinhnghiem = PlayerSave.getExp();
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	private TopLeftPiece tlp;
	private TopRightPiece trp;
	private BottomLeftPiece blp;
	private BottomRightPiece brp;
	private Portal portal;
	 private BufferedImage Table;
      private BufferedImage Tamdung;
	private Spirit spirit;
	
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	private boolean eventPortal;
	private boolean flash;
	private boolean eventBossDead;
	
	public Level5T(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		
		
		// tilemap
		 
        tileMap = new TileMap(128);
        tileMap.loadTiles("/Tilesets/tilemap5.png");
        tileMap.loadMap("/Maps/map5.map");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(200, 500);
		  player.setHealth(5);
        player.setLives(3);
        player.setTime(0);
        player.setLevel(100);
        player.setExp(0);
             try {
                 Tamdung = ImageIO.read(
                         getClass().getResourceAsStream("/HUD/pause.png")
                 );
                   Table = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/Table.png"));
             } catch (IOException ex) {
                 Logger.getLogger(Level5.class.getName()).log(Level.SEVERE, null, ex);
             }
            
          
		// explosions
		explosions = new ArrayList<Explosion>();
		
		// enemies
		enemies = new ArrayList<Enemy>();
		populateEnemies();
		
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();
		
		// init player
		player.init(enemies, energyParticles);
		
		// hud
		hud = new HUD(player);
		
		// portal
		portal = new Portal(tileMap);
		portal.setPosition(600, 600 );
		
		// artifact
		
		
		// start event
		eventStart = blockInput = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
        at.playloop();
		// sfx
//		JukeBox.load("/SFX/teleport.mp3", "teleport");
//		JukeBox.load("/SFX/explode.mp3", "explode");
//		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");
//		
//		// music
//		JukeBox.load("/Music/level1boss.mp3", "level1boss");
//		JukeBox.load("/Music/fanfare.mp3", "fanfare");
		
	}
	
	private void populateEnemies() {
		enemies.clear();
		spirit = new Spirit(tileMap, player, enemies, explosions);
		spirit.setPosition(-9000, 9000);
		enemies.add(spirit);
	}
	 public int pointDem;
	public void update() {
		
		// check keys
		handleInput();
		
		// check if boss dead event should start
		if(!eventFinish && spirit.isDead()) {
			eventBossDead = blockInput = true;
		}
		
		// check if player dead event should start
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		if(eventPortal) eventPortal();
		if(eventBossDead) eventBossDead();
		
		// move backgrounds
		
		
		// update player
		player.update();
		
		// update tilemap
		tileMap.setPosition(
			(int)GamePanel.WIDTH / 2 - player.getx(),
			(int)GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();
		
		// update enemies
                            PlayerSave.point++;
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead() || e.shouldRemove()) {
				enemies.remove(i);
				i--;
                            
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
                                
                                
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		// update portal
		portal.update();
		
		// update artfact
		
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw background
		g.setColor(Color.decode("#c4e1c7"));
            g.fillRect(0, 0, (int) (int) GamePanel.WIDTH, (int) (int) GamePanel.HEIGHT);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw portal
		portal.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}
		
		// draw artifact
		
		// draw player
		player.draw(g);
		
		 g.drawImage(Table, 5, 5, null);
        hud.draw(g);
g.drawImage(Tamdung, (int) GamePanel.WIDTH -190, 5,null);
		
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
		// flash
		if(flash) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(0, 0, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT);
		}
		
	}
	
	public void handleInput() {
		 if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(true);
            at.clip.stop();
        } else {
            at.clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if (blockInput || player.getHealth() == 0) {
            return;
        }
        player.setUp(Keys.keyState[Keys.UP]);

        player.setLeft(Keys.keyState[Keys.LEFT]);
        player.setDown(Keys.keyState[Keys.DOWN]);
        player.setRight(Keys.keyState[Keys.RIGHT]);
        player.setJumping(Keys.keyState[Keys.BUTTON1]);
        player.setDashing(Keys.keyState[Keys.BUTTON2]);
        if (Keys.isPressed(Keys.BUTTON3)) {
            player.setAttacking();
        }
        
	
	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	private void reset() {
		player.reset();
		player.setPosition(400, 500);
		populateEnemies();
		eventStart = blockInput = true;
		eventCount = 0;
		eventStart();
	}
	
	// level started
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, (int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT));
			tb.add(new Rectangle(0, (int)GamePanel.HEIGHT / 2, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT / 2));
			tb.add(new Rectangle((int)GamePanel.WIDTH / 2, 0, (int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT));
			if(!portal.isOpened()) tileMap.setShaking(true, 10);
//			JukeBox.stop("level1");
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			eventPortal = blockInput = true;
			tb.clear();
			
		}
	}
	
	// player has died
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				(int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
                                at.clip.stop();
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
                                
			}
		}
	}
	
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(
				(int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 1) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount == 60) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(GameStateManager.ACIDSTATE);
                         
          
                             at.clip.stop();
		}
		
	}
	
	private void eventPortal() {
		eventCount++;
		if(eventCount == 1) {
			if(portal.isOpened()) {
				eventCount = 360;
			}
		}
		if(eventCount > 60 && eventCount < 180) {
			energyParticles.add(
				new EnergyParticle(tileMap, 500, 500, (int) (Math.random() * 4)));
		}
		if(eventCount >= 160 && eventCount <= 180) {
			if(eventCount % 4 == 0 || eventCount % 4 == 1) flash = true;
			else flash = false;
		}
		if(eventCount == 181) {
			tileMap.setShaking(false, 0);
			flash = false;
			
			player.setEmote(Player.SURPRISED);
		}
		if(eventCount == 240) {
			
		}
		if(eventCount == 300) {
			player.setEmote(Player.NONE);
			portal.setOpening();
		}
		if(eventCount == 360) {
			flash = true;
			spirit.setPosition(500, 500);
			DarkEnergy de;
			for(int i = 0; i < 20; i++) {
				de = new DarkEnergy(tileMap);
				de.setPosition(500, 500);
				de.setVector(Math.random() * 10 - 5, Math.random() * -2 - 3);
				enemies.add(de);
			}
		}
		if(eventCount == 362) {
			flash = false;
//			JukeBox.loop(
//				"level1boss",
//				0,
//				60000,
//				JukeBox.getFrames("level1boss") - 4000
//			);
		}
		if(eventCount == 420) {
			eventPortal = blockInput = false;
			eventCount = 0;
			spirit.setActive();
		}
		
	}
	
	public void eventBossDead() {
		eventCount++;
		if(eventCount == 1) {
			player.stop();
//			JukeBox.stop("level1boss");
			enemies.clear();
		}
		if(eventCount <= 120 && eventCount % 15 == 0) {
			explosions.add(new Explosion(tileMap, spirit.getx(), spirit.gety()));
//			JukeBox.play("explode");
		}
		if(eventCount == 180) {
//			JukeBox.play("fanfare");
		}
		if(eventCount == 390) {
			eventBossDead = false;
			eventCount = 0;
			eventFinish = true;
		}
	}

}