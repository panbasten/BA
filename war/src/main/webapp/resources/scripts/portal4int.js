Flywet.Portal4int = {
	
	messages: null,
	MENU_VAR: null,
	
	FIXED_SIZE: {
		loading: 48
	},
	
	resize: function() {
		var win = Flywet.getWindowScroll();
		// cover
		$("#fly_portal_cover").width(win.width+20).height(win.height+20);
		$("#fly_portal_cover_img").css({"top":(win.height-Flywet.Portal4int.FIXED_SIZE.loading)/2+"px","left":(win.width-Flywet.Portal4int.FIXED_SIZE.loading)/2+"px"});
	
//		idMainContainer
		
		var containerWidth = $("#idMainContainer").width();
		$("#idController").css("right", ((win.width-containerWidth)/2+10)+"px");
	},

	initPage: function() {
		Flywet.env();
		
		// 更新部分尺寸
		Flywet.Portal4int.resize();
		
		// 注册遮盖层方法
		Flywet.triggerMark = Flywet.Portal4int.pageCover;
		Flywet.triggerMark(true);
		
		// 1.替换标识文字
		Flywet.ab({
			type: "get",
			url: "rest/identification/messages",
			onsuccess: function(data, status, xhr){
				Flywet.Portal4int.messages = data;
				Flywet.Portal4int.changeWebText();
			}
		});
		
		// 2.加载portal的菜单
		Flywet.ab({
			type: "get",
			url: "rest/portalet/menus/1",
			onsuccess: function(data, status, xhr){
				if(data){
					Flywet.Portal4int.MENU_VAR = new Flywet.Portal4int.menu({
						id1st : "fly_portal_menu_1st_level",
						id2ed : "fly_portal_menu_2ed_level",
						idTarget : "idMainView",
						data : data
					});
					
					// 展现一个菜单项
					Flywet.Portal4int.MENU_VAR.showMenu();
					
				}
			}
		});
		
	},
	
	pageCover: function(show) {
		if(show){
			$("#idBody").addClass("fly_portal_cover");
			$("#fly_portal_cover").show();
		}else{
			$("#fly_portal_cover").fadeOut();
			$("#idBody").removeClass("fly_portal_cover");
		}
	},
	
	changeWebText: function() {
		// TODO
//		console.log(Flywet.Portal4int.messages);
	}

};

/**
 * Internet的portal页面的菜单对象
 * @param {} cfg
 */
Flywet.Portal4int.menu = function(cfg){
	
	this.cfg = $.extend({
		activeMenus: [] // 默认激活的菜单
		,menusJq: {}
	}, cfg);
	
	this.id1st = cfg.id1st;
	this.id2ed = cfg.id2ed;
	this.idTarget = cfg.idTarget;
	
	this.id1stJq = $(Flywet.escapeClientId(this.id1st));
	this.id2edJq = $(Flywet.escapeClientId(this.id2ed));
	this.idTargetJq = $(Flywet.escapeClientId(this.idTarget));
	
	this.init();
};

Flywet.Portal4int.menu.prototype.init=function(){
	if(this.cfg.data && this.cfg.data.length > 0){
		// 第一级菜单
		if(this.cfg.activeMenus[0] == undefined){
			this.cfg.activeMenus[0] = this.cfg.data[0].id;
		}
		
		var menuId,menuJq;
		for(var i=0;i<this.cfg.data.length;i++){
			menuId = this.cfg.data[i].id;
			menuJq = initFirstLevel(this.cfg.data[i],this);
			this.cfg.menusJq["id_"+menuId] = menuJq;
			
			if(menuId == this.cfg.activeMenus[0]){
				menuJq.addClass("active");
			}
			
			this.id1stJq.append(menuJq);
		}
		
		// 第二级菜单
		this.flushSecondLevel(this.cfg.activeMenus[0]);
		
	}
	
	
	function initFirstLevel(menu,_self){
		var li = $("<li role='navi_1st' roleId='"+menu.id+"'></li>");
		var lia = $("<a href='javascript:void(0);'>"+menu.desc+"</a>");
		if(menu.disabled){
			lia.addClass("menu-disabled");
			lia.attr("title","该菜单项没有被授权。");
		}
		li.append(lia);
		
		li.data("cfg", menu);
		
		return li;
	}
	
	
};

