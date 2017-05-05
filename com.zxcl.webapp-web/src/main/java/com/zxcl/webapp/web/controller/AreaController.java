package com.zxcl.webapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;

/**
 * 地区controller
 * @author zxj
 *
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AreaService areaService;

	/**
	 * 获得所有省
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/get_provinces.do")
	@ResponseBody
	public ResultMap getProvinces(HttpServletRequest request, HttpServletResponse response){
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.setSuccess(true);
			resultMap.setData(areaService.getProvinces());
		} catch (Exception e) {
			resultMap.setSuccess(false);
			logger.error("获取省市失败",e);
		}
		return resultMap;
	}
	/**
	 * 通过省code获得其下所有城市
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/get_citys_by_provinces.do")
	@ResponseBody
	public ResultMap getCitysByProvinceCode(HttpServletRequest request, HttpServletResponse response, String code){
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.setData(areaService.getCitysByProvinceCode(code));
			resultMap.setSuccess(true);
		} catch (Exception e) {
			resultMap.setSuccess(false);
			logger.error("获取省市失败",e);
		}
		return resultMap;
	}
}
