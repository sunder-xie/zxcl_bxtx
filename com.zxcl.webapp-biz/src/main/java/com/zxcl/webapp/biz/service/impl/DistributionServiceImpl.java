package com.zxcl.webapp.biz.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.DistributionService;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.integration.dao.DistributionDAO;

@Service
public class DistributionServiceImpl implements DistributionService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DistributionDAO disDao;

	@Autowired
	private AreaService areaService;

	@Override
	public void insert(DistributionDTO dis) throws BusinessServiceException {
		logger.info("插入配送信息入参    DistributionDTO："+dis);
		try {
			disDao.insert(dis);
		} catch (Exception e) {
			logger.error("插入配送信息失败:" + e,e);
			throw new BusinessServiceException("插入配送信息失败");
		}
	}

	@Override
	public DistributionDTO getByOrder(OrderDTO order) throws BusinessServiceException {
		logger.info("根据订单号查询出订单配送信息 入参    OrderDTO："+order);
		DistributionDTO distributionDTO = null;
		try {
			distributionDTO = disDao.getByOrder(order);
		} catch (Exception e) {
			logger.error("获取订单:" + order.getOrderId() + "配送信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号查询出订单配送信息失败");
		}
		return distributionDTO;
	}

	@Override
	public DistributionDTO organizeDistribution(String userId, QuotaDTO quota, DistributionDTO dis)
			throws BusinessServiceException {
		logger.info("组装被保人信息 入参    用户ID："+userId+"  QuotaDTO："+quota+"  DistributionDTO："+dis);
		try {
			if(StringUtils.isBlank(userId)){
				String disAreaCode = StringUtils.isNotBlank(dis.getDisCity()) ? dis.getDisCity() : dis.getDisProvince();
				String disAddress = areaService.get(disAreaCode).getName() + dis.getDisAddress();
				dis.setDisAddress(disAddress);
			}else{
				dis.setCrtCode(userId);
				dis.setUpdCode(userId);
			}
			if(null!=quota){
				dis.setQuota(quota);
				dis.setInquiry(quota.getInquiry());
				dis.setInsurance(quota.getInsurance());
			}
			dis.setDispatchTime(new Date());
			dis.setStatus('1');
			dis.setDispatchType(dis.getDispatchType());
		} catch (BusinessServiceException e) {
			logger.error("组织配送信息时获取地区信息失败:" + e,e);
			throw new BusinessServiceException("组装被保人信息失败");
		}
		return dis;

	}

	@Override
	public DistributionDTO getDistributionByOrderId(String orderId)
			throws BusinessServiceException {
		logger.info("根据订单号获取配送信息 入参    orderId："+orderId);
		DistributionDTO distributionDTO = null;
		try {
			distributionDTO = disDao.getDistributionByOrderId(orderId);
		} catch (Exception e) {
			logger.error("获取订单:" +orderId + "配送信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号获取配送信息失败");
		}
		return distributionDTO;
	}
}
