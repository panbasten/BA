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
		var pivotBody = $("#pivotBody");
		pivotBody.empty();
		pivotBody.spreadsheet();
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
				var $pivot = $("#pivot");
				$pivot.width(editorContentWidth).height(editorContentHeight);
				
				// 构建布局
				$pivotLayout = $pivot.layout({
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
						,	fxSettings_open:		{ easing: "easeInQuint" }
						,	fxSettings_close:		{ easing: "easeOutQuint" }
						,	onresize_end:			function(){
							
						}
					}
					, west : {
						size : 200
						,	paneSelector : "#pivotBar"
						,	togglerTip_open:		"关闭工具箱"
						,	togglerTip_closed:		"打开工具箱"
						,	sliderTip:				"弹出工具箱"
						,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-e'></div>"
						,	resizable:				false
//						,	initClosed:				true
					}
					, center : {
						paneSelector : "#pivotContent"
						, 	minSize : 200
					}
				});
				
				// toolbar
				$("#pivotDimensionContent").height(150);
				$("#pivotMeasureContent").height(150);
				
				$pivot.hide();
				Flywet.editors.register[Flywet.editors.pivot.type] = "Y";
			}
		});
	}
};