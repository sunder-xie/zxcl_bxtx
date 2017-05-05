package com.zxcl.webapp.web.util;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * I/O 工具类
 * @author wenchang001
 *
 */
public class IoUtil {
	
	// 日志对象
    private static final Log log = LogFactory.getLog(IoUtil.class);
    
	/***
	 * HTTP(S) POST请求
	 * @param httpURL 请求URL 若httpURL以HTTPS 开头,则使用HTTPS协议
	 * @param data 请求体字符串
	 * @param charset 请求/响应数据使用的字符集
	 * @param factory 指定的SSL Socket Factory; 若不指定,则默认使用JDK自带的信任库
	 * @return 返回字符串
	 * @throws IOException
	 */
	public static String post(String httpURL, String data, String charset, SSLSocketFactory factory) throws IOException {
		URL url = null;
		HttpURLConnection conn = null;
		ByteArrayOutputStream byteOut = null;
		BufferedInputStream readInfo = null;
		String returnXml = "";
		OutputStream out = null;
		
		log.info("HTTP POST REQUEST: URL:" + httpURL + "\nDATA:" + data);
		log.info("REQUEST CHARSET:" + charset );
		
		try {
			url = new URL(httpURL);

			conn = (HttpURLConnection) url.openConnection();
			
			if(httpURL != null && ( httpURL.startsWith("https") || httpURL.startsWith("HTTPS")) && factory != null) {
				
				((HttpsURLConnection)conn).setSSLSocketFactory( factory );
				
			}
			
			conn.setRequestProperty("content-type", "text/xml;");
			conn.setRequestMethod("POST");
			conn.setUseCaches(false); // 忽略缓存
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(15000);//15s 超时
			conn.setReadTimeout(100000);
			byteOut = new ByteArrayOutputStream();
			byteOut.write(data.getBytes( charset ));
			byte[] buf = byteOut.toByteArray();
			out = conn.getOutputStream();
			out.write(buf);
			out.flush();
			out.close();
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				readInfo = new java.io.BufferedInputStream(conn.getInputStream());
				
				byte[] readBuffer = new byte[1024];
				int readed = -1;
				
				ByteArrayOutputStream readBytes = new ByteArrayOutputStream();
				while ( (readed = readInfo.read(readBuffer)) != -1) {
					readBytes.write(readBuffer,0,readed);
				}
				returnXml = readBytes.toString(charset);//decode bytes to string using given CHARSET
				
			} else {
				String msg = "ERROR:"+ conn.getResponseCode();
				throw new IOException(msg);
			}
			
		} catch (SocketException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (readInfo != null) {
				readInfo.close();
			}
			if (byteOut != null) {
				byteOut.close();
			}
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return returnXml;
	}
	
	/***
	 * HTTP GET请求
	 * @param httpURL 请求地址
	 * @param charset 响应数据使用的字符集
	 * @param factory 
	 * @return 响应数据字符串
	 * @throws IOException
	 * 
	 */
	public static String get(String httpURL,String charset, SSLSocketFactory factory) throws IOException {
		URL url = null;
		HttpURLConnection conn = null;
		ByteArrayOutputStream byteOut = null;
		BufferedInputStream readInfo = null;
		String returnXml = "";
		OutputStream out = null;
		
		log.info("HTTP GET REQUEST: URL:" + httpURL);
		log.info("REQUEST CHARSET:" + charset );
		
		try {
			url = new URL(httpURL);
			
			conn = (HttpURLConnection) url.openConnection();
			
			if(httpURL != null && ( httpURL.startsWith("https") || httpURL.startsWith("HTTPS")) && factory != null) {
				
				((HttpsURLConnection)conn).setSSLSocketFactory( factory );
				
			}
			
			conn.setRequestProperty("content-type", "text/xml;");
			conn.setRequestMethod("GET");
			conn.setUseCaches(false); // 忽略缓存
			conn.setDoOutput(false);
			conn.setDoInput(true);
			conn.setConnectTimeout(15000);//15s 超时
			conn.setReadTimeout(30000);
			
			conn.connect();
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				readInfo = new java.io.BufferedInputStream(conn.getInputStream());
				
				byte[] readBuffer = new byte[1024];
				int readed = -1;
				
				ByteArrayOutputStream readBytes = new ByteArrayOutputStream();
				while ( (readed = readInfo.read(readBuffer)) != -1) {
					readBytes.write(readBuffer,0,readed);
				}
				returnXml = readBytes.toString(charset);//decode bytes to string using given CHARSET
			} else {
				String msg = "ERROR:"+ conn.getResponseCode();
				throw new IOException(msg);
			}
			
		} catch (SocketException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (readInfo != null) {
				readInfo.close();
			}
			if (byteOut != null) {
				byteOut.close();
			}
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return returnXml;
	}
	
	public static String stream2text(InputStream in , String charset) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int c = 0;
		byte[] buf = new byte[1024];
		while((c = in.read(buf)) != -1) {
			bout.write(buf,0,c);
		}
		return bout.toString(charset);
	}
}
