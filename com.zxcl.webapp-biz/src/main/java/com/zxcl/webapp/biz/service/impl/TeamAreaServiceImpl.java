package com.zxcl.webapp.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.TeamAreaService;
import com.zxcl.webapp.integration.dao.TeamAreaDAO;

@Service
public class TeamAreaServiceImpl implements TeamAreaService {

	@Autowired
	private TeamAreaDAO teamAreaDAO;
	
	@Override
	public String getAreaCodeByTeamId(String teamId) {
		return teamAreaDAO.getAreaCodeByTeamId(teamId);
	}

	@Override
	public String getAreaCodeByUserId(String userId) {
		return teamAreaDAO.getAreaCodeByUserId(userId);
	}

}
