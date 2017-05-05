

/**
	
	bxtx_virtual_keyboard.js
	version:1.0.0  
	author zengjun
	date:2016-07-19
	
	example:<input type="text" virtual_keyboard="idcard" afterInputDelContent="1" isMaxHidden="1" />
	
	virtual_keyboard 支持类型：
		身份证号：idcard
		车牌号：plateno
		车架号：vin
		纯数字：number
		数字和字母：alphanumeric
		车牌号身份：province
		
	
	属性控制： afterInputDelContent 1||true  如果该属性为这两个值，表示在每次初始化键盘之前都要清空input的value值
			isMaxHidden 1||true 是否达到最大字符就自动隐藏
			kbreadonly = 1 当检测到kbreadonly = 1 时，不弹出键盘.针对一些其他地方控制的时候使用.
	
	键盘初始化事件：
		1. click事件.
		2. focus 事件.
		
		注意：当目标input与当前活动Input一致时，不初始化键盘.判断是否一致以id或name为准.所以在使用键盘的时候，要注意id和name的值是否有重复.
			
	
	给界面上所有的Input 元素绑定click、focus函数,并当触发事件时，执行我们指定的函数.
*/
//alert( navigator.userAgent);

var ua = window.navigator.userAgent.toLowerCase();
var isWindowsWeChat = 0;
/**
 * 是小微0不是小微1
 */
var isNotMicromessager = 0;
if(ua.match(/WindowsWeChat/i) == 'windowswechat'){
	isWindowsWeChat = 1;
}
if(ua.match(/MicroMessenger/i) != 'micromessenger'){
	isNotMicromessager = 1;
}
if(isWindowsWeChat == "1"){
	addListener(document, "click",function(evt){return listener(evt);}
	);
}else{
	addListener(document, "touchend",function(evt){return listener(evt);}
	);
}
    
    	
function listener(evt) {
    var evt = window.event ? window.event: evt,
    target = evt.srcElement || evt.target;
  //当触摸的不是键盘并且不是输入框的input时，隐藏键盘
    if ($(target).parents(".keyboardcontainer").size() < 1 &&
    		($(target).attr("id") == "kbbce" || $(target).parents("#kbbce").size() < 1) ) {
    	if((activityInput != undefined &&  $(target).attr("id") != undefined &&  activityInput.attr("id") == $(target).attr("id"))
	    		||(activityInput != undefined &&  $(target).attr("name") != undefined && activityInput.attr("name") == $(target).attr("name"))){
    		//如果当前点击的dom与活动dom一致。就返回吧.
    		return true;
    	}
    	if($(target).attr("id") == "kbbce" && $(target).attr("targetInputId") == activityInput.attr("id")){
    		//如果当前点击的dom与活动dom一致。就返回吧.
    		return true;
    	}
    	if($(target).parents("#kbbce").size() > 0 && $(target).parents("#kbbce").attr("targetInputId") == activityInput.attr("id")){
    		//如果当前点击的dom与活动dom一致。就返回吧.
    		return true;
    	}
    	
        hideKeyboard();
    }
}
function addListener(element, e, fn) {
    if (element.addEventListener) {
        element.addEventListener(e, fn, true);
    } else {
        element.attachEvent("on" + e, fn);
    }
}
var isExecOver = false;
var supporttypes = ["idcard","plateno","number","alphanumeric","province","vin","engno"]; 
$(document.body).ready(function(){

//	if(ua.match(/MicroMessenger/i) != 'micromessenger'){
//    	return false;
//    }
	
//	if(ua.match(/WindowsWeChat/i) == 'windowswechat'){
//		return ; //因为电脑版的微信不支持触摸事件，并且电脑不用键盘输入更快。所以就不使用软键盘了.
//	}
	
	$("input[virtual_keyboard]").each(function(){
		 

		if((isNotMicromessager == "1" || isWindowsWeChat == "1") && $(this).attr("virtual_keyboard") != "province"){
			//当用户是在PC版的微信上使用时，只允许使用省的键盘。因为他手动键盘输入更方便.
			return ;
		}
		
		if(supporttypes.indexOf($(this).attr("virtual_keyboard")) > -1 ){
			//满足支持类型.接下来做该做的事情。绑定click,focus. 调用弹出键盘方法。
			$(this).attr("readonly","readonly");
			if($(this).attr("oriWidth") == undefined){
				$(this).attr("oriWidth",$(this).width());
			}
			
			$(this).click(function(event){
				return !showKeyboard($(this));
			});
			
			$(this).focus(function(event){
				
				//事件来源有可能不是从遮罩层触发。所以要默认处理下字符位置.
				if(activityPoint == undefined ){
					if($(this).val() == ""){
						inputCharIndex = 0;
					}else{
						inputCharIndex = $(this).val().length;
					}
				}
				
				return !showKeyboard($(this));
			});
			
			$(this).change(function(){
				$(this).next().html($(this).val());
			});
			
			if($(this).attr("virtual_keyboard") != "province"){
				//在点击前，将input框前增加一层div，以便获取所在坐标
				var padding = $(this).css("padding");
				padding = padding == "" ? "0px" : padding;
				var offset = $(this).offset();
				var inputzz = $("<div  targetInputId='"+$(this).attr("id")+"' style=\"padding:"+padding+";width:"+$(this).attr("oriWidth")+"px;height:"+$(this).height()+"px;position:absolute;float:left;filter:alpha(opacity=1);-moz-opacity:1;opacity:1;\">"+$(this).val()+"</div>");
				if($(this).attr("virtual_keyboard") == "plateno"){
					var left =  offset.left ;
					inputzz.css({"left":left + "px"});
				}else{
					inputzz.css({"position":"absolute"});
					if($(this).is(":hidden") == false){
						
						$(this).css({"width":$(this).attr("oriWidth") + "px"});
						$(this).css({"position":"absolute"});
					}
					
				}
				inputzz.click(function(event){
					//当键盘类型为省时，不显示光标闪烁效果.
						activityPoint = event.offsetX;
						$("#"+$(this).attr("targetInputId")).click();
					
				});
				$(this).after(inputzz);
				$(this).css({"filter":"alpha(opacity=0)","-moz-opacity":"0","opacity":"0"});
				if($(this).attr("virtual_keyboard") == "plateno"){
					$(this).css({"position":"absolute"});
				}
			
			}
		}
		
	});
	
	//不知道为什么，该事件 return false + call return ,可以取消各个终端的长按事件。。 可能是事件的传递性吧。
	$(".keyboardcontainer").live("touchstart",function(){
		return false;
	});
	
	
	//在body上面加个键盘的div
	$(document.body).append("<div class=\"keyboardcontainer\" id=\"keyboardcontainer\"></div>");
	
	isExecOver = true;
	
});

