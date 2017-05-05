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
	<div class="tipsel regok txc" style="padding:15px; width:70%; ">
		<div class="img bgc" style="background-image:url(${ctx}v3/images/mrhead.png);"></div>
		<div class="name text1"></div>
		<div class="con">
			<div class="c1">恭喜您</div>
			<div id="id_cwx">成为车险业务的一员</div>
			<div class="c2"></div>
		</div>
		<div class="kszq" id="kszqb" onclick='location.href="${ctx}logon.do?user_id=${tel}"'>开始赚钱</div>
	</div>
	<div class="head" style="z-index: 201;"><div class="back lt" onclick="history.back();"></div>
		<c:choose>
			<c:when test="${'2' eq regType}">
				<p class="title">名义代理人注册</p>
			</c:when>
			<c:otherwise>
				<p class="title">注册</p>
			</c:otherwise>
		</c:choose>
	</div>
	<form  action="" method="post" id="formId"  class="user_inp content">
		<div class="part1">
			<c:choose>
				<c:when test="${'2' eq regType}">
					<div class="clear" style="display:none;">
						<span class="lt">邀请码</span>
						<div class="div">
							<input type="number"  id="invitation"   name="invitation" placeholder="请输入邀请码" onkeyup="this.value=this.value.replace(/\D/g,'')" required="" />
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="clear">
						<span class="lt">邀请码</span>
						<div class="div">
							<input type="number"  id="invitation"   name="invitation" placeholder="请输入邀请码" onkeyup="this.value=this.value.replace(/\D/g,'')" required="" />
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="clear">
				<span class="lt span">+86</span>
				<div class="div">
					<input type="tel" id="tel" name="tel" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$" placeholder="手机号" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength=11 required="" />
				</div>
			</div>
			<div class="clear">
				<span class="lt">短信验证</span>
				<div class="div">
					<input type="number"  id="vidate" name="vidate" placeholder="请输入短信验证码" required="" />
					<span class="getyzm" id="getting">获取验证码</span><!--  id="getyzm" 开始秒数变化 -->
				</div>
			</div>
			<div class="clear">
				<span class="lt">姓名</span>
				<div class="div">
					<input id="micro_name"  placeholder="请填写真实姓名"   required />
				</div>
			</div>
			<div class="clear">
				<span class="lt">密码</span>
				<div class="div">
					<input type="password"  id="password"  name="password" placeholder="请输入6位以上含字母和数字的密码"   required />
				</div>
			</div>
			<div class="clear">
				<span class="lt">确认密码</span>
				<div class="div">
					<input type="password"  id="eqlpassword" placeholder="请输入6位以上含字母和数字的密码" required />
				</div>
			</div>
		</div>
	</div>
		<c:choose>
			<c:when test="${'2' eq regType}">
				<p class="xy txr" style="display:none;">
			</c:when>
			<c:otherwise>
				<p class="xy txr">
			</c:otherwise>
		</c:choose>
			<input class="ck" type="checkbox" id="zx_mianze" checked="checked" />我已阅读并遵守<a class="c2" href="${ctx }mzsm.do">《保行天下协议》</a>
		</p>
		<div class="btndiv"><input id="submit1"  class="submit" type="submit" value="确认提交" /><!--  id="submit" 填写完成后的状态 --></div>
	</form>
	<script type="text/javascript" src="${ctx }v3/js/jquery.cookie.js"></script>
	<script type="text/javascript">
	$(function(){
		
		var invitation='${invitation}';
		if(invitation){
			$("#invitation").val(invitation);
			$("#invitation").attr('disabled',true);
		}
		/*仿刷新：检测是否存在cookie*/
		var count=$.cookie("captcha");
		if(count){  var btn = $('#getting');
        btn.val(count+'s').attr('disabled',true).css('cursor','not-allowed');
        var resend = setInterval(function(){
            count--;
            if (count > 0){
                btn.val(count+'s').attr('disabled',true).css('cursor','not-allowed');
                $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});
            }else {
                clearInterval(resend);
                btn.val("获取验证码").removeClass('disabled').removeAttr('disabled');
            }
        }, 1000);}
      
		$('#info_msg1').hide();
		
		$("#getting").on("click",function(){
			var yz=/^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$/
			var tel = $("#tel").val();
			if(tel == null || '' == null){
				showMsg("您尚未输入手机号");
				return;
			}
			if(!yz.test(tel)){
				showMsg("您输入的手机号格式不准确");
				return;
			}
			var btn = $(this);
            var count = 60;
			$.post("${ctx}/sms.do",{"tel":tel},function(data){
				if(!data.success){
					showMsg(data.message);
				}else{
		            var resend = setInterval(function(){
		                count--;
		                if (count > 0){
		                    btn.html(count+"s");
		                    $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});
		                }else {
		                    clearInterval(resend);
		                    btn.html("获取验证码").removeAttr('disabled');
		                }
		            }, 1000);
		            btn.attr('disabled',true).css('cursor','not-allowed');
				}
			});
		});
		$("#tel").on("click",function(){
			if($("#exct").attr('disabled')){
				$("#exct").attr('disabled',false)
			}
		})
		
		$('#result_yes1').on('click',function(){
			$('#info_msg1').hide();
		});
		//提交
		$("form").html5Validate(function() {
			if($("#zx_mianze").attr('checked') != 'checked'){
				showMsg("请阅读保行天下协议.");
				return ;
			}
			var vidate=$("#vidate").val();
			if(null == vidate || '' == vidate){
				showMsg("请填写手机验证码。");
				return ;
			}
			var invitation=$("#invitation").val();
			var tel=$("#tel").val();
			var password = $("#password").val();
			if(null == password || '' == password){
				showMsg("请填写密码。");
				return ;
			}
			var validate_pwd = /^(?![^a-zA-Z]+$)(?!\D+$).{6,100}$/;
			if (password.length < 6) {
				showMsg("密码必须6位以上");
				return false;
			}
			if (!validate_pwd.test(password)) {
				showMsg("密码必须由数字和字母组成");
				return false;
			}
			var eqlpassword = $("#eqlpassword").val();
			if (password != eqlpassword) {
				showMsg("密码不一致");
				return false;
			}
			var microName = $('#micro_name').val();
			if(null == microName || '' == microName){
				showMsg("请填写姓名");
				return false;
			}
			$.post("${ctx}redirectUserXW.do", {
				"password" : password,
				"user_id" : tel,
				"invitation" : invitation,
				"micro_name" : microName,
				"tel" : tel,
				"micro_class" : '0',
				'regType':'${regType}',
				'vidate':vidate
			}, function(data) {
				if (data.success) {
					if('2' == '${regType}'){
						window.location.href = '${ctx}wallet_bank/index.do';
					}
					else{
						$(".tipsel .name").html(tel);
						$('#kszqb').attr('onclick','location.href="${ctx}index.do?nocheck=1";');
						tipfun($(".tipsel"));
					}
				} else {
					showMsg(data.message);
				}
			}, 'json');
		});
	});
</script>
</body>
</html>