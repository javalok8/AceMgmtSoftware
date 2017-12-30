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
public class ImportModel {
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";

    public ImportModel(Connection conn) {
        this.conn = conn;
    }
    
    
    //find all product with start date
     public List<Import> findAllProducts(String date){
        try{
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    List<Import> result = new ArrayList<>();
                    q="select *from tbl_import_product_record where date=?";
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
                    q = "select *from tbl_import_product_record where date between  '" + startDate + "' and '" + endDate + "'";
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
     
    //individual daily
     //find all product with start date
     public List<Import> findAllProductsIndividual(String date ,String productname){
        try{
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    List<Import> result = new ArrayList<>();
                    q="select *from tbl_import_product_record where date=? and product_name=?";
                    pstm = conn.prepareStatement(q);
                    pstm.setString(1,date);
                    pstm.setString(2,productname);
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
     public List<Import> findAllProductsIndividual(String startDate ,String endDate,String productname){
        try{
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    List<Import> result = new ArrayList<>();
                   // q = "select *from tbl_selling where date between  '" + startDate + "' and '" + endDate + "'"+"' and '" + productname + "'";
                   q = "select *from tbl_import_product_record where date between ? and ? and product_name=?"; 
                   pstm = conn.prepareStatement(q);
                   pstm.setString(1,startDate);
                   pstm.setString(2,endDate);
                   pstm.setString(3,productname);
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
