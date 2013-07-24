(function($) {
	var resize_hold = false;
	$.fn.resizable = function(options, param) {
		if (typeof options == "string") {
			return $.fn.resizable.methods[options](this, param);
		}
		
		function calculateSize(e) {
			var data = e.data;
			var opts = $.data(data.target, "resizable").options;
			if (data.dir.indexOf("e") != -1) {
				var w = data.startWidth + e.pageX - data.startX;
				w = Math.min(Math.max(w, opts.minWidth), opts.maxWidth);
				data.width = w;
			}
			if (data.dir.indexOf("s") != -1) {
				var h = data.startHeight + e.pageY - data.startY;
				h = Math.min(Math.max(h, opts.minHeight), opts.maxHeight);
				data.height = h;
			}
			if (data.dir.indexOf("w") != -1) {
				data.width = data.startWidth - e.pageX + data.startX;
				if (data.width >= opts.minWidth && data.width <= opts.maxWidth) {
					data.left = data.startLeft + e.pageX - data.startX;
				}
			}
			if (data.dir.indexOf("n") != -1) {
				data.height = data.startHeight - e.pageY + data.startY;
				if (data.height >= opts.minHeight && data.height <= opts.maxHeight) {
					data.top = data.startTop + e.pageY - data.startY;
				}
			}
		}
		
		/**
		 * 为对象设置属性
		 */
		function setSize(e) {
			var data = e.data;
			var target = data.target;
			$(target).css( {
				left : data.left,
				top : data.top
			});
			$(target)._outerWidth(data.width)._outerHeight(data.height);
		}
		
		function onMouseDownFunc(e) {
			resize_hold = true;
			$.data(e.data.target, "resizable").options.onStartResize.call(
					e.data.target, e);
			return false;
		}
		
		function onMouseMoveFunc(e) {
			calculateSize(e);
			if ($.data(e.data.target, "resizable").options.onResize.call(e.data.target, e) != false) {
				setSize(e);
			}
			return false;
		}
		
		function onMouseUpFunc(e) {
			resize_hold = false;
			calculateSize(e, true);
			setSize(e);
			$.data(e.data.target, "resizable").options.onStopResize.call(
					e.data.target, e);
			$(document).unbind(".resizable");
			$("body").css("cursor", "");
			return false;
		}
		
		return this.each(function() {
			var opts = null;
			var state = $.data(this, "resizable");
			if (state) {
				$(this).unbind(".resizable");
				opts = $.extend(state.options, options || {});
			} else {
				opts = $.extend( {}, $.fn.resizable.defaults, $.fn.resizable
						.parseOptions(this), options || {});
				$.data(this, "resizable", {
					options : opts
				});
			}
			if (opts.disabled == true) {
				return;
			}
			$(this).bind("mousemove.resizable", {
				target : this
			}, function(e) {
				if (resize_hold) {
					return;
				}
				var dir = getDirection(e);
				if (dir == "") {
					$(e.data.target).css("cursor", "");
				} else {
					$(e.data.target).css("cursor", dir + "-resize");
				}
			}).bind("mouseleave.resizable", { target : this }, function(e) {
				$(e.data.target).css("cursor", "");
			}).bind("mousedown.resizable", { target : this }, function(e) {
				var dir = getDirection(e);
				if (dir == "") {
					return;
				}
				
				function getPosition(css) {
					var val = parseInt($(e.data.target).css(css));
					if (isNaN(val)) {
						return 0;
					} else {
						return val;
					}
				}
				
				var data = {
					target : e.data.target,
					dir : dir,
					startLeft : getPosition("left"),
					startTop : getPosition("top"),
					left : getPosition("left"),
					top : getPosition("top"),
					startX : e.pageX,
					startY : e.pageY,
					startWidth : $(e.data.target).outerWidth(),
					startHeight : $(e.data.target).outerHeight(),
					width : $(e.data.target).outerWidth(),
					height : $(e.data.target).outerHeight(),
					deltaWidth : $(e.data.target).outerWidth()
							- $(e.data.target).width(),
					deltaHeight : $(e.data.target).outerHeight()
							- $(e.data.target).height()
				};
				$(document).bind("mousedown.resizable", data, onMouseDownFunc);
				$(document).bind("mousemove.resizable", data, onMouseMoveFunc);
				$(document).bind("mouseup.resizable", data, onMouseUpFunc);
				$("body").css("cursor", dir + "-resize");
			});
			
			/**
			 * 获得可调整方向
			 */
			function getDirection(e) {
				// 调整对象
				var tt = $(e.data.target);
				var dir = "";
				var offset = tt.offset();
				var ow = tt.outerWidth();
				var oh = tt.outerHeight();
				var edge = opts.edge;
				if (e.pageY > offset.top && e.pageY < offset.top + edge) {
					dir += "n";
				} else {
					if (e.pageY < offset.top + oh
							&& e.pageY > offset.top + oh - edge) {
						dir += "s";
					}
				}
				if (e.pageX > offset.left && e.pageX < offset.left + edge) {
					dir += "w";
				} else {
					if (e.pageX < offset.left + ow
							&& e.pageX > offset.left + ow - edge) {
						dir += "e";
					}
				}
				var handles = opts.handles.split(",");
				for ( var i = 0; i < handles.length; i++) {
					var handle = handles[i].replace(/(^\s*)|(\s*$)/g, "");
					if (handle == "all" || handle == dir) {
						return dir;
					}
				}
				return "";
			}
			
		});
	};
	
	$.fn.resizable.methods = {
		options : function(jq) {
			return $.data(jq[0], "resizable").options;
		},
		enable : function(jq) {
			return jq.each(function() {
				$(this).resizable( {
					disabled : false
				});
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				$(this).resizable( {
					disabled : true
				});
			});
		}
	};
	$.fn.resizable.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, Flywet.parseOptions(target, [ "handles", {
			minWidth : "number",
			minHeight : "number",
			maxWidth : "number",
			maxHeight : "number",
			edge : "number"
		} ]), {
			disabled : (t.attr("disabled") ? true : undefined)
		});
	};
	$.fn.resizable.defaults = {
		disabled : false,
		handles : "n, e, s, w, ne, se, sw, nw, all",
		minWidth : 10,
		minHeight : 10,
		maxWidth : 10000,
		maxHeight : 10000,
		edge : 5,
		onStartResize : function(e) {
		},
		onResize : function(e) {
		},
		onStopResize : function(e) {
		}
	};
})(jQuery);
