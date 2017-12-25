/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Product_Sale.java
 *
 * Created on May 27, 2014, 3:49:34 PM
 */
package lok;

import NepaliCalendarClassGroup.NepaliDate;
import classgroup.CountCharacter;
import classgroup.JComboHandle;
import classgroup.JTableHandle;
import classgroup.JavaValidation;
import java.awt.event.ItemEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import jpos.*;

/**
 *
 * @author javalok
 */
public class Product_Sale extends javax.swing.JInternalFrame {
    //  POSPrinterControl19 ptr = (POSPrinterControl19) new POSPrinter();

    POSPrinterControl18 ptr;
    JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String date;
    private String productName;
    private String productQtyName;
    private double price;
    private int product_qty;
    private double total_amount;
    private int total_qty_amount, temp_qty_amount;
    private int sale_id;

    private String userName;
    private int bill_no,t_bill_no;


    public void fillProductNameCombo() {

        try {
            q = "select product_name from tbl_productname_entry";
            new JComboHandle(conn).fillComboProductName(jComboBoxProductName, q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillProductQtyNameCombo(String productNames) {
        try {
            System.out.println("product name inside fillProductQtynameCombo() is: " + productNames);
            q = "select qty_name from tbl_qty_type_manager where product_name=?";
            new JComboHandle(conn).fillComboByProductQtyType(jComboBoxProdcutQtyType, q, productNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settingBillno(String bill_date){
        System.out.println("I am inside SETTING bill no ....");
       try{
            q = "SELECT MAX(bill_no) from tbl_manage_bill_no";
            pstm = conn.prepareStatement(q);
            rs = pstm.executeQuery();
            if (rs.next()) {
              t_bill_no = rs.getInt(1);
                System.out.println("MaX t b no IS: "+t_bill_no);
              bill_no = t_bill_no+1;
                System.out.println("bill no is: "+bill_no);
            }else{
                bill_no = 1;
                System.out.println("Oh no there is no data so bill no is: "+bill_no);

            }
          //  pstm.close();
           System.out.println("BILL NO is : "+bill_no);
          
            q = "insert into tbl_manage_bill_no(date,bill_no) values (?,?)";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,bill_date);
            pstm.setInt(2,bill_no);
            pstm.executeUpdate();
            pstm.close();
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    public void add() {
        try {
            date = jTextFieldDate.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate, jTextFieldQtyPrice);
            if (flag_tf == true) {
//                q = "select product_name , product_qty_name from tbl_import_product_record where product_name=? and product_qty_name=?";
//                pstm = conn.prepareStatement(q);
//                pstm.setString(1, jComboBoxProductName.getSelectedItem().toString());
//                pstm.setString(2, jComboBoxProdcutQtyType.getSelectedItem().toString());
//                rs = pstm.executeQuery();
//                if (rs.next()) {
//                    JOptionPane.showMessageDialog(this, "Price Name Already Exists", "Dublication Warning!", JOptionPane.WARNING_MESSAGE);
//                    this.cancel();
//                    return;
//                } else {

            
            
                q = "insert into tbl_selling_product_record(date,product_name,product_qty_name,product_qty,product_price,total_amount,total_qty_amount,bill_no,entry_by) values(?,?,?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, date);
                pstm.setString(2, productName);
                pstm.setString(3, productQtyName);
                pstm.setInt(4, product_qty);
                pstm.setDouble(5, price);
                pstm.setDouble(6, total_amount);
                pstm.setInt(7, total_qty_amount);
                pstm.setInt(8,bill_no);
                pstm.setString(9,userName);
                pstm.executeUpdate();
                pstm.close();
                // JOptionPane.showMessageDialog(this, "Sale Product Added", "Sale Add Information", JOptionPane.INFORMATION_MESSAGE);
                // this.updateJTable();
                //  this.cancel();
                //  }
            } else {
                JOptionPane.showMessageDialog(this, "TextField is Empty", "TextField Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void cancel() {
        jButtonAdd.setEnabled(true);
        //  jTextFieldDate.setText("");
        jComboBoxProductName.setSelectedIndex(0);
        jComboBoxProdcutQtyType.setSelectedIndex(0);
        jTextFieldQtyPrice.setText("");
        jTextFieldQty.setText("");
        jTextFieldTotalAmount.setText("");
    }

    public void delete() {
        try {
            q = "delete from tbl_selling_product_record where date=? and product_name=? and product_qty_name=? and sale_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, jTextFieldDate.getText().toString());
            pstm.setString(2, jComboBoxProductName.getSelectedItem().toString());
            pstm.setString(3, jComboBoxProdcutQtyType.getSelectedItem().toString());
            pstm.setInt(4, sale_id);
            pstm.executeUpdate();
            pstm.close();
            //  JOptionPane.showMessageDialog(this, "Sale Product Deleted", "Product Sale Delete Information", JOptionPane.INFORMATION_MESSAGE);
            //this.updateJTable();
            //this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public void update() {
        try {
            date = jTextFieldDate.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());

            q = "update tbl_selling_product_record set date=? , product_name=? ,product_qty_name=?, product_qty=? ,product_price=?, total_amount=? , total_qty_amount=?  where sale_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2, productName);
            pstm.setString(3, productQtyName);
            pstm.setInt(4, product_qty);
            pstm.setDouble(5, price);
            pstm.setDouble(6, total_amount);
            pstm.setInt(7, total_qty_amount);
            pstm.setInt(8, sale_id);
            pstm.executeUpdate();
            pstm.close();
            //  JOptionPane.showMessageDialog(this, "Product Sale Updated", "Product Sale Update Information", JOptionPane.INFORMATION_MESSAGE);
            //this.updateJTable();
            //this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void addTemp() {
        try {
            date = jTextFieldDate.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate, jTextFieldQtyPrice);
            if (flag_tf == true) {
//                q = "select product_name , product_qty_name from tbl_import_product_record where product_name=? and product_qty_name=?";
//                pstm = conn.prepareStatement(q);
//                pstm.setString(1, jComboBoxProductName.getSelectedItem().toString());
//                pstm.setString(2, jComboBoxProdcutQtyType.getSelectedItem().toString());
//                rs = pstm.executeQuery();
//                if (rs.next()) {
//                    JOptionPane.showMessageDialog(this, "Price Name Already Exists", "Dublication Warning!", JOptionPane.WARNING_MESSAGE);
//                    this.cancel();
//                    return;
//                } else {
                q = "insert into tbl_temp_selling_product(date,product_name,product_qty_name,product_qty,product_price,total_amount,total_qty_amount) values(?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, date);
                pstm.setString(2, productName);
                pstm.setString(3, productQtyName);
                pstm.setInt(4, product_qty);
                pstm.setDouble(5, price);
                pstm.setDouble(6, total_amount);
                pstm.setInt(7, total_qty_amount);
                pstm.executeUpdate();
                pstm.close();
                //  JOptionPane.showMessageDialog(this, "Sale Product Added", "Sale Add Information", JOptionPane.INFORMATION_MESSAGE);
                this.updateJTable();
                this.cancel();
                //  }
            } else {
                JOptionPane.showMessageDialog(this, "TextField is Empty", "TextField Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void deleteTemp() {
        try {
            q = "delete from tbl_temp_selling_product where date=? and product_name=? and product_qty_name=? and sale_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, jTextFieldDate.getText().toString());
            pstm.setString(2, jComboBoxProductName.getSelectedItem().toString());
            pstm.setString(3, jComboBoxProdcutQtyType.getSelectedItem().toString());
            pstm.setInt(4, sale_id);
            pstm.executeUpdate();
            pstm.close();
            //JOptionPane.showMessageDialog(this, "Sale Product Deleted", "Product Sale Delete Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public void updateTemp() {
        try {
            date = jTextFieldDate.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());

            q = "update tbl_temp_selling_product set date=? , product_name=? ,product_qty_name=?, product_qty=? ,product_price=?, total_amount=? , total_qty_amount=?  where sale_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2, productName);
            pstm.setString(3, productQtyName);
            pstm.setInt(4, product_qty);
            pstm.setDouble(5, price);
            pstm.setDouble(6, total_amount);
            pstm.setInt(7, total_qty_amount);
            pstm.setInt(8, sale_id);
            pstm.executeUpdate();
            pstm.close();
            //JOptionPane.showMessageDialog(this, "Product Sale Updated", "Product Sale Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable() {
        try {
            q = "select product_name as 'Product Name',product_qty_name as 'Qty Name', product_price as  'Qty Price' , product_qty as 'Product Qty', total_amount as 'Total Amount'from tbl_temp_selling_product";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableSelling, q);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + e.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mouseClickJTable() {
        jButtonAdd.setEnabled(false);
        jButtonCancel.setEnabled(true);
       // jButtonDelete.setEnabled(true);
       // jButtonUpdate.setEnabled(true);

        try {
            int myrow = jTableSelling.getSelectedRow();

//            String date_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 0).toString();
//            String productname_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 1).toString();
//            String productqtyname_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 2).toString();
            //  String qtydate_click = (jTableSelling).getModel().getValueAt(myrow, 0).toString();
            String qtyproductname_click = (jTableSelling).getModel().getValueAt(myrow, 0).toString();
            String qtyproductqtyname_click = (jTableSelling).getModel().getValueAt(myrow, 1).toString();
            //q = "select *from tbl_temp_selling_product where date=? and product_name=? and product_qty_name=?";
            q = "select *from tbl_temp_selling_product where product_name=? and product_qty_name=?";
            pstm = conn.prepareStatement(q);
            //  pstm.setString(1, qtydate_click);
            pstm.setString(1, qtyproductname_click);
            pstm.setString(2, qtyproductqtyname_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                productName = rs.getString("product_name");
                productQtyName = rs.getString("product_qty_name");
                price = rs.getDouble("product_price");
                product_qty = rs.getInt("product_qty");
                total_amount = rs.getDouble("total_amount");
                System.out.println("Total Amount from Mouse Click() is: " + total_amount);
        
                sale_id = rs.getInt("sale_id");

                jTextFieldDate.setText(date);
                jComboBoxProductName.setSelectedItem(productName);
                jTextFieldQtyPrice.setText(String.valueOf(price));
                jTextFieldQty.setText(String.valueOf(product_qty));
                jTextFieldTotalAmount.setText(String.valueOf(total_amount));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + s.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }

        jComboBoxProdcutQtyType.setSelectedItem(productQtyName);

    }

    public void deleteTempAll() {
        try {
            q = "delete from tbl_temp_selling_product";
            pstm = conn.prepareStatement(q);
            pstm.executeUpdate();
            pstm.close();
            //JOptionPane.showMessageDialog(this, "Sale Product Deleted", "Product Sale Delete Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    /** Creates new form Product_Sale */
    public Product_Sale(java.sql.Connection conn ,String userName) {
        initComponents();
        this.conn = conn;
        this.userName = userName;
        //   this.ptr = ptr;
        jButtonAdd.setEnabled(true);
        jButtonCancel.setEnabled(false);
        //jButtonDelete.setEnabled(false);
        //jButtonUpdate.setEnabled(false);
      //  this.settingBillno();
        this.fillProductNameCombo();
        this.updateJTable();
        javaValidation = new JavaValidation();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDate = new javax.swing.JTextField();
        jComboBoxProductName = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldQty = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldQtyPrice = new javax.swing.JTextField();
        jComboBoxProdcutQtyType = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSelling = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTotalAmount = new javax.swing.JTextField();
        jButtonProceedBill = new javax.swing.JButton();

        setClosable(true);
        setTitle("Product Selling");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Name Entry"));

        jLabel1.setText("Product Name");

        jLabel2.setText("Date");

        jTextFieldDate.setToolTipText("");
        jTextFieldDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldDateMouseClicked(evt);
            }
        });
        jTextFieldDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDateActionPerformed(evt);
            }
        });
        jTextFieldDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldDateKeyTyped(evt);
            }
        });

        jComboBoxProductName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxProductName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxProductNameItemStateChanged(evt);
            }
        });

        jLabel3.setText("Product qty Type");

        jLabel4.setText("Product qty");

        jTextFieldQty.setToolTipText("");
        jTextFieldQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldQtyMouseClicked(evt);
            }
        });
        jTextFieldQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldQtyActionPerformed(evt);
            }
        });
        jTextFieldQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQtyKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldQtyKeyReleased(evt);
            }
        });

        jLabel5.setText("Product price");

        jTextFieldQtyPrice.setEditable(false);
        jTextFieldQtyPrice.setToolTipText("");
        jTextFieldQtyPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldQtyPriceMouseClicked(evt);
            }
        });
        jTextFieldQtyPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldQtyPriceActionPerformed(evt);
            }
        });
        jTextFieldQtyPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQtyPriceKeyTyped(evt);
            }
        });

        jComboBoxProdcutQtyType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxProdcutQtyType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxProdcutQtyTypeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                        .addGap(27, 27, 27)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxProductName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldDate, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                        .addContainerGap(48, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxProdcutQtyType, javax.swing.GroupLayout.Alignment.LEADING, 0, 147, Short.MAX_VALUE)
                            .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(jTextFieldQty, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                        .addGap(48, 48, 48))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxProdcutQtyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTableSelling.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableSelling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSellingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSelling);

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        jLabel6.setText("Total Amount ");

        jTextFieldTotalAmount.setEditable(false);
        jTextFieldTotalAmount.setToolTipText("");
        jTextFieldTotalAmount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldTotalAmountMouseClicked(evt);
            }
        });
        jTextFieldTotalAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTotalAmountActionPerformed(evt);
            }
        });
        jTextFieldTotalAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTotalAmountKeyTyped(evt);
            }
        });

        jButtonProceedBill.setText("Bill Proceed");
        jButtonProceedBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProceedBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonProceedBill, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jButtonProceedBill, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldDateMouseClicked
        new NepaliCalendarClassGroup.NepaliDate().setTextFieldWithNepaliDateValue(jTextFieldDate);
        String date = jTextFieldDate.getText().toString();
        this.settingBillno(date);
        jButtonCancel.setEnabled(true);
}//GEN-LAST:event_jTextFieldDateMouseClicked

    private void jTextFieldDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDateActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldDateActionPerformed

    private void jTextFieldDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDateKeyTyped
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldDateKeyTyped

    private void jTextFieldQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyMouseClicked

    private void jTextFieldQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyActionPerformed

    private void jTextFieldQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyKeyTyped
        javaValidation.getNumberValue(evt);
}//GEN-LAST:event_jTextFieldQtyKeyTyped

    private void jTextFieldQtyPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceMouseClicked

    private void jTextFieldQtyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceActionPerformed

    private void jTextFieldQtyPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceKeyTyped
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceKeyTyped

    private void jTextFieldTotalAmountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountMouseClicked

    private void jTextFieldTotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountActionPerformed

    private void jTextFieldTotalAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountKeyTyped

    private void jComboBoxProductNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProductNameItemStateChanged
        productName = jComboBoxProductName.getSelectedItem().toString();
        System.out.println("Product Name is: " + productName);
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            if (jComboBoxProdcutQtyType.getModel() != null) {
                jComboBoxProdcutQtyType.removeAllItems();
                jComboBoxProdcutQtyType.addItem("-");
            }
            this.fillProductQtyNameCombo(productName);
        }
    }//GEN-LAST:event_jComboBoxProductNameItemStateChanged

    private void jComboBoxProdcutQtyTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProdcutQtyTypeItemStateChanged
        double product_price = 0.0;
