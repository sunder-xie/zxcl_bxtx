<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
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
<title>保行天下-你查看的页面找不到了</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供你选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css"/>
<script type="text/javascript" src="${ctx}js/jquery-1.7.2.min.js" ></script>
<style>
.errorDetail {
    padding: 60px 0 0 17%;
    margin: 10px 0 30px;
}
.errorDetail h2 {
    font-size: 16px;
    font-weight: 700;
    margin-bottom: 30px;
}
.errorAdvice {
    line-height: 2;
}
.errorAdvice p {
    margin-bottom: 10px;
}
.errorAdvice ol {
    margin-left: 20px;
}
ul, ol {
    list-style: none;
}
.errorAdvice li {
    list-style-type: decimal;
}
</style>
<script type="text/javascript" src="${ctx}js/jquery-1.7.2.min.js" ></script>
</head>
<body class="right-body">
<header class="header">
	<div class="title">
		<a class="iconLogo" href="${ctx}/index.do?nocheck=1"><img src="${ctx}images/iconLogo.png"></a>
		保行天下
		<a class="iconTel" href="tel:4009691365"><img src="${ctx}images/iconTel.png"></a>
	</div>
</header>

<div class="current">
    <div class="crumb">当前位置：<a href="#">报错页面</a> <a href="#">查无内容</a></div>
</div>
<div class="errorDetail">
	<h2>很抱歉，你查看的页面找不到了！</h2>
	<div class="errorAdvice">
		<p>你可以：</p>
		<ol>
			<li><a href="${ctx}/index.do?nocheck=1">返回首页</a></li>
			<li><a href="javascript:;" onclick="window.history.go(-1);">返回上一页</a></li>
		</ol>
	</div>
</div>
<jsp:include page="../commons/menu.jsp"/>
<script type="text/javascript" src="${ctx}js/main.js" charset="UTF-8"></script>
</body>
</html>