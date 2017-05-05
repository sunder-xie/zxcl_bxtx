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
<html lang="en">
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/bxtx_virtual_keyboard_pink_style.css" />
<script type="text/javascript" src="${ctx }v3/js/bxtx_virtual_keyboard.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}webuploader/webuploader.css">
<link rel="stylesheet" type="text/css" href="${ctx}webuploader/style.css">
<script type="text/javascript">
function read_mzsm(){
// 	var s = $('#iframe_xieyi').attr('data-status');
// 	if(s == '0'){
// 		$('#iframe_xieyi').attr('data-status', '1').attr('src','${ctx}identity/xieyi.do');
// 	}
// 	$('#iframe_xieyi,#iframe_xieyi_div').show();
	$('#XY').show();
}
function closeIframe(){
// 	$('#iframe_xieyi,#iframe_xieyi_div').hide();
	$('#XY').hide();
}
</script>
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
    height: 90px;
   	width: 90px;
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
</style>
</head>
<body>
<div id="XY" style="display:none;position: fixed;top: 0;left: 0;border: 0px;z-index: 920;height: 100%;width: 100%;background: white;overflow: auto;">
	<div class="head lrhd userhd">
		<div class="back lt" onclick="closeIframe()"></div>
		<div class="rt txr"></div>
		<p class="title">保险销售服务协议</p>
	</div>
	<div id="connn">
	<div class="clear"></div>
	
	<div class="titlea"> </div>
	
	<div class="dlnr clear"></div>
	<div class="sjnr clear">&nbsp;&nbsp;&nbsp;根据《中华人民共和国合同法》、《保险代理人管理规定（试行）》等有关法律、法规，本着合规经营、服务为先、有序发展的原则，体现对代理人的关爱和负责，本公司与保险销售服务人员（以下称代理人）作如下约定。</div>
	
	
	<div class="dlnr clear">第一条  总则</div>
	<div class="sjnr clear">代理人代公司办理保险业务，在公司约定范围从事代理活动所产生的保险责任由公司所代理的保险公司承担。公司按各项业务的约定时间，支付代理人的代理手续费。</div>
	
	<div class="dlnr clear">第二条  保险费的收取和交付</div>
	<div class="sjnr clear">
		（一）代理人不得为客户垫缴保险费。
		<br>
		（二）任何情况下，代理人均不得使用自己的姓名和账户收款。
	</div>
	
	<div class="dlnr clear">第三条  公司权利</div>
	<div class="sjnr clear">
		（一）公司或公司所代理的保险公司对代理人在授权范围内招揽的保险业务具有最后确认权限。
		<br>
		（二）有权制定和修改各项管理办法和规章制度，随时对代理手续及其他待遇的有关规定进行修改与补充，并通知代理人。
	</div>
	
	<div class="dlnr clear">第四条  公司义务</div>
	<div class="sjnr clear">
		（一）公司按规定的手续费支付比例和时间，向代理人支付代理手续费。
		<br>
		（二）向代理人提供开展代理业务所需的证件、资料、单证及其他的帮助。
	</div>
	
	<div class="dlnr clear">第五条  保险销售人员权利</div>
	<div class="sjnr clear">
		（一）在公司授权范围内从事保险代理活动。
		<br>
		（二）要求公司支付代理手续费。
		<br>
		（三）要求公司提供力所能及的有关证件、保险单证和资料。
	</div>
	
	
	<div class="dlnr clear">第六条  保险销售人员义务</div>
	<div class="sjnr clear">
		（一）应遵守公司制定的与代理人代理业务相关的规章制度。
		<br>
		（二））按公司规定领取有关单证时履行签收手续。
		<br>
		（三）不得与客户串通，损害公司利益。
	</div>
	<div style="width:100%;text-align:center;margin-top:10px;" class="clear">Copyright 2015 四川智迅车联科技股份有限公司</div>
	</div>
	<div style="position: static; width: 0px; height: 0px; border: none; padding: 0px; margin: 0px;"><div id="trans-tooltip"><div id="tip-left-top" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-left-top.png&quot;);"></div><div id="tip-top" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-top.png&quot;) repeat-x;"></div><div id="tip-right-top" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-right-top.png&quot;);"></div><div id="tip-right" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-right.png&quot;) repeat-y;"></div><div id="tip-right-bottom" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-right-bottom.png&quot;);"></div><div id="tip-bottom" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-bottom.png&quot;) repeat-x;"></div><div id="tip-left-bottom" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-left-bottom.png&quot;);"></div><div id="tip-left" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-left.png&quot;);"></div><div id="trans-content"></div></div><div id="tip-arrow-bottom" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-arrow-bottom.png&quot;);"></div><div id="tip-arrow-top" style="background: url(&quot;chrome-extension://ikkbfngojljohpekonpldkamedehakni/imgs/map/tip-arrow-top.png&quot;);"></div></div>
</div>


	<div id="iframe_xieyi_div" style="-webkit-overflow-scrolling:touch; overflow:auto; width:100%; height:1000px;display: none;z-index: 920;position:absolute;"><iframe id="iframe_xieyi" width="100%" height="100%" style="display:none;position: fixed;top: 0;left: 0;border: 0px;z-index: 920;" data-status="0"></iframe></div>
	<div class="fixedbg"></div>
	<div class="loadding txc">上传图片中...</div>
	<div class="head lrhd userhd">
		<div class="back lt" onclick="window.location.href='${ctx}myself.do'"></div>
		<p class="title" style="padding-right: 40px;">实名认证</p>
	</div>
	
	
	
	
	<div class="content inpbox">
		<input type="hidden" id="fileIds" value=""/>
		<div class="box">
			<div class="line clear">
				<div class="lt">身份证号</div>
				<div class="div"><input class="inp" type="text" virtual_keyboard="idcard" id="icardid" placeholder="请输入身份证号" value="${ma.microCardId}"/></div>
			</div>
			<div class="line clear">
				<div class="lt">真实姓名</div>
				<div class="div"><input class="inp"  id="iname" type="text" placeholder="请输入真实姓名" value="${ma.microRealName}"/></div>
			</div>
			<div class="line clear">
				<div class="clear indd" style="text-align: center;">
					<!-- upload -->
					<div id="uploader">
						<div class="queueList">
							<div id="dndArea" class="placeholder">
								<div id="filePicker"></div>
							</div>
						</div>
<%-- 						<img for="file" src="${ctx}v3/images/22.jpg" id="uimg" height="90px" width="120px"/> --%>
						<div class="statusBar" style="display: none; height: 90px;">
							<div class="progress">
								<span class="text">0%</span> <span class="percentage"></span>
							</div>
							<div class="info"></div>
							<div class="btns btns1">
								<div id="filePicker2"></div>
								<div class="uploadBtn">开始上传</div>
							</div>
						</div>
					</div>
					<!-- upload -->
				</div>
			</div>
		</div>
		<p class="xy txr" style="padding-top: 20px;">
			<input class="ck" type="checkbox" id="zx_mianze" checked="checked">我已阅读并遵守<a class="c2" href="javascript:;" onclick="read_mzsm()">《保险销售服务协议》</a>
		</p>
		<div class="subbtn"><input class="submit" style="background: #D64141;" id="subm" type="button" value="确认提交" /></div>
	</div>
	<jsp:include page="../commons/myalert2.jsp" />
	<script type="text/javascript">
		
		$('#subm').click(function(){
			if(!$('#zx_mianze').is(':checked')){
				alert('请勾选保险销售服务协议');
				return false;
			}
			var iname = $('#iname').val();
			var fileIds = $('#fileIds').val();
			var icardid = $('#icardid').val();
			$.post("${ctx}identity/confirm.do", {'iname':iname, 'fileIds':fileIds, 'icardid':icardid} , function(data){
				if(data.succ){
					alert("提交成功");
					setTimeout(function(){
						window.location.href="${ctx}myself.do"
					},1600);
				}
				else{
					alert(data.msg);
				}
			},'json');
		});
		$(function(){
// 			var a = $('#uimg').position();
// 			$('#Filedata').css({'left':a.left,'top':a.top});
		});
		$.post("${ctx}identity/find_confirm.do",{},function(data){
			if(data.succ){
				var identity = data.data;
				if(null != identity){
					var stage = identity.approveState;
					if("1" == stage || 1 == stage){
						$('#tip_smrz').attr('data-t', '1');
						alert('正在认证中,请勿重复提交');
					}
					else if("2" == stage || 2 == stage){
						alert("已通过认证,请勿重复提交");
					}
					else if("3" == stage || 3 == stage){
						alert("未通过认证["+identity.approveReason+"],请重新认证");
					}
				}
			}
		},'json');
		
		
		
		
		var serviceUpload = {
			fileServerSysId:'bxtx',
			fileServerUploaderUrl:'${baseURL}/file_server/file/uploadAsStream.do',
			fileServerShowFileBaseUrl:'${baseURL}/file_server/files/',
			serviceFileAdd:function(fileId){
				if(null != fileId && '' != fileId){
					$('#uimg').hide();
					$('#fileIds').val($('#fileIds').val()+','+fileId);
				}
			}
		}
	</script>
	<script type="text/javascript" src="${ctx}webuploader/webuploader_identity.js"></script>
	<script type="text/javascript" src="${ctx}webuploader/upload_indentity.js?v=1.0"></script>
	
	<script type="text/javascript">
		$(function(){
// 			$('#dndArea').addClass('element-invisible');
			$('.statusBar').show();
// 			$('#filePicker .webuploader-pick').html('身份证正面');
			$('#uimg').click(function(){
				$('#filePicker2 input[name=file].webuploader-element-invisible').click();
			});
		});
	</script>
</body>
</html>