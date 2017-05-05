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
	<script type="text/javascript">
		$(function(){
			$('.zbnav .lt').click(function(){
				
				if(!$(this).attr('id')){
					var ind=$(this).index(),l;
					if(ind==0){l='25%';}else{l='75%';}
					$('.tab').eq(ind).show().siblings('.tab').hide();
					$('.zbnav .line').animate({left:l},600);
					$(this).attr('id','sel').siblings('.lt').removeAttr('id');
				}
				$('.tab').eq(ind).empty();
				panduan();
			});
		});
	</script>
	<style type="text/css">
		.tab tr td:nth-child(1){width:25%;}
		.tab tr td:nth-child(2){width:27%;}
		.tab tr td:nth-child(3){width:23%;}
		.tab tr td:nth-child(4){width:24%;}
	</style>
</head>
<body class="chaxun bdcx">
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">保单查询</p></div>
	<div class="zbnav txc">
		<div class="lt" id="sel" value="1">保单查询</div>
		<div class="lt" value="2">续保查询</div>
		<div class="line"></div>
	</div>
	<div class="search">
		<div class="rt bc2" id="btn_query">搜索</div>
		<div class="div"><input class="inp boxSet" id="queryCondition" placeholder="搜索车牌   车主姓名" /></div>
	</div>
	<table class="tab" id="bdcx" cellspacing="0" cellpadding="0" style="margin-bottom:0;">
		<!-- 
		<tr class="bg">
			<td>川A 4R199</td>
			<td class="c2">￥4589.00</td>
			<td>何晴</td>
			<td>
				<div class="btn btn1">详情</div>
			</td>
		</tr> -->
	</table>
	<table class="tab disn" id="renew" cellspacing="0" cellpadding="0" style="margin-bottom:0;">
		<!-- 
		<tr class="bg">
			<td>川A 4R199</td>
			<td class="c2">￥4589.00</td>
			<td>何晴</td>
			<td>
				<div class="btn bc2">续保</div>
			</td>
		</tr> -->
	</table>
	<div class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
	<input type="hidden" id="currPage" value="1"/>
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
$(function(){
	setNav(3);
	
	window.onkeydown= function(e){
	  e = e || window.event;
	  if(e.keyCode == 13){
			 var triggerElement = document.activeElement;
			 var inputId = triggerElement.id;
			 if('queryCondition' == inputId)	 
				 $('#btn_query')[0].click();
			 //$(triggerElement).next('input').focus();
	  }
	}
	//$('#result_close1').click(function(){$('#select_result1').hide();});
});
changeMenuTab(2);
$(function(){
	//判断小薇费率
	var micStatus="${sessionScope.MICROTE_SESSION}";
	if(micStatus&&micStatus==1){
		$("#rote").show();
	}
	$('#btn_query').on('click',function(){
		//$('tr.bb1e4').remove();
		$("#bdcx").empty();
		$('#currPage').val(1);
		var value = $('#sel').attr("value");
		if("1" == value){
			dataPage();
		}else{
			renew();
		}
	});
	$('.wrapper-load-more').on('click',function(){
		$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
		dataPage();
	});
	//$('#result_close').on('click',function(){$('#select_result').hide();});
	dataPage();
});
function panduan(){
	$('#currPage').val(1);
	var value = $('#sel').attr("value");
	if("1" == value){
		dataPage();
	}else{
		renew();
	}
}
function renew(){
	$(".wrapper-load-more").html("<img src=\"${ctx }images/status_loading.gif\" style=\"vertical-align: middle;\" />&nbsp;加载中..");
	var currPage = $('#currPage').val();
	$.post('${ctx}/queryList4ByItem.do',{'queryCondition':$('#queryCondition').val(), 'currPage':parseInt(currPage)},function(data){
		$(".wrapper-load-more").html("加载更多");
		if(data.success && data.data.recordCount > 0){
			var page = data.data;
			if(page.pageCount == 1 || page.currentPage == page.pageCount){
				$('.wrapper-load-more').hide();
			}else{
				$('.wrapper-load-more').show();
			}
			$("#renew").html('');
			var item;
			for(var i = 0;i<page.dataList.length;i++){
				
				item = page.dataList[i];
				
				var str = "<tr class=\"bg\"><td>"+item.plateNo +"</td><td class=\"c2\">￥"+item.totalMonStr+"</td><td>"+item.ownerName+"</td>"+
					"<td><div class=\"btn btn1\" onclick=\"location.href='${ctx}insuranceType.do?plateNo="+item.plateNo+"';\">续保</div></td></tr>";
				
				$("#renew").append(str);
			}
		}else{
			$('.wrapper-load-more').hide();
			showMsg("查无记录...");
		}
	},'json');
	
}
function dataPage(){
	$(".wrapper-load-more").html("<img src=\"${ctx }images/status_loading.gif\" style=\"vertical-align: middle;\" />&nbsp;加载中..");
	
	var currPage = $('#currPage').val();
	$.post('${ctx}/queryList3ByItem.do',{'queryCondition':$('#queryCondition').val(), 'currPage':parseInt(currPage)},function(data){
		$(".wrapper-load-more").html("加载更多");
		
		if(data.success && data.data.recordCount > 0){
			var page = data.data;
			if(page.pageCount == 1 || page.currentPage == page.pageCount){
				$('.wrapper-load-more').hide();
			}else{
				$('.wrapper-load-more').show();
			}
			$("#bdcx").html('');
			var item;
			for(var i = 0;i<page.dataList.length;i++){
				
				item = page.dataList[i];
				
				var str = "<tr class=\"bg\"><td>"+item.plateNo +"</td><td class=\"c2\">￥"+item.totalMonStr+"</td><td>"+item.ownerName+"</td>"+
					"<td><div class=\"btn btn1\" onclick=\"location.href='${ctx}detail.do?orderId="+item.orderId+"&insId="+item.insId+"&quotaId="+item.quotaId+"';\">详情</div></td></tr>";
				
				$("#bdcx").append(str);
			}
		}else{
			$('.wrapper-load-more').hide();
			showMsg("查无记录...");
		}
	},'json');
}
<%--
function renewalInsurance(inquiryId){
	$.ajax({
		type:'POST',
		url:'${ctx}renewalInsurance.do?inquiryId='+inquiryId,
		dateType:'json',
		success:function(result){
			if(result.succ){
				window.location.href='${ctx}insuranceType.do?provinceCode='+result.data.provinceCode+'&cityCode='+result.data.cityCode+'&newCarSign=0&plateNo='+result.data.plateNo;
			}else{
				$('#resultInfo1').html(result.msg);
				$('#select_result1').show();
			}
		}
	});
} --%>
</script>
</body>
</html>