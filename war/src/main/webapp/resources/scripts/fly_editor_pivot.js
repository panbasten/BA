// 多维报表
Flywet.editors.pivot = {
	type : "pivot",
	editorType : "tab",
	
	saveStatus : function ($tabo) {
	},
	
	reloadStatus : function ($taba) {
		var $tab = $taba.parent();
		
		if(!$tab.data('easytabs').cached){
			var $panel = $tab.data('easytabs').panel,
				exdata = $taba.data("exdata");
			
			Flywet.ajax.AjaxUtils.appendElement($panel, exdata.dom, exdata.script);
			
			$tab.data('easytabs').cached = true;

		}
	},
	
	openEditor : function(category,data,displayName,tabName){
		Flywet.ab({
			type : "get", 
			modal : true,
			modalMessage : "正在加载【"+displayName+"】...",
			url : "rest/"+data.attrs.src,
			onsuccess : function(data, status, xhr){
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