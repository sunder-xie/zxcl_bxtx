package com.zxcl.webapp.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 短信
 * 
 * @author 5555
 *
 */
public class SMSUtils {

	/**
	 * 企业编号
	 */
	private final static String SPCODE = "103909";
	/**
	 * 企业用户名称
	 */
	private final static String LOGINNAME = "sc_zxcl";
	/**
	 * 企业用户密码
	 */
	private final static String PASSWORD = "e3Z5B3";

	/**
	 * 提交时检测方式 1 --- 提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出 不为1 或该参数不存在 ---
	 * 提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出
	 */
	private final static String F = "1";

	/**
	 * 发送的接口
	 */
	private final static String SMSURL = "http://sms.api.ums86.com:8899/sms/Api/Send.do";

	/**
	 * 发送短信 Map<phone,mesContent>
	 * 
	 * @return Map<phone,returnInfo>
	 * @throws Exception
	 * 
	 *  true: 返回成功result的值为0
	 *  result=0&description=发送短信成功&taskid=229115953296&faillist=&task_id=229115953296&validation_code=132321&serial_number =1111111111111 false: 返回成功result的值不为0
	 *  result=6&description=号码中含有无效号码或不在规定的号段或为免打扰号码&faillist=1850281412&validation_code=132321&serial_number=1111111111111
	 */
	public Map<String, String> send(Map<String, String> phoneAndMsgContent)
			throws Exception {
		// 返回的信息
		Map<String, String> returnMap = new HashMap<String, String>();
		// 验证码
		Random rand = new Random();
		Set<String> phones = phoneAndMsgContent.keySet();
		for (String phone : phones) {
			// 生成验证码
			int validationCode = rand.nextInt(900000) + 100000;
			// 转换日期格式,生成流水号
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod(SMSURL);
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
			post.addParameter("SpCode", SPCODE);// 企业编码
			post.addParameter("LoginName", LOGINNAME);// 用户名
			post.addParameter("Password", PASSWORD);// 密码
			post.addParameter("MessageContent",//
					phoneAndMsgContent.get(phone) + //
							"您的验证码为：" + validationCode);// 内容
			post.addParameter("UserNumber", phone);// 发送的手机号码
			String serial_number = fmt.format(new Date());
			post.addParameter("SerialNumber", serial_number);// 流水号
			post.addParameter("ScheduleTime", "");// 预定发送时间，空为立即发送
			post.addParameter("f", F);// 提交时检测方式 1:
										// 提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出
										// 不为1 或该参数不存在 提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出//
			httpclient.executeMethod(post);
			// 对返回的消息做处理
			String info = new String(post.getResponseBody(), "gbk");
			// 在末尾加上生成的验证码,和流水号
			info = info + "&validation_code=" + validationCode
					+ "&serial_number=" + serial_number;
			returnMap.put(phone, info);
		}
		return returnMap;
	}
}
