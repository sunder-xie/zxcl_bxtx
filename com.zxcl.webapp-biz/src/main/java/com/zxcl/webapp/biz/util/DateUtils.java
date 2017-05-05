package com.zxcl.webapp.biz.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * ���ڸ�ʽת��������
 * 
 * @author ex_zouliyong
 */
public class DateUtils {
    private final static Logger logger = Logger.getLogger(DateUtils.class);

    /*
     * ʱ���ַ��ʽ�����磺HHmmss
     */
    public static String HHMMSS = "HHmmss";

    /*
     * ʱ���ַ��ʽ�����磺HH:mm:ss
     */
    public static String HH_MM_SS = "HH:mm:ss";

    /*
     * ʱ���ַ��ʽ�����磺yyyyMMdd
     */
    public static String YYYYMMDD = "yyyyMMdd";

    /*
     * ʱ���ַ��ʽ�����磺yyyy-MM-dd
     */
    public static String YYYY_MM_DD = "yyyy-MM-dd";

    /*
     * ʱ���ַ��ʽ�����磺yyyy-MM
     */
    public static String YYYY_MM = "yyyy-MM";

    /*
     * ʱ���ַ��ʽ�����磺yyyyMMddHHmmss
     */
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /*
     * ʱ���ַ��ʽ�����磺yyyyMMddHmmssSSSSS
     */
    public static String YYYYMMDDHHMMSSS = "yyyyMMddHmmssSSS";

    /*
     * ʱ���ַ��ʽ�����磺yyyyMMddHHmm
     */
    public static String YYYYMMDDHHMM = "yyyyMMddHHmm";

    /*
     * ʱ���ַ��ʽ�����磺yyyy-MM-dd HH:mm:ss
     */
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /*
     * ʱ���ַ��ʽ�����磺yyyy-MM-dd 00:00:00
     */
    public static String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";
    
    /*
     * yyyy-MM-dd 23:59:59
     */
    public static String YYYY_MM_DD_23_59_59 = "yyyy-MM-dd 23:59:59";

    /**
     * �������������ʱ�����¼
     */
    private static String serviceStartTimestamp = "";
    static {
        try {
            serviceStartTimestamp = DateUtils.getTimestamp();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static String getServiceStartTimestamp() {
        return serviceStartTimestamp;
    }

    /**
     * ����ת����ָ����ʽ���ַ�
     * 
     * @param target
     *            ת��������
     * @param format
     *            ת���ĸ�ʽ
     * @return ���ڸ�ʽ���ַ�
     * @throws Exception
     */
    public static String getDateToStr(Date target, String format) {
        if (target == null) {
            return null;
        }
        if (format == null)
            format = DateUtils.YYYYMMDD;
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(target);
    }

    /***
     * ����ת����ָ����ʽ,ת��֮ǰ��seconds��(ƽ̨������Ҫ)
     * 
     * @param target
     * @param format
     * @param seconds
     * @return
     * @throws Exception
     */
    public static String getDatePlusSecondsToStr(Date target, String format, int seconds) throws Exception {
        if (target == null)
            target = new Date();
        if (format == null)
            format = DateUtils.YYYYMMDD;
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(target.getTime() + seconds * 1000));
    }

    /**
     * �ַ�ת����ָ����ʽ������
     * 
     * @param target
     *            ת��������
     * @param format
     *            ת���ĸ�ʽ
     * @return ���ڸ�ʽ���ַ�
     * @throws ParseException
     * 
     */
    public static Date getStrToDate(String target, String format) throws Exception {
        if (target == null || "".equals(target))
            return null;
        if (format == null)
            format = DateUtils.YYYYMMDD;
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(target);
    }

    /**
     * ��ȡ��ǰϵͳʱ��
     * 
     * @return
     */
    public static Date getCurrentDate() throws Exception {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * ��ȡ�����ʱ������Ӧ��ֵ
     * 
     * @param date
     *            �����ʱ��
     * @param field
     *            ��Ҫ��ȡֵ����
     * @return
     */
    public static int obtainDateFieldValue(Date date, int field) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * �Ƚ���������֮����������
     * 
     * @param startDate
     *            ��ʼ����
     * @param endDate
     *            ��������
     * @return
     */
    public static int getDays(String strTimeStart, String strTimeEnd) throws Exception {
        int betweenDays = 0;
        if (StringUtils.isNotEmpty(strTimeStart) && StringUtils.isNotEmpty(strTimeEnd)) {
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date startDate = formatter.parse(strTimeStart, pos);
            Date endDate = formatter.parse(strTimeEnd, pos1);

            betweenDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        }
        return betweenDays;
    }

    /**
     * �Ƚ���������֮����������
     * 
     * @param startDate
     *            ��ʼ����
     * @param endDate
     *            ��������
     * @return
     */
    public static int getDays(Date startDate, Date endDate) throws Exception {
        int betweenDays = 0;// ��������
        if (startDate != null && endDate != null) {
            long sl = startDate.getTime();
            long el = endDate.getTime();
            long ei = el - sl;
            betweenDays = (int) (ei / (1000 * 60 * 60 * 24));
        }
        return betweenDays;
    }

    /**
     * ��ȡ��ǰ�������ڵ���һ��
     * 
     * @param value
     * @return
     */
    public static Date getNextDay(Date value) {
        return getAfterDays(value, 1);
    }

    /**
     * ��ȡ��ǰ�������ڵ�ǰһ��
     * 
     * @param value
     * @return
     */
    public static Date getPrevDay(Date value) {
        return getAfterDays(value, -1);
    }

    /**
     * ��ȡ��ǰ�������ڵĺ���
     * 
     * @param value
     *            ��������
     * @param days
     *            ����
     * @return
     */
    public static Date getAfterDays(Date value, int days) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(value);
        cl.add(Calendar.DAY_OF_YEAR, days);
        return cl.getTime();
    }

    /**
     * ��������
     * 
     * @param value
     *            ��Ҫ���ӵ�����
     * @param field
     *            ���ӵ���
     * @param count
     *            ��Ҫ���ӵ���
     * @return
     */
    public static Date increaseDate(Date value, int field, int count) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(value);
        cl.add(field, count);
        return cl.getTime();
    }

