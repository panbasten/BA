(function($) {
	/**
	 * 初始化事件
	 */
	function _initEvents(target) {
		var opts = $.data(target, "timespinner").options;
		$(target).addClass("ui-timespinner-f");
		$(target).spinner(opts);
		$(target).unbind(".timespinner");
		$(target).bind("click.timespinner", function() {
			var len = 0;
			if (this.selectionStart != null) {
				len = this.selectionStart;
			} else {
				if (this.createTextRange) {
					var textRange = target.createTextRange();
					var s = document.selection.createRange();
					s.setEndPoint("StartToStart", textRange);
					len = s.text.length;
				}
			}
			if (len >= 0 && len <= 2) {
				opts.highlight = 0;
			} else {
				if (len >= 3 && len <= 5) {
					opts.highlight = 1;
				} else {
					if (len >= 6 && len <= 8) {
						opts.highlight = 2;
					}
				}
			}
			_7(target);
		}).bind("blur.timespinner", function() {
			_6(target);
		});
	}
	
	function _7(target) {
		var opts = $.data(target, "timespinner").options;
		var index1 = 0, index2 = 0;
		if (opts.highlight == 0) {
			index1 = 0;
			index2 = 2;
		} else {
			if (opts.highlight == 1) {
				index1 = 3;
				index2 = 5;
			} else {
				if (opts.highlight == 2) {
					index1 = 6;
					index2 = 8;
				}
			}
		}
		if (target.selectionStart != null) {
			target.setSelectionRange(index1, index2);
		} else {
			if (target.createTextRange) {
				var _c = target.createTextRange();
				_c.collapse();
				_c.moveEnd("character", index2);
				_c.moveStart("character", index1);
				_c.select();
			}
		}
		$(target).focus();
	}
	
	/**
	 * 获得日期对象
	 */
	function _getDate(target, text) {
		var opts = $.data(target, "timespinner").options;
		if (!text) {
			return null;
		}
		var vv = text.split(opts.separator);
		for ( var i = 0; i < vv.length; i++) {
			if (isNaN(vv[i])) {
				return null;
			}
		}
		while (vv.length < 3) {
			vv.push(0);
		}
		return new Date(1900, 0, 0, vv[0], vv[1], vv[2]);
	}
	
	function _6(target) {
		var opts = $.data(target, "timespinner").options;
		var val = $(target).val();
		var date = _getDate(target, val);
		if (!date) {
			date = _getDate(target, opts.value);
		}
		if (!date) {
			opts.value = "";
			$(target).val("");
			return;
		}
		var min = _getDate(target, opts.min);
		var max = _getDate(target, opts.max);
		if (min && min > date) {
			date = min;
		}
		if (max && max < date) {
			date = max;
		}
		var tt = [ format(date.getHours()), format(date.getMinutes()) ];
		if (opts.showSeconds) {
			tt.push(format(date.getSeconds()));
		}
		var val = tt.join(opts.separator);
		opts.value = val;
		$(target).val(val);
		
		function format(num) {
			return (num < 10 ? "0" : "") + num;
		}
		
	}
	
	function _spin(target, down) {
		var opts = $.data(target, "timespinner").options;
		var val = $(target).val();
		if (val == "") {
			val = [ 0, 0, 0 ].join(opts.separator);
		}
		var vv = val.split(opts.separator);
		for ( var i = 0; i < vv.length; i++) {
			vv[i] = parseInt(vv[i], 10);
		}
		if (down == true) {
			vv[opts.highlight] -= opts.increment;
		} else {
			vv[opts.highlight] += opts.increment;
		}
		$(target).val(vv.join(opts.separator));
		_6(target);
		_7(target);
	}
	
	$.fn.timespinner = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.timespinner.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.spinner(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "timespinner");
			if (t) {
				$.extend(t.options, options);
			} else {
				$.data(this, "timespinner", {
					options : $.extend( {}, $.fn.timespinner.defaults,
							$.fn.timespinner.parseOptions(this), options)
				});
				_initEvents(this);
			}
		});
	};
	$.fn.timespinner.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], "timespinner").options;
			return $.extend(opts, {
				value : jq.val(),
				originalValue : jq.spinner("options").originalValue
			});
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				$(this).val(val);
				_6(this);
			});
		},
		getHours : function(jq) {
			var opts = $.data(jq[0], "timespinner").options;
			var vv = jq.val().split(opts.separator);
			return parseInt(vv[0], 10);
		},
		getMinutes : function(jq) {
			var opts = $.data(jq[0], "timespinner").options;
			var vv = jq.val().split(opts.separator);
			return parseInt(vv[1], 10);
		},
		getSeconds : function(jq) {
			var opts = $.data(jq[0], "timespinner").options;
			var vv = jq.val().split(opts.separator);
			return parseInt(vv[2], 10) || 0;
		}
	};
	$.fn.timespinner.parseOptions = function(target) {
		return $.extend( {}, $.fn.spinner.parseOptions(target), Plywet
				.parseOptions(target, [ "separator", {
					showSeconds : "boolean",
					highlight : "number"
				} ]));
	};
	$.fn.timespinner.defaults = $.extend( {}, $.fn.spinner.defaults, {
		separator : ":",
		showSeconds : false,
		highlight : 0,
		spin : function(down) {
			_spin(this, down);
		}
	});
})(jQuery);