/**
	
	弹出键盘，将当前活动input保存在全局，后面键盘输入的时候才能设置val.
	
	@param input  input对象
	
1.显示键盘，如果是Input切换，隐藏键盘，
2.根据类型，初始化键盘

*/
var activityInput = undefined;

/**
 * 当前光标所对应的字符的位置偏移数
 */
var inputCharIndex = undefined;

/**
 * 当点击输入框上层的遮罩层时，需要将位置保存在这里
 */
var activityPoint = undefined ;

function showKeyboard(input){
	//判断当前活动的Input和input是否一致，如果一致就不重新处理键盘.
	if(activityInput != undefined && 
			( (activityInput.attr("id") != undefined && input.attr("id") != undefined &&  activityInput.attr("id") != "" && input.attr("id") != "" &&  activityInput.attr("id") == input.attr("id"))
					|| (activityInput.attr("name") != undefined && input.attr("name") != undefined && activityInput.attr("name") != "" && input.attr("name") != "" && activityInput.attr("name") == input.attr("name")))){
		//用id或name来判断，暂时没想到其他好的判断方式.
		
		return true;
	}
	
	//当检测到属性kbreadonly值为1时，表示禁止用软键盘输入，则不弹出键盘
	if(input.attr("kbreadonly") == "1"){
		return false;
	}
	
	hideKeyboard();

	activityInput = input;
	var keyboard = new Object();
	
	//初始化键盘
	if(input.attr("virtual_keyboard") == "idcard"){ //身份证
		keyboard = new IdCard_Keyboard(input);
	}else if(input.attr("virtual_keyboard") == "number"){ //纯数字
		keyboard = new Number_Keyboard(input);
	}else if(input.attr("virtual_keyboard") == "plateno"){  //车牌号
		keyboard = new PalteNo_Keyboard(input);
	}else if(input.attr("virtual_keyboard") == "alphanumeric"){ //数字字母
		keyboard = new AlphaNumeric_Keyboard(input);
	}else if(input.attr("virtual_keyboard") == "province"){ //省
		keyboard = new Province_Keyboard(input);
	}else if(input.attr("virtual_keyboard") == "vin"){ //车架号
		keyboard = new VIN_Keyboard(input);
	}else if(input.attr("virtual_keyboard") == "engno"){ //发动机号
		keyboard = new EngNo_Keyboard(input);
	}else{
		//不支持的键盘类型.
		return false;
	}
	
	
	//判断输入框是否要求在输入之前要求删除input框内容.
	if(input.attr("afterInputDelContent") == "1" || input.attr("afterInputDelContent") == "true"){
		input.val("");
	}
	
	
	
	
	keyboard.init();
	
	/**
	 * 键盘显示前，需要判断点击位置，然后自动向上滚.
	 * 1. 获取浏览器的网页可见高
	 * 2. 获取浏览器滚动高度
	 * 3. 获取元素在浏览器的坐标.
	 * 4. 获取键盘高度.
	 * 4. 计算需滚动高度  浏览器可见高-元素坐标高 < 键盘高度
	 * 5. 计算是否可以滚动，如果可以滚动，直接滚动
	 * 6. 不能滚动需要在body增加空白，拉长网页整体高度，然后滚动.
	 */
	
	var browerHeight = window.innerHeight;
	var domOffset = input.offset();
	var domHeight = input.outerHeight();
	var kbHeight = $(".keyboardcontainer").height();
	var scrolled = $(document.body).scrollTop();
	var scollHeight = browerHeight - ( domOffset.top - scrolled );
	if(scollHeight < (kbHeight + domHeight + 10)){
		$(document.body).append("<div id=\"kbemptydom\" style=\"width:100%;background-color:#ffffff;height:"+kbHeight+"px\"></div>")
		$(document.body).scrollTop((kbHeight + domHeight + 10) - scollHeight + scrolled); //滚动时，需要+已滚动的 ，因为他是从0开始。
	}else if(scrolled > domOffset.top){
		//当元素被滚动到显示区域的上面的时候，弹出键盘的时候，需要将其显示下来.
		$(document.body).scrollTop(domOffset.top - kbHeight - domHeight);
	}
	
	
	
	if(input.attr("virtual_keyboard") != "province"){//当键盘类型为省时，不显示光标闪烁效果.
		//增加光标闪烁效果
		var padding = input.css("padding");
		padding = padding == "" ? "0px" : padding;
		$(document.body).append("<div id=\"kbbce\" targetInputId='"+input.attr("id")+"' style=\"padding:"+padding+";width:"+input.attr("oriWidth")+"px;height:"+input.height()+"px;position:absolute;top:"+domOffset.top+"px;left:"+domOffset.left+"px;\"><div style=\"float:left;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;padding-right:5px;\">"+input.val()+"</div><div style=\"width:1px;height:100%;background-color:black;float:left;height:"+input.height()+"px;\" class=\"blink\" ></div></div>");
		$("#kbbce").click(function(e){
			var tmp = $("<div style='display: inline;'>"+$(this).find("div:first").html()+"</div>");
			$(document.body).append(tmp);
			//$("#kbbce div:last").css({"position":"absolute","left":""+e.offsetX+"px"});
			var tmpStr = $(this).find("div:first").html();
			var arr = [];
			tmp.html("");
			for(var i = 0 ;i<=tmpStr.length;i++){
				arr[i] = tmp.width();
				tmp.html(tmp.html() + tmpStr.substring(i,i+1));
			}
			
			for(var j = 0;j<=arr.length;j++){
				if(parseFloat(arr[j]) < e.offsetX){
					if(activityInput.attr("virtual_keyboard") == "plateno"){
						var left = parseInt(padding.replace("px", ""))  ;
						$("#kbbce div:last").css({"position":"absolute","left":""+(arr[j] + left)+"px"});
					}else
						$("#kbbce div:last").css({"position":"absolute","left":""+arr[j]+"px"});
					inputCharIndex = j;
				}
			}
			
			tmp.remove();
		});
		
		if(activityPoint != undefined ){
			var tmp = $("<div style='display: inline;'>"+$("#kbbce").find("div:first").html()+"</div>");
			$(document.body).append(tmp);
			//$("#kbbce div:last").css({"position":"absolute","left":""+e.offsetX+"px"});
			var tmpStr = $("#kbbce").find("div:first").html();
			var arr = [];
			tmp.html("");
			for(var i = 0 ;i<=tmpStr.length;i++){
				arr[i] = tmp.width();
				tmp.html(tmp.html() + tmpStr.substring(i,i+1));
			}
			
			for(var j = 0;j<=arr.length;j++){
				if(parseFloat(arr[j]) < activityPoint){
					if(activityInput.attr("virtual_keyboard") == "plateno"){
						var left = parseInt(padding.replace("px", ""))  ;
						$("#kbbce div:last").css({"position":"absolute","left":""+(arr[j] + left)+"px"});
					}else
						$("#kbbce div:last").css({"position":"absolute","left":""+arr[j]+"px"});
					inputCharIndex = j;
				}
			}
			
			tmp.remove();
		}
		
	}
	
	
	
	$(".keyboardcontainer").fadeIn(100);
	
	//$(input).hide();
	
	return true;
}

