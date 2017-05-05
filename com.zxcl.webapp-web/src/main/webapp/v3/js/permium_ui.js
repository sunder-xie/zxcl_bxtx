
changeMenuTab(1);
//alert($("#inquiryId").val())
/* var tpicCount = true;  */
var _repeatSubmit = 0;
var canNotUpdIns = ['PAIC', 'FDCP', 'TPBX', 'YGBX', 'CPIC','PICCHTTP'];
var recCertType = '';
var voteCertType = '';
var canCheckAddress = ['PAIC','PICCHTTP'];
$(function(){
	$(".state_info_new").hide();
	$('#state').click(function(){
		$(".state_info_new").show();
	});
});
//保留省份和城市的原始信息
var owner=new Object();
owner.cityVal=$("#ownerCity").find("option:selected").val();
owner.optionsHtml=$("#ownerCity").children().clone();
owner.proVal=$("#ownerProvince").find("option:selected").val();
owner.ownerCertTypeVal=$("#ownerCertType").find("option:selected").val();
//配送方式
function isDispatchType(value){
	if('1' == disPanDuan){	
		//(0-配送 1-自取)
		if(0==value){
			$('#js_insurance').attr('checked','checked');
			isOwner('js_insurance','dis');
			$(".detailed_address3_text").text("配送地址：");
		}else if(1==value){
			$(".detailed_address3_text").text("取件地址：");
		}
	}
}
//处理是否同车主
function isOwner(checkId,name){
	if('js_insurance' == checkId && '0' != $('#manage_dispatch_select').val()){
		$('#manage_dispatch_select option[value=0]').attr('selected','selected');
	}
	var check=$("#"+checkId+"").attr("checked");
	if(check=="checked"){
		$("input[name="+name+"Name]").val($("input[name=ownerName]").val());
		$("#"+name+"CertType").val($("#ownerCertType").find("option:selected").val());
		$("input[name="+name+"CertNo]").val($("input[name=ownerCertNo]").val());
		change($("input[name="+name+"CertNo]"));
		$("input[name="+name+"Phone]").val($("input[name=ownerPhone]").val());
		change($("input[name="+name+"Phone]"));
		$("#"+name+"Province").val($("#ownerProvince").find("option:selected").val());
		var disabled=$("#ownerCity").attr("disabled");
		if(disabled!="disabled"){
			$("#"+name+"City").attr("disabled",false);
			$("#"+name+"City").html("");
			var options=$("#ownerCity").children().clone();
			$("#"+name+"City").append(options);
		}else{
			$("#"+name+"City").attr("disabled","disabled");
			$("#"+name+"City").html("");
		}
		$("#"+name+"City").val($("#ownerCity").find("option:selected").val());
		$("input[name="+name+"Address]").val($("input[name=ownerAddress]").val());
		change($("input[name="+name+"Address]"));
	}else{
		$('#manage_dispatch_select').val(0);
		//处理某些保险公司非同车主的时候要同步姓名、身份证号
		var _canNotUpdIns = $("input[name=insId]").val();
		if(canNotUpdIns.indexOf(_canNotUpdIns) > -1){
			$("input[name="+name+"Name]").val($("input[name=ownerName]").val());
			change($("input[name="+name+"Name]"));
			$("#"+name+"CertType").val($("#ownerCertType").find("option:selected").val());
			$("input[name="+name+"CertNo]").val($("input[name=ownerCertNo]").val());
			change($("input[name="+name+"CertNo]"));
		}else{
			$("input[name="+name+"Name]").val("");
			change($("input[name="+name+"Name]"));
			$("input[name="+name+"CertType]").val("");
			$("input[name="+name+"CertNo]").val("");
			change($("input[name="+name+"CertNo]"));
		}
		
		$("input[name="+name+"Phone]").val("");
		change($("input[name="+name+"Phone]"));
		$("#"+name+"CertType").val(owner.ownerCertTypeVal);
		$("#"+name+"Province").val(owner.proVal);
		change($("#"+name+"Province"));
		if(typeof(owner.cityVal)=="undefined"){
			$("#"+name+"City").html("");
			$("#"+name+"City").attr("disabled","disabled");
		}else{
			$("#"+name+"City").html("");
			$("#"+name+"City").append(owner.optionsHtml.clone());
			$("#"+name+"City").val(owner.cityVal);
		}
		$("input[name="+name+"Address]").val("");
	}
	
	$("input[name="+name+"CertNo]").change();
	$("input[name="+name+"Phone]").change();
}	
//是否阅读免责声明 
$("#state_result_yes,.close_img").on("click",function(){
	$(".state_info_new").hide();
	
});

