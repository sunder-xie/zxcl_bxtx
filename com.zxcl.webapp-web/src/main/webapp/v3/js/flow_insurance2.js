//点击不计免赔
function noAnyExcess(id){
	var isOrNo = document.getElementById(id).checked;
	var anyExcess = $('#'+id).attr('anyExcess');
	if('0' == anyExcess && isOrNo){//点击取消
		$('#'+id+'_a').show();
		$('#'+id+'_b').addClass('textchecked');
		$('#'+id+'_b').removeClass('textnochecked');
		$('#'+id).attr('anyExcess','1');
	}else{
		$('#'+id+'_a').hide();
		$('#'+id+'_b').removeClass('textchecked');
		$('#'+id+'_b').addClass('textnochecked');
		$('#'+id).attr('anyExcess','0');
	}
}
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
		//不计免赔
		$('.ins_risk_1 .bjmpys').addClass('textchecked');
		$('.ins_risk_1 .bjmpys').removeClass('textnochecked');
		$('.ins_risk').attr('anyExcess','1');
		noAnyExcess('daoqiangxiancheckbox');
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
		$('.ins_risk .noductcontainer .bjmpys').removeClass('textchecked');
		$('.ins_risk .noductcontainer .bjmpys').addClass('textnochecked');
		$('.ins_risk').attr('anyExcess','0');
		$('.damages_base').attr('anyExcess','0');
		
		$('#three option[value=500000]').attr('selected','selected');
		$('#driver option[value=10000]').attr('selected','selected');
		$('#passenger option[value=10000]').attr('selected','selected');
		$('#glass option[value=1]').attr('selected','selected');
		$('#scratch option[value=2000]').attr('selected','selected');
		noAnyExcess('daoqiangxiancheckbox');
		noAnyExcess('scratch1');
		noAnyExcess('burning1');
		noAnyExcess('wade1');
		//隐藏
		$('.risk').hide();
	}
	coverage();
});
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
//基本险点击事件
$('.ins_risk').click(function(e){
	basicControl(e.toElement);
});
//基本险控制
function basicControl(e){
	if(document.getElementById(e.id).checked){
    	$('#vciSign').attr('checked',true);
		//去掉车损险则不能投附加险
		if('cheshuncheckbox' == e.id){
			$('.damages_base').attr('disabled',false);
			$('.damages_base').css('background-color','');
			$.each($('.damages_base'), function(){
				if('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id){//附加险附加险选中
					noAnyExcess(this.id)
				}
			});
		}
		noAnyExcess(e.id)
	}else{
		var count = false;
		$.each($('.ins_risk'), function(){
			if(document.getElementById(this.id).checked){
				count = true;
			}
		});
		if(!count){
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
			$.each($('.damages_base'), function(){
				if('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id){//附加险附加险选中
					noAnyExcess(this.id)
				}
			});
		}
		noAnyExcess(e.id)
	}
	coverage();
}
//附加险点击事件
$('.damages_base').click(function(e){
	subjoinControl(e.toElement);
});
//附加险控制
function subjoinControl(e){
	if(document.getElementById(e.id).checked){
		if('scratch1' == e.id || 'burning1' == e.id || 'wade1' == e.id){//划痕险，自然险，涉水险为附加险
			noAnyExcess(e.id)
		}
	}else{	
		if('scratch1' == e.id || 'burning1' == e.id || 'wade1' == e.id){//划痕险，自然险，涉水险为附加险
			noAnyExcess(e.id)
		}
	}
	coverage();
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
			$(".ins_risk").attr("anyExcess",'0');
			$('.damages_base').attr('checked',false);
			$('.damages_base').attr('anyExcess','0');
			$('.noductcontainer .bjmpys').removeClass('textchecked');
			$('.noductcontainer .bjmpys').addClass('textnochecked');
			$('.noductcontainer .iconchecked').hide();
			//遍历险种
			for(var o in coverageItems){
				//车损险
				if('030006' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#cheshuncheckbox').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('cheshuncheckbox');
					}
				}
				//三者险
				if("030018" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#sanzhexian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('sanzhexian');
					}
					$('#three option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//司机险
				if("030001" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#shijixian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('shijixian');
					}
					$('#driver option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//乘客险
				if("030009" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#chenkexian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('chenkexian');
					}
					var seatNum3 = $('#seatNum').val()-0;
					var sumLimit = coverageItems[o].sumLimit-0;
					if(0 < seatNum3){						
						$('#passenger option[value='+sumLimit/(seatNum3-1)+']').attr('selected','selected');
					}else{
						$('#passenger option[value=10000]').attr('selected','selected');
					}
				}
				//盗抢险
				if("030061" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#daoqiangxiancheckbox').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('daoqiangxiancheckbox');
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
						noAnyExcess('scratch1');
					}
					$('#scratch option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//自燃险
				if("030012" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#burning1').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('burning1');
					}
				}
				//涉水险
				if("032608" == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#wade1').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('wade1');
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
			//去掉车损险则不能投附加险
			if(!document.getElementById('cheshuncheckbox').checked){
				//附加险
				$('.damages_base').attr('disabled',true);
				$('.damages_base').attr('checked',false);
				$('.damages_base').css('background-color','#D9D9D9');
				$.each($('.damages_base'), function(){
					if('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id){//附加险附加险选中
						noAnyExcess(this.id)
					}
				});
			}
		}
	}
	//险别信息判断（续保是传值）
	if(xubao && resourceVehicleCvrg){
		var resourceVehicleCvrgList =  eval('('+resourceVehicleCvrg+')');
		if(resourceVehicleCvrg && resourceVehicleCvrgList.length > 0){		
			$('#tciSign').attr('checked',true);
			$('#vciSign').attr('checked',true);
			$('.ins_risk').attr('disabled',false);
			$('.ins_risk').css('background-color','');
			$('.risk').show();
			
			//去除所有的险别已选中的
			$(".ins_risk").attr("checked",false);
			$(".ins_risk").attr("anyExcess",'0');
			$('.damages_base').attr('checked',false);
			$('.damages_base').attr('anyExcess','0');
			$('.noductcontainer .bjmpys').removeClass('textchecked');
			$('.noductcontainer .bjmpys').addClass('textnochecked');
			$('.noductcontainer .iconchecked').hide();
			
			for(var o in resourceVehicleCvrgList){
					var obj = resourceVehicleCvrgList[o];
					//车损险
					if('030006' == obj.cvrgId){
						$('#cheshuncheckbox').attr('checked',true);
						$('.damages_base').attr('disabled',false);
						$('.damages_base').css('background-color','');
						if('1' == obj.excldDeductible){
							noAnyExcess('cheshuncheckbox');
						}
					}
					//三者险
					if("030018" == obj.cvrgId){
						$('#sanzhexian').attr('checked',true);
						if('1' == obj.excldDeductible){
							noAnyExcess('sanzhexian');
						}
						$('#three option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//司机险
					if("030001" == obj.cvrgId){
						$('#shijixian').attr('checked',true);
						if('1' == obj.excldDeductible){
							noAnyExcess('shijixian');
						}
						$('#driver option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//乘客险
					if("030009" == obj.cvrgId){
						$('#chenkexian').attr('checked',true);
						if('1' == obj.excldDeductible){
							noAnyExcess('chenkexian');
						}
						var seatNum3 = $('#seatNum').val()-0;
						var sumLimit = obj.gvrgAmount-0;
						if(0 < seatNum3){						
							$('#passenger option[value='+sumLimit/(seatNum3-1)+']').attr('selected','selected');
						}else{
							$('#passenger option[value=10000]').attr('selected','selected');
						}
						/*$('#passenger option[value='+obj.gvrgAmount+']').attr('selected','selected');*/
					}
					//盗抢险
					if("030061" == obj.cvrgId){
						$('#daoqiangxiancheckbox').attr('checked',true);
						if('1' == obj.excldDeductible){
							noAnyExcess('daoqiangxiancheckbox');
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
							noAnyExcess('scratch1');
						}
						$('#scratch option[value='+obj.gvrgAmount+']').attr('selected','selected');
					}
					//自燃险
					if("030012" == obj.cvrgId){
						$('#burning1').attr('checked',true);
						if('1' == obj.excldDeductible){
							noAnyExcess('burning1');
						}
					}
					//涉水险
					if("032608" == obj.cvrgId){
						$('#wade1').attr('checked',true);
						if('1' == obj.excldDeductible){
							noAnyExcess('wade1');
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
				//去掉车损险则不能投附加险
				if(!document.getElementById('cheshuncheckbox').checked){
					//附加险
					$('.damages_base').attr('disabled',true);
					$('.damages_base').attr('checked',false);
					$('.damages_base').css('background-color','#D9D9D9');
					$.each($('.damages_base'), function(){
						if('scratch1' == this.id || 'burning1' == this.id || 'wade1' == this.id){//附加险附加险选中
							noAnyExcess(this.id)
						}
					});
				}
			}
		}
	//进页面的时候判断是否加载
	coverage();
});

