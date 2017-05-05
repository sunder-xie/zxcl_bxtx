<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<style>
.confirm_btn_c{
	left: 54%;
}
</style>
<div class="wrap_result dbn" id="myAlert">
	<div class="result_box">
		<div class="showBtn">
			<div class="result_top">提示</div>
			<p class="result_con alert_text"></p>
			<div class="result_bottom">
				<a class="btn_c result_close">确定</a>
			</div>
		</div>
	</div>
</div>
<div class="wrap_result dbn" id="myConfirm">
	<div class="result_box">
		<div class="showBtn">
			<div class="result_top">提示</div>
			<p class="result_con confirm_text"></p>
			<div class="result_bottom">
				<a class="btn_c2 result_close yes">确定</a>
				<a class="confirm_btn_c result_close no" style="float:right;">取消</a>
			</div>
		</div>
	</div>
</div>
<script>
	$('#myAlert a.result_close').click(function(){
		$('div.wrap_result.dbn').hide();
	});
	window.alert = function(msg){
		$('#myAlert .alert_text').text(msg);
		$('#myAlert').show();
	}
	window.confirm = function(msg, yes, no){
		$('#myConfirm .confirm_text').text(msg);
		$('#myConfirm').show();
		$('#myConfirm .yes').click(function(){
			$('#myConfirm').hide();
			yes();
		});
		$('#myConfirm .no').click(function(){
			$('#myConfirm').hide();
			no();
		});
	}
</script>