/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Product_qtys_Manager.java
 *
 * Created on May 27, 2014, 3:36:00 PM
 */
package Reports;

import classgroup.JavaValidation;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author javalok
 */
public class Report_Annual_Finance extends javax.swing.JInternalFrame {

    private JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String startDate;
    private String endDate;
    private int count;
    private String[] totalProductName;
    private double[] totalImportPrice;

    /** Creates new form Product_qtys_Manager */
    public Report_Annual_Finance(java.sql.Connection conn) {
        initComponents();
        this.conn = conn;
        javaValidation = new JavaValidation();
        jTextFieldStartDate.setEnabled(false);
        jTextFieldStartDate.setVisible(false);
        jLabel3.setVisible(false);
        jTextFieldEnd.setEnabled(false);
        jTextFieldEnd.setVisible(false);
        jLabel4.setVisible(false);
        jPanel1.updateUI();
        jPanel1.revalidate();

    }

    public void TotalFinanceReportGenerator() throws Exception {
        startDate = jTextFieldStartDate.getText();
        if (jRadioButtonDaily.isSelected() == true) {
            endDate = startDate;
        } else {
            endDate = jTextFieldEnd.getText();

        }
        System.out.println("StartDate: " + startDate + " End Date: " + endDate);

        //Delete all data from table report_temp for new data
        q = "delete from report_temp";
        pstm = conn.prepareStatement(q);
        pstm.executeUpdate();
        System.out.println("Delete Successfully!!");

        //Import
        count = 0;
        q = "select distinct product_name from tbl_import_product_record where date between  '" + startDate + "' and '" + endDate + "'";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        while (rs.next()) {
            count = rs.getRow();
        }
        System.out.println("Total Count: " + count);
        totalProductName = new String[count];
        rs.close();
        pstm.close();

        q = "select distinct product_name from tbl_import_product_record where date between  '" + startDate + "' and '" + endDate + "'";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        if (rs.next()) {
            for (int i = 0; i < count; i++) {
                totalProductName[i] = rs.getString("product_name");
                System.out.println("Product Name( " + i + 1 + ") : " + totalProductName[i]);
                rs.next();
            }
        }
        rs.close();
        pstm.close();
        totalImportPrice = new double[count];
        q = "select sum(total_amount) from tbl_import_product_record where (date between  '" + startDate + "' and '" + endDate + "') and product_name=?";
        for (int i = 0; i < count; i++) {
            pstm = conn.prepareStatement(q);
            pstm.setString(1, totalProductName[i]);
            rs = pstm.executeQuery();
            if (rs.next()) {
                totalImportPrice[i] = rs.getDouble("sum(total_amount)");
                System.out.println("Product Price( " + i + 1 + ") : " + totalImportPrice[i]);
            }
        }

        for (int i = 0; i < count; i++) {
            q = "insert into report_temp(a,b,c) values(?,?,?)";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, "import");
            pstm.setString(2, totalProductName[i]);
            pstm.setDouble(3, totalImportPrice[i]);
            pstm.executeUpdate();
        }

        //Sell
        count = 0;
        q = "select distinct product_name from tbl_selling_product_record where date between  '" + startDate + "' and '" + endDate + "'";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        while (rs.next()) {
            count = rs.getRow();
        }
        System.out.println("Total Count: " + count);
        totalProductName = new String[count];
        rs.close();
        pstm.close();

        q = "select distinct product_name from tbl_selling_product_record where date between  '" + startDate + "' and '" + endDate + "'";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        if (rs.next()) {
            for (int i = 0; i < count; i++) {
                totalProductName[i] = rs.getString("product_name");
                System.out.println("Product Name( " + i + 1 + ") : " + totalProductName[i]);
                rs.next();
            }
        }
        rs.close();
        pstm.close();
        totalImportPrice = new double[count];
        q = "select sum(total_amount) from tbl_selling_product_record where (date between  '" + startDate + "' and '" + endDate + "') and product_name=?";
        for (int i = 0; i < count; i++) {
            pstm = conn.prepareStatement(q);
            pstm.setString(1, totalProductName[i]);
            rs = pstm.executeQuery();
            if (rs.next()) {
                totalImportPrice[i] = rs.getDouble("sum(total_amount)");
                System.out.println("Product Price( " + i + 1 + ") : " + totalImportPrice[i]);
            }
        }

