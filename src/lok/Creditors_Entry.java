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
public class Creditors_Entry extends javax.swing.JInternalFrame {

    private JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";

    private String date;
    private String creditorName;
    private String creditorAddress;
    private int creditor_id;

    public void add() {
        try {

            date = jTextFieldDate.getText().toString();
            creditorName = jTextFieldCreditorName.getText().toString();
            creditorAddress =jTextAreaCreditorAddress.getText().toString();
            boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFieldDate,jTextFieldCreditorName);
            if (flag_tf == true) {
            q = "select name , address from tbl_creditor_list where name=? and name=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1,jTextFieldCreditorName.getText().toString());
            pstm.setString(2,jTextAreaCreditorAddress.getText().toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Crditor Name Already Exists", "Dublication Warning!", JOptionPane.WARNING_MESSAGE);
                this.cancel();
                return;
            } else {
                q = "insert into tbl_creditor_list(date,name,address) values(?,?,?)";
                pstm = conn.prepareStatement(q);
                pstm.setString(1,date);
                pstm.setString(2,creditorName);
                pstm.setString(3,creditorAddress);
                
                pstm.executeUpdate();
                pstm.close();
                JOptionPane.showMessageDialog(this, "Your Creditor Added", "Creditor Add Information", JOptionPane.INFORMATION_MESSAGE);
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
       jTextFieldCreditorName.setText("");
       jTextAreaCreditorAddress.setText("");
    }

    public void delete(){
        try{
            q = "delete from tbl_creditor_list where creditor_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setInt(1, creditor_id);
            pstm.executeUpdate();
            pstm.close();
             JOptionPane.showMessageDialog(this, "Creditor Deleted", "Creditor Delete Information", JOptionPane.INFORMATION_MESSAGE);
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
            creditorName = jTextFieldCreditorName.getText().toString();
            creditorAddress =(String) jTextAreaCreditorAddress.getText().toString();
            q = "update tbl_creditor_list set date=? , name=? , address=?  where creditor_id=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            pstm.setString(2,creditorName);
            pstm.setString(3,creditorAddress);
            pstm.setInt(4,creditor_id);
            pstm.executeUpdate();
            pstm.close();
             JOptionPane.showMessageDialog(this, "Your Creditor Updated", "Creditor Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void updateJTable(){
         try {
            q = "select date as 'Date',name as 'Creditor Name',address as 'Address', creditor_id as  'Creditor ID' from tbl_creditor_list";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableCreditorEntry, q);
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
            int myrow = jTableCreditorEntry.getSelectedRow();

//            String date_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 0).toString();
//            String productname_click = (jTableProductQtyManager).getModel().getValueAt(myrow, 1).toString();
//            String productqtyname_click =(jTableProductQtyManager).getModel().getValueAt(myrow, 2).toString();
            String qtyid_click =(jTableCreditorEntry).getModel().getValueAt(myrow, 3).toString();
            q = "select *from tbl_creditor_list where creditor_id=?";
            pstm = conn.prepareStatement(q);    
            pstm.setString(1,qtyid_click);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                creditorName = rs.getString("name");
                creditorAddress = rs.getString("address");

                creditor_id  = rs.getInt("creditor_id");
               
                jTextFieldDate.setText(date);
                jTextFieldCreditorName.setText(creditorName);
                jTextAreaCreditorAddress.setText(creditorAddress);
            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this,"Exception Lok: "+s.getMessage(),"Exception Caught!",JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Creates new form Product_qtys_Manager */
    public Creditors_Entry(java.sql.Connection conn) {
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
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCreditorName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaCreditorAddress = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCreditorEntry = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();

        setClosable(true);
        setTitle("Product Qty. Manager");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Name Entry"));

        jLabel1.setText("Creditor Name");

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

        jLabel3.setText("Address");

        jTextFieldCreditorName.setToolTipText("");
        jTextFieldCreditorName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldCreditorNameMouseClicked(evt);
            }
        });
        jTextFieldCreditorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCreditorNameActionPerformed(evt);
            }
        });
        jTextFieldCreditorName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCreditorNameKeyTyped(evt);
            }
        });

        jTextAreaCreditorAddress.setColumns(20);
        jTextAreaCreditorAddress.setRows(5);
        jScrollPane2.setViewportView(jTextAreaCreditorAddress);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDate)
                    .addComponent(jTextFieldCreditorName)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jTextFieldCreditorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableCreditorEntry.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCreditorEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCreditorEntryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCreditorEntry);

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
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
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

    private void jTableCreditorEntryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCreditorEntryMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTableCreditorEntryMouseClicked

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

    private void jTextFieldCreditorNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldCreditorNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCreditorNameMouseClicked

    private void jTextFieldCreditorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCreditorNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCreditorNameActionPerformed

    private void jTextFieldCreditorNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCreditorNameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCreditorNameKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCreditorEntry;
    private javax.swing.JTextArea jTextAreaCreditorAddress;
    private javax.swing.JTextField jTextFieldCreditorName;
    private javax.swing.JTextField jTextFieldDate;
    // End of variables declaration//GEN-END:variables

}
