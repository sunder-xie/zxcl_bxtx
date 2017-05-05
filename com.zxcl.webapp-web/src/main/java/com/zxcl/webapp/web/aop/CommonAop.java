package com.zxcl.webapp.web.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.util.Log;

/**
 * aop
 * @author zxj
 *
 */
@Aspect
@Component
public class CommonAop extends BaseController{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private void commonOpetate(JoinPoint joinPoint){
		String userId = null;
		try {
			userId = this.getAuthenticatedUserId();
		} catch (Exception e) {
			userId = "系统";		}
		String className = joinPoint.getTarget().getClass().toString();
	    String methodName = joinPoint.getSignature().getName();
	    Class<? extends Object> cls = joinPoint.getTarget().getClass();
	    String logValue = "";
	    Log log = (Log)cls.getAnnotation(Log.class);
	    Method[] methods = cls.getMethods();
        for (Method m : methods){
          if (m.getName().equals(methodName)) {
            log = (Log)m.getAnnotation(Log.class);
            if (null == log){
            	break;
            }
            logValue = log.value(); 
            break;
          }
        }
    	logger.info("【当前操作用户："+userId+(StringUtils.isNotBlank(logValue)?("  "+logValue) : "")+"】["+className+"."+methodName+"]");
	}
	
	@Before("execution(* com.zxcl.webapp.biz..*.*(..))")
	public void Aop1(JoinPoint joinPoint) {
		commonOpetate(joinPoint);
	}
	
	@Before("execution(* com.zxcl.webapp.web.controller..*.*(..))")
	public void Aop2(JoinPoint joinPoint) {
		commonOpetate(joinPoint);
	}
	
	@Before("execution(* com.zxcl.webapp.integration.sao..*.*(..))")
	public void Aop3(JoinPoint joinPoint) {
		commonOpetate(joinPoint);
	}
	
	
//	@Before("bean(*Controller)")
//	public void controllerAop(JoinPoint joinPoint) {
//		commonOpetate(joinPoint);
//	}
//	
//	@Before("bean(*ActionImpl)")
//	public void actionImplAop(JoinPoint joinPoint) {
//		commonOpetate(joinPoint);
//	}
//	
//	@Before("bean(*ServiceImpl)")
//	public void serviceImplAop(JoinPoint joinPoint){
//		commonOpetate(joinPoint);
//	}
//	@Before("bean(*SAOImpl)")
//	public void saoImplAop(JoinPoint joinPoint){
//		commonOpetate(joinPoint);
//	}
}