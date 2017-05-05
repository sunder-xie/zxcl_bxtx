
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.log4j.Logger" %>
	<%
		//Exception from JSP didn't log yet ,should log it here.
		//String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		//Logger.getLogger(requestUri).error(exception.getMessage(), exception);
	%>
<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel=stylesheet type="text/css" href="${ctx}css/v2_style.css" />
<script type="text/javascript" src="${ctx}js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/init.js"></script>
</head>
<body class="right-body">
<div class="current">
    <div class="crumb">当前位置：<a href="#">报错页面</a> <a href="#">异常处理</a></div>
</div>
<div class="right-frame m-err">
	<dl class="u-err u-err-2">
		<dt><i></i></dt>
		<dd>
			<div class="txt">
				<h3>访问异常</h3>
				<a href="${cxt}/index.do?nocheck=1" class="btn f-ib">返回</a>
			</div>
		</dd>
	</dl>
</div>
<div class="xError2">
	<%= exception %>
    <textarea class="textarea"><%exception.printStackTrace(new java.io.PrintWriter(out));%></textarea>
</div>
</body>
</html>