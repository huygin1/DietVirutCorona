package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Main.Game;
import javax.imageio.ImageIO;
import amThanh.amThanh;

import Entity.PlayerSave;
import Handlers.Keys;
import Main.GamePanel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import Sever.KetNoiMySQL;
import java.awt.Image;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DB extends GameState {

    private amThanh at = new amThanh();
    private BufferedImage Button;
    private BufferedImage Button2;
    private BufferedImage Button3;
    private BufferedImage Button4;
    private BufferedImage Button5;
    private BufferedImage Button6;
    private BufferedImage Button7;
    private BufferedImage Button8;
    private BufferedImage Button9;
    private BufferedImage Button10;
    private BufferedImage Button11;
    private BufferedImage Button12;
    private BufferedImage Button13;
    private BufferedImage Button14;
    private BufferedImage loading;
    private BufferedImage hand;
    private BufferedImage huongdan;

    Image image1;

    private int currentChoice = 0;
    private String[] options = {
        "Start",
        "2"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;
    private Font font2;
    String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;
    private Font HuyFont2;

    public DB(GameStateManager gsm) {

        super(gsm);

        try {

            // load floating head
            // titles and fonts
            titleColor = Color.PINK;
            titleFont = new Font("Times New Roman", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 14);
            font2 = new Font("Arial", Font.PLAIN, 10);

            ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);
            HuyFont = ttfBase.deriveFont(Font.PLAIN, 40);
            HuyFont2 = ttfBase.deriveFont(Font.PLAIN, 45);
            huongdan = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/menu.png")
            );
            hand = ImageIO.read(
                    getClass().getResourceAsStream("/State/hand.png")
            );
            loading = ImageIO.read(
                    getClass().getResourceAsStream("/State/loading.gif")
            );
            image1 = Toolkit.getDefaultToolkit().createImage("/State/loading.gif");
            // load sound fx

//			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
//			JukeBox.load("/SFX/menuselect.mp3", "menuselect");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
    }
    int ld = 0;
    boolean checkld = false;

    public void update() {

        ld++;
        if (ld > 280) {
            handleInput();
        }
    }

    public void draw(Graphics2D g) {

        if (checkld == false) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT);
            g.setColor(Color.ORANGE);
            g.setFont(HuyFont2);
            g.drawString("Loading", (int) (GamePanel.WIDTH) / 2 - 50, 400);
            g.drawRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 1200, 50);
            if (ld < 50) {
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + ld, 50, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + ld, 49, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + ld, 48, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + ld, 47, true);
            } else if (ld < 100) {
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 2), 50, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 2), 49, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 2), 48, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 2), 47, true);

            } else if (ld < 200) {
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 3), 50, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 3), 49, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 3), 48, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 3), 47, true);

            } else if (ld < 300) {

                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 4), 50, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 4), 49, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 4), 48, true);
                g.fill3DRect((int) (GamePanel.WIDTH - 1200) / 2, 500, 12 + (ld * 4), 47, true);

            }
            if (ld == 1) {
                at.playMenuSelect();
            }
            if (ld == 150) {
                at.playMenuSelect();
            }
            if (ld == 300) {
                checkld = true;
                at.playMenuSelect();
            }

        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT);

            // draw title
            g.setColor(titleColor);
            g.setFont(HuyFont2);
            g.setColor(Color.BLACK);

            g.drawString("Chọn Cơ sở dữ liệu để lưu DATA", (int) GamePanel.WIDTH / 2 - 330, (int) GamePanel.HEIGHT / 2 - 250);

            // draw menu options
            g.setFont(HuyFont);

            g.setColor(Color.ORANGE);
            if (currentChoice == 0) {
                g.setColor(Color.GRAY);
                g.drawImage(hand, ((int) GamePanel.WIDTH - ("Local".length() * 55)) / 2, (int) GamePanel.HEIGHT / 2 - 135, null);
                g.drawString("Local", ((int) GamePanel.WIDTH - ("Local".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);
                g.setColor(Color.ORANGE);
                g.drawString("Cloud", ((int) GamePanel.WIDTH - ("Cloud".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

            } else if (currentChoice == 1) {

                g.setColor(Color.ORANGE);
                g.drawImage(hand, ((int) GamePanel.WIDTH - ("Local".length() * 55)) / 2, (int) GamePanel.HEIGHT / 2 - 85, null);
                g.drawString("Local", ((int) GamePanel.WIDTH - ("Local".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);
                g.setColor(Color.GRAY);
                g.drawString("Cloud", ((int) GamePanel.WIDTH - ("Cloud".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);
                
            }
          g.drawImage(huongdan, ((int) GamePanel.WIDTH - 350), (int) GamePanel.HEIGHT - 255, null);
            g.setFont(HuyFont);

        }

        // draw bg
    }

    private void select() {
        if (currentChoice == 0) {
            at.playMenuSelect();
            KetNoiMySQL.Csdl = true;
            gsm.setState(GameStateManager.MENUSTATE);
        } else if (currentChoice == 1) {
            at.playMenuSelect();
            KetNoiMySQL.Csdl = false;
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        }

        if (Keys.isPressed(Keys.UP)) {
            if (currentChoice > 0) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                currentChoice -= 1;
            }
        }
        if (Keys.isPressed(Keys.DOWN)) {
            if (currentChoice < 1) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                currentChoice += 1;
            }
        }

    }

}
