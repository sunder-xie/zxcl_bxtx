package com.zxcl.plate_query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bxtx.tp.intf.biz.action.TPIntfAction;
import com.bxtx.tp.intf.dto.other.PolicyDTO;

public class TaipingParsePolicyNoTest {
	
	public static void main2(String[] args) {
		try (FileInputStream fis = new FileInputStream(new File("C:\\Users\\lenovo\\Desktop\\html.html"));
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);){
			StringBuffer buff = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null){
				buff.append(line);
			}
			String html = buff.toString();
			List<HashMap<String, String>> list = new ArrayList<>();
			Document doc = Jsoup.parse(html);
			Elements trs = doc.getElementById("ContentPlaceHolder1_Panel1").getElementsByTag("table").get(0).getElementsByTag("tr");
			for(int i = 0; i < trs.size(); i++){
				if(i <= 1){
					continue;
				}
				HashMap<String, String> map = new HashMap<>();
				Elements tds = trs.get(i).getElementsByTag("td");
				for(int j = 0; j < tds.size(); j++){
					if(j == 0){
						map.put("违法时间", tds.get(j).text());
					}
					if(j == 1){
						map.put("违法地点", tds.get(j).text());
					}
					if(j == 2){
						map.put("违法行为", tds.get(j).text());
					}
					if(j == 3){
						map.put("处理状态", tds.get(j).text());
					}
					if(j == 4){
						map.put("交款状态", tds.get(j).text());
					}
				}
				list.add(map);
			}
			System.out.println("list:"+JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			HttpInvokerProxyFactoryBean f = new HttpInvokerProxyFactoryBean();
			f.setServiceInterface(TPIntfAction.class);
//			f.setServiceUrl("http://192.168.8.13:8080/taiping_http/remoting/TPIntfAction");
			f.setServiceUrl("http://uat.zhixunchelian.cn/taiping_http/remoting/TPIntfAction");
			f.afterPropertiesSet();
			TPIntfAction service = (TPIntfAction)f.getObject();
			
			
			HashMap<String, String> hashMap = new HashMap<String ,String >();
			hashMap.put("companyCode", "taiping");
//			hashMap.put("userName", "506479");
//			hashMap.put("password", "Tp123456");
			hashMap.put("userName", "501041");
			hashMap.put("password", "Taiping123");
			hashMap.put("macAddress", "34-97-F6-33-AF-93");
//			hashMap.put("appPolicyNo", "05001135920160153572");
			hashMap.put("appPolicyNo", "ETC2017010448298");
			String result = service.parsePolicyByNo(hashMap);
			System.out.println(result);
			List<PolicyDTO> policyDTOList = JSONArray.parseArray(result, PolicyDTO.class);
			System.out.println(JSON.toJSONString(policyDTOList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
