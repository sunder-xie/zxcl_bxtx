package com.zxcl.webapp.dto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;

/**
 * 平安支付返回
 * @author zxj
 *
 */
public class PAICPayReturnDTO extends PayReturnDTO{
	private static final long serialVersionUID = 1837514020498876932L;

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