/**
隐藏


*/
function hideKeyboard(){
	$(".keyboardcontainer").hide();
	if(activityInput != undefined){
		//activityInput.removeAttr("readonly"); //移除该属性，其他校验框架可以出发focus事件
		activityInput.blur();  //触发事件以进行其他事件的传递
		//打开键盘时会隐藏input,隐藏键盘时需要显示
		activityInput.show();
	}
	
	activityInput = undefined;
	//删除键盘的空白间距
	$("#kbemptydom").remove();
	//删除光标闪烁效果.
	$("#kbbce").remove();
	
}


/**
 * 当文本框内容改变时，调用的方法.
 */
function change(obj){
//	$("#kbbce div:first").html($(obj).val());
//	$(activityInput).next().html($(obj).val());
}

/*************************************************** 下面是键盘具体类型对象的代码. **/

	
/** 纯数字键盘 **/
function Number_Keyboard(input){
	this.currentInput = input;
	
}

Number_Keyboard.prototype.jsstart = function (kb){
	this.time += 200;
//	this.delStartTime = setInterval(function(){
//		kb.time += 200;
//		var c = kb.currentInput.val();
//		if(c != "" && c.length > 0){
//			c = c.substring(0,c.length - 1);
//			kb.currentInput.val(c);
//			$("#keyboardinputcontent").html(kb.currentInput.val());
//		}
//	}, 200);
	this.delStartTime = setTimeout(function(){
		kb.time += 200;
		var c = kb.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			kb.currentInput.val(c);
			$("#keyboardinputcontent").html(kb.currentInput.val());
			change(kb.currentInput);
		}
		kb.jsstart(kb)},200);
}
Number_Keyboard.prototype.jsstop = function (kb){
	kb.time = 0;
	//clearInterval(this.delStartTime);
	clearTimeout(kb.delStartTime);
}
Number_Keyboard.prototype.init = function(){
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["1","2","3"],["4","5","6"],["7","8","9"],["","0","删除"]];
	var numberinputcontainer = $("<div class=\"numberinputcontainer\"></div>");
	var hidecontainer = $("<div class=\"numberinputhidecontainer\"></div>");
	hidecontainer.bind("touchstart",function(){
		hideKeyboard();
	});
	numberinputcontainer.append(hidecontainer);
	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = undefined;
			
			if(btnarr[j] == "删除"){
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn delbtn \">&nbsp;</a>");
				a.bind("touchstart",function(){
					kb.click($(this));
					kb.jsstart(kb);
					$(this).addClass("numberbtnchecked");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
			}else{
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn\">"+btnarr[j]+"</a>");
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
			}
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
	}
	$(".keyboardcontainer").append(numberinputcontainer);
}
Number_Keyboard.prototype.click = function(kbtn){
	if($(kbtn).text() == ""){ //空白区域不处理.
		return;
	}
	if($(kbtn).text() == "隐藏"){
		hideKeyboard();
		return;
	}
	if($(kbtn).text() == "删除" || $(kbtn).hasClass("delbtn")){
		var c = this.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			this.currentInput.val(c);
			change(this.currentInput);
			
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
		}
		return;
	}
	if($(kbtn).text() == "清空"){
		this.currentInput.val("");
		change(this.currentInput);
		return;
	}
	
	if($(kbtn).text() == "确认"){
		hideKeyboard();
		return ;
	}
	
	if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
			this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
		return ;
	}
	
	//分解字符串，并根据光标偏移重组字符串并显示
	var str = this.currentInput.val();
	var beforeStr = str.substring(0,inputCharIndex);
	var after = str.substring(inputCharIndex,str.length);
	str = beforeStr + $(kbtn).text() + after;
	var tmp = $("<div style='display: inline;'>"+beforeStr + $(kbtn).text() +"</div>");
	$(document.body).append(tmp);
	$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
	inputCharIndex = inputCharIndex + 1;
	tmp.remove();
	
	
	this.currentInput.val(str);
	change(this.currentInput);
}


