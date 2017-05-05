package com.zxcl.webapp.biz.service.impl;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.OwnerService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.integration.dao.OwnerDAO;
import com.zxcl.webapp.util.StringCodeUtil;

/**
 * 车主
 * 
 * @author 5555
 *
 */
@Service
public class OwnerServiceImpl implements OwnerService {
	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OwnerDAO ownerDao;

	@Autowired
	private AreaService areaService;

	@Override
	public void insert(OwnerDTO owner) throws BusinessServiceException {
		logger.info("插入车主信息 入参    车主信息："+owner);
		try {
			ownerDao.insert(owner);
		} catch (Exception e) {
			logger.error("插入车主信息失败:" + e,e);
			throw new BusinessServiceException("插入车主信息失败");
		}
	}

	@Override
	public OwnerDTO getByOrder(OrderDTO order) throws BusinessServiceException {
		logger.info("根据订单信息获取车主信息 入参    OrderDTO："+order);
		OwnerDTO ownerDTO = null;
		try {
			ownerDTO = ownerDao.getByOrder(order);
		} catch (Exception e) {
			logger.error("根据订单:" + order.getOrderId() + "查询车主信息失败:" + e,e);
			throw new BusinessServiceException("根据订单信息获取车主信息失败");
		}
		return ownerDTO;
	}

	public OwnerDTO organizeOwner(String userId, QuotaDTO quota, OwnerDTO owner)
			throws ParseException, BusinessServiceException {
		logger.info("组装车主信息 入参    用户ID："+userId+" QuotaDTO："+quota+"  OwnerDTO："+owner);
		// 基础信息
		try {
			if(StringUtils.isBlank(userId)){
				String ownerAreaCode = StringUtils.isNotBlank(owner.getOwnerCity()) ? owner.getOwnerCity() : owner.getOwnerProvince();
				String address = areaService.get(ownerAreaCode).getName()+ owner.getOwnerAddress();
				owner.setAddressForPort(address);
			}else{
				owner.setCrtCode(userId);
				owner.setUpdCode(userId);
			}
		} catch (BusinessServiceException e) {
			logger.error("查询地区名称失败:" + e,e);
			throw new BusinessServiceException("查询地区名称失败");
		}
		String ownerCertNo = owner.getOwnerCertNo();
		if (StringCodeUtil.isIdCard(ownerCertNo)) {
			owner.setOwnerSex(CommonUtil.getSex(ownerCertNo) % 2 == 0 ? '2': '1');
			owner.setOwnerBirthday(CommonUtil.getBrith(ownerCertNo));
		}
		if(null!=quota){
			owner.setQuota(quota);
			owner.setInquiry(quota.getInquiry());
			owner.setInsurance(quota.getInsurance());
		}
		return owner;
	}

	@Override
	public OwnerDTO getOwnerByOrderId(String orderId)
			throws BusinessServiceException {
		logger.info("获取车主信息 入参    orderId："+orderId);
		OwnerDTO ownerDTO = null;
		try {
			ownerDTO = ownerDao.getOwnerByOrderId(orderId);
		} catch (Exception e) {
			logger.error("根据订单:" + orderId + "查询车主信息失败:" + e);
			throw new BusinessServiceException("获取车主信息失败");
		}
		return ownerDTO;
	}

}
