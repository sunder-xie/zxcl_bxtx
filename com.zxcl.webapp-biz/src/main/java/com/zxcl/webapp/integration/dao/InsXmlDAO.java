package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.InsXmlDTO;

/**
 * 报文
 * @author zx
 *
 */
public interface InsXmlDAO {
	/**
	 * 根据询价单ID和保险公司ID得到返回报文
	 * @param inquiryId 询价单号
	 * @param insId 保险公司ID
	 * @return
	 */
	public String[] getBackXml(@Param("inquiryId")String inquiryId,@Param("insId")String insId,@Param("xmlType")String xmlType) throws Exception;
	
	/**
	 * 根据询价单ID和保险公司ID删除该数据
	 * @param inquiryId 询价单号
	 * @param insId 保险公司ID
	 */
	public void delete(String backId) throws Exception;

	/**
	 * 根据询价单ID和保险公司ID得到xmlID
	 * @param inquiryId 询价单号
	 * @param insId 保险公司ID
	 * @return
	 */
	public String[] getBackId(@Param("inquiryId")String inquiryId,@Param("insId")String insId,@Param("xmlType")String xmlType) throws Exception;
	
	/**
	 * 将返回的报文插入到数据表中
	 * @param insXmlDTO 报文DTO
	 */
	public void insertResponseXml(InsXmlDTO  insXmlDTO) throws Exception;
	
	/**
	 * 根据xml_id 跟新数据库的数据
	 * @param xmlId 报文ID
	 * @param orderId 保单号
	 * @param updCde 修改人 
	 */
	public void updateXmlOrderId(@Param("xmlId")String xmlId,@Param("orderId")String orderId,@Param("updCde")String updCde) throws Exception;
	
	/**
	 * 根据xml_id 跟新数据库的数据
	 * @param xmlId 报文ID
	 * @param quotaId 报价单号
	 * @param updCde 修改人 
	 */
	public void updateXmlQuotaId(@Param("xmlId")String xmlId,@Param("quotaId")String quotaId,@Param("updCde")String updCde) throws Exception;

	/**
	 * 查询报文信息
	 * @param queryXmlDTO
	 * @return
	 */
	public List<InsXmlDTO> getDataByDTO(InsXmlDTO queryXmlDTO) throws Exception;

	/**
	 * 根据询价单ID和保险公司ID得到查询报文
	 * @param inquiryId 询价单号
	 * @param insId 保险公司ID
	 * @return
	 */
	public String[] getRequestXml(@Param("inquiryId")String inquiryId,@Param("insId")String insId,@Param("xmlType")String xmlType) throws Exception;

	
	
}
