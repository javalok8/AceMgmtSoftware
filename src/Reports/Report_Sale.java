/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * Product_qtys_Manager.java
 *
 * Created on May 27, 2014, 3:36:00 PM
 */
package Reports;

import classgroup.JComboHandle;
import classgroup.JavaValidation;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import pojo.Selling;
import java.io.*;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import java.util.*;
import javax.swing.JOptionPane;
import model.SellingModel;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author javalok
 */
public class Report_Sale extends javax.swing.JInternalFrame {

    private JavaValidation javaValidation;
    private Connection conn;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String q = "";

    public void fillProductNameCombo() {
        try {
            q = "select product_name from tbl_productname_entry";
            new JComboHandle(conn).fillComboProductName(jComboBoxProductName, q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        jRadioButtonDaily.setSelected(false);
        jRadioButtonMonthly.setSelected(false);
        jComboBoxProductName.setEnabled(true);
        jCheckBox1.setSelected(false);
       jTextFieldStartDate.setEnabled(true);
       jTextFieldStartDate.setText("");
        jTextFieldStartDate.setVisible(true);
        jLabel3.setVisible(true);
        jTextFieldEndDate.setEnabled(false);
        jTextFieldEndDate.setVisible(false);
        jLabel4.setVisible(false);
        jPanel1.updateUI();
        jPanel1.revalidate(); 
        
    }
    public void generateReport() {
        try {

            String startdate = jTextFieldStartDate.getText().toString();
            String enddate = jTextFieldEndDate.getText().toString().toString();
            String productName = jComboBoxProductName.getSelectedItem().toString();

            //Report format 
            SellingModel sellingModel = new SellingModel(conn);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Product Selling Report"); // you can add param as sheet also
            //create heading 
            Row rowHeading = sheet.createRow(0);
            rowHeading.createCell(0).setCellValue("Date");
            rowHeading.createCell(1).setCellValue("CodeNo");
            rowHeading.createCell(2).setCellValue("Product Name");
            rowHeading.createCell(3).setCellValue("Quantity Type");
            rowHeading.createCell(4).setCellValue("Total Quantity");
            rowHeading.createCell(5).setCellValue("Single Price");
            rowHeading.createCell(6).setCellValue("Total Amount");
            // rowHeading.createCell(7).setCellValue("Description");

            //styling heading
            for (int i = 0; i < 7; i++) {
                CellStyle stylerowHeading = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setFontName(HSSFFont.FONT_ARIAL);
                font.setFontHeightInPoints((short) 11);
                stylerowHeading.setFont(font);
                stylerowHeading.setVerticalAlignment(VerticalAlignment.CENTER);
                rowHeading.getCell(i).setCellStyle(stylerowHeading);

            }

            List<Selling> result = new ArrayList<Selling>();
            if (jRadioButtonDaily.isSelected()) {

                System.out.println("Daily is Selected : ");
                //Dailty Report
                if (jCheckBox1.isSelected()) {
                    System.out.println("All is Selected : ");
                    //all report       
                    result = sellingModel.findAllProducts(startdate);

                    //////////////////////////////////////////////////////////////////////
                    ///// file All daily products ///////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                    //all report desigh
                    int r = 1;
                    for (Selling p : result) {
                        Row row = sheet.createRow(r);
                        //date 
                        Cell date = row.createCell(0);
                        date.setCellValue(p.getDate());
                        //codeno
                        Cell codeno = row.createCell(1);
                        codeno.setCellValue(p.getCodeNo());
                        //product name column 
                        Cell productNames = row.createCell(2);
                        productNames.setCellValue(p.getProductName());
                        //product qty
                        Cell qtyName = row.createCell(3);
                        qtyName.setCellValue(p.getQtyType());
                        //qty amount column 
                        Cell qtyAmount = row.createCell(4);
                        qtyAmount.setCellValue(p.getTotalQuantity());
                        //CellStyle styleCreationDate = workbook.createCellStyle();
                        //HSSFDataFormat dfCreationDate = workbook.createDataFormat();
                        //styleCreationDate.setDataFormat(dfCreationDate.getFormat("yyyy-mm-dd"));
                        //cellDate.setCellStyle(styleCreationDate);

                        //price column 
                        Cell singlePrice = row.createCell(5);
                        singlePrice.setCellValue(p.getSinglePrice());
                        CellStyle stylePrice = workbook.createCellStyle();
                        HSSFDataFormat cf = workbook.createDataFormat();
                        stylePrice.setDataFormat(cf.getFormat("#,##0.00"));
                        singlePrice.setCellStyle(stylePrice);

                        //total amount column 
                        Cell cellSubTotal = row.createCell(6);
                        cellSubTotal.setCellValue(p.getTotalAmount());
                        CellStyle styleSubTotal = workbook.createCellStyle();
                        HSSFDataFormat cfsb = workbook.createDataFormat();
                        styleSubTotal.setDataFormat(cfsb.getFormat("#,##0.00"));
                        cellSubTotal.setCellStyle(stylePrice);

                        //description column 
                        // Cell description = row.createCell(7);
                        // description.setCellValue(p.getDescription());
                        r++;
                    }

                    //total Column
                    Row rowTotal = sheet.createRow(sellingModel.findAllProducts(startdate).size() + 1);
                    Cell cellTextTotal = rowTotal.createCell(0);
                    int total_size = sellingModel.findAllProducts(startdate).size() + 2;
                    System.out.println("Total Size = " + total_size);
                    cellTextTotal.setCellValue("Total ");
                    CellRangeAddress region = CellRangeAddress.valueOf("A" + total_size + ":D" + total_size + "");
                    sheet.addMergedRegion(region);
                    CellStyle styleTotal = workbook.createCellStyle();
                    Font fontTextTotal = workbook.createFont();
                    fontTextTotal.setBold(true);
                    fontTextTotal.setFontHeightInPoints((short) 10);
                    fontTextTotal.setColor(HSSFColor.RED.index);
                    styleTotal.setFont(fontTextTotal);
                    styleTotal.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTextTotal.setCellStyle(styleTotal);

                    //Total qty
                    //q = ""
                    Cell cellTotalQty = rowTotal.createCell(4);
                    int total_colum_qty = sellingModel.findAllProducts(startdate).size() + 1;
                    System.out.println("Total qty amount : " + total_colum_qty);
                    cellTotalQty.setCellFormula("sum(E2" + ":E" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsb = workbook.createDataFormat();
                    CellStyle styleTotalValue = workbook.createCellStyle();
                    //styleTotalValue.setDataFormat(cfsb.getFormat("#,##0.00"));
                    Font fontTextTotals = workbook.createFont();
                    fontTextTotals.setBold(true);
                    fontTextTotals.setFontHeightInPoints((short) 10);
                    fontTextTotals.setColor(HSSFColor.RED.index);
                    styleTotalValue.setFont(fontTextTotals);
                    styleTotalValue.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalQty.setCellStyle(styleTotalValue);
                    //Total amount
                    Cell cellTotalAmount = rowTotal.createCell(6);
                    cellTotalAmount.setCellFormula("sum(G2" + ":G" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsbs = workbook.createDataFormat();
                    CellStyle styleTotals = workbook.createCellStyle();
                    styleTotals.setDataFormat(cfsbs.getFormat("#,##0.00"));
                    Font fontTextTotalss = workbook.createFont();
                    fontTextTotalss.setBold(true);
                    fontTextTotalss.setFontHeightInPoints((short) 10);
                    fontTextTotalss.setColor(HSSFColor.RED.index);
                    styleTotals.setFont(fontTextTotalss);
                    styleTotals.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalAmount.setCellStyle(styleTotals);

                    //Autofit
                    for (int i = 0; i < 7; i++) {
                        sheet.autoSizeColumn(i);
                    }
                    System.out.println("Result object inside is  : " + result);
                    FileOutputStream out = new FileOutputStream(new File("d:\\SellingAllDailyReport.xls"));
                    workbook.write(out);
                    out.close();
                    workbook.close();
                } else {
                    //individual report
                    System.out.println("Combo is Selected : ");
                    result = sellingModel.findAllProductsIndividual(startdate, productName);
                    //////////////////////////////////////////////////////////////////////
                    ///// file individual daily products ///////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                    //all report desigh
                    int r = 1;
                    for (Selling p : result) {
                        Row row = sheet.createRow(r);
                        //date 
                        Cell date = row.createCell(0);
                        date.setCellValue(p.getDate());
                        //codeno
                        Cell codeno = row.createCell(1);
                        codeno.setCellValue(p.getCodeNo());
                        //product name column 
                        Cell productNames = row.createCell(2);
                        productNames.setCellValue(p.getProductName());
                        //product qty
                        Cell qtyName = row.createCell(3);
                        qtyName.setCellValue(p.getQtyType());
                        //qty amount column 
                        Cell qtyAmount = row.createCell(4);
                        qtyAmount.setCellValue(p.getTotalQuantity());
                        //CellStyle styleCreationDate = workbook.createCellStyle();
                        //HSSFDataFormat dfCreationDate = workbook.createDataFormat();
                        //styleCreationDate.setDataFormat(dfCreationDate.getFormat("yyyy-mm-dd"));
                        //cellDate.setCellStyle(styleCreationDate);

                        //price column 
                        Cell singlePrice = row.createCell(5);
                        singlePrice.setCellValue(p.getSinglePrice());
                        CellStyle stylePrice = workbook.createCellStyle();
                        HSSFDataFormat cf = workbook.createDataFormat();
                        stylePrice.setDataFormat(cf.getFormat("#,##0.00"));
                        singlePrice.setCellStyle(stylePrice);

                        //total amount column 
                        Cell cellSubTotal = row.createCell(6);
                        cellSubTotal.setCellValue(p.getTotalAmount());
                        CellStyle styleSubTotal = workbook.createCellStyle();
                        HSSFDataFormat cfsb = workbook.createDataFormat();
                        styleSubTotal.setDataFormat(cfsb.getFormat("#,##0.00"));
                        cellSubTotal.setCellStyle(stylePrice);

                        //description column 
                        // Cell description = row.createCell(7);
                        // description.setCellValue(p.getDescription());
                        r++;
                    }

                    //total Column
                    Row rowTotal = sheet.createRow(sellingModel.findAllProductsIndividual(startdate, productName).size() + 1);
                    Cell cellTextTotal = rowTotal.createCell(0);
                    int total_size = sellingModel.findAllProductsIndividual(startdate, productName).size() + 2;
                    System.out.println("Total Size = " + total_size);
                    cellTextTotal.setCellValue("Total ");
                    CellRangeAddress region = CellRangeAddress.valueOf("A" + total_size + ":D" + total_size + "");
                    sheet.addMergedRegion(region);
                    CellStyle styleTotal = workbook.createCellStyle();
                    Font fontTextTotal = workbook.createFont();
                    fontTextTotal.setBold(true);
                    fontTextTotal.setFontHeightInPoints((short) 10);
                    fontTextTotal.setColor(HSSFColor.RED.index);
                    styleTotal.setFont(fontTextTotal);
                    styleTotal.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTextTotal.setCellStyle(styleTotal);

                    //Total qty
                    //q = ""
                    Cell cellTotalQty = rowTotal.createCell(4);
                    int total_colum_qty = sellingModel.findAllProductsIndividual(startdate, productName).size() + 1;
                    System.out.println("Total qty amount : " + total_colum_qty);
                    cellTotalQty.setCellFormula("sum(E2" + ":E" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsb = workbook.createDataFormat();
                    CellStyle styleTotalValue = workbook.createCellStyle();
                    //styleTotalValue.setDataFormat(cfsb.getFormat("#,##0.00"));
                    Font fontTextTotals = workbook.createFont();
                    fontTextTotals.setBold(true);
                    fontTextTotals.setFontHeightInPoints((short) 10);
                    fontTextTotals.setColor(HSSFColor.RED.index);
                    styleTotalValue.setFont(fontTextTotals);
                    styleTotalValue.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalQty.setCellStyle(styleTotalValue);
                    //Total amount
                    Cell cellTotalAmount = rowTotal.createCell(6);
                    cellTotalAmount.setCellFormula("sum(G2" + ":G" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsbs = workbook.createDataFormat();
                    CellStyle styleTotals = workbook.createCellStyle();
                    styleTotals.setDataFormat(cfsbs.getFormat("#,##0.00"));
                    Font fontTextTotalss = workbook.createFont();
                    fontTextTotalss.setBold(true);
                    fontTextTotalss.setFontHeightInPoints((short) 10);
                    fontTextTotalss.setColor(HSSFColor.RED.index);
                    styleTotals.setFont(fontTextTotalss);
                    styleTotals.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalAmount.setCellStyle(styleTotals);

                    //Autofit
                    for (int i = 0; i < 7; i++) {
                        sheet.autoSizeColumn(i);
                    }
                    FileOutputStream out = new FileOutputStream(new File("d:\\SellingDailyIndividualReport.xls"));
                    workbook.write(out);
                    out.close();
                    workbook.close();
                }
            } else {
                //Monthly Report
                System.out.println("Monthly is Selected : ");
                if (jCheckBox1.isSelected()) {
                    //all report
                    result = sellingModel.findAllProducts(startdate, enddate);
                    //////////////////////////////////////////////////////////////////////
                    ///// file All monthly products ///////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                    //all report desigh
                    int r = 1;
                    for (Selling p : result) {
                        Row row = sheet.createRow(r);
                        //date 
                        Cell date = row.createCell(0);
                        date.setCellValue(p.getDate());
                        //codeno
                        Cell codeno = row.createCell(1);
                        codeno.setCellValue(p.getCodeNo());
                        //product name column 
                        Cell productNames = row.createCell(2);
                        productNames.setCellValue(p.getProductName());
                        //product qty
                        Cell qtyName = row.createCell(3);
                        qtyName.setCellValue(p.getQtyType());
                        //qty amount column 
                        Cell qtyAmount = row.createCell(4);
                        qtyAmount.setCellValue(p.getTotalQuantity());
                        //CellStyle styleCreationDate = workbook.createCellStyle();
                        //HSSFDataFormat dfCreationDate = workbook.createDataFormat();
                        //styleCreationDate.setDataFormat(dfCreationDate.getFormat("yyyy-mm-dd"));
                        //cellDate.setCellStyle(styleCreationDate);

                        //price column 
                        Cell singlePrice = row.createCell(5);
                        singlePrice.setCellValue(p.getSinglePrice());
                        CellStyle stylePrice = workbook.createCellStyle();
                        HSSFDataFormat cf = workbook.createDataFormat();
                        stylePrice.setDataFormat(cf.getFormat("#,##0.00"));
                        singlePrice.setCellStyle(stylePrice);

                        //total amount column 
                        Cell cellSubTotal = row.createCell(6);
                        cellSubTotal.setCellValue(p.getTotalAmount());
                        CellStyle styleSubTotal = workbook.createCellStyle();
                        HSSFDataFormat cfsb = workbook.createDataFormat();
                        styleSubTotal.setDataFormat(cfsb.getFormat("#,##0.00"));
                        cellSubTotal.setCellStyle(stylePrice);

                        //description column 
                        // Cell description = row.createCell(7);
                        // description.setCellValue(p.getDescription());
                        r++;
                    }

                    //total Column
                    Row rowTotal = sheet.createRow(sellingModel.findAllProducts(startdate, enddate).size() + 1);
                    Cell cellTextTotal = rowTotal.createCell(0);
                    int total_size = sellingModel.findAllProducts(startdate, enddate).size() + 2;
                    System.out.println("Total Size = " + total_size);
                    cellTextTotal.setCellValue("Total ");
                    CellRangeAddress region = CellRangeAddress.valueOf("A" + total_size + ":D" + total_size + "");
                    sheet.addMergedRegion(region);
                    CellStyle styleTotal = workbook.createCellStyle();
                    Font fontTextTotal = workbook.createFont();
                    fontTextTotal.setBold(true);
                    fontTextTotal.setFontHeightInPoints((short) 10);
                    fontTextTotal.setColor(HSSFColor.RED.index);
                    styleTotal.setFont(fontTextTotal);
                    styleTotal.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTextTotal.setCellStyle(styleTotal);

                    //Total qty
                    //q = ""
                    Cell cellTotalQty = rowTotal.createCell(4);
                    int total_colum_qty = sellingModel.findAllProducts(startdate, enddate).size() + 1;
                    System.out.println("Total qty amount : " + total_colum_qty);
                    cellTotalQty.setCellFormula("sum(E2" + ":E" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsb = workbook.createDataFormat();
                    CellStyle styleTotalValue = workbook.createCellStyle();
                    //styleTotalValue.setDataFormat(cfsb.getFormat("#,##0.00"));
                    Font fontTextTotals = workbook.createFont();
                    fontTextTotals.setBold(true);
                    fontTextTotals.setFontHeightInPoints((short) 10);
                    fontTextTotals.setColor(HSSFColor.RED.index);
                    styleTotalValue.setFont(fontTextTotals);
                    styleTotalValue.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalQty.setCellStyle(styleTotalValue);
                    //Total amount
                    Cell cellTotalAmount = rowTotal.createCell(6);
                    cellTotalAmount.setCellFormula("sum(G2" + ":G" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsbs = workbook.createDataFormat();
                    CellStyle styleTotals = workbook.createCellStyle();
                    styleTotals.setDataFormat(cfsbs.getFormat("#,##0.00"));
                    Font fontTextTotalss = workbook.createFont();
                    fontTextTotalss.setBold(true);
                    fontTextTotalss.setFontHeightInPoints((short) 10);
                    fontTextTotalss.setColor(HSSFColor.RED.index);
                    styleTotals.setFont(fontTextTotalss);
                    styleTotals.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalAmount.setCellStyle(styleTotals);

                    //Autofit
                    for (int i = 0; i < 7; i++) {
                        sheet.autoSizeColumn(i);
                    }
                    FileOutputStream out = new FileOutputStream(new File("d:\\SellingAllMonthlyReport.xls"));
                    workbook.write(out);
                    out.close();
                    workbook.close();
                } else {
                    System.out.println("Monthly individual is Selected : ");
                    //individual report
                    result = sellingModel.findAllProductsIndividual(startdate, enddate, productName);
                    //////////////////////////////////////////////////////////////////////
                    ///// file monthly individual products ///////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////
                    //all report desigh
                    int r = 1;
                    for (Selling p : result) {
                        Row row = sheet.createRow(r);
                        //date 
                        Cell date = row.createCell(0);
                        date.setCellValue(p.getDate());
                        //codeno
                        Cell codeno = row.createCell(1);
                        codeno.setCellValue(p.getCodeNo());
                        //product name column 
                        Cell productNames = row.createCell(2);
                        productNames.setCellValue(p.getProductName());
                        //product qty
                        Cell qtyName = row.createCell(3);
                        qtyName.setCellValue(p.getQtyType());
                        //qty amount column 
                        Cell qtyAmount = row.createCell(4);
                        qtyAmount.setCellValue(p.getTotalQuantity());
                        //CellStyle styleCreationDate = workbook.createCellStyle();
                        //HSSFDataFormat dfCreationDate = workbook.createDataFormat();
                        //styleCreationDate.setDataFormat(dfCreationDate.getFormat("yyyy-mm-dd"));
                        //cellDate.setCellStyle(styleCreationDate);

                        //price column 
                        Cell singlePrice = row.createCell(5);
                        singlePrice.setCellValue(p.getSinglePrice());
                        CellStyle stylePrice = workbook.createCellStyle();
                        HSSFDataFormat cf = workbook.createDataFormat();
                        stylePrice.setDataFormat(cf.getFormat("#,##0.00"));
                        singlePrice.setCellStyle(stylePrice);

                        //total amount column 
                        Cell cellSubTotal = row.createCell(6);
                        cellSubTotal.setCellValue(p.getTotalAmount());
                        CellStyle styleSubTotal = workbook.createCellStyle();
                        HSSFDataFormat cfsb = workbook.createDataFormat();
                        styleSubTotal.setDataFormat(cfsb.getFormat("#,##0.00"));
                        cellSubTotal.setCellStyle(stylePrice);

                        //description column 
                        // Cell description = row.createCell(7);
                        // description.setCellValue(p.getDescription());
                        r++;
                    }

                    //total Column
                    Row rowTotal = sheet.createRow(sellingModel.findAllProductsIndividual(startdate, enddate, productName).size() + 1);
                    Cell cellTextTotal = rowTotal.createCell(0);
                    int total_size = sellingModel.findAllProductsIndividual(startdate, enddate, productName).size() + 2;
                    System.out.println("Total Size = " + total_size);
                    cellTextTotal.setCellValue("Total ");
                    CellRangeAddress region = CellRangeAddress.valueOf("A" + total_size + ":D" + total_size + "");
                    sheet.addMergedRegion(region);
                    CellStyle styleTotal = workbook.createCellStyle();
                    Font fontTextTotal = workbook.createFont();
                    fontTextTotal.setBold(true);
                    fontTextTotal.setFontHeightInPoints((short) 10);
                    fontTextTotal.setColor(HSSFColor.RED.index);
                    styleTotal.setFont(fontTextTotal);
                    styleTotal.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTextTotal.setCellStyle(styleTotal);

                    //Total qty
                    //q = ""
                    Cell cellTotalQty = rowTotal.createCell(4);
                    int total_colum_qty = sellingModel.findAllProductsIndividual(startdate, enddate,productName).size() + 1;
                    System.out.println("Total qty amount : " + total_colum_qty);
                    cellTotalQty.setCellFormula("sum(E2" + ":E" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsb = workbook.createDataFormat();
                    CellStyle styleTotalValue = workbook.createCellStyle();
                    //styleTotalValue.setDataFormat(cfsb.getFormat("#,##0.00"));
                    Font fontTextTotals = workbook.createFont();
                    fontTextTotals.setBold(true);
                    fontTextTotals.setFontHeightInPoints((short) 10);
                    fontTextTotals.setColor(HSSFColor.RED.index);
                    styleTotalValue.setFont(fontTextTotals);
                    styleTotalValue.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalQty.setCellStyle(styleTotalValue);
                    //Total amount
                    Cell cellTotalAmount = rowTotal.createCell(6);
                    cellTotalAmount.setCellFormula("sum(G2" + ":G" + total_colum_qty + ")" + "");
                    HSSFDataFormat cfsbs = workbook.createDataFormat();
                    CellStyle styleTotals = workbook.createCellStyle();
                    styleTotals.setDataFormat(cfsbs.getFormat("#,##0.00"));
                    Font fontTextTotalss = workbook.createFont();
                    fontTextTotalss.setBold(true);
                    fontTextTotalss.setFontHeightInPoints((short) 10);
                    fontTextTotalss.setColor(HSSFColor.RED.index);
                    styleTotals.setFont(fontTextTotalss);
                    styleTotals.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellTotalAmount.setCellStyle(styleTotals);

                    //Autofit
                    for (int i = 0; i < 7; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    FileOutputStream out = new FileOutputStream(new File("d:\\SellingMonthlyIndividualReport.xls"));
                    workbook.write(out);
                    out.close();
                    workbook.close();
                }
            }

            System.out.print("TEST");
            //save to excel file
//            FileOutputStream out = new FileOutputStream(new File("d:\\SellingReport.xls"));
//            workbook.write(out);
//            out.close();
//            workbook.close();

            System.out.println("Excel written successfully....");
            

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates new form Product_qtys_Manager
     */
    public Report_Sale(java.sql.Connection conn) {
        initComponents();
        this.conn = conn;
        javaValidation = new JavaValidation();

        jTextFieldStartDate.setEnabled(false);
        jTextFieldStartDate.setVisible(false);
        jLabel3.setVisible(false);
        jTextFieldEndDate.setEnabled(false);
        jTextFieldEndDate.setVisible(false);
        jLabel4.setVisible(false);
        jPanel1.updateUI();
        jPanel1.revalidate();
        this.fillProductNameCombo();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButtonMonthly = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jRadioButtonDaily = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldEndDate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldStartDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxProductName = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();

        setClosable(true);
        setTitle("Activities Reports");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sale Report"));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Choose Atleast One"));

        buttonGroup1.add(jRadioButtonMonthly);
        jRadioButtonMonthly.setText("Monthly");
        jRadioButtonMonthly.setOpaque(false);
        jRadioButtonMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMonthlyActionPerformed(evt);
            }
        });

        jLabel3.setText("Report Type");

        buttonGroup1.add(jRadioButtonDaily);
        jRadioButtonDaily.setText("Daily");
        jRadioButtonDaily.setOpaque(false);
        jRadioButtonDaily.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonDailyItemStateChanged(evt);
            }
        });
        jRadioButtonDaily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDailyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jRadioButtonDaily)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(jRadioButtonMonthly)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButtonDaily)
                    .addComponent(jRadioButtonMonthly))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Type Date : yyyy-mm-dd"));

        jTextFieldEndDate.setToolTipText("");
        jTextFieldEndDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldEndDateMouseClicked(evt);
            }
        });
        jTextFieldEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEndDateActionPerformed(evt);
            }
        });
        jTextFieldEndDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEndDateKeyTyped(evt);
            }
        });

        jLabel4.setText("End Date");

        jLabel2.setText("Start Date");

        jTextFieldStartDate.setToolTipText("");
        jTextFieldStartDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldStartDateMouseClicked(evt);
            }
        });
        jTextFieldStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStartDateActionPerformed(evt);
            }
        });
        jTextFieldStartDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldStartDateKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jTextFieldEndDate))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jLabel1.setText("Single/All");

        jComboBoxProductName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        jCheckBox1.setText("All Products");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox1)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxProductName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Press Button To Generate Report"));

        jButtonCancel.setText("Cance");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAdd.setText("Generate");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldStartDateMouseClicked
        new NepaliCalendarClassGroup.NepaliDate().setTextFieldWithNepaliDateValue(jTextFieldStartDate);
        jButtonCancel.setEnabled(true);
}//GEN-LAST:event_jTextFieldStartDateMouseClicked

    private void jTextFieldStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStartDateActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldStartDateActionPerformed

    private void jTextFieldStartDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldStartDateKeyTyped
        javaValidation.getDateValue(evt);
}//GEN-LAST:event_jTextFieldStartDateKeyTyped

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
       try{
            this.generateReport();
            JOptionPane.showMessageDialog(this, "Your Report Generated", "D:/ Location", JOptionPane.INFORMATION_MESSAGE);
       }catch(Exception e){
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Please Follow the probper instruction", "D:/ Location", JOptionPane.INFORMATION_MESSAGE);
           
       }

    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.cancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTextFieldEndDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldEndDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEndDateMouseClicked

    private void jTextFieldEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEndDateActionPerformed

    private void jTextFieldEndDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEndDateKeyTyped
        javaValidation.getDateValue(evt);
    }//GEN-LAST:event_jTextFieldEndDateKeyTyped

    private void jRadioButtonDailyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonDailyItemStateChanged
}//GEN-LAST:event_jRadioButtonDailyItemStateChanged

    private void jRadioButtonDailyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDailyActionPerformed
        jTextFieldStartDate.setEnabled(true);
        jTextFieldStartDate.setVisible(true);
        jLabel3.setVisible(true);
        jTextFieldEndDate.setEnabled(false);
        jTextFieldEndDate.setVisible(false);
        jLabel4.setVisible(false);
        jPanel1.updateUI();
        jPanel1.revalidate();
}//GEN-LAST:event_jRadioButtonDailyActionPerformed

    private void jRadioButtonMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMonthlyActionPerformed
        jTextFieldStartDate.setEnabled(true);
        jTextFieldStartDate.setVisible(true);
        jLabel3.setVisible(true);
        jTextFieldEndDate.setEnabled(true);
        jTextFieldEndDate.setVisible(true);
        jLabel4.setVisible(true);
        jPanel1.updateUI();
        jPanel1.revalidate();
}//GEN-LAST:event_jRadioButtonMonthlyActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()) {
            jComboBoxProductName.setEnabled(false);
        } else {
            jComboBoxProductName.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBoxProductName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButtonDaily;
    private javax.swing.JRadioButton jRadioButtonMonthly;
    private javax.swing.JTextField jTextFieldEndDate;
    private javax.swing.JTextField jTextFieldStartDate;
    // End of variables declaration//GEN-END:variables
}
