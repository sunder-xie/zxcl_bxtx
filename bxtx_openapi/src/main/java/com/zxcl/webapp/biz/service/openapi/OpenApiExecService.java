package com.zxcl.webapp.biz.service.openapi;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;

import bxtx.http.biz.service.CoreService;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.zxcl.webapp.biz.action.call.HttpAxatpCallAction;
import com.zxcl.webapp.biz.action.call.HttpPingAnCallAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.openapi.dto.CvrgDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnBaseDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnReInsureDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnReqDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnRespDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnTaxResultDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnTciResultDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnVciResultDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnVhlDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnVhlOwnerDTO;
import com.zxcl.webapp.biz.service.openapi.dto.RenewalQueryByPlateReqDTO;
import com.zxcl.webapp.biz.service.openapi.dto.RenewalQueryByPlateRespDTO;
import com.zxcl.webapp.biz.service.openapi.dto.RenewalVehicleDTO;
import com.zxcl.webapp.biz.service.openapi.dto.VhlModelDTO;
import com.zxcl.webapp.biz.service.openapi.dto.VhlQueryReqDTO;
import com.zxcl.webapp.biz.service.openapi.dto.VhlQueryRespDTO;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.DriverDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;
import com.zxcl.webapp.dto.bizdto.ResourceVehicleDTO;
import com.zxcl.webapp.dto.openapi.MsgDTO;
import com.zxcl.webapp.dto.openapi.ResultMsgDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vehicle.resp.VehicleModelDTO;

@Service
public class OpenApiExecService {

	private Logger logger = Logger.getLogger("agent.api");
	
//	@Autowired
//	private EjintaiCallAction ejintaiCallAction;
	
	@Autowired
	private CoreService plateMasterCoreService;
	
	@Autowired
	private OpenApiManageService openApiManageService;
	
	@Autowired
	private HttpAxatpCallAction httpAxatpCallAction;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private HttpPingAnCallAction httpPingAnCallAction;
	
	@SuppressWarnings("unchecked")
	public <T> T decode(String xml, Class<T> clazz) {
		XStream xs = new XStream();
		xs.alias("packet", clazz);
		xs.alias("cvrg", CvrgDTO.class);
		return (T)xs.fromXML(xml);
	}
	
	public String encode(Object o){
		XStream xs = new XStream();
		xs.alias("packet", o.getClass());
		xs.alias("vehicleModel", VhlModelDTO.class);
		xs.alias("cvrg", CvrgDTO.class);
		return xs.toXML(o);
	}
	
