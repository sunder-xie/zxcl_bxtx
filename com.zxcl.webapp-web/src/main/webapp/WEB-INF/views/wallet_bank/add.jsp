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
<title>保行天下-银行卡管理</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
.indd{
	margin:0 0 2% 0;
}
#Filedata{
   position: fixed;
   left: 127.5px;
   top: 147px;
   font-size: 100px;
   opacity: 0;
   filter: alpha(opacity=0);
}
div.info{
	display:none !important;
}
.btns2{
	display:none;
	margin: 0 0 0 30%;
}
#uploader .filelist li img{
  	height: 70%;
    width: 70%;
}
#uploader .statusBar .btns{
    padding-left: 16%;
}
body, select, input, textarea, a {
    font-family: "Helvetica Neue",Helvetica,"Hiragino Sans GB","Microsoft YaHei",Arial,sans-serif;
    font-size: 15px;
}
#uimg{
    padding-bottom: 5%;
}
#uploader .statusBar .btns .uploadBtn{
	background: #ffffff;
    border: 1px solid #cfcfcf;
    color: #565656;
    padding: 0 18px;
    display: inline-block;
    border-radius: 3px;
    margin-left: 10px;
    cursor: pointer;
    font-size: 14px;
}
#XY{
    overflow: scroll;
    padding-left: 5px;
    padding-right: 5px;
}
.titlea{
	width:100%;
	font-size:18px;
	text-align:center;
	line-height:25px;
	margin-top:45px;
}
.dlnr{
	font-size:14px;
	font-weight: 700;
	margin-top:10px;
	line-height:25px;
	letter-spacing:2px;
	color:#0E0D0D;
}
.clear {
	clear:both;
}
.sjnr{
	font-size:12px;
	letter-spacing:1px;
	color:#333;
}
input[type=file]{
    opacity: 0;
}
#filePicker{
    display: block;
    background: #d64141;
    color: white;
    margin: 0 3%;
    border-radius: 5px;
    height: 45px;
    line-height: 45px;
    margin-bottom: -9%;
    font-size: 17px;
}
.imgWrap{
	margin-top: 10%;
}
</style>
</head>
<body>
	<div class="head"><div class="back lt" onclick="window.location.href='${ctx}wallet_bank/index.do';"></div><p class="title">新增银行卡</p></div>
	<form class="content inpbox">
		<div class="box">
			<c:if test="${'Y' eq walletCanUseAgent}">
				<div class="line clear" id="id_pre_microAgentList">
					<div class="lt">代理人</div>
					<div class="div"><select  style="border-bottom:none;padding:0px;width:100%;background: url(../images/arrow_r.png) no-repeat 95% center #fff;" id="id_microAgentList" ><option value=""/>本人</option></select></div>
				</div>
			</c:if>
			<div class="line clear">
				<div class="lt">帐户名</div>
				<div class="div"><input class="inp" type="text"  id="id_cardOwner" placeholder="请输入姓名" /></div>
			</div>
			<!-- upload -->
			<c:if test="${'1' eq canUseOcr}">
			<div class="line clear" style="border-bottom: 0;">
				<div class="clear indd" style="text-align: center;">
					<div id="uploader">
						<div class="queueList">
							<div id="dndArea" class="placeholder">
								<div id="filePicker" ></div>
							</div>
						</div>
						<div class="statusBar" style="display: none; height: 90px;">
							<div class="progress">
								<span class="text">0%</span> <span class="percentage"></span>
							</div>
							<div class="info"></div>
							<div class="btns btns1">
								<div id="filePicker2"></div>
								<div class="uploadBtn" style="margin-right: 22%;">开始识别</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="line clear" style="margin-top: 11%;border-top: 1px solid #f0f0f0;">
				<div class="lt">帐号</div>
				<div class="div"><input class="inp" type="text" id="id_cardNo" placeholder="请输入帐号" /></div>
			</div>
			</c:if>
			<c:if test="${'1' ne canUseOcr}">
			<div class="line clear" style="">
				<div class="lt">帐号</div>
				<div class="div"><input class="inp" type="text" id="id_cardNo" placeholder="请输入帐号" /></div>
			</div>
			</c:if>
			<!-- upload -->
			<div id="cardNameDiv" style="padding: 3px 0;text-align: left;margin-top: 10px;display:none;"></div>
		</div>
		<div class="subbtn"><input class="submit" id="submit1" type="button" value="确定新增" /></div>
	</form>
	<jsp:include page="../commons/menu.jsp" />
	<jsp:include page="../commons/myalert2.jsp" />
	<script>
	var tempFile ;
	var inputLabelShow = '拍照识别';
	var myName = '';
	var isOnceWb = '0';
	$(function(){
		myName = '${myName}';
		$('#id_cardOwner').val(myName).attr('readonly','readonly');
		
		//是否历史性中第一次新增银行卡
		$.post("${ctx}wallet_bank/add_pre_isOnceWb.do",{},function(data){
			if(data.succ){
				isOnceWb = data.data;
				if("1" == isOnceWb || 1 == isOnceWb){
					$('#id_cardOwner').removeAttr('readonly');
				}
			}
			else{
				alert(data.msg);
			}
		},'json');
	});
	$(function(){
		setNav(3);
		$("#id_cardNo").bankInput();
		if("Y" == "${walletCanUseAgent}"){
			$.post("${ctx}wallet_bank/micro_agent_list.do",{},function(data){
				if(data.succ){
					var list = data.data;
					var html = '<option value="">本人</option>';
					var item ;
					for (var i = 0; i < list.length; i++) {
						item = list[i];
						html += '<option value="'+item.microIdAgent+'">'+item.userAgentMircoName+'</option>';
					}
					$('#id_microAgentList').html(html);
				}else{
					alert(data.msg);
				}
			},'json');
		}
		
		$('#id_microAgentList').change(function(){
			var agentMicroId = $('#id_microAgentList option:selected').val();
			if(null == agentMicroId || '' == agentMicroId || 'undefined' == agentMicroId || 'null' == agentMicroId){
				$('#id_cardOwner').val(myName).attr('readonly','readonly');
				if("1" == isOnceWb){
					$('#id_cardOwner').removeAttr('readonly');
				}
			}
			else{
				var tmpMyName = $('#id_microAgentList option:selected').text();
				if(null == tmpMyName || '' == tmpMyName){
					tmpMyName = myName;
				}
				$('#id_cardOwner').val(tmpMyName).attr('readonly','readonly');
			}
		});
		
		$('#submit1').click(function(){
			var bankNo = "-";
			var bankName = "-";
			var cardNo = $('#id_cardNo').val();
			if("Y" == "${walletCanUseAgent}"){
				var agentMicroId = $('#id_microAgentList option:selected').val();
			}
			else{
				var agentMicroId = '';
			}
			var cardOwner = $('#id_cardOwner').val();
			var regName =/^([\u4e00-\u9fa5]+|([a-zA-Z]+\s?)+)$/;
			if(!cardOwner.match(regName)){
			 	alert('真实姓名填写有误');
				return false;
			}
			if(null == cardNo || '' == cardNo){
				alert('请输入19位的银行卡号');
				return false;
			}
// 			cardNo = cardNo.replace(/\s/g,'').replace("$1 ",/(\d{4})(?=\d)/g);
			var flag = false;//是否本人银行卡
			if(null == agentMicroId || '' == agentMicroId || 'undefined' == agentMicroId || 'null' == agentMicroId){
				flag = true;
			}
			if(flag){//本人银行卡
				$.post("${ctx}wallet_bank/add_pre_isOnceWbAndNNS.do",{'cardOwnerName':cardOwner},function(data){
					if(data.succ){
						if("1" == data.data || 1 == data.data){//第一次新增且姓名不一致
							confirm("持卡人与注册用户名不一致，系统将自动更新注册用户名？","确认", "取消",function(){
								addWalletBank(bankNo, bankName, cardNo, cardOwner, agentMicroId);	
							},function(){
								$('#id_cardOwner').focus();
								return false;
							});
						}
						else{//非(第一次新增且姓名不一致) || 小微姓名为空 自动更新姓名
							addWalletBank(bankNo, bankName, cardNo, cardOwner, agentMicroId);
						}
					}
					else{
						alert(data.msg);
					}
				},'json');
			}
			else{//非本人银行卡
				addWalletBank(bankNo, bankName, cardNo, cardOwner, agentMicroId);
			}
		});
	});
	var addWalletBank = function(bankNo, bankName, cardNo, cardOwner, agentMicroId){
		$.post("${ctx}wallet_bank/add.do",{'bankNo':bankNo,'bankName':bankName, 'cardNo':trimAll(cardNo),'cardOwner':cardOwner,'agentMicroId':agentMicroId},function(data){
			if(data.succ){
				alert(data.msg);
				$('#myAlert .yes').bind('click',function(){
					window.location.href='${ctx}wallet_bank/index.do';
				});
			}else{
				alert(data.msg);
			}
		},'json');
	}
	changeMenuTab(3);
	</script>
	
	<c:if test="${'1' eq canUseOcr}">
	<script type="text/javascript">
		var serviceUpload = {
			fileServerSysId:'bxtx',
			fileServerUploaderUrl:'${baseURL}/file_server/file/uploadAsStream.do',
			fileServerShowFileBaseUrl:'${baseURL}/file_server/files/',
			serviceFileAdd:function(fileId){
				if(null != fileId && '' != fileId){
					loadfun("识别中...");
					$('.filelist,.statusBar').hide();
					$('#cardNameDiv').html('').hide();
					$.post("${ctx}wallet_bank/addPreOcrResult.do",{'fileId':fileId},function(data){
						loadclosefun();
						console.log(data);
						if(data.succ){
							var bankResult = data.data;
							var showBankNo = bankResult.bankNo;
							var showBankName = bankResult.bankName;
							if(null != showBankNo && '' != showBankNo && 'undefined' != showBankNo){
								$('#id_cardNo').val(showBankNo);
							}
							if(null != showBankName && '' != showBankName && 'undefined' != showBankName){
								$('#cardNameDiv').html(showBankName).show();
							}
							alert('识别成功');
						}
						else{
							alert(data.msg);
						}
					},'json');
				}
			}
		}		
	</script>
	<script type="text/javascript" src="${ctx}webuploader/webuploader_ocr.js"></script>
	<script type="text/javascript" src="${ctx}webuploader/upload_ocr.js?v=1.0"></script>
	</c:if>
</body>
</html>