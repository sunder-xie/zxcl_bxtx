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
	<title>选择银行卡</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
	#submitAll{
		display:none;
		width:20%;
		height:20px;
		line-height:20px;
		font-size: 12px;
		background: #d64141;
	}
</style>
</head>
<body>
	<div class="head"><div class="back lt" id="back_lt" onclick="window.location.href='${ctx}wallet_bank/index.do';"></div><p class="title">提现</p></div>
	<form class="content txje txc">
		<input type="hidden" value="0" id="id_amount_input"/>
		<div class="con">
			<div class="line1">
				<span style="margin-right: 10px;">可提现金额</span>
				<label class="c2"  id="id_amount">0元</label>
				<span><input class="submit" id="submitAll" type="button" value="全额提取" /></span>
				</div>
			<input class="inp txc" id="id_cashMoney" type="text" placeholder="最低额度1元，精确到分" />
			<div class="line2">预计2个工作日内到帐</div>
			<div class="btndiv"><input class="submit" id="submit1" type="button" value="确定提交" /></div>
		</div>
		<div class="txok disn">
			<div class="line1 c1">申请成功！</div>
			<div class="line2">已成功提现<label class="c2" id="ok_cashMoney">***元</label></div>
			<div class="line3">预计2个工作日内到帐</div>
		</div>
	</form>
	<jsp:include page="../commons/menu.jsp" />
	<jsp:include page="../commons/myalert.jsp" />
		<script type="text/javascript">
	
		$(function(){
			setNav(3);
			$("#id_cashMoney").on('keyup', function (event) {
			    var $amountInput = $(this);
			    //响应鼠标事件，允许左右方向键移动 
			    event = window.event || event;
			    if (event.keyCode == 37 | event.keyCode == 39) {
			        return;
			    }
			    //先把非数字的都替换掉，除了数字和. 
			    $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
			        //只允许一个小数点              
			        replace(/^\./g, "").replace(/\.{2,}/g, ".").
			        //只能输入小数点后两位
			        replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
			            });
			$("#amount").on('blur', function () {
			    var $amountInput = $(this);
			    //最后一位是小数点的话，移除
			    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
			});
			
			$.post("${ctx}wallet/get_wallet.do",{},function(data){
				if(data.succ){
					var userAmount = parseFloat(data.data.amount).toFixed(2);
					$('#id_amount').text(userAmount + "元");
					$('#id_amount_input').val(userAmount);
					if(userAmount > 0){
						$('#submitAll').show();
					}
				}
			},'json');
			
			$('#submitAll').click(function(){
				$('#id_cashMoney').val($('#id_amount_input').val());
			});
			
			$('#submit1').click(function(){
				var amount = $('#id_cashMoney').val();
				if(amount == null || amount == '' || isNaN(amount)){
					$('#id_cashMoney').select().focus();
					return false;
				}
				amount = parseFloat(amount);
				loadfun("处理中");
				$.post("${ctx}wallet/cash_pre.do",{'amount':amount,'walletBankId':'${walletBankId}'},function(data){
					loadclosefun();
					if(data.succ){
						var obj = data.data;
						if(null == obj){
							showMsg("提现申请失败");
						}
						else{
							//本次结算
							var confirmText = '';
							var namount = obj.settleAmount;
							if("Y" == "${walletCanUseAgent}"){//扣税
								if(null == namount || '' == namount || 'undefined' == namount || 'null' == namount){
									namount = '0';
								}
								confirmText += '本次结算金额: '+namount+'元；<br\>';
								//本次扣税
								var tax = obj.taxAmount;
								if(null == tax || '' == tax || 'undefined' == tax || 'null' == tax){
									tax = '0';
								}
								confirmText += '本次扣税金额: '+tax+'元；<br\>';
								//本月累计结算
								var anamount = obj.totalSettleAmount;
								if(null == anamount || '' == anamount || 'undefined' == anamount || 'null' == anamount){
									anamount = '0';
								}
								confirmText += '本月累计结算金额: '+anamount+'元；<br\>';
								//本月累计扣税
								var atax = obj.totalTaxAmount;
								if(null == atax || '' == atax || 'undefined' == atax || 'null' == atax){
									atax = '0';
								}
								confirmText += '本月累计扣税金额: '+atax+'元。';
							}
							else{//不扣税
								confirmText += '本次结算金额: '+namount+'元。';
							}
							
							confirm(confirmText,function(){
								$.post("${ctx}wallet/cash.do",{'amount':amount,'walletBankId':'${walletBankId}'},function(data){
									if(data.succ){
										//tipfun($(".txok"));
										$('#ok_cashMoney').text(parseFloat(amount).toFixed(2) + "元");
										$('#back_lt').attr('onclick','javascript:window.location.href="index.do"');
										$(".con").hide();
										$(".txok").show();
										//window.location.href = '${ctx}wallet/cash_succ.do?amount='+amount;
									}else{
										showMsg(data.msg);
									}
								},'json');
							},function(){
								return false;
							});
						}
					}else{
						showMsg(data.msg);
					}
				},'json');
			});
		});
		changeMenuTab(3);
	</script>
</body>
</html>