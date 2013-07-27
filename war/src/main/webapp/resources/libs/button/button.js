(function($) {
	
	function _initSeparator(target){
		var separator = $("<span></span>").insertAfter(target);
		$(target).addClass("ui-button-original").hide().appendTo(separator);
		separator.addClass("ui-separator")
			.append($("<span class='ui-icon ui-icon-grip-dotted-vertical-narrow'></span>"));
	
		return separator;
	}
	
	function _initButton(target){
		var btn = $("<button type='button'></button>").insertAfter(target);
		$(target).addClass("ui-button-original").hide().appendTo(btn);
		
		var opts = $.data(target, "pushbutton").options;
		
		var iconCls = opts.iconCls,
			iconAlign = opts.iconAlign,
			label = opts.label,
			clazz = "";
		
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
		
		if(opts.state == "disabled"){
			clazz = clazz + " ui-state-disabled";
		}else if(opts.state == "active"){
			clazz = clazz + " ui-state-active";
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
		
		// TODO 下拉列表
		
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
				var btn = _init(this);
				$.data(this, "pushbutton", {
					options : $.extend(
							{},
							$.fn.pushbutton.defaults,
							$.fn.pushbutton.parseOptions(this),
							options
						),
					button : btn
				});
			}
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
				Flywet.parseOptions(target, ["id", "type", "state", "title", "label", "iconCls", "iconAlign"])
			);
	};
	
	$.fn.pushbutton.defaults = {
		id : null,
		type : "button",
		label : null,
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
		
		if(this.jq.length > 0) return;
		
		if(this.cfg.type == "separator"){
			this.jq = $("<span></span>");
			var clazz = this.cfg["class"];
			if(clazz){
				clazz = "ui-separator " + clazz;
			}else{
				clazz = "ui-separator";
			}
			this.jq.addClass(clazz);
			if(this.cfg.style){
				this.jq.attr("style",this.cfg.style);
			}
			if(this.cfg.id){
				this.jq.attr("id",this.cfg.id);
			}
			this.jq.append("<span class='ui-icon ui-icon-grip-dotted-vertical-narrow'></span>");
		}else{
			this.jq = $("<button type='button'></button>");
			
			var clazz,icon=this.cfg.icon,iconPos=this.cfg.iconPos,label=this.cfg.label,state=this.cfg.state,userClass = this.cfg["class"];
			if(label && icon == undefined){
				clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
			}else if(label && icon){
				if(iconPos == "right"){
					clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-right";
				}else{
					clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-left";
				}
			}else if(label== undefined && icon){
				clazz = "ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only";
			}
			
			if(state == "disabled"){
				clazz = clazz + " ui-state-disabled";
			}else if(state == "active"){
				clazz = clazz + " ui-state-active";
			}
			
			if(userClass){
				clazz = clazz + " " + userClass;
			}
			
			this.jq.addClass(clazz);
			if(this.cfg.style){
				this.jq.attr("style",this.cfg.style);
			}
			if(this.cfg.id){
				this.jq.attr("id",this.cfg.id);
			}
			
			if(this.cfg.title){
				this.jq.attr("title",this.cfg.title);
			}
			
			// icon
			if(icon){
				var iconClass;
				if(iconPos == "right"){
					iconClass = "ui-button-icon-right ui-icon";
				}else{
					iconClass = "ui-button-icon-left ui-icon";
				}
				iconClass = iconClass + " " + icon;
				this.icon = $("<span></span>");
				this.icon.addClass(iconClass);
				this.jq.append(this.icon);
			}
			
			// text
			this.text = $("<span></span>");
			this.text.addClass("ui-button-text");
			if(label){
				this.text.html(label);
			}else{
				this.text.html("ui-text");
			}
			this.jq.append(this.text);
			
			// TODO 下拉列表
//			this.menu = $("<span></span>");
//			this.menu.addClass("ui-button-icon-right ui-icon");
//			this.jq.append(this.menu);
		}
		
		this.parent.append(this.jq);
		this.initEvents();
	}else{
		this.jq = $(this.jqId);
		return;
	}
	
};

Flywet.widget.PushButton.prototype.isActive = function(){
	return this.jq.hasClass("ui-state-active");
};

Flywet.widget.PushButton.prototype.initEvents = function(){
	
	// mouseOver
	this.jq.bind("mouseover",this.cfg,function(event){
		$(this).addClass('ui-state-hover');
		if(event.data && event.data.events && event.data.events["mouseover"]){
			Flywet.invokeFunction(event.data.events["mouseover"],event,event.data);
		}
	});
	
	// mouseOut
	this.jq.bind("mouseout",this.cfg,function(event){
		$(this).removeClass('ui-state-hover');
		if(event.data && event.data.events && event.data.events["mouseout"]){
			Flywet.invokeFunction(event.data.events["mouseout"],event,event.data);
		}
	});
	
	// other event
	Flywet.attachBehaviors(this.jq,Flywet.assembleBehaviors(this.cfg.events,["mouseover","mouseout"]),this.cfg);
	Flywet.attachBehaviorsOn(this.jq, this.cfg);
	
};

Flywet.PushButton = {
	showMenu : function(menuId, target){
		var $E = $(target),
			x = $E.offset().left,
			y = $E.offset().top
			w = $E.outerHeight();
		window[menuId+'_var'].show({x:x,y:y+w});
	},
	destroy : function(menuId){
		window[menuId+'_var'].destroy();
	}
};