$("#state").on("click",function(){
	$(".state_info_new").animate({scrollTop:'0px'}, 10);
	$(".state_info_new").show();
});
$(".state").click(function(){
	if($(".state").attr('checked') == 'checked'){
		$(".btn_no").attr("class","btn");
	}else{
		$(".btn").attr("class","btn_no");
	}
});

//处理城市信息
function getCityInfo(province,cityId){
	var code=$("#"+province+"").find("option:selected").val()
	$.ajax({ 
		url: ctx+"getCityByShe.do",
		type: "POST",
		dataType:"json",
		async:true,
		data:"code="+code,
		success: function(result){
			if(result.succ){
				//清空城市
				$("#"+cityId+"").html("");
				//获取省被选中的节点内容
				var provinces=$("#"+province+" option:selected").html();
				//遍历获取到城市信息
				if(result.data.length!=0){
					for(var i=0;i<result.data.length;i++){
						//设置城市的下拉框为启用
						$("#"+cityId+"").attr("disabled",false);
						var cityName="${city.name}";
						//向城市的下拉框添加子节点
						if(cityName==result.data[i].name){
							$("#"+cityId+"").append("<option selected='selected' value='"+result.data[i].code+"'>"+result.data[i].name+"</option>");
						}else{
							$("#"+cityId+"").append("<option value='"+result.data[i].code+"'>"+result.data[i].name+"</option>");
						}
					}
				}else{
					//设置城市的下拉框为禁用
					$("#"+cityId+"").attr("disabled",true);
				}
			}
		}
	});
}

//改变了证件类型
function changeCardType(man){
	//选着了身份证
	if($("#"+man+"CertType option:selected").val()=="1"){
		$("input[name="+man+"CertNo]").attr("pattern","(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)");
	}else{
		$("input[name="+man+"CertNo]").attr("pattern","");		
	}
}

//支付校验
function payCheck(type){
	$.ajax({
		type:'POST',
		url:ctx+'payDateCheck.do',
		data:{'inquiryId':inquiryId,'insId':insId},
		dataType:'json',
		async:false,
		success:function(result){
			if('1' == result){
				dialogConfirm("距离保单起保日期不到半小时，此时支付可能无法生成保单","继续支付",function(){
					payCheck('2');
				});
			}else if('2' == result){
				dialogConfirm("报价单已过期，需重新报价","重新报价",function(){
					window.location.href=ctx+"insuranceType.do?inquiryId="+inquiryId+"&useType=1&haveOrder=1";
				});
			}
			if('0' == result || '2' == type){
				$("#info_msg").show();
				var orderId=$("input[name=orderId]").val();
				$.post(ctx+"query_ins_status.do",{'insId':insId}, function(data){
					if(data.succ){
						var url = '';
						if('AICS' == insId){
							 url=ctx+"allinpayContro.do?orderId="+orderId+"&insId="+insId;
						 }else if('JTIC' == insId|| 'CICP' == insId ||'PAIC' == insId || 'TPBX' == insId || 'PICC' == insId){
							url = ctx + 'payController.do?quoteId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId;
						 }else if('FDCP' == insId){
							url=ctx + "fdcp/pay.do?quotaId="+ quotaId +"&insId="+insId+"&inquiryId="+ inquiryId +"&orderId="+orderId;
						 }else if('HHBX' == insId){
							url = ctx + "hhbx/pay.do?quotaId="+ quotaId +"&insId="+insId+"&inquiryId="+ inquiryId +"&orderId="+orderId;
				 		 }else if('PINGANHTTP' == insId){
							url=ctx+"pinganHttp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
				 		 }else{
							url=ctx+'pay.do?quotaId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId;
						 }
						window.location.href = url;
					}
					else{
						dialog(data.msg);
					}
				},'json');
			}
		}
	});
	
};

