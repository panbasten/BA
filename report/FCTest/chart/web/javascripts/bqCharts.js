//浠ｈ〃FusionCharts鐨勫父閲�
var FUSIONCHARTS = "fusioncharts";
//瀛楃涓插弬鏁扮被鍨�
var PARATYPE_STRING = "string";
//瀛楃鍨嬪弬鏁扮被鍨�
var PARATYPE_NUMBER = "number";
//瀵硅薄鎬у弬鏁扮被鍨�
var PARATYPE_OBJECT = "object";
//缁熻鍥綢D鐨勭储寮曪紝鐢ㄤ簬
var _chartIdIndex = 0;
//鏈嶅姟鍣ㄦ牴鍦板潃
var _serverRoot = ""; 

//鐢熸垚缁熻鍥綢D
function _genChartId(){
	return "chartid_" + (++ _chartIdIndex);
}

//璁剧疆鏈嶅姟鍣ㄦ牴鍦板潃
function setServerRoot(serverRoot){
	this._serverRoot = serverRoot + "/";
}

//BQ缁熻鍥惧璞�
function BQChart(type, chartDefineCode, chart, domId, chartId, data, rawChartObj){
	//鏄惁鏈夋晥
	this.isValid = true;
	//缁熻鍥炬覆鏌撶殑div绛夌殑id
	this.domId = domId;
	//缁熻鍥剧被鍨嬶紝榛樿鏄疐usionCharts
	this.type = type;
	//缁熻鍥綢D
	this.chartId = chartId;
	//缁熻鍥惧畾涔夌紪鐮�
	this.chartDefineCode = chartDefineCode;
	//鏁版嵁
	this.data = data,
	//鍘熺敓瀵硅薄
	this.rawChartObj = rawChartObj;
	
	//鏄惁宸茬粡娓叉煋瀹屾瘯
	this.hasRendered = function(){
		try{
			return rawChartObj.hasRendered();
		}catch(err){
			return false;
		}
	};
	
	//浜嬩欢鐩戝惉
	this.eventListenerMap = new Object();
	
	this.checkValid = function(){
		if(!this.isValid){
			throw "Object is invalid!";
		}
	};
	
	this.getDomId = function(){
		this.checkValid();
		
		return this.domId;
	};
	
	this.getType = function(){
		this.checkValid();
		
		return this.type;
	};
	
	this.getChartId = function(){
		this.checkValid();
		
		return this.chartId;
	};
	
	this.getChartDefineCode = function(){
		this.checkValid();
		
		return this.chartDefineCode;
	};
	
	this.getRawChartObj = function(){
		this.checkValid();
		
		return this.rawChartObj;
	};
	
	//娉ㄥ唽浜嬩欢鐩戝惉鍣�
	this.registerEventListener = function(eventCode, listenerFunction){
		this.checkValid();
		
		var listeners = this.eventListenerMap[eventCode];
		if(listeners == null){
			listeners = new Array();
		}else{
			for(var i = 0; i < listeners.length; i ++){
				if(listeners[i] == listenerFunction){
					return;
				}
			}
		}
		
		listeners[listeners.length] = listenerFunction;
	};
	
	//鏇存柊鍥�
	this.update = function(type, chartDefineCode, data, width, height){
		this.checkValid();
		
		if(this.chartDefineCode == chartDefineCode && this.type == type){
			this.rawChartObj.setDataXML(data);
			this.updateSize(width, height);
			
			return this;
		} else {
			document.getElementById(domId).style.width = width;
			document.getElementById(domId).style.height = height;
			document.getElementById(this.domId).innerHTML = "";

			var chart2 = createBQChart(type, chartDefineCode, this.domId, data, width, height, "");
		
			this.type = type;
			this.chartDefineCode = chartDefineCode;
			this.rawChartObj = chart2.getRawChartObj();
			return chart2;
		}

	};
	
	//璋冪敤API
	this.callAPI = function(apiName, parameters){
		this.checkValid();
		
		this.callAPIWithResult(apiName, parameters);
	};
	
	//璋冪敤API骞惰繑鍥炵粨鏋�
	this.callAPIWithResult = function(apiCode, parameters){
		this.checkValid();
		
		var chartDefine = chartDefineMap[this.chartDefineCode];
		
		parameterTypes = chartDefine.getAPIParameterTypeArray(apiCode);
		var script = apiCode + "(";
		for(var i = 0; i < parameters.length; i ++){
			parameterType = parameterTypes[i];
			var parameter = parameters[i];
			if(parameterType == PARATYPE_STRING)
				script += "\"" + parameters[i] + "\",";
			else
				script += parameters[i] + ",";
		}
		
		if(parameters.length > 0){
			script = script.substring(0, script.length - 1);
		}

		script += ")";
		
		return eval(script);
	};	
	
	//娓呴櫎鍥惧舰
	this.clear = function(){
		this.checkValid();
		
		this.isValid = false;		
		this.eventListenerMap = null;
		
		document.getElementById(this.domId).innerHTML = "";
	};
}

