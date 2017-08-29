package com.nimi.sqprotos.until;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUntil {
	public static String timeStampToDate(long timeStamp){  
        Date    date = new Date(timeStamp);  
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateStr = simpleDateFormat.format(date);  
        return dateStr;  
    }
    public static String timeStampT(long timeStamp){
        Date    date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
    public static String timeStamp(long timeStamp){
        Date    date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

  
    public static String getMonthByTimeStamp(long timeStamp){  
        String date = timeStampToDate(timeStamp);  
        String month = date.substring(5, 7); 
        return month;  
    }  
  
    public static String getDayByTimeStamp(long timeStamp){  
        String date = timeStampToDate(timeStamp);  
        String day = date.substring(8, 10); 
        return day;  
    }  
  
    public static String getHourByTimeStamp(long timeStamp){  
        String date = timeStampToDate(timeStamp);  
        String hour = date.substring(11, 13); 
        return hour;  
    }  


}
