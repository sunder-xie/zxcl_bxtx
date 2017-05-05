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
	   System.out.println(request.getRequestURI());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
		 System.out.println(request.getRequestURI());
	}
%> 

<html>
<head>
<!-- Developer:tian.kejun[田可军] date:20150620-->
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body>
<!--横屏显示和loading start-->
<div class="cover">
        <div class="iphone" ><img src="${ctx}images/iphone.png"/></div>
        <p>为了更好的用户体验，请将屏幕竖向浏览！</p>
    </div>
<!-- <div class="loading"> -->
<!-- 	<div class="load-container load6"> -->
<!-- 	  <div class="loader">Loading...</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!--横屏显示和loading end-->

	<!--主体内容 start-->
	<div class="wrapper mb15">
		<div class="wrap_top">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/back.png"></a>
				<a class="logo"><img src="${ctx}images/logo.png"></a>
				<a class="tel"  href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
			</div>
		</div>
		<div class="chunk mb5">
			<div class="con mauto">
				<h2 class="mb5">详细列表</h2>
				<div class="searchBox clearfix">
					<a class="fl btns  abc">待核保</a> 
					<a class="fr btns  ddd">待申请核保</a>
				</div>
				<table class="orderList" cellpadding="0" cellspacing="0">
					<tr class="bg33c fcfff fb">
						<td width="25%">车牌</td>
<!-- 						<td width="23%">保费</td> -->
						<td width="25%">姓名</td>
						<td width="25%">状态</td>
						<td width="25%">操作</td>
					</tr>