    /***
     * ����from �� to֮�������·�(���¼���) ע������
     * 
     * @param from
     * @param to
     * @return
     */
    public static int monthDiff(Date from, Date to) {
        Calendar fCal = Calendar.getInstance();
        fCal.setTime(from);
        int fYear = fCal.get(Calendar.YEAR);
        int fMonth = fCal.get(Calendar.MONTH);

        Calendar tCal = Calendar.getInstance();
        tCal.setTime(to);
        int tYear = tCal.get(Calendar.YEAR);
        int tMonth = tCal.get(Calendar.MONTH);
        return (tYear - fYear) * 12 + (tMonth - fMonth);

    }

    /***
     * ����from �� to֮�������·�(���ռ���) ע������
     * 
     * @param from
     * @param to
     * @return
     */
    public static int monthDiffDate(Date from, Date to) {
        Calendar fCal = Calendar.getInstance();
        fCal.setTime(from);
        int fYear = fCal.get(Calendar.YEAR);
        int fMonth = fCal.get(Calendar.MONTH);
        int fDate = fCal.get(Calendar.DATE);
        Calendar tCal = Calendar.getInstance();
        tCal.setTime(to);
        int tYear = tCal.get(Calendar.YEAR);
        int tMonth = tCal.get(Calendar.MONTH);
        int tDate = tCal.get(Calendar.DATE);
        int dateDiff = tDate - fDate;
        if (dateDiff > 0) {
            dateDiff = 1;
        } else {
            dateDiff = 0;
        }
        return (tYear - fYear) * 12 + (tMonth - fMonth) + dateDiff;

    }

