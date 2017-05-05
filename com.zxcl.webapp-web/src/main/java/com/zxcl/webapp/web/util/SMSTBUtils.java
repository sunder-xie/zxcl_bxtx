package com.zxcl.webapp.web.util;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.zxcl.webapp.biz.exception.SMSTBException;
import com.zxcl.webapp.biz.util.ConstantsUtill;

/**
 * @author zxj
 *
 */
@Component
public class SMSTBUtils{
	
	public static Logger logger = Logger.getLogger(SMSTBUtils.class);
	public static void main(String[] args) throws ApiException {
		try {
			new SMSTBUtils().sendSMSTBCode("15884575663","4343", ConstantsUtill.SMS_TB_TEMPLATE_REGIST);
		} catch (SMSTBException e) {
			logger.error(e);
		}
	}
	
	
	//注册短信模板编号：SMS_5265230
	//生成保单时的短信通知模板：SMS_5215182
	//找回密码短信通知模板：SMS_5265228
	
	
	
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
	public void sendSMSTB(HttpServletRequest request, String phoneNo, /*String pcode,*/ String smsTemp) throws SMSTBException{
//		if(StringUtils.isBlank(pcode)){
//			throw new SMSTBException("请输入图片验证码");
//		}
//		if(!pcode.equalsIgnoreCase((String) request.getSession().getAttribute(USERRANDOMCODE))){
//			throw new SMSTBException("图片验证码错误");
//		}
		
		//一分钟只能发送一次手机验证码
		long now = System.currentTimeMillis();
		String signTmp = (String) request.getSession().getAttribute(USERSMSPRECODETAMP);
		if(null != signTmp){
			long pre = Long.parseLong(signTmp);
			long tmp = now - pre;
			if(tmp < 60000){
				throw new SMSTBException("手机验证码发送频繁，"+(60 - (tmp/1000))+"秒后可再次发送");
			}
		}
		//发送手机验证码
		String code = randomNumberCode(4);//这里发几个字符可以配置
		sendSMSTBCode(phoneNo, code, smsTemp);
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
//		    request.getSession().removeAttribute(USERSMSPRECODE);
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
	 * @param smsTemp 模板
	 * @return
	 */
	private boolean sendSMSTBCode(String phoneNo, String code, String smsTemp) throws SMSTBException{
		boolean smsServiceFlag = true;  //默认打开服务
		if(!smsServiceFlag){
			throw new SMSTBException("手机短信发送服务未打开");
		}
		if(null == phoneNo || !phoneNo.matches("^(13|14|15|17|18)\\d{9}$")){
			throw new SMSTBException("手机号格式不正确");
		}
		if(StringUtils.isBlank(code)){
			throw new SMSTBException("手机验证码不能为空");
		}
			
		boolean result = false;
		
		//这些参数暂时写死
		String product = "保行天下";//应用名称--固定
		String url = ConstantsUtill.SMS_TB_URL;
//		String appkey = "23299376";
//		String secret = "b59fafd440f5503a1cff309f5d236787";
		
		String appkey = ConstantsUtill.SMS_TB_APPKEY;
		String secret = ConstantsUtill.SMS_TB_SECRET;
		
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("注册验证");
		req.setSmsParam("{\"code\":\"" + code + "\",\"product\":\"" + product + "\"}");
		req.setRecNum(phoneNo);
//		req.setSmsTemplateCode("SMS_4480973");
		req.setSmsTemplateCode(null == smsTemp?ConstantsUtill.SMS_TB_TEMPLATE_REGIST:smsTemp);
		
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			BizResult s = rsp.getResult();
			
			if(null != s && Boolean.TRUE == (s.getSuccess())){//成功
				logger.info(s.getSuccess());
				result = true;
				logger.info("验证码短信发送成功：" + phoneNo + ";验证码：" + code);
			}else{
				logger.error("验证码短信发送失败：" + phoneNo + ";验证码：" + code+";error:"+(null != s?s.toString():null));
				throw new SMSTBException("手机验证码发送失败");
			}
		} catch (ApiException e) {
			logger.error("验证码短信发送失败：" + phoneNo + ";验证码：" + code +"错误信息:"+e);
			throw new SMSTBException("验证码短信发送失败");
		}
		return result;
	}
}
