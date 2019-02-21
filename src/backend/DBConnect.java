
package backend;

import java.net.InetAddress;
import java.sql.*;
import javax.swing.*;


public class DBConnect {

   Connection con = null;
   public static Connection ConnectDb(){
      
       /*
       try{
           
         Class.forName("org.sqlite.JDBC");
         Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Ilham\\Desktop\\a.sql");
         
         return con;  
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           
           return null;
       }
       
      */

       Read r = new Read();
       try{
            InetAddress addr = InetAddress.getByName(r.getProperty("host")); //DESKTOP-K4EFD5D
            //DESKTOP-K4EFD5D
            String host = addr.getHostAddress();
            Class.forName("com.mysql.jdbc.Driver");
            //Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://"+host+"/Prema?zeroDateTime,Behavior=convertToNull",r.getProperty("username"),r.getProperty("password"));
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://"+host+"/"+r.getProperty("database")+"?zeroDateTimeBehavior=convertToNull",r.getProperty("username"),r.getProperty("password"));
            return con;  
       }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
       }
       
       
      
   }
       
}