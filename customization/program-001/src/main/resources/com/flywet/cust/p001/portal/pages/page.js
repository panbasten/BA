function monthPredictClick(key){
	var content = $(Flywet.escapeClientId("month_predict_imgs:Content"));
	content.html("");
	var img1 = $("<img style='height:400px;width:300px;float:left;'></img>");
	img1.attr("src", "rest/portalet/getfile?path="+key+"%2F1.jpg");
	content.append(img1);
}