package Entity;

//import Audio.JukeBox;
import TileMap.TileMap;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import amThanh.amThanh;
public class Enemy extends MapObject {
	
	public int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
        private amThanh at = new amThanh();
	protected boolean flinching;
	protected long flinchCount;
	
	public Enemy(TileMap tm) {
		super(tm);
		remove = false;
	}
	
	public boolean isDead() { return dead; }
	public boolean shouldRemove() { return remove; }
	
	public int getDamage() { return damage; }
	
	public void hit(int damage) {
            
		if(dead || flinching) return;
//		JukeBox.play("enemyhit");
                at.playVirutHit();
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		if(dead) remove = true;
		flinching = true;
		flinchCount = 5;
	}
	
	public void update() {}
	
}














