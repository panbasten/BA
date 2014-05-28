var changed = false;

function updateData(chartImplType, chartDefineCode, dataXML,width,height){
	changed = true;
	bqChart = bqChart.update(chartImplType, chartDefineCode, dataXML, width, height);

}

function updateSize(width, height){
	changed = true;
	bqChart.rawChartObj.resizeTo(width, height);
}

function chartHasRendered(){	
	chartRenderCompleted(changed);
	changed = false;
}

var params = null;
var mouseX = 0;
var mouseY = 0;
function _onMouseDown(){
	var e = window.event;
	mouseX = e.x;
	mouseY = e.y;
	params = getParams();
	if(e.button == 2 || e.button == 3){
		window.setTimeout(callRButtonDown(), 100);
//		 WRFcallRButtonDown()
		return false;
	}else{
		window.setTimeout(callLButtonDown, 100);
		
	}
}

function _onMouseUp(){
	
}

function callLButtonDown(){
	
	_onClick(mouseX, mouseY, params);
}

function callRButtonDown(){
	_onRightClick(mouseX, mouseY, params);
}

function getParams(){
	params = bqChart.getRawChartObj().getObjectByPoint(mouseX, mouseY);
	
	if(params != null){
		var from = params.indexOf("\"");
		var to = params.lastIndexOf("\"");
		params = params.substring(from + 1, to);
		params = decodeURI(params);
	}
	
	return params;
}

function _mouseOut(){
	_onMouseOut(changed);
}

var linkClicked = false;  

function _processLink(params){
	linkClicked = true;
	//alert ("_processLink : " + params);
}

function _onClick(x, y, params){}
function _onRightClick(x, y, params){}
function _onMouseOut(){}

function _chartChanged(){return changed}
function _clearChanged(){changed = false}

function chartRenderCompleted(changed){}