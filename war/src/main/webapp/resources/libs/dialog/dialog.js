Flywet.widget.Dialog = function(cfg) {
	cfg.showEffect = (cfg.showEffect)?cfg.showEffect:'fade';
	cfg.hideEffect = (cfg.hideEffect)?cfg.hideEffect:'fade';
    this.cfg = cfg;
    this.id = this.cfg.id;
    this.jqId = Flywet.escapeClientId(this.id);
    this.jq = $(this.jqId);
    this.visible = false;
    this.realRemove = cfg.realRemove || false;
    
    this.init();
    
    this.ajax();
    
    this.blockEvents = 'focus.dialog mousedown.dialog mouseup.dialog keydown.dialog keypress.dialog click.dialog';
    
    //configuration
    this.cfg.width = this.cfg.width||'auto';
    if(this.cfg.width == 'auto' && $.browser.msie && parseInt($.browser.version, 10) == 7) {
        this.cfg.width = 300;
    }
    this.cfg.height = this.cfg.height||'auto';
    this.cfg.draggable = this.cfg.draggable == false ? false : true;
    this.cfg.resizable = this.cfg.resizable == false ? false : true;
    this.cfg.minWidth = this.cfg.minWidth||150;
    this.cfg.minHeight = this.cfg.minHeight||this.titlebar.outerHeight();
    this.cfg.position = this.cfg.position||'center';
    
    //size
    this.jq.css({
        'width': this.cfg.width,
        'height': 'auto'
    });
    
    this.content.height(this.cfg.height);
    
    //events
    this.bindEvents();
    
    if(this.cfg.draggable) {
        this.setupDraggable();
    }
    
    if(this.cfg.resizable){
        this.setupResizable();
    }
    
    if(this.cfg.modal) {
        this.syncWindowResize();
    }
    
    if(this.cfg.appendToBody){
        this.jq.appendTo('body');
    }
    
    //remove related modality if there is one
    var modal = $(this.jqId + '_modal');
    if(modal.length > 0) {
        modal.remove();
    }
    
    if(this.cfg.autoOpen){
        this.show();
    }
    
    if(this.cfg.autoMaximized){
    	this.toggleMaximize();
    }
};

Flywet.extend(Flywet.widget.Dialog, Flywet.widget.BaseWidget);

Flywet.widget.Dialog.prototype.ajax = function() {
	if(this.cfg.url){
		var _self = this;
		var params;
		if(this.cfg.params){
			params = this.cfg.params;
		}else{
			params = {targetId : this.content.attr("id")};
		}
		Flywet.ab({
			type : "get",
			url : this.cfg.url,
			params : params,
			beforeSend : function(){
				_self.content.append("<div class='ui-dialog-content-loader-text'>正在加载...</div><div class='ui-dialog-content-loader'></div>");
			}
		});
	}
};

