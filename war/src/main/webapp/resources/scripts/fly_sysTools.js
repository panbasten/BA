Plywet.sysTools = {
	openTool:function(src){
		var target = $(src);
		console.log(target);
		var data = Plywet.parseJSON(target.attr('ext'));
		if(!data){
			return;
		}
		var url = data.url;
		var height = data.height||400;
		var width = data.width||700;
		
		var dialogId = target.parent().attr("id") + "_dialog";
		Plywet.cw("Dialog", dialogId+"_var", {
				id : dialogId,
				header : data.title,
				width : width,
				height : height,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : url,
//				footerButtons : [{
//					componentType : "fly:PushButton",
//					type : "button",
//					label : "确定",
//					title : "确定",
//					events: {
//						click:function(){
//							Plywet.ab({
//								type : "POST",
//								url : "rest/user/save",
//								source:"user_form",
//								onsuccess:function(data, status, xhr) {
//									if (data.state == 0) {
//										window[targetId + "_var"].hide();
//									}
//								}
//							});
//						}
//					}
//				},{
//					componentType : "fly:PushButton",
//					type : "button",
//					label : "取消",
//					title : "取消",
//					events : {
//						"click" : "hide"
//					}
//				}],
				closable : true,
				maximizable : false
			});
	}
};