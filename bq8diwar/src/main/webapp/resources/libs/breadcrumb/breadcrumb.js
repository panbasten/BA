YonYou.widget.BreadCrumb = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = YonYou.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.init();
};

YonYou.extend(YonYou.widget.BreadCrumb, YonYou.widget.BaseWidget);

YonYou.widget.BreadCrumb.prototype = {
	init : function(){
		this.jqUl = $(this.jq.find("ul").first());
		this.jqUl.html("");
		var el = this.cfg.items[0];
		var $li = $("<li role='menuitem'><a href='#' class='ui-menuitem-link ui-corner-all'><span class='ui-menuitem-text'>"+el.attrs.displayName+"</span></a></li>");
		this.jqUl.append($li);
		this.addEvent($li,el);
		for(var i=1;i<this.cfg.items.length;i++){
			this.jqUl.append("<li class='ui-breadcrumb-chevron ui-icon ui-icon-triangle-1-e'></li>");
			el = this.cfg.items[i];
			$li = $("<li role='menuitem'><a href='#' class='ui-menuitem-link ui-corner-all'><span class='ui-menuitem-text'>"+el.attrs.displayName+"</span></a></li>");
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



YonYou.widget.BreadCrumb.prototype.flush = function(cfg){
	this.cfg = cfg;
	this.init();
};