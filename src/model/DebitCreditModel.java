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

    public DebitCreditModel(Connection conn) {
        this.conn = conn;
    }
    
    
    //find all product with start date
     public List<Import> findAllProducts(String date){
        try{
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    List<Import> result = new ArrayList<>();
                    q="select *from tbl_credit_debit where date=?";
                    pstm = conn.prepareStatement(q);
                    pstm.setString(1,date);
                    rs = pstm.executeQuery();
                    while(rs.next()){
                        result.add(new Import(rs.getString("date"), rs.getString("codeno"), rs.getString("product_name"),
                                rs.getString("product_qty_name"), rs.getInt("product_qty"), rs.getDouble("product_single_price"),
                                rs.getDouble("total_amount"), rs.getString("description")));
                    }
            return result;
        }catch(Exception e){
        return null;
        }
    }
     
    //find single product with start dste
     public List<Import> findAllProducts(String startDate ,String endDate){
        try{
                    List<Import> result = new ArrayList<>();
                    q = "select *from tbl_credit_debit where date between  '" + startDate + "' and '" + endDate + "'";
                    //q = "select *from tbl_selling where date between ? and ?";
                    pstm = conn.prepareStatement(q);
                  //  pstm.setDate(1,startDate);
                  //  pstm.setDate(2,endDate);
                    rs = pstm.executeQuery();
                    while(rs.next()){
                        result.add(new Import(rs.getString("date"), rs.getString("codeno"), rs.getString("product_name"),
                                rs.getString("product_qty_name"), rs.getInt("product_qty"), rs.getDouble("product_single_price"),
                                rs.getDouble("total_amount"), rs.getString("description")));
                    }
            return result;
        }catch(Exception e){
        return null;
        }
    }
     
}
