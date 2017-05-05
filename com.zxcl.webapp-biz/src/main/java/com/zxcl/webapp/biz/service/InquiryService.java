package com.zxcl.webapp.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.ServiceException;
import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.DriverDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.VoInquiryCustomerDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

/**
 * 询价险别
 * 
 * @author 5555
 *
 */
public interface InquiryService {

	/**
	 * 添加询价基础信息
	 * 
	 * @param inquiry
	 * @throws BusinessServiceException
	 */
//	public void insert(InquiryDTO inquiry) throws BusinessServiceException;

	/**
	 * 添加询价单信息：询价基础信息表,询价关联表，询价的险种表，驾驶员信息
	 * 
	 * @param micro
	 *            小薇用户ID
	 * @param inquiry
	 *            询价基础信息
	 * @param insType
	 *            险种
	 * @param driver
	 *            驾驶员信息
	 * @throws ServiceException
	 * @throws BusinessServiceException
	 */
	public void insertInquiry(String userId, InquiryDTO inquiry,
			List<CoverageItemDTO> insTypes, List<DriverDTO> driver)
			throws BusinessServiceException;
	
	/**
	 * 近期录入车牌信息
	 * @param baseParam
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<InquiryDTO> getclosetInquiry(BaseParam baseParam)
			throws BusinessServiceException;
	/**
	 * 获取单个的询价的车辆信息
	 * 
	 * @param id
	 * @return
	 * @throws BusinessServiceException
	 */
	public InquiryDTO getInquiryVehicleByInquiryId(String inquiryId)
			throws BusinessServiceException;

	/**
	 * 组织询价信息
	 * 
	 * @param userId
	 *            登录的Id
	 * @param inquiry
	 *            未组织的询价
	 * @param init
	 * @return
	 * @throws Exception
	 */
	public InquiryDTO organizeInquiry(String userId, InquiryDTO inquiry, VoInquiryCustomerDTO customer) throws BusinessServiceException;

	/**
	 * 
	 * @param userId
	 *            登录的Id
	 * @param inquiry
	 *            已经组织好的询价
	 * @param coverItemsDB
	 *            从数据库查询出来的险种
	 * @return
	 * @throws Exception
	 */
	public List<CoverageItemDTO> organizeInsTypes(String userId,
			InquiryDTO inquiry, List<CoverageItemDTO> coverItemsDB);

	public List<InquiryDTO> getInquiryListByMicroId(
			Map<String, Object> contidion) throws BusinessServiceException;

	/**
	 * 
	 * @Title: infoTempQueryService
	 * @Description: 查询暂存数据
	 * @param microID
	 * @param @throws BusinessServiceException
	 * @return List<InquiryDTO>
	 * @throws
	 */
	public List<InquiryDTO> infoTempQueryService(String microID)
			throws BusinessServiceException;
	/**
	 * 
	 * @Title: infoQueryService
	 * @Description: 综合查询
	 * @param microID
	 * @param @throws BusinessServiceException
	 * @return List<InquiryDTO>
	 * @throws
	 */
	public List<InquiryDTO> infoQueryService(String microID,String queryParameter)
			throws BusinessServiceException;

	public PageBean<InquiryDTO> infoQueryService_v2(PageParam pageParam)
			throws BusinessServiceException;
	/**
	 * 询价单主键查询询价单信息
	 * 
	 * @param inquiryId
	 *            询价单主键
	 * @param microId
	 *            小薇编码
	 * @return
	 * @throws BusinessServiceException
	 */
	public InquiryDTO get(String inquiryId, String microId)
			throws BusinessServiceException;

	/**
	 * 更新询价单信息
	 * 
	 * @param inquiryId
	 * @throws BusinessServiceException
	 * @throws Exception 
	 */
	public void updateInquiryByInquiryId(String userId, InquiryDTO inquiry,
			List<CoverageItemDTO> covers, List<DriverDTO> drivers)
			throws BusinessServiceException, Exception;

	/**
	 * 更新询价单状态
	 */
	public void updateInquiryStatusByInquiryId(String status, String inquiryId,String updCde)
			throws BusinessServiceException;
	
	/**
	 * 更新报价失败保险公司数和失败信息
	 * @param inquiryId 询价单号
	 * @param insQuotaFailCount 报价失败保险公司数
	 * @param insQuotaFailInfo 报价失败信息
	 * @throws Exception
	 */
	public void insertInsQuotaFailInfo(String inquiryId,
			String insId,String reqType, String msg,String msgReplaced,String crtCde,String updCde) throws BusinessServiceException;
	
	/**
	 * 得到报价失败保险公司数
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public InquiryDTO getInsQuotaFailInfo(String inquiryId) throws BusinessServiceException;
	
	/**
	 * 更新重复投保信息
	 * @param reInsureInfo 重复投保信息
	 * @param inquiryId 询价单号
	 * @throws Exception
	 */
	public void updateReInsureInfo(String reInsureInfo,String inquiryId,String updCde) throws BusinessServiceException;
	
	/**
	 * 根据询价单号查询信息
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public InquiryDTO selectByInquiryId(String inquiryId) throws BusinessServiceException;
	
	/**
	 * 根据询价单号修改起保日期
	 * @param inquiryId 询价单号
	 * @param ticStartQuotaDate 交强起保日期
	 * @param ticEndQuotaDate   交强结束日期
	 * @param vicStartQuotaDate 商业起保日期
	 * @param vicEndQuotaDate 商业结束日期
	 * @throws Exception
	 */
	public void updateStartQuotaDate(String inquiryId,String ticStartQuotaDate,String ticEndQuotaDate,String vicStartQuotaDate,String vicEndQuotaDate) throws BusinessServiceException;
	
	/**
	 * 获取询价单的全部信息，包括订单信息
	 * @param inquiryId
	 * @return
	 * @throws BusinessServiceException
	 */
	public InquiryDTO getAllInto(String inquiryId)  throws BusinessServiceException;
}
