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
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="full-screen" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="format-detection" content="address=no" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<script type="text/javascript" src="${ctx}js/common/TouchSlide.1.1.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}css/activity/style.css"/>
<style type="text/css">
	.pic{
		width:100%;
		height:auto;
		display:none;
	}
	.pic img{
		width:100%;
		height:auto;
		margin-top: 5%;
	}
	div.tels.clear .lt{
		padding: 1% 1%;
	}
	.rtaaa{
		margin-top: 1%;
	}
</style>
</head>
<body class="dd_lq yyt">
	<div class="content">
		<div class="ddbox">
			<div class="ddltlogo clear">
				<img class="lt" src="${ctx}images/activity/unicome/dd_logo.png" />
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
		</div>
		<div class="telbox">
			<c:if test="${plateNo != null }">
			<div class="tels nline clear">
				<div class="lt">车牌号：</div>
				<div class="lt c2">${plateNo}</div>
			</div>
			</c:if>
			<c:if test="${phoneId != null }">
			<div class="tels nline clear">
				<div class="lt">已选号码：</div>
				<div class="lt ubertxtbc2">${phoneId}</div>
			</div>
			</c:if>
			<div class="tels nline yxtc clear">
				<div class="lt">已选套餐：</div>
				<div class="lt c1">${mealIdName}</div>
			</div>
			
			
			<div class="tels clear mycolor">
				<div class="lt">成华南双林路18号自有营业厅</div>
				<input class="ck rt rtaaa" type="radio" data-id="成华南双林路18号自有营业厅" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/1.jpg" />
				</div>
			</div>
			<div class="tels clear mycolor">
				<div class="lt">科华北路66号自有营业厅</div>
				<input class="ck rt rtaaa" type="radio" data-id="科华北路66号自有营业厅" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/2.jpg" />
				</div>
			</div>
			<div class="tels clear mycolor">
				<div class="lt">金牛西永陵路自有营业厅</div>
				<input class="ck rt rtaaa" type="radio" data-id="金牛西永陵路自有营业厅" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/3.jpg" />
				</div>
			</div>
			<div class="tels clear mycolor">
				<div class="lt">金牛东商贸大道39号自有营业厅</div>
				<input class="ck rt rtaaa" type="radio" data-id="金牛东商贸大道39号自有营业厅" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/4.jpg" />
				</div>
			</div>
			<div class="tels clear mycolor">
				<div class="lt">太升南路85号自有营业厅</div>
				<input class="ck rt rtaaa" type="radio" data-id="太升南路85号自有营业厅" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/5.jpg" />
				</div>
			</div>
			<div class="tels clear mycolor">
				<div class="lt">天府大道集团客户专属营业厅</div>
				<input class="ck rt rtaaa" type="radio" data-id="天府大道集团客户专属营业厅" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/6.jpg" />
				</div>
			</div>
		</div>
		<div class="submit bc1 txc" id="subm">确认提交</div>
	</div>

	
	
<script type="text/javascript">
	$(function(){
		$("#subm").click(function(){
			if(!confirm("活动套餐有限，若无办理需求，请慎重提交!")){
				return;
			}
			
			var hallId;
			$('input[type="radio"]').each(function(){
				if($(this).is(":checked")){
					hallId = $(this).attr('data-id');
					return false;
				}
			});
			if(null == hallId || '' == hallId){
				alert('请选择营业厅');
				return false;
			}
			$.post("${ctx}activity_unicom/complete_flow.do",{'bussHall':hallId,'phoneId':'${phoneId}'},function(data){
				if(data.success){
					var msg = data.data;
					window.location.href = '${ctx}activity_unicom/to_phone_list.do';
				}else{
					alert(data.message);
				}
			},'json');
		});
		
		$('div.tels.clear.mycolor .lt, .rtaaa').click(function(event){
			if($(this).parents('.tels.clear.mycolor').find('.pic').is(':hidden')){
				$('.pic').slideUp(500);
				$('div.tels.clear.mycolor').find('div.lt').css('color','black');
				$(this).parents('.tels.clear.mycolor').find('.pic').slideDown(500);
				$(this).parents('.tels.clear.mycolor').find('div.lt').css({'color':'#ADADAD'});
			}else{
				$('.pic').slideUp();
				$('div.tels.clear.mycolor').find('div.lt').css('color','black');
			}
		});
	});
</script>
</body>
</html>