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
<html lang="ch">
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
		//var arg=document.location.search.substring(1),tit=arg==1?'消息详情':'投保订单';
		//$('.head .title').text(tit);
	});
</script>
<style type="text/css">
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
	margin-left: 3%;
}
.dis-ok{
    margin-left: 40%;
    height: 25px;
    width: 45px;
    line-height: 25px;
    text-align: center;
    border-radius: 4px;
}
.boxx{
	display: none;
}
</style>
</head>
<body class="czxx">
	<div id="uploadFile_div">
		<iframe id="uploadFile_div_iframe" src="" data-init="0"></iframe>
	</div>
	<div class="head"><div class=" lt" ></div><p class="title">投保订单</p></div>
	<div class="content">
		<div class="tbdd">
			<div class="tbimgtxt clear c3">
				<div class="img bgc lt" style="background-image:url(${ctx}images/ins_logo/${fn:substring(insDTO.insId, 0, 4)}.png);"></div>
				<b>${insDTO.insPetName }&nbsp;&nbsp;</b>为您投保服务
			</div>
			<div class="part1">
				<div>订单号：<span class="c3">${order.orderId}</span></div>
				<div class="clear">
					<div class="lt c2">商业险</div>
					<div class="rt"><c:if test="${order.quota.VCIPremTax == '0.00'}">未投保</c:if><c:if test="${order.quota.VCIPremTax != '0.00'}">￥${order.quota.VCIPremTax }</c:if></div>
				</div>
				<div class="clear">
					<div class="lt c2">交强险</div>
					<div class="rt"><c:if test="${order.quota.tciAndTax == '0.00'}">未投保</c:if><c:if test="${order.quota.tciAndTax != '0.00'}">￥${order.quota.tciAndTax }</c:if></div>
				</div>
			</div>
			<div class="part2 clear">
				<div class="lt" onclick="location.href='${ctx}detail.do?orderId=${param.orderId }&insId=${param.insId }&quotaId=${order.quota.quotaId }&type=1'" style="background: #D64141;color:#FFFEFE;">投保明细</div>
				<div class="lt" id="uploadFile_btn" style="background: #D64141;color:#FFFEFE;">附件上传</div>
				<div class="rt">总金额：<span class="c2">￥${order.quota.totalAmount}元</span></div>
			</div>
			
			<div class="box boxx">
				<div class="line clear waybtn">
					<div class="lt c2" style="font-weight: bold;">保单配送</div>
					<div class="rt"><c:if test="${disPanDuan eq '1' }"><input id="disType0" class="ck1" type="radio" onclick="isDispatchType(this.value)" name="dispatchType" value="0">配送</c:if><input onclick="isDispatchType(this.value)" id="disType1" class="ck1" type="radio" name="dispatchType" value="1" style="margin-left:40px;">自取
					</div>
				</div>
				
				<div class="ways" style="display: block;">
					<div class="line clear">
						<div class="lt">姓名</div>
						<div class="div"><input class="inp" type="text" id="disName" placeholder="请输入姓名" required="" name="disName" value=""></div>
					</div>
					<div class="line clear">
						<div class="lt">手机号</div>
						<div class="div"><input virtual_keyboard="number" maxlength="11" class="inp" type="text" id="disPhone" placeholder="请输入联系电话" required="" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$" name="disPhone" value=""></div>
					</div>
					<div class="line clear">
						<div class="lt">省份</div>
						<div class="div">
							<select class="inp" required="" id="disProvince" name="disProvince">
							</select>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">城市</div>
						<div class="div">
							<select class="inp" id="disCity" name="disCity">
							</select>
						</div>
					</div>
					<div class="line clear">
						<div class="lt">详细地址</div>
						<div class="div"><input class="inp" type="text" id="disAddress" placeholder="区、县、街道" required="" name="disAddress" value=""></div>
					</div>
					<!-- <div class="line clear">
						<div class="lt dis-ok" onclick="updDis()" style="background: #D64141;color:#FFFEFE;">确认</div>
					</div> -->
				</div>
			</div>
			
			
			
			
			<div class="part3">
				<div class="line">选择支付方式</div>
				<c:if test="${not empty serverUrl}"><div class="line clear"><img src="${ctx }images/liant.png" />通联支付<input class="ck1 rt" type="radio" checked="checked" name="pay"  value="alipay"  /></div></c:if>
				<c:if test="${not empty weChatUrl}"><div class="line clear"><img src="${ctx }v3/images/zfwx.png" />微信支付<input class="ck1 rt" type="radio" name="pay"  value="weChat" /></div></c:if>
				<c:if test="${not empty wapUrl }"><div class="line clear"><img src="${ctx }v3/images/zfb.png" />支付宝<input class="ck1 rt" type="radio" name="pay" value="wap" /></div></c:if>
				<c:if test="${not empty unionpay}"><div class="line clear"><img src="${ctx }v3/images/zfyl.png" />银联支付<input class="ck1 rt" type="radio" name="pay" value="unionpay" /></div></c:if>
				<c:if test="${not empty alltrustPayurl}"><div class="line clear"><img src="${ctx }images/99bill.gif" />快钱支付<input class="ck1 rt" type="radio" name="pay" value="alltrustPay" /></div></c:if>
				
				<%--
				
				<c:if test="${not empty serverUrl}"><p class="tac"><a class="alipay">
				<input type="radio" name="pay" value="alipay" class="bdr50 input_ui input_ui_anim pay_radio"/><img src="${ctx}images/liant.png" alt="通联支付"/></a></p></c:if>
				<c:if test="${not empty weChatUrl}"><p class="tac"><a class="alipay">
				<input type="radio" name="pay" checked="checked" value="weChat" class="bdr50 input_ui input_ui_anim pay_radio"/><img src="${ctx}images/weix.png" alt="微信支付"/></a></p></c:if>
				<c:if test="${not empty wapUrl }"><p class = tac><a class="alipay">
				<input type="radio" name="pay" checked="checked" value="wap" class="bdr50 input_ui input_ui_anim pay_radio"/><img src="${ctx}images/alipay.jpg"  alt="支付宝"></a></p></c:if>
				<c:if test="${not empty alltrustPayurl}"><p class = tac><a class="alipay">
				<input type="radio" name="pay" checked="checked" value="alltrustPay" class="bdr50 input_ui input_ui_anim pay_radio"/><img src="${ctx}images/99bill.gif"  alt="快钱"></a></p></c:if>
				<c:if test="${not empty unionpay}"><p class="tac"><a class="alipay">
				<input type="radio" name="pay" checked="checked" value="unionpay" class="bdr50 input_ui input_ui_anim pay_radio"><img alt="银联" src="${ctx }images/Unionpay.jpg"></a></p></c:if>
				 --%>
			</div>
			<div class="part4"><img src="${ctx }v3/images/tip01.png" />请在24小时内完成支付，否则可能需要重新报价！</div>
			<div class="submit txc" id="submit1">确认付款</div>
		</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	
		<!-- 通联支付 -->
		<form name="alipayForm" action="${serverUrl}"  method="post">
	        <input type="hidden" name="inputCharset" value="${requestOrder.inputCharset }"/>
			<input type="hidden" name="pickupUrl" value="${requestOrder.pickupUrl }" />
			<input type="hidden" name="receiveUrl" value="${requestOrder.receiveUrl }" />
			<input type="hidden" name="version" value="${requestOrder.version }"/>
			<input type="hidden" name="language" value="${requestOrder.language }"  />
			<input type="hidden" name="signType" value="${requestOrder.signType }"/>
			<input type="hidden" name="merchantId" value="${requestOrder.merchantId }" />
			<input type="hidden" name="payerName" value="${requestOrder.payerName }"/>
			<input type="hidden" name="payerEmail" value="${requestOrder.payerEmail }" />
			<input type="hidden" name="payerTelephone" value="${requestOrder.payerTelephone }"  />
			<input type="hidden" name="payerIDCard" value="${requestOrder.payerIDCard }" />
			<input type="hidden" name="pid" value="${requestOrder.pid }"/>
			<input type="hidden" name="orderNo" value="${requestOrder.orderNo }" />
			<input type="hidden" name="orderAmount" value="${requestOrder.orderAmount }" />
			<input type="hidden" name="orderCurrency" value="${requestOrder.orderCurrency }" />
			<input type="hidden" name="orderDatetime" value="${requestOrder.orderDatetime }" />
			<input type="hidden" name="orderExpireDatetime" value="${requestOrder.orderExpireDatetime }" />
			<input type="hidden" name="productName" value="${requestOrder.productName }" />
			<input type="hidden" name="productPrice" value="${requestOrder.productPrice }" />
			<input type="hidden" name="productNum" value="${requestOrder.productNum }" />
			<input type="hidden" name="productId" value="${requestOrder.productId }" />
			<input type="hidden" name="productDesc" value="${requestOrder.productDesc }" />
			<input type="hidden" name="ext1" value="${requestOrder.ext1 }" />
		    <input type="hidden" name="ext2" value="${requestOrder.ext2 }" />
			<input type="hidden" name="payType" value="${requestOrder.payType }" />
			<input type="hidden" name="issuerId" value="${requestOrder.issuerId }" />
			<input type="hidden" name="pan" value="${requestOrder.pan }"  />
			<input type="hidden" name="tradeNature" value="${requestOrder.tradeNature }"  />
			<input type="hidden" name="signMsg" value="${strSignMsg }"/>
		   <!--  <input type="submit" value="确认付款，到通联支付去啦"/> -->
	</form>
	
	<!-- 微信支付 -->
	<form name="weChatForm" action="${weChatUrl}"  method="post"></form>
	
	<!-- 支付宝支付 -->
	<form name="wapForm" action="${wapUrl }" method="post"></form>
	
	<!-- 直接跳路径支付 -->
	<form name="urlForm" action="${url }" method="post"></form>
	
	<!-- 快钱支付 -->			
	<form name="alltrustPayForm" action="${alltrustPayurl}" method="post"></form>
	
	<!-- 银联支付 -->
	<form name="unionpayForm" action="${unionpay }" method="post"></form>
	
	<c:if test="${not empty payReturnDTO and 'PAIC' eq insId}">		
		<!-- 平安支付 -->
		<form name="paicPay" action="${payReturnDTO.payUrl}" method="post">
			<input type="hidden" name="customerName" value="${payReturnDTO.map.customerName}"  />
			<input type="hidden" name="phoneNo" value="${payReturnDTO.map.phoneNo}"  />
			<input type="hidden" name="businessNo" value="${payReturnDTO.map.businessNo}"  />
			<input type="hidden" name="amount" value="${payReturnDTO.payAmount}"  />
			<input type="hidden" name="callBackUrl" value="${payReturnDTO.map.callBackUrl}"  />
			<input type="hidden" name="returnUrl" value="${payReturnDTO.map.returnUrl}"  />
			<input type="hidden" name="dataSource" value="${payReturnDTO.map.dataSource}"  />
			<input type="hidden" name="currencyNo" value="${payReturnDTO.map.currencyNo}"  />
			<input type="hidden" name="productName" value="${payReturnDTO.map.productName}"  />
			<input type="hidden" name="signMsg" value="${payReturnDTO.map.signMsg}"  />
		</form>
	</c:if>
	<c:if test="${'LIBAO' eq insId and not empty libaoPayFormMap}">        
        <!-- 利宝支付 -->
        <form name="libaoPayForm" action="${url}" method="post">
            <input type="hidden" name="payment" value="${libaoPayFormMap.payment}"  />
            <input type="hidden" name="documentNo" value="${libaoPayFormMap.documentNo}"  />
            <input type="hidden" name="callBackUrl" value="${libaoPayFormMap.callBackUrl}"  />
            <input type="hidden" name="deviceType" value="${libaoPayFormMap.deviceType}"  />
            <input type="hidden" name="redirectUrl" value="${libaoPayFormMap.redirectUrl}"  />
            <input type="hidden" name="partnerAccountCode" value="${libaoPayFormMap.partnerAccountCode}"  />
            <input type="hidden" name="agreementNo" value="${libaoPayFormMap.agreementNo}"  />
            <input type="hidden" name="access_token" value="${libaoPayFormMap.access_token}"  />
            <input type="hidden" name="recordCode" value="${libaoPayFormMap.recordCode}"  />
            <input type="hidden" name="flowId" value="${libaoPayFormMap.flowId}"  />
            <input type="hidden" name="operatorDate" value="${libaoPayFormMap.operatorDate}"  />
            <input type="hidden" name="pdk" value="${libaoPayFormMap.pdk}"  />
        </form>
    </c:if>
	
	
	<!--主体内容 end-->
	
	
	<!-- 	支付结果 -->
	<div class="tipsel txc" id="payresult">
		<div class="tip">支付成功<br/>请稍后查询保单</div>
		<div class="btns clear">
			<div class="lt boxSet c1 btn" id="asdfg" onclick="location.href='${ctx}list2.do'">继续支付</div>
			<div class="lt boxSet c1"  id="vdsvd" onclick="location.href='${ctx}detail.do?orderId=${order.orderId}&insId=${insDTO.insId}&quotaId=${order.quota.quotaId}&toIndex=1'">详情</div>
		</div>
	</div>
	<!-- 获取支付方式失败 -->
	<div class="tipsel txc" id="payWay_info">
		<div class="tip">获取支付方式失败，请重试</div>
		<div class="btns clear">
			<div class="lt boxSet c1 btn" onclick="location.href='${ctx}list2.do'">暂不支付</div>
			<div class="lt boxSet c1" onclick="location.href=window.location.href">重试</div>
		</div>
	</div>
	<!-- 消息提醒 -->
	<div class="tipsel txc" id="payWay_info1">
		<div class="tip"  id="info_info"></div>
		<div class="btns c1 clear" onclick="location.href='${ctx}list2.do'">确定</div>
	</div>
	
	<div class="tipsel txc"  id="messageDialog">
		<div class="tip"></div>
		<div class="btns c1 clear"  id="messageDialog_yes" onclick="tipclosefun($(this).parent())">确定</div>
	</div>
	<script>
