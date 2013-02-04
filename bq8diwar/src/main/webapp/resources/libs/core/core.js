YonYou = {
	
	/**
	 * 获得符合jQuery标准的ID
	 */
    escapeClientId : function(id) {
        return "#" + id.replace(/:/g,"\\:");
    },
    
    /**
     * 只获得符合jQuery标准的ID，不包含#
     */
    escapeClientIdOnly : function(id) {
        return id.replace(/:/g,"\\:");
    },
    
    /**
     * 解析json串为javascript对象
     */
    parseJSON : function(data) {
    	return eval("("+data+")");		
    },
    
    /**
     * 解析参数
     * 1)首先从target对象中的data-options中解析参数
     * 2)其次从opts中解析参数
     */
    parseOptions : function(target, param) {
		var t = $(target);
		var result = {};
		var s = $.trim(t.attr("data-options"));
		if (s) {
			var firstChar = s.substring(0, 1);
			var lastChar = s.substring(s.length - 1, 1);
			if (firstChar != "{") {
				s = "{" + s;
			}
			if (lastChar != "}") {
				s = s + "}";
			}
			result = YonYou.parseJSON(s);
		}
		if (param) {
			var paramObj = {};
			for ( var i = 0; i < param.length; i++) {
				var pp = param[i];
				if (typeof pp == "string") {
					if (pp == "width" || pp == "height" || pp == "left"
							|| pp == "top") {
						paramObj[pp] = parseInt(target.style[pp]) || undefined;
					} else {
						paramObj[pp] = t.attr(pp);
					}
				} else {
					for ( var p in pp) {
						var _d = pp[p];
						if (_d == "boolean") {
							paramObj[p] = t.attr(p) ? (t.attr(p) == "true") : undefined;
						} else {
							if (_d == "number") {
								paramObj[p] = t.attr(p) == "0" ? 0 : parseFloat(t.attr(p)) || undefined;
							}
						}
					}
				}
			}
			$.extend(result, paramObj);
		}
		return result;
	},
    
    /**
     * 将javascript对象转换为json串
     */
	toJSONString:function (object){
		if(!object){
			return "{}";
		}
		var type=typeof object;			
		if('object'==type){
			if(Array==object.constructor )
			type='array';				
			else if(RegExp==object.constructor )
			type='regexp';				
			else 
			type='object';
		}
		switch(type){
			case 'undefined':
			case 'unknown':
			return ;				
			break;				
			case 'function':
			case 'boolean':
			case 'regexp':
			return object.toString ();				
			break;				
			case 'number':
			return isFinite(object)?object.toString ():'null';				
			break;				
			case 'string':
			return '"'+object.replace(/(\\|\")/g,"\\$1").replace(/\n|\r|\t/g,
			function (){
				var a=arguments[0];
				return (a=='\n')?'\\n':
				(a=='\r')?'\\r':
				(a=='\t')?'\\t':""
			})+'"';				
			break;				
			case 'object':
			if(object===null)return 'null';				
			var results=[];				
			for(var property in object){
				var value=YonYou.toJSONString(object[property]);					
				if(value!==undefined)
				results.push(YonYou.toJSONString(property)+':'+value);
			}
			return '{'+results.join(',')+'}';				
			break;				
			case 'array':
			var results=[];				
			for(var i=0;i<object.length;i++){
				var value=YonYou.toJSONString(object[i]);					
				if(value!==undefined)results.push(value);
			}
			return '['+results.join(',')+']';				
			break;
		}
	},
	
	/**
	 * 向form中添加参数
	 * @param parent 包含参数form的id
	 */
    addSubmitParam : function(parent, params) {
        var form = $(this.escapeClientId(parent));
        form.children('input.ui-submit-param').remove();

        for(var key in params) {
            form.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + params[key] + "\" class=\"ui-submit-param\"/>");
        }

        return this;
    },

    /**
     * 通过form的id提交form
     * @param formId
     */
    submit : function(formId) {
        $(this.escapeClientId(formId)).submit();
    },

    /**
     * 为元素批量绑定事件
     * @param element 元素
     * @param behaviors 行为事件集合
     */
    attachBehaviors : function(element, behaviors, data) {
    	var _selfData = data;
        $.each(behaviors, function(event, fn) {
            element.bind(event, _selfData, function(event) {
                fn.call(element, event, event.data);
            });
        });
    },
    
    assembleBehaviors : function(behaviors,exclude){
    	var rtn = {};
    	var atExclude;
    	if(behaviors){
    		for(var b in behaviors){
    			if(behaviors[b] && behaviors[b]!=null && behaviors[b]!=""){
    				atExclude = false;
    				if(exclude){
    					for(var i=0;i<exclude.length;i++){
    						if(exclude[i]==b){
    							atExclude = true;
    							break;
    						}
    					}
    				}
    				if(!atExclude){
	    				rtn[b] = function(e,data){
	    					var events = data.events || data.attrs.events;
	        				YonYou.invokeFunction(events[e.type],e,data);
	    				};
    				}
    			}
    		}
    	}
    	return rtn;
    },

    /**
     * 通过name获得cookie值
     * @param name
     */
    getCookie : function(name) {
        return $.cookie(name);
    },

    /**
     * 设置cookie值
     * @param name
     * @param value
     */
    setCookie : function(name, value) {
        $.cookie(name, value);
    },
    
    /**
     * ajax快捷方式
     * @param cfg
     * @param ext
     */
    ab : function(cfg, ext) {
        YonYou.ajax.AjaxRequest(cfg, ext);
    },
    
    /**
     * 日志：info
     * @param log
     */
    info: function(log) {
        if(this.logger) {
            this.logger.info(log);
        }
    },
    
    /**
     * 日志：debug
     * @param log
     */
    debug: function(log) {
        if(this.logger) {
            this.logger.debug(log);
        }
    },
    
    /**
     * 日志：warn
     * @param log
     */
    warn: function(log) {
        if(this.logger) {
            this.logger.warn(log);
        }
    },
    
    /**
     * 日志：error
     * @param log
     */
    error: function(log) {
        if(this.logger) {
            this.logger.error(log);
        }
    },
    
    /**
     * 清除选择
     */
    clearSelection: function() {
        if(window.getSelection) {
            if(window.getSelection().empty) {
                window.getSelection().empty();
            } else if (window.getSelection().removeAllRanges) {
                window.getSelection().removeAllRanges();
            } else if (document.selection) {
                document.selection.empty();
            }
        }
    },
    
    /**
     * 通过超类扩展子类
     * @param subClass 子类
     * @param superClass 超类
     */
    extend: function(subClass, superClass) {
        subClass.prototype = new superClass;
        subClass.prototype.constructor = subClass;
    },
    
    /**
     * 判断参数是否是数字类型
     * @param value
     */
    isNumber: function(value) {
        return typeof value === 'number' && isFinite(value);
    },
    
    /**
     * 通过url获得脚本
     * @param url 脚本的url
     * @param callback 脚本加载完之后调用的方法
     */
    getScript: function(url, callback) {
        $.ajax({
			type: "GET",
			url: url,
			success: callback,
			dataType: "script",
			cache: true
        });
    },
    
    /**
	 * 得到鼠标的位置
	 */
	getMousePosition: function(e,target){
		var win = $(window),
	    left = e.pageX,
	    top = e.pageY;
	    width = $(target).outerWidth(),
	    height = $(target).outerHeight();

	    //collision detection for window boundaries
	    if((left + width) > (win.width())+ win.scrollLeft()) {
	        left = left - width;
	    }
	    if((top + height ) > (win.height() + win.scrollTop())) {
	        top = top - height;
	    }
	    return {"left":left,"top":top};
	},
	/**
	 * @see 得到窗体的
	 */
	getWindowScroll:function (){
		
		var T,L,W,H,win=window,dom=document.documentElement,doc=document.body;			
		T=dom&&dom.scrollTop||doc&&doc.scrollTop||0;			
		L=dom&&dom.scrollLeft||doc&&doc.scrollLeft||0;			
		if(win.innerWidth){
			
			W=win.innerWidth;				
			H=win.innerHeight;				
			
		}else {
			
			W=dom&&dom.clientWidth||doc&&doc.clientWidth;				
			H=dom&&dom.clientHeight||doc&&doc.clientHeight;				
			
		}
		return {
			top:T,left:L,width:W,height:H
		};			
		
	},
	getDomSize:function (dom){
		var T=0,L=0,W=0,H=0;
		if(dom){
			var dom = $(dom);
			T=dom.scrollTop()||0;
			L=dom.scrollLeft()||0;
			W=dom.width()||dom.get(0).clientWidth||dom.get(0).offsetWidth||0;
			H=dom.height()||dom.get(0).clientHeight||dom.get(0).offsetHeight||0;
		}
		return {
			top:T,left:L,width:W,height:H
		};
	},
	getDomSizeById:function (id){
		return YonYou.getDomSize(YonYou.escapeClientId(id));
	},
	/**
	 * 替换/r/n为<br>
	 */
	replaceEnterToBr : function(str){
		return str.replace(new RegExp("\r\n","gm"),"<br>");
	},
	
	/**
	 * 字符串是否为空
	 * 
	 * @param {}
	 *            str
	 */
	isNull:function (str){
		
		if(typeof(str)=='undefined'||typeof(str)!='string'||str==null||str==''){
			
			return true;
			
		}
		return false;		
		
	},
	
	trimEmpty : function (str){
		if(typeof(str)=='undefined'||typeof(str)!='string'||str==null){
			return "";
		}
		return str;
	},
	
	/**
	 * 对象是否为空
	 * 
	 * @param {}
	 *            str
	 */
	isObjNull:function (str){
		
		if(typeof(str)=='undefined'||str==null){
			
			return true;			
			
		}
		return false;		
		
	},
	
	/**
	 * 判断对象是否没有元素
	 */
	isObjEmpty : function (obj){
		for(var i in obj){
			return false;
		}
		return true;
	},
	
	/**
	 * 设置浏览器环境
	 */
	env : function (){
		YonYou.browserDetect.init();
		$("html").addClass(YonYou.browserDetect.OS + " " + YonYou.browserDetect.browser + " " + YonYou.browserDetect.browser_ver);
	},
	
	/**
	 * 创建对象快捷方式
	 */
	cw : function(widgetConstructor, widgetVar, cfg){
		YonYou.createWidget(widgetConstructor, widgetVar, cfg);
	},
	
	/**
	 * 调用方法
	 */
	invokeFunction : function(func,event,data){
		if(typeof func == 'function'){
			func.call(this,event,data);
		}else if(typeof func == 'string'){
			eval(func+"(event,data);");
		}
	},
	
	/**
	 * 自动创建对象
	 */
	autocw : function(cfg, parent){
		if(typeof(cfg)=="string"){
			$(parent).html(cfg);
		}else if(cfg instanceof Array){
			for(var i=0;i<cfg.length;i++){
				var w = cfg[i];
				if(w && w.componentType){
					w.parent = parent;
					YonYou.createWidget(w.componentType,w.id,w);
				}
			}
		}else{
			if(cfg && cfg.componentType){
				cfg.parent = parent;
				YonYou.createWidget(cfg.componentType,cfg.id,cfg);
			}
		}
	},
	
	/**
	 * 创建对象
	 */
	createWidget : function (widgetConstructor, widgetVar, cfg) {
		if(widgetConstructor.substring(0,3) == "bq:"){
			widgetConstructor = widgetConstructor.substring(3);
		}
		if(YonYou.widget[widgetConstructor]) {
			if(widgetVar==undefined || widgetVar == null || widgetVar == ""){
				widgetVar = "widget_"+(YonYou.windex++);
				cfg = cfg || {};
				if(!cfg.id)cfg.id=widgetVar;
				widgetVar=widgetVar+"_var";
			}
            window[widgetVar] = new YonYou.widget[widgetConstructor](cfg);
        }
	},
	
	/**
	 * 添加指定属性
	 */
	addAttrs : function(target, cfg, attrsName){
		var $target = $(target);
		for(var i=0;i<attrsName.length;i++){
			if(cfg[attrsName[i]]){
				$target.attr(attrsName[i],cfg[attrsName[i]]);
			}
		}
	},
	
    locales : {},
    
    zindex : 1000,
    
    windex : 0
};


