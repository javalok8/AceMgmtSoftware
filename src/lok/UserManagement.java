/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserManagement.java
 *
 * Created on Aug 25, 2013, 2:24:17 PM
 */
package lok;


import DbHelper.DBConnection;
import classgroup.JTableHandle;
import classgroup.JavaValidation;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author JA
 */
public class UserManagement extends javax.swing.JInternalFrame {

    /*variables declarations*/
    private DBConnection dbcon = null;
    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";
    private String datechoose;
    private String name;
    private String password;
    private String date;
    private boolean mImport;
    private boolean mUtilityManagement;
    private boolean mSelling;
    private boolean mReporting;
    private boolean mOther;
   
    private JavaValidation javaValidation = null;

    private boolean luck_status;
    private int ref_id;


    /** Creates new form UserManagement */
    public UserManagement() {
        initComponents();
    }

    public UserManagement(String user,java.sql.Connection connn) {
        initComponents();
        jButtonAdd.setEnabled(true);
        jButtonCancel.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        javaValidation = new JavaValidation();
         try {
             this.conn = connn;
             System.out.println("Connection Object inside usermanagement: "+ conn);
            this.updateJTable();
        } catch (Exception s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + s.getMessage(), "Exception Caugth!", JOptionPane.WARNING_MESSAGE);
        }
        setTitle("User Manager " + " User: " + user);
    }

    /*method fo resed all field */
    public void cancel() {
        jButtonAdd.setEnabled(true);
        jButtonCancel.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonUpdate.setEnabled(false);

        this.jTextFielddatechoose.setText("");
        this.jTextFieldUserName.setText("");
        this.jPasswordField.setText("");

        this.jCheckBoxImportProduct.setSelected(false);
        this.jCheckBoxUtilityManagement.setSelected(false);
        this.jCheckBoxProductSale.setSelected(false);
        this.jCheckBoxReporting.setSelected(false);
        this.jCheckBoxOther.setSelected(false);

        this.jCheckBoxLogStatus.setSelected(false);
       
    }

    /*method for store data intor database*/
    public void add() throws SQLException, ClassNotFoundException {
         if(jCheckBoxLogStatus.isSelected()){
                 luck_status = true;
             }else{
                 luck_status = false;
             }
        boolean flag_tf = new JavaValidation().checkEmptyTextField(jTextFielddatechoose, jTextFieldUserName, jPasswordField);
        if (flag_tf == true) {
            q = "insert into tbl_user(date,user_type,password,msetting,mimport,"
                    + "mselling,mreporting,mother,lock_status) "
                    + "values (?,?,?,?,?,?,?,?,?)";
            pstm = conn.prepareStatement(q);
            datechoose = jTextFielddatechoose.getText().toString();
            name = jTextFieldUserName.getText().toString();
            password = jPasswordField.getText().toString();
            pstm.setString(1, datechoose);
            pstm.setString(2, name);
            pstm.setString(3, password);

            if (jCheckBoxUtilityManagement.isSelected()) {
                mUtilityManagement = true;
            } else {
                mUtilityManagement = false;
            }
            if (jCheckBoxImportProduct.isSelected()) {
                mImport = true;
            } else {
                mImport = false;
            }
            if (jCheckBoxProductSale.isSelected()) {
                mSelling = true;
            } else {
                mSelling = false;
            }
            if (jCheckBoxReporting.isSelected()) {
                mReporting = true;
            } else {
                mReporting = false;
            }
            if (jCheckBoxOther.isSelected()) {
                mOther = true;
            } else {
                mOther = false;
            }
           
            pstm.setBoolean(4, mUtilityManagement);
            pstm.setBoolean(5, mImport);
            pstm.setBoolean(6, mSelling);
            pstm.setBoolean(7, mReporting);
            pstm.setBoolean(8, mOther);
            pstm.setBoolean(9, luck_status);
            pstm.executeUpdate();
            pstm.close();

            JOptionPane.showMessageDialog(this, "User Created", "User Create Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } else {
            JOptionPane.showMessageDialog(this, "TextField is Empty", "TextField Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    /*method for update data onto database*/
    public void update() {
        datechoose = jTextFielddatechoose.getText().toString();
        name = jTextFieldUserName.getText().toString();
        password = jPasswordField.getText().toString();

       if (jCheckBoxImportProduct.isSelected()) {
                mImport = true;
            } else {
                mImport = false;
            }
            if (jCheckBoxUtilityManagement.isSelected()) {
                mUtilityManagement = true;
            } else {
                mUtilityManagement = false;
            }
            if (jCheckBoxProductSale.isSelected()) {
                mSelling = true;
            } else {
                mSelling = false;
            }
            if (jCheckBoxReporting.isSelected()) {
                mReporting = true;
            } else {
                mReporting = false;
            }
            if (jCheckBoxOther.isSelected()) {
                mOther = true;
            } else {
                mOther = false;
            }

         if(jCheckBoxLogStatus.isSelected()){
                 luck_status = true;
             }else{
                 luck_status = false;
             }
        try {
            q = "update tbl_user set mimport=? ,msetting=? , mselling=? "
                    + ",mreporting=? , mother=? ,date=?,password=?,user_type=? ,lock_status=? where id=?";
             pstm = conn.prepareStatement(q);
            pstm.setBoolean(1, mImport);
            pstm.setBoolean(2, mUtilityManagement);
            pstm.setBoolean(3, mSelling);
            pstm.setBoolean(4, mReporting);
            pstm.setBoolean(5, mOther);
            pstm.setString(6, date);
            pstm.setString(7, password);
            pstm.setString(8, name);
            pstm.setBoolean(9,luck_status);
            pstm.setInt(10,ref_id);
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Data Updated", "User Update Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    /*method for delete data from database*/
    public void delete() {
        try {
            q = "delete from tbl_user where user_type=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, jTextFieldUserName.getText());
            pstm.executeUpdate();
            pstm.close();
            JOptionPane.showMessageDialog(this, "Data Deleted", "User Delete Information", JOptionPane.INFORMATION_MESSAGE);
            this.updateJTable();
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    /*method for update JTableUserManagement*/
    public final void updateJTable() {
        System.out.println("I am inside updateTable()");
        try {
            q = "select id as  'ID ' , date as  'Date  ' , user_type as  'Users  ' , lock_status as 'Lock Status' from tbl_user";
            JTableHandle jth = new JTableHandle(conn);
            jth.UpdateTable(jTableUserManagement, q);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
        }
    }

    /*method for trigger mouse click event on JTableUserManagement*/
    public void mouseClickJTable() {
        jButtonAdd.setEnabled(false);
        jButtonCancel.setEnabled(true);
        jButtonDelete.setEnabled(true);
        jButtonUpdate.setEnabled(true);
        // if(evt.getClickCount()==2){
        try {
            int myrow = jTableUserManagement.getSelectedRow();
            String tableclick = (jTableUserManagement).getModel().getValueAt(myrow, 0).toString();
            q = "select *from tbl_user where id= '" + tableclick + "' ";
            pstm = conn.prepareStatement(q);
            rs = pstm.executeQuery();
            if (rs.next()) {
                date = rs.getString("date");
                name = rs.getString("user_type");
                password = rs.getString("password");
                ref_id = rs.getInt("id");
                luck_status = rs.getBoolean("lock_status");
                jTextFielddatechoose.setText(date);

                mImport = rs.getBoolean("mimport");
                mUtilityManagement = rs.getBoolean("msetting");
                mSelling = rs.getBoolean("mselling");
                mReporting = rs.getBoolean("mreporting");
                mOther = rs.getBoolean("mother");

                jTextFieldUserName.setText(name);
                jPasswordField.setText(password);

                if(luck_status==true){
                    jCheckBoxLogStatus.setSelected(true);
                }else{
                    jCheckBoxLogStatus.setSelected(false);
                }
                
                if (mImport == true) {
                    jCheckBoxImportProduct.setSelected(true);
                } else {
                    jCheckBoxImportProduct.setSelected(false);
                }

                if (mUtilityManagement == true) {
                    jCheckBoxUtilityManagement.setSelected(true);
                } else {
                    jCheckBoxUtilityManagement.setSelected(false);
                }

                if (mSelling == true) {
                    jCheckBoxProductSale.setSelected(true);
                } else {
                    jCheckBoxProductSale.setSelected(false);
                }

                if (mReporting == true) {
                    jCheckBoxReporting.setSelected(true);
                } else {
                    jCheckBoxReporting.setSelected(false);
                }

                if (mOther == true) {
                    jCheckBoxOther.setSelected(true);
                } else {
                    jCheckBoxOther.setSelected(false);
                }

            }
        } catch (SQLException s) {
            s.printStackTrace();
            JOptionPane.showMessageDialog(this, s.getMessage());
        }
        // }else{}
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jPanelPrevilege = new javax.swing.JPanel();
        jCheckBoxImportProduct = new javax.swing.JCheckBox();
        jCheckBoxReporting = new javax.swing.JCheckBox();
        jCheckBoxUtilityManagement = new javax.swing.JCheckBox();
        jCheckBoxOther = new javax.swing.JCheckBox();
        jCheckBoxProductSale = new javax.swing.JCheckBox();
        jTextFielddatechoose = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jCheckBoxLogStatus = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUserManagement = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setClosable(true);
        setTitle("User Manager");
        setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(227, 232, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)), "User Manager", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 255))); // NOI18N
        jPanel1.setOpaque(false);

        jLabel2.setForeground(new java.awt.Color(0, 102, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Name :");

        jTextFieldUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldUserNameKeyTyped(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 102, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Password :");

        jPanelPrevilege.setBorder(javax.swing.BorderFactory.createTitledBorder("Modules Previlege"));
        jPanelPrevilege.setOpaque(false);

        jCheckBoxImportProduct.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCheckBoxImportProduct.setForeground(new java.awt.Color(0, 102, 255));
        jCheckBoxImportProduct.setText("Import Product");
        jCheckBoxImportProduct.setOpaque(false);

        jCheckBoxReporting.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCheckBoxReporting.setForeground(new java.awt.Color(0, 102, 255));
        jCheckBoxReporting.setText("Reporting");
        jCheckBoxReporting.setOpaque(false);

        jCheckBoxUtilityManagement.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCheckBoxUtilityManagement.setForeground(new java.awt.Color(0, 102, 255));
        jCheckBoxUtilityManagement.setText("Utility Management");
        jCheckBoxUtilityManagement.setOpaque(false);

        jCheckBoxOther.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCheckBoxOther.setForeground(new java.awt.Color(0, 102, 255));
        jCheckBoxOther.setText("Other");
        jCheckBoxOther.setOpaque(false);

        jCheckBoxProductSale.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCheckBoxProductSale.setForeground(new java.awt.Color(0, 102, 255));
        jCheckBoxProductSale.setText("Product Sale");
        jCheckBoxProductSale.setOpaque(false);

        javax.swing.GroupLayout jPanelPrevilegeLayout = new javax.swing.GroupLayout(jPanelPrevilege);
        jPanelPrevilege.setLayout(jPanelPrevilegeLayout);
        jPanelPrevilegeLayout.setHorizontalGroup(
            jPanelPrevilegeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrevilegeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrevilegeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBoxImportProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxReporting, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelPrevilegeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBoxOther, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxUtilityManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxProductSale)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPrevilegeLayout.setVerticalGroup(
            jPanelPrevilegeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrevilegeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrevilegeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxImportProduct)
                    .addComponent(jCheckBoxUtilityManagement)
                    .addComponent(jCheckBoxProductSale))
                .addGap(18, 18, 18)
                .addGroup(jPanelPrevilegeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxReporting)
                    .addComponent(jCheckBoxOther))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextFielddatechoose.setToolTipText("Mouse Click Here for Date. Date foramat: yyyy-mm-dd");
        jTextFielddatechoose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFielddatechooseMouseClicked(evt);
            }
        });
        jTextFielddatechoose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFielddatechooseKeyTyped(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));
        jPanel3.setOpaque(false);

        jButtonAdd.setForeground(new java.awt.Color(0, 102, 255));
        jButtonAdd.setText("ADD");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonUpdate.setForeground(new java.awt.Color(0, 102, 255));
        jButtonUpdate.setText("UPDATE");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setForeground(new java.awt.Color(0, 102, 255));
        jButtonDelete.setText("DELETE");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonCancel.setForeground(new java.awt.Color(0, 102, 255));
        jButtonCancel.setText("CANCEL");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonDelete)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        jLabel4.setForeground(new java.awt.Color(0, 102, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Date :");

        jCheckBoxLogStatus.setBackground(new java.awt.Color(227, 232, 232));
        jCheckBoxLogStatus.setForeground(new java.awt.Color(255, 51, 51));
        jCheckBoxLogStatus.setText("Log Status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPasswordField)
                            .addComponent(jTextFieldUserName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFielddatechoose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addComponent(jCheckBoxLogStatus))
                    .addComponent(jPanelPrevilege, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFielddatechoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBoxLogStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanelPrevilege, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableUserManagement.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableUserManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableUserManagementMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableUserManagement);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        this.delete();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        this.cancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            this.add();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        } 
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        this.update();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jTableUserManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUserManagementMouseClicked
        this.mouseClickJTable();
    }//GEN-LAST:event_jTableUserManagementMouseClicked

    private void jTextFielddatechooseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFielddatechooseMouseClicked
        new NepaliCalendarClassGroup.NepaliDate().setTextFieldWithNepaliDateValue(jTextFielddatechoose);
        jButtonCancel.setEnabled(true);
    }//GEN-LAST:event_jTextFielddatechooseMouseClicked

    private void jTextFielddatechooseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFielddatechooseKeyTyped
        javaValidation.getDateValue(evt);
    }//GEN-LAST:event_jTextFielddatechooseKeyTyped

    private void jTextFieldUserNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldUserNameKeyTyped
        javaValidation.getCharacterValue(evt);
    }//GEN-LAST:event_jTextFieldUserNameKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JCheckBox jCheckBoxImportProduct;
    private javax.swing.JCheckBox jCheckBoxLogStatus;
    private javax.swing.JCheckBox jCheckBoxOther;
    private javax.swing.JCheckBox jCheckBoxProductSale;
    private javax.swing.JCheckBox jCheckBoxReporting;
    private javax.swing.JCheckBox jCheckBoxUtilityManagement;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelPrevilege;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableUserManagement;
    private javax.swing.JTextField jTextFieldUserName;
    private javax.swing.JTextField jTextFielddatechoose;
    // End of variables declaration//GEN-END:variables
}