/** 身份证号 **/
function IdCard_Keyboard(input){
	this.currentInput = input;
}
IdCard_Keyboard.prototype.jsstart = function (kb){
	this.time += 200;
//	this.delStartTime = setInterval(function(){
//		kb.time += 200;
//		var c = kb.currentInput.val();
//		if(c != "" && c.length > 0){
//			c = c.substring(0,c.length - 1);
//			kb.currentInput.val(c);
//			$("#keyboardinputcontent").html(kb.currentInput.val());
//		}
//	}, 200);
	this.delStartTime = setTimeout(function(){
		kb.time += 200;
		var c = kb.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			kb.currentInput.val(c);
			$("#keyboardinputcontent").html(kb.currentInput.val());
			change(kb.currentInput);
		}
		kb.jsstart(kb)},200);
}
IdCard_Keyboard.prototype.jsstop = function (kb){
	kb.time = 0;
	//clearInterval(this.delStartTime);
	clearTimeout(kb.delStartTime);
}
IdCard_Keyboard.prototype.init = function(){
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["1","2","3"],["4","5","6"],["7","8","9"],["X","0","删除"]];
	var numberinputcontainer = $("<div class=\"numberinputcontainer\"></div>");
	var hidecontainer = $("<div class=\"numberinputhidecontainer\"></div>");
	hidecontainer.bind("touchstart",function(){
		hideKeyboard();
	});
	numberinputcontainer.append(hidecontainer);
	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = undefined;
			
			if(btnarr[j] == "删除"){
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn delbtn \">&nbsp;</a>");
				a.bind("touchstart",function(){
					kb.click($(this));
					kb.jsstart(kb);
					$(this).addClass("numberbtnchecked");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
			}else{
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn\">"+btnarr[j]+"</a>");
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
			}
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
	}
	$(".keyboardcontainer").append(numberinputcontainer);
}
IdCard_Keyboard.prototype.click = function(kbtn){

	if($(kbtn).text() == "删除" || $(kbtn).hasClass("delbtn")){
		var c = this.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			this.currentInput.val(c);
			change(this.currentInput);
			
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
		}
		return;
	}
	
	if($(kbtn).text() == "确认"){
		hideKeyboard();
		return ;
	}
	
	if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
			this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
		return ;
	}
	
	//分解字符串，并根据光标偏移重组字符串并显示
	var str = this.currentInput.val();
	var beforeStr = str.substring(0,inputCharIndex);
	var after = str.substring(inputCharIndex,str.length);
	str = beforeStr + $(kbtn).text() + after;
	var tmp = $("<div style='display: inline;'>"+beforeStr + $(kbtn).text() +"</div>");
	$(document.body).append(tmp);
	$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
	inputCharIndex = inputCharIndex + 1;
	tmp.remove();
	
	
	this.currentInput.val(str);
	change(this.currentInput);
}

/** 车牌号 **/
function PalteNo_Keyboard(input){
	this.currentInput = input;
	
}
PalteNo_Keyboard.prototype.jsstart = function (kb){
	this.time += 200;
//	this.delStartTime = setInterval(function(){
//		kb.time += 200;
//		var c = kb.currentInput.val();
//		if(c != "" && c.length > 0){
//			c = c.substring(0,c.length - 1);
//			kb.currentInput.val(c);
//			$("#keyboardinputcontent").html(kb.currentInput.val());
//		}
//	}, 200);
	this.delStartTime = setTimeout(function(){
		kb.time += 200;
		var c = kb.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			var padding = activityInput.css("padding");
			padding = padding == "" ? "0px" : padding;
			var left = parseInt(padding.replace("px", "")) + tmp.width();
			$("#kbbce div:last").css({"position":"absolute","left":""+left+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			kb.currentInput.val(c);
			$("#keyboardinputcontent").html(kb.currentInput.val());
			change(kb.currentInput);
		}
		kb.jsstart(kb)},200);
}
PalteNo_Keyboard.prototype.jsstop = function (kb){
	kb.time = 0;
	//clearInterval(this.delStartTime);
	clearTimeout(kb.delStartTime);
}
PalteNo_Keyboard.prototype.init = function(){
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["1","2","3","4","5","6","7","8","9","0"],["Q","W","E","R","T","Y","U","P"],["A","S","D","F","G","H","J","K","L","删除"],["Z","X","C","V","B","N","M","确认"]];
	var numberinputcontainer = $("<div class=\"alphanumericinputcontainer\"></div>");
	var contentcontainer = $("<div class=\"alphanumericinputcontentcontainer\"><div id='keyboardinputcontent' style=\"height:30px;line-height:30px;float:left;width:80%;overflow:hidden;\"></div>	</div>");

//	palteDel.click(function(){
//		kb.click(this);
//	});
	
	numberinputcontainer.append(contentcontainer);
	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = undefined;
			
			if(btnarr[j] == "删除"){
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn delbtn \">&nbsp;</a>");
				a.bind("touchstart",function(){
					kb.click($(this));
					kb.jsstart(kb);
					$(this).addClass("numberbtnchecked");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
			}else{
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn\">"+btnarr[j]+"</a>");
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
			}
			
			if(btnarr[j] == "确认"){
				a.addClass("fnbtn");
				a.addClass("confirm");
			}
//			a.click(function(){
//				kb.click(this);
//			});
			
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
	}
	$(".keyboardcontainer").append(numberinputcontainer);
	$("#keyboardinputcontent").html(this.currentInput.val());
}
PalteNo_Keyboard.prototype.click = function(kbtn){

	if($(kbtn).text() == "删除" || $(kbtn).hasClass("delbtn")){
		var c = this.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			this.currentInput.val(c);
			$("#keyboardinputcontent").html(this.currentInput.val());
			change(this.currentInput);
			
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			var padding = activityInput.css("padding");
			padding = padding == "" ? "0px" : padding;
			var left = parseInt(padding.replace("px", "")) + tmp.width();
			$("#kbbce div:last").css({"position":"absolute","left":""+left+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
		}
		return;
	}
	
	if($(kbtn).text() == "确认"){
		hideKeyboard();
		return ;
	}
	if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
			this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
		return ;
	}
	//分解字符串，并根据光标偏移重组字符串并显示
	var str = this.currentInput.val();
	var beforeStr = str.substring(0,inputCharIndex);
	var after = str.substring(inputCharIndex,str.length);
	str = beforeStr + $(kbtn).text() + after;
	var tmp = $("<div style='display: inline;'>"+beforeStr + $(kbtn).text() +"</div>");
	$(document.body).append(tmp);
	var padding = activityInput.css("padding");
	padding = padding == "" ? "0px" : padding;
	var left = parseInt(padding.replace("px", "")) + tmp.width();
	$("#kbbce div:last").css({"position":"absolute","left":""+left+"px"});
	inputCharIndex = inputCharIndex + 1;
	tmp.remove();
	
	
	this.currentInput.val(str);
	$("#keyboardinputcontent").html(this.currentInput.val());
	change(this.currentInput);
}

