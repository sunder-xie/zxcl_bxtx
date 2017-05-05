package com.zxcl.webapp.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @ClassName: 
 * @Description:http/https client
 * @author zxj
 * @date 
 */
public final class HttpOrsClient{
    public static HttpClient httpClientProvide() throws Exception{
    		X509TrustManager tm = new X509TrustManager() {
 				@Override
 				public void checkClientTrusted(X509Certificate[] chain,
 						String authType) throws CertificateException {
 					
 				}
 				@Override
 				public void checkServerTrusted(X509Certificate[] chain,
 						String authType) throws CertificateException {
 					
 				}
 				@Override
 				public X509Certificate[] getAcceptedIssuers() {
 					return new X509Certificate[0];
 				}
	        };
 			SSLContext sslContext  = SSLContext.getInstance("TLS");
 			
 			sslContext.init(null, new TrustManager[]{tm}, new SecureRandom());

 			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
 				        sslContext,
 				        null, 
 				        null,
 				        NoopHostnameVerifier.INSTANCE);
 			
 			ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
 			
 			Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
 			        .register("http", plainsf)
 			        .register("https", sslsf)
 			        .build();
 			
 			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
 			
 			return HttpClients.custom().setConnectionManager(cm).build();
    }
}