Flywet.filesys = {
	fsopContext:{},
	ids : {
		bpVarName : "editorContent-navi-filesys-bp_var",
		rootBtns : ["fs-btn-create","fs-btn-edit","fs-btn-remove"],
		itemBtns : ["fs-btn-create-dir","fs-btn-upload-file","fs-btn-download-file"]
	},
	tb : Flywet.editors.toolbarButton,
	getItemData : function(selItem) {
		var data = {
			rootId:selItem.rootId,
			path:selItem.path,
			category:selItem.category
		};
		return encodeURIComponent(Flywet.toJSONString(data));
	},
	delFile	: function() {
		if (!Flywet.editors.item.checkSelected(Flywet.filesys.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		Flywet.ab({
			type : "DELETE",
			url : "rest/fsop/delete?data=" + Flywet.filesys.getItemData(selItem)
		});
	},
	downloadFile : function() {
		if (!Flywet.editors.item.checkSelected(Flywet.filesys.ids.bpVarName)) {
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		if(selItem.type == "node"){
			Flywet.dialog.error("系统无法下载文件夹，请选中一个文件对象进行下载。");
		}else{
			Flywet.Logger.debug("rest/fs/download?data="+Flywet.filesys.getItemData(selItem));
			$(Flywet.escapeClientId("filesys-space-frame")).attr("src","rest/fs/download?data="+Flywet.filesys.getItemData(selItem));
		}
	},
	uploadResult : function(target) {
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var targetId = "upload_dialog_" + currentCase.category;
		
		var msg = $(document.getElementById('fs_upload_space_frame').contentWindow.document.body).find("pre");
		msg = $(msg).html();
		if(msg && msg != ""){
			msg = Flywet.parseJSON(msg);
			// 清空
			$(document.getElementById('fs_upload_space_frame').contentWindow.document.body).html("");
			
			window[targetId + "_var"].hide();
			this.flushDir(currentCase.category,currentCase);
			
			Flywet.ajax.ShowMessage(msg);
		}
	},
	uploadFile	: function() {
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var targetId = "upload_dialog_" + currentCase.category;
		
		Flywet.cw("Dialog",targetId + "_var",{
			id : targetId,
			header : "文件上传",
			width : 400,
			height : 300,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/fs/items/upload?data="+Flywet.filesys.getItemData(currentCase),
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "新增",
				title : "新增",
				events: {
					click:function(){
						var layout = $(Flywet.escapeClientId("fs_upload_form")).find(".ui-grid-layout");
						var num = layout.find("label").size() + 1;
						layout.append('<div class="ui-grid-layout-item ui-layout-float ui-helper-clearfix" style="width: 30%; height: 28px;">' +
								'<label class="ui-label-default ui-helper-clearfix" for="fs'+num+'" style="margin:5px 30px 5px 5px;float:right;" text="上传文件'+num+'：" buddy="fs'+num+'">上传文件'+num+'：</label>' +
								'</div>');
						layout.append('<div class="ui-grid-layout-item ui-layout-float ui-helper-clearfix" style="width: 70%; height: 28px;">' +
								'<input id="fs'+num+'" type="file" size="30" name="fs'+num+'">' +
								'</div>');
					}
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "上传",
				title : "上传",
				events: {
					click:function(){
						$(Flywet.escapeClientId("fs_upload_form")).submit();
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
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		var targetId = "rename_dialog_" + selItem.category + "_" + selItem.type;
		
		Flywet.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "重命名【" + selItem.name + "】",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fs/items/rename?srcName=" + selItem.name+"&data=" + Flywet.filesys.getItemData(selItem),
				footerButtons : [{
					componentType : "fly:PushButton",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							Flywet.ab({
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
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var category =  currentCase.category;
		var targetId = "fs_create_dialog_folder";
		
		Flywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建目录",
			width : 500,
			height : 70,
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
						Flywet.ab({
							formId:"fs_folder_create_form",
							formAction:"rest/fs/items/folder/createsubmit",
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[targetId + "_var"].hide();
									_self.flushDir(category,currentCase);
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
	
	cut:function(){
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		if (selItem) {
			var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
			this.fsopContext = currentCase;
			this.fsopContext['path'] = selItem.path;
			this.fsopContext['operation'] = 'CUT';
		}
	},
	copy:function(){
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		if (selItem) {
			var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
			this.fsopContext = currentCase;
			this.fsopContext['path'] = selItem.path;
			this.fsopContext['operation'] = 'COPY';
		}
	},
	paste:function(){
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var operateCase = this.fsopContext;
		
		//TODO 
		Flywet.ab({
			type : "get",
			url : "rest/fsop/paste?currentCase="+Flywet.toJSONString(currentCase)+"&operateCase="+Flywet.toJSONString(operateCase)
		});
	},
	create:function(){
		var _self = this;
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var category =  currentCase.category;
		
		// 打开的dialog的id
		var targetId = "dialog_" + currentCase.category;
		
		Flywet.cw("Dialog", targetId + "_var",{
			id : targetId,
			header : "新增根目录属性",
			width : 350,
			height : 165,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/fs/items/create/"+category,
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events: {
					click:function(){
						Flywet.ab({
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
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var category = currentCase.category;
		
		// 打开的dialog的id
		var targetId = "dialog_" + currentCase.category;
		
		// 获得选择项
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		if(!selItem){
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var id = selItem.id;
		
		Flywet.cw("Dialog", targetId + "_var",{
			id : targetId,
			header : "编辑根目录属性",
			width : 350,
			height : 165,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/fs/items/edit/"+category+"/"+id,
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events: {
					click:function(){
						Flywet.ab({
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
		var currentCase = window[Flywet.filesys.ids.bpVarName].getCurrentData();
		var category = currentCase.category;
		
		// 获得选择项
		var selItem = Flywet.editors.item.getOneSelected(Flywet.filesys.ids.bpVarName);
		if(!selItem){
			Flywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var id = selItem.id;
		
		Flywet.cw("ConfirmDialog",null,{type:"confirm",
			text:"确认删除文件夹[ " + selItem.displayName + " ]？",
			confirmFunc:function(e,val){
				if(val){
					Flywet.ab({
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
		Flywet.ab({
			type : "get",
			url : "rest/fs/root/"+category
		});
	},
	
	flushDir:function(category,data){
		Flywet.ab({
			type : "get",
			url : "rest/fs/items/list/"+category+"?data="+Flywet.filesys.getItemData(data)
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