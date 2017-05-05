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
	<title>账单明细</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body style="background:#fff;">
	<div class="head"><div class="back lt" onclick="window.location.href='${ctx}wallet/index.do';"></div><p class="title">账单明细</p></div>
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
		$.post("${ctx}wallet/bill_list.do",{'currentPage':parseInt($('#currPage').val()),'pageSize':30},function(data){
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
					am = parseFloat(item.billAmount).toFixed(2);
					html+= "<tr>";
					
					//c1绿色,c2红色
					if(am < 0){
						html += '<td class="f16 c1 money">'+am+'</td>';
					}
					else{
						html += '<td class="f16 c2 money">+'+am+'</td>';
					}
					
// 					if("O" == item.billType || "WO" == item.billType){
													
// 						//支出 暂时只有提现 am肯定为正数
// 						if(am < 0){
// 							html += '<td class="f16 c1 money">'+am+'</td>';	
// 						}
// 						else{
// 							html += '<td class="f16 c1 money">-'+am+'</td>';
// 						}
						
// 					}else{
						
// 						//收入 有可能为负数(佣金结算)
// 						if(am < 0){
// 							html += '<td class="f16 c1 money">'+am+'</td>';
// 						}
// 						else{
// 							html += '<td class="f16 c2 money">+'+am+'</td>';
// 						}
// 					}
					html += '<td>'+item.showName+'</td><td>'+new Date(item.crtTm).format('yyyy.MM.dd hh:mm')+'</td>';
					html+= "</tr>";
				}
				$('#datas').append(html);
				
			}else{
				showMsg(data.msg);
				$('.wrapper-load-more').hide();
			}
		},'json');
	}
  	//毫秒日期格式化
	Date.prototype.format = function (fmt) {
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	</script>
</body>
</html>