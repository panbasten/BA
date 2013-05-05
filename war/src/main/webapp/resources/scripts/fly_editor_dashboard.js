// 表单
Plywet.editors.dashboard = {
	saveStatus : function ($tabo) {
		console.log("save dashboard");
	},
	reloadStatus : function ($taba) {
		
		if (typeof($taba.data("exdata"))=='undefined')return;
		var tabData = $taba.data("exdata");

		if (typeof dashboardEditorPanel_var == 'undefined') {
			Plywet.cw("DashboardEditor","dashboardEditorPanel_var",{
				id: "dashboardEditorPanel",
				structureId: "dashboardStructPanel",
				propId: "dashboardPropBar",
				data: tabData
			});
		} else {
			dashboardEditorPanel_var.flush(tabData);
		}
		
	},
	resize : function(){
		// TODO 工具箱的滚动按钮
		
	},
	register : function(){
		if(Plywet.editors.register["dashboard"]){
			return;
		}
		
		Plywet.ab({
			type : "get",
			url: "rest/report/dashboard/editor",
			beforeSend : function(){
				Plywet.desktop.changeMarkText("正在注册表单设计器页面...");
			},
			oncomplete : function(xhr, status){
				// 初始化尺寸
				var editorContainer = diEditorLayout.getPane("center"),
					editorContentWidth = editorContainer.outerWidth(),
					editorContentHeight = editorContainer.outerHeight()-40;
				var $dashboard = $("#dashboard");
				$dashboard.width(editorContentWidth).height(editorContentHeight);
				$("#dashboardEditorPanel").height(editorContentHeight - 40);
				
				// 结构
				$dashboardLayout = $dashboard.layout({
					defaults: {
						size:					"auto"
						,	minSize:				50
						,	hideTogglerOnSlide:		true		// hide the toggler when pane is 'slid open'
						,	slideTrigger_open:		"click"
//						,	livePaneResizing:		true
						,	spacing_closed:			16
						,	togglerAlign_closed:	"top"
						,	togglerLength_closed:	36
						//	effect defaults - overridden on some panes
						,	fxName:					"slide"		// none, slide, drop, scale
						,	fxSpeed_open:			750
						,	fxSpeed_close:			1500
						,	fxSettings_open:		{ easing: "easeOutBounce" }
						,	fxSettings_close:		{ easing: "easeOutQuint" }
					}
					, west : {
						size : 200
						,	paneSelector : "#dashboardStepBar"
						,	togglerTip_open:		"关闭工具箱"
						,	togglerTip_closed:		"打开工具箱"
						,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-e'></div>"
						,	resizable:				false
//						,	initClosed:				true
					}
					, east : {
						size : 250
						,	paneSelector : "#dashboardPropBar"
						,	togglerTip_open:		"关闭属性框"
						,	togglerTip_closed:		"打开属性框"
						,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-w'></div>"
						,	resizerTip:		"调整属性框宽度"
						,	childOptions: {
							defaults: {
								size:					"auto"
								,	contentSelector:	".ui-widget-content"
								,	minSize : 				30
								,	animatePaneSizing: 		true
								,	fxSpeed_size:			750
								,	fxSettings_size:		{ easing: "easeInQuint" }
//								,	livePaneResizing:		true
							}
							, north: {
								size : 200
								,	closable : false
								,  	resizerTip:		"调整属性框高度"
								,	paneSelector : "#dashboardStructPanel"
							}
							, center: {
								paneSelector : "#dashboardPropPanel"
							}
						}
					}
					, center : {
						paneSelector : "#dashboardContent"
						, 	minSize : 200
						,	childOptions: {
							defaults: {
								size:					"auto"
								,	contentSelector:	".ui-widget-content"
								,	minSize : 				10
								,	animatePaneSizing: 		true
								,	fxSpeed_size:			750
								,	fxSettings_size:		{ easing: "easeInQuint" }
							}
							, north: {
								size : 30
								,	resizable:				false
								,	closable : 				false
								,	paneSelector : 			"#dashboardEditorToolbar"
							}
							, center: {
								paneSelector : 				"#dashboardEditorPanel"
							}
							, south: {
								size : 200
								,	paneSelector : 			"#dashboardSignalBar"
								,	slideTrigger_open:		"click"
								,	spacing_closed:			16
								,	togglerAlign_closed:	"right"
								,	togglerLength_closed:	36
								,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-n'></div>"
							}
						}
					}
				});
				
				
				Plywet.cw("EasyTabs","diEditordashboardStepBar",{
					id : "dashboardStepBar"
				});
				Plywet.cw("Scrollbar","dashboardStepBarScroll",{
					id:'dashboardStepBar',
					tabGroup:'dashboardStepBar-ul',
					step:80,
					scrollType:'vertical'
				});
				// 拖拽
				$(".fly-dashboard-step-plugin").draggable({
					revert:true,
					deltaX:-10,
					deltaY:-10,
					proxy:function(source){
						var n = $('<div class="proxy"></div>');
						n.append($(source).children("div").clone());
						n.appendTo('body');
						return n;
					}
				});
				
				$dashboard.hide();
				
				Plywet.editors.register["dashboard"] = "Y";
			}
		});
	},
	toggleFlags : {
		structPane : true,
		propPane : true,
		structHeight : 0,
		propHeight : 0
	},
	toggleContent : function(target){
		var $close1 = $("#dashboardStructClose"),
			$close2 = $("#dashboardPropClose"),
			$layout = $("#dashboardPropBar").data('layout');
		
		if(this.toggleFlags.structPane && this.toggleFlags.propPane){
			this.toggleFlags.structHeight = $layout.getPaneSize("north");
			this.toggleFlags.propHeight = $layout.getPaneSize("center", false, "horz");
		}
		
		//最小化1，最大化2
		if(target == "structPane" && this.toggleFlags.structPane) {
			$layout.sizePane("north", 30);
			
			$close1.addClass("ui-icon-circle-plus");
			$close1.removeClass("ui-icon-circle-minus");
			
			$close2.removeClass("ui-icon-circle-plus");
			$close2.addClass("ui-icon-circle-minus");
			
			this.toggleFlags.structPane = false;
			this.toggleFlags.propPane = true;
		} 
		// 最小化2，最大化1
		else if(target == "propPane" && this.toggleFlags.propPane) {
			$layout.sizePane("north", this.toggleFlags.structHeight + this.toggleFlags.propHeight - 30);
		
			$close2.addClass("ui-icon-circle-plus");
			$close2.removeClass("ui-icon-circle-minus");
			
			$close1.removeClass("ui-icon-circle-plus");
			$close1.addClass("ui-icon-circle-minus");
			
			this.toggleFlags.structPane = true;
			this.toggleFlags.propPane = false;
		}
		else {
			$layout.sizePane("north", this.toggleFlags.structHeight);
			
			$close1.removeClass("ui-icon-circle-plus");
			$close1.addClass("ui-icon-circle-minus");
			
			$close2.removeClass("ui-icon-circle-plus");
			$close2.addClass("ui-icon-circle-minus");
			
			this.toggleFlags.structPane = true;
			this.toggleFlags.propPane = true;
		}
		
		if(this.toggleFlags.structPane && this.toggleFlags.propPane){
			$layout.enableResizable("north");
		} else {
			$layout.disableResizable("north");
		}
	}
};