//核保
function toVote(){
	if(_repeatSubmit != 0 || _repeatSubmit != '0'){
		return;
	}
	_repeatSubmit = 1;
	var frm = $("#orderForm");
	loadfun("提交核保中.....");
	$.ajax({
		url:frm.attr("action"),
		type: "POST",
		dataType:"json",
		async:true,
		data:frm.serialize(),
		success: function(result){
			$("#submit1").attr("disabled",false);
			loadclosefun();
			if(result.succ){
				
				if(result.data.quotaType=='M'&&result.data.returnCode=='2'){
					dialog("请等待人工核保..","确定",function(){
						window.location.href=ctx+'infoQuery.do';
					});
				}
				if(result.data.quotaType=='A'){
					//平安flowid过期失效
					if(result.data.returnCode=='err'){
						dialogConfirm("该单已过期失效，请重新报价","重新报价",function(){
							window.location.href=ctx+"insuranceType.do?inquiryId=${inquiryId}&useType=1&haveOrder=1";
 						},"取消",function(){
 							window.location.href=ctx+"list2.do";
 						});
					}
					//人工核保
					if(result.data.returnCode=='2'){
						dialog("请等待人工核保..","确定",function(){
							window.location.href=ctx+'infoQuery.do';
						});
					}
					//核保失败:核保异常
					if(result.data.returnCode=='3'){
						dialog(result.data.returnMsg, "确定",function(){
							window.location.href=ctx+'infoQuery.do';
						});
					}
					//核保成功
					if(result.data.returnCode=='4'){
 						$("input[name=orderId]").val(result.data.orderId);
 						$("input[name=insId]").val(result.data.insId);
 						if('3' == flowControl){
 	 						dialogConfirm("核保通过请立即支付","支付",function(){
 	 							payCheck('1');
 	 						},"取消",function(){
 	 							window.location.href=ctx+"list2.do";
 	 						});
 						}else{
 							dialog('核保成功',"确定",function(){
 								window.location.href=ctx+'infoQuery.do';
 							});
 						}
					}
					//核保中------阳光
					if(result.data.returnCode=='13'){
						dialog('核保中，请稍后查看',"确定",function(){
							window.location.href=ctx+'infoQuery.do';
						});
					}
					//核保成功-----太平
					/* if(result.data.returnCode=='0000'){
						$("input[name=orderId]").val(result.data.orderId);
 					$("input[name=insId]").val(result.data.insId);
 					$("#pay_show").show();
					} */
				}
			}else{
				dialog("核保失败", "确定",function(){
					window.location.href=ctx+'infoQuery.do';
				});
			}
		}
	});
}

	
$(function(){
	
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
	
	//附件折叠
	$(".accordion #p").addClass("active");  
    $(".accordion #wrapper").slideUp("fast"); 
    $(".accordion #p").click(function(){  
	    $(this).next("#wrapper").slideToggle("fast").siblings("#wrapper:disabled").slideUp("fast");  
	    $(this).toggleClass("active");  
	    $(this).siblings("#p").removeClass("active");  
	});
	//如果是自动报价不可选
	var isShow=$("#quotaType").val();
	if(isShow=="A"){
		$(".dispatchType").attr("disabled",true);
	}
	//回退	
	$(".back").on("click",function(){
		//保存信息
		//被保人
		if($("#js_recognizee").attr("checked")){
			isOwner('js_recognizee','rec');
		}
		//投保人
		if($("#js_applicant").attr("checked")){
			isOwner('js_applicant','vote');
		}
		//保单配送
		if($("#js_insurance").attr("checked")){
			isOwner('js_insurance','dis');
		}
		//保存订单基础信息
		var data=$("#orderForm").serialize();
		window.location.href=ctx+"orderBaseInfoBack.do?"+data;
// 		window.location.href="${ctx}toquota.do?inquiryId=${inquiryId}&useType=Q";
	});
	$("#select_result").on("click",function(){
		$(this).hide();
		window.location.href=ctx+"infoQuery.do";
	});
	
	$("#ins_result_yes").on("click",function(){
		$("#select_result").hide();
	});
	
	$(".load").hide();
	
// 	去除用户地址和手机号
	 $("#detailed_address1").val($.cookie("addrss"+inquiryId));
	 $("#phone_number1").val($.cookie("phone"+inquiryId));
$("#submit1").on("click",function(){
	for(var o in canCheckAddress){
		if(insId == canCheckAddress[o] && plate_no_control){
			console.info(plate_no_control);
			var strs = plate_no_control.split(',');
			var bool = true;
			for(var i in strs){
				if(strs[i] == plate1){// 匹配如“川*”
					console.info(strs[i]);
					bool = false;
				}else if(strs[i] == plate2){// 匹配如“川A”
					console.info(strs[i]);
					bool = false;
				}
			}
			if(bool){
				dialog('不支持异地车承保', "确定",function(){
					window.location.href=ctx+'infoQuery.do';
				});
				return;
			}
		}
	}
	var addrss=$("#detailed_address1").val();
	var  phone=$("#phone_number1").val();
	$.cookie("addrss"+inquiryId, addrss, { expires: 30 });
	$.cookie("phone"+inquiryId, phone, { expires: 30 });

	//提交核保表单:组织数据
	//被保人
	if($("#js_recognizee").attr("checked")){
		isOwner('js_recognizee','rec');
	}
	//投保人
	if($("#js_applicant").attr("checked")){
		isOwner('js_applicant','vote');
	}
	//保单配送
	
	if($("input[name=dispatchType]:checked").val() == "1"){
		isOwner('js_insurance','dis');
	}
	//alert($("input[name=dispatchType]:checked").val());
	$("#orderForm").submit();  
});

$("#orderForm").html5Validate(function() {
	

	
	if(insId == "ACIC"){
		getIsUploadVideoMaterialDone(function(){
			toVote();
		});
	}else
		toVote();
	
	return false;
}, {
	 validate:function(){
			var organizationCodeTest = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
			var businessRegistrationNumberTest = /^\d{15}$/;
		 	//车主证件号验证
		 	var ownerCertType = $('#ownerCertType').val();
			var ownerCertNo=$("input[name=ownerCertNo]").val();
			if(!ownerCertNo){
				var str = '';
				if('1' == ownerCertType){
					str = '身份证号';
				}else if('4' == ownerCertType){
					str = '组织机构代码';
				}else if('5' == ownerCertType){
					str = '工商注册号码';
				}
				$("#cardNumber1").testRemind("您尚未输入"+str);
				$("#cardNumber1").focus();
				return false;
			}
			if('1' == ownerCertType){
				if(!checkIdCard(ownerCertNo)){
					$("#cardNumber1").testRemind("您输入的身份证号格式不正确");
					$("#cardNumber1").focus();
					return false;
				}
			}else if('4' == ownerCertType){
				if(!organizationCodeTest.test(ownerCertNo)){
					$("#cardNumber1").testRemind("您输入的组织机构代码格式不正确");
					$("#cardNumber1").focus();
					return false;
				}
			}else if('5' == ownerCertType){
				if(!businessRegistrationNumberTest.test(ownerCertNo)){
					$("#cardNumber1").testRemind("您输入的工商注册号码格式不正确");
					$("#cardNumber1").focus();
					return false;
				}
			}
			
			//被保人证件号验证
			var recCertType = $('#recCertType').val();
			var recCertNo=$("input[name=recCertNo]").val();
			if(!recCertNo){
				var str = '';
				if('1' == recCertType){
					str = '身份证号';
				}else if('4' == recCertType){
					str = '组织机构代码';
				}else if('5' == recCertType){
					str = '工商注册号码';
				}
				$("#cardNumber2").testRemind("您尚未输入"+str);
				$("#cardNumber2").focus();
				return false;
			}
			if('1' == recCertType){
				if(!checkIdCard(recCertNo)){
					$("#cardNumber2").testRemind("您输入的身份证号格式不正确");
					$("#cardNumber2").focus();
					return false;
				}
			}else if('4' == recCertType){
				if(!organizationCodeTest.test(recCertNo)){
					$("#cardNumber2").testRemind("您输入的组织机构代码格式不正确");
					$("#cardNumber2").focus();
					return false;
				}
			}else if('5' == recCertType){
				if(!businessRegistrationNumberTest.test(recCertNo)){
					$("#cardNumber2").testRemind("您输入的工商注册号码格式不正确");
					$("#cardNumber2").focus();
					return false;
				}
			}
			
			//投保人人证件号验证
			var voteCertType = $('#voteCertType').val();
			var voteCertNo=$("input[name=voteCertNo]").val();
			if(!voteCertNo){
				var str = '';
				if('1' == voteCertType){
					str = '身份证号';
				}else if('4' == voteCertType){
					str = '组织机构代码';
				}else if('5' == voteCertType){
					str = '工商注册号码';
				}
				$("#cardNumber3").testRemind("您尚未输入"+str);
				$("#cardNumber3").focus();
				return false;
			}
			if('1' == voteCertType){ 
				if(!checkIdCard(voteCertNo)){
					$("#cardNumber3").testRemind("您输入的身份证号格式不正确");
					$("#cardNumber3").focus();
					return false;
				}
			}else if('4' == voteCertType){
				if(!organizationCodeTest.test(voteCertNo)){
					$("#cardNumber3").testRemind("您输入的组织机构代码格式不正确");
					$("#cardNumber3").focus();
					return false;
				}
			}else if('5' == voteCertType){
				if(!businessRegistrationNumberTest.test(voteCertNo)){
					$("#cardNumber3").testRemind("您输入的工商注册号码格式不正确");
					$("#cardNumber3").focus();
					return false;
				}
			}	
			
			return true;
		 }
});
})
//回显数据
$(function(){
	
	//投保的时候不允许更改车主信息
	$("input[name=ownerName]").attr("readonly","readonly");
	$("input[name=ownerCertNo]").attr("readonly","readonly");
	//某些保险公司保险的信息，投保人、被保人的姓名、身份证不能修改.
	var _canNotUpdIns = $("input[name=insId]").val();
	if(canNotUpdIns.indexOf(_canNotUpdIns) > -1){
		$("input[name=ownerName]").attr("readonly","readonly");
		$("input[name=ownerCertNo]").attr("readonly","readonly");
		$("input[name=recName]").attr("readonly","readonly");
		$("input[name=recCertNo]").attr("readonly","readonly");
		$("input[name=voteName]").attr("readonly","readonly");
		$("input[name=voteCertNo]").attr("readonly","readonly");
		
		$('#js_applicant').attr('disabled', 'disabled');
		$('#js_applicant').removeAttr('onclick');
		$('#js_recognizee').attr('disabled', 'disabled');
		$('#js_recognizee').removeAttr('onclick');
		$('#cardNumber1,#cardNumber3,#cardNumber2').removeAttr('virtual_keyboard');	
	}
	
	if(parseInt(sign)){
		
		//设置车主信息
		$("#ownerCertType").val(ownerCertType);
		$("#ownerProvince").val(ownerProvince);
		getCityInfo('ownerProvince','ownerCity');
		$("#ownerCity").val(ownerCity);
		
		var flag=0;
		//车主和被保人是否一致
		if(recName != ownerName){
			flag++;
		}
		if(recCertType != ownerCertType){
			flag++;
		}
		if(recCertNo != ownerCertNo){
			flag++;
		}
		if(recPhone != ownerPhone){
			flag++;
		}
		if(recAddress != ownerAddress){
			flag++;
		}
		//不同
		if(flag){
			//设置证件类型
			$("#recCertType").val(recCertType);
			//设置城市信息
			$("#recProvince").val(recProvince);
			getCityInfo('recProvince','recCity');
			$("#recCity").val(recCity);
			//显示
			$("#js_recognizee").attr("checked",false);
			$("#js_recognizee_info").show();
		}
		flag=0;
		//车主和投保人是否一致
		if(voteName != ownerName){
			flag++;
		}
		if(voteCertType != ownerCertType){
			flag++;
		}
		if(voteCertNo != ownerCertNo){
			flag++;
		}
		if(votePhone != ownerPhone){
			flag++;
		}
		if(voteAddress != ownerAddress){
			flag++;
		}
		//不同
		if(flag){
			//设置证件类型
			$("#voteCertType").val(voteCertType);
			//设置城市信息
			$("#voteProvince").val(voteProvince);
			getCityInfo('voteProvince','voteCity');
			$("#voteCity").val(voteCity);
			//先死
			$("#js_applicant").attr("checked",false);
			$("#js_applicant_info").show();
			//updateKeyBoard();
		}
		
		flag=0;
		//车主和配送信息是否一致
		if(disName != ownerName){
			flag++;
		}
		if(disPhone != ownerPhone){
			flag++;
		}
		if(disAddress != ownerAddress){
			flag++;
		}
		//不同
		if(flag){
			//设置城市信息
			$("#disProvince").val(disProvince);
			getCityInfo('disProvince','disCity');
			$("#disCity").val(disCity);
			//先死
			$("#js_insurance").attr("checked",false);
			$("#js_insurance_info").show();
		}
	}
});

