$(function(){
	setNav(1);
});
TouchSlide({ 
	slideCell:"#focus",
	titCell:".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
	mainCell:".bd ul", 
	delayTime:500,
	interTime:7000,
	effect:"leftLoop", 
	autoPlay:true,//自动播放
	autoPage:true, //自动分页
	switchLoad:"_src" //切换加载，真实图片路径为"_src" 
});

if(micStatus&&micStatus==1){
	dialog("请联系维护人员设置团队区域。");
}

if(plateNoStartWith == ""){
	dialog("请联系维护人员设置团队区域。");
}else{
	 $("#province").val(plateNoStartWith.substring(0, 1));
	$("#plateNo").val(plateNoStartWith.substring(1, 2));
}
//$("#plateNo").val(plateNoStartWith);
$("#newCarSign").removeAttr("checked");
$("#form").submit(function(){
	return submits();
	
});
$(".submit").click(function(){
	submits();
	
});
$("#newCarSign").click(function(){
	if($(this).attr("checked") == "checked"){
		$("#plateNo").val(plateNoStartWith.substring(1, 2) + "*");
		if(isWindowsWeChat == "1" || isNotMicromessager == "1"){
			$("#plateNo").attr("readonly","readonly");
		}
		 $("#province").attr("kbreadonly","1");
		$("#plateNo").attr("kbreadonly","1");
	}else{
		$("#plateNo").val(plateNoStartWith.substring(1, 2));
		if(isWindowsWeChat == "1" || isNotMicromessager == "1"){
			$("#plateNo").removeAttr("readonly");
		}
		$("#province").removeAttr("kbreadonly");
		$("#plateNo").removeAttr("kbreadonly");
	}
	
	$("#plateNo").change();
});


function submits(){
	if($("#province").val() == ""){
		$("#province").testRemind("请选择省份");
		return false;
	}
	if($("#plateNo").val() == ""){
		$("#newCarSign").testRemind("请输入车牌号");
		return false;
	}
	if($("#plateNo").val().indexOf("*") > -1 && $("#newCarSign").attr("checked") != "checked"){
		$("#plateNo").testRemind("新车请勾选未上牌");
		return false;
	}
	if($("#plateNo").val().length != 6 &&($("#plateNo").val().indexOf("*")==-1)){
		$("#plateNo").testRemind("请输入正确的车牌号");
		return false;
	}
	var test = /^[0-9a-zA-Z]{6}$/
	if(!test.test($("#plateNo").val())&&($("#plateNo").val().indexOf("*")==-1)){
		$("#plateNo").testRemind("请输入正确的车牌号");
		return false;
	}
	if($("#plateNo").val().indexOf("o") > -1  || $("#plateNo").val().indexOf("i") > -1|| $("#plateNo").val().indexOf("O") > -1|| $("#plateNo").val().indexOf("I") > -1 ){
		$("#plateNo").testRemind("车牌号不能包含O/I");
		return false;
	}
	if('1' == teamSwitch && !$('#ownerName').val()){
		$('#ownerName').testRemind('请输入车主姓名');
		return false;
	}
	var test2 = /^[a-zA-Z\u2E80-\u9FFF]{1,}$/;
	if('1' == teamSwitch && !test2.test($("#ownerName").val())){
		$('#ownerName').testRemind('姓名不能包含数字和特殊字符');
		return false;
	}
	
	$("#plateNoHidden").val($("#province").val()+$("#plateNo").val());
	
	var newCarsign=$("input[name=newCarSign]").attr("checked")?1:0;
	$("input[name=newCarSign]").val(newCarsign);
	var serInfo=$("#infoForm").serialize();
	if($("#zx_mianze").attr('checked') != 'checked'){
		showMsg("请阅读免责声明.");
		return false;
	}
	//是否阅读免责声明 
	if($("#zx_mianze").attr('checked') == 'checked'){
		var inquiryId = $('#inquiryId').val();
		var plateNo = $('#plateNo').val();
		$(".submit").removeAttr("id");
		$(".submit").val("加载中..");
		$(".submit").attr("disabled","disabled");
		loadding = setTimeout("loadingbtn()", 500);
		$.post(ctx + "closet_inquiry.do",{'plateNo':plateNo},function(data){
			var list = data.data;
			if(data.success && null != list && list != '' && list.length > 0){
				clearTimeout(loadding);
				var str = '';	
				for(var i = 0;i<list.length;i++){
					str += '<div class="lrinfo clear"><div class="lt" style="width:34%;">'+list[i].plateNo+'</div><div class="lt" style="width:24%;">&nbsp'+list[i].ownerName+'</div><div class="lt" style="width:25%;">'+list[i].createDate+'</div><div class="lt" style="width:16%;"><div class="btn rt bc2"   onclick=goon('+list[i].state+',"'+list[i].inquiryId+'")>继续</div></div></div>';
					//str += '<div class="csc-s"><span class="csc-s-1">'+list[i].plateNo+'</span><span class="csc-s-2-">'+list[i].ownerName+'</span><span class="csc-s-2">'+list[i].createDate+'</span><span onclick=goon('+list[i].state+',"'+list[i].inquiryId+'") class="smallbluetn csc-s-3">继续</span></div>';
				}
				$('#jingqiluru').html(str);
				str = '';
				//$('.fixedbg,#jingqiluru').show();
				jqlrfun();
			}else{
				window.location.href=$("#form").attr("action")+$("#form").serialize();
			}
		},'json');
	}
	
	return false;
}
function loadingbtn(){
	if(loadingtime == 1){
		$(".submit").val("加载中.");
	}else if(loadingtime == 2){
		$(".submit").val("加载中..");
	}else if(loadingtime == 3){
		$(".submit").val("加载中...");
		loadingtime = 0;
	}else{
		loadingtime = 0;
	}
	loadingtime++;
	loadding = setTimeout("loadingbtn()", 1000);
	
}
function jqlrfun(){
		$('.fixedbg,.jinqilr').fadeIn();
		var tipsel=$('.jinqilr').height(),body = $(document.body).height(),tip=$('.jinqilr .outbox').height(),cha=tipsel-70,end=parseInt(cha/35)*35+9;
		$('.jinqilr').css('margin-top','-'+(parseInt(tipsel+50)/2)+'px'); 
		if($('.jinqilr').outerHeight()>=body*0.7 && tip>=cha){
			$('.jinqilr .outbox').css({'overflow-y':'scroll','height':end+'px'});
			}
		}
$(function(){
	$('.close').click(function(){
		$('.fixedbg,.tipsel').fadeOut();
		$(".submit").attr("id","submit1");
		$(".submit").val("下一步");
		$(".submit").removeAttr("disabled","disabled");
		clearTimeout(loadding);
		loadingtime = 1;
	});
	
	
});

$("#zx-ljtb").bind("click",function(){
	$('.state_info_new2,.state_info_new_pre').hide();
	$('#useType').val('N');
	$('#inquiryId').val("");
	var serInfo=$("#form").serialize();
	loadingtime=1;
	$('.fixedbg,.tipsel').fadeOut();
	loadding = setTimeout("loadingbtn()", 1000);
	window.location.href=$("#form").attr("action")+serInfo;
});

function goon(state,inquiryID){
	$('.state_info_new2,.state_info_new_pre').hide();
	if(state == 2){
		//已报价
		$('#useType').val('C');
		window.location.href=ctx + 'toquota.do?inquiryId='+inquiryID+'&useType=C';
	}else{
		//非已报价
		$('#useType').val('Z');
		$('#inquiryId').val(inquiryID);
		var serInfo=$("#form").serialize();
		window.location.href=$("#form").attr("action")+serInfo;
	}
}