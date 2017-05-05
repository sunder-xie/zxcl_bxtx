$(function(){
	var count = 0 ;
	$.post(ctx+"activityd/getWallteLettoryNum.do","",function(data){
		
		
		for(var i = 0 ;i<parseInt(data.BD);i++){
			var screenWidth = $(document.body).width();
			var random = Math.floor(Math.random() * (screenWidth - 30) + 30);
			if(random + 30 > screenWidth){
				random = screenWidth - 30;
			}
			$("#redenvbg").before("<div revenvtype=\"BD\" id=\"redenv"+count+"\" class=\"qiandai\" style=\"left:"+random+"px;\"><img  src=\""+ctx+"activity/wallte/77a58PICcEm.png\"></div>");
			count += 1;
		}
		for(var i = 0 ;i<parseInt(data.BXTX);i++){
			var screenWidth = $(document.body).width();
			var random = Math.floor(Math.random() * (screenWidth - 30) + 30);
			if(random + 30 > screenWidth){
				random = screenWidth - 30;
			}
			$("#redenvbg").before("<div revenvtype=\"BXTX\" id=\"redenv"+count+"\" class=\"qiandai\" style=\"left:"+random+"px;\"><img  src=\""+ctx+"activity/wallte/77a58PICcEm.png\"></div>");
			count += 1;
		}
		

		for(var j = 0 ;j<count ;j++){
			acc(j.toString(),Math.floor(Math.random() * (1000 - 20) + 20));
		}
	});
	
});


