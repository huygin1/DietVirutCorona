package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import GameState.*;
import Handlers.Keys;
import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class PauseState extends GameState {

    String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;
    private Font font;
    private BufferedImage image;

    public PauseState(GameStateManager gsm) {

        super(gsm);
        try {
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);

            HuyFont = ttfBase.deriveFont(Font.PLAIN, 32);

            HuyFont = ttfBase.deriveFont(Font.PLAIN, 48);

            image = ImageIO.read(
                    getClass().getResourceAsStream("/State/hub3.png")
            );

        } catch (Exception e) {
        }
        // fonts
        font = new Font("Century Gothic", Font.PLAIN, 14);

    }

    public void init() {
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(HuyFont);
        g.drawString("TẠM DỪNG", (int) GamePanel.WIDTH / 2 - 115, (int) GamePanel.HEIGHT / 2 - 25);
        g.drawImage(image, (int) GamePanel.WIDTH / 2 - (int)image.getWidth()/2, (int) GamePanel.HEIGHT / 2 + (int)image.getHeight()*2,null);
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(false);
        }
        if (Keys.isPressed(Keys.U)) {
     
            gsm.setPaused(false);
        
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

}
