(function($) {
	function _init(target) {
		var options = $.data(target, "combotree").options;
		var treedom = $.data(target, "combotree").tree;
		$(target).addClass("combotree-f");
		$(target).combo(options);
		var combo = $(target).combo("panel");
		if (!treedom) {
			treedom = $("<ul></ul>").appendTo(combo);
			$.data(target, "combotree").tree = treedom;
		}
		treedom.tree($.extend( {}, options, {
			checkbox : options.multiple,
			onLoadSuccess : function(_6, _7) {
				var values = $(target).combotree("getValues");
				if (options.multiple) {
					var checkedDoms = treedom.tree("getChecked");
					for ( var i = 0; i < checkedDoms.length; i++) {
						var id = checkedDoms[i].id;
						(function() {
							for ( var i = 0; i < values.length; i++) {
								if (id == values[i]) {
									return;
								}
							}
							values.push(id);
						})();
					}
				}
				$(target).combotree("setValues", values);
				options.onLoadSuccess.call(this, _6, _7);
			},
			onClick : function(_a) {
				_onClick(target);
				$(target).combo("hidePanel");
				options.onClick.call(this, _a);
			},
			onCheck : function(_b, _c) {
				_onClick(target);
				options.onCheck.call(this, _b, _c);
			}
		}));
	}
	
	function _onClick(target) {
		var options = $.data(target, "combotree").options;
		var treedata = $.data(target, "combotree").tree;
		var vv = [], ss = [];
		if (options.multiple) {
			var checkedObjs = treedata.tree("getChecked");
			for ( var i = 0; i < checkedObjs.length; i++) {
				vv.push(checkedObjs[i].id);
				ss.push(checkedObjs[i].text);
			}
		} else {
			var checkedObjs = treedata.tree("getSelected");
			if (checkedObjs) {
				vv.push(checkedObjs.id);
				ss.push(checkedObjs.text);
			}
		}
		$(target).combo("setValues", vv).combo("setText", ss.join(options.separator));
	}
	
	function _setValues(top, values) {
		var options = $.data(top, "combotree").options;
		var tree = $.data(top, "combotree").tree;
		tree.find("span.tree-checkbox").addClass("tree-checkbox0").removeClass(
				"tree-checkbox1 tree-checkbox2");
		var vv = [], ss = [];
		for ( var i = 0; i < values.length; i++) {
			var v = values[i];
			var s = v;
			var temp = tree.tree("find", v);
			if (temp) {
				s = temp.text;
				tree.tree("check", temp.target);
				tree.tree("select", temp.target);
			}
			vv.push(v);
			ss.push(s);
		}
		$(top).combo("setValues", vv).combo("setText", ss.join(options.separator));
	}
	
	$.fn.combotree = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.combotree.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var combotree = $.data(this, "combotree");
			if (combotree) {
				$.extend(combotree.options, options);
			} else {
				$.data(this, "combotree", {
					options : $.extend( {}, $.fn.combotree.defaults,
							$.fn.combotree.parseOptions(this), options)
				});
			}
			_init(this);
		});
	};
	
	$.fn.combotree.methods = {
		options : function(jq) {
			var options = $.data(jq[0], "combotree").options;
			options.originalValue = jq.combo("options").originalValue;
			return options;
		},
		tree : function(jq) {
			return $.data(jq[0], "combotree").tree;
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				var options = $.data(this, "combotree").options;
				options.data = data;
				var temp = $.data(this, "combotree").tree;
				temp.tree("loadData", data);
			});
		},
		reload : function(jq, url) {
			return jq.each(function() {
				var options = $.data(this, "combotree").options;
				var obj = $.data(this, "combotree").tree;
				if (url) {
					options.url = url;
				}
				obj.tree( {
					url : options.url
				});
			});
		},
		setValues : function(jq, value) {
			return jq.each(function() {
				_setValues(this, value);
			});
		},
		setValue : function(jq, _24) {
			return jq.each(function() {
				_setValues(this, [ _24 ]);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				var temp = $.data(this, "combotree").tree;
				temp.find("div.tree-node-selected").removeClass(
						"tree-node-selected");
				var cc = temp.tree("getChecked");
				for ( var i = 0; i < cc.length; i++) {
					temp.tree("uncheck", cc[i].target);
				}
				$(this).combo("clear");
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var options = $(this).combotree("options");
				if (options.multiple) {
					$(this).combotree("setValues", options.originalValue);
				} else {
					$(this).combotree("setValue", options.originalValue);
				}
			});
		}
	};
	
	$.fn.combotree.parseOptions = function(target) {
		return $.extend( {}, $.fn.combo.parseOptions(target), $.fn.tree
				.parseOptions(target));
	};
	
	$.fn.combotree.defaults = $.extend( {}, $.fn.combo.defaults,
			$.fn.tree.defaults, {
				editable : false
			});
})(jQuery);
