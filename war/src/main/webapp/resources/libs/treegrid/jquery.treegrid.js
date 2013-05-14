(function($) {
	function _1(a, o) {
		for ( var i = 0, _2 = a.length; i < _2; i++) {
			if (a[i] == o) {
				return i;
			}
		}
		return -1;
	}
	
	function _3(a, o) {
		var _4 = _1(a, o);
		if (_4 != -1) {
			a.splice(_4, 1);
		}
	}
	
	function _initTreeGrid(target) {
		var opts = $.data(target, "treegrid").options;
		$(target).datagrid($.extend( {}, opts, {
			url : null,
			loader : function() {
				return false;
			},
			onLoadSuccess : function() {
			},
			onResizeColumn : function(field, width) {
				_fixRowHeight(target);
				opts.onResizeColumn.call(target, field, width);
			},
			onSortColumn : function(sortName, sortOrder) {
				opts.sortName = sortName;
				opts.sortOrder = sortOrder;
				if (opts.remoteSort) {
					_doLoadData(target);
				} else {
					var data = $(target).treegrid("getData");
					_loadData(target, 0, data);
				}
				opts.onSortColumn.call(target, sortName, sortOrder);
			},
			onBeforeEdit : function(_d, id) {
				if (opts.onBeforeEdit.call(target, id) == false) {
					return false;
				}
			},
			onAfterEdit : function(_f, row, data) {
				opts.onAfterEdit.call(target, row, data);
			},
			onCancelEdit : function(_11, row) {
				opts.onCancelEdit.call(target, row);
			},
			onSelect : function(id) {
				opts.onSelect.call(target, _find(target, id));
			},
			onUnselect : function(id) {
				opts.onUnselect.call(target, _find(target, id));
			},
			onSelectAll : function() {
				opts.onSelectAll.call(target, $.data(target, "treegrid").data);
			},
			onUnselectAll : function() {
				opts.onUnselectAll.call(target, $.data(target, "treegrid").data);
			},
			onCheck : function(id) {
				opts.onCheck.call(target, _find(target, id));
			},
			onUncheck : function(id) {
				opts.onUncheck.call(target, _find(target, id));
			},
			onCheckAll : function() {
				opts.onCheckAll.call(target, $.data(target, "treegrid").data);
			},
			onUncheckAll : function() {
				opts.onUncheckAll.call(target, $.data(target, "treegrid").data);
			},
			onClickRow : function(id) {
				opts.onClickRow.call(target, _find(target, id));
			},
			onDblClickRow : function(id) {
				opts.onDblClickRow.call(target, _find(target, id));
			},
			onClickCell : function(id, data) {
				opts.onClickCell.call(target, data, _find(target, id));
			},
			onDblClickCell : function(id, data) {
				opts.onDblClickCell.call(target, data, _find(target, id));
			},
			onRowContextMenu : function(e, id) {
				opts.onContextMenu.call(target, e, _find(target, id));
			}
		}));
		if (opts.pagination) {
			var pager = $(target).datagrid("getPager");
			pager.pagination( {
				pageNumber : opts.pageNumber,
				pageSize : opts.pageSize,
				pageList : opts.pageList,
				onSelectPage : function(pageNumber, pageSize) {
					opts.pageNumber = pageNumber;
					opts.pageSize = pageSize;
					_doLoadData(target);
				}
			});
			opts.pageSize = pager.pagination("options").pageSize;
		}
	}
	
	function _fixRowHeight(target, id) {
		var opts = $.data(target, "datagrid").options;
		var dc = $.data(target, "datagrid").dc;
		if (!dc.body1.is(":empty") && (!opts.nowrap || opts.autoRowHeight)) {
			if (id != undefined) {
				var subNode = _getChildren(target, id);
				for ( var i = 0; i < subNode.length; i++) {
					setHeightTr(subNode[i][opts.idField]);
				}
			}
		}
		$(target).datagrid("fixRowHeight", id);
		
		function setHeightTr(ridx) {
			var tr1 = opts.finder.getTr(target, ridx, "body", 1);
			var tr2 = opts.finder.getTr(target, ridx, "body", 2);
			tr1.css("height", "");
			tr2.css("height", "");
			var h = Math.max(tr1.height(), tr2.height());
			tr1.css("height", h);
			tr2.css("height", h);
		}
	}
	
	function _2a(target) {
		var dc = $.data(target, "datagrid").dc;
		var opts = $.data(target, "treegrid").options;
		if (!opts.rownumbers) {
			return;
		}
		dc.body1.find("div.ui-datagrid-cell-rownumber").each(function(i) {
			$(this).html(i + 1);
		});
	}
	
	/**
	 * 初始化节点的事件
	 */
	function _initNodeEvent(target) {
		var dc = $.data(target, "datagrid").dc;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0], "events") || $._data(body[0], "events")).click[0].handler;
		dc.body1.add(dc.body2).bind(
				"mouseover",
				function(e) {
					var tt = $(e.target);
					var tr = tt.closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					if (tt.hasClass("ui-tree-hit")) {
						tt.hasClass("ui-tree-expanded") ? tt
								.addClass("ui-tree-expanded-hover") : tt
								.addClass("ui-tree-collapsed-hover");
					}
					e.stopPropagation();
				}).bind(
				"mouseout",
				function(e) {
					var tt = $(e.target);
					var tr = tt.closest("tr.ui-datagrid-row");
					if (!tr.length) {
						return;
					}
					if (tt.hasClass("ui-tree-hit")) {
						tt.hasClass("ui-tree-expanded") ? tt
								.removeClass("ui-tree-expanded-hover") : tt
								.removeClass("ui-tree-collapsed-hover");
					}
					e.stopPropagation();
				}).unbind("click").bind("click", function(e) {
			var tt = $(e.target);
			var tr = tt.closest("tr.ui-datagrid-row");
			if (!tr.length) {
				return;
			}
			if (tt.hasClass("ui-tree-hit")) {
				_toggle(target, tr.attr("node-id"));
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	}
	
	function _32(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr1 = opts.finder.getTr(target, id, "body", 1);
		var tr2 = opts.finder.getTr(target, id, "body", 2);
		var tr1cols = $(target).datagrid("getColumnFields", true).length
				+ (opts.rownumbers ? 1 : 0);
		var tr2cols = $(target).datagrid("getColumnFields", false).length;
		createTr(tr1, tr1cols);
		createTr(tr2, tr2cols);
		
		function createTr(tr, cols) {
			$("<tr class=\"ui-treegrid-tr-tree\">"
				+ "<td style=\"border:0px\" colspan=\"" + cols
				+ "\">" + "<div></div>" + "</td>" + "</tr>").insertAfter(tr);
		}
		
	}
	
	/**
	 * 加载数据
	 */
	function _loadData(target, id, data, inc) {
		var opts = $.data(target, "treegrid").options;
		var dc = $.data(target, "datagrid").dc;
		data = opts.loadFilter.call(target, data, id);
		var node = _find(target, id);
		if (node) {
			var tr1 = opts.finder.getTr(target, id, "body", 1);
			var tr2 = opts.finder.getTr(target, id, "body", 2);
			var cc1 = tr1.next("tr.ui-treegrid-tr-tree").children("td").children(
					"div");
			var cc2 = tr2.next("tr.ui-treegrid-tr-tree").children("td").children(
					"div");
		} else {
			var cc1 = dc.body1;
			var cc2 = dc.body2;
		}
		if (!inc) {
			$.data(target, "treegrid").data = [];
			cc1.empty();
			cc2.empty();
		}
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, target, id, data);
		}
		opts.view.render.call(opts.view, target, cc1, true);
		opts.view.render.call(opts.view, target, cc2, false);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, target, dc.footer1, true);
			opts.view.renderFooter.call(opts.view, target, dc.footer2, false);
		}
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, target);
		}
		opts.onLoadSuccess.call(target, node, data);
		if (!id && opts.pagination) {
			var total = $.data(target, "treegrid").total;
			var dg = $(target).datagrid("getPager");
			if (dg.pagination("options").total != total) {
				dg.pagination( {
					total : total
				});
			}
		}
		_fixRowHeight(target);
		_2a(target);
		$(target).treegrid("autoSizeColumn");
	}
	
	/**
	 * 加载节点子数据
	 */
	function _doLoadData(target, id, queryParams, inc, backward) {
		var opts = $.data(target, "treegrid").options;
		var dgBody = $(target).datagrid("getPanel").find("div.ui-datagrid-body");
		if (queryParams) {
			opts.queryParams = queryParams;
		}
		var filter = $.extend( {}, opts.queryParams);
		if (opts.pagination) {
			$.extend(filter, {
				page : opts.pageNumber,
				rows : opts.pageSize
			});
		}
		if (opts.sortName) {
			$.extend(filter, {
				sort : opts.sortName,
				order : opts.sortOrder
			});
		}
		var row = _find(target, id);
		if (opts.onBeforeLoad.call(target, row, filter) == false) {
			return;
		}
		var icon = dgBody.find("tr[node-id=" + id + "] span.ui-tree-folder");
		icon.addClass("ui-tree-loading");
		$(target).treegrid("loading");
		var loader = opts.loader.call(target, filter, function(data) {
			icon.removeClass("ui-tree-loading");
			$(target).treegrid("loaded");
			_loadData(target, id, data, inc);
			if (backward) {
				backward();
			}
		}, function() {
			icon.removeClass("ui-tree-loading");
			$(target).treegrid("loaded");
			opts.onLoadError.apply(target, arguments);
			if (backward) {
				backward();
			}
		});
		if (loader == false) {
			icon.removeClass("ui-tree-loading");
			$(target).treegrid("loaded");
		}
	}
	
	function _getRoot(target) {
		var roots = _getRoots(target);
		if (roots.length) {
			return roots[0];
		} else {
			return null;
		}
	}
	
	function _getRoots(target) {
		return $.data(target, "treegrid").data;
	}
	
	function _getParent(target, id) {
		var row = _find(target, id);
		if (row._parentId) {
			return _find(target, row._parentId);
		} else {
			return null;
		}
	}
	
	function _getChildren(target, id) {
		var opts = $.data(target, "treegrid").options;
		var dgBody = $(target).datagrid("getPanel").find(
				"div.ui-datagrid-view2 div.ui-datagrid-body");
		var rtn = [];
		if (id) {
			_getChildrenById(id);
		} else {
			var roots = _getRoots(target);
			for ( var i = 0; i < roots.length; i++) {
				rtn.push(roots[i]);
				_getChildrenById(roots[i][opts.idField]);
			}
		}
		
		function _getChildrenById(id) {
			var node = _find(target, id);
			if (node && node.children) {
				for ( var i = 0, len = node.children.length; i < len; i++) {
					var subNode = node.children[i];
					rtn.push(subNode);
					_getChildrenById(subNode[opts.idField]);
				}
			}
		}
		
		return rtn;
	}
	
	function _getSelected(target) {
		var nodes = _getSelections(target);
		if (nodes.length) {
			return nodes[0];
		} else {
			return null;
		}
	}
	
	function _getSelections(target) {
		var rtn = [];
		var panel = $(target).datagrid("getPanel");
		panel.find("div.ui-datagrid-view2 div.ui-datagrid-body tr.ui-state-active")
				.each(function() {
					var id = $(this).attr("node-id");
					rtn.push(_find(target, id));
				});
		return rtn;
	}
	
	function _getLevel(target, id) {
		if (!id) {
			return 0;
		}
		var opts = $.data(target, "treegrid").options;
		var panelView = $(target).datagrid("getPanel").children("div.ui-datagrid-view");
		var tds = panelView.find("div.ui-datagrid-body tr[node-id=" + id + "]")
				.children("td[field=" + opts.treeField + "]");
		return tds.find("span.ui-tree-indent,span.ui-tree-hit").length;
	}
	
	function _find(target, id) {
		var opts = $.data(target, "treegrid").options;
		var data = $.data(target, "treegrid").data;
		var cc = [ data ];
		while (cc.length) {
			var c = cc.shift();
			for ( var i = 0; i < c.length; i++) {
				var obj = c[i];
				if (obj[opts.idField] == id) {
					return obj;
				} else {
					if (obj["children"]) {
						cc.push(obj["children"]);
					}
				}
			}
		}
		return null;
	}
	
	function _collapse(target, id) {
		var opts = $.data(target, "treegrid").options;
		var row = _find(target, id);
		var tr = opts.finder.getTr(target, id);
		var hit = tr.find("span.ui-tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("ui-tree-collapsed")) {
			return;
		}
		if (opts.onBeforeCollapse.call(target, row) == false) {
			return;
		}
		hit.removeClass("ui-tree-expanded ui-tree-expanded-hover").addClass(
				"ui-tree-collapsed");
		hit.next().removeClass("ui-tree-folder-open");
		row.state = "closed";
		tr = tr.next("tr.ui-treegrid-tr-tree");
		var cc = tr.children("td").children("div");
		if (opts.animate) {
			cc.slideUp("normal", function() {
				$(target).treegrid("autoSizeColumn");
				_fixRowHeight(target, id);
				opts.onCollapse.call(target, row);
			});
		} else {
			cc.hide();
			$(target).treegrid("autoSizeColumn");
			_fixRowHeight(target, id);
			opts.onCollapse.call(target, row);
		}
	}
	
	function _expand(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr = opts.finder.getTr(target, id);
		var hit = tr.find("span.ui-tree-hit");
		var row = _find(target, id);
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("ui-tree-expanded")) {
			return;
		}
		if (opts.onBeforeExpand.call(target, row) == false) {
			return;
		}
		hit.removeClass("ui-tree-collapsed ui-tree-collapsed-hover").addClass(
				"ui-tree-expanded");
		hit.next().addClass("ui-tree-folder-open");
		var treeTr = tr.next("tr.ui-treegrid-tr-tree");
		if (treeTr.length) {
			var cc = treeTr.children("td").children("div");
			_showSubNode(cc);
		} else {
			_32(target, row[opts.idField]);
			var cc = treeTr.children("td").children("div");
			cc.hide();
			_doLoadData(target, row[opts.idField], {
				id : row[opts.idField]
				}, true, function() {
					if (cc.is(":empty")) {
						treeTr.remove();
					} else {
						_showSubNode(cc);
					}
				});
		}
		
		function _showSubNode(cc) {
			row.state = "open";
			if (opts.animate) {
				cc.slideDown("normal", function() {
					$(target).treegrid("autoSizeColumn");
					_fixRowHeight(target, id);
					opts.onExpand.call(target, row);
				});
			} else {
				cc.show();
				$(target).treegrid("autoSizeColumn");
				_fixRowHeight(target, id);
				opts.onExpand.call(target, row);
			}
		}
		
	}
	
	function _toggle(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr = opts.finder.getTr(target, id);
		var hit = tr.find("span.ui-tree-hit");
		if (hit.hasClass("ui-tree-expanded")) {
			_collapse(target, id);
		} else {
			_expand(target, id);
		}
	}
	
	function _collapseAll(target, id) {
		var opts = $.data(target, "treegrid").options;
		var subNodes = _getChildren(target, id);
		if (id) {
			subNodes.unshift(_find(target, id));
		}
		for ( var i = 0; i < subNodes.length; i++) {
			_collapse(target, subNodes[i][opts.idField]);
		}
	}
	
	function _expandAll(target, id) {
		var opts = $.data(target, "treegrid").options;
		var subNodes = _getChildren(target, id);
		if (id) {
			subNodes.unshift(_find(target, id));
		}
		for ( var i = 0; i < subNodes.length; i++) {
			_expand(target, subNodes[i][opts.idField]);
		}
	}
	
	function _expandTo(target, pid) {
		var opts = $.data(target, "treegrid").options;
		var ids = [];
		var p = _getParent(target, pid);
		while (p) {
			var id = p[opts.idField];
			ids.unshift(id);
			p = _getParent(target, id);
		}
		for ( var i = 0; i < ids.length; i++) {
			_expand(target, ids[i]);
		}
	}
	
	function _append(target, node) {
		var opts = $.data(target, "treegrid").options;
		if (node.parent) {
			var tr = opts.finder.getTr(target, node.parent);
			if (tr.next("tr.ui-treegrid-tr-tree").length == 0) {
				_32(target, node.parent);
			}
			var dgCell = tr.children("td[field=" + opts.treeField + "]").children(
					"div.ui-datagrid-cell");
			var icon = dgCell.children("span.ui-tree-icon");
			if (icon.hasClass("ui-tree-file")) {
				icon.removeClass("ui-tree-file").addClass("ui-tree-folder");
				var hit = $("<span class=\"ui-tree-hit ui-icon ui-tree-expanded\"></span>")
						.insertBefore(icon);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
		}
		_loadData(target, node.parent, node.data, true);
	}
	
	function _insert(target, node) {
		var ref = node.before || node.after;
		var opts = $.data(target, "treegrid").options;
		var pNode = _getParent(target, ref);
		_append(target, {
			parent : (pNode ? pNode[opts.idField] : null),
			data : [ node.data ]
		});
		insertTr(true);
		insertTr(false);
		_2a(target);
		
		function insertTr(trIdxB) {
			var trIdx = trIdxB ? 1 : 2;
			var tr = opts.finder.getTr(target, node.data[opts.idField], "body", trIdx);
			var btable = tr.closest("table.ui-datagrid-btable");
			tr = tr.parent().children();
			var ptr = opts.finder.getTr(target, ref, "body", trIdx);
			if (node.before) {
				tr.insertBefore(ptr);
			} else {
				var sub = ptr.next("tr.ui-treegrid-tr-tree");
				tr.insertAfter(sub.length ? sub : ptr);
			}
			btable.remove();
		}
		
	}
	
	function _remove(target, id) {
		var opts = $.data(target, "treegrid").options;
		var tr = opts.finder.getTr(target, id);
		tr.next("tr.ui-treegrid-tr-tree").remove();
		tr.remove();
		var pNode = del(id);
		if (pNode) {
			if (pNode.children.length == 0) {
				tr = opts.finder.getTr(target, pNode[opts.idField]);
				tr.next("tr.ui-treegrid-tr-tree").remove();
				var dgCell = tr.children("td[field=" + opts.treeField + "]").children("div.ui-datagrid-cell");
				dgCell.find(".ui-tree-icon").removeClass("ui-tree-folder").addClass("ui-tree-file");
				dgCell.find(".ui-tree-hit").remove();
				$("<span class=\"ui-tree-indent\"></span>").prependTo(dgCell);
			}
		}
		_2a(target);
		
		function del(id) {
			var cc;
			var pNode = _getParent(target, id);
			if (pNode) {
				cc = pNode.children;
			} else {
				cc = $(target).treegrid("getData");
			}
			for ( var i = 0; i < cc.length; i++) {
				if (cc[i][opts.idField] == id) {
					cc.splice(i, 1);
					break;
				}
			}
			return pNode;
		}
		
	}
	
	$.fn.treegrid = function(options, param) {
		if (typeof options == "string") {
			var optMethod = $.fn.treegrid.methods[options];
			if (optMethod) {
				return optMethod(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "treegrid");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "treegrid", {
					options : $.extend( {}, $.fn.treegrid.defaults,
							$.fn.treegrid.parseOptions(this), options),
					data : []
				});
			}
			_initTreeGrid(this);
			_doLoadData(this);
			_initNodeEvent(this);
		});
	};
	
	$.fn.treegrid.methods = {
		options : function(jq) {
			return $.data(jq[0], "treegrid").options;
		},
		resize : function(jq, size) {
			return jq.each(function() {
				$(this).datagrid("resize", size);
			});
		},
		fixRowHeight : function(jq, id) {
			return jq.each(function() {
				_fixRowHeight(this, id);
			});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				_loadData(this, null, data);
			});
		},
		/**
		 * 重新加载特定节点
		 */
		reload : function(jq, id) {
			return jq.each(function() {
				if (id) {
					var node = $(this).treegrid("find", id);
					// 删除原子节点
					if (node.children) {
						node.children.splice(0, node.children.length);
					}
					var body = $(this).datagrid("getPanel").find(
							"div.ui-datagrid-body");
					var tr = body.find("tr[node-id=" + id + "]");
					tr.next("tr.ui-treegrid-tr-tree").remove();
					var hit = tr.find("span.ui-tree-hit");
					hit.removeClass("ui-tree-expanded ui-tree-expanded-hover")
							.addClass("ui-tree-collapsed");
					_expand(this, id);
				} else {
					_doLoadData(this, null, {});
				}
			});
		},
		reloadFooter : function(jq, footer) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				var dc = $.data(this, "datagrid").dc;
				if (footer) {
					$.data(this, "treegrid").footer = footer;
				}
				if (opts.showFooter) {
					opts.view.renderFooter.call(opts.view, this, dc.footer1, true);
					opts.view.renderFooter.call(opts.view, this, dc.footer2, false);
					if (opts.view.onAfterRender) {
						opts.view.onAfterRender.call(opts.view, this);
					}
					$(this).treegrid("fixRowHeight");
				}
			});
		},
		setUrlData : function(jq, data) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				opts.urlData = data;
			});
		},
		getData : function(jq) {
			return $.data(jq[0], "treegrid").data;
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], "treegrid").footer;
		},
		getRoot : function(jq) {
			return _getRoot(jq[0]);
		},
		getRoots : function(jq) {
			return _getRoots(jq[0]);
		},
		getParent : function(jq, id) {
			return _getParent(jq[0], id);
		},
		getChildren : function(jq, id) {
			return _getChildren(jq[0], id);
		},
		getSelected : function(jq) {
			return _getSelected(jq[0]);
		},
		getSelections : function(jq) {
			return _getSelections(jq[0]);
		},
		getLevel : function(jq, id) {
			return _getLevel(jq[0], id);
		},
		find : function(jq, id) {
			return _find(jq[0], id);
		},
		isLeaf : function(jq, id) {
			var opts = $.data(jq[0], "treegrid").options;
			var tr = opts.finder.getTr(jq[0], id);
			var hit = tr.find("span.ui-tree-hit");
			return hit.length == 0;
		},
		select : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("selectRow", id);
			});
		},
		unselect : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("unselectRow", id);
			});
		},
		collapse : function(jq, id) {
			return jq.each(function() {
				_collapse(this, id);
			});
		},
		expand : function(jq, id) {
			return jq.each(function() {
				_expand(this, id);
			});
		},
		toggle : function(jq, id) {
			return jq.each(function() {
				_toggle(this, id);
			});
		},
		collapseAll : function(jq, id) {
			return jq.each(function() {
				_collapseAll(this, id);
			});
		},
		expandAll : function(jq, id) {
			return jq.each(function() {
				_expandAll(this, id);
			});
		},
		expandTo : function(jq, id) {
			return jq.each(function() {
				_expandTo(this, id);
			});
		},
		append : function(jq, node) {
			return jq.each(function() {
				_append(this, node);
			});
		},
		insert : function(jq, node) {
			return jq.each(function() {
				_insert(this, node);
			});
		},
		remove : function(jq, id) {
			return jq.each(function() {
				_remove(this, id);
			});
		},
		pop : function(jq, id) {
			var row = jq.treegrid("find", id);
			jq.treegrid("remove", id);
			return row;
		},
		refresh : function(jq, id) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				opts.view.refreshRow.call(opts.view, this, id);
			});
		},
		update : function(jq, node) {
			return jq.each(function() {
				var opts = $.data(this, "treegrid").options;
				opts.view.updateRow.call(opts.view, this, node.id, node.row);
			});
		},
		beginEdit : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("beginEdit", id);
				$(this).treegrid("fixRowHeight", id);
			});
		},
		endEdit : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("endEdit", id);
			});
		},
		cancelEdit : function(jq, id) {
			return jq.each(function() {
				$(this).datagrid("cancelEdit", id);
			});
		}
	};
	$.fn.treegrid.parseOptions = function(options) {
		return $.extend( {}, $.fn.datagrid.parseOptions(options), Plywet
				.parseOptions(options, [ "treeField", {
					animate : "boolean"
				} ]));
	};
	var _view = $.extend({}, $.fn.datagrid.defaults.view,
		{
			render : function(target, _bb, fromFrozenColumns) {
				var opts = $.data(target, "treegrid").options;
				var columnFields = $(target).datagrid("getColumnFields", fromFrozenColumns);
				var rowIdPrefix = $.data(target, "datagrid").rowIdPrefix;
				if (fromFrozenColumns) {
					if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
						return;
					}
				}
				var _self = this;
				var tableArr = createTable(fromFrozenColumns, this.treeLevel, this.treeNodes);
				$(_bb).append(tableArr.join(""));
				
				function createTable(fromFrozenColumns, treeLevel, treeNodes) {
					var tableArr = [ "<table class=\"ui-datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
					if (treeNodes) {
						for ( var i = 0; i < treeNodes.length; i++) {
							var row = treeNodes[i];
							if (row.state != "open" && row.state != "closed") {
								row.state = "open";
							}
							var rowStyle = opts.rowStyler ? opts.rowStyler.call(target, row) : "";
							var style = rowStyle ? "style=\"" + rowStyle + "\"" : "";
							var rowId = rowIdPrefix + "-" + (fromFrozenColumns ? 1 : 2) + "-" + row[opts.idField];
							tableArr.push("<tr id=\""
								+ rowId
								+ "\" class=\"ui-datagrid-row\" node-id="
								+ row[opts.idField] + " "
								+ style + ">");
							tableArr = tableArr.concat(_self.renderRow.call(_self, target, columnFields, fromFrozenColumns, treeLevel, row));
							tableArr.push("</tr>");
							if (row.children && row.children.length) {
								var tt = createTable(fromFrozenColumns, treeLevel + 1, row.children);
								var v = row.state == "closed" ? "none"
										: "block";
								tableArr.push("<tr class=\"ui-treegrid-tr-tree\"><td style=\"border:0px\" colspan="
										+ (columnFields.length + (opts.rownumbers ? 1 : 0))
										+ "><div style=\"display:"
										+ v + "\">");
								tableArr = tableArr.concat(tt);
								tableArr.push("</div></td></tr>");
							}
						}
					}
					tableArr.push("</tbody></table>");
					return tableArr;
				}
			},
			renderFooter : function(target, footerTarget, fromFrozenColumns) {
				var opts = $.data(target, "treegrid").options;
				var footer = $.data(target, "treegrid").footer || [];
				var _cf = $(target).datagrid("getColumnFields", fromFrozenColumns);
				var tableArr = [ "<table class=\"ui-datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
				for ( var i = 0; i < footer.length; i++) {
					var row = footer[i];
					row[opts.idField] = row[opts.idField]
							|| ("foot-row-id" + i);
					tableArr.push("<tr class=\"ui-datagrid-row\" node-id=" + row[opts.idField] + ">");
					tableArr.push(this.renderRow.call(this, target, _cf, fromFrozenColumns, 0, row));
					tableArr.push("</tr>");
				}
				tableArr.push("</tbody></table>");
				$(footerTarget).html(tableArr.join(""));
			},
			renderRow : function(target, _d2, _d3, _d4, row) {
				var opts = $.data(target, "treegrid").options;
				var cc = [];
				if (_d3 && opts.rownumbers) {
					cc.push("<td class=\"ui-datagrid-td-rownumber\"><div class=\"ui-datagrid-cell-rownumber\">0</div></td>");
				}
				for ( var i = 0; i < _d2.length; i++) {
					var field = _d2[i];
					var col = $(target).datagrid("getColumnOption", field);
					if (col) {
						var style = col.styler ? (col.styler( row[field], row) || "") : "";
						var s = col.hidden ? "style=\"display:none;" + style + "\"" : (style ? "style=\"" + style + "\"" : "");
						cc.push("<td field=\"" + field + "\" " + s + ">");
						var divStyle = "";
						if (col.checkbox) {
						} else {
							if (col.align) {
								divStyle += "text-align:" + col.align + ";";
							}
							if (!opts.nowrap) {
								divStyle += "white-space:normal;height:auto;";
							} else {
								if (opts.autoRowHeight) {
									divStyle += "height:auto;";
								}
							}
						}
						cc.push("<div style=\"" + divStyle + "\" ");
						if (col.checkbox) {
							cc.push("class=\"ui-datagrid-cell-check ");
						} else {
							cc.push("class=\"ui-datagrid-cell " + col.cellClass);
						}
						cc.push("\">");
						if (col.checkbox) {
							if (row.checked) {
								cc.push("<input type=\"checkbox\" checked=\"checked\"");
							} else {
								cc.push("<input type=\"checkbox\"");
							}
							cc.push(" name=\""
									+ field
									+ "\" value=\""
									+ (row[field] != undefined ? row[field] : "") + "\"/>");
						} else {
							var val = null;
							if (col.formatter) {
								val = col.formatter(row[field], row);
							} else {
								val = row[field];
							}
							if (field == opts.treeField) {
								var showIcon = (row.showIcon == undefined) ? ((opts.showIcon == undefined) ? true : opts.showIcon) : row.showIcon;
								for ( var j = 0; j < _d4; j++) {
									cc.push("<span class=\"ui-tree-indent\"></span>");
								}
								if (row.state == "closed") {
									cc.push("<span class=\"ui-tree-hit ui-icon ui-tree-collapsed\"></span>");
									cc.push("<span class=\"ui-tree-icon ui-icon ui-tree-folder "
													+ (row.iconCls ? row.iconCls : "")
													+ "\"" + (showIcon ? "" : "style=\"display:none;\"") +"></span>");
								} else {
									if (row.children && row.children.length) {
										cc.push("<span class=\"ui-tree-hit ui-icon ui-tree-expanded\"></span>");
										cc.push("<span class=\"ui-tree-icon ui-icon ui-tree-folder ui-tree-folder-open "
												+ (row.iconCls ? row.iconCls
														: "")
												+ "\"" + (showIcon ? "" : "style=\"display:none;\"") +"></span>");
									} else {
										cc.push("<span class=\"ui-tree-indent\"></span>");
										cc.push("<span class=\"ui-tree-icon ui-icon ui-tree-file "
												+ (row.iconCls ? row.iconCls
														: "")
												+ "\"" + (showIcon ? "" : "style=\"display:none;\"") +"></span>");
									}
								}
								cc.push("<span class=\"ui-tree-title\">" + val + "</span>");
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
			refreshRow : function(target, id) {
				this.updateRow.call(this, target, id, {});
			},
			updateRow : function(target, id, row) {
				var opts = $.data(target, "treegrid").options;
				var _dc = $(target).treegrid("find", id);
				$.extend(_dc, row);
				var level = $(target).treegrid("getLevel", id) - 1;
				var style = opts.rowStyler ? opts.rowStyler.call(target, _dc) : "";
				
				function _df(fromFrozenColumns) {
					var column = $(target).treegrid("getColumnFields", fromFrozenColumns);
					var tr = opts.finder.getTr(target, id, "body", (fromFrozenColumns ? 1 : 2));
					var rownumber = tr.find("div.ui-datagrid-cell-rownumber").html();
					var ischeck = tr.find("div.ui-datagrid-cell-check input[type=checkbox]").is(":checked");
					tr.html(this.renderRow(target, column, fromFrozenColumns, level, _dc));
					tr.attr("style", style || "");
					tr.find("div.ui-datagrid-cell-rownumber").html(rownumber);
					if (ischeck) {
						tr.find("div.ui-datagrid-cell-check input[type=checkbox]")._propAttr("checked", true);
					}
				}
				
				_df.call(this, true);
				_df.call(this, false);
				$(target).treegrid("fixRowHeight", id);
			},
			onBeforeRender : function(target, id, data) {
				if (!data) {
					return false;
				}
				var opts = $.data(target, "treegrid").options;
				if (data.length == undefined) {
					if (data.footer) {
						$.data(target, "treegrid").footer = data.footer;
					}
					if (data.total) {
						$.data(target, "treegrid").total = data.total;
					}
					data = this.transfer(target, id, data.rows);
				} else {
					
					function _onBeforeRenders(data, id) {
						for ( var i = 0; i < data.length; i++) {
							var row = data[i];
							row._parentId = id;
							if (row.children && row.children.length) {
								_onBeforeRenders(row.children, row[opts.idField]);
							}
						}
					}
					
					_onBeforeRenders(data, id);
				}
				var _eb = _find(target, id);
				if (_eb) {
					if (_eb.children) {
						_eb.children = _eb.children.concat(data);
					} else {
						_eb.children = data;
					}
				} else {
					$.data(target, "treegrid").data = $.data(target,
							"treegrid").data.concat(data);
				}
				if (!opts.remoteSort) {
					this.sort(target, data);
				}
				this.treeNodes = data;
				this.treeLevel = $(target).treegrid("getLevel", id);
			},
			sort : function(target, _ed) {
				var opts = $.data(target, "treegrid").options;
				var opt = $(target).treegrid("getColumnOption",
						opts.sortName);
				if (opt) {
					var _ef = opt.sorter || function(a, b) {
						return (a > b ? 1 : -1);
					};
					_f0(_ed);
				}
				
				function _f0(_f1) {
					_f1.sort(function(r1, r2) {
								return _ef(r1[opts.sortName],
										r2[opts.sortName])
										* (opts.sortOrder == "asc" ? 1
												: -1);
							});
					for ( var i = 0; i < _f1.length; i++) {
						var _f2 = _f1[i].children;
						if (_f2 && _f2.length) {
							_f0(_f2);
						}
					}
				}
				
			},
			transfer : function(target, _f4, _f5) {
				var opts = $.data(target, "treegrid").options;
				var _f7 = [];
				for ( var i = 0; i < _f5.length; i++) {
					_f7.push(_f5[i]);
				}
				var _f8 = [];
				for ( var i = 0; i < _f7.length; i++) {
					var row = _f7[i];
					if (!_f4) {
						if (!row._parentId) {
							_f8.push(row);
							_3(_f7, row);
							i--;
						}
					} else {
						if (row._parentId == _f4) {
							_f8.push(row);
							_3(_f7, row);
							i--;
						}
					}
				}
				var _f9 = [];
				for ( var i = 0; i < _f8.length; i++) {
					_f9.push(_f8[i]);
				}
				while (_f9.length) {
					var _fa = _f9.shift();
					for ( var i = 0; i < _f7.length; i++) {
						var row = _f7[i];
						if (row._parentId == _fa[opts.idField]) {
							if (_fa.children) {
								_fa.children.push(row);
							} else {
								_fa.children = [ row ];
							}
							_f9.push(row);
							_3(_f7, row);
							i--;
						}
					}
				}
				return _f8;
			}
		});
	
	$.fn.treegrid.defaults = $.extend(
		{},
		$.fn.datagrid.defaults,
		{
			treeField : null,
			showIcon : true,
			animate : false,
			singleSelect : true,
			view : _view,
			loader : function(data, onsuccess, onerror) {
				var options = $(this).treegrid("options");
				if (options.urlData){
					onsuccess(options.urlData);
					return true;
				}
				if (!options.url) {
					return false;
				}
				$.ajax( {
					type : options.method,
					url : options.url,
					data : data,
					dataType : "json",
					success : function(e) {
						onsuccess(e);
					},
					error : function() {
						onerror.apply(this, arguments);
					}
				});
			},
			loadFilter : function(data, id) {
				return data;
			},
			finder : {
				getTr : function(target, id, type, trIdx) {
					type = type || "body";
					trIdx = trIdx || 0;
					var dc = $.data(target, "datagrid").dc;
					if (trIdx == 0) {
						var opts = $.data(target, "treegrid").options;
						var tr1 = opts.finder.getTr(target, id, type, 1);
						var tr2 = opts.finder.getTr(target, id, type, 2);
						return tr1.add(tr2);
					} else {
						if (type == "body") {
							var tr = $("#" + $.data(target, "datagrid").rowIdPrefix + "-" + trIdx + "-" + id);
							if (!tr.length) {
								tr = (trIdx == 1 ? dc.body1 : dc.body2).find("tr[node-id=" + id + "]");
							}
							return tr;
						} else {
							if (type == "footer") {
								return (trIdx == 1 ? dc.footer1 : dc.footer2).find("tr[node-id=" + id + "]");
							} else {
								if (type == "selected") {
									return (trIdx == 1 ? dc.body1 : dc.body2).find("tr.ui-state-active");
								} else {
									if (type == "last") {
										return (trIdx == 1 ? dc.body1 : dc.body2).find("tr:last[node-id]");
									} else {
										if (type == "allbody") {
											return (trIdx == 1 ? dc.body1 : dc.body2).find("tr[node-id]");
										} else {
											if (type == "allfooter") {
												return (trIdx == 1 ? dc.footer1 : dc.footer2).find("tr[node-id]");
											}
										}
									}
								}
							}
						}
					}
				},
				getRow : function(target, id) {
					return $(target).treegrid("find", id);
				}
			},
			onBeforeLoad : function(row, filter) {
			},
			onLoadSuccess : function(row, data) {
			},
			onLoadError : function() {
			},
			onBeforeCollapse : function(row) {
			},
			onCollapse : function(row) {
			},
			onBeforeExpand : function(row) {
			},
			onExpand : function(row) {
			},
			onClickRow : function(row) {
			},
			onDblClickRow : function(row) {
			},
			onClickCell : function(data, row) {
			},
			onDblClickCell : function(data, row) {
			},
			onContextMenu : function(e, row) {
			},
			onBeforeEdit : function(row) {
			},
			onAfterEdit : function(row, data) {
			},
			onCancelEdit : function(row) {
			}
		});
})(jQuery);

Plywet.widget.EasyTreeGrid=function(cfg){
	this.cfg=cfg;
	this.id=cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
    this.jq = $(this.jqId);
    this.jq.treegrid(cfg);
};

Plywet.extend(Plywet.widget.EasyTreeGrid, Plywet.widget.BaseWidget);

Plywet.widget.EasyTreeGrid.prototype.loadData=function(data){
	this.jq.treegrid("setUrlData", data);
	this.jq.treegrid("reload");
};