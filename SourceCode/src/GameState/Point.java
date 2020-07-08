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
import amThanh.amThanh;
import java.awt.Font;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Point extends GameState {
      private amThanh at = new amThanh();
    String fontFileName = "/Fonts/HuyFont.otf";

    InputStream fonthuy = this.getClass().getResourceAsStream(fontFileName);
    private Font ttfBase;
    private Font HuyFont;
    private float hue;
    private Color color;
    private String[] Name = new String[200];
  
    private int[] LevelDB = new int[200];

    private int[] StateDB = new int[200];
    private String[] NameSX = new String[200];
    private int[] PointDB = new int[200];
 private int[] IDDB = new int[200];
    private String Namec;

    private int lv;
    private int state;
    private int point;
  
    private int[] ID = new int[200];
    private double angle;
    private BufferedImage image;
     private BufferedImage imageh;
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
    int po;
    private void D2() {
        Connection cn = KetNoiMySQL.getConnect();
 String s;
        try {
     
                 s = "select * from HighScore ORDER BY HighPoint DESC;";
           
           
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            i = 0;
            while (rs.next()) {

               
                PointDB[i] = new Integer(rs.getInt(3));
                IDDB[i]= new Integer(rs.getInt(2));
                i++;

            }
          po = i;

            //5. dong resources
            sta.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Point(GameStateManager gsm) {
        super(gsm);

        try {
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, fonthuy);

            if (gp.checksc == true) {
                HuyFont = ttfBase.deriveFont(Font.PLAIN, 28);
                image = ImageIO.read(
                        getClass().getResourceAsStream("/State/pointmini.jpg")
                );
                 imageh = ImageIO.read(
                        getClass().getResourceAsStream("/State/hub.png")
                );
            } else {
                HuyFont = ttfBase.deriveFont(Font.PLAIN, 36);
                image = ImageIO.read(
                        getClass().getResourceAsStream("/State/point.jpg")
                );
                 imageh = ImageIO.read(
                        getClass().getResourceAsStream("/State/hub2.png")
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
 
    public void draw(Graphics2D g) {
        D1();
        D2();

        for(int a = 0 ; a< 10; a++){
            for(int u = 0; u < o; u ++){
                 if( IDDB[a] == ID[u]){
                     NameSX[a] = Name[u];
                 }
            }
           
        }
   System.out.println(lv);
                   int co ;
                    if(po < 10){
                co = po;
            }else{
                co = 10;
            }
        if (gp.checksc == true) {
             g.drawImage(image, 0, 0, null);
            g.setColor(Color.BLACK);
            g.setFont(HuyFont);
          
           
                    
            for(int k = 0; k< co;k++){
                 g.drawString(PointDB[k]+"", 986, 220+(k*42));
                 g.drawString(NameSX[k], 377,  220+(k*42));
            }
        } else {
            g.drawImage(image, 0, 0, null);
            
            g.setColor(Color.BLACK);
            g.setFont(HuyFont);
            for(int k = 0; k< co;k++){
                 g.drawString(PointDB[k]+"", 1481, 322+(k*64));
                 g.drawString(NameSX[k], 566,  322+(k*64));
            }
           
         
        }
         g.drawImage(imageh,(int) GamePanel.WIDTH / 2 - imageh.getWidth() / 2, (int) (GamePanel.HEIGHT / 2 + GamePanel.HEIGHT / 3 + GamePanel.HEIGHT / 20),null);
    }

    public void handleInput() {
        
        if (Keys.isPressed(Keys.U)) {
               at.playMenuSelect();
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

}
