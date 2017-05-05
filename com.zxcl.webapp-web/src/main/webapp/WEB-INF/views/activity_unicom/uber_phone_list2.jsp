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
<link rel="stylesheet" type="text/css" href="${ctx}css/activity/style.css"/>
<style>
.inp.boxSet.txc:focus{
	border:1px solid #171717;
}
.dd_lq .inp{
	height:1.5em;
	font-size:1em;
}
.inp{
	
}
.tcxq{
	font-size:8em;
}
.tc{
	padding:10px 20px 0 0;
}
.tc .textdiv{
	height:1em;
	line-height:1em;
	text-indent: 7%;
	font-size:1em;
} 

.uberck{
margin-top:0.2em;
height:1em;
width:1em;
border-radius:100px;

margin-right:1rm;
}

.tc div{
	margin-top:1%;
}

.tc  #up{background-position:center bottom; -webkit-transform:rotate(-90deg); transform:rotate(-90deg);width:40px; height:40px;background-image:url(${ctx}images/activity/unicome/arr_r.png);background-repeat:no-repeat;background-size:11px;transition:all ease .6s;}
.tc  #down{background-position:center top; -webkit-transform:rotate(90deg);transform:rotate(90deg);width:40px; height:40px;background-image:url(${ctx}images/activity/unicome/arr_r.png);background-repeat:no-repeat;background-size:11px;transition:all ease .6s;}

