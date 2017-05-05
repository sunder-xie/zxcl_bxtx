<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>提示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>

  </head>
  
  <body>
    <div style="font-size: 1.4rem;text-align: center;margin-top: 60%;color: #e26f6f;">${param.msg }</div>
    <div style="margin-top: 40%; font-size: 0.9rem;">遇见问题了？请点此<span style="color:#0b84ef;" onclick="location.href='${ctx}/customtel.do';">联系客服</span>。</div>
    <div style="margin-top: 5%; font-size: 0.9rem;">我已联系客服解决该问题，点此<span style="color:#0b84ef;" onclick="reLogin(0)">重新登陆</span>。</div>
    <script type="text/javascript">
    function reLogin(){
    	$.post("${ctx}/outlogin.do",function(data){
        	location.href="${ctx}/logon.do";
    	});
    }

    </script>
  </body>
</html>