Flywet.widget.Dialog.prototype.init = function() {
	this.jq = $(this.jqId);
	
	if(this.cfg.parentId){
		this.parent = $(Flywet.escapeClientId(this.cfg.parentId));
	}else{
		this.parent = $(document.body);
	}
	
	if(this.jq.length > 0) {
		this.content = this.jq.children('.ui-dialog-content');
	    this.titlebar = this.jq.children('.ui-dialog-titlebar');
	    this.footer = this.jq.find('.ui-dialog-footer');
	    this.icons = this.titlebar.children('.ui-dialog-titlebar-icon');
	    this.closeIcon = this.titlebar.children('.ui-dialog-titlebar-close');
	    this.maximizeIcon = this.titlebar.children('.ui-dialog-titlebar-maximize');
		return;
	}
	
	this.realRemove = true;
	var style=this.cfg.style,styleClass=this.cfg.styleClass;
	if(styleClass){
		styleClass = "ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow " + styleClass;
	}else{
		styleClass = "ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow";
	}
	
	this.jq = $("<div></div>");
	this.jq.attr("id", this.id);
	this.jq.addClass(styleClass);
	if(style)this.jq.attr("style",style);
	
	// 1.titlebar
	if(this.cfg.showHeader){
		this.titlebar = $("<div></div>");
		this.titlebar.addClass("ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top");
		// title
		var title = $("<span></span>");
		title.addClass("ui-dialog-title");
		title.html(this.cfg.header);
		this.titlebar.append(title);
		// closable
		if(this.cfg.closable){
			this.closeIcon = this.createIcon("ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all","ui-icon ui-icon-panel-close");
			this.titlebar.append(this.closeIcon);
		}
		// maximizable
		if(this.cfg.maximizable){
			this.maximizeIcon = this.createIcon("ui-dialog-titlebar-icon ui-dialog-titlebar-maximize ui-corner-all","ui-icon ui-icon-panel-max");
			this.titlebar.append(this.maximizeIcon);
		}
		this.icons = this.titlebar.children('.ui-dialog-titlebar-icon');
		this.jq.append(this.titlebar);
	}
	// 2.content
	this.content = $("<div></div>");
	this.content.attr("id",this.id+":content");
	this.content.addClass("ui-dialog-content ui-widget-content");
	if(this.cfg.content)
		this.content.html(this.cfg.content);
	this.jq.append(this.content);
	// 3.footer
	if(this.cfg.footerButtons){
		var _self=this;
		this.footer = $("<div></div>");
		this.footer.addClass("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix");
		for(var i=0;i<this.cfg.footerButtons.length;i++){
			var cfg = this.cfg.footerButtons[i];
			if(cfg.events){
				for(var k in cfg.events){
					if(cfg.events[k]=="hide"){
						cfg.events[k]=function(){
							_self.hide();
						}
					}
				}
			}
		}
		Flywet.autocw(this.cfg.footerButtons,this.footer);
		this.jq.append(this.footer);
	}
	this.parent.append(this.jq);
	
};

Flywet.widget.Dialog.prototype.createIcon = function(anchorClass,iconClass){
	var a = $("<a href='#'></a>");
	a.addClass(anchorClass);
	var icon = $("<span></span>");
	icon.addClass(iconClass);
	a.append(icon);
	return a;
};

Flywet.widget.Dialog.prototype.enableModality = function() {
    $(document.body).append('<div id="' + this.id + '_modal" class="ui-widget-overlay"></div>').
        children(this.jqId + '_modal').css({
            'width': $(document).width()
            ,'height': $(document).height()
            ,'z-index': this.jq.css('z-index') - 1
        });
    
    //disable tabbing out of modal dialog and stop events from targets outside of dialog
    this.content.bind('keypress.ui-dialog', function(event) {
        if(event.keyCode !== $.ui.keyCode.TAB) {
            return;
        }

        var tabbables = $(':tabbable', this),
            first = tabbables.filter(':first'),
            last  = tabbables.filter(':last');

        if (event.target === last[0] && !event.shiftKey) {
            first.focus(1);
            return false;
        } else if (event.target === first[0] && event.shiftKey) {
            last.focus(1);
            return false;
        }
    });
};

Flywet.widget.Dialog.prototype.destroy = function(){
	Flywet.destroyWidget(this.jq);
};

Flywet.widget.Dialog.prototype.disableModality = function(){
    $(document.body).children(this.jqId + '_modal').remove();
    $(document).unbind(this.blockEvents).unbind('keydown.dialog');
};

Flywet.widget.Dialog.prototype.syncWindowResize = function() {
    $(window).resize(function() {
        $(document.body).children('.ui-widget-overlay').css({
            'width': $(document).width()
            ,'height': $(document).height()
        });
    });
};

Flywet.widget.Dialog.prototype.show = function() {
    if(this.visible) {
       return;
    }

    if(!this.loaded && this.cfg.dynamic) {
        this.loadContents();
    } 
    else {
        if(!this.positionInitialized) {
            this.initPosition();
        }
        
        this._show();
    }
};

