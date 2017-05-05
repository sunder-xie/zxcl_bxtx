<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<jsp:include page="../commons/taglibs.jsp" />
<!DOCTYPE html>
<html lang="ch">
<head>
<meta charset="UTF-8">
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供你选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}webuploader/webuploader.css">
<link rel="stylesheet" type="text/css" href="${ctx}webuploader/style.css">
<title>上传</title>
<style type="text/css">
	div.info{
		display:none !important;
	}
	.btns2{
		display:none;
		margin: 0 0 0 30%;
	}
	#uploader .filelist li img{
	    height: 90px;
    	width: 90px;
	}
</style>
</head>
<body>
<div id="uploader">
	<div class="queueList">
		<div id="dndArea" class="placeholder">
			<div id="filePicker"></div>
			<p class="paddingbottom10">支持多上传</p>
		</div>
	</div>
	<div class="statusBar" style="display: none; height: 90px;">
		<div class="progress">
			<span class="text">0%</span> <span class="percentage"></span>
		</div>
		<div class="info"></div>
		<div class="btns btns1">
			<div id="filePicker2"></div>
			<div class="uploadBtn">开始上传</div>
			<div class="webuploader-pick comeplete_btn">关闭</div>
		</div>
		<div class="btns btns2" align="center">
			<div class="webuploader-pick comeplete_btn">关闭</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var serviceUpload = {
		fileServerSysId:'bxtx',
		fileServerUploaderUrl:'${baseDomain}/file_server/file/uploadAsStream.do',
		fileServerShowFileBaseUrl:'${baseDomain}/file_server/files/',
		serviceFileAdd:function(fileId){
			if(null != fileId && '' != fileId){
				$.post('${ctx}upload/inquiry_file_add.do',{'fileId':fileId, 'inquiryId':'${inquiryId}'},function(data){
					if(data.succ){
						
					}
				},'json');
			}
		},
		serviceFileDel:function(){
			$.post('${ctx}upload/inquiry_file_del.do',{'fileId':''},function(data){
				if(data.succ){
					
				}
			},'json');
		},
		serviceFileList:function(){
			$.post('${ctx}upload/inquiry_file_list.do',{'inquiryId':'${inquiryId}'},function(data){
				var list = data.data;
				if(data.succ && null != list && '[]' != list && list.length > 0){
					var html = '';
					$('#dndArea').addClass('element-invisible');
					var item;
					for(var i = 0;i<list.length;i++){
						item = list[i];
						html += '<li id="'+item.fileId+'" class="state-complete"><p class="imgWrap"><img src="'+serviceUpload.fileServerShowFileBaseUrl+item.fileId+'"></p></span><span class="success"></span></li>';
					}
					$(".filelist").append(html);
					$('.statusBar').show();
					$('.btns1').show();
					$('.btns2').hide();
					try {
						uploader.refresh();
					} catch (e) {
					}
					
				}
				else{
					$('.btns1').hide();
					$('.statusBar, .btns2').show();
				}
			},'json');
		}
}
</script>
<script type="text/javascript" src="${ctx}webuploader/webuploader.js?v=6.0"></script>
<script type="text/javascript" src="${ctx}webuploader/upload.js?v=2.0"></script>
<script type="text/javascript">
serviceUpload.serviceFileList();
$('.comeplete_btn').click(function(){
	parent.closeIframe();
});
$('div.file-panel span.cancel').live('click', function(){
	$(this).parents('li').remove();
	var id = $(this).parents('li').attr('id');
	uploader.removeFile(id);
});
</script>
</body>
</html>