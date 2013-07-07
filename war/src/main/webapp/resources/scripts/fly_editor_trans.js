// 导航按钮
Plywet.transjob = {
	ids : {
		bpVarName : "editorContent-navi-trans-bp_var"
	},
	createDir : function(){
		var _self = this;
		var currentCase = window[Plywet.transjob.ids.bpVarName].getCurrentData();
		var targetId = "tj_create_dialog_folder";
		var dirId = currentCase.dirId;
		
		Plywet.cw("Dialog",targetId+"_var",{
			id : targetId,
			header : "新建目录",
			width : 500,
			height : 70,
			autoOpen : true,
			showHeader : true,
			modal : true,
			url : "rest/transjob/dir/create/"+dirId,
			params : {
				targetId : targetId+":content",
				rootId : currentCase.rootId,
				path : currentCase.path
			},
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events: {
					click:function(){
						Plywet.ab({
							type : "post",
							source:"tj_folder_create_form",
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[targetId + "_var"].hide();
									_self.flushDir(dirId);
								}
							}
						});
					}
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "取消",
				title : "取消",
				events : {
					"click" : "hide"
				}
			}],
			closable : true,
			maximizable : false,
			resizable : false
		});
	},
	create : function(){
	},
	edit : function(){
		if (!Plywet.editors.item.checkSelected(Plywet.transjob.ids.bpVarName)) {
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Plywet.editors.item.getOneSelected(Plywet.transjob.ids.bpVarName);
	},
	remove : function(){
		if (!Plywet.editors.item.checkSelected(Plywet.transjob.ids.bpVarName)) {
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var _self = this;
		
		var currentCase = window[Plywet.transjob.ids.bpVarName].getCurrentData();
		var dirId = currentCase.dirId;
		
		var selItem = Plywet.editors.item.getOneSelected(Plywet.transjob.ids.bpVarName);
		var url,text;
		if(selItem.type=='node'){
			url = "rest/transjob/dir/remove/"+selItem.id;
			text = "确认删除目录【"+selItem.displayName+"】？";
		}else{
			url = "rest/"+selItem.category+"/"+selItem.id+"/remove";
			text = "确认删除对象【"+selItem.displayName+"】？";
		}
		
		Plywet.cw("ConfirmDialog",null,{type:"confirm",text:text,
			confirmFunc:function(e,v){
				if(v){
					Plywet.ab({
						type : "get",
						url : url,
						onsuccess:function(data, status, xhr) {
							if (data.state == 0) {
								_self.flushDir(dirId);
							}
						}
					});
				}
			}
		});
	},
	uploadFile : function(){
	},
	downloadFile : function(){
		if (!Plywet.editors.item.checkSelected(Plywet.transjob.ids.bpVarName)) {
			Plywet.dialog.warning("请先选中一个对象。");
			return;
		}
		var selItem = Plywet.editors.item.getOneSelected(Plywet.transjob.ids.bpVarName);
	},
	
	flushDir : function(id){
		Plywet.ab({
			type : "get",
			url : "rest/transjob/dir/"+id
		});
	}
};

// 转换
Plywet.editors.trans = {
	type : "trans",
	resize : function(){
		// TODO 
		
	},
	saveStatus : function ($tabo) {
		// 保存原来的结果
		if ($tabo && $tabo.data("exdata")){
			var canvasObj = transEditorPanel_var.flowChart;
			if(canvasObj && canvasObj.childCanvas){
				var flow = canvasObj.getChildCanvasByIndex(0);
				$tabo.data("exdata").data = Plywet.parseJSON(flow.getElsValue());
			}
		}
	},
	reloadStatus : function ($taba) {
		if (typeof($taba.data("exdata"))=='undefined')return;
		var canvasData = {
			onClearAll: "Plywet.editors.trans.action.stepSelect(canvasObj,flowObj)",
			onModify: "Plywet.editors.trans.action.modify(canvasObj,flowObj)",
			canvasEls : {},
			defaultAttributes: {
				onInitStep: {
					onContextMenu: "Plywet.editors.trans.action.stepContent(canvasObj,flowObj,this)",
					onDblClick: "Plywet.editors.trans.action.stepDblclick(canvasObj,flowObj,this)",
					sWidth: 32,
					sHeight: 32,
					bTextStyle: "#ffffff",
					acceptAll: true,
					onEndHop: "Plywet.editors.trans.action.checkEndHop(setting,this)",
					onRope: "Plywet.editors.trans.action.stepSelect(canvasObj,flowObj,this)",
					onClick: "Plywet.editors.trans.action.stepSelect(canvasObj,flowObj,this)"
				},
				onInitHop: {
					onDblClick: "Plywet.editors.trans.action.hopDblclick(canvasObj,flowObj,this)",
					onContextMenu: "Plywet.editors.trans.action.hopContext(canvasObj,flowObj,this)",
					style: "#2e83ff",
					textStyle: "#ffffff",
					arrowEndType: "default",
					onRope: "Plywet.editors.trans.action.stepSelect(canvasObj,flowObj,this)",
					onClick: "Plywet.editors.trans.action.stepSelect(canvasObj,flowObj,this)"
				}
			}
		};
		var tabData = $taba.data("exdata").data;
		if(tabData.canvasEls) $.extend(canvasData.canvasEls,tabData.canvasEls);
		if(tabData.defaultAttributes){
			if(tabData.defaultAttributes.onInitStep) $.extend(canvasData.defaultAttributes.onInitStep,tabData.defaultAttributes.onInitStep);
			if(tabData.defaultAttributes.onInitHop) $.extend(canvasData.defaultAttributes.onInitHop,tabData.defaultAttributes.onInitHop);
		}
		if(tabData.onClearAll) canvasData.onClearAll=tabData.onClearAll;
		if(tabData.onModify) canvasData.onModify=tabData.onModify;
		if(tabData.extendData) canvasData.extendData=tabData.extendData;
		
		if (typeof transEditorPanel_var == 'undefined') {
			var $panelSize = Plywet.getElementDimensions($(Plywet.escapeClientId("transEditorPanel")));
			Plywet.cw("FlowChart","transEditorPanel_var",{
				id: "transEditorPanel",
				oid: "transThumbContent",
				canvasConfig: {
					width: $panelSize.css.width,
					height: $panelSize.css.height
				},
				outerControl:true,
				data: canvasData
			});
		}else{
			transEditorPanel_var.flush(canvasData);
		}
	},
	register : function(){
		if(Plywet.editors.register[Plywet.editors.trans.type]){
			return;
		}
		
		// 加载转换页
		Plywet.ab({
			type : "get",
			url : "rest/transjob/trans/editor",
			beforeSend : function(){
				Plywet.desktop.changeMarkText("正在注册转换设计器页面...");
			},
			oncomplete : function(xhr, status){
				// 初始化尺寸
				var editorContainer = diEditorLayout.getPane("center"),
				editorContentWidth = editorContainer.outerWidth(),
				editorContentHeight = editorContainer.outerHeight()-40;
				var $trans = $("#trans");
				$trans.width(editorContentWidth).height(editorContentHeight);
				
				// 结构
				$transLayout = $trans.layout({
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
							$("#transEditorPanel").height($transLayout.getPaneSize("center", false, "horz") - 30)
								.width($transLayout.getPaneSize("center", false, "vert"));
							$("#transThumbContent").width($transLayout.getPaneSize("east")-2);
							// 改变canvas属性
							transEditorPanel_var.autoChangeSize();
						}
					}
					, west : {
						size : 200
						,	paneSelector : "#transStepBar"
						,	togglerTip_open:		"关闭工具箱"
						,	togglerTip_closed:		"打开工具箱"
						,	sliderTip:				"弹出工具箱"
						,	togglerContent_closed: 	"<div class='ui-layout-toggler-closed-icon ui-icon ui-icon-circle-triangle-e'></div>"
						,	resizable:				false
	//					,	initClosed:				true
					}
					, east : {
						size : 250
						,	paneSelector : "#transPropBar"
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
								,	onresize_end:			function(){
										transEditorPanel_var.autoChangeSize();
									}
							}
							, north: {
								size : 150
								,	closable : false
								,  	resizerTip:		"调整属性框高度"
								,	paneSelector : "#transThumbPanel"
							}
							, center: {
								paneSelector : "#transDSPanel"
							}
						}
					}
					, center : {
						paneSelector : "#transContent"
						, 	minSize : 200
					}
				});
				
				
				$("#transEditorPanel").height($transLayout.getPaneSize("center", false, "horz") - 30)
					.width($transLayout.getPaneSize("center", false, "vert"));
				$("#transThumbContent").width($transLayout.getPaneSize("east")-2);
				
				Plywet.cw("EasyTabs","diEditorTransStepBar",{
					id : "transStepBar"
				});
				Plywet.cw("Scrollbar","transStepBarScroll",{
					id:'transStepBar',
					tabGroup:'transStepBar-ul',
					step:80,
					scrollType:'vertical'
				});
				
				// 拖拽
				$(".fly-trans-step-plugin").draggable({
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
				$("#transEditorPanel").droppable({
					accept: '.fly-trans-step-plugin',
					onDragEnter:function(e,source,data){
						$("#transEditorPanel").addClass("ui-state-highlight");
					},
					onDragLeave: function(e,source,data){
						$("#transEditorPanel").removeClass("ui-state-highlight");
					},
					onDrop: function(e,source,data){
						$("#transEditorPanel").removeClass("ui-state-highlight");
						Plywet.editors.trans.action.appendEl($(source).data("data"),data);
					}
				});
				
				// 添加一个静态的树
				var config = {
					'id' : 'transDSContent',
//					"checkbox":true,
					"dnd":true,
					'els' : [ {
						'id' : 'id0',
						'displayName' : '数据库',
						'type' : 'node',
						'icon' : 'resources/libs/tree/images/diy/1_close.png',
						'els' : [ {
							id : 'id1-1',
							displayName : '连接名称1',
							type : 'node',
							'icon' : '',
							state:"open",
							'els' : [ {
								id : 'id1-1-1',
								displayName : '表结构',
								type : 'node',
								state:"closed",
								'icon' : '',
								'els' : [ {
									id : 'id1-1-1',
									displayName : '表1',
									type : 'leaf',
									draggable : true
								}, {
									id : 'id1-1-11',
									displayName : '表2',
									type : 'leaf',
									draggable : true
								}, {
									id : 'id1-1-12',
									displayName : '表3',
									type : 'leaf',
									draggable : true
								} ]
							}, {
								id : 'id1-1-2',
								displayName : '视图',
								type : 'node',
								'els' : [ {
									id : 'id1-1-21',
									displayName : '视图1',
									type : 'leaf'
								}, {
									id : 'id1-1-22',
									displayName : '视图2',
									type : 'leaf'
								}, {
									id : 'id1-1-23',
									displayName : '视图3',
									type : 'leaf'
								} ]
							}, {
								id : 'id1-1-3',
								displayName : '存储过程',
								type : 'node',
								'els' : [ {
									id : 'id1-1-31',
									displayName : '过程1',
									type : 'leaf'
								}, {
									id : 'id1-1-32',
									displayName : '过程2',
									type : 'leaf'
								}, {
									id : 'id1-1-33',
									displayName : '过程3',
									type : 'leaf'
								} ]
							} ]
						}, {
							id : 'id1-21',
							displayName : '连接名称2',
							type : 'node'
						}, {
							id : 'id1-22',
							displayName : '连接名称3',
							type : 'node'
						} ]
					} ]
				};
				var tree = new Plywet.widget.EasyTree(config);
				
				$trans.hide();
				
				Plywet.editors.register[Plywet.editors.trans.type] = "Y";
			}
		});
	},
	toggleFlags : {
		thumbPane : true,
		dsPane : true,
		thumbHeight : 0,
		dsHeight : 0
	},
	toggleContent : function(target){
		var $close1 = $("#transThumbClose"),
			$close2 = $("#transDSClose"),
			$layout = $("#transPropBar").data('layout');
		
		if(this.toggleFlags.thumbPane && this.toggleFlags.dsPane){
			this.toggleFlags.thumbHeight = $layout.getPaneSize("north");
			this.toggleFlags.dsHeight = $layout.getPaneSize("center", false, "horz");
		}
		
		//最小化1，最大化2
		if(target == "thumbPane" && this.toggleFlags.thumbPane) {
			$layout.sizePane("north", 30);
			
			$close1.addClass("ui-icon-circle-plus");
			$close1.removeClass("ui-icon-circle-minus");
			
			$close2.removeClass("ui-icon-circle-plus");
			$close2.addClass("ui-icon-circle-minus");
			
			this.toggleFlags.thumbPane = false;
			this.toggleFlags.dsPane = true;
		} 
		// 最小化2，最大化1
		else if(target == "dsPane" && this.toggleFlags.dsPane) {
			$layout.sizePane("north", this.toggleFlags.thumbHeight + this.toggleFlags.dsHeight - 30);
		
			$close2.addClass("ui-icon-circle-plus");
			$close2.removeClass("ui-icon-circle-minus");
			
			$close1.removeClass("ui-icon-circle-plus");
			$close1.addClass("ui-icon-circle-minus");
			
			this.toggleFlags.thumbPane = true;
			this.toggleFlags.dsPane = false;
		}
		else {
			$layout.sizePane("north", this.toggleFlags.thumbHeight);
			
			$close1.removeClass("ui-icon-circle-plus");
			$close1.addClass("ui-icon-circle-minus");
			
			$close2.removeClass("ui-icon-circle-plus");
			$close2.addClass("ui-icon-circle-minus");
			
			this.toggleFlags.thumbPane = true;
			this.toggleFlags.dsPane = true;
		}
		
		if(this.toggleFlags.thumbPane && this.toggleFlags.dsPane){
			$layout.enableResizable("north");
		} else {
			$layout.disableResizable("north");
		}
	}
};