Plywet.widget.DashboardEditor = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.structureId = this.cfg.structureId;
	this.propId = this.cfg.propId;
	
	this.off = {x:5,y:5};
	
	this.drawRectFillStyle = {strokeStyle:'#f00',fillStyle:'#f00',isFill:true};
	this.drawRectStyle = {strokeStyle:'#f00'};
	this.dropRectStyle = {strokeStyle:'#0972a5',fillStyle:'#0972a5',isFill:true};
	this.drawResizerStyle = {fillStyle:'#a0a0a0'};
	
	this.eidtor = $(Plywet.escapeClientId(this.id));
	this.structure = $(Plywet.escapeClientId(this.structureId));
	this.prop = $(Plywet.escapeClientId(this.propId));
	
	this.dashboardStepBar = $(Plywet.escapeClientId("dashboardStepBar"));
	
	// 选中的对象
	this.selected = {};
	this.targetObject = null;
	
	// 鼠标一次按下抬起操作
	this.holder = false;
	
	this.initEditor();
	
};

Plywet.widget.DashboardEditor.prototype.clearSelected = function() {
	this.selected = {};
};

Plywet.widget.DashboardEditor.prototype.addSelected = function(domId,dom) {
	if(!dom){
		dom = this.getDomPropById(domId);
	}
	this.selected[domId]=dom;
};

Plywet.widget.DashboardEditor.prototype.isSelected = function(domId) {
	return this.selected[domId];
};

