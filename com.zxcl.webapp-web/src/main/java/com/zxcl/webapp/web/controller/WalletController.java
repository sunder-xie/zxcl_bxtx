package com.zxcl.webapp.web.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.bxtxmanage.dto.resp.rmi.CalcSettleAmountRespDTO;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.web.vo.AjaxResult;



/**
 * @ClassName: 钱包controller
 * @Description:
 * @author zxj
 * @date 
 */
@Controller
@RequestMapping(value="/wallet")
public class WalletController extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private WalletCoreService walletCoreService;
	
	@Autowired
	private WalletBillService walletBillService;
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private IdentityInfoService identityInfoService;
	
	
	/**
	 * 钱包首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.do", method=RequestMethod.GET)
	@Log("钱包首页")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "wallet.index";
	}
	
	@RequestMapping(value = "/to_cash.do", method=RequestMethod.GET)
	@Log("钱包余额提现页面")
	public String toCash(HttpServletRequest request, HttpServletResponse response, String walletBankId, ModelMap model) {
		String walletCanUseAgent = "N";
		try {
			boolean walletCanUseAgentB = walletBankService.walletCanUseAgent(this.getAuthenticatedUserId());
			walletCanUseAgent = walletCanUseAgentB?"Y":"N";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("walletCanUseAgent", walletCanUseAgent);
		model.addAttribute("walletBankId", walletBankId);
		return "wallet.to_cash";
	}
	
	
	
	/**
	 * 钱包详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get_wallet.do")
	@Log("钱包详情")
	@ResponseBody
	public AjaxResult getWallet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ajaxResult.setData(walletCoreService.getWalletByUserId(this.getAuthenticatedUserId()));
			ajaxResult.setSucc(true);
		}
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取钱包详情失败:"+be.getMessage());
			logger.error("获取钱包详情失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取钱包详情失败");
			logger.error("获取钱包详情失败", e);
		}
		return ajaxResult;
	}
	
	/**
	 * 账单列表GET
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bill_list.do", method=RequestMethod.GET)
	@Log("账单列表GET")
	public String billList(HttpServletRequest request, HttpServletResponse response) {
		return "wallet.bill_list";
	}
	
	/**
	 * 账单列表POST
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bill_list.do", method=RequestMethod.POST)
	@Log("账单列表POST")
	@ResponseBody
	public AjaxResult billList(HttpServletRequest request, HttpServletResponse response, PageParam pageParam) {
		AjaxResult ajaxResult = new AjaxResult();
		pageParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			ajaxResult.setData(walletBillService.getBillListByPage(pageParam));
			ajaxResult.setSucc(true);
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取账单列表失败:"+be.getMessage());
			logger.error("获取账单列表失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取账单列表失败");
			logger.error("获取账单列表失败", e);
		}
		return ajaxResult;
	}
	
	
	/**
	 * 更新钱包密码
	 * @param request
	 * @param response
	 * @param pwd
	 * @return
	 */
