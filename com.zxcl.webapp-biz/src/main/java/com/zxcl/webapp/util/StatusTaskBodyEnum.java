package com.zxcl.webapp.util;
/**
 * 人工报价枚举工具类
 * @author  daitao
 *任务的状态:任务的状态:1:待处理 2已报价 3确认投保 4待支付 5已支付 6已出单
 */
public enum StatusTaskBodyEnum {
	PENDING("待处理",1),RECEIVED("已报价",2),ALLOCATED("确认投保",3),PAID("待支付",4),ALREADYOUT_OF_ASINGLE("已支付",5),DISTRIBUTION("已出单 ",6);
	private  String name;
	private  int  status;
	/**
	 * 根据数字获取value
	 * @param status
	 * @return
	 */ 

	public static String getValusByStatus(int status){
		for(StatusTaskBodyEnum item : StatusTaskBodyEnum.values()){
			if(item.getStatus() == status){
				return item.getName();
			}
		}
		return null;
	}
	
	private StatusTaskBodyEnum(String name,int status) {
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
