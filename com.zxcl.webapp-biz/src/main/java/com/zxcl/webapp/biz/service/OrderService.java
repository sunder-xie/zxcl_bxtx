package com.zxcl.webapp.biz.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.LogicException;
import com.zxcl.webapp.biz.exception.ServiceException;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.ReportDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.dto.webdto.SyntheticalDTO;

/**
 * 
 * 
 * @author 5555
 *
 */
public interface OrderService {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException
	 */
	public void insert(OrderDTO order) throws BusinessServiceException;
	/**
	 * 
	* @Title: peopleAaskDetailService
	* @Description: 人工报价明细
	* @param @param inquiryId 询价单号
	* @param @return
	* @param @throws BusinessServiceException
	* @return List<OrderDTO> 
	* @throws
	 */
	public List<OrderDTO> peopleAaskDetailService(String inquiryId)
			throws BusinessServiceException;

	/**
	 * 插入订单相关信息
	 * 
	 * @param order
	 * @param dis
	 * @param query
	 * @param owner
	 * @param rec
	 * @param vote
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws BusinessServiceException
	 */
	public void insertOrder(OrderDTO order, DistributionDTO dis,
			OwnerDTO owner, RecognizeeDTO rec,
			VoteInsuranceDTO vote) throws ServiceException,
			BusinessServiceException;

	/**
	 * 根据当前登录的小薇账户查询出正在进行的订单
	 * 
	 * @throws BusinessServiceException
	 */
	public List<OrderDTO> getOrderByMicroId(String microId)
			throws BusinessServiceException;

	/**
	 * 根据orderId查询出相关信息
	 * 
	 * @param orderId2
	 * @param insId
	 * @throws BusinessServiceException
	 */
	public OrderDTO getOrderById(String microId, String insId, String orderId)
			throws BusinessServiceException;

	/**
	 * 根据订单号获取支付相关信息
	 * 
	 * @param orderId
	 * @return
	 * @throws BusinessServiceException
	 */
	public OrderDTO getPay(String microId, String insId, String orderId)
			throws BusinessServiceException;

	/**
	 * 插入支付信息信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException
	 */
	public void insertOrderPay(OrderDTO order) throws BusinessServiceException;

	/**
	 * 根据小微id查询 相关的信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException
	 */
	public List<OrderDTO> selectByMicroId(String microId)throws BusinessServiceException;
	public PageBean<OrderDTO> selectByMicroId_v2(PageParam pageParam) throws BusinessServiceException;

	/**
	 * 根据条件 查询 出待核保
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<SyntheticalDTO> selectByItems(Map<String, Object> items)throws BusinessServiceException;
	//分页
	public PageBean<SyntheticalDTO> selectByItems(PageParam pageParam)throws BusinessServiceException;

	/**
	 * 根据订单号和保险公司id 获取订单信息（交强险投保单号，商业险投保单号）
	 * 
	 * @throws BusinessServiceException
	 */

	public OrderDTO selectOrderByOrderIdAndInsId(String orderId, String insId)
			throws BusinessServiceException;

	/**
	 * 根据小微id 和 orderId查询 相关的信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException
	 */
	public OrderDTO selectByMicroIdAndOrderId(String microId, String orderId,
			String insId) throws BusinessServiceException;

	/**
	 * 根据询价ID得到车主信息
	 * 
	 * @param inquiryId
	 * @return
	 * @throws BusinessServiceException
	 */
	public OwnerDTO getOwnerByInquiryId(String inquiryId)
			throws BusinessServiceException;

	/**
	 * 根据orderId 获取到付款方和付款金额
	 * 
	 * @throws BusinessServiceException
	 */

	public OrderDTO selectNameAndMoney(String insId, String OrderId)
			throws BusinessServiceException;

	/**
	 * 查询是否已经拥有相同的数据
	 * 
	 * @param quota
	 * @return
	 */
	// public Long getByInsert(QuotaDTO quota);

	/**
	 * 组装
	 */
	public OrderDTO organizeOrder(String userId, String orderId,
			QuotaDTO quota, VoteInsuranceReturnDTO voteReturn)
			throws ParseException, Exception;

	/**
	 * 根据订单号 获取保费，车牌号，被保人，小微id
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	public ReportDTO selectMon(String insId, String microId, String orderId)
			throws BusinessServiceException;

	/**
	 * 查询出 小微费率及中介id
	 * 
	 * @throws BusinessServiceException
	 */
	public ReportDTO selectMicroFeeRate(String insId, String microId)
			throws BusinessServiceException;

	/**
	 * 根据中介id 和保险公司 获取中介的费率
	 * 
	 * @throws BusinessServiceException
	 */
	public ReportDTO selectAgentFeeRate(String insId, String agentId)
			throws BusinessServiceException;

	/**
	 * 将相关的信息插入到t_report 表中
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	public void insertReport(String insId, String orderId, String userId)
			throws BusinessServiceException;

	/**
	 * 查询t_report表中是否有相同的数据
	 * 
	 * @throws BusinessServiceException
	 */
	public Integer selectTreport(String orderId, String insId)
			throws BusinessServiceException;

	/**
	 * 根据保险公司条件查询中介所有完成订单的报表
	 * 
	 * @throws BusinessServiceException
	 */
	public PagingResult<ReportDTO> selectAllMicroReportByPage(
			Map<String, Object> condition) throws BusinessServiceException;

	/**
	 * 根据订单id查询当前订单的详情
	 * 
	 * @throws BusinessServiceException
	 */
	public ReportDTO selectDetailReport(String orderId)
			throws BusinessServiceException;

