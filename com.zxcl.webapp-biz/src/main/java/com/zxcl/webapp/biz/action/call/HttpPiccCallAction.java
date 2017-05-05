package com.zxcl.webapp.biz.action.call;

import java.util.Map;

import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;

/**
 * 人保
 * @author Li xiaokang
 *
 */
public interface HttpPiccCallAction {

	/**
	 * 报价
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) throws ActionException;

	
	/**
	 * 核保
	 * @param userId 用户ID
	 * @param insId 保险公司ID
	 * @param quotaId 报价单ID
	 * @return
	 * @throws Exception
	 */
	public VoteInsuranceReturnDTO vote(String inquiryId, String userId, String insId, String quotaId, OwnerDTO owner,
									   VoteInsuranceDTO vote, RecognizeeDTO rec, Map<String, Object> configMap) throws ActionException;

	/**
	 * 投保单综合查询
	 * @param userId 用户ID
	 * @param insId 保险公司ID
	 * @param orderId 报价单ID
	 * @param status 状态
	 * @return
	 * @throws Exception
	 * @author lee
	 */
	public CombinedQueryDTO combinedQuery(String userId, String orderId, Map<String, Object> configMap)
			throws Exception;

}
