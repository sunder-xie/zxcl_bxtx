package com.zxcl.webapp.biz.action.data;

import java.util.List;

import com.zxcl.bxtx.dto.intf.InquiryDTO;
import com.zxcl.bxtx.dto.intf.OrderQueryReturnDTO;
import com.zxcl.bxtx.dto.intf.QuoteReturnDTO;
import com.zxcl.bxtx.dto.intf.UnderwriteReturnDTO;
import com.zxcl.bxtx.dto.intf.VehicleReturnListDTO;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vehicle.resp.VehicleModelDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;

/**
 * 数据转换
 * @author zx
 *
 */
public interface ChangeDataAction {
	
	/**
	 * 询价单数据信息转换
	 * @param inquiryId 询价单号
	 * @param quoteId 报价单号
	 * @param orderId 订单号
	 * @return
	 * @throws ActionException
	 */
	public InquiryDTO inquiryChange(String inquiryId,String quoteId,String orderId,OwnerDTO owner,
			VoteInsuranceDTO vote, RecognizeeDTO rec,String userId,DistributionDTO disJsp,String insId) throws ActionException;
	
	/**
	 * 报价数据返回组装
	 * @param quoteReturnDTO
	 * @return
	 * @throws ActionException
	 */
	public QuotaReturnDTO quoteReturnChange(QuoteReturnDTO quoteReturnDTO) throws ActionException;
	
	/**
	 * 核保数据返回组装
	 * @param underwriteReturnDTO
	 * @return
	 * @throws ActionException
	 */
	public VoteInsuranceReturnDTO underwriteReturnChange(UnderwriteReturnDTO underwriteReturnDTO) throws ActionException;
	
	/**
	 * 支付数据返回组装
	 * @param payReturnDTO
	 * @return
	 * @throws ActionException
	 */
	public PayReturnDTO payReturnChange(com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO) throws ActionException;
	
	/**
	 * 车型查询数据返回组装
	 * @param vehicleReturnListDTO
	 * @return
	 * @throws ActionException
	 */
	public List<VehicleModelDTO> vhlReturnChange(VehicleReturnListDTO vehicleReturnListDTO) throws ActionException;
	
	/**
	 * 订单查询数据返回组装
	 * @param orderQueryReturnDTO
	 * @return
	 * @throws ActionException
	 */
	public CombinedQueryDTO orderQuery(OrderQueryReturnDTO orderQueryReturnDTO) throws ActionException ;
}
