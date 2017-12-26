/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Home.java
 *
 * Created on Aug 18, 2013, 4:38:26 PM
 */
package lok;

import DbHelper.DBConnection;
import Reports.Report_Annual_Finance;
import Reports.Report_Damage;
import Reports.Report_Debit_Credit;
import Reports.Report_Import;
import Reports.Report_Laboring;
import Reports.Report_Sale;
import classgroup.DateAndTime;
import classgroup.WindowHandler;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 *
 * @author JA
 */
public class Home extends javax.swing.JFrame {
    /*variables declarations*/
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    private DBConnection dbcon;
    private static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
    JPanel menuPanel = null;
    JPanel topPanel = null;
    private boolean daywise_boolean = false;
    private boolean subjectwise_boolean = false;
    private String q = "";
    //some variables for session manager
    private int daysRunning;
    private Date startDate;
    private Date endDate;
    private int totalDays;
    private int runningDays;
    private int missedDays;
    private int totalSaturday;
    private int leaveManagerDays;
    private String leaveDays;
    private java.util.Date dd = new java.util.Date();
    private boolean lock_status = false;
    private String name;
    private String pass;

    //method to set userPriviledge
    public final void setUserPrivilegeComponentSetting(String user, String password)
            throws SQLException, ClassNotFoundException {
        q = "select *from tbl_user where user_type=? and password=?";
        
        pstm = conn.prepareStatement(q);
        pstm.setString(1, user);
        pstm.setString(2, password);
        rs = pstm.executeQuery();
        if (rs.next()) {
            boolean mImport = rs.getBoolean("mimport");
            System.out.println("Import : "+mImport);
            if (mImport == true) {
                this.jToggleButtonProductImport.setEnabled(true);
            } else {
                this.jToggleButtonProductImport.setEnabled(false);
            }
            boolean mUtilityManagement = rs.getBoolean("msetting");
            System.out.println("Utility : "+mUtilityManagement);
            if (mUtilityManagement == true) {
                this.jToggleButtonUtilityMgmt.setEnabled(true);
            } else {
                this.jToggleButtonUtilityMgmt.setEnabled(false);
            }
            boolean mSelling = rs.getBoolean("mselling");
            System.out.println("Selling : "+mSelling);
            if (mSelling == true) {
                this.jToggleButtonSelling.setEnabled(true);
            } else {
                this.jToggleButtonSelling.setEnabled(false);
            }
            boolean mReporting = rs.getBoolean("mreporting");
            System.out.println("Reporting : "+mReporting);
            if (mReporting == true) {
                this.jToggleButtonReporting.setEnabled(true);
            } else {
                this.jToggleButtonReporting.setEnabled(false);
            }
            boolean mOther = rs.getBoolean("mother");
            System.out.println("Other : "+mOther);
            if (mOther == true) {
                this.jToggleButtonOther.setEnabled(true);
            } else {
                this.jToggleButtonOther.setEnabled(false);
            }
        }
    }

    /*default constructor*/
    public Home() {
        initComponents();
    }

    /*parameterized contstructor*/
    public Home(String user, String password, boolean lock_status, java.sql.Connection connn) {
        initComponents();

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        desktop.setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
        jButtonMinimize.setLocation(toolkit.getScreenSize().width - 40, 0);
        jPanelBack.add(jButtonMinimize);
        jButtonMinimize.setVisible(true);
        jPanelBack.updateUI();
        jPanelBack.revalidate();
        this.name = user;
        this.pass = password;
        this.lock_status = lock_status;
        this.conn = connn;

        DateAndTime dat = new DateAndTime();
        // this.StatusDisplayer.getDefault().setStatusText("I Love Java");
        this.getLayeredPane().setToolTipText("Software Engineer:Lokendra Phagami JAVALOK");
        new DateAndTime().currentTimeForJLabel(jLabelDynamicTime);
        try {
            this.setUserPrivilegeComponentSetting(user, password);
        } catch (Exception s) {
            JOptionPane.showMessageDialog(this, s.getMessage());
        }

        // setTitle("User Logged In: " + user.toUpperCase());
        this.jLabelUser.setText(user);

    }

    public void updateMnuPanel() {
        jPanelMenu.removeAll();
        jPanelMenu.repaint();
        jPanelMenu.add(jToggleButtonProductImport);
        jPanelMenu.add(jLabel7);
        jPanelMenu.add(jToggleButtonProductImport);
        jPanelMenu.add(jLabel8);
        jPanelMenu.add(jToggleButtonUtilityMgmt);
        jPanelMenu.add(jLabel8);
        jPanelMenu.add(jToggleButtonSelling);
        jPanelMenu.add(jLabel9);
        jPanelMenu.add(jLabel10);
        jPanelMenu.add(jToggleButtonOther);
        jPanelMenu.add(jLabel12);
        jPanelMenu.add(jToggleButtonReporting);
        jPanelMenu.updateUI();
        jPanelMenu.revalidate();

        jPanelTopPanel.removeAll();
        jPanelTopPanel.repaint();
        jPanelTopPanel.add(jToggleButtonFile);
        jPanelTopPanel.add(jLabelFile);
        jPanelTopPanel.add(jToggleButtonUserMgmt);
        jPanelTopPanel.add(jLabelUsermgmt);
        jPanelTopPanel.add(jToggleButtonHelp);
        jPanelTopPanel.add(jLabelHelp);
        jPanelTopPanel.updateUI();
        jPanelTopPanel.revalidate();

    }

