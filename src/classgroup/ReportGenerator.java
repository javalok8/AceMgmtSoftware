  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dreamsoft
 */
public class ReportGenerator {



    public void GenerateReport(String q, String path, Connection conn) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(path);
            JRDesignQuery jRDesignQuery = new JRDesignQuery();
            jRDesignQuery.setText(q);
            jasperDesign.setQuery(jRDesignQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Exception: " + e.getMessage(), "Exception Caught!", JOptionPane.ERROR_MESSAGE);
        }


    }

    public void GenerateReport( String path, Connection conn) {
        try {
          
            JasperDesign jasperDesign = JRXmlLoader.load(path);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Exception: " + e.getMessage(), "Exception Caught!", JOptionPane.ERROR_MESSAGE);
        }

    }
}
