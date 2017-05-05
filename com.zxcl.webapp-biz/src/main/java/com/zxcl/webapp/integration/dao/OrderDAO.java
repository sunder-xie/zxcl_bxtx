package com.zxcl.webapp.integration.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.bxtx.dto.intf.OrderQueryReturnDTO;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.InsuranceBdGenInfo;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.ReportDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.webdto.SyntheticalDTO;

/**
 * 
 * 
 * @author 5555
 *
 */
public interface OrderDAO {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 */
	public void insert(OrderDTO order) throws Exception;

	/**
	 * 根据当前登录的小薇账户查询出正在进行的订单
	 * 
	 * @param microId
	 */
	public List<OrderDTO> getOrderByMicroId(String microId) throws Exception;

	/**
	 * 根据orderId查询出相关信息
	 * 
	 * @param orderId
	 * @param orderId2
	 * @param insId
	 */
	public OrderDTO getOrderById(@Param("microId") String microId, @Param("insId") String insId, @Param("orderId") String orderId) throws Exception;

	/**
	 * 根据orderId查询出相关信息
	 * 
	 * @param orderId
	 * @param orderId2
	 * @param insId
	 */
	public OrderDTO getOrderById(@Param("microId") String microId, @Param("orderId") String orderId) throws Exception;

	/**
	 * 
	 * @Title: infoViewByInquiryIDDAO @Description: 人工报价明细 @param inquiryId
	 * 询价单号 @param @throws Exception @return QuotaDTO @throws
	 */

	public List<OrderDTO> peopleAaskDetailDAO(String inquiryId) throws Exception;

	/**
	 * 根据订单号获取支付相关信息
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderDTO getPay(@Param("microId") String microId, @Param("insId") String insId, @Param("orderId") String orderId) throws Exception;

	/**
	 * 插入支付信息信息
	 * 
	 * @param rec
	 */
	public void insertOrderPay(OrderDTO order) throws Exception;

	/**
	 * 根据当前登录的小薇账户查询出正在进行的订单
	 * 
	 * @param microId
	 */
	public List<OrderDTO> selectOrderByMicroId(String microId) throws Exception;

	public int selectOrderByMicroIdPageCount(PageParam pageParam) throws Exception;

	public List<OrderDTO> selectOrderByMicroIdPage(PageParam pageParam) throws Exception;

	/**
	 * 根据条件 查询 出待核保
	 * 
	 * @return
	 */
	public List<SyntheticalDTO> selectByItems(Map<String, Object> items) throws Exception;

	// 分页
	public Integer selectByItemsPageCount(PageParam pageParam) throws Exception;

	public List<SyntheticalDTO> selectByItemsPage(PageParam pageParam) throws Exception;

	/**
	 * 根据订单号和保险公司id 获取订单信息（交强险投保单号，商业险投保单号）
	 */

	public OrderDTO selectOrderByOrderIdAndInsId(@Param("orderId") String orderId, @Param("insId") String insId) throws Exception;

	/**
	 * 根据当前登录的小薇账户 和orderID查询出正在进行的订单
	 * 
	 * @param microId
	 */
	public OrderDTO selectByMicroIdAndOrderId(@Param("microId") String microId, @Param("orderId") String orderId, @Param("insId") String insId) throws Exception;

	/**
	 * 根据询价ID得到车主信息
	 * 
	 * @param inquiryId
	 * @return
	 */
	public OwnerDTO getOwnerByInquiryId(@Param("inquiryId") String inquiryId) throws Exception;

	/**
	 * 根据orderId获取到付款方和支付金额
	 */
	public OrderDTO selectNameAndMoney(@Param("insId") String insId, @Param("orderId") String orderId) throws Exception;

	/**
	 * 根据订单号 获取保费，车牌号，被保人，小微id
	 * 
	 * @param orderId2
	 * @param microId
	 * 
	 * @return
	 */
	public ReportDTO selectMon(@Param("insId") String insId, @Param("microId") String microId, @Param("orderId") String orderId) throws Exception;

	/**
	 * 查询出 小微费率及中介id
	 */
	public ReportDTO selectMicroFeeRate(@Param("insId") String insId, @Param("microId") String microId) throws Exception;

	/**
	 * 根据中介id 和保险公司 获取中介的费率
	 */
	public ReportDTO selectAgentFeeRate(@Param("insId") String insId, @Param("agentId") String agentId) throws Exception;

	/**
	 * 将相关的信息插入到t_report 表中
	 */
	public void insertReport(ReportDTO reportDTO) throws Exception;

