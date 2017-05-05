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
<link rel="shortcut icon" type="image/x-icon"
	href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/jquery-ui.css"/> --%>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxb/jquery.easing.min.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxb/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxb/scroll.js"></script>
<style>
html {
	overflow-x: hidden;
}

.pin-cx {
	width: 50%;
	text-transform: uppercase;
}

.bjmp .bjmp_ischeck {
	padding: 2% 0 2% 9.6%;
}

.mauto {
	margin: 0 2%;
}

.menu .tab {
	padding: 5% 0;
	margin: 0 0 0 0;
}

input[type='date'] {
	display: inline-block;
	width: 52%;
	border: 1px solid #2BA5DF;
}

#js_info_list input[type='checkBox'] {
	border-radius: 50%;
}

.chunk {
	padding: 3% 0 9% 0% !important;
	background-color: #F2F2F2 !important;
}

select {
	height: 30px;
	background-color: white;
	margin-left: -1.2%;
}

h2 {
	font-size: 16px;
	color: #fe5a00;
}

@media screen and (min-width:760px) {
	/* 	input[type="checkbox"], input[type="radio"] { */
	/* 		width: 40px; */
	/* 		height: 40px; */
	/* 	} */
	.hei-30 {
		height: 30px !important;
		line-height: 30px !important;
	}
}
.logo {
width:55%;
left:17%;
}
</style>
</head>
<body>
	<!--横屏显示和loading start-->
	<div class="cover">
		<div class="iphone">
			<img src="${ctx}images/iphone.png" />
		</div>
		<p>为了更好的用户体验，请将屏幕竖向浏览！</p>
	</div>
	<div class="">
		<div class="wrap_top" style="background: #DB4F4F;">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/back.png"></a> <a
					class="logo bjjg">保行天下发红包活动</a> <a class="tel"
					href="tel:${sessionScope.tel }"><img
					src="${ctx}images/phone.png"></a>
			</div>
		</div>
	</div>
	<!-- 抽奖部分 -->
	<img  src="${ctx }activity/bxtxb/lADORiRmec0GPs0DWQ_857_1598.jpg" />
	<div style="position:absolute; ;top:21.5%;left:29%;">
		<img id="lotteryBtn" style="width:60%;height:60%;" src="${ctx }activity/bxtxb/lALORhzrxM0BLM0BLA_300_300.png" />
	</div>
	
	<div id="all" style="display:none;position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; z-index: 10; opacity: 0.9; background-color: rgb(142, 140, 140);">
	</div>
	<div id="cd" style="display:none;position: absolute; top: 0px; left: 0px; z-index: 11; padding-top: 35%;">
		<img src="${ctx }activity/bxtxb/coujiang_12_1.png">
		
		<a style="border:0px;position: absolute;bottom:15%;left:30%;"><img src="/bxtx/activity/bxtxb/coujiang_20_1.png"></a>
		<div style="position:absolute ;top:36%;	left:36%;font-size:22px;font-weight: 500;letter-spacing:7px;color:red;">
			温馨提示
		</div>
		<div id="confirminfo" style="position:absolute ;top:57%;left:15%;font-size:18px;width:70%;text-align:center; ">
			
		</div>
		
	</div>
	
	<div id="closebtn" style="display:none;position:absolute ;z-index:12;top:52.8%;width:41%;text-align:center;left:33%;font-size:18px;letter-spacing:18px;color:red;font-weight: 500; ">
			<div >确定</div>
		</div>
	
	<!-- 获奖名单 -->
	<div style="text-indent:3px;width:80%;height: 82px;position: absolute;top:60%;left:10%;background-color: white;border-radius:5px;">
		<div>获奖名单</div>
		<c:if test="${wincount == 0} ">
			<div>一等奖还剩1名</div>
			<div>二等奖还剩2名</div>
			<div>三等奖还剩3名</div>
		</c:if>
		<div id="scorlldiv" style="height:60px;overflow: hidden;">
		<c:if test="${wincount > 0 }">
			<ul>
			<c:forEach items="${lists }" var="item">
				<c:if test="${item.status > 1 }">
					<li>恭喜<c:out value="${fn:substring(item.userId,0,3)}" />****<c:out value="${fn:substring(item.userId,7,11)}"></c:out>获得<c:if test="${item.level == 1 }">一等奖 Applewatch</c:if><c:if test="${item.level == 2 }">二等奖 车载空气净化器</c:if><c:if test="${item.level == 3 }">三等奖 行车记录仪</c:if></li>
				</c:if>
			</c:forEach>
			</ul>
			<script>
				$(function(){
					$("#scorlldiv").myScroll({
						speed:60, //数值越大，速度越慢
						rowHeight:20 //li的高度
					});
				});
			</script>
		</c:if></div>
	</div>
	<!-- 获奖名单 -->
	<div style="text-indent:3px;width:80%;height:auto;position: absolute;top:73%;left:10%;background-color: white;border-radius:5px;">
		<div>活动规则：</div>
		<div>1、活动对象：5月16日-6月16日交易平台出单用户</div>
		<div>2、抽奖时间：6月17日20:00-6月30日24:00</div>
		<div>3、每人限一次抽奖机会</div>
	</div>
	

	<script type="text/javascript">
	
		$(function(){
			
			$("#closebtn").click(function(){
				
				$('#cd').hide();$('#all').hide();$("#closebtn").hide();
			});
			
			var timeOut = function(){  //超时函数
		        $("#lotteryBtn").rotate({
		            angle:0, 
		            duration: 10000, 
		            animateTo: 2160+56,  //这里是设置请求超时后返回的角度，所以应该还是回到最原始的位置，2160是因为我要让它转6圈，就是360*6得来的
		            callback:function(){
		                alert('网络超时')
		            }
		        }); 
		    }; 
			
			var rotateFunc = function(awards,angle,text,duration){  //awards:奖项，angle:奖项对应的角度，text:提示文字
				var b=duration?duration:5000;   
		        $('#lotteryBtn').stopRotate();
		        $("#lotteryBtn").rotate({
		            angle:0, 
		            duration: b, 
		            animateTo: angle+1440, //angle是图片上各奖项对应的角度，1440是我要让指针旋转4圈。所以最后的结束的角度就是这样子^^
		            callback:function(){
		               $("#confirminfo").html(text);
		               $("#cd").show();
		               $("#all").show();$("#closebtn").show();
		            }
		        }); 
		    };
		    <c:if test="${result > -1}">
				var result = "${result}";
				
				if(result==1){
	                rotateFunc(1,360 - 1440,'<div>恭喜您抽中一等奖!</div><div>兑奖规则请在“我的消息”中查看</div>',10);
	            }else if(result==2){
	                rotateFunc(2,116 - 1440,'<div>恭喜您抽中二等奖!</div><div>兑奖规则请在“我的消息”中查看</div>',10);
	            }else if(result==3){
	                rotateFunc(3,244 - 1440,'<div>恭喜您抽中三等奖!</div><div>兑奖规则请在“我的消息”中查看</div>',10);
	            }
			</c:if>
		<c:if test="${count > 0 && result < 1}">
			$('#cd').show();$('#all').show();$("#closebtn").show();
			$("#lotteryBtn").attr("disabled","disabled");
			var angle = [56 - 1440,181 - 1440,304 - 1440];
            angle = angle[Math.floor(Math.random()*angle.length)];
       		rotateFunc(0,angle,'<div>感谢参与！</div><div>继续出单，还有大奖等着您！</div>',10);
		</c:if>
		    <c:if test="${count ==null || count == 0}">
		   
		    
		    $("#lotteryBtn").rotate({ 
		        bind: 
		          { 
		             click: function(){
		            	 $("#lotteryBtn").attr("disabled","disabled");
		            	 $("#lotteryBtn").unbind("click");
		            	 
		            	 $.post("${ctx}activityb/luckdraw.do",{},function(data){
		            		 //var data = [1,2,3,0]; //返回的数组
		                    // data = data[Math.floor(Math.random()*data.length)];
		                     if(data==1){
		                         rotateFunc(1,360,'<div>恭喜您抽中一等奖!</div><div>兑奖规则请在“我的消息”中查看</div>')
		                     }else if(data==2){
		                         rotateFunc(2,116,'<div>恭喜您抽中二等奖!</div><div>兑奖规则请在“我的消息”中查看</div>')
		                     }else if(data==3){
		                         rotateFunc(3,244,'<div>恭喜您抽中三等奖!</div><div>兑奖规则请在“我的消息”中查看</div>')
		                     }else {
		                         var angle = [56,181,304];
		                             angle = angle[Math.floor(Math.random()*angle.length)]
		                         rotateFunc(0,angle,'<div>感谢参与！</div><div>继续出单，还有大奖等着您！</div>')
		                     }
		            	 });
		            	 
		             }
		          } 
		        
		     });
		    </c:if>
			
		});
	</script>
</body>
</html>

