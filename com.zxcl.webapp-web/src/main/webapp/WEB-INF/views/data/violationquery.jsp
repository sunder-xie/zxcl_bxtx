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
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<script type="text/javascript">
		
	</script>
	<style>
		html{
			height:100%;
		}
		body{
			height:100%;
		}
		.btn2{
			background: #cacccb;
		}
		.wzcxcontainer,.wzinfocontainer{
			padding-top:20px;margin-top:45px;width:100%;height:auto;background: #f5f3f3;z-index:901;font-family: "微软雅黑";
			fong-size:0.8rem;
		}
		.jtzk{
			position: relative;
		    width: 10px;
		    height: 10px;
		    left: 4px;
		    border: 1px solid #2BA5E9;
		    border-top: 0;
		    border-right: 0;
		    -webkit-transform: rotate(-45deg);
		    transform: rotate(-45deg);
		    float: right;
		    margin-right: 5px;
		    margin-top: 2px;
		}
		.ckxq{
			position: relative;
		    width: 10px;
		    height: 10px;
		    left: 4px;
		    border: 1px solid #2BA5E9;
		    border-top: 0;
		    border-right: 0;
		    -webkit-transform: rotate(-135deg);
		    transform: rotate(-135deg);
		    float: right;
		    margin-right: 10px;
		    margin-top: 2px;
		}
		.wzinfocontaier{
			background: #fff;margin-top:5px;padding-top:5px;font-size:0.8rem;padding-bottom:5px;
		}
		.wzinfotitle{
			width:90%;margin:0 auto;border-bottom:1px solid #F0F0F0;padding-bottom:3px;margin-top:3px;padding-bottom:10px;
		}
		.wzinfodidian{
			clear:both;width:90%;margin:0 auto;margin-top:10px;
		}
		.wzinfoyy{
			clear:both;width:90%;margin:0 auto;margin-top:10px;
		}
		.wztj{
			padding-left:20px;background: #fff;padding-bottom:5px;
		}
		.clxxmx{
			margin-left:20px;color:#696868; height: 25px;line-height: 25px;
		}
		.clxxmx span{color:#000;
		}
		
		/* Basic Grey */
.basic-grey {
}
.basic-grey input[type="text"], .basic-grey input[type="email"], .basic-grey textarea, .basic-grey select {
border: 1px solid #DADADA;
color: #888;
height: 30px;
margin-bottom: 16px;
margin-right: 6px;
margin-top: 2px;
outline: 0 none;
padding: 3px 3px 3px 5px;
width: 70%;
font-size: 12px;
line-height:15px;
box-shadow: inset 0px 1px 4px #ECECEC;
-moz-box-shadow: inset 0px 1px 4px #ECECEC;
-webkit-box-shadow: inset 0px 1px 4px #ECECEC;
-webkit-appearance:none ;
}
.tab tr td:nth-child(1){width:25%;}
		.tab tr td:nth-child(2){width:27%;}
		.tab tr td:nth-child(3){width:23%;}
		.tab tr td:nth-child(4){width:24%;}
	</style>
