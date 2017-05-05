package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.PayService;

@Service
public class PayServiceImpl implements PayService {

	Logger logger = Logger.getLogger(getClass());

	@Value("${zxcl.pay.supportPayName}")
	private String supportPayName;

	@Override
	public List<String> getPayWay(String userId) {
		logger.info("获取支付方式 入参    用户ID："+userId);
		List<String> ways = new ArrayList<String>();
		// 假设从数据库查询出来的
		String[] payWays = new String[] { "wechat" ,"wap","unionpay"};
		logger.info("小薇：+" + userId + ",支持的支付方式：" + ArrayUtils.toString(payWays));
		String[] payNames = getSupportPayNames();
		for (String payWay : payWays) {
			if (ArrayUtils.contains(payNames, payWay)) {
				ways.add(payWay);
			}
		}
		return ways;
	}

	public String[] getSupportPayNames() {
		logger.info("系统支持的支付方式：" + supportPayName);
		return supportPayName.split(",");
	}
}
