(function ($) {
	
	/**
	 * 重设Metro所有项目的外部和内部属性
	 */
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
			var itemSize = _setSize(metroItems[i], itemWidth, itemHeight, metrosOpts[i]);
			_setSizeAsType(metroItems[i], itemSize, metrosOpts[i], options);
		}
		
		// 调整整体水平位置
		var margin = Math.floor(( size.css.width - itemAllWidth) /2);
		margin = Math.max(0, margin);
		$(metro).width(itemAllWidth).css("margin-left", margin + "px");
		
	}
	
	/**
	 * 设置Metro项目的外部尺寸
	 */
	function _setSize(item, itemWidth, itemHeight, itemOpt){
		var width = itemWidth,
			height = itemHeight,
			rowspan = itemOpt.rowspan, 
			colspan = itemOpt.colspan;
		
		if(rowspan && typeof rowspan == 'number'){
			height = itemHeight * rowspan + 10 * ( rowspan - 1 );
		}
		if(colspan && typeof colspan == 'number'){
			width = itemWidth * colspan + 10 * ( colspan - 1 );
		}
		item.width(width).height(height);
		
		return {width:width,height:height};
	}
	
	/**
	 * 根据类型设置Metro项目的内部尺寸
	 */
	function _setSizeAsType(item, itemSize, itemOpt, options){
		var itemType = itemOpt.itemType;
		if(itemType == "metro"){
			_setSizeForMetro(item, itemSize, itemOpt, options);
		}else if(itemType == "cycle"){
			_setSizeForCycle(item, itemSize, itemOpt, options);
		}else if(itemType == "note"){
            _setSizeForNote(item, itemSize, itemOpt, options);
        }else{
			_setSizeForCustom(item, itemSize, itemOpt, options);
		}
	}
	
	function _setSizeForMetro(item, itemSize, itemOpt, options){
		// 图标位置
		var iconHeight = itemOpt.iconHeight;
		var imgWrapper = item.find(".ui-metro-img");
		if(imgWrapper && imgWrapper.length > 0){
			var paddingTop = Math.floor((itemSize.height - iconHeight) / 2);
			imgWrapper.css("padding-top", paddingTop+"px");
		}
	}
	
	function _setSizeForCycle(item, itemSize, itemOpt, options){
		// TODO
	}

	function _setSizeForNote(item, itemSize, itemOpt, options){
        // TODO
        _setSizeForMetro(item, itemSize, itemOpt, options);
    }
	
	function _setSizeForCustom(item, itemSize, itemOpt, options){
		// TODO
	}
	
	function _addMetroTypeMetro(options, itemOpt) {
		var metro = $("<div class='ui-metro ui-metro-type-"+itemOpt.itemType+"'></div>");
		if (itemOpt.id && itemOpt.id != "") {
			metro.attr("id", itemOpt.id);
        }
		
		if (itemOpt.backgroundCls && itemOpt.backgroundCls != "") {
			metro.addClass("ui-metro-"+itemOpt.backgroundCls);
		}
		
		if (itemOpt.backgroundImg && itemOpt.backgroundImg != "") {
			var imgType = itemOpt.backgroundImgType;
			if(imgType == "stretch"){
				var metroBgImg = $("<div class='ui-metro-bg-img'><img src='" + itemOpt.backgroundImg + "' style='width:100%;height:100%;'/></div>");
				metroBgImg.appendTo(metro);
			}else{
				metro.css("background","url(" + itemOpt.backgroundImg + ")");
			}
		}
		
		if(itemOpt.iconImg && itemOpt.iconImg != "") {
			var metroImg = $("<div class='ui-metro-img'><img src='" + itemOpt.iconImg + "' /></div>");
			metroImg.appendTo(metro);
		}

		if(itemOpt.itemData) {
            var metroContent = $("<div class='ui-metro-content'>" + itemOpt.itemData + "</div>");
            metroContent.appendTo(metro);
        }
		
		if(itemOpt.text && itemOpt.text != ""){
			var metroTextWrip = $("<div class='ui-metro-destaque-rodape'></div>");
			if(itemOpt.textBackgroundCls && itemOpt.textBackgroundCls != ""){
				metroTextWrip.addClass("ui-metro-"+itemOpt.textBackgroundCls);
			}
			var metroText = $("<span>"+itemOpt.text+"</span>");
			metroText.appendTo(metroTextWrip);
			metroTextWrip.appendTo(metro);
		}
		
		var itemSize = _setSize(metro, options.itemWidth, options.itemHeight, itemOpt);
		_setSizeAsType(metro, itemSize, itemOpt, options);
		
		return metro;
	}

	function _addMetroTypeCycle(options, itemOpt){
		// TODO
	}

	function _addMetroTypeNote(options, itemOpt){
	    // TODO
	    var metro = _addMetroTypeMetro(options, itemOpt);

        return metro;
    }
	
	function _addMetroTypeCustom(options, itemOpt){
		// TODO
	}

	function _addMetro(target, itemOpt) {
		var options = $.data(target, "metro").options;
		
		var itemType = itemOpt.itemType;
		
		var metro = null;
		if(itemType == "metro"){
			metro = _addMetroTypeMetro(options, itemOpt);
		}else if(itemType == "cycle"){
			metro = _addMetroTypeCycle(options, itemOpt);
		}else if(itemType == "note"){
            metro = _addMetroTypeNote(options, itemOpt);
        }else{
			metro = _addMetroTypeCustom(options, itemOpt);
		}
		
		if(metro){
			metro.appendTo(target);
		}
		
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
					opts.metros[i] = $.extend( {}, $.fn.metro.itemDefaults, opts.metros[i]);
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
		rowNum : 3,// 行数
		columnNum : "auto",// 列数，默认根据每个元素的宽度自动计算
		
		itemWidth : 158,// 一般不必设置，只用于初始化设置，并非真正项目尺寸
		itemHeight : 158,
		
		metros : null
		
	};
	
	$.fn.metro.itemDefaults = {
		// 元素类型:
		// Metro风格-metro(默认), 
		// 记事本-note,
		// Cycle图片风格-cycle, 
		// 报表-report
		// 自定义页面风格(如果多于一个页面自动循环)-custom
		itemType : "metro",

		itemData : null,
		
		rowspan : 1,
		colspan : 1,
		
		// --------metro类型属性--------
		// backgroundCls:roxo,verde,azul,vermelho,laranja,info
		backgroundCls : null,
		backgroundImg : null, // backgroundImg属性优先于backgroundCls属性生效
		backgroundImgType : "stretch", // 拉伸-stretch(默认), 平铺-tile
		
		iconImg : null,
		iconWidth : 64,
		iconHeight : 64,
		
		text : null,
		textBackgroundCls : null,
		
		// --------cycle类型属性---------
		srcs : null // 图片src数组
		
		
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