/**
 * 添加常用的校验规则
 * 
 * 
 */


/*日期比较方法，设置在结束时间那里*/
jQuery.validator.addMethod('compareDate',function(value, element, param) {
            //var startDate = jQuery(param).val() + ":00";补全yyyy-MM-dd HH:mm:ss格式
            //value = value + ":00";
            
            var startDate = jQuery(param).val();
            
            var date1 = new Date(Date.parse(startDate.replace("-", "/")));
            var date2 = new Date(Date.parse(value.replace("-", "/")));
            return date1 < date2;
 },"结束时间必须大于开始时间");

 /*判断手机号码*/
 jQuery.validator.addMethod("mobile", function(value, element) {
	    var length = value.length;
	    var mobile =  /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
	    return this.optional(element) || (length == 11 && mobile.test(value));
	}, "手机号码格式错误");
 
 jQuery.validator.addMethod("isIdCardNo", function (value, element) {
     return this.optional(element) || isIdCardNo(value);
 }, "请正确输入您的身份证号码");
 
 jQuery.validator.addMethod("regexPassword", function(value, element) {  
	    return this.optional(element) || /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/.test(value);  
	}, "密码必须包含数字、大写和小写英文字母");
 jQuery.validator.addMethod("isCarLicenseNo", function(value, element) {  
	 return this.optional(element) || isCarLicenseNo(value);  
 }, "车牌号码不正确");
 
//增加身份证验证
 function isIdCardNo(num) {
     var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
     var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
     var varArray = new Array();
     var intValue;
     var lngProduct = 0;
     var intCheckDigit;
     var intStrLen = num.length;
     var idNumber = num;
     // initialize
     if ((intStrLen != 15) && (intStrLen != 18)) {
         return false;
     }
     // check and set value
     for (i = 0; i < intStrLen; i++) {
         varArray[i] = idNumber.charAt(i);
         if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
             return false;
         } else if (i < 17) {
             varArray[i] = varArray[i] * factorArr[i];
         }
     }
     if (intStrLen == 18) {
         //check date
         var date8 = idNumber.substring(6, 14);
         if (isDate8(date8) == false) {
             return false;
         }
         // calculate the sum of the products
         for (i = 0; i < 17; i++) {
             lngProduct = lngProduct + varArray[i];
         }
         // calculate the check digit
         intCheckDigit = parityBit[lngProduct % 11];
         // check last digit
         if (varArray[17] != intCheckDigit) {
             return false;
         }
     }
     else {        //length is 15
         //check date
         var date6 = idNumber.substring(6, 12);
         if (isDate6(date6) == false) {
             return false;
         }
     }
     return true;
 }
 /*是否6位日期*/
 function isDate6(sDate) {
     if (!/^[0-9]{6}$/.test(sDate)) {
         return false;
     }
     var year, month, day;
     year = sDate.substring(0, 4);
     month = sDate.substring(4, 6);
     if (year < 1700 || year > 2500) return false
     if (month < 1 || month > 12) return false
     return true
 }
 /*是否8位日期*/
 function isDate8(sDate) {
     if (!/^[0-9]{8}$/.test(sDate)) {
         return false;
     }
     var year, month, day;
     year = sDate.substring(0, 4);
     month = sDate.substring(4, 6);
     day = sDate.substring(6, 8);
     var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
     if (year < 1700 || year > 2500) return false
     if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
     if (month < 1 || month > 12) return false
     if (day < 1 || day > iaMonthDays[month - 1]) return false
     return true
 }
 /*车牌是否合法*/
 function isCarLicenseNo(str) {
     return /(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/.test(str);
 }
