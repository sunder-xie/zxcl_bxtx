package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.StatisticsVisitCountDTO;

public interface StatisticsVisitCountDAO {

	/**
	 * 保存访问记录
	 * @param statisticsVisitCountDTO
	 */
	public void insertStatisticsVisitCount(StatisticsVisitCountDTO statisticsVisitCountDTO);
}
