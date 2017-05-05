/**
 * 校验出错时的显示错误处理
 * 
 * 
 */

/*把错误信息显示到控件后面的提示框中*/
function showError2Hint(label, element){
	var id = element.attr('id');
	var labelname = $("form label[for="+id+"]").text();
	if(labelname){
		labelname=labelname+":";
	}
	else{
		labelname="";
	}
	var ectrl = $("#errorcontainer #em_"+id);
	if(ectrl[0]){
		ectrl.text(labelname+label.text());
	}
	else{
		$("#errorcontainer").append('<label id="em_'+id+'" class="mustfilltext f-db" for="'+id+'">'+labelname+label.text()+'</label>');
	}
	var jqueryuictrl = element.parent('.ui-widget');
	//判断是否jquery ui的组件
	if(jqueryuictrl[0]){
		//组件高亮
		//jqueryuictrl.addClass("highlightred");
		jqueryuictrl.css('border-color','red');
	}
	else{
		//原来的控件变红色
		element.addClass("highlightred");
	}
}

/*成功时从控件后面的提示框去掉错误提示*/
function removeSuccess(label){


	var targetinput = label.attr('for');
	var ectrl = $("#errorcontainer #em_"+targetinput);
	ectrl.remove();
	var element = $('#'+targetinput)
	var jqueryuictrl = element.parent('.ui-widget');
	//判断是否jquery ui的组件
	if(jqueryuictrl[0]){
		//组件高亮
		//jqueryuictrl.removeClass("highlightred");
		jqueryuictrl.css('border-color','inherit');
	}
	else{
		//原来的控件变红色
		element.removeClass("highlightred");
	}
}

/*校验通过后的处理*/
function allGreenSubmit(fm){
	fm.submit();
	return false;
}


$.validator.setDefaults({ 
	submitHandler: function(form) { return allGreenSubmit(form); }
	,success: function (label) {removeSuccess(label);}
	,errorPlacement: function (label, element) {showError2Hint(label, element);}
});