/**
 * 扩展jQuery方法
 */
(function($) {

	$.fn._outerWidth = function(width) {
		if (width == undefined) {
			if (this[0] == window) {
				return this.width() || document.body.clientWidth;
			}
			return this.outerWidth() || 0;
		}
		return this.each(function() {
			if (!$.support.boxModel && $.browser.msie) {
				$(this).width(width);
			} else {
				$(this).width(width - ($(this).outerWidth() - $(this).width()));
			}
		});
	};
	
	$.fn._outerHeight = function(height) {
		if (height == undefined) {
			if (this[0] == window) {
				return this.height() || document.body.clientHeight;
			}
			return this.outerHeight() || 0;
		}
		return this.each(function() {
				if (!$.support.boxModel && $.browser.msie) {
					$(this).height(height);
				} else {
					$(this).height(height - ($(this).outerHeight() - $(this).height()));
				}
			});
	};
	
	$.fn._scrollLeft = function(left) {
		if (left == undefined) {
			return this.scrollLeft();
		} else {
			return this.each(function() {
				$(this).scrollLeft(left);
			});
		}
	};
	
	$.fn._propAttr = $.fn.prop || $.fn.attr;
	
})(jQuery);


YonYou.ajax = {};
YonYou.widget = {};



/***************
 * 基本元件类
 * 
 ***************/
