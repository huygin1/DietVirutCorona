/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sever;

import java.sql.*;
import Main.Game;

/**
 *
 * @author huygi
 */
public class KetNoiMySQL {

    public static boolean Csdl;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private static Game g = new Game();

    public KetNoiMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348197", "sql12348197", "HuyNguyen");
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    public void getData() {
        try {
            String sql = "select * from Player";
            rs = st.executeQuery(sql);
            System.out.println("du lieu tu database online");
            while (rs.next()) {
                String name = rs.getString("name");
                String area = rs.getString("ID");
                String pin = rs.getString("password");
                System.out.println(area + name + pin);
            }
        } catch (Exception e) {
        }
    }

    public static java.sql.Connection getConnect() {
        java.sql.Connection cn = null;
        if (Csdl == false) {

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                cn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348197?characterEncoding=UTF-8", "sql12348197", "HuyNguyen");
           
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            try {

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                String url = "jdbc:sqlserver://localhost:1433; database=DietVirutCorona";
                cn = DriverManager.getConnection(url, "sa", "123");
                
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cn;
    }

}