    //selling action
    public void sellingAction() {
        updateMnuPanel();
        menuPanel = new JPanel();
        if (jToggleButtonSelling.isSelected()) {
            menuPanel.setLayout(new GridLayout(0, 1));
            menuPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 1));
            menuPanel.setSize(200, 100);
            menuPanel.setLocation(40, 170);
            jPanelMenu.add(menuPanel, 1, 0);
            jPanelMenu.updateUI();
            jPanelMenu.revalidate();
            menuPanel.setVisible(true);

            JButton jbtButtonNormalSelling = new JButton();
            JButton jButtonCreditorSelling = new JButton();
            JButton jButtonTesting = new JButton();

            jbtButtonNormalSelling.setSize(250, 50);
            jbtButtonNormalSelling.setLocation(15, 20);
            jbtButtonNormalSelling.setForeground(Color.black);
            jbtButtonNormalSelling.setText("Product Selling");

            jbtButtonNormalSelling.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                     Product_Sale Product_Sale = new Product_Sale(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(Product_Sale);
                    Product_Sale.setLocation(10, 10);
                    Product_Sale.setVisible(true);
                    Product_Sale.setToolTipText("Product Selling");


                }
            });

            jButtonCreditorSelling.setSize(250, 50);
            jButtonCreditorSelling.setLocation(15, 125);
            jButtonCreditorSelling.setForeground(Color.black);
            jButtonCreditorSelling.setText("Laboring Record");

            jButtonCreditorSelling.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Product_Labor tfr = new Product_Labor(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Laboring Record");

                }
            });

            jButtonTesting.setSize(250, 50);
            jButtonTesting.setLocation(15, 125);
            jButtonTesting.setForeground(Color.black);
            jButtonTesting.setText("Credit Record");

            jButtonTesting.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Product_Debit_Credit tfr = new Product_Debit_Credit(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Credit Record");

                }
            });

            menuPanel.add(jbtButtonNormalSelling);
            menuPanel.add(jButtonCreditorSelling);
            menuPanel.add(jButtonTesting);
            menuPanel.updateUI();
            menuPanel.revalidate();

        } else {
            updateMnuPanel();
        }
    }
    //report action
    public void reportingAction() {
        updateMnuPanel();
        menuPanel = new JPanel();
        if (jToggleButtonReporting.isSelected()) {
            menuPanel.setLayout(new GridLayout(0, 1));
            menuPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 1));
            menuPanel.setSize(200, 200);
            menuPanel.setLocation(115, 170);
            jPanelMenu.add(menuPanel, 1, 0);
            jPanelMenu.updateUI();
            jPanelMenu.revalidate();
            menuPanel.setVisible(true);

            JButton jbtButtonImportReport = new JButton();
            JButton jButtonSellingReport = new JButton();
            JButton jButtonLaboringReport = new JButton();
            JButton jButtonDamageReport = new JButton();
            JButton jButtonCreditReport = new JButton();
            JButton jButtonAnnualFinanceReport = new JButton();

            jbtButtonImportReport.setSize(250, 30);
            jbtButtonImportReport.setLocation(15, 20);
            jbtButtonImportReport.setForeground(Color.black);
            jbtButtonImportReport.setText("Import Report");

            jbtButtonImportReport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Report_Import rsid = new Report_Import(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(rsid);
                    rsid.setLocation(10, 10);
                    rsid.setVisible(true);
                    rsid.setToolTipText("Product Reports");


                }
            });

            jButtonSellingReport.setSize(250, 30);
            jButtonSellingReport.setLocation(15, 55);
            jButtonSellingReport.setForeground(Color.black);
            jButtonSellingReport.setText("Selling Reports");

            jButtonSellingReport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Report_Sale tfr = new Report_Sale(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Selling Reports");

                }
            });

         
            
            jButtonDamageReport.setSize(250, 30);
            jButtonDamageReport.setLocation(15, 125);
            jButtonDamageReport.setForeground(Color.black);
            jButtonDamageReport.setText("Damage Reports");

            jButtonDamageReport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Report_Damage tfr = new Report_Damage(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Creditor Reports");

                }
            });
            
            jButtonCreditReport.setSize(250, 30);
            jButtonCreditReport.setLocation(15, 160);
            jButtonCreditReport.setForeground(Color.black);
            jButtonCreditReport.setText("Credit Reports");

            jButtonCreditReport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Report_Debit_Credit tfr = new Report_Debit_Credit(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Creditor Reports");

                }
            });
            
            jButtonLaboringReport.setSize(250, 30);
            jButtonLaboringReport.setLocation(15, 195);
            jButtonLaboringReport.setForeground(Color.black);
            jButtonLaboringReport.setText("Laboring Reports");

            jButtonLaboringReport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Report_Laboring tfr = new Report_Laboring(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Laboring Reports");

                }
            });
            
            jButtonAnnualFinanceReport.setSize(250, 30);
            jButtonAnnualFinanceReport.setLocation(15, 230);
            jButtonAnnualFinanceReport.setForeground(Color.black);
            jButtonAnnualFinanceReport.setText("Annual Finance Reports");

            jButtonAnnualFinanceReport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Report_Annual_Finance tfr = new Report_Annual_Finance(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(tfr);
                    tfr.setLocation(10, 10);
                    tfr.setVisible(true);
                    tfr.setToolTipText("Annual Finance Reports");

                }
            });
         
            menuPanel.add(jbtButtonImportReport);
            menuPanel.add(jButtonSellingReport);
            menuPanel.add(jButtonLaboringReport);
            menuPanel.add(jButtonDamageReport);
            menuPanel.add(jButtonCreditReport);
            menuPanel.add(jButtonAnnualFinanceReport);
            menuPanel.updateUI();
            menuPanel.revalidate();

        } else {
            updateMnuPanel();
        }
    }

    public void utilityManagementAction() {
        updateMnuPanel();
        menuPanel = new JPanel();
        if (jToggleButtonUtilityMgmt.isSelected()) {
            menuPanel.setLayout(new GridLayout(0, 1));
            menuPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 1));
            menuPanel.setSize(265, 150);
            menuPanel.setLocation(115, 70);
            jPanelMenu.add(menuPanel, 1, 0);
            jPanelMenu.updateUI();
            jPanelMenu.revalidate();
            menuPanel.setVisible(true);

            JButton jButtonProductNameEntry = new JButton();
            JButton jButtonPriceManager = new JButton();
            JButton jButtonProductQtyManager = new JButton();

            jButtonProductNameEntry.setSize(250, 30);
            jButtonProductNameEntry.setLocation(0, 0);
            jButtonProductNameEntry.setForeground(Color.BLACK);
            jButtonProductNameEntry.setText("Product Name Record");
            jButtonProductNameEntry.setHorizontalAlignment(SwingConstants.LEFT);

            jButtonProductNameEntry.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Product_names_Entry product_names_Entry = new Product_names_Entry(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(product_names_Entry);
                    product_names_Entry.setLocation(10, 10);
                    product_names_Entry.setVisible(true);
                    product_names_Entry.setToolTipText("Product Name Record");

                }
            });

            jButtonPriceManager.setSize(250, 30);
            jButtonPriceManager.setLocation(15, 55);
            jButtonPriceManager.setForeground(Color.BLACK);
            jButtonPriceManager.setText("Price Manager");
            jButtonPriceManager.setHorizontalAlignment(SwingConstants.LEFT);

            jButtonPriceManager.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Price_Manager price_Manager = new Price_Manager(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(price_Manager);
                    price_Manager.setLocation(10, 10);
                    price_Manager.setVisible(true);
                    price_Manager.setToolTipText("Price Manager");

                }
            });

            jButtonProductQtyManager.setSize(250, 30);
            jButtonProductQtyManager.setLocation(15, 90);
            jButtonProductQtyManager.setForeground(Color.black);
            jButtonProductQtyManager.setText("Product Quantity Manager");
            jButtonProductQtyManager.setHorizontalAlignment(SwingConstants.LEFT);

            jButtonProductQtyManager.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    Product_qtys_Manager product_qtys_Manager = new Product_qtys_Manager(conn);
                    desktop.removeAll();
                    desktop.repaint();
                    desktop.add(product_qtys_Manager);
                    product_qtys_Manager.setLocation(10, 10);
                    product_qtys_Manager.setVisible(true);
                    product_qtys_Manager.setToolTipText("Product Qty Manager");

                }
            });


            menuPanel.add(jButtonProductNameEntry);
            menuPanel.add(jButtonProductQtyManager);
            menuPanel.add(jButtonPriceManager);
            menuPanel.updateUI();
            menuPanel.revalidate();

        } else {
            updateMnuPanel();
        }
    }

    //File
    public void FileAction() {
        updateMnuPanel();
        topPanel = new JPanel();
        if (jToggleButtonFile.isSelected()) {
            topPanel.setLayout(new GridLayout(0, 1));
            topPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 1));
            topPanel.setSize(100, 60);
            topPanel.setLocation(2, 35);
            jPanelTopPanel.add(topPanel, 1, 0);
            jPanelTopPanel.updateUI();
            jPanelTopPanel.revalidate();
            topPanel.setVisible(true);

            JButton jbuttonLogOut = new JButton();
            JButton jbuttonExit = new JButton();

            jbuttonLogOut.setSize(140, 30);
            jbuttonLogOut.setLocation(15, 20);
            jbuttonLogOut.setForeground(Color.black);
            jbuttonLogOut.setText("LogOut");
            jbuttonLogOut.setHorizontalAlignment(SwingConstants.LEFT);

            jbuttonLogOut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();

                    int confirm = JOptionPane.showConfirmDialog(null, "Lok says do you want to Log?",
                            "Log Software", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        WindowHandler wh = new WindowHandler();
                        wh.closeWindow();
                        dispose();
                        try {
                            lock_status = false;
                            q = "update tbl_user set lock_status=? where user_type=? and password=?";
                            pstm = conn.prepareStatement(q);
                            pstm.setBoolean(1, lock_status);
                            pstm.setString(2, name);
                            pstm.setString(3, pass);
                            pstm.executeUpdate();
                            // pstm.close();
                        } catch (Exception es) {
                            es.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Exception Lok: " + es.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
                        }
                        Login login = new Login();
                        login.setVisible(true);
                        dispose();
                    } else if (confirm == JOptionPane.NO_OPTION) {
                    } else {
                    }
                }
            });

            jbuttonExit.setSize(140, 30);
            jbuttonExit.setLocation(15, 55);
            jbuttonExit.setForeground(Color.black);
            jbuttonExit.setText("Exit");
            jbuttonExit.setHorizontalAlignment(SwingConstants.LEFT);

            jbuttonExit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                    int confirm = JOptionPane.showConfirmDialog(null, "Lok says do you want to Exit?",
                            "Exit Software", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            lock_status = false;
                            q = "update tbl_user set lock_status=? where user_type=? and password=?";
                            pstm = conn.prepareStatement(q);
                            pstm.setBoolean(1, lock_status);
                            pstm.setString(2, name);
                            pstm.setString(3, pass);
                            pstm.executeUpdate();
                            pstm.close();
                        } catch (Exception es) {
                            es.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Exception Lok: " + es.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
                        }
                        WindowHandler w = new WindowHandler();
                        w.closeWindow();
                        // dispose();
                        System.exit(0);
                    } else if (confirm == JOptionPane.NO_OPTION) {
                    } else {
                    }
                }
            });
            topPanel.add(jbuttonLogOut);
            topPanel.add(jbuttonExit);
            if (menuPanel != null) {
                menuPanel.updateUI();
                menuPanel.revalidate();
            }
        } else {
            updateMnuPanel();
        }
    }

    //Help
    public void HelpAction() {
        updateMnuPanel();
        topPanel = new JPanel();
        if (jToggleButtonHelp.isSelected()) {
            topPanel.setLayout(new GridLayout(0, 1));
            topPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 1));
            topPanel.setSize(150, 60);
            topPanel.setLocation(160, 35);
            jPanelTopPanel.add(topPanel, 1, 0);
            jPanelTopPanel.updateUI();
            jPanelTopPanel.revalidate();
            topPanel.setVisible(true);

            JButton jbuttonAboutSoftware = new JButton();
            JButton jbuttonAboutDeveloper = new JButton();

            jbuttonAboutSoftware.setSize(145, 30);
            jbuttonAboutSoftware.setLocation(15, 20);
            jbuttonAboutSoftware.setForeground(Color.black);
            jbuttonAboutSoftware.setText("About Software");
            jbuttonAboutSoftware.setHorizontalAlignment(SwingConstants.LEFT);

            jbuttonAboutSoftware.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();
                }
            });


            jbuttonAboutDeveloper.setSize(145, 30);
            jbuttonAboutDeveloper.setLocation(15, 55);
            jbuttonAboutDeveloper.setForeground(Color.black);
            jbuttonAboutDeveloper.setText("About Developer");
            jbuttonAboutDeveloper.setHorizontalAlignment(SwingConstants.LEFT);
            jbuttonAboutDeveloper.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMnuPanel();

                }
            });


            topPanel.add(jbuttonAboutSoftware);
            topPanel.add(jbuttonAboutDeveloper);
            menuPanel.updateUI();
            menuPanel.revalidate();

        } else {
            updateMnuPanel();
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

        jPanelBack = new javax.swing.JPanel();
        jPanelTopPanel = new javax.swing.JPanel();
        jToggleButtonHelp = new javax.swing.JToggleButton();
        jToggleButtonUserMgmt = new javax.swing.JToggleButton();
        jToggleButtonFile = new javax.swing.JToggleButton();
        jLabelFile = new javax.swing.JLabel();
        jLabelUsermgmt = new javax.swing.JLabel();
        jLabelHelp = new javax.swing.JLabel();
        desktop = new javax.swing.JDesktopPane();
        jLabelUser = new javax.swing.JLabel();
        jButtonMinimize = new javax.swing.JButton();
        jLabelUser1 = new javax.swing.JLabel();
        jLabelDynamicTime = new javax.swing.JLabel();
        jPanelMenu = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jToggleButtonUtilityMgmt = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        jToggleButtonProductImport = new javax.swing.JToggleButton();
        jToggleButtonOther = new javax.swing.JToggleButton();
        jLabel9 = new javax.swing.JLabel();
        jToggleButtonSelling = new javax.swing.JToggleButton();
        jLabel10 = new javax.swing.JLabel();
        jToggleButtonReporting = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("lg.png")));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
            public void windowDeiconified(java.awt.event.WindowEvent evt) {
                formWindowDeiconified(evt);
            }
        });

        jPanelBack.setToolTipText("Software Engineer : Lokendra Phagami");
        jPanelBack.setPreferredSize(new java.awt.Dimension(1360, 1020));
        jPanelBack.setLayout(null);

        jPanelTopPanel.setOpaque(false);

        jToggleButtonHelp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jToggleButtonHelp.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButtonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/Help.png"))); // NOI18N
        jToggleButtonHelp.setBorderPainted(false);
        jToggleButtonHelp.setContentAreaFilled(false);
        jToggleButtonHelp.setFocusPainted(false);
        jToggleButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonHelpMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonHelpMouseEntered(evt);
            }
        });
        jToggleButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonHelpActionPerformed(evt);
            }
        });

        jToggleButtonUserMgmt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jToggleButtonUserMgmt.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButtonUserMgmt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/UserMgmt.png"))); // NOI18N
        jToggleButtonUserMgmt.setBorderPainted(false);
        jToggleButtonUserMgmt.setContentAreaFilled(false);
        jToggleButtonUserMgmt.setFocusPainted(false);
        jToggleButtonUserMgmt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonUserMgmtMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonUserMgmtMouseEntered(evt);
            }
        });
        jToggleButtonUserMgmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonUserMgmtActionPerformed(evt);
            }
        });

        jToggleButtonFile.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jToggleButtonFile.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButtonFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/File.png"))); // NOI18N
        jToggleButtonFile.setBorderPainted(false);
        jToggleButtonFile.setContentAreaFilled(false);
        jToggleButtonFile.setFocusPainted(false);
        jToggleButtonFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonFileMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonFileMouseEntered(evt);
            }
        });
        jToggleButtonFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonFileActionPerformed(evt);
            }
        });

        jLabelFile.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabelFile.setForeground(new java.awt.Color(0, 0, 255));
        jLabelFile.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelFile.setText("File");

        jLabelUsermgmt.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabelUsermgmt.setForeground(new java.awt.Color(0, 0, 255));
        jLabelUsermgmt.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelUsermgmt.setText("User Management");

        jLabelHelp.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabelHelp.setForeground(new java.awt.Color(0, 0, 255));
        jLabelHelp.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelHelp.setText("Help");

        javax.swing.GroupLayout jPanelTopPanelLayout = new javax.swing.GroupLayout(jPanelTopPanel);
        jPanelTopPanel.setLayout(jPanelTopPanelLayout);
        jPanelTopPanelLayout.setHorizontalGroup(
            jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                .addGroup(jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonFile, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelUsermgmt))
                    .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jToggleButtonUserMgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelHelp)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanelTopPanelLayout.setVerticalGroup(
            jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                .addGroup(jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonFile, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonUserMgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelHelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelUsermgmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelFile, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jPanelBack.add(jPanelTopPanel);
        jPanelTopPanel.setBounds(662, 12, 345, 140);

        desktop.setBackground(new java.awt.Color(208, 223, 231));
        desktop.setForeground(new java.awt.Color(102, 102, 255));
        desktop.setOpaque(false);
        desktop.setPreferredSize(new java.awt.Dimension(650, 610));
        desktop.setVerifyInputWhenFocusTarget(false);
        jPanelBack.add(desktop);
        desktop.setBounds(370, 110, 650, 650);

        jLabelUser.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelUser.setForeground(new java.awt.Color(153, 153, 255));
        jLabelUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUser.setText("df");
        jLabelUser.setAutoscrolls(true);
        jPanelBack.add(jLabelUser);
        jLabelUser.setBounds(170, 10, 200, 30);

        jButtonMinimize.setBackground(new java.awt.Color(51, 102, 255));
        jButtonMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/minimize.png"))); // NOI18N
        jButtonMinimize.setBorderPainted(false);
        jButtonMinimize.setContentAreaFilled(false);
        jButtonMinimize.setFocusPainted(false);
        jButtonMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonMinimizeMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonMinimizeMouseEntered(evt);
            }
        });
        jButtonMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMinimizeActionPerformed(evt);
            }
        });
        jPanelBack.add(jButtonMinimize);
        jButtonMinimize.setBounds(962, 2, 30, 30);

        jLabelUser1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelUser1.setForeground(new java.awt.Color(153, 153, 255));
        jLabelUser1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelUser1.setText("Session User :");
        jPanelBack.add(jLabelUser1);
        jLabelUser1.setBounds(10, 10, 160, 30);

        jLabelDynamicTime.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabelDynamicTime.setForeground(new java.awt.Color(153, 153, 255));
        jLabelDynamicTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDynamicTime.setText("Time");
        jPanelBack.add(jLabelDynamicTime);
        jLabelDynamicTime.setBounds(60, 40, 210, 40);

        jPanelMenu.setToolTipText("Software Engineer : Lokendra Phagami");
        jPanelMenu.setMinimumSize(new java.awt.Dimension(330, 599));
        jPanelMenu.setOpaque(false);
        jPanelMenu.setPreferredSize(new java.awt.Dimension(300, 480));
        jPanelMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelMenuMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Utility Management");

        jToggleButtonUtilityMgmt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/Utility.png"))); // NOI18N
        jToggleButtonUtilityMgmt.setBorderPainted(false);
        jToggleButtonUtilityMgmt.setContentAreaFilled(false);
        jToggleButtonUtilityMgmt.setFocusable(false);
        jToggleButtonUtilityMgmt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonUtilityMgmtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonUtilityMgmtMouseExited(evt);
            }
        });
        jToggleButtonUtilityMgmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonUtilityMgmtActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Product Import");

        jToggleButtonProductImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/import.png"))); // NOI18N
        jToggleButtonProductImport.setBorderPainted(false);
        jToggleButtonProductImport.setContentAreaFilled(false);
        jToggleButtonProductImport.setFocusable(false);
        jToggleButtonProductImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonProductImportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonProductImportMouseExited(evt);
            }
        });
        jToggleButtonProductImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonProductImportActionPerformed(evt);
            }
        });

        jToggleButtonOther.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/damage.png"))); // NOI18N
        jToggleButtonOther.setBorderPainted(false);
        jToggleButtonOther.setContentAreaFilled(false);
        jToggleButtonOther.setFocusable(false);
        jToggleButtonOther.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonOtherMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonOtherMouseEntered(evt);
            }
        });
        jToggleButtonOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonOtherActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Other");

        jToggleButtonSelling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/Finance.png"))); // NOI18N
        jToggleButtonSelling.setBorderPainted(false);
        jToggleButtonSelling.setContentAreaFilled(false);
        jToggleButtonSelling.setFocusable(false);
        jToggleButtonSelling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonSellingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonSellingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonSellingMouseExited(evt);
            }
        });
        jToggleButtonSelling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonSellingActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Selling");

        jToggleButtonReporting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lok/Report.png"))); // NOI18N
        jToggleButtonReporting.setBorderPainted(false);
        jToggleButtonReporting.setContentAreaFilled(false);
        jToggleButtonReporting.setFocusable(false);
        jToggleButtonReporting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButtonReportingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButtonReportingMouseExited(evt);
            }
        });
        jToggleButtonReporting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonReportingActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Reporting");

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                    .addComponent(jToggleButtonSelling, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addComponent(jToggleButtonProductImport, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMenuLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMenuLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jLabel12))
                            .addGroup(jPanelMenuLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jToggleButtonUtilityMgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelMenuLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jToggleButtonReporting, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9))
                    .addComponent(jToggleButtonOther, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 250, Short.MAX_VALUE))
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addComponent(jToggleButtonUtilityMgmt, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButtonReporting, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addComponent(jToggleButtonProductImport, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButtonSelling, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButtonOther, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanelBack.add(jPanelMenu);
        jPanelMenu.setBounds(10, 110, 350, 510);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1024, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            lock_status = false;
            q = "update tbl_user set lock_status=? where user_type=? and password=?";
            pstm = conn.prepareStatement(q);
            pstm.setBoolean(1, lock_status);
            pstm.setString(2, name);
            pstm.setString(3, pass);
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception es) {
            es.printStackTrace();
            JOptionPane.showMessageDialog(this, "Exception Lok: " + es.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
    }//GEN-LAST:event_formWindowIconified

    private void formWindowDeiconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeiconified
    }//GEN-LAST:event_formWindowDeiconified

    private void jToggleButtonReportingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonReportingActionPerformed
        this.reportingAction();
}//GEN-LAST:event_jToggleButtonReportingActionPerformed

    private void jToggleButtonReportingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonReportingMouseEntered

        ImageIcon icon = new ImageIcon(getClass().getResource("Report_click.png"));
        jToggleButtonReporting.setIcon(icon);
        this.reportingAction();
    }//GEN-LAST:event_jToggleButtonReportingMouseEntered

    private void jToggleButtonReportingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonReportingMouseExited

        ImageIcon icon = new ImageIcon(getClass().getResource("Report.png"));
        jToggleButtonReporting.setIcon(icon);
}//GEN-LAST:event_jToggleButtonReportingMouseExited

    private void jToggleButtonSellingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSellingActionPerformed
     this.sellingAction();
     
       
}//GEN-LAST:event_jToggleButtonSellingActionPerformed

    private void jToggleButtonSellingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonSellingMouseEntered
        ImageIcon icon = new ImageIcon(getClass().getResource("Finance_click.png"));
        jToggleButtonSelling.setIcon(icon);
        this.sellingAction();
}//GEN-LAST:event_jToggleButtonSellingMouseEntered

    private void jToggleButtonSellingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonSellingMouseExited

        ImageIcon icon = new ImageIcon(getClass().getResource("Finance.png"));
        jToggleButtonSelling.setIcon(icon);
}//GEN-LAST:event_jToggleButtonSellingMouseExited

    private void jToggleButtonOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonOtherActionPerformed
        Product_damage_lost_Entry product_damage_lost_return_Entry = new Product_damage_lost_Entry(conn);
        desktop.removeAll();
        desktop.repaint();
        desktop.add(product_damage_lost_return_Entry);
        product_damage_lost_return_Entry.setLocation(10, 10);
        product_damage_lost_return_Entry.setVisible(true);
        product_damage_lost_return_Entry.setToolTipText("Damage/Lost/Return Product");
}//GEN-LAST:event_jToggleButtonOtherActionPerformed

    private void jToggleButtonOtherMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonOtherMouseEntered

        ImageIcon icon = new ImageIcon(getClass().getResource("damage_click.png"));
        jToggleButtonOther.setIcon(icon);

    }//GEN-LAST:event_jToggleButtonOtherMouseEntered

    private void jToggleButtonOtherMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonOtherMouseExited

        ImageIcon icon = new ImageIcon(getClass().getResource("damage.png"));
        jToggleButtonOther.setIcon(icon);
}//GEN-LAST:event_jToggleButtonOtherMouseExited

    private void jToggleButtonProductImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonProductImportActionPerformed
        Product_Import product_Import = new Product_Import(conn);
        desktop.removeAll();
        desktop.repaint();
        desktop.add(product_Import);
        product_Import.setLocation(10, 10);
        product_Import.setVisible(true);
        product_Import.setToolTipText("Product Import");
    }//GEN-LAST:event_jToggleButtonProductImportActionPerformed

    private void jToggleButtonProductImportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonProductImportMouseEntered
        System.out.println("mouse entered");
        ImageIcon icon = new ImageIcon(getClass().getResource("import_click.png"));
        jToggleButtonProductImport.setIcon(icon);

}//GEN-LAST:event_jToggleButtonProductImportMouseEntered

    private void jToggleButtonProductImportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonProductImportMouseExited
        System.out.println("mouse exited");
        ImageIcon icon = new ImageIcon(getClass().getResource("import.png"));
        jToggleButtonProductImport.setIcon(icon);
    }//GEN-LAST:event_jToggleButtonProductImportMouseExited

    private void jToggleButtonUtilityMgmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonUtilityMgmtActionPerformed
        this.utilityManagementAction();
}//GEN-LAST:event_jToggleButtonUtilityMgmtActionPerformed

    private void jToggleButtonUtilityMgmtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonUtilityMgmtMouseEntered
        ImageIcon icon = new ImageIcon(getClass().getResource("Utility_click.png"));
        jToggleButtonUtilityMgmt.setIcon(icon);
        this.utilityManagementAction();
    }//GEN-LAST:event_jToggleButtonUtilityMgmtMouseEntered

    private void jToggleButtonUtilityMgmtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonUtilityMgmtMouseExited
        ImageIcon icon = new ImageIcon(getClass().getResource("Utility.png"));
        jToggleButtonUtilityMgmt.setIcon(icon);
}//GEN-LAST:event_jToggleButtonUtilityMgmtMouseExited

    private void jToggleButtonFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonFileActionPerformed
        FileAction();
    }//GEN-LAST:event_jToggleButtonFileActionPerformed

    private void jToggleButtonFileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonFileMouseEntered
        this.FileAction();
        ImageIcon icon = new ImageIcon(getClass().getResource("File_click.png"));
        jToggleButtonFile.setIcon(icon);
    }//GEN-LAST:event_jToggleButtonFileMouseEntered

    private void jToggleButtonFileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonFileMouseExited
        ImageIcon icon = new ImageIcon(getClass().getResource("File.png"));
        jToggleButtonFile.setIcon(icon);
    }//GEN-LAST:event_jToggleButtonFileMouseExited

    private void jToggleButtonUserMgmtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonUserMgmtMouseEntered
        ImageIcon icon = new ImageIcon(getClass().getResource("UserMgmt_click.png"));
        jToggleButtonUserMgmt.setIcon(icon);

    }//GEN-LAST:event_jToggleButtonUserMgmtMouseEntered

    private void jToggleButtonUserMgmtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonUserMgmtMouseExited
        ImageIcon icon = new ImageIcon(getClass().getResource("UserMgmt.png"));
        jToggleButtonUserMgmt.setIcon(icon);
    }//GEN-LAST:event_jToggleButtonUserMgmtMouseExited

    private void jToggleButtonHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonHelpMouseEntered
        ImageIcon icon = new ImageIcon(getClass().getResource("Help_click.png"));
        jToggleButtonHelp.setIcon(icon);
        this.HelpAction();
    }//GEN-LAST:event_jToggleButtonHelpMouseEntered

    private void jToggleButtonHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonHelpMouseExited
        ImageIcon icon = new ImageIcon(getClass().getResource("Help.png"));
        jToggleButtonHelp.setIcon(icon);
    }//GEN-LAST:event_jToggleButtonHelpMouseExited

    private void jToggleButtonUserMgmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonUserMgmtActionPerformed

        UserManagement userManagement = new UserManagement(jLabelUser.getText().toString(), conn);
        desktop.removeAll();
        desktop.repaint();
        desktop.add(userManagement);
        userManagement.setLocation(10, 10);
        userManagement.setVisible(true);
        userManagement.setToolTipText("User Manager");
    }//GEN-LAST:event_jToggleButtonUserMgmtActionPerformed

    private void jToggleButtonHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonHelpActionPerformed
        HelpAction();
    }//GEN-LAST:event_jToggleButtonHelpActionPerformed

    private void jButtonMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMinimizeMouseExited
        ImageIcon icon = new ImageIcon(getClass().getResource("minimize.png"));
        jButtonMinimize.setIcon(icon);
    }//GEN-LAST:event_jButtonMinimizeMouseExited

    private void jButtonMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMinimizeMouseEntered
        ImageIcon icon = new ImageIcon(getClass().getResource("minimize_click.png"));
        jButtonMinimize.setIcon(icon);
    }//GEN-LAST:event_jButtonMinimizeMouseEntered

    private void jButtonMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinimizeActionPerformed
        this.setState(ICONIFIED);
    }//GEN-LAST:event_jButtonMinimizeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            lock_status = false;
            q = "update tbl_user set lock_status=? where user_type=? and password=?";
            pstm = conn.prepareStatement(q);
            pstm.setBoolean(1, lock_status);
            pstm.setString(2, name);
            pstm.setString(3, pass);
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception es) {
            es.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exception Lok: " + es.getMessage(), "Exception Caught!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jPanelMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMenuMouseEntered
}//GEN-LAST:event_jPanelMenuMouseEntered

    private void jPanelMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMenuMouseExited
        this.updateMnuPanel();
}//GEN-LAST:event_jPanelMenuMouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//             try {
//            //Open the device.
//            //Use the name of the device that connected with your computer.
//            ptr.open("POSPrinter");
//
//            //Get the exclusive control right for the opened device.
//            //Then the device is disable from other application.
//            ptr.claim(1000);
//
//            //Enable the device.
//            ptr.setDeviceEnabled(true);
//        } catch (JposException ex) {
 //       }
    }//GEN-LAST:event_formWindowOpened

    private void jToggleButtonSellingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButtonSellingMouseClicked
        // TODO add your handling code here:
      //    this.sellingAction();
    }//GEN-LAST:event_jToggleButtonSellingMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Home().setVisible(true);
            }
        });
        // System.setProperty(JposProperties.JPOS_POPULATOR_FILE_PROP_NAME,"C:/Users/mahabir pun/Desktop/SaleWays/src/jpos.xml");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane desktop;
    private javax.swing.JButton jButtonMinimize;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDynamicTime;
    private javax.swing.JLabel jLabelFile;
    private javax.swing.JLabel jLabelHelp;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JLabel jLabelUser1;
    private javax.swing.JLabel jLabelUsermgmt;
    private javax.swing.JPanel jPanelBack;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelTopPanel;
    private javax.swing.JToggleButton jToggleButtonFile;
    private javax.swing.JToggleButton jToggleButtonHelp;
    private javax.swing.JToggleButton jToggleButtonOther;
    private javax.swing.JToggleButton jToggleButtonProductImport;
    private javax.swing.JToggleButton jToggleButtonReporting;
    private javax.swing.JToggleButton jToggleButtonSelling;
    private javax.swing.JToggleButton jToggleButtonUserMgmt;
    private javax.swing.JToggleButton jToggleButtonUtilityMgmt;
    // End of variables declaration//GEN-END:variables
}
