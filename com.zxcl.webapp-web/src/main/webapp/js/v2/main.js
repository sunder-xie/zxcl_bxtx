// JavaScript Document

$(function(){
	var wrap = $(".wrap"),
		back = $(".back"),
		cover = $(".cover"),
		loading = $(".loading");
	/**返回上一页**/
	$(back).on("click",function(){
		window.history.go(-1);					   
	})		
	window.onload = function(){
		//加载完成时隐藏loading
		$(loading).hide();
		$(wrap).show();
		var wrapW = $(wrap).width(),
			wrapH = $(wrap).height();

		//$(wrap).css("min-height",h);
		//横竖屏判断
		function orient() { 
			if (window.orientation == 90 || window.orientation == -90) { 
				$(wrap).hide();
				$(cover).show(); 
			}else if (window.orientation == 0 || window.orientation == 180) { 
				$(wrap).show();
				$(cover).hide();
			}
		};
		var timer;
		$(window).bind("orientationchange", function(){
			clearTimeout(timer);
			timer = setTimeout(orient, 300);
		});
		orient();	
		
		/*//宽高度获取  
		GetShape();
		function GetShape(){
			var browser_W=$(window).width();	 
			var browser_H=$(window).height();	
			if(wrapH < browser_H){
				$(wrap).css({"width":browser_W,"height":browser_H});
			}
		};	
		$(window).resize(function(){
			GetShape();
		});*/
	}
})
