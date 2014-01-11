// 多维报表
Flywet.editors.pivot = {
	type : "pivot",
	editorType : "tab",
	
	saveStatus : function ($tabo) {
	},
	
	reloadStatus : function ($taba) {
		var $tab = $taba.parent();
		
		
//		if(!$tab.data('easytabs').cached){
//			var $panel = $tab.data('easytabs').panel,
//				exdata = $taba.data("exdata");
//			
//			Flywet.ajax.AjaxUtils.appendElement($panel, exdata.dom, exdata.script);
//		}
		console.log($taba.data("exdata"));
//		
		var pivotBody = $("#pivotBody");
		pivotBody.empty();
		pivotBody.spreadsheet($taba.data("exdata"));
	},
	
	openEditor : function(category,data,displayName,tabName){
		Flywet.ab({
			type : "get", 
			modal : true,
			modalMessage : "正在加载【"+displayName+"】...",
			url : "rest/"+data.attrs.src,
			onsuccess : function(data, status, xhr){
				baEditorPageTabs.addTab({
					exdata: data,
					tabId: category,
					tabType: category,
					tabText: displayName,
					dataTarget: tabName,
					closable: true,
					closePanel: false,
			        checkModify: true
				});
			}
		});
	},
	
	register : function(){
		if(Flywet.editors.register[Flywet.editors.pivot.type]){
			return;
		}
		
		// 加载转换页
		Flywet.ab({
			type : "get",
			url : "rest/pivot/editor",
			modalMessage: "正在注册多维报表设计器页面...",
			oncomplete : function(xhr, status){
				// 初始化尺寸
				var editorContainer = diEditorLayout.getPane("center"),
					editorContentWidth = editorContainer.outerWidth(),
					editorContentHeight = editorContainer.outerHeight()-40;
				
				Flywet.cw("PivotEditor","pivotEditorPanel_var",{
					id: "pivot",
					width: editorContentWidth,
					height: editorContentHeight
				});
				
				
				
				// toolbar
//				$("#pivotDimensionContent").height(150);
//				$("#pivotMeasureContent").height(150);
				
				pivotEditorPanel_var.hide();
				Flywet.editors.register[Flywet.editors.pivot.type] = "Y";
			}
		});
	}
};

Flywet.widget.PivotEditor = function(cfg) {
	this.cfg = cfg;
	// 设计器ID
	this.id = this.cfg.id;
	
	this.initEditor();
};

Flywet.widget.PivotEditor.prototype.initEditor = function() {
	// 初始化整体布局
	this.initLayout();
	
	// 初始化取数区域
	this.initDataSourceArea();
	
	// 初始化左属性区域
	this.initLeftAttrsArea();
	
	// 初始化上属性区域
	this.initTopAttrsArea();
};

/**
 * 构建布局
 */
Flywet.widget.PivotEditor.prototype.initLayout = function(){
	this.pivot = $(Flywet.escapeClientId(this.id));
	this.pivot.width(this.cfg.width).height(this.cfg.height);
	
	// 构建布局
	this.pivotLayout = this.pivot.layout({
		defaults: {
			size:					"auto"
			,	minSize:				50
			,	hideTogglerOnSlide:		true		// hide the toggler when pane is 'slid open'
			,	slideTrigger_open:		"click"
//			,	livePaneResizing:		true
			,	spacing_closed:			16
			,	togglerAlign_closed:	"top"
			,	togglerLength_closed:	36
			//	effect defaults - overridden on some panes
			,	fxName:					"slide"		// none, slide, drop, scale
			,	fxSpeed_open:			750
			,	fxSpeed_close:			1500
			,	fxSettings_open:		{ easing: "easeInQuint" }
			,	fxSettings_close:		{ easing: "easeOutQuint" }
			,	onresize_end:			function(){
				
			}
		}
		, west : {
			size : 150
			,	paneSelector : "#pivotBar"
			,	togglerTip_open:		"关闭数据选择器"
			,	togglerTip_closed:		"打开数据选择器"
			,	sliderTip:				"弹出数据选择器"
			,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-e'></div>"
			,	resizable:				false
//			,	initClosed:				true
		}
		, center : {
			paneSelector : "#pivotContent"
			, 	minSize : 200
			,	childOptions: {
				defaults: {
					size:					"auto"
					,	contentSelector:	".ui-widget-content"
					,	minSize : 				30
					,	animatePaneSizing: 		true
					,	fxSpeed_size:			750
					,	fxSettings_size:		{ easing: "easeInQuint" }
//					,	livePaneResizing:		true
					,	onresize_end:			function(){
							transEditorPanel_var.autoChangeSize();
						}
				}
				, west: {
					size : 150
					,	closable : true
					,  	resizerTip:		"调整参数选择器"
					,	paneSelector : "#pivotAttrsLeftBar"
				}
				, center: {
					paneSelector : "#pivotContent2"
					, 	minSize : 200
					,	childOptions: {
						defaults: {
							size:					"auto"
							,	contentSelector:	".ui-widget-content"
							,	minSize : 				30
							,	animatePaneSizing: 		true
							,	fxSpeed_size:			750
							,	fxSettings_size:		{ easing: "easeInQuint" }
//								,	livePaneResizing:		true
							,	onresize_end:			function(){
									transEditorPanel_var.autoChangeSize();
								}
						}
						, north: {
							size : 70
							,	closable : true
							,  	resizerTip:		"调整参数选择器"
							,	paneSelector : "#pivotAttrsTopBar"
						}
						, center: {
							paneSelector : "#pivotBodyWrapper"
						}
					}
				}
			}
		}
	});
};

