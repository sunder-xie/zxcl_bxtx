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
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">我的消息</p></div>
	<div class="content wdmsg"><!-- 
		<div class="arr_r clear" id="hassee">
			<img class="lt img" src="${ctx}v3/images/kai.png" />
			<div class="lt">投保成功提醒</div>
			<div class="rt">2016.05.19 15:27</div>
		</div>
		<div class="con">恭喜你，保单生成成功(中国平安财产保险股份有限公司)保单： 12696023900010954300(商业) 12696023900010954301(交强)</div>
		 -->
	</div>
	<div class="wrapper-load-more more txc " data-stage="" style="margin-bottom:10px;">加载更多</div>
	<input type="hidden" id="currPage" value="1"/>
	<jsp:include page="../commons/menu.jsp"/>
	<script type="text/javascript">
	$(function(){
		dataPage();
	});
	$('.wrapper-load-more').on('click',function(){
		$('#currPage').val(parseInt(parseInt($('#currPage').val())+1));
		dataPage();
	});
	var dataPage = function(){
		$.post("${ctx}message/list.do",{'currentPage':parseInt($('#currPage').val()),'pageSize':30},function(data){
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
				for (var i = 0; i < list.length; i++) {
					item = list[i];
					if("2" == item.status){
						html += '<div class="arr_r clear"  data-status="'+item.status+'" data-id="'+item.messageId+'"><img class="lt img" src="${ctx}v3/images/kai.png" />'+
							'<div class="lt">'+item.messageTargetDTO.messageTheme+'</div><div class="rt">'+new Date(item.crtTm).format('yyyy.MM.dd hh:mm')+'</div></div>';
					}else{
						html += '<div class="arr_r clear" data-status="'+item.status+'" data-id="'+item.messageId+'" id="hassee"><img class="lt img" src="${ctx}v3/images/kai.png" />'+
							'<div class="lt">'+item.messageTargetDTO.messageTheme+'</div><div class="rt">'+new Date(item.crtTm).format('yyyy.MM.dd hh:mm')+'</div></div>';
					}
					
					html += '<div class="con">'+item.messageTargetDTO.messageBody+'</div>';
					
				}
				$('.content').append(html);
				
				
				$('.arr_r').click(function(){
					var cobj=$(this).find('.img');
					$(this).attr('id','hassee');
					if(cobj.attr('id')){
						cobj.removeAttr('id').attr('src','${ctx}v3/images/kai.png');
						$(this).next('.con').hide();
					}else{
						$('.img').removeAttr('id').attr('src','${ctx}v3/images/kai.png');
						$('.con').hide();
						cobj.attr('id','img').attr('src','${ctx}v3/images/guan.png');
						$(this).next('.con').show();
						
						
						var messageId = $(this).attr('data-id');
						if(null == messageId || '' == messageId || 'undefined' == messageId || 'null' == messageId){
							return false;
						}
						var status = $(this).attr('data-status');
						var $this = $(this);
						if('2' == status){
							$.post("${ctx}message/readed.do",{'messageId':messageId},function(data){
								if(data.succ){
									//$($this).removeClass('color-black').addClass('color-gray');
									$($this).attr("id","hassee");
									$($this).attr('data-status','3');
								}
							},'json');
						}
					}
				});
			}else{
				showMsg(data.msg);
				$('.wrapper-load-more').hide();
			}
		},'json');
	}
  	
	</script>
</body>
</html>