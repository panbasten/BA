Plywet = {
	
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
    parseJSON : function(str) {
    	try { return $.parseJSON ? $.parseJSON(str) : window["eval"]("("+ str +")") || {}; }
		catch (e) { return {}; }
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
			result = Plywet.parseJSON(s);
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
				var value=Plywet.toJSONString(object[property]);					
				if(value!==undefined)
				results.push(Plywet.toJSONString(property)+':'+value);
			}
			return '{'+results.join(',')+'}';				
			break;				
			case 'array':
			var results=[];				
			for(var i=0;i<object.length;i++){
				var value=Plywet.toJSONString(object[i]);					
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
	        				Plywet.invokeFunction(events[e.type],e,data);
	    				};
    				}
    			}
    		}
    	}
    	return rtn;
    },

    /**
     * ajax快捷方式
     * @param cfg
     * @param ext
     */
    ab : function(cfg, ext) {
        Plywet.ajax.AjaxRequest(cfg, ext);
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
	 * 鼠标是否经过对象
	 * @param {Object}		evt
	 * @param {Object=}		el
	 */
	isMouseOverElement: function (evt, el) {
		var
			$E	= $(el || this)
		,	d	= $E.offset()
		,	T	= d.top
		,	L	= d.left
		,	R	= L + $E.outerWidth()
		,	B	= T + $E.outerHeight()
		,	x	= evt.pageX	// evt.clientX ?
		,	y	= evt.pageY	// evt.clientY ?
		;
		// if X & Y are < 0, probably means is over an open SELECT
		return (Plywet.browserDetect.msie && x < 0 && y < 0) || ((x >= L && x <= R) && (y >= T && y <= B));
	},
	
	/**
	 * 得到窗口的尺寸
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
	/**
	 * 设置不可见的元素为可测量空间尺寸状态，返回容器的'display' 和 'visibility'的哈希值，
	 * 如果要恢复容器的这两个属性，需要在调用方法中设置
	 */
	setInvisibly: function ($E, force) {
		// 只对于display为none的元素进行设置，原因是其无法获得尺寸信息
		if ($E && $E.length && (force || $E.css('display') === "none")) {
			var s = $E[0].style
				// 保存当前的样式属性，用于调用者恢复其属性
			,	CSS = { display: s.display || '', visibility: s.visibility || '' };
			// 设置元素不可见，但可以被测量
			$E.css({ display: "block", visibility: "hidden" });
			return CSS;
		}
		return {};
	},
	
	/**
	 * 返回对象CSS属性的数字值
	 * 		- 0 如果属性不存在
	 * @param {Array.<Object>}	$E	
	 * 		必须传入一个jQuery对象（必选）
	 * @param {string}			prop
	 * 		CSS属性的名字，例如：top, width ...
	 * @param {boolean=}		[allowAuto=false]
	 * 		- true 		如果属性值是auto，返回auto字符串
	 * 		- false 	如果属性值是auto，返回0
	 * 
	 * @return {(string|number)}
	 * 		通常可获得位置(top, left)或者尺寸(width, height)的整数值
	 */
	cssNum: function ($E, prop, allowAuto) {
		if (!$E.jquery) $E = $($E);
		var CSS = Plywet.setInvisibly($E)
		,	p	= $.css($E[0], prop, true)
		,	v	= allowAuto && p=="auto" ? p : (parseInt(p, 10) || 0);
		$E.css( CSS ); // 重置
		return v;
	},
	
	/**
	 * 获得对象基于给定外部宽度的innerWidth
	 * @param {Array.<Object>}	$E 
	 * 		必须传入jQuery对象（必选）
	 * @param  {number=}
	 * 		外部宽度
	 * @return 返回对象的innerWidth，排除了padding和border
	 * 
	 */
	cssWidth: function ($E, outerWidth) {
		// a 'calculated' outerHeight can be passed so borders and/or padding are removed if needed
		if (outerWidth <= 0) return 0;

		if (!Plywet.browserDetect.boxModel) return outerWidth;

		// strip border and padding from outerWidth to get CSS Width
		var b = Plywet.borderWidth
		,	n = Plywet.cssNum
		,	W = outerWidth
				- b($E, "Left")
				- b($E, "Right")
				- n($E, "paddingLeft")		
				- n($E, "paddingRight");

		return Math.max(0,W);
	},
	
	/**
	 * 获得对象的innerHeight
	 * @param {Array.<Object>}	$E 
	 * 		必须传入jQuery对象（必选）
	 * @param  {number=}
	 * 		外部高度
	 * @return 返回对象的innerHeight，排除了padding和border
	 * 
	 */
	cssHeight: function ($E, outerHeight) {
		// a 'calculated' outerHeight can be passed so borders and/or padding are removed if needed
		if (outerHeight <= 0) return 0;

		if (!Plywet.browserDetect.boxModel) return outerHeight;

		// strip border and padding from outerHeight to get CSS Height
		var b = Plywet.borderWidth
		,	n = Plywet.cssNum
		,	H = outerHeight
			- b($E, "Top")
			- b($E, "Bottom")
			- n($E, "paddingTop")
			- n($E, "paddingBottom");

		return Math.max(0,H);
	},
	
	/**
	 * 获得元素的指定CSS
	 * @param {Array.<Object>}	$E 
	 * 		必须传入jQuery对象（必选）
	 * @param {string} 	list
	 * 		指定的CSS属性，以','分隔
	 * @return 
	 * 		指定CSS的hash
	 */
	getElementCSS: function ($E, list) {
		var
			CSS	= {}
		,	style	= $E[0].style
		,	props	= list.split(",")
		,	sides	= "Top,Bottom,Left,Right".split(",")
		,	attrs	= "Color,Style,Width".split(",")
		,	p, s, a, i, j, k
		;
		for (i=0; i < props.length; i++) {
			p = props[i];
			if (p.match(/(border|padding|margin)$/))
				for (j=0; j < 4; j++) {
					s = sides[j];
					if (p === "border")
						for (k=0; k < 3; k++) {
							a = attrs[k];
							CSS[p+s+a] = style[p+s+a];
						}
					else
						CSS[p+s] = style[p+s];
				}
			else
				CSS[p] = style[p];
		};
		return CSS
	},
	
	/**
	 * 获得border的宽度
	 * @param el 	dom对象
	 * @param side	边线位置
	 */
	borderWidth: function (el, side) {
		if (el.jquery) el = el[0];
		var b = "border"+ side.substr(0,1).toUpperCase() + side.substr(1); // left => Left
		return $.css(el, b+"Style", true) === "none" ? 0 : (parseInt($.css(el, b+"Width", true), 10) || 0);
	},
	
	/**
	 * 获得容器或者窗格的尺寸
	 * 
	 * @return JSON 
	 * 		- 返回所有维度：top, bottom, left, right, outerWidth, innerHeight...
	 */
	getElementDimensions : function ($E) {
		if (!$E.jquery) $E = $($E);
		var
			d	= {}			// dimensions hash
		,	x	= d.css = {}	// CSS hash
		,	i	= {}			// TEMP insets
		,	b, p				// TEMP border, padding
		,	N	= Plywet.cssNum
		,	off = $E.offset()
		;
		d.offsetLeft = off.left;
		d.offsetTop  = off.top;
	
		$.each("Left,Right,Top,Bottom".split(","), function (idx, e) { // e = edge
			b = x["border" + e] = Plywet.borderWidth($E, e);
			p = x["padding"+ e] = Plywet.cssNum($E, "padding"+e);
			i[e] = b + p; // total offset of content from outer side
			d["inset"+ e] = p;	// eg: insetLeft = paddingLeft
		});
	
		d.offsetWidth	= $E.innerWidth();	// offsetWidth is used in calc when doing manual resize
		d.offsetHeight	= $E.innerHeight();	// ditto
		d.outerWidth	= $E.outerWidth();
		d.outerHeight	= $E.outerHeight();
		d.innerWidth	= Math.max(0, d.outerWidth  - i.Left - i.Right);
		d.innerHeight	= Math.max(0, d.outerHeight - i.Top  - i.Bottom);
	
		x.width		= $E.width();
		x.height	= $E.height();
		x.top		= N($E,"top",true);
		x.bottom	= N($E,"bottom",true);
		x.left		= N($E,"left",true);
		x.right		= N($E,"right",true);
		
		//d.visible	= $E.is(":visible");// && x.width > 0 && x.height > 0;
		
		return d;
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
		$("html").addClass(Plywet.browserDetect.OS + " " + Plywet.browserDetect.browser + " " + Plywet.browserDetect.browser_ver);
	},
	
	/**
	 * 创建对象快捷方式
	 */
	cw : function(widgetConstructor, widgetVar, cfg){
		Plywet.createWidget(widgetConstructor, widgetVar, cfg);
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
					Plywet.createWidget(w.componentType,w.id,w);
				}
			}
		}else{
			if(cfg && cfg.componentType){
				cfg.parent = parent;
				Plywet.createWidget(cfg.componentType,cfg.id,cfg);
			}
		}
	},
	
	/**
	 * 创建对象
	 */
	createWidget : function (widgetConstructor, widgetVar, cfg) {
		if(widgetConstructor.substring(0,4) == "fly:"){
			widgetConstructor = widgetConstructor.substring(4);
		}
		if(Plywet.widget[widgetConstructor]) {
			if(widgetVar==undefined || widgetVar == null || widgetVar == ""){
				widgetVar = "widget_"+(Plywet.windex++);
				cfg = cfg || {};
				if(!cfg.id)cfg.id=widgetVar;
				widgetVar=widgetVar+"_var";
			}
            window[widgetVar] = new Plywet.widget[widgetConstructor](cfg);
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

Plywet.CookieUtils = {
	/**
	 * 是否允许使用cookie
	 */
	acceptsCookies: !!navigator.cookieEnabled,
    /**
     * 通过name获得cookie值
     * @param name
     */
    read : function(name) {
		var
			c		= document.cookie
		,	cs		= c ? c.split(';') : []
		,	pair	// loop var
		;
		for (var i=0, n=cs.length; i < n; i++) {
			pair = $.trim(cs[i]).split('='); // name=value pair
			if (pair[0] == name) // found the layout cookie
				return decodeURIComponent(pair[1]);
		
		}
		return null;
    },

    /**
     * 设置cookie值
     * @param name
     * @param val
     * @param cookieOpts
     */
    write: function (name, val, cookieOpts) {
		var
			params	= ''
		,	date	= ''
		,	clear	= false
		,	o		= cookieOpts || {}
		,	x		= o.expires
		;
		if (x && x.toUTCString)
			date = x;
		else if (x === null || typeof x === 'number') {
			date = new Date();
			if (x > 0)
				date.setDate(date.getDate() + x);
			else {
				date.setFullYear(1970);
				clear = true;
			}
		}
		if (date)		params += ';expires='+ date.toUTCString();
		if (o.path)		params += ';path='+ o.path;
		if (o.domain)	params += ';domain='+ o.domain;
		if (o.secure)	params += ';secure';
		document.cookie = name +'='+ (clear ? "" : encodeURIComponent( val )) + params; // write or clear cookie
	},
	
	/**
	 * 
	 */
	clear: function (name) {
		Plywet.CookieUtils.write(name, '', {expires: -1});
	}
};

Plywet.cookie = function (k, v, o) {
	var C = Plywet.CookieUtils;
	if (v === null)
		C.clear(k);
	else if (v === undefined)
		return C.read(k);
	else
		C.write(k, v, o);
};

Plywet.dialog = {
	prompt : function(text){
		Plywet.cw("ConfirmDialog",null,{type:"prompt",text:text});
	},
	warning : function(text){
		Plywet.cw("ConfirmDialog",null,{type:"warning",text:text});
	},
	error : function(text){
		Plywet.cw("ConfirmDialog",null,{type:"error",text:text});
	}
};

Plywet.Logger = {
	/**
     * 日志：info
     * @param log
     */
    info: function(log) {
        if(this.logger) {
            this.logger.info(log);
        }else if(window.console){
        	console.info(log);
        }
    },
    
    /**
     * 日志：debug
     * @param log
     */
    debug: function(log) {
        if(this.logger) {
            this.logger.debug(log);
        }else if(window.console){
        	console.debug(log);
        }
    },
    
    /**
     * 日志：warn
     * @param log
     */
    warn: function(log) {
        if(this.logger) {
            this.logger.warn(log);
        }else if(window.console){
        	console.warn(log);
        }
    },
    
    /**
     * 日志：error
     * @param log
     */
    error: function(log) {
        if(this.logger) {
            this.logger.error(log);
        }else if(window.console){
        	console.error(log);
        }
    }
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


Plywet.ajax = {};
Plywet.widget = {};



/***************
 * 基本元件类
 * 
 ***************/
Plywet.widget.BaseWidget = function() {};

// 获得jq对象
Plywet.widget.BaseWidget.prototype.getJQ = function() {
    return this.jq;
};


/**
 * ajax通用方法
 */
Plywet.ajax.AjaxUtils = {
	
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
Plywet.ajax.AjaxRequest = function(cfg, ext) {
    Plywet.Logger.debug('Initiating ajax request.');
    
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
            form = $(Plywet.escapeClientId(cfg.formId));//Explicit form is defined
        }
        else {
        	if (cfg.source) {
	            form = $(Plywet.escapeClientId(sourceId)).parents('form:first');//look for a parent of source
	
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
        	Plywet.Logger.debug('Form to post ' + form.attr('id') + '.');
        }
    }

    //params
    if(cfg.params) {
        ajaxParams = ajaxParams + Plywet.ajax.AjaxUtils.serialize(cfg.params);
    }
    if(ext && ext.params) {
        ajaxParams = ajaxParams + Plywet.ajax.AjaxUtils.serialize(ext.params);
    }
    
    Plywet.Logger.debug('Post Data:' + ajaxParams);
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
    				Plywet.desktop.changeMarkText(cfg.modalMessage);
    			}
    			Plywet.desktop.triggerMark(true);
    		}
            if(cfg.beforeSend) {
                cfg.beforeSend.call(this, xhr, status);
            }
            
            Plywet.Logger.debug('Request before send:' + status + '.');
        },
        error: function(xhr, status, errorThrown) {
            if(cfg.onerror) {
                cfg.onerror.call(xhr, status, errorThrown);
            }
    
            Plywet.Logger.error('Request return with error:' + status + '.');
        },
        success : function(data, status, xhr) {
            Plywet.Logger.debug('Response received succesfully.');
            
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
                Plywet.ajax.AjaxResponse.call(this, data);
            }
            
            Plywet.Logger.debug('DOM is updated.');
        },
        complete : function(xhr, status) {
            if(cfg.oncomplete) {
                cfg.oncomplete.call(this, xhr, status, this.args);
            }
            
            if(ext && ext.oncomplete) {
                ext.oncomplete.call(this, xhr, status, this.args);
            }
            
            if(cfg.modal){
    			Plywet.desktop.triggerMark(false);
    		}
            
            Plywet.Logger.debug('Response completed.');
            
            if(this.queued) {
                Plywet.ajax.Queue.poll();
            }
        }
    };
	
    xhrOptions.global = cfg.global == true || cfg.global == undefined ? true : false;
    
    if(cfg.async) {
    	$.ajax(xhrOptions);
    }
    else {
        Plywet.ajax.Queue.offer(xhrOptions);
    }
};

