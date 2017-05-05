package com.zxcl.plate_query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxcl.pingan_http.api.PinganHttpService;
import com.zxcl.pingan_http.api.dto.ApplyInfoDTO;
import com.zxcl.pingan_http.api.dto.ClientUser;
import com.zxcl.pingan_http.api.dto.CvrgDTO;
import com.zxcl.pingan_http.api.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.util.CommonUtil;

import bxtx.http.biz.service.CoreService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context-test.xml"})
public class PlateQueryVechicleTest {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void plateQuery(){
		String[] plateNos = new String[]{"渝A506W0","渝AQV123","渝AEP699","渝AHB321", "渝A62Z05","渝BHJ176","渝DTE379","渝A00S07"};
		for(String plateNo : plateNos){
			System.out.println("处理车牌号:"+plateNo);
			String result = this.doPlateQuery(plateNo);
			if(StringUtils.isBlank(result) || !result.startsWith("{")){
				continue;
			}
			System.out.println("最终查询结果:"+result);
			JSONObject jobj = JSONObject.parseObject(result);
			StringBuffer buff = new StringBuffer();
			buff.append("车牌:").append(jobj.getString("plateNo"));
			buff.append(",交强止期:").append(null!=jobj.getString("tciInsureEnd")?jobj.getString("tciInsureEnd").replace(".0", ""):"");
			buff.append(",商业止期:").append(null!=jobj.getString("vciInsureEnd")?jobj.getString("vciInsureEnd").replace(".0", ""):"");
			String r = buff.toString();
			System.out.println(r);
			try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(                        
                    new FileOutputStream(new File("C:\\Users\\lenovo\\Desktop\\test2.txt"), true))); 
					){
				out.append("\n");
				out.append(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String fullDate(String resultRmi, String quotaResultJson){
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
			System.out.println("startDate:"+startDate);
			System.out.println("endDate:"+endDate);
			if(StringUtils.isNotBlank(endDate)){
				JSONObject vhlObj = JSONObject.parseObject(resultRmi);
				vhlObj.put("vciInsureEnd", endDate);
				vhlObj.put("tciInsureEnd", endDate);
				return vhlObj.toJSONString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultRmi;
	}
	
	private String quotaFromPinganHttp(String resultRmi){
		try {
			System.out.println("平安报价开始");
			HttpInvokerProxyFactoryBean f = new HttpInvokerProxyFactoryBean();
			f.setServiceInterface(PinganHttpService.class);
			f.setServiceUrl("http://www.zhixunchelian.com/pingan_http/remoting/PinganHttpQuoteService");
			f.afterPropertiesSet();
			
			PinganHttpService pinganHttpService = (PinganHttpService)f.getObject();
			
			QuoteBackMessageDTO result = new QuoteBackMessageDTO();
			result = pinganHttpService.quote(this.buildApplyInfoDTO(resultRmi, this.buildConfigMap()));
			String quotaResultJson = JSON.toJSONString(result);
			System.out.println("报价结果:"+quotaResultJson);
			return fullDate(resultRmi, quotaResultJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultRmi;
	}
	
	private String configId = "HTTP_PINGAN_JIACHENG_CHENGXI";
	
	private Map<String, String> buildConfigMap(){
//		HTTP_PINGAN_JIACHENG_CHENGXI
		Map<String, String> configMap = new HashMap<>();
		configMap.put("PINGANHTTP_USERNAME", "DYJCBDCXWD-00002");
		configMap.put("PINGANHTTP_PASSOWRD", "S9A7Gc8f");
		configMap.put("PINGANHTTP_PARTNERWORKNETCODE", "226931204054");
		configMap.put("PINGANHTTP_TAX3_NO", "250111");
		configMap.put("PINGANHTTP_TAX3_ORG", "四川税务局");
		return configMap;
		
//		HashMap<String, String> resultMap = jdbcTemplate.query("SELECT CONFIG_KEY, CONFIG_VALUE FROM t_ins_company_config WHERE CONFIG_ID=? and CONFIG_STATUS='1'", new String[]{configId},  new ResultSetExtractor<HashMap<String, String>>(){
//
//			@Override
//			public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				HashMap<String, String> configMap = new HashMap<>();
//				while(rs.next()){
//					configMap.put(rs.getString("CONFIG_KEY"), rs.getString("CONFIG_VALUE"));
//				}
//				return configMap;
//			}
//			
//		});
//		System.out.println("configMap:"+JSON.toJSONString(resultMap));
//		return resultMap;
	}
	
	private ApplyInfoDTO buildApplyInfoDTO(String vhlJson, Map<String, String> configMap){
		ApplyInfoDTO apply = new ApplyInfoDTO();
		JSONObject vhlObj = JSONObject.parseObject(vhlJson);
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
		apply.setOwnerCertNo(vhlObj.getString("certfCde"));
		apply.setOwnerName(vhlObj.getString("ownerNme"));
		apply.setVhlModelName("别克SGM7160MTB轿车"); //车型名称
		apply.setVhlModelPriceNoTax("113900.00");//车价(不含税)
		apply.setPlateNo(vhlObj.getString("plateNo"));
		apply.setEngineNo(vhlObj.getString("engNo"));
		apply.setFirstRegDate(vhlObj.getString("fstRegYm"));
		apply.setFrameNo(vhlObj.getString("frmNo"));
		apply.setChangeOwnerFlag("1");//是否过户1/0
		apply.setTransferDate("2015-12-12");//过户日期
		
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
		
		
		return apply;
	}
	
	private String queryAreaCodeByPlateNo(final String plateNo){
		String areaCode = jdbcTemplate.query("select area_code from t_area where area_shortname=? order by area_id desc limit 1", new String[]{plateNo.substring(0, 2)},  new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()){
					return rs.getString(1);
				}
				return null;
			}
		});
		System.out.println("areaCode:"+areaCode);
		return areaCode;
	}
	
	private String queryVhlRmi(String plateNo, String areaCode) throws Exception{
		System.out.println("远程查询:"+plateNo);
		HttpInvokerProxyFactoryBean f = new HttpInvokerProxyFactoryBean();
		f.setServiceInterface(CoreService.class);
		f.setServiceUrl("http://www.zhixunchelian.com/http_plate_query_master/remoting/voteSerchServiceIntf");
		f.afterPropertiesSet();
		
		CoreService s = (CoreService)f.getObject();

		Map<String,String> m = new HashMap<String, String>();
		m.put("areaCode", areaCode);
		m.put("plateNo", plateNo);
		m.put("ownerName", "");
		m.put("caller", "test");
		ObjectMapper jsonMapper = new ObjectMapper();
		String r = s.queryVhlAndRenewalInfo(jsonMapper.writeValueAsString(m));
		
		System.out.println("远程查询结果:"+r);
		return r;
	}
	
	protected String doPlateQuery(String plateNo){
		if(StringUtils.isBlank(plateNo) || plateNo.contains("*")){
			return null;
		}
		try {
			
			//匹配地区编码
			String areaCode = this.queryAreaCodeByPlateNo(plateNo);
			if(StringUtils.isBlank(areaCode)){
				return null;
			}
			
			//远程查询
			String resultRmi = this.queryVhlRmi(plateNo, areaCode);
			if(StringUtils.isBlank(resultRmi) || !resultRmi.startsWith("{")){
				return null;
			}
			
			//保险期限不能为空
			JSONObject jsonObject = JSONObject.parseObject(resultRmi);
			String tciInsureEnd = jsonObject.getString("tciInsureEnd");
			String vciInsureEnd = jsonObject.getString("vciInsureEnd");
			if(StringUtils.isNotBlank(tciInsureEnd) && StringUtils.isNotBlank(vciInsureEnd)){
				return resultRmi;
			}
			
			//平安平台获取保险期限
			return this.quotaFromPinganHttp(resultRmi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		main1(args);
	}
	public static void main1(String[] args) {
		String quotaResultJson = "{\"qtnBase\":{\"errorCode\":\"FAIL\",\"errorMsg\":\"保险公司返回错误:警告：车牌号“川AEL125”的保单发生重复投保，与其重复投保的其它公司的保单信息如下：投保确认码 02PICC510016001453099711151238;保单号 PDZA201651940000004560;起保日期 2016-01-19 00;终保日期 2017-01-19 00;车牌号 川A39QY3;号牌种类 02;车架号 LSGSJ82N73Y021869;发动机号 L0130922172。\",\"userName\":\"DYJCBDCXWD-00002\"}}";
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
		System.out.println("startDate:"+startDate);
		System.out.println("endDate:"+endDate);
	}
//	public static void main2(String[] args) {
//		jxl.Workbook readwb = null;
//
//		try {
//
//			// 构建Workbook对象, 只读Workbook对象
//			// 直接从本地文件创建Workbook
//			InputStream instream = new FileInputStream("C:\\Users\\lenovo\\Documents\\Tencent Files\\1922833965\\FileRecv\\重庆取保险期限.xls");
//			readwb = Workbook.getWorkbook(instream);
//			
//			// Sheet的下标是从0开始
//			// 获取第一张Sheet表
//			Sheet readsheet = readwb.getSheet(0);
//
//			// 获取Sheet表中所包含的总列数
//			int rsColumns = readsheet.getColumns();
//
//			// 获取Sheet表中所包含的总行数
//			int rsRows = readsheet.getRows();
//
//			// 获取指定单元格的对象引用
//			for (int i = 0; i < rsRows; i++) {
//				for (int j = 0; j < rsColumns; j++) {
//					Cell cell = readsheet.getCell(j, i);
//					System.out.print(cell.getContents() + " ");
//				}
//				System.out.println();
//			}
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//		} finally {
//
//			readwb.close();
//
//		}
//	}
}
