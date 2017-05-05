<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
.q-item{
	margin-bottom: 10px;
	text-align: center;
	height: 60px;
	line-height:60px;
	width: 90%;
	margin: 3% auto;
	border:1px solid #2BA5E9;
	color:#2BA5E9;
	border-radius: 5px;
}
.q-item-btns{
	margin-bottom: 10px;
	text-align: center;
	height: 40px;
	line-height:40px;
	width: 90%;
	margin: 3% auto;
	border:1px dashed #dcdcdc;
	border-radius: 5px;
}
.q-item span{
	margin-right: 10%;
	float: left;
}
.q-item span.s1{
	margin-left: 10%;
}
.q-item span.s2{
	color:#D64141;
}
.q-item span.s3{
	
}
.cltbxx .div{
    padding: 0 0 ;
}
.arr {
    position: relative;
    width: 10px;
    height: 10px;
    left: 8px;
    border: 1px solid #d8d9da;
    border-top: 0;
    border-right: 0;
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    position: relative;
    left: 45%;
    top: 65%;
}
.arr2 {
    left: 45%;
    top: 55%;
}
.q-btn{
    margin-right: 10px;
    padding: 7px 9px;
    border-radius: 3px;
    color: white;
    font-size: 14px;
    margin-bottom: 5px;
}
.q-item-btn{

}
.q-item-btn{
    display: inline-block;
    border: 1px solid #D64141;
    border-radius: 5px;
    margin: 0 auto;
    padding: 2px 6px;
    height: 20px;
    line-height: 20px;
    color: white;
    background: #D64141;
    font-size: 13px;
}
.s3 a{
	color:#2ba5e9;
}
.q-item-btns{
	display: none;
}
li a,.q-item2{
	text-align: left;
    height: auto;
    color:#67d867;
    
}
.q-item2{
	border: 1px dotted;
    padding-bottom: 10px;
}
li a{
	text-decoration:underline;
}
ul li {
    list-style: inherit;
    height: 40px;
    margin-left: 25px;
}
.zhifu{
	display: none;
    width: 50px;
    height: 25px;
    line-height: 25px;
    padding: 0;
    background-color: #D64141;
    color: white !important;
    float: right;
    margin: 37% -30% 0 15%;
    border-radius: 5px;
}
.rengong{
	display:inline-block;
	width: 70px;
    height: 25px;
    line-height: 25px;
    padding: 0;
    background-color: #D64141;
    color: white !important;
    float: right;
    margin: 27% -30% 0 15%;
    border-radius: 5px;
}
</style>
</head>
<body style="background:#fff;">
	<div class="head"><div class="back lt" onclick="window.location.href='${ctx}infoQuery.do';"></div><p class="title">操作提示</p></div>
	<div class="content bxbj content">
		<div id="base_info">
			<div class="div line1 clear">
				<b class="lt c3">车牌：${inquiry.plateNo}</b>
				<b class="rt c3">车主：${inquiry.ownerName}</b>
			</div>
		</div>
		<div class="q-items">
			<div class="q-item q-item2" >
				<div style="margin-left: 3%;padding-left: 18%;">请确认此订单是否已支付?</div>
				<input onclick="window.location.href='${ctx}list2.do';" id="submit1" class="submit" type="button" value="已支付" style="margin-left: 12%;width: 38%;">
				<input onclick="window.location.href='${ctx}toquota.do?inquiryId=${inquiry.inquiryId}&panduan=1';" id="submit1" style="width: 38%;" class="submit" type="button" value="未支付">
			</div>
			
			<c:forEach items="${inquiry.orderList}" var="orderItem">
				<c:if test="${orderItem.queryStage eq '4' or orderItem.queryStage eq '14'}">  
					<div class="q-item-o">
						<div class="q-item">
							<span class="s1">${orderItem.insurance.insPetName}</span>
							<span class="s2">${orderItem.quota.totalAmount}</span>
							<span class="s3">
							</span>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
	$(function(){
	});
	
	</script>
</body>
</html>