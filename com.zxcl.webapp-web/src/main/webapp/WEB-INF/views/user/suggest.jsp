<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../commons/taglibs.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js" /></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body style="background: #fff;">
	<div class="head">
		<div class="back lt" onclick="history.back();"></div>
		<p class="title">意见反馈</p>
	</div>
	<form class="user_inp content" action="suggest.do" method="post">
		<div class="suggest part1">
			<textarea class="area boxSet"  id="suggest" placeholder="亲，~点击这里输入意见反馈信息，我们将为您不断改进。"  required="required" maxlength=1000 ></textarea>
			<%-- <div class="tit">联系电话：</div>
			<input class="inp boxSet"  id="tel" type="tel" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$" placeholder="请输入电话号码" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength=11 required=""   />
			<div class="tit">图片验证：</div>
			<div class="div">
				<input type="text" name="pcode" id="imageCode" placeholder="请输入图片验证码" required="" /> <img class="imgyzm" src="${ctx}call/random_code.do" id="image" />
				<!--  id="getyzm" 开始秒数变化 -->
			</div> --%>

			<div class="btndiv" style="margin-top: 30px;">
				<input class="submit" id="submit1" type="submit" value="确定提交" />
				<!--  id="submit" 填写完成后的状态 -->
			</div>
		</div>
	</form>
	<script>
		$('#image').click(function() {
			changeImageCode();
		});
		var changeImageCode = function() {
			$('#image').attr('src', "${ctx}call/random_code.do?r=" + Math.random());
			$('#imageCode').val('');
		};
		
		$("form").html5Validate(function() {
//			var pcode=$("#imageCode").val();
			var suggest=$("#suggest").val();
//			var tel=$("#tel").val();
				//$.post("${ctx}suggest.do",{"pcode":pcode,"tel":tel,"suggest":suggest},function(data){
				$.post("${ctx}suggest.do",{"suggest":suggest},function(data){
					if(data.succ){
						showMsg("您的反馈已收到，感谢您的建议！","","self");
						//window.setTimeout(function(){location.href="${ctx}myself.do"},2000);
					}else{
						showMsg(data.msg);
					}
				},'json');		
		});
		
	</script>
</body>
</html>