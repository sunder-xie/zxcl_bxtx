//package com.zxcl.webapp.web.util;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import net.sf.jmimemagic.Magic;
//import net.sf.jmimemagic.MagicMatch;
//
///**
// * @author zxj
// * @date 2016年8月22日
// * @description 
// */
//public class ProxyUploadUtil {
//	public static void main(String[] args) throws FileNotFoundException {
//		main2(args);
//	}
//	public static void main1(String[] args) {  
//        String filepath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\yaoqing.png";  
//        String urlStr = "http://uat.zhixunchelian.cn/file_server/file/upload.do";  
//        Map<String, String> textMap = new HashMap<String, String>();  
//        textMap.put("sysId", "bxtx");  
//        Map<String, String> fileMap = new HashMap<String, String>();  
//        fileMap.put("Filedata", filepath);  
//        String ret = formUpload(urlStr, textMap, fileMap);  
//        System.out.println(ret);  
//    }  
//	public static void main2(String[] args) throws FileNotFoundException {
//		String filepath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\yaoqing.png";  
//        String urlStr = "http://uat.zhixunchelian.cn/file_server/file/upload.do";  
//        Map<String, String> textMap = new HashMap<String, String>();  
//        textMap.put("sysId", "bxtx");  
//        String ret = formUpload(urlStr, textMap, new FileInputStream(new File(filepath)));  
//        System.out.println(ret);  
//	}
//  
//    /** 
//     * 上传图片 
//     * @param urlStr 
//     * @param textMap 
//     * @param fileMap 
//     * @return 
//     */  
//	public static String formUpload(String urlStr, Map<String, String> textMap, InputStream fis) {  
//        String res = "";  
//        HttpURLConnection conn = null;  
//        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
//        try {  
//            URL url = new URL(urlStr);  
//            conn = (HttpURLConnection) url.openConnection();  
//            conn.setConnectTimeout(5000);  
//            conn.setReadTimeout(30000);  
//            conn.setDoOutput(true);  
//            conn.setDoInput(true);  
//            conn.setUseCaches(false);  
//            conn.setRequestMethod("POST");  
//            conn.setRequestProperty("Connection", "Keep-Alive");  
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
//            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
//  
//            OutputStream out = new DataOutputStream(conn.getOutputStream());  
//            // text    
//            if (textMap != null) {  
//                StringBuffer strBuf = new StringBuffer();  
//                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
//                while (iter.hasNext()) {  
//                    Map.Entry<String, String> entry = iter.next();  
//                    String inputName = (String) entry.getKey();  
//                    String inputValue = (String) entry.getValue();  
//                    if (inputValue == null) {  
//                        continue;  
//                    }  
//                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
//                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
//                    strBuf.append(inputValue);  
//                }  
//                out.write(strBuf.toString().getBytes());  
//            }  
//  
//            // file    
//            if (fis != null) {  
//                    StringBuffer strBuf = new StringBuffer();  
//                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
//                    strBuf.append("Content-Disposition: form-data; name=\"" + "Filedata" + "\"; filename=\"" + "filename.jpg" + "\"\r\n");  
//                    strBuf.append("Content-Type:" + "image/jpg" + "\r\n\r\n");  
//  
//                    out.write(strBuf.toString().getBytes());  
//  
//                    DataInputStream in = new DataInputStream(fis);  
//                    int bytes = 0;  
//                    byte[] bufferOut = new byte[1024];  
//                    while ((bytes = in.read(bufferOut)) != -1) {  
//                        out.write(bufferOut, 0, bytes);  
//                    }  
//                    in.close();  
//            }  
//  
//            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
//            out.write(endData);  
//            out.flush();  
//            out.close();  
//  
//            // 读取返回数据    
//            StringBuffer strBuf = new StringBuffer();  
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
//            String line = null;  
//            while ((line = reader.readLine()) != null) {  
//                strBuf.append(line);  
//            }  
//            res = strBuf.toString();  
//            reader.close();  
//            reader = null;  
//        } catch (Exception e) {  
//            System.out.println("发送POST请求出错。" + urlStr);  
//            e.printStackTrace();  
//        } finally {  
//            if (conn != null) {  
//                conn.disconnect();  
//                conn = null;  
//            }  
//        }  
//        return res;  
//    }  
//    public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {  
//        String res = "";  
//        HttpURLConnection conn = null;  
//        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
//        try {  
//            URL url = new URL(urlStr);  
//            conn = (HttpURLConnection) url.openConnection();  
//            conn.setConnectTimeout(5000);  
//            conn.setReadTimeout(30000);  
//            conn.setDoOutput(true);  
//            conn.setDoInput(true);  
//            conn.setUseCaches(false);  
//            conn.setRequestMethod("POST");  
//            conn.setRequestProperty("Connection", "Keep-Alive");  
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
//            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
//  
//            OutputStream out = new DataOutputStream(conn.getOutputStream());  
//            // text    
//            if (textMap != null) {  
//                StringBuffer strBuf = new StringBuffer();  
//                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
//                while (iter.hasNext()) {  
//                    Map.Entry<String, String> entry = iter.next();  
//                    String inputName = (String) entry.getKey();  
//                    String inputValue = (String) entry.getValue();  
//                    if (inputValue == null) {  
//                        continue;  
//                    }  
//                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
//                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
//                    strBuf.append(inputValue);  
//                }  
//                out.write(strBuf.toString().getBytes());  
//            }  
//  
//            // file    
//            if (fileMap != null) {  
//                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
//                while (iter.hasNext()) {  
//                    Map.Entry<String, String> entry = iter.next();  
//                    String inputName = (String) entry.getKey();  
//                    String inputValue = (String) entry.getValue();  
//                    if (inputValue == null) {  
//                        continue;  
//                    }  
//                    File file = new File(inputValue);  
//                    String filename = file.getName();  
//                    MagicMatch match = Magic.getMagicMatch(file, false, true);  
//                    String contentType = match.getMimeType();  
//  
//                    StringBuffer strBuf = new StringBuffer();  
//                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
//                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
//                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
//  
//                    out.write(strBuf.toString().getBytes());  
//  
//                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
//                    int bytes = 0;  
//                    byte[] bufferOut = new byte[1024];  
//                    while ((bytes = in.read(bufferOut)) != -1) {  
//                        out.write(bufferOut, 0, bytes);  
//                    }  
//                    in.close();  
//                }  
//            }  
//  
//            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
//            out.write(endData);  
//            out.flush();  
//            out.close();  
//  
//            // 读取返回数据    
//            StringBuffer strBuf = new StringBuffer();  
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
//            String line = null;  
//            while ((line = reader.readLine()) != null) {  
//                strBuf.append(line).append("\n");  
//            }  
//            res = strBuf.toString();  
//            reader.close();  
//            reader = null;  
//        } catch (Exception e) {  
//            System.out.println("发送POST请求出错。" + urlStr);  
//            e.printStackTrace();  
//        } finally {  
//            if (conn != null) {  
//                conn.disconnect();  
//                conn = null;  
//            }  
//        }  
//        return res;  
//    }  
//}
