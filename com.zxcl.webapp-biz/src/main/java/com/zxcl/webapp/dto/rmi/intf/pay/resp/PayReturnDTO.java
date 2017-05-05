package com.zxcl.webapp.dto.rmi.intf.pay.resp;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * 支付返回
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class PayReturnDTO extends CommonDTO implements Serializable {
	private static final long serialVersionUID = 8697758985026745993L;

	/**
	 * 支付流水号
	 */
	private String tradeNo;
	
	/**
	 * 订单号
	 */
	private String billId;
	
	/**
	 * 总支付金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 支付结果
	 */
	private String payResult;
	
	/**
	 * 缴费地址
	 */
	private String payUrl;
	
	/**
	 * 支付时间
	 */
	private String payTime;
	
	/**
	 * 状态，1为已支付
	 */
	private String type;
	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	private String dataSource = "68";//数据来源 固定“68”
	
	private String customerName;//客户名称
	
	private String phoneNo;//手机号码
	
	private String businessNo;//通知单号  财务开单接口返回
	
	private String currencyNo = "RMB";//币种  固定“RMB”
	
	private String amount ;//支付金额
	
	private String callBackUrl;//支付成功前台回调地址
	
	private String productName = "汽车保险";//产品名称  固定“汽车保险”
	
	private String signMsg;//加密串
	
	private String returnUrl;//支付首页返回按钮地址

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSignMsg() {
		String mingwenStr = dataSource + customerName + businessNo + currencyNo + amount + "OPENAPI";
		signMsg = new MD5Util().md5Hex(mingwenStr);
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "PayReturnDTO [tradeNo=" + tradeNo + ", billId=" + billId
				+ ", payAmount=" + payAmount + ", payResult=" + payResult
				+ ", payUrl=" + payUrl + ", payTime=" + payTime + "]";
	}
	public class MD5Util {
	    private Log log = LogFactory.getLog(MD5Util.class);
	    private final String HEX_CHARS = "0123456789abcdef";
	    private MD5Util() {}

	   private MessageDigest getDigest() {
		   try {
	           return MessageDigest.getInstance("MD5");
	       } catch (NoSuchAlgorithmException e) {
	           throw new RuntimeException(e);
	       }
	    }

	    public byte[] md5(byte[] data) {
	        return getDigest().digest(data);
	    }

	    public byte[] md5(String data) {
	    	byte[] bytes = null;
	        try {
	        	bytes = md5(data.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(),e);
			}
			return bytes;
	    }

	    public String md5Hex(byte[] data) {
	        return toHexString(md5(data));
	    }

	    public String md5Hex(String data) {
	        return toHexString(md5(data));
	    }
	    
	    private String toHexString(byte[] b) {
	        StringBuffer stringbuffer = new StringBuffer();
	        for (int i = 0; i < b.length; i++) {
	        	stringbuffer.append(HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
	        	stringbuffer.append(HEX_CHARS.charAt(b[i] & 0x0F));
	        }
	        return stringbuffer.toString();
	    }
	}
}
