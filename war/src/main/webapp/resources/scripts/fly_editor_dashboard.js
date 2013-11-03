// 表单
Flywet.editors.dashboard = {
	saveStatus : function ($tabo) {
		$tabo.data("exdata",dashboardEditorPanel_var.getData())
	},
	reloadStatus : function ($taba) {
		if (typeof($taba.data("exdata"))=='undefined')return;
		var tabData = $taba.data("exdata");

		// 使其显示，再进行操作
		$("#dashboard").show();
		dashboardEditorPanel_var.reInitEditor(tabData);
	},
	resize : function(){
		// TODO 工具箱的滚动按钮
		
	},
	register : function(){
		if(Flywet.editors.register["dashboard"]){
			return;
		}
		
		Flywet.ab({
			type : "get",
			url: "rest/report/dashboard/editor",
			modalMessage: "正在注册表单设计器页面...",
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
						,	sliderTip:				"弹出工具箱"
						,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-e'></div>"
						,	resizable:				false
//						,	initClosed:				true
					}
					, east : {
						size : 250
						,	paneSelector : "#dashboardPropBar"
						,	togglerTip_open:		"关闭属性框"
						,	togglerTip_closed:		"打开属性框"
						,	sliderTip:				"弹出属性箱"
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
								,	togglerTip_open:		"关闭信号/槽框"
								,	togglerTip_closed:		"打开信号/槽框"
								,	sliderTip:				"弹出信号/槽框"
								,  	resizerTip:				"调整信号/槽框高度"
								,	togglerAlign_closed:	"right"
								,	togglerLength_closed:	36
								,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-n'></div>"
							}
						}
					}
				});
				
				
				Flywet.cw("EasyTabs","diEditordashboardStepBar",{
					id : "dashboardStepBar"
				});
				Flywet.cw("Scrollbar","dashboardStepBarScroll",{
					id:'dashboardStepBar',
					tabGroup:'dashboardStepBar-ul',
					step:80,
					scrollType:'vertical'
				});
				Flywet.cw("DashboardEditor","dashboardEditorPanel_var",{
					id: "dashboardEditorPanel",
					structureId: "dashboardStructPanel",
					propId: "dashboardPropBar"
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
				
				Flywet.editors.register["dashboard"] = "Y";
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


Flywet.widget.DashboardEditor = function(cfg) {
	this.cfg = cfg;
	// 设计器ID
	this.id = this.cfg.id;
	this.eidtor = $(Flywet.escapeClientId(this.id));
	this.editorWrapper = $(Flywet.escapeClientId(this.id+"Wrapper"));
	this.editorContent = $(Flywet.escapeClientId(this.id+"Content"));
	
	// 结构框的ID
	this.structureId = this.cfg.structureId;
	this.structure = $(Flywet.escapeClientId(this.structureId));
	
	// 属性框的ID
	this.propId = this.cfg.propId;
	this.prop = $(Flywet.escapeClientId(this.propId));
	
	// Dashboard节点Bar
	this.dashboardStepBar = $(Flywet.escapeClientId("dashboardStepBar"));
	
	this.drawStyle = {
		drawRectFillStyle : {strokeStyle:'#f00',fillStyle:'#f00',isFill:true},
		drawRectStyle : {strokeStyle:'#f00'},
		dropRectStyle : {strokeStyle:'#0972a5',fillStyle:'#0972a5',isFill:true},
		drawResizerStyle : {fillStyle:'#a0a0a0'},
		drawLineStyle : {strokeStyle:'#00f',fillStyle:'#00f'}
	};
	
	this.component = {
		selected : {},
		clearSelected : function(){
			this.selected = {};
		},
		addSelected : function(domId,dom,_self) {
			if(!dom){
				dom = _self.getDomPropById(domId);
			}
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
	
	this.signalSlot = {
		selected : {},
		clearSelected : function(){
			this.selected = {};
		},
		addSelected : function(domId,dom,_self) {
			if(!dom){
				dom = _self.getDomPropById(domId);
			}
			// 判断是否有signal属性
			while(dom){
				if(dom.signals){
					break;
				}else{
					dom = dom.parent;
				}
			}
			if(dom){
				this.selected[domId]=dom;
			}
		}
	};
	
	this.off = {x:5,y:5};
	
	// 鼠标一次按下抬起操作
	this.holder = false;
	
	// 鼠标类型，调整位置：position ( move ); 调整尺寸：8个方向（e-resize,w-resize,n-resize,s-resize,ne-resize,nw-resize）
	this.mouseType = "";
	
	this.initEditor();
	
};

/**
 * 用于切换编辑器窗口的重新加载初始化页面
 * @param data 刷新数据
 */
Flywet.widget.DashboardEditor.prototype.reInitEditor = function(data){
	this.clearDomsProp();
	this.clearDomPropPanel();
	this.reloadData(data);
	this.redraw();
};

Flywet.widget.DashboardEditor.prototype.clearDomPropPanel = function() {
	// 如果没有选中的节点，或者选中的节点不再存在，清空
	if(this.domProp){
		this.domProp.loadData([]);
	}
};

Flywet.widget.DashboardEditor.prototype.clearDomsProp = function(){
	// 所有dom对象的属性集合
	this.domsProp = undefined;
	this.senderDoms = undefined;
	this.senderOptions = undefined;
	this.accepterDoms = undefined;
	this.accepterOptions = undefined;
};

Flywet.widget.DashboardEditor.prototype.initEditor = function() {

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
			_self.mouseDownCoords = Flywet.widget.FlowChartUtils.getMouseCoords(e);
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
			_self.mouseMovingCoords = Flywet.widget.FlowChartUtils.getMouseCoords(e);
			if(_self.holder){
				
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
			
			} else {
				if(_self.reportInfo.editorState == "component"){
					// 在鼠标没有holder住时进行鼠标状态判断
					// 判断是否是选中对象的调整块
					var mt = isResizeBlock(_self.mouseMovingCoords);
					if(mt){
						_self.mouseDownDom = mt[0];
						if( _self.mouseType != mt[1] ){
							_self.mouseType = mt[1];
							var cursor = mt[1]+"-resize";
							_self.editorWrapper.css('cursor',cursor);
						}
					}else{
						if(_self.mouseType != ""){
							_self.mouseType = "";
							_self.editorWrapper.css('cursor','auto');
						}
					}
				}
			}
			
			function isResizeBlock(c){
				for(var selectedId in _self.component.selected){
					var dim = _self.component.selected[selectedId],
						resizers = dim.resizers;
					for(var r in resizers){
						if(c.x>=resizers[r].x 
								&& c.x<=(resizers[r].x+resizers[r].width)
								&& c.y>=resizers[r].y 
								&& c.y<=(resizers[r].y+resizers[r].height)){
							return [dim, r];
						}
					}
				}
				return undefined;
			}
			
			function onMouseMoveForComponent(_self){
				// 只有当鼠标不是调整状态是默认状态时
				if(_self.mouseType == ""){
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
				}
				
				_self.redraw();
			}
			
			function onMouseMoveForSignalSlot(_self){
				while(_self.mouseMovingDom){
					if(_self.mouseMovingDom.slots){
						break;
					}else{
						_self.mouseMovingDom = _self.mouseMovingDom.parent;
					}
				}
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
			
			_self.mouseUpCoords = Flywet.widget.FlowChartUtils.getMouseCoords(e);
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
				// 只有当鼠标不是调整状态是默认状态时
				if(_self.mouseType == ""){
					
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
				
				}
				// 调整尺寸
				else{
					var newSize = _self.getNewSize(_self.mouseDownDom, _self.mouseUpCoords);
					_self.resized(_self.mouseDownDom.id,newSize);
				}
				
				_self.mouseMovingCoords = undefined;
				_self.mouseMovingDom = undefined;
				_self.holder = false;
				_self.redraw();
			}
			
			function onMouseUpForSignalSlot(_self){
				if(_self.mouseMovingDom){
					// TODO 添加
				}else{
					// 只能单选，首先清空选中
					_self.signalSlot.clearSelected();
					// 增加选中，判断节点具有signal属性
					_self.signalSlot.addSelected(_self.mouseUpDom.id,_self.mouseUpDom,_self);
				}
				
				_self.mouseMovingCoords = undefined;
				_self.mouseMovingDom = undefined;
				_self.holder = false;
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
			_self.mouseMovingCoords = undefined;
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
			_self.mouseMovingCoords = undefined;
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
Flywet.widget.DashboardEditor.prototype.getDomByMouseCoords = function(m) {
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
 * 通过ID获得选项
 * @param id 节点ID
 * @param type 选项类型：signals, slots
 */
Flywet.widget.DashboardEditor.prototype.getOptionsById = function(id,type) {
	var targetDom = this.getDomPropById(id)[type],
		targetOptions = [];
	for(var i=0;i<targetDom.length;i++){
		var opt = {};
		opt.value = targetDom[i].name;
		opt.text = targetDom[i].displayName;
		targetOptions.push(opt);
	}
	return targetOptions;
};

/**
 * 通过编辑ID，获得DomProp
 */
Flywet.widget.DashboardEditor.prototype.getDomPropById = function(id) {
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
Flywet.widget.DashboardEditor.prototype.initDomsProp = function() {
	
	if(!this.domsProp) {
		
		this.domsProp = [];
		
		var editorDim = Flywet.getElementDimensions(this.editorContent);
		
		initSubDomsProp($(this.domStructure), editorDim, this.editorContent, this.domsProp, undefined, this);
		
	}
	
	function initSubDomsProp(node, editorDim, dom, domsProp, parentDomProp, target) {
		var domProp = Flywet.getElementDimensions(dom);
			
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
 * 改变元素尺寸
 * @param 
 */
Flywet.widget.DashboardEditor.prototype.resized = function(target,newSize) {
	if(!target){
		return;
	}
	
	// 设置tab为修改状态 
	Flywet.editors.dashboard.action.modify();
	
	var _self = this;
	Flywet.ab({
		type : "get",
		url: "rest/dashboard/resized/"+this.reportInfo.id,
		params: {
			target :	target,
			x : newSize.x,
			y : newSize.y,
			width : newSize.width,
			height : newSize.height
		},
		onsuccess : function(data, status, xhr){
			_self.reInitEditor(data);
		}
	});
};

/**
 * 移动元素
 * @param sources 来源元素（集合）
 * @param target 目标元素
 */
Flywet.widget.DashboardEditor.prototype.move = function(sources,target) {
	if(!sources || sources.length==0){
		return;
	}
	if(!target){
		return;
	}
	
	// 设置tab为修改状态 
	Flywet.editors.dashboard.action.modify();
	
	var _self = this;
	Flywet.ab({
		type : "get",
		url: "rest/dashboard/move/"+this.reportInfo.id,
		params: {
			source :	sources,
			target :	target
		},
		onsuccess : function(data, status, xhr){
			_self.reInitEditor(data);
		}
	});
};

Flywet.widget.DashboardEditor.prototype.append = function(source,target) {
	if(!source || !target){
		return;
	}
	
	// 设置tab为修改状态 
	Flywet.editors.dashboard.action.modify();
	
	var _self = this;
	Flywet.ab({
		type : "get",
		url: "rest/dashboard/append/"+this.reportInfo.id,
		params: {
			source :	source,
			target :	target
		},
		onsuccess : function(data, status, xhr){
			_self.reInitEditor(data);
		}
	});
};

Flywet.widget.DashboardEditor.prototype.getData = function() {
	var data = {};
	data.dom = this.dom;
	data.script = this.script;
	data.domStructure = this.domStructure;
	data.reportInfo = this.reportInfo;
	data.reportInfo.component = {
		selected : this.component.selected
	};
	data.reportInfo.signalSlot = {
		selected : this.signalSlot.selected
	};
	
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
Flywet.widget.DashboardEditor.prototype.reloadData = function(data) {
	if(!data){
		return;
	}
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
	if(this.reportInfo.component){
		this.component.selected = this.reportInfo.component.selected || {};
	}else{
		this.component.selected = {};
	}
	// 在信号组件状态时：选中的对象
	if(this.reportInfo.signalSlot){
		this.signalSlot.selected = this.reportInfo.signalSlot.selected || {};
	}else{
		this.signalSlot.selected = {};
	}
	
	this.domsProp = this.reportInfo.domsProp;
	this.senderDoms = this.reportInfo.senderDoms;
	this.senderOptions = this.reportInfo.senderOptions;
	this.accepterDoms = this.reportInfo.accepterDoms;
	this.accepterOptions = this.reportInfo.accepterOptions;
	
	this.width = parseInt(this.domStructure.attr("width"));
	this.height = parseInt(this.domStructure.attr("height"));
	changeSize(this.width, this.height, this);
	
	/**
	 * 恢复数据 End
	 */
	var _self = this;
	this.editorContent.empty();
	this.editorContent.append(this.dom);
	if(this.script){
		for(var j=0;j<this.script.length;j++){
			try{
				eval(this.script[j]);
			}catch(e){
				Flywet.Logger.error(this.script[j]);
			}
		}
	}
	
	// 1.DOM结构树
	if(!this.domStructureTree){
		var config = {
			id : 			"dashboardStructPanelContent"
			,dnd :			true
			,onSelect : 	Flywet.editors.dashboard.action.struct_on_select
			,els :			[getNodeStructure(this.domStructure, this)]
		};
		this.domStructureTree = new Flywet.widget.EasyTree(config);
	}else{
		// 重新加载树
		this.domStructureTree.loadData([getNodeStructure(this.domStructure, this)]);
		
		// 只有在编辑状态才能选择树节点
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
		this.domProp = new Flywet.widget.EasyTreeGrid(config);
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
		        				var senderDom = _self.senderDoms[target.value],
		        					selectValue = $(this).combobox("getValues"),
		        					signalDom = $(this).parents("td[field='sender']").first()
		        						.next("td[field='signal']").first()
		        						.find(".ui-combobox-f").first();
		        				
		        				$(signalDom).combobox("loadData",_self.getOptionsById(selectValue, "signals"));
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
		        				var senderDom = $(this).parents("td[field='signal']").first()
        								.prev("td[field='sender']").first()
        								.find(".ui-combobox-f").first(),
        							senderValue = $(senderDom).combobox("getValues");
		        				console.log(senderValue);
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
		        			onSelect:function(target){
		        				var accepterDom = _self.accepterDoms[target.value],
			        				selectValue = $(this).combobox("getValues"),
		        					signalDom = $(this).parents("td[field='sender']").first()
		        						.next("td[field='signal']").first()
		        						.find(".ui-combobox-f").first();
		        				
		        				$(signalDom).combobox("loadData",_self.getOptionsById(selectValue, "slots"));
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
		
		this.signalGrid = new Flywet.widget.Grid(config);
	} else {
		// 重新加载
	}
	
	// 4.修改控制栏状态
	if(this.reportInfo.editorState == "component"){
		Flywet.editors.dashboard.action.editorComponent(true);
	}else if(this.reportInfo.editorState == "signal_slot"){
		Flywet.editors.dashboard.action.editorSignalSlot(true);
	}else if(this.reportInfo.editorState == "buddy"){
		Flywet.editors.dashboard.action.editorBuddy(true);
	}else if(this.reportInfo.editorState == "tab_sequence"){
		Flywet.editors.dashboard.action.editorTab(true);
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

Flywet.widget.DashboardEditor.prototype.getPlugin = function(type) {
	return this.dashboardStepBar.find(Flywet.escapeClientId("leaf:"+type));
};

/**
 * 获得消息发出者，满足条件如下：
 * 消息发出者的插件类型具有至少1个信号；
 */
Flywet.widget.DashboardEditor.prototype.initSenders = function() {
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

Flywet.widget.DashboardEditor.prototype.getSenderSignal = function(sender) {
	
};

Flywet.widget.DashboardEditor.prototype.initAccepters = function() {
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

Flywet.widget.DashboardEditor.prototype.getAccepterSlot = function(accepter) {
	
};

Flywet.widget.DashboardEditor.prototype.getNewSize = function(dim, coords){
	var x = dim.offsetLeft,
		y = dim.offsetTop,
		w = dim.offsetWidth,
		h = dim.offsetHeight,
		cx = coords.x - this.off.x,
		cy = coords.y - this.off.y,
		tmp;
	
	if(this.mouseType == "s"){
		h = cy - y;
	}else if(this.mouseType == "n"){
		tmp = y - cy;
		y = cy;
		h = h + tmp;
	}else if(this.mouseType == "w"){
		tmp = x - cx;
		x = cx;
		w = w + tmp;
	}else if(this.mouseType == "e"){
		w = cx - x;
	}else if(this.mouseType == "nw"){
		tmp = y - cy;
		y = cy;
		h = h + tmp;
		
		tmp = x - cx;
		x = cx;
		w = w + tmp;
	}else if(this.mouseType == "ne"){
		tmp = y - cy;
		y = cy;
		h = h + tmp;
		
		w = cx - x;
	}else if(this.mouseType == "sw"){
		h = cy - y;
		
		tmp = x - cx;
		x = cx;
		w = w + tmp;
	}else if(this.mouseType == "se"){
		h = cy - y;
		
		w = cx - x;
	}
	
	h=(h<10)?10:h;
	w=(w<10)?10:w;
	
	return {x:x,y:y,width:w,height:h};
};

Flywet.widget.DashboardEditor.prototype.redraw = function() {
	
	this.ctx.clearRect(0,0,this.width+10,this.height+10);
	this.ctx.lineWidth=1;
	
	var _self = this;
	if(this.reportInfo){
		if(this.reportInfo.editorState == "component"){
			redrawForComponent();
		} else if(this.reportInfo.editorState == "signal_slot"){
			redrawForSignalSlot();
		} else if(this.reportInfo.editorState == "buddy"){
			redrawForBuddy();
		} else if(this.reportInfo.editorState == "tab_sequence"){
			redrawForTabSequence();
		}
	}
	
	function redrawForComponent(){
		var dim, mavingOff;
		if(_self.mouseType == ""){
			if(_self.mouseMovingCoords){
				mavingOff = {
					x : _self.off.x + _self.mouseMovingCoords.x - _self.mouseDownCoords.x,
					y : _self.off.y + _self.mouseMovingCoords.y - _self.mouseDownCoords.y
				};
			}
		}
		
		// 绘制所有选中节点
		for(var selectedId in _self.component.selected){
			dim = _self.component.selected[selectedId];
			if(dim){
				// 绘制选中节点的边框
				redrawSelectedComponents(dim);
				
				// 绘制选中节点的拖动框
				if(_self.mouseType == ""){
					if(_self.mouseMovingCoords){
						redrawMovingComponents(dim, mavingOff);
					}
				}
			}
		}
		
		// 绘制所有布局的提示外框
		if(_self.domsProp){
			redrawLayoutBorder(_self.domsProp);
		}
		
		// 绘制拖动目标对象的边框
		if(_self.mouseType == ""){
			if(_self.mouseMovingDom){
				redrawMovingTargetComponents();
			}
		}
		// 绘制调整尺寸对象的边框
		else{
			if(_self.mouseMovingDom){
				redrawResizeTargetComponents();
			}
		}
	}
	
	function redrawResizeTargetComponents(){
		
		var newSize = _self.getNewSize(_self.mouseDownDom, _self.mouseMovingCoords);
		
		Flywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:newSize.x,y:newSize.y,width:newSize.width,height:newSize.height}, 
				_self.drawStyle.dropRectStyle, "line", _self.off);
	}
	
	function redrawForSignalSlot(){
		var dim;
		
		// 绘制所有选中节点
		for(var selectedId in _self.signalSlot.selected){
			dim = _self.signalSlot.selected[selectedId];
			if(dim){
				// 绘制选中节点的边框
				redrawSelectedComponents(dim);
				
				// 绘制目标节点
				if(_self.mouseMovingDom){
					redrawMovingTargetComponents();
				}
				
				// 绘制联系，起点->终点
				var startPoint = {
					x : dim.offsetLeft + _self.off.x + (dim.offsetWidth / 2),
					y : dim.offsetTop + _self.off.y + (dim.offsetHeight / 2)
				},
				endPoint = _self.mouseMovingCoords;
				
				if(endPoint){
					redrawSignalLine(startPoint, endPoint);
				}
				
				break;
			}
		}
	}
	
	function redrawForBuddy(){
		// TODO
	}
	
	function redrawForTabSequence(){
		// TODO
	}
	
	function redrawSignalLine(startPoint, endPoint){
		_self.ctx.save();
		_self.ctx.lineWidth = 1;
		_self.ctx.strokeStyle = _self.drawStyle.drawLineStyle.strokeStyle;
		
		_self.ctx.beginPath();
		Flywet.widget.FlowChartUtils.drawLine.factory(_self.ctx, "line", 
				startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		_self.ctx.stroke();
		
		
		var arc = (endPoint.y-startPoint.y) / (endPoint.x-startPoint.x);
		
		Flywet.widget.FlowChartUtils.drawArrow.factory(endPoint.x, endPoint.y,
				arc,(endPoint.x>=startPoint.x),{ctx:_self.ctx},"normal",_self.drawStyle.drawLineStyle);
		
		_self.ctx.restore();
		
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
			Flywet.widget.FlowChartUtils.drawRect(_self.ctx, 
					{x:(dim.offsetLeft+2),y:(dim.offsetTop+2),width:(dim.offsetWidth-4),height:(dim.offsetHeight-4)}, 
					_self.drawStyle.drawRectStyle, "line", _self.off);
		}
	}
	
	function redrawMovingTargetComponents() {
		var dim = _self.mouseMovingDom;
		if(dim){
			Flywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.drawStyle.dropRectStyle, "line", _self.off);
		}
	}
	
	function redrawMovingComponents(dim, mavingOff) {
		Flywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight}, 
				_self.drawStyle.drawRectStyle, "dotted", mavingOff);
	}
	
	function redrawSelectedComponents(dim) {
		var resizers = Flywet.widget.FlowChartUtils.drawResizer(_self.ctx, 
				{x:dim.offsetLeft,y:dim.offsetTop,width:dim.offsetWidth,height:dim.offsetHeight},
				_self.off,_self.drawStyle.drawResizerStyle);
		dim.resizers = resizers;
	}

};

Flywet.editors.dashboard.action = {
	tb : Flywet.editors.toolbarButton,
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
		baEditorPageTabs.setTabModify(null, true);
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










