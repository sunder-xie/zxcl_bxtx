package com.zxcl.webapp.web.aop;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.web.vo.AjaxResult;

/**
 * 合并重复请求 <br />
 * 当一个新的请求过来时，检测是否存在相同的请求，如果存在，则后面来的请求等待前面的请求返回结果。返回结果时，按当前请求返回所有的线程的结果内容.
 * @author xiaoxi
 *
 */
@Aspect
@Component
public class MergeDuplicateRequestAop {

	private static Logger logger = Logger.getLogger(MergeDuplicateRequestAop.class);
	
	private static final Map<String, AjaxResult> QUOTASMAP = new  ConcurrentHashMap<String, AjaxResult>();
	
	private static final Map<String, Object> RESULT = new  ConcurrentHashMap<String, Object>();
	
	private static final Map<String,Date> TIMEOUT = new ConcurrentHashMap<>();
	
	@Around("execution(* com.zxcl.webapp.web.controller.OrderController.getInsured(..)) || execution(* com.zxcl.webapp.web.controller.OrderController.accounting(..))")
	public AjaxResult getInsured(ProceedingJoinPoint proceedingJoinPoint){
		
		String methodName = "报价";
		if(StringUtils.equals("accounting", proceedingJoinPoint.getSignature().getName())){
			methodName = "投保";
		}
		//第0个参数：userId,第1个参数：询价单号，第二个参数：保险公司ID
		Object[] objects = proceedingJoinPoint.getArgs();
		HttpServletRequest request = (HttpServletRequest) objects[0];
		String[] params = {request.getParameter("inquiryId"),request.getParameter("insId")};
		String timestamp = request.getParameter("timestamp") ;
		if(StringUtils.isBlank(timestamp)){ //如果没有时间戳，不进行合并请求。因为不好判断。正常情况有时间戳的.
			try {
				logger.info("未获取到时间戳数据，不进行合并请求." );
				return (AjaxResult) proceedingJoinPoint.proceed();
			} catch (Throwable e) {
				logger.error("执行"+methodName+"失败", e);
				return new AjaxResult(false, "系统异常");
			}
		}
		
		String key = String.valueOf(params[0]) + String.valueOf(params[1]) + timestamp;
		TIMEOUT.put(key, new Date());
		
		AjaxResult quotaResult = null;
		Object result = new Object();
		if(!RESULT.containsKey(key)){
			logger.info("未检测到重复请求，开始一个新的请求.key:" + key);
			RESULT.put(key, result);
			synchronized (result) {//同一询价单&同一保险公司&同一时间戳 才使用同步锁 
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				try {
					logger.info("未检测到之前有"+methodName+"任务，直接报价...key:" + key);
					quotaResult = (AjaxResult) proceedingJoinPoint.proceed();
					
				} catch (Throwable e) {
					logger.error("aop拦截失败后继续执行后面的代码失败.key:" + key, e);
					quotaResult = new AjaxResult(false, "系统异常");
				}
				
				QUOTASMAP.put(key, quotaResult);
			}
			
		}else{
			result = RESULT.get(key);
			logger.info("检测到重复请求，等待合并数据响应数据.key:" + key);
			synchronized (result) {
				//当这里获取到锁的时候，已经有值了。所以可以直接返回.
				logger.info("已获取到锁.开始返回之前请求获得的数据.key:" + key);
				quotaResult = QUOTASMAP.get(key);
			}
			
			
		}
		

		//最后考虑清理数据的问题..
		for(String k : TIMEOUT.keySet()){
			Date d = TIMEOUT.get(k);
			//判断数据是否超过5分钟。超过就清理.
			if(DateUtils.increaseDate(d, Calendar.MINUTE, 5) .getTime() < new Date().getTime()){
				logger.info("开始清理过期合并请求数据：key:" + k);
				TIMEOUT.remove(k);
				QUOTASMAP.remove(k);
				RESULT.remove(k);
			}
		}
		
		
		return quotaResult;
	}
	
}
