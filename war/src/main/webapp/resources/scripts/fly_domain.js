// 导航按钮
Plywet.domain = {
	ids : {
		bpVarName : "editorContent-navi-domain-bp_var"
	},
	createDir : function(){
		var _self = this;
		var currentCase = window[Plywet.domain.ids.bpVarName].getCurrentData();
		var targetId = "domain_create_dialog_folder";
		var dirId = currentCase.dirId;
		
		Plywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建目录",
			width : 500,
			height : 70,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/domain/dir/create/"+dirId,
			params : {
				targetId : targetId+":content",
				rootId : currentCase.rootId,
				path : currentCase.path
			},
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events: {
					click:function(){
						Plywet.ab({
							formId:"domain_folder_create_form",
							formAction:"rest/domain/dir/createsubmit",
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[targetId + "_var"].hide();
									_self.flushDir(dirId);
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
			maximizable : false,
			resizable : false
		});
	}
};