    /**
     * ��ȡ�����е���
     * 
     * @param d
     * @return
     */
    public static int getDay(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.DATE);
    }

    /**
     * ��ȡ�����е���
     * 
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.YEAR);
    }

    /**
     * ��ȡ�����е���
     * 
     * @param d
     * @return
     */
    public static int getMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.MONTH);
    }

    /***
     * ��ȡָ�����ڵ������һ��
     * 
     * @param date
     * @return
     */
    public static Date getLastDateOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DATE, 31);
        return cal.getTime();
    }

    /***
     * ��ȡָ�����ڵ����һ��
     * 
     * @param date
     * @return
     */
    public static Date getFirstDateOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * ��ȡ��ǰ�����е���
     * 
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * �ַ�תTimestamp
     * 
     * @param text
     * @return
     * @throws Exception
     */
    public static Timestamp getTimeAsText(String text) throws Exception {
        if (StringUtils.isEmpty(text)) {
            throw new Exception("Ҫת��������ֵ����Ϊ��");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        if (text.length() == 10) {
            sdf.applyPattern(DateUtils.YYYY_MM_DD);
        }
        return new Timestamp(sdf.parse(text).getTime());
    }

    /**
     * ��ȡ�����������
     * 
     * @param date
     * @return
     */
    public static Date nextYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    /**
     * ��ȡ��ǰʱ����ַ�
     * 
     * @return
     * @throws Exception
     */
    public static String getTimestamp() throws Exception {
        Date date = getCurrentDate();
        Timestamp timestamp = new Timestamp(date.getTime());
        return getDateToStr(timestamp, DateUtils.YYYYMMDDHHMMSSS);
    }

    /**
     * �ַ�ת���ɵ������һ�������
     * 
     * @param target
     *            ת�������ڣ�ֻ������£��磺2012-09
     * @return ���ڸ�ʽ���ַ��磺2012-09-30 23:59:59
     * @throws Exception
     * 
     */
    public static String getLastDayByDate(String target) throws Exception {
        if (target == null)
            return null;
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        String[] dateStr = target.split("-");
        if (dateStr != null) {
            if (dateStr[0] != null && dateStr[0].length() == 4 && dateStr[1] != null
                    && (dateStr[1].length() == 1 || dateStr[1].length() == 2)) {
                int year = Integer.parseInt(dateStr[0]);
                int month = Integer.parseInt(dateStr[1]);
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, 1, 0, 0, 0);
                cal.add(Calendar.SECOND, -1);
                return dateFormat.format(cal.getTime());
            }
        }
        return null;
    }

    /**
     * ��ݿ�ʼʱ��ͽ���ʱ�����������,ʹ�ã����㱣�����ޣ����㳵�� 366���365������Ϊһ��
     * ����ֹ������һ�룬��Ҫ��һ���ټ���
     * 
     * @author LiuTiancheng
     * @creaetime 2011-5-11 ����11:14:55
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Double getYearsByBeginAndEndDate(Date beginDate, Date endDate) {
        Double years = -1d;

        if (beginDate != null && endDate != null && endDate.after(beginDate)) {
            // ��ΪͶ��������������Ϊ00:00:00,��2011-12-05
            // 00:00:00,��������Ϊ23:59:59 ��2012-12-04
            // 23:59:59����һ�룬�����Ҫ����1000����
            long intervalTimes = (endDate.getTime() + 1000) - beginDate.getTime();
            long days = intervalTimes / (24 * 60 * 60 * 1000);
            if (days == 365 || days == 366) {
                years = 1d;
            } else {
                years = ((double) days / 365);
            }
        }

        return years;
    }

    public static int getMonths(Date birthDay, Date insStartTime) {

        Calendar begin = Calendar.getInstance();
        begin.setTime(birthDay);
        Calendar end = Calendar.getInstance();
        end.setTime(insStartTime);

        if (end.before(begin)) {
            return 0;
        }
        // 年差
        int ys = end.get(Calendar.YEAR) - begin.get(Calendar.YEAR);
        // 月差
        int ym = (end.get(Calendar.MONTH) + 1) - (begin.get(Calendar.MONTH) + 1);
        // 年月差
        int md = (ys * 12) + ym;
        // 根据天判断返回的年数
        if (end.get(Calendar.DAY_OF_MONTH) >= begin.get(Calendar.DAY_OF_MONTH)) {
            return md;
        } else {
            return md - 1;
        }
    }
    
	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 * @return
	 */
	public static boolean isInDate(Date date, String strDateBegin,
			String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM
					&& strDateS >= strDateBeginS && strDateS <= strDateEndS) {
				return true;
			}
			// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM == strDateEndM && strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
