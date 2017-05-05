package com.zxcl.webapp.biz.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import bxtx.http.biz.service.CoreService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxcl.pingan_http.api.PinganHttpService;
import com.zxcl.pingan_http.api.RemovePinganQuotaService;
import com.zxcl.pingan_http.api.dto.ApplyInfoDTO;
import com.zxcl.pingan_http.api.dto.ClientUser;
import com.zxcl.pingan_http.api.dto.CvrgDTO;
import com.zxcl.pingan_http.api.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.ResourceVehicleCvrgService;
import com.zxcl.webapp.biz.service.ResourceVehicleService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.bizdto.ResourceVehicleDTO;
import com.zxcl.webapp.integration.dao.ResourceVehicleDAO;

/**
 * 
* @ClassName:  ResourceVehicleService 
* @Description: 车辆信息业务处理
* @author 赵晋
* @date 2015年11月18日 上午10:58:22
*
 */
@Service
public class ResourceVehicleServiceImpl implements ResourceVehicleService {

	protected Logger logger = Logger.getLogger(getClass());
	/**
	 * 车辆信息DAO
	 */
	@Autowired
	private ResourceVehicleDAO resourceVehicleDAO;
	
	@Autowired
	private CoreService plateMasterCoreService;
	
	@Autowired
	private ResourceVehicleCvrgService resourceVehicleCvrgService;
	
	@Autowired
	private InquiryService inquiryService;
	
