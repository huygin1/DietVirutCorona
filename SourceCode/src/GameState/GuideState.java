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
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Toolkit;

import sun.audio.*;

import javax.sound.sampled.Clip;

public class GuideState extends GameState {

    private amThanh at = new amThanh();
    private Background sky;
    private Background clouds;
    private Background mountains;
    private int sodem = 0;
    private Player player;
    private TileMap tileMap;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyProjectile> eprojectiles;
    private ArrayList<EnergyParticle> energyParticles;
    private ArrayList<Explosion> explosions;
    private BufferedImage Table;
    private HUD hud;
    private BufferedImage hageonText;
    private BufferedImage Tamdung;
    private Title title;
    private Title subtitle;
    private Teleport teleport;
    int dem = PlayerSave.getLevel();
    int demkinhnghiem = PlayerSave.getExp();

    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;
    private static AudioStream theStream;

    ContinuousAudioDataStream BGM;

    public GuideState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {

        tileMap = new TileMap(128);
        tileMap.loadTiles("/Tilesets/tilemap5.png");
        tileMap.loadMap("/Maps/maphuongdan.map");

        tileMap.setPosition(140, 0);

        tileMap.setTween(1);

        player = new Player(tileMap);
        player.setPosition(300, 161);
        player.setHealth(5);
        player.setLives(3);
        player.setTime(PlayerSave.getTime());
        player.setLevel(10);
        player.setExp(0);

        enemies = new ArrayList<Enemy>();
        eprojectiles = new ArrayList<EnemyProjectile>();
        populateEnemies();

        energyParticles = new ArrayList<EnergyParticle>();

        player.init(enemies, energyParticles);

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        try {
            Tamdung = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/pause.png")
            );
            hageonText = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/tieude.png")
            );
            Table = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/Table.png"));

            title = new Title(hageonText.getSubimage(0, 0, 712, 78));
            title.sety(60);
            subtitle = new Title(hageonText.getSubimage(0, 78, 292, 105));
            subtitle.sety(135);
        } catch (Exception e) {

            e.printStackTrace();
        }

        teleport = new Teleport(tileMap);
        teleport.setPosition(9875, 820);

        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        at.playloop();

    }

    private void populateEnemies() {
        enemies.clear();
        VirutCon vrC;
        VirutBay g;

        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(5600, 400);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(6600, 1100);
        enemies.add(vrC);
        vrC = new VirutCon(tileMap, player);
        vrC.setPosition(7000, 1100);
        enemies.add(vrC);
        g = new VirutBay(tileMap);
        g.setPosition(7578, 1139);
        enemies.add(g);
    }

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

        Toolkit.getDefaultToolkit().sync();
        g.setColor(Color.decode("#c4e1c7"));
        g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) (int) GamePanel.HEIGHT);

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
        g.drawImage(Tamdung, (int) GamePanel.WIDTH - 190, 5, null);
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
            tb.add(new Rectangle(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, (int) GamePanel.WIDTH / 2, (int) GamePanel.HEIGHT));
            tb.add(new Rectangle(0, (int) GamePanel.HEIGHT / 2, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT / 2));
            tb.add(new Rectangle((int) GamePanel.WIDTH / 2, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT));
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
                    (int) GamePanel.WIDTH / 2, (int) GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getLives() == 0) {
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
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(
                    (int) GamePanel.WIDTH / 2, (int) GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;

        }
        if (eventCount == 180) {
            gsm.setState(GameStateManager.MENUSTATE);
            at.clip.stop();
        }

    }

}