YonYou.widget.BaseWidget = function() {};

// 获得jq对象
YonYou.widget.BaseWidget.prototype.getJQ = function() {
    return this.jq;
};


/**
 * ajax通用方法
 */
YonYou.ajax.AjaxUtils = {
	
	/**
	 * 序列化参数
	 * @param params 参数集合
	 */
    serialize: function(params) {
        var serializedParams = '';
		
        for(var param in params) {
            serializedParams = serializedParams + "&" + param + "=" + params[param];
        }
		
        return serializedParams;
    },

    /**
     * 更新页面元素到指定位置
     * 
     */
    updateElement: function(target, dom) {        
    	target.replaceWith(dom);
    },
    
    /**
     * 添加页面元素到指定位置
     */
    appendElement: function(target, dom) {
    	target.append(dom);
    },
    
    /**
     * 删除指定页面元素
     */
    removeElement: function(target) {
    	target.remove();
    },
    
    /**
     * 清空指定位置页面元素
     * 
     */
    emptyElement: function(target) {
    	target.empty();
    },
    
    /**
     * 在指定位置页面元素之前插入内容
     * 
     */
    beforeElement: function(target, dom) {        
    	target.before(dom);
    },
    
    /**
     * 在指定位置页面元素之后插入内容
     * 
     */
    afterElement: function(target, dom) {        
    	target.after(dom);
    }
};

