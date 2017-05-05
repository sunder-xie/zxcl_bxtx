
function initUploadVideoMaterial(){
	var container =$( "<div class=\"upload_videomasterial_container\"><div class=\"tips\">上传影像图片，请选择对应的文件类型及图片!<br/>点击查看图片可缩放拖拽以便查看已上传图片内容！</div></div>");
	//增加一个默认的上传附件的按钮.
	$.post(ctx + "getInquiryFileRequires.do",{inquiryId : inquiryId,insId:insId},function(data){
		
		if(data == ""){
			dialog("该保险公司无需上传附件.");
			return;
		}
		
		data = eval(data);
		for(var i = 0;i<data.length;i++){
			var uploadItem = getUploadItem(data[i].fileType,data[i].fileId);
			var addBtn = getAddBtn(uploadItem);
			uploadItem.append(addBtn);
			container.find(".tips").after(uploadItem);
		}
		
		var closeContainer = $("<div style=\"position:fixed;bottom:3%;right:3%;width:2rem;height:2rem;border:1px solid black;line-height:2rem;padding-left:1%;padding-right:1%;\">关闭</div>");
		closeContainer.click(function(){
			container.remove();
		});
		container.append(closeContainer);
		$(document.body).append(container);
		
	});
	
}

function getUploadItem(type,value){
	var uploadItem = $("<div class=\"upload_item\"></div>");
	var selectDiv = $("<div class=\"upload_type\"><select > <option value=\"10\">购车发票</option><option value=\"11\">投保人身份证</option><option value=\"12\">被保人身份证</option><option value=\"13\">行驶证</option><option value=\"14\">验车照</option><option value=\"15\">验车报告</option><option value=\"16\">完税凭证</option><option value=\"17\">减免税凭证</option><option value=\"18\">客户身份识别表</option><option value=\"19\">上年保单证明资料</option><option value=\"20\">其他资料</option></select></div>");
	if(type != undefined && type != ""){
		selectDiv.find("select option[value="+type+"]").attr("selected", true);
	}
	uploadItem.append(selectDiv);
	var uploadDiv = $("<div class=\"upload_btn_div\"> <span class=\"upload_btn_span\">上传图片</span> <input type=\"file\"  value=\"上传图片\" accept=\"image/gif,image/jpeg,image/jpg,image/png\" class=\"upload-input\" /></div>");
	uploadDiv.find("input").change(function(e){
		var source = e.target.files[0];
		var reader = new FileReader(source);
		var inpute = e;

		reader.onload = function(e) {
			if (source.size > 2 * 1024 * 100) { //用size属性判断文件大小是否超过200K ，否则需要压缩
				new html5ImgCompress(source, {
					before : function(file) {
						$(inpute.target).prev().removeClass("upload_btn_span");
						$(inpute.target).prev().addClass("uploading_btn_span");
						$(inpute.target).prev().html("处理中...");
						$(inpute.target).hide();
					},
					done : function(file, base64) {
				        
						uploadFileByBase64(base64,inpute);
					},
					fail : function(file) {
						dialog('处理失败,请重新选择文件');
					},
					complate : function(file) {
						
					},
					notSupport : function(file) {
						dialog('浏览器不支持！');
					}
				});
			} else {
				uploadFileByBase64(reader.result,inpute);
			}

		}
		reader.readAsDataURL(source);
	});
	if(value != undefined && value != ""){
		uploadDiv.find("input").hide()
		uploadDiv.find("span").removeClass("uploading_btn_span");
		uploadDiv.find("span").addClass("upload_btn_span");
		uploadDiv.find("span").html("查看图片");
		uploadDiv.find("span").click(function(){
			var image = $("<div style='z-index:1000;position:fixed;top:0px;left:0px;background:black;width:auto;height:auto;vertical-align: middle;'  class=\"pinch-zoom\"><img style=\"vertical-align: middle;width:90%;height:auto;\"  src = '"+value+"' /></div>");
    		image.click(function(){image.remove();$(".fixedbg").unbind("click");$(".fixedbg").hide();});
    		hasfixbg();
    		$(document.body).append(image);
    		$(image).find("img").load(function(){
    			image.css("top",($(document.body).height() - image.height()) / 2);
    		});
    		$(image).find("img").smartZoom({'containerClass':'zoomableContainer'});   
    		image.css("top",($(document.body).height() - image.height()) / 2);
    		$(".fixedbg").show();
    		$(".fixedbg").click(function(){image.remove();$(".fixedbg").unbind("click");$(".fixedbg").hide();});
    	});
    	uploadDiv.find("input").hide();
	}
	uploadItem.append(uploadDiv);
	var deleteBtn = $("<div class=\"delete_btn_div\"><img src=\""+ctx+"v3/images/delete_btn.png\" /></div>");
	deleteBtn.click(function(){
		if($(".upload_videomasterial_container .upload_item").size() == 1){
			return ;
		}
		if($(this).prev().find("span").html() != "查看图片"){
			return ;
		}
		/*dialogConfirm("您确认要删除该项吗？", "确定", function(){
			if($(deleteBtn).next().size() > 0){
				$(deleteBtn).parent().remove();
				var lastItem = $(".upload_videomasterial_container .upload_item:last");
				var addBtn = getAddBtn(lastItem);
				lastItem.append(addBtn);
			}else{
				$(deleteBtn).parent().remove();
			}
			
			
		});*/
		dialogConfirm("您确认删除已上传的附件吗？", "确定", function(){
			$.post(ctx + "clearInquiryFileRequires.do",{inquiryId : inquiryId,
				insId:insId,fileType:$(deleteBtn).parent().find("select option[selected=selected]").val()},
				function(data){
					if(data == "1"){
						uploadDiv.find("span").unbind("click");
						uploadDiv.find("span").html("上传图片");
						uploadDiv.find("span").parent().find("input").show();
					}else{
						dialog("清理失败.");
					}
			});
			
		});
	});
	uploadItem.append(deleteBtn);
	return uploadItem;
}
function getAddBtn(uploadItem){
	var addBtn = $("<div class=\"add_btn_div\"><img src=\""+ctx+"v3/images/add_btn.png\" /></div>");
	addBtn.click(function(){
		var addUploadItem = getUploadItem();
		var addBtnInner = getAddBtn(addUploadItem);
		addUploadItem.append(addBtnInner);
		addUploadItem.append(addBtnInner);
		$(".upload_videomasterial_container .upload_item:last").after(addUploadItem);
		this.remove();
	});
	//暂时不要增加按钮
	return "";
}

