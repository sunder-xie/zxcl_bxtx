import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.zxcl.webapp.biz.util.DateUtil;


public class a {

	
	public static void main(String[] args) throws ParseException {
		String time = "2016-01-18 23:59:59"; 
		Date tciInsureEndDate = null;
		String tciDateType = compareMonthAndDay(DateUtil.stringToDate("yyyy-MM-dd", time));
		System.out.println(tciDateType);
		if("0".equals(tciDateType)){
			System.out.println("wfwe");
			String str = (time).substring(4, 10);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			str = calendar.getWeekYear()+str;
			tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", str);
		}else if("1".equals(tciDateType)){
			//保险起期为当前时间的第二天
			System.out.println("11111");
		}else if("2".equals(tciDateType)){//当前年，保险总期的第二天
//			//组装起保日期
//			String str = "";
//			//获取当前时间的年份
//			Calendar cld1 = Calendar.getInstance();
//			cld1.setTime(new Date());
//			str = String.valueOf(cld1.get(Calendar.YEAR))+"-";
//			//获取终保日期的月份和天数
//			Date date = DateUtil.stringToDate("yyyy-MM-dd", time);
//			Calendar cld2 = Calendar.getInstance();
//			cld2.setTime(date);
//			int month = cld2.get(Calendar.MONTH)+1;
//			int day = cld2.get(Calendar.DATE);
//			if(month < 10){//如果月份小于10，在前面加个0
//				str += "0"+month+"-";
//			}else{
//				str += month+"-";
//			}
//			if(day < 10){//如果天数小于10，在前面加个0
//				str += "0"+day;
//			}else{
//				str += day;
//			}
//			
			tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", time);
		}else if("4".equals(tciDateType)){
			String str = (time).substring(4, 10);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, 1);
			str = calendar.getWeekYear()+str;
			tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", str);
		}else{
			System.out.println("大于3个月");
		}
		
		System.out.println(DateUtil.dateToString("yyyy-MM-dd", tciInsureEndDate));
	}
	
	/**
	 * 续保时间判断
	 * @param date
	 * @return 日期状态，0（可以续保且日期为终报日期第二天，时间大于等于当天），1（可以续保，日期为当前日期第二天，小于当前时间且月份和天数不在90天内），
	 * 2（可以续保，日期为终保日期的第二天，小于等于当天且月份和天数在90天内），3（不能续保，时间大于当前时间90天），
	 */
	public static String compareMonthAndDay(Date date){
		String i = "";
		Calendar cld1 = Calendar.getInstance();
		cld1.setTime(new Date());
		cld1.add(Calendar.DATE, 89);
		if(date.after(cld1.getTime())){//时间大于当前时间90天，不能续保
			i = "3";
		}else{
			Calendar cld2 = Calendar.getInstance();
			cld2.setTime(new Date());
			if(date.before(cld2.getTime())){//时间小于当前时间
				Calendar cld3 = Calendar.getInstance();
				cld3.setTime(date);
				//当前时间是这一年的多少天
				int dayOfYear1 = cld2.get(Calendar.DAY_OF_YEAR); 
				cld2.add(Calendar.DATE, 89);
				int dayOfYear3 = cld2.get(Calendar.DAY_OF_YEAR);
				//传入的时间是哪一年的多少天
				int dayOfYear2 = cld3.get(Calendar.DAY_OF_YEAR);
				if(dayOfYear2 >= dayOfYear1 && dayOfYear2 <= (dayOfYear1+89)){//可以续保且日期为终报日期第二天，时间大于等于当天
					i = "0";
				}else if(dayOfYear2 < dayOfYear1 && dayOfYear2 <= dayOfYear3){
					i = "4";
				}else{
					i = "1";
				}
				
			}else{//时间大于等于当前时间，日期为终保日期的第二天
				i = "2";
			}
		}
		return i;
	}
}
