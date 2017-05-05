// JavaScript Document

//焦点图插件
//(function(){
//	var index=0,
//		ul = $(".fcous ul"),
//		li_width = $(window).width(),
//		li_size = $(ul).find("li").size(),	
//		point = $(".points");
//		
//	ul.append(ul.html());
//	ul.find("li").width($(window).width())
//	ul.width(li_size*li_width*2);
//	
//	var points = $(".points");
//	for(var i = 0 ; i < li_size;i++){
//		points.append("<span>"+(i+1)+"</span>");
//	}
//	points.find("span").eq(0).addClass("active");
//
//	
//	//向左滑动
//	$(".fcous").on("swipeLeft",function(){
//		index++;
//		if(index >= li_size){
//			//$(this).find("ul").animate({"-webkit-transform":"translate3d("+(-index*li_width)+"px, 0px, 0px)"},function(){ $(this).css({"-webkit-transform":"translate3d(0px,0px,0px)"})})
//			$(this).find("ul").stop().animate({"left":(-index*li_width)+"px"},function(){
//				$(this).css("left",0)
//				//$(".fcous").find("ul").css({"left":0});
//			});
//			index = 0;
//			points.find("span").eq(index).addClass("active").siblings().removeClass("active");
//		}else{
//			//$(this).find("ul").animate({"-webkit-transform":'translate3d('+(-index*li_width)+'px'+',0px,0px)'},500);
//			$(this).find("ul").stop().animate({"left":(-index*li_width)+"px"},500);
//			points.find("span").eq(index).addClass("active").siblings().removeClass("active");
//			//console.log(-index*li_width+"----")
//		}							
//	})
//	//向右滑动
//	$(".fcous").on("swipeRight",function(){
//		index--;
//		if(index < 0){
//			$(this).find("ul").css({"left":-(li_size)*li_width+"px"})
//			$(this).find("ul").stop().animate({"left":(-(li_size-1)*li_width)+"px"},500);
//			points.find("span").eq(index).addClass("active").siblings().removeClass("active");
//			index = li_size-1;
//		}else{
//			$(this).find("ul").stop().animate({"left":-(index*li_width)+"px"},500);
//			points.find("span").eq(index).addClass("active").siblings().removeClass("active");
//		}
//	})
//	
//}())

