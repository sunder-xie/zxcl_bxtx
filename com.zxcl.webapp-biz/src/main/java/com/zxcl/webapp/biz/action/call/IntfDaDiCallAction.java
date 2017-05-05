package com.zxcl.webapp.biz.action.call;

import java.util.Map;

import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;

/**
 * 大地保险
 * @author zxcl
 *
 */
public interface IntfDaDiCallAction {

	/**
	 * 报价
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 * @throws ActionException
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId, Map<String, Object> configMap) throws ActionException;

	/**
	 * 核保
	 * @param userId
	 * @param insId
	 * @param quotaId
	 * @param owner
	 * @param vote
	 * @param rec
	 * @param configMap
	 * @return
	 * @throws ActionException
	 */
	public VoteInsuranceReturnDTO vote(String userId, String insId, String quotaId, OwnerDTO owner, VoteInsuranceDTO vote, RecognizeeDTO rec, Map<String, Object> configMap) throws ActionException;

	/**
	 * 支付声请
	 * @param userId
	 * @param insId
	 * @param orderId
	 * @param callBackUrl
	 * @param configMap
	 * @param payType
	 * @return
	 * @throws ActionException
	 */
	public PayReturnDTO pay(String userId, String insId, String orderId, String callBackUrl, Map<String, Object> configMap) throws ActionException;

	/**
	 * 核保回调的处理
	 * @param xml
	 * @return
	 */
	public String undrResult(String xml) throws ActionException;

}
