package com.zxcl.webapp.biz.util.cnums;

/**
 * 人工报价枚举工具类
 * @author  daitao
 *任务的状态:1:待处理 2已支付待确认 3已分配 4已支付 5已出单 6已配送 7完成
 */
public enum StatusTaskEnum {
	PENDING("待处理",1),RECEIVED("已支付待确认",2),ALLOCATED("已分配",3),PAID("已支付",4),ALREADYOUT_OF_ASINGLE("已出单",5),DISTRIBUTION("已配送 ",6),COMPLETE("完成",7);
	private  String name;
	private  int  status;
	/**
	 * 根据数字获取value
	 * @param status
	 * @return
	 */

	public static String getValusByStatus(int status){
		for(StatusTaskEnum item : StatusTaskEnum.values()){
			if(item.getStatus() == status){
				return item.getName();
			}
		}
		return null;
	}
	
	private StatusTaskEnum(String name,int status) {
		this.name = name;
		this.status = status;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	
    
}
