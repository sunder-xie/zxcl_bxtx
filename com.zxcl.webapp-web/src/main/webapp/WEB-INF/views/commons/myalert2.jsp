<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<style>
	#myConfirm,#fixedbg,#myAlert{
		display:none;
	}
	#alert_yes_tip{
	    width: 100% !Important;
	}
</style>
<div class="tipsel txc" id="myConfirm" style="margin-top: -68px;">
	<div class="tip confirm_text"></div>
	<div class="btns clear">
		<div class="lt boxSet c1 yes" id="yes_tip">确定</div>
		<div class="lt boxSet c1 no" id="no_tip">取消</div>
	</div>
</div>
<div class="tipsel txc" id="myAlert" style="margin-top: -68px;">
	<div class="tip alert_text"></div>
	<div class="btns clear">
		<div class="lt boxSet c1 yes" id="alert_yes_tip">确定</div>
	</div>
</div>
<div class="fixedbg2" id="fixedbg"></div>
<script>
	window.alert = function(msg, yes){
		$('#myAlert .alert_text').html(msg);
		$('#myAlert,#fixedbg').show();
		$('#myAlert .yes').bind('click',function(){
			$('#myAlert,#fixedbg').hide();
			$('#myAlert .yes').unbind('click');
			if(typeof yes === "function"){
				yes();	
			}
			else{
				console.log('yes is not a function');
			}
		});
	}
	window.confirm = function(msg, yes_tip, no_tip, yes, no){
		$('#myConfirm .confirm_text').html(msg);
		$('#myConfirm,#fixedbg').show();
		if(null != yes_tip && '' != yes_tip){
			$('#yes_tip').text(yes_tip);
		}
		if(null != no_tip && '' != no_tip){
			$('#no_tip').text(no_tip);
		}
		$('#myConfirm .yes').bind('click',function(){
			$('#myConfirm,#fixedbg').hide();
			$('#myConfirm .no').unbind('click');
			if(typeof yes === "function"){
				yes();	
			}
			else{
				console.log('yes is not a function');
			}
		});
		$('#myConfirm .no').bind('click', function(){
			$('#myConfirm,#fixedbg').hide();
			$('#myConfirm .yes').unbind('click');
			if(typeof no === "function"){
				no();
			}
			else{
				console.log('no is not a function');
			}
		});
	}
</script>