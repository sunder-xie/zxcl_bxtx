//商业险点击事件
$('#vciSign').click(function(){
	var clickEnt = document.getElementById('vciSign').checked;
	if(clickEnt){//选中商业险
		//基本险
		$('.ins_risk').attr('checked',true);
		$('.ins_risk').attr('disabled',false);
		$('.ins_risk').css('background-color','');
		$('#daoqiangxiancheckbox').attr('checked',false);
		//附加险
		$('.damages_base').attr('disabled',false);
		$('.damages_base').css('background-color','');
		//基本不计免赔
		$('#base_anyExcess').attr('checked',true);
		$('#base_anyExcess').attr("disabled",false);
		$('#base_anyExcess').css('background-color','');
		//打开
		$('.risk').show();
	}else{//去掉商业险
		//基本险
		$('.ins_risk').attr('checked',false);
		$('.ins_risk').attr('disabled',true);
		$('.ins_risk').css('background-color','#D9D9D9');
		//附加险
		$('.damages_base').attr('checked',false);
		$('.damages_base').attr('disabled',true);
		$('.damages_base').css('background-color','#D9D9D9');
		//不计免赔
		$('.bjmp').attr('checked',false);
		$('.bjmp').attr("disabled",true);
		$('.bjmp').css('background-color','#D9D9D9');
		//隐藏
		$('.risk').hide();
	}
	coverage();
});
//附加险点击事件
$('.damages_base').click(function(e){
	subjoinControl(e.toElement);
});
//附加险不计免赔控制
function subjoinControl(e){
		if(document.getElementById(e.id).checked){
			if('scratch1' == e.id || 'burning1' == e.id || 'wade1' == e.id){//划痕险，自然险，涉水险为附加险
				$('#added_anyExcess').attr("disabled",false);
				$('#added_anyExcess').attr('checked',true);
				$('#added_anyExcess').css('background-color','');
			}
		}else{			
			var count = false;
			$.each($('.damages_base'), function(){
				if(document.getElementById(this.id).checked && ('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id)){//附加险附加险选中
					count = true;
				}
			});
			if(!count){
				$('#added_anyExcess').attr("disabled",true);
				$('#added_anyExcess').attr('checked',false);
				$('#added_anyExcess').css('background-color','#D9D9D9');
			}
		}
	coverage();
}
//基本险点击事件
$('.ins_risk').click(function(e){
	basicControl(e.toElement);
});
//基本险不计免赔控制
function basicControl(e){
	if(document.getElementById(e.id).checked){
		$('#base_anyExcess').attr('checked',true);
    	$('#vciSign').attr('checked',true);
		//去掉车损险则不能投附加险
		if('cheshuncheckbox' == e.id){
			$('.damages_base').attr('disabled',false);
			$('.damages_base').css('background-color','');
			$('#added_anyExcess').attr("disabled",false);
			$('#added_anyExcess').css('background-color','');
		}
	}else{
		var count = false;
		$.each($('.ins_risk'), function(){
			if(document.getElementById(this.id).checked){
				count = true;
			}
		});
		if(!count){
			$('#base_anyExcess').attr('checked',false);
			$('#base_anyExcess').attr('disabled',true);
			$('#base_anyExcess').css('background-color','#D9D9D9');
			
			$('.ins_risk').attr('disabled',true);
			$('.ins_risk').css('background-color','#D9D9D9');
			
			$('#vciSign').attr('checked',false);
			//隐藏
			$('.risk').hide();
		}
		//去掉车损险则不能投附加险
		if('cheshuncheckbox' == e.id){
			//附加险
			$('.damages_base').attr('disabled',true);
			$('.damages_base').attr('checked',false);
			$('.damages_base').css('background-color','#D9D9D9');
			//附加险不计免赔
			$('#added_anyExcess').attr("disabled",true);
			$('#added_anyExcess').attr('checked',false);
			$('#added_anyExcess').css('background-color','#D9D9D9');
		}
	}
	coverage();
}
//保额加载
function coverage(){
	$.each($('.riskCheck'), function(){
		if(document.getElementsByName(this.id)[0].checked){
			$(this).attr('hidden',false);
		}else{
			$(this).attr('hidden',true);
		}
	});
}
$(function(){
	//根据询价单号进来的数据
	if(parseInt(signp)){
		//过户日期判断
		if('1' == transferSign){//过户
			$('#js_driver').attr('checked',true);
			$('#js_driver2').attr('checked',false);
		}else{//未过户
			$('#js_driver2').attr('checked',true);
			$('#js_driver').attr('checked',false);
		}
		//交强险是否选中
		var tciSign=parseInt(inquiry_tciSignp)?true:false;
		$('#tciSign').attr('checked',tciSign);
		//商业险是否选中
		var vciSign=parseInt(inquiry_vciSignp)?true:false;
		$('#vciSign').attr('checked',vciSign);
		
		var coverageItems=$.parseJSON(coverageItemsp);
		//隐藏险别
		if(!vciSign){
			//隐藏
			$('.risk').hide();
		}else{
			//去除所有的险别已选中的
			$(".ins_risk").attr("checked",false);
			$('.damages_base').attr('checked',false);
			$('.bjmp').attr('checked',false);
			//基本不计免赔
			var base_anyExcess = false;
			//附加不计免赔
			var added_anyExcess = false;
			//遍历险种
			for(var o in coverageItems){
				//车损险
				if('030006' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#cheshuncheckbox').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						base_anyExcess = true;
					}
				}
				//三者险
				if("030018" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#sanzhexian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						base_anyExcess = true;
					}
					$('#three option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//司机险
				if("030001" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#shijixian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						base_anyExcess = true;
					}
					$('#driver option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//乘客险
				if("030009" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#chenkexian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						base_anyExcess = true;
					}
					$('#passenger option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//盗抢险
				if("030061" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#daoqiangxiancheckbox').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						base_anyExcess = true;
					}
				}
				//玻璃险
				if("030004" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#glasses').attr('checked',true);
					$('#glass option[value='+coverageItems[o].glsType+']').attr('selected','selected');
				}
				//划痕险
				if("032601" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#scratch1').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						added_anyExcess = true;
					}
					$('#scratch option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//自燃险
				if("030012" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#burning1').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						added_anyExcess = true;
					}
				}
				//涉水险
				if("032608" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#wade1').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						added_anyExcess = true;
					}
				}
				//指定修理厂
				if("032618" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#repair').attr('checked',true);
				}
				//无法找到第三方险
				if("033008" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#odnft').attr('checked',true);
				}
			}
			if(base_anyExcess){
				$('#base_anyExcess').attr('checked',true);
			}
			if(added_anyExcess){
				$('#added_anyExcess').attr("disabled",false);
				$('#added_anyExcess').attr('checked',true);
			}
			var count = false;
			$.each($('.damages_base'), function(){
				if(document.getElementById(this.id).checked && ('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id)){
					count = true;
				}
			});
			if(!count){
				$('#added_anyExcess').attr("disabled",true);
				$('#added_anyExcess').attr('checked',false);
				$('#added_anyExcess').css('background-color','#D9D9D9');
			}
		}
	}
	
	//险别信息判断（续保是传值）
	if(xubao){
		var resourceVehicleCvrgList =  eval('('+resourceVehicleCvrg+')');
		if(resourceVehicleCvrg && resourceVehicleCvrgList.length > 0){		
			$('#tciSign').attr('checked',true);
			$('#vciSign').attr('checked',true);
			$('.ins_risk').attr('disabled',false);
			$('.ins_risk').css('background-color','');
			$('.risk').show();
			
			//去除所有的险别已选中的
			$(".ins_risk").attr("checked",false);
			$('.damages_base').attr('checked',false);
			$('.bjmp').attr('checked',false);
			//基本不计免赔
			var base_anyExcess = false;
			//附加不计免赔
			var added_anyExcess = false;
			for(var o in resourceVehicleCvrgList){
					var obj = resourceVehicleCvrgList[o];
					//车损险
					if('030006' == obj.cvrgId){
						$('#cheshuncheckbox').attr('checked',true);
						$('.damages_base').attr('disabled',false);
						$('.damages_base').css('background-color','');
						if('1' == obj.excldDeductible){
							base_anyExcess = true;
						}
					}
					//三者险
					if("030018" == obj.cvrgId){
						$('#sanzhexian').attr('checked',true);
						if('1' == obj.excldDeductible){
							base_anyExcess = true;
						}
						$('#three option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//司机险
					if("030001" == obj.cvrgId){
						$('#shijixian').attr('checked',true);
						if('1' == obj.excldDeductible){
							base_anyExcess = true;
						}
						$('#driver option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//乘客险
					if("030009" == obj.cvrgId){
						$('#chenkexian').attr('checked',true);
						if('1' == obj.excldDeductible){
							base_anyExcess = true;
						}
						$('#passenger option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//盗抢险
					if("030061" == obj.cvrgId){
						$('#daoqiangxiancheckbox').attr('checked',true);
						if('1' == obj.excldDeductible){
							base_anyExcess = true;
						}
					}
					//玻璃险
					if("030004" == obj.cvrgId){
						$('#glasses').attr('checked',true);
						$('#glass option[value='+obj.glsType+']').attr('selected','selected');
					}
					//划痕险
					if("032601" == obj.cvrgId){
						$('#scratch1').attr('checked',true);
						if('1' == obj.excldDeductible){
							added_anyExcess = true;
						}
						$('#scratch option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//自燃险
					if("030012" == obj.cvrgId){
						$('#burning1').attr('checked',true);
						if('1' == obj.excldDeductible){
							added_anyExcess = true;
						}
					}
					//涉水险
					if("032608" == obj.cvrgId){
						$('#wade1').attr('checked',true);
						if('1' == obj.excldDeductible){
							added_anyExcess = true;
						}
					}
					//指定修理厂
					if("032618" == obj.cvrgId){
						$('#repair').attr('checked',true);
					}
					//无法找到第三方险
					if("033008" == obj.cvrgId){
						$('#odnft').attr('checked',true);
					}
				}
				if(base_anyExcess){
					$('#base_anyExcess').attr('checked',true);
					$('#base_anyExcess').attr("disabled",false);
					$('#base_anyExcess').css('background-color','');
				}
				if(added_anyExcess){
					$('#added_anyExcess').attr('checked',true);
					$('#added_anyExcess').attr("disabled",false);
					$('#added_anyExcess').css('background-color','');
				}
				var count = false;
				$.each($('.damages_base'), function(){
					if(document.getElementById(this.id).checked && ('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id)){
						count = true;
					}
				});
				if(!count){
					$('#added_anyExcess').attr("disabled",true);
					$('#added_anyExcess').attr('checked',false);
					$('#added_anyExcess').css('background-color','#D9D9D9');
				}
			}
		}
	
	//进页面的时候判断是否加载
	coverage();
	
	
	//投保人  被保人处理
	$('#insureSameAsOwner').click(function(){//被保人
		if($(this).is(':checked')){//同车主
			$('#insureNoSameAsOwner').slideUp();
			$('#insureNoSameAsOwnerName,#insureNoSameAsOwnerCard').removeAttr('required');
		}
		else{//不同车主
			$('#insureNoSameAsOwner').slideDown();
			$('#insureNoSameAsOwnerName,#insureNoSameAsOwnerCard').attr('required','');
		}
	});
	
	$('#appSameAsOwner').click(function(){//投保人
		if($(this).is(':checked')){//同车主
			$('#appNoSameAsOwner').slideUp();
			$('#appNoSameAsOwnerName,#appNoSameAsOwnerCard').removeAttr('required');
		}
		else{//不同车主
			$('#appNoSameAsOwner').slideDown();
			$('#appNoSameAsOwnerName,#appNoSameAsOwnerCard').attr('required','');
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
						$('#appNoSameAsOwner').show();
					}
					else if('2' == v.customerType && !'1' == v.isVhlOwner){//被保人并不同车主
						$('#insureSameAsOwner').removeAttr('checked');
						$('#insureNoSameAsOwnerName').val(v.customerName);
						$('#insureNoSameAsOwnerCard').val(v.customerCardId);
						$('#insureNoSameAsOwner').show();
					}
				});
			}
		}
	},'json');
});
