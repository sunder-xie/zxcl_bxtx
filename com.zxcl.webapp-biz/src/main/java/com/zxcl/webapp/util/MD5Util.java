package com.zxcl.webapp.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MD5Util {

    private static final String HEX_CHARS = "0123456789abcdef";
    /** ��־ */
    private static Log logger = LogFactory.getLog(MD5Util.class);
    private MD5Util() {}

   /**
     * ���� MessageDigest MD5
     */
   private static MessageDigest getDigest() {
	   try {
           return MessageDigest.getInstance("MD5");
       } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
       }
    }

    /**
     * MD5���ܣ���������Ϊһ��ʮ������ֽ�
     */
    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * MD5���ܣ���������Ϊһ��ʮ������ֽ�
     * <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(String data) {
    	byte[] bytes = null;
        try {
        	bytes = md5(data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("MD5���ܳ��?",e);
		}
		return bytes;
    }

    /**
     * MD5���ܣ�������һ��32�ַ��ʮ�����ֵ
     */
    public static String md5Hex(byte[] data) {
        return toHexString(md5(data));
    }

    /**
     * MD5���ܣ�������һ��32�ַ��ʮ�����ֵ
     */
    public static String md5Hex(String data) {
        return toHexString(md5(data));
    }
    
    private static String toHexString(byte[] b) {
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
        	stringbuffer.append(HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
        	stringbuffer.append(HEX_CHARS.charAt(b[i] & 0x0F));
        }
        return stringbuffer.toString();
    }
}