$(function(){
	/**险种页面**/
	var info_list = $("#js_info_list");
	function hideVci(){
		$(info_list).find(".js_check").each(function(){
			var ischeck = $(this).attr("checked");
			$(this).parent().parent().hide();
			if(ischeck){
				$(this).attr("checked",false);
			}
		})
	}
	
	function showVci(){
		$(info_list).find(".js_check").each(function(){
			var ischeck = $(this).attr("checked");
				$(this).parent().parent().show();	
		})
	}
	
	$(".bjmp_ischeck").find("input").on("change",function(){									 
		var ischeck = $(this).attr("checked")
		if(ischeck =="checked"){
			$(this).parent().next(".types").show();
		}else{
			$(this).parent().next(".types").hide();
		}
	})
	
	//商业险不勾选，隐藏险种信息
	$("#vciSign").on("click",function(){
		if($(this).attr("checked")){
			$("input[name=damages]").attr("checked",true);
			$("input[name=three]").attr("checked",true);
			$("input[name=driver]").attr("checked",true);
			$("input[name=passenger]").attr("checked",true);
			
			$("input[name=base_anyExcess]").attr("checked",true);//基本不计免赔
			$("input[name=base_anyExcess]").css("background-color","");
			$("input[name=base_anyExcess]").attr("disabled",false);
			$("input[name=added_anyExcess]").attr("checked",false);
			$(".damages_base").attr("checked",false); 
			$(".damages_base").attr("disabled",false); 
			$(".damages_base").css("background-color","");
			
			//重置到选择的默认值
			 chenkexian();
			 sanzhexian()
			 shijixian();
			 chesunxian();
			 if(!$(".moren").attr("checked")){
				 $(".moren2").val("1");
				 $(".moren1").text("不投保");
			 }
			 if(!$("#daoqiangxiancheckbox").attr("checked")){
				 $("#stolenRob").val("");
				 $("#daoqiangxiancheckboxxianshi").text("不投保");
			 }
			 if(!$("input[name='glass']").attr("checked")){
				 $("#glass").val("0");
				 $("#glassxianshi").text("不投保");
			 }
			$("#js_info_list").show();
			
			showVci();
		}else{
			$("#js_info_list").hide();
			hideVci();
		}
	})
	
	/*
	 * 代码不知道做什么用的，先注释测试看看
	//车损险业务
	var csx = $(".js_csx"), isInsure = ["不投保","投保"];
	function csxIsCheck(){
		$(csx).on("change",function(){
			var base=$(".damages_base");
			var value = $(this).find("option:selected").html();
			if(value == isInsure[0]){
				//改变和车损险有关联的险种
				for (var i = 0; i < base.length; i++) {
					$(base[i]).attr("disabled",true);
				}
				$(info_list).find(".js_scx_ischeck").hide();
			}else if(value == isInsure[1]){
				//改变和车损险有关联的险种
				for (var i = 0; i < base.length; i++) {
					$(base[i]).attr("style","background-color:''");
					$(base[i]).attr("disabled",false);
				}
				$(info_list).find(".js_scx_ischeck").show();
			}
		})
	}
	csxIsCheck();
	*/
	
	//商业险不计免赔控制
	$(".vci").click(function(){
		var len = $(".vci:checked").length;
		if(len){
			if($(this).attr("checked")){
				 $("input[name=base_anyExcess]").attr('checked',true);
				 $("input[name=base_anyExcess]").css("background-color","");
				 $("input[name=base_anyExcess]").attr("disabled",false);
			}
		}else{
			 $("input[name=base_anyExcess]").attr('checked',false);
			 $("input[name=base_anyExcess]").css("background-color","#D9D9D9");
			 $("input[name=base_anyExcess]").attr("disabled",true);
		}
	});
	
	//附加险不计免赔控制
	$(".tci").click(function(){
		var len=$(".tci:checked").length;
		if(len){
			if($(this).attr("checked")){
				 $("input[name=added_anyExcess]").attr('checked',true);
				 $("input[name=added_anyExcess]").addClass('not_checked');
				 $("input[name=added_anyExcess]").css("background-color","");
				 $("input[name=added_anyExcess]").attr("disabled",false);
			}
		}else{
			 $("input[name=added_anyExcess]").attr('checked',false);
			 $("input[name=added_anyExcess]").removeClass('not_checked');
			 $("input[name=added_anyExcess]").css("background-color","#D9D9D9");
			 $("input[name=added_anyExcess]").attr("disabled",true);
		}
		
	});
	
	//车损险
	$("#damages").on("change",function(){
		var value=$(this).val();
		var base=$(".damages_base");
		if(value=="1"){//不投保
			//改变和车损险有关联的险种
			for (var i = 0; i < base.length; i++) {
				$(base[i]).attr("checked",false);
				$(base[i]).attr("disabled",true);
				$(base[i]).css("background-color","#D9D9D9");
				var name=$(base[i]).attr("name");
				$("select[id="+name+"]").attr("disabled",true);
			}
			$(info_list).find(".js_scx_ischeck").show();
			//取消附加不计免赔
			$("input[name=added_anyExcess]").attr("checked",false);
		}else{//投保
			//改变和车损险有关联的险种
			for (var i = 0; i < base.length; i++) {
				$(base[i]).attr("checked",false);
				$(base[i]).attr("style","background-color:''");
				$(base[i]).attr("disabled",false);
				var name=$(base[i]).attr("name");
				$("select[id="+name+"]").attr("disabled",false);
			}
			$(info_list).find(".js_scx_ischeck").show();
		}
	})
	
	
	$("input[name=damages]").on("click",function(){
		var base=$(".damages_base");
		if(!$(this).attr("checked")){
			//改变和车损险有关联的险种
			for (var i = 0; i < base.length; i++) {
				$(base[i]).attr("checked",false);
				$(base[i]).attr("disabled",true);
				$(base[i]).css("background-color","#D9D9D9");
				var name=$(base[i]).attr("name");
				$("select[id="+name+"]").attr("disabled",true);
			}
			$(info_list).find(".js_scx_ischeck").show();
			//取消附加不计免赔
			$("input[name=added_anyExcess]").attr("checked",false);
		}else{
			//改变和车损险有关联的险种
			for (var i = 0; i < base.length; i++) {
				$(base[i]).attr("checked",false);
				$(base[i]).attr("style","background-color:''");
				$(base[i]).attr("disabled",false);
				var name=$(base[i]).attr("name");
				$("select[id="+name+"]").attr("disabled",false);
			}
			$(info_list).find(".js_scx_ischeck").show();
		}
	})
		 
	/**底部导航**/
	$(".menu .tab").on("click",
		function () {
			$(this).next(".subM").toggle();
		
			$(this).parent().siblings().find(".subM").hide();
		})
	$(".wrapper").on("click",function(){
		$(".subM").hide();							  
	})
	
	/**返回上一页**/
//	$(".back").on("click",function(){
//		window.history.go(-1);					   
//	})		
	/**显示险别详细**/
	$(".slide").on("click",function(){
		$(this).prev().stop().slideToggle();
		$(this).toggleClass("slideDown");
	})
	
	/**日期默认提示
	function initData(){
		var date = $(".js_date"),
			temp ;
		$(date).on("blur",function(){
			temp = $(date).val();
			if(temp != "2001-01-01"){
				date.val(temp);
			}else{
				date.val("2001-01-01")
			}
		})
	}
	initData()
	**/
	/***list1，list2 页面按钮样式切换*/
	$(".searchBox a").on("click",function(){
		$(this).css("background-color","#878787").siblings().css("background-color","");	
	})
	
	
	/**信息填写页面**/
	var showPrice = $("#js_showPrice"),
		info = $("#js_info");
	showPrice.hide();
	$("#js_calc").on("click",function(e){
		var e = e || window.event;
		var isValid = $.html5Validate.isAllpass($("#js_form"));  //检测表单校验是否全部通过
		if(isValid){
			showPrice.show();
			info.hide();
			e.preventDefault();  
		}else{
			e.preventDefault();  
			return false;
		}		
	})
	$("#js_revise").on("click",function(){
		info.show();
		showPrice.hide();
	})
	
	//显示指定驾驶人信息
	var driverInfo = $("#driverInfo");
	function driverInfoDb(){
		driverInfo.hide();
		$(driverInfo).find("input").each(function(){
			$(this).removeAttr("required");
		})
	}
	driverInfoDb()
	$("#js_driver").on("change",function(){
		if($(this).attr("checked") == "checked"){
			$(driverInfo).show();
			$(driverInfo).find("input").each(function(){
				$(this).attr({required:"required"});
			})
		}else{
			driverInfoDb();
		}
	})
	
	
	/**车型选择页面**/


	//车型以及其他信息
	$(".info_sele").on("change",function(){
		var index = $(this).parent().parent().index();
		$(this).prev(".tb_cell_r").text($(this).find("option:selected").html());
	})
	
	/*显示车辆信息面板模块*/
//	$("#carVersion").on("click",function(){
//		$("#js_car_modle_info").show();
//		$("#search_box").show();
//	})
	
	//点击搜索显示车辆全部信息
	$("#searchBtn").on("click",function(){
		$(this).parent().next(".car_modle_detail").show();
	})
	//选择车辆详细信息
	$(".car_modle_detail li").live("click",function(){
		$(".search_car_info").val($(this).text());
		$(this).parent().hide();
	})
	//选择起保日期自动修改终保日期
//	$("#insStartDate").on("change",function(){
//		var d = new Date();
//		//从当前时间中获取小时和分钟
//		hours = d.getUTCHours()+8;
//		minutes = d.getUTCMinutes();
//		if(hours < 10){
//			hours = "0" + hours;
//		}
//		if(minutes < 10){
//			minutes = "0" + minutes;
//		}
//		var time = hours + ":" + minutes;
//		//获取用户设置的日期时间
//		var stringDate = $("#insStartDate").val();
//		//设置小时分钟为系统当前时间
//		var stringTime = $("#insStartDate").val();
//		var timestamp1 = Date.parse(new Date(stringDate));
//		d.setTime(timestamp1);
//		month = d.getMonth() + 1;
//		date = d.getDate()-1;
//		if(month < 10){
//			month = "0" + month;
//		}
//		if(date < 10){
//			date = "0" + d.getDate();
//		}
//	    var s = (d.getFullYear()+1) + "-" + month + "-" + date;
//	    $("#insEndDate").val(s);
//	    $("#insStartTime").val(time);
//		$("#insEndTime").val(time);
//	})
	
	//选择起保时间自动修改终保时间
	$("#insStartTime").on("change",function(){
		//获取用户设置的日期时间
		var time = $("#insStartTime").val();
		//设置小时分钟为系统当前时间
		$("#insStartTime").val(time);
		$("#insEndTime").val(time);
	})
	
	//选择终保时间自动修改终保时间
	$("#insEndTime").on("change",function(){
		//获取用户设置的日期时间
		var time = $("#insEndTime").val();
		//设置小时分钟为系统当前时间
		$("#insStartTime").val(time);
		$("#insEndTime").val(time);
	})
	
//	/*赋值并隐藏车辆信息模块*/
//	$("#js_confirm").on("click",function(){
//		if(1==$("#verificationId").val()){			
//			$("#carVersion").val($(".search_car_info").val());
//		}
//		$(this).parent().find("#search_box").hide();
//		$(this).parent().find(".car_modle_detail").hide();
//		$("#js_car_modle_info").hide();
//	})

//	获取当前日期：yyyy-MM-dd
	var getNowDate = function(){
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			month = month < 10 ? "0" +month : month;
			var day = date.getDate();
			day = day < 10 ? "0"+day : day;
			return year + "-" + month + "-" + day;
		};
		
	//驾驶员信息
	$("#js_driver_on").on("change",function(){
		var flag = $(this).attr("checked");
		if(flag){
			$("#js_driver_info").show();
			$("#card_name").attr("required",true);
			$("#card_number").attr("required",true);
			$("#driver_type").attr("required",true);
			$("#driver_number").attr("required",true);
			$("#driver_date").attr("required",true);
			$('#card_name').val($("#carName").val());
		}else{
			$("#js_driver_info").hide();
			$("#card_name").attr("required",false);
			$("#card_number").attr("required",false);
			$("#driver_type").attr("required",false);
			$("#driver_number").attr("required",false);
			$("#driver_date").attr("required",false);
			$('#card_name').val("");
		}
	})
	
	//被保人信息
	$("#js_recognizee").on("change",function(){
		var flag = $(this).attr("checked");
		if(flag){
			$("#js_recognizee_info").hide();
		}else{
			$("#js_recognizee_info").show();
		}
	})
	
	/**申请核保页面**/
	$("#js_applicant").on("change",function(){
		var flag = $(this).attr("checked");
		if(flag){
			$("#js_applicant_info").hide();
		}else{
			$("#js_applicant_info").show();
		}
	})
	/**保单配送**/
	$("#js_insurance").on("change",function(){
		var flag = $(this).attr("checked");
		
		if(flag){
			$("#js_insurance_info").hide();	
		}else{
			$("#js_insurance_info").show();	
		}
	})
	//显示商业保险
	$("#js_show_busPrice").on("click",function(){
		$("#js_show_busPrice.stak-star").toggleClass('stak-star-open-2');
		$("#js_info_list").stop().slideToggle(300);
	})
//	//显示结果
//	$("#js_showResult").on("click",function(){
//		$(".wrap_result").show();
//		event.preventDefault();
//	})
	//点击取消隐藏弹出层
	$(".cancel").on("click",function(){
		$(".wrap_result").hide();
	})	
	
	//点击基本不计免赔，将勾选中的险种勾上，其他的字改变颜色
//	$("input[name=added_anyExcess]").on("click",function(){
//		
//		console.debug(input.length);
//	})
//	
	$(".any_excess").on("click",function(){
		//获取所有险种
		var js_check=$(".js_check");
		//选中险种的那么
		var thisName=$(this).attr("name");
		//被点击的基本或者附加,下面的input
		var input=$(this).closest("div").find("input");
		if($(this).attr("checked")){
			for (var i = 0; i < js_check.length; i++) {
				var check=$(js_check[i]).attr("checked");
				var name=$(js_check[i]).attr("name");
				if(name!=thisName&&check){
					for (var j = 0; j < input.length; j++) {
						if($(input[j]).attr("name")=="f_"+name){
							$("input[name=f_"+name+"]").attr("disabled",false);
							$("input[name=f_"+name+"]").attr("checked",true);
						}
					}
				}else{
					$("input[name=f_"+name+"]").parent().css("color","gray");
					$("input[name=f_"+name+"]").attr("disabled",true);
				}
			}
		}else{
			for (var i = 0; i < js_check.length; i++) {
				var name=$(js_check[i]).attr("name");
				for (var j = 0; j < input.length; j++) {
					if($(input[j]).attr("name")=="f_"+name){
						$("input[name=f_"+name+"]").attr("checked",false);
					}
				}
			}
		}
	})
	
	//改变基本和附加不计免赔的颜色
	$(".excess_ins").on("click",function(){
		if($(this).attr("checked")){
			$(this).parent().css("color","black");
		}else{
//			$(this).parent().css("color","gray");
		}
	});
	
	//是否新车选中
	$("#isNewCar").on("click",function(){
		if($(this).attr("checked")){
			//选中处理
			var plateNo=$("#citys option:selected").attr("plateNo");
			if(typeof(plateNo)=="undefined"){
				plateNo=$("#provinces option:selected").attr("plateNo");
			}
//			var plateNo=$('#plateType').val();
			$("#plateNo").val(plateNo+"*");
			$("#plateNo").attr("readonly","readonly");
			$("input[name=plateNo]").attr("required",false);
			$("input[name=plateNo]").attr("pattern","");
			$(this).attr("value","1");
		}else{
			var plateNo=$("#citys option:selected").attr("plateNo");
			if(typeof(plateNo)=="undefined"){
				plateNo=$("#provinces option:selected").attr("plateNo");
			}
//			var plateNo=$('#plateType').val();
			$("#plateNo").val(plateNo);
			$("#plateNo").attr("readonly",false);
			$("input[name=plateNo]").attr("required","required");
			var pa="^[\u4E00-\u9FA5][\\da-zA-Z]{6}$";
			var pattern="";
			for(var i=0;i<pa.length;i++){
				var character=pa.split("");
				var code=Number(character[i].charCodeAt(0));
				if(code>127){
					var charAscii=code.toString(16);
					charAscii=new String("0000").substring(charAscii.length,4)+charAscii;
					pattern+="\\u"+charAscii;
				}
				else{
					pattern+=character[i];
				}
			}
			$("input[name=plateNo]").attr("pattern",pattern);
			$(this).attr("value","0");
		}
	});
})
window.onload = function(){
	//加载完成时隐藏loading
	$('div.loading').hide();
	$('.warpper').show();
	
//	var w = $(window).width(),
//	    h = $(window).height();
		//lis = $(".indexcont ul li.page").size();
	//$(".wrapper,.wrap_result").css("min-height",h);
//	$(".quote").css("min-height",h-111);
	//横竖屏判断
//function orient() { 
//	if (window.orientation == 90 || window.orientation == -90) { 
//		$(".warpper").hide();
//		$(".cover").show(); 
//	}else if (window.orientation == 0 || window.orientation == 180) { 
//		$(".warpper").show();
//		$(".cover").hide();
//	}};
//	var timer;
//	$(window).bind("orientationchange", function(){
//		clearTimeout(timer);
//		timer = setTimeout(orient, 300);
//	});
//	orient();	   
//	GetShape();
	//宽高度获取	
//	function GetShape(){
//		var browser_W=$(window).width();	 
//		var browser_H=$(window).height();	
//		$(".warpper,.wrap_result").css({"width":browser_W,"height":browser_H});
//	};	
//	$(window).resize(function(){
//		GetShape();
//	});
	
};


