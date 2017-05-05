$(document).ready(function(){
	$('.sideNav dt').click(function(){
		$(this).parent('dl').addClass('focus').siblings().removeClass('focus');	
	});
	$('.sideNav dd li').click(function(){
		$(this).addClass('active').siblings().removeClass('active').parents('dl').siblings().find('li').removeClass('active');
	});

	$('.exp-col h2').click(function(){
		if($(this).find('span').hasClass('collapesed'))
			{
				$(this).next().hide();
				$(this).find('span').removeClass('collapesed').addClass('expand');
			}
		else{
			$(this).next().show();
			$(this).find('span').removeClass('expand').addClass('collapesed');	
		}
	});

	$(".tab li").each(function(index){
		$(this).click(function(){
			$(this).addClass('focus').siblings().removeClass('focus');
			$('.tabCon div').eq(index).show().siblings().hide();
		})
	});

	$('#btn_save').click(function(){
		$(this).hide();
		$("#btn_saveok").animate({top: 130, opacity: 'show'}, 500);
	});

	//var menuA=window.parent.document.getElementsByTagName("frameset")[0];
	var menuA=$(window.parent.document).find("frameset");
	$('#split_exp').click(function(){
		if($(this).hasClass('splitExp')){
			menuA.attr("cols","0,*");
			$(this).removeClass('splitExp').addClass('splitCol');

		}else{
			//menuA.cols="194,*"; 
			menuA.attr("cols","194,*");
			$(this).removeClass('splitCol').addClass('splitExp');
		}
	});
	
	//dialog
	$('#answer').click(function(){
              $("#overlay").show();
              $("#dialog-box").show();
			  var scrolltop = $(document).scrollTop();
			  var screenWidth = $(window).width(), screenHeight = $(window).height();
			  var objLeft = (screenWidth - $("#dialog-box").outerWidth())/2 ;
		      var objTop = (screenHeight - $("#dialog-box").outerHeight())/2 + scrolltop;
			  $("#dialog-box").css({left: objLeft + 'px', top: objTop + 'px','display': 'block'});
         });
		$(".close").click(function(){
              $("#dialog-box").hide();
              $("#overlay").hide();
         });
});