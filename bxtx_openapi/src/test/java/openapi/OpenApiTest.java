package openapi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.zxcl.webapp.biz.service.openapi.dto.CvrgDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnBaseDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnReqDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnVhlDTO;
import com.zxcl.webapp.biz.service.openapi.dto.QtnVhlOwnerDTO;
import com.zxcl.webapp.biz.service.openapi.dto.VhlModelDTO;
import com.zxcl.webapp.openapi.util.ApiUtil;

public class OpenApiTest {

	private static Logger logger = Logger.getLogger(OpenApiTest.class);
	static String gatewayUrl = "http://uat.zhixunchelian.cn/com.zxcl.webapp-web/gateway.do";
	static {
		gatewayUrl = "http://localhost/com.zxcl.webapp-web/gateway.do";
		gatewayUrl = "http://www.zhixunchelian.com/gateway.do";
	}
	public static void main(String[] args) throws Exception {
		testVhlQuery();
		testQuote();
	}
	public static void testVhlQuery() throws IOException{
		//车型查询
		String bizContent = "<packet><vehicleName>北京现代BH6440LAY轻型客车</vehicleName></packet>";
		String reqXml = packMsg(bizContent, "API201606240001", "agent.vhlquery", "01234567890123456789012345678901");
		String respXml = ApiUtil.post(gatewayUrl, reqXml);
		System.out.println(respXml);
	}
	public void testRenewalQuery() throws IOException{
		//按车牌查询车辆往年投保信息(有一定命中率)
		String bizContent = "<packet><plateNo>川AFT318</plateNo><cityCode>510100</cityCode></packet>";
		String reqXml = packMsg(bizContent, "API201606240001", "agent.renewal.query.plate", "01234567890123456789012345678901");
		String respXml = ApiUtil.post(gatewayUrl, reqXml);
		System.out.println(respXml);
	}
	
	public static void testQuote() throws IOException{
		String bizContent = makeQtnReq();
		String reqXml = packMsg(bizContent, "API201606240001", "agent.vhlquote", "01234567890123456789012345678901");
		String respXml = ApiUtil.post(gatewayUrl, reqXml);
		System.out.println(respXml);
	}
	
	public static String date2str(Date date, String fmtStr) {
		SimpleDateFormat fmt = new SimpleDateFormat(fmtStr);
		return fmt.format(date);
	}
	
	public static String packMsg(String bizContent, String appId, String method, String appKey) {
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("method", method);
		params.put("timestamp", date2str(new Date(), "yyyyMMddHHmmss"));
		params.put("bizContent", bizContent);
		
		String sign = ApiUtil.makeSign(params,appKey);
		logger.info("签名:" + sign);
		
		String xml = "<xml>";
		
		xml += "<appId>" + appId + "</appId>";
		
		xml += "<method>" + method + "</method>";
		
		xml += "<timestamp>" + params.get("timestamp") + "</timestamp>";
		
		xml += "<sign>" + sign + "</sign>";
		
		xml += "<bizContent><![CDATA[" + bizContent + "]]></bizContent>";
		
		xml += "</xml>";

		logger.info("签名后的报文:");
		
		logger.info(xml);
		
		return xml;
	}
	
	
	
	public static String makeQtnReq(){
		
		QtnReqDTO req = new QtnReqDTO();
		QtnBaseDTO qtnBase = new QtnBaseDTO();
		req.setQtnBase(qtnBase);
		
		qtnBase.setInsCompanyCode("TIANPING_HTTP"); //天平
		qtnBase.setInsUserName("cd_jiac_dl");
		qtnBase.setInsUserPwd("jiacc123");
		
		qtnBase.setInsCompanyCode("PINGAN_HTTP"); //平安
		qtnBase.setInsUserName("JCBDSC-00008");
		qtnBase.setInsUserPwd("N4EDaQ54");
		
		qtnBase.setVciSign("1");
		qtnBase.setTciSign("1");
		qtnBase.setVciStartDate("2016-07-10");
		qtnBase.setVciEndDate("2017-07-09");
		qtnBase.setTciStartDate("2016-07-10");
		qtnBase.setTciEndDate("2017-07-09");
		
		QtnVhlDTO vehicle = new QtnVhlDTO(); 
		req.setVehicle(vehicle);
		
		vehicle.setChgOwnerFlag("0"); //是否过户车
		vehicle.setEcdemicVehicleFlag("0"); //是否外地车
		vehicle.setFirstRegisterDate("2016-04-01");
		vehicle.setEngineNo("FW704180");
		vehicle.setVin("LBELMBKBXFY626187");
		vehicle.setLicensePlateNo("川A5B19A");
		
		vehicle.setEngineNo("FW704181");
		vehicle.setVin("LBELMBKBXFY626188");
		vehicle.setLicensePlateNo("川A5B19B");
		
		vehicle.setTransferDate(null);
		vehicle.setVehicleId("XDABED0039");
		vehicle.setVehicleName("北京现代BH6440LAY轻型客车");
		vehicle.setVehiclePrice("130800");
		
		QtnVhlOwnerDTO vehicleOwner = new QtnVhlOwnerDTO();
		vehicleOwner.setOwnerName("邱悦");
		vehicleOwner.setOwnerCertNo("510123199208190327");
		req.setVehicleOwner(vehicleOwner);
		
		List<CvrgDTO> cvrgList = new ArrayList<CvrgDTO>();
		req.setCvrgList(cvrgList);
		
		CvrgDTO cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030006"); //车损
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030018"); //三者
		cvrg.setSumLimit("500000");
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030001"); //司机
		cvrg.setSumLimit("10000");
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
	
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030009"); //司机
		cvrg.setSumLimit("40000"); //5座车, 4个乘客, 每个乘客10000
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030061"); //盗抢
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030004"); //玻璃
		cvrg.setGlsType("1"); //国产玻璃
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("032601"); //划痕
		cvrg.setSumLimit("2000");
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("030012"); //自燃
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("032608"); //涉水
		cvrg.setNoDduct("1"); //投保不计免赔
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("032618"); //指定修理厂
		cvrgList.add(cvrg);
		
		cvrg = new CvrgDTO();
		cvrg.setCoverageCode("033008"); //指定修理厂
		cvrgList.add(cvrg);
		
		String xml = encode(req);
		System.out.println(xml);
		
		return xml;
	}
	
	public static String encode(Object o){
		XStream xs = new XStream();
		xs.alias("packet", o.getClass());
		xs.alias("vehicleModel", VhlModelDTO.class);
		xs.alias("cvrg", CvrgDTO.class);
		return xs.toXML(o);
	}
}
