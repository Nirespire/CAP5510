package com.cap5510.cap5510.api.objects;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Vega on 3/30/2016.
 */
public class TimeConversion {

    private static String UTCTimestr;

    public static Time getEasternTime(String time) {
        // System.out.println("Hello World");
        Time result = null;
        try{
           // String timestr = "2014-07-14 01:00:00";
            UTCTimestr = time;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //if 24 hour format


            Date d1 =(Date)format.parse(UTCTimestr);

            Time ppstime = new Time(d1.getTime());

            Calendar cal = Calendar.getInstance();
            cal.setTime(ppstime);
            result = convertToGmt(cal);


        }catch(Exception e){

        }

        return result;
    }



    private static Time convertToGmt(Calendar cal) {

        Date date = cal.getTime();
        System.out.println(date);
        TimeZone tz = cal.getTimeZone();

        String tzid = "America/New_York";
        tz = TimeZone.getTimeZone(tzid);

        System.out.println("input calendar has date [" + date + "]");

        //Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
        long msFromEpochGmt = date.getTime();
        // System.out.println(msFromEpochGmt);
        //gives you the current offset in ms from GMT at the current date
        int offsetFromUTC = tz.getOffset(msFromEpochGmt);
        System.out.println("offset is " + offsetFromUTC);

        //create a new calendar in GMT timezone, set to this date and add the offset
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.setTime(date);
        gmtCal.add(Calendar.MILLISECOND, offsetFromUTC);
//
       // System.out.println("Created EST cal with date [" + gmtCal.getTime() + "]");
//
        Date timedate = gmtCal.getTime();
        Time time =new Time(timedate.getTime());
        return time;

    }
}
