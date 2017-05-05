<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
.q-item{
	margin-bottom: 10px;
	text-align: center;
	height: 60px;
	line-height:60px;
	width: 90%;
	margin: 3% auto;
	border:1px solid #2BA5E9;
	color:#2BA5E9;
	border-radius: 5px;
}
.q-item-btns{
	margin-bottom: 10px;
	text-align: center;
	height: 40px;
	line-height:40px;
	width: 90%;
	margin: 3% auto;
	border:1px dashed #dcdcdc;
	border-radius: 5px;
}
.q-item span{
	margin-right: 10%;
	float: left;
}
.q-item span.s1{
	margin-left: 10%;
}
.q-item span.s2{
	color:#D64141;
}
.q-item span.s3{
	
}
.cltbxx .div{
    padding: 0 0 ;
}
.arr {
    position: relative;
    width: 10px;
    height: 10px;
    left: 8px;
    border: 1px solid #d8d9da;
    border-top: 0;
    border-right: 0;
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    position: relative;
    left: 45%;
    top: 65%;
}
.arr2 {
    left: 45%;
    top: 55%;
}
.q-btn{
    margin-right: 10px;
    padding: 7px 9px;
    border-radius: 3px;
    color: white;
    font-size: 14px;
    margin-bottom: 5px;
}
.q-item-btn{

}
.q-item-btn{
    display: inline-block;
    border: 1px solid #D64141;
    border-radius: 5px;
    margin: 0 auto;
    padding: 2px 6px;
    height: 20px;
    line-height: 20px;
    color: white;
    background: #D64141;
    font-size: 13px;
}
.q-item-btns{
	display: none;
}
</style>
</head>
<body style="background:#fff;">
	<div class="head"><div class="back lt" onclick="window.location.href='${ctx}infoQuery.do';"></div><p class="title">报价操作</p></div>
	<div class="content bxbj content">
		<div id="base_info">
			<div class="div line1 clear"><b class="lt c3">车牌：${inquiryDTO.plateNo}</b></div>
		</div>
		<div class="tbgs">
			<div class="cltbxx">
				<div class="nav clear"> <b class="lt">车辆信息</b>
					<div class="rt">
						<div>详情</div>
						<div class="arr"></div>
					</div>
				</div>
				<div class="con disn" style="display: none;">
					<div class="clear">
						<div class="lt">品牌车型</div>
						<div class="div">
							<div class="rt">${inquiryDTO.vehicleName }</div>
						</div>
					</div>
					<div class="clear">
						<div class="lt">配置信息</div>
						<div class="div">
							<div class="rt">${inquiryDTO.remark }</div>
						</div>
					</div>
					<div class="clear">
						<div class="lt">注册日期</div>
						<div class="rt">${inquiryDTO.fstRegNo }</div>
					</div>
					<div class="clear">
						<div class="lt">车架号</div>
						<div class="div">
							<div class="rt">${inquiryDTO.frmNo }</div>
						</div>
					</div>
					<div class="clear">
						<div class="lt">发动机号</div>
						<div class="div">
							<div class="rt">${inquiryDTO.engNo }</div>
						</div>
					</div>
					<div class="clear">
						<div class="lt">是否过户</div>
						<div class="rt"><c:if test="${inquiryDTO.transferSign == '1' }">是</c:if><c:if test="${inquiryDTO.transferSign != '1' }">否</c:if></div>
					</div>
				</div>
				<div class="nav clear"> 
					<b class="lt">投保信息</b>
					<div class="rt">
						<div>详情</div>
						<div class="arr"></div>
					</div>
				</div>
				<div class="con disn" style="display: none;">
				<c:if test="${inquiryDTO.tciSign == '1'}">
					<div class="clear">
						<div class="lt">交强险</div>
						<div class="rt"><fmt:formatDate value="${inquiryDTO.tciStartDate }" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${inquiryDTO.tciEndDate }" pattern="yyyy-MM-dd"/></div>
					</div>
				</c:if>
				<c:if test="${inquiryDTO.vciSign == '1'}">
					<div class="clear">
						<div class="lt">商业险</div>
						<div class="rt">${inquiryDTO.vciStartDateStr } 至 ${inquiryDTO.vciEndDateStr }</div>
					</div>
					<c:forEach items="${platforms }" var="item">
						<div class="clear">
							<div class="lt">${item.remark }</div>
							<%-- 为了这里显示好看。。要挨个挨个险种判断显示.. --%>
							<c:if test="${(item.code == '030006' || item.code == '030061' || 
								item.code == '030012'  || item.code == '032608' ||
								item.code == '032618' || item.code == '033008')   }">
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
		</div>
		<div class="q-items">
			<c:forEach items="${newQuotaDTOList }" var="quote">
				<div class="q-item-o">
					<div class="q-item">
						<span class="s1">${quote.insCompanyName }</span><span class="s2">${quote.totalAmount }</span><span class="s3">${quote.statusName }</span>
						<div class="arr arr-common"></div>
						<div class="arr arr2 arr-common"></div>
					</div>
					<div class="q-item-btns">
						<c:if test="${quote.status eq 4 }">
							<span class="q-item-btn" quotaId="${quote.quotaId }" orderId="${quote.orderId }" insId="${quote.insurance.insId }" inquiryId="${inquiryDTO.inquiryId }" onclick="payCheck(this,'1');">去支付</span>
						</c:if>
						<c:if test="${(quote.status eq 4 ) or (quote.status eq 2) or (quote.status eq 3) }">
							<span class="q-item-btn"><a style="color: white;" href="${ctx }insuranceType.do?inquiryId=${inquiryDTO.inquiryId}&useType=1">重新报价</a></span>
						</c:if>
						<c:if test="${(quote.status eq 4 ) or (quote.status eq 2) or (quote.status eq 3) or (quote.status eq 5 ) }">						
							<span class="q-item-btn"><a style="color: white;" href="${ctx}detail.do?orderId=${quote.orderId }&insId=${quote.insurance.insId }&quotaId=${quote.quotaId }">查看明细</a></span>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
	$(function(){
		$('.nav .rt').click(function(){
			var obj=$(this).find('.arr');
			if(obj.attr('id')){obj.removeAttr('id'); obj.prev().text('详情'); $(this).parent().next().slideUp();}else{obj.attr('id','arr'); obj.prev().text('收起'); $(this).parent().next().slideDown();}
		});
		$('.q-item-o').click(function(){
			if($(this).find('.q-item-btns').is(':hidden')){
				$('.q-item-btns').hide();
				$(this).find('.q-item-btns').slideDown();
			}
			else{
				$(this).find('.q-item-btns').slideUp();
			}
		});
		$('.q-item-btn').click(function(event){
			event.stopPropagation();
		});
	});

	function payCheck(e,type){
		 var quoteId=$(e).attr("quotaId");
		 var inquiryId=$(e).attr("inquiryId");
		$.ajax({
			type:'POST',
			url:'${ctx}goQuotaVerify.do',
			data:{'inquiryId':inquiryId,'quoteId':quoteId},
			dataType:'json',
			success:function(result){
				if(result.succ){
					if('1' == result.msg){//时间过期，进入修改信息页面
						dialogConfirm("报价单已过期，需重新报价","重新报价",function(){
							window.location.href='${ctx}insuranceType.do?inquiryId='+inquiryId+'&useType=1';
						});
					}else{//时间未过期，进入支付
						payCheck1(e,type);
					}
				}
			}
		});
	};
	function payCheck1(e,type){
		 var orderId=$(e).attr("orderId");
		 var insId=$(e).attr("insId");
		 var quotaId=$(e).attr("quotaId");
		 var inquiryId=$(e).attr("inquiryId");
		objPay = e;
		$.post("${ctx}query_ins_status.do",{'insId':insId}, function(data){
			if(data.succ){
				$.ajax({
					type:'POST',
					url:'${ctx}payDateCheck.do',
					data:{'inquiryId':inquiryId,'quoteId':quotaId,'insId':insId},
					dataType:'json',
					async:false,
					success:function(result){
						if('1' == result){
							dialogConfirm("距离保单起保日期不到半小时，此时支付可能无法生成保单","继续支付",function(){
								payCheck(e,'2');
							});
						}else if('2' == result){
							dialogConfirm("报价单已过期，需重新报价","重新报价",function(){
								window.location.href='${ctx}insuranceType.do?inquiryId='+inquiryId+'&useType=1';
							});
						}else if('3' == result){
							dialog("暂不支持线上支付");
						}
						if('0' == result || '2' == type){					
							$(".load").show();
							var url = '';
							if('JTIC01' == insId <c:if test="${IS_USE_YONGCHENG_NEW_INTERFACE != '1'}"> || 'AICS' == insId </c:if>){//锦泰
								url="${ctx}allinpayContro.do?orderId="+orderId+"&insId="+insId;
							}else if('PAIC' == insId || 'JTIC' == insId || 'TPBX' == insId <c:if test="${IS_USE_YONGCHENG_NEW_INTERFACE == '1'}"> || 'AICS' == insId </c:if>){
								url = '${ctx}payController.do?quoteId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId;
								/* url='${ctx}tpbx/pay.do?quotaId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId; */
							}else if('FDCP' == insId){
								url="${ctx}fdcp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
							}else if('HHBX' == insId){
								url="${ctx}hhbx/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
							}else if('PINGANHTTP' == insId){
								url="${ctx}pinganHttp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
							}else{
								url="${ctx}pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
							}
							window.location.href=url;
						}
					}
				});
			}
			else{
				dialog(data.msg);
			}
		},'json');
		 
	}
	</script>
</body>
</html>