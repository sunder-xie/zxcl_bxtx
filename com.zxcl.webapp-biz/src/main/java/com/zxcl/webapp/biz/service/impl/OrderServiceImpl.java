package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.bxtx.call.CallBackAPI;
import com.zxcl.bxtx.dto.intf.OrderQueryReturnDTO;
import com.zxcl.huahai.action.HuahaiTotalAction;
import com.zxcl.huahai.dto.port.huahai.req.seria.ApplyDetailQueryReqDTO;
import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailBaseRespDTO;
import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailCarDTO;
import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailCvrgDTO;
import com.zxcl.huahai.dto.port.huahai.resp.seria.ApplyDetailPersonDTO;
import com.zxcl.webapp.biz.action.call.impl.CallActionImpl;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CommonCarInsurance;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.PolicyBaseService;
import com.zxcl.webapp.biz.service.ResourceVehicleService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InsuranceBdGenInfo;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotaDetailedDTO;
import com.zxcl.webapp.dto.ReportDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.dto.webdto.SyntheticalDTO;
import com.zxcl.webapp.integration.dao.DistributionDAO;
import com.zxcl.webapp.integration.dao.InquiryDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;
import com.zxcl.webapp.integration.dao.OwnerDAO;
import com.zxcl.webapp.integration.dao.QuotaDAO;
import com.zxcl.webapp.integration.dao.QuotaDetailedDAO;
import com.zxcl.webapp.integration.dao.RecognizeeDAO;
import com.zxcl.webapp.integration.dao.VoteInsuranceDAO;

@Service
public class OrderServiceImpl implements OrderService,CallBackAPI {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderDAO orderDao;

	@Autowired
	private DistributionDAO disDao;

	@Autowired
	private OwnerDAO ownerDao;

	@Autowired
	private RecognizeeDAO recDao;

	@Autowired
	private VoteInsuranceDAO voteDao;

	@Autowired
	private MicroDAO microDao;

	@Autowired
	private ResourceVehicleService resourceVehicleService;
	
	@Autowired
	private CommonCarInsurance commonCarInsurance;
	
	@Autowired
	private InquiryDAO inquiryDAO;
	
	@Autowired
	private QuotaDAO quotaDAO;
	
	@Autowired
	private HuahaiTotalAction huahaiTotalAction;
	
	@Autowired
	private QuotaDetailedDAO quotaDetailedDAO;
	
	@Autowired
	private PolicyBaseService policyBaseService;
	
	@Override
	public void insert(OrderDTO order) throws BusinessServiceException {
		logger.info("插入订单信息 入参    OrderDTO："+order);
		try {
			orderDao.insert(order);
		} catch (Exception e) {
			logger.error("插入订单信息失败:" + e,e);
			throw new BusinessServiceException("插入订单信息失败");
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void insertOrder(OrderDTO order, DistributionDTO dis, OwnerDTO owner, RecognizeeDTO rec,
			VoteInsuranceDTO vote) throws BusinessServiceException {
		logger.info("插入订单相关信息 入参    OrderDTO："+order+"  DistributionDTO："+dis+" OnwerDTO："+owner+"  RecognizeeDTO："+rec+"  VoteInsuranceDTO："+vote);
		try {
			String quotaId = order.getQuota().getQuotaId();
			logger.info("报价单号："+quotaId);
			List<OrderDTO> orderDTOs = orderDao.getOrderByQuotaId3(quotaId);
			if(null != orderDTOs){
				for (OrderDTO orderDTO : orderDTOs) {					
					orderDao.updateStatus(orderDTO.getOrderId(), "0");
				}
			}
			
			this.insert(order);
			disDao.insert(dis);
//			if (null != query) {
//				logger.info("插入订单查询信息");
//				queryDao.insert(query);
//			}
			logger.info("插入车主信息");
			ownerDao.insert(owner);
			logger.info("插入被保人信息");
			recDao.insert(rec);
			logger.info("插入投保人信息");
			voteDao.insert(vote);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("添加订单信息失败" + e,e);
			throw new BusinessServiceException("添加订单信息有误！！");
		}
	}

	/**
	 * 根据当前登录的小薇账户查询出正在进行的订单
	 */
	public List<OrderDTO> getOrderByMicroId(String microId)
			throws BusinessServiceException {
		logger.info("根据当前登录的小薇账户查询出正在进行的订单 入参    小微ID："+microId);
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		try {
			list = orderDao.getOrderByMicroId(microId);
		} catch (Exception e) {
			logger.error("查询小薇:" + microId + "所有正在进行的订单有误.",e);
			throw new BusinessServiceException("根据当前登录的小薇账户查询出正在进行的订单失败");
		}
		return list;

	}

	@Override
	public OrderDTO getPay(String microId, String insId, String orderId)
			throws BusinessServiceException {
		logger.info("根据订单号获取支付相关信息 入参    小微ID："+microId+" 保险公司ID："+insId+" orderId："+orderId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getPay(microId, insId, orderId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "支付信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号获取支付相关信息失败");
		}
		return orderDTO;
	}

	@Override
	public void insertOrderPay(OrderDTO order) throws BusinessServiceException {
		logger.info("插入订单支付信息 入参    OrderDTO："+order);
		try {
			orderDao.insertOrderPay(order);
		} catch (Exception e) {
			logger.error("插入订单支付信息失败:" + e,e);
			throw new BusinessServiceException("插入订单支付信息失败");
		}
	}

	@Override
	public List<OrderDTO> selectByMicroId(String microId)
			throws BusinessServiceException {
		logger.info("查询小微所有的订单信息 入参    小微ID："+microId);
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		try {
			list = orderDao.selectOrderByMicroId(microId);
		} catch (Exception e) {
			logger.error("查询小薇所有的订单失败:" + e,e);
			throw new BusinessServiceException("插入小微所有的订单信息失败");
		}
		return list;
	}

	@Override
	public PageBean<OrderDTO> selectByMicroId_v2(PageParam pageParam)throws BusinessServiceException {
		logger.info("根据小微查询订单信息 入参    PageParam："+pageParam);
		PageBean<OrderDTO> page = new PageBean<OrderDTO>();
		int recordCount = 0;
		List<OrderDTO> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		
		try {
			logger.info("查询条数");
			recordCount = orderDao.selectOrderByMicroIdPageCount(pageParam);
			logger.info("查询订单信息");
			dataList = orderDao.selectOrderByMicroIdPage(pageParam);
		} catch (Exception e) {
			logger.error("查询数据失败:" + e,e);
			throw new BusinessServiceException("查询数据失败");
		}
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);

		return page;
	}
	
	@Override
	public List<SyntheticalDTO> selectByItems(Map<String, Object> items)
			throws BusinessServiceException {
		logger.info("根据条件查询待核保 入参    items："+items);
		List<SyntheticalDTO> list = new ArrayList<SyntheticalDTO>();
		try {
			list = orderDao.selectByItems(items);
		} catch (Exception e) {
			logger.error("查询订单信息失败" + e,e);
			throw new BusinessServiceException("根据条件查询待核保失败");
		}
		return list;
	}
	
	@Override
	public PageBean<SyntheticalDTO> selectByItems(PageParam pageParam) throws BusinessServiceException {
		logger.info("查询核保数据 入参    pageParam："+pageParam);
		PageBean<SyntheticalDTO> page = new PageBean<SyntheticalDTO>();
		int recordCount = 0;
		List<SyntheticalDTO> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		
		try {
			recordCount = orderDao.selectByItemsPageCount(pageParam);
			dataList = orderDao.selectByItemsPage(pageParam);
		} catch (Exception e) {
			logger.error("查询核保数据失败:" + e,e);
			throw new BusinessServiceException("查询核保数据失败");
		}
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);
		return page;
	}
	
