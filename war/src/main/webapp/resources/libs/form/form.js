(function($) {
	/**
	 * submit the form
	 */
	function ajaxSubmit(target, options) {
		options = options || {};
		
		if (options.onSubmit) {
			if (options.onSubmit.call(target) == false) {
				return;
			}
		}
		
		var form = $(target);
		if (options.url) {
			form.attr("action", options.url);
		}
		var frameId = "ui_frame_" + (new Date().getTime());
		var frame = $("<iframe id=" + frameId + " name=" + frameId + "></iframe>")
			.attr("src", window.ActiveXObject ? "javascript:false" : "about:blank")
			.css({
					position : "absolute",
					top : -1000,
					left : -1000
				});
		var t = form.attr("target"), a = form.attr("action");
		form.attr("target", frameId);
		form.attr("method", "POST");
		
		try {
			frame.appendTo("body");
			frame.bind("load", cb);
			form[0].submit();
		} finally {
			form.attr("action", a);
			t ? form.attr("target", t) : form.removeAttr("target");
		}
		
		var checkCount = 10;
		
		function cb() {
			frame.unbind();
			var body = $("#" + frameId).contents().find("body");
			var body0 = body.get(0);
			var data = body0.textContent ? body0.textContent : body0.innerText;
			if (data == "") {
				if (--checkCount) {
					setTimeout(cb, 100);
					return;
				}
				return;
			}
			var ta = body.find(">textarea");
			if (ta.length) {
				data = ta.val();
			} else {
				var pre = body.find(">pre");
				if (pre.length) {
					var pre0 = pre.get(0);
					data = pre0.textContent ? pre0.textContent : pre0.innerText;
				}
			}
			if (options.success) {
				if(options.dataType == "json"){
					options.success(Flywet.parseJSON(data));
				}else{
					options.success(data);
				}
			}
			setTimeout(function() {
				frame.unbind();
				frame.remove();
			}, 100);
		}
		
	}
	
	/**
	 * load form data
	 * if data is a URL string type load from remote site, 
	 * otherwise load from local data object. 
	 */
	function load(target, data) {
		if (!$.data(target, "form")) {
			$.data(target, "form", {
				options : $.extend( {}, $.fn.form.defaults)
			});
		}
		var opts = $.data(target, "form").options;
		if (typeof data == "string") {
			var param = {};
			if (opts.onBeforeLoad.call(target, param) == false) {
				return;
			}
			$.ajax( {
				url : data,
				data : param,
				dataType : "json",
				success : function(data) {
					_load(data);
				},
				error : function() {
					opts.onLoadError.apply(target, arguments);
				}
			});
		} else {
			_load(data);
		}
		
		function _load(data) {
			var form = $(target);
			for ( var name in data) {
				var val = data[name];
				var rr = _loadCheckbox(name, val);
				if (!rr.length) {
					var f = form.find("input[numberboxName=\"" + name + "\"]");
					if (f.length) {
						f.numberbox("setValue", val);
					} else {
						$("input[name=\"" + name + "\"]", form).val(val);
						$("textarea[name=\"" + name + "\"]", form).val(val);
						$("select[name=\"" + name + "\"]", form).val(val);
					}
				}
				_loadCombo(name, val);
			}
			opts.onLoadSuccess.call(target, data);
			clear(target);
		}
		
		function _loadCheckbox(name, val) {
			var form = $(target);
			var rr = $("input[name=\"" + name + "\"][type=radio], input[name=\""
					+ name + "\"][type=checkbox]", form);
			$.fn.prop ? rr.prop("checked", false) : rr.attr("checked", false);
			rr.each(function() {
				var f = $(this);
				if (f.val() == String(val)) {
					$.fn.prop ? f.prop("checked", true) : f.attr("checked",
							true);
				}
			});
			return rr;
		}
		
		function _loadCombo(name, val) {
			var form = $(target);
			var cc = [ "combobox", "combotree", "combogrid", "datetimebox",
					"datebox", "combo" ];
			var c = form.find("[comboName=\"" + name + "\"]");
			if (c.length) {
				for ( var i = 0; i < cc.length; i++) {
					var type = cc[i];
					if (c.hasClass(type + "-f")) {
						if (c[type]("options").multiple) {
							c[type]("setValues", val);
						} else {
							c[type]("setValue", val);
						}
						return;
					}
				}
			}
		}
	}
	
	/**
	 * clear the form fields
	 */
	function clear(target) {
		$("input,select,textarea", target).each(
				function() {
					var t = this.type, tag = this.tagName.toLowerCase();
					if (t == "text" || t == "hidden" || t == "password"
							|| tag == "textarea") {
						this.value = "";
					} else {
						if (t == "file") {
							var _1e = $(this);
							_1e.after(_1e.clone().val(""));
							_1e.remove();
						} else {
							if (t == "checkbox" || t == "radio") {
								this.checked = false;
							} else {
								if (tag == "select") {
									this.selectedIndex = -1;
								}
							}
						}
					}
				});
		if ($.fn.combo) {
			$(".ui-combo-f", target).combo("clear");
		}
		if ($.fn.combobox) {
			$(".ui-combobox-f", target).combobox("clear");
		}
		if ($.fn.combotree) {
			$(".ui-combotree-f", target).combotree("clear");
		}
		if ($.fn.combogrid) {
			$(".ui-combogrid-f", target).combogrid("clear");
		}
		validate(target);
	}
	
	function reset(target) {
		target.reset();
		var t = $(target);
		if ($.fn.combo) {
			t.find(".ui-combo-f").combo("reset");
		}
		if ($.fn.combobox) {
			t.find(".ui-combobox-f").combobox("reset");
		}
		if ($.fn.combotree) {
			t.find(".ui-combotree-f").combotree("reset");
		}
		if ($.fn.combogrid) {
			t.find(".ui-combogrid-f").combogrid("reset");
		}
		if ($.fn.spinner) {
			t.find(".ui-spinner-f").spinner("reset");
		}
		if ($.fn.timespinner) {
			t.find(".ui-timespinner-f").timespinner("reset");
		}
		if ($.fn.numberbox) {
			t.find(".ui-numberbox-f").numberbox("reset");
		}
		if ($.fn.numberspinner) {
			t.find(".ui-numberspinner-f").numberspinner("reset");
		}
		validate(target);
	}
	
	function setForm(target) {
		var options = $.data(target, "form").options;
		var form = $(target);
		form.unbind(".form").bind("submit.form", function() {
			setTimeout(function() {
				ajaxSubmit(target, options);
			}, 0);
			return false;
		});
	}
	
	function validate(target) {
		if ($.fn.validatebox) {
			var t = $(target);
			t.find(".validatebox-text:not(:disabled)").validatebox("validate");
			var inv = t.find(".validatebox-invalid");
			inv.filter(":not(:disabled):first").focus();
			return inv.length == 0;
		}
		return true;
	}
	
	$.fn.form = function(options, param) {
		if (typeof options == "string") {
			return $.fn.form.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			if (!$.data(this, "form")) {
				$.data(this, "form", {
					options : $.extend( {}, $.fn.form.defaults, options)
				});
			}
			setForm(this);
		});
	};
	$.fn.form.methods = {
		submit : function(jq, options) {
			return jq.each(function() {
				ajaxSubmit(this, $.extend( {}, $.fn.form.defaults, options || {}));
			});
		},
		load : function(jq, data) {
			return jq.each(function() {
				load(this, data);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				clear(this);
			});
		},
		reset : function(jq) {
			return jq.each(function() {
				reset(this);
			});
		},
		validate : function(jq) {
			return validate(jq[0]);
		}
	};
	$.fn.form.defaults = {
		url : null,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(data) {
		},
		onBeforeLoad : function(data) {
		},
		onLoadSuccess : function(data) {
		},
		onLoadError : function() {
		}
	};
})(jQuery);
