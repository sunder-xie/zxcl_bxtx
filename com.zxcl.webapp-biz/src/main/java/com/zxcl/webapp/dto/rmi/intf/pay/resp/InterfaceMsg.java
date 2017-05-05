package com.zxcl.webapp.dto.rmi.intf.pay.resp;

import java.io.Serializable;

import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class InterfaceMsg extends CommonDTO implements Serializable{
	
	private static final long serialVersionUID = 7626037698645800880L;

	/**
	 * 报文头格式说明
	 */
	
	//错误代码；参见代码
	private String errorCode;
	
	//错误说明；
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "InterfaceMsg [errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + "]";
	}
}