/**
 * 根据父菜单ID刷新二级目录
 */
Flywet.Portal4int.menu.prototype.flushSecondLevel = function (pMenuId){
	var pMenuCfg = this.cfg.menusJq["id_"+pMenuId].data("cfg");
	
	if(pMenuCfg.children && pMenuCfg.children.length>0){
		
		this.id2edJq.empty();
		
		if(this.cfg.activeMenus[1] == undefined){
			this.cfg.activeMenus[1] = pMenuCfg.children[0].id;
		}
		
		var menu,menuJq;
		for(var i=0;i<pMenuCfg.children.length;i++){
			menu = pMenuCfg.children[i];
			menuJq = $("<div class='list-group' role='navi_2ed' roleId='"+menu.id+"'></div>");
			
			menuJq.data("cfg", menu);
			
			this.cfg.menusJq["id_"+menu.id] = menuJq;
			
			flushThirdLevel(menu.id,this);
		
			if(menu.id == this.cfg.activeMenus[1]){
				// do nothing
			}
			
			this.id2edJq.append(menuJq);
		}
		
		// 为第三级菜单添加事件
		this.id2edJq.find("[role='navi_3th']").click(function(e,v){
			var t = $(e.target);
			Flywet.Portal4int.MENU_VAR.showMenu(t.attr("roleId"))
		});
		
	}
	
	function flushThirdLevel(pMenuId,_self){
		var pMenuJq = _self.cfg.menusJq["id_"+pMenuId],
			pMenuCfg = pMenuJq.data("cfg");
		
		if(pMenuCfg.children && pMenuCfg.children.length>0){
			
			if(_self.cfg.activeMenus[2] == undefined){
				_self.cfg.activeMenus[2] = pMenuCfg.children[0].id;
			}
			
			var menu,menuJq;
			for(var i=0;i<pMenuCfg.children.length;i++){
				menu = pMenuCfg.children[i];
				menuJq = $("<a href='javascript:void(0);' class='list-group-item' role='navi_3th' roleId='"+menu.id+"'>"+menu.desc+"</a>");
				
				menuJq.data("cfg", menu);
				
				_self.cfg.menusJq["id_"+menu.id] = menuJq;
						
				if(menu.id == _self.cfg.activeMenus[2]){
					menuJq.addClass("active");
				}
				
				pMenuJq.append(menuJq);
			}
			
		}
	}
};

Flywet.Portal4int.menu.prototype.activeMenus = function() {
	return this.cfg.activeMenus;
};

Flywet.Portal4int.menu.prototype.showMenu = function(id){
	var menuId = id || this.cfg.activeMenus[2] || this.cfg.activeMenus[1] || this.cfg.activeMenus[0];
	
	this.id2edJq.find(".active").removeClass("active");
	this.cfg.menusJq["id_"+menuId].addClass("active");
	
	Flywet.ab({
		type: "get",
		url: "rest/portalet/menu/"+menuId,
		modal: true,
		params : {
			targetId: this.idTarget
		},
		onsuccess: function(data, status, xhr){
			var view = $(Flywet.escapeClientId(data.targetId));
			view.html("");
			var view2 = $("<div style='position: relative;'></div>").appendTo(view);
			if(!data.data.width){
				data.data.width = "parent";
			}
			if(!data.data.height){
				data.data.height = "auto";
			}
			
			// 资源访问的url，在portal中是通过访问桥的方式获得
			data.data.resourceAccessFunc = function(id, name){
				return "rest/portalet/actionResource/3?param="+id+"_"+name;
			};
			
			view2.spreadsheet(data.data);
		}
	});
};