function acc(f,time){
	setTimeout(function(){
		$("#redenv"+f).show();
		var ro = $("#redenv"+f).offset();
		var bh = $(document).height();
		var marginTop = bh - ro.top - 90;
		
		$("#redenv"+f).stop().animate({
            marginTop: marginTop+"px"
        }, {
            duration: 2000,
            easing: "easeOutBounce",
            complete : function (){
            	$("#redenv"+f).click(function(event){
            		var renenvdiv = $(this);
            		$.post(ctx+"activityd/luckdrawWallte.do",{type:$(this).attr("revenvtype")},function(data){
        				if(data.result == "-1"){
        					dialog("请您先登陆","立即登陆",function(){
        						window.location.reload();
        					});
        				}else if(data.result == "1"){
        					
        					//增加金额文本向上浮效果
        					var text = $("<div>+"+data.amount+"</div>");
        					text.css({"color":"red","position":"fixed","top":renenvdiv.offset().top- 15,"left":renenvdiv.offset().left});
        					$(document.body).append(text);
        					text.animate({
        					    left: text.offset().left,
        					    top: text.offset().top - 100,
        					    opacity : 0
        					},2000, 'easeInCubic',function(){
    					    	text.remove();
    					    });
        					
        					
                    		for(var i = 0 ;i<5;i++){
                    			var img = $("<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAPCAIAAAB82OjLAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAxBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QTM1N0JCMDZDMUUyMTFFNkFFNkRGQzMxMjU1MEVEMzciIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QTM1N0JCMDVDMUUyMTFFNkFFNkRGQzMxMjU1MEVEMzciIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiBXaW5kb3dzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9Ijc4QzA1NjRDRjZGMDMyMjUwRjk1NTY2MjU4QzJFNkZGIiBzdFJlZjpkb2N1bWVudElEPSI3OEMwNTY0Q0Y2RjAzMjI1MEY5NTU2NjI1OEMyRTZGRiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PpMufvEAAARrSURBVHjaZJNrb1RFGMfndu67p3vrdsv2TtEWAQWaGIrBYGgw4ZVRvoHfwnd+AV+Y+AEUY4wXogkmJmBiKKGahgJSoLQFStuz3cvZ7uXcZs6ZGU8hMTH830wyk+eZJ7/n/4eUMUVRGAAYACAlSBKkKFHTpbUVAAKSqaZ3otsCNIKFMVieEWEgMOICxE4d9HrqgKnkLRzRjuMmQgy+OZH4gQgomaoS+mwr2mr5tx6rx0aNuTewpgT3n0mP0r0GMTzlXZ05DeF0IJMir6MclJoiMaC3lrHvs1qDxdI8e6J8+dyDn5dcx730xanukhM9fG4pmFC3Ta/dhC96QX0PlC0tnxMqVqYqWi4rkx5nfWgQVCoJeAjZg6RsezdXvO9/seamrYvzmhf6Vxf3v/pRBtHMR+9RP2CNthRAAMB7HjHHR7WPF/zlDfPYmDpeSR/0kbKM2MDoCPBdwTfBiJF0kGafYEjnOV1ZuqvqxL58UZ2uYj/0l5/I3Rbeevrkh7zHwfnPqmKVsbqbPX8aCikhAHE/wFkTAMETCjGWTq/26HYvbAlAFSNrZ4Z2n/09Prlgj7wV1XbDb3+yFk6LfQ9CxCTofH0t9/aE/848dTtTF6ZFDNlzR71wBgnO0/0pWROlB5cYEhkLBFXO1vdWPydIyKDrrn2D47W+8yuSpkwEqZaEH0CVoKyJC7Y+XDLPzEnNA5ong4RZ2WjuZGqKg4b/CWEMIAJQJoxZem5mdr5YtAtDpcHq0YJtF4qTlLMkpLJoi0TgkQo8Ug2dujYzaVYGW2u12uMd1TbaId9YbyEICPi/EMIQqrHqa5VT6tBJut0AAdCKC7hSI7njnHZh3k7uRfLhBkwArBZFsyuZ6G3vHj4+K2jEYyqQlqIAKSzwmmAKu1KEylCwUsebLtnaie//w58qcSDTqhQCN2xhGtqRKmAxanZZvWUY6K/rq9cX18nsBI/i9FPw+tSvJDzX//NKWAe8OJGMVJReEzQfAmMPFT9US4WeZdN+RB5tAAG5rdNNp/XdzdOXzpIP5hIvQUKQFId82VpKKeI4zZJMmwqhajp3HTN4oA4fdrTRiBQzOhuWy6hfi1sn+jfu06u/QwuEY0Wgm369QyJOur17X17Rdrbf//QToqvCDw+mFoJHrUYSszZNUidFvndofKpgF2luGKSlMsCJqsU05YStgW6t0/ztD6vdloVx3wNxu6t0QjmgB3ZGblO6tkX39kuFAYPIA9pJar4wjFttnB/AmcxL1pBTHq7cYHfWOSuk3tQUBMiuemY+xhONxdv+nVVNQK1op8MA35eGgicPlcaqmWoZDhWIpmIWxdn8QWSAu8daLsnncLn6inXc7lNnk73YkX0GZQQQQXmDTB8lubF4f7/zeJNu7WKCFA1DnRBL18oFbFkYAtHp080dkrfNc3OQUYoQhESRB+GXryxCW01Wv4tNAIWShOlNDIMIGIOwPAuVdD2Strs8ogoGGHLIeRofnghomtDzeM+HqqodmfhXgAEAMSpqm4uRE54AAAAASUVORK5CYII=\" />");
                    			img.css({"position":"fixed","top":renenvdiv.offset().top-(i*3),"left":renenvdiv.offset().left-(i*3)});
                    			var mo = $("#amount").offset();
                    			
                    			$(document.body).append(img);
                    			accc(mo,Math.floor(Math.random() * (400 - 20) + 20),img);
                    			
                    		}
                    		
                    		setTimeout(function(){
                				//效果结束，金额增加
                				$.post(ctx+"wallet/get_wallet.do",{},function(data){
                					if(data.succ){
                						if(numAnim == undefined){
                							numAnim = new CountUp("amount", parseFloat($('#amount').html()).toFixed(2), data.data.amount,2);
                							numAnim.start();
                						}else{
                							numAnim.update(data.data.amount);
                						}
                					}
                				},'json');
                			}, 400);
                    		
                    		renenvdiv.remove();
        				}else{
        					dialog("领取失败，请稍后重试");
        				}
        			});
            	});
            }
        });
	}, time);
}
var numAnim = undefined;
function accc(mo,time,img){
	setTimeout(function(){
		img.fly({
			start: {
				left: img.offset().left,
				top: img.offset().top
			},
			end: {
				left: mo.left+20,
				top: mo.top+20,
				width: 0,
				height: 0
			},
			speed: 1.1, 
			vertex_Rtop:100, 
			onEnd: function(){
				
				this.destory();
			}
		});
	}, time);
}