</head>
<body class="chaxun bdcx">
	<div class="head"><div class="back lt" onclick="pageBack()"></div><p class="title">车辆违章查询</p></div>
	<div id="searchContainer">
		<div class="search" style="top:45px;">
			<div class="rt bc2" id="btn_query">搜索</div>
			<div class="div"><input class="inp boxSet" id="queryCondition" placeholder="搜索车牌   车主姓名" /></div>
		</div>
		<table class="tab" id="bdcx" cellspacing="0" cellpadding="0" style="    margin: 100px 0 0 0;">
	
		</table>
		
		<div class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
	</div>
	<div class="wzcxcontainer" style="display:none;">
		<div style="padding-top:10px;padding-left:10px;margin-right:2px;height:25px;line-height:25px;padding-bottom:10px;background: #fff;">
			<div style="display: inline-block;background:#e8105e;width:3px;height:11px;margin-left:10px;margin-right:10px;"></div>
			<div style="display: inline-block;">车辆信息：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- 川AABCDE --> </div>	
			<div style="display:inline-block;float:right;margin-right:20px;" class="jtzk" onclick="hideWzInfo()"></div>
		</div>
		<div style="background: #fff;padding-bottom:5px;margin-bottom:5px;">
		<div class="clxxmx">车牌号：<span> 川AABCDE</span></div>
		<div class="clxxmx">车主姓名：<span> 张六福</span></div>
		<div class="clxxmx">车架号：<span> LSGGG54W3ESHTR585</span></div>
		<div class="clxxmx">发动机号：<span> FEWHT744</span></div>
		
		<div class="basic-grey" style="margin-left:20px;color:#696868;height: 35px;line-height: 35px;" id="validateDiv">
			<div style="display: inline-block;">验证码：</div>
			<input style="display: inline-block;width:100px;height:25px" placeholder="请输入验证码" type="text" id="vc" />
			<img style="vertical-align: middle;display: inline-block;height: 30px; width: 80px;" id="validateImg" src="data:image/png;base64," />
			<img onclick="referValiadte()" style="vertical-align: middle;display: inline-block;height: 15px; width: 15px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RTM5OTVDQUZEQzkxMTFFNkFDRDY5NDlGQTUyQzYxODIiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RTM5OTVDQjBEQzkxMTFFNkFDRDY5NDlGQTUyQzYxODIiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpFMzk5NUNBRERDOTExMUU2QUNENjk0OUZBNTJDNjE4MiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpFMzk5NUNBRURDOTExMUU2QUNENjk0OUZBNTJDNjE4MiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pur4H7AAAAEbSURBVHjaYvz//z8DNQETAx2BFRAvB+LnQPwfSi8FYgtcGvD59jjUEBD+B8RfoDRMbCs23+EykAuq6QYQm6PJWQLxeaj8NSBmxGcgI5ICdiBmwRMcc6GGLsZnYBkQd5IQxhehhupiM5AHiH9DFSgTaWAAVH0fuoHCQHwCKcAfEWkgLKxPIhsIiilFIL4ExN+A+A8QHwJiVQKGyQBxD9RALSDuAGIjdEVnoQr4iHThFSRfbQNiCfRIaYFKhhJpoChU/RtcsawOVXCfhJguB+IEfAl7MtTQTXgMEYKGO9FZbx/U0KdAHAH1GheUjoRmw//QxE90Xp6MFODY8GxSCwcQUADiBiA+DcSfoXQjEMvjK20YB30BS3UDAQIMAP1Ddw4uqdJRAAAAAElFTkSuQmCC" />	
		</div>
			
		<div id="searchWzBtn" style="display:none; background: #D64141; margin-top:10px;margin-left:20px; width:100px;  margin-right: 5% !important;line-height: 35px;color: #fff;font-size: 18px;border-radius: 5px;text-align: center;" onclick="searchWz()">查询违章</div>
		
		</div>
		
		<div class="wzinfocontainer" style="margin-top:0px;padding-top:0px;">
			<div style="padding-top:10px;padding-left:10px;margin-right:2px;height:25px;line-height:25px;padding-bottom:10px;background: #fff;">
				<div style="display: inline-block;background:#e8105e;width:3px;height:11px;margin-left:10px;margin-right:10px;"></div>
				<div style="display: inline-block;">违章信息：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>	
			</div>
			
			<div class="wztj clear">
				
			</div>
		</div>
		
	</div>
	
	<input type="hidden" id="currPage" value="1"/>
	<jsp:include page="../commons/menu.jsp" />
	<script type="text/javascript" src="${ctx }activity/wallte/jquery.easing.1.3.js"></script>
	<script type="text/javascript">
	function pageBack(){
		
		if($(".wzcxcontainer").css("display") != "none"){
			hideWzInfo();
		}else{
			history.back();
		}
	}
	var acorderId = "";
	var isSearing = false;
	function searchWz(){
		getValidateCodeInfo(acorderId,$("#vc").val());
	}
	function referValiadte(){
		var param = {orderId:acorderId,referVali : "1"};
		$.post("${ctx}getCarVioQueryValiCode.do",param,function(data){
			if(data.result == "2"){
				$("#vc").val("");
				/* $("#bdcx").hide();
				$(".search").hide();
				$(".wzcxcontainer").show();
				$("#validateDiv").show(); */
				$("#validateImg").attr("src","data:image/png;base64," + data.v);
				
			}else {
				dialog("刷新验证码失败，请稍后重试.");
			}
			
		});
		
	}
	function getValidateCodeInfo(orderId,v){
		$(".wzinfocontaier").remove();
		if($("#searchWzBtn").css("display") != "none" && $("#vc").val() == ""){
			dialog("请输入验证码");
			return;
		}
		if(isSearing){
			return ;
		}
		
		if(v != undefined){
			//输入验证码查询
			$("#searchWzBtn").hide();
			$("#searchWzBtn").after("<img style='margin-left:20px;' src='${ctx}images/status_loading.gif' />");
		}
		
		isSearing = true;
		
		acorderId = orderId;
		var param = {orderId:orderId};
		if(v != undefined){
			param = {orderId:orderId,v:v};
		}
		
		if(v == undefined){
			//表示获取验证码.从列表页面点击的.锁定其他按钮，并且显示加载按钮.
			$("#bdcx tbody tr td .btn1").each(function(){
				if($(this).attr("data") == orderId){
					$(this).hide();
					$(this).after("<img src='${ctx}images/status_loading.gif' />");
					$(this).parent().css("text-align","center");
				}else{
					$(this).addClass("btn2");
					$(this).removeClass("btn1");
				}
			});
		}
		
		$.post("${ctx}getCarVioQueryValiCode.do",param,function(data){
			
			
			$(".wzcxcontainer .clxxmx:eq(0)").html("车&nbsp;&nbsp;牌&nbsp;&nbsp;号：<span> "+data.plateno+"</span>");
			$(".wzcxcontainer .clxxmx:eq(1)").html("车主姓名：<span> "+data.name+"</span>");
			$(".wzcxcontainer .clxxmx:eq(2)").html("车&nbsp;&nbsp;架&nbsp;&nbsp;号：<span> "+data.frmno+"</span>");
			$(".wzcxcontainer .clxxmx:eq(3)").html("发动机号：<span> "+data.engno+"</span>");
			
		 	if(data.result == "1" || data.result == "2"){
		 		$(".wzcxcontainer").show();
		 		//特效执行前，要记录高度。不然后面没法显示出来.
		 		if(v == undefined ){
		 			$(".wzcxcontainer").css({"position":"fixed","top":$(window).height(),"min-height":$(window).height() - 150});
		 			$("#searchContainer").attr("oriScrollTop",$(window).scrollTop());
					$(".wzcxcontainer").animate({
					    top: 45
					},1000, 'easeInCubic',function(){
						$(".wzcxcontainer").css({"position":"","top":""});
						$("#searchContainer").hide();
						$(window).scrollTop(0);
				    });
		 			
		 		}
			} 
			
			if(data.result == "1"){
				showData(data);
				$("#validateDiv").hide();
				$("#searchWzBtn").hide();
	 			$(".wzinfocontainer").show();
	 			
			}else if(data.result == "2"){
				$(".wzcxcontainer").show();
				$("#validateDiv").show();
				$(".wzinfocontainer").hide();
				$("#searchWzBtn").show();
				$("#validateImg").attr("src","data:image/png;base64," + data.v);
				
			}else if(data.result == "3"){ 
				$("#validateDiv").hide();
				$("#searchWzBtn").hide();
				$("#searchWzBtn").next().remove();
	 			$(".wzinfocontainer").show();
	 			$(".wztj").html("<b>"+data.errmsg+"</b>");
	 			isSearing = false;
	 			return ;
			}else if(data.result == "-11"){
				$("#vc").val("");
				dialog(data.errmsg,"确定",function(){
					referValiadte();
				});
			}else if(data.result == "-12"){
				$("#vc").val("");
				dialog("验证码错误,请重新输入后重试.","确定",function(){
					referValiadte();
				});
				
			}else if(data.result == "-13"){
				dialog("暂时无法访问交管系统.请稍后再试!.","确定",function(){
					referValiadte();
				});
			}else {
				dialog("暂时无法访问交管系统.请稍后再试!.");
				$("#bdcx tbody tr td .btn").each(function(){
					$(this).show();
					$(this).next().remove();
					$(this).parent().css("text-align","");
					$(this).removeClass("btn2");
					$(this).addClass("btn1");
				});
			}
			
			if(v != undefined && data.result != "1"){
				$("#searchWzBtn").show();
			}

			$("#searchWzBtn").next().remove();
			isSearing = false;
		});
		
	}
	
	function hideWzInfo(){
		$("#vc").val("");
 		$("#searchContainer").css("overflow","hidden");
 		$("#searchContainer").show();
 		$("#searchWzBtn").hide();
 		$(".wzcxcontainer").css({"position":"fixed","top":"45px"});
 		$(window).scrollTop($("#searchContainer").attr("oriScrollTop"));
 		$(".wzcxcontainer").animate({
		    top: $(window).height()
		},1000, 'easeInCubic',function(){
			$(".wzcxcontainer").css({"position":"","top":""});
			$(".wzcxcontainer").hide();

			$("#bdcx tbody tr td .btn").each(function(){
				$(this).show();
				$(this).next().remove();
				$(this).parent().css("text-align","");
				$(this).removeClass("btn2");
				$(this).addClass("btn1");
			});
	    });
 		
 		/* 
		$("#searchContainer").animate({
		    height: $("#searchContainer").attr("oriHeight"),
		    opacity : 1
		},1000, 'easeInCubic',function(){
			
			$("#searchContainer").css("overflow","visible");
			$(".wzcxcontainer").hide();

			$("#bdcx tbody tr td .btn").each(function(){
				$(this).show();
				$(this).next().remove();
				$(this).parent().css("text-align","");
				$(this).removeClass("btn2");
				$(this).addClass("btn1");
			});
	    }); */
	}
	
	function showData(data){
		
		if(data.vios == undefined || data.vios.length < 1){
			$(".wztj").html(" <b>恭喜您！没有新的违章！</b> ");
			return ;
		}
		$(".wztj").show();
		var kf = 0;
		var kk = 0;
		for(var i = 0 ;i<data.vios.length;i++){
			kf += data.vios[i].jf;
			kk += data.vios[i].fkje;
		}
		$(".wztj").html("违章：<b>"+data.vios.length+"</b> 扣分：<b>"+kf+"</b> 罚款：<b>"+kk+"</b>");	
		
		for(var i = 0 ;i<data.vios.length;i++){
			var html = "<div class=\"wzinfocontaier\">"+
			"<div class=\"wzinfotitle clear\">"+
			"<div style=\"display: inline;float:left;\">扣分：<b>"+ data.vios[i].jf+"</b> 罚款：<b>"+data.vios[i].fkje+"</b></div>"+
			"<div style=\"display: inline;float:right;\">"+data.vios[i].wfsj+"</div></div>"+
			"<div class=\"wzinfodidian\">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点："+data.vios[i].wfdz+"</div>"+
			"<div class=\"wzinfoyy\">违章行为："+data.vios[i].wfxw+"</div>"+
			"</div>";
			$(".wzinfocontainer").append(html);
		}
		
	}
