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
<link rel="stylesheet" type="text/css" href="${ctx}css/activity/style.css"/>
<style>
.inp.boxSet.txc:focus{
	border:1px solid #F08300;
}
.tcxq{
	font-size:12px;
}
.tc{
	padding:10px 20px 0 0;
}
.tc .textdiv{
	height:20px;
	line-height:20px;
	text-indent: 7%;
} 
.tc input {
	border-radius:100px;
}
.tc div{
	margin-top:1%;
}

.tc  #up{background-position:center bottom; -webkit-transform:rotate(-90deg); transform:rotate(-90deg);width:40px; height:40px;background-image:url(${ctx}images/activity/unicome/arr_r.png);background-repeat:no-repeat;background-size:11px;transition:all ease .6s;}
.tc  #down{background-position:center top; -webkit-transform:rotate(90deg);transform:rotate(90deg);width:40px; height:40px;background-image:url(${ctx}images/activity/unicome/arr_r.png);background-repeat:no-repeat;background-size:11px;transition:all ease .6s;}
</style>
</head>
<body class="dd_lq">
	<div class="fixedbg" style="background:url(${ctx}activity/20160704unicom/diditaoc20160711.jpg) no-repeat center rgba(0,0,0,.6); background-size:100%;"></div>
	<div class="content">
		<div class="ddbox">
			<div class="ddltlogo clear">
				<img class="lt" src="${ctx}images/activity/unicome/dd_logo.png" />
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
			<b class="tit f16 disb">联系方式</b>
			<input class="inp boxSet txc" type="text" value="${unicomFlow.contactName}" id="contact_name" placeholder="输入你的姓名" />
			<input class="inp boxSet txc" type="tel" value="${unicomFlow.contactPhone}" id="contact_phone" placeholder="输入现用手机号码" />
			<input class="inp boxSet txc" type="tel" value="${unicomFlow.plateNo}" id="plateNo" placeholder="输入车牌号"  pattern="^[\u4e00-\u9fa5]{1}[0-9a-zA-Z]{6}$" required="required"   style="text-transform:uppercase;" maxlength="7" />
			<b class="tit f16 disb">选择套餐<span class="rt btn bc1 f14" id="tcxq">套餐详情</span> </b>
		</div>
		<div class="telbox">
			<div class="tc clear">
				<div>
					<div class=" clear">
					<div class="textdiv lt">106套餐</div>
					<div style="margin-right:3%;"  class="jt rt" id="down"></div>
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="textdiv lt">106单卡套餐（折后价29元）</div>
						<input class="ck rt" type="radio" data-id="106" name="tel" />
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="lt textdiv">106合约机套餐（套餐价85元，赠机如下）</div>
						<input class="ck rt" type="radio" data-id="106_1" name="tel" />
					</div>
					<div class="img clear" style="widht:80%;height:200px;padding-left:7%;margin-top:1%;">
						<img style="width:100%;height:100%;" src="${ctx}images/activity/unicome/jx01.jpg" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div>
					<div class=" clear">
					<div class="textdiv lt">136套餐</div>
					<div style="margin-right:3%;"  class="jt rt" id="down"></div>
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="textdiv lt">136单卡套餐（折后价39元）</div>
						<input class="ck rt" type="radio" data-id="136" name="tel" />
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="lt textdiv">136合约机套餐（套餐价109元，赠机如下）</div>
						<input class="ck rt" type="radio" data-id="136_1" name="tel" />
					</div>
					<div class="img clear" style="widht:80%;height:200px;padding-left:7%;margin-top:1%;">
						<img style="width:100%;height:100%;" src="${ctx}images/activity/unicome/jx02.jpg" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div>
					<div class=" clear">
					<div class="textdiv lt">166套餐</div>
					<div style="margin-right:3%;"  class="jt rt" id="down"></div>
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="textdiv lt">166单卡套餐（折后价69元）</div>
						<input class="ck rt" type="radio" data-id="166" name="tel" />
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="lt textdiv">166合约机套餐（套餐价133元，赠机如下）</div>
						<input class="ck rt" type="radio" data-id="166_1" name="tel" />
					</div>
					<div class="img clear" style="widht:80%;height:200px;padding-left:7%;margin-top:1%;">
						<img style="width:100%;height:100%;" src="${ctx}images/activity/unicome/jx03.jpg" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div>
					<div class=" clear">
					<div class="textdiv lt">196套餐</div>
					<div style="margin-right:3%;"  class="jt rt" id="down"></div>
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="textdiv lt">196单卡套餐（折后价99元）</div>
						<input class="ck rt" type="radio" data-id="196" name="tel" />
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="lt textdiv">196合约机套餐（套餐价157元，赠机如下）</div>
						<input class="ck rt" type="radio" data-id="196_1" name="tel" />
					</div>
					<div class="img clear" style="widht:80%;height:200px;padding-left:7%;margin-top:1%;">
						<img style="width:100%;height:100%;" src="${ctx}images/activity/unicome/jx04.jpg" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div>
					<div class=" clear">
					<div class="textdiv lt">296套餐</div>
					<div style="margin-right:3%;"  class="jt rt" id="down"></div>
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="textdiv lt">296单卡套餐（折后价129元）</div>
						<input class="ck rt" type="radio" data-id="296" name="tel" />
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="lt textdiv">296合约机套餐（套餐价237元，赠机如下）</div>
						<input class="ck rt" type="radio" data-id="296_1" name="tel" />
					</div>
					<div class="img clear" style="widht:80%;height:200px;padding-left:7%;margin-top:1%;">
						<img style="width:100%;height:100%;" src="${ctx}images/activity/unicome/jx05.jpg" />
					</div>
				</div>
			</div>
			
			<div class="tc clear">
				<div>
					<div class=" clear">
					<div class="textdiv lt">396套餐</div>
					<div style="margin-right:3%;"  class="jt rt" id="down"></div>
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="textdiv lt">396单卡套餐（折后价169元）</div>
						<input class="ck rt" type="radio" data-id="396" name="tel" />
					</div>
				</div>
				<div class="group" style="display: none;">
					<div class=" clear">
						<div class="lt textdiv">396合约机套餐（套餐价317元，赠机如下）</div>
						<input class="ck rt" type="radio" data-id="396_1" name="tel" />
					</div>
					<div class="img clear" style="widht:80%;height:200px;padding-left:7%;margin-top:1%;">
						<img style="width:100%;height:100%;" src="${ctx}images/activity/unicome/jx06.jpg" />
					</div>
				</div>
			</div>
			
		</div>
		<div class="submit bc1 txc" id="subm">选择手机号</div>
	</div>
	
	<input type="hidden" id="currPage" value="1"/>
	
	<script type="text/javascript">
	var arrs = ["130","131","132","155","156","185","186","176"];
	$(function(){
		$(".ck").click(function(){
			if($(this).attr("checked") == "checked" && 
					( $(this).attr("data-id") == "106" || $(this).attr("data-id") == "136" ||$(this).attr("data-id") == "166"  )){
				alert("该套餐加10元即可享受腾讯视频或QQ音乐（所选产品含6GB定向流量）");
			}else if($(this).attr("checked") == "checked" && 
					( $(this).attr("data-id") == "196" || $(this).attr("data-id") == "296" ||$(this).attr("data-id") == "396"  )){
				alert("该套餐免费赠送腾讯视频或QQ音乐（所选产品含6GB定向流量）");
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
				return false;
			}
			if(contactPhone == null || '' == contactPhone){
				$('#contact_phone').focus();
				return false;
			}
			if(plateNo == null || '' == plateNo ||  plateNo.length != 7){
				$('#plateNo').focus();
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
			console.log(phone);
			$.post("${ctx}activity_unicom/crt_flow.do",{'mealId':phone,'contactName':contactName,'contactPhone':contactPhone,'plateNo':plateNo.toUpperCase(),'source':'1'},function(data){
				if(data.success){
					var flowId = data.data;
					if(!flowId){
						alert('获取业务号失败');
						return false;
					}
					var isLt = false;
					for(var i = 0 ;i<arrs.length;i++){
						if(contactPhone.indexOf(arrs[i]) > -1){
							window.location.href = '${ctx}activity_unicom/to_hall_list.do';
							isLt = true;
							break;
						}
					}
					if(!isLt){
						window.location.href = '${ctx}activity_unicom/to_meal_list.do';
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