$(function(){
	
	if($(".part3 .ck1").size() < 1){
		$(".part3").hide();
	}
	
	var insId = '${order.insurance.insId}'.substring( 0, 4);
	if(!'${alltrustPayurl}'&&!'${weChatUrl}'&&!'${wapUrl}'&&!'${unionpay}'&&!'${success}'&&'1'!='${urlType}'){
		tipfun($("#payWay_info"));
	}else{
		tipclosefun($("payWay_info"));
	}
	if('1' == '${type}'){
		$('#info_info').html('${msgInfo}');
		//$('#payWay_info1').show();
		tipfun($("#payWay_info1"));
	}
	
	/* function submitForm(){
		var disPanDuan = true;
		
		//
		var disPanDuan = updDis();
		
		if(disPanDuan){
			if($(".part3 .ck1").size() >  0 && $(".part3 input[type='radio']:checked").size() < 1){
				$('#messageDialog .tip').html('请先选择支付方式.');
				tipfun($("#messageDialog"));
				return ;
			}
			if("PAIC" == "${insId}"){
				$("form[name=paicPay]").submit();
			}else if('${url}'){
				window.location.href=$("form[name=urlForm]").attr("action");
			} else{
				var name=$("input:radio[name=pay]:checked").val();
				if(!name){
					$('#messageDialog .tip').html('请先选择支付方式.');
					tipfun($("#messageDialog"));
					return ;
				}
				window.location.href=$("form[name="+name+"Form]").attr("action");
			}
		}
	} */
	
	$("#submit1").on("click",function(){
		var disPanDuan = true;
		
		//选择配送方式就进入
		if(null != document.getElementById('disType0') && document.getElementById('disType0').checked){
			console.info('修改配送信息');
			disPanDuan = updDis();
		}
		
		if(disPanDuan){
			if($(".part3 .ck1").size() >  0 && $(".part3 input[type='radio']:checked").size() < 1){
				$('#messageDialog .tip').html('请先选择支付方式.');
				tipfun($("#messageDialog"));
				return ;
			}
			if("PAIC" == "${insId}"){
				$("form[name=paicPay]").submit();
			}else if("LIBAO" == "${insId}"){
				$("form[name=libaoPayForm]").submit();
			}else if('${url}'){
				window.location.href=$("form[name=urlForm]").attr("action");
			} else{
				var name=$("input:radio[name=pay]:checked").val();
				if(!name){
					$('#messageDialog .tip').html('请先选择支付方式.');
					tipfun($("#messageDialog"));
					return ;
				}
				window.location.href=$("form[name="+name+"Form]").attr("action");
			}
		}
	});

	/* $('#123456').on('click',function(){
		$("form[name=taiping]").submit();
	}); */
});
showResult();
function showResult() {
	var success ="${success}";
	var msg = "${msg}";
	var info = "${info}";
	if(success){
		var asdfg = $('#asdfg');
		if(success=="0"){
	   		$("#payresult").find(".tip").html("支付失败！");
	   		asdfg.attr('onclick','location.href="${ctx}list2.do"');
	   		asdfg.val('继续支付');
	   		
	   		/* dialogConfirm('支付失败！','继续支付',function(){
				window.location.href='${ctx}list2.do';
			},"",function(){
				window.location.href='${ctx}detail.do?orderId=${order.orderId}&insId=${insDTO.insId}&quotaId=${order.quota.quotaId}&toIndex=1';
			}); */
	   	}
	   	if(null!=msg&&""!=msg){
	   		$("#payresult").find(".tip").text(msg);
	   		asdfg.val('支付完成'); 
	   		if('5' == '${order.queryStage}'){
	   			/* dialogConfirm('支付完成','继续支付',function(){
					window.location.href='${ctx}list2.do';
				},"",function(){
					window.location.href='${ctx}detail.do?orderId=${order.orderId}&insId=${insDTO.insId}&quotaId=${order.quota.quotaId}&toIndex=1';
				}); */
	   			
	   			
		   		asdfg.attr('onclick','location.href="${ctx}list2.do"');
	   		}else if('6' == '${order.queryStage}'){
	   			
	   			
		   		asdfg.attr('onclick','location.href="${ctx}list3.do"');
	   		}else if(info == '1'){
	   			asdfg.attr('onclick','location.href="${ctx}list2.do"');
	   			$("#payresult #asdfg").html("继续投保");
	   			$("#payresult #asdfg").attr("onclick","location.href='${ctx}index.do?nocheck=1'");
	   			$("#payresult #vdsvd").html("综合查询");
	   			$("#payresult #vdsvd").attr("onclick","location.href='${ctx}infoQuery.do'");
	   			//var str = '<a class="btn_l" id="asdfg" href="${ctx}index.do?nocheck=1">继续投保</a>';
	   			//str += '<a class="btn_r" href="${ctx}infoQuery.do">综合查询</a>';
	   			//$(".wrap_result").find(".result_bottom").html(str);
	   		}
	   	}
		//$(".wrap_result").find(".result_box").removeClass("pay_box");
		//$(".showBtn").show();
		//$("#payresult").show();
		tipfun($("#payresult"));
		
	}
}
$(function(){
	
	//$("#js_showResult").on("click",function(){
	//	$(".wrap_result").show();
	//	event.preventDefault();
	//});
	
	$("#sub_order").on("click",function(){
		var orderId="${order.orderId}";
		var insId="${order.insurance.insId}";
		var payWay=$("input:radio[name=pay]:checked").val()=="weChat"?"1":"2";
		$.ajax({
			url:"${ctx}payer.do",
			type: "POST",
			dataType:"json",
			data:"orderId="+orderId+"&insId="+insId+"&payWay="+payWay,
			success: function(result){
			}
		})
	});
});

