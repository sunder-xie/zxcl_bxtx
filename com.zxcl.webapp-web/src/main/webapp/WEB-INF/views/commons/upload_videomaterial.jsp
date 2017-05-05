<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
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
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon"
	href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<script type="text/javascript"
	src="${ctx }v3/js/html5ImgCompress.min.js"></script>
<style>
.upload_videomasterial_container {
	padding-left: 3%;
}

.upload_videomasterial_container .tips {
	margin-top: 10%;
	font-size: 0.8rem;
}

.upload_videomasterial_container .upload_item {
	height: 2rem;
	line-height: 2rem;
	margin-top: 1%;
}

.upload_videomasterial_container .upload_item .upload_type {
	display: inline-block;
	height: 2rem;
	line-height: 2rem;
}

.upload_videomasterial_container .upload_item .upload_btn_div {
	position: relative; /* 保证子元素的定位 */
	width: 92px;
	margin-left: 2%;
	height: 1rem;
	text-align: center;
	cursor: pointer;
	display: inline-block;
}

.upload_videomasterial_container .upload_item .upload_btn_div .upload_btn_span
	{
	display: inline-block;
	color: #666;
	font-family: "黑体";
	height: 1rem;
	border: 1px solid #ccc;
	line-height: 1rem;
}
.upload_videomasterial_container .upload_item .upload_btn_div .uploading_btn_span
	{
	display: inline-block;
	color: #666;
	font-family: "黑体";
	height: 1rem;
	line-height: 1rem;
}
.upload_videomasterial_container .upload_item .upload_btn_div input {
	display: block;
	position: absolute;
	top: 0;
	left: 0;
	width: 60px; /* 宽高和外围元素保持一致 */
	height: 30px;
	opacity: 0;
	-moz-opacity: 0; /* 兼容老式浏览器 */
	filter: alpha(opacity = 0); /* 兼容IE */
}

.upload_videomasterial_container .upload_item .delete_btn_div {
	display: inline-block;
	margin-left: 2%;
}

.upload_videomasterial_container .upload_item .delete_btn_div img {
	vertical-align: middle;
}

.upload_videomasterial_container .upload_item .add_btn_div {
	display: inline-block;
	margin-left: 2%;
}

.upload_videomasterial_container .upload_item .add_btn_div img {
	vertical-align: middle;
}
</style>
</head>
<body style="background:#fff;">
	<div class="upload_videomasterial_container">
		<form action="">
			<div class="tips">上传影像图片，请选择对应的文件类型及图片!</div>
			<div class="upload_item">
				<div class="upload_type">
					<select >
						<option value="10">购车发票</option>
						<option value="11">投保人身份证</option>
						<option value="12">被保人身份证</option>
						<option value="13">行驶证</option>
						<option value="14">验车照</option>
						<option value="15">验车报告</option>
						<option value="16">完税凭证</option>
						<option value="17">减免税凭证</option>
						<option value="18">客户身份识别表</option>
						<option value="19">上年保单证明资料</option>
						<option value="20">其他资料</option>
					</select>
				</div>
				<div class="upload_btn_div">
					<span class="upload_btn_span">上传图片</span> <input type="file"
						name="pic" id="pic" value="上传图片"
						accept="image/gif,image/jpeg,image/jpg,image/png"
						class="upload-input" />
				</div>
				<div class="delete_btn_div">
					<img src="${ctx }v3/images/delete_btn.png" />
				</div>
				<div class="add_btn_div">
					<img src="${ctx }v3/images/add_btn.png" />
				</div>
			</div>
			<div class="upload_item">
				<div class="upload_type">
					<select name="a">
						<option value="0">身份证</option>
						<option value="1">驾驶证</option>
						<option value="2">行驶证</option>
					</select>
				</div>
				<div class="upload_btn_div">
					<span class="uploading_btn_span">上传中..98%</span> <input disabled="disabled" type="file"
						name="pic" id="pic" value="上传图片"
						accept="image/gif,image/jpeg,image/jpg,image/png"
						class="upload-input" />
				</div>
				<div class="delete_btn_div">
					<img src="${ctx }v3/images/delete_btn.png" />
				</div>
				<div class="add_btn_div">
					<img src="${ctx }v3/images/add_btn.png" />
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var inquiryId = '20161009135957644993';
	</script>
	<script type="text/javascript">
		$(function() {
			$("input").change(function(e) {
				var source = e.target.files[0];
				var reader = new FileReader(source);
				var inpute = e;

				reader.onload = function(e) {
					if (source.size > 2 * 1024 * 100) { //用size属性判断文件大小是否超过200K ，否则需要压缩
						new html5ImgCompress(source, {
							before : function(file) {
								$(inpute.target).prev().removeClass("upload_btn_span");
								$(inpute.target).prev().addClass("uploading_btn_span");
								$(inpute.target).prev().html("处理中...");
								$(inpute.target).attr("disabled","disabled");
							},
							done : function(file, base64) {
						        var xhr = new XMLHttpRequest();
						        var fd = new FormData();
						    	  //关联表单数据,可以是自定义参数
						    	  fd.append("inquiryId",inquiryId);
						    	  fd.append("name", $(inpute.target).parent().prev().find("select option:selected").val());
						    	  fd.append("image", base64);
						    	  //监听事件
						    	  xhr.upload.addEventListener("progress", function(evt){
						    		  if (evt.lengthComputable) {
					    	    	    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
					    	    	    $(inpute.target).prev().html("上传中.."+percentComplete.toString() + '%');
					    	    	  }
						    	  }, false);
						    	  xhr.addEventListener("load", function(evt){
						    		//服务断接收完文件返回的结果
						    	    	$(inpute.target).prev().removeClass("uploading_btn_span");
						    	    	$(inpute.target).prev().addClass("upload_btn_span");
						    	    	$(inpute.target).prev().html("查看图片");
						    	  }, false);
						    	  xhr.addEventListener("error", function(evt){
						    		 	dialog("上传失败");
						    		 	$(inpute.target).prev().removeClass("upload_btn_span");
						    		 	$(inpute.target).prev().addClass("uploading_btn_span");
						    	    	$(inpute.target).removeAttr("disabled","disabled");
						    	  }, false);
						    	  //发送文件和表单自定义参数
						    	  xhr.open("POST", "${ctx}upload/inquiry_file_add.do");
						    	  xhr.send(fd);
						        
							},
							fail : function(file) {
								dialog('处理失败,请重新选择文件');
							},
							complate : function(file) {
								
							},
							notSupport : function(file) {
								dialog('浏览器不支持！');
							}
						});
					} else {
						//alert(reader.result);
					}

				}
				reader.readAsDataURL(source);

			});
		});

	</script>

</body>
</html>