	private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1000));

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PinganHttpService pinganHttpService;
	
	@Autowired
	private RemovePinganQuotaService removePinganQuotaService;
	
	
	/**
	 * 
	* @Title: getResVehicleByPlateNoService
	* @Description: 根据车牌号查询车辆信息
	* @param plateNo 车牌号
	* @throws BusinessServiceException
	* @return ResourceVehicleDTO
	* @throws
	 */
	@Override
	public ResourceVehicleDTO getResVehicleByPlateNoService(String plateNo)
			throws BusinessServiceException {
		logger.info("查询车牌的车辆信息 入参    车牌号："+plateNo);
		ResourceVehicleDTO resourceVehicleDTO = null;
		try {
			resourceVehicleDTO = resourceVehicleDAO.getResVehicleByPlateNoDAO(plateNo);
		} catch (Exception e) {
			logger.error("查询车牌:" + plateNo + "的车辆信息失败:"  + e,e);
			throw new BusinessServiceException("查询车牌的车辆信息失败");
		}
		return resourceVehicleDTO;
	}

	@Override
	public ResourceVehicleDTO getResVehicleByModelName(String modelName)throws BusinessServiceException {
		logger.info("查询车牌的车辆信息 入参    modelName："+modelName);
		ResourceVehicleDTO resourceVehicleDTO = null;
		try {
			resourceVehicleDTO = resourceVehicleDAO.getResVehicleByModelName(modelName);
		} catch (Exception e) {
			logger.error("查询车辆信息失败:"  + e,e);
			throw new BusinessServiceException("查询车牌的车辆信息失败");
		}
		return resourceVehicleDTO;
	}
	
	public void insert(String inquiryId){
		logger.info("资源信息添加，询价单号："+inquiryId);
		//资源信息
		try {
			InquiryDTO inquiryDTO = inquiryService.getInquiryVehicleByInquiryId(inquiryId);
			if(null != inquiryDTO && inquiryDTO.getPlateNo().length() == 7){
				logger.info("删除该车牌存在的信息，车牌为："+inquiryDTO.getPlateNo());
				resourceVehicleDAO.delete(inquiryDTO.getPlateNo());
				logger.info("重新添加该车牌相关的信息，车牌为："+inquiryDTO.getPlateNo());
				//重新添加
				ResourceVehicleDTO resourceVehicleDTO = new ResourceVehicleDTO();
				resourceVehicleDTO.setPlateNo(inquiryDTO.getPlateNo());
				resourceVehicleDTO.setEngNo(inquiryDTO.getEngNo());
				resourceVehicleDTO.setFrmNo(inquiryDTO.getFrmNo());
				resourceVehicleDTO.setFstRegYm(inquiryDTO.getFstRegNoStr());
				resourceVehicleDTO.setCertfCde(inquiryDTO.getCertfCde());
				resourceVehicleDTO.setTciInsureEnd(inquiryDTO.getTciEndDateStr());
				resourceVehicleDTO.setVciInsureEnd(inquiryDTO.getVciEndDateStr());
				resourceVehicleDTO.setOwnerNme(inquiryDTO.getOwnerName());
				resourceVehicleDTO.setModelNme(inquiryDTO.getVehicleName());
				resourceVehicleDAO.insert(resourceVehicleDTO);
			}else{
				return;
			}
		} catch (Exception e) {
			logger.error("生成保单成功添加资源信息失败",e);
		}
		//险别资源信息
		try {
			logger.info("添加资源信息");
			resourceVehicleCvrgService.insert(inquiryId);
		} catch (Exception e) {
			logger.error("生成保单成功添加险种资源信息失败",e);
		}
	}

	@Override
	public ResourceVehicleDTO getVehicleByRMI(final String areaCode, final String plateNo, final int totalSeconds, final String userId,final String ownerName) throws Exception {
		logger.info("getVehicleByRMI参数==>areaCode="+areaCode+",plateNo="+plateNo+",ownerName="+ownerName+",userId="+userId);
		ResourceVehicleDTO obj = null;
		String responsJson = null;
		
		long startTime = System.currentTimeMillis();
		
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				String requestJson = "{\"areaCode\":\""+areaCode+"\", \"plateNo\":\""+plateNo+"\", \"ownerName\":\""+ownerName+"\", \"caller\":\""+userId+"\"}";
				return plateMasterCoreService.queryVhlAndRenewalInfo(requestJson);
			}
		});
		
		EXECUTOR_SERVICE.submit(task);
		TimeUnit.SECONDS.sleep(1);
		
		try {
			while(true){
				if(task.isDone()){
					responsJson = task.get();
					break;
				}
				if((System.currentTimeMillis() - startTime) > totalSeconds){
					logger.info(totalSeconds+"ms内未有数据返回");
					break;
				}
				TimeUnit.MILLISECONDS.sleep(200);
			}
			
			if(StringUtils.isNotBlank(responsJson) && responsJson.startsWith("{")){
				obj = JSONObject.parseObject(responsJson, ResourceVehicleDTO.class);
			}
		} catch (Exception e) {
			logger.error("车牌投保查询失败", e);
		}
		return obj;
	}

	@Override
	public ResourceVehicleDTO getVehicleByRMI(String plateNo, String frmNO, String enginNo, String ownerName, String certNo, String userId) throws Exception {
		logger.info(String.format("getVehicleByRMI,plateNo[%s], frmNO[%s], enginNo[%s], ownerName[%s], certNo[%s]",plateNo, frmNO, enginNo, ownerName, certNo));
		ResourceVehicleDTO resourceVehicleDTO = new ResourceVehicleDTO();
		
		if(StringUtils.isBlank(plateNo) || plateNo.contains("*")){
			return resourceVehicleDTO;
		}
		
		//匹配地区编码
		String areaCode = this.queryTeamAreaCodeByUserId(userId); 
		if(StringUtils.isBlank(areaCode)){
			areaCode = this.queryAreaCodeByShortPlateNo(plateNo.substring(0, 2));
		}
		if(StringUtils.isBlank(areaCode)){
			areaCode = this.queryAreaCodeByShortPlateNo(plateNo.substring(0, 1)+"A");
		}
		if(StringUtils.isBlank(areaCode)){
			return null;
		}
		logger.info("匹配的地区编码:"+areaCode);
		
		ResourceVehicleDTO rmiResourceVehicleDTO = this.doPlateQuery(areaCode, plateNo, ownerName);
		if(null == rmiResourceVehicleDTO){
			rmiResourceVehicleDTO = new ResourceVehicleDTO();
		}
		if(null != rmiResourceVehicleDTO){
			//保险期限不能为空
			String tciInsureEnd = rmiResourceVehicleDTO.getTciInsureEnd();
			String vciInsureEnd = rmiResourceVehicleDTO.getVciInsureEnd();
			if(StringUtils.isBlank(tciInsureEnd) && StringUtils.isBlank(vciInsureEnd)){
				//平安平台获取保险期限
				rmiResourceVehicleDTO = this.quotaFromPinganHttp(rmiResourceVehicleDTO, areaCode, plateNo, frmNO, enginNo, ownerName, certNo);
			}
		}
		if(null != rmiResourceVehicleDTO){
			resourceVehicleDTO = rmiResourceVehicleDTO;
		}
		
		logger.info(String.format("getVehicleByRMI,resourceVehicleDTO[%s]", JSON.toJSONString(resourceVehicleDTO)));
		return resourceVehicleDTO;
	}
	
	
	protected ResourceVehicleDTO doPlateQuery(String areaCode, String plateNo, String ownerName){
		if(StringUtils.isBlank(plateNo) || plateNo.contains("*")){
			return null;
		}
		try {
			
			
			
			//远程查询
			logger.info("尝试远程查询");
			ResourceVehicleDTO resultRmi = this.getVehicleByRMI(areaCode, plateNo, 18000, "bxtx", null);
			logger.info("远程查询结果,resultRmi:"+JSON.toJSONString(resultRmi));
			return resultRmi;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	protected ResourceVehicleDTO quotaFromPinganHttp(ResourceVehicleDTO resultRmi, String areaCode, String plateNo, String frmNO, String enginNo, String ownerName, String certNo){
		try {
			if(StringUtils.isBlank(resultRmi.getPlateNo())){
				resultRmi.setPlateNo(plateNo);
			}
			if(StringUtils.isBlank(resultRmi.getFrmNo())){
				resultRmi.setFrmNo(frmNO);
			}
			if(StringUtils.isBlank(resultRmi.getEngNo())){
				resultRmi.setEngNo(enginNo);
			}
			if(StringUtils.isBlank(resultRmi.getOwnerNme())){
				resultRmi.setOwnerNme(ownerName);
			}
			if(StringUtils.isBlank(resultRmi.getCertfCde())){
				resultRmi.setCertfCde(certNo);
			}
			Map<String, String> configMap = this.buildConfigMap(areaCode);
			if(null == configMap || configMap.isEmpty()){
				return resultRmi;
			}
			ApplyInfoDTO applyInfoDTO = this.buildApplyInfoDTO(resultRmi, this.buildConfigMap(areaCode));
			String quotaResultJson = null;
			if(null != applyInfoDTO){
				QuoteBackMessageDTO result = pinganHttpService.quote(applyInfoDTO);
				quotaResultJson = JSON.toJSONString(result);
				logger.info("报价结果:"+quotaResultJson);
			}
			return this.fullDate(resultRmi, quotaResultJson, applyInfoDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resultRmi;
	}
	
	protected ResourceVehicleDTO fullDate(ResourceVehicleDTO resultRmi, String quotaResultJson, ApplyInfoDTO applyInfoDTO){
		logger.info("fullDate,如存在重复投保信息,解析保险期限;删除报价单");
		
		if(StringUtils.isBlank(quotaResultJson)){
			return resultRmi;
		}
		
		//删除报价单
		String quotaId = null;
		try {
			JSONObject obj = JSON.parseObject(quotaResultJson);
			if(null != obj.getString("quotaId")){
				String removeResult = removePinganQuotaService.removePinganQuota(applyInfoDTO.getClientUser(), quotaId);
				logger.info("删除报价单"+quotaId+"结果:"+removeResult);
			}
		} catch (Exception e) {
			logger.error("删除报价单"+quotaId+"失败", e);
		}
		
		if(!quotaResultJson.contains("起保日期") && !quotaResultJson.contains("终保日期")){
			return resultRmi;
		}
		try {
			String startDate = "";
			String endDate = "";
			if(quotaResultJson.contains("起保日期")){
				int idx = quotaResultJson.indexOf("起保日期");
				startDate = quotaResultJson.substring((idx+4), quotaResultJson.indexOf(";", idx));
			}
			if(quotaResultJson.contains("终保日期")){
				int idx = quotaResultJson.indexOf("终保日期");
				endDate = quotaResultJson.substring((idx+4), quotaResultJson.indexOf(";", idx));
			}
			if(StringUtils.isNotBlank(startDate)){
				startDate = startDate.trim();
				startDate = startDate.replace(" 00", "");
				if(startDate.length() == 10){
					startDate += " 00:00:00";
				}
			}
			if(StringUtils.isNotBlank(endDate)){
				endDate = endDate.trim();
				endDate = endDate.replace(" 00", "");
				if(endDate.length() == 10){
					endDate += " 00:00:00";
				}
			}
			logger.info("startDate:"+startDate);
			logger.info("endDate:"+endDate);
			if(StringUtils.isNotBlank(endDate)){
				resultRmi.setTciInsureEnd(endDate);
				resultRmi.setVciInsureEnd(endDate);
				updateResourceVehicle(resultRmi);
				return resultRmi;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resultRmi;
	}
	
	protected void updateResourceVehicle(ResourceVehicleDTO resultRmi){
		logger.info("updateResourceVehicle,resultRmi:"+JSON.toJSONString(resultRmi));
		if(null == resultRmi){
			return;
		}
		if(StringUtils.isBlank(resultRmi.getPlateNo())){
			return;
		}
		if(StringUtils.isBlank(resultRmi.getTciInsureEnd()) && StringUtils.isBlank(resultRmi.getVciInsureEnd())){
			return;
		}
		try {
			String sql = "UPDATE t_resource_vehicle SET tci_insure_end='"+resultRmi.getTciInsureEnd()+"', vci_insure_end='"+resultRmi.getVciInsureEnd()+"', UPD_TM=NOW() WHERE PLATE_NO='"+resultRmi.getPlateNo()+"'";
			int c = jdbcTemplate.update(sql);
			logger.info("影响行数:"+c);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	protected ApplyInfoDTO buildApplyInfoDTO(ResourceVehicleDTO vhlJson, Map<String, String> configMap){
		if(StringUtils.isBlank(vhlJson.getPlateNo())){
			logger.info("平安爬虫报价,车牌必传");
			return null;
		}
		if(StringUtils.isBlank(vhlJson.getCertfCde())){
			logger.info("平安爬虫报价,车主证件号必传");
			return null;
		}
		if(StringUtils.isBlank(vhlJson.getFrmNo())){
			logger.info("平安爬虫报价,车架号必传");
			return null;
		}
		if(StringUtils.isBlank(vhlJson.getEngNo())){
			logger.info("平安爬虫报价,发动机号必传");
			return null;
		}
		if(StringUtils.isBlank(vhlJson.getOwnerNme())){
			logger.info("平安爬虫报价,车主姓名必传");
			return null;
		}
		ApplyInfoDTO apply = new ApplyInfoDTO();
		ClientUser clientUser = new ClientUser();
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get("PINGANHTTP_USERNAME")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get("PINGANHTTP_PASSOWRD")));
		HashMap<String, String> userConfigMap = new HashMap<String, String>();
		userConfigMap.put("PINGANHTTP_PARTNERWORKNETCODE", CommonUtil.valueOf(configMap.get("PINGANHTTP_PARTNERWORKNETCODE")));//合作网点代码必传
		userConfigMap.put("PINGANHTTP_TAX3_NO", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX3_NO")));
		userConfigMap.put("PINGANHTTP_TAX3_ORG", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX3_ORG")));
		userConfigMap.put("PINGANHTTP_TAX5_NO", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX5_NO")));
		userConfigMap.put("PINGANHTTP_TAX5_ORG", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX5_ORG")));
		
		clientUser.setMap(userConfigMap);
		clientUser.setXiaoweiUserId("test");
		apply.setClientUser(clientUser);
		apply.setProductCode("C51C01"); //C51C01 联合单; C51交强; C01商业;
		apply.setOwnerCertNo(vhlJson.getCertfCde());
		apply.setOwnerName(vhlJson.getOwnerNme());
		apply.setVhlModelName("别克SGM7160MTB轿车"); //车型名称
		apply.setVhlModelPriceNoTax("113900.00");//车价(不含税)
		apply.setPlateNo(vhlJson.getPlateNo());
		apply.setEngineNo(vhlJson.getEngNo());
		apply.setFirstRegDate(StringUtils.isBlank(vhlJson.getFstRegYm())?"2015-11-21":vhlJson.getFstRegYm());
		apply.setFrameNo(vhlJson.getFrmNo());
		apply.setChangeOwnerFlag("0");//是否过户1/0
		apply.setTransferDate(null);//过户日期
		
		List<CvrgDTO> cvrgList = new ArrayList<CvrgDTO>();
		CvrgDTO cvrg = new CvrgDTO();
		cvrg.setCvrgCode("01");//车损
		cvrg.setAmount("113900.00");
		cvrgList.add(cvrg);
		apply.setCvrgList(cvrgList);
		
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DATE, 1);
		apply.setJqBeginDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(cld.getTime()));
		apply.setSyBeginDate(apply.getJqBeginDate());
		
		cld = Calendar.getInstance();
		cld.add(Calendar.YEAR, 1);
		apply.setJqEndDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(cld.getTime()));
		apply.setSyEndDate(apply.getJqEndDate());
		
		logger.info("buildApplyInfoDTO,apply:"+JSON.toJSONString(apply));
		return apply;
	}
	
	protected String queryConfigIdByAreaCode(final String areaCode){
		logger.info("获取configId:"+areaCode);
		if(StringUtils.isBlank(areaCode)){
			return null;
		}
		
		String configId = jdbcTemplate.query("SELECT CONFIG_ID FROM `t_ins_company_config_area` WHERE CONFIG_TYPE=2 AND INS_ID='PINGANHTTP' AND AREA_CODE=? ORDER BY RAND() LIMIT 1", new String[]{areaCode},  new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()){
					return rs.getString(1);
				}
				return null;
			}
		});
		logger.info("configId:"+configId);
		return configId;
	}
	
	private Map<String, String> buildConfigMap(String areaCode){
		logger.info("buildConfigMap,areaCode:"+areaCode);
		if(StringUtils.isBlank(areaCode)){
			return null;
		}
//		HTTP_PINGAN_JIACHENG_CHENGXI
//		Map<String, String> configMap = new HashMap<>();
//		configMap.put("PINGANHTTP_USERNAME", "DYJCBDCXWD-00002");
//		configMap.put("PINGANHTTP_PASSOWRD", "S9A7Gc8f");
//		configMap.put("PINGANHTTP_PARTNERWORKNETCODE", "226931204054");
//		configMap.put("PINGANHTTP_TAX3_NO", "250111");
//		configMap.put("PINGANHTTP_TAX3_ORG", "四川税务局");
//		return configMap;
		
		String configId = this.queryConfigIdByAreaCode(areaCode);
		if(StringUtils.isBlank(configId)){
			if(!areaCode.endsWith("000")){
				String parentAreaCode = (areaCode.substring(0, 3)+"000");
				logger.info("查询父级地区configId,parentAreaCode:"+parentAreaCode);
				configId = this.queryConfigIdByAreaCode(parentAreaCode);
			}
			
		}
		if(StringUtils.isBlank(configId)){
			return null;
		}
		
		logger.info("configId:"+configId);
		HashMap<String, String> resultMap = jdbcTemplate.query("SELECT CONFIG_KEY, CONFIG_VALUE FROM t_ins_company_config WHERE CONFIG_ID=? and CONFIG_STATUS='1'", new String[]{configId},  new ResultSetExtractor<HashMap<String, String>>(){

			@Override
			public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap<String, String> configMap = new HashMap<>();
				while(rs.next()){
					configMap.put(rs.getString("CONFIG_KEY"), rs.getString("CONFIG_VALUE"));
				}
				return configMap;
			}
			
		});
		logger.info("configMap:"+JSON.toJSONString(resultMap));
		return resultMap;
	}
	
	protected String queryAreaCodeByShortPlateNo(final String shortPlateNo){
		logger.info("获取地区码,shortPlateNo:"+shortPlateNo);
		String areaCode = jdbcTemplate.query("select area_code from t_area where area_shortname=? order by area_id asc limit 1", new String[]{shortPlateNo},  new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()){
					return rs.getString(1);
				}
				return null;
			}
		});
		logger.info("areaCode:"+areaCode);
		return areaCode;
	}
	
	protected String queryTeamAreaCodeByUserId(final String userId){
		logger.info("获取用户团队所属地区码,userId:"+userId);
		if(StringUtils.isBlank(userId)){
			return null;
		}
		String areaCode = jdbcTemplate.query("SELECT AREA_CODE FROM t_team_area WHERE TEAM_ID=(SELECT AGT_TEAM_ID FROM t_micro_info WHERE USER_ID=? AND STATUS='1') AND STATUS='1' LIMIT 1", new String[]{userId},  new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()){
					return rs.getString(1);
				}
				return null;
			}
		});
		logger.info("areaCode:"+areaCode);
		return areaCode;
	}
	
}
