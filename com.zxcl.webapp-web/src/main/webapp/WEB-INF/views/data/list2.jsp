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
%>
<!DOCTYPE html>
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
					var ind=$(this).index(),w,m,l;
					if(ind==0){w='66px'; m='-35px'; l='25%';}else{w='94px'; m='-46px'; l='75%';}
					$('.zbnav .line').animate({width:w,'margin-left':m,left:l},600);
					$(this).attr('id','sel').siblings('.lt').removeAttr('id');
				}
			});
		});
	</script>
</head>
<body class="chaxun zfcx">
	<div class="head"><div class="back lt" onclick="location.href='${ctx}/index.do?nocheck=1';"></div><p class="title">支付查询</p></div>
	<div class="zbnav txc">
		<div class="lt abc" id="sel">待支付</div>
		<div class="lt ddd">待生成保单</div>
		<div class="line"></div>
	</div>
	<table class="tab orderList" cellspacing="0" cellpadding="0" style="margin-top:100px;">
	</table>
	<div class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
	<input type="hidden" id="currPage" value="1"/>
	<div class="fixedbg2" style="display: none;"></div>
	<div class="tipsel txc" id="pinganHttpConfirm" style="display: none; margin-top: -68px;">
		<div class="tip">请选择你所需操作</div>
		<div class="btns clear">
			<div class="pahd lt boxSet c1 pingan-http-payed">已支付,查询保单</div>
			<div class="pahd lt boxSet c1 pay pingan-http-topay">线上支付</div>
		</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
changeMenuTab(2);

function alerting(obj){
	var str = [];
	for(var o in obj){
		str.push(obj[o])
	}
	alert(str.join(''));
	str = [];
}
//为了防止多次点击
var date = new Array();	

function payCheck(e,type){
	$(".load").show();
	//处理在一秒内多次点击
    date.push(new Date());
    if (date.length > 1&& (date[date.length - 1].getTime() - date[date.length - 2].getTime() < 1000)) {//小于1秒则认为重复提交
        event.cancelBubble = true;
        return false;
    }
    var showResult="";
	setTimeout(function(){ if(!showResult){$("#info_msg").show();}}, 2000);
	
	var orderId=$(e).attr("code");
	var insId=$(e).attr("value");
	if(insId == null || '' == insId || 'undefined' == insId || 'NO_CHECK' == insId){
		insId = $(e).attr("value2");
	}
	var inquiryId = $(e).attr("inquiryId");
	var quotaId = $(e).attr("quotaId");
	
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
						var me=$(e);
						var url = "";
						if('JTIC00' == insId <c:if test="${IS_USE_YONGCHENG_NEW_INTERFACE != '1' }"> || 'AICS' == insId </c:if>){
							url = "${ctx}allinpayContro.do?orderId="+orderId+"&insId="+insId;
						}else if('PAIC' == insId || 'PICC' == insId || 'CICP' == insId || 'JTIC' == insId || 'TPBX' == insId <c:if test="${IS_USE_YONGCHENG_NEW_INTERFACE == '1'}"> || 'AICS' == insId </c:if> ){
							url = '${ctx}payController.do?quoteId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId;
							/* url = '${ctx}tpbx/pay.do?quotaId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId; */
						}else if('FDCP' == insId){
							url="${ctx}fdcp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}else if('HHBX' == insId){
							url="${ctx}hhbx/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}else if('PINGANHTTP' == insId){
							url="${ctx}pinganHttp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}else{
							url = "${ctx}pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+'&orderId='+orderId;
						}
						window.location.href=url;
					}
				}
		 	});	
		}
		else{
			alert(data.msg);
		}
	},'json');
}

//刷新
function referOrder(obj){
	
	//处理在一秒内多次点击
    date.push(new Date());
    if (date.length > 1&& (date[date.length - 1].getTime() - date[date.length - 2].getTime() < 1000)) {//小于1秒则认为重复提交
        event.cancelBubble = true;
        return false;
    }
    
	var orderId=$(obj).attr("code");
	var insId=$(obj).attr("value");
	var quotaId=$(obj).attr('quotaId');
	var queryStage=$(obj).closest("tr").find("input[name=queryStage]").val();
	var trIndex=$(obj).closest("tr").attr("id");
	 $.ajax({
	    url:"${ctx}dataQuery.do?orderId="+orderId+"&insId="+insId+"&status="+queryStage,
		dataType:"json",
		type:"post",
		success:function(result){
			if(result.succ){
				dialog("订单最新状态：" + result.msg);
			}else{
				dialog("保单生成中...");
			}
		}
	}); 
}
 
