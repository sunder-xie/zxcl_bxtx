<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>

<html>
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">


        <script type="text/javascript">
            /* window.logs = {
                pagetime: {}
            };
            window.logs.pagetime['html_begin'] = (+new Date()); */
        </script>
        

<script type="text/javascript"> 

   /*  var page_begintime = (+new Date());

    var biz = "MzI0MjExOTczNA=="||"";
    var sn = "a93361035e611e378f45b89461f2c5e6" || ""|| "";
    var mid = "404122379" || ""|| "";
    var idx = "1" || "" || "";

    
    var is_rumor = ""*1;
    var norumor = ""*1;
    if (!!is_rumor&&!norumor){
      if (!document.referrer || document.referrer.indexOf("mp.weixin.qq.com/mp/rumor") == -1){
        location.href = "http://mp.weixin.qq.com/mp/rumor?action=info&__biz=" + biz + "&mid=" + mid + "&idx=" + idx + "&sn=" + sn + "#wechat_redirect";
      }
    }
 */
    
    
</script>

<!--         <link rel="dns-prefetch" href="//res.wx.qq.com">
<link rel="dns-prefetch" href="//mmbiz.qpic.cn"> -->
<!-- <link rel="shortcut icon" type="image/x-icon" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/images/icon/common/favicon22c41b.ico"> -->
<script type="text/javascript">
    /* String.prototype.html = function(encode) {
        var replace =["&#39;", "'", "&quot;", '"', "&nbsp;", " ", "&gt;", ">", "&lt;", "<", "&amp;", "&", "&yen;", "¥"];
        if (encode) {
            replace.reverse();
        }
        for (var i=0,str=this;i< replace.length;i+= 2) {
             str=str.replace(new RegExp(replace[i],'g'),replace[i+1]);
        }
        return str;
    };

    window.isInWeixinApp = function() {
        return /MicroMessenger/.test(navigator.userAgent);
    };

    window.getQueryFromURL = function(url) {
        url = url || 'http://qq.com/s?a=b#rd'; 
        var query = url.split('?')[1].split('#')[0].split('&'),
            params = {};
        for (var i=0; i<query.length; i++) {
            var arg = query[i].split('=');
            params[arg[0]] = arg[1];
        }
        if (params['pass_ticket']) {
        	params['pass_ticket'] = encodeURIComponent(params['pass_ticket'].html(false).html(false).replace(/\s/g,"+"));
        }
        return params;
    };

    (function() {
	    var params = getQueryFromURL(location.href);
        window.uin = params['uin'] || '';
        window.key = params['key'] || '';
        window.wxtoken = params['wxtoken'] || '';
        window.pass_ticket = params['pass_ticket'] || '';
    })(); */

</script>

 <link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/style.css"/>
