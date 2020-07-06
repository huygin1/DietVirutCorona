package GameState;

import Entity.PlayerSave;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Handlers.Keys;
import Main.GamePanel;
import Sever.KetNoiMySQL;
import java.awt.Font;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class info extends GameState {

    String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;
    private float hue;
    private Color color;
    private String[] Name = new String[200];
    private String[] Email = new String[200];
    private int[] LevelDB = new int[200];

    private int[] StateDB = new int[200];

    private int[] PointDB = new int[200];
 private int[] IDDB = new int[200];
    private String Namec;
    private String Emailc;
    private int lv;
    private int state;
    private int point;
  
    private int[] ID = new int[200];
    private double angle;
    private BufferedImage image;
    GamePanel gp = new GamePanel();
    MenuState m;
    private int i;
    private int o;

    private void D1() {
        Connection cn = KetNoiMySQL.getConnect();

        try {

            String s = "select * from Player;";
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            i = 0;
            while (rs.next()) {

                Name[i] = new String(rs.getString(2));
                Email[i] = new String(rs.getString(3));
                ID[i] = new Integer(rs.getInt(1));
                i++;

            }
            o = i;

            //5. dong resources
            sta.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void D2() {
        Connection cn = KetNoiMySQL.getConnect();

        try {

            String s = "select * from Character;";
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            i = 0;
            while (rs.next()) {

                LevelDB[i] = new Integer(rs.getInt(3));
                StateDB[i] = new Integer(rs.getInt(4));
                PointDB[i] = new Integer(rs.getInt(9));
                IDDB[i]= new Integer(rs.getInt(2));
                i++;

            }
            o = i;

            //5. dong resources
            sta.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public info(GameStateManager gsm) {
        super(gsm);

        try {
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);

            if (gp.checksc == true) {
                HuyFont = ttfBase.deriveFont(Font.PLAIN, 32);
                image = ImageIO.read(
                        getClass().getResourceAsStream("/State/infomini.jpg")
                );
            } else {
                HuyFont = ttfBase.deriveFont(Font.PLAIN, 48);
                image = ImageIO.read(
                        getClass().getResourceAsStream("/State/InFO.jpg")
                );
            }

        } catch (Exception e) {
        }
    }

    public void init() {
    }

    public void update() {
        handleInput();

    }
    public String lv(){
        return lv +"";
    }
    public String state(){
        return state +"";
    }
    public String point(){
        return point +"";
    }
    public void draw(Graphics2D g) {
        D1();
        D2();
        for (int j = 0; j < o; j++) {
            if (ID[j] == PlayerSave.IDU ) {
                Emailc = Email[j];
                Namec = Name[j];
              lv = LevelDB[j];
                state = StateDB[j];
                point = PointDB[j];
             
                break;
            }
            

        }
        
   System.out.println(lv);
                  System.out.println(lv());
        if (gp.checksc == true) {
            g.drawImage(image, 0, 0, null);
            g.setColor(Color.BLACK);
            g.setFont(HuyFont);
            g.drawString(Namec, 100, 100);
            g.drawString(Emailc, 100, 200);
        } else {
            g.drawImage(image, 0, 0, null);
            g.setColor(Color.BLACK);
            g.setFont(HuyFont);
            g.drawString(Namec, 400, 345);
            g.drawString(Emailc, 400, 398);
            g.drawString(lv(), 1650, 340);
             g.drawString(lv(), 1650, 408);
                g.drawString(point(), 1650, 470);
            g.drawString(state(), 1650, 575);
         
        }

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if (Keys.isPressed(Keys.U)) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

}
