(function($) {
	
	/**
	 * 改变尺寸
	 */
	function _resize(target, width) {
		var opts = $.data(target, "combo").options;
		var combo = $.data(target, "combo").combo;
		var panel = $.data(target, "combo").panel;
		if (width) {
			opts.width = width;
		}
		combo.appendTo("body");
		var text = combo.find("input.ui-combo-text");
		var arrow = combo.find(".ui-combo-arrow");
		if (isNaN(opts.width)) {
			opts.width = text.outerWidth();
		}
		var arrowWidth = 0;
		if (opts.hasDownArrow) {
			arrowWidth = arrow._outerWidth();
		}
		combo._outerWidth(opts.width)._outerHeight(opts.height);
		text._outerWidth(combo.width() - arrowWidth);
		text.css( {
			height : combo.height() + "px",
			lineHeight : combo.height() + "px"
		});
		arrow._outerHeight(combo.height());
		panel.panel("resize", {
			width : (opts.panelWidth ? opts.panelWidth : combo.outerWidth()),
			height : opts.panelHeight
		});
		combo.insertAfter(target);
	}

	/**
	 * 初始化箭头
	 */
	function _initArrow(target) {
		var opts = $.data(target, "combo").options;
		var combo = $.data(target, "combo").combo;
		if (opts.hasDownArrow) {
			combo.find(".ui-combo-arrow").show();
		} else {
			combo.find(".ui-combo-arrow").hide();
		}
	}

	/**
	 * 初始化对象
	 */
	function _init(target) {
		$(target).addClass("ui-combo-f").hide();
		var combo = $("<span class=\"ui-combo\"></span>").insertAfter(target);
		var text = $("<input type=\"text\" class=\"ui-combo-text\">").appendTo(combo);
		$("<span><span class=\"ui-icon ui-combo-arrow\"></span></span>").appendTo(combo);
		$("<input type=\"hidden\" class=\"ui-combo-value\">").appendTo(combo);
		var panel = $("<div class=\"ui-combo-panel\"></div>").appendTo("body");
		panel.panel( {
			doSize : false,
			closed : true,
			cls : "ui-combo-p",
			style : {
				position : "absolute",
				zIndex : 10
			},
			onOpen : function() {
				$(this).panel("resize");
			}
		});
		var name = $(target).attr("name");
		if (name) {
			combo.find("input.ui-combo-value").attr("name", name);
			$(target).removeAttr("name").attr("comboName", name);
		}
		text.attr("autocomplete", "off");
		return {
			combo : combo,
			panel : panel
		};
	}

	/**
	 * 销毁
	 */
	function _destroy(target) {
		var input = $.data(target, "combo").combo.find("input.ui-combo-text");
		input.validatebox("destroy");
		$.data(target, "combo").panel.panel("destroy");
		$.data(target, "combo").combo.remove();
		$(target).remove();
	}

	/**
	 * 初始化事件
	 */
	function _initEvents(target) {
		var t = $.data(target, "combo");
		var opts = t.options;
		var combo = $.data(target, "combo").combo;
		var panel = $.data(target, "combo").panel;
		var text = combo.find(".ui-combo-text");
		var arrow = combo.find(".ui-combo-arrow");
		$(document).unbind(".combo").bind("mousedown.combo", function(e) {
			var panel = $("body>div.ui-combo-p>div.ui-combo-panel");
			var p = $(e.target).closest("div.ui-combo-panel", panel);
			if (p.length) {
				return;
			}
			panel.panel("close");
		});
		combo.unbind(".combo");
		panel.unbind(".combo");
		text.unbind(".combo");
		arrow.unbind(".combo");
		if (!opts.disabled) {
			text.bind("mousedown.combo", function(e) {
				$("div.ui-combo-panel").not(panel).panel("close");
				e.stopPropagation();
			}).bind("keydown.combo", function(e) {
				switch (e.keyCode) {
				case 38:
					opts.keyHandler.up.call(target);
					break;
				case 40:
					opts.keyHandler.down.call(target);
					break;
				case 13:
					e.preventDefault();
					opts.keyHandler.enter.call(target);
					return false;
				case 9:
				case 27:
					_hidePanel(target);
					break;
				default:
					if (opts.editable) {
						if (t.timer) {
							clearTimeout(t.timer);
						}
						t.timer = setTimeout(function() {
							var q = text.val();
							if (t.previousValue != q) {
								t.previousValue = q;
								$(target).combo("showPanel");
								opts.keyHandler.query.call(target, text.val());
								_validate(target, true);
							}
						}, opts.delay);
					}
				}
			});
			arrow.bind("click.combo", function() {
				if (panel.is(":visible")) {
					_hidePanel(target);
				} else {
					$("div.ui-combo-panel").panel("close");
					$(target).combo("showPanel");
				}
				text.focus();
			}).bind("mouseenter.combo", function() {
				$(this).addClass("ui-combo-arrow-hover");
			}).bind("mouseleave.combo", function() {
				$(this).removeClass("ui-combo-arrow-hover");
			}).bind("mousedown.combo", function() {
				return false;
			});
		}
	}
	
	/**
	 * 显示Panel
	 */
	function _showPanel(target) {
		var opts = $.data(target, "combo").options;
		var combo = $.data(target, "combo").combo;
		var panel = $.data(target, "combo").panel;
		if ($.fn.window) {
			panel.panel("panel").css("z-index", $.fn.window.defaults.zIndex++);
		}
		panel.panel("move", {
			left : combo.offset().left,
			top : getTop()
		});
		if (panel.panel("options").closed) {
			panel.panel("open");
			opts.onShowPanel.call(target);
		}
		
		(function() {
			if (panel.is(":visible")) {
				panel.panel("move", {
					left : getLeft(),
					top : getTop()
				});
				setTimeout(arguments.callee, 200);
			}
		})();
		
		function getLeft() {
			var left = combo.offset().left;
			if (left + panel._outerWidth() > $(window)._outerWidth()
					+ $(document).scrollLeft()) {
				left = $(window)._outerWidth() + $(document).scrollLeft()
						- panel._outerWidth();
			}
			if (left < 0) {
				left = 0;
			}
			return left;
		}
		
		function getTop() {
			var top = combo.offset().top + combo._outerHeight();
			if (top + panel._outerHeight() > $(window)._outerHeight()
					+ $(document).scrollTop()) {
				top = combo.offset().top - panel._outerHeight();
			}
			if (top < $(document).scrollTop()) {
				top = combo.offset().top + combo._outerHeight();
			}
			return top;
		}
		
	}
	
	/**
	 * 隐藏Panel
	 */
	function _hidePanel(target) {
		var opts = $.data(target, "combo").options;
		var panel = $.data(target, "combo").panel;
		panel.panel("close");
		opts.onHidePanel.call(target);
	}
	
	/**
	 * 验证
	 */
	function _validate(target, auto) {
		var opts = $.data(target, "combo").options;
		var text = $.data(target, "combo").combo.find("input.ui-combo-text");
		text.validatebox(opts);
		if (auto) {
			text.validatebox("validate");
		}
	}
	
	/**
	 * 设置无效
	 */
	function _disable(target, disabled) {
		var opts = $.data(target, "combo").options;
		var combo = $.data(target, "combo").combo;
		if (disabled) {
			opts.disabled = true;
			$(target).attr("disabled", true);
			combo.find(".ui-combo-value").attr("disabled", true);
			combo.find(".ui-combo-text").attr("disabled", true);
		} else {
			opts.disabled = false;
			$(target).removeAttr("disabled");
			combo.find(".ui-combo-value").removeAttr("disabled");
			combo.find(".ui-combo-text").removeAttr("disabled");
		}
	}
	
	/**
	 * 清除
	 */
	function _clear(target) {
		var opts = $.data(target, "combo").options;
		var combo = $.data(target, "combo").combo;
		if (opts.multiple) {
			combo.find("input.ui-combo-value").remove();
		} else {
			combo.find("input.ui-combo-value").val("");
		}
		combo.find("input.ui-combo-text").val("");
	}
	
	function _getText(target) {
		var combo = $.data(target, "combo").combo;
		return combo.find("input.ui-combo-text").val();
	}
	
	function _setText(target, val) {
		var combo = $.data(target, "combo").combo;
		combo.find("input.ui-combo-text").val(val);
		_validate(target, true);
		$.data(target, "combo").previousValue = val;
	}
	
	function _getValues(target) {
		var result = [];
		var combo = $.data(target, "combo").combo;
		combo.find("input.ui-combo-value").each(function() {
			result.push($(this).val());
		});
		return result;
	}
	
	function _setValues(target, val) {
		var opts = $.data(target, "combo").options;
		var values = _getValues(target);
		var combo = $.data(target, "combo").combo;
		combo.find("input.ui-combo-value").remove();
		var comboName = $(target).attr("comboName");
		for ( var i = 0; i < val.length; i++) {
			var input = $("<input type=\"hidden\" class=\"ui-combo-value\">")
					.appendTo(combo);
			if (comboName) {
				input.attr("name", comboName);
			}
			input.val(val[i]);
		}
		var tmp = [];
		for ( var i = 0; i < values.length; i++) {
			tmp[i] = values[i];
		}
		var aa = [];
		for ( var i = 0; i < val.length; i++) {
			for ( var j = 0; j < tmp.length; j++) {
				if (val[i] == tmp[j]) {
					aa.push(val[i]);
					tmp.splice(j, 1);
					break;
				}
			}
		}
		if (aa.length != val.length || val.length != values.length) {
			if (opts.multiple) {
				opts.onChange.call(target, val, values);
			} else {
				opts.onChange.call(target, val[0], values[0]);
			}
		}
	}
	
	function _getValue(target) {
		var result = _getValues(target);
		return result[0];
	}
	
	function _setValue(target, val) {
		_setValues(target, [ val ]);
	}
	
	function _initOnChange(target) {
		var opts = $.data(target, "combo").options;
		var fn = opts.onChange;
		opts.onChange = function() {
		};
		if (opts.multiple) {
			if (opts.value) {
				if (typeof opts.value == "object") {
					_setValues(target, opts.value);
				} else {
					_setValue(target, opts.value);
				}
			} else {
				_setValues(target, []);
			}
			opts.originalValue = _getValues(target);
		} else {
			_setValue(target, opts.value);
			opts.originalValue = opts.value;
		}
		opts.onChange = fn;
	}
	
	$.fn.combo = function(options, param) {
		if (typeof options == "string") {
			return $.fn.combo.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var combo = $.data(this, "combo");
			if (combo) {
				$.extend(combo.options, options);
			} else {
				var r = _init(this);
				combo = $.data(this, "combo", {
					options : $.extend( {}, $.fn.combo.defaults, $.fn.combo
							.parseOptions(this), options),
					combo : r.combo,
					panel : r.panel,
					previousValue : null
				});
				$(this).removeAttr("disabled");
			}
			$("input.ui-combo-text", combo.combo).attr("readonly",
					!combo.options.editable);
			_initArrow(this);
			_disable(this, combo.options.disabled);
			_resize(this);
			_initEvents(this);
			_validate(this);
			_initOnChange(this);
		});
	};
	
	$.fn.combo.methods = {
		options : function(jq) {
			return $.data(jq[0], "combo").options;
		},
		panel : function(jq) {
			return $.data(jq[0], "combo").panel;
		},
		textbox : function(jq) {
			return $.data(jq[0], "combo").combo.find("input.ui-combo-text");
		},
		destroy : function(jq) {
			return jq.each(function() {
				_destroy(this);
			});
		},
		resize : function(jq, width) {
			return jq.each(function() {
				_resize(this, width);
			});
		},
		showPanel : function(jq) {
			return jq.each(function() {
				_showPanel(this);
			});
		},
		hidePanel : function(jq) {
			return jq.each(function() {
				_hidePanel(this);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				_disable(this, true);
				_initEvents(this);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				_disable(this, false);
				_initEvents(this);
			});
		},
		validate : function(jq) {
			return jq.each(function() {
				_validate(this, true);
			});
		},
		isValid : function(jq) {
			var text = $.data(jq[0], "combo").combo.find("input.ui-combo-text");
			return text.validatebox("isValid");
		},
		clear : function(jq) {
			return jq.each(function() {
				_clear(this);
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $.data(this, "combo").options;
				if (opts.multiple) {
					$(this).combo("setValues", opts.originalValue);
				} else {
					$(this).combo("setValue", opts.originalValue);
				}
			});
		},
		getText : function(jq) {
			return _getText(jq[0]);
		},
		setText : function(jq, val) {
			return jq.each(function() {
				_setText(this, val);
			});
		},
		getValues : function(jq) {
			return _getValues(jq[0]);
		},
		setValues : function(jq, val) {
			return jq.each(function() {
				_setValues(this, val);
			});
		},
		getValue : function(jq) {
			return _getValue(jq[0]);
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				_setValue(this, val);
			});
		}
	};
	
	$.fn.combo.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.validatebox.parseOptions(target), YonYou
				.parseOptions(target, [ "width", "height", "separator", {
					panelWidth : "number",
					editable : "boolean",
					hasDownArrow : "boolean",
					delay : "number"
				} ]), {
			panelHeight : (t.attr("panelHeight") == "auto" ? "auto"
					: parseInt(t.attr("panelHeight")) || undefined),
			multiple : (t.attr("multiple") ? true : undefined),
			disabled : (t.attr("disabled") ? true : undefined),
			value : (t.val() || undefined)
		});
	};
	
	$.fn.combo.defaults = $.extend( {}, $.fn.validatebox.defaults, {
		width : "auto",
		height : 22,
		panelWidth : null,
		panelHeight : 200,
		multiple : false,
		separator : ",",
		editable : true,
		disabled : false,
		hasDownArrow : true,
		value : "",
		delay : 200,
		keyHandler : {
			up : function() {
			},
			down : function() {
			},
			enter : function() {
			},
			query : function(q) {
			}
		},
		onShowPanel : function() {
		},
		onHidePanel : function() {
		},
		onChange : function(e, data) {
		}
	});
	
})(jQuery);
