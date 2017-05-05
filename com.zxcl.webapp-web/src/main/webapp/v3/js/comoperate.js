document.write('<title>保行天下</title><meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" /><meta name="apple-mobile-web-app-capable" content="yes" /><meta name="apple-touch-fullscreen" content="yes" /><meta name="full-screen" content="yes" /><meta name="apple-mobile-web-app-status-bar-style" content="black" /><meta name="format-detection" content="telephone=no" /><meta name="format-detection" content="address=no" />');
var telreg=/^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))[0-9]{8}$/;
function showMsg(msg,dis,url){if($('.showMsg').length==0){$('body').append('<div class="showMsg"></div>');} $('.showMsg').text(msg); $('.showMsg').show(); var smw=$('.showMsg').width(),sml=(document.documentElement.clientWidth-smw)/2; $('.showMsg').css('left',sml+'px'); if(dis){if(dis=='none'){$('.showMsg').hide();}}else{setTimeout(function(){$('.showMsg').hide();},1500);} if(url){if(url=='back'){setTimeout(function(){location.href=history.back();},1500);}else if(url=='self'){setTimeout(function(){window.location.reload();},1500);}else{setTimeout(function(){location.href=url;},2000);}}}
function setNav(ind){ $('.bomnav .lt').eq(ind-1).attr('id','nav'+ind).css('color','#D64141');}
/*弹框设置*/function tipfun(obj){hasfixbg(); $('.fixedbg').fadeIn(); obj.fadeIn(); var body=document.documentElement.clientHeight,tipsel=obj.height(),tip=obj.find('.tip').height(),cha=tipsel-68,end=parseInt(cha/24)*24+6; obj.css('margin-top','-'+(parseInt(tipsel+50)/2)+'px'); if(obj.outerHeight()>=body*0.7 && tip>=cha){obj.find('.tip').css({'overflow-y':'scroll','height':end+'px'});}}
/* 弹框关闭 */ function tipclosefun(obj){$('.fixedbg').fadeOut();obj.fadeOut();}
/*加载设置*/function loadfun(msg){hasfixbg(); if($('.loadding').length==0){$('body').append('<div class="loadding txc"></div>');} $('.loadding').html(msg); $('.fixedbg,.loadding').fadeIn();}
/** 加载关闭 */function loadclosefun(){$('.fixedbg,.loadding').fadeOut();}
/*判断是否含有fixedbg元素*/function hasfixbg(){if($('.fixedbg').length==0){$('body').append('<div class="fixedbg"></div>');}}
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

/**
 * 页面后退.
 */
function pageBackOff(){
	
	if(window.location.pathname.endWith("confirmQuotaInfo.do")){
		window.location.href=ctx + 'orderBaseInfoBack.do' + window.location.search;
	}else{
		window.history.back();
	}
	
	//http://uat.zhixunchelian.cn/bxtx/confirmQuotaInfo.do
	
	//history.go(-1);location.reload(); 后退并刷新.
	
}

/**********************************  身份证号校验开始 **********************/


/**
 * 校验
 * @param idCardNo
 * @returns
 * @author co-xuxiaoming001
 */
//新建普通实例
var Validator = new IDValidator();
//验证身份证号码
function checkIdCard(idCardNo) {

	if (idCardNo.length == 18) {
		return Validator.isValid(idCardNo);
	} else {
		return false;
	}
}


var GB2260 = undefined;//格式{"110000":"北京市","110100":"北京市市辖区","110101":"北京市东城区"}

