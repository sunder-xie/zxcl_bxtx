package com.zxcl.webapp.biz.action.call;



import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
/**
 * 华安爬虫接口
 * 
 * @author zxj
 *
 */
public interface HttpHACPCallAction {

	/**
	 * 报价
	 * @param userId 用户ID
	 * @param inquiryId 询价单ID
	 * @param insId 保险公司
	 * @return
	 * @throws Exception
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId)
			throws ActionException;

	/**
	 * 核保
	 * @param userId 用户ID
	 * @param insId 保险公司ID
	 * @param quotaId 报价单ID
	 * @param owner 车主
	 * @param vote 投保人
	 * @param rec 被保人
	 * @return
	 * @throws Exception
	 */
	public VoteInsuranceReturnDTO vote(String userId, String insId, String quotaId, OwnerDTO owner,
			VoteInsuranceDTO vote, RecognizeeDTO rec) throws ActionException;

	/**
	 * 投保单综合查询
	 * @param userId 用户ID
	 * @param insId 保险公司ID
	 * @param orderId 报价单ID
	 * @param status 状态
	 * @return
	 * @throws Exception
	 */
	public CombinedQueryDTO combinedQuery(String userId, String insId, String orderId, String status)
			throws ActionException;
}
