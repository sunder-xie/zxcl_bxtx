package com.zxcl.webapp.integration.dao;

import java.util.List;


import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.dto.ManageDispatchDTO;

public interface ManageDispatchDAO {
    int deleteByPrimaryKey(String id) throws Exception;

    int insertSelective(ManageDispatchDTO record) throws Exception;

    ManageDispatchDTO selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(ManageDispatchDTO record) throws Exception;

	/**
	 * 默认配送信息
	 * @param baseParam
	 * @return
	 */
	ManageDispatchDTO selectDefaultManageDispatch(BaseParam baseParam) throws Exception;

	
	/**
	 * 配送列表
	 * @param baseParam
	 * @return
	 */
	List<ManageDispatchDTO> selectManageDispatch(BaseParam baseParam) throws Exception;
	
	/**
	 * 根据ID获取配送信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ManageDispatchDTO selectById(String id) throws Exception;
	
	/**
	 * 编辑
	 * @param manageDispatchDTO
	 * @throws Exception
	 */
	void updateById(ManageDispatchDTO manageDispatchDTO) throws Exception;
}