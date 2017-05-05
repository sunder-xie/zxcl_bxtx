package com.zxcl.webapp.util.constants;

/**
 * @ClassName: 
 * @Description:消息常量池
 * @author zxj
 * @date 
 */
public class MessageConstant {
	/**
	 * 相对于系统：1待处理 
	 */
	public static final Integer TIMER_STATUS_1 = 1;
	
	/**
	 * 相对于系统：2锁定 
	 */
	public static final Integer TIMER_STATUS_2 = 2;
	
	/**
	 * 相对于系统：3处理成功 
	 */
	public static final Integer TIMER_STATUS_3 = 3;
	
	/**
	 * 相对于系统：4处理失败
	 */
	public static final Integer TIMER_STATUS_4 = 4;
	
	
	/**
	 * 相对于用户：0删除 
	 */
	public static final Integer STATUS_0 = 0;
	
	/**
	 * 相对于用户：1处理中
	 */
	public static final Integer STATUS_1 = 1;
	
	/**
	 * 相对于用户：2未读 
	 */
	public static final Integer STATUS_2 = 2;
	
	/**
	 * 相对于用户：3已读
	 */
	public static final Integer STATUS_3 = 3;
	
	
	/**
	 * 消息类型(来源)：0系统消息(一般不用)
	 */
	public static String MESSAGE_TYPE_0 = "0";
	
	/**
	 * 消息类型(来源)：1个人APP消息
	 */
	public static String MESSAGE_TYPE_1 = "1";
	
	/**
	 * 消息类型(来源)： 2个人微信消息
	 */
	public static String MESSAGE_TYPE_2 = "2";
}
