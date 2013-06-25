(function($) {
	/**
	 * 移动滑块
	 */
	function _scroll(target, val) {
		var panel = $(target).combo("panel");
		var item = panel.find("div.ui-combobox-item[value=\"" + val + "\"]");
		if (item.length) {
			if (item.position().top <= 0) {
				var h = panel.scrollTop() + item.position().top;
				panel.scrollTop(h);
			} else {
				if (item.position().top + item.outerHeight() > panel.height()) {
					var h = panel.scrollTop() + item.position().top
							+ item.outerHeight() - panel.height();
					panel.scrollTop(h);
				}
			}
		}
	}
	
	/**
	 * 鼠标抬起
	 */
	function _up(target) {
		var panel = $(target).combo("panel");
		var val = $(target).combo("getValues");
		var item = panel.find("div.ui-combobox-item[value=\"" + val.pop() + "\"]");
		if (item.length) {
			var temp = item.prev(":visible");
			if (temp.length) {
				item = temp;
			}
		} else {
			item = panel.find("div.ui-combobox-item:visible:last");
		}
		var value = item.attr("value");
		_select(target, value);
		_scroll(target, value);
	}
	
	/**
	 * 鼠标按下
	 */
	function _down(target) {
		var panel = $(target).combo("panel");
		var val = $(target).combo("getValues");
		var item = panel.find("div.ui-combobox-item[value=\"" + val.pop() + "\"]");
		if (item.length) {
			var temp = item.next(":visible");
			if (temp.length) {
				item = temp;
			}
		} else {
			item = panel.find("div.ui-combobox-item:visible:first");
		}
		var value = item.attr("value");
		_select(target, value);
		_scroll(target, value);
	}
	
	function _select(target, val) {
		var opts = $.data(target, "combobox").options;
		var data = $.data(target, "combobox").data;
		if (opts.multiple) {
			var vals = $(target).combo("getValues");
			for ( var i = 0; i < vals.length; i++) {
				if (vals[i] == val) {
					return;
				}
			}
			vals.push(val);
			_setValues(target, vals);
		} else {
			_setValues(target, [ val ]);
		}
		for ( var i = 0; i < data.length; i++) {
			if (data[i][opts.valueField] == val) {
				opts.onSelect.call(target, data[i]);
				return;
			}
		}
	}
	
	function _unselect(target, val) {
		var opts = $.data(target, "combobox").options;
		var data = $.data(target, "combobox").data;
		var vals = $(target).combo("getValues");
		for ( var i = 0; i < vals.length; i++) {
			if (vals[i] == val) {
				vals.splice(i, 1);
				_setValues(target, vals);
				break;
			}
		}
		for ( var i = 0; i < data.length; i++) {
			if (data[i][opts.valueField] == val) {
				opts.onUnselect.call(target, data[i]);
				return;
			}
		}
	}
	
	function _setValues(target, vals, setText) {
		var opts = $.data(target, "combobox").options;
		var data = $.data(target, "combobox").data;
		var panel = $(target).combo("panel");
		panel.find("div.ui-combobox-item-selected").removeClass(
				"ui-combobox-item-selected");
		var vv = [], ss = [];
		for ( var i = 0; i < vals.length; i++) {
			var v = vals[i];
			var s = v;
			for ( var j = 0; j < data.length; j++) {
				if (data[j][opts.valueField] == v) {
					s = data[j][opts.textField];
					break;
				}
			}
			vv.push(v);
			ss.push(s);
			panel.find("div.ui-combobox-item[value=\"" + v + "\"]").addClass(
					"ui-combobox-item-selected");
		}
		$(target).combo("setValues", vv);
		if (!setText) {
			$(target).combo("setText", ss.join(opts.separator));
		}
	}
	
	function _getValues(target) {
		var opts = $.data(target, "combobox").options;
		var vals = [];
		$(">option", target).each(
			function() {
				var val = {};
				val[opts.valueField] = $(this).attr("value") != undefined ? $(
						this).attr("value")
						: $(this).html();
				val[opts.textField] = $(this).html();
				val["selected"] = $(this).attr("selected");
				vals.push(val);
			});
		return vals;
	}
	
	function _loadData(target, data, setText) {
		var opts = $.data(target, "combobox").options;
		var panel = $(target).combo("panel");
		$.data(target, "combobox").data = data;
		var vals = $(target).combobox("getValues");
		panel.empty();
		for ( var i = 0; i < data.length; i++) {
			var v = data[i][opts.valueField];
			var s = data[i][opts.textField];
			var item = $("<div class=\"ui-combobox-item\"></div>").appendTo(panel);
			item.attr("value", v);
			if (opts.formatter) {
				item.html(opts.formatter.call(target, data[i]));
			} else {
				item.html(s);
			}
			if (data[i]["selected"]) {
				(function() {
					for ( var i = 0; i < vals.length; i++) {
						if (v == vals[i]) {
							return;
						}
					}
					vals.push(v);
				})();
			}
		}
		if (opts.multiple) {
			_setValues(target, vals, setText);
		} else {
			if (vals.length) {
				_setValues(target, [ vals[vals.length - 1] ], setText);
			} else {
				_setValues(target, [], setText);
			}
		}
		opts.onLoadSuccess.call(target, data);
		$(".ui-combobox-item", panel).hover(function() {
			$(this).addClass("ui-combobox-item-hover");
		}, function() {
			$(this).removeClass("ui-combobox-item-hover");
		}).click(function() {
			var t = $(this);
			if (opts.multiple) {
				if (t.hasClass("ui-combobox-item-selected")) {
					_unselect(target, t.attr("value"));
				} else {
					_select(target, t.attr("value"));
				}
			} else {
				_select(target, t.attr("value"));
				$(target).combo("hidePanel");
			}
		});
	}
	
	function _reload(target, url, data, setText) {
		var opts = $.data(target, "combobox").options;
		if (url) {
			opts.url = url;
		}
		data = data || {};
		if (opts.onBeforeLoad.call(target, data) == false) {
			return;
		}
		opts.loader.call(target, data, function(data) {
			_loadData(target, data, setText);
		}, function() {
			opts.onLoadError.apply(this, arguments);
		});
	}
	
	function _query(target, q) {
		var opts = $.data(target, "combobox").options;
		if (opts.multiple && !q) {
			_setValues(target, [], true);
		} else {
			_setValues(target, [ q ], true);
		}
		if (opts.mode == "remote") {
			_reload(target, null, {
				q : q
			}, true);
		} else {
			var panel = $(target).combo("panel");
			panel.find("div.ui-combobox-item").hide();
			var data = $.data(target, "combobox").data;
			for ( var i = 0; i < data.length; i++) {
				if (opts.filter.call(target, q, data[i])) {
					var v = data[i][opts.valueField];
					var s = data[i][opts.textField];
					var item = panel.find("div.ui-combobox-item[value=\"" + v + "\"]");
					item.show();
					if (s == q) {
						_setValues(target, [ v ], true);
						item.addClass("ui-combobox-item-selected");
					}
				}
			}
		}
	}
	
	function _init(target) {
		var opts = $.data(target, "combobox").options;
		$(target).addClass("ui-combobox-f");
		$(target).combo($.extend( {}, opts, {
			onShowPanel : function() {
				$(target).combo("panel").find("div.ui-combobox-item").show();
				_scroll(target, $(target).combobox("getValue"));
				opts.onShowPanel.call(target);
			}
		}));
	}
	
	$.fn.combobox = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.combobox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "combobox");
			if (t) {
				$.extend(t.options, options);
				_init(this);
			} else {
				t = $.data(this, "combobox", {
					options : $.extend( {}, $.fn.combobox.defaults,
							$.fn.combobox.parseOptions(this), options)
				});
				_init(this);
				_loadData(this, _getValues(this));
			}
			if (t.options.data) {
				_loadData(this, t.options.data);
			}
			_reload(this);
		});
	};
	$.fn.combobox.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], "combobox").options;
			opts.originalValue = jq.combo("options").originalValue;
			return opts;
		},
		getData : function(jq) {
			return $.data(jq[0], "combobox").data;
		},
		setValues : function(jq, val) {
			return jq.each(function() {
				_setValues(this, val);
			});
		},
		setValue : function(jq, val) {
			return jq.each(function() {
				_setValues(this, [ val ]);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				$(this).combo("clear");
				var panel = $(this).combo("panel");
				panel.find("div.ui-combobox-item-selected").removeClass(
						"ui-combobox-item-selected");
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).combobox("options");
				if (opts.multiple) {
					$(this).combobox("setValues", opts.originalValue);
				} else {
					$(this).combobox("setValue", opts.originalValue);
				}
			});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				_loadData(this, data);
			});
		},
		reload : function(jq, url) {
			return jq.each(function() {
				_reload(this, url);
			});
		},
		select : function(jq, val) {
			return jq.each(function() {
				_select(this, val);
			});
		},
		unselect : function(jq, val) {
			return jq.each(function() {
				_unselect(this, val);
			});
		}
	};
	$.fn.combobox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.combo.parseOptions(target), Plywet
				.parseOptions(target, [ "valueField", "textField", "mode",
						"method", "url" ]));
	};
	$.fn.combobox.defaults = $.extend( {}, $.fn.combo.defaults, {
		valueField : "value",
		textField : "text",
		mode : "local",
		method : "post",
		url : null,
		data : null,
		keyHandler : {
			up : function() {
				_up(this);
			},
			down : function() {
				_down(this);
			},
			enter : function() {
				var val = $(this).combobox("getValues");
				$(this).combobox("setValues", val);
				$(this).combobox("hidePanel");
			},
			query : function(q) {
				_query(this, q);
			}
		},
		filter : function(q, row) {
			var opts = $(this).combobox("options");
			return row[opts.textField].indexOf(q) == 0;
		},
		formatter : function(row) {
			var opts = $(this).combobox("options");
			return row[opts.textField];
		},
		loader : function(data, onsuccess, onerror) {
			var opts = $(this).combobox("options");
			if (!opts.url) {
				return false;
			}
			$.ajax( {
				type : opts.method,
				url : opts.url,
				data : data,
				dataType : "json",
				success : function(data) {
					onsuccess(data);
				},
				error : function() {
					onerror.apply(this, arguments);
				}
			});
		},
		onBeforeLoad : function(e) {
		},
		onLoadSuccess : function() {
		},
		onLoadError : function() {
		},
		onSelect : function(e) {
		},
		onUnselect : function(e) {
		}
	});
})(jQuery);


Plywet.widget.ComboBox = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.init();
};
Plywet.extend(Plywet.widget.ComboBox, Plywet.widget.BaseWidget);
Plywet.widget.ComboBox.prototype = {
	init : function() {
		this.jq.combobox(this.cfg);
	}
};
