/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Login.java
 *
 * Created on Sep 16, 2013, 4:27:15 PM
 */
package lok;

import DbHelper.DBConnection;
import NepaliCalendarClassGroup.NepaliDate;
import classgroup.DynamicGuiChange;
import classgroup.JComboHandle;
import classgroup.WindowHandler;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author JA
 */
public class Login extends javax.swing.JFrame {

    /*variables declarations*/
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    private DBConnection dbcon;
    private String user;
    private String pass;
    private Login schoolLogin;
    private String q = "";
    //variables for application session
    private int totalDays;
    private int runningDays;
    private Date endDate, startDate;
    private boolean endDate_systemDate_status;
    private boolean lock_status;
    private JComboHandle jComboHandle;

    //method for login button
    public void LoginFunction() throws SQLException, ClassNotFoundException {
        user = (String) jComboUserName.getSelectedItem();
        char ch[];
        ch = jUserPassword.getPassword();
        pass = new String(ch);
        System.out.println("GUI user: "+user);
        System.out.println("GUI password: "+pass);
        q = "select user_type , password ,lock_status from tbl_user";
        pstm = conn.prepareStatement(q);
        rs = pstm.executeQuery();
        boolean flag = false;
        while (rs.next()) {
            String bname = rs.getString("user_type");
            String bpass = rs.getString("password");
            System.out.println("DB user: "+bname);
            System.out.println("DB password: "+bpass);

            if ((user.equalsIgnoreCase(bname)) && (pass.equalsIgnoreCase(bpass))) {
                flag = true;
                lock_status = rs.getBoolean("lock_status");
                System.out.println("GUI and DB user and pass is equal so: lock status is: "+lock_status);
            }
        }
        if (flag == true) {
            if (lock_status == true) {
                JOptionPane.showMessageDialog(this, "Recent User is Already Logged In...", "User Busy!!!!", JOptionPane.WARNING_MESSAGE);
                this.ResetForm();
                return;
            } else {
                lock_status = true;
                q = "update tbl_user set lock_status=? where user_type=? and password=?";
                // conn = dbcon.getDbConnection();
                pstm = conn.prepareStatement(q);
                pstm.setBoolean(1, lock_status);
                pstm.setString(2, user);
                pstm.setString(3, pass);
                pstm.executeUpdate();
                
                Home home = new Home(user, pass, lock_status, conn);
                home.setVisible(true);
                dispose();
                new WindowHandler().closeWindow();
            }
        } else {
            JOptionPane.showMessageDialog(this, "User Name and Password is Wrong. Please try agian");
            jUserPassword.setText("");
            // this.ResetForm();
        }

    }

    //method for cancel button
    public void ResetForm() {
        jComboUserName.setSelectedIndex(0);
        jUserPassword.requestFocus();
        jUserPassword.setText("");
    }

    //helper method for combo handle
    public final void comboUserNameHandle() throws SQLException, ClassNotFoundException {
        q = "select user_type from tbl_user";
        jComboHandle.fillComboByName(jComboUserName, q);
    }

    /** Creates new form Login */
    public Login() {
        initComponents();
        this.setTitle("Department Store Login");
        this.setLocation(300, 200);

        try {
            dbcon = new DBConnection();
            conn = dbcon.getDbConnection();
            System.out.println("Connection object for login constructor: "+conn);
            jComboHandle = new JComboHandle(conn);
            this.getLayeredPane().setToolTipText("Software Developer : java lok ");
            this.comboUserNameHandle();
        } catch (Exception s) {
            JOptionPane.showMessageDialog(this, "Exception Lok Says: Server Not Found and " + s.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
            s.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonLogin = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboUserName = new javax.swing.JComboBox();
        jUserPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("lg.png")));
        setResizable(false);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/LoginLogo.png"))); // NOI18N

        jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/close.png"))); // NOI18N
        jButtonClose.setBorderPainted(false);
        jButtonClose.setContentAreaFilled(false);
        jButtonClose.setFocusPainted(false);
        jButtonClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonCloseMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonCloseMouseEntered(evt);
            }
        });
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)), "Sign In...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 255))); // NOI18N

        jButtonCancel.setForeground(new java.awt.Color(0, 102, 255));
        jButtonCancel.setText("Cancel");
        jButtonCancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonLogin.setForeground(new java.awt.Color(0, 102, 255));
        jButtonLogin.setText("Login");
        jButtonLogin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel2.setForeground(new java.awt.Color(0, 102, 255));
        jLabel2.setText("Password");

        jComboUserName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        jUserPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));
        jUserPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jUserPasswordKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("User Name");

        jLabelEmail.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabelEmail.setForeground(new java.awt.Color(0, 102, 255));
        jLabelEmail.setText("Visit Us : www.dreamsoft.com");
        jLabelEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEmailMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelEmailMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelEmailMouseEntered(evt);
            }
        });
        jLabelEmail.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelEmailMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addComponent(jLabelEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabelEmail)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonClose)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
//        try {
//            this.LoginFunction();
//
//        } catch (Exception s) {
//           s.printStackTrace();
//        }
    }//GEN-LAST:event_formWindowClosed

    private void jLabelEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEmailMouseClicked
        try {
            String dreamsof = "https://www.google.com/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(dreamsof));
        } catch (IOException io) {
        }
    }//GEN-LAST:event_jLabelEmailMouseClicked

    private void jLabelEmailMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEmailMouseMoved
        // TODO add your handling code here:
        jLabelEmail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabelEmail.setForeground(Color.yellow);
    }//GEN-LAST:event_jLabelEmailMouseMoved

    private void jLabelEmailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEmailMouseExited
        jLabelEmail.setForeground(Color.gray);
    }//GEN-LAST:event_jLabelEmailMouseExited

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.ResetForm();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        try {
            this.LoginFunction();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + e.getMessage(), "Exception Caught! ", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jUserPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jUserPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                this.LoginFunction();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Exception Lok: " + e.getMessage(), "Exception Caught! ", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jUserPasswordKeyPressed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        System.out.println("Click ");
        System.exit(0);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCloseMouseEntered
        ImageIcon i = new ImageIcon(this.getClass().getResource("close_mouse.png"));
        jButtonClose.setIcon(i);
        jButtonClose.setToolTipText("Close Application");
    }//GEN-LAST:event_jButtonCloseMouseEntered

    private void jButtonCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCloseMouseExited
        ImageIcon i = new ImageIcon(this.getClass().getResource("close.png"));
        jButtonClose.setIcon(i);
    }//GEN-LAST:event_jButtonCloseMouseExited

    private void jLabelEmailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEmailMouseEntered
        jLabelEmail.setForeground(Color.black);
    }//GEN-LAST:event_jLabelEmailMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //this method is to change GUI Theme
        new DynamicGuiChange().GuiChange("Nimbus");

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SplashScreen s = new SplashScreen();
                s.setVisible(true);
                s.setSplashScreen(s);
                Thread t = new Thread(s);
                t.start();

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JComboBox jComboUserName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jUserPassword;
    // End of variables declaration//GEN-END:variables
}
