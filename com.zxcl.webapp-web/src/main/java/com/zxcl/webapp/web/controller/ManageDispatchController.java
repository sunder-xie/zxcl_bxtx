package com.zxcl.webapp.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.IManageDispatchService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.ManageDispatchDTO;
import com.zxcl.webapp.integration.dao.DistributionDAO;

/**
 * 配送信息controller
 * @author zxj
 *
 */
@Controller
@RequestMapping("/manage_dispatch")
public class ManageDispatchController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IManageDispatchService dispatchService;
	
	@Autowired
	private DistributionDAO distributionDAO;

	/**
	 * 添加配送信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toEditManagetAddressPage.do")
	public String toEditManagetAddressPage(Model m,HttpServletRequest req,String id){
		
		try {
			if(StringUtils.isNotBlank(id)){
				ManageDispatchDTO manageDispatchDTO = dispatchService.selectById(id);
				if(!StringUtils.equals(manageDispatchDTO.getCrtCde(), this.getAuthenticatedUserId())){
					return "redirect:/index.do";
				}
			m.addAttribute("dispatch", manageDispatchDTO);
			}
		} catch (BusinessServiceException e) {
			logger.error("查询配送地址信息失败", e);
		}
		
		
		return "user.editmanageadress";
	}
	
	@RequestMapping(value="add.do",method=RequestMethod.POST)
	@ResponseBody
	@Log("添加配送信息")
	public ResultMap add(HttpServletRequest request, HttpServletResponse response,ManageDispatchDTO manageDispatchDTO, BaseParam baseParam){
		ResultMap resultMap = new ResultMap();
		baseParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			resultMap.setSuccess(true);
			dispatchService.insertManageDispatch(manageDispatchDTO, baseParam);
			resultMap.setMessage("操作成功");
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("添加失败,用户为："+baseParam.getOperateUser(),e);
		}
		return resultMap;
	}
	
	/**
	 * 编辑配送信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value="update.do",method=RequestMethod.POST)
	@ResponseBody
	@Log("编辑配送信息")
	public ResultMap updateInfo(HttpServletRequest request, HttpServletResponse response,String id){
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.setSuccess(true);
			ManageDispatchDTO manageDispatchDTO = dispatchService.selectById(id);
			resultMap.setData(manageDispatchDTO);
			resultMap.setMessage("操作成功");
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("修改查询失败,用户为："+this.getAuthenticatedUserId(),e);
		}
		return resultMap;
	}
	
	/**
	 * 删除配送信息
	 * @param request
	 * @param response
	 * @param manageDispatchDTO
	 * @param baseParam
	 * @return
	 */
	@RequestMapping(value="del.do",method=RequestMethod.POST)
	@ResponseBody
	@Log("删除配送信息")
	public ResultMap del(HttpServletRequest request, HttpServletResponse response,ManageDispatchDTO manageDispatchDTO, BaseParam baseParam){
		ResultMap resultMap = new ResultMap();
		baseParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			resultMap.setSuccess(true);
			dispatchService.delManageDispatch(manageDispatchDTO, baseParam);
			resultMap.setMessage("操作成功");
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("删除失败,用户为："+baseParam.getOperateUser(),e);
		}
		return resultMap;
	}
	/**
	 * 配送列表
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("list.do")
	@ResponseBody
	@Log("配送信息列表")
	public ResultMap list(HttpServletRequest request, HttpServletResponse response, BaseParam baseParam){
		ResultMap resultMap = new ResultMap();
		baseParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			resultMap.setSuccess(true);
			resultMap.setData(dispatchService.selectManageDispatch(baseParam));
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("获取失败,用户为："+baseParam.getOperateUser(),e);
		}
		return resultMap;
	}
	/**
	 * 根据ID获取配送信息
	 * @param request
	 * @param response
	 * @param baseParam
	 * @return
	 */
	@RequestMapping("detail.do")
	@ResponseBody
	@Log("配送信息详情")
	public ResultMap detail(HttpServletRequest request, HttpServletResponse response, ManageDispatchDTO manageDispatchDTO, BaseParam baseParam){
		ResultMap resultMap = new ResultMap();
		baseParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			resultMap.setSuccess(true);
			resultMap.setData(dispatchService.selectManageDispatchById(baseParam, manageDispatchDTO));
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("获取详情失败,用户为："+baseParam.getOperateUser(),e);
		}
		return resultMap;
	}
	
	@RequestMapping("/dis/get.do")
	@ResponseBody
	public ResultMap disGet(HttpServletRequest request, HttpServletResponse response, String orderId){
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.setSuccess(true);
			resultMap.setData(distributionDAO.selectByOrderId(orderId));
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("获取失败",e);
		}
		return resultMap;
	}
	
	@RequestMapping("/dis/upd.do")
	@ResponseBody
	public ResultMap disUpd(HttpServletRequest request, HttpServletResponse response, DistributionDTO dis){
		ResultMap resultMap = new ResultMap();
		if(null == dis){
			resultMap.setSuccess(false);
			resultMap.setMessage("参数不能为空");
			return resultMap;
		}
		if(StringUtils.isBlank(dis.getDispatchType())){
			resultMap.setSuccess(false);
			resultMap.setMessage("请选择配送方式");
			return resultMap;
		}
		if("0".equals(dis.getDispatchType())){//配送
			logger.info("配送");
			if(StringUtils.isBlank(dis.getDisName())){
				resultMap.setSuccess(false);
				resultMap.setMessage("姓名不能为空");
				return resultMap;
			}
			if(!dis.getDisName().matches("[\\u4e00-\\u9fa5]+")){
				resultMap.setSuccess(false);
				resultMap.setMessage("姓名请输入中文");
				return resultMap;
			}
			if(StringUtils.isBlank(dis.getDisAddress())){
				resultMap.setSuccess(false);
				resultMap.setMessage("详细地址不能为空");
				return resultMap;
			}
			if(StringUtils.isBlank(dis.getDisProvince())){
				resultMap.setSuccess(false);
				resultMap.setMessage("请选择省份");
				return resultMap;
			}
//			if(StringUtils.isBlank(dis.getDisCity())){
//				resultMap.setSuccess(false);
//				resultMap.setMessage("请选择城市");
//				return resultMap;
//			}
			if(StringUtils.isBlank(dis.getDisPhone())){
				resultMap.setSuccess(false);
				resultMap.setMessage("请输入手机号");
				return resultMap;
			}
			if(!dis.getDisPhone().matches("^(13|14|15|17|18)\\d{9}$")){
				resultMap.setSuccess(false);
				resultMap.setMessage("请输入正确的11位手机号");
				return resultMap;
			}
		}
		else{
			logger.info("更新为自取");
		}
		
		
		try {
			resultMap.setSuccess(true);
			resultMap.setData(distributionDAO.updateDistribution(dis));
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("获取失败",e);
		}
		return resultMap;
	}
	
	/**
	 * 获取默认配送信息
	 * @param request
	 * @param response
	 * @param baseParam
	 * @return
	 */
	@RequestMapping(value="get_default.do",method=RequestMethod.POST)
	@ResponseBody
	@Log("获取默认配送信息")
	public ResultMap getDefault(HttpServletRequest request, HttpServletResponse response, BaseParam baseParam){
		ResultMap resultMap = new ResultMap();
		baseParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			resultMap.setSuccess(true);
			resultMap.setData(dispatchService.selectDefaultManageDispatch(baseParam));
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("获取默认配送信息失败,用户为："+baseParam.getOperateUser(),e);
		}
		return resultMap;
	}
	
	/**
	 * 设置默认配送信息
	 * @param request
	 * @param response
	 * @param manageDispatchDTO
	 * @param baseParam
	 * @return
	 */
	@RequestMapping(value="set_default.do",method=RequestMethod.POST)
	@ResponseBody
	@Log("设置默认配送信息")
	public ResultMap setDefault(HttpServletRequest request, HttpServletResponse response,ManageDispatchDTO manageDispatchDTO, BaseParam baseParam){
		ResultMap resultMap = new ResultMap();
		baseParam.setOperateUser(this.getAuthenticatedUserId());
		try {
			resultMap.setSuccess(true);
			dispatchService.setDefaultManageDispatch(manageDispatchDTO,baseParam);
			resultMap.setMessage("操作成功");
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage(e.getMessage());
			logger.error("设置默认配送信息失败,用户为："+baseParam.getOperateUser(),e);
		}
		return resultMap;
	}
	
}
