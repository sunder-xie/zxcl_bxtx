package com.zxcl.webapp.dto.wallet.response;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BillBaseRspDTO implements Serializable{
	
	private static final long serialVersionUID = -1877478321021901288L;
	public static final String ACTION_WALLET_CHECK = "1";//审核操作  success{'1':审核失败,'0':'审核通过'}
	public static final String ACTION_WALLET_PAY = "2";//支付操作success{'1':支付失败,'0':'支付成功'}
	
	
	private String action;//动作
	private String success;//成功标识     0成功  -1失败
	private String reason;//失败原因
	private String payOrderNo;//后台支付系统结算单号
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPayOrderNo() {
		return payOrderNo;
	}
	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
