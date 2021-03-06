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
    this.cfg.minHeight = this.cfg.minHeight||this.header.outerHeight();
    this.cfg.position = this.cfg.position||'center';
    
    //size
    this.jq.css({
        'width': this.cfg.width,
        'height': 'auto'
    });
    
    this.body.height(this.cfg.height);
    
    //events
    this.bindEvents();
    
    if(this.cfg.draggable) {
        this.setupDraggable();
    }
    
    if(this.cfg.resizable){
        this.setupResizable();
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
	var _self = this;
	var params;
	if(this.cfg.params){
		params = this.cfg.params;
	}else{
		params = {};
	}
	params.targetId = this.body.attr("id");
	
	if(this.cfg.url){
		Flywet.ab({
			type : "get",
			url : this.cfg.url,
			params : params,
			beforeSend : function(data){
				_self.body.append("<div class='modal-body-loader-text'>正在加载...</div><div class='modal-body-loader'></div>");
				if(_self.cfg.beforeSend) {
					_self.cfg.beforeSend.call(this, data);
				}
			},
			onsuccess : this.cfg.onsuccess,
			onerror : this.cfg.onerror
		});
	}
	else if(this.cfg.formId){
		Flywet.ab({
			type : "post",
			formId : this.cfg.formId,
			formAction : this.cfg.formAction,
			params : params,
			beforeSend : function(data){
				_self.body.append("<div class='modal-body-loader-text'>正在加载...</div><div class='modal-body-loader'></div>");
				if(_self.cfg.beforeSend) {
					_self.cfg.beforeSend.call(this, data);
				}
			},
			onsuccess : this.cfg.onsuccess,
			onerror : this.cfg.onerror
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
		this.body = this.jq.children('.modal-body');
	    this.header = this.jq.children('.modal-header');
	    this.footer = this.jq.children('.modal-footer');
	    this.icons = this.header.children('.modal-btn');
	    this.closeIcon = this.header.children('.modal-header-close');
	    this.maximizeIcon = this.header.children('.modal-header-maximize');
		return;
	}
	
	this.realRemove = true;
	var style=this.cfg.style,styleClass=this.cfg.styleClass;
	if(styleClass){
		styleClass = "modal-content " + styleClass;
	}else{
		styleClass = "modal-content";
	}
	
	this.jq = $("<div></div>");
	this.jq.attr("id", this.id);
	this.jq.addClass(styleClass);
	if(style){
		this.jq.attr("style",style);
	}
	
	// 1.header
	if(this.cfg.showHeader){
		this.header = $("<div></div>");
		this.header.addClass("modal-header");
		
		// closable
		if(this.cfg.closable){
			this.closeIcon = this.createIcon("modal-btn modal-header-close","glyphicon glyphicon-remove");
			this.header.append(this.closeIcon);
		}
		// maximizable
		if(this.cfg.maximizable){
			this.maximizeIcon = this.createIcon("modal-btn modal-header-maximize","glyphicon glyphicon-resize-full");
			this.header.append(this.maximizeIcon);
		}
		this.icons = this.header.children('.modal-btn');
		
		// title
		var title = $("<h4></h4>");
		title.addClass("modal-title");
		title.html(this.cfg.header);
		this.header.append(title);
		
		this.jq.append(this.header);
	}
	// 2.body
	this.body = $("<div></div>");
	this.body.attr("id",this.id+":body");
	this.body.addClass("modal-body");
	if(this.cfg.body)
		this.body.html(this.cfg.body);
	this.jq.append(this.body);
	
	// 3.footer
	if(this.cfg.footerButtons || this.cfg.footerSettingButtons){
		this.footer = $("<div></div>");
		this.footer.addClass("modal-footer");
	}
	this.jq.append(this.footer);
	if(this.cfg.footerButtons){
		var _self=this;
		this.defaultFooter = $("<div></div>");
		this.defaultFooter.addClass("modal-footer-buttonpane-right");
		
		this.checkBtns(this.cfg.footerButtons);
		
		Flywet.autocw(this.cfg.footerButtons,this.defaultFooter);
		this.footer.append(this.defaultFooter);
	}
	// 4.footer setting
	if(this.cfg.footerSettingButtons){
		var _self=this;
		this.settingFooter = $("<div></div>");
		this.settingFooter.addClass("modal-footer-buttonpane-left");
		
		this.checkBtns(this.cfg.footerSettingButtons);
		
		Flywet.autocw(this.cfg.footerSettingButtons,this.settingFooter);
		this.footer.append(this.settingFooter);
	}
	
	if(this.cfg.modal){
		this.jqModal = $("<div></div>");
		this.jqModal.attr("id",this.id+":modal");
		this.jqModal.addClass("modal");
		this.jqModal.append(this.jq);
		this.parent.append(this.jqModal);
	}else{
		this.parent.append(this.jq);
	}
	
};

Flywet.widget.Dialog.prototype.checkBtns = function(btns){
	var _self=this;
	for(var i=0;i<btns.length;i++){
		var cfg = btns[i];
		if(cfg.events){
			// 替换hide按钮事件
			for(var k in cfg.events){
				if(cfg.events[k]=="hide"){
					cfg.events[k]=function(){
						_self.hide();
					}
				}
			}
		}
		
		// 处理click事件
		if(cfg.events && cfg.events.click){
			// 执行默认的click方法
		}else if(cfg.click){
			if(!cfg.events){
				cfg.events = {};
			}
			cfg.events.click = function(event,params){
				if(params.click.func){
					if(typeof params.click.func == 'function'){
						params.click.func.call(this);
					}else{
						// 参数可以使用dialogId,event,params
						eval(params.click.func);
					}
				}else{
					var ajaxOpts = $.extend({},params.click,{
						onsuccess:function(data, status, xhr) {
							if(params.click.onsuccess) {
								params.click.onsuccess.call(this, data, status, xhr);
							}
							_self.hide();
						}
					});
					
					if(!ajaxOpts.params){
						ajaxOpts.params = {};
					}
					ajaxOpts.params.targetId = (_self.id+":body");
					
					Flywet.ab(ajaxOpts);
				}
			};
		}
	}
	
};

Flywet.widget.Dialog.prototype.createIcon = function(anchorClass,iconClass){
	var a = $("<button type='button'></button>");
	a.addClass(anchorClass);
	var icon = $("<span></span>");
	icon.addClass(iconClass);
	a.append(icon);
	return a;
};

Flywet.widget.Dialog.prototype.destroy = function(){
	if(this.cfg.modal){
		Flywet.destroyWidget(this.jqModal);
	}else{
		Flywet.destroyWidget(this.jq);
	}
	
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
    
    // for children TODO
    
    var t = this.jq;
    if(this.cfg.modal){
    	t = this.jqModal;
    }
    
    if(this.cfg.hideEffect) {
        var _self = this;
    
        t.hide(this.cfg.hideEffect, null, 'normal', function() {
            _self.onHide();
            
            _self.visible = false;
            
            //replace display block with visibility hidden for hidden container support
            t.css({
                'visibility':'hidden'
                ,'display':'block'
            });
            
            if(_self.realRemove) {
            	_self.destroy();
            }
            
        });
    }
    else {
        t.hide();
        
        this.onHide();
        
        this.visible = false;
        
        //replace display block with visibility hidden for hidden container support
       	t.css({
            'visibility':'hidden'
            ,'display':'block'
        });
        
        if(this.realRemove) {
        	this.destroy();
        }
        
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
        $(this).addClass('hover');
    }).mouseout(function() {
        $(this).removeClass('hover');
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
        cancel: '.modal-body, .modal-header-close',
        handle: '.modal-header',
        containment : 'document'
    });
};

Flywet.widget.Dialog.prototype.setupResizable = function() {
    this.jq.resizable({
        handles : 'n,s,e,w,ne,nw,se,sw',
        minWidth : this.cfg.minWidth,
        minHeight : this.cfg.minHeight,
        alsoResize : this.body
    });
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
	this.body.find("div.ui-panel:visible,div.tabs-container:visible,div.layout:visible")
		.each(function() {
			$(this).triggerHandler("_resize", [ true ]);
		});
};

Flywet.widget.Dialog.prototype.toggleMaximize = function() {
    
    if(this.maximized) {
        this.jq.removeClass('maximized');
        this.restoreState();
        
        if(this.cfg.maximizable){
        	this.maximizeIcon.children('.glyphicon').removeClass('glyphicon-resize-small').addClass('glyphicon-resize-full');
        }
        
        this.maximized = false;
    } 
    else {
        this.saveState();
        
        var win = $(window);
        
        var bodyHeight = win.height();
        if(this.header){
        	bodyHeight = bodyHeight - this.header._outerHeight();
        }
        if(this.footer){
        	bodyHeight = bodyHeight - this.footer._outerHeight();
        }
                
        this.jq.addClass('maximized').css({
            'width': win.width()-2
            ,'height': 'auto'
            ,'top': win.scrollTop()
            ,'left': win.scrollLeft()
        });
        
        //maximize body
        this.body.css({
            width: 'auto',
            height: bodyHeight
        });
        
        if(this.cfg.maximizable){
        	this.maximizeIcon.removeClass('hover').children('.glyphicon').removeClass('glyphicon-resize-full').addClass('glyphicon-resize-small');
        }
        this.maximized = true;
    }
};

Flywet.widget.Dialog.prototype.saveState = function() {
    this.state = {
        width: this.jq.width()
        ,height: this.body.height()
    };
    
    var win = $(window);
    this.state.offset = this.jq.offset();
    this.state.windowScrollLeft = win.scrollLeft();
    this.state.windowScrollTop = win.scrollTop();
};

Flywet.widget.Dialog.prototype.restoreState = function(includeOffset) {
    this.jq.width(this.state.width).height("auto");
    this.body.height(this.state.height);
        
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
            body = update.text();

            if(id == _self.id){
                _self.body.html(body);
                _self.loaded = true;
            }
            else {
                Flywet.ajax.AjaxUtils.updateElement.call(this, id, body);
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

Flywet.widget.ShowDialog = function(cfg) {
	var _self = this;
	cfg = $.extend({
		footerButtons:[{
    		componentType : "fly:PushButton",
    		type : "button",
    		label : "关闭",
    		title : "关闭",
    		events : {
        		click:'hide'
        	}
    	}],
		footerSettingButtons:null,
		showHeader:true,
		width:400,
		height:300,
		draggable:true,
		resizable:false,
		modal:false,
		realRemove:true,
		autoOpen:true
	},cfg);
	
    Flywet.widget.Dialog.call(this, cfg);
};

Flywet.widget.ShowDialog.prototype = Flywet.widget.Dialog.prototype;

/**
 * 提示对话框组件
 */
Flywet.widget.ConfirmDialog = function(cfg) {
	var _self = this;
	cfg = $.extend({
		footerButtons:null,
		footerSettingButtons:null,
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
	    		btnStyle : "success",
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
	    		btnStyle : "success",
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
	    		btnStyle : "link",
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
	    		btnStyle : "info",
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
    
    if(cfg.footerSettingButtons != null){
    	for(var i = 0;i<cfg.footerSettingButtons.length;i++){
    		cfg.footerSettingButtons[i].target = _self;
    	}
    }
    
    this.createBodyDom(cfg);
    Flywet.widget.Dialog.call(this, cfg);
};

Flywet.widget.ConfirmDialog.prototype = Flywet.widget.Dialog.prototype;

Flywet.widget.ConfirmDialog.prototype.changeTitle = function(title){
	this.jq.find("div.modal-header span").html(title);
};

Flywet.widget.ConfirmDialog.prototype.createBodyDom =function(cfg){
	 var bodyDom=document.createElement("div");
	 $(bodyDom).addClass(cfg.type);
	 var icon=document.createElement("div");
	 $(icon).addClass("confirm-modal-icon");
	 var span=document.createElement("span");
	 $(span).addClass("confirm-modal-span");
	 $(span).html(cfg.text);
	 $(bodyDom).append(icon);
	 $(bodyDom).append(span);
	 cfg.body=bodyDom;
};
