<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<style>
	#myConfirm,#fixedbg{
		display:none;
	}
</style>
<div class="tipsel txc" id="myConfirm" style="margin-top: -68px;">
	<div class="tip confirm_text"></div>
	<div class="btns clear">
		<div class="lt boxSet c1 yes">确定</div>
		<div class="lt boxSet c1 no">取消</div>
	</div>
</div>
<div class="fixedbg2" id="fixedbg"></div>
<script>
	window.confirm = function(msg, yes, no){
		$('#myConfirm .confirm_text').html(msg);
		$('#myConfirm,#fixedbg').show();
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