/**
 * ajax请求
 */
YonYou.ajax.AjaxRequest = function(cfg, ext) {
    YonYou.debug('Initiating ajax request.');
    
    var ajaxURL=cfg.url,ajaxParams="",ajaxType=(cfg.type)?cfg.type:"post";
    ajaxType=ajaxType.toLowerCase();
    
    if(ajaxType == 'post') {
    	var form=null, sourceId = null;
        
        // 如果source存在，表示为提交元素
    	if (cfg.source) {
	        if(typeof(cfg.source) == 'string') {
	            sourceId = cfg.source;
	        } else {
	            sourceId = $(cfg.source).attr('id');
	        }
    	}
        
        // 如果formId存在，表示为提交表单
        if(cfg.formId) {
            form = $(YonYou.escapeClientId(cfg.formId));//Explicit form is defined
        }
        else {
        	if (cfg.source) {
	            form = $(YonYou.escapeClientId(sourceId)).parents('form:first');//look for a parent of source
	
	            //source has no parent form so use first form in document
	            if(form.length == 0) {
	                form = $('form').eq(0);
	            }
        	}
        }
        
        if(cfg.formAction){
        	ajaxURL = cfg.formAction;
        }else{
        	if(form!=null){
        		ajaxURL = form.attr('action');
        	}
        }
        
        if(form!=null){
        	if ($.fn.form) {
	        	//form ajax submit
	        	form.form("submit", {
	        		url : ajaxURL
	        	});
	        	return;
        	} else {
        		ajaxParams = form.serialize();
        	}
        	YonYou.debug('Form to post ' + form.attr('id') + '.');
        }
    }

    //params
    if(cfg.params) {
        ajaxParams = ajaxParams + YonYou.ajax.AjaxUtils.serialize(cfg.params);
    }
    if(ext && ext.params) {
        ajaxParams = ajaxParams + YonYou.ajax.AjaxUtils.serialize(ext.params);
    }
    
    YonYou.debug('Post Data:' + ajaxParams);
    var xhrOptions = {
        url : ajaxURL,
        type : ajaxType,
        cache : false,
        dataType : "json",
        data : ajaxParams,
        source: cfg.source,
        beforeSend: function(xhr, status) {
    		if(cfg.modal){
    			if(cfg.modalMessage){
    				YonYou.desktop.changeMarkText(cfg.modalMessage);
    			}
    			YonYou.desktop.triggerMark(true);
    		}
            if(cfg.beforeSend) {
                cfg.beforeSend.call(this, xhr, status);
            }
    
            YonYou.error('Request before send:' + status + '.');
        },
        error: function(xhr, status, errorThrown) {
            if(cfg.onerror) {
                cfg.onerror.call(xhr, status, errorThrown);
            }
    
            YonYou.error('Request return with error:' + status + '.');
        },
        success : function(data, status, xhr) {
            YonYou.debug('Response received succesfully.');
            
            var parsed;
            //call user callback
            if(cfg.onsuccess) {
                parsed = cfg.onsuccess.call(this, data, status, xhr);
            }

            //extension callback that might parse response
            if(ext && ext.onsuccess && !parsed) {
                parsed = ext.onsuccess.call(this, data, status, xhr); 
            }

            //do not execute default handler as response already has been parsed
            if(parsed) {
                return;
            } 
            else {
                YonYou.ajax.AjaxResponse.call(this, data);
            }
            
            YonYou.debug('DOM is updated.');
        },
        complete : function(xhr, status) {
            if(cfg.oncomplete) {
                cfg.oncomplete.call(this, xhr, status, this.args);
            }
            
            if(ext && ext.oncomplete) {
                ext.oncomplete.call(this, xhr, status, this.args);
            }
            
            if(cfg.modal){
    			YonYou.desktop.triggerMark(false);
    		}
            
            YonYou.debug('Response completed.');
            
            if(this.queued) {
                YonYou.ajax.Queue.poll();
            }
        }
    };
	
    xhrOptions.global = cfg.global == true || cfg.global == undefined ? true : false;
    
    if(cfg.async) {
    	$.ajax(xhrOptions);
    }
    else {
        YonYou.ajax.Queue.offer(xhrOptions);
    }
};