function IDValidator() {
	var param = {
		error : {
			longNumber : '长数字存在精度问题，请使用字符串传值！ Long number is not allowed, because the precision of the Number In JavaScript.'
		}
	};
	var util = {
		checkArg : function(id) {
			var argType = (typeof id);

			switch (argType) {
			case 'number':
				// long number not allowed
				id = id.toString();
				if (id.length > 15) {
					this.error(param.error.longNumber);
					return false;
				}
				break;
			case 'string':
				break;
			default:
				return false;
			}
			id = id.toUpperCase();
			var code = null;
			if (id.length === 18) {
				// 18位
				code = {
					body : id.slice(0, 17),
					checkBit : id.slice(-1),
					type : 18
				};
			} else if (id.length === 15) {
				// 15位
				code = {
					body : id,
					type : 15
				};
			} else {
				return false;
			}
			return code;
		}
		// 地址码检查
		,
		checkAddr : function(addr, GB2260) {
			var addrInfo = this.getAddrInfo(addr, GB2260);
			return (addrInfo === false ? false : true);
		}
		// 取得地址码信息
		,
		getAddrInfo : function(addr, GB2260) {
			GB2260 = GB2260 || null;
			// 查询GB/T2260,没有引入GB2260时略过
			if (GB2260 === null) {
				return addr;
			}
			if (!GB2260.hasOwnProperty(addr)) {
				// 考虑标准不全的情况，搜索不到时向上搜索
				var tmpAddr;
				tmpAddr = addr.slice(0, 4) + '00';
				if (!GB2260.hasOwnProperty(tmpAddr)) {
					tmpAddr = addr.slice(0, 2) + '0000';
					if (!GB2260.hasOwnProperty(tmpAddr)) {
						return false;
					} else {
						return GB2260[tmpAddr] + '未知地区';
					}
				} else {
					return GB2260[tmpAddr] + '未知地区';
				}
			} else {
				return GB2260[addr];
			}
		}
		// 生日码检查
		,
		checkBirth : function(birth) {
			var year, month, day;
			if (birth.length == 8) {
				year = parseInt(birth.slice(0, 4), 10);
				month = parseInt(birth.slice(4, 6), 10);
				day = parseInt(birth.slice(-2), 10);
			} else if (birth.length == 6) {
				year = parseInt('19' + birth.slice(0, 2), 10);
				month = parseInt(birth.slice(2, 4), 10);
				day = parseInt(birth.slice(-2), 10);
			} else {
				return false;
			}
			// TODO 是否需要判断年份
			/*
			 * if( year<1800 ){ return false; }
			 */
			// TODO 按月份检测
			if (month > 12 || month === 0 || day > 31 || day === 0) {
				return false;
			}

			return true;
		}
		// 顺序码检查
		,
		checkOrder : function(order) {
			// 暂无需检测

			return true;
		}
		// 加权
		,
		weight : function(t) {
			return Math.pow(2, t - 1) % 11;
		}
		// 随机整数
		,
		rand : function(max, min) {
			min = min || 1;
			return Math.round(Math.random() * (max - min)) + min;
		}
		// 数字补位
		,
		str_pad : function(str, len, chr, right) {
			str = str.toString();
			len = len || 2;
			chr = chr || '0';
			right = right || false;
			if (str.length >= len) {
				return str;
			} else {
				for (var i = 0, j = len - str.length; i < j; i++) {
					if (right) {
						str = str + chr;
					} else {
						str = chr + str;
					}
				}
				return str;
			}
		}
		// 抛错
		,
		error : function(msg) {
			var e = new Error();
			e.message = 'IDValidator: ' + msg;
			throw e;
		}
	};
	var _IDValidator = function(GB2260) {
		if (typeof GB2260 !== "undefined") {
			this.GB2260 = GB2260;
		}
		// 建立cache
		this.cache = {};
	};
	_IDValidator.prototype = {
		isValid : function(id) {
			var GB2260 = this.GB2260 || null;
			var code = util.checkArg(id);
			if (code === false) {
				return false;
			}
			// 查询cache
			if (this.cache.hasOwnProperty(id)
					&& typeof this.cache[id].valid !== 'undefined') {
				return this.cache[id].valid;
			} else {
				if (!this.cache.hasOwnProperty(id)) {
					this.cache[id] = {};
				}
			}

			var addr = code.body.slice(0, 6);
			var birth = (code.type === 18 ? code.body.slice(6, 14) : code.body
					.slice(6, 12));
			var order = code.body.slice(-3);

			if (!(util.checkAddr(addr, GB2260) && util.checkBirth(birth) && util
					.checkOrder(order))) {
				this.cache[id].valid = false;
				return false;
			}

			// 15位不含校验码，到此已结束
			if (code.type === 15) {
				this.cache[id].valid = true;
				return true;
			}

			/* 校验位部分 */

			// 位置加权
			var posWeight = [];
			for (var i = 18; i > 1; i--) {
				var wei = util.weight(i);
				posWeight[i] = wei;
			}

			// 累加body部分与位置加权的积
			var bodySum = 0;
			var bodyArr = code.body.split('');
			for (var j = 0; j < bodyArr.length; j++) {
				bodySum += (parseInt(bodyArr[j], 10) * posWeight[18 - j]);
			}

			// 得出校验码
			var checkBit = 12 - (bodySum % 11);
			if (checkBit == 10) {
				checkBit = 'X';
			} else if (checkBit > 10) {
				checkBit = checkBit % 11;
			}
			checkBit = (typeof checkBit === 'number' ? checkBit.toString()
					: checkBit);

			// 检查校验码
			if (checkBit !== code.checkBit) {
				this.cache[id].valid = false;
				return false;
			} else {
				this.cache[id].valid = true;
				return true;
			}

		}

		// 分析详细信息
		,
		getInfo : function(id) {
			var GB2260 = this.GB2260 || null;
			// 号码必须有效
			if (this.isValid(id) === false) {
				return false;
			}
			// TODO 复用此部分
			var code = util.checkArg(id);

			// 查询cache
			// 到此时通过isValid已经有了cache记录
			if (typeof this.cache[id].info !== 'undefined') {
				return this.cache[id].info;
			}

			var addr = code.body.slice(0, 6);
			var birth = (code.type === 18 ? code.body.slice(6, 14) : code.body
					.slice(6, 12));
			var order = code.body.slice(-3);

			var info = {};
			info.addrCode = addr;
			if (GB2260 !== null) {
				info.addr = util.getAddrInfo(addr, GB2260);
			}
			info.birth = (code.type === 18 ? (([ birth.slice(0, 4),
					birth.slice(4, 6), birth.slice(-2) ]).join('-')) : ([
					'19' + birth.slice(0, 2), birth.slice(2, 4),
					birth.slice(-2) ]).join('-'));
			info.sex = (order % 2 === 0 ? 0 : 1);
			info.length = code.type;
			if (code.type === 18) {
				info.checkBit = code.checkBit;
			}

			// 记录cache
			this.cache[id].info = info;

			return info;
		}

		// 仿造一个号
		,
		makeID : function(isFifteen) {
			var GB2260 = this.GB2260 || null;

			// 地址码
			var addr = null;
			if (GB2260 !== null) {
				var loopCnt = 0;
				while (addr === null) {
					// 防止死循环
					if (loopCnt > 10) {
						addr = 110101;
						break;
					}
					var prov = util.str_pad(util.rand(50), 2, '0');
					var city = util.str_pad(util.rand(20), 2, '0');
					var area = util.str_pad(util.rand(20), 2, '0');
					var addrTest = [ prov, city, area ].join('');
					if (GB2260[addrTest]) {
						addr = addrTest;
						break;
					}
				}
			} else {
				addr = 110101;
			}

			// 出生年
			var yr = util.str_pad(util.rand(99, 50), 2, '0');
			var mo = util.str_pad(util.rand(12, 1), 2, '0');
			var da = util.str_pad(util.rand(28, 1), 2, '0');
			if (isFifteen) {
				return addr + yr + mo + da
						+ util.str_pad(util.rand(999, 1), 3, '1');
			}

			yr = '19' + yr;
			var body = addr + yr + mo + da
					+ util.str_pad(util.rand(999, 1), 3, '1');

			// 位置加权
			var posWeight = [];
			for (var i = 18; i > 1; i--) {
				var wei = util.weight(i);
				posWeight[i] = wei;
			}

			// 累加body部分与位置加权的积
			var bodySum = 0;
			var bodyArr = body.split('');
			for (var j = 0; j < bodyArr.length; j++) {
				bodySum += (parseInt(bodyArr[j], 10) * posWeight[18 - j]);
			}

			// 得出校验码
			var checkBit = 12 - (bodySum % 11);
			if (checkBit == 10) {
				checkBit = 'X';
			} else if (checkBit > 10) {
				checkBit = checkBit % 11;
			}
			checkBit = (typeof checkBit === 'number' ? checkBit.toString()
					: checkBit);

			return (body + checkBit);
		}

	};// _IDValidator
	GB2260 = GB2260 == null ? "" : GB2260;
	return new _IDValidator(GB2260);
}


