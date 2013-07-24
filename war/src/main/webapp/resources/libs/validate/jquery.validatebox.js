(function($) {
	
	/**
	 * 初始化
	 */
	function _init(target) {
		$(target).addClass("ui-validatebox-text");
	}
	
	/**
	 * 销毁
	 */
	function _destroy(target) {
		var vb = $.data(target, "validatebox");
		vb.validating = false;
		var tip = vb.tip;
		if (tip) {
			tip.remove();
		}
		$(target).unbind();
		$(target).remove();
	}
	
	/**
	 * 初始化事件
	 */
	function _initEvents(target) {
		var t = $(target);
		var vb = $.data(target, "validatebox");
		t.unbind(".ui-validatebox").bind("focus.ui-validatebox", function() {
			vb.validating = true;
			vb.value = undefined;
			(function() {
				if (vb.validating) {
					if (vb.value != t.val()) {
						vb.value = t.val();
						_validate(target);
					} else {
						_showValidateBox(target);
					}
					setTimeout(arguments.callee, 200);
				}
			})();
		}).bind("blur.ui-validatebox", function() {
			vb.validating = false;
			_removeValidateBox(target);
		}).bind("mouseenter.ui-validatebox", function() {
			if (t.hasClass("ui-validatebox-invalid")) {
				_initValidateBox(target);
			}
		}).bind("mouseleave.ui-validatebox", function() {
			if (!vb.validating) {
				_removeValidateBox(target);
			}
		});
	}
	
	/**
	 * 初始化校验框
	 */
	function _initValidateBox(target) {
		var msg = $.data(target, "validatebox").message;
		var tip = $.data(target, "validatebox").tip;
		if (!tip) {
			tip = $("<div class=\"ui-validatebox-tip\">"
				+ "<span class=\"ui-validatebox-tip-content\">"
				+ "</span>"
				+ "<span class=\"ui-validatebox-tip-pointer\">"
				+ "</span>" + "</div>").appendTo("body");
			$.data(target, "validatebox").tip = tip;
		}
		tip.find(".ui-validatebox-tip-content").html(msg);
		_showValidateBox(target);
	}
	
	/**
	 * 显示校验框
	 */
	function _showValidateBox(target) {
		var vb = $.data(target, "validatebox");
		if (!vb) {
			return;
		}
		var tip = vb.tip;
		if (tip) {
			var box = $(target);
			var tipPointer = tip.find(".ui-validatebox-tip-pointer");
			var tipContent = tip.find(".ui-validatebox-tip-content");
			tip.show();
			tip.css("top", box.offset().top
					- (tipContent._outerHeight() - box._outerHeight()) / 2);
			if (vb.options.tipPosition == "left") {
				tip.css("left", box.offset().left - tip._outerWidth());
				tip.addClass("ui-validatebox-tip-left");
			} else {
				tip.css("left", box.offset().left + box._outerWidth());
				tip.removeClass("ui-validatebox-tip-left");
			}
			tipPointer.css("top", (tipContent._outerHeight() - tipPointer._outerHeight()) / 2);
		}
	}
	
	/**
	 * 移出校验框
	 */
	function _removeValidateBox(target) {
		var tip = $.data(target, "validatebox").tip;
		if (tip) {
			tip.remove();
			$.data(target, "validatebox").tip = null;
		}
	}
	
	/**
	 * 验证
	 */
	function _validate(target) {
		var vb = $.data(target, "validatebox");
		var opts = $.data(target, "validatebox").options;
		var tip = $.data(target, "validatebox").tip;
		var box = $(target);
		var val = box.val();
		
		function setMessage(msg) {
			$.data(target, "validatebox").message = msg;
		}
		
		if (opts.required) {
			if (val == "") {
				box.addClass("ui-validatebox-invalid");
				setMessage(opts.missingMessage);
				if (vb.validating) {
					_initValidateBox(target);
				}
				return false;
			}
		}
		if (opts.validType) {
			var validType = /([a-zA-Z_]+)(.*)/.exec(opts.validType);
			var rule = opts.rules[validType[1]];
			if (val && rule) {
				var result = eval(validType[2]);
				if (!rule["validator"](val, result)) {
					box.addClass("ui-validatebox-invalid");
					var msg = rule["message"];
					if (result) {
						for ( var i = 0; i < result.length; i++) {
							msg = msg.replace(new RegExp("\\{" + i + "\\}", "g"), result[i]);
						}
					}
					setMessage(opts.invalidMessage || msg);
					if (vb.validating) {
						_initValidateBox(target);
					}
					return false;
				}
			}
		}
		box.removeClass("ui-validatebox-invalid");
		_removeValidateBox(target);
		return true;
	}
	
	$.fn.validatebox = function(options, param) {
		if (typeof options == "string") {
			return $.fn.validatebox.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var vb = $.data(this, "validatebox");
			if (vb) {
				$.extend(vb.options, options);
			} else {
				_init(this);
				$.data(this, "validatebox", {
					options : $.extend( {}, $.fn.validatebox.defaults,
							$.fn.validatebox.parseOptions(this), options)
				});
			}
			_initEvents(this);
		});
	};
	
	$.fn.validatebox.methods = {
		destroy : function(jq) {
			return jq.each(function() {
				_destroy(this);
			});
		},
		validate : function(jq) {
			return jq.each(function() {
				_validate(this);
			});
		},
		isValid : function(jq) {
			return _validate(jq[0]);
		}
	};
	$.fn.validatebox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, Flywet.parseOptions(target, [ "validType",
				"missingMessage", "invalidMessage", "tipPosition" ]), {
			required : (t.attr("required") ? true : undefined)
		});
	};
	$.fn.validatebox.defaults = {
		required : false,
		validType : null,
		missingMessage : "This field is required.",
		invalidMessage : null,
		tipPosition : "right",
		rules : {
			email : {
				validator : function(val) {
					return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
							.test(val);
				},
				message : "Please enter a valid email address."
			},
			url : {
				validator : function(val) {
					return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
							.test(val);
				},
				message : "Please enter a valid URL."
			},
			length : {
				validator : function(val, param) {
					var len = $.trim(val).length;
					return len >= param[0] && len <= param[1];
				},
				message : "Please enter a value between {0} and {1}."
			},
			remote : {
				validator : function(val, param) {
					var data = {};
					data[param[1]] = val;
					var response = $.ajax( {
						url : param[0],
						dataType : "json",
						data : data,
						async : false,
						cache : false,
						type : "post"
					}).responseText;
					return response == "true";
				},
				message : "Please fix this field."
			}
		}
	};
})(jQuery);
