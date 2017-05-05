package com.zxcl.webapp.biz.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.zxcl.bxtx.dto.intf.QuoteDTO;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.DriverService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.DriverDTO;
import com.zxcl.webapp.dto.InquiryCustomerDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InquiryFailMsgDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.VoInquiryCustomerDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.integration.dao.CoverageItemDAO;
import com.zxcl.webapp.integration.dao.InquiryCustomerDAO;
import com.zxcl.webapp.integration.dao.InquiryDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;
import com.zxcl.webapp.integration.dao.QuotaDAO;
import com.zxcl.webapp.util.StringCodeUtil;

/**
 * 询价
 * 
 * @author 5555
 *
 */
@Service
public class InquiryServiceImpl implements InquiryService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private InquiryDAO inquiryDao;

	@Autowired
	private DriverService driverService;

	@Autowired
	private MicroDAO microDao;

	@Autowired
	private CorrespondService corrService;

	@Autowired
	private QuotaDAO quotaDAO;

	@Autowired
	private CoverageItemDAO coverDao;

	@Autowired
	private InquiryCustomerDAO custDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
//	@Override
//	public void insert(InquiryDTO inquiry) throws BusinessServiceException {
//		logger.info("插入询价单信息 入参    InquiryDTO："+inquiry);
//		try {
//			inquiryDao.insert(inquiry);
//		} catch (Exception e) {
//			logger.error("插入询价单信息失败:" + e,e);
//			throw new BusinessServiceException("插入询价单信息失败");
//		}
//
//	}

	@Transactional(rollbackFor = { Exception.class })
	public void insertInquiry(String userId, InquiryDTO inquiry,
			List<CoverageItemDTO> covers, List<DriverDTO> drivers)
			throws BusinessServiceException {
		logger.info("用户:" + userId + ",新增询价单:" + inquiry.getInquiryId()+"入参    用户ID："+userId+"  InquiryDTO："+inquiry+"  List<CoverageItemDTO>："+covers+"  List<DriverDTO>："+drivers);
		try {
			MicroDTO microDTO = microDao.getMicroByUserId(userId);
			inquiryDao.insertBasic(inquiry, microDTO);
			inquiryDao.insert(inquiry);
			if (null != covers && covers.size() > 0) {
				List<CoverageItemDTO> orgCovers = this.organizeInsTypes(userId,inquiry, covers);
				coverDao.insert(orgCovers);
			}
			if (null != drivers && drivers.size() > 0) {
				List<DriverDTO> orgDrivers = driverService
						.organizeDriversForInsert(userId,
								inquiry.getInquiryId(), drivers,inquiry.getVciStartDate());
				driverService.insert(orgDrivers);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.error("插入询价单信息失败:" + e,e);
			throw new BusinessServiceException("添加询价单信息失败！！");
		}
	}

	public InquiryDTO organizeInquiry(String userId, InquiryDTO inquiry, VoInquiryCustomerDTO customer) {
		logger.info("组织询价信息 入参    用户ID："+userId+"  InquiryDTO："+inquiry);
		if (null != inquiry.getEngNo())
			inquiry.setEngNo(inquiry.getEngNo().toUpperCase());
		if (null != inquiry.getFrmNo())
			inquiry.setFrmNo(inquiry.getFrmNo().toUpperCase());
		if (null != inquiry.getPlateNo())
			inquiry.setPlateNo(inquiry.getPlateNo().toUpperCase());
		if (null != inquiry.getModelCode())
			inquiry.setModelCode(inquiry.getModelCode().toUpperCase());
		if(StringCodeUtil.isIdCard(inquiry.getOwnerCertNo())){
			inquiry.setOwnerBirthday(DateUtil.dateToString("yyyy-MM-dd", StringCodeUtil.getBrithday(inquiry.getOwnerCertNo())));
			try {
				inquiry.setOwnerAge(String.valueOf(StringCodeUtil.getAge(DateUtil.stringToDate("yyyy-MM-dd", inquiry.getOwnerBirthday()))));
				inquiry.setOwnerSex(String.valueOf(StringCodeUtil.getSex(inquiry.getOwnerCertNo())));
			} catch (ParseException e) {
				logger.error(e);
			}
		}
		inquiry.setCrtCode(userId);
		inquiry.setUpdCode(userId);
		inquiry.setVoInquiryCustomerDTO(customer);
		return inquiry;
	}

	public List<CoverageItemDTO> organizeInsTypes(String userId,
			InquiryDTO inquiry, List<CoverageItemDTO> coverItemsDB) {
		logger.info("组织险种信息 入参    用户ID："+userId+"  InquiryDTO："+inquiry+"  List<CoverageItemDTO>："+coverItemsDB);
		List<CoverageItemDTO> covers = new ArrayList<CoverageItemDTO>();
		for (CoverageItemDTO c : coverItemsDB) {
			CoverageItemDTO cover = new CoverageItemDTO();
			cover.setInquiry(inquiry);
			cover.setCode(c.getCode());
			cover.setNoDduct(c.getNoDduct());
			if (!"".equals(c.getSumLimit()) && null != c.getSumLimit()) {
				cover.setSumLimit(c.getSumLimit());
			}
			if (StringUtils.isNotBlank(c.getGlsType())
					&& StringUtils.isBlank(cover.getRemark())) {
				cover.setRemark(c.getGlsType());
			}
			cover.setCrtCode(userId);
			cover.setUpdCode(userId);
			covers.add(cover);
		}
		return covers;
	}

	@Override
	public InquiryDTO getInquiryVehicleByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("根据询价单号查询询价单号 入参    询价单号："+inquiryId);
		InquiryDTO inquiryDTO = null;
		try {
			inquiryDTO = inquiryDao.getInquiryVehicleByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单号:" + inquiryId + "查询询价单信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单号查询询价单号失败");
		}
		return inquiryDTO;
	}

	@Override
	public List<InquiryDTO> getInquiryListByMicroId(
			Map<String, Object> contidion) throws BusinessServiceException {
		logger.info("查询询价单信息 入参    contidion："+contidion);
		List<InquiryDTO> inquiryList = new ArrayList<InquiryDTO>();
		try {
			inquiryList = inquiryDao
					.getInquiryListByMicroId(contidion);
			for (InquiryDTO inquiryDTO : inquiryList) {
				inquiryDTO.getQuotaList().addAll(quotaDAO.getQuotasByInqueryId(inquiryDTO.getInquiryId()));
				inquiryDTO.getQuotaList().addAll(quotaDAO.getQuotasByInqueryId(inquiryDTO
								.getInquiryId()));
			}
		} catch (Exception e) {
			logger.error("查询询价单信息失败:" + e,e);
			throw new BusinessServiceException("查询询价单信息失败");
		}
		return inquiryList;

	}

	@Override
	public InquiryDTO get(String inquiryId, String microId)
			throws BusinessServiceException {
		logger.info("根据询价单号和小微ID获取询价单信息 入参    询价单号："+inquiryId+"  小微ID："+microId);
		InquiryDTO inquiryDTO = null;
		try {
			inquiryDTO = inquiryDao.get(inquiryId, microId);
		} catch (Exception e) {
			logger.error("根据询价单号:" + inquiryId + "查询询价单信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单号和小微ID获取询价单信息失败");
		}
		return inquiryDTO;
	}


	/**
	 * 
	* @Title: infoTempQueryService
	* @Description: 查询暂存数据
	* @param  microID
	* @param @throws BusinessServiceException
	* @return List<InquiryDTO>
	* @throws
	 */
	public List<InquiryDTO> infoTempQueryService(String microID)
			throws BusinessServiceException {
		logger.info("查询数据暂存 入参    小微ID："+microID);
		List<InquiryDTO> inquiryList = new ArrayList<InquiryDTO>();
		try {
			inquiryList = inquiryDao.
					infoTempQueryDAO(microID);
		} catch (Exception e) {
			logger.error("查询暂存数据失败:" + e,e);
			throw new BusinessServiceException("查询数据暂存失败");
		}
		return inquiryList;

	}
	/**
	 * 
	* @Title: infoQueryService
	* @Description: 综合查询
	* @param  microID
	* @param @throws BusinessServiceException
	* @return List<InquiryDTO>
	* @throws
	 */
	public List<InquiryDTO> infoQueryService(String microID,String queryParameter)
			throws BusinessServiceException {
		logger.info("综合查询数据 入参    小微ID："+microID+"  queryParameter："+queryParameter);
		List<InquiryDTO> inquiryList = new ArrayList<InquiryDTO>();
		try {
			inquiryList = inquiryDao.infoQueryDAO(microID,queryParameter);
		} catch (Exception e) {
			logger.error("查询综合数据失败:" + e,e);
			throw new BusinessServiceException("综合查询数据失败");
		}
		return inquiryList;
	}
	@Override
	public PageBean<InquiryDTO> infoQueryService_v2(PageParam pageParam) throws BusinessServiceException {
		logger.info("综合查询数据 入参    PageParam："+pageParam);
		PageBean<InquiryDTO> page = new PageBean<InquiryDTO>();
		int recordCount = 0;
		List<InquiryDTO> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		
		try {
			recordCount = inquiryDao.infoQueryCount(pageParam);
			dataList = inquiryDao.infoQueryPage(pageParam);
		} catch (Exception e) {
			logger.error("查询综合数据失败:" + e,e);
			throw new BusinessServiceException("综合查询数据失败");
		}
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);

		return page;
	}

	@Override
	public void updateInquiryStatusByInquiryId(String status, String inquiryId,String updCde)
			throws BusinessServiceException {
		logger.info("更新询价单状态 入参    status："+status+"  询价单号："+inquiryId+" 修改人："+updCde);
		try {
			inquiryDao.updateInquiryStatusByInquiryId(status, inquiryId,updCde);
		} catch (Exception e) {
			logger.error("询价单号:" + inquiryId + "更新状态为:" + status + "失败:" + e,e);
			throw new BusinessServiceException("更新询价单状态失败");
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateInquiryByInquiryId(String userId, InquiryDTO inquiry, List<CoverageItemDTO> covers, List<DriverDTO> drivers) throws BusinessServiceException  {
		logger.info("更新询价单信息 入参    用户ID："+userId+"  InquiryDTO："+inquiry+"  List<CoverageItemDTO>："+covers+"  drivers："+drivers);
		try {
			inquiry.setUpdCode(userId);
			inquiry.setUpdTime(new Date());
			try {
				inquiryDao.updateInquiryBasicByInquiryId(inquiry);
			} catch (Exception e) {
				logger.error("用户:"+userId+"更新询价单:"+inquiry.getInquiryId()+"异常:"+e,e);
			}
			inquiryDao.updateInquiryByInquiryId(inquiry);
			
//			if (null != drivers ) {
//				driverService.deleteDriverByInquiryId(inquiry.getInquiryId());
//				if( drivers.size() > 0){
//					List<DriverDTO> orgDrivers = driverService.organizeDriversForInsert(userId,inquiry.getInquiryId(), drivers,inquiry.getVciStartDate());
//					driverService.insert(orgDrivers);
//				}
//			}
			if (null != covers  ) {
				coverDao.deleteCoverageItemsByInquiryId(inquiry.getInquiryId());
				if(covers.size() > 0){
					List<CoverageItemDTO> orgCovers = this.organizeInsTypes(userId, inquiry, covers);
					coverDao.insert(orgCovers);
				}
			}
			VoInquiryCustomerDTO cust = inquiry.getVoInquiryCustomerDTO();
			logger.info("cust="+ToStringBuilder.reflectionToString(cust, ToStringStyle.SHORT_PREFIX_STYLE));
			if(null != cust){
				logger.info("InsureSameAsOwner: "+cust.getInsureSameAsOwner() + "AppSameAsOwner: " + cust.getAppSameAsOwner());
				try {
					
					InquiryCustomerDTO iCust = new InquiryCustomerDTO();
					iCust.setCrtBy(userId);
					iCust.setStatus(1);
					iCust.setUpdBy(userId);
					iCust.setInquiryId(inquiry.getInquiryId());
					if("1".equals(cust.getInsureSameAsOwner()) || "on".equals(cust.getInsureSameAsOwner()) || "checked".equals(cust.getInsureSameAsOwner()) || "true".equals(cust.getInsureSameAsOwner())){
						//被保人同车主
						iCust.setIsVhlOwner(1);
						iCust.setCustomerType(2);
						iCust.setCertfCdeType(inquiry.getCertfCdeType());
						iCust.setCustomerName(inquiry.getOwnerName());
						iCust.setCustomerCardId(inquiry.getOwnerCertNo());
					}
					else{
						//被保人不同车主
						iCust.setIsVhlOwner(0);
						iCust.setCustomerType(2);
						iCust.setCertfCdeType(cust.getInsNoSameAsCertfCdeType());
						iCust.setCustomerName(cust.getInsureNoSameAsOwnerName());
						iCust.setCustomerCardId(cust.getInsureNoSameAsOwnerCard());
					}
					
					custDAO.delByInquiryId(inquiry.getInquiryId());
					if(StringUtils.isNotBlank(iCust.getCustomerName()) && StringUtils.isNotBlank(iCust.getCustomerCardId())){
						custDAO.insertSelective(iCust);
					}
					
					if("1".equals(cust.getAppSameAsOwner()) || "on".equals(cust.getAppSameAsOwner()) || "checked".equals(cust.getAppSameAsOwner()) || "true".equals(cust.getAppSameAsOwner())){
						//投保人同车主
						iCust.setIsVhlOwner(1);
						iCust.setCustomerType(1);
						iCust.setCertfCdeType(inquiry.getCertfCdeType());
						iCust.setCustomerName(inquiry.getOwnerName());
						iCust.setCustomerCardId(inquiry.getOwnerCertNo());
					}
					else{
						//投保人不同车主
						iCust.setIsVhlOwner(0);
						iCust.setCustomerType(1);
						iCust.setCertfCdeType(cust.getAppNoSameAsCertfCdeType());
						iCust.setCustomerName(cust.getAppNoSameAsOwnerName());
						iCust.setCustomerCardId(cust.getAppNoSameAsOwnerCard());
					}
					
					if(StringUtils.isNotBlank(iCust.getCustomerName()) && StringUtils.isNotBlank(iCust.getCustomerCardId())){
						custDAO.insertSelective(iCust);
					}
				} catch (Exception e) {
					logger.error("保存客户信息错误", e);
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("更新询价单信息失败:" + e,e);
			throw new BusinessServiceException("更新询价单信息失败！！");
		}
	}

	private String Date2Str(Date date){
		logger.info("转换时间 入参    date："+date);
		String r = "近期";
		if(date == null){
			return r;
		}
		long base = date.getTime();
		long curr = System.currentTimeMillis();
		long result = Math.abs(curr - base);
		try {
			//一分钟
			if(result < 60000){// 一分钟内  
	            long seconds = result / 1000;  
	            if(seconds == 0){  
	                r = "刚刚";  
	            }else{  
	                r = seconds + "秒前";  
	            }  
	        }else if (result >= 60000 && result < 3600000){// 一小时内  
	            long seconds = result / 60000;  
	            r = seconds + "分钟前";  
	        }else if (result >= 3600000 && result < 86400000){// 一天内  
	            long seconds = result / 3600000;  
	            r = seconds + "小时前";  
	        }else if (result >= 86400000 && result < 2592000000l){// 三十天内  
	            long seconds = result / 86400000;  
	            r = seconds + "天前";  
	        }else if(result >= 2592000000l){
				r = result/2592000000l + "个月前";
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return r;
	}
	@Override
	public List<InquiryDTO> getclosetInquiry(BaseParam baseParam)throws BusinessServiceException {
		logger.info("近期录入车牌信息 入参    BaseParam："+baseParam);
		List<InquiryDTO> list = null;
		try {
			list = inquiryDao.getClosetInquiry(baseParam);
			if(CollectionUtils.isNotEmpty(list)){
				for(InquiryDTO item : list){
					item.setCreateDate(Date2Str(item.getCrtTm()));
				}
			}
		} catch (Exception e) {
			logger.error("近期录入查询失败",e);
			throw new BusinessServiceException("近期录入车牌信息失败");
		}
		return list;
	}

	@Override
	public void insertInsQuotaFailInfo(String inquiryId,
			String insId,String reqType, String msg,String msgReplaced,String crtCde,String updCde)
			throws BusinessServiceException {
		logger.info("添加报价失败保险公司数和失败信息 入参    询价单号："+inquiryId+"  insId：" + insId + " msg:" + msg);
		try {
			
			InquiryFailMsgDTO failMsg = new InquiryFailMsgDTO();
			failMsg.setInquiryId(inquiryId);
			failMsg.setInsId(insId);
			failMsg.setReqType(reqType);
			failMsg.setErrorMassage(msg);
			failMsg.setErrorMsgReplaced(msgReplaced);
			failMsg.setCrtCde(crtCde);
			failMsg.setCrtTm(new Date());
			failMsg.setUpdCde(updCde);
			failMsg.setUpdTm(new Date());
			
			inquiryDao.insertInsQuotaFailInfo(failMsg);
		} catch (Exception e) {
			logger.error("更新报价失败保险公司数和失败信息失败",e);
			throw new BusinessServiceException("更新报价失败保险公司数和失败信息失败");
		}
	}

	@Override
	public InquiryDTO getInsQuotaFailInfo(String inquiryId) throws BusinessServiceException {
		logger.info("近期录入查询 入参    询价单号："+inquiryId);
		InquiryDTO inquiryDTO = null;
		try {
			inquiryDTO = inquiryDao.getInsQuotaFailInfo(inquiryId);
		} catch (Exception e) {
			logger.error("近期录入查询失败",e);
			throw new BusinessServiceException("近期录入查询失败");
		}
		return inquiryDTO;
	}

	@Override
	public void updateReInsureInfo(String reInsureInfo, String inquiryId,String updCde)
			throws BusinessServiceException {
		logger.info("更新重复投保信息 入参    reInsureInfo："+reInsureInfo+"  询价单号："+inquiryId+"  修改人："+updCde);
		try {
			inquiryDao.updateReInsureInfo(reInsureInfo, inquiryId,updCde);
		} catch (Exception e) {
			logger.error("更新重复投保信息失败",e);
			throw new BusinessServiceException("更新重复投保信息失败");
		}
	}

	@Override
	public InquiryDTO selectByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("近期录入查询 入参    询价单号："+inquiryId);
		InquiryDTO inquiryDTO = null;
		try {
			inquiryDTO = inquiryDao.selectByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("近期录入查询失败",e);
			throw new BusinessServiceException("近期录入查询失败");
		}
		return inquiryDTO;
	}

	@Override
	public void updateStartQuotaDate(String inquiryId,
			String ticStartQuotaDate, String ticEndQuotaDate,
			String vicStartQuotaDate, String vicEndQuotaDate)
			throws BusinessServiceException {
		logger.info("InquiryServiceImpl  updateStartQuotaDate   根据询价单号修改起保日期     入参    inquiryId："+inquiryId+"  ticStartQuotaDate："+ticStartQuotaDate+
				"   ticEndQuotaDate："+ticEndQuotaDate+"  vicStartQuotaDate："+vicStartQuotaDate+"   vicEndQuotaDate："+vicEndQuotaDate);
		try {
			inquiryDao.updateStartQuotaDate(inquiryId, ticStartQuotaDate,ticEndQuotaDate, vicStartQuotaDate,vicEndQuotaDate);
		} catch (Exception e) {
			logger.error(" 根据询价单号修改起保日期失败",e);
			throw new BusinessServiceException(" 根据询价单号修改起保日期失败");
		}
	}

	@Override
	public InquiryDTO getAllInto(String inquiryId)
			throws BusinessServiceException {
		logger.info("获取询价单的全部信息，包括订单信息      inquiryId："+inquiryId);
		InquiryDTO inquiryDTO = new InquiryDTO();
		try {
			inquiryDTO = inquiryDao.get(inquiryId, null);
			List<OrderDTO> orderList = orderDAO.peopleAaskDetailDAO(inquiryId);
			List<QuotaDTO> quoteList = quotaDAO.getQuotasByInqueryId(inquiryId);
			if(null != inquiryDTO){				
				inquiryDTO.setOrderList(orderList);
				inquiryDTO.setQuotaList(quoteList);
			}
		} catch (Exception e) {
			logger.error("获取询价单的全部信息失败",e);
			throw new BusinessServiceException("获取询价单的全部信息失败");
		}
		return inquiryDTO;
	}
}