/**
 * ajax响应，自动更新相应区域
 * @param responseJSON 相应的json
 */
YonYou.ajax.AjaxResponse = function(json,target) {
	
	if(YonYou.isObjNull(json)){return;}
	
	if(json instanceof Object){
		if(json.state != undefined && json.messages != undefined){
			var text = "";
			if(typeof(json.messages)=="string"){
				text = text + json.messages;
			}else if(json.messages instanceof Array){
				text = text + json.messages[0];
				for(var i=1;i<json.messages.lenght;i++){
					text = text + "<br/>" + json.messages[i];
				}
			}
			if(json.state == 0){
				YonYou.cw("ConfirmDialog",null,{type:"prompt",text:text});
			}else{
				YonYou.cw("ConfirmDialog",null,{type:"error",text:text});
			}
		}
	}
	
	if(json instanceof Array){
		for(var i=0;i<json.length;i++){
			var opera = json[i];
			if(opera.operation){
				var t;
				if(!YonYou.isNull(opera.targetId)){
					t = $(YonYou.escapeClientId(opera.targetId));
				}else if(target){
					t = $(target);
				}
				if(t){
					if(opera.operation=="update"){
						YonYou.ajax.AjaxUtils.updateElement.call(this, t, opera.dom);
						YonYou.debug('DOM ' + t.attr('id') + ' is updated.');
					} else if(opera.operation=="append"){
						YonYou.ajax.AjaxUtils.appendElement.call(this, t, opera.dom);
						YonYou.debug('DOM ' + t.attr('id') + ' is appended.');
					} else if(opera.operation=="remove"){
						YonYou.ajax.AjaxUtils.removeElement.call(this, t);
						YonYou.debug('DOM ' + t.attr('id') + ' is removed.');
					} else if(opera.operation=="empty"){
						YonYou.ajax.AjaxUtils.emptyElement.call(this, t);
						YonYou.debug('DOM ' + t.attr('id') + ' is empty.');
					} else if(opera.operation=="before"){
						YonYou.ajax.AjaxUtils.beforeElement.call(this, t, opera.dom);
						YonYou.debug('DOM ' + t.attr('id') + ' before.');
					} else if(opera.operation=="after"){
						YonYou.ajax.AjaxUtils.afterElement.call(this, t, opera.dom);
						YonYou.debug('DOM ' + t.attr('id') + ' after.');
					} else if(opera.operation=="custom"){
						if(!YonYou.isNull(opera.cmd)){
							var cfg = opera.data;
							if(YonYou.isObjNull(json)){
								cfg = {};
							}
							cfg.id = t.attr('id');
							// 初始化对象
							if(opera.cmd.indexOf("widget.") == 0){
								YonYou.cw(opera.cmd.substring(7), cfg.id+"_var", cfg);
							}
							// 执行方法
							else if(opera.cmd.indexOf("this.") == 0){
								eval("window[\""+cfg.id+"_var\"]."+opera.cmd.substring(5)+"(cfg);");
							}
						}
					}
					
					if(opera.script){
						for(var j=0;j<opera.script.length;j++){
							try{
								eval(opera.script[j]);
							}catch(e){
								YonYou.error("Error:"+opera.script[j]);
							}
						}
					}
				}
			}
		}
	}
};

