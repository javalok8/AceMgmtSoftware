/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NepaliCalendarClassGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author JA
 */
public class NepaliDate {

    public NepaliDate() {
    }

    /**
     * method to set Nepali Date in JTextField
     * by clicking on JTextField
     * 
     * ex: 2017-baisakh-12
     * 
     * @param tf 
     */
    public void setTextFieldWithNepaliDateValue(javax.swing.JTextField tf) {
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

        String ddd[] = nDate.split("-");

        int nyear = Integer.parseInt(ddd[0]);
        int nmonth = Integer.parseInt(ddd[1]);
        int nday = Integer.parseInt(ddd[2]);

        String nmonthName = null;

       // nmonthName = mcc.nepaliMonthValueChange(nmonth);

        java.util.Date nBlDate = null;
        String nTfDate = null;
        //nTfDate = String.valueOf(nyear) + "-" + nmonthName + "-" + String.valueOf(nday);
        nTfDate = String.valueOf(nyear) + "-" + String.valueOf(nmonth) + "-" + String.valueOf(nday);
        tf.setText(nTfDate);
    }
    
    /**
     * 
     * nepali date return 
     * @param tf
     */

     public String returnNepaliDateValue() {
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

        String ddd[] = nDate.split("-");

        int nyear = Integer.parseInt(ddd[0]);
        int nmonth = Integer.parseInt(ddd[1]);
        int nday = Integer.parseInt(ddd[2]);

        String nmonthName = null;

       // nmonthName = mcc.nepaliMonthValueChange(nmonth);

        java.util.Date nBlDate = null;
        String nTfDate = null;
        //nTfDate = String.valueOf(nyear) + "-" + nmonthName + "-" + String.valueOf(nday);
        nTfDate = String.valueOf(nyear) + "-" + String.valueOf(nmonth) + "-" + String.valueOf(nday);
        return nTfDate;
    }
/**
 * method to return nepali date string from english date String
 *
 */
     public String englishTonepaliDateReturn() {
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

        String ddd[] = nDate.split("-");

        int nyear = Integer.parseInt(ddd[0]);
        int nmonth = Integer.parseInt(ddd[1]);
        int nday = Integer.parseInt(ddd[2]);

        String nmonthName = null;

       // nmonthName = mcc.nepaliMonthValueChange(nmonth);

        java.util.Date nBlDate = null;
        String nTfDate = null;
        //nTfDate = String.valueOf(nyear) + "-" + nmonthName + "-" + String.valueOf(nday);
        nTfDate = String.valueOf(nyear) + "-" + String.valueOf(nmonth) + "-" + String.valueOf(nday);
        return nTfDate;
    }
    /**
     * method to create session according to user defined Nepali Date
     * ex: starting Date = 2070-12-12
     * @param jtextfieldStartingDate
     * @param jtextfieldEndDate 
     */
    public void createNepaliDateSession(javax.swing.JTextField jtextfieldStartingDate, javax.swing.JTextField jtextfieldEndDate) {
        System.out.println("I am inside Nepalidate class createNepaiDateSession() method ..");
        try {
            MyECalendarClass mecc = new MyECalendarClass();
            MyNCalendarClass mcc = new MyNCalendarClass();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String startingStringDate = String.valueOf(jtextfieldStartingDate.getText().toString());

            java.util.Date startingDate = sdf.parse(startingStringDate);

            String dateValue[] = startingStringDate.split("-");

            int nyear = Integer.parseInt(dateValue[0]);
            int nmonth = Integer.parseInt(dateValue[1]);
            int nday = Integer.parseInt(dateValue[2]);

            //converting Nepali Date to English Date
            String eDate = mecc.englishConcreteDate(nyear, nmonth, nday);

            java.util.Date eeDate = sdf.parse(eDate);
            sdf.format(eeDate);

            //now adding 365 days for session
            java.util.Date sessionDate = mecc.addDays(eeDate, 365);
            String sessionFormatedDate = sdf.format(sessionDate);

            //now converting english date to Nepali date again;
            String seDate[] = sessionFormatedDate.split("-");

            int seYear = Integer.parseInt(seDate[0]);
            int seMonth = Integer.parseInt(seDate[1]);
            int seDay = Integer.parseInt(seDate[2]);

            Calendar seCal = new GregorianCalendar();
            seCal.set(seYear, seMonth, seDay);

            String sessionNDate = mcc.nepaliConcreteDate(seCal);

            //now set starting and ending textfield value with session date
            String startingDateMonthValue = mcc.nepaliMonthValueChange(nmonth);

            String endSessionSplitValue[] = sessionNDate.split("-/");
            int enMonth = Integer.parseInt(endSessionSplitValue[1]);

            String endingDateMonthValue = mcc.nepaliMonthValueChange(enMonth);

            jtextfieldStartingDate.setText(startingDateMonthValue.trim());
            jtextfieldEndDate.setText(endingDateMonthValue.trim());


        } catch (ParseException ex) {
            System.out.println("Exception lok : " + ex.getMessage());
        }
    }

    public Date convertStringToDate(String stringDate) {
       Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(stringDate);
            return date;
        } catch (ParseException pe) {
            System.out.println("ParseException from convertStringToDate() lok : " + pe.getMessage());
        }
        return date;
    }
}
