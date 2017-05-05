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
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">配送地址</p></div>
	<div class="content psdz">
		<div class="submit txc" id="submit1" style="bottom: 8%;" onclick="location.href='${ctx}manage_dispatch/toEditManagetAddressPage.do'"><label class="radd">+</label>新建地址</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	
	<script type="text/javascript">
		$(function(){
			
			//get list
			$.post("${ctx}manage_dispatch/list.do",{},function(data){
				if(data.success){
					
					$.each(data.data,function(n,v){
						var moren= '';
						if(v.moren == 1){
							moren = '<input class="ck1" type="radio" name="address" checked="checked" disabled="disabled" />默认配送	';
						}else{
							moren ='<input class="ck1" type="radio" data-id="'+v.id+'" name="address"  onclick="setDefault(\''+v.id+'\')"/>设为默认	'
						}
						var str = '<div class="box" ><div class="line">'+
						'<div class="color"><div class="name">'+v.recName+'</div>'+v.phone+'</div>	'+
						'<div>地址：'+ v.areaChildCodeStr + v.address+'</div></div>'+
						'<div class="line clear">'+ moren +
						'<div class="rt">'+
						'<label class="edit"  onclick="update(\''+v.id+'\')"><img src="${ctx }v3/images/edit.png" />编辑</label>'+
						'<label class="del" onclick="deleteInfo(\''+v.id+'\')"><img src="${ctx }v3/images/del.png" />删除</label>'+
						'</div></div></div>';
						$(".content").append(str);
					});
					
				}
			},'json');
			
			
		});
		
		function update(id){
			window.location.href='${ctx}manage_dispatch/toEditManagetAddressPage.do' + '?id=' + id;
		}
		function add(){
			window.location.href='${ctx}manage_dispatch/toEditManagetAddressPage.do';
		}
		
		function deleteInfo(id){
			if(!confirm("确认删除该地址吗")){
				return ;
			}
			$.post("${ctx}manage_dispatch/del.do",{'id':id},function(data){
				if(data.success){
					window.location.reload();
				}else{
					$('#resultInfo').text(data.message);
					$("#select_result").show();
				}
			},'json');
		}
		function setDefault(id){
			$.post("${ctx}manage_dispatch/set_default.do",{'id':id},function(data){
				if(data.success){
					window.location.reload()
				}else{
					$('#resultInfo').text(data.message);
					$("#select_result").show();
				}
			},'json');
		}
	</script>
</body>
</html>