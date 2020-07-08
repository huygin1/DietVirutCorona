package Entity;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	private BufferedImage heart;
	private BufferedImage life;
        String fontFileName = "/Fonts/HuyFont.otf";
	
         InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
        
	private Font ttfBase;
        private Font HuyFont;
  
	public HUD(Player p) {
		player = p;
                 
		try {
			BufferedImage image = ImageIO.read(
				getClass().getResourceAsStream(
					"/HUD/hub.png"
				)
			);
			heart = image.getSubimage(0, 0, 60, 52);
			life = image.getSubimage(0, 52, 60, 54);
                          ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);
                 HuyFont = ttfBase.deriveFont(Font.PLAIN, 24);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
            
            
            
		for(int i = 0; i < player.getHealth(); i++) {
			g.drawImage(heart, 570 + i * 65, 6, null);
		}
		for(int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 570 + i * 65, 50, null);
		}
		g.setColor(java.awt.Color.BLACK);
                g.setFont(HuyFont);
                
                
		g.drawString(player.getTimeToString(), 380, 92);
                g.drawString(player.getLevelToString(), 110, 39);
                 g.drawString(player.getExptoString(), 450, 39);
                                 g.drawString(player.getPointToString(), 150, 90);
	}
	
}













