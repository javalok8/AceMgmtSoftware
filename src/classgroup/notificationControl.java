/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Dreamsoft
 */
public class notificationControl {

    private PreparedStatement psmt = null;
    private ResultSet rs = null;
    private String[] productName;
    private int productCount;
    private String q;
    private int sn;
    private int row;
    private int ramt = 0;
    private int importamt = 0;
    private int sellamt = 0;
    private int damageamt = 0;
    JButton button = new JButton();
    DefaultListModel model = new DefaultListModel();

    public void productName(Connection conn, JList list) {
        list.removeAll();

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        try {
            q = "select product_name from tbl_productname_entry";
            psmt = conn.prepareStatement(q);
            rs = psmt.executeQuery();
            while (rs.next()) {
                productCount = rs.getRow();
            }
            rs.close();
            psmt.close();
            productName = new String[productCount];

            q = "select product_name from tbl_productname_entry";
            psmt = conn.prepareStatement(q);
            rs = psmt.executeQuery();
            if (rs.next()) {
                for (int i = 0; i < productCount; i++) {
                    productName[i] = rs.getString("product_name");
                    rs.next();
                }
            }
            rs.close();
            psmt.close();

            for (int i = 0; i < productCount; i++) {
                q = "select sum(total_qty_amount) from tbl_import_product_record where product_name=?";
                psmt = conn.prepareStatement(q);
                psmt.setString(1, productName[i]);
                rs = psmt.executeQuery();
                if (rs.next()) {
                    importamt = rs.getInt("sum(total_qty_amount)");
                    System.out.println("total Import " + productName[i] + " :" + importamt);
                }

                q = "select sum(total_qty_amount) from tbl_selling_product_record where product_name=?";
                psmt = conn.prepareStatement(q);
                psmt.setString(1, productName[i]);
                rs = psmt.executeQuery();
                if (rs.next()) {
                    sellamt = rs.getInt("sum(total_qty_amount)");
                    System.out.println("total Selling " + productName[i] + " :" + sellamt);
                }

                q = "select sum(total_qty_amount) from tbl_damage_lost_product where product_name=?";
                psmt = conn.prepareStatement(q);
                psmt.setString(1, productName[i]);
                rs = psmt.executeQuery();
                if (rs.next()) {
                    damageamt = rs.getInt("sum(total_qty_amount)");
                    System.out.println("total Import " + productName[i] + " :" + damageamt);
                }

                ramt = importamt - (sellamt + damageamt);
                System.out.println("Remaining Amount: " + ramt);

                if (ramt < 300) {
//                    model.addElement(productName[i] + ": " + "\t" + ramt + "\n");
                    model.addElement(productName[i]);
                }
            }
            list.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
}
