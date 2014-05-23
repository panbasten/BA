(function($) {
	
	function _initSeparator(target){
		var separator = $("<span></span>").insertAfter(target);
		$(target).addClass("ui-button-original").hide().appendTo(separator);
		separator.addClass("ui-separator")
			.append($("<span class='ui-icon ui-icon-no-hover ui-icon-grip-dotted-vertical-narrow'></span>"));
	
		return separator;
	}
	
	function _initButton(target){
		var btn = $("<button type='button'></button>").insertAfter(target);
		$(target).addClass("ui-button-original").hide().appendTo(btn);
		
		var opts = $.data(target, "pushbutton").options;
		
		var menuItems = opts.menuItems;
		
		
		
		var iconCls = opts.iconCls,
			iconAlign = opts.iconAlign,
			label = opts.label,
			clazz = "";
		
		if(opts.menuItems){
			// 有文字，无图片
			if(label && iconCls == undefined){
				clazz = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-text-only";
			}
			// 有文字，有图片
			else if(label && iconCls){
				if(iconAlign == "right"){
					clazz = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-text-icon-right";
				}else{
					clazz = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-text-icon-left";
				}
			}
			// 无文字，只有图片
			else if(label== undefined && iconCls){
				clazz = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-icon-only";
			}
		}else{
			// 有文字，无图片
			if(label && iconCls == undefined){
				clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
			}
			// 有文字，有图片
			else if(label && iconCls){
				if(iconAlign == "right"){
					clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-right";
				}else{
					clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-left";
				}
			}
			// 无文字，只有图片
			else if(label== undefined && iconCls){
				clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only";
			}
		}
		
		if(opts.state == "disabled"){
			clazz = clazz + " ui-state-disabled";
		}else if(opts.state == "active"){
			clazz = clazz + " ui-state-active";
		}
		
		if(opts.btnStyle) {
			clazz = clazz + " btn-" + opts.btnStyle;
		}
		
		btn.addClass(clazz);
		
		// 设置图片
		if(iconCls){
			if(iconAlign == "right"){
				$("<span></span>").addClass("ui-button-icon-right ui-icon "+iconCls).appendTo(btn);
			}else{
				$("<span></span>").addClass("ui-button-icon-left ui-icon "+iconCls).appendTo(btn);
			}
		}
		
		// 设置文字
		var labelSpan = $("<span></span>").addClass("ui-button-text").appendTo(btn);
		if(label){
			labelSpan.html(label);
		}else{
			labelSpan.html("text");
		}
		
		// mouseOver
		btn.bind("mouseover",opts,function(event){
			$(this).addClass('ui-state-hover');
			if(event.data && event.data.events && event.data.events["mouseover"]){
				Flywet.invokeFunction(event.data.events["mouseover"],event,event.data);
			}
		});
		
		// mouseOut
		btn.bind("mouseout",opts,function(event){
			$(this).removeClass('ui-state-hover');
			if(event.data && event.data.events && event.data.events["mouseout"]){
				Flywet.invokeFunction(event.data.events["mouseout"],event,event.data);
			}
		});
		
		// other event
		Flywet.attachBehaviors(btn,Flywet.assembleBehaviors(opts.events,["mouseover","mouseout"]),opts);
		Flywet.attachBehaviorsOn(btn,opts);
		
		return btn;
	}
	
	function _init(target){
		var opts = $.data(target, "pushbutton").options;
		var t = $(target);
		
		// 清空对象内容
		t.empty();
		
		// 设置ID
		t.attr("id", (opts.id)?(opts.id):"");
		
		if(opts.title){
			t.attr("title", opts.title);
		}
		
		// 根据类型设置
		if (opts.type == "separator") {
			return _initSeparator(target);
		}else{
			return _initButton(target);
		}
	}
	
	function _toggle(target, state){
		var t = $.data(target, "pushbutton");
		if (state) {
		}else{
		}
	}
	
	$.fn.pushbutton = function(options, param) {
		if(typeof options == "string"){
			return $.fn.pushbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "pushbutton");
			if(t){
				$.extend(t.options, options);
			}else{
				$.data(this, "pushbutton", {
					options : $.extend(
							{},
							$.fn.pushbutton.defaults,
							$.fn.pushbutton.parseOptions(this),
							options
						)
				});
			}
			var btn = _init(this);
			$.data(this, "pushbutton", {button:btn} );
			
			// TODO 下拉列表
		});
	};
	
	$.fn.pushbutton.methods = {
		options : function(jq) {
			return $.data(jq[0], "pushbutton").options;
		},
		enable : function(jq) {
			return jq.each(function(){
				_toggle(this, false);
			});
		},
		disable : function(jq) {
			return jq.each(function(){
				_toggle(this, true);
			});
		}
	};
	
	$.fn.pushbutton.parseOptions = function(target) {
		var t = $(target);
		return $.extend(
				{},
				Flywet.parseOptions(target, ["id", "type", "state", "title", "label", "btnStyle", "iconCls", "iconAlign"])
			);
	};
	
	$.fn.pushbutton.defaults = {
		id : null,
		type : "button",
		label : null,
		btnStyle : "default",//default,primary,success,info,warning,danger
		iconCls : null,
		iconAlign : "left"
	};
	
})(jQuery);


Flywet.widget.PushButton=function(cfg){
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	
	this.init();
};

Flywet.extend(Flywet.widget.PushButton, Flywet.widget.BaseWidget);

Flywet.widget.PushButton.prototype.init = function() {
	if(this.cfg.parent || this.cfg.parentId){
		this.parent = this.cfg.parent || $(Flywet.escapeClientId(this.cfg.parentId));
		this.jq = $(this.parent).find(this.jqId);
		if(this.jq.length == 0){
			this.jq = $("<span></span>");
		}
		this.parent.append(this.jq);
	}else{
		this.jq = $(this.jqId);
	}
	
	this.jq.pushbutton(this.cfg);
};

Flywet.widget.PushButton.prototype.isActive = function(){
	return this.jq.hasClass("ui-state-active");
};

Flywet.PushButton = {
	showMenu : function(menuId, target){
		var $E = $(target),
			x = $E.offset().left,
			y = $E.offset().top,
			w = $E.outerHeight();
		window[menuId+'_var'].show({x:x,y:y+w});
	},
	destroy : function(menuId){
		window[menuId+'_var'].destroy();
	}
};