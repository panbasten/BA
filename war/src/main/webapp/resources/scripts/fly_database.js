Plywet.database = {
	changeConnectionType : function(formId){
		var connectionType = $(Plywet.escapeClientId(formId+":connectionType")).val();
		var accessType = $(Plywet.escapeClientId(formId+":accessType")).val();
		var id = $(Plywet.escapeClientId(formId+":id")).val();
		Plywet.ab({
			type : "GET",
			url : "rest/db/connectionsetting/" + id,
			params : {
				targetId : formId+":connection",
				connectionType : connectionType,
				accessType : accessType
			}
		});
	},
	option_add_on_click : function(formId){
		var grid = $(Plywet.escapeClientId(formId+":options"));
		grid.datagrid("appendRow",{
			'key' : '',
			'value' : ''
		});
		var lastIndex = grid.datagrid("getRows").length - 1;
		for(var i=0;i<lastIndex;i++){
			grid.datagrid('endEdit', i);
		}
		grid.datagrid('beginEdit', lastIndex);
	}
};