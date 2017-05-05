package com.zxcl.webapp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zxcl.webapp.biz.exception.SAOException;

public class SendMessage {
	private static Logger	logger	= Logger.getLogger(SendMessage.class);

	public static String post(String url, String xml, String coding) throws IOException, SocketTimeoutException,
			SAOException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream in = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(120 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "text/xml;charset"+coding);

			os = conn.getOutputStream();
			os.write(xml.getBytes(coding));
			os.close();
			if (conn.getConnectTimeout() > 5000) {
				throw new SAOException("连接超时！！");
			}
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return toText(in, coding);
			} else {
				throw new SAOException("HTTP ERROR:" + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
		} catch (Exception e) {
			logger.error("调用接口失败:" + e);
			throw new SAOException("调用接口失败!!");
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
	 * 忽视证书HostName
	 */
	private static HostnameVerifier	ignoreHostnameVerifier			= new HostnameVerifier() {
		public boolean verify(String s, SSLSession sslsession) {
			return true;
		}
	};

	/**
	 * Ignore Certification
	 */
	private static TrustManager		ignoreCertificationTrustManger	= new X509TrustManager() {

		private X509Certificate[] certificates;

		@Override
		public void checkClientTrusted(X509Certificate certificates[], String authType)
				throws CertificateException {
			if (this.certificates == null) {
				this.certificates = certificates;
			}
		}

		@Override
		public void checkServerTrusted(X509Certificate[] ax509certificate, String s)
				throws CertificateException {
			if (this.certificates == null) {
				this.certificates = ax509certificate;
			}

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public static String getMethod(String urlString, String reqTxt, String coding,String sign) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
		try {
			URL url = new URL(urlString);
			System.setProperty("jsse.enableSNIExtension", "false");
			/*
			 * use ignore host name verifier（使用验证器忽略主机名称）
			 */
			// HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Prepare SSL Context（准备SSL上下文）
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);
			connection.setRequestMethod("POST");
			if(StringUtils.isNotBlank(sign)){				
				connection.setRequestProperty("TP-SIGN", sign);
			}
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			try {
				os.write(reqTxt.getBytes(coding));
			} finally {
				os.close();
			}
			InputStream reader = connection.getInputStream();
			byte[] bytes = new byte[1024];
			int length = reader.read(bytes);

			do {
				buffer.write(bytes, 0, length);
				length = reader.read(bytes);
			} while (length > 0);

			reader.close();

			connection.disconnect();
		} catch (Exception ex) {
			logger.error("调用接口失败",ex);
		} finally {
		}
		String repString = "";
		try {
			repString = buffer.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("返回报文转码失败",e);
		}
		return repString;
	}

	public static String postByGET(String urlString, String reqTxt, String coding) {

		String repString = "";
		try {

			URL url = new URL(urlString);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			// Prepare SSL Context（准备SSL上下文）
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, tm, null);
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(60 * 1000);
			//connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			if(null!=reqTxt && !"".equals(reqTxt)){
				OutputStream os = connection.getOutputStream();
				os.write(reqTxt.getBytes("utf-8"));
				os.flush();
				os.close();
			}
			InputStream reader = connection.getInputStream();
			if(reader!=null){
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] receiveBuffer = new byte[2048];
				int readBytesSize = reader.read(receiveBuffer);
				while(readBytesSize != -1){
					bos.write(receiveBuffer, 0, readBytesSize);
					readBytesSize = reader.read(receiveBuffer);
				}
				 repString = new String(bos.toByteArray(), "UTF-8");
			}
			
			connection.disconnect();
		} catch (Exception ex) {
			logger.error("调用接口失败",ex);
		}
		return repString;
	}
}
