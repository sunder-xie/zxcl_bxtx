package com.zxcl.webapp.biz.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.zxcl.webapp.biz.exception.SMSTBException;

/**
 * @author zxj
 *
 */
@Component
@Controller("SMSTBUtilss")
@RequestMapping("/SMSTBUtilss")
public class SMSTBUtils{
	
	public Logger logger = Logger.getLogger(this.getClass());
	public static void main(String[] args) throws ApiException {
		try {
			new SMSTBUtils().sendSMSTBCode("15508119627","4343");
		} catch (SMSTBException e) {
			System.out.println(e);
		}
//		Map<String,String> map = new HashMap<String,String>();
//		//车主姓名
//		map.put("name", "祥哥");
//		//车牌
//		map.put("plateNo", "川A8888");
//		//保险公司
//		map.put("company", "锦泰保险");
//		//配送时间，如果为空，则默认为及时配送
//		map.put("sendScope", "及时"+"打印");
//		//联系电话
//		map.put("phone", "13281253775");
//		//配送地址
//		map.put("address", "环球中心11楼");
//		SMSTBUtils s = new SMSTBUtils();
//		String [] str = new String[]{"15114073417","18380305819"};
//		System.out.println(s.toString(str, ","));
//		try {
//			boolean result = s.sendSMSTBCode(s.toString(str, ","),map);
//			System.out.println(result);
//		} catch (SMSTBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	//手机号验证码前缀
	public static final String USERSMSPRECODE = "SMSCODE";
	
	//图片验证码前缀
	public static final String USERRANDOMCODE = "RANDOMCODE";
	
	//手机号验证码时间戳记录前缀
	public static final String USERSMSPRECODETAMP = "SMSCODE_TAMP";
	
	/**
	 * 发送验证码
	 * @param request
	 * @param phoneNo
	 * @param pcode   这个是图片验证码  
	 * @throws SMSTBException
	 */
	public void sendSMSTB(HttpServletRequest request, String phoneNo, String pcode) throws SMSTBException{
		if(StringUtils.isBlank(pcode)){
			throw new SMSTBException("请输入图片验证码");
		}
		if(!pcode.equalsIgnoreCase((String) request.getSession().getAttribute(USERRANDOMCODE))){
			throw new SMSTBException("图片验证码错误");
		}
		
		//一分钟只能发送一次手机验证码
		long now = System.currentTimeMillis();
		String signTmp = (String) request.getSession().getAttribute(USERSMSPRECODETAMP);
		if(null != signTmp){
			long pre = Long.parseLong(signTmp);
			long tmp = now - pre;
			if(tmp < 60000){
				throw new SMSTBException("手机验证码发送频繁，"+(tmp/1000)+"秒后可再次发送");
			}
		}
		//发送手机验证码
		String code = randomNumberCode(4);//这里发几个字符可以配置
		sendSMSTBCode(phoneNo, code);
		request.getSession().setAttribute(USERSMSPRECODE, phoneNo+"_"+code);
		request.getSession().setAttribute(USERSMSPRECODETAMP, now+"");
	}
	
	/**
	 * 验证用户输入的手机验证码
	 * @param request
	 * @param code
	 * @return
	 */
	public boolean checkSMSCode(HttpServletRequest request,String phoneNo, String code){
		if(StringUtils.isBlank(code) || StringUtils.isBlank(phoneNo)){
			return false;
		}
		if((phoneNo+"_"+code).equalsIgnoreCase((String) request.getSession().getAttribute(USERSMSPRECODE))){
		    request.getSession().removeAttribute(USERSMSPRECODE);
			return true;
		}
		return false;
	}
	
	
	//基本随机字符  这里去除了用户容易输入错误的字符
	private static final String[] CODES = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "Y", "Z", "K", "M", "N", "S", "T" };
	private static final String[] NUMBERS = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private static Random random = new Random();
	private static String randomNumberCode(int count) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i <count; i++) {
			stringBuilder.append(NUMBERS[random.nextInt(NUMBERS.length)]);
		}
		return stringBuilder.toString();
	}
	
	public static String randomCode(int count) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i <count; i++) {
			stringBuilder.append(CODES[random.nextInt(CODES.length)]);
		}
		return stringBuilder.toString();
	}
	/**
	 * 发送注册短信验证码
	 * @param phoneNo 接收验证码的手机号
	 * @param code 验证码
	 * @return
	 */
	private boolean sendSMSTBCode(String phoneNo, String code) throws SMSTBException{
		boolean smsServiceFlag = true;  //默认打开服务
		if(!smsServiceFlag){
			throw new SMSTBException("手机短信发送服务未打开");
		}
		if(null == phoneNo || !phoneNo.matches("^(13|14|15|18)\\d{9}$")){
			throw new SMSTBException("手机号格式不正确");
		}
		if(StringUtils.isBlank(code)){
			throw new SMSTBException("手机验证码不能为空");
		}
			
		boolean result = false;
		
		//这些参数暂时写死
		String product = "保行天下";//应用名称--固定
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey = "23299376";
		String secret = "b59fafd440f5503a1cff309f5d236787";
		
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("注册验证");
		req.setSmsParam("{\"code\":\"" + code + "\",\"product\":\"" + product + "\"}");
		req.setRecNum(phoneNo);
		req.setSmsTemplateCode("SMS_4480973");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			BizResult s = rsp.getResult();
			logger.info(s.getSuccess()+"--");
			if(Boolean.TRUE == (s.getSuccess())){//成功
				result = true;
				logger.info("验证码短信发送成功：" + phoneNo + ";验证码：" + code+";errCode:"+s.getErrCode()+";msg="+s.getMsg());
			}else{
				logger.info("验证码短信发送失败：" + phoneNo + ";验证码：" + code+";errCode:"+s.getErrCode()+";msg="+s.getMsg());
				throw new SMSTBException("手机验证码发送失败");
			}
		} catch (ApiException e) {
			logger.error("验证码短信发送失败：" + phoneNo + ";验证码：" + code +"错误信息:"+e);
			throw new SMSTBException("验证码短信发送失败");
		}
		return result;
	}
	
	public boolean sendSMSTBCode(String phoneNo,Map<String,String> map,String smsTemplateCode)  throws SMSTBException{
		boolean smsServiceFlag = true;  //默认打开服务
		//验证是否打开服务
		if(!smsServiceFlag){
			throw new SMSTBException("手机短信发送服务未打开");
		}
		//验证手机号码格式是否错误
		if(null == phoneNo){
			throw new SMSTBException("手机号不能为空");
		}else{
			String [] phones = phoneNo.split(",");
			for (String phone : phones) {
				if(!phone.matches("^(13|14|15|18)\\d{9}$")){
					throw new SMSTBException("手机号格式不正确");
				}
			}
		}
		//默认返回结果为失败	
		boolean result = false;
		//这些参数暂时写死
		String url = ConstantsUtill.SMS_TB_URL;
		String appkey = ConstantsUtill.SMS_TB_APPKEY;
		String secret = ConstantsUtill.SMS_TB_SECRET;
		
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		//短信类型,默认为normal
		req.setSmsType("normal");
		//短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。
		req.setSmsFreeSignName("保行天下");
		//短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
		logger.info("参数为"+"{"+mapToString(map)+"}");
		req.setSmsParam("{"+mapToString(map)+"}");
		//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
		req.setRecNum(phoneNo);
		//短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。
		req.setSmsTemplateCode(smsTemplateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			BizResult s = rsp.getResult();
			logger.info(s.getSuccess()+"--");
			if(Boolean.TRUE == (s.getSuccess())){//成功
				result = true;
				logger.info("验证码短信发送成功：" + phoneNo + ";errCode:"+s.getErrCode()+";msg="+s.getMsg());
			}else{
				logger.info("验证码短信发送失败：" + phoneNo + ";errCode:"+s.getErrCode()+";msg="+s.getMsg());
				throw new SMSTBException("手机验证码发送失败");
			}
		} catch (ApiException e) {
			logger.error("验证码短信发送失败：" + phoneNo + "错误信息:"+e);
			throw new SMSTBException("验证码短信发送失败");
		}
		return result;
	}
	
	public static String mapToString(Map<String,String> map){
		String str = "";
		int i = 0;
		for(Entry<String, String> entry: map.entrySet()) { 
			i++;
			str += "\""+entry.getKey()+"\":\""+entry.getValue()+"\"";
			if(i < map.size()){//如果不是最后一个参数，则用‘，’号隔开
				str += ",";	
			}
		}
		return str;
	}
}
