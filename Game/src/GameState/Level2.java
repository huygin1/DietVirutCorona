package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import amThanh.amThanh;
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
import Entity.LuuDuLieuLenDatabase;
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

public class Level2 extends GameState {

    private amThanh at = new amThanh();
    private Background sky;
    private Background clouds;
    private Background mountains;
    private int sodem = 0;
    private Player player;
    private LuuDuLieuLenDatabase DB;
    private TileMap tileMap;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyProjectile> eprojectiles;
    private ArrayList<EnergyParticle> energyParticles;
    private ArrayList<Explosion> explosions;
    private BufferedImage Table;
    private HUD hud;
    private BufferedImage TieuDe;
    private Title title;
    private Title subtitle;
    private Teleport teleport;
    int dem = PlayerSave.getLevel();
    int demkinhnghiem = PlayerSave.getExp();
    // events
    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;
    private static AudioStream theStream;
    private boolean eventQuake;

    ContinuousAudioDataStream BGM;

    public Level2(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {

        sky = new Background("/Backgrounds/c.jpg", 0);
//        clouds = new Background("/Backgrounds/2.png", 0.1);
//        mountains = new Background("/Backgrounds/2.png", 0.2);

        tileMap = new TileMap(128);
        tileMap.loadTiles("/Tilesets/tilemap5.png");
        tileMap.loadMap("/Maps/map2.map");

        tileMap.setPosition(140, 0);

        tileMap.setTween(1);

        player = new Player(tileMap);
        player.setPosition(300, 161);
        player.setHealth(PlayerSave.getHealth());
        player.setLives(PlayerSave.getLives());
        player.setTime(PlayerSave.getTime());
        player.setLevel(PlayerSave.getLevel());
        player.setExp(PlayerSave.getExp());

        enemies = new ArrayList<Enemy>();
        eprojectiles = new ArrayList<EnemyProjectile>();
        populateEnemies();

        energyParticles = new ArrayList<EnergyParticle>();

        player.init(enemies, energyParticles);

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        try {
            TieuDe = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/tieude.png")
            );
            Table = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/Table.png"));

            title = new Title(TieuDe.getSubimage(0, 0, 712, 78));
            title.sety(60);
            subtitle = new Title(TieuDe.getSubimage(0, 78, 292, 105));
            subtitle.sety(135);
        } catch (Exception e) {

            e.printStackTrace();
        }

        teleport = new Teleport(tileMap);
        teleport.setPosition(9640, 770);

        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        at.playloop();
 
    }

    private void populateEnemies() {
        enemies.clear();
        VirutCon vrC;
        VirutBay g;

        g = new VirutBay(tileMap);
        g.setPosition(1520, 1200);
        enemies.add(g);
         g = new VirutBay(tileMap);
        g.setPosition(2944, 750);
        enemies.add(g);
        g = new VirutBay(tileMap);
        g.setPosition(3745, 967);
        enemies.add(g);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(5200, 1275);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(5534, 1275);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(8300, 850);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(8500, 850);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(8700, 850);
        enemies.add(vrC);
        g = new VirutBay(tileMap);
         g.setPosition(6665, 610);
        enemies.add( g);
    }
    public int pointDem;
    public void update() {

        handleInput();

        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }

        if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            eventDead = blockInput = true;
        }

        if (eventStart) {
            eventStart();
        }

        if (eventDead) {
            eventDead();
        }
        if (eventFinish) {
            eventFinish();
        }

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

       

        player.update();

        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );
        tileMap.update();
        tileMap.fixBounds();

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                pointDem += 1000;
                player.setPoint(pointDem);
                pointDem = 0;
                demkinhnghiem += 100;
                player.setExp(demkinhnghiem);
                if (player.getExp() == PlayerSave.total + 200) {
                    demkinhnghiem = 0;
                }

                explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
            }
        }

        for (int i = 0; i < eprojectiles.size(); i++) {
            EnemyProjectile ep = eprojectiles.get(i);
            ep.update();
            if (ep.shouldRemove()) {
                eprojectiles.remove(i);
                i--;
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

    }

    public void draw(Graphics2D g) {

         g.setColor(Color.decode("#c4e1c7"));
            g.fillRect(0, 0, (int) (int) GamePanel.WIDTH, (int) (int) GamePanel.HEIGHT);

        tileMap.draw(g);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        for (int i = 0; i < eprojectiles.size(); i++) {
            eprojectiles.get(i).draw(g);
        }

        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        player.draw(g);

        teleport.draw(g);
        g.drawImage(Table, 5, 5, null);
        hud.draw(g);

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
        if (Keys.isPressed(Keys.BUTTON4)) {
            player.setCharging();
        }
    }

    private void reset() {
        player.reset();
        player.setPosition(300, 161);
//        lenh duoi nay reset lai mang virut
//        populateEnemies();
        blockInput = true;

        eventCount = 0;
        tileMap.setShaking(false, 0);
        eventStart = true;
        eventStart();
        title = new Title(TieuDe.getSubimage(0, 0, 1, 1));
        title.sety(60);
        subtitle = new Title(TieuDe.getSubimage(0, 0, 1, 1));
        subtitle.sety(135);
    }

    private void eventStart() {
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0,(int) GamePanel.WIDTH,(int) GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0,(int) GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT));
            tb.add(new Rectangle(0,(int) GamePanel.HEIGHT / 2, (int)GamePanel.WIDTH,(int) GamePanel.HEIGHT / 2));
            tb.add(new Rectangle((int)GamePanel.WIDTH / 2, 0,(int) GamePanel.WIDTH, (int)GamePanel.HEIGHT));
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

    private void eventDead() {
        eventCount++;
        if (eventCount == 1) {
            player.setDead();
            player.stop();
        }
        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(
                   (int) GamePanel.WIDTH / 2,(int) GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getLives() == 0) {
                player.capnhatDBLos();
                gsm.setState(GameStateManager.MENUSTATE);
                at.clip.stop();
            } else {
                eventDead = blockInput = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    private void eventFinish() {
        eventCount++;
        if (eventCount == 1) {
            at.playDichChuyen();
            player.capnhatDB();
           player.capnhatState(2);
           
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(
                   (int) GamePanel.WIDTH / 2, (int)GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;

        }
        if (eventCount == 180) {
            PlayerSave.setHealth(player.getHealth());
            PlayerSave.setLives(player.getLives());
            PlayerSave.setTime(player.getTime());
            PlayerSave.setExp(player.getExp());
            PlayerSave.setLevel(player.getLevel());
            gsm.setState(GameStateManager.LEVEL2);
            at.clip.stop();
        }

    }

    private void eventQuake() {
        eventCount++;
        if (eventCount == 1) {
            player.stop();
            player.setPosition(2175, player.gety());
        }
        if (eventCount == 60) {
            player.setEmote(Player.CONFUSED);
        }
        if (eventCount == 120) {
            player.setEmote(Player.NONE);
        }
        if (eventCount == 150) {
            tileMap.setShaking(true, 10);
        }
        if (eventCount == 180) {
            player.setEmote(Player.SURPRISED);
        }
        if (eventCount == 300) {
            player.setEmote(Player.NONE);
            eventQuake = blockInput = false;
            eventCount = 0;
        }
    }
}
