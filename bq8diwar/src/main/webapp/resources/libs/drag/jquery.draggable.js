(function($) {
	
	var IS_DRAG = false;
	
	/**
	 * 计算位置
	 */
	function _calPos(e) {
		var t = $.data(e.data.target, "draggable");
		var options = t.options;
		var proxy = t.proxy;
		var data = e.data;
		var left = data.startLeft + e.pageX - data.startX;
		var top = data.startTop + e.pageY - data.startY;
		if (proxy) {
			if (proxy.parent()[0] == document.body) {
				if (options.deltaX != null && options.deltaX != undefined) {
					left = e.pageX + options.deltaX;
				} else {
					left = e.pageX - e.data.offsetWidth;
				}
				if (options.deltaY != null && options.deltaY != undefined) {
					top = e.pageY + options.deltaY;
				} else {
					top = e.pageY - e.data.offsetHeight;
				}
			} else {
				if (options.deltaX != null && options.deltaX != undefined) {
					left += e.data.offsetWidth + options.deltaX;
				}
				if (options.deltaY != null && options.deltaY != undefined) {
					top += e.data.offsetHeight + options.deltaY;
				}
			}
		}
		if (e.data.parent != document.body) {
			left += $(e.data.parent).scrollLeft();
			top += $(e.data.parent).scrollTop();
		}
		if (options.axis == "h") {
			data.left = left;
		} else {
			if (options.axis == "v") {
				data.top = top;
			} else {
				data.left = left;
				data.top = top;
			}
		}
	}
	
	/**
	 * 移动代理
	 */
	function _moveProxy(e) {
		var t = $.data(e.data.target, "draggable");
		var options = t.options;
		var proxy = t.proxy;
		if (!proxy) {
			proxy = $(e.data.target);
		}
		proxy.css( {
			left : e.data.left,
			top : e.data.top
		});
		$("body").css("cursor", options.cursor);
	}
	
	function _onMouseDown(e) {
		IS_DRAG = true;
		var t = $.data(e.data.target, "draggable");
		var options = t.options;
		var droppable = $(".droppable").filter(function() {
			return e.data.target != this;
		}).filter(function() {
			var accept = $.data(this, "droppable").options.accept;
			if (accept) {
				return $(accept).filter(function() {
					return this == e.data.target;
				}).length > 0;
			} else {
				return true;
			}
		});
		t.droppables = droppable;
		var proxy = t.proxy;
		if (!proxy) {
			if (options.proxy) {
				if (options.proxy == "clone") {
					proxy = $(e.data.target).clone().insertAfter(e.data.target);
				} else {
					proxy = options.proxy.call(e.data.target, e.data.target);
				}
				t.proxy = proxy;
			} else {
				proxy = $(e.data.target);
			}
		}
		proxy.css("position", "absolute");
		_calPos(e);
		_moveProxy(e);
		options.onStartDrag.call(e.data.target, e);
		return false;
	}
	
	function _onMouseMove(e) {
		var t = $.data(e.data.target, "draggable");
		_calPos(e);
		if (t.options.onDrag.call(e.data.target, e) != false) {
			_moveProxy(e);
		}
		var obj = e.data.target;
		t.droppables
				.each(function() {
					var _thisObj = $(this);
					if (_thisObj.droppable("options").disabled) {
						return;
					}
					var p2 = _thisObj.offset();
					if (e.pageX > p2.left
							&& e.pageX < p2.left + _thisObj.outerWidth()
							&& e.pageY > p2.top
							&& e.pageY < p2.top + _thisObj.outerHeight()) {
						if (!this.entered) {
							e.data.x=e.pageX-p2.left;
							e.data.y=e.pageY-p2.top;
							$(this).trigger("_dragenter", [ obj, e.data ]);
							this.entered = true;
						}
						$(this).trigger("_dragover", [ obj, e.data ]);
					} else {
						if (this.entered) {
							$(this).trigger("_dragleave", [ obj, e.data ]);
							this.entered = false;
						}
					}
				});
		return false;
	}
	
	function _onMouseUp(e) {
		IS_DRAG = false;
		_onMouseMove(e);
		var t = $.data(e.data.target, "draggable");
		var proxy = t.proxy;
		var options = t.options;
		if (options.revert) {
			if (_1b() == true) {
				$(e.data.target).css( {
					position : e.data.startPosition,
					left : e.data.startLeft,
					top : e.data.startTop
				});
			} else {
				if (proxy) {
					var _1c, top;
					if (proxy.parent()[0] == document.body) {
						_1c = e.data.startX - e.data.offsetWidth;
						top = e.data.startY - e.data.offsetHeight;
					} else {
						_1c = e.data.startLeft;
						top = e.data.startTop;
					}
					proxy.animate( {
						left : _1c,
						top : top
					}, function() {
						_1d();
					});
				} else {
					$(e.data.target).animate( {
						left : e.data.startLeft,
						top : e.data.startTop
					}, function() {
						$(e.data.target).css("position", e.data.startPosition);
					});
				}
			}
		} else {
			$(e.data.target).css( {
				position : "absolute",
				left : e.data.left,
				top : e.data.top
			});
			_1b();
		}
		options.onStopDrag.call(e.data.target, e);
		$(document).unbind(".draggable");
		setTimeout(function() {
			$("body").css("cursor", "");
		}, 100);
		
		function _1d() {
			if (proxy) {
				proxy.remove();
			}
			t.proxy = null;
		}
		
		function _1b() {
			var _1e = false;
			t.droppables.each(function() {
				var _thisObj = $(this);
				if (_thisObj.droppable("options").disabled) {
					return;
				}
				var p2 = _thisObj.offset();
				if (e.pageX > p2.left && e.pageX < p2.left + _thisObj.outerWidth()
						&& e.pageY > p2.top
						&& e.pageY < p2.top + _thisObj.outerHeight()) {
					if (options.revert) {
						$(e.data.target).css( {
							position : e.data.startPosition,
							left : e.data.startLeft,
							top : e.data.startTop
						});
					}
					_1d();
					e.data.x=e.pageX-p2.left;
					e.data.y=e.pageY-p2.top;
					$(this).trigger("_drop", [ e.data.target, e.data ]);
					_1e = true;
					this.entered = false;
					return false;
				}
			});
			if (!_1e && !options.revert) {
				_1d();
			}
			return _1e;
		}
		
		return false;
	}
	
	$.fn.draggable = function(options, param) {
		if (typeof options == "string") {
			return $.fn.draggable.methods[options](this, param);
		}
		return this.each(function() {
			var opts;
			var t = $.data(this, "draggable");
			if (t) {
				t.handle.unbind(".draggable");
				opts = $.extend(t.options, options);
			} else {
				opts = $.extend( {}, $.fn.draggable.defaults, $.fn.draggable
						.parseOptions(this), options || {});
			}
			if (opts.disabled == true) {
				$(this).css("cursor", "");
				return;
			}
			var handle = null;
			if (typeof opts.handle == "undefined" || opts.handle == null) {
				handle = $(this);
			} else {
				handle = (typeof opts.handle == "string" ? $(opts.handle, this)
						: opts.handle);
			}
			$.data(this, "draggable", {
				options : opts,
				handle : handle
			});
			handle.unbind(".draggable").bind("mousemove.draggable", {
				target : this
			}, function(e) {
				if (IS_DRAG) {
					return;
				}
				var opts = $.data(e.data.target, "draggable").options;
				if (_26(e)) {
					$(this).css("cursor", opts.cursor);
				} else {
					$(this).css("cursor", "");
				}
			}).bind("mouseleave.draggable", {
				target : this
			}, function(e) {
				$(this).css("cursor", "");
			}).bind("mousedown.draggable", {
				target : this
			}, function(e) {
				if (_26(e) == false) {
					return;
				}
				$(this).css("cursor", "");
				var pos = $(e.data.target).position();
				var offset = $(e.data.target).offset();
				var d = {
					startPosition : $(e.data.target).css("position"),
					startLeft : pos.left,
					startTop : pos.top,
					left : pos.left,
					top : pos.top,
					startX : e.pageX,
					startY : e.pageY,
					offsetWidth : (e.pageX - offset.left),
					offsetHeight : (e.pageY - offset.top),
					target : e.data.target,
					parent : $(e.data.target).parent()[0]
				};
				$.extend(e.data, d);
				var opts = $.data(e.data.target, "draggable").options;
				if (opts.onBeforeDrag.call(e.data.target, e) == false) {
					return;
				}
				$(document).bind("mousedown.draggable", e.data, _onMouseDown);
				$(document).bind("mousemove.draggable", e.data, _onMouseMove);
				$(document).bind("mouseup.draggable", e.data, _onMouseUp);
			});
			
			function _26(e) {
				var target = $.data(e.data.target, "draggable");
				var handle = target.handle;
				var offset = $(handle).offset();
				var width = $(handle).outerWidth();
				var height = $(handle).outerHeight();
				var t = e.pageY - offset.top;
				var r = offset.left + width - e.pageX;
				var b = offset.top + height - e.pageY;
				var l = e.pageX - offset.left;
				return Math.min(t, r, b, l) > target.options.edge;
			}
			
		});
	};
	$.fn.draggable.methods = {
		options : function(jq) {
			return $.data(jq[0], "draggable").options;
		},
		proxy : function(jq) {
			return $.data(jq[0], "draggable").proxy;
		},
		enable : function(jq) {
			return jq.each(function() {
				$(this).draggable( {
					disabled : false
				});
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				$(this).draggable( {
					disabled : true
				});
			});
		}
	};
	$.fn.draggable.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, YonYou.parseOptions(target, [ "cursor", "handle",
				"axis", {
					"revert" : "boolean",
					"deltaX" : "number",
					"deltaY" : "number",
					"edge" : "number"
				} ]), {
			disabled : (t.attr("disabled") ? true : undefined)
		});
	};
	$.fn.draggable.defaults = {
		proxy : null,
		revert : false,
		cursor : "move",
		deltaX : null,
		deltaY : null,
		handle : null,
		disabled : false,
		edge : 0,
		axis : null,
		onBeforeDrag : function(e) {
		},
		onStartDrag : function(e) {
		},
		onDrag : function(e) {
		},
		onStopDrag : function(e) {
		}
	};
})(jQuery);