function reQuota(obj){
	window.location.href=$(obj).attr("url");
}
$(function(){
	setNav(3);
	
	$('#return_close').on('click',function(){
		$('#return_id').hide();
	});
	$('.pahd').on('click',function(){
		$('.fixedbg2,#pinganHttpConfirm').hide();
	});
	
	$('#conlea').on('click',function(){$('#select_in').hide();});
	//判断小薇费率
	var micStatus="${sessionScope.MICROTE_SESSION}";
	if(micStatus&&micStatus==1){
		$("#rote").show();
	}
	
	$("#select_result").hide();
	
	$("#result_yes1").on("click",function(){
		$("#pay_check").hide();
	});
	
	//关闭弹出的结果
	$(".result_close").on("click",function(){
		$("#select_result").hide();
	})
	//点击支付
	$(".pay").live("click",function(){
		var n_insId = $(this).attr('value');
		var isHttp = $(this).attr('is_http');
		var inquiryId = $(this).attr('inquiryId');
		var quoteId = $(this).attr('quotaId');
		var orderId = $(this).attr('code');
		if('PINGANHTTP' == n_insId){
			//平安爬虫处理:支付时弹出选择框,我已支付:取保单,去支付:跳转到支付页面
			$('#pinganHttpConfirm,.fixedbg2').show();
			$('.pingan-http-payed').attr('orderId',orderId);
			$('.pingan-http-topay')
				.attr('value','NO_CHECK')
				.attr('value2','PINGANHTTP')
				.attr('code',orderId)
				.attr('inquiryId',inquiryId)
				.attr('quotaId',quoteId);
		}
		else{
			var e = this;
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
						}else{//时间未过期，进入支付
							payCheck(e,'1');
						}
					}
				}
			});	
		}
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
	
	$('.wrapper-load-more').on('click',function(){
		$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
		dataPage();
	});
	dataPage();
	

	$(".abc").live("click",function(){
		$('.wrapper-load-more').attr('data-stage','4,8,14');
		$('#currPage').val(1);
		$('.wrapper-load-more').hide();
		$(".orderList").empty();
		dataPage();
	});
	
	$(".ddd").live("click",function(){
		$('.wrapper-load-more').attr('data-stage',5);
		$('#currPage').val(1);
		$('.wrapper-load-more').hide();
		$(".orderList").empty();
		dataPage();
	});
});


