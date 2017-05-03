package com.social420.social420;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {
    public static Date fromJSON(String timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timestamp));
        return cal.getTime();
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }


    public static String elapsedTime(long mill) {
        long millis = mill / 1000 ;
        if(millis >=  (30 * 24 * 60 *60 * 12 )){
            return  (millis / (30 * 24 * 60 *60 * 12 )) + " y";
        }
//        else if(millis >= 30 * 24 * 60 *60 ){
//            return (millis  / (30 * 24 * 60 *60 )) + " ";
//        }
        else if (millis >= 24 * 60 * 60 ) {
            return (millis / (24 * 60 * 60 )) + " d";
        } else if (millis >= 3600) {
            return (millis / 3600) + " h";
        } else if (millis >= 60) {
            return (millis / 60) + " m";
        } else if (millis >= 1) {
            return (millis / 1) + " s";
        } else {
            return "Now";
        }
    }
}