package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;


import Entity.Enemy;
import Entity.EnemyProjectile;
import Entity.EnergyParticle;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.PlayerSave;
import Entity.Teleport;
import Entity.Title;
import Entity.Enemies.VirutBay;
import Entity.Enemies.VirutCon;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import sun.audio.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Level1AState extends GameState {

    private Background sky;
    private Background clouds;
    private Background mountains;

    private Player player;
    private TileMap tileMap;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyProjectile> eprojectiles;
    private ArrayList<EnergyParticle> energyParticles;
    private ArrayList<Explosion> explosions;

    private HUD hud;
    private BufferedImage hageonText;
    private Title title;
    private Title subtitle;
    private Teleport teleport;
    int dem = 0;
    // events
    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;
    private static AudioStream theStream;
     public Clip clip;
	
        ContinuousAudioDataStream BGM ;
     
    public Level1AState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {

        // backgrounds
        sky = new Background("/Backgrounds/c.jpg", 0);
        clouds = new Background("/Backgrounds/2.png", 0.1);
        mountains = new Background("/Backgrounds/2.png", 0.2);

        // tilemap
        tileMap = new TileMap(128);
        tileMap.loadTiles("/Tilesets/tilemap5.png");
        tileMap.loadMap("/Maps/maphuongdan.map");

        tileMap.setPosition(140, 0);

        tileMap.setTween(1);

        // player
        player = new Player(tileMap);
        player.setPosition(300, 161);
        player.setHealth(PlayerSave.getHealth());
        player.setLives(PlayerSave.getLives());
        player.setTime(PlayerSave.getTime());

        // enemies
        enemies = new ArrayList<Enemy>();
        eprojectiles = new ArrayList<EnemyProjectile>();
        populateEnemies();

        // energy particle
        energyParticles = new ArrayList<EnergyParticle>();

        // init player
        player.init(enemies, energyParticles);

        // explosions
        explosions = new ArrayList<Explosion>();

        // hud
        hud = new HUD(player);

        // title and subtitle
        try {
            hageonText = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/tieude.png")
            );
            title = new Title(hageonText.getSubimage(0, 0, 712, 78));
            title.sety(60);
            subtitle = new Title(hageonText.getSubimage(0, 78, 292, 105));
            subtitle.sety(135);
        } catch (Exception e) {

            e.printStackTrace();
        }

        // teleport
        teleport = new Teleport(tileMap);
        teleport.setPosition(11232, 1074);

        // start event
        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        // sfx
  
        try {
            File file = new File("amThanh/a.wav");
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
          
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            else {
                throw new RuntimeException("Khong tim thay am thanh: " + "a.wav");
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("loi URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Khong ho tro dinh dang file nay: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Nhap Xuat file That bai: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
         clip.loop(Clip.LOOP_CONTINUOUSLY);
//        try {
//             at.nhacNen = Applet.newAudioClip(new URL("file", "", "amthanh/sound/a.wav"));
//
//        } catch (Exception e) {
//        }
//        
//      
//                
//            
//           at.nhacNen.play();
        
         
        // musicC:\Users\huygi\Downloads\Programs\Project\Project\Resources\Musica
//        JukeBox.load("/Musica/a.mp3", "level1");
//        JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

        //  Main method
//JukeBox.load("/Music/level1.mp3", "level1");
//                System.out.println(JukeBox.getFrames("level1"));
    }

    private void populateEnemies() {
        enemies.clear();
        /*Tengu t = new Tengu(tileMap, player, enemies);
		t.setPosition(1300, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1330, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1360, 100);
		enemies.add(t);*/
        VirutCon vrC;
        VirutBay g;

        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(4000, 1000);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(5000, 600);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(5200, 600);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(5400, 600);
        enemies.add(vrC);
//        vrC = new GelPop(tileMap, player);
//        vrC.setPosition(1680, 100);
//        enemies.add(vrC);
//        vrC = new GelPop(tileMap, player);
//        vrC.setPosition(1700, 100);

//        enemies.add(vrC);
//        vrC = new GelPop(tileMap, player);
//        vrC.setPosition(2177, 100);
//        enemies.add(vrC);
//        vrC = new GelPop(tileMap, player);
//        vrC.setPosition(2960, 100);
//        enemies.add(vrC);
//        vrC = new GelPop(tileMap, player);
//        vrC.setPosition(2980, 100);
//        enemies.add(vrC);
//        vrC = new GelPop(tileMap, player);
//        vrC.setPosition(3000, 100);
//        enemies.add(vrC);
//
//        g = new Gazer(tileMap);
//        g.setPosition(2600, 100);
//        enemies.add(g);
//        g = new Gazer(tileMap);
//        g.setPosition(3500, 100);
//        enemies.add(g);
    }

    public void update() {

        // check keys
        handleInput();

        // check if end of level event should start
        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }

        // check if player dead event should start
        if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            eventDead = blockInput = true;
        }

        // play events
        if (eventStart) {
            eventStart();
        }
        if (eventDead) {
            eventDead();
        }
        if (eventFinish) {
            eventFinish();
        }

        // move title and subtitle
        if (title != null) {
            title.update();
            if (title.shouldRemove()) {
                title = null;
            }
        }
        if (subtitle != null) {
            subtitle.update();
            if (subtitle.shouldRemove()) {
                subtitle = null;
            }
        }

        // move backgrounds
        clouds.setPosition(tileMap.getx(), tileMap.gety());
        mountains.setPosition(tileMap.getx(), tileMap.gety());

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
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                dem += 10;
                explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
            }
        }

        // update enemy projectiles
        for (int i = 0; i < eprojectiles.size(); i++) {
            EnemyProjectile ep = eprojectiles.get(i);
            ep.update();
            if (ep.shouldRemove()) {
                eprojectiles.remove(i);
                i--;
            }
        }

        // update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

        // update teleport
     

    }

    public void draw(Graphics2D g) {

        // draw background
        sky.draw(g);
//		clouds.draw(g);
//		mountains.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw enemy projectiles
        for (int i = 0; i < eprojectiles.size(); i++) {
            eprojectiles.get(i).draw(g);
        }

        // draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        // draw player
        player.draw(g);

        // draw teleport
        teleport.draw(g);

        // draw hud
        hud.draw(g);

        // draw title
        if (title != null) {
            title.draw(g);
        }
        if (subtitle != null) {
            subtitle.draw(g);
        }

        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(true);
            clip.stop();
        }else{
            clip.loop(Clip.LOOP_CONTINUOUSLY);
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
        if (Keys.isPressed(Keys.BUTTON4)) {
            player.setCharging();
        }
    }

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
    // reset level
    private void reset() {
        player.reset();
        player.setPosition(300, 161);
        populateEnemies();
        blockInput = true;
        eventCount = 0; 
        tileMap.setShaking(false, 0);
        eventStart = true;
        eventStart();
        title = new Title(hageonText.getSubimage(0, 0, 712, 78));
            title.sety(60);
            subtitle = new Title(hageonText.getSubimage(0, 78, 292, 105));
            subtitle.sety(135);
    }

    // level started
    private void eventStart() {
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, (int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT));
            tb.add(new Rectangle(0, (int)GamePanel.HEIGHT / 2, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT / 2));
            tb.add(new Rectangle((int)GamePanel.WIDTH / 2, 0, (int)GamePanel.WIDTH , (int)GamePanel.HEIGHT));
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 6;
            tb.get(1).width -= 12;
            tb.get(2).y += 6;
            tb.get(3).x += 12;
        }
        if (eventCount == 30) {
            title.begin();
        }
        if (eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;
            subtitle.begin();
            tb.clear();
        }
    }

    // player has died
    private void eventDead() {
        eventCount++;
        if (eventCount == 1) {
            player.setDead();
            player.stop();
        }
        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(
                    (int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getLives() == 0) {
                gsm.setState(GameStateManager.MENUSTATE);
                clip.stop();
            } else {
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
        if (eventCount == 1) {
//            JukeBox.play("teleport");
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(
                    (int)GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
//            JukeBox.stop("teleport");
        }
        if (eventCount == 180) {
//            PlayerSave.setHealth(player.getHealth());
//            PlayerSave.setLives(player.getLives());
//            PlayerSave.setTime(player.getTime());
            gsm.setState(GameStateManager.MENUSTATE);
            clip.stop();
        }

    }

}