Plywet.widget.DashboardEditor.prototype.getSelectedSize = function() {
	var size = 0;
	for(var selectedId in this.selected){
		if(this.selected[selectedId]){
			size++;
		}
	}
	return size;
};

Plywet.widget.DashboardEditor.prototype.initEditor = function() {
	this.editorWrapper = $("<div id='"+this.id+"Wrapper' class='fly-editor-wrapper fly-dashboard-editor-wrapper'></div>").appendTo(this.eidtor);
	this.editorContent = $("<div id='"+this.id+"Content' class='fly-editor-content fly-dashboard-editor-content'></div>");
	this.editorContent.appendTo(this.editorWrapper);
	
	if ($.browser.msie && $.browser.version < 9){ // excanvas hack
    	this.editorCanvas = $("<div id='"+this.id+"Canvas' class='fly-editor-canvas fly-dashboard-editor-canvas'></canvas>");
    	this.editorCanvas.appendTo(this.editorWrapper);
    	this.editorCanvas = window.G_vmlCanvasManager.initElement(this.editorCanvas.get(0));
    } else {
    	this.editorCanvas = $("<canvas id='"+this.id+"Canvas' class='fly-editor-canvas fly-dashboard-editor-canvas'></canvas>");
    	this.editorCanvas.appendTo(this.editorWrapper);
    	this.editorCanvas = this.editorCanvas.get(0);
    }
	
	this.ctx = this.editorCanvas.getContext('2d');
	
	
	var _self = this;
	
	this.editorWrapper
		.bind("mousedown", function(e){
			_self.holder = true;
			
			_self.initDomsProp();
			
			_self.mouseDownCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
			_self.mouseDownDom = _self.getDomByMouseCoords(_self.mouseDownCoords);
			
			// 选择一个树节点
			_self.domStructureTree.select(_self.mouseDownDom.id);
			
			// 如果按下shift，认为是多选
			if(window["__global_hold_key"] && window["__global_hold_key"] == 16){
			}else{
				// 如果选择对象已经被选中，不清除所有选中
				if(!_self.isSelected(_self.mouseDownDom.id)){
					_self.clearSelected();
				}
			}
			_self.addSelected(_self.mouseDownDom.id,_self.mouseDownDom);
			
			_self.redraw();
		})
		.bind("mouseout", function(e){
			_self.holder = false;
			_self.mouseDownCoords = undefined;
			_self.mouseDownDom = undefined;
			_self.mouseUpCoords = undefined;
			_self.mouseUpDom = undefined;
			_self.mouseMovingCoords = undefined;
			_self.mouseMovingDom = undefined;
			_self.redraw();
		})
		.bind("mousemove", function(e){
			if(_self.holder){
				
				_self.mouseMovingCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
				_self.mouseMovingDom = _self.getDomByMouseCoords(_self.mouseMovingCoords);
				
				// 判断拖动的元素能否放置在目标组件中
				for(var selectedId in _self.selected){
					dim = _self.selected[selectedId];
					if(dim){
						// 如果拖动ID与目标ID相同--false
						if(dim.id == _self.mouseMovingDom.id){
							_self.mouseMovingDom = undefined;
							break;
						}
						// 不能拖入直接父元素
						if(dim.parent){
							if(dim.parent.id == _self.mouseMovingDom.id){
								_self.mouseMovingDom = undefined;
								break;
							}
						}
						
						// GridItem只能拖入GridLayout或者GridItem
						if(dim.type=="fly:GridLayoutItem"){
							while(_self.mouseMovingDom){
								if(_self.mouseMovingDom.type=="fly:GridLayoutItem" || 
									_self.mouseMovingDom.type=="fly:GridLayout"){
									break;
								}else{
									_self.mouseMovingDom = _self.mouseMovingDom.parent;
									
								}
							}
							if(!_self.mouseMovingDom){
								break;
							}
						}
					}
				}
			
				_self.redraw({"drawMove":true});
			}
		})
		.bind("mouseup", function(e){
			_self.initDomsProp();
			
			_self.mouseUpCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
			_self.mouseUpDom = _self.getDomByMouseCoords(_self.mouseUpCoords);
			
			// 鼠标抬起对象是可接受的放置对象时，进行移动操作 
			if(_self.mouseMovingDom){
				var sources = [];
				for(var selectedId in _self.selected){
					if(_self.selected[selectedId]){
						sources.push(selectedId);
					}
				}
				_self.move(sources,_self.mouseMovingDom.id);
			}
			
			_self.mouseMovingDom = undefined;
			_self.holder = false;
			_self.redraw();
		});
	
	// 接受拖拽事件
	this.editorWrapper.droppable({
		accept: '.fly-dashboard-step-plugin',
		onDragEnter:function(e,source,data){
			_self.editorWrapper.addClass("ui-state-highlight");
			
			_self.appender = true;
			_self.initDomsProp();
			
			_self.redraw();
		},
		onDragLeave: function(e,source,data){
			_self.editorWrapper.removeClass("ui-state-highlight");
			
			_self.appender = false;
			_self.mouseMovingDom = undefined;
			_self.redraw();
		},
		onDragOver: function(e,source,data){
			if(_self.appender){
				_self.mouseMovingDom = _self.getDomByMouseCoords({x:data.x,y:data.y});
				
				// GridItem只能拖入GridLayout或者GridItem
				if($(source).data("data").id=="fly:GridLayoutItem"){
					while(_self.mouseMovingDom){
						if(_self.mouseMovingDom.type=="fly:GridLayoutItem" || 
							_self.mouseMovingDom.type=="fly:GridLayout"){
							break;
						}else{
							_self.mouseMovingDom = _self.mouseMovingDom.parent;
						}
					}
				}
			}
			
			_self.redraw();
		},
		onDrop: function(e,source,data){
			_self.editorWrapper.removeClass("ui-state-highlight");
			
			_self.initDomsProp();
			_self.mouseDownDom = _self.getDomByMouseCoords({x:data.x,y:data.y});
			
			if(_self.mouseMovingDom){
				_self.append($(source).data("data").id,_self.mouseMovingDom.id);
			}
			
			_self.appender = false;
			_self.mouseMovingDom = undefined;
			_self.redraw();
		}
	});
	
	this.flush(this.cfg.data);
	
};

