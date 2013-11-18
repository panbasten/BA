(function($){
	
	$.fn.spreadsheet = function(options, param) {
		if(typeof options == "string"){
			return $.fn.pushbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "spreadsheet");
			if(t){
				$.extend(t.options, options);
			}else{
				$.data(this, "spreadsheet", {
					options : $.extend(
							{},
							$.fn.spreadsheet.defaults,
							$.fn.spreadsheet.parseOptions(this),
							options
						)
				});
			}
			var spreadsheet = _init(this);
			$.data(this, "spreadsheet", {spreadsheet:spreadsheet} );
		});
	};
	
	$.fn.spreadsheet.methods = {
		options : function(jq) {
			return $.data(jq[0], "spreadsheet").options;
		}
	};
	
	$.fn.spreadsheet.parseOptions = function(target) {
		var t = $(target);
		return $.extend({},
			Flywet.parseOptions(target, ["id"])
		);
	};
	
	$.fn.spreadsheet.defaults = {
		id : null
	};
})(jQuery);