/**
 * 判断附件是否已经上传完成
 */
function getIsUploadVideoMaterialDone(callback){
	$.post(ctx + "getInquiryFileRequires.do",{inquiryId : inquiryId,insId:insId},function(data){
		
		if(data == ""){
			
			callback();
			
			return;
		}
		
		data = eval(data);
		for(var i = 0;i<data.length;i++){
			if(data[i].fileId == undefined || data[i].fileId == ""){
				dialog("请先上传保险公司所需附件.");
				return ;
			}
		}
		
		callback();
	});
	
}


function uploadFileByBase64(base64,inpute){
	var xhr = new XMLHttpRequest();
    var fd = new FormData();
	  //关联表单数据,可以是自定义参数
	  fd.append("inquiryId",inquiryId);
	  fd.append("insId",insId);
	  fd.append("name", $(inpute.target).parent().prev().find("select option:selected").val());
	  fd.append("image", base64);
	  //监听事件
	  xhr.upload.addEventListener("progress", function(evt){
		  if (evt.lengthComputable) {
    	    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
    	    $(inpute.target).prev().html("上传中.."+percentComplete.toString() + '%');
    	  }
	  }, false);
	  xhr.addEventListener("load", function(evt){
		//服务断接收完文件返回的结果
		  var data = eval("("+evt.currentTarget.responseText+")");
		  	if(data.succ){
		  		$(inpute.target).prev().removeClass("uploading_btn_span");
    	    	$(inpute.target).prev().addClass("upload_btn_span");
    	    	$(inpute.target).prev().html("查看图片");
    	    	$(inpute.target).prev().click(function(){
    	    		var image = $("<div style='z-index:1000;position:fixed;top:0px;left:0px;background:black;width:auto;height:auto;vertical-align: middle;'  class=\"pinch-zoom\"><img style=\"vertical-align: middle;width:90%;height:auto;\"  src = '"+data.data+"' /></div>");
    	    		image.click(function(){image.remove();$(".fixedbg").unbind("click");$(".fixedbg").hide();});
    	    		hasfixbg();
    	    		$(document.body).append(image);
    	    		$(image).find("img").load(function(){
    	    			image.css("top",($(document.body).height() - image.height()) / 2);
    	    		});
    	    		$(image).find("img").smartZoom({'containerClass':'zoomableContainer'});   
    	    		$(".fixedbg").show();
    	    		image.css("top",($(document.body).height() - image.height()) / 2);
    	    		$(".fixedbg").click(function(){image.remove();$(".fixedbg").unbind("click");$(".fixedbg").hide();});

    	    	});
    	    	$(inpute.target).hide();
		  	}else{
		  		dialog(data.msg);
		  		$(inpute.target).prev().removeClass("upload_btn_span");
    		 	$(inpute.target).prev().addClass("uploading_btn_span");
    		 	$(inpute.target).prev().html("上传图片");
    	    	$(inpute.target).show();
		  	}
	    	
	  }, false);
	  xhr.addEventListener("error", function(evt){
		 	dialog("上传失败");
		 	$(inpute.target).prev().removeClass("upload_btn_span");
		 	$(inpute.target).prev().addClass("uploading_btn_span");
		 	$(inpute.target).prev().html("上传图片");
	    	$(inpute.target).show();
	  }, false);
	  //发送文件和表单自定义参数
	  xhr.open("POST", ctx + "upload/inquiry_file_add2.do");
	  xhr.send(fd);
}
