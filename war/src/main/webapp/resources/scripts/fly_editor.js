Plywet.editors = {
	register : {},
	resize : function(){
		for(var i in Plywet.editors.register){
			if(Plywet.editors.register[i] == "Y"){
				Plywet.editors[i].resize();
			}
		}
	},
	changeEditor : function ($taba,$tabo) {
		if($tabo && $tabo.data("tabId")){
			eval("Plywet.editors."+$tabo.data("tabId")+".saveStatus($tabo);");
		}
		if($taba && $taba.data("tabId")){
			eval("Plywet.editors."+$taba.data("tabId")+".reloadStatus($taba);");
		}
	},
	saveTab : function(clicked) {
		eval("Plywet.editors."+clicked.data("tabId")+".action.saveTab(clicked);");
	},
	discardTab : function(clicked) {
		eval("Plywet.editors."+clicked.data("tabId")+".action.discardTab(clicked);");
	}
};

Plywet.editors.item = {
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
	},
};

Plywet.editors.toolbarButton = {
	isActive : function(id){
		return $(Plywet.escapeClientId(id)).hasClass("ui-state-active");
	},
	
	active : function(id){
		if(typeof(id)=="string"){
			$(Plywet.escapeClientId(id)).addClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Plywet.escapeClientId(id[i])).addClass("ui-state-active");
			}
		}
	},
	
	inactive : function(id){
		if(typeof(id)=="string"){
			$(Plywet.escapeClientId(id)).removeClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Plywet.escapeClientId(id[i])).removeClass("ui-state-active");
			}
		}
	},
	
	enabled : function(id){
		if(typeof(id)=="string"){
			$(Plywet.escapeClientId(id)).removeClass("ui-state-disabled").removeAttr("disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Plywet.escapeClientId(id[i])).removeClass("ui-state-disabled").removeAttr("disabled");
			}
		}
	},
	
	disabled : function(id){
		if(typeof(id)=="string"){
			$(Plywet.escapeClientId(id)).addClass("ui-state-disabled").attr("disabled","disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Plywet.escapeClientId(id[i])).addClass("ui-state-disabled").attr("disabled","disabled");
			}
		}
	},
	
	show : function(id){
		if(typeof(id)=="string"){
			$(Plywet.escapeClientId(id)).show();
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Plywet.escapeClientId(id[i])).show();
			}
		}
	},
	
	hide : function(id){
		if(typeof(id)=="string"){
			$(Plywet.escapeClientId(id)).hide();
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(Plywet.escapeClientId(id[i])).hide();
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
		var grid = $(Plywet.escapeClientId(id));
		grid.datagrid("appendRow",(appendRow)?appendRow:{});
		var lastIndex = grid.datagrid("getRows").length - 1;
		for(var i=0;i<lastIndex;i++){
			grid.datagrid('endEdit', i);
		}
		grid.datagrid('beginEdit', lastIndex);
	},
	
	/**
	 * 删除一行
	 * 
	 * @param id Grid的ID
	 */
	deleteRow : function(id){
		var grid = $(Plywet.escapeClientId(id));
		console.log(grid);
		var row = grid.datagrid('getSelected');
		console.log(row);
        if (row) {
            var index = grid.datagrid('getRowIndex', row);
            grid.datagrid('deleteRow', index);
        }
	}
};

