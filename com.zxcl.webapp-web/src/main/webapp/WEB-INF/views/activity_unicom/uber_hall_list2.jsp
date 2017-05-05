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
<title>优步出行</title>
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
	.uberck{
		border-radius: 100px;
	}
	.uberltlogo img:first-child{
	margin-left:8%;
}
.uberltlogo img:last-child{
	margin-right:8%;
}.tels {
font-size:1em;
line-height:1em;
}.dd_lq .telbox .tels{
line-height:1em;
}.uberck{
margin-top:0.3em;
height:1em;
width:1em;
border-radius:100px;

margin-right:1rm;
}
.uberck:checked:after{position:absolute; content:""; border-left:2px solid #fff; 
border-bottom:2px solid #fff; width:0.4em; height:0.3em; 
-webkit-transform:rotate(-45deg); transform:rotate(-45deg); left:3px; top:4px;}
</style>
 <script type="text/javascript">
        var phoneWidth =  parseInt(window.screen.width);
        var phoneScale = phoneWidth/380;
        var ua = navigator.userAgent;
        if (/Android (\d+\.\d+)/.test(ua)){
            var version = parseFloat(RegExp.$1);
            if(version>2.3){
                document.write('<meta name="viewport" content="width=380, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
            }else{
                document.write('<meta name="viewport" content="width=380, target-densitydpi=device-dpi">');
            }
        } else {
            document.write('<meta name="viewport" content="width=380, initial-scale=1, maximum-scale=1.001, user-scalable=0, target-densitydpi=device-dpi">');
        }
    </script>
</head>
<body class="dd_lq yyt">
	<div class="content">
		<div class="ddbox">
			<div class="uberltlogo clear">
				<img class="lt" style="width:25%;" src="${ctx}activity/20160704unicom/uberlogo.jpg" />
				<div class="lt" style="position:absolute;margin-left:41%;margin-top:3%;border-right: 1px solid #BDBCBC;height:60%;widht:1px;"></div>
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
		</div>
		<div class="telbox">
			<c:if test="${plateNo != null }">
			<div class="tels nline clear">
				<div class="lt">车牌号：</div>
				<div class="lt ubertxtbc2">${plateNo}</div>
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
				<div class="lt ubertxtbc2">${mealIdName}</div>
			</div>
			
			<div class="tels clear mycolor">
				<div class="lt">天府大道北段16号高新国际大厦C座一层</div>
				<input class="uberck rt rtaaa" type="radio" data-id="天府大道北段16号高新国际大厦C座一层" name="tel" />
				<div class="pic">
					<img class="pic-img" src="${ctx}images/activity/unicome/hall/6.jpg" />
				</div>
			</div>
		</div>
		<div class="submit uberbc1 txc" style="line-height:2em;font-size:1em;"  id="subm">确认提交</div>
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
					window.location.href = '${ctx}activity_unicom/uber_to_phone_list.do';
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