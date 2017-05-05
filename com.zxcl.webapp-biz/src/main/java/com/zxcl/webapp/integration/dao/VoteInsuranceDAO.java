package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;




import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailPersonDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;

/**
 * 
 * 投保人
 * 
 * @author 5555
 *
 */
public interface VoteInsuranceDAO {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 */
	public void insert(VoteInsuranceDTO vote) throws Exception;

	/**
	 * 根据订单信息查询投保人信息
	 * 
	 * @param order
	 * @return
	 */
	public VoteInsuranceDTO getByOrderId(OrderDTO order) throws Exception;

	/**
	 * 根据订单号删除投保人信息
	 * 
	 * @param orderId
	 */
	public void deleteByQuotaId(@Param("quotaId") String quotaId,
			@Param("microId") String microId, @Param("insId") String insId)
			throws Exception;

	/**
	 * 获取投保人信息
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public VoteInsuranceDTO getVoteInsuranceByOrderId(String orderId)
			throws Exception;

	/**
	 * 删除投保人信息
	 */
	public void deleteByOrderId(String orderId)
			throws Exception;

	/**
	 * 更新华海投保人信息
	 * @param appPerson
	 * @param quotaId
	 * @return
	 */
	public int updateWithHHBX(@Param("appPerson") ApplyDetailPersonDTO appPerson, @Param("quotaId") String quotaId);
}
