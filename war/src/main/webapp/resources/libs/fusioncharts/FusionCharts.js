Plywet.widget.FusionCharts = function(cfg){
	this.cfg = $.extend({autoOpen:true},cfg);
	this.id = this.cfg.id;
	this.jq = $(Plywet.escapeClientId(this.id));	
	
	if (!document.getElementById) { return; }
	
	//Flag to see whether data has been set initially
	this.initialDataSet = false;
	
	//Create container objects
	this.params = new Object();
	this.variables = new Object();
	this.attributes = new Array();
	
	//Set attributes for the SWF
	if(this.cfg.swf) { this.setAttribute('swf', "charts/"+this.cfg.swf+"/"+this.cfg.swf+".swf"); }
	if(this.id) { this.setAttribute('id', this.id + "_swf"); }
	if(this.cfg.width) { this.setAttribute('width', this.cfg.width); }
	if(this.cfg.height) { this.setAttribute('height', this.cfg.height); }
	
	//Set background color
	if(this.cfg.bgcolor) { this.addParam('bgcolor', this.cfg.bgcolor); }
	
	//Set Quality	
	this.addParam('quality', 'high');
	
	//Add scripting access parameter
	this.addParam('allowScriptAccess', 'always');
	this.addParam('swliveconnect', 'true');	 
	
	//Pass width and height to be appended as chartWidth and chartHeight
	this.addVariable('chartWidth', this.cfg.width);
	this.addVariable('chartHeight', this.cfg.height);
	
	if(this.cfg.data){
		this.setDataXML(this.cfg.data);
	}else if(this.cfg.url){
		this.setDataURL(this.cfg.url);
	}
	
	if(this.cfg.autoOpen) {
		this.render();
	}
};

Plywet.extend(Plywet.widget.FusionCharts, Plywet.widget.BaseWidget);

Plywet.widget.FusionCharts.prototype = {
	setAttribute: function(name, value){
		this.attributes[name] = value;
	},
	getAttribute: function(name){
		return this.attributes[name];
	},
	addParam: function(name, value){
		this.params[name] = value;
	},
	getParams: function(){
		return this.params;
	},
	addVariable: function(name, value){
		this.variables[name] = value;
	},
	getVariable: function(name){
		return this.variables[name];
	},
	getVariables: function(){
		return this.variables;
	},
	getVariablePairs: function(){
		var variablePairs = new Array();
		var key;
		var variables = this.getVariables();
		for(key in variables){
			variablePairs.push(key +"="+ variables[key]);
		}
		return variablePairs;
	},
	getSWFHTML: function() {
		var swfNode = "";
		if (navigator.plugins && navigator.mimeTypes && navigator.mimeTypes.length) { 
			// netscape plugin architecture			
			swfNode = '<embed type="application/x-shockwave-flash" src="'+ this.getAttribute('swf') +'" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'"  ';
			swfNode += ' id="'+ this.getAttribute('id') +'" name="'+ this.getAttribute('id') +'" ';
			var params = this.getParams();
			 for(var key in params){ swfNode += [key] +'="'+ params[key] +'" '; }
			var pairs = this.getVariablePairs().join("&");
			 if (pairs.length > 0){ swfNode += 'flashvars="'+ pairs +'"'; }
			swfNode += '/>';
		} else { // PC IE			
			swfNode = '<object id="'+ this.getAttribute('id') +'" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'">';
			swfNode += '<param name="movie" value="'+ this.getAttribute('swf') +'" />';
			var params = this.getParams();
			for(var key in params) {
			 swfNode += '<param name="'+ key +'" value="'+ params[key] +'" />';
			}
			var pairs = this.getVariablePairs().join("&");			
			if(pairs.length > 0) {swfNode += '<param name="flashvars" value="'+ pairs +'" />';}
			swfNode += "</object>";
		}
		return swfNode;
	},
	setDataURL: function(strDataURL){
		//This method sets the data URL for the chart.
		//If being set initially
		if (this.initialDataSet==false){
			this.addVariable('dataURL',strDataURL);
			//Update flag
			this.initialDataSet = true;
		}else{
			//Else do nothing as FusionCharts Free doesn't support dynamic
			//update of dataURL. It only supports update of dataXML.
		}
	},
	setDataXML: function(strDataXML){
		//If being set initially
		if (this.initialDataSet==false){
			//This method sets the data XML for the chart INITIALLY.
			this.addVariable('dataXML',strDataXML);
			//Update flag
			this.initialDataSet = true;
		}else{
			//In FusionCharts Free, we use updateChartXML method to update
			//the data of chart.
		}
	},
	render: function(elementId){
		if(elementId){
			this.jq = (typeof elementId == 'string') ? $(Plywet.escapeClientId(elementId)) : $(elementId);
		}
		this.jq.html(this.getSWFHTML());
		return true;		
	}
}

// ------------ Fix for Out of Memory Bug in IE in FP9 ---------------//
/* Fix for video streaming bug */
Plywet.widget.FusionChartsUtil.cleanupSWFs = function() {
	if (window.opera || !document.all) return;
	var objects = document.getElementsByTagName("OBJECT");
	for (var i=0; i < objects.length; i++) {
		objects[i].style.display = 'none';
		for (var x in objects[i]) {
			if (typeof objects[i][x] == 'function') {
				objects[i][x] = function(){};
			}
		}
	}
};

// Fixes bug in fp9
Plywet.widget.FusionChartsUtil.prepUnload = function() {
	__flash_unloadHandler = function(){};
	__flash_savedUnloadHandler = function(){};
	if (typeof window.onunload == 'function') {
		var oldUnload = window.onunload;
		window.onunload = function() {
			Plywet.widget.FusionChartsUtil.cleanupSWFs();
			oldUnload();
		}
	} else {
		window.onunload = Plywet.widget.FusionChartsUtil.cleanupSWFs;
	}
};

if (typeof window.onbeforeunload == 'function') {
	var oldBeforeUnload = window.onbeforeunload;
	window.onbeforeunload = function() {
		Plywet.widget.FusionChartsUtil.prepUnload();
		oldBeforeUnload();
	}
} else {
	window.onbeforeunload = Plywet.widget.FusionChartsUtil.prepUnload;
}

/* Add Array.push if needed (ie5) */
if (Array.prototype.push == null) { Array.prototype.push = function(item) { this[this.length] = item; return this.length; }}

/* Function to return Flash Object from ID */
Plywet.widget.FusionChartsUtil.getChartObject = function(id)
{
  if (window.document[id]) {
      return window.document[id];
  }
  if (navigator.appName.indexOf("Microsoft Internet")==-1) {
    if (document.embeds && document.embeds[id])
      return document.embeds[id]; 
  } else {
    return document.getElementById(id);
  }
}
/*
 Function to update chart's data at client side
*/
Plywet.widget.FusionChartsUtil.updateChartXML = function(chartId, strXML){
	//Get reference to chart object				
	var chartObj = Plywet.widget.FusionChartsUtil.getChartObject(chartId);		
	//Set dataURL to null
	chartObj.SetVariable("_root.dataURL","");
	//Set the flag
	chartObj.SetVariable("_root.isNewData","1");
	//Set the actual data
	chartObj.SetVariable("_root.newData",strXML);
	//Go to the required frame
	chartObj.TGotoLabel("/", "JavaScriptHandler"); 
}
