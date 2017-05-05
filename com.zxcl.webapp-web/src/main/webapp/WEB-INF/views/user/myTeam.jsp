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
<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style type="text/css">
img{
	width:45px;
	height:45px;
}
table{
    width: 100%;
}
.img-span{
	float: left;
	padding-right: 10px;
	height: 45px;
}

.td-content{
}
.td2{
	float: right;
	margin: 5% 0 5% 0;
    padding-right: 3%;
}
.td1{
	float: left;
    margin: 5% 0 5% 0;
     padding-left: 3%;
}

table tr{
}
</style>
</head>
<body>
	<div class="head lrhd userhd">
		<div class="back lt" onclick="window.location.href='${ctx}myself.do';"></div>
		<p class="title" style="padding-right: 40px;">我的团队</p>
	</div>
	<div class="content user">
		<div class="part1">
			<table cellpadding="0" >
				<tr>
					<td class="td1">
						<div>
							<span class="img-span"><img src="${ctx}v3/images/team/bxtxok_02.png" alt=""/></span>
							<span class="td-content">团队总人数<div style="height: 5px;"> </div><span id="param1">1</span>人</span>
						</div>
					</td>
					<td class="td2">
						<div>
							<span class="img-span"><img src="${ctx}v3/images/team/bxtxok_11.png" alt=""/></span>
							<span class="td-content" style="display: inline-block;">总保费(万)<div style="height: 5px;"> </div><span id="param2">0.00</span></span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td1">
						<div>
							<span class="img-span"><img src="${ctx}v3/images/team/bxtxok_09.png" alt=""/></span>
							<span class="td-content">月保费(万)<div style="height: 5px;"> </div><span id="param3">0.00</span></span>
						</div>
					</td>
					<td class="td2">
						<div>
							<span class="img-span"><img src="${ctx}v3/images/team/bxtxok_13.png" alt=""/></span>
							<span class="td-content">日保费(万)<div style="height: 5px;"> </div><span id="param4">0.00</span></span>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="part2 arr_r" onclick="location.href='${ctx}myTeamList.do'">团员列表</div>
		<div class="part2 arr_r" onclick="location.href='${baseURL}/dashboard/team/feeReport.do?userId=${userId}'">保费趋势</div>
		<div class="part2 arr_r" onclick="location.href='${baseURL}/dashboard/team/salesmanRank.do?userId=${userId}'">业务员排名</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	
	<script type="text/javascript">
	$(function(){
		
		//团队成员总人数
		$.post("${ctx}myTeamAllCount.do", {}, function(data){
			if(data.succ){
				$('#param1').text(data.data);
			}
		}, 'json');
		
		//总保费
		$.post("${ctx}myTeamPolicyFee.do", {'type':1}, function(data){
			if(data.succ){
				$('#param2').text(data.data);
			}
		}, 'json');
		
		//月保费
		$.post("${ctx}myTeamPolicyFee.do", {'type':2}, function(data){
			if(data.succ){
				$('#param3').text(data.data);
			}
		}, 'json');
		
		//日保费
		$.post("${ctx}myTeamPolicyFee.do", {'type':3}, function(data){
			if(data.succ){
				$('#param4').text(data.data);
			}
		}, 'json');
		setNav(3);
	});
	</script>
</body>
</html>