(function($) {
	function _init(target) {
		var opts = $.data(target, "menubutton").options;
		var t = $(target);
		t.removeClass("m-btn-active m-btn-plain-active").addClass("m-btn");
		t.linkbutton($.extend( {}, opts, {
			text : opts.text + "<span class=\"m-btn-downarrow\">&nbsp;</span>"
		}));
		if (opts.menu) {
			$(opts.menu).menu(
				{
					onShow : function() {
						t.addClass((opts.plain == true) ? "m-btn-plain-active"
							: "m-btn-active");
					},
					onHide : function() {
						t.removeClass((opts.plain == true) ? "m-btn-plain-active"
							: "m-btn-active");
					}
				});
		}
		_toggle(target, opts.disabled);
	}

	function _toggle(target, state) {
		var opts = $.data(target, "menubutton").options;
		opts.disabled = state;
		var t = $(target);
		if (state) {
			t.linkbutton("disable");
			t.unbind(".menubutton");
		} else {
			t.linkbutton("enable");
			t.unbind(".menubutton");
			t.bind("click.menubutton", function() {
				_activeMenu();
				return false;
			});
			var duration = null;
			t.bind("mouseenter.menubutton", function() {
				duration = setTimeout(function() {
					_activeMenu();
				}, opts.duration);
				return false;
			}).bind("mouseleave.menubutton", function() {
				if (duration) {
					clearTimeout(duration);
				}
			});
		}
		
		function _activeMenu() {
			if (!opts.menu) {
				return;
			}
			$("body>div.menu-top").menu("hide");
			$(opts.menu).menu("show", {
				alignTo : t
			});
			t.blur();
		}
	}

	$.fn.menubutton = function(options, param) {
		if (typeof options == "string") {
			return $.fn.menubutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "menubutton");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "menubutton", {
					options : $.extend( {}, $.fn.menubutton.defaults,
							$.fn.menubutton.parseOptions(this), options)
				});
				$(this).removeAttr("disabled");
			}
			_init(this);
		});
	};

	$.fn.menubutton.methods = {
		options : function(jq) {
			return $.data(jq[0], "menubutton").options;
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
		},
		destroy : function(jq) {
			return jq.each(function() {
				var _f = $(this).menubutton("options");
				if (_f.menu) {
					$(_f.menu).menu("destroy");
				}
				$(this).remove();
			});
		}
	};

	$.fn.menubutton.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.linkbutton.parseOptions(target), Flywet
				.parseOptions(target, [ "menu", {
					plain : "boolean",
					duration : "number"
				} ]));
	};

	$.fn.menubutton.defaults = $.extend( {}, $.fn.linkbutton.defaults, {
		plain : true,
		menu : null,
		duration : 100
	});
})(jQuery);
