/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prema;

import backend.Read;
import backend.ReportJasper;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ilham
 */
public class SalesReports extends javax.swing.JDialog {

    /**
     * Creates new form Suppliers
     */
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public SalesReports(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Calendar c = Calendar.getInstance();
       
        con = backend.DBConnect.ConnectDb();
      
        FillcomboCustomer();
        
        jDateChooser2.setDate(c.getTime());
        jDateChooser3.setDate(c.getTime());
        
        ViewInvoice();
       
       
    }
       
   
    
    
    
    String userid=null;
     public SalesReports(java.awt.Frame parent, boolean modal, String username) {
            super(parent, modal);
        initComponents();
        userid=username;
        Calendar c = Calendar.getInstance();
       
        con = backend.DBConnect.ConnectDb();
       
        FillcomboCustomer();
        
        jDateChooser2.setDate(c.getTime());
        jDateChooser3.setDate(c.getTime());
        
        ViewInvoice();
       
       
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
            combo_customer_search.setModel(new javax.swing.DefaultComboBoxModel(v));
            pst.close();
            rs.close();
        } catch (Exception e) {

         JOptionPane.showMessageDialog(null,e);
        }
       
    }
 
    public void ViewInvoice() {
        String Date1=null;
        String Date2=null;
        try {
            java.util.Date d = jDateChooser2.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date1 = getNextDate(sdf.format(d), -1);
            
            java.util.Date d2 = jDateChooser3.getDate();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date2 = sdf2.format(d2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
         DefaultTableModel dtm = (DefaultTableModel) invoicetable.getModel();
                        dtm.setRowCount(0);

          //Invoice 
          if("All".equals(combo_customer_search.getSelectedItem())){
    try {
            String sql = "Select * "
                    + "from Invoice where  Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ino"));
                v.add(rs.getString("Date"));
                v.add(rs.getString("User"));
                v.add(rs.getString("Customer"));
                v.add(rs.getString("Plate_no"));
                v.add(rs.getString("Product"));
                 if(!"user".equals(userid)){
                     if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("second_weight"))){
                    v.add(rs.getString("net_weight")+" Cubes");
                    }else{
                    v.add(rs.getString("net_weight")+" Kg");
                    }
              //  v.add(rs.getString("net_weight"));
                    v.add(rs.getString("discount"));
                    v.add(rs.getString("transport"));
                    v.add(rs.getString("total"));
                }else{
                    if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("second_weight"))){
                    v.add(Double.parseDouble(rs.getString("net_weight"))*60/100+" Cubes");
                    }else{
                    v.add(Double.parseDouble(rs.getString("net_weight"))*60/100+" Kg");
                    }
               // v.add(Double.parseDouble(rs.getString("net_weight"))*60/100+"");
                    v.add(Double.parseDouble(rs.getString("discount"))*60/100+"");
                    v.add(Double.parseDouble(rs.getString("transport"))*60/100+"");
                    v.add(Double.parseDouble(rs.getString("total"))*60/100+"");
                }
                v.add(rs.getString("payment_method"));
               
                dtm.addRow(v);
            }
          
            pst.close();
           rs.close();
        } catch (Exception e) {

           JOptionPane.showMessageDialog(null, "1"+e);

        } 
          }else{
          
          try {
            String sql = "Select * "
                    + "from Invoice where Customer='"+combo_customer_search.getSelectedItem()+"' and Date  BETWEEN '" + Date1 + "%" + "' AND '" + Date2 + "%" + "' ";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ino"));
                v.add(rs.getString("Date"));
                v.add(rs.getString("User"));
                v.add(rs.getString("Customer"));
                v.add(rs.getString("Plate_no"));
                v.add(rs.getString("Product"));
                 if(!"user".equals(userid)){
                     if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("second_weight"))){
                    v.add(rs.getString("net_weight")+" Cubes");
                    }else{
                    v.add(rs.getString("net_weight")+" Kg");
                    }
               // v.add(rs.getString("net_weight"));
                v.add(rs.getString("discount"));
                v.add(rs.getString("transport"));
                v.add(rs.getString("total"));
                }else{
                     if("0".equals(rs.getString("first_weight")) ||"0".equals(rs.getString("second_weight"))){
                    v.add(Double.parseDouble(rs.getString("net_weight"))*60/100+" Cubes");
                    }else{
                    v.add(Double.parseDouble(rs.getString("net_weight"))*60/100+" Kg");
                    }
               // v.add(Double.parseDouble(rs.getString("net_weight"))*60/100+"");
                    v.add(Double.parseDouble(rs.getString("discount"))*60/100+"");
                    v.add(Double.parseDouble(rs.getString("transport"))*60/100+"");
                    v.add(Double.parseDouble(rs.getString("total"))*60/100+"");
                }
                v.add(rs.getString("payment_method"));
               
                dtm.addRow(v);
            }
          
            pst.close();
           rs.close();
        } catch (Exception e) {

           JOptionPane.showMessageDialog(null, "2"+e);

        } 
          
          }
         
    
    }

   public String getNextDate(String curDate, int days) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = format.parse(curDate);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, days);
            return format.format(calendar.getTime());
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "7 "+e);

        }
        return null;
}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel16 = new javax.swing.JLabel();
        combo_customer_search = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane5 = new javax.swing.JScrollPane();
        invoicetable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sales Report");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Customer :");

        combo_customer_search.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_customer_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_customer_searchActionPerformed(evt);
            }
        });

        jLabel12.setText("From :");

        jLabel13.setText("To :");

        jDateChooser3.setDateFormatString("yyy-MM-dd");
        jDateChooser3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });

        jButton3.setText("Filter");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jDateChooser2.setDateFormatString("yyy-MM-dd");
        jDateChooser2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        invoicetable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        invoicetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Date", "User", "Customer", "Plate No", "Product", "Weight / Cubes", "Discount", "Transport", "Total", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        invoicetable.setRowHeight(20);
        invoicetable.setSelectionBackground(new java.awt.Color(229, 126, 49));
        invoicetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invoicetableMouseClicked(evt);
            }
        });
        invoicetable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                invoicetableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                invoicetableKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(invoicetable);

        jButton1.setText("Print Invoice");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo_customer_search, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 180, Short.MAX_VALUE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(423, 423, 423))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_customer_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void combo_customer_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_customer_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_customer_searchActionPerformed

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange

    }//GEN-LAST:event_jDateChooser3PropertyChange

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ViewInvoice();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange

    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void invoicetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoicetableMouseClicked

    }//GEN-LAST:event_invoicetableMouseClicked

    private void invoicetableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_invoicetableKeyPressed
      if ("admin".equals(userid)) {
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if(invoicetable.getSelectedRowCount()>0){
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to you want to Delete Invoice?", "Delete Confirmation", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {

                    try {
                        String sql1 = "delete from invoice where "
                        + "ino='" + (invoicetable.getModel().getValueAt(invoicetable.getSelectedRow(), 0).toString()) + "' "
                        + "and Date='" + (invoicetable.getModel().getValueAt(invoicetable.getSelectedRow(), 1).toString()) + "'";

                        PreparedStatement pst = con.prepareStatement(sql1);
                        pst.executeUpdate();
                        pst.close();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                    //If selected GRN

                    try {

                        String sql = "update Stock set weight=weight+'" + invoicetable.getValueAt(invoicetable.getSelectedRow(), 5).toString() + "' where Product='C-1' ";

                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.executeUpdate();
                        pst.close();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                    JOptionPane.showMessageDialog(this, "Deleted Successfully");
                    DefaultTableModel dtm1 = (DefaultTableModel) invoicetable.getModel();
                    dtm1.setRowCount(0);

                    ViewInvoice();

                }

            }
        }  
      }
        
    }//GEN-LAST:event_invoicetableKeyPressed

    private void invoicetableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_invoicetableKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_invoicetableKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Read r = new Read();
                  try {

                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("ID", invoicetable.getModel().getValueAt(invoicetable.getSelectedRow(), 0).toString() + "");
                    params.put("image_url",(r.getProperty("path")) + "\\Prema\\Logo.PNG");
                    String category = "Kg";
                    if("Kg".equals(invoicetable.getModel().getValueAt(invoicetable.getSelectedRow(), 6).toString().split(" ")[1])){

                    }else{
                      category="Cube";
                    }
                    params.put("sales_category", category);
                    if(!"Credit".equals(invoicetable.getModel().getValueAt(invoicetable.getSelectedRow(), 10).toString())){
                    ReportJasper.printInvoice((r.getProperty("path")) + "\\Prema\\InvoiceNew.jrxml", params);
                    }else{
                    ReportJasper.printInvoice((r.getProperty("path")) + "\\Prema\\InvoiceCreditSale.jrxml", params);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            java.util.logging.Logger.getLogger(SalesReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SalesReports dialog = new SalesReports(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> combo_customer_search;
    private javax.swing.JTable invoicetable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
