package com.zxcl.webapp.biz.service;

/**
 * @ClassName: 
 * @Description:业务 消息推送
 * @author zxj
 * @date 
 * 
 * 版本修改：4.7.0
 * 将所有接口修改为1个，并且所有消息都直接发送，不再通过定时任务轮询，发送。微信调用失败的，weixin_intf自己去重发。bxtx不再处理.
 * 
 */
public interface MessageSendProvideService {
	
	/**
	 * 提现审核
	 */
	public static final String CASH_WITHDRAWAL_AUDIT = "CASH_WITHDRAWAL_AUDIT";
	
	/**
	 * 提现成功
	 */
	public static final String CASH_WITHDRAWAL_SUCCESS = "CASH_WITHDRAWAL_SUCCESS";
	
	/**
	 * 提现失败
	 */
	public static final String CASH_WITHDRAWAL_FAIL = "CASH_WITHDRAWAL_FAIL";
	
	/**
	 * 投保成功
	 */
	public static final String INSURANCE_SUCCESS = "INSURANCE_SUCCESS";
	
	/**
	 * 投保成功，已生成保单
	 */
	public static final String INSURANCE_POLICY_GENERATED = "INSURANCE_POLICY_GENERATED";
	
	/**
	 * 人工报价，报价员处理后，通知业务员
	 */
	public static final String MQ_QUOTAED_TO_USER = "MQ_QUOTAED_TO_USER";
	
	/**
	 * 发送消息，
	 * @param msgType 消息类型
	 * @param value 消息内容
	 * @return 返回空字符串表示正确
	 */
	public String sendMessage(String msgType,String[] value);
	
	
}
