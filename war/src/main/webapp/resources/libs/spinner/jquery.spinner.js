(function($) {
	function _getSpinner(target) {
		var spinner = $("<span class=\"ui-spinner\">" + "<span class=\"ui-spinner-arrow\">"
						+ "<span class=\"ui-icon ui-spinner-arrow-up\"></span>"
						+ "<span class=\"ui-icon ui-spinner-arrow-down\"></span>"
						+ "</span>" + "</span>").insertAfter(target);
		$(target).addClass("ui-spinner-text ui-spinner-f").prependTo(spinner);
		return spinner;
	}
	
	function _resize(target, width) {
		var opts = $.data(target, "spinner").options;
		var spinner = $.data(target, "spinner").spinner;
		if (width) {
			opts.width = width;
		}
		var div = $("<div style=\"display:none\"></div>").insertBefore(spinner);
		spinner.appendTo("body");
		if (isNaN(opts.width)) {
			opts.width = $(target).outerWidth();
		}
		spinner._outerWidth(opts.width);
		$(target)._outerWidth(spinner.width() - spinner.find(".ui-spinner-arrow").outerWidth());
		spinner.insertAfter(div);
		div.remove();
	}
	
	function _initEvents(target) {
		var opts = $.data(target, "spinner").options;
		var spinner = $.data(target, "spinner").spinner;
		spinner.find(".ui-spinner-arrow-up,.ui-spinner-arrow-down").unbind(".spinner");
		if (!opts.disabled) {
			spinner.find(".ui-spinner-arrow-up").bind("mouseenter.spinner", function() {
				$(this).addClass("ui-spinner-arrow-hover");
			}).bind("mouseleave.spinner", function() {
				$(this).removeClass("ui-spinner-arrow-hover");
			}).bind("click.spinner", function() {
				opts.spin.call(target, false);
				opts.onSpinUp.call(target);
				$(target).validatebox("validate");
			});
			spinner.find(".ui-spinner-arrow-down").bind("mouseenter.spinner",
					function() {
						$(this).addClass("ui-spinner-arrow-hover");
					}).bind("mouseleave.spinner", function() {
				$(this).removeClass("ui-spinner-arrow-hover");
			}).bind("click.spinner", function() {
				opts.spin.call(target, true);
				opts.onSpinDown.call(target);
				$(target).validatebox("validate");
			});
		}
	}
	
	function _setDisabled(target, disabled) {
		var opts = $.data(target, "spinner").options;
		if (disabled) {
			opts.disabled = true;
			$(target).attr("disabled", true);
		} else {
			opts.disabled = false;
			$(target).removeAttr("disabled");
		}
	}
	
	$.fn.spinner = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.spinner.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.validatebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "spinner");
			if (t) {
				$.extend(t.options, options);
			} else {
				t = $.data(this, "spinner", {
					options : $.extend( {}, $.fn.spinner.defaults, $.fn.spinner
							.parseOptions(this), options),
					spinner : _getSpinner(this)
				});
				$(this).removeAttr("disabled");
			}
			t.options.originalValue = t.options.value;
			$(this).val(t.options.value);
			$(this).attr("readonly", !t.options.editable);
			_setDisabled(this, t.options.disabled);
			_resize(this);
			$(this).validatebox(t.options);
			_initEvents(this);
		});
	};
	$.fn.spinner.methods = {
		options : function(jq) {
			var _16 = $.data(jq[0], "spinner").options;
			return $.extend(_16, {
				value : jq.val()
			});
		},
		destroy : function(jq) {
			return jq.each(function() {
				var spinner = $.data(this, "spinner").spinner;
				$(this).validatebox("destroy");
				spinner.remove();
			});
		},
		resize : function(jq, width) {
			return jq.each(function() {
				_resize(this, width);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				_setDisabled(this, false);
				_initEvents(this);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				_setDisabled(this, true);
				_initEvents(this);
			});
		},
		getValue : function(jq) {
			return jq.val();
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				var opts = $.data(this, "spinner").options;
				opts.value = val;
				$(this).val(val);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				var opts = $.data(this, "spinner").options;
				opts.value = "";
				$(this).val("");
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).spinner("options");
				$(this).spinner("setValue", opts.originalValue);
			});
		}
	};
	$.fn.spinner.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.validatebox.parseOptions(target), Plywet
				.parseOptions(target, [ "width", "min", "max", {
					increment : "number",
					editable : "boolean"
				} ]), {
			value : (t.val() || undefined),
			disabled : (t.attr("disabled") ? true : undefined)
		});
	};
	$.fn.spinner.defaults = $.extend( {}, $.fn.validatebox.defaults, {
		width : "auto",
		value : "",
		min : null,
		max : null,
		increment : 1,
		editable : true,
		disabled : false,
		spin : function(target) {
		},
		onSpinUp : function() {
		},
		onSpinDown : function() {
		}
	});
})(jQuery);
