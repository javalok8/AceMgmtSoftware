/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import NepaliCalendarClassGroup.MyNCalendarClass;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JTextField;

/**
 *
 * @author JA
 */
public class BatchCreate {
    public final void batchCreation(JTextField jTextFieldbatcId) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startSession = sdf.format(date);
        String[] dd = startSession.split("-");

        int year = Integer.parseInt(dd[0]);
        int month = Integer.parseInt(dd[1]);
        int day = Integer.parseInt(dd[2]);

        Calendar currentEngDate = new GregorianCalendar();
        currentEngDate.set(year, month, day);

        MyNCalendarClass mcc = new MyNCalendarClass();
        String nDate = mcc.nepaliConcreteDate(currentEngDate);

        String arr[] = nDate.split("[: -]");

        String batchId = "#Year" + arr[0].trim();
        jTextFieldbatcId.setText(batchId);


    }

    public final String batchCreation() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startSession = sdf.format(date);
        String[] dd = startSession.split("-");

        int year = Integer.parseInt(dd[0]);
        int month = Integer.parseInt(dd[1]);
        int day = Integer.parseInt(dd[2]);

        Calendar currentEngDate = new GregorianCalendar();
        currentEngDate.set(year, month, day);

        MyNCalendarClass mcc = new MyNCalendarClass();
        String nDate = mcc.nepaliConcreteDate(currentEngDate);
        String arr[] = nDate.split("[: -]");
        String batchId = "#Year" + arr[0].trim();

        return batchId;

    }
}
