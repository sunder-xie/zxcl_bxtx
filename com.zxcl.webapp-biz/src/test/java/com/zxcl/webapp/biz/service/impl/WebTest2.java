package com.zxcl.webapp.biz.service.impl;



import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.um.security.crypto.password.SaltSHAPasswordEncoder;
import com.zxcl.webapp.biz.service.CommonCarInsurance;
import com.zxcl.webapp.biz.service.MessageRmiService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.util.Encoding;
import com.zxcl.webapp.dto.wallet.WalletBankDTO;
import com.zxcl.webapp.dto.wxmsg.SendGroupContentMsgDTO;
import com.zxcl.webapp.dto.wxmsg.SendGroupMsgDTO;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgDTO;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgItemDTO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.util.HttpOrsClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:app-biz-cxf-local.xml","classpath:app-biz-context.xml" })
public class WebTest2 {
	
	@Autowired
	private MessageRmiService messageRmiService;
	
	@Autowired
	private CommonCarInsurance commonCarInsurance;
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void test3() throws Exception {
//		messageRmiService.selectMessageTargetByPage("system", 1, 30);
		List<String> userIds = new ArrayList<String>();
		userIds.add("xiaoming");
		userIds.add("zhaojin");
		userIds.add("mou");
		System.out.println(userDAO.queryUserByUserIds(userIds).toString());;
	}
	
	public static void main(String[] args) {
		
		SaltSHAPasswordEncoder a = new SaltSHAPasswordEncoder();
		System.out.println(a.matches("admin123","19fIoyaIr7utuqio6kUp8jdrpFZm8SWR0sw2cQ5deTlvzL1quIvCNg=="));
		System.out.println(a.matches("123123","19fIoyaIr7utuqio6kUp8jdrpFZm8SWR0sw2cQ5deTlvzL1quIvCNg=="));
		
		System.out.println(Encoding.matches("admin123","19fIoyaIr7utuqio6kUp8jdrpFZm8SWR0sw2cQ5deTlvzL1quIvCNg=="));
		System.out.println(Encoding.matches("123123","19fIoyaIr7utuqio6kUp8jdrpFZm8SWR0sw2cQ5deTlvzL1quIvCNg=="));
	}
	
	@Test
	public void test1() throws Exception {
//		commonCarInsurance.insureSucc("TPBX", "20160317021644909", "20160317021644909");
	}
	@Test
	public void test2() throws Exception {
		WalletBankDTO walletBankDTO = new WalletBankDTO();
		walletBankDTO.setBankName("招商银行");
		walletBankDTO.setBankNo("308");
		walletBankDTO.setCardNo("33333333333333333");
		walletBankDTO.setCardOwner("水电费");
		walletBankDTO.setCardType("1");
		walletBankDTO.setCrtCde("xiaoming");
		walletBankDTO.setCrtTm(new Date());
		walletBankDTO.setId("sdfa");
		walletBankDTO.setUpdCde("xiaoming");
		walletBankDTO.setUpdTm(new Date());
		walletBankDTO.setUserId("xiaoming");
//		walletBankService.insertWalletBank(walletBankDTO);
	}
	
	
	
	public static void main3(String args[]) throws Exception {
//		String str[] = new String[3];
//		str[0] = "opQP7vssBgvPQUYBt0AK0_iWGezw";
//		str[1] = "opQP7vs2Zg80IajhLdUobqmrFznc";
//		str[2] = "opQP7vs2Zg80IajhLdUobsadfqmrFznb";
//		String content = "asdgasdgasdgadgasdgadsgasdgadg";
		//{"errcode":0,"errmsg":"ok","template_id":"6eL_uHWylLGig5dKD3Zg5bWmO4fIxrpDsMsngTWTFs0"}
		
		List<String> openIdList = new ArrayList<String>();
		openIdList.add("opQP7vssBgvPQUYBt0AK0_iWGezw");
		openIdList.add("opQP7vssBgvPQUYBt0AK0_iWGeza");
		
		HttpResponse response = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		String result = null;
		StringEntity stringEntity = null;
		httpClient = HttpOrsClient.httpClientProvide();
		httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="
				+ "Eytun7JaMXEQ8Oiq3ELeU9RHe97m4M5viz3kFqYtXimJHa_DWl_vRBB3UjemJF7XryDoKm8Dl2QvRzJncdPRSpqG8yEUXrqWCZfUyyrqUBgfHtKRIvQhv1c8mhYq5_OkYVIaACAURE");
		
