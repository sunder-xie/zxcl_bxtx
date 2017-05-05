package com.zxcl.webapp.biz.util;

public class ConstantsUtill {
	
	//SMS配置
	public static final String SMS_TB_APPKEY = "23317653";
	public static final String SMS_TB_SECRET = "e6a5d423b30b4af49acebe9f7020c168";
	public static final String SMS_TB_URL = "http://gw.api.taobao.com/router/rest";
	public static final String SMS_TB_TEMPLATE_REGIST = "SMS_5265230";//注册模版
	public static final String SMS_TB_TEMPLATE_ORDER = "SMS_5215182";//配置信息模版
	public static final String SMS_TB_TEMPLATE_PASSPORT = "SMS_5265228";//找回密码模版
	/**
	 * 用户密码错误模板
	 */
	public static final String SMS_TB_TEMPLATE_ALARM = "SMS_5420065";
	
	/**
	 * 暂存
	 */
	public static final String ORDERSTATUS_TEMP = "1";

	/**
	 * 等待人工核保
	 */
	public static final String ORDERSTATUS_AUDITING = "2";

	/**
	 * 核保退回 :人工核保失败和自动核保失败
	 */
	public static final String ORDERSTATUS_AUDITFAIL = "3";

	/**
	 * 核保通过待缴费
	 */
	public static final String ORDERSTATUS_AUDITSUCCESS = "4";

	/**
	 * 缴费成功
	 */
	public static final String ORDERSTATUS_PAYSUCCESS = "5";

	/**
	 * 已经生成保单
	 */
	public static final String ORDERSTATUS_BILLS = "6";

	/**
	 * 订单已经被删除
	 */
	public static final String ORDERSTATUS_DELETED = "7";

	/**
	 * 缴费失败
	 */
	public static final String ORDERSTATUS_PAYFAIL = "8";

	/**
	 * 人工核保支付中
	 */
	public static final String ORDERSTATUS_PAYFAILING_MANU = "9";
	/**
	 * 人工核保已支付
	 */
	public static final String ORDERSTATUS_PAYFAI_MANU = "10";
	/**
	 * 开始配送保单
	 */
	public static final String ORDERSTATUS_DISPATCHING_MANU = "11";
	/**
	 * 收件完成
	 */
	public static final String ORDERSTATUS_DISPATCH_MANU = "12";
	
	/**
	 * 核保中
	 */
	public static final String ORDERSTATUS_UNDERWAY = "13";
	/**
	 * 财务开单
	 */
	public static final String ORDERSTATUS_NOTICEDOBYNPS = "14";

	/**
	 * 微信支付
	 */
	public static final String PAY_WAY_WECHAT = "1";

	/**
	 * 通联
	 */
	public static final String PAY_WAY_ALLIN = "2";
	
	/**
	 * 永诚接口用户名
//	 */
//	public static final String ALLTRUST_USERNAME="test";
//
//	/**
//	 * 永诚接口密码
//	 */
//	public static final String ALLTRUST_PASSWORD="123456";
}
