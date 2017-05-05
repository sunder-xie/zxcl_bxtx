<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<jsp:include page="./commons/taglibs.jsp" />
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
	<script type="text/javascript" src="${ctx}v3/js/jquery.cookie.js"></script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">登录</p></div>
	<div class="content">
		<div class="login">
			<c:choose>
				<c:when test="${empty agentDisplayDTO.headImgUrl || '' eq agentDisplayDTO.headImgUrl }">
					<img class="img" src="${ctx}v3/images/logo.png" />
				</c:when>
				<c:otherwise>
					<img class="img" src="${agentDisplayDTO.headImgUrl}" />
				</c:otherwise>
			</c:choose>
			<div id="remind_info" style="text-align:center; display:none;">
				<span  class="remind_info">用户名与密码不匹配！</span>
			</div>
			<form  method="post" action="${ctx}j_spring_security_check">
				<input class="inp boxSet uname" id="j_username1" autocomplete="off" autofocus="autofocus" placeholder="请输入用户名或手机号" type="text" name="j_username" required="" />
				<input class="inp boxSet upwd" id="j_password1" autocomplete="off" placeholder="请输入密码" type="password" name="j_password" required="" />
				<div class="p clear">
					<p class="rt">记住密码</p>
					<input class="rt ck" id="checkbox12"  type="checkbox" name="_spring_security_remember_me" />
				</div>
				<input  id="submit1" class="submit" type="submit" value="登录" /><!--  id="submit" 填写完成后的状态 -->
			</form>
			<div class="abtn clear">
				<div class="lt" onclick="location.href='${ctx}passport/index.do'">忘记密码？</div>
				<div class="rt c2" onclick="location.href='${ctx}addMicro.do?openId=${openId}&invitation=${invitation}'">立即注册</div>
			</div>
		</div>
	</div>
	<script> 
	//屏蔽页面移动默认动作
	document.addEventListener('touchmove', function (e) {   
	    e.preventDefault(); 
	}, false);
	$("#tt").click(function(){
		tt();
	});
	function tt(){
		$.cookie("passWord", '', { expires: -1 });
		$.cookie("rmbUser", "false", { expires: -1 });
		$("#j_password1").text("");
		$("#checkbox12").attr("checked", false);
		$("#remind_info").fadeIn(100);
		/* setTimeout(function(){
			$("#remind_info").fadeOut(100);
		},10000); */
	}
	
	var exeption="${SPRING_SECURITY_LAST_EXCEPTION}";
	if(exeption){
		$('.remind_info').text('${SPRING_SECURITY_LAST_EXCEPTION.message}');
		tt();
	}
	<%session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");%>
	
	$(function(){
		
		$("#j_username1").on("keydown",function(){
			 $("#j_password1").val("");
			 $.cookie("rmbUser", "false", { expires: -1 });        // 删除 cookie
	         $.cookie("userName", '', { expires: -1 });
	         $.cookie("passWord", '', { expires: -1 });
	         $("#checkbox12").attr("checked",false);
		})
		
// 		用jscookie来缓存用户信息
		 $("#j_username1").val($.cookie("userName"));
	   	 if ($.cookie("rmbUser") == "true") {
	        $("#checkbox12").attr("checked", true);
	        $("#j_username1").val($.cookie("userName"));
	        $("#j_password1").val($.cookie("passWord"));
	    }else{
	    	var user_id ='${user_id}';
	    	if(user_id){
	    		$("#j_username1").val(user_id);
	    	     $("#checkbox12").attr("checked",false);
	    	}
	    	
	    }
		    
	    $(".btn").on("click",function(){
	    	var userName = $("#j_username1").val();
	    	$.cookie("userName", userName, { expires: 7 });
	    })
	    
		$("#checkbox12").on("click",function(){
			var userName = $("#j_username1").val();
		    var passWord = $("#j_password1").val();
		    if( $("#checkbox12").attr("checked")){
		        if(userName !="" && userName !=null  ){
			    	if(passWord !="" && passWord !=null){
		    		   $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
				       $.cookie("userName", userName, { expires: 7 }); // 存储一个带7天期限的 cookie
				       $.cookie("passWord", passWord, { expires: 7 }); // 存储一个带7天期限的 cookie
			    	}else{
			    		$("#j_password1").testRemind("您尚未输入密码");
			    		 $("#checkbox12").attr("checked", false);
			    	}
			    	
			    }else{
			    	$("#j_username1").testRemind("您尚未输入用户名");
			    	 $("#checkbox12").attr("checked", false);
			    }
		    }else{
		    	 $.cookie("rmbUser", "false", { expires: -1 });        // 删除 cookie
		         $.cookie("userName", '', { expires: -1 });
		         $.cookie("passWord", '', { expires: -1 });
		    }
		});
	})
</script>

<script type="text/javascript">
	$("form").html5Validate(function() {
		this.submit();	
	}, {
		validate:function(){
			var username=$("input[name=j_username]").val();
			var password=$("input[name=j_password]").val();
			if(!username){
				$("#j_username1").testRemind("您尚未输入用户名");
				return false;
			}
			if(!password){
				$("#j_password1").testRemind("您尚未输入密码");
				return false;
			}
			return true;			
		}
	});
</script>
</body>
</html>