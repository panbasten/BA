Flywet.widget.FusionCharts=function(cfg){
	this.cfg = $.extend(
		{},
		Flywet.FusionCharts.defaults,
		cfg
	);
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	
	this.init();
};

Flywet.extend(Flywet.widget.FusionCharts, Flywet.widget.BaseWidget);

Flywet.widget.FusionCharts.prototype.init = function() {
	if(this.cfg.parent || this.cfg.parentId){
		this.parent = this.cfg.parent || $(Flywet.escapeClientId(this.cfg.parentId));
		this.jq = $(this.parent).find(this.jqId);
		if(this.jq.length == 0){
			this.jq = $("<span></span>");
		}
		this.parent.append(this.jq);
	}else{
		this.jq = $(this.jqId);
	}
	
	this.jq.width(this.cfg.width).height(this.cfg.height);
	
	FusionCharts.setCurrentRenderer("javascript");
	console.log(this.cfg);
	this.chart = new FusionCharts(this.cfg.type, this.cfg.id+"_c", this.cfg.width, this.cfg.height);
	this.chart.setJSONData(this.cfg.data);
	this.chart.render(this.cfg.id);
};


Flywet.FusionCharts = {
	defaults : {
		id : null
		,width : 400
		,height : 300
		,type : "MSColumn2D"
	}
};

