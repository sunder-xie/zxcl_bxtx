package com.zxcl.webapp.web.util;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.StatisticsVisitCountService;
import com.zxcl.webapp.dto.StatisticsVisitCountDTO;

public class StatisticsVisitCountFilter implements Filter {

	private static Logger logger = Logger.getLogger(StatisticsVisitCountFilter.class);
	
	private static final String URLS = "BXTX_STATISTICSVISITCOUNTURL_FILTER";
	
	@Autowired
	private StatisticsVisitCountService statisticsVisitCountService;
	
	@Autowired
	private SecurityHolder securityHolder;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Override
	public void destroy() {
		

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		String urlStr = paramConfigService.getValueByKey(URLS);
		if(StringUtils.isNotBlank(urlStr)){
			String[] urls = StringUtils.split(urlStr, ",");
			if(urls != null && urls.length > 0){
				for(String url : urls){
					if(StringUtils.equals(request.getServletPath(), url)){
						StatisticsVisitCountDTO statisticsVisitCountDTO = new StatisticsVisitCountDTO();
						statisticsVisitCountDTO.setUserId(securityHolder.getAuthenticatedUserId());
						statisticsVisitCountDTO.setCrtCde(securityHolder.getAuthenticatedUserId());
						statisticsVisitCountDTO.setUpdCde(securityHolder.getAuthenticatedUserId());
						statisticsVisitCountDTO.setCrtTm(new Date());
						statisticsVisitCountDTO.setUpdTm(new Date());
						String visitUrl = request.getServletPath();
						if(StringUtils.equals(request.getServletPath(), "/activity/getActivityById.do")){
							//这个地址特殊。。要带参数。。因为他是活动，需要记录每个活动的具体访问数值。。
							visitUrl += "?" + request.getQueryString();
						}
						statisticsVisitCountDTO.setVisitUrl(visitUrl); ////如果这个Url超过200个字符。。就。。报错了。。
						
						try {
							statisticsVisitCountService.insertStatisticsVisitCount(statisticsVisitCountDTO);
							
						} catch (Exception e) {
							logger.error("保存访问记录失败"); //这里不打印堆栈。。太多了。知道是这里报错即可.
						}
					}
					
				}
			}
		}
		
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		

	}

}