	/**
	 * 查询t_report表中是否有相同的数据
	 */
	public Integer selectTreport(@Param("orderId") String orderId, @Param("insId") String insId) throws Exception;

	/**
	 * 根据保险公司条件查询中介所有完成订单的报表
	 */
	public PagingResult<ReportDTO> selectAllMicroReportByPage(Map<String, Object> condition) throws Exception;

	/**
	 * 根据订单id查询当前订单的详情
	 */
	public ReportDTO selectDetailReport(String orderId) throws Exception;

	/**
	 * 根据报价单号删除订单信息
	 * 
	 * @param quotaId
	 */
	public void deleteByQuotaId(@Param("quotaId") String quotaId, @Param("microId") String microId, @Param("insId") String insId) throws Exception;

	/**
	 * 根据报价单号查询订单条数
	 * 
	 * @param quotaId
	 * @param insId
	 * @param microId
	 * @return
	 */
	public Integer getCountByQuotaId(@Param("quotaId") String quotaId, @Param("microId") String microId, @Param("insId") String insId) throws Exception;

	/**
	 * 将订单状态改为无效
	 * 
	 * @param quotaId
	 * @param microId
	 * @param insId
	 */
	public void updateOrderStatusByInquiryId(@Param("status") String status, @Param("inquiryId") String inquiryId) throws Exception;

	/**
	 * 根据订单号查找订单
	 * 
	 * @param quotaId
	 * @param micro_id
	 * @param insId
	 * @return
	 */
	public OrderDTO getOrderByQuotaId(@Param("quotaId") String quotaId, @Param("microId") String microId, @Param("insId") String insId) throws Exception;

	/**
	 * 根据联合投保单号查询订单信息
	 * 
	 * @param microId
	 * @param carApplyNo
	 * @return
	 */
	public OrderDTO getOrderByCarApplyNo(@Param("microId") String microId, @Param("carApplyNo") String carApplyNo) throws Exception;

	/**
	 * 更新订单的支付信息
	 */
	public void updatePay(OrderDTO order) throws Exception;

	/**
	 * 获取订单
	 * 
	 * @param inquiryId
	 */
	public void getOrderByInquiryId(String inquiryId) throws Exception;

	/**
	 * 更新订单信息
	 * 
	 * @param order
	 */
	public void updateByOrderId(OrderDTO order) throws Exception;

	/**
	 * 获取订单状态
	 */
	public String getOrderStatusByOrderId(String orderId) throws Exception;

	/**
	 * 根据保单号获取订单信息
	 * 
	 * @param quotaId
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getOrderByQuotaId2(String quotaId) throws Exception;

	/**
	 * 根据报价单号查询投保人姓名和被保人姓名
	 * 
	 * @param quotaId
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getOrderInfoByQuotaId(String quotaId) throws Exception;

	/**
	 * 根据orderId修改订单状态
	 * 
	 * @param queryStage
	 *            订单状态
	 * @param orderId
	 *            订单号
	 * @throws Exception
	 */
	public void updateOrderStatusByOrderId(@Param("queryStage") String queryStage, @Param("orderId") String orderId) throws Exception;

	/**
	 * 根据订单编码更新订单号
	 * 
	 * @param orderId
	 *            订单编码
	 * @param noticeNo
	 *            订单号
	 * @param updCde
	 *            修改人
	 * @throws Exception
	 */
	public void updateNoticeNoByOrderId(@Param("orderId") String orderId, @Param("noticeNo") String noticeNo, @Param("updCde") String updCde) throws Exception;

	/**
	 * 查询订单信息
	 * 
	 * @param noticeNo
	 * @return
	 */
	public OrderDTO getOrderByNoticeNo(@Param("microId") String microId, @Param("noticeNo") String noticeNo) throws Exception;

	/**
	 * 根据商业投保单号或交强投保单号查询保单
	 * 
	 * @param applyNo
	 *            商业投保单号或交强投保单号
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getOrderIdByApplyNo(@Param("applyNo") String applyNo) throws Exception;

	/**
	 * 根据订单号获取投保单号
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getAppNoByOrderId(String orderId) throws Exception;

	/**
	 * 更新保单生成时间
	 * 
	 * @param orderId
	 *            订单编码
	 * @throws Exception
	 */
	public void updatePlyCrtTimeByOrderId(String orderId) throws Exception;

	/**
	 * 根据订单号修改订单状态
	 * 
	 * @param orderId
	 *            订单号
	 * @param status
	 *            状态 0:无效 1:有效
	 * @throws Exception
	 */
	public int updateStatus(@Param("orderId") String orderId, @Param("status") String status) throws Exception;

