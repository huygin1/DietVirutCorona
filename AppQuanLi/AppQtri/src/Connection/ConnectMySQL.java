
package Connection;

import static Form.SelectSever.a;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySQL {

  public static  java.sql.Connection getConnect(boolean a){
         //connect database
         Connection conn=null;
         String ur1;
         String user;
         String password;
         if(a == false){
             ur1    = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348197";
             user = "sql12348197";
             password = "HuyNguyen";
               
        try {
            conn = DriverManager.getConnection(ur1, user, password);
            System.out.println("Database connected!");
}
catch (SQLException ex) {
            throw new IllegalStateException("Cannot connect the database!", ex);
        }
      return conn;
         }
         if(a==true){
             ur1    = "jdbc:sqlserver://localhost:1433; database=DoAnJava";
             user = "sa";
             password = "123";
               
        try {
            conn = DriverManager.getConnection(ur1, user, password);
            System.out.println("Database connected!");
}
catch (SQLException ex) {
            throw new IllegalStateException("Cannot connect the database!", ex);
        }
      return conn;
       
  }
      return conn;

    }
  
   
} 