YonYou.editors = {
	register : {},
	reinit : function(){
		if(YonYou.editors.register[YonYou.editors.trans.type]){
			YonYou.editors.trans.reinit();
		}
	},
	changeEditor : function ($taba,$tabo) {
		if($tabo && $tabo.data("tabId")){
			eval("YonYou.editors."+$tabo.data("tabId")+".saveStatus($tabo);");
		}
		if($taba && $taba.data("tabId")){
			eval("YonYou.editors."+$taba.data("tabId")+".reloadStatus($taba);");
		}
	}
};

// 转换
YonYou.editors.trans = {
	type : "trans",
	reinit : function(){
		transStepBarScroll.reinit();
		
		if(window["transEditorPanel_var"]){
			transEditorPanel_var.changeSize(YonYou.desktop.contentWidthNoPadding,
				YonYou.desktop.contentHeightEditor);
		}
	},
	saveStatus : function ($tabo) {
		// 保存原来的结果
		if ($tabo && $tabo.data("exdata")){
			var canvasObj = transEditorPanel_var.flowChart;
			if(canvasObj && canvasObj.childCanvas){
				var flow = canvasObj.getChildCanvasByIndex(0);
				$tabo.data("exdata").data = YonYou.parseJSON(flow.getElsValue());
			}
		}
	},
	reloadStatus : function ($taba) {
		if (typeof($taba.data("exdata"))=='undefined')return;
		var canvasData = {
			onClearAll: "YonYou.editor.stepSelect('trans',canvasObj,flowObj)",
			onModify: "YonYou.editor.modify('trans',canvasObj,flowObj)",
			canvasEls : {},
			defaultAttributes: {
				onInitStep: {
					onContextMenu: "YonYou.editor.stepContent(canvasObj,flowObj,this)",
					onDblClick: "YonYou.editor.stepDblclick(canvasObj,flowObj,this)",
					sWidth: 32,
					sHeight: 32,
					bTextStyle: "#ffffff",
					acceptAll: true,
					onEndHop: "YonYou.editor.checkEndHop('trans',setting,this)",
					onRope: "YonYou.editor.stepSelect('trans',canvasObj,flowObj,this)",
					onClick: "YonYou.editor.stepSelect('trans',canvasObj,flowObj,this)"
				},
				onInitHop: {
					onDblClick: "YonYou.editor.hopDblclick(canvasObj,flowObj,this)",
					onContextMenu: "YonYou.editor.hopContext(canvasObj,flowObj,this)",
					style: "#2e83ff",
					textStyle: "#ffffff",
					arrowEndType: "default",
					onRope: "YonYou.editor.stepSelect('trans',canvasObj,flowObj,this)",
					onClick: "YonYou.editor.stepSelect('trans',canvasObj,flowObj,this)"
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
			var $panelSize = YonYou.getDomSizeById("transEditorPanel");
			YonYou.cw("FlowChart","transEditorPanel_var",{
				id: "transEditorPanel",
				oid: "transOverviewPanel",
				canvasConfig: {
					width: $panelSize.width,
					height: $panelSize.height
				},
				outerControl:true,
				data: canvasData
			});
		}else{
			transEditorPanel_var.flush(canvasData);
		}
	},
	register : function(){
		if(YonYou.editors.register[YonYou.editors.trans.type]){
			return;
		}
		
		// 加载转换页
		YonYou.ab({
			type : "get",
			url : "rest/transjob/trans/editor",
			beforeSend : function(){
				YonYou.desktop.changeMarkText("正在注册转换设计器页面...");
			},
			oncomplete : function(xhr, status){
				YonYou.cw("EasyTabs","diEditorTransStepBar",{
					id : "transStepBar"
				});
				YonYou.cw("Scrollbar","transStepBarScroll",{
					id:'transStepBar',
					tabGroup:'transStepBar-ul',
					step:80,
					scrollType:'vertical'
				});
				// 拖拽
				$(".hb-trans-step-plugin").draggable({
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
					accept: '.hb-trans-step-plugin',
					onDragEnter:function(e,source,data){
						$("#transEditorPanel").addClass("ui-state-highlight");
					},
					onDragLeave: function(e,source,data){
						$("#transEditorPanel").removeClass("ui-state-highlight");
					},
					onDrop: function(e,source,data){
						$("#transEditorPanel").removeClass("ui-state-highlight");
						YonYou.editor.appendEl("trans",$(source).data("data"),data);
					}
				});
				
				// 添加一个静态的树
				var config = {
					'id' : 'datasource',
					"checkbox":true,
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
				var tree = new YonYou.widget.EasyTree(config);
				
				YonYou.editors.trans.reinit();
				YonYou.editors.register[YonYou.editors.trans.type] = "Y";
			}
		});
	}
};

// 表单
YonYou.editors.form = {
	saveStatus : function ($tabo) {
		console.log("save form");
	},
	reloadStatus : function ($taba) {
		console.log("reload form");
	},
	reinit : function(){
		formStepBarScroll.reinit();
		
//		if(window["formEditorPanel_var"]){
//			formEditorPanel_var.changeSize(YonYou.desktop.contentWidthNoPadding,
//				YonYou.desktop.contentHeightEditor);
//		}
	},
	register : function(){
		if(YonYou.editors.register["form"]){
			return;
		}
		
		YonYou.ab({
			type : "get",
			url: "rest/report/form/editor",
			beforeSend : function(){
				YonYou.desktop.changeMarkText("正在注册表单设计器页面...");
			},
			oncomplete : function(xhr, status){
				YonYou.cw("EasyTabs","diEditorFormStepBar",{
					id : "formStepBar"
				});
				YonYou.cw("Scrollbar","formStepBarScroll",{
					id:'formStepBar',
					tabGroup:'formStepBar-ul',
					step:80,
					scrollType:'vertical'
				});
				// 拖拽
				$(".hb-form-step-plugin").draggable({
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
					accept: '.hb-form-step-plugin',
					onDragEnter:function(e,source,data){
						$("#formEditorPanel").addClass("ui-state-highlight");
					},
					onDragLeave: function(e,source,data){
						$("#formEditorPanel").removeClass("ui-state-highlight");
					},
					onDrop: function(e,source,data){
						$("#formEditorPanel").removeClass("ui-state-highlight");
						YonYou.editor.appendEl("form",$(source).data("data"),data);
					}
				});
				
				YonYou.editors.form.reinit();
				YonYou.editors.register["form"] = "Y";
			}
		});
	}
};


YonYou.editor = {
	ids : {
		operations : ["run","runstep","runreturn","pause","stop","validate","analize","runstep"],
		editors : ["edit","magnify","lessen","partMagnify","zoom_100","zoom_fit","screenMove"],
		grids : ["grid_show","grid_close"],
		systems : ["cut","copy","paste","delete"],
		saves : ["save","saveas","save_xml","save_image"]
	},
	getId : function(panel,id){
		if(typeof(id)=="string"){
			return panel+"_"+id;
		}else if(id instanceof Array){
			var rtn = [];
			for(var i=0;i<id.length;i++){
				rtn.push(panel+"_"+id[i]);
			}
			return rtn;
		}
	},
	getFlow : function(panel){
		eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
    	if(canvasObj && canvasObj.childCanvas){
    		return canvasObj.getChildCanvasByIndex(0);
    	}
	},
	editorZoom100 : function(panel){
		YonYou.editor.changeOuterControlType(panel,"");
    	var flow = YonYou.editor.getFlow(panel);
    	if(flow){
    		flow.showZoom100();
    	}
	},
	editorFix : function(panel){
		YonYou.editor.changeOuterControlType(panel,"");
		var flow = YonYou.editor.getFlow(panel);
    	if(flow){
    		flow.fixSize();
    	}
	},
	changeOuterControlType : function (panel,v,id){
		if(v==undefined || v==null || v==""){
			v = "";
			YonYou.BqButton.inactive(YonYou.editor.getId(panel,YonYou.editor.ids.editors));
		}else{
			var btnId = YonYou.editor.getId(panel,((id)?id:v));
			if(YonYou.BqButton.isActive(btnId)){
				v = "";
				YonYou.BqButton.inactive(btnId);
			}else{
				YonYou.BqButton.inactive(YonYou.editor.getId(panel,YonYou.editor.ids.editors));
				YonYou.BqButton.active(btnId);
			}
		}
		
		eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
    	if(canvasObj){
    		var ctrlType = v;
    		canvasObj.setOuterControl(true,ctrlType);
    		canvasObj.redraw();
    	}
    },

    deleteSelectedElFromOutset : function (panel){
    	eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
   			flow.deleteSelectedEl();
    		canvasObj.redraw();
    		
    		YonYou.editor.stepSelect('trans',canvasObj,flow);
    	}
    	
    	
    },

    save : function (panel){
    	eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
    	if(canvasObj && canvasObj.childCanvas){
    		var transId = canvasObj.config.data.extendData.transId;
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		YonYou.ab({
    			type : "post",
    			url : "rest/transjob/trans/"+transId+"/save",
    			modal : true,
    			modalMessage : "正在保存...",
    			params : {
    				val : flow.getElsValue()
    			}
    		});
    	}
    	
    	diEditorPageTabs.setTabModify(null, false);
    },
    
    saveTab : function (clicked) {
    	var isActive = diEditorPageTabs.isActive(clicked.data("exdata").id+"-tab");
    	if(isActive){
    		YonYou.editor.save("trans");
    	}else{
    		var transId = clicked.data("exdata").data.extendData.transId;
    		YonYou.ab({
    			type : "post",
    			url : "rest/transjob/trans/"+transId+"/save",
    			modal : true,
    			modalMessage : "正在保存...",
    			params : {
    				clear : true,
    				val : YonYou.toJSONString(clicked.data("exdata").data)
    			}
    		});
    	}
    },
    
    discardTab : function (clicked) {
    	var transId = clicked.data("exdata").data.extendData.transId;
    	YonYou.ab({
			type : "get",
			url : "rest/transjob/trans/"+transId+"/discard"
		});
    },
    
    appendEl : function (panel,data,pos){
    	eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		var realOffset = YonYou.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,10);
    		var realPos = YonYou.widget.FlowChartUtils.arcScalePoint(canvasObj.config,{
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
    showGrid : function(panel){
    	var btnId = YonYou.editor.getId(panel,"grid_show");
    	var showGrid;
		if(YonYou.BqButton.isActive(btnId)){
			showGrid=false;
			YonYou.BqButton.inactive(btnId);
			YonYou.BqButton.disabled(YonYou.editor.getId(panel,"grid_close"));
		}else{
			showGrid=true;
			YonYou.BqButton.active(btnId);
			YonYou.BqButton.enabled(YonYou.editor.getId(panel,"grid_close"));
		}
    	eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
    	if(canvasObj && canvasObj.childCanvas){
    		var flow = canvasObj.getChildCanvasByIndex(0);
    		flow.showGrid(showGrid);
    	}
    },
    
    /**
     * 贴近网格
     */
    closeGrid : function(panel){
    	var btnId = YonYou.editor.getId(panel,"grid_close");
    	var close;
		if(YonYou.BqButton.isActive(btnId)){
			close=false;
			YonYou.BqButton.inactive(btnId);
		}else{
			close=true;
			YonYou.BqButton.active(btnId);
		}
    	eval("var canvasObj="+panel+"EditorPanel_var.flowChart");
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
     * @param panel
     * @param setting：fromElId, toElId
     * @param flowObject
     */
    checkEndHop : function (panel,setting,flowObject){
    	var hops = flowObject.config.canvasEls.hops;
    	for(var i=0;i<hops.length;i++){
    		if(hops[i].fromElId == setting.fromElId && hops[i].toElId == setting.toElId){
    			return false;
    		}
    	}
    	return true;
    },
    
    stepSelect : function(panel,flowChart,flowObject,model){
    	var editorIds = YonYou.editor.getId(panel,["cut","copy"]);
    	var deleteId = YonYou.editor.getId(panel,"delete");
    	if(flowObject.hasSelectedEl("step")){
    		YonYou.BqButton.enabled(editorIds);
    	}else{
    		YonYou.BqButton.disabled(editorIds);
    	}
    	if(flowObject.hasSelectedEl()){
    		YonYou.BqButton.enabled(deleteId);
    	}else{
    		YonYou.BqButton.disabled(deleteId);
    	}
    },
    
    modify : function(panel,flowChart,flowObject){
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
    	YonYou.cw("Dialog",null,{
			id : "dialog-"+model.id,
			header : model.bText[0],
			width : w,
			height : h,
			autoOpen : true,
			showHeader : true,
			url : "rest/transjob/transstep/"+flowObject.config.extendData.transId+"/"+model.extendData.stepName+"/"+model.provider,
			footerButtons : [{
				componentType : "bq:PushButton",
				type : "button",
				label : "确定",
				title : "确定",
				events : {
					"click" : function(){
						YonYou.ab({
							formId : "",
							formAction : ""
						});
					}
				}
			},{
				componentType : "bq:PushButton",
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
    }
};