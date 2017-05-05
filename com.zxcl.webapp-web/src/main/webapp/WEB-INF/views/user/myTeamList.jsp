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
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
.arr_r_t{
    background: url(${ctx}v3/images/arr_r.png) no-repeat 90% center #fff;
    background-size: 9px;
}
.zdmx tr td{
    padding: 15px 20% 15px 10px !important;
}
</style>
</head>
<body style="background:#fff;">
	<div class="head"><div class="back lt" onclick="window.location.href='${ctx}myTeam.do';"></div><p class="title">团员列表</p></div>
	<div class="content zdmx">
		<table id="datas" class="tab" cellspacing="0" cellpadding="0">
		</table>
		<div class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
		<input type="hidden" id="currPage" value="1"/>
	</div>
	<jsp:include page="../commons/menu.jsp" />
		<script type="text/javascript">
	$(function(){
		setNav(3);
		dataPage();
	});
	$('.wrapper-load-more').on('click',function(){
		$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
		dataPage();
	});
	var dataPage = function(){
		$.post("${ctx}myTeamList.do",{'currentPage':parseInt($('#currPage').val())},function(data){
			if(data.succ){
				var page = data.data;
				var list = page.dataList;
				var html = '';
				var item ;
				
				if(page.pageCount == 1 || page.currentPage == page.pageCount){
					$('.wrapper-load-more').hide();
				}else{
					$('.wrapper-load-more').show();
				}
				
				if(list == null || list.length == 0){
					$('.wrapper-load-more').hide();
					showMsg('查无记录...');
					return false;
				}

				var am;
				for (var i = 0; i < list.length; i++) {
					item = list[i];
					var t_name = item.name;
					var t_tel = item.tel;
					var t_userId = item.userId;
					if(t_name == null || t_name == ''){
						t_name = '姓名不详';
					}
					if(t_tel == null || t_tel == ''){
						t_tel = '联系电话不详';
					}else{
						t_tel = t_tel.substring(0,3)+'****'+t_tel.substring(7,11);
					}
					html+= '<tr onclick="window.location.href=\'${ctx}myTeamPerson/'+t_userId+'.do\'">';
					html += '<td class="f16 c1">'+t_name+'</td>';
					html += '<td class="arr_r_t">'+t_tel+'</td>';
					html+= "</tr>";
				}
				$('#datas').append(html);
				
			}else{
				showMsg(data.msg);
				$('.wrapper-load-more').hide();
			}
		},'json');
	}
	</script>
</body>
</html>