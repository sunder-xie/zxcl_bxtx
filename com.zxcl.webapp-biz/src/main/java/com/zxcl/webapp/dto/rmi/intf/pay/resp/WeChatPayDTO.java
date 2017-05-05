package com.zxcl.webapp.dto.rmi.intf.pay.resp;

import java.io.Serializable;

import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * 小微支付返回
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class WeChatPayDTO extends CommonDTO implements Serializable {
	
	private static final long serialVersionUID = 2390989220538447420L;

	/**
	 * 错误代码
	 */
	private String errorCode;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	/**
	 * 锦泰：回调地址
	 */
	private String url;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "WeChatPayDTO [errorCode=" + errorCode + ", errorMsg="
				+ errorMsg + ", url=" + url + "]";
	}
	
}
