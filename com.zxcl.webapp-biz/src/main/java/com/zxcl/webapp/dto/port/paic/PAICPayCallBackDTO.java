package com.zxcl.webapp.dto.port.paic;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 * H5移动版支付callBackUrl回调参数
 * @author zxj
 *
 */
public class PAICPayCallBackDTO{
	private String businessNo;// 业务号通知单（非空）
	
	private String documentNo;//投保单号/支付申请单号  投保单(业务系统未传递则为空)
	
	private String bankOrderNo;//订单号/银行流水  订单号（非空）
	
	private String paymentSum;//支付金额  金额，（两位小数）0.00格式（非空）
	
	private String remark;//备注
	
	private String paymentDate ;//支付时间  格式：2013-10-11 11:43:57（非空）
	
	private String paymentState;//支付结果  1-成功，2-失败（非空）
	
	private String errorMsg;//交易结果信息/失败描述  结果，使用urldecode进行decode
	
	private String signMsg;//加密串  md5签名串

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getPaymentSum() {
		return paymentSum;
	}

	public void setPaymentSum(String paymentSum) {
		this.paymentSum = paymentSum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	
	
}
