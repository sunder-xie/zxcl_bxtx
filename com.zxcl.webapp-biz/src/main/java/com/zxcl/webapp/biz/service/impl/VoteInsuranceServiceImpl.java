package com.zxcl.webapp.biz.service.impl;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.VoteInsuranceService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.integration.dao.VoteInsuranceDAO;
import com.zxcl.webapp.util.StringCodeUtil;

/**
 * 投保人
 * 
 * @author 5555
 *
 */
@Service
public class VoteInsuranceServiceImpl implements VoteInsuranceService {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private VoteInsuranceDAO voteDao;

	@Autowired
	private AreaService areaService;

	@Override
	public void insert(VoteInsuranceDTO vote) throws BusinessServiceException {
		logger.info("保存投保人信息 入参    VoteInsuranceDTO："+vote);
		try {
			voteDao.insert(vote);
		} catch (Exception e) {
			logger.error("插入订单:" + vote.getOrder().getOrderId() + "的投保人信息失败:"
					+ e,e);
			throw new BusinessServiceException("保存投保人信息失败");
		}
	}

	@Override
	public VoteInsuranceDTO getByOrder(OrderDTO order)
			throws BusinessServiceException {
		logger.info("根据订单获取投保人信息 入参    OrderDTO："+order);
		VoteInsuranceDTO voteInsuranceDTO = null;
		try {
			voteInsuranceDTO = voteDao.getByOrderId(order);
		} catch (Exception e) {
			logger.error("查询订单:" + order.getOrderId() + "的投保人信息失败:" + e,e);
			throw new BusinessServiceException("根据订单获取投保人信息失败");
		}
		return voteInsuranceDTO;
	}

	@Override
	public VoteInsuranceDTO organizeVoteInsurance(String userId,
			QuotaDTO quota, VoteInsuranceDTO vote) throws ParseException,
			BusinessServiceException {
		logger.info("组装投保人信息 入参    用户ID："+userId+" QuotaDTO："+quota+" VoteInsuranceDTO："+vote);
		// 基本信息
		try {
			if(StringUtils.isBlank(userId)){
				String voteAreaCode = StringUtils.isNotBlank(vote.getVoteCity()) ? vote.getVoteCity() : vote.getVoteProvince();
				String address = areaService.get(voteAreaCode).getName()+ vote.getVoteAddress();
				vote.setAddressForPort(address);
			}else{
				vote.setCrtCode(userId);
				vote.setUpdCode(userId);
			}
		} catch (Exception e) {
			logger.error("报价单:" + quota.getQuotaId() + "组织投保人信息失败:" + e);
			throw new BusinessServiceException("组装投保人信息失败");
		}
		String voteCertNo = vote.getVoteCertNo();
		if (StringCodeUtil.isIdCard(voteCertNo)) {
			vote.setVoteSex(CommonUtil.getSex(voteCertNo) % 2 == 0 ? '2' : '1');
			vote.setVoteBirthday(CommonUtil.getBrith(voteCertNo));
		}
		if (null != quota) {
			vote.setQuota(quota);
			vote.setInquiry(quota.getInquiry());
			vote.setInsurance(quota.getInsurance());
		}
		return vote;
	}

	@Override
	public VoteInsuranceDTO getVoteInsuranceByOrderId(String orderId)
			throws BusinessServiceException {
		logger.info("根据订单ID获取投保人 入参    订单ID："+orderId);
		VoteInsuranceDTO voteInsuranceDTO = null;
		try {
			voteInsuranceDTO = voteDao.getVoteInsuranceByOrderId(orderId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "的投保人信息失败:" + e,e);
			throw new BusinessServiceException("根据订单ID获取投保人失败");
		}
		return voteInsuranceDTO;
	}
}