$(function(){
	//配送信息模板
	$.post(ctx+"manage_dispatch/list.do",{},function(data){
		if(data.success){
			var str = '<option value="0">请选择</option>';
			var list = data.data;
			if(list.length > 0){
				$.each(data.data,function(n,v){
					str += '<option value="'+v.id+'">'+v.recName+'</option>';
				});
				$('#manage_dispatch_select').html(str);
				$('#manage_dispatch_select_pre').show();
				//updateKeyBoard();
				manage_dispatch_select_change = function(){
					$("#js_insurance").attr("checked",false);
					var id = $('#manage_dispatch_select').val();
					$.post(ctx+"manage_dispatch/detail.do",{'id':id},function(data){
						if(data.success){
							var item = data.data;
							if(null == item){
								return false;
							}
							$('#name').val(item.recName);
							$('#phoneNum').val(item.phone);
							$('#disProvince').val(item.areaCode);
							getCityInfo('disProvince','disCity');
							if(null != item.areaChildCode){
								setTimeout(function(){
									$('#disCity').val(item.areaChildCode);
								},300);
							}
							$('#cardNumber').val(item.address);
						}
					},'json');
				}
			}
		}
	},'json');
	//获取团队所属地址 
// 	default_dispatch();
});

$('#uploadFile_btn').click(function(){
	if(insId == "ACIC"){
		initUploadVideoMaterial();
	}else{
		var init = $('#uploadFile_div_iframe').attr('data-init');
		if(init == 0 || init == '0'){
			$('#uploadFile_div_iframe').attr('data-init', '1');
			$('#uploadFile_div_iframe').attr('src',ctx+'upload/inquiry_index.do?inquiryId='+inquiryId);
		}
		$('#uploadFile_div').show();
	}
});
function closeIframe(){
	$('#uploadFile_div').hide();
}
//加载投保人被保人信息
$.post(ctx+"selectsByInquiryId.do", {'inquiryId':inquiryId},function(data){
	if(data.succ){
		var obj = data.data;
		if(null != obj && '' != obj && '[]' != obj){
			$.each(obj, function(n, v){
				if('2' == v.customerType){//被保人并不同车主
					if(!'1' == v.isVhlOwner){
						$('#js_recognizee').removeAttr('checked');
						$('#car_owner2').val(v.customerName);
						$('#cardNumber2').val(v.customerCardId);
						change($('#cardNumber2'));
						$('#recCertType').val(v.certfCdeType);
						recCertType = v.certfCdeType;
						$('#js_recognizee_info').show();
					}else{
						
					}
				}
				else if('1' == v.customerType){//投保人并不同车主
					if(!'1' == v.isVhlOwner){
						$('#js_applicant').removeAttr('checked');
						$('#car_owner3').val(v.customerName);
						$('#cardNumber3').val(v.customerCardId);
						change($('#cardNumber3'));
						$('#voteCertType').val(v.certfCdeType);
						voteCertType = v.certfCdeType;
						$('#js_applicant_info').show();
					}
					else{
						
					}
				}
			});
		}
	}
},'json');

$(function(){$('.ways').hide();
	if('1' == disPanDuan){			
		$('.waybtn .ck1').click(function(){
			if($(this).index()==0){$('.ways').show();}else{$('.ways').hide();}
		});
	}
});