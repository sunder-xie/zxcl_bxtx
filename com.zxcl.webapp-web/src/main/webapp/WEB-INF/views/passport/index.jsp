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
<html lang="zh">
<head>
<title>${agentDisplayDTO.headTitle}</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">忘记密码</p></div>
	<form class="user_inp content" method="post" >
		<div class="part1">
			<div class="clear">
				<span class="lt span">+86</span>
				<div class="div">
					<input type="tel" name="tel" id="accountName" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$" placeholder="手机号" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength=11 required="" />
				</div>
			</div>
			<div class="clear">
				<span class="lt">短信验证</span>
				<div class="div">
					<input type="number"   id="phoneCode"  name="vidate" placeholder="请输入短信验证码" required="" />
					<span class="getyzm" id="passport1">获取验证码</span><!--  id="getyzm" 开始秒数变化 -->
				</div>
			</div>
			<div class="clear">
				<span class="lt">新密码</span>
				<div class="div">
					<input type="password"  id="password1" placeholder="请输入新密码" required="" />
				</div>
			</div>
			<div class="clear">
				<span class="lt">确认密码</span>
				<div class="div">
					<input type="password"   id="password2"  placeholder="请确认密码" required="" />
				</div>
			</div>
		</div>
		<div class="btndiv" style="margin-top:30px;"><input class="submit" type="button"  id="submit"  value="确定提交" /><!--  id="submit" 填写完成后的状态 --></div>
	</form>
	
		<script>
	$('#result_yes1').click(function(){
		$('#mymassege').html('');
		$('#info_msg1').hide();
	});
	
	$('#resend_phone_code').click(function(){
		$('.passport-step2').hide();
		$('.passport-step1').show();
		$('#phoneCode').val('');
	});
	//发送手机验证码
	$('#passport1').click(function(){
		if($("#accountName").val() == ""){
			showMsg("请输入手机号码");
			return;
		}
		
		$.post("${ctx}passport/send_phone_code.do", {"accountName":$("#accountName").val()},function(data){
			if(data.succ){
				alert(data.msg);
				$('#mymassege').html(data.msg);
				$('#info_msg1').show();
				$('.passport-step1').hide();
				$('.passport-step2').show();
				$('#userId').val(data.data);
				$('#phoneCode').val('');
			}else{
				showMsg(data.msg);
				$('#userId').val('');
				if(data.msg != null && data.msg.indexOf('再次发送') != -1){
					setTimeout(function(){
						$('#info_msg1').hide();
					}, 1500);
				}
			}
		},'json');
	});
	
	//更新密码
	$('#submit').click(function(){
		var phoneCode = $("#phoneCode").val();
		var pwd = $("#password1").val();
		if($("#accountName").val() == ""){
			showMsg("请输入手机号码");
			return;
		}
		if(phoneCode == null || phoneCode == ''){
			showMsg("请输入短信验证码");
			return false;
		}
		if(pwd == null || pwd == ''){
			showMsg("请输入密码");
			return false;
		}
		if(pwd.length < 6){
			showMsg("密码必须6位以上");
			return false;
		}
		if($("#password2").val() == null || $("#password2").val() == ''){
			showMsg("请输入确认密码");
			return false;
		}
		if($("#password2").val().length < 6){
			showMsg("确认密码必须6位以上");
			return false;
		}
		var validate=/^(?![^a-zA-Z]+$)(?!\D+$).{6,100}$/;
		if(!validate.test(pwd)){
			showMsg("密码必须由数字和字母组成");
			return false;
		}
		if(pwd != $("#password2").val()){
			showMsg("两次输入密码不一致");
			return false;
		}
		$.post("${ctx}passport/update_password.do", {"phoneCode":phoneCode,"password":pwd},function(data){
			if(data.succ){
				showMsg("密码更新成功","","${ctx}logon.do");
			}else{
				showMsg(data.msg);
				$("#phoneCode").val('');
			}
			
		},'json');
	});
	</script>
	
</body>
</html>