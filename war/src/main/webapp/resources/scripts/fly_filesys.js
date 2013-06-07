Plywet.filesys = {
	fsopContext:{},
	ids : {
		rootBtns : ["fs-btn-create","fs-btn-edit","fs-btn-remove"],
		itemBtns : ["fs-btn-create-dir","fs-btn-upload-file","fs-btn-download-file"]
	},
	tb : Plywet.editors.toolbarButton,
	delFile	: function() {
		if (!this.checkSelected()) {
			return;
		}
		var selItem = this.getOneSelected();
		Plywet.ab({
			type : "DELETE",
			url : "rest/fsop/delete?data=" + Plywet.toJSONString(selItem)
		});
	},
	downloadFile	: function() {
		if (!this.checkSelected()) {
			return;
		}
		var selItem = this.getOneSelected();
		if(selItem.type == "node"){
			Plywet.dialog.error("系统无法下载文件夹，请选中一个文件对象进行下载。");
		}else{
			selItem = {
				rootId : selItem.rootId,
				path : selItem.path,
				category : selItem.category
			};
			$(Plywet.escapeClientId("filesys-space-frame")).attr("src","rest/fs/download?data="+encodeURIComponent(Plywet.toJSONString(selItem)));
		}
	},
	uploadFile	: function() {
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "upload_dialog_" + currentCase.category;
		
		Plywet.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "文件上传",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fs/items/upload?data="+Plywet.toJSONString(currentCase),
				footerButtons : [{
					componentType : "fly:PushButton",
					type : "button",
					label : "新增",
					title : "新增",
					events: {
						click:function(){
							$('#files_div').append('<input type="file" name="file"></input>');
						}
					}
				},{
					componentType : "fly:PushButton",
					type : "button",
					label : "上传",
					title : "上传",
					events: {
						click:function(){
							$("#fs_upload_form").submit();
							/*Plywet.ab({
								type : "post",
								url : "rest/fsop/upload",
								source:"fs_upload_form",
								onsuccess:function(data, status, xhr) {
									alert(data.messages[0]);
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});*/
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
	rename : function() {
		var selItem = this.getOneSelected();
		var targetId = "rename_dialog_" + selItem.category + "_" + selItem.type;
		
		Plywet.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "重命名【" + selItem.name + "】",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fs/items/rename?srcName=" + selItem.name+"&data=" + Plywet.toJSONString(selItem),
				footerButtons : [{
					componentType : "fly:PushButton",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							Plywet.ab({
								type : "post",
								url : "rest/fsop/rename",
								source:"fs_rename_form",
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
	},
	createDir : function() {
		var _self = this;
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category =  currentCase.category;
		var targetId = "create_dialog_folder";
		console.log(currentCase);
		
		Plywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建目录",
			width : 500,
			height : 50,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/fs/items/folder/create/"+category,
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
							type : "post",
							source:"fs_folder_create_form",
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[targetId + "_var"].hide();
									_self.flushDir(category,{rootId:currentCase.rootId,path:currentCase.path});
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
	},
	checkSelected:function() {
		var fsbpvar = window["editorContent-navi-filesys-bp_var"];
		
		var selItems = fsbpvar.getSelections();
		if (!selItems || selItems.length == 0) {
			return false;
		}
		if (selItems.length > 1) {
			return false;
		}
		return true;
	},
	
	/**
	 * 获得一个选中节点
	 */
	getOneSelected : function() {
		var fsbpvar = window["editorContent-navi-filesys-bp_var"];
		if (this.checkSelected()) {
			var selItems = fsbpvar.getSelections();
			return selItems[0];	
		}
		
		return null;
	},
	
	cut:function(){
		var selItem = this.getOneSelected();
		if (selItem) {
			var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
			this.fsopContext = currentCase;
			this.fsopContext['path'] = selItem.path;
			this.fsopContext['operation'] = 'CUT';
		}
	},
	copy:function(){
		var selItem = this.getOneSelected();
		if (selItem) {
			var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
			this.fsopContext = currentCase;
			this.fsopContext['path'] = selItem.path;
			this.fsopContext['operation'] = 'COPY';
		}
	},
	paste:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var operateCase = this.fsopContext;
		
		Plywet.ab({
			type : "get",
			url : "rest/fsop/paste?currentCase="+Plywet.toJSONString(currentCase)+"&operateCase="+Plywet.toJSONString(operateCase)
		});
	},
	create:function(){
		var _self = this;
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category =  currentCase.category;
		
		// 打开的dialog的id
		var targetId = "dialog_" + currentCase.category;
		
		Plywet.cw("Dialog", targetId + "_var",{
			id : targetId,
			header : "新增根目录属性",
			width : 350,
			height : 165,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/fs/items/create/"+category+"?targetId="+targetId+":content",
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events: {
					click:function(){
						Plywet.ab({
							type : "post",
							formId:"fs_create_form",
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[targetId + "_var"].hide();
									_self.flush(category);
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
	},
	
	/**
	 * 编辑一个文件夹
	 */
	edit : function(){
		var _self = this;
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category = currentCase.category;
		
		// 打开的dialog的id
		var targetId = "dialog_" + currentCase.category;
		
		// 获得选择项
		var selItem = this.getOneSelected();
		if(!selItem){
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var id = selItem.id;
		
		Plywet.cw("Dialog", targetId + "_var",{
			id : targetId,
			header : "编辑根目录属性",
			width : 350,
			height : 165,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/fs/items/edit/"+category+"/"+id+"?targetId="+targetId+":content",
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events: {
					click:function(){
						Plywet.ab({
							type : "post",
							formId:"fs_edit_form",
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[targetId + "_var"].hide();
									_self.flush(category);
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
		
	},
	remove:function(){
		var _self = this;
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category = currentCase.category;
		
		// 获得选择项
		var selItem = this.getOneSelected();
		if(!selItem){
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var id = selItem.id;
		
		Plywet.cw("ConfirmDialog",null,{type:"confirm",
			text:"确认删除文件夹[ " + selItem.displayName + " ]？",
			confirmFunc:function(e,val){
				if(val){
					Plywet.ab({
						type : "DELETE",
						url : "rest/fs/items/remove/"+category+"/"+id,
						onsuccess:function(data, status, xhr) {
							if (data.state == 0) {
								_self.flush(category);
							}
						}
					});
				}
			}
		});
	},
	
	flush:function(category){
		// 更新当前目录内容
		Plywet.ab({
			type : "get",
			url : "rest/fs/root/"+category
		});
	},
	
	flushDir:function(category,data){
		Plywet.ab({
			type : "get",
			url : "rest/fs/items/list/"+category,
			params : {
				data : Plywet.toJSONString(data)
			}
		});
	},
	
	controlBtn:function(type){
		if(type == "root"){
			this.tb.show(this.ids.rootBtns);
			this.tb.hide(this.ids.itemBtns);
		}else if(type == "item"){
			this.tb.hide(this.ids.rootBtns);
			this.tb.show(this.ids.itemBtns);
		}else{
			this.tb.hide(this.ids.rootBtns);
			this.tb.hide(this.ids.itemBtns);
		}
	}
};