/**
 * ajax队列
 */
YonYou.ajax.Queue = {
		
    requests : new Array(),
    
    offer : function(request) {
        request.queued = true;
        this.requests.push(request);
        
        if(this.requests.length == 1) {
        	$.ajax(this.peek());
        }
    },
    
    // 执行完成一个ajax请求，然后poll，执行下一个
    poll : function() {
        if(this.isEmpty()) {
            return null;
        }
        
        var processed = this.requests.shift(),
        next = this.peek();
        
        if(next != null) {
        	$.ajax(next);
        }

        return processed;
    },
    
    peek : function() {
        if(this.isEmpty()) {
            return null;
        }
        
        return this.requests[0];
    },
        
    isEmpty : function() {
        return this.requests.length == 0;
    }
};

YonYou.browserDetect = {
	init: function () {
		this.browser = this.searchString(this.dataBrowser) || "unknown";
		this.version = $.browser.version || "unknown";
		this.OS = this.searchString(this.dataOS) || "unknown";
		this.browser_ver = this.browser;
		var vers = this.version.split(".");
		for(var i=0;i<vers.length && i<2;i++){
			if(i==0){
				this.browser_ver = this.browser_ver + vers[i];
			}else{
				this.browser_ver = this.browser_ver + "_" + vers[i];
			}
		}
	},
	searchString: function (data) {
		for (var i=0;i<data.length;i++)	{
			var dataString = data[i].string;
			var dataProp = data[i].prop;
			if (dataString) {
				if (dataString.indexOf(data[i].subString) != -1)
					return data[i].identity;
			}
			else if (dataProp)
				return data[i].identity;
		}
	},
	dataBrowser: [
		{
			string: navigator.userAgent,
			subString: "Chrome",
			identity: "chrome"
		},
		{ 	string: navigator.userAgent,
			subString: "OmniWeb",
			versionSearch: "OmniWeb/",
			identity: "omniWeb"
		},
		{
			string: navigator.vendor,
			subString: "Apple",
			identity: "safari",
			versionSearch: "Version"
		},
		{
			prop: window.opera,
			identity: "opera"
		},
		{
			string: navigator.vendor,
			subString: "iCab",
			identity: "iCab"
		},
		{
			string: navigator.vendor,
			subString: "KDE",
			identity: "konqueror"
		},
		{
			string: navigator.userAgent,
			subString: "Firefox",
			identity: "firefox"
		},
		{
			string: navigator.vendor,
			subString: "Camino",
			identity: "camino"
		},
		{		// for newer Netscapes (6+)
			string: navigator.userAgent,
			subString: "Netscape",
			identity: "netscape"
		},
		{
			string: navigator.userAgent,
			subString: "MSIE",
			identity: "ie",
			versionSearch: "MSIE"
		},
		{
			string: navigator.userAgent,
			subString: "Gecko",
			identity: "mozilla",
			versionSearch: "rv"
		},
		{ 		// for older Netscapes (4-)
			string: navigator.userAgent,
			subString: "Mozilla",
			identity: "netscape",
			versionSearch: "Mozilla"
		}
	],
	dataOS : [
		{
			string: navigator.platform,
			subString: "Win",
			identity: "win"
		},
		{
			string: navigator.platform,
			subString: "Mac",
			identity: "mac"
		},
		{
			string: navigator.userAgent,
			subString: "iPhone",
			identity: "iPhone"
	    },
		{
			string: navigator.platform,
			subString: "Linux",
			identity: "linux"
		}
	]
};