	public ResultMsgDTO agentVhlQuery(MsgDTO msg) {
		logger.info("车型查询请求报文:" + msg.getBizContent());
		VhlQueryReqDTO req = decode(msg.getBizContent(),VhlQueryReqDTO.class);
		
		List<VhlModelDTO> apiVhlList = new ArrayList<VhlModelDTO>();
		try {
			InsuranceDTO ins = new InsuranceDTO();
			ins.setInsId("JTIC");
			List<VehicleModelDTO> qryResult = doVhlQuery(msg.getAppId(), req.getVehicleName());
			if(qryResult != null){
				for(VehicleModelDTO v : qryResult){
					VhlModelDTO apiVhl = new VhlModelDTO();
					apiVhl.setVehicleAlias(v.getVehicleAlias());
					apiVhl.setMarketaDate(v.getMarketaDate());
					apiVhl.setRemark(v.getRemark());
					apiVhl.setVehicleExhaust(v.getDisplacement().toString());
					apiVhl.setVehicleId(v.getModelCode());
					apiVhl.setVehicleName(v.getVehicleName());
					apiVhl.setVehiclePrice(v.getVehiclePrice().toString());
					apiVhl.setVehicleSeat(String.valueOf(v.getSeatNum()));
					apiVhlList.add(apiVhl);
				}
			}
			
		} catch (ActionException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		VhlQueryRespDTO resp = new VhlQueryRespDTO();
		resp.setErrorCode("0000");
		resp.setErrorMessage("查询成功");
		resp.setVehicleModelList(apiVhlList);
		String respXml = encode(resp);
		logger.info("车型查询返回报文:" + respXml);
		ResultMsgDTO r = new ResultMsgDTO();
		r.setBizRetCode(resp.getErrorCode());
		r.setBizRetMsg(resp.getErrorMessage());
		r.setResultXml(respXml);
		return r;
	}
	
	private List<VehicleModelDTO> doVhlQuery(String userId,String vhlModelName) throws ActionException {
		InsuranceDTO ins = new InsuranceDTO();
		ins.setInsId("JTIC");
//		List<VehicleModelDTO> qryResult = ejintaiCallAction.vehicleQuery(userId, vhlModelName, ins);
//		return qryResult;
		return null;
	}
	
	
	public ResultMsgDTO agentRenewalQuery(MsgDTO msg){
		
		logger.info("车牌续保查询请求报文:" + msg.getBizContent());
		String plateNo = null;
		boolean found = false;
		RenewalQueryByPlateRespDTO resp = new RenewalQueryByPlateRespDTO();
		try {
			
			RenewalQueryByPlateReqDTO req = decode(msg.getBizContent(), RenewalQueryByPlateReqDTO.class);
			logger.info("车牌:" + req.getPlateNo() + ",地区:" + req.getCityCode());
			if(StringUtils.isEmpty(req.getPlateNo())){
				badArg("plateNo必填");
			}
			if(StringUtils.isEmpty(req.getCityCode())){
				badArg("cityCode必填");
			}
			
			plateNo = req.getPlateNo();
			String requestJson = "{\"areaCode\":\""+req.getCityCode()+"\", \"plateNo\":\""+req.getPlateNo()+"\", \"caller\":\""+msg.getAppId()+"\"}";
			String responsJson = plateMasterCoreService.queryVhlAndRenewalInfo(requestJson);
			ResourceVehicleDTO resVhl = null;
			if(StringUtils.isNotBlank(responsJson) && responsJson.startsWith("{")){
				resVhl = JSONObject.parseObject(responsJson, ResourceVehicleDTO.class);
			}
			logger.info("车牌续保查询资源库返回结果:" + resVhl);
			
			if(resVhl == null){
				resp.setErrorCode("NO_RESULT");
				resp.setErrorMessage("未查询到结果");
				found = false;
				String respXml =  encode(resp);
				ResultMsgDTO r = new ResultMsgDTO();
				r.setBizRetCode(resp.getErrorCode());
				r.setBizRetMsg(resp.getErrorMessage());
				r.setResultXml( respXml );
				return r;
			}
			
			resp.setErrorCode("0000");
			resp.setErrorMessage("查询成功");
			
			logger.info("转换为接口对象结构");
			RenewalVehicleDTO resultVhl = new RenewalVehicleDTO();
			resultVhl.setCertfCde(resVhl.getCertfCde());
			resultVhl.setEngNo(resVhl.getEngNo());
			resultVhl.setFrmNo(resVhl.getFrmNo());
			resultVhl.setFstRegYm(resVhl.getFstRegYm());
			resultVhl.setModelNme(resVhl.getModelNme());
			resultVhl.setOwnerNme(resVhl.getOwnerNme());
			resultVhl.setPlateNo(resVhl.getPlateNo());
			resultVhl.setTciInsureEnd(resVhl.getTciInsureEnd());
			resultVhl.setVciInsureEnd(resVhl.getVciInsureEnd());
			resp.setRenewalVehicle(resultVhl);
			
			List<CvrgDTO> resultCvrgList = new ArrayList<CvrgDTO>();
			if(resVhl.getCvrgList() != null){
				for(ResourceVehicleCvrgDTO resCvrg : resVhl.getCvrgList()){
					CvrgDTO resultCvrg = new CvrgDTO();
					String cvrgId = resCvrg.getCvrgId();
					if(StringUtils.isEmpty(cvrgId))
						continue;
					if( cvrgId.equals("030006") 
							|| cvrgId.equals("030018") 
							|| cvrgId.equals("030001") 
							|| cvrgId.equals("030009")
							|| cvrgId.equals("030061")
							|| cvrgId.equals("030004")
							|| cvrgId.equals("032601")
							|| cvrgId.equals("030012")
							|| cvrgId.equals("032608")
							|| cvrgId.equals("032618")
							|| cvrgId.equals("033008")
							){
						resultCvrg.setCoverageCode( resCvrg.getCvrgId() );
						resultCvrg.setGlsType( resCvrg.getGlsType());
						resultCvrg.setSumLimit(resCvrg.getGvrgAmount() == null ? null : resCvrg.getGvrgAmount().toString());
						resultCvrg.setNoDduct(resCvrg.getExcldDeductible() == null ? "0" : resCvrg.getExcldDeductible());
						resultCvrgList.add(resultCvrg);
					}
				}
			}
			resultVhl.setCvrgList(resultCvrgList);
			found = true;
			String respXml = encode(resp);
			ResultMsgDTO r = new ResultMsgDTO();
			r.setBizRetCode(resp.getErrorCode());
			r.setBizRetMsg(resp.getErrorMessage());
			r.setResultXml( respXml );
			return r;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			
			found = false;
			String errorCode = "9999";
			String errorMsg = "系统错误:" + e.getMessage();
			if( e instanceof RemoteAccessException){
				RemoteAccessException e1 = (RemoteAccessException)e;
				if(e1.getCause() instanceof SocketTimeoutException) {
					errorMsg = "未查询到结果";
					logger.warn("查询超时,直接返回空");
				}
			}
			if(e instanceof IllegalArgumentException){
				errorMsg = "参数错误:" + e.getMessage();
			}
			
			resp.setErrorCode(errorCode);
			resp.setErrorMessage(errorMsg);
			String respXml = encode(resp);
			ResultMsgDTO r = new ResultMsgDTO();
			r.setBizRetCode(resp.getErrorCode());
			r.setBizRetMsg(resp.getErrorMessage());
			r.setResultXml( respXml );
			return r;
		} finally {
			
//			if(plateNo != null) {
//				addPlateQueryRecord(msg,plateNo, found ? "1" : "0");
//			}
			
		}

	}
	
//	private void addPlateQueryRecord(MsgDTO msg, String plateNo, String queryResult){
//		try {
//			ApiCallPlateQueryRecordDTO record = new ApiCallPlateQueryRecordDTO();
//			record.setAppId(msg.getAppId());
//			record.setCallId(msg.getCallId());
//			record.setPlateNo(plateNo);
//			record.setQueryResult(queryResult);
//			openApiManageService.addApiCallPlateQueryRecord(record);
//		} catch(Exception e) {
//			logger.error("新增车牌查询接口调用记录异常:" + e.getMessage(),e);
//		}
//	}
	
	public static void badArg(String m) {
		throw new IllegalArgumentException(m);
	}
	
	public ResultMsgDTO agentVhlQuote(MsgDTO msg){
		
		try {
			logger.info("报价请求报文:" + msg.getBizContent());
			
			QtnReqDTO req = decode(msg.getBizContent(), QtnReqDTO.class);
			logger.info("解析到请求对象");
			
			QtnBaseDTO qtnBase = req.getQtnBase();
			if(qtnBase == null)
				badArg("基础信息必填");
			
			String insCompanyCode = req.getQtnBase().getInsCompanyCode();
			logger.info("报价保险公司:" + insCompanyCode + ",使用账号:" + req.getQtnBase().getInsUserName());
			
			//TODO 校验
			
			logger.info("保存报价单");
			
			
			InquiryDTO inquiry = new InquiryDTO();
			String inquiryId = DateUtil.dateToString("yyyyMMddHHmmssSSS",new Date());
			inquiry.setInquiryId(inquiryId);
			inquiry.setState("0");
			
			inquiry.setModelCode( req.getVehicle().getVehicleId() );
			
			QtnVhlDTO qtnVhl = req.getVehicle(); //报价录入的车辆数据
			
			QtnVhlOwnerDTO vhlOwner = req.getVehicleOwner();
			
			VehicleModelDTO vhlModel = null; //车型数据
			
			List<VehicleModelDTO> vhlList = doVhlQuery(msg.getAppId(), req.getVehicle().getVehicleName());
			if(vhlList != null){
				for(VehicleModelDTO vhl : vhlList){
					if(req.getVehicle().getVehicleId().equals(vhl.getModelCode())) {
						vhlModel = vhl;
						//TODO 检查车价、车型名称
						break;
					}
				}
			}
			if(vhlModel == null){
				throw new IllegalArgumentException("根据车型代码:" + qtnVhl.getVehicleId() + ",车型名称:" + qtnVhl.getVehicleName() + ",车价:" + qtnVhl.getVehiclePrice() + "未匹配到车型");
			}
			
			inquiry.setSeatNum(vhlModel.getSeatNum());
			inquiry.setVehiclePrice(vhlModel.getVehiclePrice().toString());
			inquiry.setDisplacement(vhlModel.getDisplacement());
			inquiry.setPlateNo(qtnVhl.getLicensePlateNo());
			inquiry.setCityCode("");//TODO
			inquiry.setNewCarSign(qtnVhl.getNoLicenseFlag());
			inquiry.setVciSign(qtnBase.getVciSign());
			inquiry.setTciSign(qtnBase.getTciSign());
			inquiry.setVehicleName(vhlModel.getVehicleName());
			inquiry.setRemark(vhlModel.getRemark());
			inquiry.setFstRegNo(DateUtil.stringToDate("yyyy-MM-dd", qtnVhl.getFirstRegisterDate()));
			inquiry.setFrmNo(qtnVhl.getVin());
			inquiry.setEngNo(qtnVhl.getEngineNo());
			inquiry.setOwnerName(vhlOwner.getOwnerName());
			inquiry.setOwnerCertNo(vhlOwner.getOwnerCertNo());
			inquiry.setOwnerAge(null); //TODO 解析身份证
			inquiry.setOwnerSex(null);
			inquiry.setOwnerBirthday(null);
			inquiry.setTransferSign(qtnVhl.getChgOwnerFlag());
			if(StringUtils.isNotEmpty(qtnVhl.getTransferDate())){
				inquiry.setTransferDate(DateUtil.stringToDate("yyyy-MM-dd", qtnVhl.getTransferDate()));
			}
			if("1".equals(inquiry.getTransferSign())){
				throw new IllegalArgumentException("过户车,过户日期必填");
			}
			if(StringUtils.isNotEmpty(qtnBase.getTciStartDate())){
				inquiry.setTciStartDate(DateUtil.stringToDate("yyyy-MM-dd",qtnBase.getTciStartDate()));
			}
			if(StringUtils.isNotEmpty(qtnBase.getTciEndDate())){
				inquiry.setTciEndDate(DateUtil.stringToDate("yyyy-MM-dd",qtnBase.getTciEndDate()));
			}
			if(StringUtils.isNotEmpty(qtnBase.getVciStartDate())){
				inquiry.setVciStartDate(DateUtil.stringToDate("yyyy-MM-dd",qtnBase.getVciStartDate()));
			}
			if(StringUtils.isNotEmpty(qtnBase.getVciEndDate())){
				inquiry.setVciEndDate(DateUtil.stringToDate("yyyy-MM-dd",qtnBase.getVciEndDate()));
			}
			
			inquiry.setCrtCode(msg.getAppId());
			inquiry.setUpdCode(msg.getAppId());
			
			logger.info("将接口传入的险别转换为内部险别对象");
			List<CoverageItemDTO> cvrgList = new ArrayList<CoverageItemDTO>();
			for(CvrgDTO qtnCvrg : req.getCvrgList()){
				CoverageItemDTO item = new CoverageItemDTO();
				if(StringUtils.isNotEmpty(qtnCvrg.getSumLimit()))
					item.setSumLimit(new BigDecimal(qtnCvrg.getSumLimit()));
				item.setCode(qtnCvrg.getCoverageCode());
				
				if(StringUtils.isNotEmpty(qtnCvrg.getNoDduct()))
					item.setNoDduct(Integer.parseInt(qtnCvrg.getNoDduct()));
				
				item.setGlsType(qtnCvrg.getGlsType());
				
				cvrgList.add(item);
			}
			
			//注: insertInquriy 接口要求传入userId, 但接口并不存在userId, 暂时传入appId;
			// insertInquriy内部并不支持对appId的处理
			// 另外  DriverDTO 已废弃
			// TODO: 统筹用户体系,改造接口
			inquiryService.insertInquiry(msg.getAppId(), inquiry, cvrgList, new ArrayList<DriverDTO>());
			
			logger.info("询价单已保存,询价单号:" + inquiry.getInquiryId());
			
			//装配ConfigMap
			//因为当前对接的两家公司,都是只需要登录账号即可, 因此这里直接用传入的账号和密码来生成配置
			//TODO 如果后面要支持使用config_id来做配置,则insUserName字段要求对方传入对应的config_id(是否可以?需要梳理所有保险公司的情况后决定)
			//TODO 当前并不需要使用到t_api_app_qtn_config表, 也可能要在这个表中增加一个config_id列, 待定
			
			QtnRespDTO resp = new QtnRespDTO();
			QuotaReturnDTO ret = null;
			Map<String,Object> configMap = new HashMap<String, Object>();
			if("TIANPING_HTTP".equals(insCompanyCode)) {
				logger.info("天平报价开始");
				
				configMap.put("TPBXHTTP_AgentUserId", req.getQtnBase().getInsUserName());
				configMap.put("TPBXHTTP_AgentUserPwd", req.getQtnBase().getInsUserPwd());
				configMap.put("TPBXHTTP_XiaoweiUserId", "-");
				logger.info("报价configMap=" + configMap);
				ret = httpAxatpCallAction.quotas("-", inquiryId, "TPBXHTTP", configMap);
			} else if("PINGAN_HTTP".equals(insCompanyCode)){
				logger.info("平安报价开始");
				configMap.put("PINGANHTTP_USERNAME", req.getQtnBase().getInsUserName());
				configMap.put("PINGANHTTP_PASSOWRD", req.getQtnBase().getInsUserPwd());
				logger.info("报价configMap=" + configMap);
				ret = httpPingAnCallAction.quotas("-", inquiryId, "PINGANHTTP", configMap);
			} else {
				badArg("不支持的保险公司代码:" + insCompanyCode);
			}
			
			String retCode = ret.getErrorCode();
			if("SUCCESS".equals(retCode)) {
				
				logger.info("报价成功返回,解析结果");
				resp.setErrorCode("0000");
				resp.setErrorMessage("保费计算成功");
				
				resp.setQtnId(ret.getQuotaId());
				QtnTciResultDTO tci = new QtnTciResultDTO();
				if(ret.getPremTCITax() != null)
					tci.setPrm(ret.getPremTCITax().toString());
				else
					tci.setPrm("0");
				resp.setTciQuoteResult(tci);
				
				QtnVciResultDTO vci = new QtnVciResultDTO();
				if(ret.getPremVCITax() != null) {
					vci.setPrm(ret.getPremVCITax().toString());
					vci.setDisccount(ret.getDiscount().toString());
				} else {
					vci.setPrm("0");
					vci.setDisccount("0");
				}
				resp.setVciQuoteResult(vci);
				List<CvrgDTO> respCvrgList = new ArrayList<CvrgDTO>();
				resp.setCvrgList(respCvrgList);
				List<CoverageItemDTO> retCvrgList = ret.getCoverageItems();
				if(retCvrgList != null){
					for(CoverageItemDTO item : retCvrgList){
						CvrgDTO c = new CvrgDTO();
						c.setCoverageCode(item.getCode());
						c.setPrm(item.getAmount().toString());
						if(item.getSumLimit() != null)
							c.setSumLimit(item.getSumLimit().toString());
						respCvrgList.add(c);
					}
				}
				
				QtnTaxResultDTO tax = new QtnTaxResultDTO();
				resp.setTaxQuoteResult(tax);
				if(ret.getVehicleTax() != null){
					tax.setTaxBeginDate(ret.getTaxBeginDate());
					tax.setTaxEndDate(ret.getTaxEndDate());
					
					if(ret.getTaxCurrentYear() != null){
						tax.setTaxCurrentYear(ret.getTaxCurrentYear().toString());
					}
					if(ret.getTaxOverdue() != null){
						tax.setTaxOverdue(ret.getTaxOverdue().toString());
					}
					tax.setTaxTotal(ret.getVehicleTax().toString());
				}
				
				fillReInsureInfo(resp,ret);
				
			} else if("PSDERROR".equals(retCode)){
				resp.setErrorCode("9999");
				resp.setErrorMessage("保险公司账号错误,请联系后台修正配置");
			} else {
				resp.setErrorCode("9999");
				resp.setErrorMessage("保费计算失败:" + ret.getErrorMessages());
				//如果是重复投保导致失败,填充重复投保信息
				fillReInsureInfo(resp,ret);
			}
			
			String respXml = encode(resp);
			ResultMsgDTO r = new ResultMsgDTO();
			r.setBizRetCode(resp.getErrorCode());
			r.setBizRetMsg(resp.getErrorMessage());
			r.setResultXml( respXml );
			return r;
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			String errorCode = "9999";
			String errorMsg = "系统错误:" + e.getMessage();
			if(e instanceof IllegalArgumentException){
				errorMsg = "参数错误:" + e.getMessage();
			}
			
			QtnRespDTO resp = new QtnRespDTO();
			resp.setErrorCode(errorCode);
			resp.setErrorMessage(errorMsg);
			String respXml = encode(resp);
			ResultMsgDTO r = new ResultMsgDTO();
			r.setBizRetCode(resp.getErrorCode());
			r.setBizRetMsg(resp.getErrorMessage());
			r.setResultXml( respXml );
			return r;
		}
		
	}
	private void fillReInsureInfo(QtnRespDTO resp, QuotaReturnDTO ret){
		QtnReInsureDTO re = new QtnReInsureDTO();
		resp.setReInsureItem(re);
		re.setTciBeginDate(ret.getTciReInsureBeginDate());
		re.setTciEndDate(ret.getTciReInsureEndDate());
		re.setVciReInsure(ret.getVciReInsureSign());
		re.setVciBeginDate(ret.getVciReInsureBeginDate());
		re.setVciEndDate(ret.getVciReInsureEndDate());
	}
	

}
