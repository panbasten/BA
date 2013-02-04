YonYou.filesys = {
	fsopContext:{},
	delFile	: function() {
		if (!this.checkSelected()) {
			return;
		}
		var selItem = this.getOneSelected();
		YonYou.ab({
			type : "DELETE",
			url : "rest/fsop/delete?data=" + YonYou.toJSONString(selItem)
		});
	},
	downloadFile	: function() {
		if (!this.checkSelected()) {
			return;
		}
		var selItem = this.getOneSelected();
		$('#space_frame').attr("src","rest/fsop/download?data="+YonYou.toJSONString(selItem));
		/*
		YonYou.ab({
			type : "get",
			url : "rest/fsop/download",
			params: {
				"data": YonYou.toJSONString(selItem)
			}
		});
		*/
	},
	uploadFile	: function() {
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "upload_dialog_" + currentCase.category + "_" + currentCase.rootId;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "文件上传",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fs/items/upload?data="+YonYou.toJSONString(currentCase),
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "新增",
					title : "新增",
					events: {
						click:function(){
							$('#files_div').append('<input type="file" name="file"></input>');
						}
					}
				},{
					componentType : "bq:Button",
					type : "button",
					label : "上传",
					title : "上传",
					events: {
						click:function(){
							$("#fs_upload_form").submit();
							/*YonYou.ab({
								type : "POST",
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
					componentType : "bq:Button",
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
	rename	: function() {
		var selItem = this.getOneSelected();
		var targetId = "rename_dialog_" + selItem.category + "_" + selItem.type;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "重命名【" + selItem.name + "】",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fs/items/rename?srcName=" + selItem.name+"&data=" + YonYou.toJSONString(selItem),
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							YonYou.ab({
								type : "POST",
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
					componentType : "bq:Button",
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
	createDir	: function() {
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "create_dialog_" + currentCase.category + "_" + currentCase.rootId;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "新建目录",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fs/items/create?data="+YonYou.toJSONString(currentCase),
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							YonYou.ab({
								type : "POST",
								url : "rest/fsop/create",
								source:"fs_create_form",
								onsuccess:function(data, status, xhr) {
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});
						}
					}
				},{
					componentType : "bq:Button",
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
	getOneSelected:function() {
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
		
		YonYou.ab({
			type : "GET",
			url : "rest/fsop/paste?currentCase="+YonYou.toJSONString(currentCase)+"&operateCase="+YonYou.toJSONString(operateCase)
		});
	},
	addHost:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "create_dialog_" + currentCase.category + "_" + currentCase.rootId;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "新增主机",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/host/setting",
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							YonYou.ab({
								type : "POST",
//								url : "rest/host/setting",
								source:"fs_host_form",
								onsuccess:function(data, status, xhr) {
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});
						}
					}
				},{
					componentType : "bq:Button",
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
	editHost:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "create_dialog_" + currentCase.category + "_" + currentCase.rootId;
		
		var selItem = this.getOneSelected();
		var id = selItem.id;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "编辑主机",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/host/setting?hostId=" + id,
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							YonYou.ab({
								type : "POST",
								url : "rest/host/setting",
								source:"fs_host_form",
								onsuccess:function(data, status, xhr) {
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});
						}
					}
				},{
					componentType : "bq:Button",
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
	removeHost:function(){
		var selItem = this.getOneSelected();
		YonYou.ab({
			type : "DELETE",
			url : "rest/host/" + selItem.id
		});
	},
	addFsLocal:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "create_dialog_" + currentCase.category + "_" + currentCase.rootId;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "新增根目录",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fslocal/setting",
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							YonYou.ab({
								type : "POST",
								url : "rest/fslocal/setting",
								source:"fs_fslocal_form",
								onsuccess:function(data, status, xhr) {
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});
						}
					}
				},{
					componentType : "bq:Button",
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
	editFsLocal:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var targetId = "create_dialog_" + currentCase.category + "_" + currentCase.rootId;
		
		var selItem = this.getOneSelected();
		var id = selItem.id;
		
		YonYou.cw("Dialog",targetId + "_var",{
				id : targetId,
				header : "编辑根目录",
				width : 700,
				height : 400,
				autoOpen : true,
				showHeader : true,
				modal : true,
				url : "rest/fslocal/setting?rootId=" + id,
				footerButtons : [{
					componentType : "bq:Button",
					type : "button",
					label : "确定",
					title : "确定",
					events: {
						click:function(){
							YonYou.ab({
								type : "POST",
								url : "rest/fslocal/setting",
								source:"fs_fslocal_form",
								onsuccess:function(data, status, xhr) {
									if (data.state == 0) {
										window[targetId + "_var"].hide();
									}
								}
							});
						}
					}
				},{
					componentType : "bq:Button",
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
	removeFsLocal:function(){
		var selItem = this.getOneSelected();
		YonYou.ab({
			type : "DELETE",
			url : "rest/fslocal/" + selItem.id,
			onsuccess:function(data, status, xhr) {
						alert(data.messages[0]);
					}
		});
	},
	add:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category =  currentCase.category;
		if ("local" == category) {
			this.addFsLocal();
		} else {
			this.addHost();
		}
	},
	edit:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category =  currentCase.category;
		if ("local" == category) {
			this.editFsLocal();
		} else {
			this.editHost();
		}
	},
	remove:function(){
		var currentCase = window["editorContent-navi-filesys-bp_var"].getCurrentData();
		var category =  currentCase.category;
		if ("local" == category) {
			this.removeFsLocal();
		} else {
			this.removeHost();
		}
	}
};