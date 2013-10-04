(function ($) {
	
	function _resize(target) {
		var metro = $($.data(target, "metro").metro),
			options = $.data(target, "metro").options,
			metrosOpts = options.metros,
			metroItems = $.data(target, "metro").metroItems;
		
		metro.css({"margin-left":0,"margin-right":0});
		
		var size = Flywet.getElementDimensions(metro),
			// 按照高度放置3列
			itemHeight = Math.floor(size.css.height / options.rowNum) - 10,
			itemWidth = itemHeight;
		
		var columnNum;
		if(options.columnNum && typeof options.columnNum == "number"){
			columnNum = options.columnNum;
		}else{
			columnNum = Math.floor(size.css.width / (itemWidth + 10));
		}
		
		var itemAllWidth = (itemWidth + 10) * columnNum + 10;
		if(itemAllWidth > size.css.width){
			itemWidth = itemHeight = Math.floor((size.css.width - 10) / columnNum - 10);
		}
		
		for(var i=0;i<metroItems.length;i++){
			var imgHeight = metrosOpts[i].imgHeight || options.imgHeight;
			_setSize(metroItems[i], itemWidth, itemHeight, metrosOpts[i].rowspan, metrosOpts[i].colspan, imgHeight);
		}
		
		// 调整水平位置
		
		var margin = Math.floor(( size.css.width - itemAllWidth) /2);
		margin = Math.max(0, margin);
		$(metro).width(itemAllWidth).css("margin-left", margin + "px");
		
	}
	
	function _setSize(item, itemWidth, itemHeight, rowspan, colspan, imgHeight){
		var width = itemWidth,
		height = itemHeight;
		if(rowspan && typeof rowspan == 'number'){
			height = itemHeight * rowspan + 10 * ( rowspan - 1 );
		}
		if(colspan && typeof colspan == 'number'){
			width = itemWidth * colspan + 10 * ( colspan - 1 );
		}
		item.width(width).height(height);
		
		// 图标位置
		var imgWrapper = item.find(".ui-metro-img");
		if(imgWrapper && imgWrapper.length > 0){
			_setImgPosition(imgWrapper, height, imgHeight);
		}
	}
	
	function _setImgPosition(imgWrapper, itemHeight, imgHeight){
		var paddingTop = Math.floor((itemHeight - imgHeight) / 2);
		imgWrapper.css("padding-top", paddingTop+"px");
	}
	
	function _addMetro(target, item) {
		var options = $.data(target, "metro").options;
		
		var metro = $("<div class='ui-metro-btn ui-metro'></div>");
		if (item.id && item.id != "") {
			metro.attr("id", item.id);
        }
		
		if (item.backgroundCls && item.backgroundCls != "") {
			metro.addClass("ui-metro-"+item.backgroundCls);
		}
		
		if (item.backgroundImg && item.backgroundImg != "") {
			var imgType = item.backgroundImgType || options.backgroundImgType;
			if(imgType == "stretch"){
				var metroBgImg = $("<div class='ui-metro-bg-img'><img src='" + item.backgroundImg + "' style='width:100%;height:100%;'/></div>");
				metroBgImg.appendTo(metro);
			}else{
				metro.css("background","url(" + item.backgroundImg + ")");
			}
		}
		
		if(item.iconImg && item.iconImg != "") {
			var metroImg = $("<div class='ui-metro-img'><img src='" + item.iconImg + "' /></div>");
			metroImg.appendTo(metro);
		}
		
		if(item.text && item.text != ""){
			var metroTextWrip = $("<div class='ui-metro-destaque-rodape'></div>");
			if(item.textBackgroundCls && item.textBackgroundCls != ""){
				metroTextWrip.addClass("ui-metro-"+item.textBackgroundCls);
			}
			var metroText = $("<span>"+item.text+"</span>");
			metroText.appendTo(metroTextWrip);
			metroTextWrip.appendTo(metro);
		}
		
		var imgHeight = item.imgHeight || options.imgHeight;
		_setSize(metro, options.itemWidth, options.itemHeight, item.rowspan, item.colspan, imgHeight);
		
		metro.appendTo(target);
		return metro;
	}
	
	$.fn.metro = function(options, param){
		if (typeof options == "string") {
			return $.fn.metro.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var opts = $.extend( {}, $.fn.metro.defaults, options);
			$.data(this, "componentType", "metro");
			var metro = $.data(this, "metro", {
				options : opts,
				metro : this
			});
			
			var metroItems = [];
			if(opts.metros){
				for(var i=0;i<opts.metros.length;i++){
					var item = _addMetro(this, opts.metros[i]);
					metroItems.push(item);
				}
			}
			
			metro.metroItems = metroItems;
		});
	};
	
	$.fn.metro.methods = {
		resize : function(jq) {
			return jq.each(function() {
				_resize(this);
			});
		},
		addItem : function(jq, item) {
			return jq.each(function() {
				_addItem(this, item);
			});
		}
	};
	
	$.fn.metro.defaults = {
		id : null,
		rowNum : 3,
		columnNum : "auto",
		imgWidth : 64,
		imgHeight : 64,
		itemWidth : 158,
		itemHeight : 158,
		// 拉伸-stretch(默认), 平铺-tile
		backgroundImgType : "stretch",
		metros : null
	};
	
})(jQuery);

Flywet.widget.Metro = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jq = $(Flywet.escapeClientId(this.id));
	
	this.init();
};

Flywet.extend(Flywet.widget.Metro, Flywet.widget.BaseWidget);

Flywet.widget.Metro.prototype.init = function(){
	this.jq.metro(this.cfg);
};

Flywet.widget.Metro.prototype.resize = function(){
	this.jq.metro("resize");
};