		SendGroupMsgDTO sendGroupMsgDTO = new SendGroupMsgDTO();
		sendGroupMsgDTO.setTouser(openIdList);
		sendGroupMsgDTO.setText(new SendGroupContentMsgDTO("sdfadfasdf"));
		
		
		stringEntity = new StringEntity(JSONObject.toJSONString(sendGroupMsgDTO), "UTF-8");
		httpPost.setEntity(stringEntity);
		response = httpClient.execute(httpPost);
		result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(result);
		JSONObject jsonObj = JSONObject.parseObject(result);
		if(!"0".equals(jsonObj.get("errcode").toString())){
			return;
		}
	}
	public static void main2(String args[]) throws Exception {
//		String str[] = new String[3];
//		str[0] = "opQP7vssBgvPQUYBt0AK0_iWGezw";
//		str[1] = "opQP7vs2Zg80IajhLdUobqmrFznc";
//		str[2] = "opQP7vs2Zg80IajhLdUobsadfqmrFznb";
//		String content = "asdgasdgasdgadgasdgadsgasdgadg";
		//{"errcode":0,"errmsg":"ok","template_id":"6eL_uHWylLGig5dKD3Zg5bWmO4fIxrpDsMsngTWTFs0"}
		
		
		HttpResponse response = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		StringEntity stringEntity = null;
		httpClient = HttpOrsClient.httpClientProvide();
//		httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="
//				+ "bRK48ORwVHS0uCdW4316xLGulQbyHzFLdoUkFqRwP3SnKsilN-MgkTWdhZm-ADUU1YqFlOdTYWurrjlc-aRvTpDWtCjq24wZvH02ZSU7hUXVAxZsCbtvemKwuDbfJ4rQVATlCIARWP");
//		stringEntity = new StringEntity("{\"template_id_short\":\"OPENTM217115997\"}", "UTF-8");
//		httpPost.setEntity(stringEntity);
//		System.out.println(URLDecoder.decode(EntityUtils.toString(httpPost.getEntity()), "UTF-8"));
//		response = httpClient.execute(httpPost);
//		result = EntityUtils.toString(response.getEntity(), "UTF-8");
//		System.out.println(result);
//		JSONObject jsonObj = JSONObject.parseObject(result);
//		if(!"0".equals(jsonObj.get("errcode").toString())){
//			return;
//		}
//		tempId = jsonObj.get("template_id").toString();
		
		
		httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ "RqQgqjcgUpNOOvca0VjSH3o-y634DW8W3Y2zAy0sUAyKAEeh3z3CJkDaM4ieY6eob_uSbipCwbEX71I57ceHrPEUOi7IS1RCd_ofBJYZeIcwkVXkWd-VKs3MWFUWzleWHCHgAJAQLQ");
		SendTempMsgDTO SendTempMsgDTO = new SendTempMsgDTO();
		SendTempMsgDTO.setTemplate_id("_xJDI_wue8oeqkI9-lYEetSMapZi2xb3gpSIj1kZa9c");
		SendTempMsgDTO.setTouser("opQP7vssBgvPQUYBt0AK0_iWGezw");
		SendTempMsgDTO.setUrl("");
		Map<String, SendTempMsgItemDTO> data = new HashMap<String, SendTempMsgItemDTO>();
		data.put("first", new SendTempMsgItemDTO("保行天下","#173177"));
		data.put("keyword1", new SendTempMsgItemDTO("SM2016000009230", "#173177"));
		data.put("keyword2", new SendTempMsgItemDTO("80000", "#173177"));
		data.put("remark", new SendTempMsgItemDTO("感谢你的关注", "#173177"));
		SendTempMsgDTO.setData(data);
		stringEntity = new StringEntity(JSONObject.toJSONString(SendTempMsgDTO), "UTF-8");
		httpPost.setEntity(stringEntity);
		System.out.println(URLDecoder.decode(EntityUtils.toString(httpPost.getEntity()), "UTF-8"));
		response = httpClient.execute(httpPost);
		System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
}
