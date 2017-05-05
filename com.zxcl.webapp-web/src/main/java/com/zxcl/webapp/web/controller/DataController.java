package com.zxcl.webapp.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.car_vio.api.CarVioQueryParamDTO;
import com.zxcl.car_vio.api.CarVioRespDTO;
import com.zxcl.car_vio.service.provider.CarVioQueryProvider;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.timer.TimerOrderStatusQueryService;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.biz.service.BaseService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.ManualQuotationTaskService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.PlatformService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.QuotnConfigService;
import com.zxcl.webapp.biz.service.WalletActiveService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;
import com.zxcl.webapp.dto.BaseDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.ManualQuotationTaskDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.PlatformDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotnConfigDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.webdto.SyntheticalDTO;
import com.zxcl.webapp.web.vo.AjaxResult;

@Controller
@SessionAttributes(WebAttributes.AUTHENTICATION_EXCEPTION)
public class DataController extends BaseController {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MicroService microService;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private CallAction callAction;
    
    @Autowired
    private ParamConfigService paramConfigService;

	@Autowired
	private BaseService baseService;
    
	@Autowired
	private QuotnConfigService quotnConfigService;
	
	@Autowired
	private ManualQuotationTaskService manualQuotationTaskService;
	
	@Autowired
	private CarVioQueryProvider carVioQueryProvider;
	
	@Autowired
	private TimerOrderStatusQueryService timerOrderStatusQueryService;
	
    /**
     * 待核保的信息
     */
    @RequestMapping(value = "/list1.do")
    public String forUnderwriting(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        List<InquiryDTO> inquiryList = null;
        try {
            Map<String, Object> contidion = new HashMap<String, Object>();
            contidion.put("microId", microService.getMicroByUserId(this.getAuthenticatedUserId()).getMicro_id());
            inquiryList = inquiryService.getInquiryListByMicroId(contidion);
        } catch (Exception e) {
            logger.error("查询核保单据失败:" , e);
            inquiryList = new ArrayList<InquiryDTO>();
        }
        model.addAttribute("inquiryList", inquiryList);
        return "data.list1";
    }

    /**
     * 查询基础数据
     */
    @RequestMapping(value = "/CodeClass.do")
    @ResponseBody
    public ResultMap getCodeClass(HttpServletRequest request, HttpServletResponse response, ModelMap model,String codeClass) {
        List<PlatformDTO> list=new ArrayList<PlatformDTO>();
        ResultMap resultMap = new ResultMap();
        try {
            
            list = platformService.getCodeClass(codeClass);
            resultMap.setSuccess(true);
            resultMap.setData(list);
        } catch (Exception e) {
            logger.error("查询代码:"+codeClass+"失败" , e);
            resultMap.setSuccess(false);
            resultMap.setData(list);
            resultMap.setMessage("查询代码:"+codeClass+"失败");
        }
        return resultMap;
    }

    /**
     * 待支付的信息
     * 
     * @return
     */
    @RequestMapping(value = "/list2.do")
    public String waitPay(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.addAttribute("orderList", new ArrayList<OrderDTO>());
        model.addAttribute("IS_USE_YONGCHENG_NEW_INTERFACE", paramConfigService.getValueByKey("IS_USE_YONGCHENG_NEW_INTERFACE"));
        return "data.list2";
    }

    /**
     * 
     * 查询待核保，待申请核保，待支付，待生成保单
     */
    @RequestMapping(value = "/queryWaitInsureApplicate.do")
    @ResponseBody
    public ResultMap queryWaitInsureApplicate(HttpServletRequest request, HttpServletResponse response,
            String queryStage, Integer currPage) {
        ResultMap resultMap = new ResultMap();
        PageParam pageParam = new PageParam(this.getAuthenticatedUserId(), null);
        pageParam.setOperateUser(getAuthenticatedUserId());
        pageParam.setPageSize(30);
        pageParam.setCurrentPage(currPage);
        pageParam.setWd(queryStage);
        PageBean<OrderDTO> dataPage = null;
        try {
            MicroDTO micro = microService.getMicroByUserId(this.getAuthenticatedUserId());
            pageParam.setMicroId(micro.getMicro_id());
            dataPage = orderService.selectByMicroId_v2(pageParam);
            resultMap.setSuccess(true);
            resultMap.setData(dataPage);
        } catch (Exception e) {
            logger.error("查询失败:" , e);
            resultMap.setSuccess(false);
            resultMap.setMessage("查询失败");
        }
        return resultMap;
    }

