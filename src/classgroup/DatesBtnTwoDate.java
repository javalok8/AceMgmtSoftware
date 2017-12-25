/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classgroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dreamsoft
 */
public class DatesBtnTwoDate {
   Date Start;
   Date End;
   long interval = 24 * 1000 * 60 * 60;
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        public List<Date> DatesBetweenTwoDates(String StartDate, String EndDate){
        List<Date> dates = new ArrayList<Date>();
        try {
            Start = sdf.parse(StartDate);
            End = sdf.parse(EndDate);
            long endTime = End.getTime();
            long StartTime = Start.getTime();
            while(StartTime<=endTime){
                dates.add(new Date(StartTime));
                StartTime +=interval;
            }

            for(int i=0 ; i<dates.size(); i++){
                Date getingDate = dates.get(i);
                String getDate = sdf.format(getingDate);
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    return dates;
    }
}
