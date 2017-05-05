<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<div class="loading">
	<div class="load-container load6">
	  <div class="loader">Loading...</div>
	</div>
	<span class="loading-x"><span style="font-family:Marlett">×</span></span>
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
		$('div.loading').hide();
	});
	setTimeout(function(){
		$('span.loading-x').show().click(function(){
			$('div.loading').hide();
		});
	},5000);
</script>