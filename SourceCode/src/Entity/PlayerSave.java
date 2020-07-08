package Entity;

import Sever.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerSave {
    public static int IDU;
    public static int total;
    private static int lives = 3;
    private static int health = 5;
    private static long time = 0;
    private static int level = 1;
    public static int exp = 0;
    public static int state;
    private static int save1 = 0;
    public static int point;
    private static int[] LevelDB = new int[200];
    private static int[] LivesDB = new int[200];
    private static int[] HeartDB = new int[200];
    private static int[] StateDB = new int[200];
    private static int[] ExpDB = new int[200];
    private static int[] TotalDB = new int[200];
    private static int[] PointDB = new int[200];
    private static int[] PlayerDB = new int[200];
    private static int[] ThoiGianDB = new int[200];
    private static int p;
    private static int q;
    public static void init() {
        lives = 3;
        health = 5;
        time = 0;
        level = 0;
        exp = 0;

         Connection cn = KetNoiMySQL.getConnect();
         
        try {
            String s;
            if(KetNoiMySQL.Csdl == false){
               s   = "SELECT * FROM `Character`";
            }else{
                    s   = "SELECT * FROM Character";
            }
           
            Statement sta = cn.createStatement();

            ResultSet rs = sta.executeQuery(s);
            p = 0;
            while (rs.next()) {

                LevelDB[p] = new Integer(rs.getInt(3));
                StateDB[p] = new Integer(rs.getInt(4));
                HeartDB[p] = new Integer(rs.getInt(5));
                LivesDB[p] =  new Integer(rs.getInt(6));
                ExpDB[p] =  new Integer(rs.getInt(7));
                TotalDB[p] = new Integer(rs.getInt(8));
                PointDB[p] =  new Integer(rs.getInt(9));
                ThoiGianDB[p] = new Integer(rs.getInt(10));
                PlayerDB[p] =  new Integer(rs.getInt(2));
                
                p++;

            }
            q = p;

            //5. dong resources
            sta.close();
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (int j = 0; j < q; j++) {
                        if (PlayerDB[j] == (IDU)) {
                             lives =  LivesDB[j];
                                         health = HeartDB[j];
                            point = PointDB[j];
                                     level = LevelDB[j];
                                 
                                     state = StateDB[j];
                             exp = ExpDB[j];
                         total = TotalDB[j];
                         time =  ThoiGianDB[j];
                            break;
                        }

                    }
    }

    public static int getsave1() {
        return save1;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int i) {
        lives = i;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int i) {
        health = i;
    }

    public static void setsave1(int i) {
        save1 = i;
    }

    public static long getTime() {
        return time;
    }

    public static void setTime(long t) {
        time = t;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int i) {
        level = i;
    }

    public static int getExp() {
        return exp;
    }

    public static void setExp(int i) {
        exp = i;
    }

}
