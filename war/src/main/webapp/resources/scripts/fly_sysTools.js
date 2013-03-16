Plywet.sysTools = {
	openTool:function(src){
		var data = Plywet.parseJSON($(src).data('data'));
		var url = data.url;
		var height = data.height||400;
		var width = data.width||700;
		
		var targetId = data.targetId + "_dialog";
		Plywet.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : data.title,
				width : 700,
				height : height,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : url,
				footerButtons : [{
					componentType : "fly:PushButton",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							Plywet.ab({
								type : "POST",
//								url : "rest/user/save",
								source:"user_form",
								onsuccess:function(data, status, xhr) {
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});
						}
					}
				},{
					componentType : "fly:PushButton",
					type : "button",
					label : "取消",
					title : "取消",
					events : {
						"click" : "hide"
					}
				}],
				closable : true,
				maximizable : true
			});
	}
};