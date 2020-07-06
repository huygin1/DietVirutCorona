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
    private BufferedImage Button15;
    private BufferedImage Button16;
    private BufferedImage huongdan;
    
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
         g.setColor(Color.BLACK);
        g.drawString("Chọn Cơ sở dữ liệu để lưu DATA", ((int) GamePanel.WIDTH - ("Chọn Cơ sở dữ liệu để lưu DATA".length() * 20)) / 2, (int) GamePanel.HEIGHT / 2 - 300);
        // draw menu options
        g.setFont(HuyFont);

        g.setColor(Color.ORANGE);
        if (currentChoice == 0) {
            g.setColor(Color.GRAY);
                g.drawString("Local", ((int) GamePanel.WIDTH - ("Local".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);
                g.setColor(Color.ORANGE);
                g.drawString("Cloud", ((int) GamePanel.WIDTH - ("Cloud".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

        } else if (currentChoice == 1) {
            
                  g.setColor(Color.ORANGE);
                  
                g.drawString("Local", ((int) GamePanel.WIDTH - ("Local".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 100);
                   g.setColor(Color.GRAY);
                g.drawString("Cloud", ((int) GamePanel.WIDTH - ("Cloud".length() * 25)) / 2, (int) GamePanel.HEIGHT / 2 - 50);

        }
        g.setFont(HuyFont);
       

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
        if (Keys.isPressed(Keys.ESCAPE)) {
            if (currentChoice < 4) {
                at.playMenuOption();
//				JukeBox.play("menuoption", 0);
                gsm.setState(GameStateManager.MENUSTATE);
            }
        }

    }

}