/**
 * 通过坐标，获得Dom
 */
Plywet.widget.DashboardEditor.prototype.getDomByMouseCoords = function(m) {
	var dim = getSubDomByMouseCoords(this.domsProp);
	if(!dim){
		dim = this.domsProp[0];
	}
	return dim;
	
	function getSubDomByMouseCoords(domsProp){
		var dim;
		for (var i=0;i<domsProp.length;i++) {
			dim = domsProp[i];
			
			if(dim.offsetLeft <= m.x && (dim.offsetLeft+dim.offsetWidth) >= m.x
				&& dim.offsetTop <= m.y && (dim.offsetTop+dim.offsetHeight) >= m.y){
				if(dim.children){
					dim = getSubDomByMouseCoords(dim.children) || dim;
				}
				return dim;
			}
		}
		return undefined;
	}
};

/**
 * 通过编辑ID，获得DomProp
 */
Plywet.widget.DashboardEditor.prototype.getDomPropById = function(id) {
	return _getDomPropById(this.domsProp);
	
	function _getDomPropById(domsProp){
		for (var i=0;i<domsProp.length;i++) {
			var dim = domsProp[i];
			if(dim.id == id){
				return dim;
			}else if(dim.children){
				dim = _getDomPropById(dim.children);
				if(dim){
					return dim;
				}
			}
		}
		return undefined;
	}
};

/**
 * 初始化Dom的属性集
 */
