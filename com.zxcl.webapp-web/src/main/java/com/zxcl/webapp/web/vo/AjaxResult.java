
package com.zxcl.webapp.web.vo;

/**
 * ajax异步返回的对象
 * @author huangfj
 */
public class AjaxResult {
	/**
	 * 标识是否成功
	 */
	private boolean succ = true;

	/**
	 * 返回的提示消息
	 */
	private String msg;
	
	/**
	 * 消息类型代码
	 */
	private String msgCode;

	/**
	 * 返回的数据
	 */
	private Object data;

	/**
	 * @param data
	 */
	public AjaxResult(Object data) {
		this.data = data;
	}

	/**
	 * @param msg
	 * @param data
	 */
	public AjaxResult(String msg, Object data) {
		this.msg = msg;
		this.data = data;
	}

	/**
	 * @param isSucc
	 */
	public AjaxResult(boolean isSucc) {
		this.succ = isSucc;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	/**
	 * 
	 * @param succ
	 * @param msg
	 */
	public AjaxResult(boolean succ, String msg) {
		super();
		this.succ = succ;
		this.msg = msg;
	}

	/**
	 * 
	 */
	public AjaxResult() {
	}

	/**
	 * @param isSucc
	 * @param msg
	 * @param data
	 */
	public AjaxResult(boolean isSucc, String msg, Object data) {
		this.succ = isSucc;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * @return the succ
	 */
	public boolean isSucc() {
		return succ;
	}

	/**
	 * @param succ
	 *            the succ to set
	 */
	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
