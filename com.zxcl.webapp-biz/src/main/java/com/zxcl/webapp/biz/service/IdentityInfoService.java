package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.dto.MicroApproveDTO;

/**
 * 身份信息service
 * @author zxj
 * @date 2016年8月22日
 * @description 
 */
public interface IdentityInfoService {
	
	/**
	 * 身份是否确认(是否实名认证通过)
	 * @param userId
	 * @return
	 */
	public boolean isConfirmed(String userId) throws Exception;
	
	/**
	 * 代理设置提现是否需要实名认证
	 * @param userId
	 * @return
	 */
	public boolean isNeedApprove(String userId) throws Exception;

	/**
	 * 提交认证信息
	 * @param authenticatedUserId
	 * @param fileIds
	 * @param iname
	 * @param icardid
	 */
	public void confirmIndentityInfo(String authenticatedUserId, String fileIds, String iname, String icardid) throws Exception;
	
	/**
	 * 查询认证信息
	 * @param userId
	 * @param findFile 是否查询附件信息
	 * @return
	 */
	public MicroApproveDTO findConfirm(String userId, boolean findFile);
}
