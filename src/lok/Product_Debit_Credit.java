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

/**
 *
 * @author javalok
 */
public class Product_Debit_Credit extends javax.swing.JInternalFrame {
    JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String date;
    private String type;
    private String productNo;
    private String productName;
    private String productQtyName;
    private double price;
    private int product_qty;
    private double total_amount;
    private String description;
   // private int total_qty_amount, temp_qty_amount;
    private int id;


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
            q = "select qty_type from tbl_qty_type_manager where product_name=?";
            new JComboHandle(conn).fillComboByProductQtyType(jComboBoxProdcutQtyType, q, productNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {           
            date = jTextFieldDate.getText().toString();
            type = jComboBoxType.getSelectedItem().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount  = price * product_qty;
            description = jTextAreaDescription.getText().toString();
            
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate,jTextFieldProductNo, jTextFieldQtyPrice);
            
            if (flag_tf == true) {
                
                q = "insert into tbl_credit_debit(date,codeno,product_name,qty_type,total_qty,product_single_price,total_amount,description,type) values(?,?,?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, date);
                pstm.setString(2,productNo);
                pstm.setString(3, productName);
                pstm.setString(4, productQtyName);
                pstm.setInt(5, product_qty);
                pstm.setDouble(6, price);
                pstm.setDouble(7, total_amount);
                pstm.setString(8, description);
                pstm.setString(9,type);
                
                pstm.executeUpdate();
                pstm.close();
                 JOptionPane.showMessageDialog(this, "Sale Product Added", "Sale Add Information", JOptionPane.INFORMATION_MESSAGE);
                 this.updateJTable();
                  this.cancel();
            
            }else {
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
        jTextFieldProductNo.setText("");
        jTextAreaDescription.setText("");
        jComboBoxType.setSelectedIndex(0);
    }

    public void delete() {
        try {
            q = "delete from tbl_credit_debit where id=?";
            pstm = conn.prepareStatement(q);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            pstm.close();
              JOptionPane.showMessageDialog(this, "Sale Product Deleted", "Product Sale Delete Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public void update() {
        try {
            date = jTextFieldDate.getText().toString();
            type = jComboBoxType.getSelectedItem().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount  = price * product_qty;
            description = jTextAreaDescription.getText().toString();
            
            q = "update tbl_credit_debit set date=? ,codeno=? , product_name=? ,qty_type=?, total_qty=? ,product_single_price=?, total_amount=? , description=? , type=? where id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2,productNo);
            pstm.setString(3, productName);
            pstm.setString(4, productQtyName);
            pstm.setInt(5, product_qty);
            pstm.setDouble(6, price);
            pstm.setDouble(7, total_amount);
            pstm.setString(8, description);
            pstm.setString(9,type);
            pstm.setInt(10, id);
            
            pstm.executeUpdate();
            pstm.close();
              JOptionPane.showMessageDialog(this, "Product Sale Updated", "Product Sale Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable() {
        try {
            q = "select date as 'Date' , codeno as 'Code NO' , product_name as 'Product Name', qty_type as 'Qty', product_single_price as  'Price' , total_amount as 'Total Amount' , id as 'ID' from tbl_credit_debit";
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
            
            String codeno_click = (jTableSelling).getModel().getValueAt(myrow, 6).toString();
            //q = "select *from tbl_temp_selling_product where date=? and product_name=? and product_qty_name=?";
            q = "select *from tbl_credit_debit where id=?";
            pstm = conn.prepareStatement(q);
            //  pstm.setString(1, qtydate_click);
            pstm.setString(1, codeno_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                productNo = rs.getString("codeno");
                productName = rs.getString("product_name");
                productQtyName = rs.getString("qty_type");
                price = rs.getDouble("product_single_price");
                total_amount = rs.getDouble("total_amount");
                product_qty = rs.getInt("total_qty");
                description = rs.getString("description");
                type = rs.getString("type");
                id = rs.getInt("id");

                jTextFieldDate.setText(date);
                jTextFieldProductNo.setText(productNo);
                jComboBoxProductName.setSelectedItem(productName);
                jTextFieldQtyPrice.setText(String.valueOf(price));
                jTextFieldQty.setText(String.valueOf(product_qty));
                jTextFieldTotalAmount.setText(String.valueOf(total_amount));
                jTextAreaDescription.setText(description);
                jComboBoxType.setSelectedItem(type);
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + s.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }

        jComboBoxProdcutQtyType.setSelectedItem(productQtyName);

    }


    /** Creates new form Product_Sale */
    public Product_Debit_Credit(java.sql.Connection conn) {
        initComponents();
        this.conn = conn;
        
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
        jLabel7 = new javax.swing.JLabel();
        jTextFieldProductNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTotalAmount = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSelling = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Debit Credit"));

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldQtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQtyKeyTyped(evt);
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

        jLabel7.setText("Product No");

        jTextFieldProductNo.setToolTipText("");
        jTextFieldProductNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldProductNoMouseClicked(evt);
            }
        });
        jTextFieldProductNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldProductNoActionPerformed(evt);
            }
        });
        jTextFieldProductNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProductNoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldProductNoKeyTyped(evt);
            }
        });

        jLabel6.setText("Total Amount");

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldTotalAmountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTotalAmountKeyTyped(evt);
            }
        });

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescription);

        jLabel9.setText("Description");

        jLabel8.setText("Type");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Credit", "Debit" }));
        jComboBoxType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTypeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(40, 40, 40))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldQty, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldTotalAmount)
                                .addComponent(jComboBoxProdcutQtyType, 0, 177, Short.MAX_VALUE)
                                .addComponent(jComboBoxProductName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldDate)
                            .addComponent(jComboBoxType, 0, 166, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxProdcutQtyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

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

        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonDelete)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldDateMouseClicked
        new NepaliCalendarClassGroup.NepaliDate().setTextFieldWithNepaliDateValue(jTextFieldDate);
        String date = jTextFieldDate.getText().toString();
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
                q = "select codeno , buying_price from tbl_qty_type_manager where product_name=? and qty_type=?";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, productName);
                pstm.setString(2, productQtyName);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    product_price = rs.getDouble("buying_price");
                    productNo = rs.getString("codeno");

                    //  total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());
                    jTextFieldProductNo.setText(productNo);
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

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        this.delete();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.cancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        this.add();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        this.update();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jTextFieldProductNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldProductNoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoMouseClicked

    private void jTextFieldProductNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoActionPerformed

    private void jTextFieldProductNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductNoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoKeyReleased

    private void jTextFieldProductNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductNoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoKeyTyped

    private void jTextFieldTotalAmountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountMouseClicked

    private void jTextFieldTotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountActionPerformed

    private void jTextFieldTotalAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountKeyReleased

    private void jTextFieldTotalAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalAmountKeyTyped

    private void jComboBoxTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTypeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTypeItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox jComboBoxProdcutQtyType;
    private javax.swing.JComboBox jComboBoxProductName;
    private javax.swing.JComboBox jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableSelling;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldProductNo;
    private javax.swing.JTextField jTextFieldQty;
    private javax.swing.JTextField jTextFieldQtyPrice;
    private javax.swing.JTextField jTextFieldTotalAmount;
    // End of variables declaration//GEN-END:variables
}
