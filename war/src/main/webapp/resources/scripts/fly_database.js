Plywet.database = {
	ids : {
		bpVarName : "editorContent-navi-db-bp_var"
	},
	changeConnectionType : function(formId){
		var connectionType = $(Plywet.escapeClientId(formId+":connectionType")).val();
		var accessType = $(Plywet.escapeClientId(formId+":accessType")).val();
		var id = $(Plywet.escapeClientId(formId+":id")).val();
		Plywet.ab({
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
		Plywet.cw("Dialog",dialog_var,{
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
				events : {
					"click" : function(){
						Plywet.ab({
							formId : "db_create",
							formAction : "rest/db/object/create/save",
							onsuccess: function (data, status, xhr) {
								if (data.state == 0) {
                                	window[dialog_var].hide();
                                	// 刷新页面
                                	_self.flush();
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
	},
	
	editObject : function(category, type, id, displayName){
		var dialog_var = "dialog_"+category+"_"+type+"_"+id+"_var";
		var _self=this;
		Plywet.cw("Dialog",dialog_var,{
			id : "dialog:"+category+":"+type+":"+id,
			header : "编辑【"+displayName+"】",
			width : 700,
			height : 400,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/db/object/"+id,
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events : {
					"click" : function(){
						Plywet.ab({
							formId : "db_"+id,
							formAction : "rest/db/object/"+id+"/save",
							onsuccess: function (data, status, xhr) {
								if (data.state == 0) {
                                	window[dialog_var].hide();
                                	// 刷新页面
                                	_self.flush();
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
	},
	
	edit : function(){
		if (!Plywet.editors.item.checkSelected(Plywet.database.ids.bpVarName)) {
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Plywet.editors.item.getOneSelected(Plywet.database.ids.bpVarName);
		this.editObject(selItem.category, selItem.type, selItem.id, selItem.displayName);
	},
	
	remove : function(){
		if (!Plywet.editors.item.checkSelected(Plywet.database.ids.bpVarName)) {
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Plywet.editors.item.getOneSelected(Plywet.database.ids.bpVarName);
		var _self=this;
		Plywet.ab({
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
		Plywet.ab({
			type : "GET",
			url : "rest/db/flush"
		});
	}
};