/**
 * ajax响应，自动更新相应区域
 * @param responseJSON 相应的json
 */
Plywet.ajax.AjaxResponse = function(json,target) {
	
	if(Plywet.isObjNull(json)){return;}
	
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
				Plywet.dialog.prompt(text);
			}else{
				Plywet.dialog.error(text);
			}
		}
	}
	
	if(json instanceof Array){
		for(var i=0;i<json.length;i++){
			var opera = json[i];
			if(opera.operation){
				var t;
				if(!Plywet.isNull(opera.targetId)){
					t = $(Plywet.escapeClientId(opera.targetId));
				}else if(target){
					t = $(target);
				}
				if(t){
					if(opera.operation=="update"){
						Plywet.ajax.AjaxUtils.updateElement.call(this, t, opera.dom);
						Plywet.Logger.debug('DOM ' + t.attr('id') + ' is updated.');
					} else if(opera.operation=="append"){
						Plywet.ajax.AjaxUtils.appendElement.call(this, t, opera.dom);
						Plywet.Logger.debug('DOM ' + t.attr('id') + ' is appended.');
					} else if(opera.operation=="remove"){
						Plywet.ajax.AjaxUtils.removeElement.call(this, t);
						Plywet.Logger.debug('DOM ' + t.attr('id') + ' is removed.');
					} else if(opera.operation=="empty"){
						Plywet.ajax.AjaxUtils.emptyElement.call(this, t);
						Plywet.Logger.debug('DOM ' + t.attr('id') + ' is empty.');
					} else if(opera.operation=="before"){
						Plywet.ajax.AjaxUtils.beforeElement.call(this, t, opera.dom);
						Plywet.Logger.debug('DOM ' + t.attr('id') + ' before.');
					} else if(opera.operation=="after"){
						Plywet.ajax.AjaxUtils.afterElement.call(this, t, opera.dom);
						Plywet.Logger.debug('DOM ' + t.attr('id') + ' after.');
					} else if(opera.operation=="custom"){
						if(!Plywet.isNull(opera.cmd)){
							var cfg = opera.data;
							if(Plywet.isObjNull(json)){
								cfg = {};
							}
							cfg.id = t.attr('id');
							// 初始化对象
							if(opera.cmd.indexOf("widget.") == 0){
								Plywet.cw(opera.cmd.substring(7), cfg.id+"_var", cfg);
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
								Plywet.Logger.error(opera.script[j]);
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
Plywet.ajax.Queue = {
		
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

/**
 * 浏览器检查
 */
Plywet.browserDetect = {
	init: function () {
		// 浏览器标识
		this.browser = this.searchString(this.dataBrowser) || "unknown";
		// 操作系统
		this.OS = this.searchString(this.dataOS) || "unknown";
		// 版本
		this.version = $.browser.version || "unknown";
		// 浏览器标识_版本
		this.browser_ver = this.browser;
		var vers = this.version.split(".");
		for(var i=0;i<vers.length && i<2;i++){
			if(i==0){
				this.browser_ver = this.browser_ver + vers[i];
			}else{
				this.browser_ver = this.browser_ver + "_" + vers[i];
			}
		}
		
		// mozilla内核
		this.mozilla = !!$.browser.mozilla;
		
		// webkit内核
		this.webkit = !!$.browser.webkit || !!$.browser.safari;
		
		// msie内核
		this.msie = !!$.browser.msie;
		this.isIE6 = $.browser.msie && $.browser.version == 6;
		
		// box model
		// 只有IE需要还原到旧的box-model - update for older jQ onReady
		this.boxModel = $.support.boxModel !== false || !$.browser.msie;
		
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
Plywet.browserDetect.init();

Plywet.interaction = {
	action : function(val, act){
		if(act){
			for(var i=0;i<act.length;i++){
				eval("Plywet.interaction."+act[i].method+"("+(val==act[i].val)+",val,act[i].param)");
			}
		}
	},
	enable : function(march,val,param){
		var target;
		if(march){
			for(var i=0;i<param.length;i++){
				target = $(Plywet.escapeClientId(param[i]));
				if(target.data("componentType")){
					eval("target."+target.data("componentType")+"('enable');");
				}else{
					target.removeAttr("disabled");
					target.removeClass("ui-state-disabled");
				}
			}
		}else{
			for(var i=0;i<param.length;i++){
				target = $(Plywet.escapeClientId(param[i]));
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
				target = $(Plywet.escapeClientId(param[i]));
				target.show();
			}
		}else{
			for(var i=0;i<param.length;i++){
				target = $(Plywet.escapeClientId(param[i]));
				target.hide();
			}
		}
	}
};

