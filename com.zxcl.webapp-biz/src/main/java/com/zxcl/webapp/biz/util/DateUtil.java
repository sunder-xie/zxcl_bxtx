package com.zxcl.webapp.biz.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DateUtil {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
	
	private static final DateFormat dateFormatPrimary = new SimpleDateFormat(DateUtil.YYYYMMDDHHMMSSSSS, Locale.CHINA);
	
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATETIME24_PATTERN_LINE = "yyyy-MM-dd HH:mm:ss";

	public static String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";
	
	public static String date2String(Date date){
		if(null == date){
			return null;
		}
		return dateFormat.format(date);
	}
	
	
	/**
	 * 获得主键
	 * @return
	 */
	public static String primaryKey(){
		return dateFormatPrimary.format(new Date()) + (random.nextInt(900)+100);
	}
	
	private static final Random random = new Random();
	
	/**
	 * 
	 * @param format
	 *            date格式
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String format, String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	public static Date dateToDate(String format, Date date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String formatDate = df.format(date);
		return df.parse(formatDate);
	}

	public static int getYear(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static String dateToString(String format, Date date) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * 获取相隔的年数
	 */
	public static Integer getBetweenYear(Date start,Date end){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int startYear = calendar.get(Calendar.YEAR);
		calendar.setTime(end);
		int endYear = calendar.get(Calendar.YEAR);
		int between=startYear-endYear;
		return between>=0?between:endYear-startYear;
	}
	
	/**
	 * 获取相隔的月数
	 */
	public static Integer getBetweenMonth(Date date1,Date date2){
		if(null==date1||null==date2){
			return null;
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		int year1 = calendar1.get(Calendar.YEAR);
		int year2 = calendar2.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH)+1;
		int month2 = calendar2.get(Calendar.MONTH)+1;
		int btweenyear=year1-year2;
		int btweenMonth=0;
		if(btweenyear<0){
			btweenyear=year2-year1;
			int month=month2-month1;
			btweenMonth=btweenyear*12+month;
		}else if(btweenyear==0){
			btweenMonth=month1-month2;
			if(btweenMonth<0){
				btweenMonth=month2-month1;
			}
		}else{
			int month=month1-month2;
			btweenMonth=btweenyear*12+month;
		}
		return btweenMonth;
	}
    
    /**
     * 计算两个日期之间的天数
     * 
     * @param beginDay
     * @param endDay
     * @return
     */
    public static int getDays(Date beginDay, Date endDay)
    {
        Calendar calst = Calendar.getInstance();
        calst.setTime(beginDay);

        Calendar caled = Calendar.getInstance();
        caled.setTime(endDay);

        if (caled.before(calst))
        {
            return 0;
        }

        // 用Double计算结果
        double tempDays = (double)(caled.getTime().getTime() - calst.getTime().getTime())/(double)(1000*60*60*24);
        // Double转换为BigDecimal
        BigDecimal dciDays = new BigDecimal(tempDays);
        // 最后做四舍五入，返回int类型
        int days = dciDays.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        
        return days;
    }
    
    /**
     * 根据日期获取当期时间属于周几
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
		w = 0;
			return weekDays[w];
	}
}