<style type="text/css">
h2 {
    color: #DB4F4F;
}
.header{ width: 100%; position:fixed;top:0;left:0;z-index:99; overflow: hidden; background: #DB4F4F}
.header .title{ width: 96%; margin: 0 auto; overflow: hidden; text-align: center; font-size: 20px; line-height: 45px; color: #FFF; font-weight: bold; }
.header .title .iconLogo{ float: left; width: 13%}
.header .title .iconTel{ float: right; width: 13%}
.header_for{height:45px;}
</style>
<link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/page_mp_article_improve2c9cd6.css">
<style>
     
    </style>
<!--[if lt IE 9]>
<link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/page_mp_article_improve_pc2c9cd6.css">
<![endif]-->
<script type="text/javascript">
   /*  document.domain = "qq.com"; */
</script>

<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
    </head>
    <body id="activity-detail" class="zh_CN mm_appmsg" ontouchstart="">
    <!--主体内容 start-->
	<header class="header">
		<div class="title">
			<a class="iconLogo"><img src="${ctx}images/iconLogo.png"></a>
			关于我们
			<a class="iconTel" href="tel:${sessionScope.tel }"><img src="${ctx}images/iconTel.png"></a>
		</div>
	</header>
    <div id="js_article" class="rich_media">
        
        <div id="js_top_ad_area" class="top_banner">
 
        </div>
                

        <div class="rich_media_inner">
            <div id="page-content">
                <!-- <div id="img-content" class="rich_media_area_primary">
                        <div id="js_profile_qrcode" class="profile_container" style="display:none;">
                            <div class="profile_inner">
                                <strong class="profile_nickname">保行天下</strong>
                                <img class="profile_avatar" id="js_profile_qrcode_img" src="" alt="">

                                <p class="profile_meta">
                                <label class="profile_meta_label">微信号</label>
                                <span class="profile_meta_value"></span>
                                </p>

                                <p class="profile_meta">
                                <label class="profile_meta_label">功能介绍</label>
                                <span class="profile_meta_value">行内最受欢迎的小微保险经纪服务平台</span>
                                </p>
                                
                            </div>
                            <span class="profile_arrow_wrp" id="js_profile_arrow_wrp">
                                <i class="profile_arrow arrow_out"></i>
                                <i class="profile_arrow arrow_in"></i>
                            </span>
                        </div>
                    </div> -->
                    
                    
                    
                    
                                                            
                                                            
                    
                    <div class="rich_media_content chunk mb5" style="margin-top: 12%;" id="js_content">
                        
		<p><strong><span style="font-size:19px"><img data-type="gif" data-src="http://mmbiz.qpic.cn/mmbiz/EGqAX63sbwkT4s6Jg3COc40HQmhchWjkIqUcbeSJTECqnmibsSL7ibuFwuatEIcWGRtZWJRuwbW53qibHqevcNX3w/0?wx_fmt=gif" style="color: rgb(62, 62, 62); line-height: 25.6px; white-space: normal; width: 100%; height: auto; background-color: rgb(255, 255, 255);" data-ratio="0.1906474820143885" data-w=""  /></span></strong></p><section class="Powered-by-XIUMI V5" style="max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); white-space: normal; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important; background-color: rgb(255, 255, 255);"><section class="Powered-by-XIUMI V5" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section><section class="Powered-by-XIUMI V5" style="max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); line-height: 25.6px; white-space: normal; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important; background-color: rgb(255, 255, 255);"><section class="Powered-by-XIUMI V5" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="margin-top: 0.5em; margin-bottom: 0.5em; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="padding-top: 3px; max-width: 100%; box-sizing: border-box; border-top-width: 2px; border-top-style: solid; border-color: rgb(249, 110, 87); word-wrap: break-word !important;"><section class="" style="padding-right: 0.5em; padding-left: 0.5em; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; height: 2em; line-height: 2em; color: rgb(255, 255, 255); word-wrap: break-word !important; background-color: rgb(249, 110, 87);"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">1、公司简介</section></section><section style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; vertical-align: top; border-left-width: 0.8em; border-left-style: solid; border-left-color: rgb(249, 110, 87); border-top-width: 1em; border-top-style: solid; border-top-color: rgb(249, 110, 87); word-wrap: break-word !important; border-right-width: 0.8em !important; border-right-style: solid !important; border-right-color: transparent !important; border-bottom-width: 1em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></section></section></section></section></section></section></section></section></section><p style="line-height: 25.6px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><span style="line-height: 25.6px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“保行天下”</span><span style="line-height: 25.6px;">--</span><span style="line-height: 25.6px;">小微汽车保险交易服务平台由四川智迅车联科技股份有限公司开发。</span></p><p style="line-height: 25.6px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><span style="line-height: 25.6px;"><span style="line-height: 25.6px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“保行天下”初步规划分两步实现：</span></span></p><section><section class="" style="max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; word-wrap: break-word !important;"><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; opacity: 0.6; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span></section><span style="line-height: 1.2;">第一步：</span><span style="line-height: 1.2;">致力成为行内最受欢迎的小微保险经纪服务平台，服务于各大保险公司、保</span>险经纪、保险代理公司、汽修店、洗车店等，实现实时精准报价，网上结算，方便机构及业务员移动展业。</section><section class="" style="max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; word-wrap: break-word !important;"><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; opacity: 0.6; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span></section><span style="line-height: 1.2;">第二步：完备网上理赔等服务，合作推出保险定制服务等其他功能，成为服</span>务大众的好汽车金融平台。经过团队通力协作，已实现多家保险公司的正式上线生产，现阶段平安、太平洋、华泰、阳光、中华联合、永诚、富德等保险公司正在陆续接入过程中。四川嘉诚保险、德圣经纪、壹诚经纪、蓝色经典汽修等也已通过“保行天下”成功实现在线展业、销售，海南及河北等地保险代理机构也先后签约。<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;协助小微为提供客户独有的私享的个性化服务，并将最终实现网上轻松快捷购买到理赔等优质服务，给个人及企业用户提供便利专业的保险交易服务平台是我们的唯一宗旨。营造一个让平台上所有参与者同生共长共赢的生态圈是我们不变的目标。</p></section></section></section><section class="Powered-by-XIUMI V5" style="line-height: 25.6px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="margin-top: 0.5em; margin-bottom: 0.5em; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="padding-top: 3px; max-width: 100%; box-sizing: border-box; border-top-width: 2px; border-top-style: solid; border-color: rgb(249, 110, 87); word-wrap: break-word !important;"><section class="" style="padding-right: 0.5em; padding-left: 0.5em; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; height: 2em; line-height: 2em; color: rgb(255, 255, 255); word-wrap: break-word !important; background-color: rgb(249, 110, 87);"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">2、用保行天下有啥好处？</section></section><section style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; vertical-align: top; border-left-width: 0.8em; border-left-style: solid; border-left-color: rgb(249, 110, 87); border-top-width: 1em; border-top-style: solid; border-top-color: rgb(249, 110, 87); word-wrap: break-word !important; border-right-width: 0.8em !important; border-right-style: solid !important; border-right-color: transparent !important; border-bottom-width: 1em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></section></section></section></section></section></section></section></section></section><section class="Powered-by-XIUMI V5" style="line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); word-wrap: break-word !important;"><section class="" style="margin-top: 10px; margin-bottom: 10px; max-width: 100%; box-sizing: border-box; line-height: 1; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; word-wrap: break-word !important;"><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; opacity: 0.6; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span></section><section class="" style="padding-left: 3px; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; line-height: 1.2; word-wrap: break-word !important;"><p style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">安全保障：</p><p style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">保费支付进入对应保险公司账户；</p></section></section></section><section class="Powered-by-XIUMI V5" style="line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); word-wrap: break-word !important;"><section class="" style="margin-top: 10px; margin-bottom: 10px; max-width: 100%; box-sizing: border-box; line-height: 1; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; word-wrap: break-word !important;"><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; opacity: 0.6; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span></section><section class="" style="padding-left: 3px; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; line-height: 1.2; word-wrap: break-word !important;"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">随时随地：全天候7*24小时；</section></section></section></section><section class="Powered-by-XIUMI V5" style="line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); word-wrap: break-word !important;"><section class="" style="margin-top: 10px; margin-bottom: 10px; max-width: 100%; box-sizing: border-box; line-height: 1; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; word-wrap: break-word !important;"><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; opacity: 0.6; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span></section><section class="" style="padding-left: 3px; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; line-height: 1.2; word-wrap: break-word !important;"><p style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">方便快捷：精准报价；</p><p style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">自动核保；网上支付；</p></section></section></section><section class="Powered-by-XIUMI V5" style="line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); word-wrap: break-word !important;"><section class="" style="margin-top: 10px; margin-bottom: 10px; max-width: 100%; box-sizing: border-box; line-height: 1; word-wrap: break-word !important;"><section class="" style="max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; word-wrap: break-word !important;"><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; opacity: 0.6; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span><span style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; border-left-width: 0.6em; border-left-style: solid; border-left-color: rgb(249, 110, 87); word-wrap: break-word !important; border-top-width: 0.5em !important; border-top-style: solid !important; border-top-color: transparent !important; border-bottom-width: 0.5em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></span></section><section class="" style="padding-left: 3px; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; line-height: 1.2; word-wrap: break-word !important;"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">理赔便利：专门公估专家理赔指导。</section></section></section></section><section class="Powered-by-XIUMI V5" style="color: rgb(62, 62, 62); line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="margin-top: 0.5em; margin-bottom: 0.5em; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="padding-top: 3px; max-width: 100%; box-sizing: border-box; border-top-width: 2px; border-top-style: solid; border-color: rgb(249, 110, 87); word-wrap: break-word !important;"><section class="" style="padding-right: 0.5em; padding-left: 0.5em; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; height: 2em; line-height: 2em; color: rgb(255, 255, 255); word-wrap: break-word !important; background-color: rgb(249, 110, 87);"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">3、团队介绍</section></section><section style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; vertical-align: top; border-left-width: 0.8em; border-left-style: solid; border-left-color: rgb(249, 110, 87); border-top-width: 1em; border-top-style: solid; border-top-color: rgb(249, 110, 87); word-wrap: break-word !important; border-right-width: 0.8em !important; border-right-style: solid !important; border-right-color: transparent !important; border-bottom-width: 1em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></section></section></section></section><p style="line-height: 1.5em;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“保行天下”团队是一支即懂保险又熟悉互联网的团队。由资深的互联网行业及金融行业人士组成的核心团队，来自于北大、中大等国内院校专家，BAT等互联网从业人员，在保险机构从业三十年以上的领袖人才、有平安财险等保险公司的精英；海归精算师；平安系统开发人员，理赔专家等组成。</p><section class="Powered-by-XIUMI V5" style="color: rgb(62, 62, 62); line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="margin-top: 0.5em; margin-bottom: 0.5em; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="padding-top: 3px; max-width: 100%; box-sizing: border-box; border-top-width: 2px; border-top-style: solid; border-color: rgb(249, 110, 87); word-wrap: break-word !important;"><section class="" style="padding-right: 0.5em; padding-left: 0.5em; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; height: 2em; line-height: 2em; color: rgb(255, 255, 255); word-wrap: break-word !important; background-color: rgb(249, 110, 87);"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">4、历程</section></section><section style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; vertical-align: top; border-left-width: 0.8em; border-left-style: solid; border-left-color: rgb(249, 110, 87); border-top-width: 1em; border-top-style: solid; border-top-color: rgb(249, 110, 87); word-wrap: break-word !important; border-right-width: 0.8em !important; border-right-style: solid !important; border-right-color: transparent !important; border-bottom-width: 1em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></section></section></section></section><p style="line-height: 25.6px; white-space: normal;"><img data-s="300,640" data-type="png" src="${ctx}images/about/1.png" data-ratio="0.46223021582733814" data-w="" style="line-height: 25.6px; white-space: normal; width: 100%; height: auto;"  /></p><section class="Powered-by-XIUMI V5" style="color: rgb(62, 62, 62); line-height: 25.6px; white-space: normal; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="margin-top: 0.5em; margin-bottom: 0.5em; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"><section class="" style="padding-top: 3px; max-width: 100%; box-sizing: border-box; border-top-width: 2px; border-top-style: solid; border-color: rgb(249, 110, 87); word-wrap: break-word !important;"><section class="" style="padding-right: 0.5em; padding-left: 0.5em; max-width: 100%; box-sizing: border-box; display: inline-block; vertical-align: top; height: 2em; line-height: 2em; color: rgb(255, 255, 255); word-wrap: break-word !important; background-color: rgb(249, 110, 87);"><section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">5、对接中</section></section><section style="max-width: 100%; box-sizing: border-box; width: 0px; display: inline-block; vertical-align: top; border-left-width: 0.8em; border-left-style: solid; border-left-color: rgb(249, 110, 87); border-top-width: 1em; border-top-style: solid; border-top-color: rgb(249, 110, 87); word-wrap: break-word !important; border-right-width: 0.8em !important; border-right-style: solid !important; border-right-color: transparent !important; border-bottom-width: 1em !important; border-bottom-style: solid !important; border-bottom-color: transparent !important;"></section></section></section></section><p><strong style="line-height: 25.6px; white-space: normal;"><span style="font-size: 19px; font-family: 宋体;"><img data-s="300,640" data-type="png" src="${ctx}images/about/2.png" data-ratio="0.2697841726618705" data-w="" style="width: 100%; height: auto;"  /></span></strong></p>

                 </div>
                   <!--  <script type="text/javascript">
                        var first_sceen__time = (+new Date());

                        if ("" == 1 && document.getElementById('js_content'))
                            document.getElementById('js_content').addEventListener("selectstart",function(e){ e.preventDefault(); });

                                        (function(){
                            if (navigator.userAgent.indexOf("WindowsWechat") != -1){
                                var link = document.createElement('link');
                                var head = document.getElementsByTagName('head')[0];
                                link.rel = 'stylesheet';
                                link.type = 'text/css';
                                link.href = "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/page_mp_article_improve_winwx2c9cd6.css";
                                head.appendChild(link);
                            }
                        })();
                    </script> -->
                    <link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/page_mp_article_improve_combo2c9cd6.css">
                    
                    
                                        
                                        
                                       <!--  <div class="rich_media_tool" id="js_toobar3">
                                                                    <div id="js_read_area3" class="media_tool_meta tips_global meta_primary" style="display:none;">阅读 <span id="readNum3"></span></div>

                        <span style="display:none;" class="media_tool_meta meta_primary tips_global meta_praise" id="like3">
                            <i class="icon_praise_gray"></i><span class="praise_num" id="likeNum3"></span>
                        </span>

                        <a id="js_report_article3" style="display:none;" class="media_tool_meta tips_global meta_extra" href="javascript:void(0);">投诉</a>

                    </div> -->



                                    </div>

               <!--  <div class="rich_media_area_primary sougou" id="sg_tj" style="display:none">

                </div> -->

                <!-- <div class="rich_media_area_extra">

                    
                                        <div class="mpda_bottom_container" id="js_bottom_ad_area">
                        
                    </div>
                                        
                    <div id="js_iframetest" style="display:none;"></div>
                                        
                                    </div> -->
               
            </div>
            <!-- <div id="js_pc_qr_code" class="qr_code_pc_outer" style="display:none;">
                <div class="qr_code_pc_inner">
                    <div class="qr_code_pc">
                        <img id="js_pc_qr_code_img" class="qr_code_pc_img">
                        <p>微信扫一扫<br>关注该公众号</p>
                    </div>
                </div>
            </div> -->

        </div>
