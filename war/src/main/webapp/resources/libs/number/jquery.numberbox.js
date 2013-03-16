/**
 * jQuery EasyUI 1.3.1
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 */
(function($) {
	/**
	 * 添加隐藏input
	 */
	function _getFiledInput(target) {
		$(target).addClass("numberbox-f");
		var jqinput = $("<input type=\"hidden\">").insertAfter(target);
		var name = $(target).attr("name");
		if (name) {
			jqinput.attr("name", name);
			$(target).removeAttr("name").attr("numberboxName", name);
		}
		return jqinput;
	}
	
	function _dealwithOptions(target) {
		var options = $.data(target, "numberbox").options;
		var fn = options.onChange;
		options.onChange = function() {
		};
		_setValue(target, options.parser.call(target, options.value));
		options.onChange = fn;
		options.originalValue = _getValue(target);
	}
	
	function _getValue(target) {
		return $.data(target, "numberbox").field.val();
	}
	
	function _setValue(target, value) {
		var temp = $.data(target, "numberbox");
		var options = temp.options;
		var _value = _getValue(target);
		value = options.parser.call(target, value);
		options.value = value;
		temp.field.val(value);
		$(target).val(options.formatter.call(target, value));
		if (_value != value) {
			options.onChange.call(target, value, _value);
		}
	}
	
	function _eventOption(target) {
		var options = $.data(target, "numberbox").options;
		$(target).unbind(".numberbox").bind(
				"keypress.numberbox",
				function(e) {
					if (e.which == 45) {
						if ($(this).val().indexOf("-") == -1) {
							return true;
						} else {
							return false;
						}
					}
					if (e.which == 46) {
						if ($(this).val().indexOf(".") == -1) {
							return true;
						} else {
							return false;
						}
					} else {
						if ((e.which >= 48 && e.which <= 57
								&& e.ctrlKey == false && e.shiftKey == false)
								|| e.which == 0 || e.which == 8) {
							return true;
						} else {
							if (e.ctrlKey == true
									&& (e.which == 99 || e.which == 118)) {
								return true;
							} else {
								return false;
							}
						}
					}
				}).bind("paste.numberbox", function() {
			if (window.clipboardData) {
				var s = clipboardData.getData("text");
				if (!/\D/.test(s)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}).bind("dragenter.numberbox", function() {
			return false;
		}).bind("blur.numberbox", function() {
			_setValue(target, $(this).val());
			$(this).val(options.formatter.call(target, _getValue(target)));
		}).bind("focus.numberbox", function() {
			var vv = _getValue(target);
			if ($(this).val() != vv) {
				$(this).val(vv);
			}
		});
	}
	
	function _validate(target) {
		if ($.fn.validatebox) {
			var options = $.data(target, "numberbox").options;
			$(target).validatebox(options);
		}
	}
	
	/**
	 * 切换输入框的可用状态
	 */
	function _disable(target, isdisable) {
		var temp = $.data(target, "numberbox").options;
		if (isdisable) {
			temp.disabled = true;
			$(target).attr("disabled", true);
		} else {
			temp.disabled = false;
			$(target).removeAttr("disabled");
		}
	}
	
	$.fn.numberbox = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.numberbox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.validatebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var numberbox = $.data(this, "numberbox");
			if (numberbox) {
				$.extend(numberbox.options, options);
			} else {
				numberbox = $.data(this, "numberbox", {
					options : $.extend( {}, $.fn.numberbox.defaults,
							$.fn.numberbox.parseOptions(this), options),
					field : _getFiledInput(this)
				});
				$(this).removeAttr("disabled");
				$(this).css( {
					imeMode : "disabled"
				});
			}
			_disable(this, numberbox.options.disabled);
			_eventOption(this);
			_validate(this);
			_dealwithOptions(this);
		});
	};
	
	$.fn.numberbox.methods = {
		options : function(jq) {
			return $.data(jq[0], "numberbox").options;
		},
		destroy : function(jq) {
			return jq.each(function() {
				$.data(this, "numberbox").field.remove();
				$(this).validatebox("destroy");
				$(this).remove();
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				_disable(this, true);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				_disable(this, false);
			});
		},
		fix : function(jq) {
			return jq.each(function() {
				_setValue(this, $(this).val());
			});
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				_setValue(this, val);
			});
		},
		getValue : function(jq) {
			return _getValue(jq[0]);
		},
		clear : function(jq) {
			return jq.each(function() {
				var temp = $.data(this, "numberbox");
				temp.field.val("");
				$(this).val("");
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var temp = $(this).numberbox("options");
				$(this).numberbox("setValue", temp.originalValue);
			});
		}
	};
	
	$.fn.numberbox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.validatebox.parseOptions(target), 
			Plywet.parseOptions(target, [ "decimalSeparator", "groupSeparator",
				"suffix", {
					min : "number",
					max : "number",
					precision : "number"
				} ]), 
			{
				prefix : (t.attr("prefix") ? t.attr("prefix") : undefined),
				disabled : (t.attr("disabled") ? true : undefined),
				value : (t.val() || undefined)
			});
	};
	
	$.fn.numberbox.defaults = $.extend( {}, $.fn.validatebox.defaults,
		{
			disabled : false,
			value : "",
			min : null,
			max : null,
			precision : 0,
			decimalSeparator : ".",
			groupSeparator : "",
			prefix : "",
			suffix : "",
			formatter : function(num) {
				if (!num) {
					return num;
				}
				num = num + "";
				var options = $(this).numberbox("options");
				var s1 = num, s2 = "";
				var pointPos = num.indexOf(".");
				if (pointPos >= 0) {
					s1 = num.substring(0, pointPos);
					s2 = num.substring(pointPos + 1, num.length);
				}
				if (options.groupSeparator) {
					var p = /(\d+)(\d{3})/;
					while (p.test(s1)) {
						s1 = s1.replace(p, "$1"
								+ options.groupSeparator + "$2");
					}
				}
				if (s2) {
					return options.prefix + s1 + options.decimalSeparator
							+ s2 + options.suffix;
				} else {
					return options.prefix + s1 + options.suffix;
				}
			},
			parser : function(s) {
				s = s + "";
				var options = $(this).numberbox("options");
				if (options.groupSeparator) {
					s = s.replace(new RegExp("\\"
							+ options.groupSeparator, "g"), "");
				}
				if (options.decimalSeparator) {
					s = s.replace(new RegExp("\\"
							+ options.decimalSeparator, "g"), ".");
				}
				if (options.prefix) {
					s = s.replace(new RegExp("\\"
							+ $.trim(options.prefix), "g"), "");
				}
				if (options.suffix) {
					s = s.replace(new RegExp("\\"
							+ $.trim(options.suffix), "g"), "");
				}
				s = s.replace(/\s/g, "");
				var val = parseFloat(s).toFixed(options.precision);
				if (isNaN(val)) {
					val = "";
				} else {
					if (typeof (options.min) == "number"
							&& val < options.min) {
						val = options.min.toFixed(options.precision);
					} else {
						if (typeof (options.max) == "number"
								&& val > options.max) {
							val = options.max.toFixed(options.precision);
						}
					}
				}
				return val;
			},
			onChange : function(_25, _26) {
			}
		});
})(jQuery);
