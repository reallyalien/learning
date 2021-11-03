package com.ot.tools.time;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("当天24点时间：" + getDateString(getTimesnight()));
        System.out.println("当天时间：" + getTimeString(new Date()));
        System.out.println("当前时间：" + getDateString(new Date()));
        System.out.println("当前日期：" + getDayString(new Date()));
        System.out.println("当天0点时间：" + getDateString(getTimesmorning()));
        System.out.println("昨天0点时间：" + getDateString(getYesterdaymorning()));
        System.out.println("近2小时时间：" + getDateString(getTwoHourFromNow()));
        System.out.println("1天时间：" + getDateString(getOneDayFromNow()));
        System.out.println("近7天时间：" + getDateString(getWeekFromNow()));
        System.out.println("本周周一0点时间：" + getDateString(getTimesWeekmorning()));
        System.out.println("本周周日24点时间：" + getDateString(getTimesWeeknight()));
        System.out.println("本月初0点时间：" + getDateString(getTimesMonthmorning()));
        System.out.println("本月未24点时间：" + getDateString(getTimesMonthnight()));
        System.out.println("上月初0点时间：" + getDateString(getLastMonthStartMorning()));
        System.out.println("本季度开始点时间：" + getDateString(getCurrentQuarterStartTime()));
        System.out.println("本季度结束点时间：" + getDateString(getCurrentQuarterEndTime()));
        System.out.println("本年开始点时间：" + getDateString(getCurrentYearStartTime()));
        System.out.println("本年结束点时间：" + getDateString(getCurrentYearEndTime()));
        System.out.println("上年开始点时间：" + getDateString(getLastYearStartTime()));
        System.out.println("近一月：" + (getOneMonth()));
        Map<String, String> cd = timeIntervalHandler("oneweek");
        System.out.println("时间间隔：startTime="+cd.get("startTime")+";endTime="+cd.get("endTime"));
    }

    public static String getDateString(Date date) {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = longSdf.format(date);
        return dateString;
    }

    public static String getDayString(Date date) {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = longSdf.format(date);
        return dateString;
    }
    
    public static String getTimeString(Date date) {
        SimpleDateFormat longSdf = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat shortSdf = new SimpleDateFormat("M月dd日");
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        //System.out.println("当前月数="+month);
        String dateString = "";
        if(month>9) {
        	dateString = longSdf.format(date);
        }else {
        	dateString = shortSdf.format(date);
        }
        return dateString;
    }
    //参数date是时间戳字符串
    public static String getDateString(String date) {
    	Long time = new Long(date);
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = longSdf.format(time);
        return dateString;
    }
    
    //参数date是时间戳long
    public static String getDateString(Long date) {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = longSdf.format(date);
        return dateString;
    }

    public static String getLocalDateTimeString(LocalDateTime date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (date != null) {
            String localTime = df.format(date);
            return localTime;

        }

        return null;
    }


    // 获得当天0点时间
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得昨天0点时间
    public static Date getYesterdaymorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000);
        return cal.getTime();
    }

    //获得当天2小时之前时间
    public static Date getTwoHourFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime() - 3600 * 2 * 1000 );
        return cal.getTime();
    }

    //获得当1天之前时间
    public static Date getOneDayFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime() - 3600 * 24 * 1000 );
        return cal.getTime();
    }

    //获得当3天之前时间
    public static Date getThreeDayFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime() - 3600 * 24 * 1000 * 3 );
        return cal.getTime();
    }

    // 获得当天近7天时间
    public static Date getWeekFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000 * 7);
        return cal.getTime();
    }

    // 获得当天近30天时间
    public static Date getMonthFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000 * 30);
        return cal.getTime();
    }

    // 获得当天24点时间
    public static Date getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本周日24点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    public static Date getLastMonthStartMorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMonthmorning());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }


    public static Date getCurrentYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    public static Date getCurrentYearEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public static Date getLastYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    public static Map<String,String> timeIntervalHandler(String timeDimension){
        Map<String,String> dateTimeMap = new HashMap<>();
        String currentDate = getDateString(new Date());
        dateTimeMap.put("endTime",currentDate);
        if (timeDimension.equals("twohours")){
            String twoHours = getDateString(getTwoHourFromNow());
            dateTimeMap.put("startTime",twoHours);
        }else if (timeDimension.equals("cd")||timeDimension.equals("")){
            String currentZero = getDateString(getTimesmorning());
            dateTimeMap.put("startTime",currentZero);
        }else if (timeDimension.equals("oned")){
            String oneDay = getDateString(getOneDayFromNow());
            dateTimeMap.put("startTime",oneDay);
        }else if (timeDimension.equals("threed")){
            String threeDay = getDateString(getThreeDayFromNow());
            dateTimeMap.put("startTime",threeDay);
        }else if (timeDimension.equals("oneweek")){
            String oneWeek = getDateString(getWeekFromNow());
            dateTimeMap.put("startTime",oneWeek);
        }else if (timeDimension.equals("onemonth")){
            String oneMonth = getDateString(getTimesMonthmorning());
            dateTimeMap.put("startTime",oneMonth);
        }
        return dateTimeMap;
    }

    //近一月
    public static String getOneMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }
    
    public static Date getOneMonthDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        return c.getTime();

    }

    //近一季度
    public static String getThreeMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -3);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static String getSixMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -6);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }


    public static String getOneYear(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        return year;
    }

}