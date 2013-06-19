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
					id : "navis",
					onBeforeSelect : "Plywet.desktop.changePositionTextByNavi",
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
				
				// 设置当前位置
				Plywet.desktop.changePositionTextByNaviFirst();
			}
		});
		
		// 2.注册trans、job编辑器
		Plywet.editors.trans.register();
		
		Plywet.editors.dashboard.register();
		
		// 3.加载用户信息弹出页
		Plywet.ab({
			type : "get",
			url : "rest/identification/usersettingpage",
			beforeSend : function(){
				Plywet.desktop.changeMarkText("正在加载用户信息页面...");
			},
			onsuccess : function(data, xhr, status){
				$("#idUser").html(data.username);
				Plywet.cw("Popup","userPopup_var",{
					id: "idUserPopup",
					targetId: "idUser",
					width: 150,
					height: 100
				});
				userPopup_var.render(data.dom, data.script);
				Plywet.desktop.initComplete();
			}
		});
		
		// 4.退出按钮
		$(Plywet.escapeClientId("fly-exit")).bind("click", function(){
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
	
	/**
	 * 改变位置文字
	 * @param text 文字
	 * @param saveLast 是否保存上一个位置
	 */
	changePositionText : function(text,saveLast){
		var pos = $("#posText");
		if(saveLast){
			var last = pos.data("posLast") || [];
			last.push(pos.html());
			pos.data("posLast",last);
		}
		pos.html(text||"");
	},
	
	/**
	 * 恢复最后一个位置文字
	 */
	restorePositionText : function(){
		var last = pos.data("posLast");
		if(last){
			pos.html(last.pop());
		}
	},
	
	changePositionTextByNavi : function($taba){
		Plywet.desktop.changePositionText($taba.data("data"));
	},
	
	changePositionTextByNaviFirst : function(){
		var naviFirst = $(Plywet.escapeClientId("navigator-ul")).find("a").first();
		Plywet.desktop.changePositionText(naviFirst.data("data"));
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

