package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.InsXmlDTO;

/**
 * 
 * @author 5555
 *
 */
public interface JTICResponseXmlDAO {
	/**
	 *
	 * 
	 * 将返回的报文插入到数据表中
	 */

	public void insertResponseXml(InsXmlDTO  insXmlDTO) throws Exception;
	
	/**
	 * 根据xml_id 跟新数据库的数据
	 */
		public void updateXmlOrderId(@Param("xmlId")String xmlId,@Param("orderId")String orderId) throws Exception;
		
		/**
		 * 根据xml_id 跟新数据库的数据
		 */
		public void updateXmlQuotaId(@Param("xmlId")String xmlId,@Param("quotaId")String quotaId) throws Exception;
}
