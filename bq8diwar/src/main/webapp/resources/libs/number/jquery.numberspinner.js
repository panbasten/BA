/**
 * jQuery EasyUI 1.3.1
 *
 * Licensed under the GPL terms
 * To use it on other terms please contact us
 *
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 *
 */
(function($){
    function _initNumSpinner(target){
        $(target).addClass("numberspinner-f");
        var options = $.data(target, "numberspinner").options;
        $(target).spinner(options).numberbox(options);
    };
    function _increase(target, isAdd){
        var options = $.data(target, "numberspinner").options;
        var value = parseFloat($(target).numberbox("getValue") || options.value) || 0;
        if (isAdd == true) {
            value -= options.increment;
        }
        else {
            value += options.increment;
        }
        $(target).numberbox("setValue", value);
    };
    $.fn.numberspinner = function(options, params){
        if (typeof options == "string") {
            var method = $.fn.numberspinner.methods[options];
            if (method) {
                return method(this, params);
            }
            else {
                return this.spinner(options, params);
            }
        }
        options = options ||
        {};
        return this.each(function(){
            var numberspinner = $.data(this, "numberspinner");
            if (numberspinner) {
                $.extend(numberspinner.options, options);
            }
            else {
                $.data(this, "numberspinner", {
                    options: $.extend({}, $.fn.numberspinner.defaults, $.fn.numberspinner.parseOptions(this), options)
                });
            }
            _initNumSpinner(this);
        });
    };
    $.fn.numberspinner.methods = {
        options: function(jq){
            var options = $.data(jq[0], "numberspinner").options;
            return $.extend(options, {
                value: jq.numberbox("getValue"),
                originalValue: jq.numberbox("options").originalValue
            });
        },
        setValue: function(jq, value){
            return jq.each(function(){
                $(this).numberbox("setValue", value);
            });
        },
        getValue: function(jq){
            return jq.numberbox("getValue");
        },
        clear: function(jq){
            return jq.each(function(){
                $(this).spinner("clear");
                $(this).numberbox("clear");
            });
        },
        reset: function(jq){
            return jq.each(function(){
                var options = $(this).numberspinner("options");
                $(this).numberspinner("setValue", options.originalValue);
            });
        }
    };
    $.fn.numberspinner.parseOptions = function(target){
        return $.extend({}, $.fn.spinner.parseOptions(target), $.fn.numberbox.parseOptions(target), {});
    };
    $.fn.numberspinner.defaults = $.extend({}, $.fn.spinner.defaults, $.fn.numberbox.defaults, {
        spin: function(value){
            _increase(this, value);
        }
    });
})(jQuery);