Flywet.widget.Dialog.prototype._show = function() {
    //replace visibility hidden with display none for effect support
    this.jq.css({
        'display':'none'
        ,'visibility':'visible'
    });
    
    if(this.cfg.showEffect) {
        var _self = this;
            
        this.jq.show(this.cfg.showEffect, null, 'normal', function() {
            _self.postShow();
        });
    }    
    else {
        //display dialog
        this.jq.show();
        
        this.postShow();
    }
    
    this.visible = true;
    this.moveToTop();
    this.focusFirstInput();
    
    if(this.cfg.modal)
        this.enableModality();
};

Flywet.widget.Dialog.prototype.postShow = function() {   
    //execute user defined callback
    if(this.cfg.onShow) {
        this.cfg.onShow.call(this);
    }
};

Flywet.widget.Dialog.prototype.hide = function() {   
    if(!this.visible) {
       return;
    }
    
    if(this.cfg.hideEffect) {
        var _self = this;
    
        this.jq.hide(this.cfg.hideEffect, null, 'normal', function() {
            _self.onHide();
        });
    }
    else {
        this.jq.hide();
        
        this.onHide();
    }
    
    this.visible = false;
    
    //replace display block with visibility hidden for hidden container support
    this.jq.css({
        'visibility':'hidden'
        ,'display':'block'
    });
    
    if(this.realRemove) {
    	this.destroy();
    }
    
    if(this.cfg.modal) {
        this.disableModality();
    }
};

Flywet.widget.Dialog.prototype.focusFirstInput = function() {
    this.jq.find(':not(:submit):not(:button):input:visible:enabled:first').focus();
};

Flywet.widget.Dialog.prototype.bindEvents = function() {   
    var _self = this;
    
    //Move dialog to top if target is not a trigger for a PrimeFaces overlay
    this.jq.mousedown(function(e) {
        if(!$(e.target).data('primefaces-overlay-target')) {
        	_self.moveToTop();
        }
    });

    this.icons.mouseover(function() {
        $(this).addClass('ui-state-hover');
    }).mouseout(function() {
        $(this).removeClass('ui-state-hover');
    })
    
    if(this.cfg.closable){
	    this.closeIcon.click(function(e) {
	        _self.hide();
	        e.preventDefault();
	    });
    }
    
    if(this.cfg.maximizable){
	    this.maximizeIcon.click(function(e) {
	    	if(_self.cfg.onMaximize) {
	    		_self.cfg.onMaximize.call(this);
	        }
	        _self.toggleMaximize();
	        _self.resizeSub();
	        e.preventDefault();
	    });
    }
};

Flywet.widget.Dialog.prototype.setupDraggable = function() {    
    this.jq.draggable({
        cancel: '.ui-dialog-content, .ui-dialog-titlebar-close',
        handle: '.ui-dialog-titlebar',
        containment : 'document'
    });
};

Flywet.widget.Dialog.prototype.setupResizable = function() {
    var _self = this;
    
    this.jq.resizable({
        handles : 'n,s,e,w,ne,nw,se,sw',
        minWidth : this.cfg.minWidth,
        minHeight : this.cfg.minHeight,
        alsoResize : this.content,
        containment: 'document',
        start: function(event, ui) {
        },
        stop: function(event, ui) {
            _self.jq.css('position', 'fixed');
        }
    });
    
    this.resizers = this.jq.children('.ui-resizable-handle');
};

