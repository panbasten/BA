(function($){
	
	// 初始化适配器
	$FC.Adapter.init.call($FC.Adapter, $FC.pathAnim);
	$FC.adapterRun = $FC.Adapter.adapterRun;
	$FC.getScript = $FC.Adapter.getScript;
	$FC.inArray = $FC.Adapter.inArray;
	$FC.each = $FC.Adapter.each;
	$FC.grep = $FC.Adapter.grep;
	$FC.offset = $FC.Adapter.offset;
	$FC.map = $FC.Adapter.map;
	$FC.addEvent = $FC.Adapter.addEvent;
	$FC.removeEvent = $FC.Adapter.removeEvent;
	$FC.fireEvent = $FC.Adapter.fireEvent;
	$FC.washMouseEvent = $FC.Adapter.washMouseEvent;
	$FC.animate = $FC.Adapter.animate;
	$FC.stop = $FC.Adapter.stop;
	
	
	$.fn.flycharts = function(options, param){
		if (typeof options == "string") {
			return $.fn.flycharts.methods[options](this, param);
		}
		options = options || {};
		
		var ret,
			chart;
		
		// 创建一个图表
		if (options !== $FC.UNDEFINED) {
			options.chart = options.chart || {};
			options.chart.renderTo = this[0];
			chart = new $FC.Chart(options, param);
			ret = this;
		}

		// 当发生无参数调用时，返回一个已定义的图表
		if (options === $FC.UNDEFINED) {
			ret = $FC.charts[$FC.attr(this[0], 'data-highcharts-chart')];
		}	

		return ret;
	};
	
})(jQuery);