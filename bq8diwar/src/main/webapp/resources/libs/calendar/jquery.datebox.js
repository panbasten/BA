(function($) {
	function init(target) {
		var t = $.data(target, "datebox");
		var opts = t.options;
		$(target).addClass("datebox-f");
		$(target).combo($.extend( {}, opts, {
			onShowPanel : function() {
				t.calendar.calendar("resize");
				opts.onShowPanel.call(target);
			}
		}));
		$(target).combo("textbox").parent().addClass("datebox");
		if (!t.calendar) {
			initButton();
		}
		
		function initButton() {
			var panel = $(target).combo("panel");
			t.calendar = $("<div></div>").appendTo(panel).wrap(
					"<div class=\"datebox-calendar-inner\"></div>");
			t.calendar.calendar( {
				fit : true,
				border : false,
				onSelect : function(text) {
					var val = opts.formatter(text);
					_setValue(target, val);
					$(target).combo("hidePanel");
					opts.onSelect.call(target, text);
				}
			});
			_setValue(target, opts.value);
			var button = $("<div class=\"datebox-button\"></div>").appendTo(panel);
			$("<a href=\"javascript:void(0)\" class=\"datebox-current\"></a>")
					.html(opts.currentText).appendTo(button);
			$("<a href=\"javascript:void(0)\" class=\"datebox-close\"></a>")
					.html(opts.closeText).appendTo(button);
			button.find(".datebox-current,.datebox-close").hover(function() {
				$(this).addClass("datebox-button-hover");
			}, function() {
				$(this).removeClass("datebox-button-hover");
			});
			button.find(".datebox-current").click(function() {
				t.calendar.calendar( {
					year : new Date().getFullYear(),
					month : new Date().getMonth() + 1,
					current : new Date()
				});
			});
			button.find(".datebox-close").click(function() {
				$(target).combo("hidePanel");
			});
		}
		
	}
	
	function _query(_b, q) {
		_setValue(_b, q);
	}
	
	function _enter(target) {
		var opts = $.data(target, "datebox").options;
		var c = $.data(target, "datebox").calendar;
		var val = opts.formatter(c.calendar("options").current);
		_setValue(target, val);
		$(target).combo("hidePanel");
	}
	
	function _setValue(target, val) {
		var t = $.data(target, "datebox");
		var opts = t.options;
		$(target).combo("setValue", val).combo("setText", val);
		t.calendar.calendar("moveTo", opts.parser(val));
	}
	
	$.fn.datebox = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.datebox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "datebox");
			if (t) {
				$.extend(t.options, options);
			} else {
				$.data(this, "datebox", {
					options : $.extend( {}, $.fn.datebox.defaults, $.fn.datebox
							.parseOptions(this), options)
				});
			}
			init(this);
		});
	};
	$.fn.datebox.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], "datebox").options;
			opts.originalValue = jq.combo("options").originalValue;
			return opts;
		},
		calendar : function(jq) {
			return $.data(jq[0], "datebox").calendar;
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				_setValue(this, val);
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).datebox("options");
				$(this).datebox("setValue", opts.originalValue);
			});
		}
	};
	
	$.fn.datebox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.combo.parseOptions(target), {});
	};
	
	$.fn.datebox.defaults = $.extend( {}, $.fn.combo.defaults, {
		panelWidth : 180,
		panelHeight : "auto",
		keyHandler : {
			up : function() {
			},
			down : function() {
			},
			enter : function() {
				_enter(this);
			},
			query : function(q) {
				_query(this, q);
			}
		},
		currentText : "Today",
		closeText : "Close",
		okText : "Ok",
		formatter : function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return m + "/" + d + "/" + y;
		},
		parser : function(s) {
			var t = Date.parse(s);
			if (!isNaN(t)) {
				return new Date(t);
			} else {
				return new Date();
			}
		},
		onSelect : function(date) {
		}
	});
})(jQuery);
