package com.zxcl.webapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.util.constants.MessageConstant;
import com.zxcl.webapp.web.vo.AjaxResult;



/**
 * @ClassName: 消息推送controller
 * @Description:
 * @author zxj
 * @date 
 */
@Controller
@RequestMapping(value="/message")
public class MessageController extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 银行卡列表GET
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.do", method=RequestMethod.GET)
	@Log("消息列表页面")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "message.index";
	}
	
	
	/**
	 * 消息列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list.do", method=RequestMethod.POST)
	@Log("消息列表")
	@ResponseBody
	public AjaxResult list(HttpServletRequest request, HttpServletResponse response, ModelMap model, PageParam pageParam) {
		AjaxResult ajaxResult = new AjaxResult();
		pageParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			ajaxResult.setData(messageService.selectByPage(pageParam));
			ajaxResult.setSucc(true);
		} 
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取消息列表失败:"+be.getMessage());
			logger.error("获取消息列表失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("获取消息列表失败");
			logger.error("获取消息列表失败", e);
		}
		return ajaxResult;
	}
	
	
	/**
	 * 标记为已读
	 * @param request
	 * @param response
	 * @param model
	 * @param walletBankDTO
	 * @return
	 */
	@RequestMapping(value = "/readed.do", method=RequestMethod.POST)
	@Log("标记为已读")
	@ResponseBody
	public AjaxResult readed(HttpServletRequest request, HttpServletResponse response, ModelMap model, String messageId) {
		AjaxResult ajaxResult = new AjaxResult();
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessageId(messageId);
		messageDTO.setUpdBy(this.getAuthenticatedUserId());
		try {
			
			messageDTO.setStatus(MessageConstant.STATUS_3);
			messageService.updateMessage(messageDTO);
			ajaxResult.setMsg("标记为已读成功");
			ajaxResult.setSucc(true);
		}
		catch (BusinessServiceException be){
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("标记为已读失败:"+be.getMessage());
			logger.error("标记为已读失败", be);
		}
		catch (Exception e) {
			ajaxResult.setSucc(false);
			ajaxResult.setMsg("标记为已读失败");
			logger.error("标记为已读失败", e);
		}
		return ajaxResult;
	}
}