Plywet.widget.DashboardEditor.prototype.initDomsProp = function() {
	
	if(!this.domsProp) {
		
		this.domsProp = [];
		
		var editorDim = Plywet.getElementDimensions(this.editorContent);
		
		initSubDomsProp($(this.domStructure), editorDim, this.editorContent, this.domsProp, undefined, this);

	}
	
	function initSubDomsProp(node, editorDim, dom, domsProp, parentDomProp, target) {
		var domProp = Plywet.getElementDimensions(dom);
			
		domProp.offsetTop = domProp.offsetTop - editorDim.offsetTop;
		domProp.offsetLeft = domProp.offsetLeft - editorDim.offsetLeft;
		
		var subNodes = node.children();
		if(subNodes.size() > 0){
			domProp.children = [];
			for(var i=0;i<subNodes.size();i++){
				initSubDomsProp2($(subNodes[i]), editorDim, dom, domProp, target);
			}
		}
		
		initDomAttr(node, domProp, parentDomProp, target);
		
		domsProp.push(domProp);
	}
	
	function initSubDomsProp2(node, editorDim, pDom, pDomsProp, target){
		var id = node.attr("__editor_id"),
			dom = pDom.find("[__editor_id="+id+"]");
		initSubDomsProp(node, editorDim, dom, pDomsProp.children, pDomsProp, target);
	}
	
	/**
	 * 初始化Dom属性
	 * @param node 元素对应的树型节点
	 * @param domProp 元素对应的汇总属性集
	 * @param parentDomProp 父元素对应的汇总属性集引用
	 * @param target dashboard自身
	 */
	function initDomAttr(node, domProp, parentDomProp, target) {
		domProp.id = node.attr("__editor_id");
		domProp.type = node.attr("__editor_type");
		domProp.category = node.attr("__editor_category");
		
		var props;
		
		// Fly组件
		if(domProp.type){
			var plugin = target.getPlugin(domProp.type);
			// 构件
			if(plugin.size() == 0){
				props = [{name:"objectId",description:"对象ID",tooltip:"对象ID"}];
			}
			// 其他Fly组件
			else{
				props = plugin.data("data")["props"];
			}
		}
		// HTML组件
		else{
			props = [{name:"objectId",description:"对象ID",tooltip:"对象ID"}];
		}
		
		// 获得一份plugin的属性集
		domProp.props = transformAttrForTreeGrid(props, node);
		
		domProp.parent = parentDomProp;
		
	}
	
	function transformAttrForTreeGrid(array, node){
		var rtn = [];
		for(var i=0;i<array.length;i++){
			var conf = {};
			conf.propId="prop_"+array[i].name;
			if(array[i].attrs&&array[i].attrs.length>0){
				conf.propName="<b>"+array[i].description+"</b>";
			}else{
				conf.propName=array[i].description;
			}
			// 使用node的属性值替换
			if(array[i].name == "objectName"){
				conf.propValue=node.attr("objectName")||node.attr("id")||node.attr("name")||"";
			}else{
				conf.propValue=node.attr(array[i].name)||"";
			}
			
			// 子属性
			if(array[i].attrs&&array[i].attrs.length>0){
				conf.children=transformAttrForTreeGrid(array[i].attrs, node);
			}
			rtn.push(conf);
		}
		return rtn;
	}
};

/**
 * 移动元素
 * @param sources 来源元素（集合）
 * @param target 目标元素
 */
Plywet.widget.DashboardEditor.prototype.move = function(sources,target) {
	var params = {type:"move",source:sources,target:target};
	
	// TODO
	console.log(params);
};

Plywet.widget.DashboardEditor.prototype.append = function(source,target) {
	var params = {typd:"append",source:source,target:target};
	
	// TODO
	console.log(params);
};

