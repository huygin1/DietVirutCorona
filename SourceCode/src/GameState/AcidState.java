package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Handlers.Keys;
import Main.GamePanel;
import java.awt.Font;
import java.io.InputStream;

public class AcidState extends GameState {
	 String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;
    private Font HuyFont2;
	private float hue;
	private Color color;
	
	private double angle;
	private BufferedImage image;
	
	public AcidState(GameStateManager gsm) {
		super(gsm);
		try {
			image = ImageIO.read(
			getClass().getResourceAsStream(
			"/Sprites/Player/3.png"
			)).getSubimage(0, 0, 120, 120);
                          ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);
            HuyFont = ttfBase.deriveFont(Font.PLAIN, 40);
            HuyFont2 = ttfBase.deriveFont(Font.PLAIN, 45);
		}
		catch(Exception e) {}
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
		color = Color.getHSBColor(hue, 1f, 1f);
		hue += 0.01;
		if(hue > 1) hue = 0;
		angle += 0.1;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(0, 0, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT);
		AffineTransform at = new AffineTransform();
		at.translate(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
		at.rotate(angle);
		g.drawImage(image, at, null);
                   g.setColor(Color.BLACK);
            g.setFont(HuyFont2);
            g.drawString("Bạn thắng", (int) (GamePanel.WIDTH) / 2 - 50, 400);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setState(GameStateManager.MENUSTATE);
	}

}