//初始化日期是当前日期
function getNowDate(){
	var date=new Date();
	var yyyy=date.getFullYear().toString();
	var mm=(date.getMonth()+1)<10 ? "0" + (date.getMonth()+1): (date.getMonth()+1).toString();
	var dd=date.getDate() < 10 ? "0" + date.getDate() : date.getDate().toString();  
	return yyyy+"-"+mm+"-"+dd;
}

//日期转Str
function getDateForStr(date){
	var yyyy=date.getFullYear().toString();
	var mm=(date.getMonth()+1)<10?"0" + (date.getMonth()+1): (date.getMonth()+1).toString();
	var dd=date.getDate() < 10 ? "0" + date.getDate() : date.getDate().toString();  
	return yyyy+"-"+mm+"-"+dd;
}



//把js对象转换为json
function serializeJson(obj){      
     switch(obj.constructor){      
         case Object:      
             var str = "{";      
             for(var o in obj){      
                 str += "\""+o+"\"" + ":" + serializeJson(obj[o]) +",";      
             }  
             if(str.substr(str.length-1) == ",") 
                str = str.substr(0,str.length -1);      
              return str + "}";      
              break;      
          case Array:                  
              var str = "[";      
              for(var o in obj){      
                  str += serializeJson(obj[o]) +",";      
              }      
              if(str.substr(str.length-1) == ",")      
                  str = str.substr(0,str.length -1);      
              return str + "]";      
              break;      
          case Boolean:      
              return "\"" + obj.toString() + "\"";      
              break;      
          case Date:      
              return "\"" + obj.toString() + "\"";      
              break;      
          case Function:      
              break;      
         case Number:      
              return "\"" + obj.toString() + "\"";      
              break;       
          case String:     
             return "\"" + obj.toString() + "\"";      
              break;          
      }      
  }