Plywet.widget.DashboardEditor.prototype.flush = function(data) {
	this.dom = data.dom;
	this.script = data.script;
	this.domStructure = $(data.domStructure);
	
	this.domsProp = null;
	
	this.editorContent.html(this.dom);
	if(this.script){
		for(var j=0;j<this.script.length;j++){
			try{
				eval(this.script[j]);
			}catch(e){
				Plywet.Logger.error(this.script[j]);
			}
		}
	}
	
	this.width = parseInt(this.domStructure.attr("width"));
	this.height = parseInt(this.domStructure.attr("height"));
	changeSize(this.width, this.height, this);
	
	if(!this.domStructureTree){
		var config = {
			id : 			"dashboardStructPanelContent"
			,dnd :			true
			,onSelect : 	Plywet.editors.dashboard.action.struct_on_select
			,els :			[getNodeStructure(this.domStructure, this)]
		};
		this.domStructureTree = new Plywet.widget.EasyTree(config);
	}
	
	
	if(!this.domProp){
		var config = {
			id: 			"dashboardPropPanelContent"
			,animate:		true
			,collapsible:	true
//			,singleSelect:	true
			,idField:		"propId"
			,treeField:		"propName"
			,showIcon: 		false
			,frozenColumns:[[
                {field:'propName',title:'属性',width:120,
	                formatter:function(value){
	                	return value;
	                }
                }
			]]
			,columns:[[
				{field:'propValue',title:'值',width:150,editor:'text'}
			]]
		};
		this.domProp = new Plywet.widget.EasyTreeGrid(config);
	}
	
	if(!this.signalGrid){
		var config = {
			id:				"dashboardSignalPanelContent"
//			,singleSelect:	false
			,columns:		[
		        [{
		        	field: 'sender',
	                title: "发送者",
	                width: 150,
		      	}
		        ,{
		        	field: 'signal',
	                title: "信号",
	                width: 150,
		      	}
		        ,{
		        	field: 'accepter',
	                title: "接收者",
	                width: 150,
		      	}
		        ,{
		        	field: 'slot',
	                title: "槽",
	                width: 150,
		      	}]
			]
		};
		
		this.signalGrid = new Plywet.widget.Grid(config);
	}
	
	function changeSize(w, h, _self) {
		var $canvas = $(_self.editorCanvas);
		
		if (w) {
			_self.width= w || 600;
			_self.editorWrapper.width(w+10);
			_self.editorContent.width(w);
			
			if ($.browser.msie && $.browser.version < 9){
				$canvas.width(w+10);
			} else {
				$canvas.attr("width", w+10);
			}
		}
		if (h) {
			_self.height= h || 400;
			_self.editorWrapper.height(h+10);
			_self.editorContent.height(h);
			
			if ($.browser.msie && $.browser.version < 9){
				$canvas.height(h+10);
			} else {
				$canvas.attr("height", h+10);
			}
		}
		
		_self.redraw();
	}
	
	function getNodeStructure(node, _self) {
		var domId = node.attr("id");
		if(!domId){
			domId = "<无名称>";
		}
		var id = node.attr("__editor_id"),
			type = node.attr("__editor_type"),
			typeName = "";
		if(type){
			var plugin = _self.getPlugin(type);
			if(plugin.size() == 0){
				typeName = "构件";
			}else{
				typeName = plugin.data("data")["displayName"];
			}
		}else{
			type = typeName = node.get(0).tagName;
		}
		var rtn = {
			id : id
			,type : "node"
			,data : {
				pluginType : type
			}
			,displayName : "<b>"+domId+"</b>&nbsp;&nbsp;["+typeName+"]"
		};
		
		var subNodes = node.children();
		if(subNodes.size() > 0){
			var els = [];
			for(var i=0;i<subNodes.size();i++){
				els.push(getNodeStructure($(subNodes[i]), _self));
			}
			rtn.els = els;
		}
		
		return rtn;
	}
	
};

Plywet.widget.DashboardEditor.prototype.getPlugin = function(type) {
	return this.dashboardStepBar.find(Plywet.escapeClientId("leaf:"+type));
};

Plywet.widget.DashboardEditor.prototype.redraw = function(cfg) {
	
	cfg = cfg || {};
	
	this.ctx.clearRect(0,0,this.width+10,this.height+10);
	
	this.ctx.lineWidth=1;
	
	var _self = this;
	
	var dim, mavingOff;
	
	if(cfg.drawMove){
		mavingOff = {
			x : this.off.x + this.mouseMovingCoords.x - this.mouseDownCoords.x,
			y : this.off.y + this.mouseMovingCoords.y - this.mouseDownCoords.y
		};
	}
	
	for(var selectedId in this.selected){
		dim = this.selected[selectedId];
		if(dim){
			// 绘制选中框
			redrawSelectedComponents(dim);
			
			// 绘制拖动框
			if(cfg.drawMove){
				redrawMovingComponents(dim, mavingOff);
			}
		}
	}
	
	if(_self.mouseMovingDom){
		redrawMovingTargetComponents();
	}
	
	function redrawMovingTargetComponents() {
		var dim = _self.mouseMovingDom;
		if(dim){
			Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.dropRectStyle, "line", _self.off);
		}
	}
	
	function redrawMovingComponents(dim, mavingOff) {
		Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.drawRectStyle, "dotted", mavingOff);
	}
	
	function redrawSelectedComponents(dim) {
		
		Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.drawRectStyle, "line", _self.off);
		Plywet.widget.FlowChartUtils.drawResizer(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight},
				_self.off,_self.drawResizerStyle);
	}

};

Plywet.editors.dashboard.action = {
	struct_on_select : function(node){

		// 显示属性
		props = dashboardEditorPanel_var.getDomPropById(node.id).props;
		
		dashboardEditorPanel_var.domProp.setUrlData(props);
		
		// 页面点击独立处理这部分
		if(!dashboardEditorPanel_var.holder){
			dashboardEditorPanel_var.clearSelected();
			dashboardEditorPanel_var.addSelected(node.id);
			dashboardEditorPanel_var.redraw();
		}
		
	}
};