    /**
     * 根据条件查询保单信息
     * 
     * @return
     */
    @RequestMapping(value = "/queryList3ByItem.do")
    @ResponseBody
    public ResultMap queryList3ByItem(HttpServletRequest request, HttpServletResponse response, String queryCondition,
            Integer currPage) {
        ResultMap resultMap = new ResultMap();
        PageParam pageParam = new PageParam(this.getAuthenticatedUserId(), null);
        pageParam.setOperateUser(getAuthenticatedUserId());
        pageParam.setPageSize(30);
        pageParam.setCurrentPage(currPage);
        pageParam.setWd(queryCondition);
        PageBean<SyntheticalDTO> dataPage = null;

        try {
            MicroDTO micro = microService.getMicroByUserId(pageParam.getOperateUser());
            pageParam.setMicroId(micro.getMicro_id());
            dataPage = orderService.selectByItems(pageParam);
            resultMap.setSuccess(true);
            resultMap.setData(dataPage);
        } catch (Exception e) {
            resultMap.setSuccess(false);
            resultMap.setMessage("查询保单信息失败");
            logger.error("用户:" + getAuthenticatedUserId() + "查询保单信息失败:" , e);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/queryList4ByItem.do")
    @ResponseBody
    public ResultMap queryList4ByItem(HttpServletRequest request, HttpServletResponse response, String queryCondition,
            Integer currPage) {
         ResultMap resultMap = new ResultMap();
         PageParam pageParam = new PageParam(this.getAuthenticatedUserId(), null);
         pageParam.setOperateUser(getAuthenticatedUserId());
         pageParam.setPageSize(30);
         pageParam.setCurrentPage(currPage);
         pageParam.setWd(queryCondition);
         PageBean<SyntheticalDTO> dataPage = null;
         try {
             MicroDTO micro = microService.getMicroByUserId(pageParam.getOperateUser());
             pageParam.setMicroId(micro.getMicro_id());
             dataPage = orderService.selectByItemsRenew(pageParam);
             resultMap.setSuccess(true);
             resultMap.setData(dataPage);
         } catch (Exception e) {
             resultMap.setSuccess(false);
             resultMap.setMessage("查询保单信息失败");
             logger.error("用户:" + getAuthenticatedUserId() + "查询保单信息失败:" , e);
         }
         return resultMap;
    }
    
    /**
     * 待生成保单
     * 
     * @return
     */
    @RequestMapping(value = "/list3.do")
    public String query(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("list", new ArrayList<SyntheticalDTO>());
        return "data.list3";
    }

    /**
     * 核保失败
     */
    @RequestMapping(value = "/list4.do")
    public String queryFail(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<OrderDTO> orderList = null;
        List<QuotaDTO> quotas = null;
        try {
            MicroDTO micro = microService.getMicroByUserId(this.getAuthenticatedUserId());
            orderList = orderService.selectByMicroId(micro.getMicro_id());
            quotas = quotaService.getQuotasByMicId(micro.getMicro_id());
        } catch (Exception e) {
            logger.error("用户:" + getAuthenticatedUserId() + "查询人工核保失败单失败:" , e);
        }
        model.addAttribute("orderList", orderList);
        model.addAttribute("quotas", quotas);
        return "data.list4";
    }

    /**
     * 
     * 查询待核保，待申请核保，待支付，待生成保单
     */
    @RequestMapping(value = "/queryWaitInsureApplicateOne.do")
    @ResponseBody
    public AjaxResult queryWaitInsureApplicateOne(HttpServletRequest request, HttpServletResponse response,
            String orderId, String insId, String queryStage, String status, String queryStage1, String queryStage2,
            String type) {
        logger.info("orderId:" + orderId + "  insId:" + insId + "  queryStage:" + queryStage + "  status:" + status
                + "  queryStage1:" + queryStage1 + "  queryStage2:" + queryStage2 + "  type:" + type);
        List<InquiryDTO> inquiryList2 = null;
        try {
            Map<String, Object> contidion = new HashMap<String, Object>();
            contidion.put("microId", microService.getMicroByUserId(this.getAuthenticatedUserId()).getMicro_id());
            if (StringUtils.isNotBlank(status)) {
                contidion.put("status", status);
            }
            List<String> list = new ArrayList<String>();
            if (StringUtils.isNotBlank(queryStage1) && StringUtils.isNotBlank(queryStage2)) {
                list.add(queryStage1);
                list.add(queryStage2);
                contidion.put("queryStage", list);
            } else {
                if (StringUtils.isNotBlank(queryStage1)) {
                    contidion.put("queryStage", list.add(queryStage1));
                }
                if (StringUtils.isNotBlank(queryStage2)) {
                    contidion.put("queryStage", list.add(queryStage2));
                }
            }
            List<InquiryDTO> inquiryList = inquiryService.getInquiryListByMicroId(contidion);
            inquiryList2 = new ArrayList<InquiryDTO>();
            for (InquiryDTO inquiryDTO : inquiryList) {
                if (inquiryDTO.getOrder() != null && "1".equals(type)) {
                    inquiryList2.add(inquiryDTO);
                } else if (inquiryDTO.getOrder() == null && inquiryDTO.getQuotaList().size() > 0 && "2".equals(type)) {
                    inquiryList2.add(inquiryDTO);
                }
            }
            return new AjaxResult(true, "", inquiryList2);
        } catch (Exception e) {
            logger.error("查询保险公司:" + insId + ",订单:" + orderId + "的信息失败:" , e);
            inquiryList2 = new ArrayList<InquiryDTO>();
        }
        return new AjaxResult(true, "", inquiryList2);
    }

    /**
     * 暂存数据
     * 
     * @return
     */
    @RequestMapping(value = "/informationTempQuery.do")
    public String informationTempQuery(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<InquiryDTO> inquiryDTOList = null;
        try {
            MicroDTO micro = microService.getMicroByUserId(this.getAuthenticatedUserId());
            inquiryDTOList = inquiryService.infoTempQueryService(micro.getMicro_id());
        } catch (Exception e) {
            logger.error("查询暂存数据失败:" , e);
            inquiryDTOList = new ArrayList<InquiryDTO>();
        }
        model.addAttribute("inquiryList", inquiryDTOList);
        return "data.tempstorage";
    }

    /**
     * 综合查询页
     * 
     * @return
     */
    @RequestMapping(value = "/infoQuery.do")
    public String infoQuery(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("inquiryList", new ArrayList<InquiryDTO>());
        model.addAttribute("IS_USE_YONGCHENG_NEW_INTERFACE", paramConfigService.getValueByKey("IS_USE_YONGCHENG_NEW_INTERFACE"));
        return "data.infoQueryList";
    }

    /**
     * 综合查询页查询条件
     * 
     * @return
     */
    @RequestMapping(value = "/infoQueryParameter.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap infoQueryParameter(HttpServletRequest request, HttpServletResponse response,
            String queryParameter, Integer currPage) {
        ResultMap resultMap = new ResultMap();
        PageParam pageParam = new PageParam(this.getAuthenticatedUserId(), null);
        pageParam.setCurrentPage(currPage);
        pageParam.setWd(queryParameter);
        pageParam.setPageSize(30);
        PageBean<InquiryDTO> dataPage = null;
        try {
            MicroDTO micro = microService.getMicroByUserId(pageParam.getOperateUser());
            pageParam.setMicroId(micro.getMicro_id());
            dataPage = inquiryService.infoQueryService_v2(pageParam);
            resultMap.setSuccess(true);
            resultMap.setData(dataPage);
        } catch (Exception e) {
            resultMap.setSuccess(false);
            resultMap.setMessage("综合查询数据失败");
            logger.error("综合查询数据失败:" , e);
        }
        return resultMap;
    }

    /**
     * 综合查询页面显示
     * 
     * @return
     */
    @RequestMapping(value = "/infoView.do")
    @ResponseBody
    public AjaxResult infoView(HttpServletRequest request, HttpServletResponse response, Model model, String inquiryId) {
        List<QuotaDTO> QuotaDTOList = null;
        List<QuotaDTO> newQuotaDTOList = new ArrayList<QuotaDTO>();
        try {
            QuotaDTOList = quotaService.infoViewService(inquiryId);
            for (QuotaDTO dto : QuotaDTOList) {
                InsuranceDTO insurancedto = insuranceService.get(dto.getInsurance().getInsId());
                dto.setInsCompanyName(insurancedto.getInsPetName());
                if ("A".equals(dto.getQuotaType())) {
                    if ("-1".equals(dto.getStatus()) || "0".equals(dto.getStatus())) {
                        dto.setStatusName("已报价");
                    } else if ("4".equals(dto.getStatus())) {
                        dto.setStatusName("待支付");
                    } else if ("5".equals(dto.getStatus())) {
                        dto.setStatusName("已支付");
                    } else if ("2".equals(dto.getStatus())) {
                        dto.setStatusName("待核保");
                    } else if ("1".equals(dto.getStatus())) {
                        dto.setStatusName("订单暂存");
                    } else if ("3".equals(dto.getStatus())) {
                        dto.setStatusName("核保退回");
                    } else if ("8".equals(dto.getStatus())) {
                        dto.setStatusName("缴费失败");
                    } else if ("6".equals(dto.getStatus())) {
                        dto.setStatusName("保单");
                    }
                } else if ("M".equals(dto.getQuotaType())) {
                	ManualQuotationTaskDTO manualQuotationTaskDTO = new ManualQuotationTaskDTO();
                	manualQuotationTaskDTO = manualQuotationTaskService.queryByQuoteId(dto.getQuotaId());
                	if("5".equals(dto.getStatus())){
            			dto.setOrderStatus("10");
                		dto.setStatusName("已支付");
            		}else if("6".equals(dto.getStatus())){
            			dto.setOrderStatus("11");
                		dto.setStatusName("保单");
            		}else if("1".equals(manualQuotationTaskDTO.getTaskStatus()) || "2".equals(manualQuotationTaskDTO.getTaskStatus())){
                		dto.setOrderStatus("1");
                		dto.setStatusName("报价中");
                	} else if("3".equals(manualQuotationTaskDTO.getTaskStatus())){
                		dto.setOrderStatus("3");
                		dto.setStatusName("已报价");
                	}else if("4".equals(manualQuotationTaskDTO.getTaskStatus())){
                		dto.setOrderStatus("4");
                		dto.setStatusName("报价失败");
                	}else if("6".equals(manualQuotationTaskDTO.getTaskStatus()) || "7".equals(manualQuotationTaskDTO.getTaskStatus())){
                		dto.setOrderStatus("6");
                		dto.setStatusName("核保中");
                	}else if("8".equals(manualQuotationTaskDTO.getTaskStatus())){
                		dto.setOrderStatus("8");
                		dto.setStatusName("待支付");
                	}else if("9".equals(manualQuotationTaskDTO.getTaskStatus())){
                		dto.setOrderStatus("9");
                		dto.setStatusName("核保退回");
                	}
//                	if("10".equals(manualQuotationTaskDTO.getTaskStatus())){
//                		dto.setOrderStatus("10");
//                		dto.setStatusName("已支付");
//                	}else if("11".equals(manualQuotationTaskDTO.getTaskStatus())){
//                		dto.setOrderStatus("11");
//                		dto.setStatusName("保单");
//                	}
                }
                newQuotaDTOList.add(dto);
            }
        } catch (Exception e) {
            logger.error("综合查询数据失败:" , e);
            newQuotaDTOList = new ArrayList<QuotaDTO>();
        }

        return new AjaxResult(true, "", newQuotaDTOList);
    }

    @Autowired
	private WalletActiveService walletActiveService;
    
    @RequestMapping("dataQuery.do")
    @ResponseBody
    public AjaxResult dataQuery(HttpServletRequest request, HttpServletResponse response, String orderId, String insId,
            String status) {
        try {
        	
        	/**
        	 * 核保查询前
        	 */
        	if(null != insId && !insId.isEmpty()){
        		InsuranceDTO insurance;
        		try {
        			insurance = insuranceService.get(insId);
        		} catch (Exception e) {
        			logger.error("获取保险公司异常，insId="+insId, e);
        			return new AjaxResult(true, "查询保险公司异常.");
        		}
        		if(insurance == null){
        			return new AjaxResult(true, "保险公司不存在。");
        		}
        		if("0".equals(insurance.getStatus())){
        			return new AjaxResult(true, "保险公司停止业务。",insurance.getStatus());
        		}
        		if("2".equals(insurance.getStatus())){
        			return new AjaxResult(true, "保险公司系统维护中，暂停业务。",insurance.getStatus());
        		}
        	}
        	
        	OrderDTO orderDTO = orderService.selectByMicroIdAndOrderId(null, orderId, null);
        	if(null != orderDTO && "6".equals(orderDTO.getQueryStage())){
        		 OrderDTO resultDTO = new OrderDTO();
                 resultDTO.setOrderId(orderId);
                 resultDTO.setQueryStage(orderDTO.getQueryStage());
                 AjaxResult result = new AjaxResult();
                 result.setSucc(true);
                 result.setMsg("已生成保单");
                 result.setData(resultDTO);
                 return result;
        	}
        	if(null != orderDTO && null != orderDTO.getInsurance() && "TPIC".equals(orderDTO.getInsurance().getInsId()) && ("4".equals(orderDTO.getQueryStage()) || "5".equals(orderDTO.getQueryStage()))){
        		String returnCode = timerOrderStatusQueryService.excueteTPICM(orderId);
        		logger.info("result:"+returnCode);
        		if("2006".equals(returnCode)){
        			OrderDTO resultDTO = new OrderDTO();
                    resultDTO.setOrderId(orderId);
                    resultDTO.setQueryStage("6");
                    AjaxResult result = new AjaxResult();
                    result.setSucc(true);
                    result.setMsg("已生成保单");
                    result.setData(resultDTO);
                    return result;
        		}
        		else if("4002".equals(returnCode)){
        			//非人工报价任务
        		}
        		else{
        			//是人工报价任务,返回生成保单中
        			OrderDTO resultDTO = new OrderDTO();
                    resultDTO.setOrderId(orderId);
                    resultDTO.setQueryStage("");//设置为空
                    AjaxResult result = new AjaxResult();
                    result.setSucc(true);
                    result.setMsg("保单生成中...");
                    result.setData(resultDTO);
                    return result;
        		}
        	}
            CombinedQueryDTO combinedQuery = callAction.combinedQuery(getAuthenticatedUserId(), insId, orderId, null != orderDTO?orderDTO.getQueryStage():status);
            if (null == combinedQuery) {
                return new AjaxResult(false);
            }
            // 处理状态
            String statusQuery = "";
            if (StringUtils.isNotBlank(combinedQuery.getVciApplyStatus())
                    && StringUtils.isNotBlank(combinedQuery.getTciApplyStatus())) {
                if (combinedQuery.getVciApplyStatus().equals(combinedQuery.getTciApplyStatus())) {
                    statusQuery = combinedQuery.getVciApplyStatus();
                } else {
                    // 2016年1月14日18:02:00 牟海宁修改(对于商业险和交强险的状态不同时的处理 和 人工核保)
                    if ("3".equals(combinedQuery.getVciApplyStatus()) || "3".equals(combinedQuery.getTciApplyStatus())) {
                        statusQuery = "3";
                    } else if ("2".equals(combinedQuery.getVciApplyStatus())
                            || "2".equals(combinedQuery.getTciApplyStatus())) {
                        statusQuery = "2";
                    } else if ("5".equals(combinedQuery.getVciApplyStatus())
                            || "5".equals(combinedQuery.getTciApplyStatus())) {
                        statusQuery = "5";
                    } else {
                        logger.info("定时刷新锦泰订单信息状态错误；交强险状态：" + combinedQuery.getTciApplyStatus() + " 商业险状态:"
                                + combinedQuery.getVciApplyStatus());
                    }
                }
            } else {
                statusQuery = StringUtils.isNotBlank(combinedQuery.getVciApplyStatus()) ? combinedQuery
                        .getVciApplyStatus() : combinedQuery.getTciApplyStatus();
            }
            OrderDTO order = new OrderDTO();
            order.setMap(combinedQuery.getMap());
            if (StringUtils.isNotBlank(combinedQuery.getTciPolicyNO())) {
                order.setTciPlyNo(combinedQuery.getTciPolicyNO());
            }
            if (StringUtils.isNotBlank(combinedQuery.getVciPolicyNO())) {
                order.setVciPlyNo(combinedQuery.getVciPolicyNO());
            }
            order.setInsurance(new InsuranceDTO(insId));
            order.setOrderId(orderId);
            order.setQueryStage(statusQuery);
            order.setUpdCode(getAuthenticatedUserId());
            if (StringUtils.isNotBlank(statusQuery) && !statusQuery.equals(null != orderDTO?orderDTO.getQueryStage():"")) {
            	orderDTO = orderService.selectByMicroIdAndOrderId(null, orderId, insId);
                //定时任务是否已主动更新
				if(null != statusQuery && null != orderDTO && orderDTO.getQueryStage() != null && !statusQuery.equals(orderDTO.getQueryStage())){
					
					if("6".equals(orderDTO.getQueryStage())){
						logger.info("平安当前订单状态为已生成保单6，查询出的状态为"+statusQuery+"，不予更新");
						OrderDTO resultDTO = new OrderDTO();
			            resultDTO.setOrderId(orderId);
			            resultDTO.setQueryStage("6");
			            AjaxResult result = new AjaxResult();
			            result.setSucc(true);
			            result.setMsg("已生成保单");
			            result.setData(resultDTO);
			            return result;
					}
					//更新状态前的逻辑
					if("5".equals(orderDTO.getQueryStage()) && ("4" == statusQuery || "3".equals(statusQuery) || "2".equals(statusQuery))){
						logger.info("当前订单状态为已支付5,查询出的状态为"+statusQuery);
						statusQuery = "5";
					}
					else if("4".equals(orderDTO.getQueryStage()) && "2" == statusQuery){
						logger.info("当前订单状态为核保通过4,查询出的状态为人工核保中2");
						statusQuery = "4";
					}
					else{
						if("6".equals(order.getQueryStage()) && !"6".equals(orderDTO.getQueryStage())){//之前没有更新为6
							orderService.createPolicyOperation(order.getOrderId(), order.getTciPlyNo(), order.getVciPlyNo(), order);
						}else{
							logger.info("当前订单状态为"+order.getQueryStage()+",查询出的状态为"+statusQuery);
							orderService.updatePolicyNoAndQueryState(order);
						}
					}
					
				}
            }
            String statusName = platformService.getPlatByCode(BaseFinal.INSURED_STATUS, statusQuery).getName();
            OrderDTO resultDTO = new OrderDTO();
            resultDTO.setOrderId(orderId);
            resultDTO.setQueryStage(statusQuery);
            AjaxResult result = new AjaxResult();
            result.setSucc(true);
            result.setMsg(statusName);
            result.setData(resultDTO);
            return result;
        } catch (Exception e) {
            logger.error("用户:" + getAuthenticatedUserId() + "调用保险公司:" + insId + "查询订单:" + orderId + "接口失败：", e);
        }
        return new AjaxResult(false);
    }
    
    @ResponseBody
    @RequestMapping(value="queryBusinessType.do")
    public AjaxResult queryBusinessType(HttpServletRequest request,HttpServletResponse response,String type,String teamId){
    	try {
    		List<QuotnConfigDTO> quotnConfigList = quotnConfigService.queryByTeamId(teamId);
    		Set<String> set = new HashSet<String>();
    		for (QuotnConfigDTO quotnConfigDTO : quotnConfigList) {
    			set.add(quotnConfigDTO.getServiceType());
			}
    		List<BaseDTO> baseList = new ArrayList<BaseDTO>();
    		for (String string : set) {
				BaseDTO baseDTO = baseService.queryByCode("使用性质细分", string);
				if(type.equals(baseDTO.getParCode())){
					baseList.add(baseDTO);
				}
			}
    		return new AjaxResult(true,"",baseList);
		} catch (Exception e) {
			logger.error("用户："+getAuthenticatedUserId()+"获取业务类型失败",e);
	    	return new AjaxResult(false,"获取业务类型失败");
		}
    }

    /**
     * 车辆违章查询
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/violationquery.do")
    public String violationQuery(HttpServletRequest request, HttpServletResponse response, Model model) {
        
    	
        return "data.violationquery";
    }
    
    /**
     * 获取查询验证码
     * @return
     */
    @RequestMapping(value = "/getCarVioQueryValiCode.do")
    @ResponseBody
    public Map<String, Object> getCarVioQueryValiCode(HttpServletRequest req,String orderId,String v,String referVali){
    	
    	Map<String,Object> result = new HashMap<>();
    	
    	try {
    		
    		String carVioSId = req.getSession().getAttribute("CARVIO_SESSION_ID") != null ? 
    				String.valueOf(req.getSession().getAttribute("CARVIO_SESSION_ID")) : null;
    		
    		MicroDTO micro = microService.getMicroByUserId(this.getAuthenticatedUserId());
        	OrderDTO orderDTO = orderService.selectByMicroIdAndOrderId(micro.getMicro_id(), orderId, null);
        	if(null != orderDTO && "6".equals(orderDTO.getQueryStage())){
        		InquiryDTO inquiryDTO = inquiryService.getInquiryVehicleByInquiryId(orderDTO.getInquiry().getInquiryId());
        		CarVioQueryParamDTO param = new CarVioQueryParamDTO();
        		param.setEngNo(inquiryDTO.getEngNo());
        		param.setFrmNo(inquiryDTO.getFrmNo());
        		param.setOwnerName(inquiryDTO.getOwnerName());
        		param.setPlateNo(inquiryDTO.getPlateNo());
        		param.setUserId(this.getAuthenticatedUserId());
        		param.setHpzl("02"); //小型汽车
        		CarVioRespDTO carvio = null;
        		
        		result.put("engno", inquiryDTO.getEngNo());
        		result.put("frmno", inquiryDTO.getFrmNo());
        		result.put("name", inquiryDTO.getOwnerName());
        		result.put("plateno", inquiryDTO.getPlateNo());
        		
        		if(StringUtils.equals("1", referVali)){
        			carvio = carVioQueryProvider.refreshVerCode(carVioSId);
        		}else{
        			if(StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(v) && StringUtils.isNotBlank(carVioSId)){
            			carvio = carVioQueryProvider.inputVerCode(param, carVioSId,v);
            		}else{
            			carvio = carVioQueryProvider.carVioQuery(param, carVioSId);
            		}
        		}
        		
        		
        		if(carvio != null && StringUtils.equalsIgnoreCase(CarVioRespDTO.OK, carvio.getCode())){
        			//表示之前已有违章信息，并且返回
        			result.put("result", "1"); //表示之前已查询过，直接返回违章信息
        			result.put("vios", carvio.getVioInfo().getVioList());
        			
        		}else if(carvio != null && StringUtils.equals(carvio.getCode(), CarVioRespDTO.NEED_INPUT_VER_CODE)){
        			result.put("result", "2"); //表示之前未查询过，直接用验证码
        			result.put("v", carvio.getVerCodeImgBase64());
        			
        		}else if(carvio != null && StringUtils.equals(carvio.getCode(), CarVioRespDTO.BAD_RESULT)){
        			result.put("result", "3"); //交管返回的错误，直接提示。
        			result.put("errmsg", carvio.getMsg());
        			
        		}else if(carvio != null && StringUtils.equals(carvio.getCode(), CarVioRespDTO.BAD_VER_CODE)){
        			result.put("result", "-12"); //验证码错误，要求刷新
        			
        		}else{
        			result.put("result", "-11"); //查询失败，返回错误信息
        			result.put("errmsg", carvio!= null ? carvio.getMsg() : "查询失败");
        		}
        		
        		if(carvio != null ){
        			req.getSession().setAttribute("CARVIO_SESSION_ID",carvio.getSessionId());
        		}
        		
        	}else{
        		result.put("result", "-10");
        	}
		} catch (Exception e) {
			logger.error("查询信息失败", e);
			result.put("result", "-13");
		}
    	
    	return result;
    }
}