//        productName = jComboBoxProductName.getSelectedItem().toString();
//        productQtyName= jComboBoxProdcutQtyType.getSelectedItem().toString();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            System.out.println("product name: " + productName);
            System.out.println("product qty name: " + productQtyName);
            try {
                q = "select qty_price,qty_amount from tbl_qty_type_manager where product_name=? and qty_name=?";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, productName);
                pstm.setString(2, productQtyName);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    product_price = rs.getDouble("qty_price");
                    temp_qty_amount = rs.getInt("qty_amount");

                    //  total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());
                    System.out.println("prodct price: " + product_price);
                    jTextFieldQtyPrice.setText(String.valueOf(product_price));
                }
                rs.close();
                pstm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jComboBoxProdcutQtyTypeItemStateChanged

    private void jTextFieldQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyKeyReleased
        double price, total_amount;
        int qty;
        price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
        qty = Integer.parseInt(jTextFieldQty.getText().toString());
        String p = String.valueOf(qty);
        total_amount = price * Double.parseDouble(p);
        jTextFieldTotalAmount.setText(String.valueOf(total_amount));
    }//GEN-LAST:event_jTextFieldQtyKeyReleased

    private void jTableSellingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSellingMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTableSellingMouseClicked

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        this.add();
        this.addTemp();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.cancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonProceedBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProceedBillActionPerformed
        try {
//            POSPrinterControl18 ptr = (POSPrinterControl18) new POSPrinter();
//            System.out.println("Printer Object : " + ptr);
//            //Open the device.
//            //Use the name of the device that connected with your computer.
//            ptr.open("TM-U220D");
//            //Get the exclusive control right for the opened device.
//            //Then the device is disable from other application.
//            ptr.claim(1000);
//            //Enable the device.
//            ptr.setDeviceEnabled(true);
//
//            // set map mode to metric - all dimensions specified in 1/100mm units
//            ptr.setMapMode(POSPrinterConst.PTR_MM_METRIC);  // unit = 1/100 mm - i.e. 1 cm = 10 mm = 10 * 100 units
//
//            String date = new NepaliDate().returnNepaliDateValue();
//
//            // constants defined for convience sake (could be inlined)
//            String ESC = ((char) 0x1b) + "";
//            String LF = ((char) 0x0a) + "";
//            String SPACES = "                                                                      ";
//            // Print address
//            //   ESC|N = Normal char
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "PAN NO.:\u001b|bC305588245\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bCSHREE BAISHALI DEPARTMENTAL STORE\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA\u001b|bC KUSMA - 8 , PARBAT \n");
//            //   ESC|rA = Right side char
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA \u001b|bC TEL: 067-421234   \n");
//            //   ESC|cA = Centaring char
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "Bill No.:"+bill_no+"\u001b|rADate:" + date + "\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA---------------------------------\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bCP\u001b|Nroducts \u001b|bCQ\u001b|Nty\u001b|bCT\u001b|Nype \u001b|bCQ\u001b|Nty. \u001b|bCR\u001b|Nate \u001b|bCA\u001b|Nmount\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA---------------------------------\n");



            //Print buying goods
            String p_name;
            String p_qty_type;
            int qty;
            double rate;
            double amount;
            double total_amount;
            q = "select  *from tbl_temp_selling_product";
            pstm = conn.prepareStatement(q);
            rs = pstm.executeQuery();
            while (rs.next()) {
                System.out.println("I am inside RS.NEXT():::");
                p_name = rs.getString("product_name");
                p_qty_type = rs.getString("product_qty_name");
                qty = rs.getInt("product_qty");
                rate = rs.getDouble("product_price");
                amount = rs.getDouble("total_amount");

                System.out.println("product: " + p_name);
                System.out.println("Product qty type: " + p_qty_type);
                System.out.println("Product qty: " + qty);
                System.out.println("Product rate: " + rate);
                System.out.println("Product total amount: " + amount);

                //space constances
                /**
                 *  qty_type_space = 10
                 *  qty_spcae = 18
                 *  price_space = 23
                 *  amount_space - 28
                 */
                String qty_type_space = "";
                String qty_space = "";
                String rate_space = "";
                String amount_space = "";

                int p_count, p_q_count, p_price_count, p_amount_count;

                int need_p_count, need_p_q_count, need_p_price_count, need_p_amount_count;

                CountCharacter cc = new CountCharacter();

                p_count = cc.countCharacter(p_name);
                p_q_count = cc.countCharacter(p_qty_type);
                p_price_count = cc.countCharacter(String.valueOf(qty));
                p_amount_count = cc.countCharacter(String.valueOf(rate));

                System.out.println("==========character count==========");
                System.out.println("Product count : "+p_count);
                System.out.println("Pruduct qty count : "+p_q_count);
                System.out.println("Product price count: "+p_price_count);
                System.out.println("Product Amount count: "+p_amount_count);
                System.out.println("=====================");

                need_p_count = 10 - p_count-1;
                need_p_q_count = 18- p_q_count-need_p_count-p_count-1;
                need_p_price_count = 23 - p_price_count-need_p_q_count-p_q_count-need_p_count-p_count-2;
                need_p_amount_count = 28-p_amount_count-need_p_price_count-p_price_count-need_p_count-p_count-p_q_count-need_p_q_count-1;

                System.out.println("=========== needed count=================");
                System.out.println("Needed Product count : "+need_p_count);
                System.out.println("Needed Product count : "+need_p_q_count);
                System.out.println("Needed Product count : "+need_p_price_count);
                System.out.println("Needed Product count : "+need_p_amount_count);
                System.out.println("=======================================");

//                System.out.println("================space generated===================");
//
//                for(int i=0;i<need_p_count;i++){
//                    qty_type_space = qty_type_space+" ";
//                    System.out.println("Quantity Type Space : "+ qty_type_space);
//
//                }
//                for(int i=0;i<need_p_q_count;i++){
//                    qty_space = qty_space+" ";
//                    System.out.println("Quantity Space : "+qty_space);
//                }
//                for(int i=0;i<need_p_price_count;i++){
//                    rate_space = rate_space+" ";
//                    System.out.println("Rate Space : "+rate_space);
//                }
//                for(int i=0;i<need_p_amount_count;i++){
//                    amount_space = amount_space+" ";
//                    System.out.println("Amount Space : "+amount_space);
//                }
//                System.out.println("-------------------------------------------------");
//             ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT,ESC + "|1C"+ p_name +qty_type_space+
//                     p_qty_type+qty_space +
//                     qty+rate_space +
//                     rate+amount_space +
//                     amount+"\n");

            }

