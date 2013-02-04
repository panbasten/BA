YonYou.database = {
	changeConnectionType : function(formId){
		var connectionType = $(YonYou.escapeClientId(formId+":connectionType")).val();
		var accessType = $(YonYou.escapeClientId(formId+":accessType")).val();
		var id = $(YonYou.escapeClientId(formId+":id")).val();
		YonYou.ab({
			type : "GET",
			url : "rest/db/connectionsetting/" + id,
			params : {
				targetId : formId+":connectionFieldset",
				connectionType : connectionType,
				accessType : accessType
			}
		});
	}
};