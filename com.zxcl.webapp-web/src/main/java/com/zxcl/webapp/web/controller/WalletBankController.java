package com.zxcl.webapp.web.controller;

import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.ocr_service.openapi.OcrBankCardService;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.BankService;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.biz.service.MicroAgentService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.AgentServiceControlDTO;
import com.zxcl.webapp.dto.AgentBankMicroContrlDTO;
import com.zxcl.webapp.dto.MicroAgentDTO;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.wallet.WalletBankDTO;
import com.zxcl.webapp.integration.dao.AgentServiceControlDAO;
import com.zxcl.webapp.integration.dao.AgentBankMicroContrlDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.web.vo.AjaxResult;



/**
 * @ClassName: 银行卡controller
 * @Description:
 * @author zxj
 * @date 
 */
@Controller
@RequestMapping(value="/wallet_bank")
public class WalletBankController extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private MicroAgentService microAgentService;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MicroDAO microDAO;
	
	@Autowired
	private AgentServiceControlDAO agentServiceControlDAO;
	
	@Autowired
	private AgentBankMicroContrlDAO agentBankMicroContrlDAO;
	
	private @Value("${share.url}")String baseURL;
	
	@Autowired
	private IdentityInfoService identityInfoService;
	
	@Autowired
	private OcrBankCardService ocrBankCardService;
	
	
	/**
	 * 银行卡列表GET
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.do", method=RequestMethod.GET)
	@Log("银行卡管理页面")
	public String index(Model model , HttpServletRequest request, HttpServletResponse response, String rac) {
		String walletCanUseAgent = "N";
		try {
			boolean walletCanUseAgentB = walletBankService.walletCanUseAgent(this.getAuthenticatedUserId());
			walletCanUseAgent = walletCanUseAgentB?"Y":"N";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("walletCanUseAgent", walletCanUseAgent);
		model.addAttribute("rac", rac);
		return "wallet_bank.index";
	}
	
	/**
	 * 添加银行卡页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/to_add.do", method=RequestMethod.GET)
	@Log("添加银行卡页面")
	public String toAdd(Model model , HttpServletRequest request, HttpServletResponse response) {
		String myName = null;
		String walletCanUseAgent = "N";
		try {
			boolean walletCanUseAgentB = walletBankService.walletCanUseAgent(this.getAuthenticatedUserId());
			walletCanUseAgent = walletCanUseAgentB?"Y":"N";
			MicroDTO micro = microService.getMicroByUserId(this.getAuthenticatedUserId());
			if(micro != null){
				myName = micro.getMicro_name();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("baseURL", baseURL);
		model.addAttribute("walletCanUseAgent", walletCanUseAgent);
		model.addAttribute("myName", myName);
		model.addAttribute("canUseOcr", canUseOcr()?"1":"0");
		return "wallet_bank.add";
	}
	
	/**
	 * 获取代理人列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/micro_agent_list.do", method=RequestMethod.POST)
	@Log("获取代理人列表")
	@ResponseBody
	public AjaxResult microAgentList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			
			List<MicroAgentDTO> microAgentList = null;
			try {
				microAgentList = microAgentService.selectMicroAgentListByMicroId(microService.getMicroByUserId(this.getAuthenticatedUserId()).getMicro_id());
				if(CollectionUtils.isNotEmpty(microAgentList)){
					MicroDTO microDTO = null;
					for(MicroAgentDTO item : microAgentList ){
						microDTO = microDAO.getMicroByMicroId(item.getMicroIdAgent());
						if(null == microDTO){
							item.setUserAgentMircoName("姓名不详");
						}
						else{
							if(StringUtils.isBlank(microDTO.getMicro_name())){
								item.setUserAgentMircoName(microDTO.getTel());
							}
							else{
								item.setUserAgentMircoName(microDTO.getMicro_name());
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("获取名义代理人关联信息失败", e);
			}
			
			
			ajaxResult.setData(microAgentList);
			ajaxResult.setSucc(true);
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取代理人列表失败");
			logger.error("获取代理人列表失败", e);
		}
		return ajaxResult;
	}
	
	
	
	/**
	 * 银行卡列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list.do", method=RequestMethod.POST)
	@Log("银行卡列表")
	@ResponseBody
	public AjaxResult list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ajaxResult.setData(walletBankService.selectWalletBankListByUserId(this.getAuthenticatedUserId()));
			ajaxResult.setSucc(true);
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取银行卡列表失败:"+be.getMessage());
			logger.error("获取银行卡列表失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取银行卡列表失败");
			logger.error("获取银行卡列表失败", e);
		}
		return ajaxResult;
	}
	
	
	/**
	 * 获取银行列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bank_list.do", method=RequestMethod.POST)
	@Log("获取银行列表")
	@ResponseBody
	public AjaxResult bankList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ajaxResult.setData(bankService.queryBankService());
			ajaxResult.setSucc(true);
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取银行列表失败");
			logger.error("获取银行列表失败", e);
		}
		return ajaxResult;
	}
	
	@RequestMapping(value = "/cash_bank_check.do", method=RequestMethod.POST)
	@Log("获取银行列表")
	@ResponseBody
	public AjaxResult cashBankCheck(HttpServletRequest request, HttpServletResponse response, ModelMap model,String walletBankId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			boolean flag = walletBankService.cashBankCheck(this.getAuthenticatedUserId(), walletBankId);
			if(flag){
				ajaxResult.setSucc(true);
			}
			else{
				ajaxResult.setSucc(false);
				ajaxResult.setMsg("校验失败：银行卡账户名与帐号姓名不一致");
				return ajaxResult;
			}
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("校验的银行卡失败");
			logger.error("校验的银行卡失败", e);
		}
		return ajaxResult;
	}
	
	@RequestMapping(value = "/add_pre_isOnceWb.do", method=RequestMethod.POST)
	@Log("用户是否第一次新增银行卡 || 实名认证")
	@ResponseBody
	public AjaxResult addPreIsOnceWb(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			
			MicroApproveDTO ma = identityInfoService.findConfirm(this.getAuthenticatedUserId(), false);
			if(null != ma && "2".equals(ma.getApproveState()+"")){//认证通过
				ajaxResult.setData("0");//已实名认证
			}
			else{
				if(walletBankService.isOnceWb(this.getAuthenticatedUserId())){
					ajaxResult.setData("1");//第一次新增
				}
				else{
					ajaxResult.setData("0");//非第一次新增
				}
			}
			ajaxResult.setMsg("查询成功");
			ajaxResult.setSucc(true);
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("查询失败");
			logger.error("查询失败", e);
		}
		return ajaxResult;
	}
	
	@RequestMapping(value = "/add_pre_isOnceWbAndNNS.do", method=RequestMethod.POST)
	@Log("用户是否第一次新增银行卡，且持卡人与小微姓名不一致")
	@ResponseBody
	public AjaxResult addPreIsOnceWbAndNNS(HttpServletRequest request, HttpServletResponse response, ModelMap model, String cardOwnerName) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ajaxResult.setData(walletBankService.isOnceWbAndMicNameNotSame(this.getAuthenticatedUserId(), cardOwnerName));
			ajaxResult.setMsg("查询成功");
			ajaxResult.setSucc(true);
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("查询失败");
			logger.error("查询失败", e);
		}
		return ajaxResult;
	}
	
	
	/**
	 * 是否开启银行卡识别功能
	 * @param request
	 * @param response
	 * @param model
	 * @param walletBankDTO
	 * @return
	 */
	
	protected boolean canUseOcr(){
		try {
			String userId = this.getAuthenticatedUserId();
			AgentServiceControlDTO obj = agentServiceControlDAO.selectByAgentIdAndServiceType(microService.getMicroByUserId(userId).getAgency().getAgent_id(), 2);
			if(null != obj && null != obj.getIsOn() && obj.getIsOn().equals(1)){
				
				//识别次数是否已用完
				AgentBankMicroContrlDTO mc = getAgentBankMicroContrl();
				if(null != mc && (mc.getTodayMaxCount() != -1 && mc.getTodayCount() >= mc.getTodayMaxCount())){
					logger.info(userId+"当日银行卡识别次数已用完");
					return false;
				}
				return true;
			}
			else{
				logger.info(userId+"当前代理未开启银行卡识别功能");
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}
	
	@RequestMapping(value = "/addPreOcr.do", method=RequestMethod.POST)
	@Log("是否开启银行卡识别功能")
	@ResponseBody
	public AjaxResult addPreOcr(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			
			if(canUseOcr()){
				ajaxResult.setSucc(true);
				ajaxResult.setMsg("ON");
			}
			else{
				ajaxResult.setSucc(true);
				ajaxResult.setMsg("OFF");
			}
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return ajaxResult;
	}
	
	
	@RequestMapping(value = "/testOcr.do")
	public String testOcr(HttpServletRequest request, HttpServletResponse response) {
		return "other.ocr";
	}
	
	
	
	protected AgentBankMicroContrlDTO getAgentBankMicroContrl(){
		AgentBankMicroContrlDTO r = agentBankMicroContrlDAO.getByUserId(this.getAuthenticatedUserId());
		logger.info("getAgentBankMicroContrl="+JSONObject.toJSONString(r));
		return r;
	}
	
	protected void updateAgentBankMicroContrl(int totalCount, int todayCount){
		AgentBankMicroContrlDTO u = new AgentBankMicroContrlDTO();
		u.setUserId(this.getAuthenticatedUserId());
		u.setTotalCount(totalCount);
		u.setTodayCount(todayCount);
		int c = agentBankMicroContrlDAO.updateSelective(u);
		logger.info("updateAgentBankMicroContrl影响行数"+c);
	}
	
	protected void insertAgentBankMicroContrl(int todayMaxCount){
		AgentBankMicroContrlDTO i = new AgentBankMicroContrlDTO();
		i.setUserId(this.getAuthenticatedUserId());
		i.setTodayMaxCount(todayMaxCount);
		int c = agentBankMicroContrlDAO.insertSelective(i);
		logger.info("insertAgentBankMicroContrl影响行数"+c);
	}
	
	/**
	 * 获取银行卡识别结果
	 * 6VQtmYNRQiipge7FuAmuQQ.jpg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addPreOcrResult.do")
	@ResponseBody
	public AjaxResult addPreOcrResult(HttpServletRequest request, HttpServletResponse response, String fileId) {
		logger.info("获取银行卡识别结果==>fileId="+fileId);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			
			if(!canUseOcr()){
				ajaxResult.setSucc(false);
				ajaxResult.setMsg("系统限制:当前代理设置为不允许使用银行卡识别功能,或者当日识别次数已用完");
				return ajaxResult;
			}
			
			if(StringUtils.isBlank(fileId)){
				ajaxResult.setSucc(false);
				ajaxResult.setMsg("文件必传");
				return ajaxResult;
			}
			
//			URL url = new URL(baseURL+"/file_server/files/"+fileId);
			String resultBack = ocrBankCardService.ocrBankCardForUrl(baseURL+"/file_server/files/"+fileId);
			logger.info("resultBack="+resultBack);
			
			//调用接口后统计识别次数
			AgentBankMicroContrlDTO mc = getAgentBankMicroContrl();
			if(null == mc){
				insertAgentBankMicroContrl(5);//新增控制
			}
			else{
				
				//更新控制
				Calendar calendar = Calendar.getInstance();
				int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
				calendar.setTime(mc.getUpdTm());
				int uptDay = calendar.get(Calendar.DAY_OF_YEAR);
				
				if (nowDay == uptDay) {//是否跨天
					updateAgentBankMicroContrl(mc.getTotalCount()+1, mc.getTodayCount() + 1);
				}
				else{
					updateAgentBankMicroContrl(mc.getTotalCount()+1, 1);
				}
			}
			String resultCode = "FAIL";
			String resultMsg = "识别失败";
			HashMap<String, String> resultMap = new HashMap<String, String>();
			if(StringUtils.isNotBlank(resultBack) && resultBack.startsWith("{")){
				JSONObject ocrResultDTO = JSONObject.parseObject(resultBack);
				String returnCode = ocrResultDTO.getString("returnCode");//E0200:成功
				JSONObject resultDetail = ocrResultDTO.getJSONObject("resultDetail");//详情
				if("E0200".equals(returnCode) && resultDetail != null){
					String bankNo = resultDetail.getString("cardNo");
					if(StringUtils.isNotBlank(bankNo)){
						//识别成功
						resultCode = "OK";
						resultMsg = "识别成功";
						resultMap.put("bankNo", bankNo);
						resultMap.put("bankName", resultDetail.getString("bankName"));
					}
				}
				else{
					//识别失败
				}
			}
			if("OK".equals(resultCode)){
				ajaxResult.setSucc(true);
				ajaxResult.setMsg("OK");
				ajaxResult.setData(resultMap);
			}
			else{
				ajaxResult.setSucc(false);
				ajaxResult.setMsg(resultMsg);
			}
			logger.info("获取银行卡识别结果<==result="+JSONObject.toJSONString(ajaxResult));
			return ajaxResult;
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("识别失败");
			logger.error(e.getMessage(), e);
		}
		return ajaxResult;
	}
	
	/**
	 * 添加银行卡
	 * @param request
	 * @param response
	 * @param model
	 * @param walletBankDTO
	 * @return
	 */
	@RequestMapping(value = "/add.do", method=RequestMethod.POST)
	@Log("添加银行卡")
	@ResponseBody
	public AjaxResult add(HttpServletRequest request, HttpServletResponse response, ModelMap model, WalletBankDTO walletBankDTO) {
		AjaxResult ajaxResult = new AjaxResult();
		walletBankDTO.setUserId(this.getAuthenticatedUserId());
		try {
			walletBankService.addWalletBank(walletBankDTO);
			ajaxResult.setMsg("添加银行卡成功");
			ajaxResult.setSucc(true);
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("添加银行卡失败:"+be.getMessage());
			logger.error("添加银行卡失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("添加银行卡失败");
			logger.error("添加银行卡失败", e);
		}
		return ajaxResult;
	}
	
	/**
	 * 更新银行卡
	 * @param request
	 * @param response
	 * @param model
	 * @param walletBankDTO
	 * @return
	 */
	@RequestMapping(value = "/upd.do", method=RequestMethod.POST)
	@Log("更新银行卡")
	@ResponseBody
	public AjaxResult upd(HttpServletRequest request, HttpServletResponse response, ModelMap model, WalletBankDTO walletBankDTO) {
		AjaxResult ajaxResult = new AjaxResult();
		walletBankDTO.setUserId(this.getAuthenticatedUserId());
		try {
			walletBankService.updateWalletBank(walletBankDTO);
			ajaxResult.setMsg("更新银行卡成功");
			ajaxResult.setSucc(true);
		}
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("更新银行卡失败:"+be.getMessage());
			logger.error("更新银行卡失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("更新银行卡失败");
			logger.error("更新银行卡失败", e);
		}
		return ajaxResult;
	}
	
	/**
	 * 删除银行卡
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del.do", method=RequestMethod.POST)
	@Log("删除银行卡")
	@ResponseBody
	public AjaxResult del(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			walletBankService.delWalletBankById(this.getAuthenticatedUserId(), id);
			ajaxResult.setMsg("删除银行卡成功");
			ajaxResult.setSucc(true);
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("删除银行卡失败:"+be.getMessage());
			logger.error("删除银行卡失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("删除银行卡失败");
			logger.error("删除银行卡失败", e);
		}
		return ajaxResult;
	}
	
	/**
	 * 查询银行卡数量
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/count.do", method=RequestMethod.POST)
	@Log("查询银行卡数量")
	@ResponseBody
	public AjaxResult count(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ajaxResult.setData(walletBankService.selectWalletBankCountByUserId(this.getAuthenticatedUserId()));
			ajaxResult.setMsg("查询银行卡数量成功");
			ajaxResult.setSucc(true);
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("查询银行卡数量失败:"+be.getMessage());
			logger.error("查询银行卡数量失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("查询银行卡数量失败");
			logger.error("查询银行卡数量失败", e);
		}
		return ajaxResult;
	}
}