$(function(){
	setNav(3);
	$("#vc").focus(function(){
		$(".bomnav").hide();
	});
	$("#vc").blur(function(){
		$(".bomnav").show();
	});
	window.onkeydown= function(e){
	  e = e || window.event;
	  if(e.keyCode == 13){
			 var triggerElement = document.activeElement;
			 var inputId = triggerElement.id;
			 if('queryCondition' == inputId)	 
				 $('#btn_query')[0].click();
			 //$(triggerElement).next('input').focus();
	  }
	}
	//$('#result_close1').click(function(){$('#select_result1').hide();});
});
changeMenuTab(2);
$(function(){
	//判断小薇费率
	var micStatus="${sessionScope.MICROTE_SESSION}";
	if(micStatus&&micStatus==1){
		$("#rote").show();
	}
	$('#btn_query').on('click',function(){
		//$('tr.bb1e4').remove();
		$("#bdcx").empty();
		$('#currPage').val(1);
		dataPage();
	});
	$('.wrapper-load-more').on('click',function(){
		$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
		dataPage();
	});

	dataPage();
});

function dataPage(){
	$(".wrapper-load-more").html("<img src=\"${ctx }images/status_loading.gif\" style=\"vertical-align: middle;\" />&nbsp;加载中..");
	
	var currPage = $('#currPage').val();
	$.post('${ctx}/queryList3ByItem.do',{'queryCondition':$('#queryCondition').val(), 'currPage':parseInt(currPage)},function(data){
		$(".wrapper-load-more").html("加载更多");
		
		if(data.success && data.data.recordCount > 0){
			var page = data.data;
			if(page.pageCount == 1 || page.currentPage == page.pageCount){
				$('.wrapper-load-more').hide();
			}else{
				$('.wrapper-load-more').show();
			}
			var item;
			for(var i = 0;i<page.dataList.length;i++){
				
				item = page.dataList[i];
				var cxstr = "";
				if(item.plateNo.indexOf("*") < 0){
					cxstr = "<div class=\"btn btn1\" data='"+item.orderId+"' onclick=\"getValidateCodeInfo('"+item.orderId+"')\">查询违章</div>";
				}
				
				var str = "<tr class=\"bg\"><td>"+item.plateNo +"</td><td class=\"c2\">￥"+item.totalMonStr+"</td><td>"+item.ownerName+"</td>"+
					"<td>"+cxstr+"</td></tr>";
				
				$("#bdcx").append(str);
			}
		}else{
			$('.wrapper-load-more').hide();
			showMsg("查无记录...");
		}
	},'json');
}

</script>
</body>
</html>