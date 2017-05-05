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
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/TouchSlide.1.1.js"></script>
	<script type="text/javascript">
		$(function(){
			setNav(2);
			$('.zbnav .lt').click(function(){
				if(!$(this).attr('id')){
					var ind=$(this).index(),l=33.3*ind+16.6; l=l+'%';
					if(ind==2){$('.head .rt').attr('id','wenda'); $('.tiwen').show();}else{$('.head .rt').removeAttr('id'); $('.tiwen').hide();}
					$('.zbnav .line').animate({left:l},600);
					$(this).attr('id','sel').siblings('.lt').removeAttr('id');
					$('.cases').eq(ind).show().siblings('.cases').hide();
				}
			});
		});
	</script>
</head>
<body class="chaxun square">
	<div class="head indexhd"><div class="lt" onclick="javascript:window.location.href='${ctx }wallet/index.do?nocheck=1'"></div><a class="tel"  href="${ctx }customtel.do"><b class="rt c2">我的问答</b></a><p class="title text1">广场</p></div>
	<div class="zbnav txc">
		
		<!-- <div class="lt" id="sel">活动</div>
		 <div class="lt">咨询</div>
		<div class="lt">问答</div>
		<div class="line"></div>-->
	</div>
	<div class="content" style="padding-top:35px;">
		<div id="content" class="cases case1">
			
		</div>
	</div>
	<div id="loadmorebtn" class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
	<!--<div class="tiwen bc2 txc disn" onclick="location.href='tw.html'"><img src="${ctx }v3/images/edit_b.png" /><br />提问</div>-->
		<div class="line"></div>
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
		var curPage = 1;
		$(document.body).ready(function(){
			loadHistoryActivityInfo(curPage);
			
		});
		$("#loadmorebtn").click(function(){
			if($(this).html() == "加载更多"){
				loadHistoryActivityInfo(curPage + 1 );
			}
		});
		function loadHistoryActivityInfo(page){
			$(".wrapper-load-more").html("<img src=\"${ctx }images/status_loading.gif\" style=\"vertical-align: middle;\" />&nbsp;加载中..");
			
			$.post("${ctx}/activity/historyActivityData.do",{pageNo:page},function(data){
				$(".wrapper-load-more").html("加载更多");
				
				if(data == undefined || data.length　< 1 || data.length < 10){
					$("#loadmorebtn").hide();	
				}else if( data.length >= 10){
					$("#loadmorebtn").html("加载更多");	
				}
				
				if(data != undefined && data.length > 0){
					for(var i = 0 ;i<data.length;i++){
						var status = "";
						if(data[i].status == "1"){
							status = "进行中";
						}else if(data[i].status == "-1"){
							status = "已过期";
						}else if(data[i].status == "-2"){
							status = "已过期";
						}else if(data[i].status == "-3"){
							status = "未开始";
						}
						$("#content").append("<div class=\"box\" onclick=\"location.href='${ctx }/activity/getActivityById.do?id="+data[i].id+"'\"><div class=\"img bgc\" style=\"background-image:url("+data[i].mainImgUrl+");\">"+
							"<div class=\"tit\">活动时间："+data[i].startTime+" 至 "+data[i].endTime+"</div><div class=\"stus bc2\">"+status+"</div>"+
						"</div></div>");
						
					}
				}
			});
		}
	</script>
</body>
</html>