	/**
	 * 根据报价单号查询订单条数
	 * 
	 * @param quotaId
	 * @param insId
	 * @param microId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getCountByQuotaId(String quotaId, String microId,
			String insId) throws BusinessServiceException;

	/**
	 * 更新订单状态为无效
	 * 
	 * @param quotaId
	 * @param microId
	 * @param insId
	 * @throws BusinessServiceException
	 */
	public void updateOrderStatusByInquiryId(String status, String inquiryId)
			throws BusinessServiceException;

	/**
	 * 根据订单号查找订单
	 * 
	 * @param quotaId
	 * @param micro_id
	 * @param insId
	 * @return
	 * @throws BusinessServiceException
	 */
	public OrderDTO getOrderByQuotaId(String quotaId, String micro_id,
			String insId) throws BusinessServiceException;

	/**
	 * 根据联合投保单号返回订单信息
	 * 
	 * @param microId
	 * @param carApplyNo
	 * @return
	 * @throws BusinessServiceException
	 */
	public OrderDTO getOrderByCarApplyNo(String microId, String carApplyNo)
			throws BusinessServiceException;
	
	
	/**
	 * 根据联合投保单号返回订单信息
	 * 
	 * @param microId
	 * @param carApplyNo
	 * @return
	 * @throws BusinessServiceException
	 */
	public OrderDTO getOrderByCarApplyNo(String carApplyNo)
			throws BusinessServiceException;

	/**
	 * 更新订单支付的信息
	 * 
	 * @throws BusinessServiceException
	 */
	public void updatePay(OrderDTO order) throws BusinessServiceException;
	
	/**
	 * 获取订单
	 * 
	 * @param inquiryId
	 */
	public void getOrderByInquiryId(String inquiryId) throws BusinessServiceException;

	/**
	 * 更新订单信息
	 */
	public void updateOrderAll(String userId, String insId, OrderDTO order,
			DistributionDTO dis, OwnerDTO owner,
			RecognizeeDTO rec, VoteInsuranceDTO vote)
			throws BusinessServiceException;
	
	/**
	 * 获取订单状态
	 */
	public String getOrderStatusByOrderId(String orderId)throws BusinessServiceException;
	
	/**
	 * 更新保单号和订单当前状态
	 */
	public void updatePolicyNoAndQueryState(OrderDTO order)throws BusinessServiceException;
	
	/**
	 * 根据保单号获取订单信息
	 * @param quotaId
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getOrderByQuotaId2(String quotaId) throws BusinessServiceException;
	
	/**
	 * 根据orderId修改订单状态
	 * @param queryStage 订单状态
	 * @param orderId 订单号
	 * @throws Exception
	 */
	public void updateOrderStatusByOrderId(@Param("queryStage")String queryStage,@Param("orderId")String orderId) throws BusinessServiceException;
	
	/**
	 * 修改
	 * @param orderDTO
	 * @throws BusinessServiceException
	 */
	public void updateOrder(OrderDTO orderDTO) throws BusinessServiceException;


	/**
	 * 根据订单编码更新订单号
	 * @param orederId 订单编码
	 * @param noticeNo 订单号
	 * @throws Exception
	 */
	public void updateNoticeNoByOrderId(String orderId,String noticeNo,String updCde) throws BusinessServiceException;
	
	/**
	 * 根据字段noticeNo查询订单信息
	 * @return 
	 */
	 public OrderDTO getOrderByNoticeNo(String userId, String noticeNo) throws BusinessServiceException;
		
	 /**
	  * 根据商业投保单号或交强投保单号得到保单
	  * @param applyNo 商业投保单号或交强投保单号
	  * @return
	  * @throws Exception
	  */
	 public OrderDTO getOrderIdByApplyNo(String applyNo) throws BusinessServiceException;
		
	/**
	 * 根据订单号获取投保单号
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getAppNoByOrderId(String orderId) throws BusinessServiceException;
	
	/**
	 * 生成保单操作
	 * @param orderId 订单号
	 * @param tciPlyNo 交强保单号
	 * @param vciPlyNo 商业保单号
	 * @throws BusinessServiceException
	 */
	public void createPolicyOperation(String orderId,String tciPlyNo,String vciPlyNo, OrderDTO order) throws BusinessServiceException;
	
	/**
	 * 修改询价单的状态
	 * @param status 状态
	 * @param inquiryId 询价单号
	 * @param userId 修改人
	 * @throws BusinessServiceException
	 */
	public void updateInquiryStatusByInquiryId(String status,String inquiryId, String userId) throws BusinessServiceException;
	
	/**
	 * 添加投保单号
	 * @param orderId 订单号
	 * @param tciPlyNo 交强保单号
	 * @param vciPlyNo 商业保单号
	 * @throws Exception
	 */
	public void createPolicyOperation2(String orderId, String tciPlyNo, String vciPlyNo) throws BusinessServiceException;
	
	/**
	 * @param orderId
	 * @return
	 */
	public String getInquiryIdByOrderId(String orderId);
	
	/**
	 * 根据询价单号返回该询价单下所有的订单状态
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public String[] getQueryStageByInquiryId(String inquiryId) throws BusinessServiceException;
	
	/**
	 * 根据状态得到订单信息
	 * @return
	 */
	public List<OrderDTO> getAllByStage(Date start,Integer pageNo,
			Integer pageSize) throws BusinessServiceException;
	
	/**
	 * 根据询价单号和订单状态获取订单号（5或6状态）
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getOrderIdByInquiryIdAndStatus(String inquiryId) throws BusinessServiceException;
	
	/**
	 * 根据条件 查询 续保
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	public PageBean<SyntheticalDTO> selectByItemsRenew(PageParam pageParam)throws BusinessServiceException;
}
