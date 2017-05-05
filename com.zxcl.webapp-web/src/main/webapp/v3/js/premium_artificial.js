//
function changeBusinessTypeSan(){
	if('A' == quotnType){
		$('select[name=ownerCertType]').html('<option value="1">居民身份证</option>');
		$('select[name=recCertType]').html('<option value="1">居民身份证</option>');
		$('select[name=voteCertType]').html('<option value="1">居民身份证</option>');
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
		$('select[name=ownerCertType]').html(cdeStr1);
		
		//被保人
		var cdeStr2 = '';
		if(recCertType){
			if('1' == recCertType){
				cdeStr2 = '<option value="1" selected="selected">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('4' == recCertType){
				cdeStr2 = '<option value="1">居民身份证</option><option value="4" selected="selected">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('5' == recCertType){
				cdeStr2 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5" selected="selected">工商注册号码</option>';
			}
		}else{
			cdeStr2 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
		}
		$('select[name=recCertType]').html(cdeStr2);
		
		//投保人
		var cdeStr3 = '';
		if(voteCertType){
			if('1' == voteCertType){
				cdeStr3 = '<option value="1" selected="selected">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('4' == voteCertType){
				cdeStr3 = '<option value="1">居民身份证</option><option value="4" selected="selected">组织机构代码</option><option value="5">工商注册号码</option>';
			}else if('5' == voteCertType){
				cdeStr3 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5" selected="selected">工商注册号码</option>';
			}
		}else{
			cdeStr3 = '<option value="1">居民身份证</option><option value="4">组织机构代码</option><option value="5">工商注册号码</option>';
		}
		$('select[name=voteCertType]').html(cdeStr3);
	}
}

$(function(){
	changeBusinessTypeSan();
});