Flywet.widget.Dialog.prototype.initPosition = function() {
    //reset
    this.jq.css({left:0,top:0});
                
    if(/(center|left|top|right|bottom)/.test(this.cfg.position)) {
        this.cfg.position = this.cfg.position.replace(',', ' ');
        
        this.jq.position({
            my: 'center'
            ,at: this.cfg.position
            ,collision: 'fit'
            ,of: window
            //make sure dialog stays in viewport
            ,using: function(pos) {
                var l = pos.left < 0 ? 0 : pos.left,
                t = pos.top < 0 ? 0 : pos.top;
                
                $(this).css({
                    left: l
                    ,top: t
                });
            }
        });
    }
    else {
        var coords = this.cfg.position.split(','),
        x = $.trim(coords[0]),
        y = $.trim(coords[1]);
        
        this.jq.css({
            left: x
            ,top: y
        });
    }
    
    this.positionInitialized = true;
};

Flywet.widget.Dialog.prototype.onHide = function(event, ui) {
    if(this.cfg.onHide) {
        this.cfg.onHide.call(this, event, ui);
    }

    if(this.cfg.behaviors) {
        var closeBehavior = this.cfg.behaviors['close'];

        if(closeBehavior) {
            closeBehavior.call(this);
        }
    }
};

Flywet.widget.Dialog.prototype.moveToTop = function() {
    this.jq.css('z-index', ++Flywet.zindex);
};

/**
 * 改变子元素尺寸
 */
Flywet.widget.Dialog.prototype.resizeSub = function() {
	this.content.find("div.ui-panel:visible,div.tabs-container:visible,div.layout:visible")
		.each(function() {
			$(this).triggerHandler("_resize", [ true ]);
		});
};

Flywet.widget.Dialog.prototype.toggleMaximize = function() {
    
    if(this.maximized) {
        this.jq.removeClass('ui-dialog-maximized');
        this.restoreState();
        
        if(this.cfg.maximizable){
        	this.maximizeIcon.children('.ui-icon').removeClass('ui-icon-panel-restore').addClass('ui-icon-panel-max');
        }
        
        this.maximized = false;
    } 
    else {
        this.saveState();
        
        var win = $(window);
        
        var contentHeight = win.height()-5;
        if(this.titlebar && this.titlebar.length>0){
        	contentHeight = contentHeight - this.titlebar.height();
        }
        if(this.footer && this.footer.length>0){
        	contentHeight = contentHeight - this.footer.height();
        }
                
        this.jq.addClass('ui-dialog-maximized').css({
            'width': win.width()-2
            ,'height': 'auto'
            ,'top': win.scrollTop()
            ,'left': win.scrollLeft()
        });
        
        //maximize content
        this.content.css({
            width: 'auto',
            height: contentHeight
        });
        
        if(this.cfg.maximizable){
        	this.maximizeIcon.removeClass('ui-state-hover').children('.ui-icon').removeClass('ui-icon-panel-max').addClass('ui-icon-panel-restore');
        }
        this.maximized = true;
    }
};

Flywet.widget.Dialog.prototype.saveState = function() {
    this.state = {
        width: this.jq.width()
        ,height: this.content.height()
    };
    
    var win = $(window);
    this.state.offset = this.jq.offset();
    this.state.windowScrollLeft = win.scrollLeft();
    this.state.windowScrollTop = win.scrollTop();
};

Flywet.widget.Dialog.prototype.restoreState = function(includeOffset) {
    this.jq.width(this.state.width).height("auto");
    this.content.height(this.state.height);
        
    var win = $(window);
    this.jq.css({
       top: this.state.offset.top + (win.scrollTop() - this.state.windowScrollTop)
       ,left: this.state.offset.left + (win.scrollLeft() - this.state.windowScrollLeft)
    });
};

Flywet.widget.Dialog.prototype.loadContents = function() {
    var options = {
        source: this.id,
        process: this.id,
        update: this.id
    },
    _self = this;

    options.onsuccess = function(responseXML) {
        var xmlDoc = $(responseXML.documentElement),
        updates = xmlDoc.find("update");

        for(var i=0; i < updates.length; i++) {
            var update = updates.eq(i),
            id = update.attr('id'),
            content = update.text();

            if(id == _self.id){
                _self.content.html(content);
                _self.loaded = true;
            }
            else {
                Flywet.ajax.AjaxUtils.updateElement.call(this, id, content);
            }
        }

        Flywet.ajax.AjaxUtils.handleResponse.call(this, xmlDoc);
        
        return true;
    };
    
    options.oncomplete = function() {
        _self.show();
    };

    var params = [];
    params[this.id + '_contentLoad'] = true;

    options.params = params;

    Flywet.ajax.AjaxRequest(options);
};

