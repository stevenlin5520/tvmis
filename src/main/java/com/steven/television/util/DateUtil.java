package com.steven.television.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author steven
 * @desc
 * @date 2020/11/14 19:14
 */
public class DateUtil {

    public static String getDateStr(Date date){
        if(date == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDateTimeStr(Date date){
        if(date == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static Date StrDateToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static Date StrTimeToDate(String datetime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(datetime);
    }

    public static Date dateAddTime(Date startDate,long seconds){
        long allTime = startDate.getTime() + seconds*1000;
        return new Date(allTime);
    }

    public static Date getDate(String date,String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    /**
     * 计算两个时间之间的长度（单位：s）
     * @param start
     * @param end
     * @return
     */
    public static Long diffDate(Date start,Date end){
        return (end.getTime()-start.getTime())/1000;
    }

    public static String getTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
}
