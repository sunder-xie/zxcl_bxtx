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
</head>
<body>
<div class="head"><div class="back lt" onclick="window.location.href='${ctx}wallet/index.do';"></div><p class="title">提现</p></div>
<div class="content tx" style="margin-bottom: 120px;">
	<c:choose>
		<c:when test="${'Y' eq walletCanUseAgent}">
			<div style="margin: 50px auto;margin-bottom: 115px;" class="submit txc" id="submit1" onclick="location.href='${ctx}wallet_bank/to_add.do'"><label class="radd">+</label>新增银行卡</div>
			<div style="margin: 50px auto;margin-bottom: 60px;background: #D64141;" class="submit txc" onclick="location.href='${ctx}addMicro.do?regType=2'"><label class="radd">+</label>添加名义代理人</div>
		</c:when>
		<c:otherwise>
			<div style="margin: 50px auto;" class="submit txc" id="submit1" onclick="location.href='${ctx}wallet_bank/to_add.do'"><label class="radd">+</label>新增银行卡</div>
		</c:otherwise>
	</c:choose>
</div>
<jsp:include page="../commons/menu.jsp" />
<jsp:include page="../commons/myalert.jsp" />
<script>
var myName = '';
$(function(){
	var rac = "${rac}";
	if("N" == rac){
		showMsg("最多注册2个名义代理人");
		setTimeout(function(){
			window.location.href='${ctx}wallet_bank/index.do';
		},1000);
	}
});
$(function(){
	setNav(3);
	//初始化银行卡列表
	$.post("${ctx}wallet_bank/list.do",{},function(data){
		if(data.succ){
			var list = data.data;
			var item ;
			var html = '';
			for (var i = 0; i < list.length; i++) {
				item = list[i];
				
				
				html += '<div class="box"><div class="line1 clear"><div data-id="'+item.id+'"  class="rt txbtn bc3">提现</div>'+
				'<div class="div">开户行：'+item.bankName+'<br />帐户名：'+item.cardOwner+('1'==item.isAgent?'(名义代理人)':'')+'<br />账户：'+item.cardNo+'</div>'+
				'</div><div class="line2 clear">'+
					'<div class="rt deldiv"  data-id="'+item.id+'"><img class="iconimg del" src="${ctx}v3/images/del.png" />删除</div></div></div>';
			}
			$('#submit1').before(html);
			
			
			//删除银行卡
			$('.deldiv').click(function(){
				var walletBankId = $(this).attr('data-id');
				if(null == walletBankId || '' == walletBankId || 'undefined' == walletBankId){
					showMsg('请选择银行卡');
					return false;
				}
				confirm("是否确认删除该银行卡？",function(){
					$.post("${ctx}wallet_bank/del.do",{'id':walletBankId},function(data){
						if(data.succ){
							showMsg(data.msg);
							setTimeout(function(){
								window.location.reload();
							},1500);
						}else{
							showMsg(data.msg);
						}
					},'json');	
				},function(){
					return false;
				});
			});
			
			$('.txbtn').click(function(){
				var walletBankId = $(this).attr('data-id');
				//验证提现录的账号名和t_micro_info里面的micro_name一致
				$.post("${ctx}wallet_bank/cash_bank_check.do",{'walletBankId':walletBankId},function(data){
					if(data.succ){
						window.location.href = "${ctx}wallet/to_cash.do?walletBankId="+walletBankId;
					}else{
						showMsg(data.msg);
					}
				},'json');
			});
		}else{
			showMsg(data.msg);
		}
	},'json');
});
changeMenuTab(3);
</script>
	
</body>
</html>