package com.zxcl.webapp.biz.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

	/**
	 * 从身份证中获取生日
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getBrith(String certNo) throws ParseException {
		if (certNo.length() == 15) {
			if (Integer.valueOf(certNo.substring(6, 8)) > 49) {
				return DateUtil.stringToDate("yyyyMMdd", "19" + certNo.substring(6, 12));
			} else {
				return DateUtil.stringToDate("yyyyMMdd", "20" + certNo.substring(6, 12));
			}
		} else {
			return DateUtil.stringToDate("yyyyMMdd", certNo.substring(6, 14));
		}
	}
	
	/**
	 * 从身份证中获取性别位
	 * 
	 * @return
	 */
	public static int getSex(String certNo) {
		if (certNo.length() == 15) {// 15位的身份证号码
			return Integer.valueOf(certNo.substring(14, certNo.length()));
		} else {// 18位的
			return Integer.valueOf(certNo.substring(16, certNo.length() - 1));
		}
	}
	/**
	 *富德根据身份证 计算性别
	 *1	男 2	女 9	未说明
	 * @param certNo
	 * @return
	 */
	public static int getSexByCerNoWithFDCP(String certNo) {
		if (certNo.length() == 15) {
			return 9;
		} else {
			String sex = certNo.substring(16, 17);
			if(Integer.parseInt(sex)%2==0){
				return 2;
			}else{
				return 1;
			}
		}
	}
	/**
     * 根据初登日期和保险起期计算车龄
     * 
     * @param birthDay
     * @param insStartTime
     * @return
     */
    public static int getYearAge(Date birthDay, Date insStartTime)
    {
        Calendar begin = Calendar.getInstance();
        begin.setTime(birthDay);

        Calendar end = Calendar.getInstance();
        end.setTime(insStartTime);

        if (end.before(begin))
        {
            return 0;
        }
        // 年差
        int ys = end.get(Calendar.YEAR) - begin.get(Calendar.YEAR);
        // 月差
        int ym = (end.get(Calendar.MONTH)+1) - (begin.get(Calendar.MONTH)+1);
        // 年月差
        int md = (ys * 12) + ym;
        // 根据天判断返回的年数
        if(end.get(Calendar.DAY_OF_MONTH) >= begin.get(Calendar.DAY_OF_MONTH))
        {
            // int除法为取整
            return md/12;
        }
        else
        {
            return (md-1)/12;
        }
    }
    
    
    /**
     * 根据两个日期之间相差的年，只按年计算，用于计算车主年龄
     * 
     * @param birthDay
     * @param insStartTime
     * @return
     */
    public static int getBetweenYear(Date birthDay, Date insStartTime)
    {
        Calendar begin = Calendar.getInstance();
        begin.setTime(birthDay);

        Calendar end = Calendar.getInstance();
        end.setTime(insStartTime);

        if (end.before(begin))
        {
            return 0;
        }
        // 年差
        int ys = end.get(Calendar.YEAR) - begin.get(Calendar.YEAR);
        
        return ys;
    }
    
    /**
     * 将对象转换成String类型 
     * @param obj
     * @return
     */
    public static String valueOf(Object obj){
    	return (obj == null) ? "" : obj.toString();
    }
}
