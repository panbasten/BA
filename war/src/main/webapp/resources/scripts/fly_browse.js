Flywet.browse = {
	TEMP : {},
	showOperationForDir : function(event,data) {
		if (event.which != 3) { //不是右键
			return false;
		}
		Flywet.browse.TEMP.contextObjectData = data;
		if(window["menu_dir_var"] == undefined){
			Flywet.cw("Menu","menu_dir_var",{
				id : "menu_dir",
				itemWidth : 120,
				menuItems : [{
					id : "menu_dir_open",
					text : "<b>打开</b>",
					iconCls : "ui-icon-system-open",
					onclick : "Flywet.browse.changeDirByContext(event)"
				},{
					type : "separator"
				},{
					id : "menu_dir_cut",
					text : "剪切",
					iconCls : "ui-icon-system-cut"
				},{
					id : "menu_dir_copy",
					text : "复制",
					iconCls : "ui-icon-system-copy"
				},{
					id : "menu_dir_rename",
					text : "重命名",
					iconCls : ""
				},{
					id : "menu_dir_delete",
					text : "删除",
					iconCls : "ui-icon-system-delete"
				}]
			});
		}
		menu_dir_var.show(event);
	},
	changeDirByContext : function(event) {
		Flywet.browse.changeDir(event,Flywet.browse.TEMP.contextObjectData);
	},
	changeDir : function(event,data) {
		Flywet.ab({
			type : "get",
			url : "rest/"+data.attrs.src,
			params: {
				"data":Flywet.toJSONString($.extend({},data.data,data.attrs))
			}
		});
	},
	showOperationForFile : function(event,data) {
		if (event.which != 3) { //不是右键
			return false;
		}
		Flywet.browse.TEMP.contextObjectData = data;
		var category = data.data.category;
		if(category=="trans" || category=="job"){
			if(window["menu_file_var"] == undefined){
				Flywet.cw("Menu","menu_file_var",{
					id : "menu_file",
					itemWidth : 120,
					menuItems : [{
						id : "menu_file_open",
						text : "<b>打开</b>",
						iconCls : "ui-icon-system-open",
						onclick : "Flywet.browse.openFileByContext(event)"
					},{
						type : "separator"
					},{
						id : "menu_file_cut",
						text : "剪切",
						iconCls : "ui-icon-system-cut"
					},{
						id : "menu_file_copy",
						text : "复制",
						iconCls : "ui-icon-system-copy"
					},{
						id : "menu_file_rename",
						text : "重命名",
						iconCls : ""
					},{
						id : "menu_file_delete",
						text : "删除",
						iconCls : "ui-icon-system-delete"
					},{
						type : "separator"
					},{
						id : "menu_file_attribute",
						text : "属性",
						iconCls : "ui-icon-system-setting",
						onclick : "Flywet.browse.openFileSettingByContext(event)"
					}]
				});
			}
			menu_file_var.show(event);
		}else if(category=="db"){
			if(window["menu_db_var"] == undefined){
				Flywet.cw("Menu","menu_db_var",{
					id : "menu_db",
					itemWidth : 120,
					menuItems : [{
						id : "menu_db_open",
						text : "<b>打开</b>",
						iconCls : "ui-icon-system-open",
						onclick : "Flywet.browse.openFileByContext(event)"
					},{
						type : "separator"
					},{
						id : "menu_db_copy",
						text : "复制",
						iconCls : "ui-icon-system-copy"
					},{
						id : "menu_file_rename",
						text : "重命名",
						iconCls : ""
					},{
						id : "menu_db_delete",
						text : "删除",
						iconCls : "ui-icon-system-delete"
					}]
				});
			}
			menu_db_var.show(event);
		}
		
	},
	openFileSettingByContext : function(event){
		var data = Flywet.browse.TEMP.contextObjectData;
		var category = data.data.category;
		var dialog_var = "dialog_"+category+"_"+data.data.type+"_"+data.data.id+"_var";
		Flywet.cw("Dialog",dialog_var,{
			id : "dialog:"+category+":"+data.data.type+":"+data.data.id,
			header : "设置【"+data.attrs.displayName+"】",
			width : 700,
			height : 400,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/"+data.attrs.src+"/setting",
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				click : {
					formId : category+"_"+data.data.id,
					formAction : "rest/"+data.attrs.src+"/setting/save"
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
	openFileByContext : function(event) {
		Flywet.browse.openFile(event,Flywet.browse.TEMP.contextObjectData);
	},
	openFile : function(event,data) {
		var superData = data,
			category = data.data.category,
			tabName = category+"-"+data.id+"-tab",
			displayName = data.attrs.displayName;
		
		// 如果是dialog类型的编辑界面
		if(Flywet.editors[category].editorType == "dialog"){
			Flywet.editors[category].openEditor(category,data,displayName);
		}
		// 打开tab类型编辑界面
		else{
			if (baEditorPageTabs.hasMatch(tabName)) {
				baEditorPageTabs.select(tabName);
				return;
			}
			Flywet.editors[category].openEditor(category,data,displayName,tabName);
		}
		
	}
};
