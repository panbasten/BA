(function($) {
	/**
	 * 初始化移除对象内容
	 */
	function _reset(target) {
		target.each(function() {
			$(this).remove();
			if ($.browser.msie) {
				this.outerHTML = "";
			}
		});
	}
	
	function _fit(target, fit) {
		fit = fit == undefined ? true : fit;
		var panel = $.data(target, "panel").panel;
		var p = panel.parent()[0];
		var t = panel[0];
		var fcount = p.fcount || 0;
		if (fit) {
			if (!t.fitted) {
				t.fitted = true;
				p.fcount = fcount + 1;
				$(p).addClass("ui-panel-noscroll");
				if (p.tagName == "BODY") {
					$("html").addClass("ui-panel-fit");
				}
			}
		} else {
			if (t.fitted) {
				t.fitted = false;
				p.fcount = fcount - 1;
				if (p.fcount == 0) {
					$(p).removeClass("ui-panel-noscroll");
					if (p.tagName == "BODY") {
						$("html").removeClass("ui-panel-fit");
					}
				}
			}
		}
		return {
			width : $(p).width(),
			height : $(p).height()
		};
	}
	
	/**
	 * 改变尺寸
	 */
	function _resize(target, param) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var header = panel.children("div.ui-panel-header");
		var body = panel.children("div.ui-panel-body");
		if (param) {
			if (param.width) {
				opts.width = param.width;
			}
			if (param.height) {
				opts.height = param.height;
			}
			if (param.left != null) {
				opts.left = param.left;
			}
			if (param.top != null) {
				opts.top = param.top;
			}
		}
		opts.fit ? $.extend(opts, _fit(target)) : _fit(target, false);
		panel.css( {
			left : opts.left,
			top : opts.top
		});
		if (!isNaN(opts.width)) {
			panel.width(opts.width);
		} else {
			panel.width("auto");
		}
		header.add(body).width(panel.width());
		if (!isNaN(opts.height)) {
			panel.height(opts.height);
			body.height(panel.height() - header.height());
		} else {
			body.height("auto");
		}
		panel.css("height", "");
		opts.onResize.apply(target, [ opts.width, opts.height ]);
		panel.find(">div.ui-panel-body>div").triggerHandler("_resize");
	}
	
	/**
	 * 移动
	 */
	function _move(target, param) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (param) {
			if (param.left != null) {
				opts.left = param.left;
			}
			if (param.top != null) {
				opts.top = param.top;
			}
		}
		panel.css( {
			left : opts.left,
			top : opts.top
		});
		opts.onMove.apply(target, [ opts.left, opts.top ]);
	}
	
	/**
	 * 初始化Panel的Div
	 */
	function _initPanel(target) {
		$(target).addClass("ui-panel-body");
		var panel = $("<div class=\"ui-panel ui-widget ui-helper-clearfix ui-corner-all\"></div>").insertBefore(target);
		panel[0].appendChild(target);
		panel.bind("_resize", function() {
			var opts = $.data(target, "panel").options;
			if (opts.fit == true) {
				_resize(target);
			}
			return false;
		});
		return panel;
	}
	
	/**
	 * 初始化Header的Div，以及相关button
	 */
	function _initHeader(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (opts.tools && typeof opts.tools == "string") {
			panel.find(">div.ui-panel-header>div.ui-panel-tool .ui-panel-tool-a").appendTo(opts.tools);
		}
		_reset(panel.children("div.ui-panel-header"));
		if (opts.title && !opts.noheader) {
			var header = $("<div class=\"ui-panel-header ui-widget ui-widget-header\"><div class=\"ui-panel-title\">"
					+ opts.title + "</div></div>").prependTo(panel);
			if (opts.iconCls) {
				header.find(".ui-panel-title").addClass("ui-panel-with-icon");
				$("<div class=\"ui-panel-icon\"></div>").addClass(opts.iconCls)
						.appendTo(header);
			}
			var panelTool = $("<div class=\"ui-panel-tool\"></div>").appendTo(header);
			panelTool.bind("click", function(e) {
				e.stopPropagation();
			});
			if (opts.tools) {
				if (typeof opts.tools == "string") {
					$(opts.tools).children().each(
						function() {
							$(this).addClass($(this).attr("iconCls"))
								.addClass("ui-panel-tool-a").appendTo(panelTool);
						});
				} else {
					for ( var i = 0; i < opts.tools.length; i++) {
						var t = $("<a href=\"javascript:void(0)\"></a>")
								.addClass(opts.tools[i].iconCls).appendTo(panelTool);
						if (opts.tools[i].handler) {
							t.bind("click", eval(opts.tools[i].handler));
						}
					}
				}
			}
			if (opts.collapsible) {
				$("<a class=\"ui-panel-tool-collapse ui-icon ui-icon-triangle-1-n\" href=\"javascript:void(0)\"></a>")
					.appendTo(panelTool).bind("click", function() {
						if (opts.collapsed == true) {
							_expand(target, true);
						} else {
							_collapse(target, true);
						}
						return false;
					});
			}
			if (opts.minimizable) {
				$("<a class=\"ui-panel-tool-min ui-icon ui-icon-panel-min\" href=\"javascript:void(0)\"></a>")
					.appendTo(panelTool).bind("click", function() {
						_minimize(target);
						return false;
					});
			}
			if (opts.maximizable) {
				$("<a class=\"ui-panel-tool-max ui-icon ui-icon-panel-max\" href=\"javascript:void(0)\"></a>")
					.appendTo(panelTool).bind("click", function() {
						if (opts.maximized == true) {
							_restore(target);
						} else {
							_maximize(target);
						}
						return false;
					});
			}
			if (opts.closable) {
				$("<a class=\"ui-panel-tool-close ui-icon ui-icon-panel-close\" href=\"javascript:void(0)\"></a>")
					.appendTo(panelTool).bind("click", function() {
						_close(target);
						return false;
					});
			}
			panel.children("div.ui-panel-body").removeClass("ui-panel-body-noheader");
		} else {
			panel.children("div.ui-panel-body").addClass("ui-panel-body-noheader");
		}
	}
	
	/**
	 * 请求远端数据
	 */
	function _request(target) {
		var panel = $.data(target, "panel");
		if (panel.options.href && (!panel.isLoaded || !panel.options.cache)) {
			panel.isLoaded = false;
			_destroySub(target);
			var body = panel.panel.find(">div.ui-panel-body");
			if (panel.options.loadingMessage) {
				body.html($("<div class=\"ui-panel-loading ui-loading\"></div>").html(
						panel.options.loadingMessage));
			}
			// 获得页面执行相应的匹配操作
			Flywet.ab({
				type : "get",
				url : panel.options.href,
				onsuccess : function(data) {
					Flywet.ajax.AjaxResponse.call(this, data, body);
					panel.options.onLoad.apply(target, arguments);
					panel.isLoaded = true;
					return true;
				}
			});
		}
	}
	
	/**
	 * 销毁子元素
	 * TODO
	 */
	function _destroySub(target) {
		var t = $(target);
		t.find(".combo-f").each(function() {
			$(this).combo("destroy");
		});
		t.find(".m-btn").each(function() {
			$(this).menubutton("destroy");
		});
		t.find(".s-btn").each(function() {
			$(this).splitbutton("destroy");
		});
	}
	
	/**
	 * 调用子对象的改变尺寸
	 * TODO
	 */
	function _resizeSub(target) {
		$(target).find("div.ui-panel:visible,div.tabs-container:visible,div.layout:visible")
			.each(function() {
				$(this).triggerHandler("_resize", [ true ]);
			});
	}
	
	/**
	 * 打开
	 */
	function _open(target, skip) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (skip != true) {
			if (opts.onBeforeOpen.call(target) == false) {
				return;
			}
		}
		panel.show();
		opts.closed = false;
		opts.minimized = false;
		var toolRestore = panel.children("div.ui-panel-header").find("a.ui-panel-tool-restore");
		if (toolRestore.length) {
			opts.maximized = true;
		}
		opts.onOpen.call(target);
		if (opts.maximized == true) {
			opts.maximized = false;
			_maximize(target);
		}
		if (opts.collapsed == true) {
			opts.collapsed = false;
			_collapse(target);
		}
		if (!opts.collapsed) {
			_request(target);
			_resizeSub(target);
		}
	}
	
	/**
	 * 关闭
	 */
	function _close(target, skip) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (skip != true) {
			if (opts.onBeforeClose.call(target) == false) {
				return;
			}
		}
		_fit(target, false);
		panel.hide();
		opts.closed = true;
		opts.onClose.call(target);
	}
	
	/**
	 * 销毁
	 */
	function _destroy(target, skip) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (skip != true) {
			if (opts.onBeforeDestroy.call(target) == false) {
				return;
			}
		}
		_destroySub(target);
		_reset(panel);
		opts.onDestroy.call(target);
	}
	
	/**
	 * 收缩
	 */
	function _collapse(target, animate) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var body = panel.children("div.ui-panel-body");
		var header = panel.children("div.ui-panel-header")
				.find("a.ui-panel-tool-collapse");
		if (opts.collapsed == true) {
			return;
		}
		body.stop(true, true);
		if (opts.onBeforeCollapse.call(target) == false) {
			return;
		}
		header.addClass("ui-panel-tool-expand ui-icon ui-icon-triangle-1-s");
		if (animate == true) {
			body.slideUp("normal", function() {
				opts.collapsed = true;
				opts.onCollapse.call(target);
			});
		} else {
			body.hide();
			opts.collapsed = true;
			opts.onCollapse.call(target);
		}
	}
	
	/**
	 * 展开
	 */
	function _expand(target, animate) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var body = panel.children("div.ui-panel-body");
		var header = panel.children("div.ui-panel-header")
				.find("a.ui-panel-tool-collapse");
		if (opts.collapsed == false) {
			return;
		}
		body.stop(true, true);
		if (opts.onBeforeExpand.call(target) == false) {
			return;
		}
		header.removeClass("ui-panel-tool-expand ui-icon-triangle-1-s");
		if (animate == true) {
			body.slideDown("normal", function() {
				opts.collapsed = false;
				opts.onExpand.call(target);
				_request(target);
				_resizeSub(target);
			});
		} else {
			body.show();
			opts.collapsed = false;
			opts.onExpand.call(target);
			_request(target);
			_resizeSub(target);
		}
	}
	
	/**
	 * 最大化
	 */
	function _maximize(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var toolMax = panel.children("div.ui-panel-header").find("a.ui-panel-tool-max");
		if (opts.maximized == true) {
			return;
		}
		toolMax.addClass("ui-panel-tool-restore ui-icon ui-icon-panel-restore");
		if (!$.data(target, "panel").original) {
			$.data(target, "panel").original = {
				width : opts.width,
				height : opts.height,
				left : opts.left,
				top : opts.top,
				fit : opts.fit
			};
		}
		opts.left = 0;
		opts.top = 0;
		opts.fit = true;
		_resize(target);
		opts.minimized = false;
		opts.maximized = true;
		opts.onMaximize.call(target);
	}
	
	/**
	 * 最小化
	 */
	function _minimize(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		_fit(target, false);
		panel.hide();
		opts.minimized = true;
		opts.maximized = false;
		opts.onMinimize.call(target);
	}
	
	/**
	 * 恢复
	 */
	function _restore(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var toolMax = panel.children("div.ui-panel-header").find("a.ui-panel-tool-max");
		if (opts.maximized == false) {
			return;
		}
		panel.show();
		toolMax.removeClass("ui-panel-tool-restore ui-icon-panel-restore");
		$.extend(opts, $.data(target, "panel").original);
		_resize(target);
		opts.minimized = false;
		opts.maximized = false;
		$.data(target, "panel").original = null;
		opts.onRestore.call(target);
	}
	
	/**
	 * 初始化样式
	 */
	function _initStyle(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var header = $(target).panel("header");
		var body = $(target).panel("body");
		panel.css(opts.style);
		panel.addClass(opts.cls);
		if (opts.panelStyle) {
			panel.addClass("ui-panel-" + opts.panelStyle);
		}
		if (opts.border) {
			header.removeClass("ui-panel-header-noborder");
			body.removeClass("ui-panel-body-noborder");
		} else {
			header.addClass("ui-panel-header-noborder");
			body.addClass("ui-panel-body-noborder");
		}
		header.addClass(opts.headerCls);
		body.addClass(opts.bodyCls);
		if (opts.id) {
			$(target).attr("id", opts.id);
		} else {
			$(target).attr("id", "");
		}
	}
	
	/**
	 * 设置Title
	 */
	function _setTitle(target, title) {
		$.data(target, "panel").options.title = title;
		$(target).panel("header").find("div.ui-panel-title").html(title);
	}
	
	var TO = false;
	var resized = true;
	
	// TODO 
	$(window).unbind(".panel").bind("resize.panel",
		function() {
			if (!resized) {
				return;
			}
			if (TO !== false) {
				clearTimeout(TO);
			}
			TO = setTimeout(
				function() {
					resized = false;
					var layout = $("body.layout");
					if (layout.length) {
						layout.layout("resize");
					} else {
						$("body").children("div.panel,div.accordion,div.tabs-container,div.layout")
								.triggerHandler("_resize");
					}
					resized = true;
					TO = false;
				}, 200);
		});
	
	/**
	 * 构造jQuery扩展方法panel的入口
	 */
	$.fn.panel = function(options, param) {
		if (typeof options == "string") {
			return $.fn.panel.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var panel = $.data(this, "panel");
			var opts;
			if (panel) {
				opts = $.extend(panel.options, options);
			} else {
				opts = $.extend( {}, $.fn.panel.defaults, $.fn.panel
						.parseOptions(this), options);
				$(this).attr("title", "");
				panel = $.data(this, "panel", {
					options : opts,
					panel : _initPanel(this),
					isLoaded : false
				});
				$.data(this, "componentType", "panel");
			}
			if (opts.content) {
				Flywet.autocw(opts.content, this);
			}
			_initHeader(this);
			_initStyle(this);
			if (opts.doSize == true) {
				panel.panel.css("display", "block");
				_resize(this);
			}
			if (opts.closed == true || opts.minimized == true) {
				panel.panel.hide();
			} else {
				_open(this);
			}
		});
	};
	
	$.fn.panel.methods = {
		options : function(jq) {
			return $.data(jq[0], "panel").options;
		},
		panel : function(jq) {
			return $.data(jq[0], "panel").panel;
		},
		header : function(jq) {
			return $.data(jq[0], "panel").panel.find(">div.ui-panel-header");
		},
		body : function(jq) {
			return $.data(jq[0], "panel").panel.find(">div.ui-panel-body");
		},
		setTitle : function(jq, title) {
			return jq.each(function() {
				_setTitle(this, title);
			});
		},
		open : function(jq, skip) {
			return jq.each(function() {
				_open(this, skip);
			});
		},
		close : function(jq, skip) {
			return jq.each(function() {
				_close(this, skip);
			});
		},
		destroy : function(jq, skip) {
			return jq.each(function() {
				_destroy(this, skip);
			});
		},
		refresh : function(jq, href) {
			return jq.each(function() {
				$.data(this, "panel").isLoaded = false;
				if (href) {
					$.data(this, "panel").options.href = href;
				}
				_request(this);
			});
		},
		resize : function(jq, param) {
			return jq.each(function() {
				_resize(this, param);
			});
		},
		move : function(jq, pos) {
			return jq.each(function() {
				_move(this, pos);
			});
		},
		maximize : function(jq) {
			return jq.each(function() {
				_maximize(this);
			});
		},
		minimize : function(jq) {
			return jq.each(function() {
				_minimize(this);
			});
		},
		restore : function(jq) {
			return jq.each(function() {
				_restore(this);
			});
		},
		collapse : function(jq, animate) {
			return jq.each(function() {
				_collapse(this, animate);
			});
		},
		expand : function(jq, animate) {
			return jq.each(function() {
				_expand(this, animate);
			});
		}
	};
	$.fn.panel.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, Flywet.parseOptions(target, [ "id", "width",
				"height", "left", "top", "title", "iconCls", "cls",
				"headerCls", "bodyCls", "tools", "href", {
					cache : "boolean",
					fit : "boolean",
					border : "boolean",
					noheader : "boolean"
				}, {
					collapsible : "boolean",
					minimizable : "boolean",
					maximizable : "boolean"
				}, {
					closable : "boolean",
					collapsed : "boolean",
					minimized : "boolean",
					maximized : "boolean",
					closed : "boolean"
				} ]), {
			loadingMessage : (t.attr("loadingMessage") != undefined ? t
					.attr("loadingMessage") : undefined)
		});
	};
	$.fn.panel.defaults = {
		id : null,
		title : null,
		iconCls : null,
		width : "auto",
		height : "auto",
		left : null,
		top : null,
		cls : null,
		panelStyle : "default", //default,primary,success,info,warning,danger
		headerCls : null,
		bodyCls : null,
		style : {},
		href : null,
		cache : true,
		fit : false,
		border : true,
		doSize : true,
		noheader : false,
		content : null,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		collapsed : false,
		minimized : false,
		maximized : false,
		closed : false,
		tools : null,
		href : null,
		loadingMessage : "Loading...",
		extractor : function(str) {
			var reg = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
			var result = reg.exec(str);
			if (result) {
				return result[1];
			} else {
				return str;
			}
		},
		onLoad : function() {
		},
		onBeforeOpen : function() {
		},
		onOpen : function() {
		},
		onBeforeClose : function() {
		},
		onClose : function() {
		},
		onBeforeDestroy : function() {
		},
		onDestroy : function() {
		},
		onResize : function(target, size) {
		},
		onMove : function(target, top) {
		},
		onMaximize : function() {
		},
		onRestore : function() {
		},
		onMinimize : function() {
		},
		onBeforeCollapse : function() {
		},
		onBeforeExpand : function() {
		},
		onCollapse : function() {
		},
		onExpand : function() {
		}
	};
})(jQuery);
