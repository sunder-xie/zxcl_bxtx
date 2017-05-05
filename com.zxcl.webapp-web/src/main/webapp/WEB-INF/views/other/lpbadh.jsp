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
	<title>理赔报案电话</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<script type="text/javascript">
		$(function(){
			$('.arr_r .rt').click(function(){location.href='tel:'+Trim($(this).text(),"g");});
		});
		 function Trim(str,is_global)
	        {
	            var result;
	            result = str.replace(/(^\s+)|(\s+$)/g,"");
	            if(is_global.toLowerCase()=="g")
	            {
	                result = result.replace(/\s/g,"");
	             }
	            return result;
	}
	</script>
	<style>
		.clear{
			clear: both;
		}
	</style>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">理赔报案电话</p></div>
	<div class="kfzx content">
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/picc.png" /></div>
			<div class="rt" style="line-height: 50px;">95518</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="60%" src="${ctx }v3/images/lpbadh/paic.png" /></div>
			<div class="rt" style="line-height: 50px;">95511</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/tpy.png" /></div>
			<div class="rt" style="line-height: 50px;">95500</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/zh.jpg" /></div>
			<div class="rt" style="line-height: 50px;">95585</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/yg.png" /></div>
			<div class="rt" style="line-height: 50px;">95510</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" height="50%" src="${ctx }v3/images/lpbadh/ab.png" /></div>
			<div class="rt" style="line-height: 50px;">95569</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/tp.png" /></div>
			<div class="rt" style="line-height: 50px;">95589</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ya.png" /></div>
			<div class="rt" style="line-height: 50px;">95502</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ac.png" /></div>
			<div class="rt" style="line-height: 50px;">400050000</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/db.png" /></div>
			<div class="rt" style="line-height: 50px;">95586</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/astp.png" /></div>
			<div class="rt" style="line-height: 50px;">95550</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/zj.png" /></div>
			<div class="rt" style="line-height: 50px;">4008280018</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ta.png" /></div>
			<div class="rt" style="line-height: 50px;">95505</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ht.png" /></div>
			<div class="rt" style="line-height: 50px;">4006095509</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ha.png" /></div>
			<div class="rt" style="line-height: 50px;">95556</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/yt.png" /></div>
			<div class="rt" style="line-height: 50px;">95506</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/yc.png" /></div>
			<div class="rt" style="line-height: 50px;">95552</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/gs.png" /></div>
			<div class="rt" style="line-height: 50px;">4008695519</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:60%;"><img width="75%" src="${ctx }v3/images/lpbadh/zy.png" /></div>
			<div class="rt" style="line-height: 50px;">4006995566</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/bh.png" /></div>
			<div class="rt" style="line-height: 50px;">4006116666</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:60%;"><img width="80%" src="${ctx }v3/images/lpbadh/dd.png" /></div>
			<div class="rt" style="line-height: 50px;">95590</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/zs.png" /></div>
			<div class="rt" style="line-height: 50px;">4008666777</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ca.png" /></div>
			<div class="rt" style="line-height: 50px;">95592</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/xd.png" /></div>
			<div class="rt" style="line-height: 50px;">4008667788</div>
		</div>
		<div class="arr_r clear" style="height:50px;"> 
			<div class="lt" style="width:50%;"><img style="padding-top:3%;padding-left:2%;" width="100%" src="${ctx }v3/images/lpbadh/yd.png" /></div>
			<div class="rt" style="line-height: 50px;">4000188688</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/fd.png" /></div>
			<div class="rt" style="line-height: 50px;">4006695535</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/zm.png" /></div>
			<div class="rt" style="line-height: 50px;">4006536888</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/ts.png" /></div>
			<div class="rt" style="line-height: 50px;">4006077777</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/fb.png" /></div>
			<div class="rt" style="line-height: 50px;">4008817518</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/dh.png" /></div>
			<div class="rt" style="line-height: 50px;">4008888136</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/sx.png" /></div>
			<div class="rt" style="line-height: 50px;">4009333000</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:50%;"><img width="80%" src="${ctx }v3/images/lpbadh/hn.png" /></div>
			<div class="rt" style="line-height: 50px;">4000100000</div>
		</div>
		<div class="arr_r clear" style="height:50px;">
			<div class="lt" style="width:55%;"><img width="80%" src="${ctx }v3/images/lpbadh/jt.png" /></div>
			<div class="rt" style="line-height: 50px;">4008666555</div>
		</div>
	</div>
</body>
</html>