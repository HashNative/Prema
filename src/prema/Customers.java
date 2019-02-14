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
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ilham
 */
public class Customers extends javax.swing.JDialog {

    /**
     * Creates new form Suppliers
     */
     Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public Customers(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        con=backend.DBConnect.ConnectDb();
        FillTableSupplier();
        FillTableStock();
    }

     public boolean checkExistency() {
        boolean isexist = false;
        String sql = "SELECT * FROM CUSTOMERS where name=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, txt_customer.getText());

            rs = pst.executeQuery();

            if (rs.next()) {
                isexist = true;
            } else {
                isexist = false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return isexist;
    }

    private void FillTableSupplier() {
         DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        try {
            String sql = "Select * from Customers";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("Name"));
                v.add(rs.getString("Contact"));
                v.add(rs.getString("Address"));
                v.add(rs.getString("Due"));
                v.add(rs.getString("vatnbt"));
               
                dtm.addRow(v);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
    private void FillTableStock() {
         DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
        dtm.setRowCount(0);
        try {
            String sql = "Select * from Stock";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("Product"));
                v.add("0");
                v.add("0");
               
                dtm.addRow(v);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    
    
    public void InsertPrice(){
    
      DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            try {
                
                 for (int i = 0; i <= dtm.getRowCount() - 1; i++) {
                try {

                    String sql = "Insert into Personalprice(customer,Product,Price,Cube_price) "
                            + "values(?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, txt_customer.getText());
                    pst.setString(2, jTable2.getModel().getValueAt(i, 0).toString());
                    pst.setString(3, jTable2.getModel().getValueAt(i, 1).toString());
                    pst.setString(4, jTable2.getModel().getValueAt(i, 2).toString());
                   
                    pst.executeUpdate();
                    pst.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);

                }
                 }
               
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
    
    }
    
    
     public void UpdatePrice(){
    
      DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            try {
                
                 for (int i = 0; i <= dtm.getRowCount() - 1; i++) {
              
                try {

                        String sql = "update Personalprice set Price='" + jTable2.getModel().getValueAt(i, 1).toString() + "',"
                                      +"Cube_price='" + jTable2.getModel().getValueAt(i, 2).toString() + "'"
                                + " where Product='" + jTable2.getModel().getValueAt(i, 0).toString() + "' and Customer='" + txt_customer.getText() + "'";

                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.executeUpdate();
                        pst.close();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                
                
                 }
               
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

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

        txt_contact = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_due = new javax.swing.JTextField();
        txt_customer = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customers");

        txt_contact.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_contact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_contactMouseClicked(evt);
            }
        });
        txt_contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactKeyReleased(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact number", "Address", "Due", "VAT/NBT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(22);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Address :");

        txt_address.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_addressMouseClicked(evt);
            }
        });
        txt_address.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_addressKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Due / Outstanding:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Customer Name: ");

        txt_due.setBackground(new java.awt.Color(248, 227, 227));
        txt_due.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_due.setText("0");
        txt_due.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_dueFocusGained(evt);
            }
        });
        txt_due.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_dueMouseClicked(evt);
            }
        });
        txt_due.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dueKeyReleased(evt);
            }
        });

        txt_customer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("VAT/NBT");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "Price / Kg", "Cube Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(22);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Contact Number:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel1)
                                .addGap(5, 5, 5)
                                .addComponent(txt_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_address)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addGap(0, 172, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(288, 288, 288)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_contactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_contactMouseClicked
        txt_contact.selectAll();
    }//GEN-LAST:event_txt_contactMouseClicked

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased

    }//GEN-LAST:event_txt_contactKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        txt_customer.setText(jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0).toString());
        txt_contact.setText(jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1).toString());
        txt_address.setText(jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2).toString());
        txt_due.setText(jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 3).toString());

        if("true".equals(jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 4).toString())){
            jCheckBox1.setSelected(true);
        }else{
            jCheckBox1.setSelected(false);
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
        dtm.setRowCount(0);
        try {
            String sql = "Select * from PersonalPrice where Customer ='"+txt_customer.getText()+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("Product"));
                v.add(rs.getString("Price"));
                v.add(rs.getString("Cube_price"));
                dtm.addRow(v);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (jTable1.getSelectedRowCount() > 0) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to you want to delete?", "Aleart", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {

                        String sql1 = "delete from Customers where "
                        + "Name='" + (jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0).toString()) + "' and "
                        + "Contact='" + (jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1).toString()) + "'";

                        PreparedStatement pst = con.prepareStatement(sql1);
                        pst.executeUpdate();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "1 " + e);

                    }

                    try {

                        String sql1 = "delete from PersonalPrice where "
                        + "Customer='" + (jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0).toString()) + "'";

                        PreparedStatement pst = con.prepareStatement(sql1);
                        pst.executeUpdate();

                    } catch (Exception e) {
                        // JOptionPane.showMessageDialog(this, "1 " + e);

                    }

                    DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                    dtm.setRowCount(0);
                    FillTableSupplier();
                }

            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void txt_addressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_addressMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_addressMouseClicked

    private void txt_addressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_addressKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_addressKeyReleased

    private void txt_dueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_dueFocusGained
        txt_due.selectAll();
    }//GEN-LAST:event_txt_dueFocusGained

    private void txt_dueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_dueMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dueMouseClicked

    private void txt_dueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dueKeyReleased
        if ("".equals(txt_due.getText())) {
            txt_due.setText("0");
            txt_due.selectAll();

        }
    }//GEN-LAST:event_txt_dueKeyReleased

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (txt_customer.getText().isEmpty() || txt_contact.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill all the fields !");

        } else {
            if (checkExistency() == false) {
                try {

                    String sql = "Insert into Customers(Name,Contact,Address,Due,vatnbt) values(?,?,?,?,?)";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, txt_customer.getText());
                    pst.setString(2, txt_contact.getText());
                    pst.setString(3, txt_address.getText());
                    pst.setString(4, txt_due.getText());
                    pst.setString(5, jCheckBox1.isSelected()+"");

                    pst.execute();

                    
                    InsertPrice();
                    JOptionPane.showMessageDialog(null, "Saved Succesfully !");

                    DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                    dtm.setRowCount(0);
                    FillTableSupplier();
                    
                    txt_customer.setText("");
                    txt_contact.setText("");

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, e);

                }

            } else {

                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Customer name exists already. Do you want to update?", "Aleart", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {

                        String sql = "update Customers set Contact='" + txt_contact.getText() + "', Address='" + txt_address.getText() + "', Due='" + txt_due.getText() + "', vatnbt='"+jCheckBox1.isSelected()+"' where Name='" + txt_customer.getText() + "' ";

                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.executeUpdate();
                        pst.close();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }
                UpdatePrice();
            }
        }

        FillTableSupplier();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txt_contact.setText("");
        txt_customer.setText("");
        txt_address.setText("");
        txt_due.setText("0");

        FillTableStock();

    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Customers dialog = new Customers(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_contact;
    private javax.swing.JTextField txt_customer;
    private javax.swing.JTextField txt_due;
    // End of variables declaration//GEN-END:variables
}
