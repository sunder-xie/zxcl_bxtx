package com.zxcl.webapp.biz.service;

/**
 * @ClassName: 
 * @Description:统一公共调用接口 （如保单生成调用此接口）  
 * @author zxj
 * @date 2016年4月14日 10:08:25
 */
public interface CommonCarInsurance {
	/**
	 * 保单已生成
	 * @param insId 保险公司ID （注意：非顶级保险公司）
	 * @param orderId 订单ID(保行天下系统)
	 */
	public void insureSucc(String insId, String orderId);
}