//	@RequestMapping(value = "/upd_pwd.do", method=RequestMethod.POST)
//	@Log("更新钱包密码")
//	@ResponseBody
//	public AjaxResult updPwd(HttpServletRequest request, HttpServletResponse response, String pwd) {
//		AjaxResult ajaxResult = new AjaxResult();
//		try {
//			walletCoreService.updWalletPwdByUserId(this.getAuthenticatedUserId(), pwd);
//			ajaxResult.setSucc(true);
//			ajaxResult.setMsg("更新钱包密码成功");
//		} 
//		catch (BusinessServiceException be){
//			ajaxResult.setSucc(false);
//			ajaxResult.setMsg("更新钱包密码失败:"+be.getMessage());
//			logger.error("更新钱包密码失败", be);
//		}
//		catch (Exception e) {
//			ajaxResult.setSucc(false);
//			ajaxResult.setMsg("更新钱包密码失败");
//			logger.error("更新钱包密码失败", e);
//		}
//		return ajaxResult;
//	}
	
	/**
	 * 钱包提现申请
	 * @param request
	 * @param response
	 * @param amount  提现金额
	 * @param pwd 钱包密码 
	 * @param walletBankId 银行卡
	 * @param agentUserId 名义代理人
	 * @return
	 */
	
	@RequestMapping(value = "/cash_pre.do", method=RequestMethod.POST)
	@Log("钱包提现申请前")
	@ResponseBody
	public AjaxResult cashPre(HttpServletRequest request, HttpServletResponse response, String amount, String walletBankId) {
		AjaxResult ajaxResult = new AjaxResult();
		if(StringUtils.isBlank(amount)){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("提现金额不能为空");
			return ajaxResult;
		}
		try {
			
			if(identityInfoService.isNeedApprove(getAuthenticatedUserId())
					&& !identityInfoService.isConfirmed(getAuthenticatedUserId())){
				ajaxResult.setSucc(false);
				ajaxResult.setMsg("请先完成实名认证");
				return ajaxResult;
			}
			
			
			CalcSettleAmountRespDTO respreqCalc = walletCoreService.cashWalletPre(walletBankId, this.getAuthenticatedUserId(), new BigDecimal(amount), null);
			request.getSession().setAttribute("respreqCalc", respreqCalc);
			ajaxResult.setSucc(true);
			ajaxResult.setData(respreqCalc);
			ajaxResult.setMsg("钱包提现申请提现记录成功");
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("钱包提现申请提现记录失败:"+be.getMessage());
			logger.error("钱包提现申请提现记录失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("钱包提现申请提现记录失败");
			logger.error("钱包提现申请提现记录失败", e);
		}
		return ajaxResult;
	}
	
	
	/**
	 * 代理是否设置了必须是名认证且已经实名认证
	 * @param request
	 * @param response
	 * @param amount
	 * @param walletBankId
	 * @return
	 */
	@RequestMapping(value = "/can_cash.do", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult canCash(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if(identityInfoService.isNeedApprove(getAuthenticatedUserId())){
				// AjaxResult.data  1通过认证  2认证中  0未认证
				MicroApproveDTO ma = identityInfoService.findConfirm(getAuthenticatedUserId(), false);
				if(null == ma || "3".equals(ma.getApproveState()+"")){
					ajaxResult.setSucc(true);
					ajaxResult.setData("0");
					ajaxResult.setMsg("请先完成实名认证");
				}else{
					if("1".equals(ma.getApproveState()+"")){
						ajaxResult.setSucc(true);
						ajaxResult.setData("2");
						ajaxResult.setMsg("正在认证中");
					}
					else if("2".equals(ma.getApproveState()+"")){
						ajaxResult.setSucc(true);
						ajaxResult.setData("1");
						ajaxResult.setMsg("已通过认证");
					}
				}
				
			}
			else{
				ajaxResult.setSucc(true);
				ajaxResult.setData("1");
				ajaxResult.setMsg("代理未设置必须实名认证");
			}
			ajaxResult.setSucc(true);
			ajaxResult.setMsg("钱包提现申请成功");
		} 
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return ajaxResult;
	}
	
	
	@RequestMapping(value = "/cash.do", method=RequestMethod.POST)
	@Log("钱包提现申请")
	@ResponseBody
	public AjaxResult cash(HttpServletRequest request, HttpServletResponse response, String amount, String walletBankId) {
		AjaxResult ajaxResult = new AjaxResult();
		if(StringUtils.isBlank(amount)){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("提现金额不能为空");
			return ajaxResult;
		}
		try {
			walletCoreService.cashWallet(walletBankId, this.getAuthenticatedUserId(), new BigDecimal(amount), null, (CalcSettleAmountRespDTO) request.getSession().getAttribute("respreqCalc"));
			ajaxResult.setSucc(true);
			ajaxResult.setMsg("钱包提现申请成功");
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("钱包提现申请失败:"+be.getMessage());
			logger.error("钱包提现申请失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("钱包提现申请失败");
			logger.error("钱包提现申请失败", e);
		}
		return ajaxResult;
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @param amount
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cash_succ.do", method=RequestMethod.GET)
	@Log("提现申请成功页面")
	public String cashSucc(HttpServletRequest request, HttpServletResponse response, String amount, ModelMap model) {
		model.addAttribute("amount", amount);
		return "wallet.cash_succ";
	}
	
}