//缁熻鍥惧畾涔夋槧灏�
//key: 缁熻鍥惧畾涔夌紪鐮侊紝value: 
var chartDefineMap = new Object();

function addChartDefine(chartDefine){
	chartDefineMap[chartDefine.getChartDefineCode()] = chartDefine;
	
};

//鍒涘缓缁熻鍥惧畾涔�
function ChartDefine(chartDefineCode){
	this.chartDefineCode = chartDefineCode;
	
	//key: api Code, value: 鍙傛暟鏄犲皠銆傚弬鏁版槧灏勭粨鏋勶細key: 鍙傛暟鍚嶇О锛寁alue: 鍙傛暟绫诲瀷
	this.apiParamertTypesMap = new Object();

	//浜嬩欢瀹氫箟.浠ュ悗瀹炵幇
	this.eventParameterTypesMap = new Object();
	
	this.getChartDefineCode = function(){
		return this.chartDefineCode;
	};
	
	//璁剧疆API鐨勫弬鏁扮被鍨�
	this.addAPIParameterTypesMapping = function(apiName, apiParameterTypeArray){
		//var apiParameterTypeArray = eval("[" + apiParameterTypeArrayStr + "]");
		this.apiParamertTypesMap[apiName] = apiParameterTypeArray;
	};
	
	this.getAPIParameterTypeArray = function(apiName){
		return this.apiParamertTypesMap[apiName];
	};
}

function registerBQChartEvent (chartId){
	FusionCharts.addEventListener (FusionChartsEvents.LinkedItemOpened, callLinkedItemOpened);
	FusionCharts.addEventListener (FusionChartsEvents.DrawComplete, callDrawComplete);
}

function callLinkedItemOpened (eventObject, argumentObject){
	//alert ("callLinkedItemOpened");
}

function callDrawComplete (eventObject, argumentsObject){
	chartHasRendered(); //by yxl not define
	//alert ("callDrawComplete");
}

//鍒涘缓缁熻鍥�
function createBQChart(type, chartDefineCode, domId, data, width, height, swfFile){
	var bqChart = null;
	if(type.toLowerCase().indexOf(FUSIONCHARTS) >= 0){
		var chartId = _genChartId();
		registerBQChartEvent (chartId);
		if ( swfFile==null || swfFile==""){
			swfFile = _serverRoot + "chartDefines/" + chartDefineCode + "/fusionCharts/" + chartDefineCode + ".swf";
		}
		
		var chart = new FusionCharts(swfFile, chartId, width, height, "0", "0");
		chart.setTransparent(true);
		chart.setXMLData(data);
		chart.render(domId);
		
		//var chart = new FusionCharts.render(_serverRoot + "chartDefines/" + chartDefineCode + "/fusionCharts/" + chartDefineCode + ".swf", 
			//	chartId, width, height, domId, data, "xml");
		
		bqChart = new BQChart(type, chartDefineCode, chart, domId, chartId, data, chart);
	} else if (type.toLowerCase().indexOf("hightcharts") >= 0){
		
	} else {
		//do nothing
	}
	
	return bqChart;
}