/**
 * PrimeFaces ConfirmDialog Widget
 */
Flywet.widget.ConfirmDialog = function(cfg) {
	var _self = this;
	cfg = $.extend({
		footerButtons:null,
		showHeader:true,
		width:400,
		height:100
	},cfg);
	cfg.id=(cfg.id)?cfg.id:"widget_"+(Flywet.windex++);
    cfg.draggable = false;
    cfg.resizable = false;
    cfg.modal = true;
    cfg.realRemove = true;
    cfg.autoOpen = true;
    var types={
    		"prompt":"提示",
    		"warning":"预警",
    		"error":"错误",
    		"confirm":"确认"
    };
    cfg.type=cfg.type?cfg.type:"prompt";//prompt(提示)，warning（预警）,error(错误),confirm(确认，需要提供交互接口)
    cfg.header=types[cfg.type];
    if(cfg.footerButtons == null){
	    if(cfg.type=="prompt"){
	    	cfg.footerButtons = [{
	    		componentType : "fly:PushButton",
	    		type : "button",
	    		label : "确定",
	    		title : "确定",
	    		events : {
	        		click:'hide'
	        	}
	    	}];
	    }
	    else if(cfg.type=="confirm"){
	    	cfg.footerButtons = [{
	    		componentType : "fly:PushButton",
	    		type : "button",
	    		label : "确定",
	    		title : "确定",
	    		events : {
	        		click:function(event){
	    				Flywet.invokeFunction(cfg.confirmFunc,event,true);
	    				_self.hide();
	    			}
	        	}
	    	},{
	    		componentType : "fly:PushButton",
	    		type : "button",
	    		label : "取消",
	    		title : "取消",
	    		events : {
		    		click:function(event){
						Flywet.invokeFunction(cfg.confirmFunc,event,false);
						_self.hide();
					}
	        	}
	    	}];
	    } 
	    else {
	    	cfg.footerButtons = [{
	    		componentType : "fly:PushButton",
	    		type : "button",
	    		label : "关闭",
	    		title : "关闭",
	    		events : {
	        		click:'hide'
	        	}
	    	}];
	    }
    }
    else{
    	for(var i = 0;i<cfg.footerButtons.length;i++){
    		cfg.footerButtons[i].target = _self;
    	}
    }
    this.createContentDom(cfg);
    Flywet.widget.Dialog.call(this, cfg);
};

Flywet.widget.ConfirmDialog.prototype = Flywet.widget.Dialog.prototype;

Flywet.widget.ConfirmDialog.prototype.changeTitle = function(title){
	this.jq.find("div.ui-dialog-titlebar span").html(title);
};

Flywet.widget.ConfirmDialog.prototype.changeContent = function(img,content){
	this.jq.find("div.ui-dialog-content p").html('<span class="ui-icon ui-icon-'+img+'" style="float: left; margin: 0pt 7px 20px 0pt;"></span>'+content);
};

Flywet.widget.ConfirmDialog.prototype.createContentDom =function(cfg){
	 var contentDom=document.createElement("div");
	 $(contentDom).addClass(cfg.type);
	 var icon=document.createElement("div");
	 $(icon).addClass("ui-confirm-dialog-icon");
	 var span=document.createElement("span");
	 $(span).addClass("ui-confirm-dialog-span");
	 $(span).html(cfg.text);
	 $(contentDom).append(icon);
	 $(contentDom).append(span);
	 cfg.content=contentDom;
};
