package com.zxcl.webapp.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.StatisticsVisitCountService;
import com.zxcl.webapp.dto.StatisticsVisitCountDTO;
import com.zxcl.webapp.integration.dao.StatisticsVisitCountDAO;

@Service
public class StatisticsVisitServiceImpl implements StatisticsVisitCountService {

	@Autowired
	private StatisticsVisitCountDAO statisticsVisitCountDAO;
	
	@Override
	public void insertStatisticsVisitCount(
			StatisticsVisitCountDTO statisticsVisitCountDTO) {
		statisticsVisitCountDAO.insertStatisticsVisitCount(statisticsVisitCountDTO);

	}

}
