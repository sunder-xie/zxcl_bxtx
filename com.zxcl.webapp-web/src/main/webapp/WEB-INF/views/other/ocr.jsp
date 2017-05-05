<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>翔云识别服务-HTTP接口-网页版</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!--为简洁，本demo采用cdn方式引入js库-->
<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.js"></script>
<script type="text/javascript">//0.判断是否为ie
	function ifie(){
		if (!!window.ActiveXObject || "ActiveXObject" in window)  { //适用于ie9以上版本
			//alert("ie"); 
			return true;
		}
	    else  {//非ie浏览器
	    	//alert("not ie");
	    	return false;
	    }
	}
</script>
<script type="text/javascript">
	function setImagePreview(avalue) {//方法1.该方法专用于图片预览(火狐43.0.4以及ie11以上通用)
		var docObj = document.getElementById("file");
		var imgObjPreview = document.getElementById("preview");
		
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '250px';
		//imgObjPreview.style.height = '100%';
		//imgObjPreview.src = docObj.files[0].getAsDataURL();
		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		
		return true;
	}
</script>
<script type="text/javascript">//方法2.该方法专用于input中的默认提示文字的效果
	function func1(el) {
		if (el.value == el.defaultValue) {
			el.value = '';
			el.style.color = '#000';
		}
	}
	function func2(el) {
		if (!el.value) {
			el.value = el.defaultValue;
			el.style.color = '#999';
		}
	}
</script>
<script type="text/javascript">//方法3.向该方法传入xml对象返回string字符串
	function xmlToString(xmlData) { 
	    var xmlString;
	    //IE
	    if (ifie()){
	    	xmlString = xmlData.xml;
	    }
	    // code for Mozilla, Firefox, Opera, etc.
	    else{
	        xmlString = (new XMLSerializer()).serializeToString(xmlData);
	    }
	    return xmlString;
	}   
</script>
<script type="text/javascript">//方法4.核心方法.采用HTML5的formdata对象上传带文件的表单；采用jQuery+ajax技术异步无刷新上传表单；根据返回类型将返回数据转为字符串显示在页面上
	$(document).ready(function(){
		var form = document.getElementById("uploadForm");
		$(form).on("submit", function(e) {
			e.preventDefault();//必须
			var oData = new FormData(form);  
			var format = $("input:radio[name=format]:checked").val();//取到用户选择的格式
			
			if(ifie() && format=="xml"){ //如果用户用ie且选择xml返回
				 $.ajax({
					    url: "http://netocr.com/api/recog.do" ,
					    type: 'POST',
					    data: oData,
					    async: true,  //异步，其他默认为false即可，xmlhttprequest自动处理
				        cache: false,  
				        contentType: false,  
				        processData: false,  
				        dataType: "text",//注意，此处设置为text，可以在ie中解析返回的xml
					    success: function (returndata) {
					    		$( '#output').html(returndata);
					    },
					    error: function (returndata) {
			                    alert("请求失败");  
					    }
					  });
			}else{
			  $.ajax({
			    url: "http://netocr.com/api/recog.do" ,
			    type: 'POST',
			    data: oData,
			    async: true,  
		        cache: false,  
		        contentType: false,  
		        processData: false,  
			    success: function (returndata) {
			    	if (format=="json")
			    	  {
			    		var jsonresult=JSON.stringify(returndata);
				    	$( '#output').html(jsonresult);  
			    	  }
			    	else
			    	  {
	    				var xmlresult = xmlToString(returndata);
			    		$( '#output').html(xmlresult);
			    	  } 
			    },
			    error: function (returndata) {
	                    alert("请求失败");  
			    }
			  });
				
			} 
		 });
	});
</script> 
<style type="text/css">
	table.gridtable {
		font-family: verdana,arial,sans-serif;
		font-size:18px;
		color:#333333;
		border-width: 1px;
		border-color: #666666;
		border-collapse: collapse;
		width:850px;
		text-align:center;
		margin:0 auto
	}
	table.gridtable th {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		background-color: #dedede;
		text-align:center;
	}
	table.gridtable td {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		background-color: #ffffff;
		text-align:center;
	}
	table.gridtable2 {
		font-family: verdana,arial,sans-serif;
		font-size:18px;
		color:#333333;
		border-width: 1px;
		border-style: solid;
		border-color: #666666;
		border-collapse: collapse;
		width:850px;
		text-align:center;
		margin:0 auto
	}
	table.gridtable2 th {
		border-width: 0px;
		padding: 8px;
		border-style: solid;
		background-color: #dedede;
		text-align:center;
	}
