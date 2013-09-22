function monthPredictClick(key){
	var content = $(Flywet.escapeClientId("month_predict_imgs:Content"));
	var dim = Flywet.getElementDimensions(content);
	var width = dim.css.width / 4, height = width / 3 * 4;
	content.html("");
	// 气候平均气温
	var img1 = $("<img style='height:"+height+"px;width:"+width+"px;float:left;'></img>");
	img1.attr("src", "rest/portalet/getfile?path="+key+"%2F%E6%B0%94%E5%80%99%E5%B9%B3%E5%9D%87%E6%B0%94%E6%B8%A9.jpg");
	content.append(img1);
	
	// 气候平均降水
	var img2 = $("<img style='height:"+height+"px;width:"+width+"px;float:left;'></img>");
	img2.attr("src", "rest/portalet/getfile?path="+key+"%2F%E6%B0%94%E5%80%99%E5%B9%B3%E5%9D%87%E9%99%8D%E6%B0%B4.jpg");
	content.append(img2);
	
	// 气温预测
	var img3 = $("<img style='height:"+height+"px;width:"+width+"px;float:left;'></img>");
	img3.attr("src", "rest/portalet/getfile?path="+key+"%2F%E6%B0%94%E6%B8%A9%E9%A2%84%E6%B5%8B.jpg");
	content.append(img3);
	
	// 降水预测
	var img4 = $("<img style='height:"+height+"px;width:"+width+"px;float:left;'></img>");
	img4.attr("src", "rest/portalet/getfile?path="+key+"%2F%E9%99%8D%E6%B0%B4%E9%A2%84%E6%B5%8B.jpg");
	content.append(img4);
	
}