<%-- 					 <c:forEach items="${orderList}" var="order" varStatus="i">						 --%>
<%-- 						<tr class="bb1e4" id="tabletr${i.index}" onclick="showInquiry('${order.quota.quotaId}','${order.queryStage }')"> --%>
<%-- 								<c:if test="${empty order.orderId and not empty  order.quota.quotaId }"> --%>
<%-- 									<td>${order.inquiry.plateNo }</td> --%>
<%-- 									<td>￥${order.quota.totalAmount }</td>  --%>
<%-- 									<td>${order.inquiry.ownerName }</td> --%>
<!-- 									<td ><span>已报价</span> </td> -->
<%-- 									<td><a class="smallBtn" href="premium.do?quotaId=${order.quota.quotaId }&insId=${order.insurance.insId }&ownerName=${order.inquiry.ownerName }">核保</a></td> --%>
<%-- 									<td><a class="smallBtn" href="comPrice.do?code=${order.insurance.insId }&ownerName=${order.inquiry.ownerName }&inquiryId=${order.inquiry.inquiryId}">核保</a></td> --%>
<%-- 								</c:if> --%>
<%-- 								<c:if test="${order.queryStage eq '1' and not empty order.quota.quotaId}" > --%>
<%-- 									<td>${order.inquiry.plateNo }</td> --%>
<%-- 									<td>￥${order.quota.totalAmount }</td>  --%>
<%-- 									<td>${order.inquiry.ownerName }</td> --%>
<!-- 									<td class="bbb"><span>核保中</span> </td> -->
<%-- 									<td><a class="smallBtn aaa"  code="${orderId.orderId }" value="${order.insurance.insId}">刷新</a></td> --%>
<%-- 								</c:if> --%>
<%-- 								<c:if test="${order.queryStage eq '2' and not empty order.quota.quotaId}" > --%>
<%-- 									<td>${order.inquiry.plateNo }</td> --%>
<%-- 									<td>￥${order.quota.totalAmount }</td>  --%>
<%-- 									<td>${order.inquiry.ownerName }</td> --%>
<!-- 									<td class="bbb"><span>核保中</span></td> -->
<%-- 									<td><a class="smallBtn aaa"  code="${order.orderId }" value="${order.insurance.insId}">刷新</a></td> --%>
<%-- 								</c:if> --%>
<!-- 							</tr> -->
<%-- 							<c:if test="${empty order.orderId and not empty  order.quota.quotaId }"> --%>
<%-- 								<c:forEach items="${quotas }" var="quota"> --%>
<%-- 									<c:if test="${quota.quotaId eq order.quota.quotaId and quota.inquiry.inquiryId eq order.inquiry.inquiryId }"> --%>
<%-- 										<tr id="${order.quota.quotaId }" class="bb1e4" style="display: none;"> --%>
<%-- 											<td><input type="hidden" name="${order.quota.quotaId }" value="0"/></td> --%>
<%-- 											<td>${quota.insurance.insName }</td> --%>
<%-- 											<td>￥${quota.totalAmount }</td> --%>
<%-- 											<td><a href="premium.do?insId=${quota.insurance.insId }&quotaId=${quota.quotaId }&ownerName=${quota.inquiry.ownerName}" class="smallBtn bbb">投保</a></td> --%>
<!-- 										</tr>									 -->
<%-- 									</c:if> --%>
<%-- 								</c:forEach> --%>
<%-- 							</c:if> --%>
<%-- 					</c:forEach> --%>
						<c:forEach items="${inquiryList }" var="inquiry" varStatus="i">
							<tr class="bb1e4" id="tableter${i.index }" onclick="showInquiry('${inquiry.inquiryId }','${inquiry.queryStage }')">
								<input type="hidden" id="queryStage" value="${inquiry.queryStage }">
								<c:if test="${empty inquiry.order and not empty inquiry.quotaList}">
									<td>${inquiry.plateNo }</td>
									<td>${inquiry.ownerName }</td>
									<td>已报价</td>
									<td></td>
								</c:if>
								<c:if test="${not empty inquiry.order and inquiry.order.status eq '1'}">
									<c:if test="${inquiry.queryStage eq '1' }">
										<td>${inquiry.plateNo }</td>
										<td>${inquiry.ownerName }</td>
										<td>核保中</td>
										<td><a class="smallBtn aaa"  code="${inquiry.order.orderId }" value="${inquiry.insId}">刷新</a></td>
									</c:if>
									<c:if test="${inquiry.queryStage eq '2' }">
										<td>${inquiry.plateNo }</td>
										<td>${inquiry.ownerName }</td>
										<td>核保中</td>
										<td><a class="smallBtn aaa"  code="${inquiry.order.orderId }" value="${inquiry.insId}">刷新</a></td>
									</c:if>
								</c:if>
							</tr>
							<c:forEach items="${inquiry.quotaList }" var="quota">
								<tr id="${inquiry.inquiryId }" class="bb1e4" style="display: none;">
									<td></td>
									<td>${quota.insurance.insId }</td>
									<td>￥${quota.totalAmount }</td>
									<td><a href="${ctx}toquota.do?inquiryId=${inquiry.inquiryId}&useType=Q" class="smallBtn bbb">报价</a></td>
								</tr>
							</c:forEach>
						</c:forEach>
				</table>
				<br/>
			</div>
		</div>
	</div>
	<!--主体内容 end-->
	<!--导航部分-->
		<jsp:include page="../commons/menu.jsp"/>
	<div class="wrap_result dbn" id="select_result">
		<div class="result_box">
			<div class="showBtn">
				<div class="result_top">查询结果</div>
				<p class="result_con" id="resultInfo">订单状态<br/>请稍后查询保单</p>
				<div class="result_bottom">
					<a class="btn_l result_close">取消</a>
					<a class="btn_r result_close" id="result_show">查看</a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/rote.jsp"></jsp:include>
<!--<script type="text/javascript" src="js/zepto.min.js"></script>-->
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<%-- <script type="text/javascript" src="${ctx}js/prettify.js"></script> --%>
<script type="text/javascript" src="${ctx}js/main.js" charset="UTF-8"></script>
<script type="text/javascript">
changeMenuTab(2);
var overallInquiryId = null;
function showInquiry(inquiryId,queryStage){
	if('1' == queryStage || '2' == queryStage){
		return;
	}
	if(null != inquiryId && '' != inquiryId && undefined != inquiryId){
		
		if(overallInquiryId != null){
			document.getElementById(overallInquiryId).style.display="none";
		}
		overallInquiryId = inquiryId;
		document.getElementById(inquiryId).style.display="";
		
		
// 		if($('input[name='+inquiryId+']').val() == 0){			
// 			document.getElementById(inquiryId).style.display="";
// 			$('input[name='+inquiryId+']').val(1);
// 		}else{
// 			document.getElementById(inquiryId).style.display="none";
// 			$('input[name='+inquiryId+']').val(0);
// 		}
	}
}