/** 车架号 **/
function VIN_Keyboard(input){
	this.currentInput = input;
	
}
VIN_Keyboard.prototype.jsstart = function (kb){
	this.time += 200;
//	this.delStartTime = setInterval(function(){
//		kb.time += 200;
//		var c = kb.currentInput.val();
//		if(c != "" && c.length > 0){
//			c = c.substring(0,c.length - 1);
//			kb.currentInput.val(c);
//			$("#keyboardinputcontent").html(kb.currentInput.val());
//		}
//	}, 200);
	this.delStartTime = setTimeout(function(){
		kb.time += 200;
		var c = kb.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			kb.currentInput.val(c);
			$("#keyboardinputcontent").html(kb.currentInput.val());
			change(kb.currentInput);
		}
		kb.jsstart(kb)},200);
}
VIN_Keyboard.prototype.jsstop = function (kb){
	kb.time = 0;
	//clearInterval(this.delStartTime);
	clearTimeout(kb.delStartTime);
}
VIN_Keyboard.prototype.init = function(){
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["1","2","3","4","5","6","7","8","9","0"],["W","E","R","T","Y","U","P"],["A","S","D","F","G","H","J","K","L","删除"],["Z","X","C","V","B","N","M","确认"]];
	var numberinputcontainer = $("<div class=\"alphanumericinputcontainer\"></div>");
	var contentcontainer = $("<div class=\"alphanumericinputcontentcontainer\"><div id='keyboardinputcontent' style=\"height:30px;line-height:30px;float:left;width:80%;overflow:hidden;\"></div>	</div>");

	numberinputcontainer.append(contentcontainer);
	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = undefined;
			if(btnarr[j] == "删除"){
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn delbtn\">&nbsp;</a>");
				a.bind("touchstart",function(){
					kb.click($(this));
					kb.jsstart(kb);
					$(this).addClass("numberbtnchecked");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
			}else{
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn\">"+btnarr[j]+"</a>");
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
			}
			if(btnarr[j] == "确认"){
				a.addClass("fnbtn");
				a.addClass("confirm")
			}
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
	}
	$(".keyboardcontainer").append(numberinputcontainer);
	$("#keyboardinputcontent").html(this.currentInput.val());
}
VIN_Keyboard.prototype.click = function(kbtn){

	if($(kbtn).text() == "删除" || $(kbtn).hasClass("delbtn")){
		var c = this.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			this.currentInput.val(c);
			$("#keyboardinputcontent").html(this.currentInput.val());
			change(this.currentInput);
			
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			
		}
		return;
	}
	
	if($(kbtn).text() == "确认"){
		hideKeyboard();
		return ;
	}
	if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
			this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
		return ;
	}
	
	//分解字符串，并根据光标偏移重组字符串并显示
	var str = this.currentInput.val();
	var beforeStr = str.substring(0,inputCharIndex);
	var after = str.substring(inputCharIndex,str.length);
	str = beforeStr + $(kbtn).text() + after;
	var tmp = $("<div style='display: inline;'>"+beforeStr + $(kbtn).text() +"</div>");
	$(document.body).append(tmp);
	$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
	inputCharIndex = inputCharIndex + 1;
	tmp.remove();
	
	
	this.currentInput.val(str);
	$("#keyboardinputcontent").html(	this.currentInput.val());
	change(this.currentInput);
}

