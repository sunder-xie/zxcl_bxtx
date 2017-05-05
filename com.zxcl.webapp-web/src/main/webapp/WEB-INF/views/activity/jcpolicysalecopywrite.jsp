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
<html>
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css"/>
<style type="text/css">
	.content-main{
		background: url(images/activity/bxtx3_02.png) no-repeat center center;
		height:100%;
		width:100%;
		min-height:550px;
		background-size: 300% auto;
		z-index:1;
	}
	.content-top-img{
		background: url(images/activity/bxtx3_01.png) no-repeat center center;
		width:90%;
		text-align: center;
		background-size: 100% auto;
		z-index:2;
		min-height:120px;
	    margin: 0 auto;
	}
	.content-activity{
		margin: 0 auto;
		width:90%;
		min-height:200px;
		border:2px solid #E0A543;
		border-radius: 10px;
		margin-bottom: 5%;
		background: #fff;
	    margin-top: 10%;
        padding-bottom: 3%;
	}
	.content-other-remark{
		margin: 2% auto;
		width:90%;
		height:auto;
		border-radius: 10px;
		margin-bottom: 5%;
	}
	p{
		text-align: left;
	    padding: 0 2% 0 2%;
		text-indent:5%;
	    font-size: 16px;
	}
	p.p1{
		margin: 10% 0 0 0 ;
	}
	.clor-red{
		color:red;
	}
	
	.head-title {
	    font-size: 18px;
	    font-weight: 700;
	    display: block;
	    color: #fff;
	    background: rgb(242, 150, 0);
	    width: 43%;
	    border-radius: 5px;
	    height: 40px;
	    line-height: 40px;
	    position: absolute;
	    margin: -6% 0 0 20%;
	}
	.gree{
		color:green;
		font-weight: 700;
		font-size: 16px;
	}
	.p-gree{
		margin-top: 3%;
	}
</style>
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body>
	<div class="">
		<div class="wrap_top" style="background: #DB4F4F;">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/back.png"></a>
				<a class="logo bjjg">活动规则</a>
				<a class="tel"  href="tel:${sessionScope.tel }img src="${ctx}images/phone.png"></a>	
			</div>
		</div>
	</div>
	
	<div class="content-main"  style="padding-top:10px;"><%--
		<div class="content-activity content-activity-1" align="center">
		<p class="head-title">推荐出单送大礼</p>
		<p class="p1 gree"></p>
			<p>凡通过“保行天下”完成全流程商业险在线出单的（即最后结点为完成在线保费支付），奖励用户本人<span class="clor-red">10元/单</span>，并奖励直接推荐该用户注册的用户<span class="clor-red">20元/单</span>。</p>
			
			<p class="head-title">推荐出单送大礼</p>
			<p class="p1 gree">一重礼:推荐有礼</p>
			<p>活动期间每成功推荐一人注册，即可得2元奖励，奖励金额可累加、无上限。</p>
			<p class="gree p-gree">二重礼：注册有礼</p>
			<p>活动期间每位新注册用户，可得2元奖励。</p>
			<p class="gree p-gree">三重礼：出单有礼</p>
			<p>1、注册成功的用户只要在线全流程完成<span class="clor-red">第一单</span>出单<span class="clor-red">（仅限锦泰财险、太平财险商业险业务）</span>，其推荐人即可得5元奖励。</p>
			<p>2、活动期间，注册用户每在线全流程完成<span class="clor-red">太平财险</span>或<span class="clor-red">锦泰财险</span>一单商业险出单，即可得获得奖励，太平财险奖励<span class="clor-red">40元/单</span>，锦泰财险奖励<span class="clor-red">30元/单</span>。（内勤人员不参与该奖励）。</p>
			 
		</div>--%>
		
		<div class="content-activity" align="center">
			<p class="head-title">&nbsp;&nbsp;&nbsp;&nbsp;活动说明</p>
			<p class="p1">一、参与用户类型：为业务人员账户（即不含内勤账户）</p>
			<p class="">二、参与保险公司：所有全流程上线保险公司</p>
			<p class="">三、活动范围：通过保行天下全流程完成出单的商业险业务</p>
			<p class="">四、活动时间：2016年5月1日至5月15日</p>
			<p class="">五、奖励金额：每件商业险保单出单后，奖励丰厚红包一份。</p>
			<p class="">六、获奖方式：商业险保单出单后，在保行天下系统界面产生一个类似于微信的红包，用户点击红包打开后，显示相应奖励金额，并将金额直接放入用户的保行天下电子钱包（为激活金额），可提现。</p>
			<p class="">七、不清楚的用户，可咨询嘉诚保险对应机构负责人或拨打嘉诚保险客户热线82006100进行咨询。</p>
			<p class="">八、本项活动方案内容最终解释权归嘉诚保险运营部和营销发展部所有。</p>
			
			
			<%--
			<p class="p1">一、奖励兑现方式：</p>
			<p class="">所有奖励将通过保行天下钱包发放给用户，用户在线提现申请，2个工作日内即可到账。保行天下钱包请参看菜单“更多—我的钱包”。</p>
			 
			<p class="p-gree">活动时间：</p>
			<p class=""> 1、活动时间为2016年4月1日到4月8日；</p>--%>
		</div>
		
<!-- 		<div class="content-other-remark" align="center"> -->
<!-- 			<p><span class="clor-red">特别说明：</span>以上活动的报价车辆需为真实车辆，如同一辆车多次报价，只按一次计。</p> -->
<!-- 		</div> -->
	</div>
<script type="text/javascript">
$(function(){
	$('.back').on('click',function(){history.go(-1);});
});
</script>
</body>
</html>