	@Override
	public OrderDTO selectOrderByOrderIdAndInsId(String orderId, String insId)
			throws BusinessServiceException {
		logger.info("根据订单号和保险公司id获取订单信息 入参    orderId："+orderId+"  insId："+insId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.selectOrderByOrderIdAndInsId(orderId, insId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号和保险公司id 获取订单信息失败");
		}
		return orderDTO;
	}

	@Override
	public OrderDTO selectByMicroIdAndOrderId(String microId, String orderId,
			String insId) throws BusinessServiceException {
		logger.info("根据小微ID，订单ID和保险公司ID获取订单信息 入参    小微ID："+microId+"  orderId："+orderId+"  保险公司ID："+insId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.selectByMicroIdAndOrderId(microId, orderId, insId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "信息失败:" + e,e);
			throw new BusinessServiceException("根据小微ID，订单ID和保险公司ID获取订单信息失败");
		}
		return orderDTO;
	}

	@Override
	public OwnerDTO getOwnerByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("根据询价单号获取车主信息 入参    询价单号："+inquiryId);
		OwnerDTO ownerDTO =null;
		try {
			ownerDTO = orderDao.getOwnerByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单号:" + inquiryId + "查询车主信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单号获取车主信息失败");
		}
		return ownerDTO;
	}

	@Override
	public OrderDTO selectNameAndMoney(String insId, String orderId)
			throws BusinessServiceException {
		logger.info("根据orderId 获取到付款方和付款金额 入参    保险公司ID："+insId+"  orderId："+orderId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.selectNameAndMoney(insId, orderId);
		} catch (Exception e) {
			logger.error("订单:" + orderId + "查询车主信息失败:" + e,e);
			throw new BusinessServiceException("根据orderId 获取到付款方和付款金额失败");
		}
		return orderDTO;
	}

	// @Override
	// public Long getByInsert(QuotaDTO quota) {
	// try {
	// return orderDao.getByInsert(quota);
	// } catch (Exception e) {
	// logger.error("参数.insId:" + insId + ",orderId:" + orderId + ".查询车主信息异常.");
	// }
	// }

	@Override
	public OrderDTO organizeOrder(String userId, String orderId,
			QuotaDTO quota, VoteInsuranceReturnDTO voteReturn)
			throws ParseException, Exception {
		logger.info("组装订单相关信息 入参    用户ID："+userId+"  orderId："+orderId+"  QuotaDTO："+quota+"  VoteInsuranceReturnDTO："+voteReturn);
		OrderDTO order = new OrderDTO();
		order.setInquiry(quota.getInquiry());
		order.setQuota(quota);
		if(null!=voteReturn){
			order.setNoticeNo(voteReturn.getNoticeNo());
			order.setCarApplyNo(voteReturn.getCarApplyNo());
			order.setVciApplyNo(voteReturn.getVciApplyNo());
			order.setTciApplyNo(voteReturn.getTciApplyNo());
			order.setUnderTime(new Date());
			order.setQueryStage(voteReturn.getStatus());
			order.setVciPlyNo(voteReturn.getVciPlyNo());
			order.setTciPlyNo(voteReturn.getTciPlyNo());
			if(StringUtils.isNotBlank(voteReturn.getVciPlyNo()) || StringUtils.isNotBlank(voteReturn.getTciPlyNo())){			
				order.setPlyCrtTime(new Date());
			}
		}
		order.setCreatTime(new Date());
		order.setCrtCode(userId);
		order.setUpdCode(userId);
		order.setInquiry(quota.getInquiry());
		order.setInsurance(quota.getInsurance());
		order.setMicro(microDao.getMicroByUserId(userId));
		order.setOrderId(orderId);
		order.setQuota(quota);
		order.setStatus("1");
		return order;
	}

	@Override
	public ReportDTO selectMon(String insId, String microId, String orderId)
			throws BusinessServiceException {
		logger.info("根据订单号 获取保费，车牌号，被保人，小微id 入参    保险公司ID："+insId+"  小微ID："+microId+"  orderId："+orderId);
		ReportDTO reportDTO = null;
		try {
			reportDTO = orderDao.selectMon(insId, microId, orderId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "报表信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号 获取保费，车牌号，被保人，小微id失败");
		}
		return reportDTO;

	}

	@Override
	public ReportDTO selectMicroFeeRate(String insId, String microId)
			throws BusinessServiceException {
		logger.info("获取小微费率及中介id 入参    保险公司ID："+insId+"  小微ID："+microId);
		ReportDTO reportDTO = null;
		try {
			reportDTO = orderDao.selectMicroFeeRate(insId, microId);
		} catch (Exception e) {
			logger.error("参数.insId:" + insId + ",microId:" + microId
					+ ".查询报表信息.",e);
			throw new BusinessServiceException("获取小微费率及中介id失败");
		}
		return reportDTO;
	}

