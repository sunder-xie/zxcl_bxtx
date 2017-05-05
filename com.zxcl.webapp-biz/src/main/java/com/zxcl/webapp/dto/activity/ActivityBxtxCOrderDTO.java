package com.zxcl.webapp.dto.activity;

/**
 * 保行天下活动C，中奖名单排序.
 * @author xiaoxi
 *
 */
public class ActivityBxtxCOrderDTO {

	
	private String name;
	
	private String total;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ActivityBxtxCOrderDTO [name=" + name + ", total=" + total + "]";
	}
	
}