/** 发动机号 **/
function EngNo_Keyboard(input){
	this.currentInput = input;
	
}
EngNo_Keyboard.prototype.jsstart = function (kb){
	this.time += 200;
//	this.delStartTime = setInterval(function(){
//		kb.time += 200;
//		var c = kb.currentInput.val();
//		if(c != "" && c.length > 0){
//			c = c.substring(0,c.length - 1);
//			kb.currentInput.val(c);
//			$("#keyboardinputcontent").html(kb.currentInput.val());
//		}
//	}, 200);
	this.delStartTime = setTimeout(function(){
		kb.time += 200;
		var c = kb.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			kb.currentInput.val(c);
			$("#keyboardinputcontent").html(kb.currentInput.val());
			change(kb.currentInput);
		}
		kb.jsstart(kb)},200);
}
EngNo_Keyboard.prototype.jsstop = function (kb){
	kb.time = 0;
	//clearInterval(this.delStartTime);
	clearTimeout(kb.delStartTime);
}
EngNo_Keyboard.prototype.init = function(){
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["1","2","3","4","5","6","7","8","9","0"],["Q","W","E","R","T","Y","U","I","O","P"],["A","S","D","F","G","H","J","K","L","删除"],["-","Z","X","C","V","B","N","M","确认"]];
	var numberinputcontainer = $("<div class=\"alphanumericinputcontainer\"></div>");
	var contentcontainer = $("<div class=\"alphanumericinputcontentcontainer\"><div id='keyboardinputcontent' style=\"height:30px;line-height:30px;float:left;width:80%;overflow:hidden;\"></div>	</div>");
	
	numberinputcontainer.append(contentcontainer);
	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = undefined;
			if(btnarr[j] == "删除"){
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn delbtn\">&nbsp;</a>");
				a.bind("touchstart",function(){
					kb.click($(this));
					kb.jsstart(kb);
					$(this).addClass("numberbtnchecked");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
			}else{
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn\">"+btnarr[j]+"</a>");
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
			}
			if(btnarr[j] == "确认"){
				a.addClass("fnbtn");
				a.addClass("confirm");
			}
//			a.bind("click",function(){
//				kb.click(this);
//				//$(this).addClass("numberbtnchecked");
//			});
			
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
		
		
	}
	$(".keyboardcontainer").append(numberinputcontainer);
	//输入内容左右滚动.
	$("#keyboardinputcontent").bind("touchstart",function(event){
		this.startX = event.originalEvent.changedTouches[0].clientX;
	});
	$("#keyboardinputcontent").bind("touchmove",function(event){
		this.count =  this.count == undefined ? 1 : this.count  += 1;
		this.moveX = event.originalEvent.changedTouches[0].clientX;
		//$("#input").val("3:"+this.count+".:" + $("#keyboardinputcontent")[0].scrollLeft + "a:" + ($("#keyboardinputcontent")[0].scrollLeft - 10) + ",b:" + ($("#keyboardinputcontent")[0].scrollLeft + 10));
		if(this.moveX > this.startX){ 
			//向左滑
			if($("#keyboardinputcontent")[0].scrollLeft - 1 > 0){
				$("#keyboardinputcontent")[0].scrollLeft = $("#keyboardinputcontent")[0].scrollLeft - 1; 
			}else {
				$("#keyboardinputcontent")[0].scrollLeft = 0;
			}
			
		}else if(this.moveX < this.startX){
			//向右滑
			if($("#keyboardinputcontent")[0].scrollLeft + 1 < $("#keyboardinputcontent")[0].scrollWidth){
				$("#keyboardinputcontent")[0].scrollLeft = $("#keyboardinputcontent")[0].scrollLeft + 1; 
			}else {
				$("#keyboardinputcontent")[0].scrollLeft = $("#keyboardinputcontent")[0].scrollWidth;
			}
		}
		//$("#input").val("<br>Touch end (" + event.originalEvent.changedTouches[0].clientX + "," + event.originalEvent.changedTouches[0].clientY + ")");
		
	});
	$("#keyboardinputcontent").bind("touchend",function(){
		
	});
	$("#keyboardinputcontent").bind("touchcancel",function(){
		
	});
	$("#keyboardinputcontent").html(this.currentInput.val());
	
}
EngNo_Keyboard.prototype.click = function(kbtn){
	if($(kbtn).text() == "删除" || $(kbtn).hasClass("delbtn")){
		var c = this.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			this.currentInput.val(c);
			$("#keyboardinputcontent").html(this.currentInput.val());
			change(this.currentInput);
			
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
		}
		return;
	}
	
	if($(kbtn).text() == "确认"){
		hideKeyboard();
		return ;
	}
	
	if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
			this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
		return ;
	}
	//分解字符串，并根据光标偏移重组字符串并显示
	var str = this.currentInput.val();
	var beforeStr = str.substring(0,inputCharIndex);
	var after = str.substring(inputCharIndex,str.length);
	str = beforeStr + $(kbtn).text() + after;
	var tmp = $("<div style='display: inline;'>"+beforeStr + $(kbtn).text() +"</div>");
	$(document.body).append(tmp);
	$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
	inputCharIndex = inputCharIndex + 1;
	tmp.remove();
	
	
	this.currentInput.val(str);
	$("#keyboardinputcontent").html(this.currentInput.val());
	//当字数输入很多的时候，支持自动向右滚动.
	$("#keyboardinputcontent")[0].scrollLeft=$("#keyboardinputcontent")[0].scrollWidth;
	change(this.currentInput);
}