</style>
</head>
<body >
	<h3>翔云Http接口测试demo</h3>
	<hr />
		<form id="uploadForm" name="uploadForm" action="" method="post" enctype="multipart/form-data" style="text-align:center;margin:0 auto;">
			<table class="gridtable" >
				<tr>
					<th style="width: 380px;height:160px">请选择要识别的图片:</th>
					<th style="width: 500px;height:160px">
						<table >
							<tr>
								<td style="width: 180px;height:200px ;background-color: #dedede;">
									<input type="file" name="file" id="file"  onchange="setImagePreview();">
								</td>
								<td style="width: 300px;height:200px ;background-color: #dedede;">
									<div id="localImag" style="width:auto; height:auto">
										<img id="preview" src="" alt="预览图片处" style="display: block;" >
									</div>
								</td>
							</tr>
						</table>
					</th>
				</tr>
				<tr>
					<td >key:</td>
					<td >
						<div style="float:left;">
							<input type="text" name="key" value="**********" style="width: 400px" onfocus="func1(this);" onblur="func2(this);" />
						</div>
						<div style="color:#999;font-size:15px;" >OCRKey</div>
					</td>
				</tr>
				<tr>
					<td >secret:</td>
					<td >
						<div style="float:left;">
							<input type="text" name="secret" value="********************" style="width: 400px" onfocus="func1(this);" onblur="func2(this);" />
						</div>
						<div style="color:#999;font-size:15px;">OCRSecret</div>
					</td>
				</tr>
				<tr>
					<td>证件类型(typeId):</td>
					<td>
						<div style="float:left;">
							<select name="typeId" style="width: 400px">
								<option value="1">一代身份证</option>
								<option value="2" selected="selected">二代身份证正面</option>
								<option value="3">二代身份证证背面</option>
								<option value="4">临时身份证</option>
								<option value="5">驾照</option>
								<option value="6">行驶证</option>
								<option value="7">军官证</option>
								<option value="9">中华人民共和国往来港澳通行证</option>
								<option value="10">台湾居民往来大陆通行证</option>
								<option value="11">大陆居民往来台湾通行证</option>
								<option value="12">签证</option>
								<option value="13">护照</option>
								<option value="14">港澳居民来往内地通行证正面</option>
								<option value="15">港澳居民来往内地通行证背面</option>
								<option value="16">户口本</option>
								<option value="17">银行卡</option>
								<option value="19">车牌</option>
								<option value="20">名片</option>
								<option value="1000">居住证</option>
								<option value="1001">香港永久性居民身份证</option>
								<option value="1002">登机牌</option>
								<option value="1003">边民证（A）（照片页）</option>
								<option value="1004">边民证（B）（个人信息页）</option>
								<option value="1005">澳门身份证</option>
								<option value="1006">领取凭证(AVA6支持)</option>
								<option value="1007">律师证（A）（信息页）</option>
								<option value="1008">律师证（B） （照片页）</option>
								<option value="1030">全民健康保险卡</option>
								<option value="1031">台湾身份证正面</option>
								<option value="1032">台湾身份证背面</option>
							</select>
						</div>
						<div style="color:#999;font-size:15px;">证件类型</div>
					</td>
				</tr>
				<tr>
					<td>返回格式(format):</td>
					<td>
						<div style="float:left;">
							<input type="radio"  name="format" value="xml"  checked="checked"/>xml
							<input type="radio"  name="format" value="json" />json
						</div>
						<div style="color:#999;font-size:15px;">返回的格式为:xml或json</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
					<!-- 在表单的最后，加一个隐藏的元素可以解决ie10及11中通过 XHR2对象发送ajax请求时表单不完整发送的问题 request body in IE10 / IE11 -->
						<input type="hidden" name="_dontcare">
						<input type="submit" name="commit" value="提交">
					</td>
				</tr>
			</table>
		</form> 
		<table class="gridtable2">
			<tr>
				<th style="width: 380px;">结果显示区域:</th>
				<th style="width: 500px">
					<textarea id="output" style="resize: none;width: 480px;height: 400px;max-width: 490px;max-height: 410px;background-color: #dedede;"></textarea>
				</th>
			</tr>
		</table>
</body>
</html>
