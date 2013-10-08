
function downloadFile(obj,data){
	$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/portalet/getfile?rootPath="+data.attrs.rootPath+"&category="+data.attrs.category+"&path="+encodeURIComponent(data.data.path));
}