	/**
	 * 根据保单号获取订单信息（防止数据出错查出多条数据）
	 * 
	 * @param quotaId
	 * @return
	 * @throws Exception
	 */
	public List<OrderDTO> getOrderByQuotaId3(String quotaId) throws Exception;

	/**
	 * 根据联合投保单号查询订单信息
	 * 
	 * @param carApplyNo
	 * @return
	 */
	public OrderDTO getOrderByCApplyNo(@Param("carApplyNo") String carApplyNo) throws Exception;
	
	/**
	 * 根据订单号获取询价单号和保险公司ID 
	 * @param orderId 订单号
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getInquiryIdByOrderId(String orderId) throws Exception;
	
	/**
	 * 生成保单操作
	 * @param orderId 订单号
	 * @param tciPlyNo 交强保单号
	 * @param vciPlyNo 商业保单号
	 * @throws Exception
	 */
	public void createPolicyOperation(@Param("orderId")String orderId, @Param("tciPlyNo")String tciPlyNo,
			@Param("vciPlyNo")String vciPlyNo) throws Exception;
	
	/**
	 * 添加投保单号
	 * @param orderId 订单号
	 * @param tciPlyNo 交强保单号
	 * @param vciPlyNo 商业保单号
	 * @throws Exception
	 */
	public void createPolicyOperation2(@Param("orderId")String orderId, @Param("tciPlyNo")String tciPlyNo,
			@Param("vciPlyNo")String vciPlyNo) throws Exception;
	
	/**
	 * 根据订单ID查询是否存在该条数据
	 * @param orderId 订单ID
	 * @return
	 * @throws Exception
	 */
	public Integer queryOrderApplyStatus(String orderId) throws Exception;
	
	/**
	 * 修改信息
	 * @param orderQueryReturnDTO
	 * @throws Exception
	 */
	public void updateOrderApplyStatus(OrderQueryReturnDTO orderQueryReturnDTO) throws Exception;
	
	/**
	 * 添加信息
	 * @param orderQueryReturnDTO
	 * @throws Exception
	 */
	public void insertOrderApplyStatus(OrderQueryReturnDTO orderQueryReturnDTO) throws Exception;
	
	/**
	 * 根据询价单号返回该询价单下所有的订单状态
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public String[] getQueryStageByInquiryId(String inquiryId) throws Exception;
	
	/**
	 * 定时任务刷新订单
	 * @return
	 */
	public List<OrderDTO> getAllByStage(
			@Param("minTime")Date start,
			@Param("pageNo")Integer pageNo,
			@Param("pageSize")Integer pageSize) throws Exception;
	public List<OrderDTO> getAllHttpByStage(
			@Param("minTime")Date start,
			@Param("pageNo")Integer pageNo,
			@Param("pageSize")Integer pageSize) throws Exception;
	
	/**
	 * 根据询价单号和订单状态获取订单号（5或6状态）
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public OrderDTO getOrderIdByInquiryIdAndStatus(String inquiryId) throws Exception;
	
	/**
	 * 根据订单号获取用户对应的业管公司代码
	 * @param orderId
	 * @return
	 */
	public String getCompCodeByOrderId(String orderId);
	
	/**
	 * 回写业管系统数据
	 * @param insuranceBdGenInfo
	 */
	public void insertInsuranceBdGenInfo(InsuranceBdGenInfo insuranceBdGenInfo);

	/**
	 * 续保分页
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Integer selectByItemsRenewPageCount(PageParam pageParam) throws Exception;
	
	/**
	 * 续保数据查询
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public List<SyntheticalDTO> selectByItemsRenewPage(PageParam pageParam) throws Exception;
	
	/**
	 * 根据询价单号更新下面的报价单为无效（生成订单和已支付的列外）
	 * @param inquiryId 询价单号
	 * @throws Exception
	 */
	public void updateQuoteByInquiryId(String inquiryId) throws Exception;
	
	/**
	 * 根据询价单号更新下面的订单为无效（生成订单和已支付的列外）
	 * @param inquiryId 询价单号
	 * @throws Exception
	 */
	public void updateOrderByInquiryId(String inquiryId) throws Exception;
	
	/**
	 * t_quota_task_trace_remark获取REMARK_STATUS状态
	 * @param inquiryId
	 * @param quotaId
	 * @param insId
	 * @return
	 */
	public String findRemarkStatusByPrimaryKey(@Param("inquiryId")String inquiryId, @Param("quotaId")String quotaId, @Param("insId")String insId);

}
