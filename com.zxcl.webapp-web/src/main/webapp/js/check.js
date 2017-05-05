/**
 * 校验
 * @param idCardNo
 * @returns
 * @author co-xuxiaoming001
 */

//验证身份证号码
function checkIdCard(idCardNo) {
	// 15位和18位身份证号码的基本校验
	//pattern="(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)" 
	var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
	if (!check){
		return false;
	}	
	// 判断长度为15位或18位
	if (idCardNo.length == 15) {
		return check15IdCardNo(idCardNo);
	} else if (idCardNo.length == 18) {
		return check18IdCardNo(idCardNo);
	} else {
		return false;
	}
}
// 校验15位的身份证号码
function check15IdCardNo(idCardNo) {
	// 15位身份证号码的基本校验
	var reg = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/;
	var check = reg.test(idCardNo);
	return check;
}

// 校验18位的身份证号码
function check18IdCardNo(idCardNo) {
	// 18位身份证号码的基本格式校验
	var reg = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|X)$/;
	var check = reg.test(idCardNo);
	return check;
}

function checkCard(val){
	var reg = /^[\u4e00-\u9fa5A-Za-z0-9]+$/;
	var check = reg.test(val);
	return check;
}

function getSexAndBirth(val,type){
	var birthSex = new Array(2);
	birthSex[0] = "";
	birthSex[1] = "";
	if(val!=""&&val.length==18){		
		//获取出生日期 
		var bir = val.substring(6, 10) + "-" + val.substring(10, 12) + "-" + val.substring(12, 14); 
		birthSex[0] = bir;
		//获取性别
		if (Number(val.substr(16, 1)) % 2 == 0) { 
			if(type=="1"){
				birthSex[1] = "106002";
			}else{
				birthSex[1] = "2";
			}
			
		} else { 			
			if(type=="1"){
				birthSex[1] = "106001";
			}else{
				birthSex[1] = "1";
			}
		} 
	}
	
	return birthSex;
}

//检查身份证
function checkCardId(socialNo){   
    if(socialNo == "") {  
      return false;   
    }   
    if (socialNo.length != 15 && socialNo.length != 18) {   
      return false;   
    }   
  	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};    
        
    if(area[parseInt(socialNo.substr(0,2))]==null) {   
          return false;   
     }    
             
    if (socialNo.length == 15){   
       pattern= /^\d{15}$/;   
       if (pattern.exec(socialNo)==null){   
          return false;   
      }   
      var birth = parseInt("19" + socialNo.substr(6,2));   
      var month = socialNo.substr(8,2);   
      var day = parseInt(socialNo.substr(10,2));   
      switch(month) {   
          case '01':   
          case '03':   
          case '05':   
          case '07':   
          case '08':   
          case '10':   
          case '12':   
              if(day>31) {   
                 
                  return false;   
              }   
              break;   
          case '04':   
          case '06':   
          case '09':   
          case '11':   
              if(day>30) {   
                
                  return false;   
              }   
              break;   
          case '02':   
              if((birth % 4 == 0 && birth % 100 != 0) || birth % 400 == 0) {   
                  if(day>29) {   
                     
                      return false;   
                  }   
              } else {   
                  if(day>28) {   
                     
                      return false;   
                  }   
              }   
              break;   
          default:   
             
              return false;   
      }   
      var nowYear = new Date().getYear();   
      if(nowYear - parseInt(birth)<15 || nowYear - parseInt(birth)>100) {   
         
          return false;   
      }   
      return (true);   
    }   
       
    var Wi = new Array(   
              7,9,10,5,8,4,2,1,6,   
              3,7,9,10,5,8,4,2,1   
              );   
    var   lSum        = 0;   
    var   nNum        = 0;   
    var   nCheckSum   = 0;   
       
      for (i = 0; i < 17; ++i)   
      {   
             

          if ( socialNo.charAt(i) < '0' || socialNo.charAt(i) > '9' )   
          {   
             
              return false;   
          }   
          else  
          {   
              nNum = socialNo.charAt(i) - '0';   
          }   
           lSum += nNum * Wi[i];   
      }   

       
      if( socialNo.charAt(17) == 'X' || socialNo.charAt(17) == 'x')   
      {   
          lSum += 10*Wi[17];   
      }   
      else if ( socialNo.charAt(17) < '0' || socialNo.charAt(17) > '9' )   
      {   
         
          return false;   
      }   
      else  
      {   
          lSum += ( socialNo.charAt(17) - '0' ) * Wi[17];   
      }   

         
         
      if ( (lSum % 11) == 1 )   
      {   
          return true;   
      }   
      else  
      {   
          
          return false;   
      }   
         
}  

//获得coolie 的值

 

function cookie(name){    

   var cookieArray=document.cookie.split("; "); //得到分割的cookie名值对    

   var cookie=new Object();    

   for (var i=0;i<cookieArray.length;i++){    

      var arr=cookieArray[i].split("=");       //将名和值分开    

      if(arr[0]==name)return unescape(arr[1]); //如果是指定的cookie，则返回它的值    

   } 

   return ""; 

} 

 

function delCookie(name)//删除cookie

{

   document.cookie = name+"=;expires="+(new Date(0)).toGMTString();

}

 

function getCookie(objName){//获取指定名称的cookie的值

    var arrStr = document.cookie.split("; ");

    for(var i = 0;i < arrStr.length;i ++){

        var temp = arrStr[i].split("=");

        if(temp[0] == objName) return unescape(temp[1]);

   } 

}

 

function addCookie(objName,objValue,objHours){      //添加cookie

    var str = objName + "=" + escape(objValue);

    if(objHours < 0){                               //为时不设定过期时间，浏览器关闭时cookie自动消失

        var date = new Date();

        var ms = objHours*3600*1000;

        date.setTime(date.getTime() + ms);

        str += "; expires=" + date.toGMTString();

   }

   document.cookie = str;

}

 

function SetCookie(name,value)//两个参数，一个是cookie的名子，一个是值

{

    var Days = 30; //此 cookie 将被保存 30 天

    var exp = new Date();    //new Date("December 31, 9998");

    exp.setTime(exp.getTime() + Days*24*60*60*1000);

    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

}

function getCookie(name)//取cookies函数        
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));

     if(arr != null) return unescape(arr[2]); return null;
}

function delCookie(name)//删除cookie
{
    var exp = new Date();

    exp.setTime(exp.getTime() - 1);

    var cval=getCookie(name);

    if(cval!=null) 
    	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}


String.prototype.endWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	     return false;
	  if(this.substring(this.length-s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
}

String.prototype.startWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	  	 return false;
	  if(this.substr(0,s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
}

Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} 
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
} 
