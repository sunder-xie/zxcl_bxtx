<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}css/index_new.css" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}css/slidestyles.css" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxc/countUp.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxc/countUp-jquery.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxb/scroll.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style>
.fixedbg{position:fixed; z-index:1000; top:0; bottom:0; left:0; right:0; background:rgba(0,0,0,.6); display:none;}
.input_ui {
	margin-left: -3% !important;
	margin-top: -3% !important;
}
</style>
</head>
<body>
	<div class="cover">
		<div class="iphone">
			<img src="${ctx }images/iphone.png" />
		</div>
		<p>为了更好的用户体验，请将屏幕竖向浏览！</p>
	</div>
	<div>
		<div class="wrap_top" style="background:#DB4F4F;">
			<div class="con mauto clearfix">
				<a class="back" href="javascript:history.go(-1)"><img
					src="${ctx }images/back.png" /></a> <a class="logo bjjg">双11活动</a>
			</div>
		</div>
	</div>
	
	
	<div id="waitluck" style="display: none;">
		 <img src="${ctx }activity/bxtxc/a_01.jpg" />
		<img src="${ctx }activity/bxtxc/a_02.jpg" />
		<img src="${ctx }activity/bxtxc/a_03.jpg" />
		<img src="${ctx }activity/bxtxc/a_04.jpg" />
		<img src="${ctx }activity/bxtxc/a_05.jpg" />
		<div id="cds" style=" position: absolute;  top: 22.7%; left: 5%; color: white; font-family: 微软雅黑;font-weight: bold;font-size: 1.3rem;">离活动结束还有：</div>
		<div style="position: absolute;top: 28%;left: 8%;color: #ec4f3f;font-weight: bold; font-family: 微软雅黑;font-size: 1.4rem;">当前奖金池金额：</div>
		<div style="position:absolute;top: 31.2%;left: 18%;color: #ec4f3f;font-weight: bold;text-align: right;width: 63%;font-weight: bold;font-family: 微软雅黑;"><span style="font-size:3rem;" id="money">0.00</span><span>（元）</span></div>
		<div style="position: absolute;top: 41%;text-align: center;width: 100%; color: white;font-weight: bold;font-family: 微软雅黑;font-size: 1rem;">
			<div><span style="color: yellow;">第二轮</span>抽奖时间</div>
			<div>2016年11月9日 13:00 - 15:00</div>
		</div> 
	</div>
	
	<div id="lucking" style="display: none;">
		
	<img src="${ctx }activity/bxtxc/ca_01.png" />
	<img src="${ctx }activity/bxtxc/ca_02.png" />
	<img src="${ctx }activity/bxtxc/ca_03.png" />
	<img src="${ctx }activity/bxtxc/ca_04.png" />
	<img src="${ctx }activity/bxtxc/ca_05.png" />
	<img src="${ctx }activity/bxtxc/ca_06.png" />
	<img src="${ctx }activity/bxtxc/ca_07.png" />
	<img src="${ctx }activity/bxtxc/ca_08.png" />
	<div id="cds" style="    position: absolute;  top: 16.7%; left: 5%; color: white; font-family: 微软雅黑;font-weight: bold;font-size: 1.3rem;">离活动结束还有：</div>
	<div style="position: absolute;top: 21%;left: 8%;color: #ec4f3f;font-weight: bold; font-family: 微软雅黑;font-size: 1.4rem;">当前奖金池金额：</div>
	<div style="position:absolute;top: 24.2%;left: 18%;color: #ec4f3f;font-weight: bold;text-align: right;width: 63%;font-weight: bold;font-family: 微软雅黑;"><span style="font-size:3rem;" id="money">0.00</span><span>（元）</span></div>
	<div id="scorlldiv" style="  height: 12.7%; overflow: hidden;  position: absolute;top: 50.6%;left: 24%; color: white;width: 51%;">
		<ul id="namelists">

		</ul>
	</div>
	<div id="qiangbtn" style="position: absolute; width: 32%;height: 13%; top: 32%; left: 34%;"></div>
	<div id="hblqopen" style=" display:none;position: absolute;background: url(${ctx}activity/bxtxc/ca_hb.png) no-repeat 49%;top: 19%; z-index: 1001;     width: 98%;margin-left: 1%; margin-right: 1%; height: 40%;"></div>
	<div id="hblqje" style=" display: none;position: absolute;top: 36%; width: 37%;left: 33%;color: #f7f71b;z-index: 1003;font-size: 2.6rem;text-align: center; font-weight: bold;">500</div>
	<div id="zlyc" style=" display:none;position: absolute;top: 46%;left: 35%;width: 31%; height: 3%;z-index: 1002;"></div>
	<div id="hblqcjcs" style=" display:none;position: absolute;top: 51.5%; width: 53%; left: 24%; color: white;z-index: 1003;font-size: 1.4rem;text-align: center; font-weight: bold;">抽奖次数还剩0次</div>
	<div id="cancel" style="display:none;background:url(${ctx}activity/bxtxc/cancel.png);position: absolute;top: 20.5%;width: 24px;height: 24px;z-index: 1004; left: 3%;"></div>
	</div>
	
	<div id="remark"></div>
	
	<script type="text/javascript">
	
	
	
	var intDiff = parseInt(60);//倒计时总秒数量
	
	
	function timer(intDiff){
		window.setInterval(function(){
		var day=0,
			hour=0,
			minute=0,
			second=0;//时间默认值		
		if(intDiff > 0){
			day = Math.floor(intDiff / (60 * 60 * 24));
			hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}
		$('#hour_show').html(hour);
		$('#minute_show').html(minute);
		$('#second_show').html(second);
		$("#cds").html("离活动结束还有：" + day+"天"+hour+"时"+minute+"分"+second + "秒");
		intDiff--;
		}, 1000);
	} 
	var serverTime = '${currentTime}';
	var end = new Date("2016/11/11 23:59:59");
	serverTime = serverTime.replace(/-/g, "/");
	var now = new Date(serverTime);
	$(function(){
		
		timer(((end - now) / 1000) + 1);
		
		controlPageContent();
		
		
	});	

	function controlPageContent(){
		var startTime = '${startTime}';
		var endTime = "${endTime}";
		startTime = startTime.replace(/-/g, "/");
		endTime = endTime.replace(/-/g, "/");
		var now = new Date(serverTime);
		startTime = new Date(startTime);
		endTime = new Date(endTime);
		if(now > startTime && now < endTime){
			var html = $("#lucking").html();
			$("#waitluck").remove();
			$("#lucking").remove();
			$("#remark").after(html);
			setTimeout("window.location.reload()",endTime - now); //当活动时间到了，刷新页面，更新为下波活动
		}else if(now > endTime || now < startTime || end - now > 0){
			var html = $("#waitluck").html();
			$("#waitluck").remove();
			$("#lucking").remove();
			$("#remark").after(html);
			
			if(now < startTime ){
				setTimeout("window.location.reload()",startTime - now + 1000); //抽奖时间到了。刷新页面
			}
		}
		
		
	}
	
	</script>
	
	<script type="text/javascript">
		var numAnim =  undefined;
	
		$(function(){
			
			get();
			getNameLists();
			$("#qiangbtn").click(function(){
				luckdraw();
			});
			
			$("#zlyc,#cancel").click(function(){
				$("#hblqopen").hide();
				$("#hblqje").hide();
				$("#zlyc").hide();
				$("#hblqcjcs").hide();
				$(".fixedbg").hide();
				$("#cancel").hide();
			});
			

			/* 
			$("#scorlldiv").myScroll({
				speed:60, //数值越大，速度越慢
				rowHeight:15 //li的高度
			}); */
			
		});
		
		function get(){
			$.post("${ctx}/activityc/getCurrentMoney.do","",function(data){
				if(isNaN(parseFloat(data))){
					return ;
				}
				if(numAnim == undefined){
					numAnim = new CountUp("money", 0, data,2);
					numAnim.start();
				}else{
					numAnim.update(data);
				}
			});
			
			setTimeout("get()", 3000);
		}
		
		function getLettoryNum(){
			$.post("${ctx}/activityc/getLettoryNum.do","",function(data){
				alert(data);
			});
		}
		
		function luckdraw(){
			$.post("${ctx}/activityc/luckdraw.do","",function(data){
				if(data.result == "-1"){
					dialog("请您先登陆","立即登陆",function(){
						window.location.reload();
					});
				}else if(data.result == "-2"){
					dialog("尚未开始抽奖，请稍等");
				}else if(data.result == "-3"){
					dialog("尚未开始抽奖，请稍等");
				}else if(data.result == "-10"){
					dialog("亲，出单后才能抢红包哦");
				}else if(data.result == "-11"){
					dialog("亲，本轮抽奖次数已用完，多多出单，下轮红包等您来抢~");
				}else if(data.result == "1"){
					hasfixbg();
					$(".fixedbg").show();
					$("#hblqje").html(data.money+".00");
					$("#hblqcjcs").html("抽奖次数还剩"+data.num+"次");
					$("#hblqopen").show();
					$("#hblqje").show();
					$("#zlyc").show();
					$("#hblqcjcs").show();
					$("#cancel").show();
				}
			});
		}
		
		function getNameLists(){
			$.post("${ctx}/activityc/getNamelists.do","",function(data){
				if(data.length > 0 ){
					$("#namelists").empty();
					for(var i = 0 ;i<data.length ;i++){
						
						$("#namelists").append("<li style=\"height:12px;line-height:12px;font-size:11px;\"><div style=\"float:left;\">"+data[i].name+"</div><div style=\"float:right;\">￥"+data[i].total+"</div></li>")
					}
					
					
				}else{
					$("#namelists").append("<li style=\"height:15px;line-height:15px;\"><div style=\"float:left;\">暂无抽奖名单.</div><div style=\"float:right;\"></div></li>")
				}
			});
			
			setTimeout("getNameLists()", 20000);
		}
	</script>
</body>
</html>

