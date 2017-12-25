/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NepaliCalendarClassGroup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JA
 */
public class MyECalendarClass {

    public Map<Integer, int[]> nepaliMap;
    //defining starting nepali date
    int startingNepYear = 2000;
    int startingNepMonth = 01;
    int startingNepDay = 01;
    int dayOfWeek = Calendar.WEDNESDAY; // 2000/1/1 is Wednesday
    //equivalent english  date
    int startingEngYear = 1943;
    int startingEngMonth = 04;
    int startingEngDay = 14;

    public MyECalendarClass() {
        // The 0s at index 0 are dummy values so as to make the int array of
        // days in months seems more intuitive that index 1 refers to first
        // month "Baisakh", index 2 refers to second month "Jesth" and so on. 

        nepaliMap = new HashMap<Integer, int[]>();

        nepaliMap.put(2000, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2001, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2002, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2003, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2004, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2005, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2006, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2007, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2008, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31});
        nepaliMap.put(2009, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2010, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2011, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2012, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
        nepaliMap.put(2013, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2014, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2015, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2016, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
        nepaliMap.put(2017, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2018, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2019, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2020, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2021, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2022, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
        nepaliMap.put(2023, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2024, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2025, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2026, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2027, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2028, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2029, new int[]{0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2030, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2031, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2032, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2033, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2034, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2035, new int[]{0, 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31});
        nepaliMap.put(2036, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2037, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2038, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2039, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
        nepaliMap.put(2040, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2041, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2042, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2043, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
        nepaliMap.put(2044, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2045, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2046, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2047, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2048, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2049, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
        nepaliMap.put(2050, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2051, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2052, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2053, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
        nepaliMap.put(2054, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2055, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2056, new int[]{0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2057, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2058, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2059, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2060, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2061, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2062, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31});
        nepaliMap.put(2063, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2064, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2065, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2066, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31});
        nepaliMap.put(2067, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2068, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2069, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2070, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30});
        nepaliMap.put(2071, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2072, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2073, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31});
        nepaliMap.put(2074, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2075, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2076, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
        nepaliMap.put(2077, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31});
        nepaliMap.put(2078, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2079, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30});
        nepaliMap.put(2080, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30});
        nepaliMap.put(2081, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2082, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2083, new int[]{0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2084, new int[]{0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2085, new int[]{0, 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2086, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2087, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2088, new int[]{0, 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2089, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});
        nepaliMap.put(2090, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});

    }
    //nepali date to be converted
//    int nepYear = 2070;
//    int nepMonth = 10;
//    int nepDay = 27;

    /**
     * method to count days between two nepali dates
     */
    public long daysBetween(int nepYear, int nepMonth, int nepDay) {
        System.out.println("I am inside daysBetween: ");
        long totalNepDaysCount = 0;

// count total days in-terms of year
        for (int i = startingNepYear; i < nepYear; i++) {
            for (int j = 1; j <= 12; j++) {
                totalNepDaysCount += nepaliMap.get(i)[j];
            }
        }
// count total days in-terms of month
        for (int j = startingNepMonth; j < nepMonth; j++) {
            totalNepDaysCount += nepaliMap.get(nepYear)[j];
        }
// count total days in-terms of date
        System.out.println("Total Days Count: " + totalNepDaysCount);
        totalNepDaysCount += nepDay - startingNepDay;
        return totalNepDaysCount;

    }

    /**
     * method to calculate english date
     * by adding total nepali days count onto 
     */
    public String EnglishDateCalculation(int ny, int nm, int nd) {
        System.out.println("I am inside EnglisDateCalculation ");
        int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // calculation of equivalent english date...
        int engYear = startingEngYear;
        int engMonth = startingEngMonth;
        int engDay = startingEngDay;

        int endDayOfMonth = 0;

        long totalNepDaysCount = this.daysBetween(ny, nm, nd);

        while (totalNepDaysCount != 0) {
            if (isLeapYear(engYear)) {
                endDayOfMonth = daysInMonthOfLeapYear[engMonth];
            } else {
                endDayOfMonth = daysInMonth[engMonth];
            }
            engDay++;
            dayOfWeek++;
            if (engDay > endDayOfMonth) {
                engMonth++;
                engDay = 1;
                if (engMonth > 12) {
                    engYear++;
                    engMonth = 1;
                }
            }
            if (dayOfWeek > 7) {
                dayOfWeek = 1;
            }
            totalNepDaysCount--;
        }

        String edate = String.valueOf(engYear) + "-" + String.valueOf(engMonth) + "-" + String.valueOf(engDay) + "-" + dayOfWeek;

        System.out.println("Year: " + engYear);
        System.out.println("Month: " + engMonth);
        System.out.println("Day: " + engDay);
        System.out.println("DayOf Week: " + dayOfWeek);
        return edate;
    }

    public static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        } else {
            return year % 4 == 0;
        }
    }

    /**
     * method to calculate nepali date into english 
     * return as String
     */
    public String englishConcreteDate(int ny, int nm, int nd) {
        int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // calculation of equivalent english date...
        int engYear = startingEngYear;
        int engMonth = startingEngMonth;
        int engDay = startingEngDay;

        int endDayOfMonth = 0;

        long totalNepDaysCount = this.daysBetween(ny, nm, nd);

        while (totalNepDaysCount != 0) {
            if (isLeapYear(engYear)) {
                endDayOfMonth = daysInMonthOfLeapYear[engMonth];
            } else {
                endDayOfMonth = daysInMonth[engMonth];
            }
            engDay++;
            dayOfWeek++;
            if (engDay > endDayOfMonth) {
                engMonth++;
                engDay = 1;
                if (engMonth > 12) {
                    engYear++;
                    engMonth = 1;
                }
            }
            if (dayOfWeek > 7) {
                dayOfWeek = 1;
            }
            totalNepDaysCount--;
        }

        String edate = String.valueOf(engYear) + "-" + String.valueOf(engMonth) + "-" + String.valueOf(engDay);

        System.out.println("Year: " + engYear);
        System.out.println("Month: " + engMonth);
        System.out.println("Day: " + engDay);
        return edate;
    }

    public java.util.Date addDays(java.util.Date d, int days) {
        //d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
       // return d;

        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, days);
         d = c.getTime();
         
         return d;
    }

    /**
     * method to change value for Nepali month
     * ex: Baisakh , jesth etc...
     */
    public String englishMonthValueChange(int month) {

        String smonth = null;

        if (month == 1) {
            return smonth = "January";
        } else if (month == 2) {
            return smonth = "February";
        } else if (month == 3) {
            return smonth = "March";
        } else if (month == 4) {
            return smonth = "April";
        } else if (month == 5) {
            return smonth = "May";
        } else if (month == 6) {
            return smonth = "June";
        } else if (month == 7) {
            return smonth = "July";
        } else if (month == 8) {
            return smonth = "August";
        } else if (month == 9) {
            return smonth = "September";
        } else if (month == 10) {
            return smonth = "October";
        } else if (month == 11) {
            return smonth = "November";
        } else if (month == 12) {
            return smonth = "December";
        } else {
            try {
                throw new Exception("Lok says there is problem ");
            } catch (Exception ex) {
                System.out.println("Exception lok: " + ex);
            }
            return null;
        }

    }

    /**
     * method to change Nepali Day Of week
     * ex: sunday , monday ...
     */
    public String englishDayOfWeekValueChange(int dayOfWeek) {
        String sdayOfWeek = null;

        if (dayOfWeek == 1) {
            return sdayOfWeek = "Sunday";
        } else if (dayOfWeek == 2) {
            return sdayOfWeek = "Monday";
        } else if (dayOfWeek == 3) {
            return sdayOfWeek = "Tuesday";
        } else if (dayOfWeek == 4) {
            return sdayOfWeek = "Wednesday";
        } else if (dayOfWeek == 5) {
            return sdayOfWeek = "Thursday";
        } else if (dayOfWeek == 6) {
            return sdayOfWeek = "Friday";
        } else if (dayOfWeek == 7) {
            return sdayOfWeek = "Saturday";
        } else {
            try {
                throw new Exception("Lok says There is some problem with Nepali Date");
            } catch (Exception ex) {
                System.out.println("Exception lok: " + ex);
            }
            return null;
        }
    }
}
