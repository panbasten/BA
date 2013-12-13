// 导航按钮
Flywet.smart = {
	ids : {
		bpVarName : "editorContent-navi-smart-bp_var"
	},
	createDir : function(){
		var _self = this;
		var currentCase = window[Flywet.smart.ids.bpVarName].getCurrentData();
		var targetId = "smart_create_dialog_folder";
		var dirId = currentCase.dirId;
		
		Flywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建目录",
			width : 500,
			height : 70,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/smart/dir/create/"+dirId,
			params : {
				targetId : targetId+":content"
			},
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				click: {
					formId:"folder_create_form",
					formAction:"rest/smart/dir/createsubmit",
					onsuccess:function(data, status, xhr) {
						if (data.state == 0) {
							window[targetId + "_var"].hide();
							_self.flushDir(dirId);
						}
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
			maximizable : false,
			resizable : false
		});
	}
};