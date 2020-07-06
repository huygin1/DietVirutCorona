package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import GameState.*;
import Handlers.Keys;
import Main.GamePanel;

public class PauseState extends GameState {
	
	private Font font;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		// fonts
		font = new Font("Century Gothic", Font.PLAIN, 14);
		
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Tạm dừng", 190, 190);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
		if(Keys.isPressed(Keys.ok)) {
                        Level1AState gs = new Level1AState(gsm);
			gsm.setPaused(false);
                        gs.clip.stop();
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

}
