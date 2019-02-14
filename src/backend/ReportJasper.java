/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Silencer
 */
public class ReportJasper {

    public static void printInvoice(String filename, HashMap<String, Object> params) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(filename);
           
         InetAddress addr = InetAddress.getByName("localhost");
String host = addr.getHostAddress();
         Class.forName("com.mysql.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://"+host+"/Prema","root","123");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
            //JasperViewer.viewReport(jasperPrint, false);
           JasperPrintManager.printReport(jasperPrint, false);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


