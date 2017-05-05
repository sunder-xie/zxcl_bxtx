package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;




import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailPersonDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;

/**
 * 被保人
 * 
 * @author 5555
 *
 */
public interface RecognizeeDAO {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 */
	public void insert(RecognizeeDTO rec) throws Exception;

	/**
	 * 根据订单信息查询被保人信息
	 * 
	 * @param order
	 * @return
	 */
	public RecognizeeDTO getByOrder(OrderDTO order) throws Exception;

	/**
	 * 根据报价单号删除被保人信息
	 * 
	 * @param quotaId
	 */
	public void deleteByQuotaId(@Param("quotaId") String quotaId,
			@Param("microId") String microId, @Param("insId") String insId)
			throws Exception;

	/**
	 * 查询被保人信息
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public RecognizeeDTO getRecognizeeByOrderId(String orderId)
			throws Exception;

	/**
	 * 删除被保人信息
	 */
	public void deleteByOrderId(String orderId)
			throws Exception;

	/**
	 * 更新华海被保人信息
	 * @param appPerson
	 * @param quotaId
	 * @return
	 */
	public int updateWithHHBX(@Param("appPerson") ApplyDetailPersonDTO appPerson, @Param("quotaId") String quotaId);
}
