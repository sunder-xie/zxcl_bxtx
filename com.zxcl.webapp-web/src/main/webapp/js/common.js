$(document).ready(function() {
	$(".m-list-item01").click(function(){
		$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		if($(this).hasClass("m-list-itembg")){
			$(this).removeClass("m-list-itembg").addClass("m-list-itembg-1");
			}else{
			$(this).removeClass("m-list-itembg-1").addClass("m-list-itembg");
				}
		});
		$(".m-list-item02").click(function(){
			$(".m-list-item02").removeClass("m-list-item-bg");
			$(this).addClass("m-list-item-bg");
			
			});


});