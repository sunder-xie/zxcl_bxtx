<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/bxtx_virtual_keyboard_pink_style.css" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/bxtx.css" />
<script type="text/javascript" src="${ctx }v3/js/bxtx_virtual_keyboard.js"></script>
<script type="text/javascript">
	$(function(){
		$('.xzsel .rt').click(function(){
			var tid=$(this).attr('id');
			if(tid=='down'){$(this).attr('id','up');}else{$(this).attr('id','down');}
		});
	});
</script>
<style type="text/css">
.bxbj .clhcz {
    line-height: 30px;
}
#delIsResourceVehicleDTO{
	/* margin-left: 10%; */
    height: 22px;
    line-height: 22px;
    display: inline-block;
    color: #FFF;
    padding: 2px;
    border-radius: 5px;
}
.bxbj .part .clxx .search {
    border-radius: 5px;
}
</style>
<style type="text/css">
.indd{
	margin:0 0 2% 0;
}
#Filedata{
   position: fixed;
   left: 127.5px;
   top: 147px;
   font-size: 100px;
   opacity: 0;
   filter: alpha(opacity=0);
}
div.info{
	display:none !important;
}
.btns2{
	display:none;
	margin: 0 0 0 30%;
}
#uploader .filelist li img{
  	height: 100%;
    width: 90%;
    border-radius: 5px;
}
#uploader .statusBar .btns{
    padding-left: 16%;
}
body, select, input, textarea, a {
    font-family: "Helvetica Neue",Helvetica,"Hiragino Sans GB","Microsoft YaHei",Arial,sans-serif;
    font-size: 15px;
}
body{}
#uimg{
    padding-bottom: 5%;
}
#uploader .statusBar .btns .uploadBtn{
	background: #ffffff;
    border: 1px solid #cfcfcf;
    color: #565656;
    padding: 0 18px;
    display: inline-block;
    border-radius: 3px;
    margin-left: 10px;
    cursor: pointer;
    font-size: 14px;
}
#XY{
    overflow: scroll;
    padding-left: 5px;
    padding-right: 5px;
}
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
input[type=file]{
    opacity: 0;
}
#filePicker{
    display: block;
    background: #d64141;
    color: white;
    margin: 0 3%;
    border-radius: 5px;
    height: 45px;
    line-height: 45px;
    margin-bottom: -9%;
    font-size: 17px;
}
.imgWrap{
	margin-top: 10%;
}
#ocr_dododo{
    margin-right: 22%;
    color: white !important;
    background: #d64141 !important;
    border-radius: 5px !important;
    font-size: 16px !important;
    font-weight: 500 !important;
    height: 25px !important;
    line-height: 25px !important;
}
#autoFindInsureDateId{
    color: white;
    background: #d64141;
    border-radius: 5px;
    height: 25px;
    line-height: 25px;
    display: inline-block;
    padding: 0 6px;
    margin: 0 0 0 4px;
}
</style>
</head>
<body style="background:#fff;">
	<div class="head"><div class="back lt" id="back1" onclick="javascript:window.location.href='${ctx}index.do?nocheck=1'"></div><!-- <div id="back2" class="back lt"></div> --><p class="title">保险报价</p></div>
	<form class="bxbj content" action="${ctx}comPrice.do" id="comPriceForm" method="post">
			<input type="hidden" name="drivers" value="">
			<input type="hidden" name="items" value="">
<!-- 			车辆基本信息 -->
			<input type="hidden" name="modelCode" value="${inquiry.modelCode}" id="vehicleId">
			<input type="hidden" name="vehiclePrice" value="${inquiry.vehiclePrice}" id="vehiclePrice">
			<input type="hidden" name="seatNum" value="${inquiry.seatNum}" id="seatNum">
			<input type="hidden" name="displacement" value="${inquiry.displacement}" id="displacement">
			<input type="hidden" name="marketDate" value="${inquiry.marketDate}" id="marketDate">
			<input type="hidden" name="modelCodeType" value="${inquiry.modelCodeType}" id="modelCodeType">
			<input type="hidden" name="inquiryId" value="${inquiry.inquiryId}">
			<input type="hidden" name="plateNo" value="${inquiry.plateNo}">
			<input type="hidden" name="cityCode" value="${inquiry.cityCode}">
			<input type="hidden" name="newCarSign" value="${inquiry.newCarSign}">
			<input type="hidden" name="remark" value="${inquiry.remark}" />
			<input type="hidden" name="vehicleName" value="${inquiry.vehicleName}" />
			<input type="hidden" name="panduan" value="${panduan }">
			<input type="hidden" name="isResourceVehicleDTO" id="isResourceVehicleDTO" value="${isResourceVehicleDTO}">
			