String.prototype.endWith=function(endStr){
  var d=this.length-endStr.length;
  return (d>=0&&this.lastIndexOf(endStr)==d)
}

/**********************************  身份证号校验结束 **********************/

/**********************************  时间工具相关开始 **********************/

//毫秒日期格式化
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


/**********************************  时间工具相关结束 **********************/

/**********************************  字符串工具相关 **********************/

function isNull(str){
	if('' == str || null == str || undefined == str){
		return true;
	}else{
		return false;
	}
}
/**
 * 去除字符串的所有空格格
 * @param str
 * @returns
 */
function trimAll(str)
{ 
    return str.replace(/\s+/g, ""); 
}


/**********************************  字符串工具结束 **********************/

/**********************************  时间工具相关结束 **********************/

/*******************  银行卡格式化开始 ****************************/

(function($){
// 输入框格式化
$.fn.bankInput = function(options){
		var defaults = {
			min : 10, // 最少输入字数
			max : 25, // 最多输入字数
			deimiter : ' ', // 账号分隔符
			onlyNumber : true, // 只能输入数字
			copy : true // 允许复制
		};
		var opts = $.extend({}, defaults, options);
		var obj = $(this);
		//obj.css({imeMode:'Disabled',borderWidth:'1px',color:'#000',fontFamly:'Times New Roman'}).attr('maxlength', opts.max);
		if(obj.val() != '') obj.val( obj.val().replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter) );
			obj.bind('keyup',function(event){
		if(opts.onlyNumber){
			if(!(event.keyCode>=48 && event.keyCode<=57)){
				this.value=this.value.replace(/\D/g,'');
			}
		}
		this.value = this.value.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter);
		}).bind('dragenter',function(){
			return false;
		}).bind('onpaste',function(){
		return !clipboardData.getData('text').match(/\D/);
		})/*.bind('blur',function(){
		this.value = this.value.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter);
		if(this.value.length < opts.min){
		showMsg('最少输入'+opts.min+'位账号信息！');
		obj.focus();
		}
		})*/
	}
	// 列表显示格式化
	$.fn.bankList = function(options){
	var defaults = {
	deimiter : ' ' // 分隔符
	};
	var opts = $.extend({}, defaults, options);
	return this.each(function(){
	$(this).text($(this).text().replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter));
	})
	}
})(jQuery);

