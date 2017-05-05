package com.zxcl.webapp.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log4JLogChute;
import org.dom4j.Node;

public class Common {
	
	public static String buildRequstXML(String templateFilePath, Map<String, Object> paramMap, String charsetName) throws Exception {
        String className = TemplateLoader.class.getName();
		VelocityEngine veEngine = new VelocityEngine(); 
		Properties p = new Properties();
        p.put("input.encoding", "GBK");
        p.put("output.encoding", "GBK");
        p.put("resource.loader", "srl");
        p.put("srl.resource.loader.class", className);
		p.put(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				"org.apache.velocity.runtime.log.Log4JLogChute");
		p.put(Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER_LEVEL, "WARN");
		p.put("runtime.log.logsystem.log4j.logger", Common.class + "");
        veEngine.init(p);	
        Template template = veEngine.getTemplate(templateFilePath);
        
        VelocityContext velocityContext = new VelocityContext();
        
		//参数集合添加到模板
		if(paramMap != null){
			for(Map.Entry<String, Object> entry: paramMap.entrySet()){
				velocityContext.put(entry.getKey(), entry.getValue());
			}
		}
        StringWriter sw = new StringWriter();
        template.merge(velocityContext, sw);
		return sw.toString();
	}
	
	/**
	 * 对象转Map
	 * @param obj
	 * @return
	 */
	public static Map<String,Object> objToMap(Object obj)  throws IntrospectionException,  
    	IllegalAccessException, InvocationTargetException{
		Class type = obj.getClass();  
        Map returnMap = new HashMap();  
        BeanInfo beanInfo = Introspector.getBeanInfo(type);  
  
        PropertyDescriptor[] propertyDescriptors = beanInfo  
                .getPropertyDescriptors();  
        for (int i = 0; i < propertyDescriptors.length; i++) {  
            PropertyDescriptor descriptor = propertyDescriptors[i];  
            String propertyName = descriptor.getName();  
            if (!propertyName.equals("class")) {  
                Method readMethod = descriptor.getReadMethod();  
                Object result = readMethod.invoke(obj, new Object[0]);  
                if (result != null) {  
                    returnMap.put(propertyName, result);  
                } else {  
                    returnMap.put(propertyName, "");  
                }  
            }  
        }  
        return returnMap;  
	}
	
	 /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
    /** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    }
    
    
    /**
     * 判断是否报文字段是否为非空
     * @param node
     * @return
     */
    public static String judgeXml(List<org.dom4j.Node> node){
    	String str = "";
    	if(null != node && node.size() > 0){
    		str = node.get(0).getText();
    	}
    	return str;
    }
    /**
     * 判断是否报文字段是否为非空
     * @param node
     * @return
     */
    public static String judgeXml(Node node){
    	String str = "";
    	if(null != node){
    		str = node.getText();
    	}
    	return str;
    }

    /**
     * 将对象转换成String类型 
     * @param obj
     * @return
     */
    public static String valueOf(Object obj){
    	return (obj == null) ? "" : obj.toString();
    }
}
