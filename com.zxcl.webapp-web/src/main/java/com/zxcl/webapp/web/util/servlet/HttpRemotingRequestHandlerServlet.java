package com.zxcl.webapp.web.util.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public class HttpRemotingRequestHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(getClass());
	private HashMap<String, HttpRequestHandler> handlerMap;
	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private boolean isEnableHttpRemoting = true;
	public static final String DISABLE_HTTP_REMOTING_KEY = "DISABLE_JTFA_HTTP_REMOTING";

	public void init() throws ServletException {
		this.handlerMap = new HashMap<String, HttpRequestHandler>();
		detectHandlers();
		detectEnableHttpRemotingFlag();
	}

	private void detectEnableHttpRemotingFlag() {
		String disable = System.getProperty("DISABLE_JTFA_HTTP_REMOTING");
		this.logger.info("JVM参数,是否禁用HTTP远程调用:" + disable);
		if (disable != null) {
			if ("false".equalsIgnoreCase(disable))
				this.isEnableHttpRemoting = true;
			else if ("true".equalsIgnoreCase(disable))
				this.isEnableHttpRemoting = false;
		} else {
			disable = System.getenv("DISABLE_JTFA_HTTP_REMOTING");
			this.logger.info("ENV参数,是否禁用HTTP远程调用:" + disable);
			if ("true".equalsIgnoreCase(disable))
				this.isEnableHttpRemoting = false;
		}
	}

	protected void detectHandlers() throws BeansException {
		this.logger
				.info("Initializing HttpRemoteRequestHandler,start detecting handlers...");

		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		if (this.logger.isDebugEnabled()) {
			this.logger
					.debug("Looking for URL mappings in application context: "
							+ wac);
		}
		String[] beanNames = wac.getBeanNamesForType(HttpRequestHandler.class);

		for (String beanName : beanNames) {
			String[] urls = determineUrlsForHandler(wac, beanName);
			if (!ObjectUtils.isEmpty(urls)) {
				for (String url : urls) {
					registerHandler(url, beanName, wac);
				}
			} else if (this.logger.isDebugEnabled())
				this.logger.debug("Rejected bean name '" + beanName
						+ "': no URL paths identified");
		}
	}

	protected void registerHandler(String urlPath, String handlerName,
			ApplicationContext ctx) throws BeansException,
			IllegalStateException {
		Assert.notNull(urlPath, "URL path must not be null");
		Assert.notNull(handlerName, "Handler must not be null");
		HttpRequestHandler resolvedHandler = null;

		if (ctx.isSingleton(handlerName)) {
			resolvedHandler = (HttpRequestHandler) ctx.getBean(handlerName);
		} else {
			this.logger.warn("can not register httpRequestHanler ["
					+ handlerName + "] because its not singleton.");
			return;
		}

		Object mappedHandler = this.handlerMap.get(urlPath);
		if (mappedHandler != null) {
			if (mappedHandler != resolvedHandler) {
				throw new IllegalStateException("Cannot map " + resolvedHandler
						+ " to URL path [" + urlPath + "]: There is already "
						+ mappedHandler + " mapped.");
			}

		} else {
			if (urlPath.equals("/")) {
				throw new IllegalArgumentException(
						"Cannot set URL path [/] to instance ["
								+ resolvedHandler + "]");
			}
			this.handlerMap.put(urlPath, resolvedHandler);
			if (this.logger.isInfoEnabled())
				this.logger.info("Mapped URL path [" + urlPath + "] onto "
						+ resolvedHandler);
		}
	}

	protected String[] determineUrlsForHandler(ApplicationContext ctx,
			String beanName) {
		List<String> urls = new ArrayList<String>();
		if (beanName.startsWith("/")) {
			urls.add(beanName);
		}
		String[] aliases = ctx.getAliases(beanName);
		for (String alias : aliases) {
			if (alias.startsWith("/")) {
				urls.add(alias);
			}
		}
		return StringUtils.toStringArray(urls);
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if (!this.isEnableHttpRemoting) {
				this.logger
						.error("**Can not handler request ["
								+ request.getRequestURL()
								+ "] as http remoting service dispatcher was disabled!**");
				response.sendError(400,
						"DO NOW ALLOW HTTP REMOTING SERVICE REQUEST");
				return;
			}

			String lookupPath = this.urlPathHelper
					.getLookupPathForRequest(request);
			HttpRequestHandler handler = (HttpRequestHandler) this.handlerMap
					.get(lookupPath);
			if (handler == null) {
				this.logger.error("Cant not find handler for request ["
						+ request.getRequestURL() + "] with lookupPath ["
						+ lookupPath + "]");
				response.sendError(400,
						"Bad Request-Can Not Find Requested Service");
				return;
			}
			handler.handleRequest(request, response);
		} catch (HttpRequestMethodNotSupportedException ex) {
			String[] supportedMethods = ex.getSupportedMethods();
			if (supportedMethods != null) {
				response.setHeader("Allow", StringUtils.arrayToDelimitedString(
						supportedMethods, ", "));
			}
			response.sendError(405, ex.getMessage());
		}
	}
}