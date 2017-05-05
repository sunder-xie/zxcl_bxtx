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
		.tab{table-layout:fixed;}
		.tab tr td:nth-child(3){width:33%;}
		.tab tr td:nth-child(4){width:28%;}
		.tab tr td:nth-child(5){width:20%;}
		.tab tr td:nth-child(6){width:20%;}
		.chaxun .tab td{
			padding:13px 1px;
		}
	</style>
</head>
<body class="chaxun zhcx">
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">综合查询</p></div>
	<div class="search">
		<div class="rt bc2" id="btn_query">搜索</div>
		<div class="div"><input id="queryParameter" class="inp boxSet" placeholder="搜索车牌   车主姓名" /></div>
	</div>
	<table id="datas" class="tab" cellspacing="0" cellpadding="0">
		
	</table>
	<input type="hidden" id="currPage" value="1"/>
	<div class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
	<div class="fixedbg2" style="display: none;"></div>
	<div class="tipsel txc" id="pinganHttpConfirm" style="display: none; margin-top: -68px;">
		<div class="tip">请选择你所需操作</div>
		<div class="btns clear">
			<div class="pahd lt boxSet c1 pingan-http-payed">已支付,查询保单</div>
			<div class="pahd lt boxSet c1 pay pingan-http-topay">线上支付</div>
		</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<jsp:include page="../commons/loading.jsp" />
	
	<script type="text/javascript">
$(function(){
	setNav(3);
	
	window.onkeydown= function(e){
	  e = e || window.event;
	  if(e.keyCode == 13){
			 var triggerElement = document.activeElement;
			 var inputId = triggerElement.id;
			 if('queryParameter' == inputId)	 
				 $('#btn_query')[0].click();
			 //$(triggerElement).next('input').focus();
	  }
	}
	
	$('#return_close').on('click',function(){
		$('#return_id').hide();
	});
	$('.pahd').on('click',function(){
		$('.fixedbg2,#pinganHttpConfirm').hide();
	});
});

changeMenuTab(2);

function reQuota(obj){
	
	window.location.href=$(obj).attr("url");
	
}

/**
 * 支付
 */
