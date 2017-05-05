package com.zxcl.webapp.biz.service;


public interface CarModelRelatedService {

	/**
	 * 根据车型编码A，或B进行查询，两者必须有一个。不能两个同时有
	 * @param modelCodeA
	 * @param modelCodeB
	 * @return 返回关联的编码，如果是用A查询，返回B，反之则返回A
	 */
	public String getCarModelRelated(String modelCodeA,String modelCodeB);
}
