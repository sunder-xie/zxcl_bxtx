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
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<script type="text/javascript" src="${ctx}v3/js/jquery.cookie.js"></script>
<style>
.titlea{
	width:100%;
	font-size:18px;
	text-align:center;
	line-height:25px;
	margin-top:45px;
}
.dlnr{
	font-size:14px;
	font-weight: 700;
	margin-top:10px;
	line-height:18px;
	letter-spacing:2px;
	color:#0E0D0D;
}
.clear {
	clear:both;
}
.sjnr{
	font-size:12px;
	letter-spacing:1px;
	color:#333;
}
</style>
</head>
<body class="chaxun zfcx">
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">关于我们</p></div>
	<div style="margin-top:45px;margin-left: 5px;">
		<div class="dlnr clear">1、公司简介</div>
		<div class="sjnr clear">
			 &nbsp&nbsp&nbsp&nbsp“保行天下”--小微汽车保险交易服务平台由四川智迅车联科技股份有限公司开发。<br>
			 &nbsp&nbsp&nbsp&nbsp“保行天下”初步规划分两步实现：
			 第一步：致力成为行内最受欢迎的小微保险经纪服务平台，服务于各大保险公司、保险经纪、保险代理公司、汽修店、洗车店等，实现实时精准报价，网上结算，方便机构及业务员移动展业。<br>
			 第二步：完备网上理赔等服务，合作推出保险定制服务等其他功能，成为服务大众的好汽车金融平台。经过团队通力协作，已实现多家保险公司的正式上线生产，现阶段平安、太平洋、华泰、阳光、中华联合、永诚、富德等保险公司正在陆续接入过程中。四川嘉诚保险、德圣经纪、壹诚经纪、蓝色经典汽修等也已通过“保行天下”成功实现在线展业、销售，海南及河北等地保险代理机构也先后签约。<br>
      		&nbsp&nbsp&nbsp&nbsp 协助小微为提供客户独有的私享的个性化服务，并将最终实现网上轻松快捷购买到理赔等优质服务，给个人及企业用户提供便利专业的保险交易服务平台是我们的唯一宗旨。营造一个让平台上所有参与者同生共长共赢的生态圈是我们不变的目标。
		</div>
		<div class="dlnr clear">2、用保行天下有啥好处？</div>
		<div class="sjnr clear">
			&nbsp&nbsp&nbsp&nbsp安全保障：保费支付进入对应保险公司账户；<br>
			&nbsp&nbsp&nbsp&nbsp随时随地：全天候7*24小时；<br>
			&nbsp&nbsp&nbsp&nbsp方便快捷：精准报价；自动核保；网上支付；<br>
			&nbsp&nbsp&nbsp&nbsp理赔便利：专门公估专家理赔指导。
		</div>
		<div class="dlnr clear">3、团队介绍</div>
		<div class="sjnr clear">
			&nbsp&nbsp&nbsp&nbsp“保行天下”团队是一支即懂保险又熟悉互联网的团队。由资深的互联网行业及金融行业人士组成的核心团队，来自于北大、中大等国内院校专家，BAT等互联网从业人员，在保险机构从业三十年以上的领袖人才、有平安财险等保险公司的精英；海归精算师；平安系统开发人员，理赔专家等组成。
		</div>
		<div class="dlnr clear">4、历程</div>
		<div class="sjnr clear">
			<img data-s="300,640" data-type="png" src="${ctx}images/about/1.png" data-ratio="0.2697841726618705" data-w="" style="width: 100%; height: auto;"  />
		</div>
		<div class="dlnr clear">5、对接中</div>
		<div class="sjnr clear">
			<img data-s="300,640" data-type="png" src="${ctx}images/about/2.png" data-ratio="0.2697841726618705" data-w="" style="width: 100%; height: auto;"  />
		</div>
	</div>
</body>
</html>