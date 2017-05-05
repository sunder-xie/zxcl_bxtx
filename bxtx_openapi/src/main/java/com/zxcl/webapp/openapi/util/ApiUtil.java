package com.zxcl.webapp.openapi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.zxcl.webapp.biz.exception.SAOException;

public class ApiUtil {

	private static Logger logger = Logger.getLogger(ApiUtil.class);
	

	/**
	 * 发起HTTP POST请求
	 * 
	 * @param url
	 * @param xml
	 * @throws IOException
	 * @throws SAOException 
	 */
	public static String post(String url, String xml) throws IOException, SocketTimeoutException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream in = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(60 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			os = conn.getOutputStream();
			os.write(xml.getBytes("utf-8"));
			os.close();

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return toText(in, "utf-8");
			} else {
				throw new RuntimeException("HTTP ERROR:" + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (Exception e) {
			}
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
			}
			if (conn != null)
				conn.disconnect();
		}
	}

	/**
	 * 输入流按照指定字符集转换为文本
	 */
	public static String toText(InputStream in, String charset) throws IOException {
		byte[] buf = new byte[1024];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int c = -1;
		while ((c = in.read(buf)) != -1) {
			bout.write(buf, 0, c);
		}
		String s = bout.toString(charset);
		bout.close();
		return s;
	}

	/**
	 * 读取XML的所有一级节点(报文结构是只有一级节点),将标签名和值写入Map结构返回
	 * 
	 * @param xml
	 * @return
	 * @throws MsgException
	 */
	public static Map<String, String> parseXmlFields(String xml) {
		try {
			StringReader sr = new StringReader(xml);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Map<String, String> p = new HashMap<String, String>();
			Element root = document.getDocumentElement();
			NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
					p.put(node.getNodeName(), node.getTextContent());
			}
			return p;
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e.getMessage(), e);
		}
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
			if (!key.equals("sign") &&StringUtils.isNotBlank(val)) {

				if (first) {
					first = false;
				} else {
					stringTemp += "&";
				}
				stringTemp += key + "=" + val;
			}
		}
		stringTemp += "&appKey=" + appKey;
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

	public static Document strToXMLDocument(String xmlStr) throws ParserConfigurationException, SAXException, IOException {
		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		return builder.parse(is);
	}

}
