package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.InsXmlDTO;

/**
 * 
 * @author 5555
 *
 */
public interface ResponseXmlDAO {
	/**
	 *
	 * 
	 * 将返回的报文插入到数据表中
	 */
	
	public void insertResponseXml(InsXmlDTO insXmlDTO) throws Exception;

	/**
	 * 根据xml_id 跟新数据库的数据
	 */
	public void updateXmlOrderId(@Param("xmlId") String xmlId, @Param("orderId") String orderId,@Param("updCde")String updCde) throws Exception;

	/**
	 * 根据xml_id 跟新数据库的数据
	 */
	public void updateXmlQuotaId(@Param("xmlId") String xmlId, @Param("quotaId") String quotaId,@Param("updCde")String updCde) throws Exception;
	/**
	 * 根据询价单ID和保险公司ID得到返回报文
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	public String getBackXml(@Param("inquiryId")String inquiryId,@Param("insId")String insId,@Param("quotaId")String quotaId) throws Exception;
	/**
	 * 获取信息
	 * @param insXml
	 * @return
	 */
	public InsXmlDTO getDataByDTO(InsXmlDTO insXml) throws Exception;

	/**
	 * 删除
	 * @param inquiryId
	 * @param insId
	 */
	public void deleteByXMLID(String xmlId) throws Exception;
	/**
	 * 
	* @Title: getXmlFileDAO
	* @Description: 查询XMLFILE
	* @param  insXmlDTO
	* @param @return
	* @return String
	* @throws
	 */
	public String getXmlFileDAO(InsXmlDTO insXmlDTO);

	
	/**
	 * 
	 * 按时间升序
	* @Title: getXmlFileDAO
	* @Description: 查询XMLFILE
	* @param  insXmlDTO
	* @param @return
	* @return String
	* @throws
	 */
	public String getPAICXmlFile4BDAO(InsXmlDTO insXmlDTO);
}