function dataPage() {
	$(".wrapper-load-more").html("<img src=\"${ctx }images/status_loading.gif\" style=\"vertical-align: middle;\" />&nbsp;加载中..");
	
	$.ajax({
		url:"${ctx}queryWaitInsureApplicate.do",
		dataType:"json",
		type:"post",
		data:{'queryStage':$('.wrapper-load-more').attr('data-stage'),'currPage':parseInt($('#currPage').val())},
		success:function(data){
			$(".wrapper-load-more").html("加载更多");
			
			if(data.success && data.data.recordCount > 0){
				var page = data.data;
				if(page.pageCount == 1 || page.currentPage == page.pageCount){
					$('.wrapper-load-more').hide();
				}else{
					$('.wrapper-load-more').show();
				}
				var str = ''; 
				var order;
				for(var i = 0;i<page.dataList.length;i++){
					order = page.dataList[i];
					var stage = order.queryStage;
					var type = order.quota.quotaType;
					var insId = order.insurance.insId;
					str += '<tr class="bg" id="tabletr_'+order.orderId+'">';
					if(stage == 8){
						str += '<input type="hidden" name="queryStage" value="'+stage+'">';
						str += '<td>'+order.inquiry.plateNo+'</td>';
						str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
						str += '<td>'+order.inquiry.ownerName+'</td>';
						str += '<td class="c1">支付失败</td>';
						str += '<td><a class="btn bc2 pay" code="'+order.orderId+'"  value="'+order.insurance.insId+'" quotaId="'+order.quota.quotaId+'" inquiryId="'+order.inquiry.inquiryId+'" jump="${ctx}allinpayContro.do?orderId='+order.orderId+'">支付</a></td>';
					}else if(stage == 4 && type == 'A'){
						if(insId == 'PAIC'){
							str += '<input type="hidden" name="queryStage" value="'+stage+'">';
							str += '<td>'+order.inquiry.plateNo+'</td>';
							str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
							str += '<td>'+order.inquiry.ownerName+'</td>';
							str += '<td class="c1">待支付 </td>';//待开单
							str += '<td><a class="btn bc2  pay" code="'+order.orderId+'" value="'+order.insurance.insId+'" quotaId="'+order.quota.quotaId+'" inquiryId="'+order.inquiry.inquiryId+'">支付</a></td>';
						}
						else if(insId == 'PICCHTTP'){//人保给个刷新按钮
							str += '<input type="hidden" name="queryStage" value="'+stage+'">';
							str += '<td>'+order.inquiry.plateNo+'</td>';
							str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
							str += '<td>'+order.inquiry.ownerName+'</td>';
							str += '<td class="c1">已核保 </td>';
							str += '<td><a class="btn bc2   aaa" code="'+order.orderId+'" value="'+order.insurance.insId+'"  quotaId="'+order.quota.quotaId+'" onclick="referOrder(this)">刷新</a></td>';
						}
						else{
							str += '<input type="hidden" name="queryStage" value="'+stage+'">';
							str += '<td>'+order.inquiry.plateNo+'</td>';
							str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
							str += '<td>'+order.inquiry.ownerName+'</td>';
							str += '<td class="c1">已核保 </td>';
							str += '<td><a class="btn bc2  pay" code="'+order.orderId+'" value="'+order.insurance.insId+'" quotaId="'+order.quota.quotaId+'" inquiryId="'+order.inquiry.inquiryId+'" jump="${ctx}allinpayContro.do?orderId='+order.orderId+'">支付</a></td>';
						}
					}
					else if(stage == 4 && type == 'M'){
						if(insId == 'TPIC'){//太平人工报价单给个刷新按钮
                            str += '<input type="hidden" name="queryStage" value="'+stage+'">';
                            str += '<td>'+order.inquiry.plateNo+'</td>';
                            str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
                            str += '<td>'+order.inquiry.ownerName+'</td>';
                            str += '<td class="c1">已核保 </td>';
                            str += '<td><a class="btn bc2   aaa" code="'+order.orderId+'" value="'+order.insurance.insId+'"  quotaId="'+order.quota.quotaId+'" onclick="referOrder(this)">刷新</a></td>';
                        }
						else{
							str += '<input type="hidden" name="queryStage" value="'+stage+'">';
	                        str += '<td>'+order.inquiry.plateNo+'</td>';
	                        str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
	                        str += '<td>'+order.inquiry.ownerName+'</td>';
	                        str += '<td class="c1">已核保 </td>';
	                        str += '<td><a class="btn bc2" href="javascript:viewPayType();">支付</a></td>';
						}
					}else if(stage == 5){
						str += '<input type="hidden" name="queryStage" value="'+stage+'">';
						str += '<td>'+order.inquiry.plateNo+'</td>';
						str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
						str += '<td>'+order.inquiry.ownerName+'</td>';
						str += '<td class="c1">待出单 </td>';
						str += '<td><a class="btn bc2   aaa" code="'+order.orderId+'" value="'+order.insurance.insId+'"  quotaId="'+order.quota.quotaId+'" onclick="referOrder(this)">刷新</a></td>';
					}else if(stage == 14){
						str += '<input type="hidden" name="queryStage" value="'+stage+'">';
						str += '<td>'+order.inquiry.plateNo+'</td>';
						str += '<td class="c2">￥'+order.quota.totalAmount+'</td> ';
						str += '<td>'+order.inquiry.ownerName+'</td>';
						str += '<td class="c1">待支付 </td>';//已开单
						str += '<td><a class="btn bc2   pay" code="'+order.orderId+'" inquiryId="'+order.inquiry.inquiryId+'" value="'+order.insurance.insId+'"  quotaId="'+order.quota.quotaId+'">支付</a></td>';
					}
					str += '</tr>';
				}
				$('.orderList').append(str);
				str = '';
			}else{
				$('.wrapper-load-more').hide();
				showMsg("查无记录...");
			}
		}
	});
}
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