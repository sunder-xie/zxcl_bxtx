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
<link rel="stylesheet" type="text/css" href="${ctx}css/slidestyles.css" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<style>
.input_ui {
	margin-left: -3% !important;
	margin-top: -3% !important;
}
.djstxt	{
	position: absolute;
    top: 7%;
    left: 5%;
    color: white;
}
.djssjdiv{
	 padding-top: 5%;
    position: absolute;
    top: 10%;
    text-align: center;
    width: 100%;
}
.djssjdiv div{
	font-weight: bold;
    display: inline-block;
    width: 2.5rem;
    height: 2.5rem;
    line-height: 2.5rem;
    color: white;
    border: 2px solid;
    border-radius: 5px;
    text-align: center;
}


@keyframes scale{0%,100%{transform:scale(1.1);}50%{transform:scale(1.3);}}
@keyframes scale2{
0%{transform:scale(1.1);}
50%{transform:scale(5);opacity:0;}
100%{transform:scale(1.1);opacity:0;}
}
@-moz-keyframes  scale {
	0%,100%{-moz-transform:scale(1.1);}50%{-moz-transform:scale(1.3);}
}

@-webkit-keyframes  scale {
	0%,100%{-webkit-transform:scale(1.1);}50%{-webkit-transform:scale(1.3);}
}

@-o-keyframes  scale {
	0%,100%{-o-transform:scale(1.1);}50%{-o-transform:scale(1.3);}
}

@keyframes scale2{
0%{transform:scale(1.1);}
50%{transform:scale(5);opacity:0;}
100%{transform:scale(5);opacity:0;}
}
@-moz-keyframes  scale2 {
	
0%{-moz-transform:scale(1.1);}
50%{-moz-transform:scale(5);opacity:0;}
100%{-moz-transform:scale(5);opacity:0;}
}

@-webkit-keyframes  scale2 {
	
0%{-webkit-transform:scale(1.1);}
50%{-webkit-transform:scale(5);opacity:0;}
100%{-webkit-transform:scale(5);opacity:0;}
}

@-o-keyframes  scale2 {
	
0%{-o-transform:scale(1.1);}
50%{-o-transform:scale(5);opacity:0;}
100%{-o-transform:scale(5);opacity:0;}
}

.xiu_pay{
width:80px;
height:80px;
background:#DC3B58;
border-radius:50%;
margin:0 auto;
position:relative;
transition:all .5s;
-moz-transition: all .5s; /* Firefox 4 */
-webkit-transition: all .5s; /* Safari 和 Chrome */
-o-transition: all .5s; /* Opera */
position: absolute;
top: 26%;
    left: 39%;
}
.xiu_pay:before{
position:absolute;
content:'';
width:100%;
height:100%;
box-shadow:inset 0 0 5px 1px #ECBAD3;
border-radius:50%;
transform:scale(1.1);
-ms-transform:scale(1.1);
-moz-transform:scale(1.1);
-webkit-transform:scale(1.1);
-o-transform:scale(1.1);
border:1px solid #EFB0CD;
top:-1px;
left:-1px;
-webkit-animation: scale 2s infinite ;
    -moz-animation: scale 2s infinite ;
    -ms-animation: scale 2s infinite ;
animation:scale 2s infinite;
}
.xiu_pay.scale:before{
position:absolute;
content:'';
width:100%;
height:100%;
box-shadow:inset 0 0 5px 1px #ECBAD3;
border-radius:50%;
transform:scale(1.1);
-ms-transform:scale(1.1);
-moz-transform:scale(1.1);
-webkit-transform:scale(1.1);
-o-transform:scale(1.1);
border:1px solid #EFB0CD;
top:-1px;
left:-1px;
-webkit-animation: scale2 3s ;
    -moz-animation: scale2 3s ;
    -ms-animation: scale2 3s ;
animation:scale2 3s ;
}
.xiu_txt{
margin:50px 0 30px 0;
height:30px;
text-align: center;
color: #EAB387;
position: absolute;
    top: 13%;
    width: 100%;
    text-align: center;
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
					src="${ctx }images/back.png" /></a> <a class="logo bjjg">双11活动</a> <a
					class="tel" href="tel:"><img src="${ctx }images/phone.png" /></a>
			</div>
		</div>
	</div>
	<img src="${ctx }activity/bxtxc/psd0713cdoq_2.jpg" />
	<div class="djstxt">红包开抢倒计时：</div>
	<div class="djssjdiv">
		<div id="hour_show"></div>
		<div style="border:none;">：</div>
		<div id="minute_show"></div>
		<div style="border:none;">：</div>
		<div id="second_show"></div>
	</div>
	
	<div class='xiu_txt'></div><div class='xiu_pay' id='xiu_pay'></div>
	
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
		intDiff--;
		}, 1000);
	} 

	$(function(){
		timer(intDiff);
	});	

	</script>
	<script>
var go=true;
$('#xiu_pay').click(function(){
       if(go){
	var arr=['咻咻大神，请赐予我力量吧','世界那么大，我想咻一咻','世界那么大，我想咻一咻','憋说话，咻我','一起扭着咻一咻！扭着！扭着！','咻咻咻咻，鸡鸭鱼肉','福来了醉，福来了喜天降','','别急，深呼吸三秒，给运气充个值','左手再来一个慢动作，咻着','重要的事说三遍：咻我，咻我，咻我','换个姿势继续咻一咻','红包哪里逃','像咻街机一样咻你手机','让我们红尘作伴，咻得潇潇洒洒','咻得太腻害了，快快收下我的膝盖','你不是一个人在咻嘛','这个咻姿势我给满分','耶耶切克闹，福气红包来一套','五魁首，六六六，咻个福气一起走','有钱有福，猴嗨森','满园春色关不住，福气红包咻出来','艾玛，红包咋还不来呢','世界那么大，我想咻一咻','福来了美，福来了浪啊~','咻咻咻咻，福气满堂','福来了肥，福来了烫啊','感情深一口气，哥俩好分福气','大王派我来拜年嘞，咻咻咻咻'];
	var length=arr.length;
	var txt=arr[Math.floor(Math.random()*length+1)];
	$('.xiu_txt').html('   正在寻宝中...');
	$(this).addClass('scale').css({'transform':'scale(1)','-moz-transform':'scale(1)','-webkit-transform':'scale(1)','-o-transform':'scale(1)'});
	setTimeout(function(){
		$('.xiu_pay').css({'transform':'scale(1)','-moz-transform':'scale(1)','-webkit-transform':'scale(1)','-o-transform':'scale(1)'});
	},500);
	setTimeout(function(){
                go=true;
		$('.xiu_pay').removeClass('scale');
		$('.xiu_txt').html(txt);
                if($('.xiu_txt').html()!='   正在寻宝中...'){
                setTimeout(function(){$('.xiu_txt').html('');},1000);}
		if(txt==""){
			alert('你中大奖了')
		}		
	},1200);
      go=false;
    }
})</script>
</body>
</html>

