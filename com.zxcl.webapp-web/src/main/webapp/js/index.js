function apopup(id){
	window.top.popupwindow(id);
};

function dopaging(){
	//set dowithpaging ctrl's value=1,so that java can know the request is paging
	document.getElementById('dowithpaging').value=1;
}

$(function(){
	var oh = document.getElementsByTagName("body")[0].offsetHeight;
	var _h = oh - 88 - 30 - 41 - 40;
	var _h2 = oh - 88 - 30 - 58;
	$(".right-frame").css("height",_h+"px");
	$("#leftroll").css("height",_h2+"px");
});

$(function(){
	$(".sideNav dt").bind("click",function(){
		$(".sideNav dt .left-a-focus,.sideNav dd .left-a-focus").removeClass("left-a-focus");
		$(this).find("a").addClass("left-a-focus");
		
		$(this).next("dd").slideToggle();
	});	
	$(".sideNav dd a").bind("click",function(){
		$(".sideNav dt .left-a-focus,.sideNav dd .left-a-focus").removeClass("left-a-focus");	
		$(this).addClass("left-a-focus");
		$(this).parents("dd").prev("dt").find("a").addClass("left-a-focus");
	});
	
	$(".autoSearchText,.searchsel").bind("click",function(){
		$(".searchUl").show();	
	});
	
	$(".searchUl li").bind("click",function(){
		$(".autoSearchText").val($(this).text());
		$(this).parent().hide();	
	});
	$(".autosearch").bind("mouseleave",function(){
		$(this).find("ul").hide();	
	});
	var oh = window.top.document.getElementsByTagName("body")[0].offsetHeight;
	var _h = oh - 88 - 30 - 58;
	$("#leftroll").css("height",_h+"px");
});
$(function() {
	var total = parseInt($("#paging_totalPage").text());
	
	var recordTotal = parseInt($("#paging_recordTotal").val());
	var miniPageSize = 5;
	$("#paging_pre").click(function() {
		var current = parseInt($("#paging_currentPage").val());
		var pageSize = parseInt($("#paging_pageSize").val());
		if (pageSize < miniPageSize) {
			pageSize = miniPageSize;
			$("#paging_pageSize").val(miniPageSize)
		}
		total = parseInt(recordTotal / pageSize);
		if (recordTotal % pageSize > 0) {
			total = total + 1;
		}
		if (current <= 1) {
			$("#paging_currentPage").val(1);
			return false;
		} else {
			$("#paging_currentPage").val(current - 1);
			return true;
		}
	});
	$("#paging_next").click(function() {
		var current = parseInt($("#paging_currentPage").val());
		var pageSize = parseInt($("#paging_pageSize").val());
		if (pageSize < miniPageSize) {
			pageSize = miniPageSize;
			$("#paging_pageSize").val(miniPageSize)
		}
		total = parseInt(recordTotal / pageSize);
		if (recordTotal % pageSize > 0) {
			total = total + 1;
		}
		if (current >= total) {
			$("#paging_currentPage").val(total);
			return false;
		} else {
			$("#paging_currentPage").val(current + 1);
			return true;
		}
	});
	$("#paging_goto").click(function() {
		var current = parseInt($("#paging_currentPage").val());
		var pageSize = parseInt($("#paging_pageSize").val());
		if (pageSize < miniPageSize) {
			pageSize = miniPageSize;
			$("#paging_pageSize").val(miniPageSize)
		}
		total = parseInt(recordTotal / pageSize);
		if (recordTotal % pageSize > 0) {
			total = total + 1;
		}
		if (current < 1) {
			$("#paging_currentPage").val(1);
		} else if (current > total) {
			$("#paging_currentPage").val(total);
		}
		return true;
		
	});
	$("input.intNumber").keyup(function() {
		var tmptxt = $(this).val();
		var srcValue = tmptxt;
		tmptxt = tmptxt.replace(/\D/g, '');
		if (tmptxt != srcValue) {
			$(this).val(tmptxt);
		}
	}).blur(function() {
		var tmptxt = $(this).val();
		tmptxt = tmptxt.replace(/\D/g, '');
		$(this).val(tmptxt);
	}).bind("paste", function() {
		var tmptxt = $(this).val();
		tmptxt = tmptxt.replace(/\D/g, '');
		$(this).val(tmptxt);
	}).css("ime-mode", "disabled");
});