	@Override
	public void insertReport(String insId, String orderId, String userId)
			throws BusinessServiceException {
		logger.info("订单号：" + orderId + ",插入报表信息 入参    保险公司ID："+insId+"  orderId："+orderId+"  用户ID："+userId);
		try {
			MicroDTO micro = microDao.getMicroByUserId(userId);
			ReportDTO reportDTO = this.selectMon(insId, micro.getMicro_id(),orderId);
			ReportDTO reportDTO2 = this.selectMicroFeeRate(reportDTO.getInsId(), reportDTO.getMicroId());
			if (null != reportDTO && null != reportDTO2) {
				ReportDTO reportDTO3 = this.selectAgentFeeRate(
						reportDTO2.getInsId(), reportDTO2.getAgentId());
				if (null != reportDTO3) {
					double total = reportDTO.getTax() + reportDTO.getTciPrem()
							+ reportDTO.getVciPrem();
					reportDTO.setPrem(Double.toString(total));
					reportDTO.setMicroFee(reportDTO2.getMicroFee());
					reportDTO.setMicroComm(Double.toString(total
							* reportDTO2.getMicroFee() / 100));
					reportDTO.setAgentFee(reportDTO3.getAgentFee());
					reportDTO.setAgentComm(Double.toString(total
							* reportDTO3.getAgentFee() / 100));
					reportDTO.setUpdCode(userId);
					reportDTO.setCrtCode(userId);
					reportDTO.setStatus("1");
					Integer number = this.selectTreport(reportDTO.getOrderId(),
							reportDTO.getInsId());
					if (number.equals(0)) {
						try {
							orderDao.insertReport(reportDTO);
						} catch (Exception e) {
							logger.error("插入订单：" + orderId + "的业绩信息失败:" + e,e);
							throw new BusinessServiceException("插入订单：" + orderId + "的业绩信息失败");
						}
						logger.info("订单号：" + orderId + ",插入业绩信息成功");
					} else {
						logger.warn("订单号：" + orderId
								+ ",插入业绩信息失败,数据库中已经拥有此订单的业绩信息");
					}
				} else {
					logger.warn("订单号：" + orderId + ",插入业绩信息失败,没有查询到小薇:"
							+ userId + "所属中介的费率信息");
				}
			} else {
				logger.warn("订单号：" + orderId + ",插入业绩信息失败,没有查询到小薇:" + userId
						+ "的费率信息");
			}
		} catch (Exception e) {
			logger.error("插入业绩信息失败",e);
			throw new BusinessServiceException("查询小薇信息失败:" + e);
		}
	}

	@Override
	public ReportDTO selectAgentFeeRate(String insId, String agentId)
			throws BusinessServiceException {
		logger.info("根据中介id 和保险公司 获取中介的费率 入参    保险公司ID："+insId+"  中介公司ID："+agentId);
		ReportDTO reportDTO = null;
		try {
			reportDTO = orderDao.selectAgentFeeRate(insId, agentId);
		} catch (Exception e) {
			logger.error("插入中介公司：" + agentId + "的费率信息失败:" + e,e);
			throw new BusinessServiceException("根据中介id 和保险公司 获取中介的费率失败");
		}
		return reportDTO;
	}

	@Override
	public OrderDTO getOrderById(String microId, String insId, String orderId)
			throws BusinessServiceException {
		logger.info("获取订单信息 入参    小微ID："+microId+"  保险公司ID："+insId+"  orderId："+orderId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getOrderById(microId, insId, orderId);
		} catch (Exception e) {
			logger.error("获取订单：" + orderId + "信息失败:" + e,e);
			throw new BusinessServiceException("获取订单信息失败");
		}
		return orderDTO;
	}

	@Override
	public Integer selectTreport(String orderId, String insId)
			throws BusinessServiceException {
		logger.info("查询t_report表中是否有相同的数据 入参    orderId："+orderId+"  保险公司ID："+insId);
		Integer i = 0;
		try {
			i = orderDao.selectTreport(orderId, insId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "是否有相同的报表信息失败:" + e,e);
			throw new BusinessServiceException("查询t_report表中是否有相同的数据失败");
		}
		return i;
	}

	@Override
	public PagingResult<ReportDTO> selectAllMicroReportByPage(
			Map<String, Object> condition) throws BusinessServiceException {
		logger.info("根据保险公司条件查询中介所有完成订单的报表 入参    condition："+condition);
		PagingResult<ReportDTO> reportDTOs = null;
		try {
			reportDTOs = orderDao.selectAllMicroReportByPage(condition);
		} catch (Exception e) {
			logger.error("查询订单的报表信息失败:" + e);
			throw new BusinessServiceException("根据保险公司条件查询中介所有完成订单的报表失败");
		}
		return reportDTOs;
	}

	@Override
	public ReportDTO selectDetailReport(String orderId)
			throws BusinessServiceException {
		logger.info("根据订单id查询当前订单的详情 入参    orderId："+orderId);
		ReportDTO reportDTO = null;
		try {
			reportDTO = orderDao.selectDetailReport(orderId);
		} catch (Exception e) {
			logger.error("查询订单:" + orderId + "的详细报表信息失败:" + e,e);
			throw new BusinessServiceException("根据订单id查询当前订单的详情失败");
		}
		return reportDTO;
	}

//	@Override
//	@Transactional(rollbackFor = { Exception.class })
//	public ReportDTO deleteOrderByQuotaId(String quotaId, String microId, String insId)
//			throws BusinessServiceException {
//		try {
//			ownerDao.deleteByQuotaId(quotaId, microId, insId);
//			disDao.deleteByQuotaId(quotaId, microId, insId);
//			recDao.deleteByQuotaId(quotaId, microId, insId);
//			voteDao.deleteByQuotaId(quotaId, microId, insId);
//			queryDao.deleteByQuotaId(quotaId, microId, insId);
//			orderDao.deleteByQuotaId(quotaId, microId, insId);
//		} catch (Exception e) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			logger.error("根据报价单:" + quotaId + "删除订单信息失败:" + e);
//			throw new BusinessServiceException("删除订单信息有误！！");
//		}
//		return null;
//	}

	@Override
	public Integer getCountByQuotaId(String quotaId, String microId, String insId)
			throws BusinessServiceException {
		logger.info("根据报价单号查询订单条数 入参    报价单号："+quotaId+"  小微ID："+microId+"  保险公司ID："+insId);
		Integer i = 0;
		try {
			i = orderDao.getCountByQuotaId(quotaId, microId, insId);
		} catch (Exception e) {
			logger.error("根据报价单:" + quotaId + "删除订单信息失败:" + e,e);
			throw new BusinessServiceException("根据报价单号查询订单条数失败");
		}
		return i;
	}

