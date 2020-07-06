package Entity;

import Sever.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//import Audio.JukeBox;
import TileMap.TileMap;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import amThanh.amThanh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sound.sampled.Clip;

public class Player extends MapObject {

    // references
    private ArrayList<Enemy> enemies;

    // player stuff
    private int sodem = 0;
    private int bc;
    private int ac = PlayerSave.getLevel();
    private int save1;
    private int level;
    private int exp;
    private int lives;
    private int health;
    private int maxHealth;
    private int damage;
    private int chargeDamage;
    private boolean knockback;
    private boolean flinching;
    private long flinchCount;
    private int score;
    private boolean doubleJump;
    private boolean alreadyDoubleJump;
    private double doubleJumpStart;
    private ArrayList<EnergyParticle> energyParticles;
    private long time;
    private long time2 = 10000;
    private long a;
    private amThanh at = new amThanh();
    // actions
    private boolean dashing;
    private boolean attacking;
    private boolean upattacking;
    private boolean charging;
    private int chargingTick;
    private boolean teleporting;
      private int lv;
     private int li;
      
       private int ex;
        private int he;
    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] NUMFRAMES = {
        1, 8, 5, 3, 3, 5, 3, 8, 2, 1, 3
    };
    private final int[] FRAMEWIDTHS = {
        160, 160, 320, 160, 160, 160, 320, 160, 160, 160, 160
    };
    private final int[] FRAMEHEIGHTS = {
        160, 160, 160, 160, 160, 320, 160, 160, 160, 160, 160
    };
    private final int[] SPRITEDELAYS = {
        -1, 3, 2, 6, 5, 2, 2, 2, 1, -1, 1
    };

    private Rectangle ar;
    private Rectangle aur;
    private Rectangle cr;

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int ATTACKING = 2;
    private static final int JUMPING = 3;
    private static final int FALLING = 4;
    private static final int UPATTACKING = 5;
    private static final int CHARGING = 6;
    private static final int DASHING = 7;
    private static final int KNOCKBACK = 8;
    private static final int DEAD = 9;
    private static final int TELEPORTING = 10;

    // emotes
    private BufferedImage confused;
    private BufferedImage surprised;
    public static final int NONE = 0;
    public static final int CONFUSED = 1;
    public static final int SURPRISED = 2;
    private int emote = NONE;

    
    public Player(TileMap tm) {

        super(tm);

        ar = new Rectangle(0, 0, 0, 0);
        ar.width = 120;
        ar.height = 130;
        aur = new Rectangle((int) x - 15, (int) y - 45, 30, 30);
        cr = new Rectangle(0, 0, 0, 0);
        cr.width = 200;
        cr.height = 160;

        width = 120;
        height = 120;
        cwidth = 60;
        cheight = 137;

        moveSpeed = 5;
        maxSpeed = 5;
        stopSpeed = 1.6;
        fallSpeed = 0.28;
        maxFallSpeed = 12.0;
        jumpStart = -16;
        stopJumpSpeed = 1.6;
        doubleJumpStart = -10;

        damage = 1;
        chargeDamage = 1;

        facingRight = true;

        lives = 3;
        level = 1;
        exp = 0;
              
        health = maxHealth = 5;
    
        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/3.png"
                    )
            );

            int count = 0;
            sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < NUMFRAMES.length; i++) {
                BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
                for (int j = 0; j < NUMFRAMES[i]; j++) {
                    bi[j] = spritesheet.getSubimage(
                            j * FRAMEWIDTHS[i],
                            count,
                            FRAMEWIDTHS[i],
                            FRAMEHEIGHTS[i]
                    );
                }
                sprites.add(bi);
                count += FRAMEHEIGHTS[i];
            }

            // emotes
            spritesheet = ImageIO.read(getClass().getResourceAsStream(
                    "/HUD/Emotes.gif"
            ));
            confused = spritesheet.getSubimage(
                    0, 0, 14, 17
            );
            surprised = spritesheet.getSubimage(
                    14, 0, 14, 17
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        energyParticles = new ArrayList<EnergyParticle>();

        setAnimation(IDLE);
    }

    public void init(
            ArrayList<Enemy> enemies,
            ArrayList<EnergyParticle> energyParticles) {
        this.enemies = enemies;
        this.energyParticles = energyParticles;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setEmote(int i) {
        emote = i;
    }

    public void setTeleporting(boolean b) {
        teleporting = b;
    }

    public void setJumping(boolean b) {
        if (knockback) {
            return;
        }
        if (b && !jumping && falling && !alreadyDoubleJump) {
            doubleJump = true;
        }
        jumping = b;
    }

    public void setAttacking() {
        if (knockback) {
            return;
        }
        if (charging) {
            return;
        }
        if (up && !attacking) {
            upattacking = true;
        } else {
            attacking = true;
        }
    }

    public void setCharging() {
        if (knockback) {
            return;
        }
        if (!attacking && !upattacking && !charging) {
            charging = true;
//			JukeBox.play("playercharge");
            chargingTick = 0;
        }
    }

    public void setDashing(boolean b) {
        if (!b) {
            dashing = false;
        } else if (b && !falling) {
            dashing = true;
        }
    }

    public boolean isDashing() {
        return dashing;
    }

    public void setDead() {
        health = 0;
        stop();
    }

    public String getTimeToString() {
        int minutes = (int) (time / 3600);
        int seconds = (int) ((time % 3600) / 60);
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

    public String getLevelToString() {

        damage = level;

        return level + "";

    }
    public String getPointToString() {

       
       
        
        return   PlayerSave.point +"";

    }
    public String getExptoString(){
        if(getExp()==PlayerSave.total+200){
            ac+=1;
            setLevel(ac);
           PlayerSave.total += 100;
            setExp(0);
           
           
        }
        bc = PlayerSave.total + 200;
        return getExp() +"/"+bc;
      
    }
   
    
    public long getTime() {
        return time;
    }
public void setPoint(int i) {
        PlayerSave.point += i;
    }
    public void setTime(long t) {
        time = t;
    }

    public void setHealth(int i) {
        health = i;
    
    }

    public void setLives(int i) {
        lives = i;
       
      
    }
   
    public void setSave1(int i) {
        save1 = i;
    }
    public void gainLife() {
        lives++;
        
    }
    
    public void loseLife() {
        lives--;
             
    }

    public int getLives() {
      
        return lives;
        
    }

    public void setLevel(int i) {
        level = i;
       lv = level;
        
      
    }
   
    public int getLevel() {
        return level;
        
    }

    public void setExp(int i) {
        exp = i;
        ex = exp;
    }

    public int getExp() {
        return exp;
    }
    public int getSave1(){
        return save1;
    }
    
    public void increaseScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }
    public int capnhatState(int state){
         int r = -1;

        //1. tao ket noi tu ung dung java den sql server
      Connection cn = KetNoiMySQL.getConnect();

        try {
            String sql;
            if(KetNoiMySQL.Csdl == false){
                 sql = "UPDATE `Character` SET `State` = ? WHERE `Character`.`PlayerID` = ?;";
            }else{
                sql = "UPDATE Character SET State = ? WHERE Character.PlayerID = ?;";
            }
            //2. tao doi tuong statement chua linh insert SQL
           
            PreparedStatement pst = cn.prepareStatement(sql);
            
            //3. set gia tri cho cac tham so ? trong linh insert
           
            pst.setInt(1, state);
             pst.setInt(2, PlayerSave.IDU);
           
            //4. thuc hinh linh insert SQL
            r = pst.executeUpdate();

            //5. dong cac resources
            pst.close();
            cn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }
     public int capnhatDBLos(){
         int r = -1;

        //1. tao ket noi tu ung dung java den sql server
      Connection cn = KetNoiMySQL.getConnect();

        try {
             String sql;
            if(KetNoiMySQL.Csdl == false){
                 sql = "UPDATE `Character` SET `Level` = ?, `Heart` = ?, `Live` = ?, `Total` = ?,`Exp` = ?,`Point` = ?,`Time` = ? ,`State` = ? WHERE `Character`.`PlayerID` = ?;";
            }else{
                sql = "UPDATE Character SET Level = ?, Heart = ?, Live = ?, Total = ?,Exp = ?,Point = ?,Time = ?,State = ? WHERE Character.PlayerID = ?;";
            }
            //2. tao doi tuong statement chua linh insert SQL
            
            PreparedStatement pst = cn.prepareStatement(sql);
            
            //3. set gia tri cho cac tham so ? trong linh insert
           
            pst.setInt(1, 1);
            
              pst.setInt(2, 5);
               pst.setInt(3, 3);
                pst.setInt(4, 0);
                 pst.setInt(5, 0);
                  pst.setInt(6, 0);
                   pst.setInt(7, 0);
                     pst.setInt(8, 1);
                  pst.setInt(9, PlayerSave.IDU);
            //4. thuc hinh linh insert SQL
            r = pst.executeUpdate();

            //5. dong cac resources
            pst.close();
            cn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }
        
   public int capnhatDB(){
         int r = -1;

        //1. tao ket noi tu ung dung java den sql server
      Connection cn = KetNoiMySQL.getConnect();

        try {
             String sql;
            if(KetNoiMySQL.Csdl == false){
                 sql = "UPDATE `Character` SET `Level` = ?, `Heart` = ?, `Live` = ?, `Total` = ?,`Exp` = ?,`Point` = ?,`Time` = ? WHERE `Character`.`PlayerID` = ?;";
            }else{
                sql = "UPDATE Character SET Level = ?, Heart = ?, Live = ?, Total = ?,Exp = ?,Point = ?,Time = ? WHERE Character.PlayerID = ?;";
            }
            //2. tao doi tuong statement chua linh insert SQL
          
            PreparedStatement pst = cn.prepareStatement(sql);
            
            //3. set gia tri cho cac tham so ? trong linh insert
           
            pst.setInt(1, lv);
            
              pst.setInt(2, getHealth());
               pst.setInt(3, getLives());
                pst.setInt(4, PlayerSave.total);
                 pst.setInt(5, ex);
                  pst.setInt(6, PlayerSave.point);
                   pst.setInt(7,(int) getTime());
                  pst.setInt(8, PlayerSave.IDU);
            //4. thuc hinh linh insert SQL
            r = pst.executeUpdate();

            //5. dong cac resources
            pst.close();
            cn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }
    public void hit(int damage) {
        if (flinching) {
            return;
        }
//		JukeBox.play("playerhit");
        stop();
        health -= damage;
        he = health;
        if (health < 0) {
            health = 0;
        }
        flinching = true;
        flinchCount = 0;
        if (facingRight) {
            dx = -1;
        } else {
            dx = 1;
        }
        dy = -3;
        knockback = true;
        falling = true;
        jumping = false;
    }

    public void reset() {
        health = maxHealth;
        facingRight = true;
        currentAction = -1;
        stop();
    }

    public void stop() {
        left = right = up = down = flinching
                = dashing = jumping = attacking = upattacking = charging = false;
    }

    private void getNextPosition() {

        if (knockback) {
            dy += fallSpeed * 2;
            if (!falling) {
                knockback = false;
            }
            return;
        }

        double maxSpeed = this.maxSpeed;
        if (dashing) {
            maxSpeed *= 1.75;
        }

        // movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        // cannot move while attacking, except in air
        if ((attacking || upattacking || charging)
                && !(jumping || falling)) {
            dx = 0;
        }

        // charging
        if (charging) {
            chargingTick++;
            if (facingRight) {
                dx = moveSpeed + (chargingTick * 0.01);
            } else {
                dx = -moveSpeed + (chargingTick * 0.01);
            }
        }

        // jumping
        if (jumping && !falling) {
            //sfx.get("jump").play();
            dy = jumpStart;
            falling = true;

            at.play();

        }

        if (doubleJump) {
            dy = doubleJumpStart;

            alreadyDoubleJump = true;
            doubleJump = false;
//                         clip.loop(1);

            at.play();

        }

        if (!falling) {
            alreadyDoubleJump = false;
        }

        // falling
        if (falling) {
            dy += fallSpeed;
            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
            }
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }

    }

    private void setAnimation(int i) {
        currentAction = i;
        animation.setFrames(sprites.get(currentAction));
        animation.setDelay(SPRITEDELAYS[currentAction]);
        width = FRAMEWIDTHS[currentAction];
        height = FRAMEHEIGHTS[currentAction];
    }

    public void update() {

        time++;
        if(PlayerSave.point > 0){
            PlayerSave.point--;
        }
        // check teleporting
        if (teleporting) {
            energyParticles.add(
                    new EnergyParticle(tileMap, x, y, EnergyParticle.UP)
            );
        }

        // update position
        boolean isFalling = falling;
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        if (isFalling && !falling) {
//			JukeBox.play("playerlands");
        }
        if (dx == 0) {
            x = (int) x;
        }

        // check done flinching
        if (flinching) {
            flinchCount++;
            if (flinchCount > 120) {
                flinching = false;
            }
        }

        // energy particles
        for (int i = 0; i < energyParticles.size(); i++) {
            energyParticles.get(i).update();
            if (energyParticles.get(i).shouldRemove()) {
                energyParticles.remove(i);
                i--;
            }
        }

        // check attack finished
        if (currentAction == ATTACKING
                || currentAction == UPATTACKING) {
            if (animation.hasPlayedOnce()) {
                attacking = false;
                upattacking = false;
            }
        }
        if (currentAction == CHARGING) {
            if (animation.hasPlayed(20)) {
                charging = false;
            }
            cr.y = (int) y - 20;
            if (facingRight) {
                cr.x = (int) x - 15;
            } else {
                cr.x = (int) x - 135;
            }
            if (facingRight) {
                energyParticles.add(
                        new EnergyParticle(
                                tileMap,
                                x + 130,
                                y,
                                EnergyParticle.RIGHT));
            } else {
                energyParticles.add(
                        new EnergyParticle(
                                tileMap,
                                x - 130,
                                y,
                                EnergyParticle.LEFT));
            }
        }

        // check enemy interaction
        for (int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            // check attack
            if (currentAction == ATTACKING
                    && animation.getFrame() == 3 && animation.getCount() == 0) {
                if (e.intersects(ar)) {
                    e.hit(damage);
                }
            }

            // check upward attack
            if (currentAction == UPATTACKING
                    && animation.getFrame() == 3 && animation.getCount() == 0) {
                if (e.intersects(aur)) {
                    e.hit(damage);
                }
            }

            // check charging attack
            if (currentAction == CHARGING) {
                if (animation.getCount() == 0) {
                    if (e.intersects(cr)) {
                        e.hit(chargeDamage);
                    }
                    /*if(e.intersects(this)) {
						e.hit(chargeDamage);
					}*/
                }
            }

            // collision with enemy
            if (!e.isDead() && intersects(e) && !charging) {
                hit(e.getDamage());
            }

            if (e.isDead()) {
                
                at.playVirutDie();
//				JukeBox.play("explode", 2000);
            }

        }

        // set animation, ordered by priority
        if (teleporting) {
            if (currentAction != TELEPORTING) {
                setAnimation(TELEPORTING);
            }
        } else if (knockback) {
            if (currentAction != KNOCKBACK) {
                setAnimation(KNOCKBACK);
            }
        } else if (health == 0) {
            if (currentAction != DEAD) {
                setAnimation(DEAD);
            }
        } else if (upattacking) {
            if (currentAction != UPATTACKING) {
//				JukeBox.play("playerattack");
                at.playattack();
                setAnimation(UPATTACKING);
                aur.x = (int) x - 15;
                aur.y = (int) y - 50;
            } else {
                if (animation.getFrame() == 4 && animation.getCount() == 0) {
                    for (int c = 0; c < 3; c++) {
                        energyParticles.add(
                                new EnergyParticle(
                                        tileMap,
                                        aur.x + aur.width / 2,
                                        aur.y + 5,
                                        EnergyParticle.UP));
                    }
                }
            }
        } else if (attacking) {
            if (currentAction != ATTACKING) {
//				JukeBox.play("playerattack");
                at.playattack();
                setAnimation(ATTACKING);
                ar.y = (int) y - 36;
                if (facingRight) {
                    ar.x = (int) x + 10;
                } else {
                    ar.x = (int) x - 140;
                }
            } else {
                if (animation.getFrame() == 4 && animation.getCount() == 0) {
                    for (int c = 0; c < 20; c++) {
                        if (facingRight) {
                            energyParticles.add(
                                    new EnergyParticle(
                                            tileMap,
                                            ar.x + ar.width - 40,
                                            ar.y + ar.height / 2 - 35,
                                            EnergyParticle.RIGHT));
                        } else {
                            energyParticles.add(
                                    new EnergyParticle(
                                            tileMap,
                                            ar.x + 40,
                                            ar.y + ar.height / 2 - 35,
                                            EnergyParticle.LEFT));
                        }
                    }
                }
            }
        } else if (charging) {
            if (currentAction != CHARGING) {
                setAnimation(CHARGING);
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                setAnimation(JUMPING);
            }
        } else if (dy > 0) {
            if (currentAction != FALLING) {
                setAnimation(FALLING);
            }
        } else if (dashing && (left || right)) {
            if (currentAction != DASHING) {
                setAnimation(DASHING);
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                setAnimation(WALKING);
            }
        } else if (currentAction != IDLE) {
            setAnimation(IDLE);
        }

        animation.update();

        // set direction
        if (!attacking && !upattacking && !charging && !knockback) {
            if (right) {
                facingRight = true;
            }
            if (left) {
                facingRight = false;
            }
        }

    }

    public void draw(Graphics2D g) {

        // draw emote
        if (emote == CONFUSED) {
            g.drawImage(confused, (int) (x + xmap - cwidth / 2), (int) (y + ymap - 40), null);
        } else if (emote == SURPRISED) {
            g.drawImage(surprised, (int) (x + xmap - cwidth / 2), (int) (y + ymap - 40), null);
        }

        // draw energy particles
        for (int i = 0; i < energyParticles.size(); i++) {
            energyParticles.get(i).draw(g);
        }

        // flinch
        if (flinching && !knockback) {
            if (flinchCount % 10 < 5) {
                return;
            }
        }

        super.draw(g);

    }

}
