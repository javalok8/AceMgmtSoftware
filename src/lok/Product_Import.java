/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Product_Import.java
 *
 * Created on May 27, 2014, 3:35:18 PM
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
public class Product_Import extends javax.swing.JInternalFrame {

    JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String date;
    private String productNo;
    private String productName;
    private String productQtyName;
    private double price;
    private int product_qty;
    private double total_amount;
    private int total_qty_amount;
    private int import_id;
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

            q = "select qty_name from tbl_qty_type_manager where product_name=?";
            new JComboHandle(conn).fillComboByProductQtyType(jComboBoxProdcutQtyType, q, productNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        try {

            date = jTextFieldDate.getText().toString();
            productNo = jTextFieldProductNo.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQtyAmount.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            description = jTextAreaDescription.getText().toString();
            
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate,jTextFieldProductNo, jTextFieldQtyPrice);
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
                total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQtyAmount.getText().toString());
                System.out.println("Total Qty Amount from Transaction is: " + total_qty_amount);
                q = "insert into tbl_import_product_record(date,codeno,product_name,product_qty_name,product_single_price,product_qty,total_amount,qty_amount,description) values(?,?,?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, date);
                pstm.setString(2,productNo);
                pstm.setString(3, productName);
                pstm.setString(4, productQtyName);
                pstm.setDouble(5, price);
                pstm.setInt(6, product_qty);
                pstm.setDouble(7, total_amount);
                pstm.setInt(8, total_qty_amount);
                pstm.setString(9,description);
                pstm.executeUpdate();
                pstm.close();
                JOptionPane.showMessageDialog(this, "Product Added", "Product Add Information", JOptionPane.INFORMATION_MESSAGE);
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
        jComboBoxProductName.setSelectedIndex(0);
        jComboBoxProdcutQtyType.setSelectedIndex(0);
        jTextFieldQtyPrice.setText("");
        jTextFieldQtyAmount.setText("");
        jTextFieldTotalAmount.setText("");
        jTextFieldProductNo.setText("");
        jTextAreaDescription.setText("");
    }

    public void delete() {
        try {
            q = "delete from tbl_import_product_record where product_name=? and product_qty_name=? and import_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, jComboBoxProductName.getSelectedItem().toString());
            pstm.setString(2, jComboBoxProdcutQtyType.getSelectedItem().toString());
            pstm.setInt(3, import_id);
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Product Deleted", "Product Delete Information", JOptionPane.INFORMATION_MESSAGE);
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
            productNo = jTextFieldProductNo.getText().toString();
            productName = jComboBoxProductName.getSelectedItem().toString();
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
            product_qty = Integer.parseInt(jTextFieldQtyAmount.getText().toString());
            total_amount = Double.parseDouble(jTextFieldTotalAmount.getText().toString());
            description = jTextAreaDescription.getText().toString();

            q = "update tbl_import_product_record set date=? ,codeno=? , product_name=? ,product_qty_name=?, product_single_price=?,product_qty=? , total_amount=? ,qty_amount=? , description = ? where import_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
                pstm.setString(2,productNo);
                pstm.setString(3, productName);
                pstm.setString(4, productQtyName);
                pstm.setDouble(5, price);
                pstm.setInt(6, product_qty);
                pstm.setDouble(7, total_amount);
                pstm.setInt(8, total_qty_amount);
                pstm.setString(9,description);
            pstm.setInt(10, import_id);
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Product Updated", "Product Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable() {
        try {
            q = "select date as 'Date',codeno as 'Code NO',product_name as 'Product Name',product_qty_name as 'Qty Name', product_single_price as  'Qty Price' , product_qty as 'Product Qty', total_amount as 'Total Amount', id as  'Import ID' from tbl_import_product_record";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableProductImport, q);
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
            int myrow = jTableProductImport.getSelectedRow();

//            String date_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 0).toString();
//            String productname_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 1).toString();
//            String productqtyname_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 2).toString();
            String qtyid_click = (jTableProductImport).getModel().getValueAt(myrow, 6).toString();
            q = "select *from tbl_import_product_record where import_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, qtyid_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                productNo = rs.getString("codeno");
                productName = rs.getString("product_name");
                productQtyName = rs.getString("product_qty_name");
                price = rs.getDouble("product_single_price");
                product_qty = rs.getInt("product_qty");
                total_amount = rs.getDouble("total_amount");
                total_qty_amount = rs.getInt("total_qty_amount");
                description = rs.getString("description");
                import_id = rs.getInt("import_id");

                jTextFieldDate.setText(date);
                jTextFieldProductNo.setText(productNo);
                jComboBoxProductName.setSelectedItem(productName);
                jComboBoxProdcutQtyType.setSelectedItem(productQtyName);
                jTextFieldQtyPrice.setText(String.valueOf(price));
                jTextFieldQtyAmount.setText(String.valueOf(product_qty));
                jTextFieldTotalAmount.setText(String.valueOf(total_amount));
                jTextAreaDescription.setText(description);
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + s.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }

    }

    /** Creates new form Product_Import */
    public Product_Import(java.sql.Connection conn) {
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
        jLabel4 = new javax.swing.JLabel();
        jTextFieldQtyPrice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldQtyAmount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTotalAmount = new javax.swing.JTextField();
        jComboBoxProdcutQtyType = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldProductNo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProductImport = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();

        setClosable(true);
        setTitle("Product Import");

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

        jLabel4.setText("Product Price");

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

        jLabel5.setText("Product qty");

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldQtyAmountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQtyAmountKeyTyped(evt);
            }
        });

        jLabel6.setText("Total Amount");

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

        jComboBoxProdcutQtyType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxProdcutQtyTypeItemStateChanged(evt);
            }
        });

        jLabel7.setText("Product No");

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

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescription);

        jLabel8.setText("Description");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxProductName, javax.swing.GroupLayout.Alignment.TRAILING, 0, 133, Short.MAX_VALUE)
                            .addComponent(jTextFieldDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxProdcutQtyType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldQtyAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jComboBoxProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxProdcutQtyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldProductNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldQtyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldQtyAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTableProductImport.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableProductImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductImportMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableProductImport);

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
                        .addGap(25, 25, 25)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(51, 51, 51))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
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
        // TODO add your handling code here:
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

    private void jTextFieldQtyAmountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyAmountMouseClicked

    private void jTextFieldQtyAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldQtyAmountActionPerformed

    private void jTextFieldQtyAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountKeyTyped
        javaValidation.getNumberValue(evt);
}//GEN-LAST:event_jTextFieldQtyAmountKeyTyped

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

    private void jTableProductImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductImportMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTableProductImportMouseClicked

    private void jComboBoxProdcutQtyTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProdcutQtyTypeItemStateChanged

//        productName = jComboBoxProductName.getSelectedItem().toString();
//        productQtyName= jComboBoxProdcutQtyType.getSelectedItem().toString();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            productQtyName = jComboBoxProdcutQtyType.getSelectedItem().toString();
            System.out.println("product name: " + productName);
            System.out.println("product qty name: " + productQtyName);
            try {
                q = "select codeno , qty_price , qty_amount from tbl_qty_type_manager where product_name=? and qty_name=?";
                pstm = conn.prepareStatement(q);
                pstm.setString(1, productName);
                pstm.setString(2, productQtyName);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    product_price = rs.getDouble("qty_price");
                    temp_qty_amount = rs.getInt("qty_amount");
                    productNo = rs.getString("codeno");
                    System.out.println("Temp qty amount: " + temp_qty_amount);
                    //   total_qty_amount = temp_qty_amount * Integer.parseInt(jTextFieldQtyAmount.getText().toString());
                    System.out.println("prodct price: " + product_price);
                    jTextFieldProductNo.setText(productNo);
                    jTextFieldQtyPrice.setText(String.valueOf(product_price));
                    

                }
                rs.close();
                pstm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_jComboBoxProdcutQtyTypeItemStateChanged

    private void jTextFieldQtyAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyAmountKeyReleased
        double price, total_amount;
        int qty;

        price = Double.parseDouble(jTextFieldQtyPrice.getText().toString());
        qty = Integer.parseInt(jTextFieldQtyAmount.getText().toString());
        String p = String.valueOf(qty);
        total_amount = price * Double.parseDouble(p);
        jTextFieldTotalAmount.setText(String.valueOf(total_amount));


    }//GEN-LAST:event_jTextFieldQtyAmountKeyReleased

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
    private javax.swing.JComboBox jComboBoxProdcutQtyType;
    private javax.swing.JComboBox jComboBoxProductName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableProductImport;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldProductNo;
    private javax.swing.JTextField jTextFieldQtyAmount;
    private javax.swing.JTextField jTextFieldQtyPrice;
    private javax.swing.JTextField jTextFieldTotalAmount;
    // End of variables declaration//GEN-END:variables
}