	@Override
	public void updateOrderStatusByInquiryId(String status, String inquiryId)
			throws BusinessServiceException {
		logger.info("更新订单状态为无效 入参    status："+status+"  询价单号："+inquiryId);
		try {
			orderDao.updateOrderStatusByInquiryId(status, inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单:" + inquiryId + "更新订单状态为:"+status+"失败:" + e,e);
			throw new BusinessServiceException("更新订单状态为无效失败");
		}
	}

	@Override
	public OrderDTO getOrderByQuotaId(String quotaId, String microId,
			String insId) throws BusinessServiceException {
		logger.info(" 根据订单号查找订单 入参    报价单号："+quotaId+"  小微ID："+microId+"  保险公司ID："+insId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getOrderByQuotaId(quotaId, microId, insId);
		} catch (Exception e) {
			logger.error("根据报价单:" + quotaId + "查询订单信息失败:" + e,e);
			throw new BusinessServiceException("根据订单号查找订单失败");
		}
		return orderDTO;
	}

	@Override
	public OrderDTO getOrderByCarApplyNo(String microId, String carApplyNo)
			throws BusinessServiceException {
		logger.info("根据联合投保单号获取订单信息 入参    小微ID："+microId+"  联合投保单号："+carApplyNo);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getOrderByCarApplyNo(microId, carApplyNo);
		} catch (Exception e) {
			logger.error("根据投保单号:" + carApplyNo + "查询订单信息失败:" + e,e);
			throw new BusinessServiceException("根据联合投保单号获取订单信息失败");
		}
		return orderDTO;
	}

