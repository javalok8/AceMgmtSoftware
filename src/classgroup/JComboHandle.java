/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.awt.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author JA
 */
public class JComboHandle {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    public JComboHandle(java.sql.Connection connn) {
        try {
            if (conn == null) {
               // conn = new DBConnection().getDbConnection();
                this.conn = connn;
                System.out.println("Connections Objecct Status inside JComboHandle class:: "+conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to populate data on Combobox 
    public void fillComboByName(JComboBox combo, String sql) throws SQLException, ClassNotFoundException {
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            combo.addItem(rs.getString("user_type"));
        }

    }

    //method to populate data on Combobox
    public void fillComboProductName(JComboBox combo, String sql) throws SQLException, ClassNotFoundException {
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            combo.addItem(rs.getString("product_name"));
        }

    }

     //method to populate data on Combobox
    public void fillComboCreditorName(JComboBox combo, String sql) throws SQLException, ClassNotFoundException {
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();
        while (rs.next()) {
            combo.addItem(rs.getString("name"));
        }

    }

    //method to populate data on Combobox
    public void fillComboByProductQtyType(JComboBox combo, String sql,String param) throws SQLException, ClassNotFoundException {
        pstm = conn.prepareStatement(sql);
        pstm.setString(1,param);
        rs = pstm.executeQuery();
        while (rs.next()) {
            combo.addItem(rs.getString("qty_type"));
        }

    }

    //method to populate data on Combobox
    public void fillTextFieldProductNo(JTextField productNo, String sql,String param1 , String param2) throws SQLException, ClassNotFoundException {
        pstm = conn.prepareStatement(sql);
        pstm.setString(1,param1);
        pstm.setString(2,param2);
        rs = pstm.executeQuery();
        while (rs.next()) {
            productNo.setText(rs.getString("codeno"));
        }

    }

 
    }