//            rs.close();
//            pstm.close();

            String sum = null;

            q = "select sum(total_amount) from tbl_temp_selling_product";
            pstm = conn.prepareStatement(q);
            rs = pstm.executeQuery();
            if (rs.next()) {
                sum = rs.getString(1);
            }

         total_amount = Double.parseDouble(sum);
            System.out.println("Total Amount "+total_amount);
//            //Print the total cost
//            //   ESC|bC = Bold
//            //   ESC|uC = Underline
//            //   ESC|2C = Wide charcter
//             ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA---------------------------------\n");
//             ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT,               ESC + "|rA" +               "\u001b|bCTotal Amount: "+ total_amount + LF);
//          //  ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bCTotal Amount:           " + total_amount + "\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA---------------------------------\n");
//
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT,  ESC + "|1uC" + "\u001b|bCEntry By:" +userName+"\n");
//
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|NThank you for shopping with us\n");
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|NReturns/Exchanges within 1 week, after\n");
//
//            //Feed the receipt to the cutter position automatically, and cut.
//            //   ESC|#fP = Line Feed and Paper cut
//            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
//
//            //Cancel the device.
//            ptr.setDeviceEnabled(false);
//
//            //Release the device exclusive control right.
//            ptr.release();
//
//            //Finish using the device.
//            ptr.close();
//
//        } catch (JposException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Lokendra : " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
                    JOptionPane.showMessageDialog(this, "Lokendra : Success");
        this.deleteTempAll();
        this.jTextFieldDate.setText("");
    }//GEN-LAST:event_jButtonProceedBillActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
//        try {
//            //Cancel the device.
//            ptr.setDeviceEnabled(false);
//
//            //Release the device exclusive control right.
//            ptr.release();
//
//            //Finish using the device.
//            ptr.close();
//        } catch (JposException ex) {
//            ex.printStackTrace();
//        }
//        // JavaPOS's code for Step1--END
//        System.exit(0);
    }//GEN-LAST:event_formInternalFrameClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonProceedBill;
    private javax.swing.JComboBox jComboBoxProdcutQtyType;
    private javax.swing.JComboBox jComboBoxProductName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSelling;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldQty;
    private javax.swing.JTextField jTextFieldQtyPrice;
    private javax.swing.JTextField jTextFieldTotalAmount;
    // End of variables declaration//GEN-END:variables
}
