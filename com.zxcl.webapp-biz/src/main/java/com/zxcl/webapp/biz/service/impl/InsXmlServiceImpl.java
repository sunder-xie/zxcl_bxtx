package com.zxcl.webapp.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.dto.InsXmlDTO;
import com.zxcl.webapp.integration.dao.InsXmlDAO;

@Service
public class InsXmlServiceImpl implements InsXmlService{

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private InsXmlDAO insXmlDAO;
	
	@Override
	public String[] getBackXml(String inquiryId, String insId,String xmlType) throws BusinessServiceException{
		logger.info("获取返回报文 入参    询价单号："+inquiryId+"  保险公司ID："+insId+"  xmlType："+xmlType);
		String[] xml = null;
		try {
			xml = insXmlDAO.getBackXml(inquiryId, insId,xmlType);
			if(xml.length > 1){
				logger.info("查出两条数据以上");
				logger.info(getBackId(inquiryId, insId,xmlType));
			}
			logger.info("得到返回报文:"+xml);
		} catch (Exception e) {
			logger.error("查询返回报文失败:" + e,e);
			throw new BusinessServiceException("获取返回报文失败");
		}
		return xml;
	}

	@Override
	public void delete(String backId) throws BusinessServiceException {
		logger.info("删除报文入参    backId："+backId);
		try {
			insXmlDAO.delete(backId);
			logger.info("删除成功");
		} catch (Exception e) {
			logger.error("删除报文失败:" + e,e);
			throw new BusinessServiceException("删除报文失败");
		}
	}

	@Override
	public String[] getBackId(String inquiryId, String insId,String xmlType) throws BusinessServiceException{
		logger.info("得到报文ID入参    询价单号："+inquiryId+" 保险公司ID："+insId+"  xmlType："+xmlType);
		String [] ids = null;
		try {
			ids = insXmlDAO.getBackId(inquiryId, insId,xmlType);
			logger.info("得到ID"+ids);
		} catch (Exception e) {
			logger.error("得到ID失败:" + e,e);
			throw new BusinessServiceException("得到ID失败");
		}
		return ids;
	}

	@Override
	public void insertResponseXml(InsXmlDTO insXmlDTO) throws BusinessServiceException{
		logger.info("添加报文信息 入参    InsXmlDTO："+insXmlDTO);
		try {
			//查询是否存在，如果存在则删除
			String[] ids = getBackId(insXmlDTO.getInquiryId(), insXmlDTO.getInsId(),insXmlDTO.getXmlType());
			for (String id : ids) {
				logger.info("删除多余的数据：ids("+ids+")");
				delete(id);
			}
			insXmlDAO.insertResponseXml(insXmlDTO);
		} catch (Exception e) {
			logger.error("添加失败:" + e,e);
			throw new BusinessServiceException("添加报文信息失败");
		}
	}

	@Override
	public void updateXmlOrderId(String xmlId, String orderId,String updCde) throws BusinessServiceException{
		logger.info("更新报文信息中的订单号 入参    xmlId："+xmlId+"  orderId："+orderId+"  修改人："+updCde);
		try {
			insXmlDAO.updateXmlOrderId(xmlId, orderId,updCde);
		} catch (Exception e) {
			logger.error("更新报文信息中的订单号失败:" + e,e);
			throw new BusinessServiceException("更新报文信息中的订单号失败");
		}
	}

	@Override
	public void updateXmlQuotaId(String xmlId, String quotaId,String updCde) throws BusinessServiceException{
		logger.info("更新报文信息中的报价单号 入参    xmlId："+xmlId+"  报价单ID："+quotaId+"  修改人："+updCde);
		try {
			insXmlDAO.updateXmlQuotaId(xmlId, quotaId,updCde);
		} catch (Exception e) {
			logger.error("更新报文信息中的报价单号失败:" + e,e);
			throw new BusinessServiceException("更新报文信息中的报价单号失败");
		}
	}

	@Override
	public void insertXml(InsXmlDTO insXml) throws BusinessServiceException{
		logger.info("保存报文信息 入参    InsXmlDTO："+insXml);
		if(null!=insXml){
			if(StringUtils.isNotBlank(insXml.getXmlFile())){
				InsXmlDTO queryXmlDTO=new InsXmlDTO();
				queryXmlDTO.setInsId(insXml.getInsId());
				queryXmlDTO.setInquiryId(insXml.getInquiryId());
				queryXmlDTO.setQuotaId(insXml.getQuotaId());
				queryXmlDTO.setOrderId(insXml.getOrderId());
				queryXmlDTO.setXmlType(insXml.getXmlType());
				queryXmlDTO.setRequestOrBack(insXml.getRequestOrBack());
				try {
					List<InsXmlDTO> data = insXmlDAO.getDataByDTO(queryXmlDTO);
					if(null!=data&&data.size()>0){
						for (InsXmlDTO insXmlDTO : data) {							
							insXmlDAO.delete(insXmlDTO.getXmlId());
						}
					}	
				} catch (Exception e) {
					logger.error("查询是否有相同报文失败:"+e);
					throw new BusinessServiceException();
				}
				try {
					insXmlDAO.insertResponseXml(insXml);
				} catch (Exception e) {
					logger.error("插入报文信息失败:"+e,e);
					throw new BusinessServiceException("保存报文信息失败");
				}
			}
		}
	}

	@Override
	public InsXmlDTO getDataByDTO(InsXmlDTO queryXmlDTO) throws BusinessServiceException {
		logger.info("查询报文信息 入参    InsXmlDTO："+queryXmlDTO);
		List<InsXmlDTO> insXmlDTOs = null;
		try {
			insXmlDTOs = insXmlDAO.getDataByDTO(queryXmlDTO);
		} catch (Exception e) {
			logger.error("查询报文信息失败:"+e,e);
			throw new BusinessServiceException("查询报文信息失败");
		}
		if(null!=insXmlDTOs&&insXmlDTOs.size()>0){			
			return insXmlDTOs.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String[] getRequestXml(String inquiryId, String insId, String xmlType) throws BusinessServiceException {
		logger.info("获取查询报文 入参    询价单号："+inquiryId+"  保险公司ID："+insId+"  xmlType："+xmlType);
		String[] xml = null;
		try {
			xml = insXmlDAO.getRequestXml(inquiryId, insId,xmlType);
			if(xml.length > 1){
				logger.info("查出两条数据以上");
				logger.info(getBackId(inquiryId, insId,xmlType));
			}
			logger.info("得到返回报文:"+xml);
		} catch (Exception e) {
			logger.error("查询查询报文失败:" + e,e);
			throw new BusinessServiceException("获取查询报文失败");
		}
		return xml;
	}
	
}
