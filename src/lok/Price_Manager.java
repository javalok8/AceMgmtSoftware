/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * Price_Manager.java
 *
 * Created on May 27, 2014, 3:44:39 PM
 */
package lok;

import classgroup.JComboHandle;
import classgroup.JTableHandle;
import classgroup.JavaValidation;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author javalok
 */
public class Price_Manager extends javax.swing.JInternalFrame {

    private JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String date;
    private String productName;
    private String productQtyName;
    private double price;
    private String productNo;
    // private int price_id;

    public void fillProductNameCombo() {

        try {
            q = "select product_name from tbl_productname_entry";
            new JComboHandle(conn).fillComboProductName(jComboBoxProductName, q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillProductQtyNameCombo() {
        String productNames = jComboBoxProductName.getSelectedItem().toString();
        try {
            q = "select qty_type from tbl_qty_type_manager where product_name=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, productNames);
            rs = pstm.executeQuery();
            while (rs.next()) {
                jComboBoxProdcutQtyType.addItem(rs.getString("qty_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillProductNo(String productName, String qtyType) {
        try {
            q = "select codeno from tbl_qty_type_manager where qty_type=? and product_name=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,productName);
            pstm.setString(2,qtyType);
            rs = pstm.executeQuery();
            if (rs.next()) {
             System.out.println("fillProductNo : name and type "+ productName + ": "+qtyType + "Code NO: "+rs.getString("codeno"));
            jTextFieldProductNo.setText(rs.getString("codeno"));
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {

            date = jTextFieldDate.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate, jTextFieldProductNo, jTextFieldQtyPrice);
            if (flag_tf == true) {
                q = "select product_name , qty_type from tbl_price_manager where product_name=? and qty_type=? and codeno=?";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, jComboBoxProductName.getSelectedItem().toString());
                pstm.setString(2, jComboBoxProdcutQtyType.getSelectedItem().toString());
                pstm.setString(3, jTextFieldProductNo.getText().toString());
                rs = pstm.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Price Name Already Exists", "Dublication Warning!", JOptionPane.WARNING_MESSAGE);
                    this.cancel();
                    return;
                } else {
                    q = "insert into tbl_price_manager(date,codeno ,product_name,qty_type,selling_price) values(?,?,?,?,?)";
                    pstm = conn.prepareStatement(q);
                    pstm.setString(1, date);
                    pstm.setString(2, productNo);
                    pstm.setString(3, productName);
                    pstm.setString(4, productQtyName);
                    pstm.setDouble(5, price);
                    pstm.executeUpdate();
                    pstm.close();
                    JOptionPane.showMessageDialog(this, "Price for Product Added", "Product Price Add Information", JOptionPane.INFORMATION_MESSAGE);
                    this.updateJTable();
                    this.cancel();
                }
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
        jTextFieldProductNo.setText("");
    }

    public void delete() {
        try {
            q = "delete from tbl_price_manager where product_name=? and qty_type=? and codeno=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, jComboBoxProductName.getSelectedItem().toString());
            pstm.setString(2, jComboBoxProdcutQtyType.getSelectedItem().toString());
            pstm.setString(3, jTextFieldProductNo.getText().toString());
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Price for Product Deleted", "Product Price Delete Information", JOptionPane.INFORMATION_MESSAGE);
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
            productName = jComboBoxProductName.getSelectedItem().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());

            q = "update tbl_price_manager set date=? ,codeno=? , product_name=? ,qty_type=?, selling_price=?  where codeno=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2, productNo);
            pstm.setString(3, productName);
            pstm.setString(4, productQtyName);
            pstm.setDouble(5, price);
            pstm.setString(6, productNo);
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Product Price Updated", "Product Price Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable() {
        try {
            q = "select date as 'Date',codeno as 'Code NO',product_name as 'Product Name',qty_type as 'Qty Name', selling_price as  'Qty Price' from tbl_price_manager";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTablePriceManager, q);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + e.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mouseClickJTable() {
        jButtonAdd.setEnabled(false);
        jButtonCancel.setEnabled(true);
        jButtonDelete.setEnabled(true);
        jButtonUpdate.setEnabled(true);

        try {
            int myrow = jTablePriceManager.getSelectedRow();

//            String date_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 0).toString();
//            String productname_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 1).toString();
//            String productqtyname_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 2).toString();
            String qtyid_click = (jTablePriceManager).getModel().getValueAt(myrow, 1).toString();
            q = "select *from tbl_price_manager where codeno=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, qtyid_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                productNo = rs.getString("codeno");
                productName = rs.getString("product_name");
                productQtyName = rs.getString("qty_type");
                price = rs.getDouble("selling_price");
                //price_id = rs.getInt("price_id");
                System.out.println("date: " + date);
                jTextFieldDate.setText(date);
                jTextFieldProductNo.setText(productNo);
                jComboBoxProductName.setSelectedItem(productName);
                jComboBoxProdcutQtyType.setSelectedItem(productQtyName);
                jTextFieldQtyPrice.setText(String.valueOf(price));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + s.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Creates new form Price_Manager
     */
    public Price_Manager(java.sql.Connection conn) {
        initComponents();
        this.conn = conn;
        jButtonAdd.setEnabled(true);
        jButtonCancel.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        this.fillProductNameCombo();
        this.updateJTable();
        javaValidation = new JavaValidation();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        jTextFieldQtyPrice = new javax.swing.JTextField();
        jComboBoxProdcutQtyType = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldProductNo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePriceManager = new javax.swing.JTable();

        setClosable(true);
        setTitle("Price Manager");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Price Manager"));

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

        jLabel4.setText("Product Price");

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

        jLabel5.setText("Product No");

        jTextFieldProductNo.setEditable(false);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldProductNoKeyTyped(evt);
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
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBoxProductName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxProdcutQtyType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(48, 48, 48))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(65, Short.MAX_VALUE))))))
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
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jTablePriceManager.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePriceManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePriceManagerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePriceManager);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldDateMouseClicked
        new NepaliCalendarClassGroup.NepaliDate().setTextFieldWithNepaliDateValue(jTextFieldDate);
        jButtonCancel.setEnabled(true);
}//GEN-LAST:event_jTextFieldDateMouseClicked

    private void jTextFieldDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDateActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldDateActionPerformed

    private void jTextFieldDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDateKeyTyped
        javaValidation.getDateValue(evt);
}//GEN-LAST:event_jTextFieldDateKeyTyped

    private void jTextFieldQtyPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceMouseClicked

    private void jTextFieldQtyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceActionPerformed

    private void jTextFieldQtyPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceKeyTyped
        javaValidation.getDoubleValue(evt);
}//GEN-LAST:event_jTextFieldQtyPriceKeyTyped

    private void jComboBoxProductNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProductNameItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (jComboBoxProductName.getSelectedItem().toString().equalsIgnoreCase("-")) {
                return;
            }
          
            if (jComboBoxProdcutQtyType.getModel() != null) {
                jComboBoxProdcutQtyType.removeAllItems();
            }
            
            jComboBoxProdcutQtyType.addItem("-");
            this.fillProductQtyNameCombo();

        
        }
    }//GEN-LAST:event_jComboBoxProductNameItemStateChanged

    private void jTablePriceManagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePriceManagerMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTablePriceManagerMouseClicked

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        this.add();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        this.update();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        this.delete();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.cancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTextFieldProductNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldProductNoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoMouseClicked

    private void jTextFieldProductNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoActionPerformed

    private void jTextFieldProductNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductNoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoKeyTyped

    private void jComboBoxProdcutQtyTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProdcutQtyTypeItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            
            if (jComboBoxProdcutQtyType.getSelectedItem().toString().equalsIgnoreCase("-")) {
                return;
            }
            try {
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            System.out.println("ITEM STATE CHANGED .......");
            System.out.println("PPPPP : name and type "+ productName + ": "+productQtyName +"");
            
            
            q = "select codeno from tbl_qty_type_manager where qty_type=? and product_name=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,productQtyName);
            pstm.setString(2,productName);
            rs = pstm.executeQuery();
             while(rs.next()){
                 jTextFieldProductNo.setText(rs.getString("codeno"));
             }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
       }
    }//GEN-LAST:event_jComboBoxProdcutQtyTypeItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox jComboBoxProdcutQtyType;
    private javax.swing.JComboBox jComboBoxProductName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePriceManager;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldProductNo;
    private javax.swing.JTextField jTextFieldQtyPrice;
    // End of variables declaration//GEN-END:variables
}