	@Override
	public void updatePay(OrderDTO order) throws BusinessServiceException {
		logger.info("更新订单支付的信息 入参    OrderDTO："+order);
		try {
			orderDao.updatePay(order);
		} catch (Exception e) {
			logger.error("根据订单:" + order.getOrderId() + "更新支付信息失败:" + e,e);
			throw new BusinessServiceException("更新订单支付的信息失败");
		}
	}
	@Override
	public void getOrderByInquiryId(String inquiryId) throws BusinessServiceException {
		logger.info("获取订单信息 入参    询价单号："+inquiryId);
		try {
			orderDao.getOrderByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单:" + inquiryId + "获取订单信息失败:" + e,e);
			throw new BusinessServiceException("获取订单信息失败");
		}
	}
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
			throws BusinessServiceException {
		List<OrderDTO> list = null;
		try {
			list = orderDao.peopleAaskDetailDAO(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单:" + inquiryId + " 人工报价明细信息失败:" + e);
		}
		return list;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateOrderAll(String userId,String insId,OrderDTO order, DistributionDTO dis,
			OwnerDTO owner, RecognizeeDTO rec,
			VoteInsuranceDTO vote) throws BusinessServiceException {
		logger.info("更新订单信息 入参    用户ID："+userId+"  保险公司ID："+insId+" OrderDTO："+order+"  DistributionDTO："+dis
				+"  OwnerDTO："+owner+"  RecognizeeDTO：rec"+"  VoteInsuranceDTO："+vote);
		try {
			String quotaId=order.getQuota().getQuotaId();
			String microId = microDao.getMicroByUserId(userId).getMicro_id();
			orderDao.updateByOrderId(order);
			if(null!=dis){
				logger.info("删除保单配送信息");
				disDao.deleteByQuotaId(quotaId, microId, insId);
				disDao.insert(dis);
			}
			if(null!=owner){
				logger.info("更新车主信息");
//				ownerDao.deleteByQuotaId(quotaId, microId, insId);
//				ownerDao.insert(owner);
				ownerDao.update(quotaId, microId, insId, owner);
			}
			if(null!=rec){
				logger.info("删除被保人信息");
				recDao.deleteByQuotaId(quotaId, microId, insId);
				recDao.insert(rec);
			}
			if(null!=vote){
				logger.info("删除投保人信息");
				voteDao.deleteByQuotaId(quotaId, microId, insId);
				voteDao.insert(vote);
			}
//			if (null != query) {
//				logger.info("删除订单查询信息");
//				queryDao.deleteByQuotaId(quotaId, microId, insId);
//				queryDao.insert(query);
//			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.error("添加订单信息失败" + e,e);
			throw new BusinessServiceException("更新订单信息失败！！");
		}
	}

	@Override
	public String getOrderStatusByOrderId(String orderId)
			throws BusinessServiceException {
		logger.info("获取订单状态 入参    orderId："+orderId);
		String orderStatus = "";
		try {
			orderStatus = orderDao.getOrderStatusByOrderId(orderId);
		} catch (Exception e) {
			logger.error("获取订单:" + orderId + " 状态信息失败:" + e,e);
			throw new BusinessServiceException("获取订单状态失败");
		}
		return orderStatus;
	}

	@Override
	public void updatePolicyNoAndQueryState(OrderDTO order)
			throws BusinessServiceException {
		logger.info("更新保单号和订单当前状态 入参    OrderDTO："+order);
		try {
			
			try {
				OrderDTO orderDTO = orderDao.selectByMicroIdAndOrderId(null, order.getOrderId(), null);
//				OrderQueryDTO orderQuery = orderQueryService.selectOrderByOrderIdAndInsId(order.getOrderId(), order.getInsurance().getInsId());
				if(null != orderDTO){
					if("6".equals(orderDTO.getQueryStage()) && 
							("2".equals(order.getQueryStage())
							||"3".equals(order.getQueryStage())
							||"4".equals(order.getQueryStage())
							||"5".equals(order.getQueryStage())
							||"14".equals(order.getQueryStage()))){
						logger.info("当前订单状态为已出单6,需要更新的状态为"+order.getQueryStage()+"不予更新");
					}
					else{
						orderDao.updateOrderStatusByOrderId(order.getQueryStage(), order.getOrderId());
//						queryDao.updateQueryStage(order.getOrderId(), order.getInsurance().getInsId(), order.getQueryStage());
					}
					policyBaseService.addPolicyBase(order.getOrderId(), orderDTO.getInsurance().getInsId());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
//			if("6".equals(order.getQueryStage())){//状态为6，保单生成，更新保单生成时间
//				order.setPlyCrtTime(new Date());
//			}
			orderDao.updateByOrderId(order);
			
			
			//common
//			if("6".equals(order.getQueryStage())){
//				logger.info("状态为6，保单生成，执行公共动作");
//				commonCarInsurance.insureSucc(order.getInsurance().getInsId(), order.getOrderId());
//			}
		} catch (Exception e) {
			logger.error("更新订单:" + order.getOrderId() + " 状态信息失败:" + e,e);
			throw new BusinessServiceException("更新保单号和订单当前状态失败");
		}
	}

	@Override
	public OrderDTO getOrderByQuotaId2(String quotaId) 
			throws BusinessServiceException {
		logger.info("根据保单号获取订单信息 入参    报价单ID："+quotaId);
		OrderDTO orderDTO = null;
		try{
			orderDTO = orderDao.getOrderByQuotaId2(quotaId);
		}catch (Exception e) {
			logger.error("获取订单信息失败:" + e,e);
			throw new BusinessServiceException("根据保单号获取订单信息失败");
		}
		return orderDTO;
	}

	@Override
	public void updateOrderStatusByOrderId(String queryStage, String orderId)
			throws BusinessServiceException {
		logger.info("OrderServiceImpl  updateOrderStatusByOrderId  更新订单状态    入参    queryStage："+queryStage+"  orderId："+orderId);
		//------------------------------------------更新订单状态-----------------------------------------------------
		OrderDTO orderDTO = this.getOrderById(null, null, orderId);
		if(null != orderDTO && null != orderDTO.getQueryStage() && orderDTO.getQueryStage().equals(queryStage)){
			logger.info("数据库状态["+orderDTO.getQueryStage()+"],传入状态["+queryStage+"]相等不更新");
			return;
		}
		try{
			logger.info("更新订单状态");
			orderDao.updateOrderStatusByOrderId(queryStage, orderId);	
		}catch (Exception e) {
			logger.error("更新订单状态失败，订单号为：" + orderId,e);
			throw new BusinessServiceException("更新订单状态失败");
		}
		//-----------------------------------------生成保单后续操作---------------------------------------------------
		if("6".equals(queryStage)){//状态为6，保单生成
			String inquiryId= "";
			String insId = "";
			try {			
				logger.info("根据订单号获取询价单号和保险公司ID，订单号为："+orderId);
				Map<String,Object> map = orderDao.getInquiryIdByOrderId(orderId);
				if(null != map){
					inquiryId = CommonUtil.valueOf(map.get("inquiryId"));
					insId = CommonUtil.valueOf(map.get("insId"));
				}
			} catch (Exception e) {
				logger.error("根据订单号获取询价单号和保险公司ID失败，订单号为："+orderId,e);
			}
			if(StringUtils.isNotBlank(inquiryId)){
				try {
					logger.info("状态为6，保单生成，添加资源信息");
					resourceVehicleService.insert(inquiryId);
				} catch (Exception e) {
					logger.error("资源信息添加失败，订单号为："+orderId,e);
				}
				try {						
					logger.info("更新询价单状态");
					inquiryDAO.updateInquiryStatusByInquiryId("3", inquiryId, null);
				} catch (Exception e) {
					logger.error("更新询价单状态失败，订单号为："+orderId,e);
				}
				try {					
					logger.info("更新保单生成时间");
					orderDao.updatePlyCrtTimeByOrderId(orderId);
				} catch (Exception e) {
					logger.error("更新保单生成时间失败，订单号为："+orderId,e);
				}
				if(StringUtils.isNotBlank(insId)){
					logger.info("状态为6，保单生成，执行公共动作");
					commonCarInsurance.insureSucc(insId,orderId);
				}
			}
		}
	}

	@Override
	public void updateOrder(OrderDTO orderDTO) throws BusinessServiceException {
		logger.info("更新订单信息 入参    OrderDTO："+orderDTO);
		try {
			orderDao.updateByOrderId(orderDTO);
		} catch (Exception e) {
			logger.error("更新订单信息失败："+e,e);
			throw new BusinessServiceException("更新订单信息失败");
		}
	}

	@Override
	public void updateNoticeNoByOrderId(String orderId, String noticeNo,String updCde)
			throws BusinessServiceException {
		logger.info("根据订单编码更新订单号 入参    orderId："+orderId+"  noticeNo："+noticeNo+"   修改人："+updCde);
		try {
			orderDao.updateNoticeNoByOrderId(orderId, noticeNo,updCde);;
		} catch (Exception e) {
			logger.error("更新订单号失败："+e,e);
			throw new BusinessServiceException("根据订单编码更新订单号失败");
		}
	}

	@Override
	public OrderDTO getOrderByNoticeNo(String userId,String noticeNo) throws BusinessServiceException {
		logger.info("根据字段noticeNo查询订单信息 入参    用户ID："+userId+"  noticeNo："+noticeNo);
		OrderDTO orderDto = null;
		try {
			String microId = null;
			try {
				if(null != userId){
					microId = microDao.getMicroByUserId(userId).getMicro_id();
				}
			} catch (Exception e) {
			}
			orderDto = orderDao.getOrderByNoticeNo(microId, noticeNo);
			if(null == userId){
				userId = orderDto.getCrtCode();
			}
		} catch (Exception e) {
			logger.error("更新订单号失败："+e,e);
			throw new BusinessServiceException("根据字段noticeNo查询订单信息失败");
		}
		return orderDto;
	}

	@Override
	public OrderDTO getOrderIdByApplyNo(String applyNo)
			throws BusinessServiceException {
		logger.info("根据商业投保单号或交强投保单号得到保单信息 入参    applyNo："+applyNo);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getOrderIdByApplyNo(applyNo);
		} catch (Exception e) {
			logger.error(" 根据商业投保单号或交强投保单号查询保单失败："+e,e);
			throw new BusinessServiceException("根据商业投保单号或交强投保单号得到保单信息失败");
		}
		return orderDTO;
	}

	@Override
	public OrderDTO getAppNoByOrderId(String orderId)
			throws BusinessServiceException {
		logger.info("根据订单号获取投保单号 入参    orderId："+orderId);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getAppNoByOrderId(orderId);
		} catch (Exception e) {
			logger.error("根据订单号获取投保单号失败："+e,e);
			throw new BusinessServiceException("根据订单号获取投保单号失败");
		}
		return orderDTO;
	}

	@Override
	public OrderDTO getOrderByCarApplyNo(String carApplyNo) throws BusinessServiceException {
		logger.info("根据联合投保单号获取订单信息 入参     联合投保单号："+carApplyNo);
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderDao.getOrderByCApplyNo(carApplyNo);
		} catch (Exception e) {
			logger.error("根据投保单号:" + carApplyNo + "查询订单信息失败:" + e,e);
			throw new BusinessServiceException("根据联合投保单号获取订单信息失败");
		}
		return orderDTO;
	}

	@Override
	public void createPolicyOperation(String orderId, String tciPlyNo,
			String vciPlyNo, OrderDTO order) throws BusinessServiceException {
		logger.info("OrderServiceImpl  updateOrderStatusByOrderId  出单更新信息    入参    orderId："+orderId+"  tciPlyNo："+tciPlyNo+"  vciPlyNo："+vciPlyNo);
		//------------------------------------------生成保单操作-----------------------------------------------------
		try {
			
//			OrderDTO orderDTO = this.getOrderById(null, null, orderId);
//			if(null != orderDTO && null != orderDTO.getQueryStage() && orderDTO.getQueryStage().equals("6")){
//				logger.info("生成保单,数据库状态["+orderDTO.getQueryStage()+"]不重复更新");
//				orderDao.createPolicyOperation(orderId, tciPlyNo, vciPlyNo);
//				return;
//			}
			orderDao.createPolicyOperation(orderId, tciPlyNo, vciPlyNo);
			orderDao.updateOrderStatusByOrderId("6", orderId);
			
		} catch (Exception e) {
			logger.error("回写保单号失败，订单号为："+orderId,e);
			throw new BusinessServiceException("回写保单号失败");
		}
		//-----------------------------------------生成保单后续操作---------------------------------------------------
		String inquiryId= "";
		String insId = "";
		try {
			logger.info("根据订单号获取询价单号和保险公司ID，订单号为："+orderId);
			Map<String,Object> map = orderDao.getInquiryIdByOrderId(orderId);
			if(null != map){
				inquiryId = CommonUtil.valueOf(map.get("inquiryId"));
				insId = CommonUtil.valueOf(map.get("insId"));
			}
		} catch (Exception e) {
			logger.error("根据订单号获取询价单号和保险公司ID失败，订单号为："+orderId,e);
		}
		if(StringUtils.isNotBlank(inquiryId)){
			try {
				//状态为6，保单生成，添加资源信息
				resourceVehicleService.insert(inquiryId);
			} catch (Exception e) {
				logger.error("资源信息添加失败，订单号为："+orderId,e);
			}
			try {						
				//更新询价单状态
				inquiryDAO.updateInquiryStatusByInquiryId("3", inquiryId, null);
				//更新询价单下报价单的状态
				orderDao.updateQuoteByInquiryId(inquiryId);
				//更新询价单下订单的状态
				orderDao.updateOrderByInquiryId(inquiryId);
			} catch (Exception e) {
				logger.error("更新询价单状态失败，订单号为："+orderId,e);
			}
		}
		
		//华海保险有线下支付，可能导致线上和线下保费不一致的情况
		updateHHBXPrm(order);
		
		
		if(StringUtils.isNotBlank(insId)){			
			//状态为6，保单生成，执行公共动作
			commonCarInsurance.insureSucc(insId,orderId);
		}
		
		try {
			String compCode = orderDao.getCompCodeByOrderId(orderId);
			OrderDTO orderDTO = this.getOrderById(null, null, orderId);
			//开始回写数据到业管系统
			if(StringUtils.isNotBlank(tciPlyNo)){
				InsuranceBdGenInfo tci = new InsuranceBdGenInfo();
				tci.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				tci.setBdNo(tciPlyNo);
				tci.setBdType("1");
				tci.setInsId(orderDTO.getInsurance().getInsId());
				tci.setCompCode(compCode);
				orderDao.insertInsuranceBdGenInfo(tci);
			}
			if(StringUtils.isNotBlank(vciPlyNo)){
				InsuranceBdGenInfo vci = new InsuranceBdGenInfo();
				vci.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				vci.setBdNo(vciPlyNo);
				vci.setBdType("0");
				vci.setCompCode(compCode);
				vci.setInsId(orderDTO.getInsurance().getInsId());
				orderDao.insertInsuranceBdGenInfo(vci);
			}
		} catch (Exception e) {
			logger.error("保单号回写业管表失败", e);
		}
		
	}
	
	//华海保险有线下支付，可能导致线上和线下保费不一致的情况
	private void updateHHBXPrm(OrderDTO order){
		try {
			if(null != order && null != order.getInsurance() && "HHBX".equals(order.getInsurance().getInsId()) && MapUtils.isNotEmpty(order.getMap())){
				logger.info("更新华海线上保费");
				OrderDTO orderDTO = getOrderById(null, "HHBX", order.getOrderId());
				if(null == orderDTO){
					logger.info("订单信息不存在");
				}
				else{
					
					//原保费
					BigDecimal vehicleTax = new BigDecimal("0.00");//车船税
					BigDecimal VCIPremTax = new BigDecimal("0.00");//商业保费
					BigDecimal TCIPremTax = new BigDecimal("0.00");//交强保费
					if(null != orderDTO.getQuota().getVehicleTax()){
						vehicleTax = vehicleTax.add(orderDTO.getQuota().getVehicleTax());
					}
					if(null != orderDTO.getQuota().getVCIPremTax()){
						VCIPremTax = VCIPremTax.add(orderDTO.getQuota().getVCIPremTax());
					}
					if(null != orderDTO.getQuota().getTCIPremTax()){
						TCIPremTax = TCIPremTax.add(orderDTO.getQuota().getTCIPremTax());
					}
					logger.info("线上原保费,车船税:"+vehicleTax+",商业保费:"+VCIPremTax+",交强保费:"+TCIPremTax);
					
					//华海查询到的保费
					Map<String, Object> map = order.getMap();
					BigDecimal vehicleTaxm = new BigDecimal("0.00");//车船税
					BigDecimal VCIPremTaxm = new BigDecimal("0.00");//商业保费
					BigDecimal TCIPremTaxm = new BigDecimal("0.00");//交强保费
					if(null != map.get("taxm") && StringUtils.isNotBlank(map.get("taxm").toString())){
						vehicleTaxm = vehicleTaxm.add(new BigDecimal(map.get("taxm").toString()));
					}
					if(null != map.get("vcim") && StringUtils.isNotBlank(map.get("vcim").toString())){
						VCIPremTaxm = VCIPremTaxm.add(new BigDecimal(map.get("vcim").toString()));
					}
					if(null != map.get("tcim") && StringUtils.isNotBlank(map.get("tcim").toString())){
						TCIPremTaxm = TCIPremTaxm.add(new BigDecimal(map.get("tcim").toString()));
					}
					logger.info("华海查询到的保费,车船税:"+vehicleTaxm+",商业保费:"+VCIPremTaxm+",交强保费:"+TCIPremTaxm);
					
					//更新的保费
					BigDecimal vehicleTaxu = null;//车船税
					BigDecimal VCIPremTaxu = null;//商业保费
					BigDecimal TCIPremTaxu = null;//交强保费
					if(!vehicleTax.equals(vehicleTaxm)){
						vehicleTaxu = vehicleTaxm;
					}
					if(!VCIPremTax.equals(VCIPremTaxm)){
						VCIPremTaxu = VCIPremTaxm;
					}
					if(!TCIPremTax.equals(TCIPremTaxm)){
						TCIPremTaxu = TCIPremTaxm;
					}
					logger.info("更新的保费,车船税:"+vehicleTaxu+",商业保费:"+VCIPremTaxu+",交强保费:"+TCIPremTaxu);
					if(null != vehicleTaxu || null != VCIPremTaxu || null != TCIPremTaxu){
						int c = quotaDAO.updateQuotePrm(vehicleTaxu, VCIPremTaxu, TCIPremTaxu, orderDTO.getQuota().getQuotaId());
						logger.info("更新成功,影响行数"+c);
					}
					else{
						logger.info("华海查询的保费和线上的保费一致，无需更新");
					}
					
					//更新投保人 被保人 车主  险别信息
					try {
						InquiryDTO inquiryDTO = inquiryDAO.getInquiryVehicleByInquiryId(orderDTO.getInquiry().getInquiryId());
						if(null != inquiryDTO && StringUtils.isNotBlank(inquiryDTO.getFrmNo())){
							HashMap<String, Object> paramMap = new HashMap<String, Object>(2);
							paramMap.put("HHBX_COPERID", CallActionImpl.hhbxCMap.get("HHBX_COPERID"));
							paramMap.put("HHBX_CPASSWD", CallActionImpl.hhbxCMap.get("HHBX_CPASSWD"));
							
							ApplyDetailQueryReqDTO r = new ApplyDetailQueryReqDTO();
							r.setcAppNo(StringUtils.isNotBlank(orderDTO.getVciApplyNo())?orderDTO.getVciApplyNo():orderDTO.getTciApplyNo());
							r.setcPlateNo(inquiryDTO.getPlateNo());
							r.setcFrmNo(inquiryDTO.getFrmNo());
							
							ApplyDetailBaseRespDTO ad = huahaiTotalAction.applyDetailQuery(r, paramMap);
							logger.info("投保单详情"+ToStringBuilder.reflectionToString(ad, ToStringStyle.SHORT_PREFIX_STYLE));
							if(null != ad && "200".equals(ad.getResultCode())){
								ApplyDetailPersonDTO emptyPerson = new ApplyDetailPersonDTO();
								ApplyDetailPersonDTO ownerPerson = ad.getOwnerPerson();
								ApplyDetailPersonDTO appPerson = ad.getAppPerson();
								ApplyDetailPersonDTO insurePerson = ad.getInsurePerson();
								ApplyDetailCarDTO car = ad.getCar();
								ArrayList<ApplyDetailCvrgDTO> cvrgList = ad.getCvrgList();//投保商业险的才更新
								
								if(null != ownerPerson && !ownerPerson.equals(emptyPerson)){
									logger.info("更新车主信息");
									int c = ownerDao.updateWithHHBX(ownerPerson, orderDTO.getQuota().getQuotaId());
									logger.info("影响行数"+c);
								}
								if(null != appPerson && !appPerson.equals(emptyPerson)){
									logger.info("更新投保人信息");
									int c = voteDao.updateWithHHBX(appPerson, orderDTO.getQuota().getQuotaId());
									logger.info("影响行数"+c);
								}
								if(null != insurePerson && !insurePerson.equals(emptyPerson)){
									logger.info("更新被保人信息");
									int c = recDao.updateWithHHBX(appPerson, orderDTO.getQuota().getQuotaId());
									logger.info("影响行数"+c);
								}
								if(null != car && !car.equals(new ApplyDetailCarDTO())){
									logger.info("更新车辆信息");
									int c = inquiryDAO.updateCarWithHHBX(car, inquiryDTO.getInquiryId());
									logger.info("影响行数"+c);
								}
								if(!VCIPremTax.equals(VCIPremTaxm) && null != cvrgList && cvrgList.size() > 0){//总保费不一样且华海查询有结果
									logger.info("更新险别信息");
									
									//有效的险种
									ArrayList<ApplyDetailCvrgDTO> updCvrgList = new ArrayList<ApplyDetailCvrgDTO>();
									
									//保行天下记录的险种
									QuotaDetailedDTO dt = quotaDetailedDAO.getDetailed(orderDTO.getQuota().getQuotaId(), "HHBX");
									
									//华海查询出的不计免赔
									BigDecimal bgmp = new BigDecimal("0.00");
									
									//过滤出有效的险种 并计算出不计免赔
									for(ApplyDetailCvrgDTO item : cvrgList){
										if(null != cvrgMap.get(item.getCvrgNo())){
											item.setCvrgNo(cvrgMap.get(item.getCvrgNo()));
											updCvrgList.add(item);
											if(StringUtils.isNotBlank(item.getnDductPrm())){
												bgmp = bgmp.add(new BigDecimal(item.getnDductPrm()));
											}
										}
									}
									if(bgmp.compareTo(new BigDecimal("0.00")) > 0){
										ApplyDetailCvrgDTO bgmpd = new ApplyDetailCvrgDTO();
										bgmpd.setCvrgNo("00000");
										bgmpd.setPrm(bgmp.toString());
										updCvrgList.add(bgmpd);
									}
									
									//更新保费和保额
									if(updCvrgList.size() > 0 && null != dt){
										dt.setInquiry(inquiryDTO);
										int c = quotaDetailedDAO.deleteQuotaDetailedByQuotaId(orderDTO.getQuota().getQuotaId());
										logger.info("删除报价单明细,影响行数"+c);
										List<CoverageItemDTO> oldCvrgs = dt.getCoverageItems();
										List<CoverageItemDTO> cvrgs = new ArrayList<CoverageItemDTO>();
										CoverageItemDTO ci = null;
										for(ApplyDetailCvrgDTO item : updCvrgList){
											ci = new CoverageItemDTO();
											ci.setSumLimit(null != item.getAmt()?new BigDecimal(item.getAmt()):null);
											ci.setCode(item.getCvrgNo());
											ci.setCrtCode("system");
											ci.setAmount(null != item.getPrm()?new BigDecimal(item.getPrm()):null);
											ci.setUpdCode("system");
											cvrgs.add(ci);
										}
										dt.setCoverageItems(cvrgs);
										try {
											c = quotaDetailedDAO.insertDetailed(dt);
											logger.info("插入成功");
										} catch (Exception e) {
											logger.error("插入失败,还原老数据", e);
											dt.setCoverageItems(oldCvrgs);
											c = quotaDetailedDAO.insertDetailed(dt);
											logger.info("还原成功");
										}
										
									}
								}
								
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			logger.error("更新华海保费失败", e);
		}
	}
	
	public static final Map<String, String> cvrgMap = new HashMap<String, String>(9);
	static{
		cvrgMap.put("63", "030006");//机动车损失险
		cvrgMap.put("68", "030018");//第三者责任保险
		cvrgMap.put("73", "030001");//车上人员责任：驾驶人
		cvrgMap.put("89", "030009");//车上人员责任：乘客
		cvrgMap.put("74", "030061");//机动车盗抢险
		cvrgMap.put("34", "030004");//玻璃单独破碎险
		cvrgMap.put("75", "032601");//车身划痕损失险
		cvrgMap.put("36", "030012");//自燃损失险
		cvrgMap.put("90", "033008");//自车辆损失保险无法找到第三方特约险 
	}
	
	@Override
	public void updateInquiryStatusByInquiryId(String status, String inquiryId,
			String userId) throws BusinessServiceException {
		logger.info("OrderServiceImpl  updateInquiryStatusByInquiryId  修改询价单的状态  入参    status："+status+"  inquiryId："+inquiryId+"  userId："+userId);
		try {
			inquiryDAO.updateInquiryStatusByInquiryId("3", inquiryId, null);
		} catch (Exception e) {
			logger.error("根据询价单号:" + inquiryId + "修改询价单状态失败",e);
			throw new BusinessServiceException("根据询价单号:" + inquiryId + "修改询价单状态失败");
		}
	}

	@Override
	public void createPolicyOperation2(String orderId, String tciPlyNo,
			String vciPlyNo) throws BusinessServiceException {
		try {
			orderDao.createPolicyOperation2(orderId, tciPlyNo, vciPlyNo);
		} catch (Exception e) {
			logger.error("添加投保单号失败",e);
			throw new BusinessServiceException("添加投保单号失败");
		}
	}

	@Override
	public boolean callBack(OrderQueryReturnDTO orderQueryReturnDTO) {
		try {
			if("6".equals(orderQueryReturnDTO.getQueryState())){
				this.createPolicyOperation(orderQueryReturnDTO.getOrderId(), orderQueryReturnDTO.getTciPlyNo(), orderQueryReturnDTO.getVciPlyNo(), null);
			}else{
				OrderDTO order = orderDao.getAppNoByOrderId(orderQueryReturnDTO.getOrderId());
				// 有些情况下，还未生成订单的时候，就返回信息了。
				if(order == null || !"6".equals(order.getQueryStage())){				
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setOrderId(orderQueryReturnDTO.getOrderId());
					orderDTO.setCarApplyNo(orderQueryReturnDTO.getCarApplyNo());
					orderDTO.setTciApplyNo(orderQueryReturnDTO.getTciApplyNo());
					orderDTO.setVciApplyNo(orderQueryReturnDTO.getVciApplyNo());
					orderDTO.setQueryStage(orderQueryReturnDTO.getQueryState());
					if(StringUtils.isNotBlank(orderQueryReturnDTO.getTciPlyNo()) || StringUtils.isNotBlank(orderQueryReturnDTO.getVciPlyNo())){
						orderDTO.setTciPlyNo(orderQueryReturnDTO.getTciPlyNo());
						orderDTO.setVciPlyNo(orderQueryReturnDTO.getVciPlyNo());
						orderDTO.setPlyCrtTime(new Date());
					}
					orderDao.updateOrderStatusByOrderId(orderQueryReturnDTO.getQueryState(), orderQueryReturnDTO.getOrderId());
//				queryDao.updateQueryStage(orderQueryReturnDTO.getOrderId(), orderQueryReturnDTO.getInsId(), orderQueryReturnDTO.getQueryState());
					orderDao.updateByOrderId(orderDTO);
					
					Integer i = orderDao.queryOrderApplyStatus(orderQueryReturnDTO.getOrderId());
					if(i > 0)
						orderDao.updateOrderApplyStatus(orderQueryReturnDTO);
					else
						orderDao.insertOrderApplyStatus(orderQueryReturnDTO);
				}
			}
				
		} catch (Exception e) {
			logger.error("回调失败",e);
			return false;
		}
		return true;
	}

	@Override
	public String getInquiryIdByOrderId(String orderId) {
		logger.info("getInquiryIdByOrderId ==> orderId:"+orderId);
		String inquiryId = inquiryDAO.getInquiryIdByOrderId(orderId);
		logger.info("getInquiryIdByOrderId <== inquiryId:"+inquiryId);
		return inquiryId;
	}
	
	@Override
	public String[] getQueryStageByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("根据询价单号返回该询价单下所有的订单状态");
		logger.info("入参  inquiryId： "+inquiryId);
		String [] queryStages = null;
		try {
			queryStages = orderDao.getQueryStageByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单号返回该询价单下所有的订单状态失败",e);
			throw new BusinessServiceException("根据询价单号返回该询价单下所有的订单状态失败");
		}
		return queryStages;
	}
	
	@Override
	public List<OrderDTO> getAllByStage(Date minTime,Integer pageNo,
			Integer pageSize) throws BusinessServiceException{
		logger.info("根据状态得到订单信息");
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		try {
			list = orderDao.getAllByStage(minTime,pageNo,pageSize);
		} catch (Exception e) {
			logger.error("根据状态得到订单信息失败",e);
			throw new BusinessServiceException("根据状态得到订单信息失败");
		}
		return list;
	}
	
	@Override
	public OrderDTO getOrderIdByInquiryIdAndStatus(String inquiryId)
			throws BusinessServiceException {
		logger.info("OrderQueryServiceImpl  getOrderIdByInquiryIdAndStatus 根据询价单号和订单状态获取订单号（5或6状态） 入参   inquiryId："+inquiryId);
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderDao.getOrderIdByInquiryIdAndStatus(inquiryId);
		} catch (Exception e) {
			logger.error("根据询价单号和订单状态获取订单号（5或6状态）失败",e);
			throw new BusinessServiceException("根据询价单号和订单状态获取订单号（5或6状态）失败");
		}
		logger.info("OrderQueryServiceImpl  getOrderIdByInquiryIdAndStatus  出惨    orderDTO："+orderDTO);
		return orderDTO;
	}

	@Override
	public PageBean<SyntheticalDTO> selectByItemsRenew(PageParam pageParam)
			throws BusinessServiceException {
		logger.info("OrderServiceImpl  selectByItems  查询续保数据   入参    pageParam："+pageParam);
		PageBean<SyntheticalDTO> page = new PageBean<SyntheticalDTO>();
		int recordCount = 0;
		List<SyntheticalDTO> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		
		try {
			recordCount = orderDao.selectByItemsRenewPageCount(pageParam);
			dataList = orderDao.selectByItemsRenewPage(pageParam);
		} catch (Exception e) {
			logger.error("查询核保数据失败:" + e,e);
			throw new BusinessServiceException("查询核保数据失败");
		}
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);
		return page;
	}
}