        for (int i = 0; i < count; i++) {
            q = "insert into report_temp(a,b,c) values(?,?,?)";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, "sell");
            pstm.setString(2, totalProductName[i]);
            pstm.setDouble(3, totalImportPrice[i]);
            pstm.executeUpdate();
        }

        //lost and damage
        count = 0;
        q = "select distinct product_name from tbl_damage_lost_product where date between  '" + startDate + "' and '" + endDate + "'";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        while (rs.next()) {
            count = rs.getRow();
        }
        System.out.println("Total Count: " + count);
        totalProductName = new String[count];
        rs.close();
        pstm.close();

        q = "select distinct product_name from tbl_damage_lost_product where date between  '" + startDate + "' and '" + endDate + "'";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        if (rs.next()) {
            for (int i = 0; i < count; i++) {
                totalProductName[i] = rs.getString("product_name");
                System.out.println("Product Name( " + i + 1 + ") : " + totalProductName[i]);
                rs.next();
            }
        }
        rs.close();
        pstm.close();
        totalImportPrice = new double[count];
        q = "select sum(total_amount) from tbl_damage_lost_product where (date between  '" + startDate + "' and '" + endDate + "') and product_name=?";
        for (int i = 0; i < count; i++) {
            pstm = conn.prepareStatement(q);
            pstm.setString(1, totalProductName[i]);
            rs = pstm.executeQuery();
            if (rs.next()) {
                totalImportPrice[i] = rs.getDouble("sum(total_amount)");
                System.out.println("Product Price( " + i + 1 + ") : " + totalImportPrice[i]);
            }
        }

        for (int i = 0; i < count; i++) {
            q = "insert into report_temp(a,b,c) values(?,?,?)";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, "damage/lost");
            pstm.setString(2, totalProductName[i]);
            pstm.setDouble(3, totalImportPrice[i]);
            pstm.executeUpdate();
        }


        //Open Report
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldStartDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldEnd = new javax.swing.JTextField();
        jRadioButtonMonthly = new javax.swing.JRadioButton();
        jRadioButtonDaily = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();

        setClosable(true);
        setTitle("Total Finance Report");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Annual Finance Report"));

        jLabel2.setText("Start Date :");

        jTextFieldStartDate.setToolTipText("");
        jTextFieldStartDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldStartDateMouseClicked(evt);
            }
        });
        jTextFieldStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStartDateActionPerformed(evt);
            }
        });
        jTextFieldStartDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldStartDateKeyTyped(evt);
            }
        });

        jLabel3.setText("Report Type :");

        jLabel4.setText("End Date :");

        jTextFieldEnd.setToolTipText("");
        jTextFieldEnd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldEndMouseClicked(evt);
            }
        });
        jTextFieldEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEndActionPerformed(evt);
            }
        });
        jTextFieldEnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEndKeyTyped(evt);
            }
        });

        buttonGroup1.add(jRadioButtonMonthly);
        jRadioButtonMonthly.setText("Monthly");
        jRadioButtonMonthly.setOpaque(false);
        jRadioButtonMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMonthlyActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonDaily);
        jRadioButtonDaily.setText("Daily");
        jRadioButtonDaily.setOpaque(false);
        jRadioButtonDaily.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonDailyItemStateChanged(evt);
            }
        });
        jRadioButtonDaily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDailyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldEnd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldStartDate))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButtonDaily)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonMonthly)
                        .addGap(59, 59, 59))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButtonDaily)
                    .addComponent(jRadioButtonMonthly))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonCancel.setText("Cance");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAdd.setText("Generate");
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
                .addGap(2, 2, 2)
                .addComponent(jButtonAdd)
                .addGap(18, 18, 18)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonAdd))
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
                        .addGap(72, 72, 72)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldStartDateMouseClicked
        new NepaliCalendarClassGroup.NepaliDate().setTextFieldWithNepaliDateValue(jTextFieldStartDate);
        jButtonCancel.setEnabled(true);
}//GEN-LAST:event_jTextFieldStartDateMouseClicked

    private void jTextFieldStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStartDateActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldStartDateActionPerformed

    private void jTextFieldStartDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldStartDateKeyTyped
        javaValidation.getDateValue(evt);
}//GEN-LAST:event_jTextFieldStartDateKeyTyped

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
//        try {
//            this.TotalFinanceReportGenerator();
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, e);
//        }
        JOptionPane.showMessageDialog(this, "data should store more that a month");
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTextFieldEndMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldEndMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEndMouseClicked

    private void jTextFieldEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEndActionPerformed

    private void jTextFieldEndKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEndKeyTyped
        javaValidation.getDateValue(evt);
    }//GEN-LAST:event_jTextFieldEndKeyTyped

    private void jRadioButtonMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMonthlyActionPerformed
        jTextFieldStartDate.setEnabled(true);
        jTextFieldStartDate.setVisible(true);
        jLabel3.setVisible(true);
        jTextFieldEnd.setEnabled(true);
        jTextFieldEnd.setVisible(true);
        jLabel4.setVisible(true);
        jPanel1.updateUI();
        jPanel1.revalidate();
    }//GEN-LAST:event_jRadioButtonMonthlyActionPerformed

    private void jRadioButtonDailyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonDailyItemStateChanged
    }//GEN-LAST:event_jRadioButtonDailyItemStateChanged

    private void jRadioButtonDailyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDailyActionPerformed
        jTextFieldStartDate.setEnabled(true);
        jTextFieldStartDate.setVisible(true);
        jLabel3.setVisible(true);
        jTextFieldEnd.setEnabled(false);
        jTextFieldEnd.setVisible(false);
        jLabel4.setVisible(false);
        jPanel1.updateUI();
        jPanel1.revalidate();
    }//GEN-LAST:event_jRadioButtonDailyActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButtonDaily;
    private javax.swing.JRadioButton jRadioButtonMonthly;
    private javax.swing.JTextField jTextFieldEnd;
    private javax.swing.JTextField jTextFieldStartDate;
    // End of variables declaration//GEN-END:variables
}