/**********************/
function myconfirm(msg,yes,no){
	
}

/***************格式化银行卡输入框**************************
$(function(){
    $("#bank_input").bankInput();
    $("#bank_input").bankList();
})**/


/*******************  银行卡格式化结束 ****************************/

/******************** 保行天下v3版本的提示框开始. *************************************/

/**
 * 提示框，只有一个按钮
 * @param content 提示内容
 * @param btnAlt 按钮内容
 * @param callback 点击按钮后的回调函数.
 */
function dialog(content,btnAlt,callback){
	var prompt = $("<div  class=\"tipsel txc\" id=\"prompt\" ></div>");
	var contentDiv = $("<div class=\"tip\"></div>").html(content);
	prompt.append(contentDiv);
	var btn = $("<div class=\"btns c1 clear\"></div>");
	if(btnAlt != undefined && btnAlt != ""){
		btn.html(btnAlt);
	}else{
		btn.html("确定");
	}
	
	btn.click(function(){
		if(typeof callback == 'function'){
			callback();
		}
		//多做一点事情，隐藏提示框
		tipclosefun(prompt);
		prompt.remove();
	});
	
	prompt.append(btn);
	
	$(document.body).append(prompt);
	
	tipfun(prompt);
	
}

/**
 * 提示框，两个按钮
 * @param content 提示内容
 * @param okBtnAlt 确认按钮内容
 * @param okCallback 点击确定按钮后的回调函数.
 * @param cancelBtnAlt 取消按钮内容
 * @param cancelCallback 取消按钮回调.
 * 
 */
