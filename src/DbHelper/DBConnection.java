/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JavaLok
 */
public class DBConnection {

    private Connection conn = null;


    public Connection getDbConnection() throws SQLException , ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lok_pun", "root", "lok");
            //  conn = DriverManager.getConnection("jdbc:mysql://192.168.2.112:3306/department_store", "root", "root");
            System.out.println("Connection object inside dbconnection class: "+conn);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return conn;
    }

    

    public void closeConnection(Connection con) throws SQLException {
        con.close();
    }
    //method to close PreparedStatment object

    public void closePreparedStatement(PreparedStatement pstm) throws SQLException {
        pstm.close();
    }
    //method to close ResultSet objet

    public void closeResultSet(ResultSet rs) throws SQLException {
        rs.close();
    }
    //method to rollback transaction

    public void rolledBackTransaction(Connection con) throws SQLException {
        con.rollback();
    }
    //method to commit transaction

    public void commitTransaction(Connection con) throws SQLException {
        con.commit();
    }
}
