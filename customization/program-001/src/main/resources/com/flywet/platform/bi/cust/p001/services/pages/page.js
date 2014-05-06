function predictSettingUpdate(){
	Flywet.PortalAction.updateMenuDialog(105,21,'month_predict_files',$('#mouth_predict_setting_select').val());
}

function extendSettingUpdata(){
    Flywet.PortalAction.updateMenuDialog(101,38,'content',$('#esu_title').val());
}

function changeNote(val){
    Flywet.PortalAction.updateMenuDialog(18,41,'fs,rootDir,fileName,category',val);
}

function buzNormsUpdate(){
	Flywet.PortalAction.updateMenuDialog(16,28,'norm_files');
}

function monthPredictEvaluateSettingDownloadFile(dialogId){
	var dialog = $(Flywet.escapeClientId(dialogId+":content"));
	
	var rtn = "",s,c;
	rtn = rtn + "<p>";
	for(var i=0;i<11;i++){
		for(var j=1;j<=8;j++){
			c = dialog.find("#s"+j).get(i);
			rtn = rtn + $(c).val() + " ";
		}
		rtn = rtn + "<br/>";
	}
	rtn = rtn + "</p>";
	
	Flywet.cw("ShowDialog",null,{
		header: "获取数据",
		width:200,
		height:200,
		content:rtn
	});
}