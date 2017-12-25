/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Product_qtys_Manager.java
 *
 * Created on May 27, 2014, 3:36:00 PM
 */

package lok;

import classgroup.JComboHandle;
import classgroup.JTableHandle;
import classgroup.JavaValidation;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author javalok
 */
public class Product_qtys_Manager extends javax.swing.JInternalFrame {

    private JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";

    private String date;
    private String productNo;
    private String productName;
    private String productQtyName;
    private double price;
    private int qty_amount;
    private int qty_id;

    public void fillProductNameCombo(){
        try{
            q="select product_name from tbl_productname_entry";
            new JComboHandle(conn).fillComboProductName(jComboBoxProductName, q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void add() {
        try {
            date = jTextFieldDate.getText().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName =jTextFieldQtyName.getText().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            qty_amount = Integer.parseInt(jTextFieldQtyAmount.getText().toString());
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate,jTextFieldProductNo
                    ,jTextFieldQtyName,jTextFieldQtyPrice,jTextFieldQtyAmount);
            if (flag_tf == true) {
            q = "select product_name , qty_name from tbl_qty_type_manager where product_name=? and qty_name=? and codeno=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,jComboBoxProductName.getSelectedItem().toString());
            pstm.setString(2,jTextFieldQtyName.getText().toString());
            pstm.setString(3,jTextFieldProductNo.getText().toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Qty Name for Product Name Already Exists", "Dublication Warning!", JOptionPane.WARNING_MESSAGE);
                this.cancel();
                return;
            } else {
                q = "insert into tbl_qty_type_manager(date,codeno,product_name,qty_type,qty_amount,buying_price) values(?,?,?,?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1,date);
                pstm.setString(2,productNo);
                pstm.setString(3,productName);
                pstm.setString(4,productQtyName);
                pstm.setInt(5,qty_amount);
                pstm.setDouble(6,price);
                pstm.executeUpdate();
                pstm.close();
                JOptionPane.showMessageDialog(this, "Product Qty Added", "Product Qty Add Information", JOptionPane.INFORMATION_MESSAGE);
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

    public void cancel(){
        jButtonAdd.setEnabled(true);
      //  jTextFieldDate.setText("");
        jComboBoxProductName.setSelectedIndex(0);
        jTextFieldQtyName.setText("");
        jTextFieldQtyPrice.setText("");
        jTextFieldQtyAmount.setText("");
        jTextFieldProductNo.setText("");
    }

    public void delete(){
        try{
            q = "delete from tbl_qty_type_manager where product_name=? and qty_name=? and codeno=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, jComboBoxProductName.getSelectedItem().toString());
            pstm.setString(2,jTextFieldQtyName.getText().toString());
            pstm.setInt(3,Integer.parseInt(jTextFieldProductNo.getText().toString()));
            pstm.executeUpdate();
            pstm.close();
             JOptionPane.showMessageDialog(this, "Product Qty Deleted", "Product Qty Delete Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public void update(){
        try{
            date = jTextFieldDate.getText().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName =jTextFieldQtyName.getText().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            qty_amount = Integer.parseInt(jTextFieldQtyAmount.getText().toString());

            //q = "update tbl_qty_type_manager set date=? ,codeno=?, qty_type=? , buying_price=? , product_name=? ,qty_amount=? where qty_id=?";
            q = "update tbl_qty_type_manager set date=? ,codeno=?, qty_type=? , buying_price=? , product_name=? ,qty_amount=? where codeno=?";
            pstm = conn.prepareStatement(q);
            pstm = conn.prepareStatement(q);
                pstm.setString(1,date);
                pstm.setString(2,productNo);
                pstm.setString(3,productName);
                pstm.setString(4,productQtyName);
                pstm.setInt(5,qty_amount);
                pstm.setDouble(6,price);
                pstm.setString(7,productNo);
            pstm.executeUpdate();
            pstm.close();
             JOptionPane.showMessageDialog(this, "Product Qty Updated", "Product Qty Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable(){
         try {
            q = "select date as 'Date',codeno as  'Code NO',product_name as 'Product Name',qty_type as 'Qty Name', buying_price as  'Buy Price' ,qty_amount as 'Qty Amount' from tbl_qty_type_manager";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableProductQtyManager, q);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Exception Lok: "+e.getMessage(),"Exception Caught!",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mouseClickJTable(){
        jButtonAdd.setEnabled(false);
        jButtonCancel.setEnabled(true);
        jButtonDelete.setEnabled(true);
        jButtonUpdate.setEnabled(true);

        try {
            int myrow = jTableProductQtyManager.getSelectedRow();

//            String date_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 0).toString();
//            String productname_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 1).toString();
//            String productqtyname_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 2).toString();
            String qtyid_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 5).toString();
            q = "select *from tbl_qty_type_manager where codeno=?";
            pstm = conn.prepareStatement(q);    
            pstm.setString(1,qtyid_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                productNo = rs.getString("codeno");
                productName = rs.getString("product_name");
                productQtyName = rs.getString("qty_type");
                price = rs.getDouble("buying_price");
               // qty_id = rs.getInt("qty_id");
                qty_amount = rs.getInt("qty_amount");
                System.out.println("date: "+date);
                jTextFieldQtyAmount.setText(String.valueOf(qty_amount));
                jTextFieldProductNo.setText(productNo);
                jTextFieldDate.setText(date);
               jComboBoxProductName.setSelectedItem(productName);
               jTextFieldQtyName.setText(productQtyName);
               jTextFieldQtyPrice.setText(String.valueOf(price));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this,"Exception Lok: "+s.getMessage(),"Exception Caught!",JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Creates new form Product_qtys_Manager */
    public Product_qtys_Manager(java.sql.Connection conn) {
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
        jTextFieldQtyName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldQtyPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldQtyAmount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldProductNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProductQtyManager = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();

        setClosable(true);
        setTitle("Product Qty. Manager");

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

        jLabel3.setText("Qty. Name Type");

        jTextFieldQtyName.setToolTipText("");
        jTextFieldQtyName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldQtyNameMouseClicked(evt);
            }
        });
        jTextFieldQtyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldQtyNameActionPerformed(evt);
            }
        });
        jTextFieldQtyName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQtyNameKeyTyped(evt);
            }
        });

        jLabel5.setText("Buying  Price");

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

        jLabel6.setText("Qty Amount");

        jTextFieldQtyAmount.setToolTipText("");
        jTextFieldQtyAmount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldQtyAmountMouseClicked(evt);
            }
        });
        jTextFieldQtyAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldQtyAmountActionPerformed(evt);
            }
        });
        jTextFieldQtyAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQtyAmountKeyTyped(evt);
            }
        });

        jLabel4.setText("Product No.");

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxProductName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldDate, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(39, 39, 39))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldQtyAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(34, 34, 34)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldProductNo)
                            .addComponent(jTextFieldQtyName, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))))
                .addContainerGap())
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldQtyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldQtyAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTableProductQtyManager.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableProductQtyManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductQtyManagerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableProductQtyManager);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jTextFieldQtyNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyNameMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyNameMouseClicked

    private void jTextFieldQtyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyNameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyNameActionPerformed

    private void jTextFieldQtyNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyNameKeyTyped
       javaValidation.getNumberCharacterValue(evt);
}//GEN-LAST:event_jTextFieldQtyNameKeyTyped

    private void jTextFieldQtyPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceMouseClicked

    private void jTextFieldQtyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceActionPerformed

    private void jTextFieldQtyPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyPriceKeyTyped
        javaValidation.getDoubleValue(evt);
}//GEN-LAST:event_jTextFieldQtyPriceKeyTyped

    private void jTableProductQtyManagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductQtyManagerMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTableProductQtyManagerMouseClicked

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

    private void jTextFieldQtyAmountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldQtyAmountMouseClicked

    private void jTextFieldQtyAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldQtyAmountActionPerformed

    private void jTextFieldQtyAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountKeyTyped
        javaValidation.getNumberValue(evt);
    }//GEN-LAST:event_jTextFieldQtyAmountKeyTyped

    private void jTextFieldProductNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldProductNoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoMouseClicked

    private void jTextFieldProductNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoActionPerformed

    private void jTextFieldProductNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductNoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
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
    private javax.swing.JTable jTableProductQtyManager;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldProductNo;
    private javax.swing.JTextField jTextFieldQtyAmount;
    private javax.swing.JTextField jTextFieldQtyName;
    private javax.swing.JTextField jTextFieldQtyPrice;
    // End of variables declaration//GEN-END:variables

}
