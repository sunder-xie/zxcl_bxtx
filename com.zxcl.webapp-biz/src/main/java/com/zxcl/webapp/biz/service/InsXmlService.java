package com.zxcl.webapp.biz.service;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.InsXmlDTO;

public interface InsXmlService {

	/**
	 * 根据询价单ID和保险公司ID得到返回报文
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	public String[] getBackXml(@Param("inquiryId")String inquiryId,@Param("insId")String insId,String xmlType) throws BusinessServiceException;

	/**
	 * 根据询价单ID和保险公司ID删除该数据
	 * @param inquiryId
	 * @param insId
	 */
	public void delete(String backId) throws BusinessServiceException;

	/**
	 * 根据询价单ID和保险公司ID得到xmlID
	 * @param inquiryId 询价单ID
	 * @param insId 保险公司ID
	 * @param xmlType xml类型
	 * @return
	 */
	public String[] getBackId(@Param("inquiryId")String inquiryId,@Param("insId")String insId,String xmlType) throws BusinessServiceException;

	/**
	 * 将返回的报文插入到数据表中
	 * @param insXmlDTO 报文DTO
	 */
	public void insertResponseXml(InsXmlDTO  insXmlDTO) throws BusinessServiceException;
	
	/**
	 * 根据xml_id 跟新数据库的数据
	 * @param xmlId 报文ID
	 * @param orderId 保单号
	 */
	public void updateXmlOrderId(@Param("xmlId")String xmlId,@Param("orderId")String orderId,String updCde) throws BusinessServiceException;
	
	/**
	 * 根据xml_id 跟新数据库的数据
	 * @param xmlId 报文ID
	 * @param quotaId 报价单号
	 */
	public void updateXmlQuotaId(@Param("xmlId")String xmlId,@Param("quotaId")String quotaId,String updCde) throws BusinessServiceException;

	/**
	 * 插入报文信息
	 * @param insXmlDTO
	 * @throws BusinessServiceException
	 */
	public void insertXml(InsXmlDTO insXmlDTO) throws BusinessServiceException;
	
	/**
	 * 查询报文信息
	 * @param queryXmlDTO
	 * @return
	 */
	public InsXmlDTO getDataByDTO(InsXmlDTO queryXmlDTO) throws BusinessServiceException;

	/**
	 * 根据询价单ID和保险公司ID得到查询报文
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	public String[] getRequestXml(@Param("inquiryId")String inquiryId,@Param("insId")String insId,String xmlType) throws BusinessServiceException;

}
