package com.zxcl.webapp.biz.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * 通过ip获取城市信息
 * 
 * @author 5555
 *
 */
public class AddressUtils {

	private static Logger logger = Logger.getLogger(AddressUtils.class);
	
	/**
	 * 请求的网址
	 */
	private static final String URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";
	/**
	 * 编码
	 */
	private static final String ENCODING = "UTF-8";

	/**
	 * unicode 转换成 中文
	 * 
	 * @param theString
	 * @return
	 */
	private static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 
	 * @param ip
	 * 
	 * @return
	 * @throws IOException
	 */
	private static String getResult(String ip) throws IOException {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(URL + ip);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), ENCODING));// 往对端写完数据对端服务器返回数据
			// 以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			throw new IOException("获取定位信息失败");
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
	}

	/**
	 * @param content
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等 *
	 * @return 返回的模板：河南省平顶山市
	 * @throws UnsupportedEncodingException
	 */
	public static String getAddresses(String ip) {
		try {
			String returnStr = getResult(ip);
			if (returnStr != null) {
				// 处理返回的省市区信息
				String[] temp = returnStr.split(",");
				if (temp.length < 3) {
					return "0";// 无效IP
				}
				// String country = "";
				// String area = "";
				String region = "";
				String city = "";
				// String county = "";
				// String isp = "";
				for (int i = 0; i < temp.length; i++) {
					switch (i) {
					// case 1:
					// country = (temp[i].split(":"))[2].replaceAll("\"", "");
					// country = decodeUnicode(country);// 国家
					// break;
					// case 3:
					// area = (temp[i].split(":"))[1].replaceAll("\"", "");
					// area = decodeUnicode(area);// 地区
					// break;
					case 5:
						region = (temp[i].split(":"))[1].replaceAll("\"", "");
						region = decodeUnicode(region);// 省份
						break;
					case 7:
						city = (temp[i].split(":"))[1].replaceAll("\"", "");
						city = decodeUnicode(city);// 市区
						break;
					// case 9:
					// county = (temp[i].split(":"))[1].replaceAll("\"", "");
					// county = decodeUnicode(county);// 地区
					// break;
					// case 11:
					// isp = (temp[i].split(":"))[1].replaceAll("\"", "");
					// isp = decodeUnicode(isp);// ISP公司
					// break;
					}
				}
				return region + city;
			}
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
}