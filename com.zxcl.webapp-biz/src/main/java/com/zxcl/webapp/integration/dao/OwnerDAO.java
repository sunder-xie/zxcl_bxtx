package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;




import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailPersonDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;

/**
 * 车主
 * 
 * @author 5555
 *
 */
public interface OwnerDAO {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 */
	public void insert(OwnerDTO owner) throws Exception;

	/**
	 * 根据订单ID查询车主信息
	 * 
	 * @param orderId
	 * @return
	 */
	public OwnerDTO get(String orderId) throws Exception;

	/**
	 * 根据订单信息查找车主
	 * 
	 * @param order
	 * @return
	 */
	public OwnerDTO getByOrder(OrderDTO order) throws Exception;

	/**
	 * 根据报价单号删除车主信息
	 * 
	 * @param quotaId
	 * @param insId
	 * @param microId
	 */
	public void deleteByQuotaId(@Param("quotaId") String quotaId,
			@Param("microId") String microId, @Param("insId") String insId)
			throws Exception;

	/**
	 * 获取车主信息
	 * 
	 * @param orderId
	 * @return
	 */
	public OwnerDTO getOwnerByOrderId(String orderId) throws Exception;
	
	/**
	 * 删除车主信息
	 */
	public void deleteByOrderId(String orderId)
			throws Exception;
	/**
	 * 更新配送信息
	 * @param quotaId
	 * @param microId
	 * @param insId
	 * @throws Exception
	 */
	public void update(@Param("quotaId")String quotaId,@Param("microId")String microId,@Param("insId")String insId,@Param("owner")OwnerDTO owner) throws Exception;

	/**
	 * 华海更新车主信息
	 * @param ownerPerson
	 * @param quotaId
	 */
	public int updateWithHHBX(@Param("ownerPerson") ApplyDetailPersonDTO ownerPerson, @Param("quotaId") String quotaId);
}
