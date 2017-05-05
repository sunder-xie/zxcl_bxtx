<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<jsp:include page="../commons/taglibs.jsp" /><!DOCTYPE html><%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>
<html lang="en">
<head>
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<script type="text/javascript">
		$(function(){
			setNav(3);
		});
	</script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">添加新地址</p></div>
	<form class="content inpbox"><input type="hidden" id="s_id" value="${dispatch.id }">
		<div class="box">
			<div class="line clear">
				<div class="lt">姓名</div>
				<div class="div"><input class="inp" type="text" id="s_recName" value="${dispatch.recName }" placeholder="请输入收件人姓名" /></div>
			</div>
			<div class="line clear">
				<div class="lt">手机号</div>
				<div class="div"><input class="inp"  id="s_phone" type="tel" value="${dispatch.phone }" placeholder="请输入联系电话" /></div>
			</div>
			<div class="line clear">
				<div class="lt">省份</div>
				<div class="div"><select class="inp" id="area_city1" onchange="getCitys('')"></select></div>
			</div>
			<div class="line clear">
				<div class="lt">城市</div>
				<div class="div"><select class="inp" id="area_city2"></select></div>
			</div>
			<div class="line clear">
				<div class="lt">详细地址</div>
				<div class="div"><input class="inp"  type="text" id="s_address"  value="${dispatch.address }" placeholder="区、县、街道" /></div>
			</div>
		</div>
		<div class="subbtn"><input class="submit" onclick="update('${dispatch.id}')" id="submit1" type="button" value="保存" /></div>
	</form>
	
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript">
		
		var mareacode = "${dispatch.areaCode}";
		var mareachildcode ="${dispatch.areaChildCode}";
	
		$(function(){
			//get provinces
			$.post("${ctx}area/get_provinces.do",{},function(data){
				if(data.success){
					var str = '';
					$.each(data.data,function(n,v){
						str += '<option value="'+v.code+'">'+v.name+'</option>';
					});
					$('#area_city1').html(str);
					if('' != mareacode && null != mareacode){
						$('#area_city1 option[value='+mareacode+']').attr("selected",true);
						getCitys(mareachildcode);
					}
					
				}
			},'json');
			
			
		});
		
		//获得市
		function getCitys(areaCode){
			var code = $("#area_city1 option:selected").val();
			$('#area_city2').html('');
			$.post("${ctx}area/get_citys_by_provinces.do",{'code':code},function(data){
				if(data.success){
					var str = '';
					$.each(data.data,function(n,v){
						str += '<option value="'+v.code+'">'+v.name+'</option>';
					});
					$('#area_city2').html(str);
					if('' != areaCode && null != areaCode){				
						$('#area_city2 option[value='+areaCode+']').attr("selected",true);
					}
				}
			},'json');
		}
		
		
		//编辑配送信息
		function update(id){
			
			if(id > 0){
				$.post('${ctx}manage_dispatch/update.do',{'id':id},function(data){
					if(data.success){
						//设置值
						var obj = data.data;
						$('#s_id').val(obj.id);
						$('#s_recName').val(obj.recName);
						$('#area_city1 option[value='+obj.areaCode+']').attr("selected",true);
						getCitys(obj.areaChildCode);
						$('#s_phone').val(obj.phone);
						$('#s_address').val(obj.address);
						//展开
						$('.addressDetail ul').slideDown(500);
						$('.addAddress').attr('data-status',1).text('-');
					}else{
						showMsg(data.message);
					}
				},'json');
			}else{
				var id = $('#s_id').val();
				var recName = $('#s_recName').val();
				var phone = $('#s_phone').val();
				var areaCode = $("#area_city1 option:selected").val();
				var areaChildCode = $("#area_city2 option:selected").val();
				var address = $('#s_address').val();
				$.post("${ctx}manage_dispatch/add.do",{'id':id,'recName':recName,'phone':phone,'areaCode':areaCode,'areaChildCode':areaChildCode,'address':address},function(data){
					if(data.success){
						showMsg("保存成功","","${ctx}manageAddress.do");
					}else{
						showMsg(data.message)
					}
				},'json');
			}
			
			
		}
	</script>
</body>
</html>