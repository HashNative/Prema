
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

       try{
             InetAddress addr = InetAddress.getByName("localhost"); //DESKTOP-K4EFD5D
             //DESKTOP-K4EFD5D
String host = addr.getHostAddress();
           Class.forName("com.mysql.jdbc.Driver");
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://"+host+"/prema?zeroDateTimeBehavior=convertToNull","root","");
         return con;  
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           
           return null;
       }
       
       
      
   }
       
}