YonYou.interaction = {
	action : function(val, act){
		if(act){
			for(var i=0;i<act.length;i++){
				eval("YonYou.interaction."+act[i].method+"("+(val==act[i].val)+",val,act[i].param)");
			}
		}
	},
	enable : function(march,val,param){
		var target;
		if(march){
			for(var i=0;i<param.length;i++){
				target = $(YonYou.escapeClientId(param[i]));
				if(target.data("componentType")){
					eval("target."+target.data("componentType")+"('enable');");
				}else{
					target.removeAttr("disabled");
					target.removeClass("ui-state-disabled");
				}
			}
		}else{
			for(var i=0;i<param.length;i++){
				target = $(YonYou.escapeClientId(param[i]));
				if(target.data("componentType")){
					eval("target."+target.data("componentType")+"('disabled');");
				}else{
					target.attr("disabled","");
					target.addClass("ui-state-disabled");
				}
			}
		}
	},
	show : function(march,val,param){
		var target;
		if(march){
			for(var i=0;i<param.length;i++){
				target = $(YonYou.escapeClientId(param[i]));
				target.show();
			}
		}else{
			for(var i=0;i<param.length;i++){
				target = $(YonYou.escapeClientId(param[i]));
				target.hide();
			}
		}
	}
};

YonYou.BqButton = {
	isActive : function(id){
		return $(YonYou.escapeClientId(id)).hasClass("ui-state-active");
	},
	
	active : function(id){
		if(typeof(id)=="string"){
			$(YonYou.escapeClientId(id)).addClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(YonYou.escapeClientId(id[i])).addClass("ui-state-active");
			}
		}
	},
	
	inactive : function(id){
		if(typeof(id)=="string"){
			$(YonYou.escapeClientId(id)).removeClass("ui-state-active");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(YonYou.escapeClientId(id[i])).removeClass("ui-state-active");
			}
		}
	},
	
	enabled : function(id){
		if(typeof(id)=="string"){
			$(YonYou.escapeClientId(id)).removeClass("ui-state-disabled").removeAttr("disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(YonYou.escapeClientId(id[i])).removeClass("ui-state-disabled").removeAttr("disabled");
			}
		}
	},
	
	disabled : function(id){
		if(typeof(id)=="string"){
			$(YonYou.escapeClientId(id)).addClass("ui-state-disabled").attr("disabled","disabled");
		}else if(id instanceof Array){
			for(var i=0;i<id.length;i++){
				$(YonYou.escapeClientId(id[i])).addClass("ui-state-disabled").attr("disabled","disabled");
			}
		}
	}
}
