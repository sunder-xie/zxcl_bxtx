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
<script type="text/javascript">
	var ctx = "${ctx}";
	$(function(){
		$('.zbnav .lt').click(function(){
			if(!$(this).attr('id')){
				var ind=$(this).index(),w,m='-35px',l;
				if(ind==0){l='25%';}else{l='75%';}
				$('.tab').eq(ind).show().siblings('.tab').hide();
				$('.zbnav .line').animate({width:w,'margin-left':m,left:l},600);
				$(this).attr('id','sel').siblings('.lt').removeAttr('id');
			}
		});
	});
</script>
<style type="text/css">
.head-v2{
	height: 40px;
    line-height: 40px;
}
.content-v2{
	padding-top: 40px;
}
.tbimgtxt-v2{
	line-height: 55px;
}
.img-v2{
	width: 55px !important;
    height: 55px !important;
    background-size: 95% !important;
}
.xci-start-date{
	margin-left: 15px;
    color: #797979;
    font-size: 0.8rem;
}
.submit-v2{
    line-height: 40px !important;
    margin: 5px 5px !important;
    width: 30% !important;
}
.submit-v2-l{
	float:left;
	margin-left:5% !important;
}
.submit-v2-r{
	float:right;
	margin-right:5% !important;
}
.vehicle-message-item{
    margin-bottom: 1px;
}
.arr {
    position: relative;
    width: 10px;
    height: 10px;
    left: 4px;
    border: 1px solid #2BA5E9;
    border-top: 0;
    border-right: 0;
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    float:right;
    margin-right: 5px;
    margin-top: 2px;
}
#arr {
    -webkit-transform: rotate(135deg);
    transform: rotate(135deg);
    top: 6px;
}
.clear-v2{
	padding: 6px !important;
}
.xian.txr .clear{
	padding-right: 15px;
	height: 20px;
    line-height: 20px;
}
.vehicle-message-item span{
	font-size:0.8rem;
}
.tbxq .part2 .xian{
    padding: 0 10px 10px 10px;
}
.font-rem{
	font-size:0.8rem;
}
</style>
</head>
<body class="tbxq">
	<div class="head head-v2"><div class=" lt"></div><p class="title">投保详情</p></div>
	<div class="content-v2">
		<div class="tbimgtxt clear c3 tbimgtxt-v2">
			<div class="img bgc lt img-v2" style="background-image:url(${cxt}images/ins_logo/${fn:substring(insuranceDTO.insId, 0, 4)}.png);"></div>
			<b>${insuranceDTO.insPetName }&nbsp;&nbsp;</b><span>为您投保服务</span>
		</div>
		<div class="part2">
			<div class="tit clear vehicle-message">
				<div class="vehicle-message-item">
					<span>车牌号码：${inquiry.plateNo}</span><span style="float:right;">车主：${inquiry.ownerName}</span>
				</div>
				<div class="vehicle-message-item">
					<span>车&nbsp;&nbsp;架&nbsp;&nbsp;号：${inquiry.frmNo}</span>
				</div>
				<div class="vehicle-message-item">
					<span>发动机号：${inquiry.engNo}</span>
				</div>
				<div class="vehicle-message-item">
					<span>初登日期：<fmt:formatDate value="${inquiry.fstRegNo}" pattern="yyyy-MM-dd"/></span>
				</div>
				<div class="vehicle-message-item">
					<span>车型信息：${inquiry.remark}</span>
				</div>
				<div class="vehicle-message-item">
					<c:if test="${inquiry.vciSign == '1'}">
						<c:if test="${not empty quota.discount}"><span>商业折扣：${quota.discount}</span></c:if>
					</c:if>
				</div>
				<div class="vehicle-message-item">
					<c:if test="${not empty quota.lastYearClaimNum && quota.lastYearClaimNum ne 0}">
						<span>出险：${quota.lastYearClaimNum}次</span>
					</c:if>
				</div>
			</div>
			<div class="tit clear clear-v2">
				<div class="lt c2">
				<span>交强险</span>
				<c:if test="${inquiry.tciSign == '1'}">
					<span class="xci-start-date">起保时间：<fmt:formatDate value="${inquiry.tciStartDate}" pattern="yyyy-MM-dd"/></span>
				</c:if>
				</div>
				<div class="rt">
				<span style="margin-right: 15px;"><c:if test="${inquiry.tciSign == '0'}">未投保</c:if></span>
				<span style="margin-right: 15px;" class="font-rem"><c:if test="${inquiry.tciSign == '1'}"><fmt:formatNumber pattern="￥0.00" value="${quota.TCIPremTax }" /></c:if></span>
				</div>
			</div>
			<div class="tit clear clear-v2" >
				<div class="lt c2">车船税</div>
				<div class="rt">
					<span style="margin-right: 15px;"><c:if test="${inquiry.tciSign == '0'}">未投保</c:if></span>
					<span style="margin-right: 15px;" class="font-rem"><c:if test="${inquiry.tciSign == '1'}"><fmt:formatNumber pattern="￥0.00" value="${quota.vehicleTax }" /></c:if></span>
				</div>
			</div>
			<div class="tit clear clear-v2" style="border-bottom: 0px">
				<div class="lt c2">
					<span>商业险</span>
					<c:if test="${inquiry.vciSign == '1'}">
						<span class="xci-start-date">起保时间：<fmt:formatDate value="${inquiry.vciStartDate}" pattern="yyyy-MM-dd"/></span>
					</c:if>
				</div>
				<div class="rt">
					<c:if test="${inquiry.vciSign == '0' }"><span style="margin-right: 15px;">未投保</span></c:if>
					<c:if test="${inquiry.vciSign == '1'}"><span style="margin-right: 0px;" class="font-rem"><fmt:formatNumber pattern="￥0.00" value="${quota.VCIPremTax}" /></span>
					<c:if test="${riskCheck == '1' }"><div class="arr arr-clicked"></div></c:if> </c:if>
				</div>
			</div>
			<div class="tit clear clear-v2" style="color: #a7a7a7;font-size: 10px;margin-top: -1.5%;" id="xian_duan">
				<c:if test="${inquiry.vciSign == '1'}">
					<c:forEach items="${platforms }" var="item" varStatus="varStatus">
						<span>${item.remark}<c:if test="${item.sumLimit > 0}">（ ${item.sumLimit}万）</c:if><c:if test="${fn:length(platforms) ne varStatus.index+1}">、</c:if></span>
					</c:forEach>
				</c:if>
			</div>
			<c:if test="${inquiry.vciSign == '1'}">
				<div class="xian txr" style="display: none;">
					<c:forEach items="${platforms }" var="item">
						<div class="clear">
							<div class="lt font-rem">${item.remark }<c:if test="${item.sumLimit > 0}">（ ${item.sumLimit}万）</c:if></div><span class="font-rem"><fmt:formatNumber pattern="￥0.00" value="${item.amount }" /></span>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<div class="tit c2 txr">合计：<b><fmt:formatNumber pattern="￥0.00" value="${quota.totalAmount }" /></b></div>
			<!-- 人工报价 -->
			<c:if test="${'A1' ne inquiry.usageCode }">	
				<c:if test="${not empty manualQuotationRemark }">
					<div class="tit clear clear-v2" style="border-bottom: 0px">
						<div class="lt c2">
							<span>备注</span>
							<span class="xci-start-date">${manualQuotationRemark }</span>
						</div>
					</div>
				</c:if>			
				<c:if test="${not empty manualQuotationTaskDTO.fileIds }">
					<div class="tit clear clear-v2" style="border-bottom: 0px">
						<div class="lt c2">
							<span>保费计算截图<div class="arr images_switch" ></div></span>
						</div>
						<br/><br/>
						<ul id="images" class="images_son" style="display: none;">
							<c:forEach items="${manualQuotationTaskDTO.fileIds }" var="fileId">
								<li><img style="width: 95%;" alt="" src="${fileId }"></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</c:if>
		</div>
		<c:if test="${warns!= null || fn:length(warns) > 0}"><div class="warnsIcon" style="text-align:right;margin-bottom:5px;text-align: left; padding-left: 5px;padding-right:5px;font-size: 0.8rem;color: #676464; background: #fff;padding-top: 5px;padding-bottom: 5px; border-bottom: #f0f0f0">
			
			<div class="clear" style="text-align:left;padding-left:5px;font-size: 0.8rem;color: #676464;font-weight: bold;">保险公司提示信息：</div>
			<c:forEach items="${warns }" var="warn" varStatus="status">
					<div class="clear" style="text-align:left;padding-left:5px;font-size: 0.8rem;color: #676464;">${status.index + 1}.${warn }</div>
				</c:forEach>
				<div class="clear" style="margin-bottom:5px;"></div>
				<div style="width:100%;">
					<input style="vertical-align: text-bottom; margin-left:5px;-webkit-appearance: checkbox;width:17px;height:17px;display: inline;" name="readed" type="checkbox"  />
					<div style="padding-right:5px; font-size:0.8rem;   color: #fb0909;display: inline;">我已阅读并确认提示信息正确无误!</div>
				</div>
		</div>
		<div class="clear" style="margin-bottom:15px;"></div>
		</c:if>
		<div class="submit txc clear submit-v2 submit-v2-l"  id="submit1" onclick="window.location.href= '${ctx}orderBaseInfoBack.do' + window.location.search">返回</div>
		<c:choose>
			<c:when test="${flowControl eq '2' or flowControl eq '3'}">
				<div class="submit txc clear submit-v2 submit-v2-r" id="submit1"   onclick="toVote()"><c:if test="${warns!= null || fn:length(warns) > 0}"><img style="width:15px;margin-right:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RTlCMTQwQTVDN0YzMTFFNjgwMTU4NTgzQjg2RjgwNUUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RTlCMTQwQTZDN0YzMTFFNjgwMTU4NTgzQjg2RjgwNUUiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpFOUIxNDBBM0M3RjMxMUU2ODAxNTg1ODNCODZGODA1RSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpFOUIxNDBBNEM3RjMxMUU2ODAxNTg1ODNCODZGODA1RSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PnO2BR8AAAJ/SURBVHjarFVLaxNRGD33ZpJM3qk1bZVGceULRfwHgrgS3LjTpYJRKT5q1RZ37gpuhBZcCW51oa4UBBF1I65dWQhB8FEjTbRJp/Pw3GlSkukkk6T54DBzH98999zvfvcTK+8z6MNOExeIJ8SzXp1kHwTniZfEGeIpUejVUfShpERMtrT/EDnCGpaSgodA2QgxPUwlJR+SppoxwtyukssdCJpqbg5DSZHYY5sS8VQdkdEajLKO1UoMUrPVeJmYINYHVXJFEaifTHINX7+NYOrBKXwp5pAlYcN2BMUmiOTOpuTsKh6/O4CHiyfx6M1hIF1rnXeLCA9CcrUtFpZAUueJTJaRihmALb2xuTYIyd326DEp1O4Zm1xKqXC88+eISD8kKi92t/VwzXSMShyBTNxwST2mbtCNfkjubenh8cSjJOGNSugbZB3UR3ohUSp2bellTBJR5lzY5HfdbftYmrjeC8ltX21UkuTimmbxa3oD32rT3nWlT17s9XXl8ehUYVZj0EK2T9w3bad3o16SmU6eRj2M8WwNs+c+ID/6F2Yt0i2/lBrN71m5RCx28nKUEt2AsRZGhMdVr2sQomsiqw3Pe5XMdfPIjFWw8Ooo0menMP/8ONvVoDdvtnnTZJd6AW+eyEZWugqcwNc723gFpFj5mLkI282LfJCXFrZQ/JFGfrwKh5nvBBP94s7ucyaOIBRMYDMmjiVxcN8yJC+XZYvgShRyy/N+TS6FTCdnw5mwu1QEdVSOW8wrdd29N41a0tnUm/xTQiwLU0MVL0RNHoOBE+6Aje2b3CjI4rv8xHVfa4jiLbf4WZTkAmN6iMP/egprd0sweZcQdgrQ8fu/AAMAXOuxVKzvrIUAAAAASUVORK5CYII=" /></c:if>确认投保</div>
			</c:when>
			<c:otherwise>
				<div class="submit txc clear submit-v2 submit-v2-r" id="submit1" style="background: gray;"  onclick="javascript:dialog('暂未开放线上投保')">确认投保</div>
			</c:otherwise>
		</c:choose>
		<div class="clear"></div>
		<jsp:include page="../commons/menu.jsp" />
		<script type="text/javascript">
			$('.arr-clicked').on('click', function(){
				var id = $(this).attr('id');
				console.log(id);
				if(id == 'arr'){
					$(this).removeAttr('id');
					$('.xian.txr').slideUp();
					$('#xian_duan').show();
				}
				else{
					$(this).attr('id', 'arr');
					$('.xian.txr').slideDown();
					$('#xian_duan').hide();
				}
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
			
			function toVote(){
				<c:if test="${warns!= null || fn:length(warns) > 0}">
				if(!$("input[name=readed]").attr("checked")){
					dialog("请您阅读提示信息，并确认信息无误后，勾选复选框再继续投保！");
					return;
				}</c:if>
				location.href='${ctx }premium.do?insId=${param.insId}&quotaId=${param.quotaId}&inquiryId=${param.inquiryId}';
			}
			
		</script>
	</div>
</body>
</html>