<!-- 			险别 -->
			<input type="hidden" name="vciSign" value="${inquiry.vciSign}">
			<input type="hidden" name="tciSign" value="${inquiry.tciSign}">
		<div id='base_info'>
		<div class="div line1 clear">
			<div class="lt c3">城市：${areaDTO.name}</div>
			<b id="show_plateNO" class="rt c3">车牌：${inquiry.plateNo}</b>
		</div>
		<div class="clhcz  part clear ins_main">
			<div class="lt">车辆和车主信息</div>
			<div class="rt c3"><span class="wh">?</span>如何填写</div>
		</div>
		<c:if test="${isResourceVehicleDTO eq '1'}">
			<!-- <span class="bc2" id="delIsResourceVehicleDTO">不是我的车</span> -->
			<div id="delIsResourceVehicleDTO2" style="font-size: 14px;color: #FFA54F;background-color: #F5F5F5;margin-top: -5px;"><p style="margin-left: 10px;">${remark},为保护用户资料，部分信息隐藏，无需重复填写，若核实信息不正确，请点击<span class="bc2" id="delIsResourceVehicleDTO">修改</span></p></div>
		</c:if>
		<div id="ocr_div" style="border-bottom: 10px solid #F5F5F5;height: auto;min-height:60px;padding-top: 15px;">
			<!-- uploader -->
			<div class="line clear" style="border-bottom: 0;">
				<div class="clear indd" style="text-align: center;">
					<div id="uploader">
						<div class="queueList">
							<div id="dndArea" class="placeholder">
								<div id="filePicker" ></div>
							</div>
						</div>
						<div class="statusBar" style="display: none; height: 25px;">
							<div class="progress">
								<span class="text">0%</span> <span class="percentage"></span>
							</div>
							<div class="info"></div>
							<c:if test="${'1' eq canUseVehicleLicenseOcr}">
								<div class="btns btns1">
									<div id="filePicker2"></div>
									<div id="ocr_dododo" class="uploadBtn">开始识别</div>
								</div>							
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<!-- uploader -->
		</div>
		<c:if test="${isResourceVehicleDTO eq '1'}">
			<script type="text/javascript">
				$('#ocr_div').hide();
			</script>
		</c:if>
		<div class="part ins_main" >
			
			<div class="clxx div clear">
				<div class="lt">车&nbsp;&nbsp;架&nbsp;&nbsp;号</div>
				<div class="inpdiv">
					<c:choose>
						<c:when test="${fn:contains(inquiry.frmNo, '*')}">
							<input disabled="disabled" style="text-transform:uppercase;" virtual_keyboard="vin" class="inp boxSet" type="text"  id="carFrame" placeholder="请输入车架号或车辆识别代码" maxlength="17"   required  name="frmNo" value="${inquiry.frmNo }" />
						</c:when>
						<c:otherwise>
							<input style="text-transform:uppercase;" virtual_keyboard="vin" class="inp boxSet" type="text"  id="carFrame" placeholder="请输入车架号或车辆识别代码" maxlength="17"   required  name="frmNo" value="${inquiry.frmNo }" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="clxx div clear">
				<div class="lt">发动机号</div>
				<div class="inpdiv">
					<c:choose>
						<c:when test="${fn:contains(inquiry.engNo, '*')}">
							<input disabled="disabled" style="text-transform:uppercase;" virtual_keyboard="engno"  class="inp boxSet" type="text"  id="carEngine" placeholder="请输入发动机号" required name="engNo" maxlength="20" value="${inquiry.engNo}" />
						</c:when>
						<c:otherwise>
							<input style="text-transform:uppercase;" virtual_keyboard="engno"  class="inp boxSet" type="text"  id="carEngine" placeholder="请输入发动机号" required name="engNo" maxlength="20" value="${inquiry.engNo}" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="clxx div clear">
				<div class="lt">注册日期</div>
				<div class="inpdiv"><input style="height:25px;" placeholder="请选择注册日期" type="date"  value="${inquiry.fstRegNoStr}" class="inp boxSet"  id="initDate"  placeholder="2015-04-05" pattern="^\d{4}[\.|\-|\/][0|1]?\d{1}[\.|\-|\/][0-3]?\d{1}$" required="required" name="fstRegNo" max="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" /></div>
			</div>
			<div class="clxx div clear">
				<div class="lt">业务类型</div>
				<div>
					<select style="margin-left: 20px;" id="businessTypeId" onchange="chanageBusinessType()">
						<c:forEach items="${businessTypeList }" var="businessType">
							<option value="${businessType }" <c:if test="${(not empty usageCodeParCode and usageCodeParCode eq  businessType) or (empty usageCodeParCode and 'A' eq  businessType)}">selected="selected"</c:if>><c:choose><c:when test="${'A' eq  businessType}">非营运</c:when><c:otherwise>营运</c:otherwise></c:choose></option>
						</c:forEach>
					</select>
					<select style="margin-left: 30px;" name="usageCode" id="businessTypeSanId" onchange="changeBusinessTypeSan()"></select>
				</div>
			</div>
			<c:choose>
				<c:when test="${isResourceVehicleDTO eq '1'}">
					<c:choose>
						<c:when test="${empty inquiry.ownerName}">
							<div class="clxx div clear" id="pre_carName">
								<div class="lt">车主姓名</div>
								<c:choose>
									<c:when test="${not empty ownerName }">
										<div class="inpdiv"><input class="inp boxSet" type="text"  id="carName" placeholder="请输入车主姓名" required name="ownerName" value="${ownerName }" /></div>
									</c:when>
									<c:otherwise>
										<div class="inpdiv"><input class="inp boxSet" type="text"  id="carName" placeholder="请输入车主姓名" required name="ownerName" value="${inquiry.ownerName }" /></div>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:otherwise>
							<div class="clxx div clear" style="display: none;" id="pre_carName">
								<div class="lt">车主姓名</div>
								<div class="inpdiv"><input class="inp boxSet" type="text"  id="carName" placeholder="请输入车主姓名" required name="ownerName"  value="${inquiry.ownerName }" /></div>
							</div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${empty inquiry.ownerCertNo}">
							<div class="clxx div clear" id="pre_certfCdeType">
								<div class="lt">证件类型</div>
								<div class="inpdiv">
									<select name="certfCdeType"></select>
								</div>
							</div>
							<div class="clxx div clear" id="pre_cardNumber">
								<div class="lt">身份证号</div>
								<div class="inpdiv"><input  virtual_keyboard="idcard" maxlength="18"   style="text-transform:uppercase;" class="inp boxSet" type="text"  id="cardNumber" placeholder="请输入证件号" required name="ownerCertNo"  value="${inquiry.ownerCertNo }"/></div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="clxx div clear"  style="display: none;" id="pre_certfCdeType">
								<div class="lt">证件类型</div>
								<div class="inpdiv">
									<select name="certfCdeType"></select>
								</div>
							</div>
							<div class="clxx div clear" style="display: none;" id="pre_cardNumber">
								<div class="lt">身份证号</div>
								<div class="inpdiv"><input  virtual_keyboard="idcard"  maxlength="18"   style="text-transform:uppercase;" class="inp boxSet" type="text"  id="cardNumber" placeholder="请输入证件号" required name="ownerCertNo"  value="${inquiry.ownerCertNo }"/></div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="clxx div clear">
						<div class="lt">车主姓名</div>
						<c:choose>
							<c:when test="${not empty ownerName }">
								<div class="inpdiv"><input class="inp boxSet" type="text"  id="carName" placeholder="请输入车主姓名" required name="ownerName" value="${ownerName }" /></div>
							</c:when>
							<c:otherwise>
								<div class="inpdiv"><input class="inp boxSet" type="text"  id="carName" placeholder="请输入车主姓名" required name="ownerName" value="${inquiry.ownerName }" /></div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="clxx div clear">
						<div class="lt">证件类型</div>
						<div class="inpdiv">
							<select name="certfCdeType"></select>
						</div>
					</div>
					<div class="clxx div clear">
						<div class="lt">证件号</div>
						<div class="inpdiv"><input maxlength="18" virtual_keyboard="idcard"  style="text-transform:uppercase;" class="inp boxSet" type="text"  id="cardNumber" placeholder="请输入证件号" required name="ownerCertNo" value="${inquiry.ownerCertNo}"/></div>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="clxx div clear">
				<div class="lt">被保人信息</div>
				<div class="rt"><input class="ck1 ckb" type="checkbox" checked="checked" name="insureSameAsOwner" id="insureSameAsOwner" />同车主</div>
			</div>
			<div class="clxx div clear" id="insureNoSameAsOwner" style="display: none;">
				<div class="lt">姓名</div>
				<div class="inpdiv"><input placeholder="被保人姓名" class="inp boxSet"  id="insureNoSameAsOwnerName" type="text" name="insureNoSameAsOwnerName" /></div>
				<div class="clxx div clear" style="border-bottom: 0px;padding-bottom: 1px;">
					<div class="lt">证件类型</div>
					<div class="inpdiv">                              
						<select id="insNoSameAsCertfCdeType" name="insNoSameAsCertfCdeType"></select>
					</div>
				</div>
				<div class="clxx div clear" style="border-bottom: 0px;padding-bottom: 1px;">
					<div class="lt">证件号</div>
					<div class="inpdiv"><input style="width: auto;text-transform:uppercase;" virtual_keyboard="idcard" maxlength="18" class="inp boxSet" type="text"  id="insureNoSameAsOwnerCard" placeholder="被保人身份证号" name="insureNoSameAsOwnerCard"/></div>
				</div>
			</div>
			<div class="clxx div clear">
				<div class="lt">投保人信息</div>
				<div class="rt"><input class="ck1 ckb" type="checkbox" checked="checked" name="appSameAsOwner" id="appSameAsOwner" />同车主</div>
			</div>
			<div class="clxx div clear" id="appNoSameAsOwner" style="display: none;">
				<div class="lt">姓名</div>
				<div class="inpdiv"><input placeholder="投保人姓名" class="inp boxSet"  id="appNoSameAsOwnerName" type="text" name="appNoSameAsOwnerName" /></div>
				
				<div class="clxx div clear" style="border-bottom: 0px;padding-bottom: 1px;">
					<div class="lt">证件类型</div>
					<div class="inpdiv">
						<select id="appNoSameAsCertfCdeType" name="appNoSameAsCertfCdeType"></select>
					</div>
				</div>
				<div class="clxx div clear" style="border-bottom: 0px;padding-bottom: 1px;">
					<div class="lt">证件号</div>
					<div class="inpdiv"><input style="width: auto;text-transform:uppercase;" virtual_keyboard="idcard" maxlength="18" class="inp boxSet" type="text"  id="appNoSameAsOwnerCard" placeholder="投保人身份证号" name="appNoSameAsOwnerCard"/></div>
				</div>
			</div>
			<div class="clxx div clear">
				<div class="lt">一年之内有过户</div>
				<div class="rt"><input class="ck1 ckb" type="radio"  name="transferSign" id="js_driver" />是<input class="ck1 ckb"  id="js_driver2" type="radio" name="transferSign" checked="checked" style="margin-left:40px;" />否</div>
			</div>
			<div class="clxx div clear" id="transfer" style="display: none;">
				<div class="lt">过户日期</div>
				<div class="inpdiv"><input style="height:25px;" placeholder="请选择过户日期" class="inp boxSet"  id="transferDate" type="date" pattern="^\d{4}[\.|\-|\/][0|1]?\d{1}[\.|\-|\/][0-3]?\d{1}$" name="transferDate" value="${inquiry.transferDateStr}" max="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />"/></div>
			</div>
		</div>
		</div>
		<div class="xzsel ins_main">
			<span>险种选择</span>
			<c:if test="${not empty autoFindDateSign and '1' eq autoFindDateSign}">
    			<span id="autoFindInsureDateId">自动获取保险期限</span>
			</c:if>
			<div class="rt xianzhongxuanz" id="up"></div>
		</div>
		<div class="part part2 ins_main" id="js_info_list">
			<div class="xzxz div clear">
				<input class="ck1 ckb" id="tciSign" type="checkbox" checked="checked" />交强险保险起期
				<input class="rt" style="height:25px;min-width:30%;"  type="date" id="tciStartDate" name="tciStartDate" value="${inquiry.tciStartDateStr }" />
				<input type="date"  id="tciEndDate"   name="tciEndDate" value="${inquiry.tciEndDateStr }" style="display: none;" readonly="readonly"/>
			</div>
			<div class="xzxz div clear">
				<input class="ck1 ckb" id="vciSign" type="checkbox" checked="checked" />商业险保险起期
				<input class="rt" style="height:25px;min-width:30%;" type="date" id="vciStartDate" name="vciStartDate" value="${inquiry.vciStartDateStr }"/>
				<input type="date"  id="vciEndDate"  readonly="readonly" value="${inquiry.vciEndDateStr }" name="vciEndDate" style="display: none;">
			</div>
			
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_default risk_base risk_noductcontainer" type="checkbox"  id="chesunxian" anyExcess="1" value="030006" checked="checked"/>车损险
				<div class="noductcontainer" onclick="noAnyExcess('chesunxian')"><div class="textchecked bjmpys" id="chesunxian_b">不计免赔</div><div class="iconchecked" id="chesunxian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_default risk_base risk_noductcontainer" type="checkbox" id="sanzhexian" anyExcess="1" value="030018" checked="checked">三者险
				<div class="noductcontainer" onclick="noAnyExcess('sanzhexian')"><div class="textchecked bjmpys" id="sanzhexian_b">不计免赔</div><div class="iconchecked" id="sanzhexian_a"></div></div>
				<select class="rt riskCheck" id="amount_sanzhexian"><option value="50000">5万</option><option value="100000">10万</option><option value="150000">15万</option><option value="200000">20万</option><option value="300000">30万</option><option value="500000" selected="selected">50万</option><option value="1000000">100万</option><option value="1500000">150万</option><option value="2000000">200万</option></select>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_default risk_base risk_noductcontainer" type="checkbox"  id="sijixian" anyExcess="1" value="030001" checked="checked"/>司机险
				<div class="noductcontainer" onclick="noAnyExcess('sijixian')"><div class="textchecked bjmpys" id="sijixian_b">不计免赔</div><div class="iconchecked" id="sijixian_a"></div></div>
				<select class="rt riskCheck" id="amount_sijixian"><option value="10000"selected="selected">1万</option><option value="20000">2万</option><option value="30000">3万</option><option value="40000">4万</option><option value="50000">5万</option><option value="100000">10万</option><option value="150000">15万</option><option value="200000">20万</option></select>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_default risk_base risk_noductcontainer" type="checkbox"  id="chengkexian" anyExcess="1" value="030009" checked="checked"/>乘客险
				<div class="noductcontainer" onclick="noAnyExcess('chengkexian')"><div class="textchecked bjmpys" id="chengkexian_b">不计免赔</div><div class="iconchecked" id="chengkexian_a"></div></div>
				<select class="rt riskCheck" id="amount_chengkexian"><option value="10000" selected="selected">1万/座</option><option value="20000">2万/座</option><option value="30000">3万/座</option><option value="40000">4万/座</option><option value="50000">5万/座</option><option value="100000">10万/座</option><option value="150000">15万/座</option><option value="200000">20万/座</option></select>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_base risk_noductcontainer" type="checkbox"  id="daoqiangxian" anyExcess="0" value="030061"/>盗抢险
				<div class="noductcontainer" onclick="noAnyExcess('daoqiangxian')"><div class="textnochecked bjmpys" id="daoqiangxian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="daoqiangxian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached chesunxian_son" type="checkbox"  id="bolixian" value="030004"/>玻璃险
				<select class="rt riskCheck"  id="amount_bolixian"><option class="textnochecked bjmpys" value="1">国产</option><option value="2">进口</option></select>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_4">
				<input class="ck1 js_check ckb risk_attached risk_noductcontainer chesunxian_son" type="checkbox"  id="huahenxian" anyExcess="0" value="032601"/>划痕险
				<div class="noductcontainer" onclick="noAnyExcess('huahenxian')"><div class="textnochecked bjmpys" id="huahenxian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="huahenxian_a"></div></div>
				<select class="rt riskCheck" id="amount_huahenxian"><option value="2000">2千</option><option value="5000">5千</option><option value="10000">1万</option><option value="20000">2万</option></select>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached risk_noductcontainer chesunxian_son" type="checkbox"  id="ziranxian" anyExcess="0" value="030012"/>自燃险
				<div class="noductcontainer" onclick="noAnyExcess('ziranxian')"><div class="textnochecked bjmpys" id="ziranxian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="ziranxian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_4">
				<input class="ck1 js_check ckb risk_attached risk_noductcontainer chesunxian_son" type="checkbox"  id="sheshuixian" anyExcess="0" value="032608"/>涉水险
				<div class="noductcontainer" onclick="noAnyExcess('sheshuixian')"><div class="textnochecked bjmpys" id="sheshuixian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="sheshuixian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached risk_noductcontainer chesunxian_son" type="checkbox"  id="shebeisunshixian" anyExcess="0" value="030021"/>设备损失险
				<div class="noductcontainer" onclick="noAnyExcess('shebeisunshixian')"><div class="textnochecked bjmpys" id="shebeisunshixian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="shebeisunshixian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached risk_noductcontainer" type="checkbox"  id="jingshensunhaixian" anyExcess="0" value="033002"/>精神损害险
				<div class="noductcontainer" onclick="noAnyExcess('jingshensunhaixian')"><div class="textnochecked bjmpys" id="jingshensunhaixian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="jingshensunhaixian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_2">
				<input class="ck1 js_check ckb risk_attached risk_noductcontainer" type="checkbox"  id="cheshanghuowuzerenxian" anyExcess="0" value="030032"/>车上货物责任险
				<div class="noductcontainer" onclick="noAnyExcess('cheshanghuowuzerenxian')"><div class="textnochecked bjmpys" id="cheshanghuowuzerenxian_b">不计免赔</div><div class="iconchecked" style="display: none;" id="cheshanghuowuzerenxian_a"></div></div>
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached chesunxian_son" type="checkbox"  id="zhidingxiulichangxian" value="032618"/>指定修理厂险
			</div>
			<div class="xzxz div clear risk risk_1 risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached chesunxian_son" type="checkbox"  id="wufazhaodaodisanfangxian" value="033008"/>无法找到第三方险
			</div>
			<div class="xzxz div clear risk risk_2 risk_3 risk_4">
				<input class="ck1 js_check ckb risk_attached chesunxian_son" type="checkbox"  id="xiuliqijianfeiyongbuchangxian" value="033007"/>修理期间费用补偿险
			</div>
			<div class="xzxz div clear risk risk_3">
				<input class="ck1 js_check ckb risk_attached chesunxian_son" type="checkbox"  id="chesunkuozhantiaokuan" value="033022"/>起重、装卸、挖掘车辆损失扩展条款
			</div>
			<div class="xzxz div clear risk risk_3">
				<input class="ck1 js_check ckb risk_attached chesunxian_son" type="checkbox"  id="shebeisunhuaikuochongtiaokuan" value="033023"/>特种车辆固定设备、仪器损坏扩展条款
			</div>
			
			<!-- <div class="xzxz div clear risk">
				<input class="ck1 vci js_check ins_risk ckb" type="checkbox"  id="cheshuncheckbox" name="damages"  anyExcess="1" value="030006"  checked="checked" />车损险
				<div class="noductcontainer ins_risk_1" onclick="noAnyExcess('cheshuncheckbox')"><div class="textchecked bjmpys" id="cheshuncheckbox_b">不计免赔</div><div class="iconchecked" id="cheshuncheckbox_a"></div></div>
				<select class="rt jsfujia" id="damages"><option value=1>不投保</option><option value=2 selected="selected">投保</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 vci js_check ins_risk ckb" type="checkbox"  id="sanzhexian" name="three" value="030018"  anyExcess="1"  checked="checked" />三者险
				<div class="noductcontainer ins_risk_1" onclick="noAnyExcess('sanzhexian')"><div class="textchecked bjmpys" id="sanzhexian_b">不计免赔</div><div class="iconchecked" id="sanzhexian_a"></div></div>
				<select class="rt jsfujia riskCheck" id="three"><option value="">不投保</option><option value="50000">5万</option><option value="100000">10万</option><option value="150000">15万</option><option value="200000">20万</option><option value="300000">30万</option><option value="500000" selected="selected">50万</option><option value="1000000">100万</option><option value="1500000">150万</option><option value="2000000">200万</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 vci js_check ins_risk ckb" type="checkbox"  id="shijixian" name="driver" value="030001"  anyExcess="1" checked="checked" />司机险
				<div class="noductcontainer ins_risk_1" onclick="noAnyExcess('shijixian')"><div class="textchecked bjmpys" id="shijixian_b">不计免赔</div><div class="iconchecked" id="shijixian_a"></div></div>
				<select  id="driver" class="rt jsfujia riskCheck"><option value="">不投保</option><option value="10000"selected="selected">1万</option><option value="20000">2万</option><option value="30000">3万</option><option value="40000">4万</option><option value="50000">5万</option><option value="100000">10万</option><option value="150000">15万</option><option value="200000">20万</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 vci js_check ins_risk ckb" type="checkbox"  id="chenkexian"  name="passenger" value="030009"   anyExcess="1" checked="checked" />乘客险
				<div class="noductcontainer ins_risk_1" onclick="noAnyExcess('chenkexian')"><div class="textchecked bjmpys" id="chenkexian_b">不计免赔</div><div class="iconchecked" id="chenkexian_a"></div></div>
				<select class="rt jsfujia riskCheck" id="passenger"><option value="">不投保</option><option value="10000" selected="selected">1万/座</option><option value="20000">2万/座</option><option value="30000">3万/座</option><option value="40000">4万/座</option><option value="50000">5万/座</option><option value="100000">10万/座</option><option value="150000">15万/座</option><option value="200000">20万/座</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 vci js_check ins_risk ckb" type="checkbox"  id="daoqiangxiancheckbox" name="stolenRob"  anyExcess="0" value="030061" />盗抢险
				<div class="noductcontainer ins_risk_1" onclick="noAnyExcess('daoqiangxiancheckbox')"><div class="textnochecked bjmpys" id="daoqiangxiancheckbox_b">不计免赔</div><div class="iconchecked" style="display: none;" id="daoqiangxiancheckbox_a"></div></div>
				<select class="rt jsfujia"   id="stolenRob"><option value="">不投保</option><option value="2">投保</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 js_check damages_base not_anyExcess ckb"  id="glasses" name="glass" value="030004" type="checkbox" />玻璃险
				<select class="rt riskCheck"  id="glass"><option value="0">不投保</option><option value="1">国产</option><option value="2">进口</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 tci js_check damages_base ckb"  id="scratch1" name="scratch" value="032601"   anyExcess="0" type="checkbox" />划痕险
				<div class="noductcontainer" onclick="noAnyExcess('scratch1')"><div class="textnochecked bjmpys" id="scratch1_b">不计免赔</div><div class="iconchecked" style="display: none;" id="scratch1_a"></div></div>
				<select class="rt jsfujia2 riskCheck" id="scratch"><option value="1">不投保</option><option value="2000">2千</option><option value="5000">5千</option><option value="10000">1万</option><option value="20000">2万</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 tci js_check damages_base ckb"  id="burning1" type="checkbox" name="burning"  anyExcess="0" value="030012"  />自燃险
				<div class="noductcontainer" onclick="noAnyExcess('burning1')"><div class="textnochecked bjmpys" id="burning1_b">不计免赔</div><div class="iconchecked" style="display: none;" id="burning1_a"></div></div>
				<select class="rt jsfujia2" id="burning"><option value="1">不投保</option><option value="2">投保</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 tci js_check damages_base ckb"  id="wade1" name="wade" value="032608"  anyExcess="0" type="checkbox" />涉水险
				<div class="noductcontainer" onclick="noAnyExcess('wade1')"><div class="textnochecked bjmpys" id="wade1_b">不计免赔</div><div class="iconchecked" style="display: none;" id="wade1_a"></div></div>
				<select class="rt jsfujia2"  id="wade"><option value="1">不投保</option><option value="2">投保</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 js_check damages_base not_anyExcess ckb"  id="repair" type="checkbox" name="repair" value="032618"  />指定修理厂
				<select class="rt" id="repair"><option value="1">不投保</option><option value="2">投保</option></select>
			</div>
			<div class="xzxz div clear risk">
				<input class="ck1 js_check damages_base not_anyExcess ckb"  id="odnft" name="odnft" value="033008"  type="checkbox" />无法找到第三方险
				<div class="noductcontainer"><div class="textchecked">不计免赔</div><div class="iconchecked"></div></div>
				<select class="rt" id="odnft" ><option value="1">不投保</option><option value="2">投保</option></select>
			</div> -->
		</div>
		<div style="height:45px;"></div>
		<div class="bombtn">
			<div class="bc2 ins_main" style="width: 100%;" id="js_calc_price">下一页</div>
			<div class="lt ins_car" id="js_calc_temp" >暂时保存</div>
			<div class="rt bc2 ins_car" id="js_calc_price1">保费计算</div>
		</div>
		<!-- 车型查询 -->
		<%-- <div class="part ins_car">
			<!-- <p id="backSoow">返回上一页</p> -->
			<div class="clxx div clear">
				<div class="lt">品牌车型</div>
				<div class="inpdiv" style="padding-right:80px;"><input style="text-transform:uppercase;" class="inp boxSet comh" type="text" placeholder="仅录入英文和数字进行搜索" id="carVersion" name="vehicleName" onblur="deleteCarRemark()"  placeholder="仅录入字母和数字" value="${inquiry.vehicleName}" required/></div>
	
				<div class="search bc2" id="searchBtn1">搜索</div>
			</div>
			<div class="clxx div">
				<div class="clear">
					<div class="lt">配置信息</div>
					<!-- <div class="inpdiv">秦川-福莱尔QCJ7081DD微型轿车 2003款 选装型   4座(参考价50200￥)</div> -->
					<div class="inpdiv">
					<textarea rows="3" cols="50"  class="inp boxSet comh" type="text"  id="carRemark"  name="remark" placeholder="点击搜索按钮选择相应车型" readonly="readonly"  required >${inquiry.remark}</textarea>
					<input  class="inp boxSet comh" type="text"  id="carRemark"  name="remark" placeholder="点击搜索按钮选择相应车型" readonly="readonly"  required value="${inquiry.remark}" /></div>
				</div>
				<div class="pzs disn" id="car_modle_detail" style="margin-bottom: 50px;">
					
				</div>
			</div>
		</div> --%>
	</form>
	<!-- 车型查询 -->
	<div id="chexingchaxun"></div>
	
	<div class="fixedbg"></div>
	
	<img class="bxbjdemo" src="${ctx }v3/images/demo.jpg" />
	
	<div class="tipsel txc" id="policyQueryId" >
		<div class="tip" id="infoId"></div>
		<div class="btns c1 clear" id="queryDataInfo">确定</div>
	</div>
	
	<div class="tipsel txc"  id="messageDialog">
		<div class="tip"></div>
		<div class="btns c1 clear"  id="messageDialog_yes" onclick="tipclosefun($(this).parent())">确定</div>
	</div>
	<jsp:include page="../commons/myalert2.jsp" />
	<script type="text/javascript">
		var ctx = "${ctx}";
		var inquiryId = '${inquiryId}';
		var resourceVehicleCvrg = '${resourceVehicleCvrg}';
		var areaDTO = '${areaDTO}';
		var inquiry = '${inquiry}';
		var iiquiryId = '${inquiry.inquiryId}';
		var transferSign = '${inquiry.transferSign}';
		var seatNum = '${inquiry.seatNum}';
		var ticSinp = '${ticSin}';
		var vicSinp = '${vicSin}';
		var xbmsg = '${msg}';
		var coverageItemsp= '${coverageItems}';
		var coverageItemsLength= '${fn:length(coverageItems)}';
		var fstRegNop = '${fstRegNo}';
		var signp = '${sign}';
		/* var driversp = '${drivers}'; */
		var inquiry_drivingRegp = '${inquiry.drivingReg}';
		var inquiry_transferSignp = "${inquiry.transferSign}";
		var inquiry_tciSignp = "${inquiry.tciSign}";
		var inquiry_vciSignp = "${inquiry.vciSign}";
		var xubao = "${xubao}";
		var plateNo = '${inquiry.plateNo}';
		var vehicleName1 = '${inquiry.vehicleName}';
		var panduan = '${panduan}';
		var teamId = '${teamId}';
		var usageCode = '${inquiry.usageCode}';
		var certfCdeType = '${inquiry.certfCdeType}'; 
		var insNoSameAsCertfCdeType = '';
		var appNoSameAsCertfCdeType = '';
		var vehiclePrice = '${inquiry.vehiclePrice}';
		var marketDate = '${inquiry.marketDate}';
		var vehicleName = '${inquiry.vehicleName}';
		var displacement = '${inquiry.displacement}';
		
		var isResourceVehicleDTO='${isResourceVehicleDTO}';

		 $(function(){
			 $('.clhcz .rt').click(function(){$('.fixedbg,.bxbjdemo').fadeIn();});
				$('.fixedbg').click(function(){$('.fixedbg,.bxbjdemo').fadeOut();});
				
			 }) ;
		 $('.ins_main').show();
		 $('.ins_car').hide();
		 $('#back2').hide();
	</script>
	<%-- <script type="text/javascript" src="${ctx }v3/js/flow_insurance1.js"></script> --%>
	<%-- <script type="text/javascript" src="${ctx }v3/js/flow_insurance2.js"></script> --%>
	<script type="text/javascript" src="${ctx }v3/js/insurance_risk.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/insurance_artificial.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/flow_insurance.js"></script>
	
	<script type="text/javascript">
		var tempFile ;
		var inputLabelShow = '行驶证对照录入';
		if('1' == '${canUseVehicleLicenseOcr}'){
			inputLabelShow = '行驶证照片识别';
		}
		var serviceUpload = {
			fileServerSysId:'bxtx',
			fileServerUploaderUrl:'${baseURL}/file_server/file/uploadAsStream.do',
			fileServerShowFileBaseUrl:'${baseURL}/file_server/files/',
			serviceFileAdd:function(fileId){
				if(null != fileId && '' != fileId){
					loadfun("识别中...");
					$('.statusBar').hide();
					$('.filelist').show();
					$('#cardNameDiv').html('').hide();
					$.post("${ctx}ocr/identifyVehicleLicense.do",{'fileId':fileId},function(data){
						loadclosefun();
						if(data.succ){
							var card = data.data;
							$('#carFrame').val(card.vin);$('#carFrame').change();
							$('#carEngine').val(card.engine_no);$('#carEngine').change();
							$('#initDate').val(card.register_date);
							$('#carName').val(card.owner);
							$('input[name=vehicleName]').val(card.model);
							vehicleName1 = card.model;
							vehicleName = card.model;
							
// 							$('input[name=plateNo]').val(card.plate_no);
// 							$('#show_plateNO').val("车牌："+card.plate_no);
// 							plateNo=card.plate_no;
							alert('识别完成，请先核对系统识别出的信息后，再进行下一步操作');
						}
						else{
							alert(data.msg);
							if(null != data.msg && data.msg.indexOf('未开启')){
								$('#ocr_dododo').hide();
							}
						}
					},'json');
				}
			}
		}		
		console.log('autoFindDateSign:${autoFindDateSign}');
		$('#autoFindInsureDateId').on({
			'click':function(){
				loadfun("处理中");
				$('#autoFindInsureDateId').hide();
				$.post('${ctx}autoFindInsureDate.do',{
					'plateNo':'${inquiry.plateNo}',
					'frmNO':$('#carFrame').val(),
					'enginNo':$('#carEngine').val(),
					'ownerName':$('#carName').val(),
					'certNo':$('#cardNumber').val()
				},function(data){
					loadclosefun();
					var returnCode = data.returnCode;
					var returnMessage = data.returnMessage;
					var tciStartDate = data.tciStartDate;
					var vciStartDate = data.vciStartDate;
					if('0' != returnCode){
						alert('未自动获取到保险期限,请核实上年保单起止期后手动录入！');
						return;
					}
					else{
						alert(returnMessage);
						if(null != tciStartDate && '' != tciStartDate){
							$('#tciStartDate').val(tciStartDate);
							$('#tciSign').attr('checked','checked');
						}
					    if(null != vciStartDate && '' != vciStartDate){
                            $('#vciStartDate').val(vciStartDate);
					    	$('#vciSign').attr('checked','checked');
                        }
					}
				},'json');
			}
		});
	</script>
	<script type="text/javascript" src="${ctx}webuploader/webuploader_ocr.js"></script>
	<script type="text/javascript" src="${ctx}webuploader/upload_ocr.js?v=1.0"></script>
</body>
</html>