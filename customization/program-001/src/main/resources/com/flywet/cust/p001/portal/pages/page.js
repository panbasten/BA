function predictSettingUpdate(){
	Flywet.PortalAction.updateMenuDialog(105,21,'month_predict_files');
}

function downloadFile(obj,data){
	$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/portalet/getfile?rootPath="+data.attrs.rootPath+"&category="+data.attrs.category+"&path="+encodeURIComponent(data.data.path));
}