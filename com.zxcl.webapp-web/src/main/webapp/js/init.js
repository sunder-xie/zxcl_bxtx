function apopup(id){
	window.top.popupwindow(id);
};

$(function(){
	var oh = window.top.document.getElementsByTagName("body")[0].offsetHeight;
	var _h = oh - 88 - 30 - 41 - 40;
	$(".right-frame").css("height",_h+"px");	
});