// 导航按钮
Flywet.di = {
	ids : {
		bpVarName : "editorContent-navi-di-bp_var"
	},
	createDir : function(){
		var _self = this;
		var currentCase = window[Flywet.di.ids.bpVarName].getCurrentData();
		var targetId = "create_dialog_folder";
		var dirId = currentCase.dirId;
		
		Flywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建目录",
			width : 500,
			height : 70,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/di/dir/create/"+dirId,
			params : {
				targetId : targetId+":content"
			},
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				click : {
					formId:"folder_create_form",
					formAction:"rest/di/dir/createsubmit",
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
	},
	create : function(type){
		var _self = this;
		var currentCase = window[Flywet.di.ids.bpVarName].getCurrentData();
		var targetId = "create_dialog_di";
		var dirId = currentCase.dirId;
		
		Flywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建"+((type=="trans")?"转换":"作业"),
			width : 500,
			height : 70,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/"+type+"/create/"+dirId,
			params : {
				targetId : targetId+":content"
			},
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				click : {
					formId:"di_create_form",
					formAction:"rest/"+type+"/createsubmit",
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
	},
	edit : function(){
		if (!Flywet.editors.item.checkSelected(Flywet.di.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Flywet.editors.item.getOneSelected(Flywet.di.ids.bpVarName);
		
		if(selItem.type=='node'){
			
			var currentCase = window[Flywet.di.ids.bpVarName].getCurrentData();
			var targetId = "edit_dialog_folder";
			var dirId = currentCase.dirId;
			var _self = this;
			
			Flywet.cw("Dialog",targetId+"_var",{
				id : targetId,
				header : "修改目录",
				width : 500,
				height : 70,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/di/dir/edit/"+dirId+"/"+selItem.id,
				params : {
					targetId : targetId+":content",
					desc : selItem.displayName
				},
				footerButtons : [{
					componentType : "fly:PushButton",
					type : "button",
					label : "确定",
					title : "确定",
					click : {
						formId:"folder_edit_form",
						formAction:"rest/di/dir/editsubmit",
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
			
		}else{
		
			var tabName = selItem.category+"-"+selItem.id+"-tab";
			Flywet.ab({
				type : "get",
				modal : true,
				modalMessage : "正在加载【"+selItem.displayName+"】...",
				url : "rest/"+selItem.category+"/"+selItem.id,
				onsuccess : function(data, status, xhr){
					baEditorPageTabs.addTab({
						exdata: data,
						tabId: selItem.category,
						tabText: selItem.displayName,
						dataTarget: tabName,
						closable: true,
						closePanel: false,
				        checkModify: true
					});
				}
			});
		
		}
	},
	remove : function(){
		if (!Flywet.editors.item.checkSelected(Flywet.di.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var _self = this;
		
		var currentCase = window[Flywet.di.ids.bpVarName].getCurrentData();
		var dirId = currentCase.dirId;
		
		var selItem = Flywet.editors.item.getOneSelected(Flywet.di.ids.bpVarName);
		var url,text;
		if(selItem.type=='node'){
			url = "rest/di/dir/remove/"+selItem.id;
			text = "确认删除目录【"+selItem.displayName+"】？";
		}else{
			url = "rest/"+selItem.category+"/"+selItem.id+"/remove";
			text = "确认删除对象【"+selItem.displayName+"】？";
		}
		
		Flywet.cw("ConfirmDialog",null,{type:"confirm",text:text,
			confirmFunc:function(e,v){
				if(v){
					Flywet.ab({
						type : "get",
						url : url,
						onsuccess:function(data, status, xhr) {
							if (data.state == 0) {
								_self.flushDir(dirId);
							}
						}
					});
				}
			}
		});
	},
	uploadFile : function(){
	},
	downloadFile : function(){
		if (!Flywet.editors.item.checkSelected(Flywet.di.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Flywet.editors.item.getOneSelected(Flywet.di.ids.bpVarName);
	},
	
	flushDir : function(id){
		Flywet.ab({
			type : "get",
			url : "rest/di/dir/"+id
		});
	}
};