//为空返回true，不为空返回false
function isEmpty(str){
	if(null != str && '' != str && undefined != str){
		return false;
	}else{
		return true;
	}
}
</script>
<script type="text/javascript">
$('#uploadFile_btn').click(function(){
	var init = $('#uploadFile_div_iframe').attr('data-init');
	if(init == 0 || init == '0'){
		$('#uploadFile_div_iframe').attr('data-init', '1');
		$('#uploadFile_div_iframe').attr('src','${ctx}upload/inquiry_index.do?inquiryId=${order.inquiry.inquiryId}');
	}
	$('#uploadFile_div').show();
});
function closeIframe(){
	$('#uploadFile_div').hide()
}
function isDispatchType(value){
	if('1' == '${disPanDuan}'){		
		//(0-配送 1-自取)
		if(0==value){
			$("div.ways").slideDown();
			/* if(null == $('#disAddress').val() || '' == $('#disAddress').val()){
				$.post("${ctx}selectMicroTeamAddressByUserId.do",{},function(data){
					if(data.succ){
						var address = data.data;
						if(address != null && '' != address && null != address && 'null' != address && 'undefined' != address){
							$('#disAddress').val(address);
						}
					}
				},'json');
			} */
		}else if(1==value){
			$("div.ways").slideUp();
			var orderId = '${order.orderId}';
			$.post("${ctx}manage_dispatch/dis/upd.do",{
				'dispatchType':'1',
				'orderId':orderId
				},function(data){
			if(data.success){
				$("div.ways").slideUp();
				showMsg("更新成功");
			}
			else{
				showMsg(data.message);
			}
		},'json');
		}
	}
}
function disSelectCity(prov, cityCode){
	$.post("${ctx}area/get_citys_by_provinces.do",{'code':prov},function(data){
		if(data.success){
			var str = '';
			$.each(data.data,function(n,v){
				if(v.code == cityCode){
					str += '<option value="'+v.code+'" selected="selected">'+v.name+'</option>';
				}
				else{
					str += '<option value="'+v.code+'">'+v.name+'</option>';
				}
			});
			$('#disCity').html(str);
		}
	},'json');
}
$('#disProvince').change(function(){
	var prov = $('#disProvince option:selected').val();
	disSelectCity(prov, '');
});
function updDis(){
	var orderId = '${order.orderId}';
	var disName = $('#disName').val();
	var disAddress = $('#disAddress').val();
	var disPhone = $('#disPhone').val();
	var disAddress = $('#disAddress').val();
	var disProvince = $('#disProvince option:selected').val();
	var disCity = $('#disCity option:selected').val();
	var disPanDuan = false;
	/* $.post("${ctx}manage_dispatch/dis/upd.do",{
			'disAddress':disAddress,
			'disPhone':disPhone,
			'dispatchType':'0',
			'disName':disName,
			'disAddress':disAddress,
			'disProvince':disProvince,
			'disCity':disCity,
			'orderId':orderId
			},function(data){
		 if(data.success){
			 $("div.ways").slideUp(); 
			showMsg("更新成功");
		}
		else{
			showMsg(data.message);
		} 
	},'json'); */
	$.ajax({
		type:'post',
		url:'${ctx}manage_dispatch/dis/upd.do',
		data:{'disAddress':disAddress,
			'disPhone':disPhone,
			'dispatchType':'0',
			'disName':disName,
			'disAddress':disAddress,
			'disProvince':disProvince,
			'disCity':disCity,
			'orderId':orderId},
		dateType:'json',
		async:false,
		success:function(result){
			if(result.success){	
				disPanDuan = true;
			}
		}
	});
	return disPanDuan;
}
$.post("${ctx}manage_dispatch/dis/get.do",{'orderId':'${order.orderId}'},function(data){
	if(data.success && null != data.data){
		var obj = data.data;
		var disType = obj.dispatchType;
		var prov = obj.disProvince;
		var cityv = obj.disCity;
		var disName = obj.disName;
		var disAddress = obj.disAddress;
		var disPhone = obj.disPhone;
		$('#disName').val(disName);
		$('#disPhone').val(disPhone);
		$('#disAddress').val(disAddress);
		
		if(null == prov || '' == prov || 'undefined' == prov){
			prov='510000';
		}
		if(null == cityv || '' == cityv || 'undefined' == cityv){
			cityv='510100';
		}
		$.post("${ctx}area/get_provinces.do",{},function(data){
			if(data.success){
				var str = '';
				$.each(data.data,function(n,v){
					if(v.code == prov){
						str += '<option value="'+v.code+'" selected="selected">'+v.name+'</option>';
					}
					else{
						str += '<option value="'+v.code+'">'+v.name+'</option>';
					}
				});
				$('#disProvince').html(str);
				disSelectCity(prov, cityv);
			}
		},'json');
		
		
		if(disType == 1 || '1' == disType){//自取
			$('.boxx').show();
			$("div.ways").hide();
			$('#disType1').attr('checked','checked');
		}
		else{//配送
			$('#disType0').attr('checked','checked');
			$('.boxx').slideDown();
		}
	}
},'json');
</script>
</body>
</html>