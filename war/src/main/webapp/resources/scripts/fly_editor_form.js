// 表单
Plywet.editors.form = {
	saveStatus : function ($tabo) {
		console.log("save form");
	},
	reloadStatus : function ($taba) {
		
		if (typeof($taba.data("exdata"))=='undefined')return;
		var tabData = $taba.data("exdata");

		if (typeof formEditorPanel_var == 'undefined') {
			Plywet.cw("FormEditor","formEditorPanel_var",{
				id: "formEditorPanel",
				structureId: "formStructPanel",
				propId: "formPropBar",
				data: tabData
			});
		} else {
			formEditorPanel_var.flush(tabData);
		}
		
	},
	resize : function(){
		// TODO 工具箱的滚动按钮
		
	},
	register : function(){
		if(Plywet.editors.register["form"]){
			return;
		}
		
		Plywet.ab({
			type : "get",
			url: "rest/report/form/editor",
			beforeSend : function(){
				Plywet.desktop.changeMarkText("正在注册表单设计器页面...");
			},
			oncomplete : function(xhr, status){
				// 初始化尺寸
				var editorContainer = diEditorLayout.getPane("center"),
					editorContentWidth = editorContainer.outerWidth(),
					editorContentHeight = editorContainer.outerHeight()-40;
				var $form = $("#form");
				$form.width(editorContentWidth).height(editorContentHeight);
				$("#formEditorPanel").height(editorContentHeight - 40);
				
				// 结构
				$formLayout = $form.layout({
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
						,	paneSelector : "#formStepBar"
						,	togglerTip_open:		"关闭工具箱"
						,	togglerTip_closed:		"打开工具箱"
						,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-e'></div>"
						,	resizable:				false
//						,	initClosed:				true
					}
					, east : {
						size : 250
						,	paneSelector : "#formPropBar"
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
								,	paneSelector : "#formStructPanel"
							}
							, center: {
								paneSelector : "#formPropPanel"
							}
						}
					}
					, center : {
						paneSelector : "#formContent"
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
								,	paneSelector : 			"#formEditorToolbar"
							}
							, center: {
								paneSelector : 				"#formEditorPanel"
							}
							, south: {
								size : 200
								,	paneSelector : 			"#formSignalBar"
								,	slideTrigger_open:		"click"
								,	spacing_closed:			16
								,	togglerAlign_closed:	"right"
								,	togglerLength_closed:	36
								,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-n'></div>"
							}
						}
					}
				});
				
				
				Plywet.cw("EasyTabs","diEditorFormStepBar",{
					id : "formStepBar"
				});
				Plywet.cw("Scrollbar","formStepBarScroll",{
					id:'formStepBar',
					tabGroup:'formStepBar-ul',
					step:80,
					scrollType:'vertical'
				});
				// 拖拽
				$(".fly-form-step-plugin").draggable({
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
				$("#formEditorPanel").droppable({
					accept: '.fly-form-step-plugin',
					onDragEnter:function(e,source,data){
						$("#formEditorPanel").addClass("ui-state-highlight");
					},
					onDragLeave: function(e,source,data){
						$("#formEditorPanel").removeClass("ui-state-highlight");
					},
					onDrop: function(e,source,data){
						$("#formEditorPanel").removeClass("ui-state-highlight");
						Plywet.editor.appendEl("form",$(source).data("data"),data);
					}
				});
				
				$form.hide();
				
				Plywet.editors.register["form"] = "Y";
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
		var $close1 = $("#formStructClose"),
			$close2 = $("#formPropClose"),
			$layout = $("#formPropBar").data('layout');
		
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


Plywet.widget.FormEditor = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.structureId = this.cfg.structureId;
	this.propId = this.cfg.propId;
	
	this.off = {x:5,y:5};
	
	this.drawRectFillStyle = {strokeStyle:'#f00',fillStyle:'#f00',isFill:true};
	this.drawRectStyle = {strokeStyle:'#f00'};
	this.drawResizerStyle = {fillStyle:'#a0a0a0'};
	
	this.eidtor = $(Plywet.escapeClientId(this.id));
	this.structure = $(Plywet.escapeClientId(this.structureId));
	this.prop = $(Plywet.escapeClientId(this.propId));
	
	this.formStepBar = $(Plywet.escapeClientId("formStepBar"));
	
	this.selected = [];
	
	this.initEditor();
};

Plywet.widget.FormEditor.prototype.clearSelected = function() {
	this.selected = [];
};

Plywet.widget.FormEditor.prototype.addSelected = function(domId) {
	this.selected.push(domId)
};

Plywet.widget.FormEditor.prototype.initEditor = function() {
	this.editorContent = $("<div id='"+this.id+"Content' class='fly-editor-content fly-form-editor-content'></div>");
	this.editorContent.appendTo(this.eidtor);
	
	if ($.browser.msie && $.browser.version < 9){ // excanvas hack
    	this.editorCanvas = $("<div id='"+this.id+"Canvas' class='fly-editor-canvas fly-form-editor-canvas'></canvas>");
    	this.editorCanvas.appendTo(this.eidtor);
    	this.editorCanvas = window.G_vmlCanvasManager.initElement(this.editorCanvas.get(0));
    } else {
    	this.editorCanvas = $("<canvas id='"+this.id+"Canvas' class='fly-editor-canvas fly-form-editor-canvas'></canvas>");
    	this.editorCanvas.appendTo(this.eidtor);
    	this.editorCanvas = this.editorCanvas.get(0);
    }
	
	this.ctx = this.editorCanvas.getContext('2d');
	
	
	var _self = this;
	this.eidtor.mouseup(function(e){
		var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
		
		if(!_self.domSize){
			_self.createDomSize();
		}
		
		var clickId = _self.clickDom(mouseCoords);
		if(clickId){
			_self.domStructureTree.select(clickId);
		}
		
	});
	
	this.flush(this.cfg.data);
};

Plywet.widget.FormEditor.prototype.clickDom = function(m) {
	var id, dim, dimLeft, dimTop;
	for (var d in this.domSize) {
		dim = this.domSize[d];
		
		if(dim.offsetLeft <= m.x && (dim.offsetLeft+dim.offsetWidth) >= m.x
			&& dim.offsetTop <= m.y && (dim.offsetTop+dim.offsetHeight) >= m.y){
			id = d;
		}
	}
	return id;
};

Plywet.widget.FormEditor.prototype.createDomSize = function() {
	this.domSize = {};
	
	var editorDim = Plywet.getElementDimensions(this.editorContent);
	
	createSubDomSize(this.domStructure, editorDim, this);
	
	function createSubDomSize(node, editorDim, _self) {
		var id = node.attr("__editor_id"),
			dom = _self.editorContent.find("[__editor_id="+id+"]");
		
		if(dom.size() > 0){
			_self.domSize[id] = Plywet.getElementDimensions(dom);
			
			_self.domSize[id].offsetTop = _self.domSize[id].offsetTop - editorDim.offsetTop;
			_self.domSize[id].offsetLeft = _self.domSize[id].offsetLeft - editorDim.offsetLeft;
		}
		
		var subNodes = node.children();
		if(subNodes.size() > 0){
			for(var i=0;i<subNodes.size();i++){
				createSubDomSize($(subNodes[i]), editorDim, _self);
			}
		}
	}
};

Plywet.widget.FormEditor.prototype.flush = function(data) {
	this.dom = data.dom;
	this.script = data.script;
	this.domStructure = $(data.domStructure);
	
	this.domSize = null;
	
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
			id : 		"formStructPanelContent"
			,onSelect : 	Plywet.editors.form.action.struct_on_select
			,els :		[getNodeStructure(this.domStructure, this)]
		};
		this.domStructureTree = new Plywet.widget.EasyTree(config);
	}
	
	
	if(!this.domProp){
		var config = {
			id: 			"formPropPanelContent"
			,animate:		true
			,collapsible:	true
			,idField:		"propName"
			,treeField:		"propName"
			,frozenColumns:[[
                {field:'propName',title:'属性',width:120,
	                formatter:function(value){
	                	return '<span style="color:red">'+value+'</span>';
	                }
                }
			]]
			,columns:[[
				{field:'propValue',title:'值',width:150}
			]]
		};
		this.domProp = new Plywet.widget.EasyTreeGrid(config);
	}
	
	if(!this.signalGrid){
		var config = {
			id:				"formSignalPanelContent"
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
			_self.editorContent.width(w);
			
			if ($.browser.msie && $.browser.version < 9){
				$canvas.width(w+10);
			} else {
				$canvas.attr("width", w+10);
			}
		}
		if (h) {
			_self.height= h || 400;
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
			var plugin = _self.formStepBar.find(Plywet.escapeClientId("leaf:"+type));
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

Plywet.widget.FormEditor.prototype.redraw = function() {
	this.ctx.clearRect(0,0,this.width+10,this.height+10);
	
	this.ctx.lineWidth=1;
	
	for(var i=0;i<this.selected.length;i++){
		redrawSelectComponent(this.selected[i], this);
	}
	
	function redrawSelectComponent(selectedId, _self) {
		var dom = _self.editorContent.find("[__editor_id="+selectedId+"]");
		
		if(dom.size() == 0){
			Plywet.widget.FlowChartUtils.drawResizer(_self.ctx, 
					{x:0,y:0,width:_self.width,height:_self.height},_self.off);
			return;
		}
		
		var dim = Plywet.getElementDimensions(dom),
			editorDim = Plywet.getElementDimensions(_self.editorContent);
		
		var editorDim_top = dim.offsetTop-editorDim.offsetTop,
			editorDim_left = dim.offsetLeft-editorDim.offsetLeft,
			editorDim_width = dim.offsetWidth,
			editorDim_height = dim.offsetHeight;
		
		Plywet.widget.FlowChartUtils.drawRect(_self.ctx, 
				{x:editorDim_left,y:editorDim_top,width:editorDim_width,height:editorDim_height}, 
				_self.drawRectStyle, "line", _self.off);
		Plywet.widget.FlowChartUtils.drawResizer(_self.ctx, 
				{x:editorDim_left,y:editorDim_top,width:editorDim_width,height:editorDim_height},
				_self.off,_self.drawResizerStyle);
	}

};

Plywet.editors.form.action = {
	struct_on_select : function(node){
		var pluginType = $(node.target).data("uiTreeNode").attributes.pluginType;
		var props = [];
		if(pluginType.indexOf("fly:")==0){ // fly组件
			var plugin = $(Plywet.escapeClientId("formStepBar")).find(Plywet.escapeClientId("leaf:"+pluginType));
			if(plugin.size() == 0){// 表示构件
				props = [{propName:"objectId",propType:"input"}];
			}else{
				props = plugin.data("data")["props"];
			}
		}else{// html组件
			props = [{propName:"objectId",propType:"input"}];
		}
		// TODO
		console.log(props);
		formEditorPanel_var.domProp.jq.treegrid("loadData", {
		});
		
		formEditorPanel_var.clearSelected();
		formEditorPanel_var.addSelected(node.id);
		formEditorPanel_var.redraw();
	}
};










