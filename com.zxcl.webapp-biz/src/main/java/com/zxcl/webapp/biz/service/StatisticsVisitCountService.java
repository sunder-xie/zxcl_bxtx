package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.dto.StatisticsVisitCountDTO;

public interface StatisticsVisitCountService {

	/**
	 * 保存访问记录
	 * @param statisticsVisitCountDTO
	 */
	public void insertStatisticsVisitCount(StatisticsVisitCountDTO statisticsVisitCountDTO);
}
