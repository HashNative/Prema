/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prema;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
        con=backend.DBConnect.ConnectDb();
          jDateChooser1.setDate(c.getTime());
        jDateChooser2.setDate(c.getTime());
        
        ViewSales();
        ViewPurchases();
        FillcomboCustomer();
        FillcomboSupplier();
    }
    
    String userid=null;
    FinalReport(java.awt.Frame parent, boolean modal,String username){
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
    
    }

    
     
    public void ViewSales(){
        
         String Date1=null;
        String Date2=null;
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
        
        String paymentmethod="All";
        if(!"All".equals(combo_customer_type.getSelectedItem())){
            if("Credit".equals(combo_customer_type.getSelectedItem())){
                paymentmethod="='Credit'";
            }else{
                paymentmethod="<> 'Credit'";
            }
        }
        
         DefaultTableModel dtm = (DefaultTableModel) sales_table.getModel();
                        dtm.setRowCount(0);
        
        if("All".equals(combo_customer.getSelectedItem()) && "All".equals(combo_customer_type.getSelectedItem())){
             try {
            String sql = "Select * "
                    + "from Invoice where Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ino"));
                v.add(rs.getString("Product"));
                 if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
                }
                if(!"user".equals(userid)){
                v.add(rs.getString("paid"));
                }else{
                   
                 v.add(Double.parseDouble(rs.getString("total"))*60/100+""); 
                }
                v.add(rs.getString("payment_method"));
               
                dtm.addRow(v);
            }
          
            pst.close();
           rs.close();
        } catch (Exception e) {

           JOptionPane.showMessageDialog(null, e);

        } 
        }else if("All".equals(combo_customer.getSelectedItem()) && !"All".equals(combo_customer_type.getSelectedItem())){
             try {
            String sql = "Select * "
                    + "from Invoice where Payment_method "+paymentmethod+" and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ino"));
                v.add(rs.getString("Product"));
                 if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
                }
                if(!"user".equals(userid)){
                v.add(rs.getString("paid"));
                }else{
                   
                 v.add(Double.parseDouble(rs.getString("total"))*60/100+""); 
                }
                v.add(rs.getString("payment_method"));
               
                dtm.addRow(v);
            }
          
            pst.close();
           rs.close();
        } catch (Exception e) {

           JOptionPane.showMessageDialog(null, e);

        } 
        }else if(!"All".equals(combo_customer.getSelectedItem()) && "All".equals(combo_customer_type.getSelectedItem())){
             try {
            String sql = "Select * "
                    + "from Invoice where Customer='"+combo_customer.getSelectedItem()+"' and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ino"));
                v.add(rs.getString("Product"));
                if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
                }
                if(!"user".equals(userid)){
                v.add(rs.getString("paid"));
                }else{
                   
                 v.add(Double.parseDouble(rs.getString("total"))*60/100+""); 
                }
                v.add(rs.getString("payment_method"));
               
                dtm.addRow(v);
            }
          
            pst.close();
           rs.close();
        } catch (Exception e) {

           JOptionPane.showMessageDialog(null, e);

        } 
        }else if(!"All".equals(combo_customer.getSelectedItem()) && !"All".equals(combo_customer_type.getSelectedItem())){
             try {
            String sql = "Select * "
                    + "from Invoice where Customer='"+combo_customer.getSelectedItem()+"' and Payment_method "+paymentmethod+" "
                    + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ino"));
                v.add(rs.getString("Product"));
                 if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
                }
                if(!"user".equals(userid)){
                v.add(rs.getString("paid"));
                }else{
                   
                 v.add(Double.parseDouble(rs.getString("total"))*60/100+""); 
                }
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
    
    public void ViewPurchases(){
         String Date1=null;
        String Date2=null;
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
                
                         String paymentmethod="All";
        if(!"All".equals(combo_supplier_type.getSelectedItem())){
            if("Credit".equals(combo_supplier_type.getSelectedItem())){
                paymentmethod="='Credit'";
            }else{
                paymentmethod="<> 'Credit'";
            }
        }
         
        if("All".equals(combo_supplier.getSelectedItem()) && "All".equals(combo_supplier_type.getSelectedItem())){
            try {
            String sql = "Select * "
                    + "from GRN where Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("grnno"));
                v.add(rs.getString("Product"));
               if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
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
        }else if("All".equals(combo_supplier.getSelectedItem()) && !"All".equals(combo_supplier_type.getSelectedItem())){
        try {
            String sql = "Select * "
                    + "from GRN where Payment_method "+paymentmethod+" and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("grnno"));
                v.add(rs.getString("Product"));
                  if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
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
        
        }else if(!"All".equals(combo_supplier.getSelectedItem()) && "All".equals(combo_supplier_type.getSelectedItem())){
        try {
            String sql = "Select * "
                    + "from GRN where Supplier='"+combo_supplier.getSelectedItem()+"' and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("grnno"));
                v.add(rs.getString("Product"));
                  if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
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
        
        }else if(!"All".equals(combo_supplier.getSelectedItem()) && !"All".equals(combo_supplier_type.getSelectedItem())){
        try {
            String sql = "Select * "
                    + "from GRN where Supplier='"+combo_supplier.getSelectedItem()+"' and Payment_method "+paymentmethod+" "
                    + "and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("grnno"));
                v.add(rs.getString("Product"));
                if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("first_weight"))){
                v.add(rs.getString("net_weight")+" Cubes");
                }else{
                v.add(rs.getString("net_weight")+" Kg");
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

           JOptionPane.showMessageDialog(null,e);
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

            JOptionPane.showMessageDialog(null,e);
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
        combo_customer_type = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        combo_supplier_type = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

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

        combo_customer_type.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_customer_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Cash", "Credit" }));
        combo_customer_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_customer_typeActionPerformed(evt);
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
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(combo_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(45, 45, 45)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(combo_customer_type, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(purchases, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(455, 455, 455))
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
                    .addComponent(combo_customer_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sales)
                    .addComponent(purchases)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void combo_customer_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_customer_typeActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_customer_typeActionPerformed

    private void combo_supplier_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_supplier_typeActionPerformed
        ViewSales();
        ViewPurchases();
        calculateTotal();
    }//GEN-LAST:event_combo_supplier_typeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JComboBox<String> combo_customer_type;
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