function dialogConfirm(content,okBtnAlt,okCallback,cancelBtnAlt,cancelCallback){
	
	var prompt = $("<div  class=\"tipsel txc\" id=\"promptConfirm\" ></div>");
	var contentDiv = $("<div class=\"tip\"></div>").html(content);
	prompt.append(contentDiv);
	var btns = $("<div class=\"btns clear\"></div>");
	
	//ok btn
	var okBtn = $("<div class=\"lt boxSet c1\"></div>");
	if(okBtnAlt != undefined && okBtnAlt != ""){
		okBtn.html(okBtnAlt);
	}else{
		okBtn.html("确定");
	}
	
	okBtn.click(function(){
		if(typeof okCallback == 'function'){
			okCallback();
		}
		//多做一点事情，隐藏提示框
		tipclosefun(prompt);
		prompt.remove();
	});
	btns.append(okBtn);
	
	//cancel btn
	var cancelBtn = $("<div class=\"lt boxSet c1\"></div>");
	if(cancelBtnAlt != undefined && cancelBtnAlt != ""){
		cancelBtn.html(cancelBtnAlt);
	}else{
		cancelBtn.html("取消");
	}
	
	cancelBtn.click(function(){
		if(typeof cancelCallback == 'function'){
			cancelCallback();
		}
		//多做一点事情，隐藏提示框
		tipclosefun(prompt);
		prompt.remove();
	});
	btns.append(cancelBtn);
	
	prompt.append(btns);
	
	$(document.body).append(prompt);
	
	tipfun(prompt);
	
	
}


/******************** 保行天下v3版本的提示框结束. *************************************/


/******************** 不计免赔排版处理开始. *************************************/
$(document.body).ready(function(){
	$(".noductcontainer").each(function(){
		
		//$(this).css("margin-left",($(document.body).width() / 2 ) + "px");
		$(this).css("margin-left",($(document.body).width() / 2) - $(this).position().left);
	});
	
});
/******************** 不计免赔排版处理结束. *************************************/



/******************** 因为这是所有页面都引用了的js。所以有些公共的JS需要执行 开始. *************************************/
$(document.body).ready(function(){
	if($(".bomnav .nav3").html() == "我的"){
		$.post(ctx + "activityd/getWallteLettoryNum.do","",function(data){
			var count = parseInt(data.BD) + parseInt(data.BXTX);
			if(count > 0 ){
				$(".bomnav .nav3").css({"position":"relative"});
				$(".bomnav .nav3").append("<div style=\"position: absolute;top: 4px;left: 68px;background-color: red;color: white;padding: 3px 5px 3px 5px;border-radius: 50%;\">"+count+"</div>");
				if(window.location.pathname.indexOf("myself.do") > -1){
					if(count > 9){
						$("#wallteamount").css({"position":"relative"});
						$("#wallteamount").append("<div style=\"font-size:10px;position: absolute;top: 0px;left: 84%;background-color: red;color: white;padding: 1px 2px 1px 2px;border-radius: 50%;\">"+count+"</div>");
					}else {
						$("#wallteamount").css({"position":"relative"});
						$("#wallteamount").append("<div style=\"font-size:10px;position: absolute;top: 0px;left: 84%;background-color: red;color: white;padding: 2px 3px 1px 3px;border-radius: 50%;\">"+count+"</div>");
					}
				}
			}
			
		});
	}    
});


//公共代码，百度统计
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?14c2b8e897f4246d098911b83ed1f02e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
/******************** 因为这是所有页面都引用了的js。所以有些公共的JS需要执行 结束. *************************************/