//处理日期

//设置当前日期
//var dd = new Date();
//var x = dd.toLocaleString();
//var o = pasDate(x);
//$('date').value = o.join('-');

//转化日期函数
//function pasDate(da) {
//    var yp = da.indexOf('年'),
//   mp = da.indexOf('月'),
//   dp = da.indexOf('日');
//    var y = da.substr(0,yp),
//   m = da.substr(yp + 1,mp - yp - 1),
//   d = da.substr(mp + 1,dp - mp - 1);
//    return [y,m,d];
//}

var ma = [['1','3','5','7','8','10'],['4','6','9','11']];
//判断数组a是否存在在元素n
function check(n,a) {
	n=parseInt(n);
    for(var i = 0,len = a.length;i < len;i++) {
    	a[i]=parseInt(a[i]);
        if(a[i] == n) {
            return true;
        }
    }
    return false;
}

function isLeap(y) {
    return ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) ? true : false;
}
//实现加减f：'1'加，'0'减
//2016-02-29
function trans(o,f) {
    var d = o.split('-');
    var l = isLeap(parseInt(d[0]-1));
    if(f == '1') {
        if((check(d[1],ma[0]) && (d[2] == '31')) || (check(d[1],ma[1]) && (d[2] == '30')) ||
                  (d[1] == '2' && d[2] == '28' && !l) || (d[1] == '2' && d[2] == '29' && l)) {
        	var mm = parseInt(d[1] * 1 + 1) < 10 ? "0" + parseInt(d[1] * 1 + 1) : d[1] * 1 + 1;
            return d[0] + '-' + mm + '-01';
        } else if(d[1] == '12' && d[2] == '31') {
            return (d[0] * 1 + 1) + '-' + '01-01';
        } else {
        	var mm = parseInt(d[1]) < 10 ? "0" + parseInt(d[1]) : d[1];
        	var dd=parseInt(d[2] * 1 + 1)<10 ? "0"+parseInt(d[2] * 1 + 1):(d[2] * 1 + 1);
            return d[0] + '-' + mm + '-' + dd;
        }
    } else if(f == '0') {
        if(check(parseInt(d[1]) - 1,ma[0]) && (d[2] == '01')) {
        	var mm=parseInt(d[1] - 1)<10?"0"+parseInt(d[1] - 1):(d[1] - 1);
            return d[0] + '-' + mm + '-' + '31';
        } else if(check(parseInt(d[1]) - 1,ma[1]) && (d[2] == '01')) {
        	var mm=parseInt(d[1] - 1)<10?"0"+parseInt(d[1] - 1):(d[1] - 1);
            return d[0] + '-' + mm + '-' + '30';
        } else if(d[1] == '01' && d[2] == '01') {
            return (d[0] - 1) + '-' + '12-31';
        } else if(d[1] == '03' && d[2] == '01' && !l) {
            return d[0] + '-' + '02-28';
        } else if(d[1] == '03' && d[2] == '01' && l) {
            return d[0] + '-' + '02-29';
        }else if(d[1] == '02' && d[2] == '28' && l) {
            return d[0] + '-' + '02-27';
        }else if(d[1] == '02' && d[2] == '29' && l) {
            return d[0] + '-' + '02-27';
        }else if(d[1] == '02' && d[2] == '29' && !l) {
            return d[0] + '-' + '02-28';
        }else {
        	var mm = parseInt(d[1]) < 10 ? "0" + parseInt(d[1]) : d[1];
        	var dd=parseInt(d[2] - 1) < 10? "0" + parseInt(d[2] - 1) : d[2] - 1;
            return d[0] + '-' + mm + '-' + dd;
        }
    } else {
        return;
    }
}

