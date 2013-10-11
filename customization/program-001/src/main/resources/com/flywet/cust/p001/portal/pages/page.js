function predictSettingUpdate(){
	Flywet.Portal.updateMenuDialog(105,21,'month_predict_files');
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

function seasonForecast(dialogId,event,params){
	Flywet.Portal.openEditFileDialog('custom.portal.seasonForecast.file.rootPath','custom.portal.seasonForecast.file.category','custom.portal.seasonForecast.file.fileName','namelist');
}

function evaluation(dialogId,event,params){
	Flywet.Portal.openEditFileDialog('custom.portal.evaluation.file.rootPath','custom.portal.evaluation.file.category','custom.portal.evaluation.file.fileName','cityforecast.txt');
}

function processForecast(dialogId,event,params){
	Flywet.Portal.openEditFileDialog('custom.portal.processForecast.file.rootPath','custom.portal.processForecast.file.category','custom.portal.processForecast.file.fileName','namelist');
}

function downloadFile(obj,data){
	$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/portalet/getfile?rootPath="+data.attrs.rootPath+"&category="+data.attrs.category+"&path="+encodeURIComponent(data.data.path));
}