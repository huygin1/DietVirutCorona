package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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

public class TestState extends GameState {

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
    private BufferedImage Button15;
    private BufferedImage Button16;
    private BufferedImage huongdan;
    private int currentChoice = 0;
    private String[] options = {
        "Start",
        "2", "3", "4", "5", "6", "7", "8"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;
    private Font font2;
    String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;

    public TestState(GameStateManager gsm) {

        super(gsm);

        try {

            // load floating head
            Button = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(0, 0, 129, 129);

            Button2 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(129, 0, 129, 129);
            Button3 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(258, 0, 129, 129);
            Button4 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(387, 0, 129, 129);
            Button5 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(0, 129, 129, 129);
            Button6 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(129, 129, 129, 129);
            Button7 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(258, 129, 129, 129);
            Button8 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(387, 129, 129, 129);
            Button9 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(0, 258, 129, 129);
            Button10 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(129, 258, 129, 129);
            Button11 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(258, 258, 129, 129);
            Button12 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(387, 258, 129, 129);
            Button13 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(0, 387, 129, 129);
            Button14 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(129, 387, 129, 129);
            Button15 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(258, 387, 129, 129);
            Button16 = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/test.png")).getSubimage(387, 387, 129, 129);
            // titles and fonts
            titleColor = Color.PINK;
            titleFont = new Font("Times New Roman", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 14);
            font2 = new Font("Arial", Font.PLAIN, 10);

            ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);
            HuyFont = ttfBase.deriveFont(Font.PLAIN, 40);

            huongdan = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/menu.png")
            );
            // load sound fx

//			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
//			JukeBox.load("/SFX/menuselect.mp3", "menuselect");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
    }

    public void update() {

        // check keys
        handleInput();

    }

    public void draw(Graphics2D g) {

        // draw bg
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int)GamePanel.WIDTH, (int)GamePanel.HEIGHT);

        // draw title
        g.setColor(titleColor);
        g.setFont(HuyFont);

        // draw menu options
        g.setFont(HuyFont);

        g.setColor(Color.ORANGE);
        if (currentChoice == 0) {
            g.drawImage(Button2, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3, (int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7,(int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9, (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11, (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);

        } else if (currentChoice == 1) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button4,(int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9,  (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11,  (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13,(int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);

        } else if (currentChoice == 2) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3, (int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button6,(int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9, (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11,(int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);
        } else if (currentChoice == 3) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3, (int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button8,(int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9,  (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11, (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13,(int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);
        } else if (currentChoice == 4) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3,(int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button10, (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11, (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);
        } else if (currentChoice == 5) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3, (int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9,  (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button12, (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);
        } else if (currentChoice == 6) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3, (int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9, (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11, (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button14, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button15, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);
        } else if (currentChoice == 7) {
            g.drawImage(Button, (int) GamePanel.WIDTH / 2 - 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button3, (int) GamePanel.WIDTH / 2 - 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button5, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button7, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 - 150, null);
            g.drawImage(Button9, (int) GamePanel.WIDTH / 2 -300, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button11, (int) GamePanel.WIDTH / 2 -100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button13, (int) GamePanel.WIDTH / 2 + 100, (int) GamePanel.HEIGHT / 2 +50, null);
            g.drawImage(Button16, (int) GamePanel.WIDTH / 2 + 300, (int) GamePanel.HEIGHT / 2 +50, null);
        }
      
     

    }

    private void select() {
        if (currentChoice == 0) {
            at.playMenuSelect();
//			JukeBox.play("menuselect");
            PlayerSave.init();

            gsm.setState(GameStateManager.LEVEL1);
        } else if (currentChoice == 1) {

            at.playMenuSelect();
            gsm.setState(GameStateManager.LEVEL2);
        } else if (currentChoice == 2) {
           
            at.playMenuSelect();
        } else if (currentChoice == 3) {
            at.playMenuSelect();

        } else if (currentChoice == 4) {

            at.playMenuSelect();
        } else if (currentChoice == 5) {

            at.playMenuSelect();
        } else if (currentChoice == 6) {

            at.playMenuSelect();
        } else if (currentChoice == 7) {

            at.playMenuSelect();
        }
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        }
        if (Keys.isPressed(Keys.TRAI)) {
            if (currentChoice > 0) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);

                currentChoice--;
            }
        }
        if (Keys.isPressed(Keys.PHAI)) {
            if (currentChoice < options.length - 1) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                currentChoice++;
            }
        }
        if (Keys.isPressed(Keys.UP)) {
            if (currentChoice > 3) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                currentChoice -= 4;
            }
        }
        if (Keys.isPressed(Keys.DOWN)) {
            if (currentChoice < 4) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                currentChoice += 4;
            }
        }
        if (Keys.isPressed(Keys.ESCAPE)) {
            if (currentChoice < 4) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                gsm.setState(GameStateManager.MENUSTATE);
            }
        }

    }

}
