package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.dto.ManageDispatchDTO;

public interface IManageDispatchService {
	/**
	 * 新增配送信息
	 * @param manageDispatchDTO
	 * @throws BusinessServiceException
	 */
	public void insertManageDispatch(ManageDispatchDTO manageDispatchDTO,BaseParam baseParam) throws BusinessServiceException;
	
	/**
	 * 删除配送信息
	 * @param manageDispatchDTO
	 * @throws BusinessServiceException
	 */
	public void delManageDispatch(ManageDispatchDTO manageDispatchDTO,BaseParam baseParam) throws BusinessServiceException;
	
	/**
	 * 获取配送信息列表
	 * @param baseParam
	 * @throws BusinessServiceException
	 */
	public List<ManageDispatchDTO> selectManageDispatch(BaseParam baseParam) throws BusinessServiceException;
	
	
	/**
	 * 获取单条配送信息
	 * @param baseParam
	 * @param manageDispatchDTO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ManageDispatchDTO selectManageDispatchById(BaseParam baseParam,ManageDispatchDTO manageDispatchDTO) throws BusinessServiceException;
	
	/**
	 * 获取默认配送信息
	 * @param baseParam
	 * @return
	 * @throws BusinessServiceException
	 */
	public ManageDispatchDTO selectDefaultManageDispatch(BaseParam baseParam) throws BusinessServiceException;

	/**
	 * 设置默认配送信息
	 * @param manageDispatchDTO
	 * @param baseParam
	 * @return
	 * @throws BusinessServiceException
	 */
	public void setDefaultManageDispatch(ManageDispatchDTO manageDispatchDTO,
			BaseParam baseParam) throws BusinessServiceException;
	
	/**
	 * 根据ID获取配送信息
	 * @param id
	 * @return
	 * @throws BusinessServiceException
	 */
	public ManageDispatchDTO selectById(String id) throws BusinessServiceException;
	
	/**
	 * 编辑
	 * @param manageDispatchDTO
	 * @throws Exception
	 */
	public void updateById(ManageDispatchDTO manageDispatchDTO) throws BusinessServiceException;
}
