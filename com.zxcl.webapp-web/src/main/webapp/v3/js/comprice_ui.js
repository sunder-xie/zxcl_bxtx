
//属性：字符串转日期
String.prototype.toDate = function() {
	var _str = new String(this);
	var _strAry = _str.trim().replace(/ |:|\//g, "-").split("-");
	if(_str.length >= 4 && _strAry.length) {
		var _date = new Date(_strAry[0], parseFloat(_strAry[1] || 1) - 1, _strAry[2] || 1, _strAry[3] || 0, _strAry[4] || 0, _strAry[5] || 0, _strAry[6] || 0);
		// 判断结果中是否有N或者a，有则转换异常
		// if(_date == undefined || _date.indexOf('N') > -1 ||
		// _date.indexOf('a') > -1){
			// throw new Error("日期转换出错，请输入正确的日期，格式如“yyyy-MM-dd hh:mm:ss”");
		// }
		return _date;
	} else {
		throw new Error("日期格式不正确，至少输入年，格式如“yyyy”");
	}
};
// 属性：字符串转日期并格式化
String.prototype.formatDate = function(format) {
	return this.toDate().format(format);
};
// 属性：日期格式化，与Date.prototype.format暂未归并，日期格式参考：yyyy-MM-dd HH:mm:ss
Date.prototype.format = function(format) {
	if(!this){return;}
	if(!format){format = "yyyy-MM-dd";}// 缺省值
	var M = this.getMonth() + 1, H = this.getHours(), m = this.getMinutes(), d = this.getDate(), s = this.getSeconds(), q = Math.floor((this.getMonth() + 3) / 3), S = this.getMilliseconds();
	var dict = {
		"M+" : M, // month
		"d+" : d, // day
		"H+" : H, // hour
		"m+" : m, // minute
		"s+" : s, // second
		"q+" : q, // quarter
		"S" : S // millisecond
	};// 支持毫秒
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
// 属性：在日期上增加指定的天数v
Date.prototype.dateAdd=function(v){		
	var d = new Date(this);
	if(/\d/.test(v)){
		d.setDate(d.getDate()+v);
	}else{
		throw new Error("参数不是数字.");
	}
	return d;
};
// 属性：返回一年后的日期
Date.prototype.nextYear = function(){
	var d = new Date(this);
	d.setFullYear(d.getFullYear()+1);
	return d;
};

// 属性：计算与传入日期之间的天数(比如1.1天算2天,1.5天算是2天，)
// 老产品的天数算法还是按照Math.round()四舍五入的方法
Date.prototype.dateDiff = function(date){
	var now = this;
	var diff = Math.abs(now.getTime()-date.getTime());
	return Math.ceil(diff/(24*60*60*1000));// 天数差用floor计算
};
$(function(){
	
	$(".warnsIcon").click(function(){
		var tips = $($(this).html());
		tips.css("display","block");
		dialog(tips);
	});
});
$(".company .getQuotes").each(function(){
	$(this).bind("click",function(){
		if($(this).attr("disabled") == "disabled"){
			return ;
		}
		$(this).attr("disabled","disabled");
		$(this).nextAll(".errorIcon").remove();
		$(this).nextAll(".warnsIcon").remove();
		$(this).html("");
		$(this).next().show();
		$(this).next().next().show();
		$(this).next().next().next().show();
		$(this).next().next().next().next().hide();
		
		var quotnType = $(this).attr("quotnType");
		// 自动报价
		var bjbtn = this;
		if('A' == quotnType){
			$(bjbtn).attr("disabled","disabled");
			$(bjbtn).removeClass("bc2");
			$(bjbtn).html("<img src=\""+ctx+"images/status_loading.gif\"/>");
			$.ajax({
				url:ctx+"getInsured.do?insId="+$(bjbtn).parent().attr("id")+"&inquiryId="+inquiryId+"&timestamp="+new Date().getTime(),
				type:"post",
				dateType:"json",
				success:function(result){
					$(bjbtn).removeAttr("disabled");
					if(result.succ){
						$(bjbtn).unbind("click");//移除点击事件
						$(bjbtn).addClass("bc4");
						/*先注释下，看看有没有问题。因为觉得这几句代码没用if(result.data.msg){
							var message = '';
							if(result.data.msg){
								message = result.data.msg;
							}else{
								message = '报价失败';
							}
							dialog(message);
						}*/
						$(bjbtn).html("报价详情");
						// var img = "<span class="litb">立即投保</span>";
						var lastYearClaimNum = '';
						if(result.data.lastYearClaimNum > 0){
							lastYearClaimNum = ' 出险'+result.data.lastYearClaimNum+'次';
						}
						if('2' == result.data.insurance.insType){
							img = '';
						}
						
						if('TPIC' == result.data.insurance.insId){
							var url = 'confirmQuotaInfo.do?insId='+result.data.insurance.insId+'&quotaId='+result.data.quotaId+'&inquiryId='+result.data.inquiryId;
							$(bjbtn).bind("click",function(){
								returnPageTpic(result.data.carCheckStatus,url);
							});
						}else{
							var url = 'confirmQuotaInfo.do?insId='+result.data.insurance.insId+'&quotaId='+result.data.quotaId+'&inquiryId='+result.data.inquiryId;
							$(bjbtn).bind("click",function(){
								location.href=url;
							});
						}
						try{
							var discount = result.data.discount;
							if(discount != null && discount != '' && discount > 0){
								$("#"+result.data.insurance.insId).find('.line1').html('¥' + result.data.amount);
								$("#"+result.data.insurance.insId).find('.line2').html('折扣：'+discount+lastYearClaimNum);

							}else{
								$("#"+result.data.insurance.insId).find('.line1').html('¥' + result.data.amount);
								$("#"+result.data.insurance.insId).find('.line2').html(lastYearClaimNum);
							}
						}catch(e){
							$("#"+result.data.insurance.insId).find('.line1').html('¥' + result.data.amount);
							$("#"+result.data.insurance.insId).find('.line2').html(lastYearClaimNum);
						}
						$("#"+result.data.insurance.insId).attr("quotaId",result.data.quotaId);
						
						// 显示商业险费率
						$.ajax({
							type:'POST',
							url:ctx+'getFeeVCIRatioByInsId.do',
							data:{"insId":result.data.insurance.insId, 'quotaId':result.data.quotaId},
							dataType:'json',
							async:false,
							success:function(data){
								var ratio_v = data.data;
								if(null != ratio_v && '' != ratio_v && 'null' != ratio_v){
									$("#"+result.data.insurance.insId).find('.line22').html('<span class="eye-top"><span class="eye"><img src="'+ctx+'images/yan_off.png"/></span><span class="ratio-value">奖金：'+ratio_v+'</span></span>');
								}
							}
						});
						// 太平自动改变起保日期提示
						if('1' == result.data.isOrNoUpdateStartDateSign){
							var str = '';
							if(result.data.ticStartQuotaDate){
								str += '系统已自动更新了起保日期，交强险起保日期：'+result.data.ticStartQuotaDate;
								var bigDate = result.data.ticStartQuotaDate;
								var _endDateFormat = "yyyy-MM-dd";
								var bgnDate = bigDate.toDate();
								var nextYear = bgnDate.nextYear();
								var nextYear_seconds = nextYear.getSeconds();  
								nextYear.setSeconds(nextYear_seconds-1);// 往前推一秒
								var endDate = nextYear.format(_endDateFormat);
								$('#tciDate').html(bigDate.substring(0,10)+' 至 '+endDate);
							}
							if(result.data.vicStartQuotaDate){
								if(str){
									str += '；';
								}else{
									str += '系统已自动更新了起保日期，';
								}
								str += '商业险起保日期：'+result.data.vicStartQuotaDate;
								var bigDate = result.data.vicStartQuotaDate;
								var _endDateFormat = "yyyy-MM-dd";
								var bgnDate = bigDate.toDate();
								var nextYear = bgnDate.nextYear();
								var nextYear_seconds = nextYear.getSeconds();  
								nextYear.setSeconds(nextYear_seconds-1);// 往前推一秒
								var endDate = nextYear.format(_endDateFormat);
								$('#vciDate').html(bigDate.substring(0,10)+' 至 '+endDate);
							}
							dialog(str);
						}
						
						/*//处理提示信息
						if(result.data.warns.length > 0){
							var info = "";
							for(var i = 0 ;i<result.data.warns.length;i++){
								info += result.data.warns[i] + "<div style='clear:both;'></div>";
							}
							$(bjbtn).parent().css({"position":"relative"});
							var warnsTips = $("<div class=\"warnsIcon\"\"></div>");
							warnsTips.click(function(){
								dialog(info);
							});
							$(bjbtn).next().next().next().next().after(warnsTips);
						}*/
					}else{
						
						if(result.data.isAllowReQuote == 1){
							//允许重复报价.
							$(bjbtn).html("重新报价");
							$(bjbtn).next().hide();
							$(bjbtn).addClass("bc2");
							$(bjbtn).next().next().hide();
							$(bjbtn).next().next().next().hide();
							$(bjbtn).next().next().next().next().show();
							$(bjbtn).parent().css({"position":"relative"});
							var errorTips = $("<div class=\"errorIcon\"></div>");
							errorTips.click(function(){
								dialog(result.data.errorMessages);
							});
							$(bjbtn).next().next().next().next().after(errorTips);
							
						}else{
							// 报价失败查看原因按钮
							$(bjbtn).unbind("click");//移除点击事件
							$(bjbtn).bind("click",function(){
								show_fail_reason($(this),$(bjbtn).parent().attr("id"),inquiryId);
							});
							$(bjbtn).html("查看原因");
							$(bjbtn).addClass("bc6");
							$(bjbtn).next().hide();
							$(bjbtn).next().next().hide();
							$(bjbtn).next().next().next().hide();
							$(bjbtn).next().next().next().next().show();
							
							if(null != result.data.errorMessages && '' != result.data.errorMessages){
								var insName = $('#'+result.data.insId).attr('insName');
								errorMessages.push(insName+'!!,!');
								errorMessages.push(result.data.errorMessages+'>>,>');										
								// 用来标识保险公司
								// 报价失败后将失败信息存入显示报价后的标签
								$(bjbtn).append("<input type='hidden' id='insNameId' name='insName' value='"+insName+"'/>");
								var message = '';
								if(result.data.errorMessages){
									message = result.data.errorMessages;
								}else{
									message = '报价失败';
								}
								$(bjbtn).append("<input type='hidden' id='insMsgId' name='insMsg' value='"+message+"' manual_quotn='"+result.data.manualQuotn+"'/>");											
							}
							failIns++;
							$('#failIns').html(failIns);
						}
						
					}
				}
			});
		}else{// 人工报价
			$(bjbtn).attr("disabled","disabled");
			$(bjbtn).removeClass("bc2");
			$(bjbtn).removeClass("btnCol");
			$(bjbtn).html("<img src=\""+ctx+"images/status_loading.gif\"/>");
			$.ajax({
				url:ctx+'manualQuotation/manualQuotation.do',
				type:"post",
				data:{'insId':$(this).parent().attr("id"),'inquiryId':inquiryId},
				dateType:"json",
				success:function(result){
					$(bjbtn).removeAttr("disabled");
					$(bjbtn).unbind("click");//移除点击事件
					if(result.succ){
						$(bjbtn).addClass("bc4");
						$(bjbtn).html('报价中');
					}else{
						$(bjbtn).addClass("bc6");
						$(bjbtn).html('提交人工报价失败');
						dialog(result.msg, "确定", function(){
							tipclosefun($('#promptConfirm'));
						});
					}
				}
			});
		}
	});
});

// 人工报价失败原因查看
function viewManualQuotationFailureReason(quoteId,type,inquiryId){
	$.ajax({
		type:'POST',
		url:ctx+'manualQuotation/viewManualQuotationFailureReason.do',
		data:{'quoteId':quoteId,'type':type},
		dataType:'json',
		success:function(result){
			if(result.succ){
				dialog(result.data,"确定",function(){
					tipclosefun($('#promptConfirm'));
				});
			}else{
				dialog(result.data,"确定",function(){
					tipclosefun($('#promptConfirm'));
				});
			}
		}
	});
}

// 查询报价失败原因
function show_fail_reason(obj,insId,inquiryId){
	var manual_quotn = $(obj).find("#insMsgId").attr('manual_quotn');
	console.info(manual_quotn);
	var msg = $(obj).find("#insMsgId").val();
	if('1' == manual_quotn){//可以转人工报价
		dialogConfirm(msg,'转人工报价',function(){
			var bjbtn = obj;
			$(bjbtn).attr("disabled","disabled");
			$(bjbtn).removeClass("bc6");
			$(bjbtn).html("<img src=\""+ctx+"images/status_loading.gif\"/>");
			$.ajax({
				url:ctx+'manualQuotation/manualQuotation.do',
				type:"post",
				data:{'insId':insId,'inquiryId':inquiryId},
				dateType:"json",
				success:function(result){
					$(bjbtn).removeAttr("disabled");
					$(bjbtn).unbind("click");//移除点击事件
					$('.zwbj').hide();
					if(result.succ){
						$(bjbtn).addClass("bc4");
						$(bjbtn).html('报价中');
					}else{
						$(bjbtn).addClass("bc6");
						$(bjbtn).html('提交人工报价失败');
						dialog(result.msg, "确定", function(){
							tipclosefun($('#promptConfirm'));
						});
					}
				}
			});
		},'取消',function(){
			tipclosefun($('#promptConfirm'));
		});
	}else{		
		dialog(msg);
	}
	
}
// 查看支付方式
function viewPayType(){
	dialog('营业网点缴费',"确定",function(){
		tipclosefun($('#promptConfirm'));
	});
	/*$.ajax({
		type:'POST',
		url:ctx+'manualQuotation/viewPayType.do',
		data:{'insId':insId,'inquiryId':inquiryId},
		dataType:'json',
		success:function(result){
			if(result.succ){
				dialog(result.data,"确定",function(){
					tipclosefun($('#promptConfirm'));
				});
			}else{
				dialog(result.msg,"确定",function(){
					tipclosefun($('#promptConfirm'));
				});
			}
		}
	});*/
}
function dataQuery(_this, orderId, insId, quotaId){
	loadfun("查询中");
	$.ajax({
	    url:ctx+"dataQuery.do?orderId="+orderId+"&insId="+insId,
		dataType:"json",
		type:"post",
		success:function(result){
			loadclosefun();
			if(result.succ){
				dialog("订单最新状态：" + result.msg);
				if(result.msg == '已生成保单'){
					$(_this).html('保单');
					$(_this).attr("onclick","window.location.href='"+(ctx+'detail.do?orderId='+orderId+'&insId='+insId+"&quotaId="+quotaId)+"'");
				}
			}else{
				dialog("未查询到保单信息");
			}
		}
	});
}
function returnPageTpic(carCheckStatus,url){
	if(1 == carCheckStatus){
		dialog("在给您上门收款或配送保单时，我们需要对您的车辆进行拍照查验，谢谢配合。", "确定", function(){
			document.location.href=url;
		});
	}else{
		document.location.href=url;
	}
}

$('.eye').live('click',function(){
	if($(this).parents('.eye-top').find('.ratio-value').is(':hidden')){
		$(this).html('<img src="'+ctx+'images/yan_on.png"/>');
		$(this).parents('.eye-top').find('.ratio-value').show().css({position: 'absolute',display:'inline-block'});
	}
	else{
		$(this).html('<img src="'+ctx+'images/yan_off.png"/>');
		$(this).parents('.eye-top').find('.ratio-value').hide();
	}
});

function quoteInfo(insId,quoteId,inquiryId){
	$.ajax({
		type:'POST',
		url:ctx+'goQuotaVerify.do',
		data:{'inquiryId':inquiryId,'quoteId':quoteId},
		dataType:'json',
		success:function(result){
			if(result.succ){
				if('1' == result.msg){// 时间过期，进入修改信息页面
					dialogConfirm(result.data,"重新报价",function(){
						window.location.href=ctx+'insuranceType.do?inquiryId='+inquiryId+'&useType=1&haveOrder=1';
					});
				}else{// 时间未过期，进入支付
					window.location.href = ctx+'confirmQuotaInfo.do?insId='+insId+'&quotaId='+quoteId+'&inquiryId='+inquiryId;
				}
			}
		}
	});
}

function payCheck(e,type){
	 var quoteId=$(e).attr("quotaId");
	 var inquiryId=$(e).attr("inquiryId");
	$.ajax({
		type:'POST',
		url:ctx+'goQuotaVerify.do',
		data:{'inquiryId':inquiryId,'quoteId':quoteId},
		dataType:'json',
		success:function(result){
			if(result.succ){
				if('1' == result.msg){// 时间过期，进入修改信息页面
					dialogConfirm(result.data,"重新报价",function(){
						window.location.href=ctx+'insuranceType.do?inquiryId='+inquiryId+'&useType=1&haveOrder=1';
					});
				}else{// 时间未过期，进入支付
					payCheck1(e,type);
				}
			}
		}
	});
};
function payCheck1(e,type){
	 var orderId=$(e).attr("orderId");
	 var insId=$(e).attr("insId");
	 var quotaId=$(e).attr("quotaId");
	 var inquiryId=$(e).attr("inquiryId");
	objPay = e;
	$.post(ctx+"query_ins_status.do",{'insId':insId}, function(data){
		if(data.succ){
			$.ajax({
				type:'POST',
				url:ctx+'payDateCheck.do',
				data:{'inquiryId':inquiryId,'quoteId':quotaId,'insId':insId},
				dataType:'json',
				async:false,
				success:function(result){
					if('1' == result){
						dialogConfirm("距离保单起保日期不到半小时，此时支付可能无法生成保单","继续支付",function(){
							payCheck(e,'2');
						});
					}else if('2' == result){
						dialogConfirm("报价单已过期，需重新报价","重新报价",function(){
							window.location.href=ctx+'insuranceType.do?inquiryId='+inquiryId+'&useType=1&haveOrder=1';
						});
					}else if('3' == result){
						dialog("暂不支持线上支付");
					}
					if('0' == result || '2' == type){					
						$(".load").show();
						var url = '';
						/* var insId = insId.substring(0,4); */
						if('JTIC01' == insId){// 锦泰
							url=ctx+"allinpayContro.do?orderId="+orderId+"&insId="+insId;
						}else if('PAIC' == insId || 'PICC' == insId || 'CICP' == insId || 'JTIC' == insId || 'TPBX' == insId ){
							url = ctx+'payController.do?quoteId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId;
							/* url='${ctx}tpbx/pay.do?quotaId='+quotaId+'&insId='+insId+'&inquiryId='+inquiryId+'&orderId='+orderId; */
						}else if('FDCP' == insId){
							url=ctx+"fdcp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}else if('HHBX' == insId){
							url=ctx+"hhbx/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}else if('PINGANHTTP' == insId){
							url=ctx+"pinganHttp/pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}else{
							url=ctx+"pay.do?quotaId="+quotaId+"&insId="+insId+"&inquiryId="+inquiryId+"&orderId="+orderId;
						}
						window.location.href=url;
					}
				}
			});
		}
		else{
			dialog(data.msg);
		}
	},'json');
	 
}

	function backOn(){
		
		if('1' == haveOrder){// 页面上存在订单
			dialogConfirm("是否确定放弃当前订单及报价","确定",function(){
				window.location.href=ctx+"insuranceType.do?inquiryId="+inquiryId+"&useType=1&haveOrder="+haveOrder;
			},"取消",function(){
				tipclosefun($('#promptConfirm'));
			});
		}else{
			var quoteCount = false;
			$.ajax({
				type:'POST',
				url:ctx+'manualQuotation/queryManualQuotationByInquiryId.do',
				data:{'inquiryId':inquiryId},
				datatype:'json',
				async:false,
				success:function(result){
					console.info(result.data);
					if(result.succ){
						quoteCount = result.data;
					}
				}
			});
			console.info('2:'+quoteCount);
			if(quoteCount){
				dialogConfirm("人工报价正在处理中，修改信息将自动放弃人工报价请求，是否确定修改投保信息？","确定",function(){
					$.ajax({
						type:'POST',
						url:ctx+'manualQuotation/withdrawQuotn.do',
						data:{'inquiryId':inquiryId},
						datatype:'json',
						success:function(result){
							if(result.succ){
								window.location.href=ctx+"insuranceType.do?inquiryId="+inquiryId+"&useType=1&haveOrder="+haveOrder;
							}
						}
					});
				},"取消",function(){
					tipclosefun($('#promptConfirm'));
				});
			}else{
				window.location.href=ctx+'insuranceType.do?inquiryId='+inquiryId+'&useType=1';
			}
		}
		
	}