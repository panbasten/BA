Flywet.editors = {
	register : {},
	resize : function(){
		for(var i in Flywet.editors.register){
			if(Flywet.editors.register[i] == "Y"){
				Flywet.editors[i].resize();
			}
		}
	},
	changeEditor : function ($taba,$tabo) {
		if($tabo && $tabo.data("tabId")){
			eval("Flywet.editors."+$tabo.data("tabId")+".saveStatus($tabo);");
		}
		if($taba && $taba.data("tabId")){
			eval("Flywet.editors."+$taba.data("tabId")+".reloadStatus($taba);");
		}
	},
	saveTab : function(clicked) {
		eval("Flywet.editors."+clicked.data("tabId")+".action.saveTab(clicked);");
	},
	discardTab : function(clicked) {
		eval("Flywet.editors."+clicked.data("tabId")+".action.discardTab(clicked);");
	},
	flushTab : function($taba,params) {
		if($taba && $taba.data("tabId")){
			eval("Flywet.editors."+$taba.data("tabId")+".flushStatus($taba,params);");
		}
	}
};

Flywet.editors.item = {
	// 检查是否选中
	checkSelected : function(target) {
		var bpvar = (typeof(target)=="string")?(window[target]):target;
		var selItems = bpvar.getSelections();
		if (!selItems || selItems.length == 0) {
			return false;
		}
		if (selItems.length > 1) {
			return false;
		}
		return true;
	},
	// 获得一个选中节点
	getOneSelected : function(target) {
		var bpvar = (typeof(target)=="string")?(window[target]):target;
		if (this.checkSelected(target)) {
			var selItems = bpvar.getSelections();
			return selItems[0];	
		}
		
		return null;
	}
};

Flywet.editors.toolbarButton = {
	isActive : function(id){
		return $(Flywet.escapeClientId(id)).hasClass("ui-state-active");
	},
	
	active : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).addClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Flywet.escapeClientId(id[i])).addClass("ui-state-active");
			}
		}
	},
	
	inactive : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).removeClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Flywet.escapeClientId(id[i])).removeClass("ui-state-active");
			}
		}
	},
	
	enabled : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).removeClass("ui-state-disabled").removeAttr("disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Flywet.escapeClientId(id[i])).removeClass("ui-state-disabled").removeAttr("disabled");
			}
		}
	},
	
	disabled : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).addClass("ui-state-disabled").attr("disabled","disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Flywet.escapeClientId(id[i])).addClass("ui-state-disabled").attr("disabled","disabled");
			}
		}
	},
	
	show : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).show();
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Flywet.escapeClientId(id[i])).show();
			}
		}
	},
	
	hide : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).hide();
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Flywet.escapeClientId(id[i])).hide();
			}
		}
	},
	
	/**
	 * 添加一行
	 * 
	 * @param id Grid的ID
	 * @param appendRow 添加行的初始值，例如：{'key':'','value':''}
	 * 		如果为空，添加行的所有列是空的初始值
	 */
	addRow : function(id,appendRow){
		var grid = $(Flywet.escapeClientId(id));
		grid.datagrid("appendRow",(appendRow)?appendRow:{});
	},
	
	/**
	 * 删除一行
	 * 
	 * @param id Grid的ID
	 */
	deleteRow : function(id){
		var grid = $(Flywet.escapeClientId(id));
		var row = grid.datagrid('getSelected');
        if (row) {
            var index = grid.datagrid('getRowIndex', row);
            grid.datagrid('deleteRow', index);
        }
	}
};

