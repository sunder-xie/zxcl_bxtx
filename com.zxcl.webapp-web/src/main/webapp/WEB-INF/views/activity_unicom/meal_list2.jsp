<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>

<html>
<head>
<title>滴滴出行</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="full-screen" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="format-detection" content="address=no" />
<link rel="shortcut icon" type="image/x-icon"
	href="${ctx}images/favicon.ico" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/TouchSlide.1.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}css/activity/style.css" />
<style>
.hyp{
	margin:0 0 0 15%;
	font-size:12px;
	color:#F08300;
}
.inp.boxSet.txc:focus{
	border:1px solid #F08300;
}
#yxtc{
    padding: 8% 0;
}
</style>
</head>
<body class="dd_lq">
	<div class="fixedbg" style="background:url(${ctx}images/activity/unicome/taoc.JPG) no-repeat center rgba(0,0,0,.6); background-size:100%;"></div>
	<div class="content">
		<div class="ddbox">
			<div class="ddltlogo clear">
				<img class="lt" src="${ctx}images/activity/unicome/dd_logo.png" />
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
			<div class="tels line nline clear" id="yxtc">
				<div class="lt">已选套餐：</div>
				<div class="lt c2">${mealIdName}</div>
				<c:if test="${plateNo != null }">
				<div class="lt">&nbsp;车牌号：</div>
				<div class="lt c2">${plateNo}</div></c:if>
			</div>
			<b class="tit f16 disb">选择新号码 <span class="hyp wrapper-load-more22">换一批</span></b>
		</div>
		<div class="telbox">
		</div>
		<div class="submit bc1 txc" id="subm">选择营业厅</div>
	</div>
<input type="hidden" id="currPage" value="1"/>
<script type="text/javascript">
	var cc = 0;
	$(function(){
		dataPage();
		$('.wrapper-load-more22').on('click',function(){
			$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
			cc += 1;
			dataPage();
		});
		$('#subm').click(function(){
			var mealId;//手机号
			$('input[type="radio"]').each(function() {
				if ($(this).is(":checked")) {
					mealId = $(this).attr('data-id');
					return false;
				}
			});

			if (null == mealId || '' == mealId) {
				alert('请选择手机号');
				return false;
			}
			window.location.href = '${ctx}activity_unicom/to_hall_list.do?phoneId='+mealId;
		});
	});
	var dataPage = function(){
		$.post("${ctx}activity_unicom/page_phone.do",{'currentPage':parseInt($('#currPage').val()),'mealId':'${mealId}'},function(data){
			if(data.success){
				var page = data.data;
				var list = page.dataList;
				var html = '';
				var item ;
				
				if(page.pageCount == 1 || page.currentPage == page.pageCount || cc >= 3){
					$('.wrapper-load-more22').hide();
				}else{
					$('.wrapper-load-more22').show();
				}
				
				if(list == null || list.length == 0){
					$('.wrapper-load-more22').hide();
					alert('查无记录...');
					return false;
				}
				var am;
				for (var i = 0; i < list.length; i++) {
					item = list[i];
					if(null != item.remark && '' != item.remark){
						html += '<div class="tels clear"><div class="lt">'+item.phoneId+'</div><div class="lt c1">靓号哦~</div><input class="ck rt" data-id="'+item.phoneId+'" type="radio" name="tel" /></div>';
					}
					else{
						html += '<div class="tels clear"><div class="lt">'+item.phoneId+'</div><div class="lt c1"></div><input class="ck rt" data-id="'+item.phoneId+'" type="radio" name="tel" /></div>';
					}
				}
				$('.telbox').html(html);
			}else{
				alert(data.message);
				$('.wrapper-load-more22').hide();
			}
		},'json');
	}
	</script>
</body>














</html>