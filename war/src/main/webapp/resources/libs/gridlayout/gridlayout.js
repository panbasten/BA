(function($) {
	
	function _resizeLineHeight(itemsLine,maxHeight){
		if(maxHeight > 0) {
			for(var j=0; j<itemsLine.length; j++){
				itemsLine[j].height(maxHeight);
			}
		}
	}
	
	function _resizeItemWidth(item,opts,maxWidth,idx,cols){
		var itemMargin = opts.itemMargin;
		if(idx==0){
			// 单个元素
			if((idx+cols)>=opts.column){
				maxWidth = maxWidth - 2*itemMargin;
				item.css({
					"margin-top" : opts.itemMargin + "px",
					"margin-bottom" : "0",
					"margin-left" : opts.itemMargin + "px",
					"margin-right" : opts.itemMargin + "px"
				});
			}
			// 第一个元素
			else{
				maxWidth = maxWidth - 1.5*itemMargin;
				item.css({
					"margin-top" : opts.itemMargin + "px",
					"margin-bottom" : "0",
					"margin-left" : opts.itemMargin + "px",
					"margin-right" : "0"
				});
			}
		}else{
			// 最后一个元素
			if((idx+cols)>=opts.column){
				maxWidth = maxWidth - 1.5*itemMargin;
				item.css({
					"margin-top" : opts.itemMargin + "px",
					"margin-bottom" : "0",
					"margin-left" : opts.itemMargin + "px",
					"margin-right" : opts.itemMargin + "px"
				});
			}
			// 中间元素
			else{
				maxWidth = maxWidth - itemMargin;
				item.css({
					"margin-top" : opts.itemMargin + "px",
					"margin-bottom" : "0",
					"margin-left" : opts.itemMargin + "px",
					"margin-right" : "0"
				});
			}
		}
		
		item.width(maxWidth);
	}
	
	function _initGridLayout(target) {
		var opts = $.data(target, "gridlayout").options;
		var gridlayout = $.data(target, "gridlayout").gridlayout;
		
		var column = opts.column;
		var items = gridlayout.children(".ui-grid-layout-item");
		
		for(var i=0;i<items.size();){
			var itemsLine = [];
			var cols=1,maxHeight=opts.minItemHeight,dim;
			for(var idx=0;i<items.size()&&idx<column;i++){
				var item = $(items.get(i));
				cols = parseInt(item.attr("cols") || "1");
				
				itemsLine.push(item);
				
				dim = Flywet.getElementDimensions(item);
				
				// 设置项目实际像素值
				_resizeItemWidth(item,opts,dim.offsetWidth,idx,cols)
				
				// 计算最大高度
				if(!opts.heightAuto){
					maxHeight = Math.max(maxHeight, dim.offsetHeight);
				}
				
				idx = idx + cols;
			}
			if(!opts.heightAuto){
				_resizeLineHeight(itemsLine,maxHeight);
			}
		}
	}
	
	$.fn.gridlayout = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.gridlayout.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return $.fn.gridlayout.methods[options](this, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "gridlayout");
			if (t) {
				$.extend(t.options, options);
			} else {
				t = $.data(this, "gridlayout", {
					options : $.extend(
						{}, 
						$.fn.gridlayout.defaults,
						$.fn.gridlayout.parseOptions(this),
						options
					),
					gridlayout : $(this)
				});
			}
			_initGridLayout(this);
		});
	};
	
	/**
	 * 外部调用方法
	 */
	$.fn.gridlayout.methods = {
		options : function(jq) {
			return $.data(jq[0], "gridlayout").options;
		}
	};
	
	$.fn.gridlayout.parseOptions = function(target) {
		var t = $(target);
		return $.extend(
			{},
			Flywet.parseOptions(target)
		);
	};
	
	$.fn.gridlayout.defaults = {
		width : "auto",
		height : "auto",
		column : 1,
		itemWidth : "100%",
		minItemHeight : 22,
		itemMargin : 0
	};
	
})(jQuery);

/**
 * 栅格布局
 */
Flywet.widget.GridLayout = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.init();
};
Flywet.extend(Flywet.widget.GridLayout, Flywet.widget.BaseWidget);
Flywet.widget.GridLayout.prototype = {
	init : function() {
		this.jq.gridlayout(this.cfg);
	}
};