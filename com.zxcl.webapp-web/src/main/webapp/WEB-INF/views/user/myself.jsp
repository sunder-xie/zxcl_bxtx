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
<style type="text/css">
#tip_smrz{
    margin-left: 10px;
    color: #6ed04f;
}
</style>
</head>
<body>
	<div class="head lrhd userhd">
		<div class="back lt" onclick="history.back();"></div>
		<c:if test="${INVITE != '1' }">
			<div class="rt txr" onclick="location.href='${ctx }invite.do'"><img src="${ctx }v3/images/user02.png" />邀请</div>
		</c:if>
		<p class="title">个人中心</p>
	</div>
	<div class="content user">
		<div class="part1">
			<div class="line1 arr_r clear" onclick="location.href='${ctx }personal.do'">
				<div class="lt bgc"></div>
				<div class="txt tx1 text1">${sessionScope.teamName }</div>
				<div class="txt tx2 text1">${microDTO.micro_name }</div>
			</div>
			<div class="line2 txc clear">
				<div class="lt boxSet" onclick="location.href='${ctx }wallet/index.do'">
					<div class="row1" id="wallteamount"><fmt:formatNumber pattern="0.00" value="${wallet.amount }" />元</div>
					<div class="row2"><img src="${ctx }v3/images/zh.png" style="width:14px;" />我的钱包</div>
				</div>
				<div class="lt boxSet">
					<!-- 
					<div class="row1">0</div>
					<div class="row2"><img src="${ctx }v3/images/bi.png" style="width:11px;" />保币</div> -->
					<div class="row1"></div>
					<div class="row2"></div>
				</div>
				<div class="lt boxSet end" onclick="location.href='${ctx }message/index.do'">
					<div class="row1">${unreadMessageCount }<c:if test="${unreadMessageCount > 0}"><div class="new"></div></c:if></div>
					<div class="row2"><img src="${ctx }v3/images/xx.png" style="width:13px;" />消息</div>
				</div>
			</div>
		</div>
		<!-- 
		<div class="part2 arr_r" onclick="location.href='wddd.html'"><img src="${ctx }v3/images/user01.png" />我的订单</div> -->
		<c:if test="${INVITE != '1' }">
		<div class="part2 arr_r" onclick="location.href='${ctx}invite.do'"><img src="${ctx }v3/images/user02.png" />邀请好友</div>
		</c:if>
		<c:if test="${microDTO.duty eq '0'}">
			<div class="part2 arr_r" onclick="location.href='${ctx}myTeam.do'"><img src="${ctx }v3/images/user07.png" />我的团队</div>
		</c:if>
		<div class="part2 arr_r" onclick="to_smrz()"><img src="${ctx }v3/images/user08.png" />实名认证<span id="tip_smrz" data-t="0"></span></div>
		<div class="part2 arr_r mb" onclick="location.href='${ctx}manageAddress.do'"><img src="${ctx }v3/images/user03.png" />配送地址</div>
		<c:if test="${agentServiceControlDTO != null && agentServiceControlDTO.isOn == '1' }">
			<div class="part2 arr_r" onclick="location.href='${ctx}violationquery.do'"><img src="${ctx }v3/images/wzcx2.png" />车辆违章查询</div>
		</c:if>
		<div class="part2 arr_r" onclick="location.href='${ctx}lpbadh.do'"><img src="${ctx }v3/images/tell.png" />理赔报案电话</div>
		<div class="part2 arr_r" onclick="location.href='${ctx}set.do'"><img src="${ctx }v3/images/user06.png" />设置</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<jsp:include page="../commons/myalert2.jsp" />
	<script type="text/javascript">
	    $.post("${ctx}identity/find_confirm.do",{},function(data){
			if(data.succ){
				var identity = data.data;
				if(null != identity){
					var stage = identity.approveState;
					if("1" == stage || 1 == stage){
						$('#tip_smrz').attr('data-t', '1');
						$('#tip_smrz').text('正在认证中');
					}
					else if("2" == stage || 2 == stage){
						$('#tip_smrz').attr('data-t', '1');
						$('#tip_smrz').text("已通过认证");
					}
					else if("3" == stage || 3 == stage){
						$('#tip_smrz').attr('data-t', '2');
						$('#tip_smrz').text("未通过认证["+identity.approveReason+"],请重新认证");
					}
				}
			}
			else{
				alert(data.msg);
			}
		},'json');
		function to_smrz(){
			var datamsg = $('#tip_smrz').text();
			var stage = $('#tip_smrz').attr('data-t');
			if("1" == stage || 1 == stage){
				alert(datamsg);
			}
			else{
				window.location.href='${ctx}identity/index.do';
			}
		}
	</script>
</body>
</html>