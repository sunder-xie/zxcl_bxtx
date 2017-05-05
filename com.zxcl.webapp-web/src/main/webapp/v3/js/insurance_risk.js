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
/*
 * 根据业务类型判断显示的险种类别
 * 1-非营运私家车
 * 2-非营运货车和营运货车
 * 3-特种车
 * 4-其他
 */
function judgeRisk(){
	var businessTypeSanType = $('#businessTypeSanId').val();
	var riskType = '1';
	if('A1' == businessTypeSanType){
		riskType = '1';
	}else if('A4' == businessTypeSanType || 'B4' == businessTypeSanType){
		riskType = '2';
	}else if('A5' == businessTypeSanType || 'B5' == businessTypeSanType){
		riskType = '3';
	}else{
		riskType = '4';
	}
	return riskType;
}
//商业险点击事件
$('#vciSign').click(function(){
	var clickEnt = document.getElementById('vciSign').checked;
	if(clickEnt){//选中商业险
		showRisk();
	}else{//去掉商业险
		hideRisk();
	}
});
//选中商业险险别控制
function showRisk(riskType){
	//默认的险
	$('.risk_base').attr('checked',true);
	$('.risk_base').attr('disabled',false);
	$('.risk_base').css('background-color','');
	$('#daoqiangxian').attr('checked',false);
	noAnyExcess('daoqiangxian');
	//附加险
	$('.risk_attached').attr('disabled',false);
	$('.risk_attached').css('background-color','');
	//不计免赔
	$.each($('.risk_default'), function(){
		noAnyExcess(this.id);
	});
	var riskType = judgeRisk();
	//打开
	$('.risk_'+riskType).show();
	coverage();
}
//去掉商业险，险别控制
function hideRisk(){
	//基本险
	$('.risk_base').attr('checked',false);
	$('.risk_base').attr('disabled',true);
	$('.risk_base').css('background-color','#D9D9D9');
	//附加险
	$('.risk_attached').attr('checked',false);
	$('.risk_attached').attr('disabled',true);
	$('.risk_attached').css('background-color','#D9D9D9');
	//不计免赔
	$('.noductcontainer .bjmpys').removeClass('textchecked');
	$('.noductcontainer .bjmpys').addClass('textnochecked');
	$('.risk_noductcontainer').attr('anyExcess','0');
	$('.noductcontainer .iconchecked').hide();
	
	$('#amount_sanzhexian option[value=500000]').attr('selected','selected');
	$('#amount_sijixian option[value=10000]').attr('selected','selected');
	$('#amount_chengkexian option[value=10000]').attr('selected','selected');
	$('#amount_bolixian option[value=1]').attr('selected','selected');
	$('#amount_huahenxian option[value=2000]').attr('selected','selected');
	//隐藏
	$('.risk').hide();
}
//保额加载
function coverage(){
	$.each($('.riskCheck'), function(){
		var id = this.id;
		if(id){
			id = id.substring(7,30);
		}
		if($('#'+id)[0].checked){
			$(this).attr('hidden',false);
		}else{
			$(this).attr('hidden',true);
		}
	});
}
//基本险点击事件
$('.js_check').click(function(e){
	basicControl(e.toElement);
});
//基本险控制
function basicControl(e){
	//选中状态
	if(document.getElementById(e.id).checked){
		$('#vciSign').attr('checked',true);
		//添加车损险则能投附加险（精神损害险和车上货物责任险例外）
		if('chesunxian' == e.id){
			$('.chesunxian_son').attr('disabled',false);
			$('.chesunxian_son').css('background-color','');
			$.each($('.chesunxian_son'), function(){
				if('huahenxian' == this.id || 'ziranxian' == this.id || 'sheshuixian' == this.id || 'shebeisunshixian' == this.id){//附加险附加险选中
					noAnyExcess(this.id)
				}
			});
		}
		//添加司机险和乘客险则能投精神损害险
		if('sijixian' == e.id || 'chengkexian' == e.id){
			$('#jingshensunhaixian').attr('disabled',false);
			$('#jingshensunhaixian').css('background-color','');
			noAnyExcess('jingshensunhaixian');
		}
		//添加三者险则能投精神损害险和车上货物责任险
		if('sanzhexian' == e.id){
			$('#jingshensunhaixian').attr('disabled',false);
			$('#jingshensunhaixian').css('background-color','');
			noAnyExcess('jingshensunhaixian');
			
			$('#cheshanghuowuzerenxian').attr('disabled',false);
			$('#cheshanghuowuzerenxian').css('background-color','');
			noAnyExcess('cheshanghuowuzerenxian');
		}
		noAnyExcess(e.id)
	}else{//去掉状态
		var count = false;
		$.each($('.js_check'), function(){
			if(document.getElementById(this.id).checked){
				count = true;
			}
		});
		if(!count){
			$('#vciSign').attr('checked',false);
			hideRisk();
		}
		//去掉车损险则不能投附加险
		if('chesunxian' == e.id){
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
		if('sanzhexian' == e.id || 'sijixian' == e.id || 'chengkexian' == e.id){
			var sanzhexianChecked = document.getElementById('sanzhexian').checked;
			var sijixianChecked = document.getElementById('sijixian').checked;
			var chengkexianChecked = document.getElementById('chengkexian').checked;
			if(!sanzhexianChecked && !sijixianChecked && !chengkexianChecked){
				$('#jingshensunhaixian').attr('disabled',true);
				$('#jingshensunhaixian').attr('checked',false);
				$('#jingshensunhaixian').css('background-color','#D9D9D9');
				noAnyExcess('jingshensunhaixian');
			}
		}
		//去掉三者险则不能投车上货物责任险
		if('sanzhexian' == e.id){
			$('#cheshanghuowuzerenxian').attr('disabled',true);
			$('#cheshanghuowuzerenxian').attr('checked',false);
			$('#cheshanghuowuzerenxian').css('background-color','#D9D9D9');
			noAnyExcess('cheshanghuowuzerenxian');
		}
		noAnyExcess(e.id);
		if('sanzhexian' == e.id){			
			$('#amount_sanzhexian option[value=500000]').attr('selected','selected');
		}else if('sijixian' == e.id){			
			$('#amount_sijixian option[value=10000]').attr('selected','selected');
		}else if('chengkexian' == e.id){
			$('#amount_chengkexian option[value=10000]').attr('selected','selected');
		}else if('bolixian' == e.id){
			$('#amount_bolixian option[value=1]').attr('selected','selected');
		}else if('huahenxian' == e.id){
			$('#amount_huahenxian option[value=2000]').attr('selected','selected');
		}
	}
	coverage();
}

function riskAssignment(coverageItemsp){
	//交强险是否选中
	var tciSign=parseInt(inquiry_tciSignp)?true:false;
	$('#tciSign').attr('checked',tciSign);
	//商业险是否选中
	var vciSign=parseInt(inquiry_vciSignp)?true:false;
	$('#vciSign').attr('checked',vciSign);
	
	var coverageItems=$.parseJSON(coverageItemsp);
	//隐藏险别
	if(!vciSign){
		hideRisk()
	}else{//根据获取的值给险别赋值
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
			if('033004' == coverageItems[o].code){
				$('#vciSign').attr('checked',true);
				$('#jingshensunhaixian').attr('checked',true);
				if('1' == coverageItems[o].noDduct){
					noAnyExcess('jingshensunhaixian');
				}
			}
			//车上货物责任险
			if('033002' == coverageItems[o].code){
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
			if('033007' == coverageItems[o].code){
				$('#vciSign').attr('checked',true);
				$('#xiuliqijianfeiyongbuchangxian').attr('checked',true);
			}
			//起重、装卸、挖掘车辆损失扩展条款
			if('033022' == coverageItems[o].code){
				$('#vciSign').attr('checked',true);
				$('#chesunkuozhantiaokuan').attr('checked',true);
			}
			//特种车辆固定设备、仪器损坏扩展条款
			if('033023' == coverageItems[o].code){
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
		coverage();
	}
}

function getItems(){
	var items = '';
	var count = document.getElementById('vciSign').checked;
	if(!count){
		return false;
	}
	var items=[];
	var input=$(".js_check");
	for (var i = 0;i <input.length;i++) {
		
		//被选中的险别
		if($(input[i]).attr("checked") == "checked"){console.info($(input[i]).attr("id"));
			var id=$(input[i]).attr("id");
			if('sanzhexian' == id || 'sijixian' == id || 'huahenxian' == id){//处理需用户选择保额的险别（三者险，司机险，划痕险）
				var count = document.getElementById(id).checked;
				if(count){
					var coverageItem=new Object();
					coverageItem.code=$(input[i]).val();
					coverageItem.sumLimit= $('#amount_'+id).val();
					var noDduct = $('#'+id).attr('anyExcess');
					if(noDduct){
						coverageItem.noDduct=noDduct;
					}
					items.push(coverageItem);
				}
			}else if('bolixian' == id){//处理玻璃险
				var value=$("#amount_bolixian").val();
				var count = document.getElementById(id).checked;
				if(count){
					var coverageItem=new Object();
					coverageItem.code=$(input[i]).val();
					coverageItem.glsType=value;
					items.push(coverageItem);
				}
			}else if('chengkexian' == id){//处理乘客险
				var coverageItem=new Object();
				coverageItem.code=$(input[i]).val();
				var seatNum2 = $('#seatNum').val()-0;
				var sumLimit = $(input[i]).next().next().find("option:selected").val()-0;
				coverageItem.sumLimit=sumLimit*(seatNum2-1);
				var noDduct = $('#'+id).attr('anyExcess');
				if(noDduct){
					coverageItem.noDduct=noDduct;
				}
				items.push(coverageItem);
			}else{
				var count = document.getElementById(id).checked;
				if(count){
					var coverageItem=new Object();
					coverageItem.code=$(input[i]).val();
					var noDduct = $('#'+id).attr('anyExcess');
					if(noDduct){
						coverageItem.noDduct=noDduct;
					}
					items.push(coverageItem);
				}
			}
		}
	}	
	return items;
}
