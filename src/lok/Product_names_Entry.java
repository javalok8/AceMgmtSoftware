/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Product_names_Entry.java
 *
 * Created on May 27, 2014, 3:36:47 PM
 */
package lok;

import classgroup.JTableHandle;
import classgroup.JavaValidation;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author javalok
 */
public class Product_names_Entry extends javax.swing.JInternalFrame {

    private JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";

    private String date;
    private String productName;
    private int id;

    public void add() {
        try {
            date = jTextFieldDate.getText().toString();
            productName = jTextFieldProductName.getText().toString();
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate, jTextFieldProductName);
            if (flag_tf == true) {
            q = "select product_name from tbl_productname_entry where product_name=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,jTextFieldProductName.getText().toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Product Name Already Exists", "Dublication Warning!", JOptionPane.WARNING_MESSAGE);
                this.cancel();
                return;
            } else {
                q = "insert into tbl_productname_entry(date,product_name) values(?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1,date);
                pstm.setString(2,productName);
                pstm.executeUpdate();
                pstm.close();
                JOptionPane.showMessageDialog(this, "Product Added", "Product Add Information", JOptionPane.INFORMATION_MESSAGE);
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
       // jTextFieldDate.setText("");
        jTextFieldProductName.setText("");
    }

    

    public void delete(){
        try{
            q = "delete from tbl_productname_entry where product_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            pstm.close();
             JOptionPane.showMessageDialog(this, "Product Deleted", "Product Delete Information", JOptionPane.INFORMATION_MESSAGE);
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
            productName = jTextFieldProductName.getText().toString();
          
            q = "update tbl_productname_entry set date=? , product_name=? where product_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2,productName);
            pstm.setInt(3, id);
            pstm.executeUpdate();
            pstm.close();
             JOptionPane.showMessageDialog(this, "Product Updated", "Product Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable(){
         try {
            q = "select date as 'Date',product_name as 'Product Name',product_id as 'Id' from tbl_productname_entry";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableProductNameEntry, q);
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
            int myrow = jTableProductNameEntry.getSelectedRow();

            String date_click = (jTableProductNameEntry).getModel().getValueAt(myrow, 0).toString();
            String classname_click = (jTableProductNameEntry).getModel().getValueAt(myrow, 1).toString();
            String id_click =(jTableProductNameEntry).getModel().getValueAt(myrow, 2).toString();
            q = "select *from tbl_productname_entry where date =? and product_name=? and product_id=? ";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,date_click);
            pstm.setString(2,classname_click);
            pstm.setString(3,id_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                productName = rs.getString("product_name");
                id = rs.getInt("product_id");
                jTextFieldDate.setText(date);
               jTextFieldProductName.setText(productName);
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this,"Exception Lok: "+s.getMessage(),"Exception Caught!",JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Creates new form Product_names_Entry */
    public Product_names_Entry(java.sql.Connection conn) {
        initComponents();
        this.conn = conn;
        jButtonAdd.setEnabled(true);
        jButtonCancel.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonUpdate.setEnabled(false);
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
        jTextFieldProductName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDate = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProductNameEntry = new javax.swing.JTable();

        setClosable(true);
        setTitle("Product Name Entry");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Name Entry"));

        jLabel1.setText("Product Name");

        jTextFieldProductName.setToolTipText("");
        jTextFieldProductName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldProductNameMouseClicked(evt);
            }
        });
        jTextFieldProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldProductNameActionPerformed(evt);
            }
        });
        jTextFieldProductName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldProductNameKeyTyped(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDate)
                    .addComponent(jTextFieldProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
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
                    .addComponent(jTextFieldProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTableProductNameEntry.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableProductNameEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductNameEntryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableProductNameEntry);

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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldProductNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldProductNameMouseClicked

}//GEN-LAST:event_jTextFieldProductNameMouseClicked

    private void jTextFieldProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductNameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldProductNameActionPerformed

    private void jTextFieldProductNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductNameKeyTyped
       // new JavaValidation().getCharacterValue(evt);
}//GEN-LAST:event_jTextFieldProductNameKeyTyped

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

    private void jTableProductNameEntryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductNameEntryMouseClicked
       this.mouseClickJTable();
    }//GEN-LAST:event_jTableProductNameEntryMouseClicked

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProductNameEntry;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldProductName;
    // End of variables declaration//GEN-END:variables
}
