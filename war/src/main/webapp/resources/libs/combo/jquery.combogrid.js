(function($) {
	function _initGrid(target) {
		var opts = $.data(target, "combogrid").options;
		var grid = $.data(target, "combogrid").grid;
		$(target).addClass("combogrid-f");
		$(target).combo(opts);
		var panel = $(target).combo("panel");
		if (!grid) {
			grid = $("<table></table>").appendTo(panel);
			$.data(target, "combogrid").grid = grid;
		}
		grid.datagrid($.extend( {}, opts, {
			border : false,
			fit : true,
			singleSelect : (!opts.multiple),
			onLoadSuccess : function(e) {
				var remainText = $.data(target, "combogrid").remainText;
				var val = $(target).combo("getValues");
				_setValues(target, val, remainText);
				opts.onLoadSuccess.apply(target, arguments);
			},
			onClickRow : clickRow,
			onSelect : function(e, data) {
				setVal();
				opts.onSelect.call(this, e, data);
			},
			onUnselect : function(e, data) {
				setVal();
				opts.onUnselect.call(this, e, data);
			},
			onSelectAll : function(e) {
				setVal();
				opts.onSelectAll.call(this, e);
			},
			onUnselectAll : function(e) {
				if (opts.multiple) {
					setVal();
				}
				opts.onUnselectAll.call(this, e);
			}
		}));
		
		function clickRow(e, row) {
			$.data(target, "combogrid").remainText = false;
			setVal();
			if (!opts.multiple) {
				$(target).combo("hidePanel");
			}
			opts.onClickRow.call(this, e, row);
		}
		
		function setVal() {
			var remainText = $.data(target, "combogrid").remainText;
			var selections = grid.datagrid("getSelections");
			var vv = [], ss = [];
			for ( var i = 0; i < selections.length; i++) {
				vv.push(selections[i][opts.idField]);
				ss.push(selections[i][opts.textField]);
			}
			if (!opts.multiple) {
				$(target).combo("setValues", (vv.length ? vv : [ "" ]));
			} else {
				$(target).combo("setValues", vv);
			}
			if (!remainText) {
				$(target).combo("setText", ss.join(opts.separator));
			}
		}
		
	}
	
	function _selectRow(target, inc) {
		var opts = $.data(target, "combogrid").options;
		var grid = $.data(target, "combogrid").grid;
		var len = grid.datagrid("getRows").length;
		$.data(target, "combogrid").remainText = false;
		var row;
		var selections = grid.datagrid("getSelections");
		if (selections.length) {
			row = grid.datagrid("getRowIndex", selections[selections.length - 1][opts.idField]);
			row += inc;
			if (row < 0) {
				row = 0;
			}
			if (row >= len) {
				row = len - 1;
			}
		} else {
			if (inc > 0) {
				row = 0;
			} else {
				if (inc < 0) {
					row = len - 1;
				} else {
					row = -1;
				}
			}
		}
		if (row >= 0) {
			grid.datagrid("clearSelections");
			grid.datagrid("selectRow", row);
		}
	}
	
	function _setValues(target, vals, setText) {
		var opts = $.data(target, "combogrid").options;
		var grid = $.data(target, "combogrid").grid;
		var rows = grid.datagrid("getRows");
		var ss = [];
		for ( var i = 0; i < vals.length; i++) {
			var row = grid.datagrid("getRowIndex", vals[i]);
			if (row >= 0) {
				grid.datagrid("selectRow", row);
				ss.push(rows[row][opts.textField]);
			} else {
				ss.push(vals[i]);
			}
		}
		if ($(target).combo("getValues").join(",") == vals.join(",")) {
			return;
		}
		$(target).combo("setValues", vals);
		if (!setText) {
			$(target).combo("setText", ss.join(opts.separator));
		}
	}
	
	function _query(target, q) {
		var opts = $.data(target, "combogrid").options;
		var grid = $.data(target, "combogrid").grid;
		$.data(target, "combogrid").remainText = true;
		if (opts.multiple && !q) {
			_setValues(target, [], true);
		} else {
			_setValues(target, [ q ], true);
		}
		if (opts.mode == "remote") {
			grid.datagrid("clearSelections");
			grid.datagrid("load", $.extend( {}, opts.queryParams, {
				q : q
			}));
		} else {
			if (!q) {
				return;
			}
			var rows = grid.datagrid("getRows");
			for ( var i = 0; i < rows.length; i++) {
				if (opts.filter.call(target, q, rows[i])) {
					grid.datagrid("clearSelections");
					grid.datagrid("selectRow", i);
					return;
				}
			}
		}
	}
	
	$.fn.combogrid = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.combogrid.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return $.fn.combo.methods[options](this, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "combogrid");
			if (t) {
				$.extend(t.options, options);
			} else {
				t = $.data(this, "combogrid", {
					options : $.extend( {}, $.fn.combogrid.defaults,
							$.fn.combogrid.parseOptions(this), options)
				});
			}
			_initGrid(this);
		});
	};
	$.fn.combogrid.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], "combogrid").options;
			opts.originalValue = jq.combo("options").originalValue;
			return opts;
		},
		grid : function(jq) {
			return $.data(jq[0], "combogrid").grid;
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
				$(this).combogrid("grid").datagrid("clearSelections");
				$(this).combo("clear");
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				var opts = $(this).combogrid("options");
				if (opts.multiple) {
					$(this).combogrid("setValues", opts.originalValue);
				} else {
					$(this).combogrid("setValue", opts.originalValue);
				}
			});
		}
	};
	$.fn.combogrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.combo.parseOptions(target), $.fn.datagrid
				.parseOptions(target), Plywet.parseOptions(target, [ "idField",
				"textField", "mode" ]));
	};
	$.fn.combogrid.defaults = $.extend( {}, $.fn.combo.defaults,
			$.fn.datagrid.defaults, {
				loadMsg : null,
				idField : null,
				textField : null,
				mode : "local",
				keyHandler : {
					up : function() {
						_selectRow(this, -1);
					},
					down : function() {
						_selectRow(this, 1);
					},
					enter : function() {
						_selectRow(this, 0);
						$(this).combo("hidePanel");
					},
					query : function(q) {
						_query(this, q);
					}
				},
				filter : function(q, row) {
					var opts = $(this).combogrid("options");
					return row[opts.textField].indexOf(q) == 0;
				}
			});
})(jQuery);
