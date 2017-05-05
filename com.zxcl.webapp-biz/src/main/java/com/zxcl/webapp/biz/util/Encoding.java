package com.zxcl.webapp.biz.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

public class Encoding {
	
	private static final int SALT_LENGTH = 8;
	
	private static final String SHA = "SHA-256";
	
    private static final Charset CHARSET = Charset.forName("UTF-8");

	 
	public static String encode(CharSequence rawPassword){
		 try {
	            byte[] result = digest(rawPassword, getSalt());
	            return new String(base64Encode(result));
	        } catch (NoSuchAlgorithmException ex) {
	            throw new RuntimeException("处理密码异常", ex);
	        }
	}
	
	
	  private static byte[] getSalt() throws NoSuchAlgorithmException {
	        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	        byte[] salt = new byte[SALT_LENGTH];
	        sr.nextBytes(salt);
	        return salt;
	    }
	  
	  
	  private static  byte[] digest(CharSequence rawPassword, byte[] salt)
	            throws NoSuchAlgorithmException {
	        MessageDigest sha = MessageDigest.getInstance(SHA);
	        sha.update(concatenate(salt, utf8Encode(rawPassword)));
	        return concatenate(salt, sha.digest());
	    }
	  
	  
	  public  static String base64Encode(byte[] bytes) {
	        return DatatypeConverter.printBase64Binary(bytes);
	    }
	  
	  
	  public  static byte[] utf8Encode(CharSequence string) {
	        try {
	            ByteBuffer bytes = CHARSET.newEncoder().encode(
	                    CharBuffer.wrap(string));
	            byte[] bytesCopy = new byte[bytes.limit()];
	            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());

	            return bytesCopy;
	        } catch (CharacterCodingException e) {
	            throw new IllegalArgumentException("Encoding failed", e);
	        }
	    }
	  
	  
	  private  static byte[] concatenate(byte[]... arrays) {
	        int length = 0;
	        for (byte[] array : arrays) {
	            length += array.length;
	        }
	        byte[] newArray = new byte[length];
	        int destPos = 0;
	        for (byte[] array : arrays) {
	            System.arraycopy(array, 0, newArray, destPos, array.length);
	            destPos += array.length;
	        }
	        return newArray;
	    }
	  
	  
	  /*
	     * (non-Javadoc)
	     * 
	     * @see
	     * org.springframework.security.crypto.password.PasswordEncoder#matches(
	     * java.lang.CharSequence, java.lang.String)
	     */
	
	    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
	        try {
	            byte[] digested = base64Decode(encodedPassword);
	            byte[] salt = subArray(digested, 0, SALT_LENGTH);
	            return matches(digested, digest(rawPassword, salt));
	        } catch (NoSuchAlgorithmException ex) {
	            throw new RuntimeException("比对密码异常", ex);
	        }
	    }
	    
	    
	    public static byte[] base64Decode(String str) {
	        return DatatypeConverter.parseBase64Binary(str);
	    }
	    
	    private static byte[] subArray(byte[] array, int beginIndex, int endIndex) {
	        int length = endIndex - beginIndex;
	        byte[] subarray = new byte[length];
	        System.arraycopy(array, beginIndex, subarray, 0, length);
	        return subarray;
	    }
	    
	    private static boolean matches(byte[] expected, byte[] actual) {
	        if (expected.length != actual.length) {
	            return false;
	        }
	        int result = 0;
	        for (int i = 0; i < expected.length; i++) {
	            result |= expected[i] ^ actual[i];
	        }
	        return result == 0;
	    }
	

}
