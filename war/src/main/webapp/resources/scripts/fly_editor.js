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
		if($tabo && $tabo.data("tabType")){
			eval("Flywet.editors."+$tabo.data("tabType")+".saveStatus($tabo);");
		}
		if($taba && $taba.data("tabType")){
			eval("Flywet.editors."+$taba.data("tabType")+".reloadStatus($taba);");
		}
	},
	saveTab : function(clicked) {
		eval("Flywet.editors."+clicked.data("tabType")+".action.saveTab(clicked);");
	},
	discardTab : function(clicked) {
		eval("Flywet.editors."+clicked.data("tabType")+".action.discardTab(clicked);");
	},
	flushTab : function($taba,params) {
		if($taba && $taba.data("tabType")){
			eval("Flywet.editors."+$taba.data("tabType")+".flushStatus($taba,params);");
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
	getStatus : function(id){
		if(typeof(id)=="string"){
			var rtn = {};
			rtn.active = this.isActive(id);
			rtn.enabled = this.isEnabled(id);
			rtn.show = this.isShow(id);
			return rtn;
		}else if(id instanceof Array){
			var rtn = {};
			for(var i=0;i<id.length;i++){
				rtn[id[i]] = this.getStatus(id[i]);
			}
			return rtn;
		}
	},
	
	setStatus : function(id,status){
		if(status.active != undefined){
			if(status.active){
				this.active(id);
			}else{
				this.inactive(id);
			}
		}
		if(status.enabled != undefined){
			if(status.enabled){
				this.enabled(id);
			}else{
				this.disabled(id);
			}
		}
		if(status.show != undefined){
			if(status.show){
				this.show(id);
			}else{
				this.hide(id);
			}
		}
	},
		
	isActive : function(id){
		if(typeof(id)=="string"){
			return $(Flywet.escapeClientId(id)).hasClass("ui-state-active");
		}else if(id instanceof Array){
			var rtn = {};
			for(var i=0;i<id.length;i++){
				rtn[id[i]] = this.isActive(id[i]);
			}
			return rtn;
		}
	},
	
	active : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).addClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				this.active(id[i]);
			}
		}
	},
	
	inactive : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).removeClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				this.inactive(id[i]);
			}
		}
	},
	
	isEnabled : function(id){
		if(typeof(id)=="string"){
			return !$(Flywet.escapeClientId(id)).hasClass("ui-state-disabled");
		}else if(id instanceof Array){
			var rtn = {};
			for(var i=0;i<id.length;i++){
				rtn[id[i]] = this.isEnabled(id[i]);
			}
			return rtn;
		}
	},
	
	enabled : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).removeClass("ui-state-disabled").removeAttr("disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				this.enabled(id[i]);
			}
		}
	},
	
	disabled : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).addClass("ui-state-disabled").attr("disabled","disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				this.disabled(id[i]);
			}
		}
	},
	
	isShow : function(id){
		if(typeof(id)=="string"){
			return $(Flywet.escapeClientId(id)).is(":visible");
		}else if(id instanceof Array){
			var rtn = {};
			for(var i=0;i<id.length;i++){
				rtn[id[i]] = this.isShow(id[i]);
			}
			return rtn;
		}
	},
	
	show : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).show();
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				this.show(id[i]);
			}
		}
	},
	
	hide : function(id){
		if(typeof(id)=="string"){
			$(Flywet.escapeClientId(id)).hide();
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				this.hide(id[i]);
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

