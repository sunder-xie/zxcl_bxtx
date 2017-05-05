package com.zxcl.webapp.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class HttpProcess {
	
	private static Logger logger = Logger.getLogger(HttpProcess.class);
	
//	public static void main(String[] args) {
//		String href = "https://api.weixin.qq.com/cgi-bin/token";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("grant_type", "client_credential");
//		params.put("appid", "wx92d392aa1425aaf8");
//		params.put("secret", "42c18337f0bf0fb49b2775e0ad4333d0");
//		byte[] resultBytes = HttpProcess.doGet(href, params, false);
//		String result = null;
//		try {
//			result = new String(resultBytes, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		System.out.println(result);
//	}
	
	public static byte[] doGet(String url, Map<String, String> params,
			boolean type) {
		HttpClient httpClient = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		Random random = new Random();
		headers.add(new Header("User-Agent","Mozilla/4.0 (compatible; MSIE "+(random.nextInt(6) + 6)+"."+(random.nextInt(9))+"; Windows NT "+(random.nextInt(4) + 1)+"."+(random.nextInt(4) + 1)+")"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		StringBuffer queryString = new StringBuffer("");
		if (type)
			url = url + "/";
		else
			url = url + "?";
		String param;
		if (params != null) {
			try {
				for (String key : params.keySet()) {
					param = "";
					if (type)
						param = URLEncoder.encode((String) params.get(key),
								"UTF-8") + "/";
					else {
						param = key
								+ "="
								+ URLEncoder.encode((String) params.get(key),
										"UTF-8") + "&";
					}
					queryString.append(param);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e);
			}

			queryString.delete(queryString.length() - 1, queryString.length());
		}
		logger.info(url + queryString.toString());
		GetMethod getMethod = null;
		try {
			getMethod = new GetMethod(url + queryString.toString());
			int statusCode = httpClient.executeMethod(getMethod);
			logger.info("==========="+statusCode);
			if (statusCode != 200) {
				System.err.println("Method failed:" + getMethod.getStatusLine());
			}

			return getMethod.getResponseBody();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public static byte[] doPost(String url, Map<String, String> params) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter("http.protocol.content-charset",
				"GBK");
		NameValuePair[] nameValuePairs = new NameValuePair[params.size()];
		int index;
		if (params != null) {
			index = 0;
			for (String key : params.keySet()) {
				NameValuePair nameValuePair = new NameValuePair(key,
						(String) params.get(key));

				nameValuePairs[index] = nameValuePair;
				index++;
			}
		}
		try {
			postMethod.setRequestBody(nameValuePairs);

			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != 200) {
				System.err.println("Method failed:"
						+ postMethod.getStatusLine());
			}

			return postMethod.getResponseBody();
		} catch (HttpException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
}