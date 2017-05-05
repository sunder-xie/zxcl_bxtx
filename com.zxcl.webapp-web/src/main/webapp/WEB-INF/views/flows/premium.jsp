<%@page import="java.util.Date"%>
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
<html lang="ch">
<head>
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UT-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/uploadvideomaterial.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<script type="text/javascript" src="${ctx}v3/js/jquery.cookie.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/bxtx_virtual_keyboard_pink_style.css" />
	<script type="text/javascript" src="${ctx }v3/js/html5ImgCompress.min.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/upload_videomaterial.js"></script>
	
	<link href="${ctx }v3/css/pinchzoom.css" rel="stylesheet">
	<script type="text/javascript" src="${ctx }v3/js/e-smart-zoom-jquery.min.js"></script>
	
<style type="text/css">
.tankuang1{
	display:none; 
	POSITION:absolute; 
	top:10%; 
	width:90%; 
	min-height:200px;
	border:1px solid #888; 
	background-color:#edf; 
	margin-left: 5%;
	padding-bottom:50px;
}
.button_sure{
	margin-top: 20px;
	text-align:center;
	
}
.button_sure1{
	font-weight: bolder;
}
.addxiahuaxian{
	text-decoration:underline;
}
#uploadFile_div{
	position: fixed;
	left: 0;
	top: 0;
	right: 0;
	bottom: 0;
	width: 100%;
	height: 100%;
	display: none;
    z-index: 901;
}
#uploadFile_div_iframe{
	left: 0;
	top: 0;
	right: 0;
	bottom: 0;
	width: 100%;
	height: 100%;
	border:0;
}
#uploadFile_btn{
	background: #D64141;
    height: 25px;
    width: 80px;
    color: white;
    border-radius: 5px;
    line-height: 25px;
    float: left;
}
</style>
</head>
<body class="czxx">

	<div id="uploadFile_div">
		<iframe id="uploadFile_div_iframe" src="" data-init="0"></iframe>
	</div>
	<div class="fixedbg"></div>
	<div class="loadding txc">正在核保中...</div>
	<div class="tipsel" style="margin:-93px 0 0 -122px;">
		<div class="tip ictip">自动核保不通过<br />已转人工核保</div>
		<div class="btns txc clear">
			<div class="lt boxSet btn">查看</div>
			<div class="lt boxSet c2">确定</div>
		</div>
	</div>
	<!-- 以上是提示弹框 fixedbg：整个遮罩层，分别和tipsel,loadding搭配使用 -->
	<div class="head"><div class=" lt"></div><p class="title">车主信息</p></div>
	<form class="content" action="${ctx}accounting.do?quotnType=${ quota.quotaType}" id="orderForm">
		<input type="hidden" name="insId" value="${quota.insurance.insId}">
		<input type="hidden" name="orderId" value="${orderId}">
		<input type="hidden" name="quotaId" value="${quota.quotaId}">
		<input type="hidden" name="inquiry.inquiryId" value="${inquiryId}" id="inquiryId">
		<input type="hidden" name="inquiryId" value="${inquiryId}">
		<input type="hidden" name="timestamp" value="<%=new Date().getTime() %>">
		<input type="hidden" name="quotaType" value="${quota.quotaType}" id="quotaType">
		<div class="box">
			<div class="line clear">
				<div class="lt">姓名</div>
				<div class="div">
					<c:choose>
						<c:when test="${not empty ownerName}"><input class="inp" type="text" id="carOwner1" placeholder="请输入真实姓名" required="required" name="ownerName" value="${ownerName}"></c:when>
						<c:when test="${not empty owner}"><input class="inp" type="text" id="carOwner1" placeholder="请输入真实姓名" required="required" name="ownerName" value="${owner.ownerName}"></c:when>
						<c:otherwise><input type="text" class="inp" id="carOwner" placeholder="请输入真实姓名" required="required" name="ownerName" value="${ownerName}"></c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="line clear">
				<div class="lt">证件类型</div>
				<div class="div">
					<!-- <input type="hidden" value="1" id="ownerCertType"  name="ownerCertType"> -->
					<div class="inpdiv">
						<select id="ownerCertType" name="ownerCertType"></select>
					</div>
				</div>
			</div>
			<div class="line clear">
				<div class="lt">证件号码</div>
				<div class="div">
					<c:choose>
						<c:when test="${not empty ownerCertNo}"><input virtual_keyboard="idcard" class="inp" maxlength="18" type="text" id="cardNumber1" placeholder="请输入真实证件号码" required=""  name="ownerCertNo" value="${ownerCertNo}"></c:when>
						<c:when test="${not empty owner}"><input virtual_keyboard="idcard" class="inp" maxlength="18" type="text" id="cardNumber1" placeholder="请输入真实证件号码" required=""  name="ownerCertNo" value="${owner.ownerCertNo}"></c:when>
						<c:otherwise><input virtual_keyboard="idcard"  maxlength="18"  class="inp"  type="text" id="cardNumber1" placeholder="请输入真实证件号码" required=""  name="ownerCertNo" value="${ownerCertNo}"></c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="line clear">
				<div class="lt">手机号</div>
				<div class="div"><input virtual_keyboard="number"  maxlength="11"  class="inp" type="tel" id="phone_number1" placeholder="请输入真实手机号" required="" name="ownerPhone" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$"  value="${owner.ownerPhone }"></div>
			</div>
			<div class="line clear">
				<div class="lt">省份</div>
				<div class="div">
					<select class="inp" required id="ownerProvince" name="ownerProvince" onchange="getCityInfo('ownerProvince','ownerCity');">
							<c:forEach items="${provinces}" var="pro">
								<c:choose>
									<c:when test="${not empty areaProvince && areaProvince.code eq pro.code}">
										<option selected="selected" value="${pro.code}">${pro.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${pro.code}">${pro.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>	
					</select>
				</div>
			</div>
			<div class="line clear">
				<div class="lt">城市</div>
				<div class="div">
					<c:if test="${not empty areaCitys}">
						<select class="inp" id="ownerCity" name="ownerCity">
							<c:forEach items="${areaCitys}" var="c">
								<c:choose>
									<c:when test="${not empty owmerCityCode && owmerCityCode eq c.code}">
										<option selected="selected" value="${c.code}">${c.name}</option>
									</c:when>
									<c:otherwise>
										<option  value="${c.code}">${c.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>	
						</select>
					</c:if>
					<c:if test="${empty areaCitys}">
						<select class="inp" id="ownerCity" name="ownerCity" disabled="disabled"></select>
					</c:if>	
				</div>
			</div>
			<div class="line clear">
				<div class="lt">详细地址</div>
				<div class="div"><input class="inp"  id="detailed_address1" required="" name="ownerAddress" value="${owner.ownerAddress }" type="text" placeholder="区、县、街道" /></div>
			</div>
			<div class="line clear">
				<div class="lt c2">被保人信息</div>
				<div class="rt"><input type="checkbox" checked="true" class="ck1 input_ui input_ui_anim" id="js_recognizee" onclick="isOwner('js_recognizee','rec');">同车主</div>
				<div class="box" style="margin-top:20px;display: none;" id="js_recognizee_info">
					<div class="line clear">
						<div class="lt">姓名</div>
						<div class="div">
							<input   class="inp" type="text" id="car_owner2" placeholder="请输入真实姓名" required="" name="recName" value="${rec.recName}">
						</div>
					</div>
					<div class="line clear">
						<div class="lt">证件类型</div>
						<div class="div">
							<select id="recCertType" name="recCertType"></select>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">证件号码</div>
						<div class="div">
							<input virtual_keyboard="idcard"  maxlength="18" class="inp" type="text" id="cardNumber2" placeholder="请输入真实证件号码" required="" name="recCertNo" value="${rec.recCertNo}">
						</div>
					</div>
					<div class="line clear">
						<div class="lt">手机号</div>
						<div class="div">
						<input virtual_keyboard="number"  maxlength="11" class="inp" type="tel" id="phone_number2" placeholder="请输入真实手机号" required="" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$" name="recPhone" value="${rec.recPhone}"></div>
					</div>
					<div class="line clear">
						<div class="lt">省份</div>
						<div class="div">
							<select class="inp"  required id="recProvince" name="recProvince" onchange="getCityInfo('recProvince','recCity');">
								<c:forEach items="${provinces}" var="pro">
									<c:choose>
										<c:when test="${not empty areaProvince && areaProvince.code eq pro.code}">
											<option selected="selected" value="${pro.code}">${pro.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${pro.code}">${pro.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>	
						</select>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">城市</div>
						<div class="div">
							<c:if test="${not empty areaCitys}">
								<select class="inp" id="recCity" name="recCity">
									<c:forEach items="${areaCitys}" var="c">
										<c:choose>
											<c:when test="${not empty owmerCityCode && owmerCityCode eq c.code}">
												<option selected="selected" value="${c.code}">${c.name}</option>
											</c:when>
											<c:otherwise>
												<option  value="${c.code}">${c.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>	
								</select>
							</c:if>
							<c:if test="${empty areaCitys}">
								<select class="inp" id="recCity" name="recCity" disabled="disabled"></select>
							</c:if>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">详细地址</div>
						<div class="div"><input class="inp" type="text" id="detailed_address2" placeholder="区、县、街道"  required="" name="recAddress"  value="${rec.recAddress}" /></div>
					</div>
				</div>
			</div>
			<div class="line clear">
				<div class="lt c2">投保人信息</div>
				<div class="rt">	<input type="checkbox" checked="true" class="ck1 input_ui input_ui_anim" id="js_applicant" onclick="isOwner('js_applicant','vote');">同车主</div>
				<div class="box" style="margin-top:20px;display: none;" id="js_applicant_info">
					<div class="line clear">
						<div class="lt">姓名</div>
						<div class="div">
							<input class="inp" type="text" id="car_owner3" placeholder="请输入真实姓名" required="" name="voteName" value="${vote.voteName}">
						</div>
					</div>
					<div class="line clear" >
						<div class="lt">证件类型</div>
						<div class="div">
							<select id="voteCertType" name="voteCertType"></select>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">证件号码</div>
						<div class="div">
							<input  virtual_keyboard="idcard"  maxlength="18" class="inp" type="text" id="cardNumber3"  placeholder="请输入真实证件号码" required="ture" name="voteCertNo"  value="${vote.voteCertNo}">
						</div>
					</div>
					<div class="line clear">
						<div class="lt">手机号</div>
						<div class="div">
						<input virtual_keyboard="number"  maxlength="11" class="inp" type="tel" id="phone_number3" placeholder="请输入真实手机号" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$" required="" name="votePhone"  value="${vote.votePhone}"></div>
					</div>
					<div class="line clear">
						<div class="lt">省份</div>
						<div class="div">
							<select class="inp" required id="voteProvince" name="voteProvince" onchange="getCityInfo('voteProvince','voteCity');">
								<c:forEach items="${provinces}" var="pro">
									<c:choose>
										<c:when test="${not empty areaProvince && areaProvince.code eq pro.code}">
											<option selected="selected" value="${pro.code}">${pro.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${pro.code}">${pro.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">城市</div>
						<div class="div">
							<c:if test="${not empty areaCitys}">
								<select class="inp" id="voteCity" name="voteCity">
									<c:forEach items="${areaCitys}" var="c">
										<c:choose>
											<c:when test="${not empty owmerCityCode && owmerCityCode eq c.code}">
												<option selected="selected" value="${c.code}">${c.name}</option>
											</c:when>
											<c:otherwise>
												<option  value="${c.code}">${c.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>	
								</select>
							</c:if>
							<c:if test="${empty areaCitys}">
								<select class="inp" id="voteCity" name="voteCity" disabled="disabled"></select>
							</c:if>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">详细地址</div>
						<div class="div"><input class="inp" type="text" id="detailed_address3" placeholder="区、县、街道"  required="" name="voteAddress"  value="${vote.voteAddress}"></div>
					</div>
				</div>
			</div>
			<div class="line clear waybtn">
				<div class="lt c2">保单配送</div>
				<div class="rt"><c:if test="${disPanDuan eq '1' }"><input class="ck1" type="radio"  onclick="isDispatchType(this.value)" name="dispatchType"  value="0"  />配送</c:if><input onclick="isDispatchType(this.value)" checked="checked" class="ck1" type="radio"  name="dispatchType"  value="1"  style="margin-left:40px;" />自取
				</div>
			</div>
			<div class="line clear ways" style="padding-right:0px;padding-top:5px;padding-bottom:5px;">
				<div class="lt">是否同车主</div>
				<div class="rt"><input type="checkbox" checked="checked" class="ck1 input_ui input_ui_anim" id="js_insurance" onclick="isOwner('js_insurance','dis');" />同车主</div>
			</div>
			<div class="ways">
				<div class="line clear">
					<div class="lt">常用配送</div>
					<div class="div"><select class="inp" id="manage_dispatch_select" onchange="manage_dispatch_select_change();">
						</select></div>
				</div>
				<div class="line clear">
					<div class="lt">姓名</div>
					<div class="div"><input class="inp" type="text" id="name" placeholder="请输入姓名" required="" name="disName" value="${dis.disName}" /></div>
				</div>
				<div class="line clear">
					<div class="lt">手机号</div>
					<div class="div"><input  virtual_keyboard="number"  maxlength="11" class="inp"  type="text" id="phoneNum" placeholder="请输入联系电话" required="" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$" name="disPhone" value="${dis.disPhone}" /></div>
				</div>
				<div class="line clear">
					<div class="lt">省份</div>
					<div class="div">
						<select class="inp" required id="disProvince" name="disProvince" onchange="getCityInfo('disProvince','disCity');">
							<c:forEach items="${provinces}" var="pro">
								<c:choose>
									<c:when test="${not empty areaProvince && areaProvince.code eq pro.code}">
										<option selected="selected" value="${pro.code}">${pro.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${pro.code}">${pro.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>	
						</select>
					</div>
				</div>
				<div class="line clear">
					<div class="lt">城市</div>
					<div class="div">
						<c:if test="${not empty areaCitys}">
							<select class="inp" id="disCity" name="disCity">
								<c:forEach items="${areaCitys}" var="c">
									<c:choose>
										<c:when test="${not empty owmerCityCode && owmerCityCode eq c.code}">
											<option selected="selected" value="${c.code}">${c.name}</option>
										</c:when>
										<c:otherwise>
											<option  value="${c.code}">${c.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>	
							</select>
						</c:if>
						<c:if test="${empty areaCitys}">
							<select class="inp" id="disCity" name="disCity" disabled="disabled"></select>
						</c:if>
					</div>
				</div>
				<div class="line clear">
					<div class="lt">详细地址</div>
					<div class="div"><input class="inp"  type="text" id="cardNumber" placeholder="区、县、街道"  required="" name="disAddress" value="${dis.disAddress}"></div>
				</div>
			</div>
			<div class="line clear">
				<div class="lt">
					<input type="button" id="uploadFile_btn" value="上传附件"  />
				</div>
			</div>
		</div>
		<div class="subbtn" style="width:40%;float:left;padding:0px 10px 10px 10px;"><input class="submit" style="background:#D64141;" type="button" value="查看报价"  onclick="window.location.href= '${ctx}confirmQuotaInfo.do' + window.location.search"/></div>
		<div class="subbtn" style="width:40%;float:right;padding:0px 10px 10px 10px;"><input class="submit" id="submit1" type="button" value="提交核保"/></div>
		<div class="clear"></div>
		<jsp:include page="../commons/menu.jsp" />
	</form>
	
	<script type="text/javascript" src="${ctx }v3/js/premium_artificial.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
		var certfCdeType = '${inquiry.certfCdeType}';
		var quotnType = '${quota.quotaType}';
		var inquiryId = '${inquiryId}';
		var insId = '${quota.insurance.insId}';
		var quotaId= '${quota.quotaId}';
		var flowControl = '${flowControl}';
		var plate_no_control = '${plate_no_control}';
		var plate1 = '${plate1}';
		var plate2 = '${plate2}';
		var sign = '${sign}';
		var ownerName="${owner.ownerName}";
		var ownerCertType="${owner.ownerCertType}";
		var ownerCertNo="${owner.ownerCertNo}";
		var ownerPhone="${owner.ownerPhone}";
		var ownerProvince="${owner.ownerProvince}";
		var ownerCity="${owner.ownerCity}";
		var ownerAddress="${owner.ownerAddress}";
		var recName = "${rec.recName}";
		var recCertType = "${rec.recCertType}";
		var recCertNo = "${rec.recCertNo}";
		var recPhone = "${rec.recPhone}";
		var recAddress = "${rec.recAddress}";
		var recProvince = "${rec.recProvince}";
		var recCity = "${rec.recCity}";
		var voteName = "${vote.voteName}";
		var voteCertType = "${vote.voteCertType}";
		var voteCertNo = "${vote.voteCertNo}";
		var votePhone = "${vote.votePhone}";
		var voteAddress = "${vote.voteAddress}";
		var voteProvince = "${vote.voteProvince}";
		var voteCity = "${vote.voteCity}";
		var disName = "${dis.disName}";
		var disPhone = "${dis.disPhone}";
		var disAddress = "${dis.disAddress}";
		var disProvince = "${dis.disProvince}";
		var disCity = "${dis.disCity}";
		var disPanDuan = '${disPanDuan}';
	</script>
<script type="text/javascript" src="${ctx }v3/js/permium_ui.js"></script>
<script type="text/javascript" src="${ctx }v3/js/bxtx_virtual_keyboard.js"></script>
</body>
</html>