package com.zxcl.webapp.biz.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.PolicyBaseService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.openapi.OpenApiManageService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.PolicyBaseDTO;
import com.zxcl.webapp.dto.openapi.ApiAppDTO;
import com.zxcl.webapp.integration.dao.PolicyBaseDAO;

/**
 * @ClassName: 
 * @Description:佣金提现 
 * @author zxj
 * @date 
 */
@Service
public class PolicyBaseServiceImpl implements PolicyBaseService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private PolicyBaseDAO policyBaseDAO;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
//	@Autowired
//	private OrderQueryService orderQueryService;
	
	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private OpenApiManageService openApiManageService;
	
	@Override
	@Log("新增费用计算记录数据")
	public void insertPolicyBase(final PolicyBaseDTO policyBaseDTO) throws BusinessServiceException {
		logger.info("insertPolicyBase.PolicyBaseDTO:" + JSONObject.toJSONString(policyBaseDTO));
		if(null == policyBaseDTO){
			logger.warn("policyBaseDTO对象为空");
			return;
		}
		try {
			int c = policyBaseDAO.insertSelective(policyBaseDTO);
			logger.info("影响行数" + c);
			uploadPolicy(policyBaseDTO);
		}catch(DuplicateKeyException dke){
			logger.info("结算重复,"+policyBaseDTO.getInsId()+"-"+policyBaseDTO.getProductCode()+"-"+policyBaseDTO.getPolicyNo());
		}
		catch (Exception e) {
			logger.error("新增费用计算记录数据失败", e);
		}
	}
	
	/**
	 * uploadPolicy
	 * @param policyBaseDTO
	 */
	protected void uploadPolicy(final PolicyBaseDTO policyBaseDTO){
		if(null != policyBaseDTO && StringUtils.isNotBlank(policyBaseDTO.getUserId())){
			try {
				MicroDTO microDTO = microService.getMicroByUserId(policyBaseDTO.getUserId());
				if(null == microDTO){
					logger.info("小薇不存在");
					return;
				}
				
				ApiAppDTO apiAppDTO = openApiManageService.findAppByUserId(policyBaseDTO.getUserId());
				logger.info("apiAppDTO:" + JSONObject.toJSONString(apiAppDTO));
				if(null == apiAppDTO || !"1".equals(apiAppDTO.getPolicyUploadSign()) || StringUtils.isBlank(apiAppDTO.getPolicyUploadUrl())){
					return ;
				}
				logger.info(String.format("保单上传,上传接口%s, 保单号%s", apiAppDTO.getPolicyUploadUrl(), policyBaseDTO.getPolicyNo()));
				HashMap<String, String> singMap = new HashMap<>();
				singMap.put("username", policyBaseDTO.getUserId());
				singMap.put("companyID", policyBaseDTO.getInsId());
				singMap.put("name", URLEncoder.encode(microDTO.getMicro_name(), "UTF-8"));
				singMap.put("owner", URLEncoder.encode(policyBaseDTO.getOwnerName(), "UTF-8"));
				singMap.put("plate", URLEncoder.encode(policyBaseDTO.getPlateNo(), "UTF-8"));
				singMap.put("frameno", policyBaseDTO.getFrmNo());
				singMap.put("product", policyBaseDTO.getProductCode());
				singMap.put("policyno", policyBaseDTO.getPolicyNo());
				singMap.put("premium", policyBaseDTO.getPolicyPrm().toString());
				singMap.put("timestamp", URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "UTF-8"));
				logger.info("待签名map:"+JSONObject.toJSONString(singMap));
				TreeMap<String, String> treeMap = new TreeMap<>();
				treeMap.putAll(singMap);
				Iterator<Entry<String, String>> itert = treeMap.entrySet().iterator();
				StringBuffer buffer = new StringBuffer("");
				boolean first = true;
				while(itert.hasNext()){
					Entry<String, String> entry = itert.next();
					String key = entry.getKey();
					String val = entry.getValue();
					if(StringUtils.isNotEmpty(val)){
						if(first){
							first = false;
						}
						else{
							buffer.append("&");
						}
						buffer.append(key).append("=").append(val);
					}
				}
				String preSignStr = URLDecoder.decode(buffer.toString(), "utf-8")+"&appKey="+apiAppDTO.getAppKey();
				logger.info("待签名参数:"+preSignStr);
				
				String sign = DigestUtils.md5DigestAsHex(preSignStr.getBytes("utf-8"));
				logger.info("签名sign:"+sign);
				
				String requestParams = buffer.toString()+"&sign="+sign;
				logger.info("请求参数:"+requestParams);
				
				String url = apiAppDTO.getPolicyUploadUrl();
				url = apiAppDTO.getPolicyUploadUrl().contains("?")?(url+"&"+requestParams) : (url+"?"+requestParams);
				logger.info("url:"+url);
				
				HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
				conn.addRequestProperty("User-Agent", "Application With BXTX-(UploadPlicy.0.307.03)");
				conn.setRequestMethod("GET");
				conn.setReadTimeout(10*1000);
				conn.setConnectTimeout(2*1000);
				conn.connect();
				InputStream inStream = conn.getInputStream();
				String result = inStream2String(inStream, "utf-8");
				logger.info("result:"+result);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * inStream2String
	 * @param inStream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	private String inStream2String(InputStream inStream, String charset) throws IOException{
		if(null == inStream){
			return null;
		}
			
		byte[] buff = new byte[512];
		int len = -1;
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		while((len = inStream.read(buff)) != -1){
			bao.write(buff, 0, len);
		}
		return bao.toString(charset);
	}
	
	@Override
	@Log("获取费用计算记录DTO")
	public List<PolicyBaseDTO> getPolicyBase(String orderId, String insId) throws BusinessServiceException {
		List<PolicyBaseDTO> dataList = new ArrayList<PolicyBaseDTO>();
		logger.info("insId="+insId+",orderId="+orderId);
		if(StringUtils.isBlank(insId) || StringUtils.isBlank(orderId)){
			return null;
		}
		Date date = new Date();
		PolicyBaseDTO policyBaseDTO = null;
		String userId = null;
//		OrderQueryDTO orderQueryDTO = null;
		MicroDTO microDTO = null;
		OrderDTO orderDTO = null;
		InquiryDTO inquiryDTO = null;
		BigDecimal vehicleTax = new BigDecimal("0.00");//车船税
		BigDecimal VCIPremTax = new BigDecimal("0.00");//商业保费
		BigDecimal TCIPremTax = new BigDecimal("0.00");//交强保费
		String vicPlyNo = "";//商业保单号
		String tciPlyNo = "";//交强保单号
		
		
		/**
		 * 组装数据
		 */
		logger.info("组装数据");
//		orderQueryDTO = orderQueryService.selectOrderByOrderIdAndInsId(orderId, insId);
//		if(null == orderQueryDTO){
//			logger.info("保单状态查询为空");
//			return null;
//		}

		logger.info("订单信息");
		orderDTO = orderService.getOrderById(null, insId, orderId);
		if(null == orderDTO){
			logger.info("订单信息不存在");
			return null;
		}
		
		userId = microService.getUserIdByMicId(orderDTO.getMicro().getMicro_id());
		if(null == userId){
			logger.info("用户不存在");
			return null;
		}
		
		microDTO = microService.getMicroByUserId(userId);
		if(null == microDTO){
			logger.info("小薇不存在");
			return null;
		}
		
		
		logger.info("询价单信息");
		inquiryDTO = inquiryService.getInquiryVehicleByInquiryId(orderDTO.getInquiry().getInquiryId());
		if(null == inquiryDTO){
			logger.info("询价单信息不存在");
			return null;
		}
		
		/**
		 * 设置数据
		 */
		logger.info("设置数据");
		if(null != orderDTO && null != orderDTO.getQuota()){
			
			//保费
			if(null != orderDTO.getQuota().getVehicleTax()){
				vehicleTax = vehicleTax.add(orderDTO.getQuota().getVehicleTax());
			}
			if(null != orderDTO.getQuota().getVCIPremTax()){
				VCIPremTax = VCIPremTax.add(orderDTO.getQuota().getVCIPremTax());
			}
			if(null != orderDTO.getQuota().getTCIPremTax()){
				TCIPremTax = TCIPremTax.add(orderDTO.getQuota().getTCIPremTax());
			}
			
			//保单号
			vicPlyNo = orderDTO.getVciPlyNo();
			tciPlyNo = orderDTO.getTciPlyNo();
		}
		
		logger.info("交强险保单号：" + tciPlyNo);
		if(StringUtils.isNotBlank(tciPlyNo)){
			policyBaseDTO = new PolicyBaseDTO();
			policyBaseDTO.setAgentId(microDTO.getAgency().getAgent_id());
			policyBaseDTO.setTeamId(microDTO.getAgt_team_id());
			policyBaseDTO.setCalStatus("0");//0未结算
			policyBaseDTO.setCreatedBy("system");
			policyBaseDTO.setCreatedDate(date);
			policyBaseDTO.setEngNo(inquiryDTO.getEngNo());
			policyBaseDTO.setFrmNo(inquiryDTO.getFrmNo());
			policyBaseDTO.setInsId(insId);
			policyBaseDTO.setOwnerName(inquiryDTO.getOwnerName());
			policyBaseDTO.setPlateNo(inquiryDTO.getPlateNo());
			policyBaseDTO.setPolicyBaseId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			policyBaseDTO.setPolicyCrtTm(date);
			policyBaseDTO.setPolicyNo(tciPlyNo);
			policyBaseDTO.setPolicyPrm(TCIPremTax);
			policyBaseDTO.setProductCode("TCI");
			policyBaseDTO.setTax(vehicleTax);
			policyBaseDTO.setUpdatedBy(policyBaseDTO.getCreatedBy());
			policyBaseDTO.setUpdatedDate(date);
			policyBaseDTO.setUserId(userId);
			dataList.add(policyBaseDTO);
		}
		
		logger.info("商业险保单号：" + vicPlyNo);
		if(StringUtils.isNotBlank(vicPlyNo)){
			policyBaseDTO = new PolicyBaseDTO();
			policyBaseDTO.setAgentId(microDTO.getAgency().getAgent_id());
			policyBaseDTO.setTeamId(microDTO.getAgt_team_id());
			policyBaseDTO.setCalStatus("0");//0未结算
			policyBaseDTO.setCreatedBy("system");
			policyBaseDTO.setCreatedDate(date);
			policyBaseDTO.setEngNo(inquiryDTO.getEngNo());
			policyBaseDTO.setFrmNo(inquiryDTO.getFrmNo());
			policyBaseDTO.setInsId(insId);
			policyBaseDTO.setOwnerName(inquiryDTO.getOwnerName());
			policyBaseDTO.setPlateNo(inquiryDTO.getPlateNo());
			policyBaseDTO.setPolicyBaseId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			policyBaseDTO.setPolicyCrtTm(date);
			policyBaseDTO.setPolicyNo(vicPlyNo);
			policyBaseDTO.setPolicyPrm(VCIPremTax);
			policyBaseDTO.setProductCode("VCI");
			policyBaseDTO.setTax(new BigDecimal("0.00"));
			policyBaseDTO.setUpdatedBy(policyBaseDTO.getCreatedBy());
			policyBaseDTO.setUpdatedDate(date);
			policyBaseDTO.setUserId(userId);
			dataList.add(policyBaseDTO);
		}
			
		
		
		return dataList;
	}

	@Override
	@Log("添加费用计算记录")
	public void addPolicyBase(String orderId, String insId) throws BusinessServiceException {
		logger.info("insId="+insId+",orderId="+orderId);
		List<PolicyBaseDTO> dateList = getPolicyBase(orderId, insId);
		if(CollectionUtils.isEmpty(dateList)){
			logger.info("数据为空");
			return;
		}
		for(PolicyBaseDTO policyBaseDTO : dateList){
			insertPolicyBase(policyBaseDTO);
		}
	}


}
