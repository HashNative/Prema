/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prema;

import backend.ReportJasper;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.AbstractButton;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Ilham
 */
public class MainFrame extends javax.swing.JFrame implements SerialPortEventListener{

    /**
     * Creates new form Login
     */
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public static String result="0";
    private static SerialPort serialPort;
    public MainFrame() {
        initComponents();
        
        AutoCompleteDecorator.decorate(combo_supplier);
        AutoCompleteDecorator.decorate(combo_customer);
        AutoCompleteDecorator.decorate(product);
        AutoCompleteDecorator.decorate(combo_supplier2);
         Calendar c = Calendar.getInstance();
        jDateChooser1.setDate(c.getTime());
        con = backend.DBConnect.ConnectDb();
        ViewTruckRecord();
        FillcomboSupplier();
       this.setExtendedState(MAXIMIZED_BOTH);
       
        getInvoiceNo();
        FillcomboCustomer(); 
        jDateChooser2.setDate(c.getTime());        
        FillcomboProduct();
        getPrice();
        
        getGRNNo();        
        jDateChooser4.setDate(c.getTime());     
        getRecentVehicleAmountForWeight();
        
    }
    
     MainFrame(String username){
         initComponents();
         AutoCompleteDecorator.decorate(combo_supplier);
        AutoCompleteDecorator.decorate(combo_customer);
        AutoCompleteDecorator.decorate(product);
        AutoCompleteDecorator.decorate(combo_supplier2);
         Calendar c = Calendar.getInstance();
        jDateChooser1.setDate(c.getTime());
        con = backend.DBConnect.ConnectDb();
        ViewTruckRecord();
        FillcomboSupplier();
       this.setExtendedState(MAXIMIZED_BOTH);
         txt_userid.setText(username);
         getInvoiceNo();
        FillcomboCustomer(); 
        jDateChooser2.setDate(c.getTime());
        
        FillcomboProduct();
        getPrice();
        
        getGRNNo();        
        jDateChooser4.setDate(c.getTime());     
        getRecentVehicleAmountForWeight();
        
        Timer t = new Timer();
t.schedule(new TimerTask() {
    @Override
    public void run() {
       weight.setText(result);
    }
}, 0, 50);
     }

     
    public void UpdateRecord(String no){
        try {
            
         String time = null;
        try {
           
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            time=dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
                
            String sql = "update Record set OutTime='"+time+"', OutWeight='"+weight.getText()+"', Status='Completed' where Plate_no='"+no+"' ";

           PreparedStatement pst = con.prepareStatement(sql);
                pst.executeUpdate();
                pst.close();
               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    
    }
    
    private void InsertToRecord() {
        try {
            String sql = "Insert into Record(Plate_no, Driver_name, Intime, OutTime, Inweight,Outweight,Status) values(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

           
             String time = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            time=dateFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }   

            
            pst.setString(1, plate_no.getText());
            pst.setString(2, combo_supplier.getSelectedItem().toString());
            pst.setString(3, time);
            pst.setString(4, "");
            pst.setString(5, weight.getText());
            pst.setString(6, "0");
            pst.setString(7, "In Progress");
            pst.executeUpdate();
            pst.close();
            
            JOptionPane.showMessageDialog(null, "Recorded Successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
    
    public void CheckInOrOut(){
     
      try{
          String sql = "select * from Record where plate_no='"+plate_no.getText()+"' and Status='In Progress'";
          PreparedStatement pst = con.prepareStatement(sql);
          ResultSet rs = pst.executeQuery();
          if(rs.next()){
          
          inout_button.setText("Mark as Out");
        }else{
           inout_button.setText("Mark as In");
        }

          pst.close();
         
      }catch(SQLException e){
          JOptionPane.showMessageDialog(null, "3"+e);
      }
     
     }
     
    public void ViewTruckRecord() {
 DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                        dtm.setRowCount(0);
        
    
      try {
            String sql = "Select * from Record where Plate_no like '%"+plateno.getText()+"%'";
           PreparedStatement pst = con.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
           
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("Plate_no"));
                v.add(rs.getString("Driver_name")); 
                v.add(rs.getString("InTime"));
                v.add(rs.getString("Inweight"));
                v.add(rs.getString("OutTime"));
                v.add(rs.getString("OutWeight"));
                double inweight=Double.parseDouble(rs.getString("Inweight"));
                double outweight=Double.parseDouble(rs.getString("Outweight"));
                if(inweight>outweight){
                v.add(inweight-outweight+"");
                }else{
                v.add(outweight-inweight+"");
                }
                v.add(rs.getString("Status"));
               
                dtm.addRow(v);
                //branch = rs.getString("branch");
            }
          
            pst.close();
           
          
        } catch (Exception e) {

           // JOptionPane.showMessageDialog(null, e);

        } 
      }
      
    private void FillcomboSupplier() {
        Vector v1 = new Vector();
        try {
            String sql = "select * from Suppliers";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            
            Vector v2 = new Vector();
            v1.add("Customer");
            while (rs.next()) {
                v1.add(rs.getString("Name"));
                v2.add(rs.getString("Name"));
            }
            
            combo_supplier2.setModel(new javax.swing.DefaultComboBoxModel(v2));
            

            pst.close();
        } catch (Exception e) {

            // JOptionPane.showMessageDialog(null,e);
        }
        
        try {
            String sql = "select * from Customers";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            
            while (rs.next()) {
                v1.add(rs.getString("Name"));
            }
            combo_supplier.setModel(new javax.swing.DefaultComboBoxModel(v1));
            

            pst.close();
        } catch (Exception e) {

            // JOptionPane.showMessageDialog(null,e);
        }
    }
      
       @Override
    public void serialEvent(SerialPortEvent event) {
         if(event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    
                    String receivedData = serialPort.readString(event.getEventValue());
                     System.out.println(receivedData.split("\\s+")[1]);
                      result=receivedData.split("\\s+")[1];
                     
                }
                catch (SerialPortException ex) {
                    System.out.println("Error in receiving response from port: " + ex);
                }
            }
    }
    
    
    //Invoice
    public void getInvoiceNo() {

        try {
            String sql = "select * from InvoiceNo";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ino.setText(rs.getString("ino"));

            }

            pst.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "3" + e);
        }
    }

    public void updateInvoiceNo() {
        try {

            String sql = "update InvoiceNo set ino=ino+1 where No=1 ";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
            getInvoiceNo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void FillcomboCustomer() {

        try {
            String sql = "select * from Customers";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            Vector v = new Vector();
            while (rs.next()) {
                v.add(rs.getString("Name"));
            }
            combo_customer.setModel(new javax.swing.DefaultComboBoxModel(v));
            
            pst.close();
            
        } catch (Exception e) {

            // JOptionPane.showMessageDialog(null,e);
        }
        
      
    }
 
    private void FillcomboProduct() {

        try {
            String sql = "select * from Stock";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            Vector v = new Vector();
            while (rs.next()) {
                v.add(rs.getString("Product"));
            }
            product.setModel(new javax.swing.DefaultComboBoxModel(v));

            pst.close();
           
        } catch (Exception e) {

            // JOptionPane.showMessageDialog(null,e);
        }
    }
  
    public void clear() {
        plateno.setText("");
        firstweight.setText("0");
        intime.setText("");
        secondweight.setText("0");
        outtime.setText("0");        
        amount.setText("0");
        txt_netweight.setText("0");
        txt_total.setText("0");
        paid_amount.setText("0");
        due.setText("0");
        discount.setText("0");
        txt_nettotal.setText("0");
        transport.setText("0");
       
    }

    public void InsertToInvoice() {
        String Date = null;
        try {
            java.util.Date d = jDateChooser1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date = sdf.format(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
                 try {

                   String sql = "Insert into Invoice(ino,Date,Customer,Plate_no,Product,first_weight,"
                            + "second_weight,net_weight,Intime,outtime,amount,total,discount,transport,net_total,payment_method,user,paid,due) "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, ino.getText());
                    pst.setString(2, Date);
                    pst.setString(3, combo_customer.getSelectedItem().toString());
                    pst.setString(4, plateno1.getText());
                    pst.setString(5, product.getSelectedItem().toString());
                    pst.setString(6, firstweight.getText());
                    pst.setString(7, secondweight.getText());
                    pst.setString(8, txt_netweight.getText());
                    pst.setString(9, intime.getText());
                    pst.setString(10, outtime.getText());
                    pst.setString(11, amount.getText());
                    pst.setString(12, txt_total.getText());
                    pst.setString(13, discount.getText());
                    pst.setString(14, transport.getText());
                    pst.setString(15, txt_nettotal.getText());
                    pst.setString(16, getSelectedPaymentButton());
                    pst.setString(17, txt_userid.getText());
                    pst.setString(18, paid_amount.getText());
                    pst.setString(19, due.getText());
                    
                    pst.executeUpdate();
                    pst.close();

                    updateCustomerAmount();
                  try {
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("ID", ino.getText() + "");
                            params.put("image_url", System.getenv("APPDATA") + "\\Prema\\Logo.PNG");
                            String category = "Kg";
                            if("Weight".equals(sale_category.getSelectedItem())){
                                
                            }else{
                              category="Cube";
                            }
                            params.put("sales_category", category);
                            ReportJasper.printInvoice(System.getenv("APPDATA") + "\\Prema\\InvoiceNew.jrxml", params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                 
                 
                clear();
                updateInvoiceNo();
                
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);

                }
                 }
  
    public String getSelectedPaymentButton() {
        for (Enumeration<AbstractButton> buttons = buttonGroup1.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    public void updateCustomerAmount() {
        
        
        try {

            String sql = "update Customers set due=due+'" + due.getText() + "' "
                    + "where Name='" + combo_customer.getSelectedItem() + "' ";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
       
    }
  
    public void getPrice(){
       
        if("Weight".equals(sale_category.getSelectedItem())){
           
         try {
            String sql = "select Price "
                    + "from PersonalPrice where Customer='"+combo_customer.getSelectedItem()+"' "
                    + "and Product='"+product.getSelectedItem()+"'";
            System.out.println(sql);
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                amount.setText(rs.getString("Price"));
               
            }
            
            pst.close();
           
        } catch (Exception e) {

             JOptionPane.showMessageDialog(null,e);
        }
         
         
        }else{
        
            try {
            String sql = "select Cube_price "
                    + "from PersonalPrice where Customer='"+combo_customer.getSelectedItem()+"' "
                    + "and Product='"+product.getSelectedItem()+"'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                amount.setText(rs.getString("Cube_price"));
               
            }
            
            pst.close();
            
        } catch (Exception e) {

             JOptionPane.showMessageDialog(null,e);
        }
        
        }
    }
    
    
    public void getPriceGRN(){
        
         try {
            String sql = "select amount "
                    + "from Suppliers where name='"+combo_supplier2.getSelectedItem()+"' ";
                   
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                amount1.setText(rs.getString("amount"));
               
            }
            
            pst.close();
            
        } catch (Exception e) {

             JOptionPane.showMessageDialog(null,e);
        }
         
         
        
    }
     
    public void getRecentVehicleAmountForWeight(){
       // getAmount();
         try {
            String sql = "select Inweight, Outweight,Intime,Outtime,Driver_name "
                    + "from Record where Plate_no='"+plateno1.getText()+"' ORDER BY Intime DESC LIMIT 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                combo_customer.setSelectedItem(rs.getString("Driver_name"));
                
                double inweight=Double.parseDouble(rs.getString("Inweight"));
                   
                firstweight.setText(inweight+"");
                double outweight=Double.parseDouble(rs.getString("Outweight"));
                double netweight =outweight-inweight;
                secondweight.setText(outweight+"");
                txt_netweight.setText(netweight+"");
                
                intime.setText(rs.getString("Intime"));
                outtime.setText(rs.getString("Outtime"));
                
                txt_total.setText(netweight*Double.parseDouble(amount.getText())/1000+"");
                
                txt_nettotal.setText(Double.parseDouble(txt_total.getText())-Double.parseDouble(discount.getText())+Double.parseDouble(transport.getText())+"");
                due.setText((Double.parseDouble(txt_nettotal.getText()))-Double.parseDouble(paid_amount.getText())+"");
            }
            
            pst.close();
           
        } catch (Exception e) {

             JOptionPane.showMessageDialog(null,e);
        }
         
         
        
    }
    
    public void getRecentVehicleAmountForCubes(){
        DecimalFormat df = new DecimalFormat("#.##");	
            double total_amount= Double.parseDouble(txt_netweight.getText())*Double.parseDouble(amount.getText());

            double net_total= total_amount-Double.parseDouble(discount.getText()); //+Double.parseDouble(transport.getText());

            double due_amount= net_total-Double.parseDouble(paid_amount.getText()); //+Double.parseDouble(transport.getText());

            double transport_fee = Double.parseDouble(transport.getText());

            txt_total.setText(df.format(total_amount)+"");
            txt_nettotal.setText(df.format(net_total + transport_fee)+"");
            due.setText(df.format(due_amount + transport_fee)+"");
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
    
    
    
   //GRN
    
    public void getGRNNo() {
        try {
            String sql = "select * from GRNNo";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                grnno.setText(rs.getString("grnno"));
            }
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "3" + e);
        }
    }

    public void updateGRNNo() {
        try {
            String sql = "update GRNNo set grnno=grnno+1 where No=1 ";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
            getGRNNo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void clearGRN() {
        plateno3.setText("");
        firstweight1.setText("0");
        secondweight1.setText("0");
        intime1.setText("");
        outtime1.setText("");
        txt_netweight1.setText("0");
        txt_total1.setText("0");
        paid_amount1.setText("0");
        amount1.setText("0");
    }
     
    public void InsertToGRN() {
        String Date = null;
        try {
            java.util.Date d = jDateChooser4.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date = sdf.format(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
                try {

                    String sql = "Insert into GRN(grnno,Date,Supplier,Plate_no,Product,first_weight,"
                            + "second_weight,net_weight,Intime,outtime,amount,total,payment_method,user,paid,due) "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, grnno.getText());
                    pst.setString(2, Date);
                    pst.setString(3, combo_supplier2.getSelectedItem().toString());
                    pst.setString(4, plateno3.getText());
                    pst.setString(5, product2.getText());
                    pst.setString(6, firstweight1.getText());
                    pst.setString(7, secondweight1.getText());
                    pst.setString(8, txt_netweight1.getText());
                    pst.setString(9, intime1.getText());
                    pst.setString(10, outtime1.getText());
                    pst.setString(11, amount1.getText());
                    pst.setString(12, txt_total1.getText());
                    pst.setString(13, getSelectedPaymentButtonGRN());
                    pst.setString(14, txt_userid.getText());
                    pst.setString(15, paid_amount1.getText());
                    pst.setString(16, due1.getText());
                    
                    pst.executeUpdate();
                    pst.close();

                    updateSupplierAmount();
                  try {
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("ID", grnno.getText() + "");
                            params.put("image_url", System.getenv("APPDATA") + "\\Prema\\Logo.PNG");
                            ReportJasper.printInvoice(System.getenv("APPDATA") + "\\Prema\\GRNote.jrxml", params);
                        } catch (Exception e) {
                           JOptionPane.showMessageDialog(null, e);
                        }
                 
                 IncreaseStock();
                clearGRN();
                updateGRNNo();
                
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);

                }
                 }
    
    public String getSelectedPaymentButtonGRN() {
        for (Enumeration<AbstractButton> buttons = buttonGroup2.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
    
    private void IncreaseStock() {

       
                try {

                    String sql = "update Stock set weight=weight+'" + txt_netweight1.getText() + "'where Product='" + product2.getText() + "' ";

                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    pst.close();

                } catch (Exception e) {
                   // JOptionPane.showMessageDialog(this, e);
                }
           
    }
    
    public void updateSupplierAmount() {
        
        
        try {

            String sql = "update Suppliers set due=due+'" + due1.getText() + "' "
                    + "where Name='" + combo_supplier2.getSelectedItem() + "' ";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
       
    }
        
    public void getRecentVehicleAmountGRN(){
        getAmount();
         try {
            String sql = "select Inweight, Outweight,Intime,Outtime,Driver_name "
                    + "from Record where Plate_no='"+plateno3.getText()+"' ORDER BY Intime DESC LIMIT 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                 combo_supplier2.setSelectedItem(rs.getString("Driver_name"));
                double inweight=Double.parseDouble(rs.getString("Inweight"));
                firstweight1.setText(inweight+"");
                double outweight=Double.parseDouble(rs.getString("Outweight"));
                secondweight1.setText(outweight+"");
                double netweight = inweight-outweight;
                
                txt_netweight1.setText(netweight+"");
                
                intime1.setText(rs.getString("Intime"));
                outtime1.setText(rs.getString("Outtime"));
                
                 txt_total1.setText(netweight*Double.parseDouble(amount1.getText())/1000+"");
                 due1.setText(netweight*Double.parseDouble(amount1.getText())/1000+"");
            }
            
            pst.close();
            
        } catch (Exception e) {

             JOptionPane.showMessageDialog(null,e);
        }
         
         
        
    }
    
    public void getAmount(){
     try {
            String sql = "select * from Suppliers where Name='"+combo_supplier2.getSelectedItem()+"'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                amount1.setText(rs.getString("amount"));

            }

            pst.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "3" + e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        txt_userid = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        plate_no = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        weight = new javax.swing.JTextField();
        inout_button = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        combo_supplier = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        plateno = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txt_total = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        ino = new javax.swing.JTextField();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel32 = new javax.swing.JLabel();
        plateno1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        combo_customer = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        paid_amount = new javax.swing.JTextField();
        due = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        amount = new javax.swing.JLabel();
        product = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        firstweight = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        intime = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        secondweight = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        outtime = new javax.swing.JTextField();
        sale_category = new javax.swing.JComboBox<>();
        txt_netweight = new javax.swing.JTextField();
        discount = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txt_nettotal = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        transport = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txt_total1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txt_netweight1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        grnno = new javax.swing.JTextField();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel44 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel45 = new javax.swing.JLabel();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        plateno3 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        firstweight1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        combo_supplier2 = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        paid_amount1 = new javax.swing.JTextField();
        due1 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        product2 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        secondweight1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        outtime1 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        intime1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        amount1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txt_userid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_userid.setForeground(new java.awt.Color(51, 51, 51));
        txt_userid.setText("admin");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("User :");

        jPanel3.setLayout(new java.awt.CardLayout());

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(236, 236, 236));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Date :");

        jDateChooser1.setDateFormatString("yyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel27.setText("Plate No :");

        plate_no.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        plate_no.setSelectionColor(new java.awt.Color(229, 126, 49));
        plate_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plate_noActionPerformed(evt);
            }
        });
        plate_no.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plate_noKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                plate_noKeyReleased(evt);
            }
        });

        jLabel29.setText("Weight :");

        weight.setBackground(new java.awt.Color(0, 0, 0));
        weight.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        weight.setForeground(new java.awt.Color(0, 153, 153));
        weight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        weight.setText("0");
        weight.setSelectionColor(new java.awt.Color(229, 126, 49));
        weight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightActionPerformed(evt);
            }
        });
        weight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                weightKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                weightKeyReleased(evt);
            }
        });

        inout_button.setBackground(new java.awt.Color(204, 0, 0));
        inout_button.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        inout_button.setText("Mark as In");
        inout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inout_buttonActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Kg");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Supplier/Customer :");

        combo_supplier.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/truck.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(combo_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(70, 70, 70)
                            .addComponent(jLabel27)
                            .addGap(18, 18, 18)
                            .addComponent(plate_no, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(185, 185, 185)
                            .addComponent(inout_button))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(195, 195, 195))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plate_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(inout_button)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("New Truck Record", jPanel1);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Truck Plate No :");
        jLabel5.setAutoscrolls(true);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        plateno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        plateno.setSelectionColor(new java.awt.Color(229, 126, 49));
        plateno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                platenoActionPerformed(evt);
            }
        });
        plateno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                platenoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                platenoKeyReleased(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plate No", "Supplier/Customer", "In Time", "In weight", "Out Time", "Out Weight", "Net Weight", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(21);
        jTable1.setSelectionBackground(new java.awt.Color(145, 145, 145));
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
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(plateno, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(plateno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Truck record history", jPanel2);

        jPanel3.add(jTabbedPane1, "card2");

        jPanel4.setLayout(new java.awt.CardLayout());

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Customer :");

        txt_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total.setForeground(new java.awt.Color(153, 0, 51));
        txt_total.setText("0");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear-icon.png"))); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save-icon.png"))); // NOI18N
        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        ino.setEditable(false);
        ino.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ino.setSelectionColor(new java.awt.Color(229, 126, 49));
        ino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inoActionPerformed(evt);
            }
        });
        ino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inoKeyReleased(evt);
            }
        });

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton5.setText("Memo");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton4.setText("Credit");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton3.setText("Cheque");

        jLabel28.setText("Invoice No :");

        jDateChooser2.setDateFormatString("yyy-MM-dd");
        jDateChooser2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Date :");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton2.setText("Card");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Cash");

        jLabel32.setForeground(new java.awt.Color(153, 0, 0));
        jLabel32.setText("Payment Method :");

        plateno1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        plateno1.setSelectionColor(new java.awt.Color(229, 126, 49));
        plateno1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plateno1ActionPerformed(evt);
            }
        });
        plateno1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plateno1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                plateno1KeyReleased(evt);
            }
        });

        jLabel31.setText("Plate No :");

        jLabel33.setText("Product :");

        combo_customer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_customerActionPerformed(evt);
            }
        });

        jLabel34.setText("Paid :");

        paid_amount.setBackground(new java.awt.Color(251, 225, 225));
        paid_amount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        paid_amount.setText("0");
        paid_amount.setSelectionColor(new java.awt.Color(229, 126, 49));
        paid_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                paid_amountFocusGained(evt);
            }
        });
        paid_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paid_amountActionPerformed(evt);
            }
        });
        paid_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                paid_amountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paid_amountKeyReleased(evt);
            }
        });

        due.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        due.setForeground(new java.awt.Color(153, 0, 51));
        due.setText("0");

        jLabel35.setText("Due :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Total Amount :");

        jLabel6.setText("X");

        amount.setText("0");

        product.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productActionPerformed(evt);
            }
        });

        jLabel36.setText("First Weight :");

        firstweight.setEditable(false);
        firstweight.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        firstweight.setSelectionColor(new java.awt.Color(229, 126, 49));
        firstweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstweightActionPerformed(evt);
            }
        });
        firstweight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                firstweightKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                firstweightKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Kg");

        jLabel37.setText("In Time :");

        intime.setEditable(false);
        intime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        intime.setSelectionColor(new java.awt.Color(229, 126, 49));
        intime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intimeActionPerformed(evt);
            }
        });
        intime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                intimeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                intimeKeyReleased(evt);
            }
        });

        jLabel38.setText("Second Weight :");

        secondweight.setEditable(false);
        secondweight.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        secondweight.setSelectionColor(new java.awt.Color(229, 126, 49));
        secondweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondweightActionPerformed(evt);
            }
        });
        secondweight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                secondweightKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                secondweightKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Kg");

        jLabel39.setText("Out Time :");

        outtime.setEditable(false);
        outtime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outtime.setSelectionColor(new java.awt.Color(229, 126, 49));
        outtime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outtimeActionPerformed(evt);
            }
        });
        outtime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                outtimeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                outtimeKeyReleased(evt);
            }
        });

        sale_category.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sale_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Weight", "Cubes" }));
        sale_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sale_categoryActionPerformed(evt);
            }
        });

        txt_netweight.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_netweight.setForeground(new java.awt.Color(204, 0, 0));
        txt_netweight.setText("0");
        txt_netweight.setEnabled(false);
        txt_netweight.setSelectionColor(new java.awt.Color(229, 126, 49));
        txt_netweight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_netweightFocusGained(evt);
            }
        });
        txt_netweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_netweightActionPerformed(evt);
            }
        });
        txt_netweight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_netweightKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_netweightKeyReleased(evt);
            }
        });

        discount.setBackground(new java.awt.Color(251, 225, 225));
        discount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        discount.setText("0");
        discount.setSelectionColor(new java.awt.Color(229, 126, 49));
        discount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                discountFocusGained(evt);
            }
        });
        discount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discountActionPerformed(evt);
            }
        });
        discount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                discountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                discountKeyReleased(evt);
            }
        });

        jLabel56.setText("Discount :");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setText("Net Total :");

        txt_nettotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_nettotal.setForeground(new java.awt.Color(153, 0, 51));
        txt_nettotal.setText("0");

        jLabel58.setText("Transport :");

        transport.setBackground(new java.awt.Color(204, 204, 255));
        transport.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        transport.setText("0");
        transport.setSelectionColor(new java.awt.Color(229, 126, 49));
        transport.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                transportFocusGained(evt);
            }
        });
        transport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transportActionPerformed(evt);
            }
        });
        transport.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                transportKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                transportKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel4))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transport, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(383, 383, 383))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel28)
                .addGap(4, 4, 4)
                .addComponent(ino, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel16)
                .addGap(10, 10, 10)
                .addComponent(combo_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton1)
                        .addGap(24, 24, 24)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jRadioButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(461, 461, 461)
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nettotal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paid_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel35)
                                .addGap(6, 6, 6)
                                .addComponent(due, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(sale_category, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_netweight, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(jLabel38)
                        .addGap(6, 6, 6)
                        .addComponent(secondweight, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel39)
                        .addGap(10, 10, 10)
                        .addComponent(outtime, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel33)
                        .addGap(22, 22, 22)
                        .addComponent(product, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel36)
                        .addGap(4, 4, 4)
                        .addComponent(firstweight, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel37)
                        .addGap(10, 10, 10)
                        .addComponent(intime, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(plateno1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(ino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combo_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(plateno1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(firstweight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1))
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(intime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(secondweight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3))
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(outtime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sale_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_netweight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(amount))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(txt_nettotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paid_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(due)))
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3))))
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton4)
                        .addComponent(jRadioButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Invoice", jPanel5);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Supplier :");

        txt_total1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total1.setForeground(new java.awt.Color(153, 0, 51));
        txt_total1.setText("0");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear-icon.png"))); // NOI18N
        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txt_netweight1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_netweight1.setForeground(new java.awt.Color(153, 0, 51));
        txt_netweight1.setText("0");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save-icon.png"))); // NOI18N
        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Net Weight :");

        grnno.setEditable(false);
        grnno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grnno.setSelectionColor(new java.awt.Color(229, 126, 49));
        grnno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grnnoActionPerformed(evt);
            }
        });
        grnno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grnnoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                grnnoKeyReleased(evt);
            }
        });

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton6.setText("Memo");

        buttonGroup2.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton7.setText("Credit");

        buttonGroup2.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton8.setText("Cheque");

        jLabel44.setText("GRN No :");

        jDateChooser4.setDateFormatString("yyy-MM-dd");
        jDateChooser4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel45.setText("Date :");

        buttonGroup2.add(jRadioButton9);
        jRadioButton9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton9.setText("Card");

        buttonGroup2.add(jRadioButton10);
        jRadioButton10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton10.setSelected(true);
        jRadioButton10.setText("Cash");

        jLabel46.setForeground(new java.awt.Color(153, 0, 0));
        jLabel46.setText("Payment Method :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Kg");

        plateno3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        plateno3.setSelectionColor(new java.awt.Color(229, 126, 49));
        plateno3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plateno3ActionPerformed(evt);
            }
        });
        plateno3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plateno3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                plateno3KeyReleased(evt);
            }
        });

        jLabel47.setText("Plate No :");

        jLabel48.setText("First Weight :");

        firstweight1.setEditable(false);
        firstweight1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        firstweight1.setSelectionColor(new java.awt.Color(229, 126, 49));
        firstweight1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstweight1ActionPerformed(evt);
            }
        });
        firstweight1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                firstweight1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                firstweight1KeyReleased(evt);
            }
        });

        jLabel49.setText("Product :");

        combo_supplier2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        combo_supplier2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_supplier2ActionPerformed(evt);
            }
        });

        jLabel50.setText("Paid :");

        paid_amount1.setBackground(new java.awt.Color(251, 225, 225));
        paid_amount1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        paid_amount1.setText("0");
        paid_amount1.setSelectionColor(new java.awt.Color(229, 126, 49));
        paid_amount1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                paid_amount1FocusGained(evt);
            }
        });
        paid_amount1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paid_amount1ActionPerformed(evt);
            }
        });
        paid_amount1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                paid_amount1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paid_amount1KeyReleased(evt);
            }
        });

        due1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        due1.setForeground(new java.awt.Color(153, 0, 51));
        due1.setText("0");

        jLabel51.setText("Due :");

        product2.setText("C-1");

        jLabel52.setText("Second Weight :");

        secondweight1.setEditable(false);
        secondweight1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        secondweight1.setSelectionColor(new java.awt.Color(229, 126, 49));
        secondweight1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondweight1ActionPerformed(evt);
            }
        });
        secondweight1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                secondweight1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                secondweight1KeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Kg");

        outtime1.setEditable(false);
        outtime1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outtime1.setSelectionColor(new java.awt.Color(229, 126, 49));
        outtime1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outtime1ActionPerformed(evt);
            }
        });
        outtime1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                outtime1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                outtime1KeyReleased(evt);
            }
        });

        jLabel53.setText("Out Time :");

        jLabel54.setText("In Time :");

        intime1.setEditable(false);
        intime1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        intime1.setSelectionColor(new java.awt.Color(229, 126, 49));
        intime1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intime1ActionPerformed(evt);
            }
        });
        intime1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                intime1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                intime1KeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Total Amount :");

        jLabel12.setText("X");

        amount1.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton7)
                    .addComponent(jRadioButton10))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton9)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secondweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(outtime1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(product2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(intime1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(3, 3, 3)
                                .addComponent(txt_total1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(amount1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_netweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(240, 240, 240)))
                .addGap(85, 85, 85))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(459, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paid_amount1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(due1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(169, 169, 169))))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(7, 7, 7)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel44)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(grnno, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(45, 45, 45)
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(combo_supplier2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel47)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(plateno3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel49))
                            .addGap(18, 18, 18)
                            .addComponent(jLabel48)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(firstweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel8)))
                    .addGap(8, 384, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(product2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(secondweight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(outtime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_netweight1)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(amount1))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_total1))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton10)
                            .addComponent(jRadioButton9)
                            .addComponent(jRadioButton8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton7)
                            .addComponent(jRadioButton6))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(paid_amount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(due1)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(grnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_supplier2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(plateno3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(firstweight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addContainerGap(380, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("New Purchasing", jPanel7);

        jPanel4.add(jTabbedPane2, "card2");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(224, 235, 235));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel9MouseReleased(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(0, 0, 102));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 51, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Purchase Report");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/purchase-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(224, 235, 235));
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel10MouseReleased(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(0, 0, 102));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 51, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Sales Report");

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sales-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(224, 235, 235));
        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel11MouseReleased(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(0, 0, 102));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 51, 102));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Final Report");

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/final-report.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(224, 235, 235));
        jPanel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel12MouseReleased(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(0, 0, 102));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 51, 102));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Suppliers");

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/suppliers-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(224, 235, 235));
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel13MouseReleased(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(0, 0, 102));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 51, 102));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Customers");

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/customer-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(233, 233, 233));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/company_logo.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("www.hashnative.com");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setText("0777-140 803");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel13))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel17))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel55))
        );

        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu1.setText("File");

        jMenuItem1.setText("Logout");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setText("Control User Accounts");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("View");

        jMenuItem4.setText("Suppliers");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("Customers");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem9.setText("Stock");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reports");

        jMenuItem6.setText("Sales Report");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem7.setText("Purchase Report");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setText("Final Report");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Help");
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(6, 6, 6)
                                .addComponent(txt_userid, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txt_userid))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       
        if("admin".equals(txt_userid.getText())){
        new UserAccounts(this, true).setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void plate_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plate_noActionPerformed

    }//GEN-LAST:event_plate_noActionPerformed

    private void plate_noKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plate_noKeyPressed

        weight.setText("");
    }//GEN-LAST:event_plate_noKeyPressed

    private void plate_noKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plate_noKeyReleased
        CheckInOrOut();
        try{
            String sql = "select * from Record where Plate_no='"+plate_no.getText()+"'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){

                combo_supplier.setSelectedItem(rs.getString("driver_name"));

            }

            pst.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "1"+e);
        }
    }//GEN-LAST:event_plate_noKeyReleased

    private void weightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weightActionPerformed

    private void weightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_weightKeyPressed

    private void weightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weightKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_weightKeyReleased

    private void inout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inout_buttonActionPerformed
        if (!plate_no.getText().isEmpty() && !weight.getText().isEmpty()) {
            try{
                String sql1 = "select * from Record where plate_no='"+plate_no.getText()+"' and Status='In Progress'";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                ResultSet rs1 = pst1.executeQuery();
                if(rs1.next()){

                    UpdateRecord(rs1.getString("plate_no"));
                }else{

                    InsertToRecord();
                }
                plate_no.setText("");
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "x"+e);
            }
            CheckInOrOut();
        }else{
            JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        }

        ViewTruckRecord();
    }//GEN-LAST:event_inout_buttonActionPerformed

    private void platenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_platenoActionPerformed
        ViewTruckRecord();
    }//GEN-LAST:event_platenoActionPerformed

    private void platenoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_platenoKeyPressed
        try {
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

        } catch (Exception e) {
        }
    }//GEN-LAST:event_platenoKeyPressed

    private void platenoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_platenoKeyReleased
        ViewTruckRecord();
    }//GEN-LAST:event_platenoKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to you want to delete?", "Aleart", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {

                    String sql1 = "delete from Record where "
                    + "Plate_no='" + (jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0).toString()) + "' "
                    + " and Driver_name='" + (jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1).toString()) + "'"
                    + " and Intime='" + (jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2).toString()) + "' ";
                    PreparedStatement pst = con.prepareStatement(sql1);
                    pst.executeUpdate();

                    pst.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);

                }

            }
            ViewTruckRecord();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clear();
        getPrice();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       if(!"0".equals(txt_netweight.getText())){
        
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to you want to Print Invoice?", "Print Confirmation", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            
            InsertToInvoice();

        }
       }else{
       JOptionPane.showMessageDialog(null, "Please load to print!");
       }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void inoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inoActionPerformed

    }//GEN-LAST:event_inoActionPerformed

    private void inoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inoKeyPressed

    }//GEN-LAST:event_inoKeyPressed

    private void inoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inoKeyReleased

    }//GEN-LAST:event_inoKeyReleased

    private void plateno1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plateno1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plateno1ActionPerformed

    private void plateno1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plateno1KeyPressed
        firstweight.setText("0");
        intime.setText("");
        secondweight.setText("0");
        outtime.setText("0");
        amount.setText("0");
        txt_netweight.setText("0");
        txt_total.setText("0");
        paid_amount.setText("0");
        due.setText("0");
        txt_nettotal.setText("0");
        discount.setText("0");
        transport.setText("0");
    }//GEN-LAST:event_plateno1KeyPressed

    private void plateno1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plateno1KeyReleased
       
        getPrice();
      if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
    }//GEN-LAST:event_plateno1KeyReleased

    private void combo_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_customerActionPerformed
        getPrice();
       if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
    }//GEN-LAST:event_combo_customerActionPerformed

    private void paid_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paid_amountFocusGained
        paid_amount.selectAll();
    }//GEN-LAST:event_paid_amountFocusGained

    private void paid_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paid_amountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paid_amountActionPerformed

    private void paid_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paid_amountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_paid_amountKeyPressed

    private void paid_amountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paid_amountKeyReleased
       
        if ("".equals(paid_amount.getText())) {
            paid_amount.setText("0");
            paid_amount.selectAll();
            if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
        }else if(!"0".equals(paid_amount.getText())) {
           if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
        }
    }//GEN-LAST:event_paid_amountKeyReleased

    private void productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productActionPerformed
        getPrice();
    }//GEN-LAST:event_productActionPerformed

    private void firstweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstweightActionPerformed

    private void firstweightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstweightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstweightKeyPressed

    private void firstweightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstweightKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_firstweightKeyReleased

    private void intimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_intimeActionPerformed

    private void intimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_intimeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_intimeKeyPressed

    private void intimeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_intimeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_intimeKeyReleased

    private void secondweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_secondweightActionPerformed

    private void secondweightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_secondweightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_secondweightKeyPressed

    private void secondweightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_secondweightKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_secondweightKeyReleased

    private void outtimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outtimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_outtimeActionPerformed

    private void outtimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_outtimeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_outtimeKeyPressed

    private void outtimeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_outtimeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_outtimeKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearGRN();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
        if(!"0".equals(txt_netweight1.getText())){
        
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to you want to Print GRN?", "Print Confirmation", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {

            InsertToGRN();

        }
        
       }else{
       JOptionPane.showMessageDialog(null, "Please load to print!");
       }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void grnnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grnnoActionPerformed

    }//GEN-LAST:event_grnnoActionPerformed

    private void grnnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grnnoKeyPressed

    }//GEN-LAST:event_grnnoKeyPressed

    private void grnnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grnnoKeyReleased

    }//GEN-LAST:event_grnnoKeyReleased

    private void plateno3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plateno3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plateno3ActionPerformed

    private void plateno3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plateno3KeyPressed
        firstweight1.setText("0");
        intime1.setText("");
        secondweight1.setText("0");
        outtime1.setText("0");
        amount1.setText("0");
        txt_netweight1.setText("0");
        txt_total1.setText("0");
        paid_amount1.setText("0");
        due1.setText("0");

    }//GEN-LAST:event_plateno3KeyPressed

    private void plateno3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plateno3KeyReleased
        getPriceGRN();
        getRecentVehicleAmountGRN();
    }//GEN-LAST:event_plateno3KeyReleased

    private void firstweight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstweight1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstweight1ActionPerformed

    private void firstweight1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstweight1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstweight1KeyPressed

    private void firstweight1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstweight1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_firstweight1KeyReleased

    private void combo_supplier2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_supplier2ActionPerformed
        getPriceGRN();
        getRecentVehicleAmountGRN();
    }//GEN-LAST:event_combo_supplier2ActionPerformed

    private void paid_amount1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paid_amount1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paid_amount1ActionPerformed

    private void paid_amount1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paid_amount1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_paid_amount1KeyPressed

    private void paid_amount1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paid_amount1KeyReleased
        due1.setText(txt_total1.getText());
        if ("".equals(paid_amount1.getText())) {
            paid_amount1.setText("0");
            paid_amount1.selectAll();

        }else if(!"0".equals(paid_amount1.getText())) {
            due1.setText(Double.parseDouble(txt_total1.getText())-Double.parseDouble(paid_amount1.getText())+"");

        }
    }//GEN-LAST:event_paid_amount1KeyReleased

    private void secondweight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondweight1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_secondweight1ActionPerformed

    private void secondweight1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_secondweight1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_secondweight1KeyPressed

    private void secondweight1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_secondweight1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_secondweight1KeyReleased

    private void outtime1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outtime1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_outtime1ActionPerformed

    private void outtime1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_outtime1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_outtime1KeyPressed

    private void outtime1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_outtime1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_outtime1KeyReleased

    private void intime1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intime1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_intime1ActionPerformed

    private void intime1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_intime1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_intime1KeyPressed

    private void intime1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_intime1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_intime1KeyReleased

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
      if("admin".equals(txt_userid.getText())) {
        new Suppliers(this, true).setVisible(true);
      }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
       if("admin".equals(txt_userid.getText())) {
        new Customers(this, true).setVisible(true);
       }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new FinalReport(this, true,txt_userid.getText()).setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
       new PurchaseReport(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
       new SalesReports(this, true,txt_userid.getText()).setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
       if("admin".equals(txt_userid.getText())) {
           new Stock(this, true).setVisible(true);
       }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jPanel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseReleased
       new PurchaseReport(this, true).setVisible(true);
    }//GEN-LAST:event_jPanel9MouseReleased

    private void jPanel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseReleased
        new SalesReports(this, true,txt_userid.getText()).setVisible(true);
    }//GEN-LAST:event_jPanel10MouseReleased

    private void jPanel11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseReleased
       new FinalReport(this, true,txt_userid.getText()).setVisible(true);
    }//GEN-LAST:event_jPanel11MouseReleased

    private void jPanel12MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseReleased
       if("admin".equals(txt_userid.getText())) {
           new Suppliers(this, true).setVisible(true);
       }
    }//GEN-LAST:event_jPanel12MouseReleased

    private void jPanel13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseReleased
       if("admin".equals(txt_userid.getText())){
           new Customers(this, true).setVisible(true);
       }
    }//GEN-LAST:event_jPanel13MouseReleased

    private void paid_amount1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paid_amount1FocusGained
        paid_amount1.selectAll();
    }//GEN-LAST:event_paid_amount1FocusGained

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        serialPort = new SerialPort("COM3");
        try {
            // opening port
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.addEventListener(new MainFrame(), SerialPort.MASK_RXCHAR);

        }
        catch (SerialPortException ex) {
            System.out.println("Error in writing data to port: " + ex);
        }

    }//GEN-LAST:event_formWindowOpened

    private void sale_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sale_categoryActionPerformed
          
        firstweight.setText("0");
        intime.setText("");
        secondweight.setText("0");
        outtime.setText("0");
        amount.setText("0");
        txt_netweight.setText("0");
        txt_total.setText("0");
        
        due.setText("0");
        txt_nettotal.setText("0");
        transport.setText("0");
        
        
        
        
        getPrice();
        if ("Weight".equals(sale_category.getSelectedItem())) {
            txt_netweight.setEnabled(false);
            getRecentVehicleAmountForWeight();
        } else {
            txt_netweight.setEnabled(true);
            txt_netweight.setText("0");
            getRecentVehicleAmountForCubes();
        }


            
         
    }//GEN-LAST:event_sale_categoryActionPerformed

    private void txt_netweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_netweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_netweightActionPerformed

    private void txt_netweightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_netweightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_netweightKeyPressed

    private void txt_netweightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_netweightKeyReleased
            
        if ("".equals(txt_netweight.getText())) {
            txt_netweight.setText("0");
            txt_netweight.selectAll();
        }
            amount.setText("0");
            txt_total.setText("0");
            due.setText("0");
            txt_nettotal.setText("0");
            
            getPrice();
              getRecentVehicleAmountForCubes();
             
      
       
    }//GEN-LAST:event_txt_netweightKeyReleased

    private void txt_netweightFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_netweightFocusGained
        txt_netweight.selectAll();
    }//GEN-LAST:event_txt_netweightFocusGained

    private void discountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_discountFocusGained
        // TODO add your handling code here:
        discount.selectAll();
    }//GEN-LAST:event_discountFocusGained

    private void discountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_discountActionPerformed

    private void discountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_discountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_discountKeyPressed

    private void discountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_discountKeyReleased
      
        if ("".equals(discount.getText())) {
            discount.setText("0");
            discount.selectAll();
            if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
        }else if(!"0".equals(discount.getText())) {
            
            if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
            
        }
    }//GEN-LAST:event_discountKeyReleased

    private void transportFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transportFocusGained
        // TODO add your handling code here:
        transport.selectAll();
    }//GEN-LAST:event_transportFocusGained

    private void transportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transportActionPerformed

    private void transportKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transportKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_transportKeyPressed

    private void transportKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transportKeyReleased
        // TODO add your handling code here:
        if ("".equals(transport.getText())) {
            transport.setText("0");
            transport.selectAll();
            if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
        }else if(!"0".equals(transport.getText())) {
            
            if("Weight".equals(sale_category.getSelectedItem())){
              getRecentVehicleAmountForWeight();
            }else{
            
              getRecentVehicleAmountForCubes();
            }
            
        }
    }//GEN-LAST:event_transportKeyReleased

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amount;
    private javax.swing.JLabel amount1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> combo_customer;
    private javax.swing.JComboBox<String> combo_supplier;
    private javax.swing.JComboBox<String> combo_supplier2;
    private javax.swing.JTextField discount;
    private javax.swing.JLabel due;
    private javax.swing.JLabel due1;
    private javax.swing.JTextField firstweight;
    private javax.swing.JTextField firstweight1;
    private javax.swing.JTextField grnno;
    private javax.swing.JTextField ino;
    private javax.swing.JButton inout_button;
    private javax.swing.JTextField intime;
    private javax.swing.JTextField intime1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField outtime;
    private javax.swing.JTextField outtime1;
    private javax.swing.JTextField paid_amount;
    private javax.swing.JTextField paid_amount1;
    private javax.swing.JTextField plate_no;
    private javax.swing.JTextField plateno;
    private javax.swing.JTextField plateno1;
    private javax.swing.JTextField plateno3;
    private javax.swing.JComboBox<String> product;
    private javax.swing.JLabel product2;
    private javax.swing.JComboBox<String> sale_category;
    private javax.swing.JTextField secondweight;
    private javax.swing.JTextField secondweight1;
    private javax.swing.JTextField transport;
    private javax.swing.JLabel txt_nettotal;
    private javax.swing.JTextField txt_netweight;
    private javax.swing.JLabel txt_netweight1;
    private javax.swing.JLabel txt_total;
    private javax.swing.JLabel txt_total1;
    private javax.swing.JLabel txt_userid;
    private javax.swing.JTextField weight;
    // End of variables declaration//GEN-END:variables
}
