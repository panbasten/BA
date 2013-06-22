Plywet.database = {
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
	}
};