/**
 * 转换的按钮操作
 */
Plywet.editors.trans.action = {
	tb : Plywet.editors.toolbarButton,
	editorPrefix : "trans",
	ids : {
		operations : ["run","runstep","runreturn","pause","stop","validate","analize","runstep"],
		editors : ["edit","magnify","lessen","partMagnify","zoom_100","zoom_fit","screenMove"],
		grids : ["grid_show","grid_close"],
		systems : ["cut","copy","paste","delete"],
		saves : ["save","saveas","save_xml","save_image"]
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
	getFlow : function(){
		var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj && canvasObj.childCanvas){
    		return canvasObj.getChildCanvasByIndex(0);
    	}
	},
	editorZoom100 : function(){
		this.changeOuterControlType("");
    	var flow = this.getFlow();
    	if(flow){
    		flow.showZoom100();
    	}
	},
	editorFix : function(){
		this.changeOuterControlType("");
		var flow = this.getFlow();
    	if(flow){
    		flow.fixSize();
    	}
	},
	changeOuterControlType : function (v,id){
		if(v==undefined || v==null || v==""){
			v = "";
			this.tb.inactive(this.getId(this.ids.editors));
		}else{
			var btnId = this.getId((id)?id:v);
			if(this.tb.isActive(btnId)){
				v = "";
				this.tb.inactive(btnId);
			}else{
				this.tb.inactive(this.getId(this.ids.editors));
				this.tb.active(btnId);
			}
		}
		
		var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj){
    		var ctrlType = v;
    		canvasObj.setOuterControl(true,ctrlType);
    		canvasObj.redraw();
    	}
    },

    deleteSelectedElFromOutset : function (){
    	var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
   			flow.deleteSelectedEl();
    		canvasObj.redraw();
    		
    		this.stepSelect(this.editorPrefix,canvasObj,flow);
    	}
    	
    	
    },

    save : function (){
    	var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj && canvasObj.childCanvas){
    		var transId = canvasObj.config.data.extendData.transId;
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		Plywet.ab({
    			type : "post",
    			url : "rest/trans/"+transId+"/save",
    			modal : true,
    			modalMessage : "正在保存...",
    			params : {
    				val : flow.getElsValue()
    			},
    			onsuccess : function(){
    				diEditorPageTabs.setTabModify(null, false);
    			}
    		});
    	}
    },
    
    // @Override 必要方法：用于在Tab发生修改时，点击保存按钮调用的方法。
    saveTab : function (clicked) {
    	var isActive = diEditorPageTabs.isActive(clicked.data("exdata").id+"-tab");
    	if(isActive){
    		this.save("trans");
    	}else{
    		var transId = clicked.data("exdata").data.extendData.transId;
    		Plywet.ab({
    			type : "post",
    			url : "rest/trans/"+transId+"/save",
    			modal : true,
    			modalMessage : "正在保存...",
    			params : {
    				clear : true,
    				val : Plywet.toJSONString(clicked.data("exdata").data)
    			}
    		});
    	}
    },
    
    // @Override 必要方法：用于在Tab发生修改时，点击放弃按钮调用的方法。
    discardTab : function (clicked) {
    	var transId = clicked.data("exdata").data.extendData.transId;
    	Plywet.ab({
			type : "get",
			url : "rest/trans/"+transId+"/discard"
		});
    },
    
    appendEl : function (data,pos){
    	var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		var realOffset = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,10);
    		var realPos = Plywet.widget.FlowChartUtils.arcScalePoint(canvasObj.config,{
    			x:(pos.x-realOffset),
    			y:(pos.y-realOffset)
    		});
    		// 判断是否重名
    		var steps = flow.config.canvasEls.steps;
    		var displayName = data.displayName, displayName_old = displayName, idx = 2;
    		var rename = true;
    		while(rename){
    			rename = false;
	    		for(var i=0;i<steps.length;i++){
	    			if(displayName == steps[i].extendData.stepName){
	    				displayName = displayName_old + (idx++);
	    				rename = true;
	    				break;
	    			}
	    		}
    		}
    		// 添加一个节点
    		flow.appendEl({
    			'type':'sys:step:picture',
    			'imgSrc':data.icon,
    			'provider':data.id,
    			'dx':realPos.x,'dy':realPos.y,
    			'extendData':{'stepName':displayName,'dialogPosition':data.dialogPosition},
    			'bText':[displayName]},"step");
    		canvasObj.redraw();
    	}
    },
    
    
    
    /**
     * 显示网格
     */
    showGrid : function(){
    	var btnId = this.getId("grid_show");
    	var showGrid;
		if(this.tb.isActive(btnId)){
			showGrid=false;
			this.tb.inactive(btnId);
			this.tb.disabled(this.getId("grid_close"));
		}else{
			showGrid=true;
			this.tb.active(btnId);
			this.tb.enabled(this.getId("grid_close"));
		}
		var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		flow.showGrid(showGrid);
    	}
    },
    
    /**
     * 贴近网格
     */
    closeGrid : function(){
    	var btnId = this.getId("grid_close");
    	var close;
		if(this.tb.isActive(btnId)){
			close=false;
			this.tb.inactive(btnId);
		}else{
			close=true;
			this.tb.active(btnId);
		}
		var canvasObj=transEditorPanel_var.flowChart;
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		flow.closeGrid(close);
    	}
    },

    hopContext : function (flowChart,flowObject,model){
    	console.log(flowChart);
        console.log(flowObject);
        console.log(model);
    },

    hopDblclick : function (flowChart,flowObject,model){
    	console.log(flowChart);
        console.log(flowObject);
        console.log(model);
    },

    stepContent : function (flowChart,flowObject,model){
    	console.log(flowChart.oraData);
        console.log(flowObject);
        console.log(model);
    },
    
    /**
     * 判断是否可以进行连线
     * 
     * @param setting：fromElId, toElId
     * @param flowObject
     */
    checkEndHop : function (setting,flowObject){
    	var hops = flowObject.config.canvasEls.hops;
    	for(var i=0;i<hops.length;i++){
    		if(hops[i].fromElId == setting.fromElId && hops[i].toElId == setting.toElId){
    			return false;
    		}
    	}
    	return true;
    },
    
    stepSelect : function(flowChart,flowObject,model){
    	var editorIds = this.getId(["cut","copy"]);
    	var deleteId = this.getId("delete");
    	if(flowObject.hasSelectedEl("step")){
    		this.tb.enabled(editorIds);
    	}else{
    		this.tb.disabled(editorIds);
    	}
    	if(flowObject.hasSelectedEl()){
    		this.tb.enabled(deleteId);
    	}else{
    		this.tb.disabled(deleteId);
    	}
    },
    
    modify : function(flowChart,flowObject){
    	// 表示修改
		diEditorPageTabs.setTabModify(null, true);
    },

    stepDblclick : function (flowChart,flowObject,model){
    	var autoMaximized = false, maximizable = true, w = 600, h = 400;
    	var	dialogPos;
    	if(model.extendData){
    		dialogPos = model.extendData.dialogPosition;
    	}
    	if(dialogPos){
    		if(dialogPos == "full"){
    			autoMaximized = true;
    			maximizable = false;
    		} else if(dialogPos.indexof(",")>0){
    			var pos = dialogPos.split(",");
    			w = pos[0];
    			h = pos[1];
    		}
    	}
    	
    	var dialogId = "dialog-trans-step";
    	Plywet.cw("Dialog",dialogId+"_var",{
			id : dialogId,
			header : model.bText[0],
			width : w,
			height : h,
			autoOpen : true,
			showHeader : true,
			url : "rest/trans/step/"+flowObject.config.extendData.transId+"/"+model.extendData.stepName+"/"+model.provider,
			footerButtons : [{
				componentType : "fly:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events : {
					"click" : function(){
						Plywet.ab({
							formId : "form:"+dialogId,
							formAction : "rest/trans/step/"+flowObject.config.extendData.transId+"/"+model.extendData.stepName+"/save",
							params : {
								dx : model.dx,
								dy : model.dy
							},
							onsuccess:function(data, status, xhr) {
								if (data.state == 0) {
									window[dialogId + "_var"].hide();
									// TODO 刷新页面
								}
							}
						});
					}
				}
			},{
				componentType : "fly:PushButton",
				type : "button",
				label : "取消",
				title : "取消",
				events : {
					"click" : "hide"
				}
			}],
			closable : true,
			maximizable : maximizable,
			autoMaximized : autoMaximized
		});
    },
    
    check : function(){
    }
};