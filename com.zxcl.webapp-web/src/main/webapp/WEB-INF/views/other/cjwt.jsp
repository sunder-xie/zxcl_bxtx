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
<script type="text/javascript" src="${ctx}v3/js/jquery.cookie.js"></script>
<script type="text/javascript">
	$(function(){
		$('.zbnav .lt').click(function(){
			if(!$(this).attr('id')){
				var ind=$(this).index(),w,m,l;
				if(ind==0){w='66px'; m='-35px'; l='25%';}else{w='94px'; m='-46px'; l='75%';}
				$('.zbnav .line').animate({width:w,'margin-left':m,left:l},600);
				$(this).attr('id','sel').siblings('.lt').removeAttr('id');
			}
		});
	});
</script>
<style>
.titlea{
	width:100%;
	font-size:18px;
	text-align:center;
	line-height:25px;
	margin-top:45px;
}
.dlnr{
	font-size:14px;
	font-weight: 700;
	margin-top:10px;
	line-height:18px;
	letter-spacing:2px;
	color:#0E0D0D;
}
.clear {
	clear:both;
}
.sjnr{
	font-size:12px;
	letter-spacing:1px;
	color:#333;
}
</style>
</head>
<body class="chaxun zfcx">
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">常见问题</p></div>
	<div class="zbnav txc">
		<div class="lt sle1" id="sel">常见问题</div>
		<div class="lt sle2">常识一览</div>
		<div class="line"></div>
	</div>
	<div style="margin-top: 100px;margin-left: 5px;">
		<div id="body1">
			<div class="dlnr clear">1. 为什么我要选择保行天下买车险？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp保行天下给您提供精确的车险报价，省却您挨家算保费的辛苦；
				保行天下给您提供保险专家建议，让您省钱、省心买保险；
				保行天下对您的保险购买实行全程担保，保单官网验真，购买安全。
			</div>
			<div class="dlnr clear">2. 保行天下购买流程？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp精确报价—补充订单内容—支付。
			</div>
			<div class="dlnr clear">3. 买保险怎么省钱？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp1、新车，价格优惠15%，还有丰厚的投保礼赠送。<br>
				&nbsp&nbsp&nbsp&nbsp2、上年未出险或出险1次，建议您选择原保险公司继续续保，因为出险次数少，仍然可享受保单最大幅度优惠，同时续保客户还会享受丰厚礼品赠送。<br>
				&nbsp&nbsp&nbsp&nbsp3、上年出险2次及以上的客户，一般来说如果你上年的理赔金额不超过你的保费，今年保费可能不会上浮，如果理赔金额过高，建议您选择别的保险公司承保，可通过报价网看看哪家保险公司的报价便宜，服务又能满足你的需求。<br> 
				&nbsp&nbsp&nbsp&nbsp<span style="color:red">专家提醒：减少理赔次数，合规驾驶，才是最有效的省钱法宝。</span>
			</div>
			<div class="dlnr clear">4. 点击精确报价为什么给我的车险报价的保险公司很少呢？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp老旧车型、稀有车型、车辆零整比过高、车龄过长、上年出险次数过多（超过3次）、重复投保等会导致报价公司偏少。也可能由于保险起期和现在相差太远引起，建议保险起期选3个月之内。未获取到更多的报价信息，您可以联系我们的客服人员，他们会根据你的要求及时帮您解决投保过程中遇到的问题。
			</div>
			<div class="dlnr clear">5. 车险报价完成后，是否可以直接购买？网上投保支付成功，后续还会有什么流程呢？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp保行天下与各保险公司已经完成支付接口的对接，<b>凡有报价均支持在线支付购买。</b>支付成功后，保险公司的客服人员跟您进行电话承保确认，同时确定您的保单配送信息及投保礼信息，并根据您的时间安排配送纸质保单、发票、投保礼等，请确保您留存的手机号码正确并。 
			</div>
		</div>
		<div id="body2">
			<div class="dlnr clear">1. 哪些车辆可以在网上投保?</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp目前接通承保9座(含)以下的家庭自用客车。
			</div>
			<div class="dlnr clear">2. 跨省投保可以么？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp大部分保险公司是允许跨省投保的，但需要您提供车辆长期在投保所在地使用的证明，如暂住证、居住证、单位证明、社保卡等。跨省投保对您的后续理赔并不会产生影响，保险公司目前均做到全国通赔。因各家保险公司的各地政策都不相同，您异地承保时可能会受到限制导致核保失败，具体请与客服联系咨询。
			</div>
			<div class="dlnr clear">3. 为什么要问我车辆是否过户？ </div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp是否为过户车为行业平台需要采集的信息，过户车辆首年投保无出险次数优惠，因很多车辆在过户时并未对保险进行过户，因此需要对该信息进行采集，投保人需要如实告知。
			</div>
			<div class="dlnr clear">4. 投保单信息和配送信息都填写完成了，但是订单显示核保失败，这是什么原因？车险自动核保出现问题平台会自动处理吗？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp订单生成后需要由保险公司核保审核，核保通过才可以进行支付。审核不通过的会显示核保失败，线上无法审核通过的订单保险公司的座席会线下与您沟通，跟您确认投保信息，完成车辆投保。<br>
				您也可以直接致电我们客服电话，或在微信公众号回复“5”联系保行天下微信客服。
			</div>
			<div class="dlnr clear">5. 新车购置价可以在参考价的基础上上下浮动，我该怎么确定？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp新车购置价决定您的车损险、盗抢险、自燃险保额，并直接影响您的保费。新车购置价需要根据您行驶证上的厂牌型号及您购车时的价格确定，高选会导致您多缴纳保险费，但是在车损理赔时还是会根据实际价值进行赔付，低选会导致车辆不足额投保，造成比例赔付。通常情况下，足额投保时，新车购置价可在参考价的基础上上下浮动10%。
			</div>
			<div class="dlnr clear">6. 投保人、被保险人、车主不一致可以投保么？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp投保人与车主不一致可以投保，但不一致时可能无法网上直通。投保人负责缴纳保险金，被保险人享有领取保险金的权利，二者可以不同。被保险人与车主不同时，被保险人需对车辆具备可保利益，与车辆的关系可为使用、管理，同时需要在保单的特别约定中进行说明。 
			</div>
			<div class="dlnr clear">7. 我不想缴纳车船使用税，可以么？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp车船税属于地方税，由地方税务机关负责征收管理。对于机动车，为了方便纳税人缴税，节约纳税人的缴税成本和时间，条例规定从事机动车交通事故责任强制保险业务的保险机构为车船税的扣缴义务人，在销售机动车交通事故责任强制保险时代收代缴车船税，并及时向国库解缴税款。 
			</div>
			<div class="dlnr clear">8. 收到保险公司配送的保单时，需要注意什么？ </div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp保单由保行天下合作服务商负责配送，通常2天左右可送达。您可以在“订单”中查看保单号，点击“官网查单”进行保单验真。（保单验真可能会有1天左右的延时）.保险公司配送时，一定要仔细核对以下单证是否齐全：商业险保单、交强险保单、条款、发票、交强险标志，同时仔细核对保单打印信息是否正确，如投保人、被保险人、车辆信息、保险期限、投保险种等，以免影响理赔。
			</div>
			<div class="dlnr clear">9. 出险了，我该怎么办？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp目前出险了，不要急，保行天下教您四步走，轻轻松松就搞定<br>
				第一步：拨打保险公司的报案电话。如果您不记得，可以看下贴在车上的交强险标志或者拿出保单进行查找。<br>
				第二步：等待保险公司查勘定损，如果符合保险公司免现场流程，可以按照工作人员的指示进行操作。<br>
				第三步：修车并提交理赔所需资料，如果只是小事故，有些保险公司还是免单证的呢。<br>
				第四步：等待领取赔款。<br>
				后期我们会开通移动网上快捷理赔服务。
			</div>
			<div class="dlnr clear">10. 厂牌型号中的字母及数字都代表什么？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp首部有2-3个字母组成，代表汽车生产厂商，如一汽为CA，二汽为EQ,TJ为天津制造厂；中部为四个数字组成，第一个数字代表车辆类型，常见的轿车代码为7，2-3位数字为汽车排量，第4位数字表示生产批次0为第一代，1为第二代，尾部还有字母的为厂家自定义。例：马自达CAF7201AT5,CAF代表一汽马自达，7为轿车，2.0为排气量，1为第二代，AT5为厂家自定义的自动档。
			</div>
			<div class="dlnr clear">11. 车架号（VIN码）可以获知哪些信息？ </div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbspVIN是英文Vehicle Identification Number（车辆识别码）的缩写，VIN码由17位字符组成，俗称车架号。它包含了车辆的生产厂家、年代、车型、车身型式及代码、发动机代码及组装地点等信息。 
			</div>
			<div class="dlnr clear">12. 买了不计免赔就全赔么？ </div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp不计免赔指按照条款规定应当由保险人自行承担的免赔金额部分，保险人负责赔偿。比如车损险事故中，负主要责任，事故责任免赔率为10%。但是不计免赔率也是有责任免除的，以下几种情况还是要扣免赔率的：<br>
				1、无法找到第三方或者应由第三方赔偿的；<br>
				2、超载的；<br>
				3、指定驾驶人或者行驶区域，而出险时非指定驾驶人或非指定区域的；<br>
				4、发生全车盗抢时，被保险人未能提供《机动车登记证书》、机动车来历凭证的，每缺少一项而增加的；<br>
				5、每次事故绝对免赔额；<br>
			</div>
			<div class="dlnr clear">13. 出险多次保费会上浮，可是出险了又不能不索赔，怎么办才好呢 ？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp很多车主都会有这样的经历，不小心制造了单方事故或发生了小小的交通意外，找保险公司索赔，不但未得到100%理赔，还因为出险次数过多，导致来年保费上浮，甚至可能被保险公司拒保。专家在这里建议您，小刮小蹭，私下处理自掏腰包为上策，保险期限内的损失最好不要超过2次，否则第二年上涨的保费说不定还要高过你的理赔款。现在有的保险公司可以选择合并报案，对赔款较少的案件自动放弃索赔，保险期间内再次出险保险公司可一并理赔，在选择投保时可询问投保的保险公司是否有这项服务。
			</div>
			<div class="dlnr clear">14. 第三者责任险多少保额比较合适？</div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp开车上路，如果出现交通意外，不是你撞别人就是别人撞你，赔偿第三者的损失不可避免，交强险总赔偿限额才12.2万，根本不够用，如果碰了豪车或者发生人伤事故，后果更不堪设想。从大多数人投保第三者的保额统计上来看，70%左右的车主都选择了50万的保额，保额居中，不高不低，既能满足现在的人伤赔偿标准，同时对不可预料的损失，保额也相对充足，不会因为保额不足而自掏腰包。人越来越贵，豪车越来越多，50万的保障是您合适的选择。 
			</div>
			<div class="dlnr clear">15. 车辆保险可以提前多少天购买？ </div>
			<div class="sjnr clear">
				&nbsp&nbsp&nbsp&nbsp车险并非只有在到期月才能购买，提前也可以购买。根据承保条例规定，交强险、商业险提前投保天数不得超过3个月，但有些地区有更严格的规定，如：山东、浙江交强险商业险提前投保天数不得超过30天，江苏交强险商业险不得超过40天，上海交强险不得超过40天，辽宁交强险不得超过30天，湖南交强险商业险不得超过60天，贵州遵义交强险商业险不得超过60天。
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#body2').hide();
			$('.sle1').click(function(){
				$('#body1').show();
				$('#body2').hide();
			});
			$('.sle2').click(function(){
				$('#body2').show();
				$('#body1').hide();
			});
		});
	</script>
</body>
</html>