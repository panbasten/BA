// 多维报表
Flywet.editors.pivot = {
	type : "pivot",
	editorType : "tab",
	
	saveStatus : function ($tabo) {
	},
	
	reloadStatus : function ($taba) {
		console.log($taba);
	},
	
	openEditor : function(category,data,displayName,tabName){
		Flywet.ab({
			type : "get",
			modal : true,
			modalMessage : "正在加载【"+displayName+"】...",
			url : "rest/"+data.attrs.src,
			onsuccess : function(data, status, xhr){
				console.log("------ddd-----");
				console.log(data.reportInfo.id);
				baEditorPageTabs.addTab({
					exdata: data,
					tabId: category+"_"+data.reportInfo.id,
					tabType: category,
					tabText: displayName,
					dataTarget: tabName,
					closable: true,
					closePanel: false,
			        checkModify: true
				});
			}
		});
	}
};