// 表单
Plywet.editors.dashboard = {
	saveStatus : function ($tabo) {
		$tabo.data("exdata",dashboardEditorPanel_var.getData())
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
			dashboardEditorPanel_var.reInitEditor(tabData);
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
								,	onresize_end: 	function(pane){
									dashboardEditorPanel_var.domProp.resize();
								}
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
	// 设计器ID
	this.id = this.cfg.id;
	this.eidtor = $(Plywet.escapeClientId(this.id));
	
	// 结构框的ID
	this.structureId = this.cfg.structureId;
	this.structure = $(Plywet.escapeClientId(this.structureId));
	
	// 属性框的ID
	this.propId = this.cfg.propId;
	this.prop = $(Plywet.escapeClientId(this.propId));
	
	// Dashboard节点Bar
	this.dashboardStepBar = $(Plywet.escapeClientId("dashboardStepBar"));
	
	this.component = {
		selected : {},
		drawStyle : {
			drawRectFillStyle : {strokeStyle:'#f00',fillStyle:'#f00',isFill:true},
			drawRectStyle : {strokeStyle:'#f00'},
			dropRectStyle : {strokeStyle:'#0972a5',fillStyle:'#0972a5',isFill:true},
			drawResizerStyle : {fillStyle:'#a0a0a0'}
		},
		clearSelected : function(){
			this.selected = {};
		},
		addSelected : function(domId,dom,_self) {
			
			if(!dom){
				dom = _self.getDomPropById(domId);
			}
			console.log(domId);
			console.log(dom);
			this.selected[domId]=dom;
		},
		isSelected : function(domId) {
			return this.selected[domId];
		}
//		isSelectedByCoords : function(m){
//			var dim;
//			for(var selectedId in this.selected){
//				dim = this.selected[selectedId];
//				if(dim){
//					if(dim.offsetLeft <= m.x && (dim.offsetLeft+dim.offsetWidth) >= m.x
//						&& dim.offsetTop <= m.y && (dim.offsetTop+dim.offsetHeight) >= m.y){
//						return dim;
//					}
//				}
//			}
//			return false;
//		},
//		getSelectedSize : function() {
//			var size = 0;
//			for(var selectedId in this.selected){
//				if(this.selected[selectedId]){
//					size++;
//				}
//			}
//			return size;
//		}
	};
	
	this.off = {x:5,y:5};
	
	// 鼠标一次按下抬起操作
	this.holder = false;
	
	this.initEditor();
	
};

/**
 * 用于切换编辑器窗口的重新加载初始化页面
 * @param data 刷新数据
 */
Plywet.widget.DashboardEditor.prototype.reInitEditor = function(data){
	this.clearDomsProp();
	this.clearDomPropPanel();
	this.reloadData(data);
	this.redraw();
};

Plywet.widget.DashboardEditor.prototype.clearDomPropPanel = function() {
	// 如果没有选中的节点，或者选中的节点不再存在，清空
	if(this.domProp){
		this.domProp.loadData([]);
	}
};

Plywet.widget.DashboardEditor.prototype.clearDomsProp = function(){
	// 所有dom对象的属性集合
	this.domsProp = undefined;
	this.senderDoms = undefined;
	this.senderOptions = undefined;
	this.accepterDoms = undefined;
	this.accepterOptions = undefined;
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
				
				if(_self.reportInfo.editorState == "component"){
					onMouseMoveForComponent(_self);
				} else if(_self.reportInfo.editorState == "signal_slot"){
					onMouseMoveForSignalSlot(_self);
				} else if(_self.reportInfo.editorState == "buddy"){
					onMouseMoveForBuddy(_self);
				} else if(_self.reportInfo.editorState == "tab_sequence"){
					onMouseMoveForTabSequence(_self);
				}
			
			}
			
			function onMouseMoveForComponent(_self){
				// 判断拖动的元素能否放置在目标组件中
				for(var selectedId in _self.component.selected){
					var dim = _self.component.selected[selectedId];
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
						
						// 不能拖入子元素
						var movingDomParent = _self.mouseMovingDom.parent;
						while(movingDomParent){
							if(dim.id == movingDomParent.id){
								_self.mouseMovingDom = undefined;
								break;
							}else{
								movingDomParent = movingDomParent.parent;
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
			
			function onMouseMoveForSignalSlot(_self){
				// TODO
				_self.redraw();
			}
			
			function onMouseMoveForBuddy(_self){
				// TODO
				_self.redraw();
			}
			
			function onMouseMoveForTabSequence(_self){
				// TODO
				_self.redraw();
			}
		})
		.bind("mouseup", function(e){
			_self.initDomsProp();
			
			_self.mouseUpCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
			_self.mouseUpDom = _self.getDomByMouseCoords(_self.mouseUpCoords);
			
			if(_self.reportInfo.editorState == "component"){
				onMouseUpForComponent(_self);
			} else if(_self.reportInfo.editorState == "signal_slot"){
				onMouseUpForSignalSlot(_self);
			} else if(_self.reportInfo.editorState == "buddy"){
				onMouseUpForBuddy(_self);
			} else if(_self.reportInfo.editorState == "tab_sequence"){
				onMouseUpForTabSequence(_self);
			}
			
			function onMouseUpForComponent(_self){
				// 鼠标抬起对象是可接受的放置对象时，进行移动操作 
				if(_self.mouseMovingDom){
					var sources = [];
					for(var selectedId in _self.component.selected){
						if(_self.component.selected[selectedId]){
							sources.push(selectedId);
						}
					}
					_self.move(sources,_self.mouseMovingDom.id);
				} else {
					// 鼠标抬起对象是选中对象，将鼠标抬起对象设置为选中对象的父对象
					if(_self.component.isSelected(_self.mouseUpDom.id)){
						// 选择父对象
						_self.mouseUpDom = _self.mouseUpDom.parent;
					}
					
					// 选择一个树节点
					_self.domStructureTree.select(_self.mouseUpDom.id);
					
					// 如果按下shift，认为是多选
					if(window["__global_hold_key"] && window["__global_hold_key"] == 16){
					}else{
						// 如果选择对象已经被选中，不清除所有选中
						if(!_self.component.isSelected(_self.mouseUpDom.id)){
							_self.component.clearSelected();
						}
					}
					_self.component.addSelected(_self.mouseUpDom.id,_self.mouseUpDom,_self);
					
				}
				
				_self.mouseMovingDom = undefined;
				_self.holder = false;
				_self.redraw();
			}
			
			function onMouseUpForSignalSlot(_self){
				// TODO
				_self.redraw();
			}
			
			function onMouseUpForBuddy(_self){
				// TODO
				_self.redraw();
			}
			
			function onMouseUpForTabSequence(_self){
				// TODO
				_self.redraw();
			}
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
	
	this.reloadData(this.cfg.data);
	this.redraw();
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
	this.initDomsProp();
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
		
		var props,signals,slots,displayName,nodeId="<无名称>";
		
		// Fly组件
		if(domProp.type){
			var plugin = target.getPlugin(domProp.type);
			// 构件
			if(plugin.size() == 0){
				props = [{name:"objectId",description:"对象ID",tooltip:"对象ID"}];
				displayName = "[构件]";
			}
			// 其他Fly组件
			else{
				props = plugin.data("data")["props"];
				signals = plugin.data("data")["signals"];
				slots = plugin.data("data")["slots"];
				displayName = plugin.data("data")["displayName"];
			}
		}
		// HTML组件
		else{
			props = [{name:"objectId",description:"对象ID",tooltip:"对象ID"}];
			displayName = node.get(0).tagName;
		}
		
		// 获得一份plugin的属性集
		domProp.props = transformAttrForTreeGrid(props, node);
		if(signals){
			domProp.signals = signals;
		}
		if(slots){
			domProp.slots = slots;
		}
		
		nodeId=node.attr("objectName")||node.attr("id")||node.attr("name")||nodeId;
		domProp.displayName = "<b>"+nodeId+"</b>&nbsp;&nbsp;"+displayName;
		
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
	if(!sources || sources.length==0){
		return;
	}
	if(!target){
		return;
	}
	
	// 设置tab为修改状态 
	Plywet.editors.dashboard.action.modify();
	
	var _self = this;
	Plywet.ab({
		type : "get",
		url: "rest/dashboard/move/"+this.reportInfo.id,
		params: {
			source :	sources,
			target :	target
		},
		onsuccess : function(data, status, xhr){
			_self.reInitEditor(data);
			_self.redraw();
		}
	});
};

Plywet.widget.DashboardEditor.prototype.append = function(source,target) {
	if(!source || !target){
		return;
	}
	
	// 设置tab为修改状态 
	Plywet.editors.dashboard.action.modify();
	
	var _self = this;
	Plywet.ab({
		type : "get",
		url: "rest/dashboard/append/"+this.reportInfo.id,
		params: {
			source :	source,
			target :	target
		},
		onsuccess : function(data, status, xhr){
			_self.reInitEditor(data);
			_self.redraw();
		}
	});
};

Plywet.widget.DashboardEditor.prototype.getData = function() {
	var data = {};
	data.dom = this.dom;
	data.script = this.script;
	data.domStructure = this.domStructure;
	data.reportInfo = this.reportInfo;
	data.reportInfo.component = this.component;
	
	data.reportInfo.domsProp = this.domsProp;
	data.reportInfo.senderDoms = this.senderDoms;
	data.reportInfo.senderOptions = this.senderOptions;
	data.reportInfo.accepterDoms = this.accepterDoms;
	data.reportInfo.accepterOptions = this.accepterOptions;
	return data;
};

/**
 * 重新加载数据
 */
Plywet.widget.DashboardEditor.prototype.reloadData = function(data) {
	/**
	 * 恢复数据 Start
	 */
	this.reportInfo = data.reportInfo;
	this.dom = data.dom;
	this.script = data.script;
	this.domStructure = $(data.domStructure);
	
	// 报表显示信息
	this.reportInfo.editorState = this.reportInfo.editorState || "component";
	
	// 在编辑组件状态时：选中的对象
	this.reportInfo.component = this.reportInfo.component || {};
	this.reportInfo.component.selected = this.reportInfo.component.selected || {};
	this.component.selected = this.reportInfo.component.selected;
	
	this.domsProp = this.reportInfo.domsProp;
	this.senderDoms = this.reportInfo.senderDoms;
	this.senderOptions = this.reportInfo.senderOptions;
	this.accepterDoms = this.reportInfo.accepterDoms;
	this.accepterOptions = this.reportInfo.accepterOptions;
	/**
	 * 恢复数据 End
	 */
	
	var _self = this;
	
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
	
	// 1.DOM结构树
	if(!this.domStructureTree){
		var config = {
			id : 			"dashboardStructPanelContent"
			,dnd :			true
			,onSelect : 	Plywet.editors.dashboard.action.struct_on_select
			,els :			[getNodeStructure(this.domStructure, this)]
		};
		this.domStructureTree = new Plywet.widget.EasyTree(config);
	}else{
		// 重新加载树
		this.domStructureTree.loadData([getNodeStructure(this.domStructure, this)]);
		
		if(this.reportInfo.editorState == "component"){
			if(this.component.selected){
				for(var comp in this.component.selected){
					this.domStructureTree.select(comp);
					break;
				}
			}
		}
	}
	
	// 2.DOM属性栏
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
	
	// 3.信号和槽列表
	if(!this.signalGrid){
		var config = {
			id:				"dashboardSignalPanelContent"
			,singleSelect:	true
			,columns:		[
		        [{
		        	field: 'sender',
	                title: '发送者',
	                width: 150,
	                editor: {
		        		type: 'combobox',
		        		options: {
		        			loader:function(data, onsuccess, onerror) {
		        				_self.initSenders();
		        				onsuccess(_self.senderOptions);
		        			},
		        			onSelect:function(target){
		        				var senderDom = _self.senderDoms[target.value];
		        				console.log($(this).combobox("getValues"));
		        			}
		        		}
		        	}
		      	}
		        ,{
		        	field: 'signal',
	                title: '信号',
	                width: 150,
	                editor: {
		        		type: 'combobox',
		        		options: {
		        			loader:function(data, onsuccess, onerror) {
		        				onsuccess(data);
		        			}
		        		}
		        	}
		      	}
		        ,{
		        	field: 'accepter',
	                title: '接收者',
	                width: 150,
	                editor: {
		        		type: 'combobox',
		        		options: {
		        			loader:function(data, onsuccess, onerror) {
		        				_self.initAccepters();
		        				onsuccess(_self.accepterOptions);
		        			},
		        			onSelect:function(e){
		        				var accepterDom = _self.accepterDoms[e.value];
		        			}
		        		}
		        	}
		      	}
		        ,{
		        	field: 'slot',
	                title: '槽',
	                width: 150,
	                editor: {
		        		type: 'combobox',
		        		options: {
		        			loader:function(data, onsuccess, onerror) {
		        				onsuccess(data);
		        			}
		        		}
		        	}
		      	}]
			]
		};
		
		this.signalGrid = new Plywet.widget.Grid(config);
	} else {
		// 重新加载
	}
	
	// 4.修改控制栏状态
	if(this.reportInfo.editorState == "component"){
		Plywet.editors.dashboard.action.editorComponent(true);
	}else if(this.reportInfo.editorState == "signal_slot"){
		Plywet.editors.dashboard.action.editorSignalSlot(true);
	}else if(this.reportInfo.editorState == "buddy"){
		Plywet.editors.dashboard.action.editorBuddy(true);
	}else if(this.reportInfo.editorState == "tab_sequence"){
		Plywet.editors.dashboard.action.editorTab(true);
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

/**
 * 获得消息发出者，满足条件如下：
 * 消息发出者的插件类型具有至少1个信号；
 */
Plywet.widget.DashboardEditor.prototype.initSenders = function() {
	if(!this.senderDoms){
		this.initDomsProp();
		this.senderDoms = {};
		this.senderOptions = [];
		getSubSender(this.domsProp,this);
	}
	
	function getSubSender(domsProp,_self){
		var domProp;
		for(var i=0;i<domsProp.length;i++){
			domProp = domsProp[i];
			if(domProp.signals){
				_self.senderDoms[domProp.id] = domProp.signals;
				var signal = {value:domProp.id,text:domProp.displayName};
				_self.senderOptions.push(signal);
			}
			if(domProp.children){
				getSubSender(domProp.children,_self);
			}
		}
	}
};

Plywet.widget.DashboardEditor.prototype.getSenderSignal = function(sender) {
	
};

Plywet.widget.DashboardEditor.prototype.initAccepters = function() {
	if(!this.accepterDoms){
		this.initDomsProp();
		this.accepterDoms = {};
		this.accepterOptions = [];
		getSubAccepter(this.domsProp,this);
	}
	
	function getSubAccepter(domsProp,_self){
		var domProp;
		for(var i=0;i<domsProp.length;i++){
			domProp = domsProp[i];
			if(domProp.slots){
				_self.accepterDoms[domProp.id] = domProp.slots;
				var slot = {value:domProp.id,text:domProp.displayName};
				_self.accepterOptions.push(slot);
			}
			if(domProp.children){
				getSubAccepter(domProp.children,_self);
			}
		}
	}
};

Plywet.widget.DashboardEditor.prototype.getAccepterSlot = function(accepter) {
	
};

Plywet.widget.DashboardEditor.prototype.redraw = function(cfg) {
	
	cfg = cfg || {};
	this.ctx.clearRect(0,0,this.width+10,this.height+10);
	this.ctx.lineWidth=1;
	
	var _self = this;
	
	if(this.reportInfo.editorState == "component"){
		redrawForComponent();
	} else if(this.reportInfo.editorState == "signal_slot"){
		redrawForSignalSlot();
	} else if(this.reportInfo.editorState == "buddy"){
		redrawForBuddy();
	} else if(this.reportInfo.editorState == "tab_sequence"){
		redrawForTabSequence();
	}
	
	function redrawForComponent(){
		var dim, mavingOff;
		
		if(cfg.drawMove){
			mavingOff = {
				x : _self.off.x + _self.mouseMovingCoords.x - _self.mouseDownCoords.x,
				y : _self.off.y + _self.mouseMovingCoords.y - _self.mouseDownCoords.y
			};
		}
		
		for(var selectedId in _self.component.selected){
			dim = _self.component.selected[selectedId];
			if(dim){
				// 绘制选中框
				redrawSelectedComponents(dim);
				
				// 绘制拖动框
				if(cfg.drawMove){
					redrawMovingComponents(dim, mavingOff);
				}
			}
		}
		
		// 绘制布局的外框
		if(_self.domsProp){
			redrawLayoutBorder(_self.domsProp);
		}
		
		if(_self.mouseMovingDom){
			redrawMovingTargetComponents();
		}
	}
	
	function redrawForSignalSlot(){
		// TODO
	}
	
	function redrawForBuddy(){
		// TODO
	}
	
	function redrawForTabSequence(){
		// TODO
	}
	
	function redrawLayoutBorder(domsProp){
		var dim;
		for (var i=0;i<domsProp.length;i++) {
			dim = domsProp[i];
			
			if(dim.type == "fly:GridLayoutItem"){
				redrawLayoutComponent(dim);
			}
			
			if(dim.children){
				redrawLayoutBorder(dim.children);
			}
		}
	}
	
	function redrawLayoutComponent(dim) {
		if(dim){
			Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
					{x:(dim.offsetLeft+2),y:(dim.offsetTop+2),width:(dim.offsetWidth-4),height:(dim.offsetHeight-4)}, 
					_self.component.drawStyle.drawRectStyle, "line", _self.off);
		}
	}
	
	function redrawMovingTargetComponents() {
		var dim = _self.mouseMovingDom;
		if(dim){
			Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.component.drawStyle.dropRectStyle, "line", _self.off);
		}
	}
	
	function redrawMovingComponents(dim, mavingOff) {
		Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.component.drawStyle.drawRectStyle, "dotted", mavingOff);
	}
	
	function redrawSelectedComponents(dim) {
		Plywet.widget.FlowChartUtils.drawResizer(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight},
				_self.off,_self.component.drawStyle.drawResizerStyle);
	}

};

Plywet.editors.dashboard.action = {
	tb : Plywet.editors.toolbarButton,
	editorPrefix : "dashboard",
	ids : {
		editors : ["editor_component","editor_signal_slot",
		           "editor_buddy","editor_tab_sequence"],
		layouts : ["layout_horizontal","layout_vertical",
		           "layout_grid","layout_broke"],
		operators : ["resize","preview"]
		
	},
	getId : function(id){
		if(typeof(id)=="string"){
			return this.editorPrefix+"_"+id;
		}else if(id instanceof Array){
			var rtn = [];
			for(var i=0;i<id.length;i++){
				rtn.push(this.editorPrefix+"_"+id[i]);
			}
			return rtn;
		}
	},
	// 表示修改
	modify : function(){
    	
		diEditorPageTabs.setTabModify(null, true);
    },
    // @Override 必要方法：用于在Tab发生修改时，点击保存按钮调用的方法。
    saveTab : function (clicked) {
    	console.log(clicked.data("exdata"));
    },
    // @Override 必要方法：用于在Tab发生修改时，点击放弃按钮调用的方法。
    discardTab : function (clicked) {
    	console.log(clicked.data("exdata"));
    },
    
	struct_on_select : function(node){

		// 显示属性
		var props = dashboardEditorPanel_var.getDomPropById(node.id).props;
		
		dashboardEditorPanel_var.domProp.loadData(props);
		
		// 页面点击独立处理这部分
		if(!dashboardEditorPanel_var.holder){
			dashboardEditorPanel_var.component.clearSelected();
			dashboardEditorPanel_var.component.addSelected(node.id,undefined,dashboardEditorPanel_var);
			dashboardEditorPanel_var.redraw();
		}
		
	},
	
	signal_add_on_click : function(){
		this.modify();
		dashboardEditorPanel_var.signalGrid.jq.datagrid("appendRow", {
			sender: '',
			signal: '',
			accepter: '',
			slot: ''
		});
		var lastIndex = dashboardEditorPanel_var.signalGrid.jq.datagrid("getRows").length - 1;
		for(var i=0;i<lastIndex;i++){
			dashboardEditorPanel_var.signalGrid.jq.datagrid('endEdit', i);
		}
		dashboardEditorPanel_var.signalGrid.jq.datagrid('beginEdit', lastIndex);
	},
	
	signal_delete_on_click : function(){
		this.modify();
		var row = dashboardEditorPanel_var.signalGrid.jq.datagrid('getSelected');
        if (row) {
            var index = dashboardEditorPanel_var.signalGrid.jq.datagrid('getRowIndex', row);
            dashboardEditorPanel_var.signalGrid.jq.datagrid('deleteRow', index);
        }
	},
	
	editorComponent : function(no_redraw){
		this.tb.inactive(this.getId(this.ids.editors));
		this.tb.active(this.getId("editor_component"));
		if(!no_redraw){
			dashboardEditorPanel_var.reportInfo.editorState = "component";
			dashboardEditorPanel_var.redraw();
		}
	},
	editorSignalSlot : function(no_redraw){
		this.tb.inactive(this.getId(this.ids.editors));
		this.tb.active(this.getId("editor_signal_slot"));
		if(!no_redraw){
			dashboardEditorPanel_var.reportInfo.editorState = "signal_slot";
			dashboardEditorPanel_var.redraw();
		}
	},
	editorBuddy : function(no_redraw){
		this.tb.inactive(this.getId(this.ids.editors));
		this.tb.active(this.getId("editor_buddy"));
		if(!no_redraw){
			dashboardEditorPanel_var.reportInfo.editorState = "buddy";
			dashboardEditorPanel_var.redraw();
		}
	},
	editorTab : function(no_redraw){
		this.tb.inactive(this.getId(this.ids.editors));
		this.tb.active(this.getId("editor_tab_sequence"));
		if(!no_redraw){
			dashboardEditorPanel_var.reportInfo.editorState = "tab_sequence";
			dashboardEditorPanel_var.redraw();
		}
	},
	layoutHorizontal : function(){
		console.log("layoutHorizontal");
	},
	layoutVertical : function(){
		console.log("layoutVertical");
	},
	layoutGrid : function(){
		console.log("layoutGrid");
	},
	layoutBroke : function(){
		console.log("layoutBroke");
	},
	resize : function(){
		console.log("resize");
	},
	preview : function(){
		console.log("preview");
	},
	save : function(){
    	console.log("save");
    }
};










