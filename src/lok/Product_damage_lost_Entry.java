/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Product_damage_lost_Entry.java
 *
 * Created on May 27, 2014, 3:53:52 PM
 */
package lok;

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
public class Product_damage_lost_Entry extends javax.swing.JInternalFrame {

    JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String date;
    private String codeno;
    private String productName;
    private String productQtyName;
    private String type;
    private double price;
    private int product_qty;
    private double total_amount;
    private int total_qty_amount;
    private int id;
    double product_price = 0.0;
    int temp_qty_amount = 0;
    private String description;

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
            new JComboHandle(conn).fillComboByProductQtyType(jComboBoxProdcutQty, q, productNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {

            date = jTextFieldDate.getText().toString();
            codeno = jTextFieldProductNo.getText().toString();
            type = jComboBoxType.getSelectedItem().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQty.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            description = jTextArea1.getText().toString();

            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate,jTextFieldProductNo, jTextFieldQtyPrice, jTextFieldQty);
            if (flag_tf == true) {
                total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());
                q = "insert into tbl_damage_lost_product(date,codeno,product_name,product_qty_name,product_qty,product_single_price,total_amount,type,description) values(?,?,?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, date);
                pstm.setString(2, codeno);
                pstm.setString(3, productName);
                pstm.setString(4, productQtyName);
                pstm.setInt(5, product_qty);
                pstm.setDouble(6, price);
                pstm.setDouble(7, total_amount);
                pstm.setString(8, type);
                pstm.setString(9,description);
                pstm.executeUpdate();
                pstm.close();
                JOptionPane.showMessageDialog(this, "Damage Product Recorded", "Damage Product Add Information", JOptionPane.INFORMATION_MESSAGE);
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

    public void cancel() {
        jButtonAdd.setEnabled(true);
        //  jTextFieldDate.setText("");
        jComboBoxType.setSelectedIndex(0);
        jComboBoxProductName.setSelectedIndex(0);
        jComboBoxProdcutQty.setSelectedIndex(0);
        jTextFieldQtyPrice.setText("");
        jTextFieldQty.setText("");
        jTextFieldTotalAmount.setText("");
        jTextFieldProductNo.setText("");
        jTextArea1.setText("");
    }

    public void delete() {
        try {
            q = "delete from tbl_damage_lost_product where id=?";
            pstm = conn.prepareStatement(q);
            pstm.setInt(1,id);
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Damage Product Record Deleted", "Damage Product Delete Information", JOptionPane.INFORMATION_MESSAGE);
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
            codeno = jTextFieldProductNo.getText().toString();
            type = jComboBoxType.getSelectedItem().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQty.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQty.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            description = jTextArea1.getText().toString();
            
            q = "update tbl_damage_lost_product set date=? ,codeno=? , product_name=? ,product_qty_name=?, product_single_price=?,product_qty=?,total_amount=? ,type=? ,description=? where id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2, codeno);
            pstm.setString(3, productName);
            pstm.setString(4, productQtyName);
            pstm.setDouble(5, price);
            pstm.setInt(6, product_qty);
            pstm.setDouble(7, total_amount);
            pstm.setString(8,type);
            pstm.setString(9, description);
            pstm.setDouble(10, id);
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Damage Product Updated", " Damage Product Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable() {
        try {
            q = "select date as 'Date',type as 'Type' ,product_name as 'Product Name',product_qty_name as 'Qty Name', product_single_price as  'Qty Price' , product_qty as 'Product Qty',total_amount as 'Total Amount', id as  'ID' from tbl_damage_lost_product";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableDamageEntry, q);
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
            int myrow = jTableDamageEntry.getSelectedRow();

//            String date_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 0).toString();
//            String productname_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 1).toString();
//            String productqtyname_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 2).toString();
            String qtyid_click = (jTableDamageEntry).getModel().getValueAt(myrow, 7).toString();
            q = "select *from tbl_damage_lost_product where id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, qtyid_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                codeno = rs.getString("codeno");
                productName = rs.getString("product_name");
                productQtyName = rs.getString("product_qty_name");
                price = rs.getDouble("product_single_price");
                product_qty = rs.getInt("product_qty");
                total_amount = rs.getDouble("total_amount");
                type = rs.getString("type");
                description = rs.getString("description");
                id = rs.getInt("id");
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + s.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }
        jTextFieldDate.setText(date);
        jTextFieldProductNo.setText(codeno);
        jComboBoxType.setSelectedItem(type);
        jComboBoxProductName.setSelectedItem(productName);
        jComboBoxProdcutQty.setSelectedItem(productQtyName);
        jTextFieldQtyPrice.setText(String.valueOf(price));
        jTextFieldQty.setText(String.valueOf(product_qty));
        jTextFieldTotalAmount.setText(String.valueOf(total_amount));
        jTextArea1.setText(description);
    }

    /** Creates new form Product_damage_lost_Entry */
    public Product_damage_lost_Entry(java.sql.Connection conn) {
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
        jComboBoxType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldQtyPrice = new javax.swing.JTextField();
        jComboBoxProductName = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldQty = new javax.swing.JTextField();
        jComboBoxProdcutQty = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldTotalAmount = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldProductNo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDamageEntry = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();

        setClosable(true);
        setTitle("Damage|Lost Product Record");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Name Entry"));

        jLabel1.setText("Type");

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

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Lost", "Damage" }));
        jComboBoxType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTypeItemStateChanged(evt);
            }
        });

