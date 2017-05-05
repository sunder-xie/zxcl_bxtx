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
<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%><!DOCTYPE html>
<html lang="en">
<head>
<title>免责申明</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon"
	href="/bxtx/images/favicon.ico" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="full-screen" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
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
	line-height:25px;
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
<body>
	<div class="head lrhd userhd">
		<div class="back lt" onclick="history.back();"></div>
		<div class="rt txr"></div>
		<p class="title">免责申明</p>
	</div>
	<div class="clear"></div>
	<div class="titlea">保行天下网络服务协议</div>
	<div class="dlnr clear">一、服务条款的确认和接受</div>
	<div class="sjnr clear">用户（以下简称：您）使用四川智迅车联科技股份有限公司（以下称“智迅车联”）的移动端产品“保行天下”（以下称“保行天下”）通过注册阅读《保行天下网络服务协议》（以下简称“本协议”）并选中“我已同意保行天下网络服务协议”，即表示您与智迅车联已达成协议，自愿接受本协议条款的所有内容。保行天下的各项网络服务的所有权和运营权归智迅车联所有。</div>
	<div class="dlnr clear">二、特别提醒</div>
	<div class="sjnr clear">
			1.为了保障您的权益，在注册成为保行天下的会员或使用、接受保行天下的服务之前，请您充分阅读、理解并接受本协议的全部内容，并同意遵循协议的所有约定；
			<br/>
			2.在使用过程中，保行天下可能会对本协议内容进行必要变更，为了避免打扰，保行天下不再向您单独通知；
			<br/>
            3.在本协议内容公告变更后，您在继续使用、接受保行天下的服务前请充分阅读、理解并接受修改后的协议内容，并同意遵循修改后的协议约定；若您不同意修改后的协议内容，请您与保行天下及时联系或暂停使用保行天下的服务。
			<br/>
			</div>
	
	<div class="dlnr clear">三、使用规则</div>
	<div class="sjnr clear">
			1.您是具备完全民事权利能力和完全民事行为能力、且能够独立承担民事责任的自然人，法人或其他组织。<br/>
			2.您在申请使用本服务时，应同意:<br>
			  &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(1)向保行天下提供真实、即时、完整的注册信息及相应的证件资料；<br>
              &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(2)在您使用保行天下服务时，您同意保行天下搜集您的相关车辆等信息以便为您提供更好的使用体验。保行天下搜集的信息包括但不限于车辆的车牌号、车架号、发动机号、品牌型号、车主姓名、注册登记日期、个人资料信息等；您对此没有异议且同意使用该车辆的相关信息进行后续算价服务。您均已了解收集、使用其信息的目的、方式和范围，并同意保行天下取得和使用其信息进行车险算价相关服务。 <br>
              &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(3)若您所提供的信息有任何错误、不实或不完整，保行天下保留结束您使用保行天下各项服务的权利。由上述原因导致服务无法正常提供，并由此导致的任何直接或间接损失由您自行承担。  <br>
              &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(4)因通过您提供的电子邮件地址、手机或座机号码等联系方式无法及时与您取得联系，导致您在使用保行天下服务过程中产生任何损失或增加费用的，亦由您自行承担。 <br>
              &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(5)接受并同意保行天下向您以各种方式（包括但不限于电子邮件、手机短信等）推送与保行天下所提供服务相关的信息。<br>
			
		    3.请您妥善保管用户名和密码。若因密码泄露造成的损失由您本人承担责任。<br />
			
		    4.您有义务遵守法律法规、政策、道德、保行天下各项规则以及智迅车联相关业务规则等，不将保行天下用于任何非法目的，也不以任何非法方式使用保行天下所提供的服务。
			
			<br />您必须遵守以下原则：<br />
			
			 &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(1)遵守中国有关法律和法规；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(2)遵守所有使用网络服务的网络协议、规定、程序和惯例；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(3)不得为任何非法目的而使用网络服务系统；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(4)不得传输任何骚扰性的、中伤他人的、辱骂性的、恐吓性的、庸俗的、淫秽的、教唆他人实施犯罪行为等非法的信息资料；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(5)不得侵犯其他任何第三方的专利权、著作权、商标权、名誉权或其他任何合法权益；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(6)不得利用保行天下网络服务系统进行任何不利于智迅车联的行为；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(7)不干扰或混乱网络服务；<br>
             &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;(8)未经许可不得非法进入进入他电脑系统。
	<div class="dlnr clear">四、知识产权声明</div>
	<div class="sjnr clear">
			1.保行天下上所有可涉及的资料等都受到《中华人民共和国著作权法》等的法律保护。<br />
			
		    2.智迅车联拥有保行天下及其相关信息、内容的全部合法权利，包括但不限于图标、界面设计、有关数据、电子文档等的知识产权。任何组织或个人未经保行天下书面许可，不得修改、出版、传播、参与传播、销售、制作衍生作品，或以其他任何方式使用整体或部分的保行天下上的内容。<br />
			
			3.保行天下对其开发或者合作开发的系统等享有知识产权。保行天下的所有权利受法律保护。智迅车联有权在合理范围内对保行天下及其功能和服务进行维护、改动、升级。
			</div>
	
	<div class="dlnr clear">五、法律责任及免责</div>
	<div class="sjnr clear">
			1.您同意保障和维护保行天下及其他用户的利益，如因您违反有关法律、法规或本服务条款的任何条款而给智迅车联或任何其他第三人造成损失，您同意承担由此造成的损害赔偿责任。<br />
			
			2.保行天下上公布的或其服务中的建议和资讯仅仅给您提供参考，您应当独立判断做出相关决定，您承诺并同意承担因此造成的任何后果。<br />
			
			3.使用保行天下时可能产生的风险需由您自行承担，保行天下对您不作任何类型的担保，也不承担任何责任。对于因不可抗力或保行天下不能避免或控制的原因造成的网络服务中断或其他缺陷，智迅车联不承担任何责任，但将尽力减少因此而给您造成的损失和影响。
			</div>
	
	
	<div class="dlnr clear">六、法律适用及管辖</div>
	<div class="sjnr clear">
			 本服务协议的订立、执行和解释及争议的解决均应适用中国法律并受中国法院管辖。</div>
	
	<div style="width:100%;text-align:center;margin-top:10px;" class="clear">Copyright 2015 四川智迅车联科技股份有限公司</div>
	
</body>
</html>