/** 数字字母 **/
function AlphaNumeric_Keyboard(input){
	this.currentInput = input;
	
}
AlphaNumeric_Keyboard.prototype.jsstart = function (kb){
	this.time += 200;
//	this.delStartTime = setInterval(function(){
//		kb.time += 200;
//		var c = kb.currentInput.val();
//		if(c != "" && c.length > 0){
//			c = c.substring(0,c.length - 1);
//			kb.currentInput.val(c);
//			$("#keyboardinputcontent").html(kb.currentInput.val());
//		}
//	}, 200);
	this.delStartTime = setTimeout(function(){
		kb.time += 200;
		var c = kb.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
			kb.currentInput.val(c);
			$("#keyboardinputcontent").html(kb.currentInput.val());
			change(kb.currentInput);
		}
		kb.jsstart(kb)},200);
}
AlphaNumeric_Keyboard.prototype.jsstop = function (kb){
	kb.time = 0;
	//clearInterval(this.delStartTime);
	clearTimeout(kb.delStartTime);
}
AlphaNumeric_Keyboard.prototype.init = function(){
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["1","2","3","4","5","6","7","8","9","0"],["Q","W","E","R","T","Y","U","I","O","P"],["A","S","D","F","G","H","J","K","L","删除"],["Z","X","C","V","B","N","M","确认"]];
	var numberinputcontainer = $("<div class=\"alphanumericinputcontainer\"></div>");
	var contentcontainer = $("<div class=\"alphanumericinputcontentcontainer\"><div id='keyboardinputcontent' style=\"height:30px;line-height:30px;float:left;width:80%;overflow:hidden;\"></div>	</div>");
	
	numberinputcontainer.append(contentcontainer);
	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = undefined;
			if(btnarr[j] == "删除"){
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn delbtn\">&nbsp;</a>");
				a.bind("touchstart",function(){
					kb.click($(this));
					kb.jsstart(kb);
					$(this).addClass("numberbtnchecked");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeClass("numberbtnchecked");
					kb.jsstop(kb);
				});
			}else{
				a = $("<a href=\"javascript:void(0)\" class=\"numberbtn\">"+btnarr[j]+"</a>");
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
				});
			}
			if(btnarr[j] == "确认"){
				a.addClass("fnbtn");
				a.addClass("confirm");
			}
//			a.bind("click",function(){
//				kb.click(this);
//				//$(this).addClass("numberbtnchecked");
//			});
			
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
		
		
	}
	$(".keyboardcontainer").append(numberinputcontainer);
	//输入内容左右滚动.
	$("#keyboardinputcontent").bind("touchstart",function(event){
		this.startX = event.originalEvent.changedTouches[0].clientX;
	});
	$("#keyboardinputcontent").bind("touchmove",function(event){
		this.count =  this.count == undefined ? 1 : this.count  += 1;
		this.moveX = event.originalEvent.changedTouches[0].clientX;
		//$("#input").val("3:"+this.count+".:" + $("#keyboardinputcontent")[0].scrollLeft + "a:" + ($("#keyboardinputcontent")[0].scrollLeft - 10) + ",b:" + ($("#keyboardinputcontent")[0].scrollLeft + 10));
		if(this.moveX > this.startX){ 
			//向左滑
			if($("#keyboardinputcontent")[0].scrollLeft - 1 > 0){
				$("#keyboardinputcontent")[0].scrollLeft = $("#keyboardinputcontent")[0].scrollLeft - 1; 
			}else {
				$("#keyboardinputcontent")[0].scrollLeft = 0;
			}
			
		}else if(this.moveX < this.startX){
			//向右滑
			if($("#keyboardinputcontent")[0].scrollLeft + 1 < $("#keyboardinputcontent")[0].scrollWidth){
				$("#keyboardinputcontent")[0].scrollLeft = $("#keyboardinputcontent")[0].scrollLeft + 1; 
			}else {
				$("#keyboardinputcontent")[0].scrollLeft = $("#keyboardinputcontent")[0].scrollWidth;
			}
		}
		//$("#input").val("<br>Touch end (" + event.originalEvent.changedTouches[0].clientX + "," + event.originalEvent.changedTouches[0].clientY + ")");
		
	});
	$("#keyboardinputcontent").bind("touchend",function(){
		
	});
	$("#keyboardinputcontent").bind("touchcancel",function(){
		
	});
	$("#keyboardinputcontent").html(this.currentInput.val());
	
}
AlphaNumeric_Keyboard.prototype.click = function(kbtn){
	if($(kbtn).text() == "删除" || $(kbtn).hasClass("delbtn")){
		var c = this.currentInput.val();
		if(c != "" && c.length > 0){
			var beforeStr = c.substring(0,inputCharIndex - 1);
			var after = c.substring(inputCharIndex,c.length);
			c = beforeStr + after;
			this.currentInput.val(c);
			$("#keyboardinputcontent").html(this.currentInput.val());
			change(this.currentInput);
			
			var tmp = $("<div style='display: inline;'>"+beforeStr+"</div>");
			$(document.body).append(tmp);
			$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
			inputCharIndex = inputCharIndex - 1;
			tmp.remove();
		}
		return;
	}
	
	if($(kbtn).text() == "确认"){
		hideKeyboard();
		return ;
	}
	
	if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
			this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
		return ;
	}
	//分解字符串，并根据光标偏移重组字符串并显示
	var str = this.currentInput.val();
	var beforeStr = str.substring(0,inputCharIndex);
	var after = str.substring(inputCharIndex,str.length);
	str = beforeStr + $(kbtn).text() + after;
	var tmp = $("<div style='display: inline;'>"+beforeStr + $(kbtn).text() +"</div>");
	$(document.body).append(tmp);
	$("#kbbce div:last").css({"position":"absolute","left":""+tmp.width()+"px"});
	inputCharIndex = inputCharIndex + 1;
	tmp.remove();
	
	
	this.currentInput.val(str);
	$("#keyboardinputcontent").html(this.currentInput.val());
	//当字数输入很多的时候，支持自动向右滚动.
	$("#keyboardinputcontent")[0].scrollLeft=$("#keyboardinputcontent")[0].scrollWidth;
	change(this.currentInput);
}






/** 选择车牌号，省 **/
function Province_Keyboard(input){
	this.currentInput = input;
	
}