        jLabel3.setText("Product Name");

        jLabel4.setText("Product qty Type");

        jLabel5.setText("Product Price");

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

        jComboBoxProductName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxProductName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxProductNameItemStateChanged(evt);
            }
        });

        jLabel6.setText("Product Qty");

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

        jComboBoxProdcutQty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxProdcutQty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxProdcutQtyItemStateChanged(evt);
            }
        });
        jComboBoxProdcutQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProdcutQtyActionPerformed(evt);
            }
        });

        jLabel7.setText("Total Amount");

        jTextFieldTotalAmount.setEditable(false);
        jTextFieldTotalAmount.setToolTipText("Mouse Click to get Total Amount");
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

        jLabel8.setText("Product No");

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

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel9.setText("Description");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTotalAmount)
                            .addComponent(jTextFieldQty)
                            .addComponent(jTextFieldQtyPrice)
                            .addComponent(jTextFieldProductNo))
                        .addGap(48, 48, 48))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxProdcutQty, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxProductName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.Alignment.LEADING, 0, 147, Short.MAX_VALUE)
                            .addComponent(jTextFieldDate, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxProdcutQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)))
                .addContainerGap(29, Short.MAX_VALUE))
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

        jTableDamageEntry.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDamageEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDamageEntryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDamageEntry);

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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
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
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyPriceKeyTyped

    private void jTextFieldQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyMouseClicked

    private void jTextFieldQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyActionPerformed

    private void jTextFieldQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyKeyTyped
        javaValidation.getNumberValue(evt);
          
}//GEN-LAST:event_jTextFieldQtyKeyTyped

    private void jComboBoxProductNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProductNameItemStateChanged
        productName = jComboBoxProductName.getSelectedItem().toString();
        System.out.println("Product Name is: " + productName);
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            if (jComboBoxProdcutQty.getModel() != null) {
                jComboBoxProdcutQty.removeAllItems();
                jComboBoxProdcutQty.addItem("-");
            }
            this.fillProductQtyNameCombo(productName);
        }
    }//GEN-LAST:event_jComboBoxProductNameItemStateChanged

    private void jComboBoxTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTypeItemStateChanged
    }//GEN-LAST:event_jComboBoxTypeItemStateChanged

    private void jTableDamageEntryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDamageEntryMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTableDamageEntryMouseClicked

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

    private void jComboBoxProdcutQtyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProdcutQtyItemStateChanged

//        productName = jComboBoxProductName.getSelectedItem().toString();
//        productQtyName= jComboBoxProdcutQtyType.getSelectedItem().toString();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            productQtyName = jComboBoxProdcutQty.getSelectedItem().toString();
            System.out.println("product name: " + productName);
            System.out.println("product qty name: " + productQtyName);
            try {
                q = "select buying_price ,codeno from tbl_qty_type_manager where product_name=? and qty_type=?";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, productName);
                pstm.setString(2, productQtyName);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    product_price = rs.getDouble("buying_price");
                    String codeno1 = rs.getString("codeno");
                    //  total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQty.getText().toString());
                    System.out.println("prodct price: " + product_price);
                    jTextFieldQtyPrice.setText(String.valueOf(product_price));
                    jTextFieldProductNo.setText(codeno1);
                }
                rs.close();
                pstm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jComboBoxProdcutQtyItemStateChanged

    private void jTextFieldTotalAmountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldTotalAmountMouseClicked

    private void jTextFieldTotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldTotalAmountActionPerformed

    private void jTextFieldTotalAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTotalAmountKeyTyped
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldTotalAmountKeyTyped

    private void jTextFieldQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyKeyReleased
        double price, total_amount;
        int qty;

        price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
        qty = Integer.parseInt(jTextFieldQty.getText().toString());
        String p = String.valueOf(qty);
        total_amount = price * Double.parseDouble(p);
        jTextFieldTotalAmount.setText(String.valueOf(total_amount));

    }//GEN-LAST:event_jTextFieldQtyKeyReleased

    private void jComboBoxProdcutQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProdcutQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxProdcutQtyActionPerformed

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
    private javax.swing.JComboBox jComboBoxProdcutQty;
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
    private javax.swing.JTable jTableDamageEntry;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldProductNo;
    private javax.swing.JTextField jTextFieldQty;
    private javax.swing.JTextField jTextFieldQtyPrice;
    private javax.swing.JTextField jTextFieldTotalAmount;
    // End of variables declaration//GEN-END:variables
}
