package com.zxcl.webapp.integration.dao;

/**
 * 人工报价任务上传的文件DAO
 * @author zx
 *
 */
public interface TaskFileDAO {
	
	/**
	 * 根据任务ID获取文件ID信息
	 * @param taskId 任务ID
	 * @return
	 * @throws Exception
	 */
	public String [] queryByTaskId(String taskId) throws Exception;
}