$(document.body).ready(function(){
	//判断小薇费率
	var micStatus="${sessionScope.MICROTE_SESSION}";
	if(micStatus&&micStatus==1){
		$("#rote").show();
	}
	
	$(".result_close").on("click",function(){
		$("#select_result").hide();
// 		$("#result_show").removeClass("result_close");
		$("#result_show").html("查看");
	})
	//为了防止多次点击
	 var date = new Array();
	//刷新
	$(".aaa").live("click",function(){
		
		//处理在一秒内多次点击
	      date.push(new Date());
	      if (date.length > 1&& (date[date.length - 1].getTime() - date[date.length - 2].getTime() < 1000)) {//小于1秒则认为重复提交
	          event.cancelBubble = true; 
	          return false;
	      }
		
		var showResult="";
		setTimeout(function(){if(!showResult){$("#select_result").show();}}, 2000);
		
		var orderId=$(this).attr("code");
		var insId=$(this).attr("value");
		var queryStage=$("#queryStage").val();
		var trIndex=$(this).closest("tr").attr("id");
		$.ajax({
  			url:"${ctx}queryInsureApplicate.do?orderId="+orderId+"&insId="+insId+"&queryStage="+queryStage,
			dataType:"json",
			type:"post",
			success:function(result){
				showResult=1;
				if(result.succ){
					console.dir(result.data);
					var tr="";
					if(result.data.queryStage=='2'||result.data.queryStage=='1'){
						$("#resultInfo").html("当前订单正在核保中..");
						$("#result_show").removeAttr("href");
						$("#result_show").html("确定");
						$("#select_result").show();
// 						var tr= tr +"<td>"+result.data.inquiry.plateNo+"</td>"
// 							+"<td>￥"+result.data.quota.totalAmount+"</td>"
// 							+"<td>"+result.data.inquiry.ownerName+"</td>";
// 						tr=tr+"<td ><span>核保中</span></td>";
// 				    	tr=tr+'<td><a class="smallBtn aaa"  code='+result.data.orderId +' value='+result.data.insurance.insId+'>刷新</a></td>' ;
// 				    	tr=tr+'<input type="hidden" id="queryStage" value="'+result.data.queryStage +'">';
					}else if(result.data.queryStage=='3'){
						$("#resultInfo").html("当前订单人工核保不通过,请重新核保！！");
						$("#result_show").attr("href","${ctx}list4.do");
						$("#select_result").show();
					    tr="<td>"+result.data.inquiry.plateNo+"</td>"
// 						+"<td>￥"+result.data.quota.totalAmount+"</td>"
						+"<td>"+result.data.inquiry.ownerName+"</td>";
						+"<td ><span>核保中</span></td>";
				    	+'<td><a class="smallBtn aaa"  code='+result.data.orderId +' value='+result.data.insurance.insId+'>刷新</a></td>' ;
				    	+'<input type="hidden" id="queryStage" value="'+result.data.queryStage +'">';
					}else if(result.data.queryStage=='4'){
						$("#resultInfo").html("当前订单核保已经通过了,是否前往查看？");
						$("#result_show").attr("href","${ctx}list2.do");
						$("#select_result").show();
						$(this).closest("tr").remove();
					}
					if(tr!=""){
						$("#"+trIndex).html(tr); 
					}
				}else{
					$("#resultInfo").text(result.msg);
					$("#select_result").show();
				}
			}
		});
	}); 
	
	$(".abc").live("click",function(){
		console.log("111");
		$.ajax({
  			url:"${ctx}queryWaitInsureApplicateOne.do",
  			data:'status=1&queryStage1=1&queryStage2=2&type=1',
			dataType:"json",
			type:"post",
			success:function(result){
				console.log("111"+result);
				console.dir(result);
				if(result.succ){
					$(".bb1e4").remove();
					var info = [];
					for (var i = 0; i < result.data.length; i++) {
					   /* var tr="";
					   if(result.data[i].orderId!="" && result.data[i].orderId!=null && (result.data[i].queryStage=='2' || result.data[i].queryStage=='1')){
						   tr="<tr class='bb1e4' id='tabletr"+i+"'><td width='23%'>"+result.data[i].inquiry.plateNo+"</td>"
							+"<td>￥"+result.data[i].quota.totalAmount+"</td>"
							+"<td>"+result.data[i].inquiry.ownerName+"</td>"
							+"<td><span>核保中</span></td>"
					    	+'<td><a class="smallBtn aaa"  code='+result.data[i].orderId +' value='+result.data[i].insurance.insId+'>刷新</a></td>'
					    	+'<input type="hidden" id="queryStage" value="'+result.data[i].queryStage +'/"></tr>'
					    }
						$(".orderList").append(tr); */
// 						if(result.data[i].order!=null&&result.data[i].order.status=="1"){
// 							if(result.data[i].queryStage == '2' || result.data[i].queryStage == '1'){
// 								var tr='<tr class="bb1e4" id="tabletr'+i+'"><td>'+result.data[i].plateNo+
// 										'</td><td>'+result.data[i].ownerName+'</td><td>核保中</td>'+
// 										'<td><a class="smallBtn aaa"  code='+result.data[i].order.orderId+' value='+result.data[i].insId+'>刷新</a></td></tr>';
// 							    $(".orderList").append(tr);
// 							}
// 						}
						info.push('<tr class="bb1e4" id="tabletr'+i+'"><td>'+result.data[i].plateNo+
								'</td><td>'+result.data[i].ownerName+'</td><td>核保中</td>'+
								'<td><a class="smallBtn aaa"  code='+result.data[i].order.orderId+' value='+result.data[i].insId+'>刷新</a></td></tr>');
					}
					$(".orderList").append(info.join(''));
					info = [];
				}
			},
			  error: function (XMLHttpRequest, textStatus, errorThrown)
		        {
		            alert('访问网络失败！' + errorThrown);
		        }
		});
	});
	
	$(".ddd").live("click",function(){
		$.ajax({
  			url:"${ctx}queryWaitInsureApplicateOne.do",
  			data:"type=2",
			dataType:"json",
			type:"post",
			success:function(result){
				if(result.succ){
					console.dir(result.data);
					var length=result.data.length;
					$(".bb1e4").remove();
					var info = [];
					for (var i = 0; i < length; i++) {
// 						var tr="";
// 					   if(result.data[i].orderId=="" || result.data[i].orderId==null){
// 						   tr="<tr class='bb1e4' id=tabletr"+i+"><td width='23%'>"+result.data[i].inquiry.plateNo+"</td>"
// 							+"<td>￥"+result.data[i].quota.totalAmount+"</td>"
// 							+"<td>"+result.data[i].inquiry.ownerName +"</td><td ><span>已报价</span></td>"
// 					    	+"<td><a class='smallBtn aaa' href='comPrice.do?code="+result.data[i].insurance.insId+"&ownerName="+
// 					    			result.data[i].inquiry.ownerName+"&inquiryId="+result.data[i].inquiry.inquiryId+"'>核保</a></td>"; 
// 					    	+'<input type="hidden" id="queryStage" value="'+result.data[i].queryStage +'"></tr>';
// 					    }
// 						$(".orderList").append(tr);
// 						if(result.data[i].order==null&&result.data[i].quotaList.length > 0){
// 						   var tr='<tr class="bb1e4" id="tabletr'+i+'" onclick="showInquiry(\''+result.data[i].inquiryId+'\',\''+result.data[i].queryStage+'\')"><td>'+result.data[i].plateNo+
// 									'</td><td>'+result.data[i].ownerName+'</td><td>已报价</td><td></td></tr>';
// 							for(var o in result.data[i].quotaList){
// 								tr+='<tr id="'+result.data[i].inquiryId+'" class="bb1e4" style="display: none;"><td>'+
// 								'</td><td>'+result.data[i].quotaList[o].insurance.insId +'</td><td>￥'+result.data[i].quotaList[o].totalAmount +'</td>'+
// 								'<td><a href="premium.do?insId='+result.data[i].quotaList[o].insurance.insId +'&quotaId='+result.data[i].quotaList[o].quotaId +'&ownerName='+result.data[i].ownerName+'" class="smallBtn bbb">核保</a></td></tr>';
// 							}
// 							$(".orderList").append(tr);
// 						}
						
						info.push('<tr class="bb1e4" id="tabletr'+i+'" onclick="showInquiry(\''+result.data[i].inquiryId+'\',\''+result.data[i].queryStage+'\')"><td>'+result.data[i].plateNo+
								'</td><td>'+result.data[i].ownerName+'</td><td>已报价</td><td></td></tr>');
						for(var o in result.data[i].quotaList){
							info.push('<tr id="'+result.data[i].inquiryId+'" class="bb1e4" style="display: none;"><td>'+
									'</td><td>'+result.data[i].quotaList[o].insurance.insId +'</td><td>￥'+result.data[i].quotaList[o].totalAmount +'</td>'+
									'<td><a href="${ctx}toquota.do?inquiryId='+result.data[i].inquiryId+'&useType=Q" class="smallBtn bbb">核保</a></td></tr>');
						}
					}
					$(".orderList").append(info.join(''));
					info = [];
				}
			}
		});
	});
});


</script>
</body>
</html>