/**
 * 初始化属性Panel
 */
Flywet.widget.PivotEditor.prototype.initPanel = function(cfg){
	var panelWapper = $(cfg.target),
		panelWapperDim = {
			width : panelWapper.width(),
			height : panelWapper.height()
		};
	var panels = panelWapper.children("."+cfg.panelSelector);
	
	var perPanelContentHeight = Math.floor((panelWapperDim.height + 6) / panels.length - 32);
	
	var len = panels.length;
	if(cfg.fixContentHeight){
		len = panels.length-1;
	}
	
	for(var i=0;i<len;i++){
		var $R = $("<div></div>").insertAfter($(panels[i]));
		$R.attr("title", cfg.tip)
		  .css("cursor", "n-resize")
		  .addClass("ui-layout-resizer ui-layout-resizer-north ui-layout-resizer-open ui-layout-resizer-north-open");
		
		$R.height(6).width(panelWapperDim.width);
		
		if(cfg.fixContentHeight){
			$(panels[i]).find("."+cfg.contentSelector).height(perPanelContentHeight);
		}
	}
	
	if(cfg.fixContentHeight){
		$(panels[panels.length-1]).find("."+cfg.contentSelector).height(perPanelContentHeight);
	}
};

/**
 * 初始化数据取数区域
 */
Flywet.widget.PivotEditor.prototype.initDataSourceArea = function(){
	this.pivotBar = $("#pivotBar");
	this.pivotDimensionPanel = $("#pivotDimensionPanel");
	this.pivotMeasurePanel = $("#pivotMeasurePanel");
	
	this.initPanel({
		target : this.pivotBar,
		panelSelector : "ui-widget-panel",
		contentSelector : "ui-widget-content",
		tip : "调整高度",
		fixContentHeight : true
	});
};

/**
 * 初始化左属性区域
 */
Flywet.widget.PivotEditor.prototype.initLeftAttrsArea = function(){
	this.pivotAttrsLeftBar = $("#pivotAttrsLeftBar");
	this.pivotPagePanel = $("#pivotPagePanel");
	this.pivotPagePanel.children(".ui-widget-content").height(43);
	this.pivotFilterPanel = $("#pivotFilterPanel");
	this.pivotFilterPanel.children(".ui-widget-content").height(120);
	this.pivotMarkPanel = $("#pivotMarkPanel");
	this.pivotMarkPanel.children(".ui-widget-content").height(120);
	
	this.initPanel({
		target : this.pivotAttrsLeftBar,
		panelSelector : "ui-widget-panel",
		contentSelector : "ui-widget-content",
		tip : "调整高度"
	});
};

/**
 * 初始化上属性区域
 */
Flywet.widget.PivotEditor.prototype.initTopAttrsArea = function(){
	
};

Flywet.widget.PivotEditor.prototype.hide = function(){
	this.pivot.hide();
};