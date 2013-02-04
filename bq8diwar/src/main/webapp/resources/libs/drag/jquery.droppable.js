(function($) {
	function _initEvents(target) {
		$(target).addClass("droppable");
		$(target).bind("_dragenter", function(e, source, data) {
			$.data(target, "droppable").options.onDragEnter.apply(target, [ e, source, data ]);
		});
		$(target).bind("_dragleave", function(e, source, data) {
			$.data(target, "droppable").options.onDragLeave.apply(target, [ e, source, data ]);
		});
		$(target).bind("_dragover", function(e, source, data) {
			$.data(target, "droppable").options.onDragOver.apply(target, [ e, source, data ]);
		});
		$(target).bind("_drop", function(e, source, data) {
			$.data(target, "droppable").options.onDrop.apply(target, [ e, source, data ]);
		});
	}
	;
	$.fn.droppable = function(option, param) {
		if (typeof option == "string") {
			return $.fn.droppable.methods[option](this, param);
		}
		option = option || {};
		return this.each(function() {
			var t = $.data(this, "droppable");
			if (t) {
				$.extend(t.options, option);
			} else {
				_initEvents(this);
				$.data(this, "droppable", {
					options : $.extend( {}, $.fn.droppable.defaults,
							$.fn.droppable.parseOptions(this), option)
				});
			}
		});
	};
	$.fn.droppable.methods = {
		options : function(jq) {
			return $.data(jq[0], "droppable").options;
		},
		enable : function(jq) {
			return jq.each(function() {
				$(this).droppable( {
					disabled : false
				});
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				$(this).droppable( {
					disabled : true
				});
			});
		}
	};
	$.fn.droppable.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, YonYou.parseOptions(target, [ "accept" ]), {
			disabled : (t.attr("disabled") ? true : undefined)
		});
	};
	$.fn.droppable.defaults = {
		accept : null,
		disabled : false,
		onDragEnter : function(e, source) {
		},
		onDragOver : function(e, source) {
		},
		onDragLeave : function(e, source) {
		},
		onDrop : function(e, source) {
		}
	};
})(jQuery);
