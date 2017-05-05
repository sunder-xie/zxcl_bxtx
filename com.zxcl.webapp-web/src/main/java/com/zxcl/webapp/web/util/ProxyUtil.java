package com.zxcl.webapp.web.util;

import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ProxyUtil implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = Logger.getLogger(ProxyUtil.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		try {
			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("proxy.properties");
			Properties p = new Properties();
			p.load(inputStream);
			
			if (StringUtils.isBlank(p.getProperty("socksProxyHost"))) {
				logger.info("已读取到配置文件，但socksProxyHost为空");
				return ;
			}
			if (StringUtils.isBlank(p.getProperty("socksProxyPort"))) {
				logger.info("已读取到配置文件，但socksProxyPort为空");
				return ;
			}
			if (StringUtils.isBlank(p.getProperty("socksProxyUser"))) {
				logger.info("已读取到配置文件，但socksProxyUser为空");
				return ;
			}
			if (StringUtils.isBlank(p.getProperty("socksProxyPasswd"))) {
				logger.info("已读取到配置文件，但socksProxyPasswd为空");
				return ;
			}
			
			System.setProperty("socksProxyHost", p.getProperty("socksProxyHost"));
			System.setProperty("socksProxyPort", p.getProperty("socksProxyPort"));
			Authenticator.setDefault(
					new MyAuthenticator(p.getProperty("socksProxyUser"), 
							p.getProperty("socksProxyPasswd")));
			logger.info("已加载proxy的配置信息.");
		} catch (Exception e) {
			logger.error("未读取到proxy.properties.不使用代理");
		}

		
	}

	static class MyAuthenticator extends Authenticator {
		private String user = "";
		private String password = "";

		public MyAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password.toCharArray());
		}
	}

}
