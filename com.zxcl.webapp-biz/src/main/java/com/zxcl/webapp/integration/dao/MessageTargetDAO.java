package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;


public interface MessageTargetDAO {
    int deleteByPrimaryKey(String messageBodyId);

    int insertSelective(MessageTargetDTOWithBLOBs record);

    MessageTargetDTOWithBLOBs selectByPrimaryKey(String messageBodyId);

    int updateByPrimaryKeySelective(MessageTargetDTOWithBLOBs record);

	int messageTargetListCount(PageParam pageParam);

	List<MessageTargetDTOWithBLOBs> messageTargetList(PageParam pageParam);
}