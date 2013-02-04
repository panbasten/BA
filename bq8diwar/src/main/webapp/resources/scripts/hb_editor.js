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
				componentType : "bq:Button",
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
				componentType : "bq:Button",
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