function payCheck(e,type){
	var orderId=$(e).attr("orderId");;
	var insId=$(e).attr("insId");
	var quotaId=$(e).attr("quotaId");
	var inquiryId=$(e).attr("inquiryId");
	objPay = e;
	if(insId == null || '' == insId || 'undefined' == insId || 'NO_CHECK' == insId){
		insId = $(e).attr("insId2");
	}
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
							window.location.href='${ctx}insuranceType.do?inquiryId=${inquiryId}&useType=1&haveOrder=1';
						});
					}else if('3' == result){
						dialog("暂不支持线上支付");
					}
					if('0' == result || '2' == type){					
						$(".load").show();
						var url = '';
						if('JTIC01' == insId <c:if test="${IS_USE_YONGCHENG_NEW_INTERFACE != '1'}"> || 'AICS' == insId </c:if>){//锦泰
							url="${ctx}allinpayContro.do?orderId="+orderId+"&insId="+insId;
						}else if('PAIC' == insId || 'PICC' == insId || 'CICP' == insId || 'JTIC' == insId || 'TPBX' == insId <c:if test="${IS_USE_YONGCHENG_NEW_INTERFACE == '1'}"> || 'AICS' == insId </c:if>){
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
};

function showDetail(obj,inquiryId,state){
	var ctrIndex = 'c' + inquiryId;
	// state=1 刷新tr
	if(state == '1'){
		$(obj).removeAttr('id').find('img').attr('src','${ctx}v3/images/kai.png');
		//$('.bg').hide();
		$(obj).nextUntil($('tr').not('.bg'),'tr').remove();
	}
	
	 $.ajax({
			url:"${ctx}infoView.do?inquiryId="+inquiryId,
			dataType:"json",
			type:"post",
			success:function(result){
				var info = [];
				var str = '';
				for ( var o in result.data) {
					var tdClass = '';
					if('3' == result.data[o].inquiry.state){
						tdClass = 'class="sty"';
					}
					var strUrl="";
					if(result.data[o].quotaType=="A"){
						//投保
						if(result.data[o].status=="0"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="javascript:goQuota(\''+result.data[o].inquiry.inquiryId+'\',\'1\',\'\',\''+result.data[o].quotaId+'\');">投保</a></td>'+
							'</tr>';
						}
						else if(result.data[o].status=="-1"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="javascript:goQuota(\''+result.data[o].inquiry.inquiryId+'\',\'1\',\'\',\''+result.data[o].quotaId+'\');">投保</a></td>'+
							'</tr>';
						}else if(result.data[o].status=="4"){
							if(result.data[o].insurance.insId=="PAIC"){
								str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
								'<td>'+result.data[o].insCompanyName +'</td>'+
								'<td>￥'+result.data[o].totalAmount+'</td>'+
								'<td  class="a-r">待支付</td>'+//待开单
								'<td'+tdClass+'><a class="btn bc2" inquiryId="'+result.data[o].inquiry.inquiryId+'" quotaId="'+result.data[o].quotaId+'" orderId="'+result.data[o].orderId+'" insId="'+result.data[o].insurance.insId+'" onclick="payCheck(this,\'1\');">支付</a></td>'+
								'</tr>';
							}
							else if(result.data[o].insurance.insId=="PICCHTTP"){//人保核保后给个刷新按钮
								str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
								'<td>'+result.data[o].insCompanyName +'</td>'+
								'<td>￥'+result.data[o].totalAmount+'</td>'+
								'<td  class="a-r">已核保</td>'+
								'<td '+tdClass+'><a class="btn bc2" orderId="'+result.data[o].orderId+'" status="'+5+'" inquiryId="'+result.data[o].inquiry.inquiryId+'" quotaId="'+result.data[o].quotaId+'" insId="'+result.data[o].insurance.insId+'" onclick="reqData(this,\''+1+'\');">刷新</a></td>'+
								'</tr>';
							}
							else{
								str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
								'<td>'+result.data[o].insCompanyName +'</td>'+
								'<td>￥'+result.data[o].totalAmount+'</td>'+
								'<td  class="a-r">'+result.data[o].statusName+'</td>'+
								'<td '+tdClass+'><a class="btn bc2" inquiryId="'+result.data[o].inquiry.inquiryId+'" quotaId="'+result.data[o].quotaId+'" orderId="'+result.data[o].orderId+'" insId="'+result.data[o].insurance.insId+'" onclick="goQuota(\''+result.data[o].inquiry.inquiryId+'\',\'2\',this,\''+result.data[o].quotaId+'\');">支付</a></td>'+
								'</tr>';
							}
						}else if(result.data[o].status=="5"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" orderId="'+result.data[o].orderId+'" status="'+5+'" inquiryId="'+result.data[o].inquiry.inquiryId+'" quotaId="'+result.data[o].quotaId+'" insId="'+result.data[o].insurance.insId+'" onclick="reqData(this,\''+1+'\');">刷新</a></td>'+
							'</tr>';
						}else if(result.data[o].status=="1"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="${ctx}premium.do?orderId='+result.data[o].orderId+
							'&insId='+result.data[o].insurance.insId+'&quotaId='+result.data[o].quotaId+'&inquiryId='+result.data[o].inquiry.inquiryId+'">继续投保</a></td>'+
							'</tr>';
						}else if(result.data[o].status=="2"){
								str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
								'<td>'+result.data[o].insCompanyName +'</td>'+
								'<td>￥'+result.data[o].totalAmount+'</td>'+
								'<td  class="a-r">'+result.data[o].statusName+'</td>'+
								'<td '+tdClass+'><a class="btn bc2" quotaId="'+result.data[o].quotaId+'" inquiryId="'+result.data[o].inquiry.inquiryId+'" orderId="'+result.data[o].orderId+'" status="'+2+'" insId="'+result.data[o].insurance.insId+'" onclick="reqData(this,\''+2+'\');">刷新</a></td>'+
								'</tr>';
						}else if(result.data[o].status=="3"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="${ctx}insuranceType.do?inquiryId='+result.data[o].inquiry.inquiryId+'">继续投保</a></td>'+
							'</tr>';
						}else if(result.data[o].status=="8"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" insId='+result.data[o].insurance.insId+' orderId='+result.data[o].orderId+' onclick="payCheck(this,\'1\');">继续支付</a></td>'+
							'</tr>';
						}else if(result.data[o].status=="6"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td><a class="btn bc2" href="${ctx}detail.do?orderId='+result.data[o].orderId+'&insId='+result.data[o].insurance.insId+'&quotaId='+result.data[o].quotaId+'">详情</a></td>'+
							'</tr>';
						}else if(result.data[o].status=="13"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">核保中</td>'+
							'<td>&nbsp;</td>'+
							'</tr>';
						}else if(result.data[o].status=="14"){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">待支付</td>'+//已开单
							'<td'+tdClass+'><a class="btn bc2" quotaId="'+result.data[o].quotaId+'" inquiryId="'+result.data[o].inquiry.inquiryId+'" orderId="'+result.data[o].orderId+'" status="'+2+'" insId="'+result.data[o].insurance.insId+'" onclick="payCheck(this,\'1\');">支付</a></td>'+
							'</tr>';
						}
					}
					if(result.data[o].quotaType=="M"){
						//投保
						if(result.data[o].orderStatus == '1'){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td></td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="${ctx}toquota.do?inquiryId='+result.data[o].inquiry.inquiryId+'&useType=Q">查看</a></td>'+
							'</tr>';
						}else if(result.data[o].orderStatus ==  '3'){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="javascript:goQuota(\''+result.data[o].inquiry.inquiryId+'\',\'1\',\'\',\''+result.data[o].quotaId+'\');">投保</a></td>'+
							'</tr>';
						}else if(result.data[o].orderStatus == '4'){
								str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
	                            '<td>'+result.data[o].insCompanyName +'</td>'+
	                            '<td></td>'+
	                            '<td  class="a-r">'+result.data[o].statusName+'</td>'+
	                            '<td '+tdClass+'><a class="btn bc2" href="${ctx}toquota.do?inquiryId='+result.data[o].inquiry.inquiryId+'&useType=Q">查看</a></td>'+
	                            '</tr>';
						}else if(result.data[o].orderStatus == '6'){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="${ctx}toquota.do?inquiryId='+result.data[o].inquiry.inquiryId+'&useType=Q">查看</a></td>'+
							'</tr>';
						}else if(result.data[o].orderStatus == '8'){
							if(result.data[o].insurance.insId=="TPIC"){//太平人工报价单核保后给个刷新按钮
                                str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
                                '<td>'+result.data[o].insCompanyName +'</td>'+
                                '<td>￥'+result.data[o].totalAmount+'</td>'+
                                '<td  class="a-r">待支付</td>'+
                                '<td '+tdClass+'><a class="btn bc2" orderId="'+result.data[o].orderId+'" status="'+5+'" inquiryId="'+result.data[o].inquiry.inquiryId+'" quotaId="'+result.data[o].quotaId+'" insId="'+result.data[o].insurance.insId+'" onclick="reqData(this,\''+1+'\');">刷新</a></td>'+
                                '</tr>';
                            }
							else{
								str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
	                            '<td>'+result.data[o].insCompanyName +'</td>'+
	                            '<td>￥'+result.data[o].totalAmount+'</td>'+
	                            '<td  class="a-r">待支付</td>'+//待开单
	                            '<td'+tdClass+'><a class="btn bc2" href="javascript:viewPayType();">支付</a></td>'+
	                            '</tr>';
							}
						}else if(result.data[o].orderStatus == '9'){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'><a class="btn bc2" href="${ctx}toquota.do?inquiryId='+result.data[o].inquiry.inquiryId+'&useType=Q">查看</a></td>'+
							'</tr>';
						}else if(result.data[o].orderStatus == '10'){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td '+tdClass+'>待出单</td>'+
							'</tr>';
						}else if(result.data[o].orderStatus == '11'){
							str = '<tr class="bb1e4 bg tr_details '+ctrIndex+'" style="display: table-row;">'+
							'<td>'+result.data[o].insCompanyName +'</td>'+
							'<td>￥'+result.data[o].totalAmount+'</td>'+
							'<td  class="a-r">'+result.data[o].statusName+'</td>'+
							'<td><a class="btn bc2" href="${ctx}detail.do?orderId='+result.data[o].orderId+'&insId='+result.data[o].insurance.insId+'&quotaId='+result.data[o].quotaId+'">详情</a></td>'+
							'</tr>';
						}
					}
					info.push(str);
					str = '';
				}
				$(obj).after(info.toString());
				$(obj).removeAttr('id').find('img').attr('src','${ctx}v3/images/kai.png');
				//$('.bg').hide();
				$(obj).nextUntil($('tr').not('.bg'),'tr').hide();
				$(obj).attr('id','img').find('img').attr('src','${ctx}v3/images/guan.png');
				$(obj).nextUntil($('tr').not('.bg'),'tr').show();
				info = [];
				$('.sty').html('');
				
			}

		});
};
$(document.body).ready(function(){
	$('#conlea').on('click',function(){$('#select_in').hide();});
	$(".close_div").on("click",function(){
		$(this).closest(".wrap_result").hide();
	});
	
	$(".next").live("click",function (){
		
		if($(this).find('.stak-star').length == 0){
		}else{
		var trIndex=$(this).closest("tr").attr("id");
		var ctrIndex= 'c'+trIndex;
		var inquiryId=$(this).children('td').eq(0).html();
		var state=$(this).children('td').eq(1).html();
		var trId = $(this).find("td:eq(0)").html();
		$('.next').each(function(){
			if($(this).attr('id') == "img" && $(this).find("td:eq(0)").html()!=trId){
				$(this).removeAttr('id').find('img').attr('src','${ctx}v3/images/kai.png');
				$(this).nextUntil($('tr').not('.bg'),'tr').hide();
			}
		});
		//已经计价成功的询价单才能点击
		if($(this).attr('id')){
			$(this).removeAttr('id').find('img').attr('src','${ctx}v3/images/kai.png');
			$(this).nextUntil($('tr').not('.bg'),'tr').hide();
		}else{
			if($(this).nextUntil($('tr').not('.bg'),'tr').size() > 0){
				$(this).removeAttr('id').find('img').attr('src','${ctx}v3/images/kai.png');
				//$('.bg').hide();
				$(this).nextUntil($('tr').not('.bg'),'tr').hide();
				$(this).attr('id','img').find('img').attr('src','${ctx}v3/images/guan.png');
				$(this).nextUntil($('tr').not('.bg'),'tr').show();
			}else{
				showDetail(this,inquiryId,'');
			}
			
			
		}

	}});
	
	var removeStyle=function(me){
		var next=me.next(".qoutaInfo");
		if(next.attr("style")){
			$(next).attr("style","");
			removeStyle(next);
		}else{
			return;
		}
	}
	
	$("#btn_query").on("click",function(){
		$("#datas").empty();
		$('#currPage').val(1);
		dataPage();
	});
	$('.wrapper-load-more').on('click',function(){
		$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
		dataPage();
	});
	$('.pingan-http-payed').live('click',function(){
		 var orderId=$(this).attr("orderId");
		 $.ajax({
		    url:"${ctx}dataQuery.do?orderId="+orderId+"&insId=PINGANHTTP",
			dataType:"json",
			type:"post",
			success:function(result){
				if(result.succ){
					dialog("订单最新状态：" + result.msg);
				}else{
					dialog("未查询到保单信息");
				}
			}
		}); 		
	});
	dataPage();
});

function goQuota(inquiryId,type,e,quoteId){
	var n_insId = $(e).attr('insId');
	if('PINGANHTTP' == n_insId){
		//平安爬虫处理:支付时弹出选择框,我已支付:取保单,去支付:跳转到支付页面
		var orderId = $(e).attr('orderId');
		$('#pinganHttpConfirm,.fixedbg2').show();
		$('.pingan-http-payed').attr('orderId',orderId);
		$('.pingan-http-topay')
			.attr('insId','NO_CHECK')
			.attr('insId2','PINGANHTTP')
			.attr('orderId',orderId)
			.attr('inquiryId',inquiryId)
			.attr('quotaId',quoteId)
			.attr('onclick','goQuota("'+inquiryId+'","2",this,"'+quoteId+'");');
	}else{
		$.ajax({
			type:'POST',
			url:'${ctx}goQuotaVerify.do',
			data:{'inquiryId':inquiryId,'quoteId':quoteId},
			dataType:'json',
			success:function(result){
				if(result.succ){
					if('1' == result.msg){//时间过期，进入修改信息页面
						dialogConfirm(result.data,"重新报价",function(){
							window.location.href='${ctx}insuranceType.do?inquiryId='+inquiryId+'&useType=1&haveOrder=1';
						});
					}else{//时间未过期，进入报价页面
						if('1' == type){
							window.location.href='${ctx}toquota.do?inquiryId='+inquiryId+'&useType=Q';						
						}else{
							payCheck(e,'1');
						}
					}
				}else{
					if('1' == result.msg){
						dialog("该订单已支付或已出单，不能再进行投保");
					}
				}
			}
		});
	}
}

function dataPage() {
	$(".wrapper-load-more").html("<img src=\"${ctx }images/status_loading.gif\" style=\"vertical-align: middle;\" />&nbsp;加载中..");
	
	
	$.ajax({
		url:"${ctx}infoQueryParameter.do",
		dataType:"json",
		type:"post",
		data:{'queryParameter':$('#queryParameter').val(),'currPage':parseInt($('#currPage').val())},
		success:function(data){
			$(".wrapper-load-more").html("加载更多");
			
			if(data.success && data.data.recordCount > 0){
				var page = data.data;
				if(page.pageCount == 1 || page.currentPage == page.pageCount){
					$('.wrapper-load-more').hide();
				}else{
					$('.wrapper-load-more').show();
				}
				var info = [];
				var str = ''; 
				var item;
				for(var i = 0;i<page.dataList.length;i++){
					item = page.dataList[i];
					var isNext = "";
					var open = '';
					if(!(item.state=='1' || item.state =='0')){
						isNext = " next ";
						open = '<span class="lt"><img class="lt" src="${ctx}v3/images/kai.png" /></span>';
					}
					
					str += '<tr onclick="" class="bb1e4 '+isNext+' '  +item.inquiryId+'" >'+
					'<td style="display:none">'+item.inquiryId+'</td>'+
					'<td style="display:none">'+item.state+'</td>';	
					str += '<td  class="td1 stak-start-init stak-star">' + open+item.plateNo+'</td>';
					str += '<td >'+item.ownerName+'</td>'+
					'<td class="a-r">'+item.stateName+'</td>';
					if(item.state=='1' || item.state =='0'){
						str += '<td><div onclick="location.href=\'${ctx}insuranceType.do?inquiryId='+item.inquiryId+'&useType=1\'" class="btn bc2">报价</div></td>';
					}else{
						str += '<td></td>';
					}
					info.push(str+'</tr>');
					str = '';
				}
				$('#datas').append(info.join(""));
				info = [];
			}else{
				$('.wrapper-load-more').hide();
				showMsg("查无记录...");
			}
		}
	});
}


function reqData(e,type){
	$(e).parent().css("height","30px");
	var loading = $("<img src=\"${ctx }images/status_loading.gif\" />");
	$(e).after(loading);
	$(e).hide();
	
	var orderId=$(e).attr("orderid");
	var insId=$(e).attr("insId");
	var status=$(e).attr("status");
	var inquiryId=$(e).attr("inquiryId");
	var quotaId=$(e).attr("quotaId");
	
	$.ajax({
		url:"dataQuery.do",
		dataType:"json",
		data:"orderId="+orderId+"&insId="+insId+"&status="+status,
		success:function(result){
			loading.remove();
			$(e).show();
			if(result.succ){
				
				if('PICCHTTP' == insId && '4' == result.data.queryStage){
					dialog("订单最新状态：已核保通过");
				}
				else{
					dialog("订单最新状态：" + result.msg);
				}

				if('6' == result.data.queryStage){
					$(e).removeAttr('onclick');
					$(e).click(function(){
						window.location.href='${ctx}detail.do?orderId='+orderId+'&insId='+insId+'&quotaId='+quotaId;
					});
					$(e).html('详情');
					$(e).parent().prev().html('保单');
				}
				if('4' == result.data.queryStage){
					if('PICCHTTP' == insId){
						$(e).parent().prev().html('已核保');
						$(e).attr('onclick', 'reqData(this,"'+1+'");');
					}
					else{
						$(e).removeAttr('onclick');
						$(e).click(function(){
							payCheck(e,'1');
						});
						$(e).html('支付');
						$(e).parent().prev().html('待支付');
					}
				}
				if('3' == result.data.queryStage){
					$(e).removeAttr('onclick');
					$(e).attr('href','${ctx}insuranceType.do?inquiryId='+inquiryId);
					$(e).html('继续投保');
					$(e).parent().prev().html('核保退回');
				}
			}else{
				var tes = '';
				if('1' == type){
					tes = '保单生成中...';
				}else{
					tes = '等待人工核保...';
				}
				dialog(tes);
			}
		}
	})
}

/* //查看失败原因
function viewManualFailureReason(quoteId,type,inquiryId){
	$.ajax({
		type:'POST',
		url:'${ctx}manualQuotation/viewManualQuotationFailureReason.do',
		data:{'quoteId':quoteId,'type':type},
		dataType:'json',
		success:function(result){
			if(result.succ){
				dialogConfirm(result.data,"修改投保信息",function(){
					$.ajax({
						type:'POST',
						url:'${ctx}manualQuotation/withdrawQuotn.do',
						data:{'inquiryId':inquiryId},
						datatype:'json',
						success:function(result){
							if(result.succ){
								window.location.href='${ctx}insuranceType.do?inquiryId='+inquiryId+'&useType=1';
							}
						}
					});
				},"取消",function(){
					tipclosefun($('#promptConfirm'));
				});
			}else{
				dialogConfirm(result.data,"修改投保信息",function(){
					window.location.href='${ctx}insuranceType.do?inquiryId='+inquiryId+'&useType=1';
				},"取消",function(){
					tipclosefun($('#promptConfirm'));
				});
			}
		}
	});
} */
//查看支付方式
function viewPayType(){
	dialog('营业网点缴费',"确定",function(){
		tipclosefun($('#promptConfirm'));
	});
	/* $.ajax({
		type:'POST',
		url:'${ctx}manualQuotation/viewPayType.do',
		data:{'insId':insId,'inquiryId':inquiryId},
		dataType:'json',
		success:function(result){
			if(result.succ){
				dialog(result.data,"确定",function(){
					tipclosefun($('#promptConfirm'));
				});
			}else{
				dialog(result.msg,"确定",function(){
					tipclosefun($('#promptConfirm'));
				});
			}
		}
	}); */
}
</script>
</body>
</html>