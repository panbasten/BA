(function($) {
	function _init(target) {
		var t = $.data(target, "pagination");
		var opts = t.options;
		var bb = t.bb = {};
		var table = $(target)
				.addClass("pagination")
				.html("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr></tr></table>");
		var tr = table.find("tr");
		
		function initButton(type) {
			var nav = opts.nav[type];
			var a = $("<a href=\"javascript:void(0)\"></a>").appendTo(tr);
			a.wrap("<td></td>");
			a.linkbutton( {
				iconCls : nav.iconCls,
				plain : true
			}).unbind(".pagination").bind("click.pagination", function() {
				nav.handler.call(target);
			});
			return a;
		}
		
		if (opts.showPageList) {
			var ps = $("<select class=\"pagination-page-list\"></select>");
			ps.bind("change", function() {
				opts.pageSize = parseInt($(this).val());
				opts.onChangePageSize.call(target, opts.pageSize);
				_select(target, opts.pageNumber);
			});
			for ( var i = 0; i < opts.pageList.length; i++) {
				$("<option></option>").text(opts.pageList[i]).appendTo(ps);
			}
			$("<td></td>").append(ps).appendTo(tr);
			$("<td><div class=\"pagination-btn-separator\"></div></td>")
					.appendTo(tr);
		}
		bb.first = initButton("first");
		bb.prev = initButton("prev");
		$("<td><div class=\"pagination-btn-separator\"></div></td>").appendTo(
				tr);
		$("<span style=\"padding-left:6px;\"></span>").html(opts.beforePageText)
				.appendTo(tr).wrap("<td></td>");
		bb.num = $("<input class=\"pagination-num\" type=\"text\" value=\"1\" size=\"2\">")
				.appendTo(tr).wrap("<td></td>");
		bb.num.unbind(".pagination").bind("keydown.pagination", function(e) {
			if (e.keyCode == 13) {
				var pageNumber = parseInt($(this).val()) || 1;
				_select(target, pageNumber);
				return false;
			}
		});
		bb.after = $("<span style=\"padding-right:6px;\"></span>").appendTo(tr)
				.wrap("<td></td>");
		$("<td><div class=\"pagination-btn-separator\"></div></td>").appendTo(
				tr);
		bb.next = initButton("next");
		bb.last = initButton("last");
		if (opts.showRefresh) {
			$("<td><div class=\"pagination-btn-separator\"></div></td>")
					.appendTo(tr);
			bb.refresh = initButton("refresh");
		}
		if (opts.buttons) {
			$("<td><div class=\"pagination-btn-separator\"></div></td>")
					.appendTo(tr);
			for ( var i = 0; i < opts.buttons.length; i++) {
				var btn = opts.buttons[i];
				if (btn == "-") {
					$("<td><div class=\"pagination-btn-separator\"></div></td>")
							.appendTo(tr);
				} else {
					var td = $("<td></td>").appendTo(tr);
					$("<a href=\"javascript:void(0)\"></a>").appendTo(td)
							.linkbutton($.extend(btn, {
								plain : true
							})).bind("click", eval(btn.handler || function() {
							}));
				}
			}
		}
		$("<div class=\"pagination-info\"></div>").appendTo(table);
		$("<div style=\"clear:both;\"></div>").appendTo(table);
	}
	
	function _select(target, pageNumber) {
		var opts = $.data(target, "pagination").options;
		var num = Math.ceil(opts.total / opts.pageSize) || 1;
		opts.pageNumber = pageNumber;
		if (opts.pageNumber < 1) {
			opts.pageNumber = 1;
		}
		if (opts.pageNumber > num) {
			opts.pageNumber = num;
		}
		_refresh(target, {
			pageNumber : opts.pageNumber
		});
		opts.onSelectPage.call(target, opts.pageNumber, opts.pageSize);
	}
	
	function _refresh(target, options) {
		var opts = $.data(target, "pagination").options;
		var bb = $.data(target, "pagination").bb;
		$.extend(opts, options || {});
		var ps = $(target).find("select.pagination-page-list");
		if (ps.length) {
			ps.val(opts.pageSize + "");
			opts.pageSize = parseInt(ps.val());
		}
		var num = Math.ceil(opts.total / opts.pageSize) || 1;
		bb.num.val(opts.pageNumber);
		bb.after.html(opts.afterPageText.replace(/{pages}/, num));
		var displayMsg = opts.displayMsg;
		displayMsg = displayMsg.replace(/{from}/, opts.total == 0 ? 0 : opts.pageSize
				* (opts.pageNumber - 1) + 1);
		displayMsg = displayMsg.replace(/{to}/, Math.min(opts.pageSize * (opts.pageNumber),
				opts.total));
		displayMsg = displayMsg.replace(/{total}/, opts.total);
		$(target).find("div.pagination-info").html(displayMsg);
		bb.first.add(bb.prev).linkbutton( {
			disabled : (opts.pageNumber == 1)
		});
		bb.next.add(bb.last).linkbutton( {
			disabled : (opts.pageNumber == num)
		});
		_loading(target, opts.loading);
	}
	
	function _loading(target, loading) {
		var opts = $.data(target, "pagination").options;
		var bb = $.data(target, "pagination").bb;
		opts.loading = loading;
		if (opts.showRefresh) {
			if (opts.loading) {
				bb.refresh.linkbutton( {
					iconCls : "pagination-loading"
				});
			} else {
				bb.refresh.linkbutton( {
					iconCls : "pagination-load"
				});
			}
		}
	}
	
	$.fn.pagination = function(options, param) {
		if (typeof options == "string") {
			return $.fn.pagination.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var opts;
			var t = $.data(this, "pagination");
			if (t) {
				opts = $.extend(t.options, options);
			} else {
				opts = $.extend( {}, $.fn.pagination.defaults, $.fn.pagination
						.parseOptions(this), options);
				$.data(this, "pagination", {
					options : opts
				});
			}
			_init(this);
			_refresh(this);
		});
	};
	$.fn.pagination.methods = {
		options : function(jq) {
			return $.data(jq[0], "pagination").options;
		},
		loading : function(jq) {
			return jq.each(function() {
				_loading(this, true);
			});
		},
		loaded : function(jq) {
			return jq.each(function() {
				_loading(this, false);
			});
		},
		refresh : function(jq, options) {
			return jq.each(function() {
				_refresh(this, options);
			});
		},
		select : function(jq, pageNumber) {
			return jq.each(function() {
				_select(this, pageNumber);
			});
		}
	};
	$.fn.pagination.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, Flywet.parseOptions(target, [ {
			total : "number",
			pageSize : "number",
			pageNumber : "number"
		}, {
			loading : "boolean",
			showPageList : "boolean",
			showRefresh : "boolean"
		} ]), {
			pageList : (t.attr("pageList") ? eval(t.attr("pageList"))
					: undefined)
		});
	};
	$.fn.pagination.defaults = {
		total : 1,
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 10, 20, 30, 50 ],
		loading : false,
		buttons : null,
		showPageList : true,
		showRefresh : true,
		onSelectPage : function(target, pageNumber) {
		},
		onBeforeRefresh : function(target, pageNumber) {
		},
		onRefresh : function(target, pageNumber) {
		},
		onChangePageSize : function(target) {
		},
		beforePageText : "Page",
		afterPageText : "of {pages}",
		displayMsg : "Displaying {from} to {to} of {total} items",
		nav : {
			first : {
				iconCls : "pagination-first",
				handler : function() {
					var opts = $(this).pagination("options");
					if (opts.pageNumber > 1) {
						$(this).pagination("select", 1);
					}
				}
			},
			prev : {
				iconCls : "pagination-prev",
				handler : function() {
					var opts = $(this).pagination("options");
					if (opts.pageNumber > 1) {
						$(this).pagination("select", opts.pageNumber - 1);
					}
				}
			},
			next : {
				iconCls : "pagination-next",
				handler : function() {
					var opts = $(this).pagination("options");
					var pageNumber = Math.ceil(opts.total / opts.pageSize);
					if (opts.pageNumber < pageNumber) {
						$(this).pagination("select", opts.pageNumber + 1);
					}
				}
			},
			last : {
				iconCls : "pagination-last",
				handler : function() {
					var opts = $(this).pagination("options");
					var pageNumber = Math.ceil(opts.total / opts.pageSize);
					if (opts.pageNumber < pageNumber) {
						$(this).pagination("select", pageNumber);
					}
				}
			},
			refresh : {
				iconCls : "pagination-refresh",
				handler : function() {
					var opts = $(this).pagination("options");
					if (opts.onBeforeRefresh.call(this, opts.pageNumber,
							opts.pageSize) != false) {
						$(this).pagination("select", opts.pageNumber);
						opts.onRefresh.call(this, opts.pageNumber, opts.pageSize);
					}
				}
			}
		}
	};
})(jQuery);
