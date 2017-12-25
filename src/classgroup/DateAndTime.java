/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author JA
 */
public class DateAndTime extends JFrame {

    Calendar cal = null;
    Thread clock = null;

    public DateAndTime() {

        cal = new GregorianCalendar();
       
    }

    // method for date
    public String currentDate() {
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return year + "/" + (month + 1) + "/" + day;
    }

    //method for time
    public void currentTimeForJLabel(final JLabel jLabelTime) {
        Thread clock = new Thread() {

            @Override
            public void run() {

                for (;;) {
                    Calendar cal = new GregorianCalendar();
                    //int hour = cal.get(Calendar.HOUR_OF_DAY -- for 24
                    int hour = cal.get(Calendar.HOUR);
                    int minute = cal.get(Calendar.MINUTE);
                    int second = cal.get(Calendar.SECOND);
                    int ampm = cal.get(Calendar.AM_PM);

                    String ampms = null;

                    if (ampm == 0) {
                        ampms = "am";
                    }
                    if (ampm == 1) {
                        ampms = "pm";
                    }
                    if(hour==0){
                        hour = 12;
                    }
                    
                    if(second<10){
                        second = Integer.parseInt("0") +second;
                    }
                    if(minute<10){
                        minute = Integer.parseInt("0")+minute;
                    }
                    jLabelTime.setText(hour + ":" + minute + ":" + second + " " + ampms);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        clock.start();
    }
}
