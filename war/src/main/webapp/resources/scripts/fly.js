Plywet.desktop = {
	initPage : function () {
		// 设置浏览器环境
		Plywet.env();
		
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
		Plywet.ab({
			type : "get",
			url : "rest/base/navigatorpage",
			beforeSend : function(){
				Plywet.desktop.changeMarkText("正在加载导航页面...");
			},
			oncomplete : function(xhr, status){
				// 创建导航页的选项卡
				Plywet.cw("EasyTabs","diEditorNaviTabs",{
					id : "navis"
				});
				
				// 滚动页签放在设置页面尺寸之后再初始化，根据变化后的尺寸计算
				Plywet.cw("Scrollbar","diEditorNaviScroll",{
					id:'navis',
					tabGroup:'navigator-ul',
					step:70,
					scrollType:'vertical'
				});
				
				// 循环遍历加载每个导航页的内容页
				var naviLis = $("#navigator-ul").find("li");
				for(var i=0; i<naviLis.length; i++){
					// 加载导航具体内容
					Plywet.ab({
						type : "get",
						url : "rest/"+$(naviLis[i]).attr("category")+"/navi",
						params:{parentId : $(naviLis[i]).attr("id")},
						beforeSend : function(){
							Plywet.desktop.changeMarkText("正在加载导航内容...");
						},
						oncomplete : function(xhr, status){
							
						}
					});
				}
			}
		});
		
		// 2.注册trans、job编辑器
		Plywet.editors.trans.register();
		
		Plywet.editors.dashboard.register();
		
		// 3.加载用户信息弹出页
		Plywet.ab({
			type : "get",
			url : "rest/base/usersettingpage",
			beforeSend : function(){
				Plywet.desktop.changeMarkText("正在加载用户信息页面...");
			},
			oncomplete : function(xhr, status){
				Plywet.cw("Popup","userPopup_var",{
					id: "idUserPopup",
					targetId: "idUser"
				});
				Plywet.desktop.initComplete();
			}
		});
		
		// 4.退出按钮
		$(".fly-exit").bind("click", function(){
			window.location='login';
		});
		
	},
	
	resize : function() {
		// 设置页面尺寸
		Plywet.desktop.changeSize();
		
		Plywet.editors.resize();
	},
	
	/* 
	 * 构建完成后的处理过程
	 */
	initComplete : function() {
		
		// 创建编辑器总体Tab页面
		Plywet.cw("EasyTabs","diEditorPageTabs",{
			id : "editorContent",
//			animate:false,
			createTab: "<li><a class='ui-tab-a' href='##tabId'><div class='ui-tab-left'></div><div class='ui-tab-middle'>#modifyTag<span class='ui-tab-text'>#tabText</span>#closeButton</div><div class='ui-tab-right'></div></a></li>",
			onBeforeSelect : "Plywet.editors.changeEditor",
			onSave : "Plywet.editors.saveTab",
			onDiscard : "Plywet.editors.discardTab",
			modifyCloseTip : "即将关闭设计【#tabText】已经被修改。<br>选择【保存】按钮进行保存操作然后关闭选项卡，选择【放弃】按钮所进行的修改操作将丢失，请进行选择？"
		});
		
		// 添加搜索框
		Plywet.cw("Search","search_var",{
			id:"idSearch",
			styleClass: "fly-search",
			onsearch:function(data){
				alert("您搜索的是："+data);
			}
		});
		
		// 改变首页部分页面元素尺寸
		Plywet.desktop.changeSize();
		
		// 隐藏蒙版
		Plywet.desktop.triggerMark(false);

	},
	
	changeSize : function () {
		var win = Plywet.getWindowScroll();
		this.contentHeight = win.height-70;
		this.contentWidth = win.width-395;
		$(".fly-editor-content-height").height(this.contentHeight);
		$(".fly-editor-content-height-no-padding").height(this.contentHeight-10);
		$(".fly-editor-content-height-browse-panel").height(this.contentHeight-33);
		
		diEditorNaviScroll.reinit();
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
				Plywet.desktop.changeMarkText();
			});
		}
	}
};

