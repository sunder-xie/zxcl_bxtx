

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Demo {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String url = "http://localhost:8080/bxtx/openapi/login/regist_and_login.do";
		
		//接口参数，也是待签名数据
		Map<String,String> params = new TreeMap<String, String>();
		params.put("appId", "API201606240001");
		params.put("timestamp", date2str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		params.put("user_phone", "18502863513");
		params.put("user_name", "测试用户名称");
		params.put("user_team_id", "0");
		
		//秘钥
		String appKey = "01234567890123456789012345678901";
		
		String sign = makeSign(params,appKey);
		System.out.println("得到签名" + sign);
		
		//组装POST数据
		String postData = "";
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		boolean first = true;
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String val = e.getValue();

			if (val != null && val.length() != 0) {

				if (first) {
					first = false;
				} else {
					postData += "&";
				}
				postData += key + "=" + URLEncoder.encode(val,"utf-8");
			}
		}
		postData += "&sign=" + sign;
		System.out.println("请求参数:\n" + postData);
		
		System.out.println("完整请求URL:" + url + "?" + postData);
	}
	
	public static String date2str(Date date, String fmtStr) {
		SimpleDateFormat fmt = new SimpleDateFormat(fmtStr);
		return fmt.format(date);
	}
	
	/**
	 * 将传入参数值按字母序排序后,执行MD5哈希,转化为Hex格式,得到签名串
	 * 
	 * @param appId
	 * @param appKey
	 * @param method
	 * @param encrypt
	 * @return 签名串
	 * @throws EncryptException
	 */
	public static String makeSign(Map<String, String> signParams, String appKey) {

		// 利用TreeMap对参数进行排序(字典序)
		TreeMap<String, String> sortedParams = new TreeMap<String, String>();
		sortedParams.putAll(signParams);
		String stringTemp = "";
		Iterator<Entry<String, String>> it = sortedParams.entrySet().iterator();
		boolean first = true;
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String val = e.getValue();

			// sign字段不参与签名, 值为空的字段不参与签名
			// 其余字段,用key1=val1&key2=val2格式拼接起来
			if (!key.equals("sign") && val != null && val.length() != 0) {

				if (first) {
					first = false;
				} else {
					stringTemp += "&";
				}
				stringTemp += key + "=" + val;
			}
		}
		stringTemp += "&appKey=" + appKey;
		System.out.println("待签名:" + stringTemp);
		return md5hash(stringTemp);
	}

	/**
	 * 对源串做MD5哈希,然后转换为16进制,小写字符
	 * 
	 * @param src
	 * @return
	 */
	public static String md5hash(String src) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(src.getBytes("UTF-8"));

			StringBuffer hexstr = new StringBuffer();

			String hex = "";
			for (int i = 0; i < digest.length; i++) {
				hex = Integer.toHexString(digest[i] & 0xFF);
				if (hex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(hex);
			}
			return hexstr.toString().toLowerCase();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
}
