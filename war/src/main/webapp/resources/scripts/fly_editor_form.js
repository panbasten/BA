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
						,	slideTrigger_open:		"mouseover"
						,	livePaneResizing:		true
						,	spacing_closed:			16
						,	togglerAlign_closed:	"top"
						,	togglerLength_closed:	16
						//	effect defaults - overridden on some panes
						,	fxName:					"slide"		// none, slide, drop, scale
						,	fxSpeed_open:			750
						,	fxSpeed_close:			1500
						,	fxSettings_open:		{ easing: "easeInQuint" }
						,	fxSettings_close:		{ easing: "easeOutQuint" }
					}
					, west : {
						size : 200
						,	paneSelector : "#formStepBar"
						,	togglerTip_open:		"关闭工具箱"
						,	togglerTip_closed:		"打开工具箱"
						,	resizable:				false
//						,	initClosed:				true
						,	fxSettings_open:		{ easing: "easeOutBounce" }
					}
					, east : {
						size : 250
						,	paneSelector : "#formPropBar"
						,	togglerTip_open:		"关闭属性框"
						,	togglerTip_closed:		"打开属性框"
						,	resizerTip:		"调整属性框宽度"
						,	childOptions: {
							defaults: {
								size:					"auto"
								,	contentSelector:	".ui-widget-content"
								,	minSize : 				30
								,	animatePaneSizing: 		true
								,	fxSpeed_size:			750
								,	fxSettings_size:		{ easing: "easeInQuint" }
								,	livePaneResizing:		true
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
	
	this.eidtor = $(Plywet.escapeClientId(this.id));
	this.structure = $(Plywet.escapeClientId(this.structureId));
	this.prop = $(Plywet.escapeClientId(this.propId));
	
	this.initEditor();
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
	
	this.flush(this.cfg.data);
};

Plywet.widget.FormEditor.prototype.changeSize = function(w, h) {
	var $canvas = $(this.editorCanvas);
	
	if (w) {
		this.width= w || 600;
		this.editorContent.width(w);
		
		if ($.browser.msie && $.browser.version < 9){
			$canvas.width(w+10);
		} else {
			$canvas.attr("width", w+10);
		}
	}
	if (h) {
		this.height= h || 400;
		this.editorContent.height(h);
		
		if ($.browser.msie && $.browser.version < 9){
			$canvas.height(h+10);
		} else {
			$canvas.attr("height", h+10);
		}
	}
	
	this.redraw();
};

Plywet.widget.FormEditor.prototype.flush = function(data) {
	this.dom = data.dom;
	this.domStructure = data.domStructure;
	
	this.editorContent.html(this.dom);
	
	this.changeSize(this.domStructure.attrs.width, this.domStructure.attrs.height);
};

Plywet.widget.FormEditor.prototype.redraw = function() {
	this.ctx.clearRect(0,0,this.width+10,this.height+10);
	
	this.ctx.lineWidth=1;

	Plywet.widget.FlowChartUtils.drawResizer(this.ctx, {x:0,y:0,width:600,height:400},this.off);
	Plywet.widget.FlowChartUtils.drawRect(this.ctx, {x:30,y:30,width:70,height:30}, {strokeStyle:'#f00',fillStyle:'#f00',isFill:true}, "line", this.off);
	Plywet.widget.FlowChartUtils.drawResizer(this.ctx, {x:30,y:30,width:70,height:30},this.off,{fillStyle:'#a0a0a0'});

};










