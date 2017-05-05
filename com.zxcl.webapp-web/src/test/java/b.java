import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.zxcl.webapp.biz.util.DateUtil;

/**
 * 续保起止期规则实现
 * @author xiaoxi
 *
 */
public class b {

	public static void main(String[] args) {
		String insuranEndTime = "2017-01-18";
		
		Date endTime = getInsuranStartTime(insuranEndTime);
		System.out.println(DateUtil.dateToString("yyyy-MM-dd", endTime));
	}
	
	/**
	 * 根据保险止期，获取第二年保险起期
	 * @param insuranEndTime
	 * @return
	 */
	private static Date getInsuranStartTime(String insuranEndTimeStr){
		
		DateTime now = DateTime.now();
		
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd");
		DateTime insuranEndTime = DateTime.parse(insuranEndTimeStr, format);
		
		if(Days.daysBetween(now, insuranEndTime).getDays() > 90){
			//止期 - 录入日期 > 90
			
			// 录单日期+1  并返回提示
			return now.plusDays(1).toDate();
			
		}else if(Days.daysBetween(now, insuranEndTime).getDays() <= 90 && 
				Days.daysBetween(now, insuranEndTime).getDays() > -1){
			//止期 - 录入日期 < 90 &&  止期 - 录单日期 > -
			
			// 止期+1
			return insuranEndTime.plusDays(1).toDate();
		}else if(Days.daysBetween(now, insuranEndTime).getDays() < -365 &&
				insuranEndTime.get(DateTimeFieldType.dayOfMonth()) <= now.get(DateTimeFieldType.dayOfMonth())){
			//止期 - 录单日期 < -365 && 止期月 <= 录单日期月
			
			 //上年无保单，并且月份小于录单日期
		     //录单年份+1+止期月日+1
			String returnDateStr = ( now.get(DateTimeFieldType.year()) + 1  ) + "-" + insuranEndTime.get(DateTimeFieldType.monthOfYear())  + "-" + insuranEndTime.get(DateTimeFieldType.dayOfMonth());
			DateTime returnDate = DateTime.parse(returnDateStr, format);
			
			if(Days.daysBetween(now, returnDate).getDays() > 90){
				//止期 - 录入日期 > 90
				
				// 录单日期+1  并返回提示
				return now.plusDays(1).toDate();
				
			}
			
			return returnDate.plusDays(1).toDate();
		}else if(Days.daysBetween(now, insuranEndTime).getDays() < -365 &&
				insuranEndTime.get(DateTimeFieldType.dayOfMonth()) > now.get(DateTimeFieldType.dayOfMonth())){
			 //上年无保单，并且月份大于录入日期月
			
			//录单日期年份+止期月日+1   
			String returnDateStr = now.get(DateTimeFieldType.year())  + "-" + insuranEndTime.get(DateTimeFieldType.monthOfYear())  + "-" + insuranEndTime.get(DateTimeFieldType.dayOfMonth());
			DateTime returnDate = DateTime.parse(returnDateStr, format);
			
			if(Days.daysBetween(now, returnDate).getDays() > 90){
				//止期 - 录入日期 > 90
				
				// 录单日期+1  并返回提示
				return now.plusDays(1).toDate();
				
			}
			
			return returnDate.plusDays(1).toDate();
		}else if(Days.daysBetween(now, insuranEndTime).getDays() >= -365 && Days.daysBetween(now, insuranEndTime).getDays() < 0) {
			//止期 - 录入日期 >= -365
			
			////上年有保单  录单日期+1
			return now.plusDays(1).toDate();
		}
		
		
		return null;
	}
}
