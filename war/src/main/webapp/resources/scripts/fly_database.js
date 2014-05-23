Flywet.db = {
	ids : {
		bpVarName : "editorContent-navi-db-bp_var"
	},
	changeConnectionType : function(formId){
		var connectionType = $(Flywet.escapeClientId(formId+":connectionType")).val();
		var accessType = $(Flywet.escapeClientId(formId+":accessType")).val();
		var id = $(Flywet.escapeClientId(formId+":id")).val();
		Flywet.ab({
			type : "GET",
			url : "rest/db/connectionsetting/" + id,
			params : {
				targetId : formId+":connection:Content",
				connectionType : connectionType,
				accessType : accessType
			}
		});
	},
	
	create : function(){
		var dialog_var = "dialog_db_create_var";
		var _self=this;
		Flywet.cw("Dialog",dialog_var,{
			id : "dialog:db:create",
			header : "新增数据源链接",
			width : 700,
			height : 400,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/db/create",
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				btnStyle : "success",
				click : {
					formId : "db_create",
					formAction : "rest/db/object/create/save",
					onsuccess: function (data, status, xhr) {
						if (data.state == 0) {
	                    	window[dialog_var].hide();
	                    	// 刷新页面
	                    	_self.flush();
	                    }
					}
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "取消",
				title : "取消",
				btnStyle : "link",
				events : {
					"click" : "hide"
				}
			}],
			closable : true,
			maximizable : true
		});
	},
	
	/**
	 * 浏览数据库
	 */
	exploreDatabase : function(category, type, id, displayName){
		var dialog_var = "dialog_"+category+"_"+type+"_"+id+"_explore_var";
		var _self=this;
		Flywet.cw("Dialog",dialog_var,{
			id : "dialog:"+category+":"+type+":explore:"+id,
			header : "浏览【"+displayName+"】",
			width : 400,
			height : 400,
			autoOpen : true,
			showHeader : true,
			modal : true,
			formId : "db_"+id,
			formAction : "rest/db/object/"+id+"/explore",
			footerSettingButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "预览前100行",
				title : "预览前100行",
				btnStyle : "primary",
				click : {
					// TODO
					formId : "db_"+id,
					formAction : "rest/db/object/"+id+"/explore/top100"
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "预览前N行",
				title : "预览前N行",
				btnStyle : "primary",
				click : {
					// TODO
					formId : "db_"+id,
					formAction : "rest/db/object/"+id+"/explore/topn"
				}
			}],
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "关闭",
				title : "关闭",
				btnStyle : "info",
				events : {
					"click" : "hide"
				}
			}],
			closable : true,
			maximizable : true
		});
	},
	
	editObject : function(category, type, id, displayName){
		var dialog_var = "dialog_"+category+"_"+type+"_"+id+"_var";
		var _self=this;
		Flywet.cw("Dialog",dialog_var,{
			id : "dialog:"+category+":"+type+":"+id,
			header : "编辑【"+displayName+"】",
			width : 700,
			height : 400,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/db/object/"+id,
			footerSettingButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "测试",
				title : "测试",
				btnStyle : "primary",
				click : {
					formId : "db_"+id,
					formAction : "rest/db/object/"+id+"/test",
					onsuccess:function(data, status, xhr) {
						// DO NOTHING
					}
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "浏览数据库",
				title : "浏览数据库",
				btnStyle : "primary",
				click : {
					func : function(){
						Flywet.db.exploreDatabase(category, type, id, displayName);
					}
				}
			}],
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				btnStyle : "success",
				click : {
					formId : "db_"+id,
					formAction : "rest/db/object/"+id+"/save",
					onsuccess: function (data, status, xhr) {
						if (data.state == 0) {
	                    	window[dialog_var].hide();
	                    	// 刷新页面
	                    	_self.flush();
	                    }
					}
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "取消",
				title : "取消",
				btnStyle : "link",
				events : {
					"click" : "hide"
				}
			}],
			closable : true,
			maximizable : true
		});
	},
	
	edit : function(){
		if (!Flywet.editors.item.checkSelected(Flywet.db.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Flywet.editors.item.getOneSelected(Flywet.db.ids.bpVarName);
		this.editObject(selItem.category, selItem.type, selItem.id, selItem.displayName);
	},
	
	remove : function(){
		if (!Flywet.editors.item.checkSelected(Flywet.db.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Flywet.editors.item.getOneSelected(Flywet.db.ids.bpVarName);
		var _self=this;
		Flywet.ab({
			type : "DELETE",
			url : "rest/db/object/"+selItem.displayName+"/remove",
			onsuccess: function (data, status, xhr) {
				if (data.state == 0) {
                	// 刷新页面
                	_self.flush();
                }
			}
		});
	},
	
	flush : function(){
		Flywet.ab({
			type : "GET",
			url : "rest/db/flush"
		});
	}
};

Flywet.editors.db = {
	type : "db",
	editorType : "dialog",
	openEditor : function(category,data,displayName){
		Flywet.db.editObject(category,data.data.type,data.data.id,displayName);
	}
};