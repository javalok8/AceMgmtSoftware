/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classgroup.JavaValidation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import pojo.DebitCredit;
import pojo.Import;

/**
 *
 * @author javalok
 */
public class DebitCreditModel {

    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";

    String typeC = "Credit";
    String typeD = "Debit";

    double sumC = 0.0;
    double sumD = 0.0;

    double total_amount = 0.0;

    public DebitCreditModel(Connection conn) {
        this.conn = conn;
    }

    //find all product with start date
    public List<DebitCredit> findAllProducts(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            List<DebitCredit> result = new ArrayList<>();
            q = "select *from tbl_credit_debit where date=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, date);
            rs = pstm.executeQuery();
            while (rs.next()) {
                result.add(new DebitCredit(rs.getString("date"), rs.getString("codeno"), rs.getString("product_name"),
                        rs.getString("qty_type"), rs.getInt("total_qty"), rs.getDouble("product_single_price"),
                        rs.getDouble("total_amount"), rs.getString("description"), rs.getString("type")));
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    //find single product with start dste
    public List<DebitCredit> findAllProducts(String startDate, String endDate) {
        try {
            List<DebitCredit> result = new ArrayList<>();
            q = "select *from tbl_credit_debit where date between  '" + startDate + "' and '" + endDate + "'";
            //q = "select *from tbl_selling where date between ? and ?";
            pstm = conn.prepareStatement(q);
            //  pstm.setDate(1,startDate);
            //  pstm.setDate(2,endDate);
            rs = pstm.executeQuery();
            while (rs.next()) {
                result.add(new DebitCredit(rs.getString("date"), rs.getString("codeno"), rs.getString("product_name"),
                        rs.getString("qty_type"), rs.getInt("total_qty"), rs.getDouble("product_single_price"),
                        rs.getDouble("total_amount"), rs.getString("description"), rs.getString("type")));
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public double DebitCreditAmount(String startDate) {
        try {

            q = "SELECT SUM(total_amount) FROM tbl_credit_debit where date=? and type=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, startDate);
            pstm.setString(2, typeC);
            rs = pstm.executeQuery();
            while (rs.next()) {
                double c = rs.getDouble(1);
                sumC = sumC + c;
                System.out.println("Sum C is : " + sumC);
            }

            q = "SELECT SUM(total_amount) FROM tbl_credit_debit where date=? and type=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, startDate);
            pstm.setString(2, typeD);
            rs = pstm.executeQuery();
            while (rs.next()) {
                double d = rs.getDouble(1);
                sumD = sumD + d;
                System.out.println("Sum D is : " + sumD);
            }

            total_amount = sumD - sumC;
            System.out.println("Total amount is : " + total_amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total_amount;
    }
    
    public double DebitCreditAmount(String startDate,String endDate) {
        try {
            q = "SELECT SUM(total_amount) FROM tbl_credit_debit where date between ? and ? and type=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, startDate);
            pstm.setString(2,endDate);
            pstm.setString(3, typeC);
            rs = pstm.executeQuery();
            while (rs.next()) {
                double c = rs.getDouble(1);
                sumC = sumC + c;
                System.out.println("Sum C is : " + sumC);
            }

           q = "SELECT SUM(total_amount) FROM tbl_credit_debit where date between ? and ? and type=?";
            pstm = conn.prepareStatement(q);
            pstm.setString(1, startDate);
            pstm.setString(2,endDate);
            pstm.setString(3, typeD);
            rs = pstm.executeQuery();
            while (rs.next()) {
                double d = rs.getDouble(1);
                sumD = sumD + d;
                System.out.println("Sum D is : " + sumD);
            }

            total_amount = sumD - sumC;
            System.out.println("Total amount is : " + total_amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total_amount;
    }

}
