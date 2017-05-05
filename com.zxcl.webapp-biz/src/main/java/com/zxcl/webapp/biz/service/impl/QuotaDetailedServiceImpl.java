package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.QuotaDetailedService;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotaDetailedDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.integration.dao.QuotaDetailedDAO;

/**
 * 报价明细
 * 
 * @author 5555
 *
 */
@Service
public class QuotaDetailedServiceImpl implements QuotaDetailedService {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private QuotaDetailedDAO detailedDao;

	public QuotaDetailedDTO organizeQuotaDetaileds(String userId, InquiryDTO inquiry,
			InsuranceDTO insurance, QuotaDTO quota, List<CoverageItemDTO> coverItemsDB,
			Map<String, BigDecimal> amounts) throws Exception {
		logger.info("组装报价明细相关信息 入参    用户ID："+userId+"  InquiryDTO："+inquiry+"  InsuranceDTO："+insurance+" QuotaDTO："+quota
				+"  List<CoverageItemDTO>："+coverItemsDB+"  Map<String, BigDecimal>："+amounts);
		QuotaDetailedDTO detailed = new QuotaDetailedDTO();
		detailed.setQuota(quota);
		detailed.setInquiry(inquiry);
		detailed.setInsurance(insurance);
		detailed.setInquiry(inquiry);
		detailed.setUpdCode(userId);
		detailed.setCrtCode(userId);
		for (CoverageItemDTO cover : coverItemsDB) {
			Iterator<String> iterator = amounts.keySet().iterator();
			while (iterator.hasNext()) {
				String code = iterator.next();
				if (code.equals(cover.getCode())) {
					cover.setAmount(amounts.get(code));
				}
			}
		}
		detailed.setCoverageItems(coverItemsDB);
		return detailed;
	}

	@Override
	public QuotaDetailedDTO getDetailed(String quotaId, String insId)
			throws BusinessServiceException {
		logger.info("获取报价详细信息 入参    报价单ID："+quotaId+"  保险公司ID："+insId);
		QuotaDetailedDTO quotaDetailedDTO = null;
		try {
			quotaDetailedDTO = detailedDao.getDetailed(quotaId, insId);
		} catch (Exception e) {
			logger.error("从报价单:" + quotaId + "获取报价单详细信息失败:" + e,e);
			throw new BusinessServiceException("获取报价详细信息失败");
		}
		return quotaDetailedDTO;
	}

	@Override
	public void insertDetailed(QuotaDetailedDTO detaileds) throws BusinessServiceException {
		logger.info("插入报价明细 入参    QuotaDetailedDTO："+detaileds);
		try {
			detailedDao.insertDetailed(detaileds);
		} catch (Exception e) {
			logger.error("插入订单详情失败:" + e,e);
			throw new BusinessServiceException("插入报价明细失败");
		}
	}

	@Override
	public QuotaDetailedDTO organizeQuotaDetaileds(String userId, String inquiryId,
			InsuranceDTO insurance, QuotaDTO quota, QuotaReturnDTO quotaRequest) 
					throws BusinessServiceException{
		logger.info("组装报价明细 入参    用户ID："+userId+"  询价单ID："+inquiryId+"  InsuranceDTO："+insurance+" QuotaDTO："+quota+"  QuotaReturnDTO："+quotaRequest);
		QuotaDetailedDTO detailed = new QuotaDetailedDTO();
		detailed.setQuota(quota);
		detailed.setInquiry(new InquiryDTO(inquiryId));
		detailed.setInsurance(insurance);
		detailed.setUpdCode(userId);
		detailed.setCrtCode(userId);
		List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
		for (CoverageItemDTO returnCover : quotaRequest.getCoverageItems()) {
			CoverageItemDTO cover = new CoverageItemDTO();
			cover.setCode(returnCover.getCode());
			cover.setAmount(returnCover.getAmount());
			cover.setSumLimit(returnCover.getSumLimit());
			coverageItems.add(cover);
		}
		detailed.setCoverageItems(coverageItems);
		return detailed;
	}
}
