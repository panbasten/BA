function monthPredictClick(id, param){
	Flywet.ab({
		type : "get",
		url : "rest/portalet/menu/"+id+"/update",
		params : {
			method : "monthPredictUpdate"
			,targetId : "month_predict_imgs,month_predict_files"
			,param : param
		}
	});
}

function downloadFile(obj,data){
	$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/portalet/getfile?rootPath="+data.attrs.rootPath+"&category="+data.attrs.category+"&path="+encodeURIComponent(data.data.path));
}