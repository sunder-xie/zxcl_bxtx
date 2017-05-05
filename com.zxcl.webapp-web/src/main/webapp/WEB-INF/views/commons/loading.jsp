<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<div class="wrap_result load">
	<div class="tips" style="width: 30%;left: 55%;">
		<img src="${ctx}images/loading_v3.gif"/>
		<p style="margin-top: -10%;font-size: 14px;">&nbsp;&nbsp;努力加载中...</p>
		<span class="loading-x"><span style="font-family:Marlett">×</span></span>
	</div>
</div>
<style>
span.loading-x{
    position: fixed;
    left: 96%;
    top: 0;
    color: #8E8888;
    z-index: 1000;
    display: none;
}
</style>
<script>
	$(function(){
		$('div.wrap_result.load').hide();
	});
	setTimeout(function(){
		$('span.loading-x').show().click(function(){
			$('div.wrap_result.load').hide();
		});
	},5000);
</script>