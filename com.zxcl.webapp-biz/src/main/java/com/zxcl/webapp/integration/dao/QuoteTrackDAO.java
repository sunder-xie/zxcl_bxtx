package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.QuoteTrackDTO;

/**
 * 报价轨迹DAO
 * @author zx
 *
 */
public interface QuoteTrackDAO {
	
	/**
	 * 保存报价轨迹信息
	 * @param quoteTrackDTO 
	 * @throws Exception
	 */
	public void insert(QuoteTrackDTO quoteTrackDTO) throws Exception;
	
	/**
	 * 查询该任务上一次的轨迹的ID
	 * @param taskId 任务ID
	 * @return
	 * @throws Exception
	 */
	public String queryOnOperatId(String taskId) throws Exception;
	
	/**
	 * 查询该任务上一次的轨迹信息
	 * @param taskId 任务ID
	 * @return
	 * @throws Exception
	 */
	public QuoteTrackDTO queryInfo(String taskId) throws Exception;
	
	/**
	 * 根据任务ID和状态查询出改报价轨迹信息
	 * @param taskId 任务ID
	 * @param status 状态
	 * @return
	 * @throws Exception
	 */
	public List<QuoteTrackDTO> queryByTaskIdAndStatus(@Param("taskId")String taskId,@Param("status")String status) throws Exception;
	
	/**
	 * 根据ID获取轨迹信息
	 * @param operatId 轨迹ID
	 * @return
	 * @throws Exception
	 */
	public QuoteTrackDTO queryOperatId(String operatId) throws Exception;
}