<br/><br/><br/>
<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>
        <script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/prettify.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
        <script>
    /* var __DEBUGINFO = {
        debug_js : "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/debug/console2ca724.js",
        safe_js : "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/safe/moonsafe2c5484.js",
        res_list: []
    }; */
</script>
        <script>window.moon_map = {"appmsg/emotion/caret.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/caret278965.js","biz_wap/jsapi/cardticket.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/jsapi/cardticket275627.js","appmsg/emotion/map.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/map278965.js","appmsg/emotion/textarea.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/textarea27cdc5.js","appmsg/emotion/nav.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/nav278965.js","appmsg/emotion/common.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/common278965.js","appmsg/emotion/slide.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/slide2a9cd9.js","pages/report.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/pages/report2c9cd6.js","pages/music_player.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/pages/music_player2b674b.js","pages/loadscript.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/pages/loadscript2c9cd6.js","appmsg/emotion/dom.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/dom278965.js","biz_wap/utils/hashrouter.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/utils/hashrouter2805ea.js","a/gotoappdetail.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/a/gotoappdetail2a2c13.js","a/ios.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/a/ios275627.js","a/android.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/a/android2c5484.js","a/profile.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/a/profile29b1f8.js","a/card.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/a/card2c5484.js","biz_wap/utils/position.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/utils/position29b1f8.js","appmsg/a_report.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/a_report2b6c15.js","biz_common/utils/respTypes.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/respTypes2c57d0.js","appmsg/cmt_tpl.html.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/cmt_tpl.html2a2c13.js","sougou/a_tpl.html.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/sougou/a_tpl.html2c6e7c.js","appmsg/emotion/emotion.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/emotion/emotion2a9cd9.js","biz_common/utils/report.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/report275627.js","biz_common/utils/huatuo.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/huatuo293afc.js","biz_common/utils/cookie.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/cookie275627.js","pages/voice_component.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/pages/voice_component2c5484.js","new_video/ctl.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/new_video/ctl292ed8.js","biz_common/utils/monitor.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/monitor2a30ee.js","biz_common/utils/spin.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/spin275627.js","biz_wap/jsapi/pay.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/jsapi/pay275627.js","appmsg/reward_entry.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/reward_entry2b1291.js","appmsg/comment.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/comment2c5484.js","appmsg/like.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/like2b5583.js","appmsg/a.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/a2b6c8f.js","pages/version4video.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/pages/version4video2c7543.js","rt/appmsg/getappmsgext.rt.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/rt/appmsg/getappmsgext.rt2c21f6.js","biz_wap/utils/storage.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/utils/storage2a74ac.js","biz_common/tmpl.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/tmpl2b3578.js","appmsg/img_copyright_tpl.html.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/img_copyright_tpl.html2a2c13.js","appmsg/a_tpl.html.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/a_tpl.html2c7543.js","biz_common/ui/imgonepx.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/ui/imgonepx275627.js","biz_common/dom/attr.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/dom/attr275627.js","biz_wap/utils/ajax.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/utils/ajax2c7a90.js","biz_common/utils/string/html.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/string/html29f4e9.js","sougou/index.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/sougou/index2c7543.js","appmsg/report.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/report2cb9ae.js","biz_common/dom/class.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/dom/class275627.js","appmsg/report_and_source.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/report_and_source2c0ff9.js","appmsg/page_pos.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/page_pos2c7629.js","appmsg/cdn_speed_report.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/cdn_speed_report2c57d0.js","appmsg/voice.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/voice2ab8bd.js","appmsg/qqmusic.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/qqmusic2ab8bd.js","appmsg/iframe.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/iframe2c2c35.js","appmsg/review_image.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/review_image2a5394.js","appmsg/outer_link.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/outer_link275627.js","biz_wap/jsapi/core.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/jsapi/core2c30c1.js","biz_common/dom/event.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/dom/event275627.js","appmsg/copyright_report.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/copyright_report2c57d0.js","appmsg/cache.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/cache2a74ac.js","appmsg/pay_for_reading.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/pay_for_reading2c5484.js","appmsg/async.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/async2ca7fa.js","biz_wap/ui/lazyload_img.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/ui/lazyload_img2b18f6.js","biz_common/log/jserr.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/log/jserr2805ea.js","appmsg/share.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/share2b5583.js","biz_wap/utils/mmversion.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/utils/mmversion275627.js","appmsg/cdn_img_lib.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/cdn_img_lib275627.js","biz_common/utils/url/parse.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_common/utils/url/parse275627.js","biz_wap/utils/device.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/utils/device2b3aae.js","biz_wap/jsapi/a8key.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/jsapi/a8key2a30ee.js","appmsg/index.js":"http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/index2cbf80.js"};window.moon_crossorigin = true;</script><script type="text/javascript" src="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/biz_wap/moon2c7b0a.js" crossorigin></script>
    <script id="voice_tpl" type="text/html">        
        <span id="voice_main_<#=voiceid#>_<#=posIndex#>" class="db audio_area <#if(!musicSupport){#> unsupport<#}#>">
            <span class="tc tips_global unsupport_tips" <#if(show_not_support!==true){#>style="display:none;"<#}#>>
            当前浏览器不支持播放音乐或语音，请在微信或其他浏览器中播放            </span>
            <span class="audio_wrp db">
                <span id="voice_play_<#=voiceid#>_<#=posIndex#>" class="audio_play_area">
                    <i class="icon_audio_default"></i>
                    <i class="icon_audio_playing"></i>
                    <img src="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/images/icon/appmsg/audio/icon_audio_unread26f1f1.png" alt="" class="pic_audio_default">
                </span>
                <span class="audio_length tips_global"><#=duration_str#></span>
                <span class="db audio_info_area">
                    <strong class="db audio_title"><#=title#></strong>
                    <span class="audio_source tips_global"><#if(window.nickname){#>来自<#=window.nickname#><#}#></span>
                </span>
                <span id="voice_progress_<#=voiceid#>_<#=posIndex#>" class="progress_bar" style="width:0px;"></span>
            </span>
        </span>
    </script>

    <script id="qqmusic_tpl" type="text/html">        
        <span id="qqmusic_main_<#=comment_id#>_<#=posIndex#>" class="db qqmusic_area <#if(!musicSupport){#> unsupport<#}#>">
            <span class="tc tips_global unsupport_tips" <#if(show_not_support!==true){#>style="display:none;"<#}#>>
            当前浏览器不支持播放音乐或语音，请在微信或其他浏览器中播放            </span>
            <span class="db qqmusic_wrp">
                <span class="db qqmusic_bd">
                    <span id="qqmusic_play_<#=musicid#>_<#=posIndex#>" class="play_area">
                        <i class="icon_qqmusic_switch"></i>
                        <img src="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/images/icon/appmsg/qqmusic/icon_qqmusic_default.2x26f1f1.png" alt="" class="pic_qqmusic_default">
                        <img src="<#=music_img#>" data-autourl="<#=audiourl#>" data-musicid="<#=musicid#>" class="qqmusic_thumb" alt="">
                    </span>
                                        <a id="qqmusic_home_<#=musicid#>_<#=posIndex#>" href="javascript:void(0);" class="access_area">
                        <span class="qqmusic_songname"><#=music_name#></span>
                        <span class="qqmusic_singername"><#=singer#></span>
                        <span class="qqmusic_source"><img src="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/images/icon/appmsg/qqmusic/icon_qqmusic_source263724.png" alt=""></span>
                    </a>
                </span>
            </span>       
        </span>
    </script>
  <script type="text/javascript">
     /*  var not_in_mm_css = "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/not_in_mm2c9cd6.css";
      var windowwx_css = "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/page_mp_article_improve_winwx2c9cd6.css";
      var tid = "";
      var aid = "";
      var clientversion = "0";
      var appuin = "MzI0MjExOTczNA=="||"";

      var source = "";
      var scene = 75;
      
      var itemidx = "";

      var _copyright_stat = "0";
      var _ori_article_type = "";

      var nickname = "保行天下";
      var appmsg_type = "6";
      var ct = "1459149455";
      var publish_time = "2016-03-28" || "";
      var user_name = "gh_ad2d96d004d8";
      var user_name_new = "";
      var fakeid   = "";
      var version   = "";
      var is_limit_user   = "0";
      var msg_title = "保行天下，共同生长的生态圈！";
      var msg_desc = "（1）公司简介：“保行天下”--小微汽车保险交易服务平台由四川智迅车联科技股份有限公司开发。“保行天下”初步";
      var msg_cdn_url = "http://mmbiz.qpic.cn/mmbiz/EGqAX63sbwkT4s6Jg3COc40HQmhchWjkYoOGQwZrzsU778JJIo7HpO53bnZqyic6n946oKxZZ956iaJHNFcgTEMg/0?wx_fmt=jpeg";
      var msg_link = "http://mp.weixin.qq.com/s?__biz=MzI0MjExOTczNA==&amp;mid=404122379&amp;idx=1&amp;sn=a93361035e611e378f45b89461f2c5e6#rd";
      var user_uin = "0"*1;
      var msg_source_url = '';
      var img_format = 'jpeg';
      var srcid = '';
      var networkType;
      var appmsgid = '' || '404122379'|| "";
      var comment_id = "0" * 1;
      var comment_enabled = "" * 1;
      var is_need_reward = "0" * 1;
      var is_https_res = ("" * 1) && (location.protocol == "https:");

      var devicetype = "";
      var source_username = "";  
      var profile_ext_signature = "" || "";
      var reprint_ticket = "";
      var source_mid = "";
      var source_idx = "";

      var show_comment = "";
      var __appmsgCgiData = {
            can_use_page : "0"*1,
            is_wxg_stuff_uin : "0"*1,
            card_pos : "",
            copyright_stat : "0",
            source_biz : "",
            hd_head_img : "http://wx.qlogo.cn/mmhead/Q3auHgzwzM52Sqh8OVT93bviah8q97Qa11ObbyqkO7Ctbvl7OoRfVjw/0"||(window.location.protocol+"//"+window.location.host + "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/images/pic/appmsg/pic_rumor_link.2x264e76.jpg")
      };
      var _empty_v = "http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/images/pic/pages/voice/empty26f1f1.mp3";

      var copyright_stat = "0" * 1;

      var pay_fee = "" * 1;
      var pay_timestamp = "";
      var need_pay = "" * 1;

      var need_report_cost = "0" * 1;
      var use_tx_video_player = "0" * 1;
      
            window.wxtoken = "";
          if(!!window.__initCatch){
        window.__initCatch({
            idkey : 27613,
            startKey : 0,
            limit : 128,
            reportOpt : {
                uin : uin,
                biz : biz,
                mid : mid,
                idx : idx,
                sn  : sn
            },
            extInfo : {
                network_rate : 0.01    
            }
        });
    } */
  </script>
  <script type="text/javascript">
         /*  seajs.use('appmsg/index.js'); */
  </script>

    </body>
</html>

 
