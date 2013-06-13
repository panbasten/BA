Plywet.widget.PushButton=function(cfg){
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
	
	this.init();
};

Plywet.extend(Plywet.widget.PushButton, Plywet.widget.BaseWidget);

Plywet.widget.PushButton.prototype.init = function() {
	if(this.cfg.parent || this.cfg.parentId){
		this.parent = this.cfg.parent || $(Plywet.escapeClientId(this.cfg.parentId));
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
		}
		
		this.parent.append(this.jq);
		this.initEvents();
	}else{
		this.jq = $(this.jqId);
		return;
	}
	
};

Plywet.widget.PushButton.prototype.isActive = function(){
	return this.jq.hasClass("ui-state-active");
};

Plywet.widget.PushButton.prototype.initEvents = function(){
	
	// mouseOver
	this.jq.bind("mouseover",this.cfg,function(event){
		$(this).addClass('ui-state-hover');
		if(event.data && event.data.events && event.data.events["mouseover"]){
			Plywet.invokeFunction(event.data.events["mouseover"],event,event.data);
		}
	});
	
	// mouseOut
	this.jq.bind("mouseout",this.cfg,function(event){
		$(this).removeClass('ui-state-hover');
		if(event.data && event.data.events && event.data.events["mouseout"]){
			Plywet.invokeFunction(event.data.events["mouseout"],event,event.data);
		}
	});
	
	// other event
	Plywet.attachBehaviors(this.jq,Plywet.assembleBehaviors(this.cfg.events,["mouseover","mouseout"]),this.cfg);
	Plywet.attachBehaviorsOn(this.jq, this.cfg);
	
};