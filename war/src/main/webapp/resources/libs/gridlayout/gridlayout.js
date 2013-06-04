/**
 * 栅格布局
 */
Plywet.widget.GridLayout = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.init();
};
Plywet.extend(Plywet.widget.GridLayout, Plywet.widget.BaseWidget);
Plywet.widget.GridLayout.prototype = {
	init : function() {
		var _self = this;
		this.jq.each(function(){
			var column = _self.cfg.column;
			var items = $(this).children(".ui-grid-layout-item");
			for(var i=0;i<items.size();){
				var itemsLine = [];
				var idx = 0,maxHeight = 0;
				for(;i<items.size()&&idx<column;){
					itemsLine.push(items.get(i));
					// 计算最大高度
					maxHeight = Math.max(maxHeight, Plywet.getElementDimensions(items.get(i)).offsetHeight);
					
					i++
					idx++;
				}
				if(maxHeight > 0) {
					for(var j=0; j<itemsLine.length; j++){
						$(itemsLine[j]).height(maxHeight);
					}
				}
			}
			
		});
	}
};