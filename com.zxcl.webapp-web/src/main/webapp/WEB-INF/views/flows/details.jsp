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
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
.arr {
    position: relative;
    width: 10px;
    height: 10px;
    left: 8px;
    border: 1px solid #2BA5E9;
    border-top: 0;
    border-right: 0;
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    float:right;
    margin-right: 7%;
    margin-top: 2px;
}
#arr {
    -webkit-transform: rotate(135deg);
    transform: rotate(135deg);
    top: 6px;
}
</style>
</head>
<body>
	<div class="head"><div class="back lt" onclick="<c:choose>
					<c:when test="${param.toIndex eq 1 }">location.href='${ctx }index.do?nocheck=1';</c:when>
					<c:otherwise>history.back();</c:otherwise>
				</c:choose>"></div><p class="title"><c:choose>
					<c:when test="${type eq 1 }"><a class="logo bjjg">投保明细</a></c:when>
					<c:otherwise><a class="logo bjjg">保单详情</a></c:otherwise>
				</c:choose></p></div>
	<div class="content tbmx">
		<div class="tit c2">保单配送</div>
		<div class="box">
			<label>姓名：</label>${dis.disName}<br />
			<label>电话：</label>${dis.disPhone}<br />
			<label>详细地址：</label>${dis.disAddress}<br />
			<c:choose>
				<c:when test="${dis.dispatchType==0}">
					<label>配送方式：</label>配送<br />
				</c:when>
				<c:when test="${dis.dispatchType==1}">
					<label>配送方式：</label>自取<br />
				</c:when>
				<c:otherwise>
					<label>配送方式：</label>自取<br />
				</c:otherwise>
			</c:choose>
			<c:if test="${not empty  dis.expressDeliveryNum}">
				<label>快递单号：${dis.expressDeliveryNum }</label><br />
				<a href="https://m.kuaidi100.com/result.jsp?nu=${dis.expressDeliveryNum }" style="color: blue;">点击查询配送进度</a><br />
			</c:if>
		</div>
		<div class="tit c2">险种信息</div>
		<div class="box">
			<c:if test="${not empty order.tciPlyNo }">
				<label>交强险保单号：</label>${order.tciPlyNo }<br />
			</c:if>
			<c:if test="${not empty  order.vciPlyNo }">
				<label>商业险保单号：</label>${order.vciPlyNo }<br />
			</c:if>
			<c:if test="${vehicle.tciSign == '1' }">
				<label>交强险保险期限：</label>${order.inquiry.tciStartDateStr } 至 ${order.inquiry.tciEndDateStr }<br />
			</c:if>
			<c:if test="${vehicle.vciSign == '1' }">
				<label>商业险保险期限：</label>${order.inquiry.vciStartDateStr } 至 ${order.inquiry.vciEndDateStr }<br />
			</c:if>
		</div>
		<div class="tit c2">车辆信息</div>
		<div class="box">
			<label>车牌号：</label>${vehicle.plateNo}<br />
			<label>车架号：</label>${vehicle.frmNo}<br />
			<label>发动机号：</label>${vehicle.engNo}<br />
			<label>车型：</label>${vehicle.remark}(参考价${vehicle.vehiclePrice})<br />
			<label>初登日期：</label>${vehicle.fstRegNoStr }<br />
			<c:if test="${vehicle.transferSign == '1' }"><label>是否过户：</label>是<br /></c:if>
			<c:if test="${vehicle.transferSign != '1' }"><label>是否过户：</label>否<br /></c:if>
			<c:if test="${vehicle.transferSign == '1' }">
				<label>过户日期：</label><fmt:formatDate value="${vehicle.transferDate }" pattern="yyyy-MM-dd"/><br />
			</c:if>
		</div>
		<div class="tit c2">车主信息</div>
		<div class="box">
			<label>姓名：</label>${owner.ownerName}<br />
			<label>证件类型：</label>${owner.ownerCertTypeName}<br />
			<label>证件号码：</label>${owner.ownerCertNo}<br />
			<label>手机号：</label>${owner.ownerPhone}<br />
		</div>
		<div class="tit c2">被保人信息</div>
		<div class="box">
			<label>姓名：</label>${rec.recName}<br />
			<label>证件类型：</label>${rec.recCertTypeName}<br />
			<label>证件号码：</label>${rec.recCertNo}<br />
			<label>手机号：</label>${rec.recPhone}<br />
		</div>
		<div class="tit c2">投保人信息</div>
		<div class="box">
			<label>姓名：</label>${vote.voteName}<br />
			<label>证件类型：</label>${vote.voteCertTypeName}<br />
			<label>证件号码：</label>${vote.voteCertNo}<br />
			<label>手机号：</label>${vote.votePhone}<br />
		</div>
		
		<c:if test="${not empty drivers}">
			<c:forEach items="${drivers}" var="driver">
				<div class="tit c2">驾驶员信息</div>
				<div class="box">
					<label>姓名：</label>${driver.name}<br />
					<label>身份证号：</label>${driver.certNo}<br />
					<label>驾驶证类型：</label>${driver.driverTypeName}<br />
					<label>驾驶证号：</label>${driver.licenseNo}<br />
					<label>发证日期：</label>${driver.licenseDate}<br />
				</div>
			</c:forEach>
		</c:if>
		
		<div class="tit c2">订单详情</div>
		<div class="box">
			<label>订单号：</label>${order.orderId}<br />
			<c:if test="${vehicle.tciSign == '1' }">
				<label>交强险投保单号：</label>${order.tciApplyNo}<br />
			</c:if>
			<c:if test="${vehicle.vciSign == '1' }">
				<label>商业险投保单号：</label>${order.vciApplyNo}<br />
			</c:if>
			<label>支付单号：</label>${order.payId}<br />
			<label>保险公司：</label>${ins.insName}<br />
			<label>订单总价：</label>¥${order.quota.totalAmount}<br />
		</div>
		
		<div class="tit c2">保险信息</div>
		
		<c:if test="${vehicle.vciSign == '1' }">
		<div class="line"><label class="c2">商业险&nbsp;&nbsp;</label>¥${quota.VCIPremTax}</div>
		<div class="box">
			<c:forEach items="${platforms}" var="plat">
					<label>${plat.remark} </label><c:if test="${not empty plat.sumLimit and plat.sumLimit >= 1 }">(${plat.sumLimit }万)</c:if>
					<c:if test="${not empty plat.sumLimit and plat.sumLimit > 0 and plat.sumLimit < 1 }">(${plat.sumLimit * 10 }千)</c:if> ¥${plat.amount}<br />
			</c:forEach>
		</div>
		
		</c:if>
		<c:if test="${vehicle.vciSign == '0' }">
		<div class="line"><label class="c2">商业险&nbsp;&nbsp;</label>未投保</div>
		</c:if>
		<c:if test="${vehicle.tciSign == '1' }">
			<div class="line"><label class="c2">交强险&nbsp;&nbsp;</label>¥${quota.TCIPremTax}</div>
			<div class="line"><label class="c2">车船税&nbsp;&nbsp;</label>¥${quota.vehicleTax}</div>
		</c:if>
		<c:if test="${vehicle.tciSign == '0' }">
			<div class="line"><label class="c2">交强险&nbsp;&nbsp;</label>未投保</div>
			<div class="line"><label class="c2">车船税&nbsp;&nbsp;</label>未投保</div>
		</c:if>
		<c:if test="${not empty manualQuotationRemark }">
			<div class="tit c2">保费计算备注</div>
			<div class="box">
				<label>${manualQuotationRemark }</label>
			</div>
		</c:if>
		<c:if test="${not empty manualQuotationTaskDTO.fileIds }">
			<div class="tit c2">保费计算截图<div class="arr images_switch" ></div></div>
			<div class="box">
				<ul id="images" class="images_son" style="display: none;">
					<c:forEach items="${manualQuotationTaskDTO.fileIds }" var="fileId">
						<li><img style="width: 97%;" alt="" src="${fileId }"></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>
	
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
		$(function(){
			setNav(3);
		});
		$('.images_switch').on('click', function(){
			var id = $(this).attr('id');
			console.log(id);
			if(id == 'arr'){
				$(this).removeAttr('id');
				$('.images_son').slideDown();
				$('#images').hide();
			}
			else{
				$(this).attr('id', 'arr');
				$('.images_son').slideUp();
				$('#images').show();
			}
		});
	</script>
</body>
</html>