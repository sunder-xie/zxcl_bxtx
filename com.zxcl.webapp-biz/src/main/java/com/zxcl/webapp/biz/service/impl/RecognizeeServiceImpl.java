package com.zxcl.webapp.biz.service.impl;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.RecognizeeService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.integration.dao.RecognizeeDAO;
import com.zxcl.webapp.util.StringCodeUtil;

/**
 * 被保人
 * 
 * @author 5555
 *
 */
@Service
public class RecognizeeServiceImpl implements RecognizeeService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private RecognizeeDAO recDao;

	@Autowired
	private AreaService areaService;

	@Override
	public void insert(RecognizeeDTO rec) throws BusinessServiceException {
		logger.info("添加被保人信息 入参    RecognizeeDTO："+rec);
		try {
			recDao.insert(rec);
		} catch (Exception e) {
			logger.error("插入被保人信息失败:" + e,e);
			throw new BusinessServiceException("添加被保人信息失败");
		}
	}

	@Override
	public RecognizeeDTO getByOrder(OrderDTO order)
			throws BusinessServiceException {
		logger.info("根据订单信息获取被保人 入参    OrderDTO："+order);
		RecognizeeDTO recognizeeDTO = null;
		try {
			recognizeeDTO = recDao.getByOrder(order);
		} catch (Exception e) {
			logger.error("获取被保人信息失败:" + e,e);
			throw new BusinessServiceException("根据订单信息获取被保人失败");
		}
		return recognizeeDTO;
	}

	@Override
	public RecognizeeDTO organizeRecognizee(String userId, QuotaDTO quota,
			RecognizeeDTO rec) throws ParseException, BusinessServiceException {
		logger.info("组装被保人信息 入参    用户ID："+userId+" QuotaDTO："+quota+" RecognizeeDTO："+rec);
		try {
			if(StringUtils.isBlank(userId)){
				String recAreaCode = StringUtils.isNotBlank(rec.getRecCity()) ? rec.getRecCity() : rec.getRecProvince();
				String address = areaService.get(recAreaCode).getName()+ rec.getRecAddress();
				rec.setAddressForPort(address);
			}else{
				rec.setCrtCode(userId);
				rec.setUpdCode(userId);
			}
		} catch (BusinessServiceException e) {
			logger.error("组织被被人信息获取城市信息失败:" + e,e);
			throw new BusinessServiceException("组装被保人信息失败");
		}
		String recCertNo = rec.getRecCertNo();
		if(StringCodeUtil.isIdCard(recCertNo)){
			rec.setRecSex(CommonUtil.getSex(recCertNo) % 2 == 0 ? '2' : '1');
			rec.setRecBirthday(CommonUtil.getBrith(recCertNo));
		}
		if(null!=quota){
			rec.setQuota(quota);
			rec.setInquiry(quota.getInquiry());
			rec.setInsurance(quota.getInsurance());
		}
		return rec;
	}

	@Override
	public RecognizeeDTO getRecognizeeByOrderId(String orderId)
			throws BusinessServiceException {
		logger.info("根据订单号获取被保人信息 入参    订单号："+orderId);
		RecognizeeDTO recognizeeDTO = null;
		try {
			recognizeeDTO = recDao.getRecognizeeByOrderId(orderId);
		} catch (Exception e) {
			logger.error("获取订单:" + orderId + "被保人信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号获取被保人信息失败");
		}
		return recognizeeDTO;
	}
}
