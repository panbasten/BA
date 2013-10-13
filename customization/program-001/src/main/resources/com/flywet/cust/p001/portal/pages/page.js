function predictSettingUpdate(){
	Flywet.PortalAction.updateMenuDialog(105,21,'month_predict_files');
}

function dataUpdateSubmit(dialogId,event,params){
	Flywet.Portal.pageCover(true);
	Flywet.Portal.UPLOAD_DIALOG_ID = dialogId;
	$(Flywet.escapeClientId("portal_upload_form")).submit();
}

function dataUpdateCallback(target){
	Flywet.Portal.pageCover(false);
	
	var msg = $(document.getElementById('portal_upload_space_frame').contentWindow.document.body).find("pre");
	msg = $(msg).html();
	if(msg && msg != ""){
		msg = Flywet.parseJSON(msg);
		// 清空
		$(document.getElementById('portal_upload_space_frame').contentWindow.document.body).html("");
		window[Flywet.Portal.UPLOAD_DIALOG_ID + "_var"].hide();
		Flywet.ajax.ShowMessage(msg);
	}
}

function downloadFile(obj,data){
	$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/portalet/getfile?rootPath="+data.attrs.rootPath+"&category="+data.attrs.category+"&path="+encodeURIComponent(data.data.path));
}