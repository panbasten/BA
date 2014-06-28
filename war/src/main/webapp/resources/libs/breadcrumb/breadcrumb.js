Flywet.widget.BreadCrumb = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.init();
};

Flywet.extend(Flywet.widget.BreadCrumb, Flywet.widget.BaseWidget);

Flywet.widget.BreadCrumb.prototype = {
	init : function(){
		this.jqUl = $(this.jq.find("ul").first());
		this.jqUl.html("");
		var el,$li;
		for(var i=0;i<this.cfg.items.length;i++){
			el = this.cfg.items[i];
			$li = $("<li><a href='#'><span>"+el.attrs.displayName+"</span></a></li>");
			this.jqUl.append($li);
			this.addEvent($li,el);
		}
	},
	
	addEvent : function($li,el){
		var _self = this;
		if(this.cfg.attrs && this.cfg.attrs.events){
			for (var eve in this.cfg.attrs.events) {
				$($li.children("a")).bind(eve,[_self.cfg.attrs.events[eve],el],function(event){
					eval(event.data[0] + "(event,event.data[1]);");
				});
			}
		}
	}
};



Flywet.widget.BreadCrumb.prototype.flush = function(cfg){
	this.cfg = cfg;
	this.init();
};