.uberltlogo img:first-child{
	margin-left:8%;
}
.uberltlogo img:last-child{
	margin-right:8%;
}.f16{
font-size:1em;
}
.f14{
	font-size:0.7rem;
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
<body class="dd_lq">
	<div class="fixedbg" style="background:url(${ctx}activity/20160704unicom/ubertaocan.jpg) no-repeat center rgba(0,0,0,.6); background-size:100%;"></div>
	<div class="content">
		<div class="ddbox">
			<div class="uberltlogo clear">
				<img class="lt" style="width:25%;" src="${ctx}activity/20160704unicom/uberlogo.jpg" />
				<div class="lt" style="position:absolute;margin-left:41%;margin-top:3%;border-right: 1px solid #BDBCBC;height:60%;widht:1px;"></div>
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
			<b class="tit f16 disb">联系方式</b>
			<input class="inp boxSet txc" type="text" value="${unicomFlow.contactName}" id="contact_name" placeholder="输入你的姓名" />
			<input class="inp boxSet txc" type="tel" value="${unicomFlow.contactPhone}" id="contact_phone" placeholder="输入现用手机号码" maxlength="11"/>
			<input class="inp boxSet txc" type="text" value="<c:if test="${unicomFlow == null }">川</c:if><c:if test="${unicomFlow != null }">${unicomFlow.plateNo}</c:if>" id="plateNo" placeholder="输入车牌号"  pattern="^[\u4e00-\u9fa5]{1}[0-9a-zA-Z]{6}$" required="required" style="text-transform:uppercase;" maxlength="7" />
			<b class="tit f16 disb">选择套餐<span class="rt btn uberbc1 f14" id="tcxq">点击查看套餐详情</span> </b>
		</div>
		<div class="telbox">
			<div class="tc clear">
				<div class="group">
					<div class=" clear">
						<div class="textdiv lt">106单卡套餐（折后价29元）</div>
						<input class="uberck rt" type="radio" data-id="106" name="tel" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div class="group">
					<div class=" clear">
						<div class="textdiv lt">136单卡套餐（折后价39元）</div>
						<input class="uberck rt" type="radio" data-id="136" name="tel" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div class="group">
					<div class=" clear">
						<div class="textdiv lt">166单卡套餐（折后价69元）</div>
						<input class="uberck rt" type="radio" data-id="166" name="tel" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div class="group">
					<div class=" clear">
						<div class="textdiv lt">196单卡套餐（折后价99元）</div>
						<input class="uberck rt" type="radio" data-id="196" name="tel" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div class="group">
					<div class=" clear">
						<div class="textdiv lt">296单卡套餐（折后价129元）</div>
						<input class="uberck rt" type="radio" data-id="296" name="tel" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div class="group">
					<div class=" clear">
						<div class="textdiv lt">396单卡套餐（折后价159元）</div>
						<input class="uberck rt" type="radio" data-id="396" name="tel" />
					</div>
				</div>
			</div>
			
		</div>
		<div class="submit uberbc1 txc" style="line-height:2em;font-size:1em;"  id="subm">选择手机号</div>
	</div>
	
	<input type="hidden" id="currPage" value="1"/>
	
	<script type="text/javascript">
	var arrs = ["130","131","132","155","156","185","186","176"];

	$(function(){
		$(".uberck").click(function(){
			if($(this).attr("checked") == "checked" && 
					( $(this).attr("data-id") == "106" || $(this).attr("data-id") == "136" ||$(this).attr("data-id") == "166"  )){
				alert("该套餐加10元即可享受优步2G定向流量+腾讯视频包或QQ音乐");
			}else{
				alert("该套餐免费赠送优步2G定向流量+腾讯视频或QQ音乐");
			}
		});
		
		
		$(".telbox .jt").click(function(){
			if($(this).attr("id") == "down"){
				$(this).parent().parent().parent().find(".group").show();
				$(this).attr("id","up");
			}else{
				$(this).attr("id","down");
				$(this).parent().parent().parent().find(".group").hide();
			}
			
		});
		
		$('#tcxq').click(function() {
			$('.fixedbg').fadeIn();
		});
		$('.fixedbg').click(function() {
			$('.fixedbg').fadeOut();
		});
		
		$('#subm').click(function(){
			var contactName = $('#contact_name').val();
			var contactPhone = $('#contact_phone').val();
			var plateNo = $('#plateNo').val();
			if(contactName == null || '' == contactName){
				$('#contact_name').focus();
				alert("请输入正确的姓名");
				return false;
			}
			if(!/^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$/.test(contactName)){
				$('#contact_name').focus();
				alert("请输入正确的姓名");
				return false;
			}
			
			if(contactPhone == null || '' == contactPhone){
				$('#contact_phone').focus();
				alert("请输入正确的手机号码");
				return false;
			}
			
			if(!/^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$/.test(contactPhone)){
				$('#contact_phone').focus();
				alert("请输入正确的手机号码")
				return false;
			}
			
			if(plateNo == null || '' == plateNo ||  plateNo.length != 7){
				$('#plateNo').focus();
				alert("请输入正确的车牌号");
				return false;
			}
			
			if(!/^[\u4e00-\u9fa5]{1}[0-9a-zA-Z]{6}$/.test(plateNo)){
				$('#plateNo').focus();
				alert("请输入正确的车牌号");
				return false;
				
			}
			
			
			var phone = null;
			$('input[type="radio"]').each(function(){
				if($(this).is(":checked")){
					phone = $(this).attr('data-id');
					return false;
				}
			});
			if(null == phone || '' == phone || 'undefined' == phone || 'null' == phone){
				alert('请选择套餐');
				return false;
			}
			$.post("${ctx}activity_unicom/crt_flow.do",{'mealId':phone,'contactName':contactName,'contactPhone':contactPhone,'plateNo':plateNo.toUpperCase(),'source':'2'},function(data){
				if(data.success){
					var flowId = data.data;
					if(!flowId){
						alert('获取业务号失败');
						return false;
					}
					var isLt = false;
					for(var i = 0 ;i<arrs.length;i++){
						if(contactPhone.indexOf(arrs[i]) > -1){
							window.location.href = '${ctx}activity_unicom/uber_to_hall_list.do';
							isLt = true;
							break;
						}
					}
					if(!isLt){
						window.location.href = '${ctx}activity_unicom/uber_to_meal_list.do';
					}
					
				}else{
					alert(data.message);
				}
			},'json');
		});
	});
	</script>
</body>

</html>