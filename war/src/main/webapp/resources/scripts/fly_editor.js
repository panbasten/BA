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
	}
};

