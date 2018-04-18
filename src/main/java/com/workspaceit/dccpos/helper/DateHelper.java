package com.workspaceit.dccpos.helper;

import org.joda.time.DateTimeZone;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mi_rafi on 1/5/18.
 */
public class DateHelper {
    public static String getProcessedTimeStamp(Timestamp timeStamp) {
        String processedTime = "";
        if(timeStamp!=null){

            Long longTime = timeStamp.getTime() / 1000;
            processedTime = Long.toString(longTime);
        }

        return processedTime;
    }
    public static Date getCurrentSystemDate() {
        return new Date();

    }
    public static Timestamp getCurrentUtcDateTimeStamp() {
        Date local = new Date();
        DateTimeZone zone = DateTimeZone.getDefault();
        long utc = zone.convertLocalToUTC(local.getTime(), false);

        return new Timestamp(utc);


    }
    public static String getUtcDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return dateFormat.format(new Date());
    }
    public static Timestamp getUtcTimeStamp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


        return getStringToTimeStamp(dateFormat.format(new Date()), "yyyy-MM-dd");
    }

    public static boolean isDateValid(String date,String dateFormat)
    {
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Timestamp getStringToTimeStamp(String strDate,String format)throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(strDate);
        return new Timestamp(date.getTime());
    }
    public static java.sql.Date getStringToDate(String strDate,String format)throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(strDate);

        return  new java.sql.Date(date.getTime());

    }
    public static Date getStringToUtilDate(String strDate,String format)throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(strDate);

        return  date;

    }
    public static Timestamp getSQLDateToTimeStamp(Date date){
        return new Timestamp(date.getTime());

    }
    public static Time getStringToTime(String timeStr)throws IllegalArgumentException{
        return Time.valueOf(timeStr);
    }

    public static java.sql.Date getUtilToSqlDate(Date utilDate){
        return new java.sql.Date(utilDate.getTime());
    }
}
