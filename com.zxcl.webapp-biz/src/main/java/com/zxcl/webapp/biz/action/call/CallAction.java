package com.zxcl.webapp.biz.action.call;

import java.util.List;
import java.util.Map;

import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.InterfaceMsg;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.WeChatPayDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;

/**
 * 调用接口
 * 
 * @author 5555
 *
 */
public interface CallAction {

	/**
	 * 车型
	 */
	public List<VehicleReturnDTO> vehicleQuery(String userId,String inquiryId,String carName, InsuranceDTO ins)
			throws ActionException;

	/**
	 * 报价
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId)
			throws ActionException;

	/**
	 * 核保
	 * @param orderId 
	 */
	public VoteInsuranceReturnDTO vote(String userId, String insId, String quotaId, String orderId, OwnerDTO owner,
			VoteInsuranceDTO vote, RecognizeeDTO rec,DistributionDTO disJsp) throws ActionException;

	/**
	 * 支付校验
	 */
	public InterfaceMsg payCheck(String userId, String insId, String orderId) throws ActionException;

	/**
	 * 投保单综合查询
	 */
	public CombinedQueryDTO combinedQuery(String userId, String insId, String orderId, String status)
			throws ActionException;

	/**
	 * 支付通知
	 */
	public InterfaceMsg paySueccessInform(String userId, String insId, String orderId)
			throws ActionException;

	/**
	 * 微信支付
	 */
	public WeChatPayDTO weChatPay(String userId, String insId, String orderId,
			String callBackUrl) throws ActionException;
	
	/**
	 * 银联支付
	 * @param userId
	 * @param insId
	 * @param orderId
	 * @param callBackUrl
	 * @return
	 * @throws ActionException
	 */
	public WeChatPayDTO unionPay(String userId, String insId, String orderId,
			String callBackUrl) throws ActionException;
	/**
	 * 支付
	 * @param callBackUrl
	 * @param requestSource 微信内部支付使用 wechat；app和wap请传值wap
	 * @param quotaId
	 * @return
	 * @throws Exception
	 */
	public PayReturnDTO pay(String callBackUrl,String requestSource,String quotaId,String insId,
			String userId,String inquiryId,String orderId) throws ActionException;
	
//	/**
//	 * 浮动告知单（太平）
//	 * @param quotaId 报价单号
//	 * @param insIdS 保险公司
//	 * @param inquiryId 询价单号
//	 * @param userId 用户ID
//	 * @return
//	 * @throws ActionException
//	 */
//	public Map<String,Object> floatingToldSingle(String quotaId,String insId,String inquiryId,String userId) throws ActionException;
	
	/**
	 * 投保查询
	 * @param userId 用户ID
	 * @param inquiryId 询价单号
	 * @param insId 保险公司ID
	 * @return
	 * @throws ActionException
	 */
	public Map<String,Object> voteQuery(String userId, String inquiryId, String insId) throws ActionException;
	
//	/**
//	 * 投保单状态查询
//	 * @param userId 用户ID 
//	 * @param inquiryId 询价单号
//	 * @param insId 保险公司ID
//	 * @param orderId 订单号
//	 * @param quotaId 报价单号
//	 * @return
//	 * @throws ActionException
//	 */
//	public Map<String,Object> insuranceSlipTypeQuery(String userId,String inquiryId,String insId,String orderId,
//			String quotaId) throws ActionException;
	
//	/**
//	 * 获取支付流水号
//	 * @param userId 用户ID 
//	 * @param inquiryId 询价单号
//	 * @param insId 保险公司ID
//	 * @param orderId 订单号
//	 * @param quotaId 报价单号
//	 * @return
//	 * @throws ActionException
//	 */
//	public Map<String,Object> getPaySerialNum(String userId,String inquiryId,String insId,String orderId,
//			String quotaId) throws ActionException;
//	
//	
//	/**
//	 * 获取手机验证码
//	 * @param userId 用户ID 
//	 * @param inquiryId 询价单号
//	 * @param insId 保险公司ID
//	 * @param orderId 订单号
//	 * @param quotaId 报价单号
//	 * @param bankCardInfoDTO 银行卡信息
//	 * @return
//	 * @throws ActionException
//	 */
//	public Map<String,Object> getPhoneVerificationCode(String userId,String inquiryId,String insId,String orderId,
//			String quotaId,BankCardInfoDTO bankCardInfoDTO) throws ActionException;
//	
//	/**
//	 * 快钱支付
//	 * @param userId 用户ID 
//	 * @param inquiryId 询价单号
//	 * @param insId 保险公司ID
//	 * @param orderId 订单号
//	 * @param quotaId 报价单号
//	 * @param bankCardInfoDTO 银行卡信息
//	 * @return
//	 * @throws ActionException
//	 */
//	public Map<String,Object> quickMoneyPay(String userId,String inquiryId,String insId,String orderId,
//			String quotaId,BankCardInfoDTO bankCardInfoDTO) throws ActionException;
	
	/**
	 * 支付（新接口用，后面保险公司接口改完就全用新接口的，上面的老接口会删除）
	 * @param quoteId 报价单号
	 * @param insId 保险公司ID
	 * @param userId 用户ID
	 * @param inquiryId 询价单ID
	 * @param orderId 订单ID
	 * @return
	 * @throws ActionException
	 */
	public com.zxcl.bxtx.dto.intf.PayReturnDTO pay(String quoteId,String insId,
			String userId,String inquiryId,String orderId) throws ActionException;
	
	/**
	 * 车型查询（主要是根据车架号查询，平安的）
	 * @param vehicleFrameNo 车架号
	 * @param userId 用户ID
	 * @return
	 */
	public List<VehicleReturnDTO> vehicleQuery(String vehicleFrameNo,String userId);
	
}
