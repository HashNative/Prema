/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prema;

import backend.Read;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.StyleConstants.Alignment;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Ilham
 */
public class FinalReport extends javax.swing.JDialog {

    /**
     * Creates new form Suppliers
     */
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public FinalReport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Calendar c = Calendar.getInstance();
      con = backend.DBConnect.ConnectDb();
        jDateChooser1.setDate(c.getTime());
        jDateChooser2.setDate(c.getTime());
        AutoCompleteDecorator.decorate(combo_supplier);
        AutoCompleteDecorator.decorate(combo_product);
        AutoCompleteDecorator.decorate(combo_customer);
        
        ViewSales();
        ViewPurchases();
        FillcomboCustomer();
        FillcomboSupplier();
        FillcomboProduct();
    }

    String userid = null;

    FinalReport(java.awt.Frame parent, boolean modal, String username) {
        super(parent, modal);
        userid = username;
        initComponents();
        Calendar c = Calendar.getInstance();

        con = backend.DBConnect.ConnectDb();

        jDateChooser1.setDate(c.getTime());
        jDateChooser2.setDate(c.getTime());

        ViewSales();
        ViewPurchases();
        FillcomboCustomer();
        FillcomboSupplier();
        FillcomboProduct();

    }

    public void ViewSales() {

        String Date1 = null;
        String Date2 = null;
        try {
            java.util.Date d = jDateChooser1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date1 = sdf.format(d);

            java.util.Date d2 = jDateChooser2.getDate();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date2 = sdf2.format(d2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String paymentmethod = "All";
        if (!"All".equals(combo_payment_type.getSelectedItem())) {
            if ("Credit".equals(combo_payment_type.getSelectedItem())) {
                paymentmethod = "='Credit'";
            } else {
                paymentmethod = "<> 'Credit'";
            }
        }

        DefaultTableModel dtm = (DefaultTableModel) sales_table.getModel();
        dtm.setRowCount(0);

        if ("All".equals(combo_customer.getSelectedItem()) && "All".equals(combo_payment_type.getSelectedItem()) 
                && "All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Date BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "1"+e);

            }
        } else if ("All".equals(combo_customer.getSelectedItem()) && "All".equals(combo_payment_type.getSelectedItem()) && 
                !"All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Product='" + combo_product.getSelectedItem() + "' and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "2"+e);

            }
        } else if ("All".equals(combo_customer.getSelectedItem()) && !"All".equals(combo_payment_type.getSelectedItem()) && 
                "All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Customer='" + combo_customer.getSelectedItem() + "' and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "3"+e);

            }
        } else if ("All".equals(combo_customer.getSelectedItem()) && !"All".equals(combo_payment_type.getSelectedItem())&& 
                !"All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Product='" + combo_product.getSelectedItem() + "' and Payment_method " + paymentmethod + " "
                        + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "4"+e);

            }
        }
         else if (!"All".equals(combo_customer.getSelectedItem()) && "All".equals(combo_payment_type.getSelectedItem())&& 
                "All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Customer='" + combo_customer.getSelectedItem()+"' "
                        + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "5"+e);

            }
        } else if (!"All".equals(combo_customer.getSelectedItem()) && "All".equals(combo_payment_type.getSelectedItem())&& 
                !"All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Customer='" + combo_customer.getSelectedItem() + "' and Product='" + combo_product.getSelectedItem() + "' "
                        + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "6"+e);

            }
        } else if (!"All".equals(combo_customer.getSelectedItem()) && !"All".equals(combo_payment_type.getSelectedItem())&& 
                "All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Customer='" + combo_customer.getSelectedItem() + "' and Payment_method " + paymentmethod + " "
                        + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "7"+e);

            }
        } else if (!"All".equals(combo_customer.getSelectedItem()) && !"All".equals(combo_payment_type.getSelectedItem())&& 
                !"All".equals(combo_product.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from Invoice where Customer='" + combo_customer.getSelectedItem() + "' and Payment_method " + paymentmethod + " "
                        + "and Product = '" + combo_product.getSelectedItem() + "' "
                        + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ino"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    if (!"user".equals(userid)) {
                        v.add(rs.getString("paid"));
                    } else {

                        v.add(Double.parseDouble(rs.getString("total")) * 60 / 100 + "");
                    }
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "8"+e);

            }
        }

    }

    public void ViewPurchases() {
        String Date1 = null;
        String Date2 = null;
        try {
            java.util.Date d = jDateChooser1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date1 = sdf.format(d);

            java.util.Date d2 = jDateChooser2.getDate();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date2 = sdf2.format(d2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultTableModel dtm = (DefaultTableModel) purchase_table.getModel();
        dtm.setRowCount(0);

        String paymentmethod = "All";
        if (!"All".equals(combo_supplier_type.getSelectedItem())) {
            if ("Credit".equals(combo_supplier_type.getSelectedItem())) {
                paymentmethod = "='Credit'";
            } else {
                paymentmethod = "<> 'Credit'";
            }
        }

        if ("All".equals(combo_supplier.getSelectedItem()) && "All".equals(combo_supplier_type.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from GRN where Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("grnno"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    v.add(rs.getString("paid"));
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }
        } else if ("All".equals(combo_supplier.getSelectedItem()) && !"All".equals(combo_supplier_type.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from GRN where Payment_method " + paymentmethod + " and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("grnno"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    v.add(rs.getString("paid"));
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }

        } else if (!"All".equals(combo_supplier.getSelectedItem()) && "All".equals(combo_supplier_type.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from GRN where Supplier='" + combo_supplier.getSelectedItem() + "' and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("grnno"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    v.add(rs.getString("paid"));
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }

        } else if (!"All".equals(combo_supplier.getSelectedItem()) && !"All".equals(combo_supplier_type.getSelectedItem())) {
            try {
                String sql = "Select * "
                        + "from GRN where Supplier='" + combo_supplier.getSelectedItem() + "' and Payment_method " + paymentmethod + " "
                        + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("grnno"));
                    v.add(rs.getString("Product"));
                    if ("0".equals(rs.getString("first_weight")) || "0".equals(rs.getString("first_weight"))) {
                        v.add(rs.getString("net_weight") + " Cubes");
                    } else {
                        v.add(rs.getString("net_weight") + " Kg");
                    }
                    v.add(rs.getString("paid"));
                    v.add(rs.getString("payment_method"));

                    dtm.addRow(v);
                }

                pst.close();
                rs.close();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }

        }

    }

    public void calculateTotal() {

        double salesamount = 0;
        for (int i = 0; i < sales_table.getRowCount(); i++) {

            salesamount = salesamount + Double.parseDouble(sales_table.getValueAt(i, 3).toString());
        }

        double purchaseamount = 0;
        for (int i = 0; i < purchase_table.getRowCount(); i++) {

            purchaseamount = purchaseamount + Double.parseDouble(purchase_table.getValueAt(i, 3).toString());
        }

        sales.setText(salesamount + "");
        purchases.setText(purchaseamount + "");

    }

    private void FillcomboCustomer() {

        try {
            String sql = "select * from Customers";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            Vector v = new Vector();
            v.add("All");
            while (rs.next()) {
                v.add(rs.getString("Name"));
            }
            combo_customer.setModel(new javax.swing.DefaultComboBoxModel(v));

            pst.close();
            rs.close();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void FillcomboSupplier() {

        try {
            String sql = "select * from Suppliers";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            Vector v = new Vector();
            v.add("All");
            while (rs.next()) {
                v.add(rs.getString("Name"));
            }
            combo_supplier.setModel(new javax.swing.DefaultComboBoxModel(v));

            pst.close();
            rs.close();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void generateReport() {

        String Date1 = null;
        String Date2 = null;
        try {
            java.util.Date d = jDateChooser1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date1 = sdf.format(d);

            java.util.Date d2 = jDateChooser2.getDate();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date2 = sdf2.format(d2);

        } catch (Exception e) {
           e.printStackTrace(); 
        }
         
        String EXCEL_FILE_LOCATION = System.getProperty("user.home") + "/Desktop/FinalReport.xls";

        //1. Create an Excel file
        WritableWorkbook myFirstWbook = null;
        try {

            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));

            // create an Excel sheet
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            // add something into the Excel sheet
            
            //Title style
            WritableCellFormat cFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
            font.setUnderlineStyle(UnderlineStyle.DOUBLE);
            cFormat.setFont(font);

            Label label = new Label(6, 1, "Final Report:" + " From " + Date1 + " To " + Date2, cFormat);
            excelSheet.addCell(label);
            
            //Sub title style
            WritableCellFormat cFormat1 = new WritableCellFormat();
            WritableFont font1 = new WritableFont(WritableFont.createFont("Times New Roman"), 16, WritableFont.BOLD);
            font1.setUnderlineStyle(UnderlineStyle.SINGLE);
            cFormat1.setFont(font1);         
           
            //Customer/Supplier details style
            WritableCellFormat cFormat2 = new WritableCellFormat();
            WritableFont font2 = new WritableFont(WritableFont.createFont("Times New Roman"), 13, WritableFont.BOLD);
            cFormat2.setFont(font2);
            
            //cFormat2.setBorder(Border.BOTTOM, BorderLineStyle.DASHED);
                         
             //table header style
            WritableCellFormat cFormat4 = new WritableCellFormat();
            WritableFont font4 = new WritableFont(WritableFont.createFont("Times New Roman"), 13, WritableFont.BOLD);
            cFormat4.setFont(font4);
            
            cFormat4.setBackground(Colour.GRAY_25);
             
             //table content style
            WritableCellFormat cFormat3 = new WritableCellFormat();
            WritableFont font3 = new WritableFont(WritableFont.createFont("Times New Roman"), 11); 
            cFormat3.setFont(font3);
            
            
            cFormat3.setBorder(Border.ALL, BorderLineStyle.THIN);
            
            //Sales -Header
            label = new Label(5, 3, "Sales",cFormat1);   
            excelSheet.addCell(label);

            label = new Label(3, 4, "Customer : ",cFormat2);
            excelSheet.addCell(label);

            label = new Label(4, 4, combo_customer.getSelectedItem().toString(),cFormat2);
            excelSheet.addCell(label);

            label = new Label(6, 4, "Payement : ",cFormat2);
            excelSheet.addCell(label);

            label = new Label(7, 4, combo_payment_type.getSelectedItem().toString(),cFormat2);
            excelSheet.addCell(label);

            //Table - Header
            for (int i = 0; i <= sales_table.getColumnCount() - 1; i++) {
                label = new Label(3 + i, 6, sales_table.getColumnName(i),cFormat4);
                excelSheet.addCell(label);
            }

            //Sales- Details
            String salesfield = null;
               
                for (int j = 0; j < sales_table.getColumnCount(); j++) {
                    for (int i = 0; i < sales_table.getRowCount(); i++) {
                    salesfield = sales_table.getValueAt(i, j).toString();
                    label = new Label(3 + j, 7 + i, salesfield, cFormat3);
                    excelSheet.addCell(label);
                    
                    if(i==sales_table.getRowCount()-1){
                       // System.out.println(i);
                        
                    label = new Label(5, 9 + i, "Total : ",cFormat2);
                    excelSheet.addCell(label);
                    
                    label = new Label(6, 9 + i, sales.getText(),cFormat2);
                    excelSheet.addCell(label);
                    }
                }
            }

            
                
                //Purchase -Header
            label = new Label(11, 3, "Purchase",cFormat1);
            excelSheet.addCell(label);

            label = new Label(9, 4, "Supplier : ",cFormat2);
            excelSheet.addCell(label);

            label = new Label(10, 4, combo_supplier.getSelectedItem().toString(),cFormat2);
            excelSheet.addCell(label);

            label = new Label(12, 4, "Payement : ",cFormat2);
            excelSheet.addCell(label);

            label = new Label(13, 4, combo_supplier_type.getSelectedItem().toString(),cFormat2);
            excelSheet.addCell(label);

            //Table - Header
            for (int i = 0; i <= purchase_table.getColumnCount() - 1; i++) {
                label = new Label(9 + i, 6, purchase_table.getColumnName(i),cFormat4);
                excelSheet.addCell(label);
            }

            //Purchase- Details
            String purchasefield = null;
               
                for (int j = 0; j < purchase_table.getColumnCount(); j++) {
                    for (int i = 0; i < purchase_table.getRowCount(); i++) {
                    purchasefield = purchase_table.getValueAt(i, j).toString();
                    label = new Label(9 + j, 7 + i, purchasefield, cFormat3);
                    excelSheet.addCell(label);
                    
                    if(i==purchase_table.getRowCount()-1){
                       
                    label = new Label(11, 9 + i, "Total : ",cFormat2);
                    excelSheet.addCell(label);
                    
                    label = new Label(12, 9 + i, purchases.getText(),cFormat2);
                    excelSheet.addCell(label);
                    }
                }
            }
                            
            myFirstWbook.write();

             int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to open the Export file?", "Open Confirmation", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
//            try {
//                        Desktop dt = Desktop.getDesktop();
//                        dt.open(new File(EXCEL_FILE_LOCATION));
//                    } catch (Exception e ){
//                                    JOptionPane.showMessageDialog(null, e);
//                    } 
       }  
            JOptionPane.showMessageDialog(null, "Report Printed to " + EXCEL_FILE_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    
    private void FillcomboProduct() {

        try {
            String sql = "select * from Stock";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            Vector v = new Vector();
            v.add("All");
            while (rs.next()) {
                v.add(rs.getString("Product"));
            }
            combo_product.setModel(new javax.swing.DefaultComboBoxModel(v));

            pst.close();
           
        } catch (Exception e) {

            // JOptionPane.showMessageDialog(null,e);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        sales = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        purchases = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        sales_table = new javax.swing.JTable();
        combo_customer = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        purchase_table = new javax.swing.JTable();
        combo_supplier = new javax.swing.JComboBox<>();
        combo_payment_type = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        combo_supplier_type = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        combo_product = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Final Report");

        jLabel12.setText("From :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Total :");

        jLabel6.setText("Payment :");

        jDateChooser1.setDateFormatString("yyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        sales.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sales.setForeground(new java.awt.Color(153, 0, 51));
        sales.setText("0");

        jLabel13.setText("To :");

        jButton1.setText("Export Report");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        purchases.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        purchases.setForeground(new java.awt.Color(153, 0, 51));
        purchases.setText("0");

        jDateChooser2.setDateFormatString("yyy-MM-dd");
        jDateChooser2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Total :");

        jButton3.setText("Filter");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Customer :");

        sales_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sales_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Product", "Weight / Cubes", "Paid Amount", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sales_table.setRowHeight(20);
        sales_table.setSelectionBackground(new java.awt.Color(229, 126, 49));
        sales_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sales_tableMouseClicked(evt);
            }
        });
        sales_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_tableKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(sales_table);

        combo_customer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_customerActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Supplier :");

        purchase_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        purchase_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GRN No", "Product", "Weight / Cubes", "Paid Amount", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchase_table.setRowHeight(20);
        purchase_table.setSelectionBackground(new java.awt.Color(229, 126, 49));
        purchase_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchase_tableMouseClicked(evt);
            }
        });
        purchase_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                purchase_tableKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(purchase_table);

        combo_supplier.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_supplierActionPerformed(evt);
            }
        });

        combo_payment_type.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_payment_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Cash", "Credit" }));
        combo_payment_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_payment_typeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Sales");

        combo_supplier_type.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_supplier_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Cash", "Credit" }));
        combo_supplier_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_supplier_typeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Purchasing");

        jLabel5.setText("Payment :");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Product :");

        combo_product.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_productActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(233, 233, 233))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(sales, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel15)
                                .addComponent(jLabel17))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(combo_product, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(combo_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(combo_payment_type, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combo_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combo_supplier_type, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(purchases, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(438, 438, 438))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_supplier_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_payment_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sales)
                    .addComponent(purchases)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange

    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange

    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void sales_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sales_tableMouseClicked

    }//GEN-LAST:event_sales_tableMouseClicked

    private void sales_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_tableKeyPressed

    }//GEN-LAST:event_sales_tableKeyPressed

    private void combo_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_customerActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_customerActionPerformed

    private void purchase_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchase_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_purchase_tableMouseClicked

    private void purchase_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchase_tableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_purchase_tableKeyPressed

    private void combo_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_supplierActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_supplierActionPerformed

    private void combo_payment_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_payment_typeActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_payment_typeActionPerformed

    private void combo_supplier_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_supplier_typeActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_supplier_typeActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        generateReport();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void combo_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_productActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_productActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FinalReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinalReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinalReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinalReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FinalReport dialog = new FinalReport(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combo_customer;
    private javax.swing.JComboBox<String> combo_payment_type;
    private javax.swing.JComboBox<String> combo_product;
    private javax.swing.JComboBox<String> combo_supplier;
    private javax.swing.JComboBox<String> combo_supplier_type;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable purchase_table;
    private javax.swing.JLabel purchases;
    private javax.swing.JLabel sales;
    private javax.swing.JTable sales_table;
    // End of variables declaration//GEN-END:variables
}
