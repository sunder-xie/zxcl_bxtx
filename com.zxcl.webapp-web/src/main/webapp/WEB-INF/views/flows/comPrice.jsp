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
	$(function(){
		$('.nav .rt').click(function(){
			var obj=$(this).find('.arr');
			if(obj.attr('id')){obj.removeAttr('id'); obj.prev().text('详情'); $(this).parent().next().hide();}else{obj.attr('id','arr'); obj.prev().text('收起'); $(this).parent().next().show();}
		});
	});
</script>
<style type="text/css">
.eye{
	height: 20px;
    width: 20px;
    display: inline-block;
/*     border: 1px solid gray; */
}
.eye img{
	height: 20px;
    width: 20px;
    display: inline-block;
}
.mytip{
	background: url(${ctx}images/yan_on.png) no-repeat 10px center #fff !important;
}
.ratio-value{
	color: #0F0F8E;
    font-size: 12px;
    position: absolute;
    margin: 1px 0 0 1px;
    display:none;
}
.line22.ratio{
	margin-top: 1%;
}
</style>
<style>
.fee-contain{
	position: fixed;
	top:0px;
	height:auto;
	min-height: 20px;
	width:100%;
	margin:0;
	z-index:1100;
	display: none;
    background-color: white;
    color: black;
/*     border-bottom-right-radius: 10px; */
/*     border-bottom-left-radius: 10px; */
    opacity: 0.9;
}
.fixedbg{
/* 	display: block; */
}
.fee-table{
    color: black;
    text-align: center;
    width: 100%;
    margin-top: 10%;
}
.fee-table tr{
	margin-bottom: 5px;
    height: 40px;
}
.fee-btn{
    background-color: #e42b2b;
    display: block;
    width: 80px;
    height: 30px;
    border-radius: 5px;
    font-size: 16px;
    margin: 7% 0 7% 0;
    color: white;
}
.warnsIcon{
position: absolute;top: 35%;left: 61%;width:25px;height:25px;background:url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RTlCMTQwQTVDN0YzMTFFNjgwMTU4NTgzQjg2RjgwNUUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RTlCMTQwQTZDN0YzMTFFNjgwMTU4NTgzQjg2RjgwNUUiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpFOUIxNDBBM0M3RjMxMUU2ODAxNTg1ODNCODZGODA1RSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpFOUIxNDBBNEM3RjMxMUU2ODAxNTg1ODNCODZGODA1RSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PnO2BR8AAAJ/SURBVHjarFVLaxNRGD33ZpJM3qk1bZVGceULRfwHgrgS3LjTpYJRKT5q1RZ37gpuhBZcCW51oa4UBBF1I65dWQhB8FEjTbRJp/Pw3GlSkukkk6T54DBzH98999zvfvcTK+8z6MNOExeIJ8SzXp1kHwTniZfEGeIpUejVUfShpERMtrT/EDnCGpaSgodA2QgxPUwlJR+SppoxwtyukssdCJpqbg5DSZHYY5sS8VQdkdEajLKO1UoMUrPVeJmYINYHVXJFEaifTHINX7+NYOrBKXwp5pAlYcN2BMUmiOTOpuTsKh6/O4CHiyfx6M1hIF1rnXeLCA9CcrUtFpZAUueJTJaRihmALb2xuTYIyd326DEp1O4Zm1xKqXC88+eISD8kKi92t/VwzXSMShyBTNxwST2mbtCNfkjubenh8cSjJOGNSugbZB3UR3ohUSp2bellTBJR5lzY5HfdbftYmrjeC8ltX21UkuTimmbxa3oD32rT3nWlT17s9XXl8ehUYVZj0EK2T9w3bad3o16SmU6eRj2M8WwNs+c+ID/6F2Yt0i2/lBrN71m5RCx28nKUEt2AsRZGhMdVr2sQomsiqw3Pe5XMdfPIjFWw8Ooo0menMP/8ONvVoDdvtnnTZJd6AW+eyEZWugqcwNc723gFpFj5mLkI282LfJCXFrZQ/JFGfrwKh5nvBBP94s7ucyaOIBRMYDMmjiVxcN8yJC+XZYvgShRyy/N+TS6FTCdnw5mwu1QEdVSOW8wrdd29N41a0tnUm/xTQiwLU0MVL0RNHoOBE+6Aje2b3CjI4rv8xHVfa4jiLbf4WZTkAmN6iMP/egprd0sweZcQdgrQ8fu/AAMAXOuxVKzvrIUAAAAASUVORK5CYII=');
}
.errorIcon{
position: absolute;top: 35%;left: 61%;width:25px;height:25px;background:url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MjhCQ0M5NThDN0Y0MTFFNkE0MEVDOTU1NzYyNTA4NzEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MjhCQ0M5NTlDN0Y0MTFFNkE0MEVDOTU1NzYyNTA4NzEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDoyOEJDQzk1NkM3RjQxMUU2QTQwRUM5NTU3NjI1MDg3MSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDoyOEJDQzk1N0M3RjQxMUU2QTQwRUM5NTU3NjI1MDg3MSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pld7vlsAAALHSURBVHjavFY9aBRBFP7e7t7dJpjkEkwULkWuU/xB8AcEi2AhFtFGUNDKRlvF3oiFBMRCUImgkVRGG0GwsLKwEDQgSBBFEAsLkfxgNLnbu50Zv9nkjsvP/lyhd3y7M8y8efO9782bFWMM/vXPMx/3tTO/k7hC3CXmsxo5bW7qBnGduNWOkaiZvVnnlomvLf09xExGJlaTDDBmAlY/0Y3+o6y2jhU+A4aNwrB01yG9NUh3DUbMAaPNySz2En7YlYXxd+mpl+rve1CZKiF/eAH+iR8wy+48tGxd3XJ8uOgr7X8ZniqRCSrPtiF4U0T1RT/UzzxzLezj+GjaCk4U53j4xJjdiq440K6CU1riO4T+7UDcaM5Vopi0Tpom94i8Cck5RyoFLh4A2lMweUJFc4R4kLROUnZRLHPetikwnJyGoRNV5ZgfQjpDmDoac08Rh+LW8hLKymSzZVk7uskEPrOsYJkALfYPV89OZuFHiP3Nnl0ocqKgamx3MH4+nYRotdlNnG1H+Ik1fYYLVmQbLsuETsSGT2+wGye8LMJf47N/zV4aTKzYwYozWPEN1u+6i7iZJnyRGN3AjGNiRR6o4k9ABn0BHG+V4cYoXOJje+u6sjxdbtXoCXF6U/FcICSBhc8+esoBCls0VF3ikuYlcbxZhZffDTXaByF4u6mJ3aAW5Prr8Do0wsBBOOcBKuGyMDjK56v12TUZe6DEwO2r4dfzIr4c24HZOwNRCsOKH2cDM7E+u84QO+PKgngMTSCYf9qL+qcCFqZ6Uf2Wh9MVJpWkIeLCSnbxmBH3E0scRbcnPj8yh0p5CS7fucFqVL9SiuNtooOaDI7T2cXEQm/vKhv7LoXKohOJ7tUclhhJvcBF8FhmX5dUIWfPC9L8WAN4vuGpF+hwpZ/iADVGwVtcknM5F0ds6DPd9IuS/QNCWHkUpuV/fHf9FWAAqD/v3vetJvsAAAAASUVORK5CYII=');
}
.btnCol{
background-color: #1fa096;
}
</style>
</head>
<body>
	<div class="fixedbg" id="fixedbg"></div>
	<div class="fee-contain">
		<table  cellpadding="0" cellspacing="0" class="fee-table" id="fee-table">
			<tr>
				<th width="30%">保险公司</th>
				<th width="30%">商业险</th>
				<th width="30%">交强险</th>
			</tr>
		</table>
		<div align="center">
			<input class="fee-btn" type="button" value="确定"/>
		</div>
	</div>
	<div class="head"><div class=" lt"></div><div class=" rt" onclick="location.href='${ctx}index.do?nocheck=1';"><img style="width:80%;margin-top:15%;" src="${ctx }v3/images/souye.png" /></div><p class="title">报价</p></div>
	<div class="content tbgs">
		<div class="cltbxx">
			<div class="nav clear">
				<b class="lt">车辆信息</b>
				<div class="rt"><div>详情</div><div class="arr"></div></div>
			</div>
			<div class="con disn">
				<div class="clear">
					<div class="lt">品牌车型</div>
					<div class="div"><div class="rt">${inquiryDTO.vehicleName }</div></div>
				</div>
				<div class="clear">
					<div class="lt">配置信息</div>
					<div class="div"><div class="rt">${inquiryDTO.remark } </div></div>
				</div>
				<div class="clear">
					<div class="lt">注册日期</div>
					<div class="rt"><fmt:formatDate value="${inquiryDTO.fstRegNo }" pattern="yyyy-MM-dd"/></div>
				</div>
				<div class="clear">
					<div class="lt">车架号</div>
					<div class="div"><div class="rt">${inquiryDTO.frmNo }</div></div>
				</div>
				<div class="clear">
					<div class="lt">发动机号</div>
					<div class="div"><div class="rt">${inquiryDTO.engNo }</div></div>
				</div>
				<div class="clear">
					<div class="lt">是否过户</div>
					<div class="rt"><c:if test="${inquiryDTO.transferSign == '1' }">是</c:if><c:if test="${inquiryDTO.transferSign != '1' }">否</c:if></div>
				</div>
				<c:if test="${inquiryDTO.transferSign == '1' }">
				<div class="clear">
					<div class="lt">过户日期</div>
					<div class="rt"><fmt:formatDate value="${inquiryDTO.transferDate }" pattern="yyyy-MM-dd"/> </div>
				</div>
				</c:if>
				
			</div>
			<div class="nav clear">
				<b class="lt">投保信息</b>
				<div class="rt"><div>详情</div><div class="arr"></div></div>
			</div>
			<div class="con disn">
				<c:if test="${inquiryDTO.tciSign == '1' }">
				<div class="clear">
					<div class="lt">交强险</div>
					<div class="rt"><fmt:formatDate value="${inquiryDTO.tciStartDate }" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${inquiryDTO.tciEndDate }" pattern="yyyy-MM-dd"/></div>
				</div>
				</c:if>
				<c:if test="${inquiryDTO.vciSign == '1'}">
					<div class="clear">
						<div class="lt">商业险</div>
						<div class="rt" id="vciDate">${inquiryDTO.vciStartDateStr } 至 ${inquiryDTO.vciEndDateStr }</div>
					</div>
					<c:forEach items="${platforms }" var="item">
						<div class="clear">
							<div class="lt">${item.remark }</div>
							<%-- 为了这里显示好看。。要挨个挨个险种判断显示.. --%>
							<c:if test="${(item.code == '030006' || item.code == '030061' || 
								item.code == '030012' || item.code == '032608' ||
								item.code == '032618' || item.code == '033008' || 
								item.code == '030015' || item.code == '033003' || 
								item.code == '030021' || item.code == '030032' || 
								item.code == '033018' || item.code == '033019') }">
								<div class="rt c3">投保</div>
							</c:if>
							<c:if test="${(item.code == '030018' || item.code == '030001' || item.code == '032601')   }">
								<div class="rt c2">${item.sumLimit }万</div>
							</c:if>
							<c:if test="${(item.code == '030009')   }">
								<div class="rt c2">${item.sumLimit/(inquiryDTO.seatNum-1)} 万/座</div>
							</c:if>
							<c:if test="${(item.code == '030004')   }">
								<div class="rt c3">投保</div>
							</c:if>
						</div>
					</c:forEach>
					<c:if test="${isNoDduct }">
						<div class="clear">
							<div class="lt">不计免赔</div>
							<div class="rt c3">投保</div>
						</div>
					</c:if>
				</c:if>
			</div>
		</div>
		<div class="edit txr clear">
			<div onclick="backOn()" style="margin-right:10px;padding:7px 9px;border-radius:3px;color:white;font-size:14px;margin-bottom:5px;" class="rt btn bc2" >修改信息</div>
		</div>
		<div class="tip" id="mytip">以下报价是为各保险公司的标准价格，若需确定最终的承保价格和方案，请通过投保操作提交保险公司审核。</div>
		<div class="company">
			<c:forEach items="${insurances}" var="ins">
				<div class="box clear" quotaId="" quotaType="" status="" showType="" insName="${ins.insName}" errorMessage="" id="${ins.insId}">
					<div class="lt img bgc" style="background-image:url(${cxt}images/ins_logo/${fn:substring(ins.insId, 0, 4)}.png);" ></div>
					<c:choose>
						<c:when test="${ins.quotnType eq 'A' }">
							<div class="rt btn bc2 getQuotes" quotnType="${ins.quotnType }" >立即报价</div>
						</c:when>
						<c:otherwise>
							<div class="rt btn bc2 getQuotes btnCol" quotnType="${ins.quotnType }" >人工报价</div>
						</c:otherwise>
					</c:choose>
					<b class="line1 c2 disb"></b>
					<div class="line2"></div>
					<div class="line22 ratio"></div>
					<div class="no zwbj" style="display:none;">暂无报价</div>
					
				</div>
			</c:forEach>
			<c:forEach items="${quoteds}" var="quoted">
				<div class="box clear" <c:if test="${quoted.warns!= null || fn:length(quoted.warns) > 0}">style="position:relative;"</c:if> inquiryId="${quoted.inquiry.inquiryId}"  orderId="${quoted.orderId}"  quotaId="${quoted.quotaId }" quotaType="${quoted.quotaType }" showType="${quoted.showType }" status="${quoted.orderStatus }" errorMessage="" insName="${quoted.insurance.insName}" id="${quoted.insurance.insId}">
					<div class="lt img bgc" data-ins-name="${quoted.insurance.insName}"  style="background-image:url(${cxt}images/ins_logo/${fn:substring(quoted.insurance.insId, 0, 4)}.png);"></div>
					<c:if test="${quoted.warns!= null || fn:length(quoted.warns) > 0}"><div class="warnsIcon" style="">
						<c:forEach items="${quoted.warns }" var="warn">
							<div class="clear" style="display: none;">${warn }</div>
						</c:forEach></div>
					</c:if>
					<!-- 没有订单状态，可以投保 -->
					<c:if test="${quoted.quotaType eq 'A' and empty quoted.orderStatus}">
						<div class="rt btn bc4" onclick="quoteInfo('${quoted.insurance.insId}','${quoted.quotaId}','${quoted.inquiryId}')">
								报价详情
						</div>
					</c:if>
					<!-- 订单暂存，可以投保 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '1'}">
						<div class="rt btn bc4" onclick="quoteInfo('${quoted.insurance.insId}','${quoted.quotaId}','${quoted.inquiryId}')">
								报价详情
						</div>
					</c:if>
					<!-- 核保失败 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '3'}">
						<div class="rt btn bc4" onclick="quoteInfo('${quoted.insurance.insId}','${quoted.quotaId}','${quoted.inquiryId}')">
								报价详情
						</div>
						
					</c:if>
					<!-- 等待人工核保 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '2'}">
						<div style="float: right;margin-top: 10%;color: black;margin-right: 10px;">等待人工核保</div>
						
					</c:if>
					<!-- 核保通过待缴费 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '4' and quoted.insurance.insId ne 'PAIC'}">
						<div class="rt btn bc2" style="padding-left:31px;padding-right:31px;"  inquiryId="${quoted.inquiry.inquiryId}" quotaId="${quoted.quotaId}" orderId="${quoted.orderId}" insId="${quoted.insurance.insId}"
						  onclick="payCheck(this,'1');">支付</div>
						
					</c:if>
					<!-- 核保通过待开单 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '4' and quoted.insurance.insId eq 'PAIC'}">
					<!-- 核保通过待开单 -->
						<div class="rt btn bc2"  style="padding-left:31px;padding-right:31px;"   inquiryId="${quoted.inquiry.inquiryId}" quotaId="${quoted.quotaId}" orderId="${quoted.orderId}" insId="${quoted.insurance.insId}"
						  onclick="payCheck(this,'1');">支付</div>
						<td class="quota_result guota-message">
	
							
						</td>
					</c:if>
					<!-- 缴费成功 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '5'}">
						<div class="rt btn bc4">缴费成功</div>
					</c:if>
					<!-- 已经生成保单 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '6'}">
						<div class="rt btn bc4" >已经生成保单</div>
					</c:if>
					<!-- 核保中 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '13'}">
						<div class="rt btn bc4">核保中</div>
					</c:if>
					<!-- 已开单 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '14'}">
						<!-- 已开单 -->
						<div class="rt btn bc2"  style="padding-left:31px;padding-right:31px;"  inquiryId="${quoted.inquiry.inquiryId}" quotaId="${quoted.quotaId}" orderId="${quoted.orderId}" insId="${quoted.insurance.insId}" 
						 onclick="payCheck(this,'1');">支付</div>
						
					</c:if>
	
					<!-- 人工报价状态 -->
					<c:if test="${quoted.quotaType eq 'M'  and ('2' eq quoted.manualQuotationTaskDTO.taskStatus or '1' eq quoted.manualQuotationTaskDTO.taskStatus)}">
						<div style="margin: 35px 5px 0 2px;float: right;color: black;">人工报价处理中，<br/>请稍后查看报价结果</div>
					</c:if>
					<!-- 投保 -->
					<c:if test="${quoted.quotaType eq 'M' and '3' eq quoted.manualQuotationTaskDTO.taskStatus}">
						<div  class="rt btn bc4"  onclick="quoteInfo('${quoted.insurance.insId}','${quoted.quotaId}','${quoted.inquiryId}')">
								报价详情
						</div>
					</c:if>
					<!-- 报价失败 -->
					<c:if test="${quoted.quotaType eq 'M' and '4' eq quoted.manualQuotationTaskDTO.taskStatus}">
						<div  class="rt btn bc6" onclick="viewManualQuotationFailureReason('${quoted.quotaId}','4','${quoted.inquiryId }')">
								报价失败原因
						</div>
					</c:if>
					<!-- 待核保 -->
					<c:if test="${quoted.quotaType eq 'M' and ('6' eq quoted.manualQuotationTaskDTO.taskStatus or '7' eq quoted.manualQuotationTaskDTO.taskStatus)}">
						<!-- <div class="rt btn bc4" >核保中</div> -->
						<div style="margin: 35px 5px 0 2px;float: right;color: black;">稍后查看核保结果吧</div>
					</c:if>
					<!-- 待支付 -->
					<c:if test="${quoted.quotaType eq 'M' and '8' eq quoted.manualQuotationTaskDTO.taskStatus}">
					    <c:choose>
					       <c:when test="${'TPIC' eq quoted.insurance.insId}">
					           <div class="rt btn bc2" style="padding-left:31px;padding-right:31px;" onclick="dataQuery(this, '${quoted.orderId}','${quoted.insurance.insId}', '${quoted.quotaId}')">刷新</div>
					       </c:when>
					       <c:otherwise>
					           <div class="rt btn bc2" style="padding-left:31px;padding-right:31px;" onclick="viewPayType()">支付</div>
					       </c:otherwise>
					    </c:choose>
					</c:if>
					<!-- 核保失败 -->
					<c:if test="${quoted.quotaType eq 'M' and '9' eq quoted.manualQuotationTaskDTO.taskStatus }">
						<div  class="rt btn bc6" onclick="viewManualQuotationFailureReason('${quoted.quotaId}','9','${quoted.inquiryId }')">
								核保失败原因
						</div>
					</c:if>
					<!-- 已经生成保单 -->
					<c:if test="${quoted.quotaType eq 'A' and  quoted.orderStatus eq '6'}">
						<div class="rt btn bc4" >已经生成保单</div>
					</c:if>
					<c:if test="${quoted.amount > 0}">
						<b class="line1 c2 disb">¥${quoted.amount}</b>
						<div class="line2"><c:if test="${quoted.discount gt 0}">
								<!-- 折扣这里动态显示 -->
								折扣：${quoted.discount}
							</c:if>
							<c:if test="${quoted.lastYearClaimNum gt 0 }">
								出险${quoted.lastYearClaimNum}次
							</c:if></div>
						<div class="line22 ratio">
							<c:if test="${not empty quoted.ratio and quoted.ratio ne ''}">
								<span class="eye-top">
									<span class="eye"><img src="${cxt}images/yan_off.png"/></span>
									<span class="ratio-value">奖金：${quoted.ratio}</span>
								</span>
							</c:if>
						</div>
					</c:if>
				</div>	
			</c:forEach>
			<c:forEach items="${insurancesTwo}" var="ins">
				<div class="box clear" quotaId="" quotaType="" status="" showType="" insName="${ins.insName}" errorMessage="" id="${ins.insId}">
					<div class="lt img bgc" data-quotaid=""  style="background-image:url(${cxt}images/ins_logo/${fn:substring(ins.insId, 0, 4)}.png);"></div>
					<c:choose>
						<c:when test="${ins.quotnType eq 'A' }">
							<div class="rt btn bc2 getQuotes" quotnType="${ins.quotnType }" >立即报价</div>
						</c:when>
						<c:otherwise>
							<div class="rt btn bc2 getQuotes btnCol" quotnType="${ins.quotnType }" >人工报价</div>
						</c:otherwise>
					</c:choose>
					<b class="line1 c2 disb"></b>
					<div class="line2"></div>
					<div class="line22 ratio"></div>
					<div class="no zwbj" style="display:none;">暂无报价</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<script type="text/javascript">
		var ctx = "${ctx}";
		var inquiryId="${inquiryId}";
		// 报价失败的保险公司数量
		var failIns = '${failIns}';
		// 报价失败的错误信息
		var errorMessages = [];
		var haveOrder = '${haveOrder }';
		var usageCode = '${inquiryDTO.usageCode}';
	</script> 
	<script type="text/javascript" src="${ctx }v3/js/comprice_ui.js"></script>
</body>
</html>