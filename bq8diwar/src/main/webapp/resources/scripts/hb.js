YonYou.desktop = {
	initPage : function () {
		// Editor
		var layoutSettings_diEditor = {
			name: "diEditor",	
			defaults: {
				size : "auto",
				resizable : false,
				slidable : false,
				spacing_open : 0, // cosmetic spacing
				togglerLength_open : 0, // HIDE the toggler button
				togglerLength_closed : -1, // "100%" OR -1 = full width of pane
				resizable : false,
				slidable : false,
				fxName : "none",
				spacing_closed : 1
			},	
			north: {
				size : 0 // cosmetic spacing
			},	
			south: {
				size : 30
			},	
			center: {
				paneSelector : "#editorContent" // sample: use an ID to select pane instead of a class
			}
		};

		diEditorLayout = $("body").layout( layoutSettings_diEditor );
		
		// 通过ajax队列加载
		// 1.加载导航页
		YonYou.ab({
			type : "get",
			url : "rest/base/navigatorpage",
			beforeSend : function(){
				YonYou.desktop.changeMarkText("正在加载导航页面...");
			},
			oncomplete : function(xhr, status){
				// 创建导航页的选项卡
				YonYou.cw("EasyTabs","diEditorNaviTabs",{
					id : "navis"
				});
				
				// 滚动页签放在设置页面尺寸之后再初始化，根据变化后的尺寸计算
				YonYou.cw("Scrollbar","diEditorNaviScroll",{
					id:'navis',
					tabGroup:'navigator-ul',
					step:70,
					scrollType:'vertical'
				});
				
				// 4.循环遍历加载每个导航页的内容页
				var naviLis = $("#navigator-ul").find("li");
				for(var i=0; i<naviLis.length; i++){
					// 加载导航具体内容
					YonYou.ab({
						type : "get",
						url : "rest/"+$(naviLis[i]).attr("category")+"/navi",
						params:{parentId : $(naviLis[i]).attr("id")},
						beforeSend : function(){
							YonYou.desktop.changeMarkText("正在加载导航内容...");
						},
						oncomplete : function(xhr, status){
							
						}
					});
				}
			}
		});
		
		// 2.加载转换页
		YonYou.ab({
			type : "get",
			url : "rest/transjob/trans/editor",
			beforeSend : function(){
				YonYou.desktop.changeMarkText("正在加载转换页面...");
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
			}
		});
		
		// 3.加载用户信息弹出页
		YonYou.ab({
			type : "get",
			url : "rest/base/usersettingpage",
			beforeSend : function(){
				YonYou.desktop.changeMarkText("正在加载用户信息页面...");
			},
			oncomplete : function(xhr, status){
				YonYou.cw("Popup","userPopup_var",{
					id: "idUserPopup",
					targetId: "idUser"
				});
				YonYou.desktop.initComplete();
			}
		});
		
	},
	
	initSize : function() {
		// 设置浏览器环境
		YonYou.env();

		// 设置页面尺寸
		YonYou.desktop.changeSize();
		
		diEditorNaviScroll.reinit();
		
		transStepBarScroll.reinit();
		
		if(window["transEditorPanel_var"]){
			transEditorPanel_var.changeSize(YonYou.desktop.contentWidthNoPadding,
				YonYou.desktop.contentHeightEditor);
		}
	},
	
	/* 
	 * 构建完成后的处理过程
	 */
	initComplete : function() {
		
		// 创建编辑器总体Tab页面
		YonYou.cw("EasyTabs","diEditorPageTabs",{
			id : "editorContent",
			createTab: "<li><a class='ui-tab-a' href='##tabId'><div class='ui-tab-left'></div><div class='ui-tab-middle'>#modifyTag<span class='ui-tab-text'>#tabText</span>#closeButton</div><div class='ui-tab-right'></div></a></li>",
			onBeforeSelect : "YonYou.desktop.changeTransEditorPage",
			onSave : "YonYou.editor.saveTab",
			onDiscard : "YonYou.editor.discardTab",
			modifyCloseTip : "即将关闭任务【#tabText】已经被修改。<br>选择【保存】按钮进行保存操作然后关闭选项卡，选择【放弃】按钮所进行的修改操作将丢失，请进行选择？"
		});
		
		YonYou.desktop.initSize();
		
		// 添加搜索框
		YonYou.cw("Search","search_var",{
			id:"idSearch",
			styleClass: "hb-search",
			onsearch:function(data){
				alert("您搜索的是："+data);
			}
		});
		
		// 隐藏蒙版
		YonYou.desktop.triggerMark(false);

		// TODO
		//diEditorPageTabs.setTabModify("trans-1-tab", true);
	},
	
	changeSize : function () {
		var win = YonYou.getWindowScroll();
		this.contentHeight = win.height-70;
		this.contentWidth = win.width-395;
		$(".hb-editor-content-height").height(this.contentHeight);
		$(".hb-editor-content-height-no-padding").height(this.contentHeight-10);
		$(".hb-editor-content-height-browse-panel").height(this.contentHeight-33);
		$(".hb-editor-stepbar-content-height").height(this.contentHeight);
		$(".hb-editor-stepbar-height-no-padding").height(this.contentHeight-10);
		$(".hb-editor-content-width").width(this.contentWidth);
		
		this.contentWidthNoPadding = this.contentWidth-10;
		$(".hb-editor-content-width-no-padding").width(this.contentWidthNoPadding);
		// trans
		this.contentHeightEditor = this.contentHeight-40;
		$(".hb-editor-content-height-editor").height(this.contentHeightEditor);
	},
	
	changeMarkText : function (text) {
		if(!text){
			text="正在加载页面...";
		}
		$("#startingText").html(text);
	},
	triggerMark : function(show){
		if(show){
			$("#startingCover").fadeIn();
		}else{
			$("#startingCover").fadeOut("slow",function(){
				YonYou.desktop.changeMarkText();
			});
		}
	},
	changeTransEditorPage : function ($taba,$tabo) {
		// 保存原来的结果
		if ($tabo && $tabo.data("exdata")){
			var canvasObj = transEditorPanel_var.flowChart;
			if(canvasObj && canvasObj.childCanvas){
				var flow = canvasObj.getChildCanvasByIndex(0);
				$tabo.data("exdata").data = YonYou.parseJSON(flow.getElsValue());
			}
		}
		
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
	toggleContent : function(id,close){
		$(YonYou.escapeClientId(id)).toggle("fast",function(){
			$(close).toggleClass("ui-icon-circle-plus");
			$(close).toggleClass("ui-icon-circle-minus");
		});
	}
};