Province_Keyboard.prototype.init = function(){
	var kb = this;	
	$(".keyboardcontainer").empty(); //清空之前键盘的内容。重新初始化.
	var btns = [["粤","川","鄂","甘","贵","桂","黑","沪"],["吉","冀","津","晋","京","辽","鲁","蒙"],["闽","宁","青","琼","陕","苏","皖","湘"],["新","渝","豫","云","藏","赣","浙"]];
	var numberinputcontainer = $("<div class=\"provinceinputcontainer\"></div>");

	for(var i = 0 ;i<btns.length;i++){
		var btnarr = btns[i];
		var btnszone = $("<div class=\"btnszone clear\"></div>");
		for(var j = 0 ;j<btnarr.length;j++){
			var a = $("<div  class=\"numberbtn\">"+btnarr[j]+"</div>");
//			a.bind("click",function(){
//				kb.click(this);
//				//$(this).addClass("numberbtnchecked");
//			});
			
			if(isWindowsWeChat == 1){
				//pc版微信使用click.因为他不支持touch事件
				a.bind("click",function(e){
					kb.click(this);
				});
			}
			if(isWindowsWeChat == 0){
				//手机版微信使用touch.因为这个事件体验更改.
				a.bind("touchstart",function(){
					kb.click(this);
					$(this).attr("style","background-color: #a1d5e9;");
					return false;
					
				});
				
				a.bind("touchend",function(){
					$(this).removeAttr("style","background-color: #a1d5e9;");
					return false;
				});
				a.bind("touchcancel",function(eventObject){
					$(this).removeAttr("style","background-color: #a1d5e9;");
					return false;
				});
			}
			
			btnszone.append(a);
		}
		numberinputcontainer.append(btnszone);
		
		
	}
	$(".keyboardcontainer").append(numberinputcontainer);
	$("#keyboardinputcontent").html(this.currentInput.val());
	
	
}
Province_Keyboard.prototype.click = function(kbtn){
	
	//直接将之前选择的省替换掉.
	this.currentInput.val($(kbtn).text());
	
	if(this.currentInput.attr("isMaxHidden") == "1" || this.currentInput.attr("isMaxHidden") == "true"){
		if(this.currentInput.attr("maxlength") != undefined && this.currentInput.attr("maxlength") > 0 && 
				this.currentInput.val().length + 1 > this.currentInput.attr("maxlength")){
			//添加自动隐藏属性.
			hideKeyboard();
		}
	}
	
}

/**
 * 因为元素在隐藏的时候，无法获取到其宽高offset等位置，所以当input显示出来的时候，需要调用该方法。以通知键盘更新dom
 */
function updateKeyBoard(){
	
	
//		$("input[virtual_keyboard]").each(function(){
//			if((isNotMicromessager != "1" || isWindowsWeChat != "1") && $(this).attr("virtual_keyboard") != "province"){
//				console.log($(this).attr("id") + "," + $(this).width()+","+$(this).val());
//				$(this).attr("oriWidth",$(this).width());
//				var oriWidth = $(this).width();
//				if($(this).next().attr("targetinputid") != ""){
//					var inputzz = $(this).next();
//					var padding = $(this).css("padding");
//					padding = padding == "" ? "0px" : padding;
//					inputzz.css({"padding":padding,"width":oriWidth,"height":$(this).height()});
//					inputzz.html($(this).val());
//				}
//			}
//		});
	
}



//重写jquery方法，以便当键盘在输入时，可以自动控制键盘内容的显示.而不用再在其他地方写额外的.js
(function ($) {
  var originalVal = $.fn.val;
  $.fn.val = function(value) {
	  var result =  undefined;
	if (arguments.length >= 1) {
		result =  originalVal.call(this, value);
		  
	    if (typeof value != undefined && (isNotMicromessager != "1" && isWindowsWeChat != "1")
  			  && supporttypes.indexOf($(this).attr("virtual_keyboard")) > -1
			  && $(this).attr("virtual_keyboard") != "province") {
	    	$("#kbbce div:first").html($(this).val());
	    	if($(this).next().attr("targetinputid") == $(this).attr("id")){
	    		$(this).next().html($(this).val());
	    	}
	    	
	    }
    }else{
    	result =  originalVal.call(this);
    }
    return result;
  };
  $.override ={'show': $.fn.show, 'hide': $.fn.hide};

  $.each($.override,function(M,F){

          var m=M.replace( /^\w/, function(r){ return r.toUpperCase(); });

          $.fn[M] = function(speed, easing, callback) {

              var args=[speed||0,easing||'',callback||function(){}];

              if( $.isFunction(speed)){
                  args[2]=speed;
                  args[0]=0;
              }                   
              else if( $.isFunction(easing)){
                  args[2]=easing;
                  args[1]='';
              }                   

              if(!this.selector){
                  F.apply(this, arguments);
                  return this;
              }
              var result = this.each(function () {
                  var obj = $(this),
                      oldCallback = args[args.length-1],
                      newCallback = function () {
                          if ($.isFunction(oldCallback)){
                              oldCallback.apply(obj);
                          }
                  };

                  args[args.length-1]=newCallback;
                  //alert(args);
                  F.apply(obj,args);
                  
                 
                  
              });
              

              $(this).find("input").each(function(){
            	  //更新键盘.
            	  if(isExecOver && (isNotMicromessager != "1" && isWindowsWeChat != "1")
            			  && supporttypes.indexOf($(this).attr("virtual_keyboard")) > -1
            			  && $(this).attr("virtual_keyboard") != "province"){
            	  var oriWidth = $(this).width();
            	  $(this).attr("oriWidth",$(this).width());
            	  $(this).css({"position":"absolute","width":$(this).attr("oriWidth")});
            	  
            		
            		if($(this).next().attr("targetinputid") != ""){
            			var inputzz = $(this).next();
            			var padding = $(this).css("padding");
            			padding = padding == "" ? "0px" : padding;
            			inputzz.css({"padding":padding,"width":oriWidth,"height":$(this).height()});
            			inputzz.html($(this).val());
            		}
            	  
            	  }
              });
              
              return result;
              
              
          };
  });
})(jQuery);