$("#rote_yes").on("click",function(){
	$("#rote").hide();
})

$("#info_result_yes").on("click",function(){
	$("#info_msg_ti").hide();
})

$("#result_yes").on("click",function(){
	$("#info_msg").hide();
});

//非效果JS
function isNintyAfter(date){
	var loadDate=new Date();
	var yyyy=loadDate.getFullYear().toString();
	var MM=(loadDate.getMonth()+1)<10?"0" + (loadDate.getMonth()+1): (loadDate.getMonth()+1).toString();
	var dd=loadDate.getDate() < 10 ? "0" + loadDate.getDate() : loadDate.getDate().toString();
	if(DateDiff(yyyy+"-"+MM+"-"+dd,date)>90){
		return true;
	}else{
		return false;
	}
}

function  DateDiff(sDate1,  sDate2){
    var  aDate,  oDate1,  oDate2,  iDays  ;
    aDate  =  sDate1.split("-")  ;
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);
    aDate  =  sDate2.split("-");
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  ;
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);
    return  iDays ;
}    
function changeMenuTab(tabNum){
	try {
		if(!tabNum){
			tabNum = 1;
		}
		var src = $('#menu-tab-'+tabNum).attr('data-base');
		if(null != src && '' != src && 'undefined' != src){
			src += "Curr.png";
			$('#menu-tab-'+tabNum).attr('src', src);
		}
	} catch (e) {
		console.log(e);
	}
}
