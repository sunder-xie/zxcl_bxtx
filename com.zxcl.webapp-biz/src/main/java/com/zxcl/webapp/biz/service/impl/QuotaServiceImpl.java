package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.ServiceException;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotaDetailedDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.integration.dao.QuotaDAO;
import com.zxcl.webapp.integration.dao.QuotaDetailedDAO;

@Service
public class QuotaServiceImpl implements QuotaService {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private QuotaDAO quotaDao;

	@Autowired
	private QuotaDetailedDAO detailedDao;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void insertDetailed(QuotaDTO quota, QuotaDetailedDTO detaileds)
			throws ServiceException {
		logger.info("插入报价单相关信息 入参    QuotaDTO："+quota+" QuotaDetailedDTO："+detaileds);
		try {
			if (null != quota) {
				logger.info("添加报价单信息");
				quotaDao.insert(quota);
			}
			if (null != detaileds) {
				logger.info("插入报价明细");
				detailedDao.insertDetailed(detaileds);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.error("插入报价单信息失败:" + e,e);
			throw new ServiceException("添加报价单信息有误！！");
		}
	}

	public QuotaDTO organizeQuota(String userId, String inquiryId,
			QuotaReturnDTO quotaReturn, MicroDTO micro, InsuranceDTO insurance)
			throws BusinessServiceException {
		logger.info("组装报价单相关信息 入参    用户ID："+userId+"  询价单ID："+inquiryId+"  QuotaReturnDTO："+quotaReturn+"  MicroDTO："+micro+"  InsuranceDTO："+insurance);
		//组装报价单信息
		QuotaDTO quota = new QuotaDTO();
		quota.setQuotaId(quotaReturn.getQuotaId());
		quota.setInquiry(new InquiryDTO(inquiryId));
		quota.setInsurance(insurance);
		quota.setMicro(micro);
		if (null != quotaReturn.getTotalCost()) {
			quota.setVCIPremTax(quotaReturn.getTotalCost());
		}
		
		quota.setTCIPremTax(quotaReturn.getPremTCITax());
		quota.setVCIPremTax(quotaReturn.getPremVCITax());
		if (quotaReturn.getVehicleTax() != null) {
			quota.setVehicleTax(quotaReturn.getVehicleTax());
		}
		quota.setCrtCode(userId);
		quota.setUpdCode(userId);
		quota.setQuotaType("A");
		if(quotaReturn.getWarns() != null && quotaReturn.getWarns().size() > 0){
			quota.setWarns(JSONObject.toJSONString(quotaReturn.getWarns()));
		}
		return quota;
	}

	@Override
	public QuotaDTO get(QuotaDTO quota) throws BusinessServiceException {
		logger.info("获取报价单信息 入参    QuotaDTO："+quota);
		QuotaDTO quotaDTO = null;
		try {
			quotaDTO = quotaDao.get(quota);
		} catch (Exception e) {
			logger.error("获取报价单:" + quota.getQuotaId() + "的信息失败:" + e,e);
			throw new BusinessServiceException("获取报价单信息失败");
		}
		return quotaDTO;
	}

	// @Override
	// public void insertOrderPay(String quotaId) {
	// try {
	// quotaDao.insertOrderPay(quotaId);
	// } catch (Exception e) {
	// logger.error("价单:" + quota.getQuotaId() + "的信息失败:" + e);
	// }
	// }

	public QuotaDTO getByQuotaId(String quotaid)
			throws BusinessServiceException

	{
		logger.info("根据报价单ID获取报价单信息 入参    报价单号："+quotaid);
		QuotaDTO quotaDTO = null;
		try {
			quotaDTO = quotaDao.getByQuotaId(quotaid);
		} catch (Exception e) {
			logger.error("获取报价单:" + quotaid + "的信息失败:" + e,e);
			throw new BusinessServiceException("根据报价单ID获取报价单信息失败");
		}
		return quotaDTO;
	}

	@Override
	public List<QuotaDTO> getQuotasByMicId(String microId)
			throws BusinessServiceException

	{
		logger.info("根据小微ID获取报价单信息 入参    小微ID："+microId);
		List<QuotaDTO> list = new ArrayList<QuotaDTO>();
		try {
			list = quotaDao.getQuotasByMicId(microId);
		} catch (Exception e) {
			logger.error("获取小薇:" + microId + "所有的报价单信息失败:" + e,e);
			throw new BusinessServiceException("根据小微ID获取报价单信息失败");
		}
		return list;
	}

	@Override
	public List<QuotaDTO> getQuotasByInqueryId(String inquiryId)
			throws BusinessServiceException

	{
		logger.info("根据询价单获取报价单信息 入参    询价单号："+inquiryId);
		List<QuotaDTO> list = new ArrayList<QuotaDTO>();
		try {
			list = quotaDao.getQuotasByInqueryId(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单:" + inquiryId + "所有的报价单信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单获取报价单信息失败");
		}
		return list;
	}
	/**
	 * 
	* @Title: infoViewService
	* @Description: 综合查询显示
	* @param @param inquiryId 询价单号
	* @param @return
	* @param @throws BusinessServiceException
	* @return List<QuotaDTO> 
	* @throws
	 */
	public List<QuotaDTO> infoViewService(String inquiryId)
		throws BusinessServiceException

	{
		logger.info("根据询价单号获取报价单信息 入参    询价单号："+inquiryId);
		List<QuotaDTO> list = new ArrayList<QuotaDTO>();
		try {
			list = quotaDao.infoViewByInquiryIDDAO(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单:" + inquiryId + "所有的报价单信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单号获取报价单信息失败");
		}
		return list;
	}
	@Override
	public void updateQuotaStatusByInquiryId(String status, String inquiryId,String updCde)
			throws BusinessServiceException {
		logger.info("QuotaServiceImpl  updateQuotaStatusByInquiryId");
		logger.info("根据询价单:" + inquiryId + "更新状态为:" + status + "的报价单信息");
		logger.info("入参    状态："+status+"  询价单号："+inquiryId+"  修改人："+updCde);
		try {
			quotaDao.updateQuotaStatusByInquiryId(status, inquiryId,updCde);
		} catch (Exception e) {
			logger.error("根据询价单:" + inquiryId + "更新状态为:" + status + "的报价单信息失败:"
					+ e,e);
			throw new BusinessServiceException("根据询价单:" + inquiryId + "更新状态为:" + status + "的报价单信息失败:");
		}
	}

	/**
	 * 
	* @Title: peopleAaskQuery
	* @Description: 人工报价查询
	* @param @return
	* @param @throws BusinessServiceException
	* @return List<QuotaDTO> 
	* @throws
	 */
	public List<QuotaDTO> peopleAaskQueryService(String microId)
			throws BusinessServiceException {
		logger.info("人工报价查询 入参    小微ID："+microId);
		List<QuotaDTO> list = new ArrayList<QuotaDTO>();
		try {
			list = quotaDao.peopleAaskQueryDAO(microId);
		} catch (Exception e) {
			logger.error("查询所有人工报价单信息失败:" + e,e);
			throw new BusinessServiceException("查询所有人工报价单信息失败");
		}
		return list;
	}

	@Override
	public List<QuotaDTO> getQuotaNoOrderByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("根据询价单号查询所有人工报价单信息 入参    询价单号："+inquiryId);
		List<QuotaDTO> list = new ArrayList<QuotaDTO>();
		try {
			list = quotaDao.getQuotaNoOrderByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("查询所有人工报价单信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单号查询所有人工报价单信息失败");
		}
		return list;
	}

	/**
	 * 
	* @Title: existByInsIdService
	* @Description: 判断有没有该保险公司的报价单
	* @param  inquiryId
	* @param  microId
	* @param  insId
	* @param @return 
	* @param @throws Exception
	* @return List<QuotaDTO>
	* @throws
	 */
	public QuotaDTO existByInsIdService(String inquiryId, String microId,
			String insId) throws BusinessServiceException {
		logger.info("判断有没有该保险公司的报价单 入参    询价单号："+inquiryId+"  小微ID："+microId);
		QuotaDTO quotaDTO = null;
		try {
			quotaDTO = quotaDao.existByInsIdDAO(inquiryId, microId, insId);
		} catch (Exception e) {
			logger.error("判断有没有该保险公司的报价单失败:" + e,e);
			throw new BusinessServiceException("判断有没有该保险公司的报价单失败");
		}
		return quotaDTO;
	}

	@Override
	public Map<String, Integer> quotaCountWithBetweenDate(String userId, Date startDate, Date endDate) throws BusinessServiceException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		int year2 = calendar.get(Calendar.YEAR);
		int days2 = calendar.get(Calendar.DAY_OF_YEAR);
		
		calendar.setTime(startDate);
		int year1 = calendar.get(Calendar.YEAR);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		int chadays = (year2-year1)*366 + (days2-days1);
		Date date = null;
		String dateStr = null;
		List<QuotaDTO> list = null;
		for (int i = 0; i <= chadays; i++) {
			date = calendar.getTime();
			dateStr = DateUtil.dateToString(DateUtil.YYYY_MM_DD, date);
			list = quotaDao.quotaCountWithBetweenDate(userId, DateUtil.dateToString(DateUtils.YYYY_MM_DD_00_00_00, date), DateUtil.dateToString(DateUtils.YYYY_MM_DD_23_59_59, date));
			if(null != list && list.size() >= 2){
				map.put(dateStr, list.size());
			}else{
				map.put(dateStr, 0);
			}
			calendar.add(Calendar.DATE, 1);
		}
		
		
		return map;
	}

	@Override
	public Date getCreateTimeByQuoteId(String quoteId)
			throws BusinessServiceException {
		Date date = null;
		try {
			date = quotaDao.getCreateTimeByQuoteId(quoteId);
		} catch (Exception e) {
			logger.error("根据报价单号获取创建时间失败",e);
			throw new BusinessServiceException("根据报价单号获取创建时间失败");
		}
		return date;
	}

	@Override
	public Integer queryManualQuotationByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("queryManualQuotationByInquiryId   inquiryId："+inquiryId);
		Integer i = 0;
		try {
			i = quotaDao.queryManualQuotationByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("查询改询价单下的有效人工报价单数失败",e);
			throw new BusinessServiceException("查询改询价单下的有效人工报价单数失败");
		}
		return i;
	}

}
