(function($) {
	/**
	 * 初始化
	 */
	function _init(target) {
		var t = $.data(target, "datetimebox");
		var opts = t.options;
		$(target).datebox($.extend( {}, opts, {
			onShowPanel : function() {
				var val = $(target).datetimebox("getValue");
				_setValue(target, val, true);
				opts.onShowPanel.call(target);
			},
			formatter : $.fn.datebox.defaults.formatter,
			parser : $.fn.datebox.defaults.parser
		}));
		$(target).removeClass("datebox-f").addClass("datetimebox-f");
		$(target).datebox("calendar").calendar( {
			onSelect : function(e) {
				opts.onSelect.call(target, e);
			}
		});
		var panel = $(target).datebox("panel");
		if (!t.spinner) {
			var p = $("<div style=\"padding:2px\"><input style=\"width:80px\"></div>")
					.insertAfter(panel.children("div.datebox-calendar-inner"));
			t.spinner = p.children("input");
			var btn = panel.children("div.datebox-button");
			var ok = $("<a href=\"javascript:void(0)\" class=\"datebox-ok\"></a>")
					.html(opts.okText).appendTo(btn);
			ok.hover(function() {
				$(this).addClass("datebox-button-hover");
			}, function() {
				$(this).removeClass("datebox-button-hover");
			}).click(function() {
				_enter(target);
			});
		}
		t.spinner.timespinner( {
			showSeconds : opts.showSeconds,
			separator : opts.timeSeparator
		}).unbind(".datetimebox").bind("mousedown.datetimebox", function(e) {
			e.stopPropagation();
		});
		_setValue(target, opts.value);
	}
	
	function _getCurrent(target) {
		var c = $(target).datetimebox("calendar");
		var t = $(target).datetimebox("spinner");
		var current = c.calendar("options").current;
		return new Date(current.getFullYear(), current.getMonth(), current.getDate(), t
				.timespinner("getHours"), t.timespinner("getMinutes"), t
				.timespinner("getSeconds"));
	}
	
	function _query(target, q) {
		_setValue(target, q, true);
	}
	
	function _enter(target) {
		var opts = $.data(target, "datetimebox").options;
		var current = _getCurrent(target);
		_setValue(target, opts.formatter.call(target, current));
		$(target).combo("hidePanel");
	}
	
	function _setValue(target, val, query) {
		var opts = $.data(target, "datetimebox").options;
		$(target).combo("setValue", val);
		if (!query) {
			if (val) {
				var day = opts.parser.call(target, val);
				$(target).combo("setValue", opts.formatter.call(target, day));
				$(target).combo("setText", opts.formatter.call(target, day));
			} else {
				$(target).combo("setText", val);
			}
		}
		var day = opts.parser.call(target, val);
		$(target).datetimebox("calendar").calendar("moveTo", day);
		$(target).datetimebox("spinner").timespinner("setValue", formatDay(day));
		
		function formatDay(day) {
			
			function format(num) {
				return (num < 10 ? "0" : "") + num;
			}
			
			var tt = [ format(day.getHours()), format(day.getMinutes()) ];
			if (opts.showSeconds) {
				tt.push(format(day.getSeconds()));
			}
			return tt.join($(target).datetimebox("spinner").timespinner("options").separator);
		}
		
	}
	
	$.fn.datetimebox = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.datetimebox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.datebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "datetimebox");
			if (t) {
				$.extend(t.options, options);
			} else {
				$.data(this, "datetimebox", {
					options : $.extend( {}, $.fn.datetimebox.defaults,
							$.fn.datetimebox.parseOptions(this), options)
				});
			}
			_init(this);
		});
	};
	$.fn.datetimebox.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], "datetimebox").options;
			opts.originalValue = jq.datebox("options").originalValue;
			return opts;
		},
		spinner : function(jq) {
			return $.data(jq[0], "datetimebox").spinner;
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				_setValue(this, val);
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).datetimebox("options");
				$(this).datetimebox("setValue", opts.originalValue);
			});
		}
	};
	$.fn.datetimebox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.datebox.parseOptions(target), YonYou
				.parseOptions(target, [ "timeSeparator", {
					showSeconds : "boolean"
				} ]));
	};
	$.fn.datetimebox.defaults = $.extend( {}, $.fn.datebox.defaults,
			{
				showSeconds : true,
				timeSeparator : ":",
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
				formatter : function(text) {
					var h = text.getHours();
					var M = text.getMinutes();
					var s = text.getSeconds();
					
					function _25(_26) {
						return (_26 < 10 ? "0" : "") + _26;
					}
					
					var separator = $(this).datetimebox("spinner").timespinner(
							"options").separator;
					var r = $.fn.datebox.defaults.formatter(text) + " " + _25(h)
							+ separator + _25(M);
					if ($(this).datetimebox("options").showSeconds) {
						r += separator + _25(s);
					}
					return r;
				},
				parser : function(s) {
					if ($.trim(s) == "") {
						return new Date();
					}
					var dt = s.split(" ");
					var d = $.fn.datebox.defaults.parser(dt[0]);
					if (dt.length < 2) {
						return d;
					}
					var separator = $(this).datetimebox("spinner").timespinner(
							"options").separator;
					var tt = dt[1].split(separator);
					var year = parseInt(tt[0], 10) || 0;
					var month = parseInt(tt[1], 10) || 0;
					var date = parseInt(tt[2], 10) || 0;
					return new Date(d.getFullYear(), d.getMonth(), d.getDate(),
							year, month, date);
				}
			});
})(jQuery);
