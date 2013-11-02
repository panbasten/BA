(function($) {
	// 全局行序列化
	var rowIndex = 0;
	
	/**
	 * 获得对象在对象数组中的编号
	 * @param objs 对象数组
	 * @param obj 目标对象
	 */
	function getIndex(objs, obj) {
		for ( var i = 0, len = objs.length; i < len; i++) {
			if (objs[i] == obj) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 删除指定对象，或者指定ID的对象
	 * @param objs 对象数组
	 * @param obj 目标对象，或者是ID对于的属性名称
	 * @param id ID
	 */
	function deleteObject(objs, obj, id) {
		if (typeof obj == "string") {
			for ( var i = 0, len = objs.length; i < len; i++) {
				if (objs[i][obj] == id) {
					objs.splice(i, 1);
					return;
				}
			}
		} else {
			var index = getIndex(objs, obj);
			if (index != -1) {
				objs.splice(index, 1);
			}
		}
	}
	
	/**
	 * 无效
	 */
	function _disabled(target) {
		var panel = $.data(target, "datagrid").panel;
		$("<div class=\"ui-datagrid-disabled-mask\" style=\"display:block\"></div>")
			.appendTo(panel);
	}
	
	/**
	 * 生效
	 */
	function _enable(target) {
		var panel = $.data(target, "datagrid").panel;
		panel.children("div.ui-datagrid-disabled-mask").remove();
	}
	
	/**
	 * 改变Panel尺寸
	 */
	function _resize(target, size) {
		var opts = $.data(target, "datagrid").options;
		var panel = $.data(target, "datagrid").panel;
		if (size) {
			if (size.width) {
				opts.width = size.width;
			}
			if (size.height) {
				opts.height = size.height;
			}
		}
		if (opts.fit == true) {
			var p = panel.panel("panel").parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		panel.panel("resize", {
			width : opts.width,
			height : opts.height
		});
	}
	
	/**
	 * 改变View尺寸
	 */
	function _resizeView(target) {
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		var panel = $.data(target, "datagrid").panel;
		var width = panel.width();
		var height = panel.height();
		var view = dc.view;
		var view1 = dc.view1;
		var view2 = dc.view2;
		var view1Header = view1.children("div.ui-datagrid-header");
		var view2Header = view2.children("div.ui-datagrid-header");
		var view1HeaderTb = view1Header.find("table");
		var view2HeaderTb = view2Header.find("table");
		view.width(width);
		var view1HeaderInner = view1Header.children("div.ui-datagrid-header-inner").show();
		view1.width(view1HeaderInner.find("table").width());
		if (!opts.showHeader) {
			view1HeaderInner.hide();
		}
		view2.width(width - view1._outerWidth());
		view1.children(
				"div.ui-datagrid-header,div.ui-datagrid-body,div.ui-datagrid-footer")
				.width(view1.width());
		view2.children(
				"div.ui-datagrid-header,div.ui-datagrid-body,div.ui-datagrid-footer")
				.width(view2.width());
		var hh;
		view1Header.css("height", "");
		view2Header.css("height", "");
		view1HeaderTb.css("height", "");
		view2HeaderTb.css("height", "");
		hh = Math.max(view1HeaderTb.height(), view2HeaderTb.height());
		view1HeaderTb.height(hh);
		view2HeaderTb.height(hh);
		view1Header.add(view2Header)._outerHeight(hh);
		if (opts.height != "auto") {
			var height = height - view2.children("div.ui-datagrid-header")._outerHeight()
					- view2.children("div.ui-datagrid-footer")._outerHeight()
					- panel.children("div.ui-datagrid-toolbar")._outerHeight();
			panel.children("div.ui-datagrid-pager").each(function() {
				height -= $(this)._outerHeight();
			});
			view1.children("div.ui-datagrid-body").height(height);
			view2.children("div.ui-datagrid-body").height(height);
		}
		view.height(view2.height());
		view2.css("left", view1._outerWidth());
	}
	
	/**
	 * 设置行高
	 */
	function _fixRowHeight(target, ridx, auto) {
		var rows = $.data(target, "datagrid").data.rows;
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		if (!dc.body1.is(":empty") && (!opts.nowrap || opts.autoRowHeight || auto)) {
			if (ridx != undefined) {
				var tr1 = opts.finder.getTr(target, ridx, "body", 1);
				var tr2 = opts.finder.getTr(target, ridx, "body", 2);
				setHeightTrs(tr1, tr2);
			} else {
				var tr1 = opts.finder.getTr(target, 0, "allbody", 1);
				var tr2 = opts.finder.getTr(target, 0, "allbody", 2);
				setHeightTrs(tr1, tr2);
				if (opts.showFooter) {
					var tr1 = opts.finder.getTr(target, 0, "allfooter", 1);
					var tr2 = opts.finder.getTr(target, 0, "allfooter", 2);
					setHeightTrs(tr1, tr2);
				}
			}
		}
		_resizeView(target);
		if (opts.height == "auto") {
			var p = dc.body1.parent();
			var body2 = dc.body2;
			var height = 0;
			var width = 0;
			body2.children().each(function() {
				var c = $(this);
				if (c.is(":visible")) {
					height += c._outerHeight();
					if (width < c._outerWidth()) {
						width = c._outerWidth();
					}
				}
			});
			if (width > body2.width()) {
				height += 18;
			}
			p.height(height);
			body2.height(height);
			dc.view.height(dc.view2.height());
		}
		dc.body2.triggerHandler("scroll");
		
		function setHeightTrs(tr1s, tr2s) {
			for ( var i = 0; i < tr2s.length; i++) {
				var tr1 = $(tr1s[i]);
				var tr2 = $(tr2s[i]);
				tr1.css("height", "");
				tr2.css("height", "");
				var maxHeight = Math.max(tr1.height(), tr2.height());
				tr1.css("height", maxHeight);
				tr2.css("height", maxHeight);
			}
		}
	}
	
	/**
	 * 返回grid对象、fields和columns的包装对象
	 */
	function wrapGrid(target, rownumbers) {
		
		/**
		 * 获得列信息[锁定列, 非锁定列]
		 */
		function getColumns() {
			var frozenColumns = [];
			var columns = [];
			$(target).children("thead").each(
				function() {
					var opt = Flywet.parseOptions(this, [ {
						frozen : "boolean"
					} ]);
					$(this).find("tr").each(
					function() {
						var cols = [];
						$(this).find("th").each(
							function() {
								var th = $(this);
								var col = $.extend({},
									Flywet.parseOptions(this, [
										"field",
										"align",
										{
											sortable : "boolean",
											checkbox : "boolean",
											resizable : "boolean"
										},
										{
											rowspan : "number",
											colspan : "number",
											width : "number"
										} ]),
									{
									title : (th.html() || undefined),
									hidden : (th.attr("hidden") ? true : undefined),
									formatter : (th.attr("formatter") ? eval(th.attr("formatter")) : undefined),
									styler : (th.attr("styler") ? eval(th.attr("styler")) : undefined)
								});
								if (!col.align) {
									col.align = "left";
								}
								if (th.attr("editor")) {
									var s = $.trim(th.attr("editor"));
									if (s.substr(0, 1) == "{") {
										col.editor = eval("(" + s + ")");
									} else {
										col.editor = s;
									}
								}
								cols.push(col);
						});
						opt.frozen ? frozenColumns.push(cols) : columns.push(cols);
					});
				});
			return [ frozenColumns, columns ];
		}

		var gridPanel = $(
			"<div class=\"ui-datagrid-wrap\">"
			+ "<div class=\"ui-datagrid-view\">"
			+ "<div class=\"ui-datagrid-view1\">"
			+ "<div class=\"ui-datagrid-header\">"
			+ "<div class=\"ui-datagrid-header-inner\"></div>"
			+ "</div>" + "<div class=\"ui-datagrid-body\">"
			+ "<div class=\"ui-datagrid-body-inner\"></div>"
			+ "</div>" + "<div class=\"ui-datagrid-footer\">"
			+ "<div class=\"ui-datagrid-footer-inner\"></div>"
			+ "</div>" + "</div>"
			+ "<div class=\"ui-datagrid-view2\">"
			+ "<div class=\"ui-datagrid-header\">"
			+ "<div class=\"ui-datagrid-header-inner\"></div>"
			+ "</div>" + "<div class=\"ui-datagrid-body\"></div>"
			+ "<div class=\"ui-datagrid-footer\">"
			+ "<div class=\"ui-datagrid-footer-inner\"></div>"
			+ "</div>" + "</div>" + "</div>" + "</div>"
			).insertAfter(target);
		gridPanel.panel( { doSize : false });
		gridPanel.panel("panel").addClass("ui-datagrid").bind("_resize",
			function(e, data) {
				var opts = $.data(target, "datagrid").options;
				if (opts.fit == true || data) {
					_resize(target);
					if ($.data(target, "datagrid")) {
						_fixColumnSize(target);
					}
				}
				return false;
			});
		$(target).addClass("ui-datagrid-original").hide().appendTo(gridPanel.children("div.ui-datagrid-view"));
		var columns = getColumns();
		var view = gridPanel.children("div.ui-datagrid-view");
		var view1 = view.children("div.ui-datagrid-view1");
		var view2 = view.children("div.ui-datagrid-view2");
		return {
			panel : gridPanel,
			frozenColumns : columns[0],
			columns : columns[1],
			dc : {
				view : view,
				view1 : view1,
				view2 : view2,
				header1 : view1.children("div.ui-datagrid-header").children(
						"div.ui-datagrid-header-inner"),
				header2 : view2.children("div.ui-datagrid-header").children(
						"div.ui-datagrid-header-inner"),
				body1 : view1.children("div.ui-datagrid-body").children(
						"div.ui-datagrid-body-inner"),
				body2 : view2.children("div.ui-datagrid-body"),
				footer1 : view1.children("div.ui-datagrid-footer").children(
						"div.ui-datagrid-footer-inner"),
				footer2 : view2.children("div.ui-datagrid-footer").children(
						"div.ui-datagrid-footer-inner")
			}
		};
	}
	
	/**
	 * 返回行数据的包装对象：total、rows
	 */
	function wrapData(target) {
		var rtn = {
			total : 0,
			rows : []
		};
		var columns = _getColumnFields(target, true).concat(_getColumnFields(target, false));
		$(target).find("tbody tr").each(function() {
			rtn.total++;
			var col = {};
			for ( var i = 0; i < columns.length; i++) {
				col[columns[i]] = $("td:eq(" + i + ")", this).html();
			}
			rtn.rows.push(col);
		});
		return rtn;
	}
	
	/**
	 * 初始化
	 */
	function initGrid(target) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var dc = grid.dc;
		var panel = grid.panel;
		
		panel.panel($.extend( {}, opts, {
			id : null,
			doSize : false,
			onResize : function(e, data) {
				if ($.data(target, "datagrid")) {
					_resizeView(target);
					_fitColumns(target);
					opts.onResize.call(panel, e, data);
				}
			},
			onExpand : function() {
				_fixRowHeight(target);
				opts.onExpand.call(panel);
			}
		}));
		grid.rowIdPrefix = "datagrid-row-r" + (++rowIndex);
		initHeader(dc.header1, opts.frozenColumns, true);
		initHeader(dc.header2, opts.columns, false);
		initStyle();
		dc.header1.add(dc.header2).css("display", opts.showHeader ? "block" : "none");
		dc.footer1.add(dc.footer2).css("display", opts.showFooter ? "block" : "none");
		if (opts.toolbar) {
			if (typeof opts.toolbar == "string") {
				$(opts.toolbar).addClass("ui-datagrid-toolbar").prependTo(panel);
				$(opts.toolbar).show();
			} else {
				$("div.ui-datagrid-toolbar", panel).remove();
				var tb = $("<div class=\"ui-datagrid-toolbar\"></div>").prependTo(panel);
				Flywet.autocw(opts.toolbar, tb);
			}
		} else {
			$("div.ui-datagrid-toolbar", panel).remove();
		}
		$("div.ui-datagrid-pager", panel).remove();
		if (opts.pagination) {
			var pager = $("<div class=\"ui-datagrid-pager\"></div>");
			if (opts.pagePosition == "bottom") {
				pager.appendTo(panel);
			} else {
				if (opts.pagePosition == "top") {
					pager.addClass("ui-datagrid-pager-top").prependTo(panel);
				} else {
					var pagerTop = $("<div class=\"ui-datagrid-pager ui-datagrid-pager-top\"></div>").prependTo(panel);
					pager.appendTo(panel);
					pager = pager.add(pagerTop);
				}
			}
			pager.pagination( {
				total : 0,
				pageNumber : opts.pageNumber,
				pageSize : opts.pageSize,
				pageList : opts.pageList,
				onSelectPage : function(pageNumber, pageSize) {
					opts.pageNumber = pageNumber;
					opts.pageSize = pageSize;
					pager.pagination("refresh", {
						pageNumber : pageNumber,
						pageSize : pageSize
					});
					request(target);
				}
			});
			opts.pageSize = pager.pagination("options").pageSize;
		}
		
		// originalData
		 grid.originalData = Flywet.deepClone(opts.data);
		
		function initHeader(header, columns, fromFrozenColumns) {
			if (!columns) {
				return;
			}
			$(header).show();
			$(header).empty();
			var t = $("<table class=\"ui-datagrid-htable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody></tbody></table>")
					.appendTo(header);
			for ( var i = 0; i < columns.length; i++) {
				var tr = $("<tr class=\"ui-datagrid-header-row\"></tr>").appendTo($("tbody", t));
				var column = columns[i];
				for ( var j = 0; j < column.length; j++) {
					var col = column[j];
					var tdAttrs = "";
					if (col.rowspan) {
						tdAttrs += "rowspan=\"" + col.rowspan + "\" ";
					}
					if (col.colspan) {
						tdAttrs += "colspan=\"" + col.colspan + "\" ";
					}
					var td = $("<td " + tdAttrs + "></td>").appendTo(tr);
					if (col.checkbox) {
						td.attr("field", col.field);
						$("<div class=\"ui-datagrid-header-check\"></div>").html(
								"<input type=\"checkbox\"/>").appendTo(td);
					} else {
						if (col.field) {
							td.attr("field", col.field);
							td.append("<div class=\"ui-datagrid-cell\"><span></span><span class=\"ui-datagrid-sort-icon\"></span></div>");
							$("span", td).html(col.title);
							$("span.ui-datagrid-sort-icon", td).html("&nbsp;");
							var cell = td.find("div.ui-datagrid-cell");
							if (col.resizable == false) {
								cell.attr("resizable", "false");
							}
							if (col.width) {
								cell._outerWidth(col.width);
								col.boxWidth = parseInt(cell[0].style.width);
							} else {
								col.auto = true;
							}
							cell.css("text-align", (col.align || "left"));
							col.cellClass = "ui-datagrid-cell-c" + rowIndex + "-" + col.field.replace(/\./g, "-");
							col.cellSelector = "div." + col.cellClass;
						} else {
							$("<div class=\"ui-datagrid-cell-group\"></div>")
									.html(col.title).appendTo(td);
						}
					}
					if (col.hidden) {
						td.hide();
					}
				}
			}
			if (fromFrozenColumns && opts.rownumbers) {
				var td = $("<td rowspan=\""
						+ opts.frozenColumns.length
						+ "\"><div class=\"ui-datagrid-header-rownumber\"></div></td>");
				if ($("tr", t).length == 0) {
					td.wrap("<tr class=\"ui-datagrid-header-row\"></tr>").parent()
							.appendTo($("tbody", t));
				} else {
					td.prependTo($("tr:first", t));
				}
			}
		}
		
		function initStyle() {
			var ss = [ "<style type=\"text/css\">" ];
			var columns = _getColumnFields(target, true).concat(_getColumnFields(target));
			for ( var i = 0; i < columns.length; i++) {
				var col = _getColumnOption(target, columns[i]);
				if (col && !col.checkbox) {
					ss.push(col.cellSelector + " {width:" + col.boxWidth + "px;}");
				}
			}
			ss.push("</style>");
			$(ss.join("\n")).prependTo(dc.view);
		}
		
	}
	
	/**
	 * 初始化事件
	 */
	function initEvents(target) {
		var grid = $.data(target, "datagrid");
		var panel = grid.panel;
		var opts = grid.options;
		var dc = grid.dc;
		var header = dc.header1.add(dc.header2);
		header.find("input[type=checkbox]").unbind(".datagrid").bind(
				"click.datagrid", function(e) {
					if (opts.singleSelect && opts.selectOnCheck) {
						return false;
					}
					if ($(this).is(":checked")) {
						_checkAll(target);
					} else {
						_uncheckAll(target);
					}
					e.stopPropagation();
				});
		var headerCell = header.find("div.ui-datagrid-cell");
		headerCell.closest("td").unbind(".datagrid").
			bind("mouseenter.datagrid",
				function() {
					if (grid.resizing) {
						return;
					}
					$(this).addClass("ui-state-hover");
				})
			.bind("mouseleave.datagrid", 
				function() {
					$(this).removeClass("ui-state-hover");
				})
			.bind("contextmenu.datagrid", 
				function(e) {
					var field = $(this).attr("field");
					opts.onHeaderContextMenu.call(target, e, field);
				});
		headerCell.unbind(".datagrid")
			.bind("click.datagrid", 
				function(e) {
					if (e.pageX < $(this).offset().left + $(this)._outerWidth() - 5) {
						var field = $(this).parent().attr("field");
						var col = _getColumnOption(target, field);
						if (!col.sortable || grid.resizing) {
							return;
						}
						opts.sortName = field;
						opts.sortOrder = "asc";
						var c = "ui-datagrid-sort-asc";
						if ($(this).hasClass(c)) {
							c = "ui-datagrid-sort-desc";
							opts.sortOrder = "desc";
						}
						headerCell.removeClass("ui-datagrid-sort-asc ui-datagrid-sort-desc");
						$(this).addClass(c);
						if (opts.remoteSort) {
							request(target);
						} else {
							var data = $.data(target, "datagrid").data;
							_loadData(target, data);
						}
						opts.onSortColumn.call(target, opts.sortName, opts.sortOrder);
					}
				})
			.bind("dblclick.datagrid", 
				function(e) {
					if (e.pageX > $(this).offset().left + $(this)._outerWidth() - 5) {
						var field = $(this).parent().attr("field");
						var col = _getColumnOption(target, field);
						if (col.resizable == false) {
							return;
						}
						$(target).datagrid("autoSizeColumn", field);
						col.auto = false;
					}
				});
		headerCell.each(function() {
			$(this).resizable({
				handles : "e",
				disabled : ($(this).attr("resizable") ? $(
						this).attr("resizable") == "false"
						: false),
				minWidth : 25,
				onStartResize : function(e) {
					grid.resizing = true;
					header.css("cursor", "e-resize");
					if (!grid.proxy) {
						grid.proxy = $("<div class=\"ui-datagrid-resize-proxy\"></div>").appendTo(dc.view);
					}
					grid.proxy.css( {
						left : e.pageX - $(panel).offset().left - 1,
						display : "none"
					});
					setTimeout(function() {
						if (grid.proxy) {
							grid.proxy.show();
						}
					}, 500);
				},
				onResize : function(e) {
					grid.proxy.css( {
						left : e.pageX - $(panel).offset().left - 1,
						display : "block"
					});
					return false;
				},
				onStopResize : function(e) {
					header.css("cursor", "");
					var field = $(this).parent().attr("field");
					var col = _getColumnOption(target, field);
					col.width = $(this)._outerWidth();
					col.boxWidth = parseInt(this.style.width);
					col.auto = undefined;
					_fixColumnSize(target, field);
					dc.view2.children("div.ui-datagrid-header").scrollLeft(dc.body2.scrollLeft());
					grid.proxy.remove();
					grid.proxy = null;
					if ($(this).parents("div:first.ui-datagrid-header").parent().hasClass("ui-datagrid-view1")) {
						_resizeView(target);
					}
					_fitColumns(target);
					opts.onResizeColumn.call(target, field, col.width);
					grid.resizing = false;
				}
			});
		});
		
		dc.body1.add(dc.body2).unbind()
			.bind("mouseover", function(e) {
					if (grid.resizing) {
						return;
					}
					var tr = $(e.target).closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					var _5e = getRowIndexFromTr(tr);
					opts.finder.getTr(target, _5e).addClass("ui-state-hover");
					e.stopPropagation();
				})
			.bind("mouseout", function(e) {
					var tr = $(e.target).closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					var index = getRowIndexFromTr(tr);
					opts.finder.getTr(target, index).removeClass("ui-state-hover");
					e.stopPropagation();
				})
			.bind("click", function(e) {
					var tt = $(e.target);
					var tr = tt.closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					var index = getRowIndexFromTr(tr);
					
					// beginEditOnClickRow
					if (opts.beginEditOnClickRow == true) {
						if (opts.lastEditIndex == undefined) {
							_beginEdit(target, index);
							opts.lastEditIndex = index;
						} else {
							if(opts.lastEditIndex != index){
								_endEdit(target, opts.lastEditIndex);
								_beginEdit(target, index);
								opts.lastEditIndex = index;
							} else {
								_endEdit(target, opts.lastEditIndex);
								opts.lastEditIndex = undefined;
							}
						}
					}
					opts.onClickRow.call(target, index, row);
					
					
					if (tt.parent().hasClass("ui-datagrid-cell-check")) {
						if (opts.singleSelect && opts.selectOnCheck) {
							if (!opts.checkOnSelect) {
								_uncheckAll(target, true);
							}
							_checkRow(target, index);
						} else {
							if (tt.is(":checked")) {
								_checkRow(target, index);
							} else {
								_uncheckRow(target, index);
							}
						}
					} else {
						var row = opts.finder.getRow(target, index);
						var td = tt.closest("td[field]", tr);
						if (td.length) {
							var field = td.attr("field");
							opts.onClickCell.call(target, index, field, row[field]);
						}
						if (opts.singleSelect == true) {
							_selectRow(target, index);
						} else {
							if (tr.hasClass("ui-state-active")) {
								_unselectRow(target, index);
							} else {
								_selectRow(target, index);
							}
						}
					}
					
					e.stopPropagation();
				})
			.bind("dblclick", function(e) {
					var tt = $(e.target);
					var tr = tt.closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					var index = getRowIndexFromTr(tr);
					var row = opts.finder.getRow(target, index);
					var td = tt.closest("td[field]", tr);
					if (td.length) {
						var field = td.attr("field");
						opts.onDblClickCell.call(target, index, field, row[field]);
					}
					opts.onDblClickRow.call(target, index, row);
					e.stopPropagation();
				})
			.bind("contextmenu", function(e) {
					var tr = $(e.target).closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					var field = getRowIndexFromTr(tr);
					var row = opts.finder.getRow(target, field);
					opts.onRowContextMenu.call(target, e, field, row);
					e.stopPropagation();
				});
		
		dc.body2.bind("scroll", function() {
			dc.view1.children("div.ui-datagrid-body").scrollTop($(this).scrollTop());
			dc.view2.children("div.ui-datagrid-header,div.ui-datagrid-footer").scrollLeft($(this).scrollLeft());
		});
		
		function getRowIndexFromTr(tr) {
			if (tr.attr("datagrid-row-index")) {
				return parseInt(tr.attr("datagrid-row-index"));
			} else {
				return tr.attr("node-id");
			}
		}
		
	}
	
	/**
	 * 设置列尺寸
	 */
	function _fitColumns(target) {
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		if (!opts.fitColumns) {
			return;
		}
		var header = dc.view2.children("div.ui-datagrid-header");
		var sumWidth = 0;
		var lastColumn;
		var columns = _getColumnFields(target, false);
		for ( var i = 0; i < columns.length; i++) {
			var col = _getColumnOption(target, columns[i]);
			if (needFix(col)) {
				sumWidth += col.width;
				lastColumn = col;
			}
		}
		var headerInner = header.children("div.ui-datagrid-header-inner").show();
		var restWidth = header.width() - header.find("table").width() - opts.scrollbarSize;
		var scale = restWidth / sumWidth;
		if (!opts.showHeader) {
			headerInner.hide();
		}
		for ( var i = 0; i < columns.length; i++) {
			var col = _getColumnOption(target, columns[i]);
			if (needFix(col)) {
				var inc = Math.floor(col.width * scale);
				fix(col, inc);
				restWidth -= inc;
			}
		}
		if (restWidth && lastColumn) {
			fix(lastColumn, restWidth);
		}
		
		_fixColumnSize(target);
		
		function fix(col, inc) {
			col.width += inc;
			col.boxWidth += inc;
			header.find("td[field=\"" + col.field + "\"] div.ui-datagrid-cell")
					.width(col.boxWidth);
		}
		
		function needFix(col) {
			if (!col.hidden && !col.checkbox && !col.auto) {
				return true;
			}
		}
	}
	
	/**
	 * 自动匹配列尺寸
	 */
	function _autoSizeColumn(target, field) {
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		
		if (field) {
			fix(field);
			if (opts.fitColumns) {
				_resizeView(target);
				_fitColumns(target);
			}
		} else {
			var march = false;
			var fields = _getColumnFields(target, true).concat(_getColumnFields(target, false));
			for ( var i = 0; i < fields.length; i++) {
				var field = fields[i];
				var col = _getColumnOption(target, field);
				if (col.auto) {
					fix(field);
					march = true;
				}
			}
			if (march && opts.fitColumns) {
				_resizeView(target);
				_fitColumns(target);
			}
		}
		
		function fix(field) {
			var column = dc.view.find("div.ui-datagrid-header td[field=\"" + field
					+ "\"] div.ui-datagrid-cell");
			column.css("width", "");
			var col = $(target).datagrid("getColumnOption", field);
			col.width = undefined;
			col.boxWidth = undefined;
			col.auto = true;
			$(target).datagrid("fixColumnSize", field);
			var w = Math.max(column._outerWidth(), maxWidth("allbody"),
					maxWidth("allfooter"));
			column._outerWidth(w);
			col.width = w;
			col.boxWidth = parseInt(column[0].style.width);
			$(target).datagrid("fixColumnSize", field);
			opts.onResizeColumn.call(target, field, col.width);
			
			function maxWidth(tr) {
				var max = 0;
				opts.finder.getTr(target, 0, tr).find(
					"td[field=\"" + field + "\"] div.ui-datagrid-cell").each(
					function() {
						var w = $(this)._outerWidth();
						if (max < w) {
							max = w;
						}
					});
				return max;
			}
		}
	}
	
	/**
	 * 根据指定的cell对象设置列尺寸
	 */
	function _fixColumnSize(target, cell) {
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		var fixTable = dc.view.find("table.ui-datagrid-btable,table.ui-datagrid-ftable");
		fixTable.css("table-layout", "fixed");
		if (cell) {
			fix(cell);
		} else {
			var ff = _getColumnFields(target, true).concat(_getColumnFields(target, false));
			for ( var i = 0; i < ff.length; i++) {
				fix(ff[i]);
			}
		}
		fixTable.css("table-layout", "auto");
		_fixMergedColumnSize(target);
		
		_fixRowHeight(target);
		_fixEditableColumnSize(target);
		
		function fix(cell) {
			var col = _getColumnOption(target, cell);
			if (col.checkbox) {
				return;
			}
			var viewStyle = dc.view.children("style")[0];
			var viewStyleSheet = viewStyle.styleSheet ? viewStyle.styleSheet
					: (viewStyle.sheet || document.styleSheets[document.styleSheets.length - 1]);
			var rules = viewStyleSheet.cssRules || viewStyleSheet.rules;
			for ( var i = 0, len = rules.length; i < len; i++) {
				var rule = rules[i];
				if (rule.selectorText.toLowerCase() == col.cellSelector.toLowerCase()) {
					rule.style["width"] = col.boxWidth ? col.boxWidth + "px" : "auto";
					break;
				}
			}
		}
	}
	
	/**
	 * 设置合并的单元格的尺寸
	 */
	function _fixMergedColumnSize(target) {
		var dc = $.data(target, "datagrid").dc;
		dc.body1.add(dc.body2).find("td.ui-datagrid-td-merged").each(function() {
			var td = $(this);
			var colspan = td.attr("colspan") || 1;
			var width = _getColumnOption(target, td.attr("field")).width;
			for ( var i = 1; i < colspan; i++) {
				td = td.next();
				width += _getColumnOption(target, td.attr("field")).width + 1;
			}
			$(this).children("div.ui-datagrid-cell")._outerWidth(width);
		});
	}
	
	/**
	 * 设置编辑框的单元格尺寸
	 */
	function _fixEditableColumnSize(target) {
		var dc = $.data(target, "datagrid").dc;
		dc.view.find("div.ui-datagrid-editable").each(function() {
			var grid = $(this);
			var field = grid.parent().attr("field");
			var col = $(target).datagrid("getColumnOption", field);
			grid._outerWidth(col.width);
			var ed = $.data(this, "datagrid.editor");
			if (ed.actions.resize) {
				ed.actions.resize(ed.target, grid.width());
			}
		});
	}
	
	/**
	 * 获得column的指定域参数
	 * @param target grid对象
	 * @param field 参数域
	 */
	function _getColumnOption(target, field) {
		
		function getCol(columns) {
			if (columns) {
				for ( var i = 0; i < columns.length; i++) {
					var cols = columns[i];
					for ( var j = 0; j < cols.length; j++) {
						var col = cols[j];
						if (col.field == field) {
							return col;
						}
					}
				}
			}
			return null;
		}
		
		var opts = $.data(target, "datagrid").options;
		var col = getCol(opts.columns);
		if (!col) {
			col = getCol(opts.frozenColumns);
		}
		return col;
	}
	
	/**
	 * 获得column的所有域参数
	 */
	function _getColumnFields(target, fromFrozenColumns) {
		var opts = $.data(target, "datagrid").options;
		var columns = (fromFrozenColumns == true) ? (opts.frozenColumns || [ [] ]) : opts.columns;
		if (columns.length == 0) {
			return [];
		}
		var fields = [];
		
		function getSubColIndex(cidx) {
			var c = 0;
			var index = 0;
			while (true) {
				if (fields[index] == undefined) {
					if (c == cidx) {
						return index;
					}
					c++;
				}
				index++;
			}
		}
		
		function getFields(ridx) {
			var ff = [];
			var c = 0;
			for ( var i = 0; i < columns[ridx].length; i++) {
				var col = columns[ridx][i];
				if (col.field) {
					ff.push( [ c, col.field ]);
				}
				c += parseInt(col.colspan || "1");
			}
			for ( var i = 0; i < ff.length; i++) {
				ff[i][0] = getSubColIndex(ff[i][0]);
			}
			for ( var i = 0; i < ff.length; i++) {
				var f = ff[i];
				fields[f[0]] = f[1];
			}
		}
		
		for ( var i = 0; i < columns.length; i++) {
			getFields(i);
		}
		return fields;
	}
	
	function _loadConvertData(target, data) {
		var opts = $(target).datagrid("options");
		if(data.rows){
			var cols, colOpt, colsConvertAction={}, editorType;
			// 可转换类型的字段
			cols = _getColumnFields(target, true).concat(_getColumnFields(target, false));
			
			for(var i=0;i<cols.length;i++){
				colOpt = _getColumnOption(target, cols[i]);
				if(colOpt.editor){
					var editorOpts;
					if (typeof colOpt.editor == "string") {
						editorType = colOpt.editor;
					} else {
						editorType = colOpt.editor.type;
						editorOpts = colOpt.editor.options;
					}
					var editorAction = opts.editors[editorType];
					if (editorAction && editorAction.convertValue) {
						colsConvertAction[cols[i]] = {
							action: editorAction.convertValue,
							options: editorOpts
						};
					}
				}
			}
			
			var row;
			for(var i=0;i<data.rows.length;i++){
				row = data.rows[i];
				if(row){
					for(var fieldName in colsConvertAction){
						row[fieldName] = colsConvertAction[fieldName].action(colsConvertAction[fieldName].options, row[fieldName]);
					}
				}
			}
		}
		
		return data;
	}
	
	/**
	 * 获得表格数据
	 */
	function _getConvertRows(target, rows) {
		var opts = $(target).datagrid("options");
		if(rows){
			var cols, colOpt, colsConvertAction={}, editorType;
			// 可转换类型的字段
			cols = _getColumnFields(target, true).concat(_getColumnFields(target, false));
			
			for(var i=0;i<cols.length;i++){
				colOpt = _getColumnOption(target, cols[i]);
				if(colOpt.editor){
					var editorOpts;
					if (typeof colOpt.editor == "string") {
						editorType = colOpt.editor;
					} else {
						editorType = colOpt.editor.type;
						editorOpts = colOpt.editor.options;
					}
					var editorAction = opts.editors[editorType];
					if (editorAction && editorAction.getConvertValue) {
						colsConvertAction[cols[i]] = {
							action: editorAction.getConvertValue,
							options: editorOpts
						};
					}
				}
			}
			
			var row;
			for(var i=0;i<rows.length;i++){
				row = rows[i];
				if(row){
					for(var fieldName in colsConvertAction){
						if(row[fieldName]){
							row[fieldName] = colsConvertAction[fieldName].action(colsConvertAction[fieldName].options, row[fieldName]);
						}
					}
				}
			}
		}
		return rows;
	}
	
	/**
	 * 加载数据
	 */
	function _loadData(target, data) {
		var gridData = $.data(target, "datagrid");
		var opts = gridData.options;
		var dc = gridData.dc;
		var selectedRows = gridData.selectedRows;
		
		// 过滤数据
		data = opts.loadFilter(target, data);
		
		// 转换数据，主要对于checkbox和combobox等，显示值和实际值不一致的对象
		data = _loadConvertData(target, data);
		
		gridData.data = data;
		if (data.footer) {
			gridData.footer = data.footer;
		}
		if (!opts.remoteSort) {
			var opt = _getColumnOption(target, opts.sortName);
			if (opt) {
				var sorter = opt.sorter || function(a, b) {
					return (a > b ? 1 : -1);
				};
				data.rows.sort(function(r1, r2) {
					return sorter(r1[opts.sortName], r2[opts.sortName])
							* (opts.sortOrder == "asc" ? 1 : -1);
				});
			}
		}
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, target, data.rows);
		}
		opts.view.render.call(opts.view, target, dc.body2, false);
		opts.view.render.call(opts.view, target, dc.body1, true);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, target, dc.footer2, false);
			opts.view.renderFooter.call(opts.view, target, dc.footer1, true);
		}
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, target);
		}
		dc.view.children("style:gt(0)").remove();
		opts.onLoadSuccess.call(target, data);
		var pager = $(target).datagrid("getPager");
		if (pager.length) {
			if (pager.pagination("options").total != data.total) {
				pager.pagination("refresh", {
					total : data.total
				});
			}
		}
		_fixRowHeight(target);
		dc.body2.triggerHandler("scroll");
		selectRows();
		$(target).datagrid("autoSizeColumn");
		
		function selectRows() {
			if (opts.idField && data.rows) {
				for ( var i = 0; i < data.rows.length; i++) {
					var row = data.rows[i];
					if (isSelected(row)) {
						_selectRecord(target, row[opts.idField]);
					}
				}
			}
			function isSelected(row) {
				for ( var i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i][opts.idField] == row[opts.idField]) {
						selectedRows[i] = row;
						return true;
					}
				}
				return false;
			}
		}
	}
	
	/**
	 * 获得指定行的序号
	 */
	function _getRowIndex(target, row) {
		var opts = $.data(target, "datagrid").options;
		var rows = $.data(target, "datagrid").data.rows;
		if (typeof row == "object") {
			return getIndex(rows, row);
		} else {
			for ( var i = 0; i < rows.length; i++) {
				if (rows[i][opts.idField] == row) {
					return i;
				}
			}
			return -1;
		}
	}
	
	/**
	 * 获得选中的行
	 */
	function _getSelections(target) {
		var opts = $.data(target, "datagrid").options;
		var data = $.data(target, "datagrid").data;
		if (opts.idField) {
			return $.data(target, "datagrid").selectedRows;
		} else {
			var rows = [];
			opts.finder.getTr(target, "", "selected", 2).each(function() {
				var index = parseInt($(this).attr("datagrid-row-index"));
				rows.push(data.rows[index]);
			});
			return rows;
		}
	}
	
	/**
	 * 根据ID选中一行
	 */
	function _selectRecord(target, idValue) {
		var opts = $.data(target, "datagrid").options;
		if (opts.idField) {
			var ridx = _getRowIndex(target, idValue);
			if (ridx >= 0) {
				_selectRow(target, ridx);
			}
		}
	}
	
	/**
	 * 根据行索引号选中一行，索引号从0开始
	 * @param target grid对象
	 * @param index 行索引号
	 * @param skipCheck 是否忽略表示行选中的checkbox
	 */
	function _selectRow(target, index, skipCheck) {
		var grid = $.data(target, "datagrid");
		var dc = grid.dc;
		var opts = grid.options;
		var data = grid.data;
		if(!opts.selectable){
			return;
		}
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (opts.singleSelect) {
			_unselectAll(target);
			selectedRows.splice(0, selectedRows.length);
		}
		if (!skipCheck && opts.checkOnSelect) {
			_checkRow(target, index, true);
		}
		if (opts.idField) {
			var row = opts.finder.getRow(target, index);
			(function() {
				for ( var i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i][opts.idField] == row[opts.idField]) {
						return;
					}
				}
				selectedRows.push(row);
			})();
		}
		opts.onSelect.call(target, index, data.rows[index]);
		var tr = opts.finder.getTr(target, index).addClass("ui-state-active");
		if (tr.length) {
			var headerHeight = dc.view2.children("div.ui-datagrid-header")._outerHeight();
			var body2 = dc.body2;
			var top = tr.position().top - headerHeight;
			if (top <= 0) {
				body2.scrollTop(body2.scrollTop() + top);
			} else {
				if (top + tr._outerHeight() > body2.height() - 18) {
					body2.scrollTop(body2.scrollTop() + top + tr._outerHeight()
							- body2.height() + 18);
				}
			}
		}
	}
	
	/**
	 * 取消选中一行
	 */
	function _unselectRow(target, index, skip) {
		var grid = $.data(target, "datagrid");
		var dc = grid.dc;
		var opts = grid.options;
		var data = grid.data;
		if(!opts.selectable){
			return;
		}
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (!skip && opts.checkOnSelect) {
			_uncheckRow(target, index, true);
		}
		opts.finder.getTr(target, index).removeClass("ui-state-active");
		var row = opts.finder.getRow(target, index);
		if (opts.idField) {
			deleteObject(selectedRows, opts.idField, row[opts.idField]);
		}
		opts.onUnselect.call(target, index, row);
	}
	
	/**
	 * 选中所有行
	 */
	function _selectAll(target, skip) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var rows = grid.data.rows;
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (!skip && opts.checkOnSelect) {
			_checkAll(target, true);
		}
		opts.finder.getTr(target, "", "allbody").addClass("ui-state-active");
		if (opts.idField) {
			for ( var ridx = 0; ridx < rows.length; ridx++) {
				(function() {
					var row = rows[ridx];
					for ( var i = 0; i < selectedRows.length; i++) {
						if (selectedRows[i][opts.idField] == row[opts.idField]) {
							return;
						}
					}
					selectedRows.push(row);
				})();
			}
		}
		opts.onSelectAll.call(target, rows);
	}
	
	/**
	 * 取消选中所有行
	 */
	function _unselectAll(target, skip) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var rows = grid.data.rows;
		var selectedRows = $.data(target, "datagrid").selectedRows;
		if (!skip && opts.checkOnSelect) {
			_uncheckAll(target, true);
		}
		opts.finder.getTr(target, "", "selected").removeClass(
				"ui-state-active");
		if (opts.idField) {
			for ( var _dc = 0; _dc < rows.length; _dc++) {
				deleteObject(selectedRows, opts.idField, rows[_dc][opts.idField]);
			}
		}
		opts.onUnselectAll.call(target, rows);
	}
	
	/**
	 * 通过行选择框选择一行
	 * @param target grid目标对象
	 * @param ridx 行索引号
	 * @param skip 是否忽略行显示被选中
	 */
	function _checkRow(target, ridx, skip) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var data = grid.data;
		if (!skip && opts.selectOnCheck) {
			_selectRow(target, ridx, true);
		}
		var checkboxes = opts.finder.getTr(target, ridx).find(
				"div.ui-datagrid-cell-check input[type=checkbox]");
		checkboxes._propAttr("checked", true);
		checkboxes = opts.finder.getTr(target, "", "allbody").find(
				"div.ui-datagrid-cell-check input[type=checkbox]:not(:checked)");
		if (!checkboxes.length) {
			var dc = grid.dc;
			var header1 = dc.header1.add(dc.header2);
			header1.find("input[type=checkbox]")._propAttr("checked", true);
		}
		opts.onCheck.call(target, ridx, data.rows[ridx]);
	}
	
	/**
	 * 通过行选择框取消选择一行
	 */
	function _uncheckRow(target, ridx, skip) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var data = grid.data;
		if (!skip && opts.selectOnCheck) {
			_unselectRow(target, ridx, true);
		}
		var ck = opts.finder.getTr(target, ridx).find(
				"div.ui-datagrid-cell-check input[type=checkbox]");
		ck._propAttr("checked", false);
		var dc = grid.dc;
		var header1 = dc.header1.add(dc.header2);
		header1.find("input[type=checkbox]")._propAttr("checked", false);
		opts.onUncheck.call(target, ridx, data.rows[ridx]);
	}
	
	/**
	 * 通过行选择框选中所有行
	 */
	function _checkAll(target, skip) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var data = grid.data;
		if (!skip && opts.selectOnCheck) {
			_selectAll(target, true);
		}
		var ck = opts.finder.getTr(target, "", "allbody").find(
				"div.ui-datagrid-cell-check input[type=checkbox]");
		ck._propAttr("checked", true);
		opts.onCheckAll.call(target, data.rows);
	}
	
	/**
	 * 通过行选择框取消选中所有行
	 */
	function _uncheckAll(target, skip) {
		var grid = $.data(target, "datagrid");
		var opts = grid.options;
		var data = grid.data;
		if (!skip && opts.selectOnCheck) {
			_unselectAll(target, true);
		}
		var ck = opts.finder.getTr(target, "", "allbody").find(
				"div.ui-datagrid-cell-check input[type=checkbox]");
		ck._propAttr("checked", false);
		opts.onUncheckAll.call(target, data.rows);
	}
	
	/**
	 * 获得当前编辑行索引
	 */
	function _getEditRowIndex(target){
		var opts = $.data(target, "datagrid").options;
		return opts.lastEditIndex;
	}
	
	/**
	 * 开始编辑一行
	 * @param target grid目标对象
	 * @param ridx 行索引号
	 */
	function _beginEdit(target, ridx) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, ridx);
		var row = opts.finder.getRow(target, ridx);
		if (tr.hasClass("ui-datagrid-row-editing")) {
			return;
		}
		if (opts.onBeforeEdit.call(target, ridx, row) == false) {
			return;
		}
		tr.addClass("ui-datagrid-row-editing");
		_startEditState(target, ridx);
		_fixEditableColumnSize(target);
		
		// 给编辑行元素赋值
		tr.find("div.ui-datagrid-editable").each(function() {
			var _fc = $(this).parent().attr("field");
			var ed = $.data(this, "datagrid.editor");
			ed.actions.setValue(ed.target, row[_fc]);
		});
		_validateRow(target, ridx);
	}
	
	/**
	 * 结束编辑一行
	 * @param target grid目标对象
	 * @param ridx 行索引号
	 * @param cancel 是否取消编辑结果
	 */
	function _endEdit(target, ridx, cancel) {
		var opts = $.data(target, "datagrid").options;
		var updatedRows = $.data(target, "datagrid").updatedRows;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		var tr = opts.finder.getTr(target, ridx);
		var row = opts.finder.getRow(target, ridx);
		if (!tr.hasClass("ui-datagrid-row-editing")) {
			return;
		}
		if (!cancel) {
			if (!_validateRow(target, ridx)) {
				return;
			}
			var march = false;
			var param = {};
			tr.find("div.ui-datagrid-editable").each(function() {
				var field = $(this).parent().attr("field");
				var ed = $.data(this, "datagrid.editor");
				var val = ed.actions.getValue(ed.target);
				if (row[field] != val) {
					row[field] = val;
					march = true;
					param[field] = val;
				}
			});
			if (march) {
				if (getIndex(insertedRows, row) == -1) {
					if (getIndex(updatedRows, row) == -1) {
						updatedRows.push(row);
					}
				}
			}
		}
		tr.removeClass("ui-datagrid-row-editing");
		_endEditState(target, ridx);
		$(target).datagrid("refreshRow", ridx);
		if (!cancel) {
			opts.onAfterEdit.call(target, ridx, row, param);
		} else {
			opts.onCancelEdit.call(target, ridx, row);
		}
	}
	
	/**
	 * 获得指定行的可编辑对象
	 */
	function _getEditors(target, index) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, index);
		var editors = [];
		tr.children("td").each(function() {
			var cell = $(this).find("div.ui-datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				editors.push(ed);
			}
		});
		return editors;
	}
	
	/**
	 * 获得指定行的指定可编辑对象
	 */
	function _getEditor(target, ed) {
		var editors = _getEditors(target, ed.index);
		for ( var i = 0; i < editors.length; i++) {
			if (editors[i].field == ed.field) {
				return editors[i];
			}
		}
		return null;
	}
	
	/**
	 * 开始编辑状态的必要设置
	 */
	function _startEditState(target, ridx) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, ridx);
		tr.children("td").each(
			function() {
				var cell = $(this).find("div.ui-datagrid-cell");
				var field = $(this).attr("field");
				var col = _getColumnOption(target, field);
				if (col && col.editor) {
					var editorType, editorOpts;
					if (typeof col.editor == "string") {
						editorType = col.editor;
					} else {
						editorType = col.editor.type;
						editorOpts = col.editor.options;
					}
					var actions = opts.editors[editorType];
					if (actions) {
						var oldHtml = cell.html();
						var width = cell._outerWidth();
						cell.addClass("ui-datagrid-editable");
						cell._outerWidth(width);
						cell.html("<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>");
						var table = cell.children("table");
						table.attr("align", col.align);
						table.find("td").css("text-align", col.align);
						table.bind(
								"click dblclick contextmenu",
								function(e) {
									e.stopPropagation();
								});
						$.data(cell[0], "datagrid.editor", {
							actions : actions,
							target : actions.init(table.find("td"), editorOpts),
							field : field,
							type : editorType,
							oldHtml : oldHtml
						});
					}
				}
			});
		_fixRowHeight(target, ridx, true);
	}
	
	/**
	 * 结束编辑状态的必要设置
	 */
	function _endEditState(target, ridx) {
		var opts = $.data(target, "datagrid").options;
		var tr = opts.finder.getTr(target, ridx);
		tr.children("td").each(function() {
			var cell = $(this).find("div.ui-datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				if (ed.actions.destroy) {
					ed.actions.destroy(ed.target);
				}
				cell.html(ed.oldHtml);
				$.removeData(cell[0], "datagrid.editor");
				cell.removeClass("ui-datagrid-editable");
				cell.css("width", "");
			}
		});
	}
	
	/**
	 * 校验一行的数据
	 */
	function _validateRow(target, ridx) {
		var tr = $.data(target, "datagrid").options.finder.getTr(target, ridx);
		if (!tr.hasClass("ui-datagrid-row-editing")) {
			return true;
		}
		var vbox = tr.find(".ui-validatebox-text");
		vbox.validatebox("validate");
		vbox.trigger("mouseleave");
		var invalid = tr.find(".ui-validatebox-invalid");
		return invalid.length == 0;
	}
	
	/**
	 * 获得发生改变的行
	 * @param type 改变类型，如果为空表示全部改变
	 */
	function _getChanges(target, type) {
		var insertedRows = $.data(target, "datagrid").insertedRows;
		var deletedRows = $.data(target, "datagrid").deletedRows;
		var updatedRows = $.data(target, "datagrid").updatedRows;
		if (!type) {
			var rows = [];
			rows = rows.concat(insertedRows);
			rows = rows.concat(deletedRows);
			rows = rows.concat(updatedRows);
			return rows;
		} else {
			if (type == "inserted") {
				return insertedRows;
			} else if (type == "deleted") {
				return deletedRows;
			} else if (type == "updated") {
				return updatedRows;
			}
		}
		return [];
	}
	
	/**
	 * 删除一行
	 */
	function _deleteRow(target, index) {
		var opts = $.data(target, "datagrid").options;
		var data = $.data(target, "datagrid").data;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		var deletedRows = $.data(target, "datagrid").deletedRows;
		var selectedRows = $.data(target, "datagrid").selectedRows;
		$(target).datagrid("cancelEdit", index);
		var row = data.rows[index];
		if (getIndex(insertedRows, row) >= 0) {
			deleteObject(insertedRows, row);
		} else {
			deletedRows.push(row);
		}
		deleteObject(selectedRows, opts.idField, data.rows[index][opts.idField]);
		opts.view.deleteRow.call(opts.view, target, index);
		if (opts.height == "auto") {
			_fixRowHeight(target);
		}
		$(target).datagrid("getPager").pagination("refresh", {
			total : data.total
		});
	}
	
	/**
	 * 插入一行
	 */
	function _insertRow(target, row) {
		var data = $.data(target, "datagrid").data;
		var view = $.data(target, "datagrid").options.view;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		view.insertRow.call(view, target, row.index, row.row);
		insertedRows.push(row.row);
		$(target).datagrid("getPager").pagination("refresh", {
			total : data.total
		});
	}
	
	/**
	 * 在最后添加一行
	 */
	function _appendRow(target, row) {
		var data = $.data(target, "datagrid").data;
		var view = $.data(target, "datagrid").options.view;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		view.insertRow.call(view, target, null, row);
		insertedRows.push(row);
		$(target).datagrid("getPager").pagination("refresh", {
			total : data.total
		});
	}
	
	/**
	 * 初始化数据设置
	 */
	function _initData(target) {
		var data = $.data(target, "datagrid").data;
		var rows = data.rows;
		var originalRows = [];
		if(rows){
			for ( var i = 0; i < rows.length; i++) {
				originalRows.push($.extend( {}, rows[i]));
			}
		}
		$.data(target, "datagrid").originalRows = originalRows;
		$.data(target, "datagrid").updatedRows = [];
		$.data(target, "datagrid").insertedRows = [];
		$.data(target, "datagrid").deletedRows = [];
	}
	
	/**
	 * 判断是否接受改变
	 */
	function _acceptChanges(target) {
		var data = $.data(target, "datagrid").data;
		var ok = true;
		for ( var i = 0, len = data.rows.length; i < len; i++) {
			if (_validateRow(target, i)) {
				_endEdit(target, i, false);
			} else {
				ok = false;
			}
		}
		if (ok) {
			_initData(target);
		}
	}
	
	/**
	 * 拒绝所有改变，恢复初始数据
	 */
	function _rejectChanges(target) {
		var opts = $.data(target, "datagrid").options;
		var originalRows = $.data(target, "datagrid").originalRows;
		var insertedRows = $.data(target, "datagrid").insertedRows;
		var deletedRows = $.data(target, "datagrid").deletedRows;
		var selectedRows = $.data(target, "datagrid").selectedRows;
		var data = $.data(target, "datagrid").data;
		for ( var i = 0; i < data.rows.length; i++) {
			_endEdit(target, i, true);
		}
		var ids = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			ids.push(selectedRows[i][opts.idField]);
		}
		selectedRows.splice(0, selectedRows.length);
		data.total += deletedRows.length - insertedRows.length;
		data.rows = originalRows;
		_loadData(target, data);
		for ( var i = 0; i < ids.length; i++) {
			_selectRecord(target, ids[i]);
		}
		_initData(target);
	}
	
	/**
	 * 请求数据，分别可以从远端和本地加载数据
	 */
	function request(target, param) {
		var opts = $.data(target, "datagrid").options;
		if (param) {
			opts.queryParams = param;
		}
		var p = $.extend( {}, opts.queryParams);
		if (opts.pagination) {
			$.extend(p, {
				page : opts.pageNumber,
				rows : opts.pageSize
			});
		}
		if (opts.sortName) {
			$.extend(p, {
				sort : opts.sortName,
				order : opts.sortOrder
			});
		}
		if (opts.onBeforeLoad.call(target, p) == false) {
			return;
		}
		
		// 远程加载数据
		if (opts.mode == "remote") {
			$(target).datagrid("loading");
			loader();
		}
		// 本地加载数据
		else{
			_loadData(target, opts.data);
			$(target).datagrid("loaded");
			_initData(target);
		}
		
		
		
		function loader() {
			var loaded = opts.loader.call(target, p, 
				function(data) {
					_loadData(target, data);
					$(target).datagrid("loaded");
					_initData(target);
				}, 
				function() {
					opts.onLoadError.apply(target, arguments);
					$(target).datagrid("loaded");
				});
			if (loaded == false) {
				$(target).datagrid("loaded");
			}
		}
	}
	
	/**
	 * 合并Cell
	 */
	function _mergeCells(target, param) {
		var opts = $.data(target, "datagrid").options;
		var rows = $.data(target, "datagrid").data.rows;
		param.rowspan = param.rowspan || 1;
		param.colspan = param.colspan || 1;
		if (param.index < 0 || param.index >= rows.length) {
			return;
		}
		if (param.rowspan == 1 && param.colspan == 1) {
			return;
		}
		var field = rows[param.index][param.field];
		var tr = opts.finder.getTr(target, param.index);
		var td = tr.find("td[field=\"" + param.field + "\"]");
		td.attr("rowspan", param.rowspan).attr("colspan", param.colspan);
		td.addClass("ui-datagrid-td-merged");
		for ( var i = 1; i < param.colspan; i++) {
			td = td.next();
			td.hide();
			rows[param.index][td.attr("field")] = field;
		}
		for ( var i = 1; i < param.rowspan; i++) {
			tr = tr.next();
			var td = tr.find("td[field=\"" + param.field + "\"]").hide();
			rows[param.index + i][td.attr("field")] = field;
			for ( var j = 1; j < param.colspan; j++) {
				td = td.next();
				td.hide();
				rows[param.index + i][td.attr("field")] = field;
			}
		}
		_fixMergedColumnSize(target);
	}
	
	/**
	 * 构造jQuery扩展方法datagrid的入口
	 */
	$.fn.datagrid = function(options, param) {
		if (typeof options == "string") {
			return $.fn.datagrid.methods[options](this, param);
		}
		options = options || {};

		return this.each(function() {
			var state = $.data(this, "datagrid");
			var opts;
			if (state) {
				opts = $.extend(state.options, options);
				state.options = opts;
			} else {
				opts = $.extend( {}, $.extend( {}, $.fn.datagrid.defaults, {
					queryParams : {}
				}), $.fn.datagrid.parseOptions(this), options);
				$(this).css("width", "").css("height", "");

				var wrapResult = wrapGrid(this, opts.rownumbers);
				if (!opts.columns) {
					opts.columns = wrapResult.columns;
				}
				if (!opts.frozenColumns) {
					opts.frozenColumns = wrapResult.frozenColumns;
				}
				opts.columns = $.extend(true, [], opts.columns);
				opts.frozenColumns = $.extend(true, [], opts.frozenColumns);
				$.data(this, "datagrid", {
					options : opts,
					panel : wrapResult.panel,
					dc : wrapResult.dc,
					selectedRows : [],
					data : {
						total : 0,
						rows : []
					},
					originalRows : [],
					updatedRows : [],
					insertedRows : [],
					deletedRows : []
				});
				$.data(this, "componentType", "datagrid");
			}
			initGrid(this);
			if (!state) {
				var data = wrapData(this);
				if (data.total > 0) {
					_loadData(this, data);
					_initData(this);
				}
			}
			_resize(this);
			request(this);
			initEvents(this);
			
			if(opts.disabled){
				_disabled(this);
			}
		});
	};
	
	// TODO grid中的cell类型（可扩展）
	var editors = {
		text : {
			init : function(target, opts) {
				var ed = $("<input type=\"text\" class=\"ui-datagrid-editable-input\">").appendTo(target);
				return ed;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		textarea : {
			init : function(target, opts) {
				var ed = $("<textarea class=\"ui-datagrid-editable-input\"></textarea>").appendTo(target);
				return ed;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		checkbox : {
			init : function(target, opts) {
				var ed = $("<input type=\"checkbox\">").appendTo(target);
				ed.val(opts.on);
				ed.attr("offval", opts.off);
				return ed;
			},
			getValue : function(target) {
				if ($(target).is(":checked")) {
					return $(target).val();
				} else {
					return $(target).attr("offval");
				}
			},
			setValue : function(target, val) {
				var v = false;
				if ($(target).val() == val) {
					v = true;
				}
				$(target)._propAttr("checked", v);
			},
			convertValue : function(opts, val) {
				if(opts == undefined || val == undefined){
					return val;
				}
				
				if(val == true || val == "true" 
					|| val == "on" || val == "Y" || val == "yes"){
					return opts.on;
				} else {
					return opts.off;
				}
			},
			getConvertValue : function(opts, val) {
				if(opts == undefined || val == undefined){
					return val;
				}
				
				if(val == opts.on){
					return true;
				} else if(val == opts.off){
					return false;
				}
				
				return val;
			}
		},
		numberbox : {
			init : function(target, opts) {
				var ed = $("<input type=\"text\" class=\"ui-datagrid-editable-input\">").appendTo(target);
				ed.numberbox(opts);
				return ed;
			},
			destroy : function(target) {
				$(target).numberbox("destroy");
			},
			getValue : function(target) {
				return $(target).numberbox("getValue");
			},
			setValue : function(target, val) {
				$(target).numberbox("setValue", val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		validatebox : {
			init : function(target, opts) {
				var ed = $("<input type=\"text\" class=\"ui-datagrid-editable-input\">").appendTo(target);
				ed.validatebox(opts);
				return ed;
			},
			destroy : function(target) {
				$(target).validatebox("destroy");
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, val) {
				$(target).val(val);
			},
			resize : function(target, width) {
				$(target)._outerWidth(width);
			}
		},
		datebox : {
			init : function(target, opts) {
				var ed = $("<input type=\"text\">").appendTo(target);
				ed.datebox(opts);
				return ed;
			},
			destroy : function(target) {
				$(target).datebox("destroy");
			},
			getValue : function(target) {
				return $(target).datebox("getValue");
			},
			setValue : function(target, val) {
				$(target).datebox("setValue", val);
			},
			resize : function(target, size) {
				$(target).datebox("resize", size);
			}
		},
		combobox : {
			init : function(target, opts) {
				var ed = $("<input type=\"text\">").appendTo(target);
				ed.combobox(opts || {});
				return ed;
			},
			destroy : function(target) {
				$(target).combobox("destroy");
			},
			getValue : function(target) {
				return $(target).combobox("getValue");
			},
			setValue : function(target, val) {
				$(target).combobox("setValue", val);
			},
			resize : function(target, size) {
				$(target).combobox("resize", size);
			}
		},
		combotree : {
			init : function(target, opts) {
				var ed = $("<input type=\"text\">").appendTo(target);
				ed.combotree(opts);
				return ed;
			},
			destroy : function(target) {
				$(target).combotree("destroy");
			},
			getValue : function(target) {
				return $(target).combotree("getValue");
			},
			setValue : function(target, val) {
				$(target).combotree("setValue", val);
			},
			resize : function(target, size) {
				$(target).combotree("resize", size);
			}
		}
	};
	
	/**
	 * 方法
	 */
	$.fn.datagrid.methods = {
		/**
		 * 获得参数
		 */
		options : function(jq) {
			var gridOpts = $.data(jq[0], "datagrid").options;
			var panelOpts = $.data(jq[0], "datagrid").panel.panel("options");
			var opts = $.extend(gridOpts, {
				width : panelOpts.width,
				height : panelOpts.height,
				closed : panelOpts.closed,
				collapsed : panelOpts.collapsed,
				minimized : panelOpts.minimized,
				maximized : panelOpts.maximized
			});
			return opts;
		},
		setOptions : function(jq, opts){
			var options = $.data(jq[0], "datagrid").options;
			options = $.extend(options, opts);
		},
		getPanel : function(jq) {
			return $.data(jq[0], "datagrid").panel;
		},
		getPager : function(jq) {
			return $.data(jq[0], "datagrid").panel
					.children("div.ui-datagrid-pager");
		},
		getColumnFields : function(jq, colType) {
			return _getColumnFields(jq[0], colType);
		},
		getColumnOption : function(jq, field) {
			return _getColumnOption(jq[0], field);
		},
		resize : function(jq, size) {
			return jq.each(function() {
				_resize(this, size);
			});
		},
		load : function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid("options");
				opts.pageNumber = 1;
				var pager = $(this).datagrid("getPager");
				pager.pagination( {
					pageNumber : 1
				});
				request(this, param);
			});
		},
		reload : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				var originalData = $.data(this, "datagrid").originalData;
				opts.data = Flywet.deepClone(originalData);
				request(this, param);
			});
		},
		reloadFooter : function(jq, footer) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				var dc = $.data(this, "datagrid").dc;
				if (footer) {
					$.data(this, "datagrid").footer = footer;
				}
				if (opts.showFooter) {
					opts.view.renderFooter.call(opts.view, this, dc.footer2,
							false);
					opts.view.renderFooter.call(opts.view, this, dc.footer1,
							true);
					if (opts.view.onAfterRender) {
						opts.view.onAfterRender.call(opts.view, this);
					}
					$(this).datagrid("fixRowHeight");
				}
			});
		},
		loading : function(jq) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				if ($(this).datagrid("getPager").pagination)
					$(this).datagrid("getPager").pagination("loading");
				if (opts.loadMsg) {
					var panel = $(this).datagrid("getPanel");
					$("<div class=\"ui-datagrid-mask\" style=\"display:block\"></div>")
						.appendTo(panel);
					var msg = $("<div class=\"ui-datagrid-mask-msg\" style=\"display:block\"></div>")
						.html(opts.loadMsg).appendTo(panel);
					msg.css("left", (panel.width() - msg._outerWidth()) / 2);
				}
			});
		},
		disabled : function(jq) {
			return jq.each(function() {
				_disabled(this);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				_enable(this);
			});
		},
		loaded : function(jq) {
			return jq.each(function() {
				if ($(this).datagrid("getPager").pagination)
					$(this).datagrid("getPager").pagination("loaded");
				var panel = $(this).datagrid("getPanel");
				panel.children("div.ui-datagrid-mask-msg").remove();
				panel.children("div.ui-datagrid-mask").remove();
			});
		},
		fitColumns : function(jq) {
			return jq.each(function() {
				_fitColumns(this);
			});
		},
		fixColumnSize : function(jq, cell) {
			return jq.each(function() {
				_fixColumnSize(this, cell);
			});
		},
		fixRowHeight : function(jq, ridx) {
			return jq.each(function() {
				_fixRowHeight(this, ridx);
			});
		},
		autoSizeColumn : function(jq, field) {
			return jq.each(function() {
				_autoSizeColumn(this, field);
			});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				_loadData(this, data);
				_initData(this);
			});
		},
		getData : function(jq) {
			return $.data(jq[0], "datagrid").data;
		},
		getRows : function(jq) {
			return $.data(jq[0], "datagrid").data.rows;
		},
		getConvertRows : function(jq, rows){
			return _getConvertRows(jq[0], rows);
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], "datagrid").footer;
		},
		getRowIndex : function(jq, id) {
			return _getRowIndex(jq[0], id);
		},
		getChecked : function(jq) {
			var rr = [];
			var rows = jq.datagrid("getRows");
			var dc = $.data(jq[0], "datagrid").dc;
			dc.view.find("div.ui-datagrid-cell-check input:checked").each(
				function() {
					var ridx = $(this).parents("tr.ui-datagrid-row:first")
							.attr("datagrid-row-index");
					rr.push(rows[ridx]);
				});
			return rr;
		},
		getSelected : function(jq) {
			var rows = _getSelections(jq[0]);
			return rows.length > 0 ? rows[0] : null;
		},
		getSelections : function(jq) {
			return _getSelections(jq[0]);
		},
		clearSelections : function(jq) {
			return jq.each(function() {
				var selectedRows = $.data(this, "datagrid").selectedRows;
				selectedRows.splice(0, selectedRows.length);
				_unselectAll(this);
			});
		},
		selectAll : function(jq) {
			return jq.each(function() {
				_selectAll(this);
			});
		},
		unselectAll : function(jq) {
			return jq.each(function() {
				_unselectAll(this);
			});
		},
		selectRow : function(jq, index) {
			return jq.each(function() {
				_selectRow(this, index);
			});
		},
		selectRecord : function(jq, id) {
			return jq.each(function() {
				_selectRecord(this, id);
			});
		},
		unselectRow : function(jq, index) {
			return jq.each(function() {
				_unselectRow(this, index);
			});
		},
		checkRow : function(jq, index) {
			return jq.each(function() {
				_checkRow(this, index);
			});
		},
		uncheckRow : function(jq, index) {
			return jq.each(function() {
				_uncheckRow(this, index);
			});
		},
		checkAll : function(jq) {
			return jq.each(function() {
				_checkAll(this);
			});
		},
		uncheckAll : function(jq) {
			return jq.each(function() {
				_uncheckAll(this);
			});
		},
		beginEdit : function(jq, index) {
			return jq.each(function() {
				_beginEdit(this, index);
			});
		},
		endEdit : function(jq, index) {
			return jq.each(function() {
				_endEdit(this, index, false);
			});
		},
		getEditRowIndex : function(jq){
			return _getEditRowIndex(jq[0]);
		},
		cancelEdit : function(jq, index) {
			return jq.each(function() {
				_endEdit(this, index, true);
			});
		},
		getEditors : function(jq, index) {
			return _getEditors(jq[0], index);
		},
		getEditor : function(jq, ed) {
			return _getEditor(jq[0], ed);
		},
		refreshRow : function(jq, data) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				opts.view.refreshRow.call(opts.view, this, data);
			});
		},
		validateRow : function(jq, index) {
			return _validateRow(jq[0], index);
		},
		updateRow : function(jq, data) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				opts.view.updateRow.call(opts.view, this, data.index, data.row);
			});
		},
		appendRow : function(jq, row) {
			return jq.each(function() {
				_appendRow(this, row);
			});
		},
		insertRow : function(jq, row) {
			return jq.each(function() {
				_insertRow(this, row);
			});
		},
		deleteRow : function(jq, index) {
			return jq.each(function() {
				_deleteRow(this, index);
			});
		},
		getChanges : function(jq, type) {
			return _getChanges(jq[0], type);
		},
		acceptChanges : function(jq) {
			return jq.each(function() {
				_acceptChanges(this);
			});
		},
		rejectChanges : function(jq) {
			return jq.each(function() {
				_rejectChanges(this);
			});
		},
		mergeCells : function(jq, param) {
			return jq.each(function() {
				_mergeCells(this, param);
			});
		},
		showColumn : function(jq, field) {
			return jq.each(function() {
				var panel = $(this).datagrid("getPanel");
				panel.find("td[field=\"" + field + "\"]").show();
				$(this).datagrid("getColumnOption", field).hidden = false;
				$(this).datagrid("fitColumns");
			});
		},
		hideColumn : function(jq, field) {
			return jq.each(function() {
				var panel = $(this).datagrid("getPanel");
				panel.find("td[field=\"" + field + "\"]").hide();
				$(this).datagrid("getColumnOption", field).hidden = true;
				$(this).datagrid("fitColumns");
			});
		}
	};
	
	/**
	 * 参数解析器
	 */
	$.fn.datagrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.panel.parseOptions(target), 
			Flywet.parseOptions(target, 
				[ "url", "toolbar", "idField", "sortName", "mode",
					"sortOrder", "pagePosition", {
						fitColumns : "boolean",
						autoRowHeight : "boolean",
						striped : "boolean",
						nowrap : "boolean"
					}, {
						rownumbers : "boolean",
						singleSelect : "boolean",
						checkOnSelect : "boolean",
						selectOnCheck : "boolean"
					}, {
						pagination : "boolean",
						pageSize : "number",
						pageNumber : "number"
					}, {
						remoteSort : "boolean",
						showHeader : "boolean",
						showFooter : "boolean"
					}, {
						scrollbarSize : "number"
					} 
				]), 
				{
					pageList : (t.attr("pageList") ? eval(t.attr("pageList")) : undefined),
					loadMsg : (t.attr("loadMsg") != undefined ? t.attr("loadMsg") : undefined),
					rowStyler : (t.attr("rowStyler") ? eval(t.attr("rowStyler")) : undefined)
				});
	};
	
	/**
	 * 视图渲染器
	 */
	var view = {
		render : function(target, body, fromFrozenColumns) {
			var grid = $.data(target, "datagrid");
			var opts = grid.options;
			var rows = grid.data.rows;
			var columns = $(target).datagrid("getColumnFields", fromFrozenColumns);
			if (fromFrozenColumns) {
				if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
					return;
				}
			}
			var tb = [ "<table class=\"ui-datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
			for ( var i = 0; i < rows.length; i++) {
				var cls = (i % 2 && opts.striped) ? "class=\"ui-datagrid-row ui-datagrid-row-alt\""
						: "class=\"ui-datagrid-row\"";
				var rowStyler = opts.rowStyler ? opts.rowStyler.call(target, i,
						rows[i]) : "";
				var style = rowStyler ? "style=\"" + rowStyler + "\"" : "";
				var rowIdPrefix = grid.rowIdPrefix + "-" + (fromFrozenColumns ? 1 : 2) + "-" + i;
				tb.push("<tr id=\"" + rowIdPrefix + "\" datagrid-row-index=\"" + i
						+ "\" " + cls + " " + style + ">");
				tb.push(this.renderRow.call(this, target, columns, fromFrozenColumns, i,
						rows[i]));
				tb.push("</tr>");
			}
			tb.push("</tbody></table>");
			$(body).html(tb.join(""));
		},
		renderFooter : function(target, footer, fromFrozenColumns) {
			var opts = $.data(target, "datagrid").options;
			var rows = $.data(target, "datagrid").footer || [];
			var columns = $(target).datagrid("getColumnFields", fromFrozenColumns);
			var tb = [ "<table class=\"ui-datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
			for ( var i = 0; i < rows.length; i++) {
				tb.push("<tr class=\"ui-datagrid-row\" datagrid-row-index=\""
						+ i + "\">");
				tb.push(this.renderRow.call(this, target, columns, fromFrozenColumns, i,
						rows[i]));
				tb.push("</tr>");
			}
			tb.push("</tbody></table>");
			$(footer).html(tb.join(""));
		},
		/**
		 * 渲染一行记录
		 */
		renderRow : function(target, columns, fromFrozenColumns, ridx, row) {
			var opts = $.data(target, "datagrid").options;
			var cc = [];
			if (fromFrozenColumns && opts.rownumbers) {
				var rownumber = ridx + 1;
				if (opts.pagination) {
					rownumber += (opts.pageNumber - 1) * opts.pageSize;
				}
				cc.push("<td class=\"ui-datagrid-td-rownumber\"><div class=\"ui-datagrid-cell-rownumber\">" + rownumber + "</div></td>");
			}
			for ( var i = 0; i < columns.length; i++) {
				var column = columns[i];
				var col = $(target).datagrid("getColumnOption", column);
				if (col) {
					var val = row[column];
					var styler = col.styler ? (col.styler(val, row, ridx) || "") : "";
					var style = col.hidden ? "style=\"display:none;" + styler
							+ "\"" : (styler ? "style=\"" + styler + "\"" : "");
					cc.push("<td field=\"" + column + "\" " + style + ">");
					var styleDiv = "";
					if (col.checkbox) {
					} else {
						styleDiv += "text-align:" + (col.align || "left") + ";";
						if (!opts.nowrap) {
							styleDiv += "white-space:normal;height:auto;";
						} else {
							if (opts.autoRowHeight) {
								styleDiv += "height:auto;";
							}
						}
					}
					cc.push("<div style=\"" + styleDiv + "\" ");
					if (col.checkbox) {
						cc.push("class=\"ui-datagrid-cell-check ");
					} else {
						cc.push("class=\"ui-datagrid-cell " + col.cellClass);
					}
					cc.push("\">");
					if (col.checkbox) {
						cc.push("<input type=\"checkbox\" name=\"" + column
								+ "\" value=\""
								+ (val != undefined ? val : "") + "\"/>");
					} else {
						if (col.formatter) {
							cc.push(col.formatter(val, row, ridx));
						} else {
							cc.push(val);
						}
					}
					cc.push("</div>");
					cc.push("</td>");
				}
			}
			return cc.join("");
		},
		refreshRow : function(target, ridx) {
			this.updateRow.call(this, target, ridx, {});
		},
		updateRow : function(target, ridx, row) {
			var opts = $.data(target, "datagrid").options;
			var rows = $(target).datagrid("getRows");
			$.extend(rows[ridx], row);
			var style = opts.rowStyler ? opts.rowStyler.call(target, ridx, rows[ridx]) : "";
			
			function renderColumns(fromFrozenColumns) {
				var columns = $(target).datagrid("getColumnFields", fromFrozenColumns);
				var tr = opts.finder.getTr(target, ridx, "body", (fromFrozenColumns ? 1 : 2));
				var ck = tr.find(
						"div.ui-datagrid-cell-check input[type=checkbox]").is(
						":checked");
				tr.html(this.renderRow.call(this, target, columns, fromFrozenColumns, ridx,
						rows[ridx]));
				tr.attr("style", style || "");
				if (ck) {
					tr.find("div.ui-datagrid-cell-check input[type=checkbox]")._propAttr("checked", true);
				}
			}
			
			renderColumns.call(this, true);
			renderColumns.call(this, false);
			$(target).datagrid("fixRowHeight", ridx);
		},
		insertRow : function(target, ridx, row) {
			var grid = $.data(target, "datagrid");
			var opts = grid.options;
			var dc = grid.dc;
			var data = grid.data;
			if (ridx == undefined || ridx == null) {
				ridx = data.rows.length;
			}
			if (ridx > data.rows.length) {
				ridx = data.rows.length;
			}
			
			function insertCell(fromFrozenColumns) {
				var bodyType = fromFrozenColumns ? 1 : 2;
				for ( var i = data.rows.length - 1; i >= ridx; i--) {
					var tr = opts.finder.getTr(target, i, "body", bodyType);
					tr.attr("datagrid-row-index", i + 1);
					tr.attr("id", grid.rowIdPrefix + "-" + bodyType + "-" + (i + 1));
					if (fromFrozenColumns && opts.rownumbers) {
						tr.find("div.ui-datagrid-cell-rownumber").html(i + 2);
					}
				}
			}
			
			function insertBody(fromFrozenColumns) {
				var bodyType = fromFrozenColumns ? 1 : 2;
				var columns = $(target).datagrid("getColumnFields", fromFrozenColumns);
				var id = grid.rowIdPrefix + "-" + bodyType + "-" + ridx;
				var tr = "<tr id=\"" + id + "\" class=\"ui-datagrid-row\" datagrid-row-index=\"" + ridx + "\"></tr>";
				if (ridx >= data.rows.length) {
					if (data.rows.length) {
						opts.finder.getTr(target, "", "last", bodyType).after(tr);
					} else {
						var cc = fromFrozenColumns ? dc.body1 : dc.body2;
						cc.html("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" + tr + "</tbody></table>");
					}
				} else {
					opts.finder.getTr(target, ridx + 1, "body", bodyType).before(tr);
				}
			}
			
			insertCell.call(this, true);
			insertCell.call(this, false);
			insertBody.call(this, true);
			insertBody.call(this, false);
			data.total += 1;
			data.rows.splice(ridx, 0, row);
			this.refreshRow.call(this, target, ridx);
		},
		deleteRow : function(target, ridx) {
			var grid = $.data(target, "datagrid");
			var opts = grid.options;
			var data = grid.data;
			
			function _deleteRow(fromFrozenColumns) {
				var bodyType = fromFrozenColumns ? 1 : 2;
				for ( var i = ridx + 1; i < data.rows.length; i++) {
					var tr = opts.finder.getTr(target, i, "body", bodyType);
					tr.attr("datagrid-row-index", i - 1);
					tr.attr("id", grid.rowIdPrefix + "-" + bodyType + "-" + (i - 1));
					if (fromFrozenColumns && opts.rownumbers) {
						tr.find("div.ui-datagrid-cell-rownumber").html(i);
					}
				}
			}
			
			opts.finder.getTr(target, ridx).remove();
			_deleteRow.call(this, true);
			_deleteRow.call(this, false);
			data.total -= 1;
			data.rows.splice(ridx, 1);
		},
		onBeforeRender : function(target, rows) {
		},
		onAfterRender : function(target) {
			var opts = $.data(target, "datagrid").options;
			if (opts.showFooter) {
				var footer = $(target).datagrid("getPanel").find("div.ui-datagrid-footer");
				footer.find("div.ui-datagrid-cell-rownumber,div.ui-datagrid-cell-check")
					.css("visibility", "hidden");
			}
		}
	};
	
	$.fn.datagrid.defaults = $.extend({}, $.fn.panel.defaults, {
		frozenColumns : undefined,
		columns : undefined,
		fitColumns : false,
		autoRowHeight : true,
		toolbar : null,
		striped : false,
		mode : "local",
		method : "post",
		data : {"total":0,"rows":[]},
		nowrap : true,
		disabled : false,
		idField : null,
		url : null,
		loadMsg : "Processing, please wait ...",
		rownumbers : false,
		singleSelect : false,
		beginEditOnClickRow : true,
		selectOnCheck : true,
		checkOnSelect : true,
		selectable : true,
		pagination : false,
		pagePosition : "bottom",
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		queryParams : {},
		sortName : null,
		sortOrder : "asc",
		remoteSort : true,
		showHeader : true,
		showFooter : false,
		scrollbarSize : 18,
		rowStyler : function(index, row) { },
		loader : function(data, onsuccess, onerror) {
			var opts = $(this).datagrid("options");
			if (!opts.url) {
				return false;
			}
			Flywet.ab({
				type : opts.method,
				url : opts.url,
				params : data,
				dataType : "json",
				onsuccess : onsuccess,
				onerror : onerror
			});
		},
		loadFilter : function(target, data) {
			var opts = $(target).datagrid("options");
			var newdata = [];
			// 如果data是数组，进行组装
			if (typeof data.length == "number"
					&& typeof data.splice == "function") {
				for(var i=0;i<data.length;i++){
					if(opts.dataFilter(i, data[i])){
						newdata.push(data[i]);
					}
				}
				return {
					total : newdata.length,
					rows : newdata
				};
			} else {
				if(data.rows){
					for(var i=0;i<data.rows.length;i++){
						if(opts.dataFilter(i, data.rows[i])){
							newdata.push(data.rows[i]);
						}
					}
					data.rows = newdata;
				}
				return data;
			}
		},
		// 用于过滤数据
		dataFilter : function(index, row) {return true;},
		editors : editors,
		finder : {
			getTr : function(target, ridx, type, bodyType) {
				type = type || "body";
				bodyType = bodyType || 0;
				var grid = $.data(target, "datagrid");
				var dc = grid.dc;
				var opts = grid.options;
				if (bodyType == 0) {
					var tr1 = opts.finder.getTr(target, ridx, type, 1);
					var tr2 = opts.finder.getTr(target, ridx, type, 2);
					return tr1.add(tr2);
				} else {
					if (type == "body") {
						var tr = $("#" + grid.rowIdPrefix + "-" + bodyType + "-" + ridx);
						if (!tr.length) {
							tr = (bodyType == 1 ? dc.body1
									: dc.body2)
									.find(">table>tbody>tr[datagrid-row-index="
											+ ridx + "]");
						}
						return tr;
					} else {
						if (type == "footer") {
							return (bodyType == 1 ? dc.footer1
									: dc.footer2)
									.find(">table>tbody>tr[datagrid-row-index="
											+ ridx + "]");
						} else {
							if (type == "selected") {
								return (bodyType == 1 ? dc.body1
										: dc.body2)
										.find(">table>tbody>tr.ui-state-active");
							} else {
								if (type == "last") {
									return (bodyType == 1 ? dc.body1
											: dc.body2)
											.find(">table>tbody>tr:last[datagrid-row-index]");
								} else {
									if (type == "allbody") {
										return (bodyType == 1 ? dc.body1
												: dc.body2)
												.find(">table>tbody>tr[datagrid-row-index]");
									} else {
										if (type == "allfooter") {
											return (bodyType == 1 ? dc.footer1
													: dc.footer2)
													.find(">table>tbody>tr[datagrid-row-index]");
										}
									}
								}
							}
						}
					}
				}
			},
			getRow : function(target, ridx) {
				return $.data(target, "datagrid").data.rows[ridx];
			}
		},
		view : view,
		onBeforeLoad : function(param) {
		},
		onLoadSuccess : function() {
		},
		onLoadError : function() {
		},
		onClickRow : function(target, index) {
		},
		onDblClickRow : function(target, index) {
		},
		onClickCell : function(target, index, field) {
		},
		onDblClickCell : function(target, index, field) {
		},
		onSortColumn : function(sort, sortName) {
		},
		onResizeColumn : function(target, field) {
		},
		onSelect : function(target, index) {
		},
		onUnselect : function(target, index) {
		},
		onSelectAll : function(rows) {
		},
		onUnselectAll : function(rows) {
		},
		onCheck : function(target, index) {
		},
		onUncheck : function(target, index) {
		},
		onCheckAll : function(rows) {
		},
		onUncheckAll : function(rows) {
		},
		onBeforeEdit : function(target, index) {
		},
		onAfterEdit : function(target, index, row) {
		},
		onCancelEdit : function(target, index) {
		},
		onHeaderContextMenu : function(e, field) {
		},
		onRowContextMenu : function(e, field, row) {
		}
	});
})(jQuery);

Flywet.widget.Grid = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.jq.attr('title', cfg.title ? cfg.title : "");
	this.cfg.columns[0] = cfg.singleSelect == false ? 
			[ {checkbox : true} ].concat(this.cfg.columns[0]) 
			: this.cfg.columns[0];
	this.cfg.rownumbers = this.cfg.rownumbers != false ? true : false;
	
	this.datagrid = this.jq.datagrid(this.cfg);
};

Flywet.extend(Flywet.widget.Grid, Flywet.widget.BaseWidget);
