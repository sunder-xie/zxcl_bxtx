//业务类型切换
function chanageBusinessType(){
	$.ajax({
		type:'post',
		url:ctx+'queryBusinessType.do',
		data:{'type':$('#businessTypeId').val(),'teamId':teamId},
		dateType:'json',
		async : false,
		success:function(result){
			if(result.succ){
				var str = [];
				for(var o in result.data){
					var str2 = '';
					if((!usageCode && 'A1' == result.data[o].code) || (usageCode && usageCode == result.data[o].code)){
						str2 = ' selected="selected"';
					}
					str.push('<option value='+result.data[o].code+str2+'>'+result.data[o].name+'</option>');
					str2 = '';
				}
				$('#businessTypeSanId').html(str.join(''));
			}else{
				dialog(result.msg);
			}
		}
	});
	changeBusinessTypeSan();
}
function changeBusinessTypeSan(){
	var businessTypeSanId = $('#businessTypeSanId').val();
	if('A1' == businessTypeSanId){
		$('select[name=certfCdeType]').html('<option value="1">居民身份证</option>');
		$('select[name=insNoSameAsCertfCdeType]').html('<option value="1">居民身份证</option>');
		$('select[name=appNoSameAsCertfCdeType]').html('<option value="1">居民身份证</option>');
	}else{
		//车主
		var cdeStr1 = '';
		if(certfCdeType){
			if('1' == certfCdeType){
				cdeStr1 = '<option value="1" selected="selected">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('4' == certfCdeType){
				cdeStr1 = '<option value="1">居民身份证</option><option value="4" selected="selected">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('5' == certfCdeType){
				cdeStr1 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5" selected="selected">工商注册号码</option>';
			}
		}else{
			cdeStr1 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
		}
		$('select[name=certfCdeType]').html(cdeStr1);
		
		//被保人
		var cdeStr2 = '';
		if(insNoSameAsCertfCdeType){
			if('1' == insNoSameAsCertfCdeType){
				cdeStr2 = '<option value="1" selected="selected">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('4' == insNoSameAsCertfCdeType){
				cdeStr2 = '<option value="1">居民身份证</option><option value="4" selected="selected">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('5' == insNoSameAsCertfCdeType){
				cdeStr2 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5" selected="selected">工商注册号码</option>';
			}
		}else{
			cdeStr2 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
		}
		$('select[name=insNoSameAsCertfCdeType]').html(cdeStr2);
		
		//投保人
		var cdeStr3 = '';
		if(appNoSameAsCertfCdeType){
			if('1' == appNoSameAsCertfCdeType){
				cdeStr3 = '<option value="1" selected="selected">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('4' == appNoSameAsCertfCdeType){
				cdeStr3 = '<option value="1">居民身份证</option><option value="4" selected="selected">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('5' == appNoSameAsCertfCdeType){
				cdeStr3 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5" selected="selected">工商注册号码</option>';
			}
		}else{
			cdeStr3 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
		}
		$('select[name=appNoSameAsCertfCdeType]').html(cdeStr3);
	}
	if(document.getElementById('vciSign').checked){		
		hideRisk();
		showRisk();
	}
}
$(function(){
	chanageBusinessType();
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
		riskAssignment(coverageItemsp);
	}
	//险别信息判断（续保是传值）
	if(xubao && resourceVehicleCvrg){
		if(resourceVehicleCvrg && resourceVehicleCvrg.length > 0){		
			$('#tciSign').attr('checked',true);
			$('#vciSign').attr('checked',true);
			$('.js_check').show();
			
			var coverageItems=$.parseJSON(coverageItemsp);
			
			/*------------------清除险别状态---------------------------------*/
			//基本险
			$('.js_check').attr('checked',false);
			$('.noductcontainer .bjmpys').removeClass('textchecked');
			$('.noductcontainer .bjmpys').addClass('textnochecked');
			$('.risk_noductcontainer').attr('anyExcess','0');
			$('.noductcontainer .iconchecked').hide();
			
			/*-------------------遍历险别，给险别赋值-------------------------------*/
			for(var o in coverageItems){
				//车损险
				if('030006' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#chesunxian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('chesunxian');
					}
				}
				//三者险
				if('030018' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#sanzhexian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('sanzhexian');
					}
					$('#amount_sanzhexian option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//司机险
				if('030001' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#sijixian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('sijixian');
					}
					$('#amount_sijixian option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//乘客险
				if('030009' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#chengkexian').attr('checked',true);
					console.info(coverageItems[o].noDduct);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('chengkexian');
					}
					var seatNum3 = $('#seatNum').val()-0;
					var sumLimit = coverageItems[o].sumLimit-0;
					if(0 < seatNum3){						
						$('#amount_chengkexian option[value='+sumLimit/(seatNum3-1)+']').attr('selected','selected');
					}else{
						$('#amount_chengkexian option[value=10000]').attr('selected','selected');
					}
				}
				//盗抢险
				if('030061' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#daoqiangxian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('daoqiangxian');
					}
				}
				//玻璃险
				if('030004' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#bolixian').attr('checked',true);
					$('#amount_bolixian option[value='+coverageItems[o].glsType+']').attr('selected','selected');
				}
				//划痕险
				if('032601' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#huahenxian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('huahenxian');
					}
					$('#amount_huahenxian option[value='+coverageItems[o].sumLimit+']').attr('selected','selected');
				}
				//自然险
				if('030012' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#ziranxian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('ziranxian');
					}
				}
				//涉水险
				if('032608' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#sheshuixian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('sheshuixian');
					}
				}
				//设备损失险
				if('030021' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#shebeisunshixian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('shebeisunshixian');
					}
				}
				//精神损害险
				if('030015' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#jingshensunhaixian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('jingshensunhaixian');
					}
				}
				//车上货物责任险
				if('030032' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#cheshanghuowuzerenxian').attr('checked',true);
					if('1' == coverageItems[o].noDduct){
						noAnyExcess('cheshanghuowuzerenxian');
					}
				}
				//指定修理厂险
				if('032618' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#zhidingxiulichangxian').attr('checked',true);
				}
				//无法找到第三方险
				if('033008' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#wufazhaodaodisanfangxian').attr('checked',true);
				}
				//修理期间费用补偿险
				if('033003' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#xiuliqijianfeiyongbuchangxian').attr('checked',true);
				}
				//起重、装卸、挖掘车辆损失扩展条款
				if('033018' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#chesunkuozhantiaokuan').attr('checked',true);
				}
				//特种车辆固定设备、仪器损坏扩展条款
				if('033019' == coverageItems[o].code){
					$('#vciSign').attr('checked',true);
					$('#shebeisunhuaikuochongtiaokuan').attr('checked',true);
				}
				
			}
			//去掉车损险则不能投附加险
			if(!document.getElementById('chesunxian').checked){
				$('.chesunxian_son').attr('disabled',true);
				$('.chesunxian_son').attr('checked',false);
				$('.chesunxian_son').css('background-color','#D9D9D9');
				$.each($('.chesunxian_son'), function(){
					if('huahenxian' == this.id || 'ziranxian' == this.id || 'sheshuixian' == this.id || 'shebeisunshixian' == this.id){//附加险附加险选中
						noAnyExcess(this.id)
					}
				});
			}
			//如果三者险司机险和乘客险都未选择，则不能投精神损害险
			if(!document.getElementById('sanzhexian').checked && !document.getElementById('sijixian').checked && !document.getElementById('chengkexian').checked){
				$('#jingshensunhaixian').attr('disabled',true);
				$('#jingshensunhaixian').attr('checked',false);
				$('#jingshensunhaixian').css('background-color','#D9D9D9');
				noAnyExcess('jingshensunhaixian');
			}
			//去掉三者险则不能投车上货物责任险
			if(!document.getElementById('sanzhexian').checked){
				$('#cheshanghuowuzerenxian').attr('disabled',true);
				$('#cheshanghuowuzerenxian').attr('checked',false);
				$('#cheshanghuowuzerenxian').css('background-color','#D9D9D9');
				noAnyExcess('cheshanghuowuzerenxian');
			}
		}
	}
	coverage();
});

