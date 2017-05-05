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
	<title>修改密码</title>
	<meta name="keywords" content="车险,车险报价,车险报价" />
	<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">修改密码</p></div>
	<form class="user_inp content">
		<div class="part1">
			<div class="clear">
				<span class="lt">原密码</span>
				<div class="div">
					<input type="password" id="oldpwd" placeholder="请输入原密码"  required="" />
				</div>
			</div><div class="clear">
				<span class="lt">新密码</span>
				<div class="div">
					<input type="password" id="pwd" placeholder="请输入新密码"   required="" />
				</div>
			</div>
			<div class="clear">
				<span class="lt">确认密码</span>
				<div class="div">
					<input type="password" id="pwd2" placeholder="请再次输入新密码"  required="" />
				</div>
			</div>
		</div>
		<div class="btndiv"><input class="submit" type="button" id="submit1" value="确认修改" /><!--  id="submit" 填写完成后的状态 --></div>
	</form>
	<jsp:include page="../commons/menu.jsp" />
	<script>
	$(function(){
		setNav(3);
	});
	
var count = false;
function alerting(obj){
	var str = [];
	for(var o in obj){
		str.push(obj[o])
	}
	alert(str.join(''));
	str = [];
}
$('#submit1').click(function(){
	var oldpwd = $('#oldpwd').val();
	var pwd = $('#pwd').val();
	var pwd2 = $('#pwd2').val();
	if(isNull(oldpwd)){
		showMsg('请输入原密码');
		//$('#resultInfo').text('请输入原密码');
		//$("#select_result").show();
		$('#oldpwd').focus();
		return false;
	}else{
		$.ajax({
			type:'post',
			url:'${ctx}editPwd2.do',
			data:{'oldpwd':oldpwd},
			dataType:'json',
			async:false,
			success: function(result){
				if(!result.succ){
					showMsg(result.msg);
					//$("#select_result").show();
					$('#oldpwd').focus();
					count = true;
				}
			}
		});
	}
	if(count){
		count = false;
		return false;
	}
	if(isNull(pwd)){
		showMsg('请输入新密码');
		$('#pwd').focus();
		//$("#select_result").show();
		return false;
	}
	if(isNull(pwd2)){
		showMsg('请再次输入新密码');
		$('#pwd2').focus();
		//$("#select_result").show();
		return false;
	}
	
	var pwd2Code = true;
	//验证数字
	var regName1 =/^[0-9]*$/;
	if(pwd2.match(regName1)){
		pwd2Code = false;
	}
	//验证字符
	var regName2 =/^[A-Za-z]+$/;
	if(pwd2.match(regName2)){
		pwd2Code = false;
	}
	//验证字符和数字
	var regName3 =/^[A-Za-z0-9]+$/;
	if(!pwd2.match(regName3)){
		pwd2Code = false;
	}
	if(pwd2.length < 6 || !pwd2Code){
		showMsg('账户密码必须为包含数字和字母的6位以上的密码');
		$('#pwd2').focus();
		//$("#select_result").show();
		return false;
	}
	if(pwd != pwd2){
		showMsg('两次输入密码不一致');
		$('#pwd').focus();
		//$("#select_result").show();
		return false;
	}
	$.post('${ctx}editPwd.do',{'oldpwd':oldpwd,'pwd':pwd},function(data){
		if(data.success){
			$('#oldpwd,#pwd,#pwd2').val('');
			showMsg('修改成功');
			//$("#select_result").show();
		}else{
			showMsg(data.message);
			//$("#select_result").show();
		}
	},'json');
});
</script>
</body>
</html>