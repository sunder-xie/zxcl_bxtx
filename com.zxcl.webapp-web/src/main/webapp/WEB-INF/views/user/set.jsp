<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<jsp:include page="../commons/taglibs.jsp" />
	<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">设置</p></div>
	<div class="content set">
		<div class="arr_r yij" onclick="location.href='${ctx}suggest.do'">意见反馈</div>
		<div class="arr_r"  onclick="location.href='${ctx}cjwt.do'">常见问题</div>
		<div class="arr_r"  onclick="location.href='${ctx}gywm.do'">关于我们</div>
		<div class="arr_r" onclick="location.href='${ctx}revisedCode.do'">修改密码</div>
		<div class="submit txc" id="submit1">退出登录</div>
	</div>
	
	<script type="text/javascript">
	$("#submit1").on("click",function(){
		$.post("${ctx}outlogin.do",function(data){
			if(data.success){
				location.href="${ctx}logon.do";
			}
		});
	});
	</script>
	
</body>
</html>