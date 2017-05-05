jQuery.validator.addMethod("checknewPwd",
function(value,element){
/*if(value.length==0)
{
    return true;
}
if(value.length!=0){
    return false;
}*/
/*return /^[-\da-zA-Z`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]*((\d+[a-zA-Z]+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+)|(\d+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+[a-zA-Z]+)|([a-zA-Z]+\d+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+)|([a-zA-Z]+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+\d+)|([-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+\d+[a-zA-Z]+)|([-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+[a-zA-Z]+\d+))[-\da-zA-Z`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]*$/.test(value);
*/
	
//密码至少包一个大写字母、一个小写字母及一个符号，长度至少8位	
return this.optional(element) || /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/.test(value);  

},
"密码至少包一个大写字母、一个小写字母及一个符号，长度至少8位"
);


jQuery.validator.addMethod("same", function(value, element) {  
    return this.optional(element) || same(value);  
}, "新密码不能与老密码重复");  


function same(pwd) {  
    var oldPwd = $("#oldPwd").val();  
    if (oldPwd == pwd)  
        return false;  
    else  
        return true;  
}  