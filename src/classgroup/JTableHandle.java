/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author JA
 */
public class JTableHandle {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public JTableHandle(java.sql.Connection connn){
        try{
        if(conn==null){
//            conn = new DBConnection().getDbConnection();
            this.conn = connn;
        }
        }catch(Exception s){
        JOptionPane.showMessageDialog(null, s.getMessage());
        s.printStackTrace();
        }
       
    }

    //method to update every jtable 
    public void UpdateTable(JTable table, String query ,String type) throws SQLException, ClassNotFoundException {
        pst = conn.prepareStatement(query);
        pst.setString(1, type);
        rs = pst.executeQuery();
        table.setModel(DbUtils.resultSetToTableModel(rs));
    }

     //method to update every jtable
    public void UpdateTable(JTable table, String query) throws SQLException, ClassNotFoundException {
        pst = conn.prepareStatement(query);
   
        rs = pst.executeQuery();
        table.setModel(DbUtils.resultSetToTableModel(rs));
    }

    //getValue from Jtable while mouse click on data of jtable
    public ResultSet getValueByClickMouse(MouseEvent evt, String query, JTable table) throws SQLException, ClassNotFoundException {

        int rows = table.getSelectedRow();

        String tbl_click = (table).getModel().getValueAt(rows, 0).toString();

        String q = query + tbl_click;

        pst = conn.prepareStatement(q);

        return rs = pst.executeQuery();

    }

    //getValue from Jtable while press UP and DOWN arrow
    public ResultSet getValueByPressingArrow(KeyEvent evt, JTable table, String query) throws SQLException, ClassNotFoundException {

        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            int rows = table.getSelectedRow();

            String tbl_click = (table).getModel().getValueAt(rows, 0).toString();

            String q = query + tbl_click;

            pst = conn.prepareStatement(q);

            rs = pst.executeQuery();
        }

        return rs;
    }
}
