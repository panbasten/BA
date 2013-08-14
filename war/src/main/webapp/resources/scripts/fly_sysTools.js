Flywet.sysTools = {
	openTool:function(src){
		var target = $(src);
		var data = target.attr('ext');
		if(!data){
			return;
		}
		
		data = Flywet.parseJSON(decodeURIComponent(data));
		
		var dialogId = target.attr("id") + "_dialog";
		
		var opt = $.extend({},{
			id : dialogId,
			width : 700,
			height : 400,
			autoOpen : true,
			showHeader : true,
			modal : true,
			closable : true,
			maximizable : false
		},data);
		
		if(opt.btn){
			var btns = [];
			var btnsSetting = Flywet.parseJSON(opt.btn);
			for(var i=0;i<btnsSetting.length;i++){
				if(btnsSetting[i].type && btnsSetting[i].type == "cancel"){
					btns.push({
						componentType : "fly:PushButton",
						type : "button",
						label : "取消",
						title : "取消",
						events : {
							"click" : "hide"
						}
					});
				}else{
					var btn = {
						componentType : "fly:PushButton",
						type : "button",
						label : btnsSetting[i].label,
						title : btnsSetting[i].title,
						url : btnsSetting[i].url,
						events: {
							click:function(event,params){
								Flywet.ab({
									type : "POST",
									url : params.url,
									source:"sys_tools_item_form",
									onsuccess:function(data, status, xhr) {
										if (data.state == 0) {
											window[dialogId + "_var"].hide();
										}
									}
								});
							}
						}
					};
					btns.push(btn);
				}
			}
			opt.btn = undefined;
			opt.footerButtons = btns;
		}
		
		Flywet.cw("Dialog", dialogId+"_var", opt);
	}
};