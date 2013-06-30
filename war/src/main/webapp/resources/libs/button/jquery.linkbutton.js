(function($) {
	
	function _init(target) {
		var opts = $.data(target, "linkbutton").options;
		$(target).empty();
		$(target).addClass("l-btn");
		if (opts.id) {
			$(target).attr("id", opts.id);
		} else {
			$(target).attr("id", "");
		}
		if (opts.plain) {
			$(target).addClass("l-btn-plain");
		} else {
			$(target).removeClass("l-btn-plain");
		}
		if (opts.text) {
			$(target).html(opts.text).wrapInner(
					"<span class=\"l-btn-left\">"
							+ "<span class=\"l-btn-text\">" + "</span>"
							+ "</span>");
			if (opts.iconCls) {
				$(target).find(".l-btn-text").addClass(opts.iconCls).addClass(
						opts.iconAlign == "left" ? "l-btn-icon-left"
								: "l-btn-icon-right");
			}
		} else {
			$(target).html("&nbsp;").wrapInner(
					"<span class=\"l-btn-left\">"
							+ "<span class=\"l-btn-text\">"
							+ "<span class=\"l-btn-empty\"></span>" + "</span>"
							+ "</span>");
			if (opts.iconCls) {
				$(target).find(".l-btn-empty").addClass(opts.iconCls);
			}
		}
		$(target).unbind(".linkbutton").bind("focus.linkbutton", function() {
			if (!opts.disabled) {
				$(this).find("span.l-btn-text").addClass("l-btn-focus");
			}
		}).bind("blur.linkbutton", function() {
			$(this).find("span.l-btn-text").removeClass("l-btn-focus");
		});
		_toggle(target, opts.disabled);
	}
	
	function _toggle(target, state) {
		var t = $.data(target, "linkbutton");
		if (state) {
			t.options.disabled = true;
			var href = $(target).attr("href");
			if (href) {
				t.href = href;
				$(target).attr("href", "javascript:void(0)");
			}
			if (target.onclick) {
				t.onclick = target.onclick;
				target.onclick = null;
			}
			$(target).addClass("l-btn-disabled");
		} else {
			t.options.disabled = false;
			if (t.href) {
				$(target).attr("href", t.href);
			}
			if (t.onclick) {
				target.onclick = t.onclick;
			}
			$(target).removeClass("l-btn-disabled");
		}
	}
	
	$.fn.linkbutton = function(options, param) {
		if (typeof options == "string") {
			return $.fn.linkbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var _b = $.data(this, "linkbutton");
			if (_b) {
				$.extend(_b.options, options);
			} else {
				$.data(this, "linkbutton", {
					options : $.extend( {}, $.fn.linkbutton.defaults,
							$.fn.linkbutton.parseOptions(this), options)
				});
				$(this).removeAttr("disabled");
			}
			_init(this);
		});
	};
	
	$.fn.linkbutton.methods = {
		options : function(jq) {
			return $.data(jq[0], "linkbutton").options;
		},
		enable : function(jq) {
			return jq.each(function() {
				_toggle(this, false);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				_toggle(this, true);
			});
		}
	};
	
	$.fn.linkbutton.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, Plywet.parseOptions(target, [ "id", "iconCls",
				"iconAlign", {
					plain : "boolean"
				} ]), {
			disabled : (t.attr("disabled") ? true : undefined),
			text : $.trim(t.html()),
			iconCls : (t.attr("icon") || t.attr("iconCls"))
		});
	};
	
	$.fn.linkbutton.defaults = {
		id : null,
		disabled : false,
		plain : false,
		text : "",
		iconCls : null,
		iconAlign : "left"
	};
})(jQuery);
