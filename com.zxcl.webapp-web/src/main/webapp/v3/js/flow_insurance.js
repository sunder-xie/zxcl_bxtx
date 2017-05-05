//初始化数据
	//时间处理
	/*
	var loadDate=new Date();
	var loadYYYY=loadDate.getFullYear().toString();
	var loadMM=(loadDate.getMonth()+1)<10?"0" + (loadDate.getMonth()+1): (loadDate.getMonth()+1).toString();
	var loadDD=loadDate.getDate() < 10 ? "0" + loadDate.getDate() : loadDate.getDate().toString();
	$("#tciStartDate").val(trans(loadYYYY+"-"+loadMM+"-"+loadDD,'1'));
	$("#vciStartDate").val(trans(loadYYYY+"-"+loadMM+"-"+loadDD,'1'));
	var loadEndYYYY=parseInt(loadYYYY)+1;
	var loadEndDD=loadDate.getDate() < 10 ? "0" + loadDate.getDate() : loadDate.getDate().toString();
	$("#tciEndDate").val(loadEndYYYY+"-"+loadMM+"-"+loadEndDD);
	$("#vciEndDate").val(loadEndYYYY+"-"+loadMM+"-"+loadEndDD);
	*/
	//属性：字符串转日期
	String.prototype.toDate = function() {
		var _str = new String(this);
		var _strAry = _str.trim().replace(/ |:|\//g, "-").split("-");
		if(_str.length >= 4 && _strAry.length) {
			var _date = new Date(_strAry[0], parseFloat(_strAry[1] || 1) - 1, _strAry[2] || 1, _strAry[3] || 0, _strAry[4] || 0, _strAry[5] || 0, _strAry[6] || 0);
			//判断结果中是否有N或者a，有则转换异常
			//if(_date == undefined || _date.indexOf('N') > -1 || _date.indexOf('a') > -1){
				//throw new Error("日期转换出错，请输入正确的日期，格式如“yyyy-MM-dd hh:mm:ss”");
			//}
			return _date;
		} else {
			throw new Error("日期格式不正确，至少输入年，格式如“yyyy”");
		}
	};
	//属性：字符串转日期并格式化
	String.prototype.formatDate = function(format) {
		return this.toDate().format(format);
	};
	//属性：日期格式化，与Date.prototype.format暂未归并，日期格式参考：yyyy-MM-dd HH:mm:ss
	Date.prototype.format = function(format) {
		if(!this){return;}
		if(!format){format = "yyyy-MM-dd";}//缺省值
		var M = this.getMonth() + 1, H = this.getHours(), m = this.getMinutes(), d = this.getDate(), s = this.getSeconds(), q = Math.floor((this.getMonth() + 3) / 3), S = this.getMilliseconds();
		var dict = {
			"M+" : M, // month
			"d+" : d, // day
			"H+" : H, // hour
			"m+" : m, // minute
			"s+" : s, // second
			"q+" : q, // quarter
			"S" : S // millisecond
		};//支持毫秒
		if(/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for(var k in dict) {
			if(new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? dict[k] : ("00" + dict[k]).substr(("" + dict[k]).length));
			}
		}
		//判断结果中是否有N或者a，有则转换异常
		//if(format == undefined || format.indexOf('N') > -1 || format.indexOf('a') > -1){
			//return this; 放开会有什么影响还没有测试，使用时一定看看现有的是如何运用这个方法的
			//throw new Error("日期格式不正确");
		//}
		return format;
	};
	//属性：在日期上增加指定的天数v
	Date.prototype.dateAdd=function(v){		
		var d = new Date(this);
		if(/\d/.test(v)){
			d.setDate(d.getDate()+v);
		}else{
			throw new Error("参数不是数字.");
		}
		return d;
	};
	//属性：返回一年后的日期
	Date.prototype.nextYear = function(){
		var d = new Date(this);
		d.setFullYear(d.getFullYear()+1);
		return d;
	};
	
	//属性：计算与传入日期之间的天数(比如1.1天算2天,1.5天算是2天，)
	//老产品的天数算法还是按照Math.round()四舍五入的方法
	Date.prototype.dateDiff = function(date){
		var now = this;
		var diff = Math.abs(now.getTime()-date.getTime());
		return Math.ceil(diff/(24*60*60*1000));//天数差用floor计算
	};
	
function conversion(obj){
	if('true' == obj){
		return true;
	}else if('false' == obj){
		return false;
	}else{
		return '';
	}
}
$(function(){

    var now = new Date();
	now=now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
	//过户日期大于注册日期
	$("input[name=transferDate]").on("blur",function(){
		//所填过户日期
		var transferDate=$(this).val();
		
		//获取注册日期
		var initDate=$("input[name=fstRegNo]").val();
			if(transferDate<initDate&&initDate!=null&&initDate!=""&&transferDate!=null&&transferDate!=""&&transferDate<=now){
				showMsg("过户日期必须大于注册日期，请重新输入。");
				$("input[name=transferDate]").val("");
		}
	});
	$("input[name=fstRegNo]").on("blur",function(){
		//所填过注册期
		var initDate=$(this).val();
		//获取注册日期
		var transferDate=$("input[name=transferDate]").val();
		
			if(transferDate<initDate&&transferDate!=null&&transferDate!=""&&initDate!=null&&initDate!=""&&initDate<=now){
				showMsg("注册日期必须小于过户日期，请重新输入。");
				$("input[name=fstRegNo]").val("");
			}
			var initDate=initDate.toDate();
			var d = (new Date()).format("yyyy-MM-dd 00:00:00").toDate();//当天
			if(initDate>d){
				showMsg("注册日期不能大于当前日期，请重新输入。");
				$("input[name=fstRegNo]").val("");
			}
	});	
	
	
	//交强险起保时间
	$("input[name=tciStartDate]").on("blur",function(){
		var bigDate = $(this).val();
		var _bigDateFormat = "yyyy-MM-dd";
		var _endDateFormat = "yyyy-MM-dd";
		var bgnDate = bigDate.toDate();
		var nextYear = bgnDate.nextYear();
		
		var nextYear_seconds = nextYear.getSeconds();  
		nextYear.setSeconds(nextYear_seconds-1);//往前推一秒
		var endDate = nextYear.format(_endDateFormat);
		
		var d = (new Date()).format("yyyy-MM-dd 00:00:00").toDate();//当天
		var tci_bgn_date = d.dateAdd(1).format(_bigDateFormat);
		var d1 = d.dateAdd(1).nextYear();
		d1.setSeconds(nextYear_seconds-1);//往前推一秒
		var tci_end_date = d1.format(_endDateFormat);
		
		if(bgnDate <= d){
			showMsg("交强险：保险起期不能为今天或今天之前.");
			$(this).val(tci_bgn_date);
			$("#tciEndDate").val(tci_end_date);
		}else if(bgnDate > d.dateAdd(90)){
			showMsg("交强险:只能提前90天投保!");
			$(this).val(tci_bgn_date);
			$("#tciEndDate").val(tci_end_date);
		}else{
			$("#tciEndDate").val(endDate);
		}
	});
	
// 	商业险起保时间
	$("input[name=vciStartDate]").on("blur",function(){
		var bigDate = $(this).val();
		var _bigDateFormat = "yyyy-MM-dd";
		var _endDateFormat = "yyyy-MM-dd";
		var bgnDate = bigDate.toDate();
		var nextYear = bgnDate.nextYear();
		
		var nextYear_seconds = nextYear.getSeconds();  
		nextYear.setSeconds(nextYear_seconds-1);//往前推一秒
		var endDate = nextYear.format(_endDateFormat);
		
		var d = (new Date()).format("yyyy-MM-dd 00:00:00").toDate();//当天
		var tci_bgn_date = d.dateAdd(1).format(_bigDateFormat);
		var d1 = d.dateAdd(1).nextYear();
		d1.setSeconds(nextYear_seconds-1);//往前推一秒
		var tci_end_date = d1.format(_endDateFormat);
		
		if(bgnDate <= d){
			showMsg("商业险：保险起期不能为今天或今天之前.");
			$(this).val(tci_bgn_date);
			$("#vciEndDate").val(tci_end_date);
		}else if(bgnDate > d.dateAdd(90)){
			showMsg("交强险:只能提前90天投保!");
			$(this).val(tci_bgn_date);
			$("#vciEndDate").val(tci_end_date);
		}else{
			$("#vciEndDate").val(endDate);
		}
	});
	
	$(".xianzhongxuanz").click(function(){
		if($(this).attr("id") == "down"){
			$("#base_info").hide();
		}else{
			$("#base_info").show();
		}
		
	});
	
	window.onkeydown= function(e){
	  e = e || window.event;
	  if(e.keyCode == 13){
			 var triggerElement = document.activeElement;
			 var inputId = triggerElement.id;
			 if('carVersion' == inputId)	 
				 $('#searchBtn')[0].click();
			 //$(triggerElement).next('input').focus();
	  }
	}
	if(!areaDTO||!inquiry){
		$('#info_msg2').show();
	}
	$('#result_yes2').click(function(){history.go(-1);});
	$('#result_yes3').click(function(){
		var ticSin = conversion(ticSinp);
		var vicSin = conversion(vicSinp);
		if((ticSin || vicSin) && (!ticSin || !vicSin)){
			if(!ticSin){
				$('#tciSign').attr('disabled','disabled');
				$('#tciSign').attr('checked',false);
			}else{
				$('#vciSign').attr('disabled','disabled');
				$('#vciSign').attr('checked',false);
			}			
			$('#info_msg3').hide();
		}else{			
			history.go(-1);
		}
	});
});
changeMenuTab(1);
function deleteCarRemark(){
	$('#carRemark').val('');
}
function getVehicle(vehicleName,remark){
	var inquiryId = $('input[name=inquiryId]').val();
	var vehiclePrice =$('input[name=vehiclePrice]').val();
	$.ajax({
		type:'post',
		url:ctx+'getVehicle.do',
		data:{'vehicleName':vehicleName,'inquiryId':inquiryId,'vehiclePrice':vehiclePrice,'remark':remark},
		dataType:'json',
		success:function(result){
		}
	});
}
function getResVehicleByModelName(vehicleName){
	$.post(ctx + "getResVehicleByModelName.do",{'modelName':vehicleName},function(data){
		if(data.success){
			
		}
	},'json');
}
$(function(){
	
	if(fstRegNop){
		$('#initDate').attr('required','required');
	}
	
	$('#info_msg1').hide();
	$('#result_yes1').on('click',function(){
		$('#info_msg1').hide();
	});
	//回退
	$(".back").on("click",function(){
	})
	$('.reminder').on('click',function(){
		$(this).hide();
	});
    $('.showdom').on('click',function(){    	
    	$(this).hide();
    })
    
	if($("#js_driver").attr("checked") == "checked"){
		$("#transfer").show();
	}else{
		$("#transfer").hide();
	}
	//初登日期，过户日期，发证日期 提示
  $(".commonDate").on("blur",function(){
		//选中的时间
		var selectDate=new Date(Date.parse($(this).val().replace(/-/g,   "/")));   
		var selectYYYY=selectDate.getFullYear().toString();
		var selectMM=(selectDate.getMonth()+1) < 10? "0" + (selectDate.getMonth()+1): (selectDate.getMonth()+1).toString();
		var selectDD=selectDate.getDate() < 10 ? "0" + selectDate.getDate() : selectDate.getDate().toString();
		//new的时间
		var date=new Date();   
		var yyyy=date.getFullYear().toString();
		var mm=(date.getMonth()+1)<10?"0" + (date.getMonth()+1): (date.getMonth()+1).toString();
		var dd=date.getDate()< 10 ? "0" + date.getDate() : date.getDate().toString(); 
		if(new Date(selectYYYY+"-"+selectMM+"-"+selectDD) > new Date(yyyy+"-"+mm+"-"+dd)){
			$('.showdom').show();
           	$(this).val(trans(parseInt(yyyy)+"-"+mm+"-"+dd));
		}
	}); 
	//
	if($("#js_driver_on").attr("checked")){
		$("#js_driver_info").show();
		$("#card_name").attr("required",true);
		$("#card_number").attr("required",true);
		$("#driver_type").attr("required",true);
		$("#driver_number").attr("required",true);
		$("#driver_date").attr("required",true);
	}else{
	   $("#js_driver_on").attr("checked",false);
	   $("#js_driver_info").hide();
	   $("#card_name").attr("required",false);
		$("#card_number").attr("required",false);
		$("#driver_type").attr("required",false);
		$("#driver_number").attr("required",false);
		$("#driver_date").attr("required",false);
	}
	
	/*//搜索车型并加载到页面
	//为了防止多次点击
	var date = new Array();
	$("#searchBtn").on("click",function(event){
		  //处理在一秒内多次点击
	      date.push(new Date());
	      if (date.length > 1&& (date[date.length - 1].getTime() - date[date.length - 2].getTime() < 1000)) {//小于1秒则认为重复提交
	          event.cancelBubble = true; 
	          return false;
	      }
	      
		$(".car_modle_detail li").remove();
		$(".car_modle_detail li").unbind("click");
		var carModel=$("#carVersion").val();
		if('' == carModel || null == carModel || undefined == carModel){
			showMsg("请输入车型.");
			return;
		}
		var inquiryId=$("input[name=inquiryId]").val();
		$.ajax({
			url:ctx + "getCarType.do?carName="+carModel+"&inquiryId="+inquiryId,
			dataType:"json",
			type:"post",
			success:function(result){
				$("#info_msg").hide();
				if(result.succ){
					if(result.data&&result.data.length>0){
						$("#js_car_modle_info").show();
						$("#car_modle_detail").show();
						$("#car_modle_detail").empty();
						for (var i = 0; i <result.data.length; i++) {
							var li="<p name='"+result.data[i].modelCode+"' seatnum='"+result.data[i].seatNum+
							"'displacement='"+result.data[i].displacement+"' vehicleName='"+result.data[i].vehicleName+
							"'vehiclePrice='"+result.data[i].vehiclePrice+"' configDesc='"+result.data[i].configDesc+"' remark='"+result.data[i].remark+"'>"+result.data[i].remark+"(参考价"+result.data[i].vehiclePrice+"￥)</p>";
							$("#car_modle_detail").append(li);	
		//						<li class="ptb2">宝马BMW X5 3.0si越野车 手自一体 2009款 3.0si 尊贵型 四驱 7座(参考价950000)</li>
						}
						//处理搜索后的车型
						$("#car_modle_detail p").bind("click",function(){
							//隐藏DIV
							$("#car_modle_detail").hide();
							//获取焦点
							$("#carRemark").focus();
							//设置参数
							var seatNum=$(this).attr("seatnum");
							var vehicleId=$(this).attr("name");
							var displacement=$(this).attr("displacement");
							var vehicleName=$(this).attr("vehicleName");
							var vehiclePrice=$(this).attr("vehiclePrice");
							var remark=$(this).attr("remark");
							$("#vehicleId").val(vehicleId);
							$("#seatnum").val(seatNum);
							$("#displacement").val(displacement);
							$("#vehicleName").val(vehicleName);
							$("#vehiclePrice").val(vehiclePrice);
							$(".tempSeatNa").text(seatNum-1);
							//显示信息
							$("#carVersion").val(vehicleName);
							$("#carRemark").val($(this).attr("configDesc"));
							$("#passenger").attr("disabled",false);
							//设置三者险
					        $("#passenger option").each(function () {
					            var val = $(this).val(); //获取单个value
					            if(val.length > 0){
					           		$(this).text(parseInt(val)/10000+"万/座("+(seatNum-1)+"座)");
					            }else{
					            }
					        });
					        getVehicle(vehicleName,remark);
						});
					}else{
						showMsg("未搜索到相关车型");
					}
				}else{
					showMsg(result.msg);
				}
			}
		});
	});*/
	
	$('#searchBtn1').click(function(){
		$(".car_modle_detail li").remove();
		$(".car_modle_detail li").unbind("click");
		var carModel=$("#carVersion").val();
		if('' == carModel || null == carModel || undefined == carModel){
			showMsg("请输入车型.");
			return;
		}
		$.ajax({
			url:ctx+'vhlQuery.do',
			type:'post',
			data:{'vehicleFrameNo':$('#carFrame').val(),'carName':$('#carVersion').val()},
			dataType:'json',
			success:function(result){
				if(result.succ){
					if(result.data&&result.data.length>0){
						$("#car_modle_detail").show();
						$("#car_modle_detail").empty();
						for(var i in  result.data){
							var li="<p name='"+result.data[i].modelCode+"' marketDate='"+result.data[i].marketDate+"' seatNum='"+result.data[i].seatNum+
							"'modelCodeType='"+result.data[i].modelCodeType+"' vehicleName='"+result.data[i].vehicleName+
							"'vehiclePrice='"+result.data[i].vehiclePrice+"' configDesc='"+result.data[i].configDesc+"' remark='"+result.data[i].remark+"'>"+result.data[i].remark+"(参考价"+result.data[i].vehiclePrice+"￥)</p>";
							$("#car_modle_detail").append(li);	
						}
						//处理搜索后的车型
						$("#car_modle_detail p").bind("click",function(){
							//隐藏DIV
							$("#car_modle_detail").hide();
							//获取焦点
							$("#carRemark").focus();
							//设置参数
							var marketDate=$(this).attr("marketDate");
							var vehicleId=$(this).attr("name");
							var modelCodeType=$(this).attr("modelCodeType");
							var vehicleName=$(this).attr("vehicleName");
							var vehiclePrice=$(this).attr("vehiclePrice");
							var remark=$(this).attr("remark");
							var seatNum=$(this).attr("seatNum");
							$("#vehicleId").val(vehicleId);
							$("#marketDate").val(marketDate);
							$("#modelCodeType").val(modelCodeType);
							$("#vehicleName").val(vehicleName);
							$("#vehiclePrice").val(vehiclePrice);
							$("#seatNum").val(seatNum);
							//显示信息
							$("#carVersion").val(vehicleName);
							$("#carRemark").val($(this).attr("remark"));
					        /*getVehicle(vehicleName,remark);*/
						});
					}else{
						showMsg("未搜索到相关车型");
					}
				}else{
					showMsg(result.msg);
				}
			}
		});	
	});
	//是否过户
	$("#js_driver").on("click",function(){
		if($("#js_driver").attr("checked")){
			$("input[name=transferSign]").val("1");
			$("#transfer").show();
			$("#transferDate").attr("required",true);
		}else{
			$("#transfer").hide();
			//清空日期
			$("#transferDate").val("");
			$("input[name=transferSign]").val("0");
			$("#transferDate").attr("required",false);
		}
	});
	$("#js_driver2").on("click",function(){
		if($("#js_driver2").attr("checked")){
			$("#transfer").hide();
			//清空日期
			$("#transferDate").val("");
			$("input[name=transferSign]").val("0");
			$("#transferDate").attr("required",false);
		}
	});
	
	
	//属性：字符串转日期
	String.prototype.toDate = function() {
		var _str = new String(this);
		var _strAry = _str.trim().replace(/ |:|\//g, "-").split("-");
		if(_str.length >= 4 && _strAry.length) {
			var _date = new Date(_strAry[0], parseFloat(_strAry[1] || 1) - 1, _strAry[2] || 1, _strAry[3] || 0, _strAry[4] || 0, _strAry[5] || 0, _strAry[6] || 0);
			//判断结果中是否有N或者a，有则转换异常
			//if(_date == undefined || _date.indexOf('N') > -1 || _date.indexOf('a') > -1){
				//throw new Error("日期转换出错，请输入正确的日期，格式如“yyyy-MM-dd hh:mm:ss”");
			//}
			return _date;
		} else {
			throw new Error("日期格式不正确，至少输入年，格式如“yyyy”");
		}
	};
	//属性：字符串转日期并格式化
	String.prototype.formatDate = function(format) {
		return this.toDate().format(format);
	};
	//属性：日期格式化，与Date.prototype.format暂未归并，日期格式参考：yyyy-MM-dd HH:mm:ss
	Date.prototype.format = function(format) {
		if(!this){return;}
		if(!format){format = "yyyy-MM-dd";}//缺省值
		var M = this.getMonth() + 1, H = this.getHours(), m = this.getMinutes(), d = this.getDate(), s = this.getSeconds(), q = Math.floor((this.getMonth() + 3) / 3), S = this.getMilliseconds();
		var dict = {
			"M+" : M, // month
			"d+" : d, // day
			"H+" : H, // hour
			"m+" : m, // minute
			"s+" : s, // second
			"q+" : q, // quarter
			"S" : S // millisecond
		};//支持毫秒
		if(/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for(var k in dict) {
			if(new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? dict[k] : ("00" + dict[k]).substr(("" + dict[k]).length));
			}
		}
		return format;
	};
	//属性：在日期上增加指定的天数v
	Date.prototype.dateAdd=function(v){		
		var d = new Date(this);
		if(/\d/.test(v)){
			d.setDate(d.getDate()+v);
		}else{
			throw new Error("参数不是数字.");
		}
		return d;
	};
	//属性：返回一年后的日期
	Date.prototype.nextYear = function(){
		var d = new Date(this);
		d.setFullYear(d.getFullYear()+1);
		return d;
	};
	
	//属性：计算与传入日期之间的天数(比如1.1天算2天,1.5天算是2天，)
	//老产品的天数算法还是按照Math.round()四舍五入的方法
	Date.prototype.dateDiff = function(date){
		var now = this;
		var diff = Math.abs(now.getTime()-date.getTime());
		return Math.ceil(diff/(24*60*60*1000));//天数差用floor计算
	};
	var now = new Date();
	
	now=now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
	//过户日期大于注册日期
	$("input[name=transferDate]").on("blur",function(){
		//所填过户日期
		var transferDate=$(this).val();
		
		//获取注册日期
		var initDate=$("input[name=fstRegNo]").val();
			if(transferDate<initDate&&initDate!=null&&initDate!=""&&transferDate!=null&&transferDate!=""&&transferDate<=now){
				$("#tipDate").text("过户日期必须大于注册日期，请重新输入。");
				$('.reminder').show();
				$("input[name=transferDate]").val("");
		}
	});
	$("input[name=fstRegNo]").on("blur",function(){
		//所填过注册期
		var initDate=$(this).val();
		//获取注册日期
		var transferDate=$("input[name=transferDate]").val();
			if(transferDate<initDate&&transferDate!=null&&transferDate!=""&&initDate!=null&&initDate!=""&&initDate<=now){
				$("#tipDate").text("注册日期必须小于过户日期，请重新输入。");
				$('.reminder').show();
				$("input[name=fstRegNo]").val("");
			}
	});
	//交强险起保时间
	$("input[name=tciStartDate]").on("blur",function(){
		var bigDate = $(this).val();
		var _bigDateFormat = "yyyy-MM-dd";
		var _endDateFormat = "yyyy-MM-dd";
		var bgnDate = bigDate.toDate();
		var nextYear = bgnDate.nextYear();
		
		var nextYear_seconds = nextYear.getSeconds();  
		nextYear.setSeconds(nextYear_seconds-1);//往前推一秒
		var endDate = nextYear.format(_endDateFormat);
		
		var d = (new Date()).format("yyyy-MM-dd 00:00:00").toDate();//当天
		var tci_bgn_date = d.dateAdd(1).format(_bigDateFormat);
		var d1 = d.dateAdd(1).nextYear();
		d1.setSeconds(nextYear_seconds-1);//往前推一秒
		var tci_end_date = d1.format(_endDateFormat);
		
		if(bgnDate <= d){
			$("#tipDate").text("交强险：保险起期不能为今天或今天之前.");
			$('.reminder').show();
			$(this).val(tci_bgn_date);
			$("#tciEndDate").val(tci_end_date);
		}else if(bgnDate > d.dateAdd(90)){
			$("#tipDate").text("交强险:只能提前90天投保!");
			$('.reminder').show();
			$(this).val(tci_bgn_date);
			$("#tciEndDate").val(tci_end_date);
		}else{
			$("#tciEndDate").val(endDate);
		}
	});
	
// 	商业险起保时间
	$("input[name=vciStartDate]").on("blur",function(){
		var bigDate = $(this).val();
		var _bigDateFormat = "yyyy-MM-dd";
		var _endDateFormat = "yyyy-MM-dd";
		var bgnDate = bigDate.toDate();
		var nextYear = bgnDate.nextYear();
		
		var nextYear_seconds = nextYear.getSeconds();  
		nextYear.setSeconds(nextYear_seconds-1);//往前推一秒
		var endDate = nextYear.format(_endDateFormat);
		
		var d = (new Date()).format("yyyy-MM-dd 00:00:00").toDate();//当天
		var tci_bgn_date = d.dateAdd(1).format(_bigDateFormat);
		var d1 = d.dateAdd(1).nextYear();
		d1.setSeconds(nextYear_seconds-1);//往前推一秒
		var tci_end_date = d1.format(_endDateFormat);
		
		if(bgnDate <= d){
			$("#tipDate").text("商业险：保险起期不能为今天或今天之前.");
			$('.reminder').show();
			$(this).val(tci_bgn_date);
			$("#vciEndDate").val(tci_end_date);
		}else if(bgnDate > d.dateAdd(90)){
			$("#tipDate").text("交强险:只能提前90天投保!");
			$('.reminder').show();
			$(this).val(tci_bgn_date);
			$("#vciEndDate").val(tci_end_date);
		}else{
			$("#vciEndDate").val(endDate);
		}
	});

	
	//校验组织机构代码
	function checkOrganizationCode(organizationCode){
		var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
		if(organizationCodeTest.test(organizationCode)){
			return true;
		}else{
			return false;
		}
	}
	
	//校验工商注册号码
	function checkBusinessRegistrationNumber(businessRegistrationNumber){
		var businessRegistrationNumberTest = /^\d{15}$/;
		if(businessRegistrationNumberTest.test(businessRegistrationNumber)){
			return true;
		}else{
			return false;
		}
	}
	
	var infoCount = true;
	//提交报价信息
	$("#js_calc_price").on("click",function(){
		//车架号
		var carFrameTest = /^[0-9,A-H,J-N,P,R-Z,a-h,j-n,p,r-z]{17}$/;
		var bool1 = $('#carFrame').val().indexOf("*");
		if(!$('#carFrame').val()){
			$('#carFrame').testRemind("请输入车架号");
			$('#carFrame').focus();
			return false;
		}else if($('#carFrame').val().length != 17){
			$('#carFrame').testRemind("车架号必须为17位");
			$('#carFrame').focus();
			return false;
		}else if(!carFrameTest.test($('#carFrame').val()) && -1 == bool1){
			$('#carFrame').testRemind("车架号不能含有IOQ字母");
			$('#carFrame').focus();
			return false;
		}
		//发动机号
		if(!$('#carEngine').val()){
			$('#carEngine').testRemind("请输入发动机号");
			$('#carEngine').focus();
			return false;
		}
		var carTest = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
		//注册日期
		if(!$('#initDate').val()){
			$('#initDate').testRemind("请输入注册日期");
			$('#initDate').focus();
			return false;
		}
		//车主姓名
		if(!$('#carName').val()){
			$('#carName').testRemind("请输入车主姓名");
			$('#carName').focus();
			return false;
		}
		//身份证号
		var bool2 = $('#cardNumber').val().indexOf("*");
		var certfCdeType = $('select[name=certfCdeType]').val();
		if(!$('#cardNumber').val()){
			var str = '';
			if('1' == certfCdeType){
				str = '身份证号';
			}else if('4' == certfCdeType){
				str = '组织机构代码';
			}else if('5' == certfCdeType){
				str = '工商注册号码';
			}
			$('#cardNumber').testRemind("请输入"+str);
			$('#cardNumber').focus();
			return false;
		}
		if('1' == certfCdeType){			
			if(!carTest.test($('#cardNumber').val()) && -1 == bool2){
				$('#cardNumber').testRemind("车主身份证号格式有误");
				$('#cardNumber').focus();
				return false;
			}else if(!checkIdCard($('#cardNumber').val()) && -1 == bool2){
				$('#cardNumber').testRemind("身份证不合法");
				$('#cardNumber').focus();
				return false;
			}
		}else if('4' == certfCdeType){
			var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
			if(!organizationCodeTest.test($('#cardNumber').val()) && -1 == bool2){
				$('#cardNumber').testRemind("车主组织机构代码格式有误");
				$('#cardNumber').focus();
				return false;
			}
		}else if('5' == certfCdeType){
			var businessRegistrationNumberTest = /^\d{15}$/;
			if(!businessRegistrationNumberTest.test($('#cardNumber').val()) && -1 == bool2){
				$('#cardNumber').testRemind("车主工商注册号码格式有误");
				$('#cardNumber').focus();
				return false;
			}
		}
		
		//投保人身份证校验
		if(!$('#appSameAsOwner').is(':checked')){
			if($("#appNoSameAsOwnerName").val() == ""){
				$("#appNoSameAsOwnerName").testRemind("请输入投保人姓名");
				$("#appNoSameAsOwnerName").focus().select();
				return false;
			}
			var appNoSameAsCertfCdeType = $('select[name=appNoSameAsCertfCdeType]').val();
			var appCardid = $('#appNoSameAsOwnerCard').val();
			if(appCardid == ""){
				var str = '';
				if('1' == appNoSameAsCertfCdeType){
					str = '身份证号';
				}else if('4' == appNoSameAsCertfCdeType){
					str = '组织机构代码';
				}else if('5' == appNoSameAsCertfCdeType){
					str = '工商注册号码';
				}
				$("#appNoSameAsOwnerCard").testRemind("请输入投保人"+str);
				$("#appNoSameAsOwnerCard").focus().select();
				return false;
			}
			if('1' == appNoSameAsCertfCdeType){				
				if(!checkIdCard(appCardid)){
					$("#appNoSameAsOwnerCard").testRemind("您输入的身份证号格式不正确");
					$("#appNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('4' == appNoSameAsCertfCdeType){
				var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
				if(!organizationCodeTest.test(appCardid)){
					$("#appNoSameAsOwnerCard").testRemind("您输入的组织机构代码格式不正确");
					$("#appNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('5' == appNoSameAsCertfCdeType){
				var businessRegistrationNumberTest = /^\d{15}$/;
				if(!businessRegistrationNumberTest.test(appCardid)){
					$("#appNoSameAsOwnerCard").testRemind("您输入的工商注册号码格式不正确");
					$("#appNoSameAsOwnerCard").focus().select();
					return false;
				}
			}
		}
		
		//被保人身份证校验
		if(!$('#insureSameAsOwner').is(':checked')){
			if($("#insureNoSameAsOwnerName").val() == ""){
				$("#insureNoSameAsOwnerName").testRemind("请输入被保人姓名");
				$("#insureNoSameAsOwnerName").focus().select();
				return false;
			}
			var insNoSameAsCertfCdeType = $('select[name=insNoSameAsCertfCdeType]').val();
			var insureCardid = $('#insureNoSameAsOwnerCard').val();
			if(insureCardid == ""){
				var str = '';
				if('1' == insNoSameAsCertfCdeType){
					str = '身份证号';
				}else if('4' == insNoSameAsCertfCdeType){
					str = '组织机构代码';
				}else if('5' == insNoSameAsCertfCdeType){
					str = '工商注册号码';
				}
				$("#insureNoSameAsOwnerCard").testRemind("请输入被保人身份证号");
				$("#insureNoSameAsOwnerCard").focus().select();
				return false;
			}
			if('1' == insNoSameAsCertfCdeType){				
				if(!checkIdCard(insureCardid)){
					$("#insureNoSameAsOwnerCard").testRemind("您输入的身份证号格式不正确");
					$("#insureNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('4' == insNoSameAsCertfCdeType){
				if(!checkOrganizationCode(insureCardid)){
					$("#insureNoSameAsOwnerCard").testRemind("您输入的组织机构代码格式不正确");
					$("#insureNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('5' == insNoSameAsCertfCdeType){
				if(!checkBusinessRegistrationNumber(insureCardid)){
					$("#insureNoSameAsOwnerCard").testRemind("您输入的工商注册号码不正确");
					$("#insureNoSameAsOwnerCard").focus().select();
					return false;
				}
			}
		}
		
		
		//过户日期
		var transferDateSign = document.getElementById("js_driver").checked;
		if(transferDateSign && !$('#transferDate').val()){
			$('#transferDate').testRemind("请输入过户日期");
			$('#transferDate').focus();
			return false;
		}
		//险种
		var tciSign = document.getElementById("tciSign").checked;
		var vciSign = document.getElementById("vciSign").checked;
		if(!tciSign && !vciSign){
			$('#tciSign').testRemind("请选择一个险种");
			$('#tciSign').focus();
			return false;
		}
		//被保人
		var insureSameAsOwner = document.getElementById("insureSameAsOwner").checked;
		if(!insureSameAsOwner){
			if(!$('#insureNoSameAsOwnerName').val()){
				$('#insureNoSameAsOwnerName').testRemind("请输入被保人姓名");
				$('#insureNoSameAsOwnerName').focus();
				return false;
			}
			var insNoSameAsCertfCdeType = $('select[name=insNoSameAsCertfCdeType]').val();
			var bool3 = $('#insureNoSameAsOwnerCard').val().indexOf("*");
			if(!$('#insureNoSameAsOwnerCard').val()){
				var str = '';
				if('1' == insNoSameAsCertfCdeType){
					str = '身份证号';
				}else if('4' == insNoSameAsCertfCdeType){
					str = '组织机构代码';
				}else if('5' == insNoSameAsCertfCdeType){
					str = '工商注册号码';
				}
				$('#insureNoSameAsOwnerCard').testRemind("请输入被保人"+str);
				$('#insureNoSameAsOwnerCard').focus();
				return false;
			}
			if('1' == insNoSameAsCertfCdeType){
				if(!carTest.test($('#insureNoSameAsOwnerCard').val()) && -1 == bool3){
					$("#insureNoSameAsOwnerCard").testRemind("被保人身份证号格式有误");
					$('#insureNoSameAsOwnerCard').focus();
					return false;
				}
			}else if('4' == insNoSameAsCertfCdeType){
				if(!checkOrganizationCode($('#insureNoSameAsOwnerCard').val()) && -1 == bool3){
					$("#insureNoSameAsOwnerCard").testRemind("被保人组织机构代码格式有误");
					$('#insureNoSameAsOwnerCard').focus();
					return false;
				}
			}else if('5' == insNoSameAsCertfCdeType){
				if(!checkBusinessRegistrationNumber($('#insureNoSameAsOwnerCard').val()) && -1 == bool3){
					$("#insureNoSameAsOwnerCard").testRemind("被保人工商注册号码格式有误");
					$('#insureNoSameAsOwnerCard').focus();
					return false;
				}
			}
		}
		//投保人
		var appSameAsOwner = document.getElementById("appSameAsOwner").checked;
		if(!appSameAsOwner){
			if(!$('#appNoSameAsOwnerName').val()){
				$('#appNoSameAsOwnerName').testRemind("请输入投保人姓名");
				$('#appNoSameAsOwnerName').focus();
				return false;
			}
			var appNoSameAsCertfCdeType = $('input[name=appNoSameAsCertfCdeType]').val();
			var bool4 = $('#appNoSameAsOwnerCard').val().indexOf("*");
			if(!$('#appNoSameAsOwnerCard').val()){
				var str = '';
				if('1' == appNoSameAsCertfCdeType){
					str = '身份证号';
				}else if('4' == appNoSameAsCertfCdeType){
					str = '组织机构代码';
				}else if('5' == appNoSameAsCertfCdeType){
					str = '工商注册号码';
				}
				$('#appNoSameAsOwnerCard').testRemind("请输入投保人"+str);
				$('#appNoSameAsOwnerName').focus();
				return false;
			}
			if('1' == appNoSameAsCertfCdeType){
				if(!carTest.test($('#appNoSameAsOwnerCard').val()) && -1 == bool4){
					$("#appNoSameAsOwnerCard").testRemind("投保人身份证号格式有误");
					$('#appNoSameAsOwnerName').focus();
					return false;
				}
			}else if('4' == appNoSameAsCertfCdeType){
				if(!checkOrganizationCode($('#appNoSameAsOwnerCard').val()) && -1 == bool4){
					$("#appNoSameAsOwnerCard").testRemind("投保人组织机构代码格式有误");
					$('#appNoSameAsOwnerName').focus();
					return false;
				}
			}else if('5' == appNoSameAsCertfCdeType){
				if(!checkBusinessRegistrationNumber($('#appNoSameAsOwnerCard').val()) && -1 == bool4){
					$("#appNoSameAsOwnerCard").testRemind("投保人工商注册号码格式有误");
					$('#appNoSameAsOwnerName').focus();
					return false;
				}
			}
		}
		//交强起保日期
		var tciSign = document.getElementById("tciSign").checked;
		if(tciSign && !$('#tciStartDate').val()){
			$('#tciStartDate').testRemind("请输入交强险起保日期");
			$('#tciStartDate').focus();
			return false;
		}
		//商业起保日期
		var vciSign = document.getElementById("vciSign").checked;
		if(vciSign && !$('#vciStartDate').val()){
			$('#vciStartDate').testRemind("请输入商业险起保日期");
			$('#vciStartDate').focus();
			return false;
		}
		/*$.ajax({
			url:ctx+'vhlQuery.do',
			type:'POST',
			data:{'vehicleFrameNo':$('#carFrame').val()},
			dataTpye:'json',
			async:false,
			success:function(result){
				if(result.succ){
					if(null != result.data){
						$("#vehicleId").val(result.data.modelCode);
						$("#marketDate").val(result.data.marketDate);
						$("#modelCodeType").val(result.data.modelCodeType);
						$("#vehicleName").val(result.data.vehicleName);
						$("#vehiclePrice").val(result.data.vehiclePrice);
						$("#seatNum").val(result.data.seatNum);
						//显示信息
						$("#carVersion").val(result.data.vehicleName);
						$("#carRemark").val(result.data.configDesc);
						$("#car_modle_detail").show();
						$("#car_modle_detail").empty();
						for(var i in  result.data){
							var li="<p name='"+result.data[i].modelCode+"' marketDate='"+result.data[i].marketDate+"' seatNum='"+result.data[i].seatNum+
							"'modelCodeType='"+result.data[i].modelCodeType+"' vehicleName='"+result.data[i].vehicleName+
							"'vehiclePrice='"+result.data[i].vehiclePrice+"' configDesc='"+result.data[i].configDesc+"' remark='"+result.data[i].remark+"'>"+result.data[i].remark+"(参考价"+result.data[i].vehiclePrice+"￥)</p>";
							$("#car_modle_detail").append(li);	
						}
						//处理搜索后的车型
						$("#car_modle_detail p").bind("click",function(){
							//隐藏DIV
							$("#car_modle_detail").hide();
							//获取焦点
							$("#carRemark").focus();
							//设置参数
							var marketDate=$(this).attr("marketDate");
							var vehicleId=$(this).attr("name");
							var modelCodeType=$(this).attr("modelCodeType");
							var vehicleName=$(this).attr("vehicleName");
							var vehiclePrice=$(this).attr("vehiclePrice");
							var remark=$(this).attr("remark");
							var seatNum=$(this).attr("seatNum");
							$("#vehicleId").val(vehicleId);
							$("#marketDate").val(marketDate);
							$("#modelCodeType").val(modelCodeType);
							$("#vehicleName").val(vehicleName);
							$("#vehiclePrice").val(vehiclePrice);
							$("#seatNum").val(seatNum);
							//显示信息
							$("#carVersion").val(vehicleName);
							$("#carRemark").val($(this).attr("remark"));
					        getVehicle(vehicleName,remark);
						});
					}
				}
			}
		});*/
		
		//关闭本来存在的加载
		$('#loading').hide();
    	//清空车型内容
		$("#carQueryResult").html('');
		//关闭主界面
		$('.ins_main').hide();
		//显示车型查询界面
		$('#chexingchaxun').show();
		var vehicleFrameNo = $('#carFrame').val();
		//显示根据车架号查询的头
		/*$('#carOrCarFrame').show();
		$('#carOrCarFrame').html('根据车架号<b>'+vehicleFrameNo+'</b>查询车型');*/
		//应该车型信息查询的输入框
		$('#vehicleNameQuery').hide();
		//开始加载
		$('#loading').show();	
		//隐藏根据车架号找不到车型
		$("#chexingchaxuan .carNoQuery").hide();
		
		initCarQuery();
//		if(vehiclePrice){
//			var remark = vehicleName + ' ' + displacement + 'L ' + marketDate +'款 ' + seatNum +'座(参考价'+vehiclePrice+'￥)';
//			console.info(remark);
//			$('#carQueryResult').html('<br/><br/><br/><span style="width:90%;margin-left:5%;">'+remark+'</span>');
//			//当通过车架号查询的时候，不管是否有结果，都可点击使用车型名称查询
//			var vinnoquery = $("<div class=\"carNoQuery\" >找不到对应车型，试试品牌车型查询&gt;&gt;<br/><br/><br/></div>");
//			vinnoquery.click(function(){
//				disbaledCalcBtn();
//				
//				$('#carOrCarFrame').hide();
//				$(this).remove();
//				//增加使用车型名称查询提示
//				$("#chexingchaxun #vinsearch").remove();
//				$("#chexingchaxun #namesearch").remove();
//				var namequery = $("<div class=\"namesearch\" id=\"vehicleNameQuery\"></div>");
//				var namequeryInput = $("<input type=\"search\" style='text-transform:uppercase;' id=\"vehicleName\" value='"+vehicleName1+"' placeholder=\"请输入品牌车型\" /> <input type='button' onclick='carQuery()' style='width:15%;background-color:#D64141;color:#FFFFFF;text-indent:0px;' value='搜索'>");
//				/*namequeryInput.focus(function(){
//					carQueryTwo();
//				});
//				namequeryInput.blur(function(){
//					clearCarQueryTwo();
//				});*/
//				console.info(namequeryInput);
//				namequery.append(namequeryInput);
//				
//				$("#chexingchaxun #carQueryResult").before(namequery);
//				
//	        	$('#carQueryResult').empty();
//	        	
//			});
//			$("#chexingchaxun").append(vinnoquery);
//			enabledCalcBtn();
//			$('#loading').hide();
//		}else{			
			carQuery(vehicleFrameNo);
//		}
		
		$('#back1').hide();
		$('#back2').show();
		/*$('.ins_main').hide();*/
		$('.ins_car').show();
	});
	
	$("#js_calc_price1").on("click",function(){
		
		if($("#carQueryResult input[name=odnfbdft]:checked").size() < 1){
			return ;
		}
		var tciSign =$("#tciSign").attr("checked");
		var vciSign =$("#vciSign").attr("checked");
		if(!tciSign & !vciSign){
			showMsg("请选择一个险种.");
			//$('#info_msg1').show();
		}else{
			var i = policyQuery(panduan);
			if(i){
				return;
			}
			saveInfo();
			$('.before1-zx-hide1').attr('data-status',1);
			$('.before2-zx-hide1,.before2-zx-hide2').html('<img src="'+ctx+'images/arrow/arrowU.png">');
			$('.zx-hide1').show();
	 		$("#comPriceForm").submit();
	 		infoCount = false;
		}
	});
	
	$('#save_msg').hide();
	$('#save_yes').on('click',function(){
		$('#save_msg').hide();
	});
	//提交报价信息现在暂存表数据============================================
	$("#js_calc_temp").on("click",function(){
		var i = policyQuery(panduan);
		if(i){
			return;
		}
		saveInfo();
	});
	
	function saveInfo(){
		setTransferSign();
		setInsTypeSign();
		setCoverItems();
		var item=$("input[name=items]").val();
		var frm = $("#comPriceForm");
		$.ajax({
  			url:ctx + "informationTempSave.do",
			dataType:"json",
			type:"post",
			data:frm.serialize(),
			async:false,
			success:function(result){
				if(result.succ){
					if(infoCount){
						showMsg(result.msg);
						//$('#save_info').html(result.msg);
						//$('#save_msg').show();
					}else{
						infoCount = true;
					}
				}else{
					showMsg("询价单保存异常，请重试");
				}
			}
		});
	}
	
	function policyQuery(panduan){
		var i = true;
		var inquiryId = $('input[name=inquiryId]').val();
		$.ajax({
			type:'post',
			url:ctx+'policyQuery.do',
			data:{'inquiryId':inquiryId,'panduan':panduan},
			dataType:'json',
			async:false,
			success:function(result){
				if(result.succ){
					if(null != result.data){
						var str = "";
						if('5' == result.data.queryStage){
							str = '订单正在生成保单，不可修改投保内容。点确定进入支付成功的订单详情页';
						}
						if('6' == result.data.queryStage){
							str = '订单已生成保单，不可修改投保内容。点确定进入保单详情页';
						}
						if(str){
							dialog(str, "查看明细",function(){
								window.location.href=ctx+'detail.do?orderId='+result.data.orderId+'&insId='+result.data.insurance.insId+'&quotaId='+result.data.quota.quotaId;
							});
						}else{
							window.location.href=ctx+result.msg;
						}
					}else{
						i = false;
					}
				}
			}
		});
		return i;
	}
	//设置过户标志
   	function setTransferSign(){
		var transferSign=$("input[name=transferSign]").attr("checked")?1:0;
		$("input[name=transferSign]").val(transferSign);
		if(transferSign =="1"){
			$("#transferDate").attr("required",true);
		}
		
	}
	
	//设置险种值
	function setInsTypeSign(){
		var vciSign=$("#vciSign").attr("checked")?1:0;
		$("input[name=vciSign]").val(vciSign);
		var tciSign=$("#tciSign").attr("checked")?1:0;
		$("input[name=tciSign]").val(tciSign);
	}
	
	//处理驾驶员信息
	/*function setDrivers(){
		var drivers=[];
		if($("#js_driver_on").attr("checked")){
			var driver1=new Object();
			driver1.name=$("input[name=name1]").val();
			driver1.certNo=$("input[name=certNo1]").val();
			driver1.driverType=$("#driver_type1 option:selected").val();
			driver1.licenseDate=$("input[name=licenseDate1]").val();
			drivers.push(driver1);
		}
		$("input[name=drivers]").val(serializeJson(drivers));
	}*/
	
	//处理险别信息
	function setCoverItems(){
		var items = getItems();
		if(items){			
			$("input[name=items]").val(serializeJson(items));
		}
		console.dir(items);
	}
	
	//没有座位数的乘客险
	/*if(!parseInt($("input[name=seatNum]").val())){
		$("#passenger").attr("disabled",true);
	}*/
});

$("form").html5Validate(function() {
	//alert("全部通过！");
	this.submit();	
}, {
	 validate:function(){
		var certfCdeType = $('select[name=certfCdeType]').val();
		var idCarid=$("input[name=ownerCertNo]").val();
		if(idCarid.indexOf("*") != -1){
			if(!idCarid){
				var str = "";
				if('1' == certfCdeType){
					str = '身份证号';
				}else if('4' == certfCdeType){
					str = '组织机构代码';
				}else if('5' == certfCdeType){
					str = '工商注册号码';
				}
				$("#cardNumber").testRemind("您尚未输入"+str);
				$("input[name=ownerCertNo]").focus().select();
				return false;
			}
		}
		else{
			if('1' == certfCdeType){				
				if(!checkIdCard(idCarid)){
					$("#cardNumber").testRemind("您输入的身份证号格式不正确");
					$("input[name=ownerCertNo]").focus().select();
					return false;
				}
			}else if('4' == certfCdeType){
				var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
				if(!organizationCodeTest.test(idCarid)){
					$("#cardNumber").testRemind("您输入的组织机构代码格式不正确");
					$("input[name=ownerCertNo]").focus().select();
					return false;
				}
			}else if('5' == certfCdeType){
				var businessRegistrationNumberTest = /^\d{15}$/;
				if(!businessRegistrationNumberTest.test(idCarid)){
					$("#cardNumber").testRemind("您输入的工商注册号码格式不正确");
					$("input[name=ownerCertNo]").focus().select();
					return false;
				}
			}
		}
		
		//投保人身份证校验
		if(!$('#appSameAsOwner').is(':checked')){
			var appNoSameAsCertfCdeType = $('select[name=appNoSameAsCertfCdeType]').val();
			var appCardid = $('#appNoSameAsOwnerCard').val();
			if('1' == appNoSameAsCertfCdeType){				
				if(!checkIdCard(appCardid)){
					$("#appNoSameAsOwnerCard").testRemind("您输入的身份证号格式不正确");
					$("#appNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('4' == appNoSameAsCertfCdeType){
				var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
				if(!organizationCodeTest.test(appCardid)){
					$("#appNoSameAsOwnerCard").testRemind("您输入的组织机构代码格式不正确");
					$("#appNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('5' == appNoSameAsCertfCdeType){
				var businessRegistrationNumberTest = /^\d{15}$/;
				if(!businessRegistrationNumberTest.test(appCardid)){
					$("#appNoSameAsOwnerCard").testRemind("您输入的工商注册号码格式不正确");
					$("#appNoSameAsOwnerCard").focus().select();
					return false;
				}
			}
		}
		
		//被保人身份证校验
		if(!$('#insureSameAsOwner').is(':checked')){
			var insNoSameAsCertfCdeType = $('select[name=insNoSameAsCertfCdeType]').val();
			var insureCardid = $('#insureNoSameAsOwnerCard').val();
			if('1' == insNoSameAsCertfCdeType){				
				if(!checkIdCard(insureCardid)){
					$("#insureNoSameAsOwnerCard").testRemind("您输入的身份证号格式不正确");
					$("#insureNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('4' == insNoSameAsCertfCdeType){
				var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
				if(!organizationCodeTest.test(insureCardid)){
					$("#insureNoSameAsOwnerCard").testRemind("您输入的组织机构代码格式不正确");
					$("#insureNoSameAsOwnerCard").focus().select();
					return false;
				}
			}else if('5' == insNoSameAsCertfCdeType){
				var businessRegistrationNumberTest = /^\d{15}$/;
				if(!businessRegistrationNumberTest.test(insureCardid)){
					$("#insureNoSameAsOwnerCard").testRemind("您输入的工商注册号码格式不正确");
					$("#insureNoSameAsOwnerCard").focus().select();
					return false;
				}
			}
		}
		
		//update by zxj 由于需求变动 很多校验都要改 有*号的情况
//		if(!checkIdCard(idCarid)){
//			$("#cardNumber").testRemind("您输入的身份证号格式不正确");
//			$("input[name=ownerCertNo]").focus().select();
//			return false;
//		}
		return true;
	 }
	// novalidate: false,
	// labelDrive: false
});

//回显信息
$(function(){
	if(parseInt(signp)){
		/*//驾驶员信息
		 if(driversp){
			   $("#js_driver_on").attr("checked",true);
			   $("#js_driver_info").show();
			   $("#card_name").attr("required",true);
			   $("#card_number").attr("required",true);
				$("#driver_type").attr("required",true);
				$("#driver_number").attr("required",true);
				$("#driver_date").attr("required",true);
		 }*/
		//行驶区域
		$("#driving_area").val(inquiry_drivingRegp);			
		/*//过户标志
		if(parseInt(inquiry_transferSignp)){
			$("#transfer").show();
			$("input[name=transferSign]").attr("checked",true);
			$("input[name=transferSign]").val("1");
		}*/
	}
});

//	附加修护7
$(function(){
	
	/*var sum1=$("#seatnum").val();
	if(sum1>0){
		$(".tempSeatNa").text(sum1-1);
	    $("#passenger option").each(function () {
            var val = $(this).val(); //获取单个value
       	    if(val.length > 0){
           		$(this).text(parseInt(val)/10000+"万/座("+(sum1-1)+"座)");
            }else{
            	//$(this).text("不投保");
            }
            
        });
	    
	}*/
	
	
	var damages = $("input[name=damages]").attr("checked");
	var three = $("input[name=three]").attr("checked");
	var driver = $("input[name=driver]").attr("checked");
	var passenger = $("input[name=passenger]").attr("checked");
	var stolenRob = $("input[name=stolenRob]").attr("checked");
	
});



/**
 * 车型查询，先根据车架号带出品牌名称，然后自动搜索。如果没有搜索到品牌名称，弹出输入框
 */
function carQuery(vin){
	
//	if(vin != undefined && vin != ""){
//		data = {'vehicleFrameNo':vin};
//		$("#carQueryResult").empty();
//		$('#loading').show();
//		$.ajax({
//			url:ctx+'vhlQuery.do',
//			type:'POST',
//			data:data,
//			dataTpye:'json',
//			success:function(result){
//				if(result.succ){
//					for(var i in  result.data){
//						var name = result.data[i].vehicleName;
//						carQueryByName(name);
//						var namequery = $("<div class=\"namesearch\" id=\"vehicleNameQuery\"></div>");
//						var namequeryInput = $("<input type=\"search\" style='text-transform:uppercase;' id=\"vehicleName\" value='"+name+"' placeholder=\"请输入品牌车型\" /> <input type='button' onclick='carQueryByName()' style='width:15%;background-color:#D64141;color:#FFFFFF;text-indent:0px;' value='搜索'>");
//						namequery.append(namequeryInput);
//						
//						$("#chexingchaxun #carQueryResult").before(namequery);
//						
//						
//						
//						break;
//					}
//				}
//			}
//		});
//	}else{
//		var namequery = $("<div class=\"namesearch\" id=\"vehicleNameQuery\"></div>");
//		var namequeryInput = $("<input type=\"search\" style='text-transform:uppercase;' id=\"vehicleName\" value='"+name+"' placeholder=\"请输入品牌车型\" /> <input type='button' onclick='carQueryByName()' style='width:15%;background-color:#D64141;color:#FFFFFF;text-indent:0px;' value='搜索'>");
//		namequery.append(namequeryInput);
//		
//		$("#chexingchaxun #carQueryResult").before(namequery);
//		
//		
//	}
	
	
	var carName = $('#vehicleName').val();
	var data = {};
	//如果有vin 则用vin查询，否则用品牌名称
	if(vin != undefined && vin != ""){
		data = {'vehicleFrameNo':vin};
		//增加使用vin查询提示
		$("#chexingchaxun #vinsearch").remove();
		$("#chexingchaxun #namesearch").remove();
		$("#chexingchaxun #carQueryResult").before($("<div class=\"vinsearch\" id=\"carOrCarFrame\"><br/><br/><br/></div>"));
		
	}else{
		//清理一些效果
		$("#chexingchaxun #vinsearch").remove();
		$("#chexingchaxun #namesearch").remove();
		
		data = {'carName':carName};
	}
	
	$("#carQueryResult").empty();
	$('#loading').show();
	$.ajax({
		url:ctx+'vhlQuery.do',
		type:'POST',
		data:data,
		dataTpye:'json',
		success:function(result){
			$('#loading').hide();
			
			if(result.succ){
				//$('#vehicleName').val(result.data[0].vehicleName);
				/*$('#carQueryResult').css('margin-top','8%');*/
				for(var i in  result.data){
					var str = '<div class="carStyle"  name="'+result.data[i].modelCode+'" marketDate="'+result.data[i].marketDate+'" seatNum="'+result.data[i].seatNum+
					'"modelCodeType="'+result.data[i].modelCodeType+'" vehicleName="'+result.data[i].vehicleName+
					'"vehiclePrice="'+result.data[i].vehiclePrice+'" configDesc="'+result.data[i].configDesc+'" remark="'+result.data[i].remark+'" displacement="'+result.data[i].displacement+'"><input class="ck1 qwerw" name="odnfbdft"  type="radio" />'+result.data[i].remark+'(参考价'+result.data[i].vehiclePrice+'￥)</div>';
					$("#carQueryResult").append(str);	
				}
				//处理搜索后的车型
				$('.carStyle').bind('click',function(){
					$(this).find('.qwerw')[0].click();
					//设置参数
					var marketDate=$(this).attr("marketDate");
					var vehicleId=$(this).attr("name");
					var modelCodeType=$(this).attr("modelCodeType");
					var vehicleName=$(this).attr("vehicleName");
					var vehiclePrice=$(this).attr("vehiclePrice");
					var remark=$(this).attr("remark");
					var seatNum=$(this).attr("seatNum");
					var displacement = $(this).attr('displacement');
					$("#vehicleId").val(vehicleId);
					$("#marketDate").val(marketDate);
					$("#modelCodeType").val(modelCodeType);
					$("#vehicleName").val(vehicleName);
					vehicleName1 = vehicleName;
					$("#vehiclePrice").val(vehiclePrice);
					$("#seatNum").val(seatNum);
					$('#displacement').val(displacement);
					//显示信息
					$("#carVersion").val(vehicleName);
					$('input[name=vehicleName]').val(vehicleName);
					$("#carRemark").html(remark);
					/*$('#chexingchaxun').hide();*/
					$('input[name=remark]').val(remark);
					/*$('.carStyle').removeAttr('background');*/
					$('.carStyle').css("background", "");
					$(this).css("background", "#E0E0E0");
					/*$('.ins_main').show();*/
					/*$(".bombtn").show();*/
					
					enabledCalcBtn();
				});
			}
			
			
			if(vin != undefined && vin != ""){
				//当通过车架号查询的时候，不管是否有结果，都可点击使用车型名称查询
				var vinnoquery = $("<div class=\"carNoQuery\" >找不到对应车型，试试品牌车型查询&gt;&gt;<br/><br/><br/></div>");
				vinnoquery.click(function(){
					disbaledCalcBtn();
					
					$('#carOrCarFrame').hide();
					$(this).remove();
					//增加使用车型名称查询提示
					$("#chexingchaxun #vinsearch").remove();
					$("#chexingchaxun #namesearch").remove();
					var namequery = $("<div class=\"namesearch\" id=\"vehicleNameQuery\"></div>");
					var namequeryInput = $("<input type=\"search\" style='text-transform:uppercase;' id=\"vehicleName\" value='"+vehicleName1+"' placeholder=\"请输入品牌车型\" /> <input type='button' onclick='carQuery()' style='width:15%;background-color:#D64141;color:#FFFFFF;text-indent:0px;' value='搜索'>");
					/*namequeryInput.focus(function(){
						carQueryTwo();
					});
					namequeryInput.blur(function(){
						clearCarQueryTwo();
					});*/
					console.info(namequeryInput);
					namequery.append(namequeryInput);
					
					$("#chexingchaxun #carQueryResult").before(namequery);
					
		        	$('#carQueryResult').empty();
		        	
				});
				$("#chexingchaxun").append(vinnoquery);
				
				
			}else {
				$("#chexingchaxun #vinsearch").remove();
				$("#chexingchaxun #namesearch").remove();
				var namenoquery1 = $('.carNoQuery').html();
				if(!namenoquery1){					
					var namenoquery = $("<div  class=\"carNoQuery\" >找不到车型？请通过微信公众号联系客服.<br/><br/><br/></div>");
					$("#chexingchaxun").append(namenoquery);
				}
			}
		}
	});
}
function carQueryByName(name){
	disbaledCalcBtn();
	var data = undefined;
	if(name == undefined || name == ""){
		data = {'carName':$("#vehicleName").val()};
	}else{
		data = {'carName':name};
	}
	$("#carQueryResult").empty();
	$('#loading').show();
	$("#chexingchaxun .carNoQuery").remove();
	$.ajax({
		url:ctx+'vhlQuery.do',
		type:'POST',
		data:data,
		dataTpye:'json',
		success:function(result){
			$('#loading').hide();
			
			if(result.succ){
				for(var i in  result.data){
					var str = '<div class="carStyle"  name="'+result.data[i].modelCode+'" marketDate="'+result.data[i].marketDate+'" seatNum="'+result.data[i].seatNum+
					'"modelCodeType="'+result.data[i].modelCodeType+'" vehicleName="'+result.data[i].vehicleName+
					'"vehiclePrice="'+result.data[i].vehiclePrice+'" configDesc="'+result.data[i].configDesc+'" remark="'+result.data[i].remark+'" displacement="'+result.data[i].displacement+'"><input class="ck1 qwerw" name="odnfbdft"  type="radio" />'+result.data[i].remark+'(参考价'+result.data[i].vehiclePrice+'￥)</div>';
					$("#carQueryResult").append(str);
					
				}
				//处理搜索后的车型
				$('.carStyle').bind('click',function(){
					$(this).find('.qwerw')[0].click();
					//设置参数
					var marketDate=$(this).attr("marketDate");
					var vehicleId=$(this).attr("name");
					var modelCodeType=$(this).attr("modelCodeType");
					var vehicleName=$(this).attr("vehicleName");
					var vehiclePrice=$(this).attr("vehiclePrice");
					var remark=$(this).attr("remark");
					var seatNum=$(this).attr("seatNum");
					var displacement = $(this).attr('displacement');
					$("#vehicleId").val(vehicleId);
					$("#marketDate").val(marketDate);
					$("#modelCodeType").val(modelCodeType);
					$("#vehicleName").val(vehicleName);
					vehicleName1 = vehicleName;
					$("#vehiclePrice").val(vehiclePrice);
					$("#seatNum").val(seatNum);
					$('#displacement').val(displacement);
					//显示信息
					$("#carVersion").val(vehicleName);
					$('input[name=vehicleName]').val(vehicleName);
					$("#carRemark").html(remark);
					/*$('#chexingchaxun').hide();*/
					$('input[name=remark]').val(remark);
					/*$('.carStyle').removeAttr('background');*/
					$('.carStyle').css("background", "");
					$(this).css("background", "#E0E0E0");
					/*$('.ins_main').show();*/
					/*$(".bombtn").show();*/
					
					enabledCalcBtn();
				});
			}
			
			var namenoquery = $("<div  class=\"carNoQuery\" >找不到车型？请通过微信公众号联系客服.<br/><br/><br/></div>");
			$("#chexingchaxun").append(namenoquery);
			
			$('#loading').hide();
		}
	});
}
//初始化选择车型的样式
function initCarQuery(){
	//初始化前，清理下
	$("#chexingchaxun").empty();
	
	var cxcxContainer = $("#chexingchaxun");
	var head = $("<div class=\"head\"></div>");
	var back = $("<div class=\"back lt\" id=\"back2\"></div>");
	back.click(function(){
		cxcxContainer.hide();
		cxcxContainer.empty();
		$('.ins_main').show();
		$(".bombtn").show();
		$('#js_calc_price').show();
		$('#back1').show();
		$('.ins_car').hide();
	});
	head.append(back);
	var title = $("<p class=\"title\">选择车型</p>");
	head.append(title);
	cxcxContainer.append(head);
	//把loading放进去
	var loadding = $("<div id=\"loading\" class=\"loading\"><div ><img src=\"./images/status_loading.gif\" /></div><div>努力加载中...</div></div>");
	cxcxContainer.append(loadding);
	//把查询结果的div放进去
	var result = $("<div id=\"carQueryResult\"></div>");
	cxcxContainer.append(result);
	
}
$(function(){
	disbaledCalcBtn(); //默认禁用

	//续保问题信息提示
	if(xbmsg){
		$("#messageDialog .tip").html(xbmsg);
		tipfun($("#messageDialog"));
		return;
	}
	
    if(isResourceVehicleDTO!=''){
		if(!ticSinp && !vicSinp ){
			$("#messageDialog .tip").html("保险期限未能自动获取到，请核实调整");
			tipfun($("#messageDialog"));
		}
		if(resourceVehicleCvrg.length == 2 || resourceVehicleCvrg == '' ){
			$("#messageDialog .tip").html("上年险别未能自动获取到，请核实调整");
			tipfun($("#messageDialog"));
		}
		if( (resourceVehicleCvrg == ''|| resourceVehicleCvrg.length ==2) && !ticSinp && !vicSinp ){
			$("#messageDialog .tip").html("保险期限和上年险别未能自动获取到，请核实调整");
			tipfun($("#messageDialog"));
			return;
		}
	}
    
   /* $('#carRemark').click(function(){
    	//验证车架号
		var carFrameTest = /^[0-9,A-H,J-N,P,R-Z,a-h,j-n,p,r-z]{17}$/;
		var bool1 = $('#carFrame').val().indexOf("*");
		if(!$('#carFrame').val()){
			$('#carFrame').testRemind("请输入车架号");
			$('#carFrame').focus();
			return false;
		}else if($('#carFrame').val().length != 17){
			$('#carFrame').testRemind("车架号必须为17位");
			$('#carFrame').focus();
			return false;
		}else if(!carFrameTest.test($('#carFrame').val()) && -1 == bool1){
			$('#carFrame').testRemind("车架号不能含有IOQ字母");
			$('#carFrame').focus();
			return false;
		}
		//隐藏按钮
		$('.ins_car').hide();
		//关闭本来存在的加载
		$('#loading').hide();
    	//清空车型内容
		$("#carQueryResult").html('');
		//关闭主界面
		$('.ins_main').hide();
		//显示车型查询界面
		$('#chexingchaxun').show();
		var vehicleFrameNo = $('#carFrame').val();
		//显示根据车架号查询的头
		$('#carOrCarFrame').show();
		$('#carOrCarFrame').html('根据车架号<b>'+vehicleFrameNo+'</b>查询车型');
		//应该车型信息查询的输入框
		$('#vehicleNameQuery').hide();
		//开始加载
		$('#loading').show();	
		//隐藏根据车架号找不到车型
		$("#chexingchaxuan .carNoQuery").hide();
		
		initCarQuery();
		
		carQuery(vehicleFrameNo);
		
    });*/
});


function disbaledCalcBtn(){
	$("#js_calc_price1").removeClass("bc2");
	$("#js_calc_price1").addClass("disabledbtn");
}
function enabledCalcBtn(){
	$("#js_calc_price1").removeClass("disabledbtn");
	$("#js_calc_price1").addClass("bc2");
	
}

$(function(){
	//投保人  被保人处理
	$('#insureSameAsOwner').click(function(){//被保人
		if($(this).is(':checked')){//同车主
			$('#insureNoSameAsOwner').hide();
		}
		else{//不同车主
			$('#insureNoSameAsOwner').show();
			//updateKeyBoard();
		}
	});
	
	$('#appSameAsOwner').click(function(){//投保人
		if($(this).is(':checked')){//同车主
			$('#appNoSameAsOwner').hide();
		}
		else{//不同车主
			$('#appNoSameAsOwner').show();
			//updateKeyBoard();
		}
	});
	
	//加载投保人被保人信息
	$.post(ctx+"selectsByInquiryId.do", {'inquiryId':iiquiryId},function(data){
		if(data.succ){
			var obj = data.data;
			if(null != obj && '' != obj && '[]' != obj){
				$.each(obj, function(n, v){
					if('1' == v.customerType && !'1' == v.isVhlOwner){//投保人并不同车主
						$('#appSameAsOwner').removeAttr('checked');
						$('#appNoSameAsOwnerName').val(v.customerName);
						$('#appNoSameAsOwnerCard').val(v.customerCardId);
						//change($('#appNoSameAsOwnerCard'));
						$('#appNoSameAsCertfCdeType').val(v.certfCdeType);
						appNoSameAsCertfCdeType = v.certfCdeType;
						$('#appNoSameAsOwner').show();
						//updateKeyBoard();
					}
					else if('2' == v.customerType && !'1' == v.isVhlOwner){//被保人并不同车主
						$('#insureSameAsOwner').removeAttr('checked');
						$('#insureNoSameAsOwnerName').val(v.customerName);
						$('#insureNoSameAsOwnerCard').val(v.customerCardId);
						//change($('#insureNoSameAsOwnerCard'));
						$('#insNoSameAsCertfCdeType').val(v.certfCdeType);
						insNoSameAsCertfCdeType = v.certfCdeType;
						$('#insureNoSameAsOwner').show();
						//updateKeyBoard();
					}
				});
				
			}
		}
	},'json');
	
	$('#delIsResourceVehicleDTO').click(function(){
		$('#isResourceVehicleDTO').val("0");
		//把发动机号，车架号 ，身份证号，车主姓名全部清空
		//注意 ：发动机号，车架号 ，身份证号，车主姓名的校验要改 有些用*号代替了   全部拿到后端验证
		$('#carEngine,#carName,#cardNumber,#carFrame,#carVersion,#carRemark').val('');
		$('#tciSign #vciSign').attr('checked','checked');
		//--------起保时间
		var vciStartDate = new Date();
		var vciStartDate2 = vciStartDate.dateAdd(1);
		var vciEndDate = vciStartDate.nextYear();
		$('#vciStartDate').val(vciStartDate2.format('yyyy-MM-dd'));
		$('#vciEndDate').val(vciEndDate.format('yyyy-MM-dd'));
		var tciStartDate = new Date();
		var tciStartDate2 = tciStartDate.dateAdd(1);
		var tciEndDate = tciStartDate.nextYear();
		$('#tciStartDate').val(tciStartDate2.format('yyyy-MM-dd'));
		$('#tciEndDate').val(tciEndDate.format('yyyy-MM-dd'));
		//--------
		hideRisk();
		showRisk();
		$('#carEngine,#carFrame').change();
		$("#initDate").val('');
		$('#delIsResourceVehicleDTO2').remove();
		$('#pre_carName, #pre_cardNumber').show();
		$('#pre_carName, #pre_certfCdeType').show();
		$('#carFrame,#carEngine').removeAttr('disabled');
		$('#ocr_div').show();
		$(">div",$('#filePicker')).eq(1).css({'left':$('.webuploader-pick').offset().left, 'top':$('.webuploader-pick').offset().top, 'width':$('.webuploader-pick').width()+'px', 'height':$('.webuploader-pick').height()+'px'});
		
		//TODO  修改发动机号，车架号 ，身份证号，车主姓名验证
	});
});