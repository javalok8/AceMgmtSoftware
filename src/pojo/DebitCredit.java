/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;
import java.util.Date;

/**
 *
 * @author javalok
 */
public class DebitCredit {
    private String date;
    private String codeNo;
    private String productName;
    private String qtyType;
    private int totalQuantity;
    private double singlePrice;
    private double totalAmount;
    private String description;
    private String type;

    public DebitCredit(String date, String codeNo, String productName, String qtyType, int totalQuantity, double singlePrice, double totalAmount, String description, String type) {
        this.date = date;
        this.codeNo = codeNo;
        this.productName = productName;
        this.qtyType = qtyType;
        this.totalQuantity = totalQuantity;
        this.singlePrice = singlePrice;
        this.totalAmount = totalAmount;
        this.description = description;
        this.type = type;
    }
    
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCodeNo() {
        return codeNo;
    }

    public void setCodeNo(String codeNo) {
        this.codeNo = codeNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQtyType() {
        return qtyType;
    }

    public void setQtyType(String qtyType) {
        this.qtyType = qtyType;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
