Flywet.Portal = {
	FIXED_SIZE : {
		header : 35,
		footer : 95,
		footerContent : 60,
		loading : 48
	},
	PIC_TOTILE_NUM : 10,
	PIC_NUM : 0,
	MENU_VAR : null,
	MAX_SCEEN : false,
	COOKIE_KEYS : ["username","loginname","repository","repositoryType","toeditor"],
	messages : null,
	section: {
		slideShow : null,
		slideShowId : null,
		slideShowRunning : false,
		transitionEffect : null,
		resetSlideshowCounter: function(){
			Flywet.Portal.section.pauseSlideshow();
			Flywet.Portal.section.startSlideshow();
		},
		startSlideshow: function(){
			if (Flywet.Portal.section.slideShowRunning) return true;
			Flywet.Portal.section.slideShowRunning = true;
		    Flywet.Portal.section.slideShowId = setInterval(function(){
		    	Flywet.Portal.section.slideShow.cycle('next');
		    },7500);
		},
		pauseSlideshow: function(){
			Flywet.Portal.section.slideShowRunning = false;
		    clearInterval(Flywet.Portal.section.slideShowId);
		}
	},
	
	pageBlocked : function() {
		$("#loginBtn").blur()
			.removeClass("ui-login-button-disabled")
			.removeClass("ui-login-button-hover")
			.bind("click", function(){
				Flywet.Portal.loginAction();
			});
		Flywet.dialog.warning("浏览器阻止弹出系统首页，请将该网站加入授信站点，并且允许浏览器弹出该网址窗口，然后再次登录。");
	},
	
	loginAction : function(){
		Flywet.ab({
			formId: "loginForm",
			formAction: "rest/identification",
			beforeSend : function(){
				$("#loginBtn").addClass("ui-login-button-disabled")
					.unbind("click");
				$("#fly_portal_cover").show();
			},
			onsuccess: function(data, status, xhr){
				if(data.state == 0){
					var cookieJson = Flywet.parseJSON(data.data);
					for(var p in cookieJson){
						Flywet.CookieUtils.write(p,cookieJson[p]);
					}
					// 判断是否是子页面
					//window.location = "editor";
					
					if(cookieJson.toeditor){
						if (Flywet.browserDetect.msie){
							window["editorPageHandle"] = window.open("editor","","modal=1,dialog=1,fullscreen=1,toolbar=0,menubar=0,location=0,directries=0,location=0,scrollbars=0,status=0,resizable=0");
							var num = 0;
							function checkWebPageForIE(){
								if(window["editorPageHandle"]){
									clearInterval(interval);
									if (Flywet.browserDetect.isIE6) {  
										window.opener = null; 
										window.close();  
									}  
									else {  
										window.open('', '_top'); 
										window.top.close();  
						            }
								}else{
									num = num + 1;
									if(num>10){
										clearInterval(interval);
										Flywet.Portal.pageBlocked();
									}
								}
							}
							var interval = setInterval(checkWebPageForIE, 500);
						
						}else if (Flywet.browserDetect.webkit){
							window["editorPageHandle"] = window.open("editor","","left=0,top=0,width="+window.screen.availWidth+",height="+window.screen.availHeight+",modal=1,dialog=1,toolbar=0,menubar=0,location=0,personalbar=0,location=0,scrollbars=0,status=0,resizable=0");
							var num = 0;
							function checkWebPageForWebkit(){
								if (window["editorPageHandle"] && window["editorPageHandle"].outerHeight > 0){
									clearInterval(interval);
									window.opener = null;
						            window.open('', '_self', '');
									window.close();
								}else{
									num = num + 1;
									if(num>10){
										clearInterval(interval);
										Flywet.Portal.pageBlocked();
									}
								}
							}
							var interval = setInterval(checkWebPageForWebkit, 500);
							
						}else if (Flywet.browserDetect.mozilla){
							try{
								window["editorPageHandle"] = window.open("editor","","modal=1,dialog=1,toolbar=0,menubar=0,location=0,personalbar=0,location=0,scrollbars=0,status=0,resizable=0");
								window["editorPageHandle"].moveTo(0, 0);
								window["editorPageHandle"].resizeTo(window.screen.availWidth, window.screen.availHeight);
						
								window.location.href = 'about:blank';
							}
							catch(e){
								Flywet.Portal.pageBlocked();
							}
						}
						if(window["editorPageHandle"]){
							window["editorPageHandle"].focus();
						}
					}else{
						window.location = "portal";
					}
				}else{
					var msg = "";
					if(data.messages){
						for(var i=0;i<data.messages.length;i++){
							msg = msg + "&#9830;" + data.messages[i] + "<br/>";
						}
					}
					$("#loginBtn").blur()
						.removeClass("ui-login-button-disabled")
						.removeClass("ui-login-button-hover")
						.bind("click", function(){
							Flywet.Portal.loginAction();
						});
					Flywet.dialog.error(msg);
					$("#fly_portal_cover").hide();
				}
				return true;
			}
		});
	},
	
	resize: function(){
		var win = Flywet.getWindowScroll();
		var h = Math.max(0,(win.height-580)),
			w = Math.max(0,(win.width-1100));
		// login
		$("#fly_login_wrapper").css({
			"margin-top":h+"px",
			"margin-left":w+"px"
		});
		
		// bg img
		Flywet.Portal.PIC_NUM = Math.floor(Math.random()*(Flywet.Portal.PIC_TOTILE_NUM-0.001)+1);
		$("#fly_portal_bg_img")
			.attr("src", "resources/images/pics/wallpaper"+Flywet.Portal.PIC_NUM+".jpg")
			.width(win.width+20).height(win.height+20).show("fast");
		
		// cover
		$("#fly_portal_cover").width(win.width+20).height(win.height+20);
		$("#fly_portal_cover_img").css({"top":(win.height-Flywet.Portal.FIXED_SIZE.loading)/2+"px","left":(win.width-Flywet.Portal.FIXED_SIZE.loading)/2+"px",});
		
		// size
		$("#fly_portal_header").width(win.width);
		$("#fly_portal_menus").width(win.width);
		$("#fly_portal_footer").width(win.width).css("top", (win.height-Flywet.Portal.FIXED_SIZE.footer)+"px");
		
		// content
		$("#fly_portal_content").width(win.width).height(win.height-Flywet.Portal.FIXED_SIZE.header-Flywet.Portal.FIXED_SIZE.footer).css("margin-top","35px");
		
		if(window["portal_content_var"]){
			window["portal_content_var"].resize();
		}
	},
	
	changeWebText : function(){
		document.title=Flywet.Portal.messages["msg_page_title"];
		$("#companyName").html(Flywet.Portal.messages["msg_page_company_name"]);
		$("#footerCompanyName").html(Flywet.Portal.messages["msg_page_company_name"]);
		$("#companyCopyright").html(Flywet.Portal.messages["msg_page_company_copyright"]);
		$("#footerCompanyCopyright").html(Flywet.Portal.messages["msg_page_company_copyright"]);
		$("#companyOthers").html(Flywet.Portal.messages["msg_page_company_others"]);
	},
	
	nextBackground : function(){
		Flywet.Portal.PIC_NUM = Flywet.Portal.PIC_NUM + 1;
		if(Flywet.Portal.PIC_NUM > Flywet.Portal.PIC_TOTILE_NUM){
			Flywet.Portal.PIC_NUM = Flywet.Portal.PIC_NUM - Flywet.Portal.PIC_TOTILE_NUM;
		}
		var bg = $(Flywet.escapeClientId("fly_portal_bg_img"));
		bg.fadeOut(700, function(){
			bg.attr("src", "resources/images/pics/wallpaper"+Flywet.Portal.PIC_NUM+".jpg");
			bg.fadeIn("fast");
		});
	},
	
	previousBackground : function(){
		Flywet.Portal.PIC_NUM = Flywet.Portal.PIC_NUM - 1;
		if(Flywet.Portal.PIC_NUM < 1){
			Flywet.Portal.PIC_NUM = Flywet.Portal.PIC_NUM + Flywet.Portal.PIC_TOTILE_NUM;
		}
		var bg = $(Flywet.escapeClientId("fly_portal_bg_img"));
		bg.fadeOut(700, function(){
			bg.attr("src", "resources/images/pics/wallpaper"+Flywet.Portal.PIC_NUM+".jpg");
			bg.fadeIn("fast");
		});
			
	},
	
	fullSceen : function(){
		var win = Flywet.getWindowScroll();
		if(Flywet.Portal.MAX_SCEEN){
			$(Flywet.escapeClientId("fly_portal_header")).animate({height:"toggle"},500);
			$(Flywet.escapeClientId("fly_portal_menus")).animate({height:"toggle"},500);
			$(Flywet.escapeClientId("fly_portal_footer")).animate({top:(win.height-95)+"px"},500);
			
			$(Flywet.escapeClientId("btn_full")).addClass("fly-full-sceen-out").removeClass("fly-full-sceen-in").attr("title", "全屏");
			Flywet.Portal.MAX_SCEEN = false;
		}else{
			$(Flywet.escapeClientId("fly_portal_header")).animate({height:"toggle"},500);
			$(Flywet.escapeClientId("fly_portal_menus")).animate({height:"toggle"},500);
			$(Flywet.escapeClientId("fly_portal_footer")).animate({top:(win.height-35)+"px"},500);
			
			$(Flywet.escapeClientId("btn_full")).addClass("fly-full-sceen-in").removeClass("fly-full-sceen-out").attr("title", "恢复");
			Flywet.Portal.MAX_SCEEN = true;
		}
	},
	
	initPageComplete : function(){
		// 显示用户名称
		var username = Flywet.CookieUtils.read("username");
		var loginname = Flywet.CookieUtils.read("loginname");
		var loginUser = $("#btn_login_user");
		if(username){
			$("#btn_login_user_text").html(username);
			loginUser.bind("mouseover", function(){
					$(this).addClass("highlight");
				}).bind("mouseout", function(){
					$(this).removeClass("highlight");
				});
		}else{
			loginUser.hide();
		}
		
		// 登录按钮
		var btnLogin = $("#btn_login");
		if(username){
			btnLogin.hide();
		}else{
			btnLogin.bind("click", function(){
				if(Flywet.browserDetect.msie){
					$("#fly_login_wrapper").show();
				}else{
					$("#fly_login_wrapper").show("normal");
				}
			}).bind("mouseover", function(){
				$(this).addClass("highlight");
			}).bind("mouseout", function(){
				$(this).removeClass("highlight");
			});
			
			$(document).keydown(function(e){
				if(btnLogin.is(":visible")){
					if(e.keyCode==13){
						Flywet.Portal.loginAction();
					}
				}
			});
		}
		
		// 登录窗口的登录按钮
		$("#loginBtn").bind("click", function(){
			Flywet.Portal.loginAction();
		}).live("mouseover", function(){
			$(this).addClass('ui-login-button-hover');
		}).live("mouseout", function(){
			$(this).removeClass('ui-login-button-hover');
		}).live("focus", function(){
			$(this).addClass('ui-login-button-hover');
		}).live("blur", function(){
			$(this).removeClass('ui-login-button-hover');
		});
		
		// 登录窗口的用户名
		if(loginname){
			$("#username").val(loginname);
		}
		
		// 对于设置按钮的事件
		var settingBtn = $("#btn_setting");
		var settingDiv = $("#fly_portal_sub_menu_setting");
		var time = null;
		settingBtn.bind("mouseover", function(){
			if (time){
				clearTimeout(time);
				time = null;
			}
			settingBtn.addClass("setting-highlight");
			settingDiv.show();
		}).bind("mouseout", function(){
			time = setTimeout(function(){
				settingBtn.removeClass("setting-highlight");
				settingDiv.hide();
			}, 300);
		});
		settingDiv.bind('mouseenter', function(){
			if (time){
				clearTimeout(time);
				time = null;
			}
		}).bind('mouseleave', function(){
			time = setTimeout(function(){
				settingBtn.removeClass("setting-highlight");
				settingDiv.hide();
			}, 500);
		});
		
		settingDiv.find("a").each(function(){
			var dl = $(this);
			dl.bind("mouseover", function(){
				dl.addClass("highlight");
			}).bind("mouseout", function(){
				dl.removeClass("highlight");
			});
		});
		
		// --------------设置区域操作--------------------
		// 生成并下载私钥
		$("#createPriKey").bind("click", function(){
			// 判断是否存在公钥
			if(exKey){
				Flywet.dialog.warning("密钥已经存在，请先移除密钥再生成新的密钥。");
			}else{
				$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/identification/createKey");
			}
		});
		
		// 重新下载私钥
		$("#downloadPriKey").bind("click", function(){
			// 判断是否存在私钥
			if(exKey){
				Flywet.dialog.warning("密钥已经存在，请先移除密钥再生成新的密钥。");
			}else{
				$(Flywet.escapeClientId("file-download-frame")).attr("src","rest/identification/createKey");
			}
		});
		
		// 登入平台
		$("#loginPortal").bind("click", function(){
			if(Flywet.browserDetect.msie){
				$("#fly_login_wrapper").show();
			}else{
				$("#fly_login_wrapper").show("normal");
			}
		});
		
		// 登出平台
		$("#logoutPortal").bind("click", function(){
			Flywet.CookieUtils.clear(Flywet.Portal.COOKIE_KEYS);
			window.location = "portal";
		});
		
		
		// --------------主页面区域操作-------------------
		$("#btn_next").bind("click", function(){
			Flywet.Portal.nextBackground();
		});
		$("#btn_previous").bind("click", function(){
			Flywet.Portal.previousBackground();
		});
		$("#btn_full").bind("click", function(){
			Flywet.Portal.fullSceen();
		});
		$("#btn_login_close").bind("click", function(){
			$("#fly_login_wrapper").hide("fast");
		});
		
		// 调整尺寸
		Flywet.Portal.resize();
		
		// 将背景置底
		$("#fly_portal_bg").removeClass("fly_portal_cover");
	},
	
	updateMenuDialog: function(id, action, targetId, param){
		Flywet.ab({
			type : "get",
			url : "rest/portalet/menu/"+id+"/update",
			params : {
				action : action
				,targetId : targetId
				,param : param
			}
		});
	},
	
	openMenuDialog: function(id,disabled){
		var menu = Flywet.Portal.MENU_VAR.getMenu(id);
		
		var optMenu = {};
		for(var i=0; i<menu.extAttrs.length;i++){
			optMenu[menu.extAttrs[i].name] = menu.extAttrs[i].value;
		}
		if(optMenu.cls){
			if(disabled){
				Flywet.dialog.warning("您未登陆，或者当前登陆用户不具有【"+menu.desc+"】的执行权限。");
				return;
			}
			
			var dialogId = "dialog_" + menu.id;
			
			var opt = $.extend({},{
				id : dialogId,
				url : "rest/portalet/menu/"+menu.id,
				width : 700,
				height : 400,
				header : menu.desc,
				autoOpen : true,
//				autoMaximized : true,
				showHeader : true,
				modal : true,
				closable : true,
				maximizable : false
			}, optMenu);
			
			if(opt.btn){
				var btns = [];
				var btnsSetting = Flywet.parseJSON(opt.btn);
				for(var i=0;i<btnsSetting.length;i++){
					if(btnsSetting[i].type && btnsSetting[i].type == "cancel"){
						btns.push({
							componentType : "fly:PushButton",
							type : "button",
							label : "取消",
							title : "取消",
							events : {
								"click" : "hide"
							}
						});
					}else{
						var btn = {
							componentType : "fly:PushButton",
							type : "button",
							label : btnsSetting[i].label,
							title : btnsSetting[i].title,
							action : btnsSetting[i].action,
							func : btnsSetting[i].func,
							url : btnsSetting[i].url || ("rest/portalet/action/"+btnsSetting[i].action),
							urlType : btnsSetting[i].urlType || "GET",
							formId : btnsSetting[i].formId || "portal_menu_form",
							events: {
								click:function(event,params){
									if(params.func){
										eval(params.func+"(dialogId,event,params);");
									}else{
										Flywet.ab({
											type : params.urlType,
											url : params.url,
											source : params.formId,
											onsuccess:function(data, status, xhr) {
												if (data.state == 0) {
													window[dialogId + "_var"].hide();
												}
											}
										});
									}
								}
							}
						};
						btns.push(btn);
					}
				}
				opt.btn = undefined;
				opt.footerButtons = btns;
			}
			
			Flywet.cw("Dialog", dialogId+"_var", opt);
		}
	},
	
	initPage: function(){
		
		Flywet.env();
		
		// 1.替换标识文字
		Flywet.ab({
			type: "get",
			url: "rest/identification/messages",
			onsuccess: function(data, status, xhr){
				Flywet.Portal.messages = data;
				Flywet.Portal.changeWebText();
			}
		});
		
		// 2.加载资源库名称
		Flywet.ab({
			type: "get",
			url: "rest/identification/repositoryNames",
			onsuccess: function(data, status, xhr){
				for(var i=0;i<data.length;i++){
					$("#repository").append("<option value=\""+data[i]+"\" >"+data[i]+"</option>");
				}
				if(data.length > 1){
					$("#repositoryDiv").show();
				}
				return true;
			}
		});
		
		// 3.加载滚动内容
		Flywet.ab({
			type: "get",
			url: "rest/identification/slides",
			onsuccess: function(data, status, xhr){
				if ($.browser.msie == true && ($.browser.version == '7.0' || $.browser.version == '8.0')) {
					Flywet.Portal.section.transitionEffect = 'none';
			    } else {
			    	Flywet.Portal.section.transitionEffect = 'scrollLeft';
			    }
				
				Flywet.Portal.section.slideShow = $("#section");
				
				// 加载滑动内容
				Flywet.Portal.section.slideShow.empty();
				Flywet.render(Flywet.Portal.section.slideShow, data.dom, data.script);
				
				Flywet.Portal.section.slideShow.after("<div id='section-paging' class='section-paging'>");
				Flywet.Portal.section.slideShow.cycle({
			        fx          	: Flywet.Portal.section.transitionEffect,
			        pager       	: "#section-paging",
			        timeout     	: 0,
			        cleartype   	: true,
			        cleartypeNoBg	: true,
			        onPagerEvent	: Flywet.Portal.section.resetSlideshowCounter
			    });
				Flywet.Portal.section.startSlideshow();
			    
				Flywet.Portal.section.slideShow.mouseenter(function(){ Flywet.Portal.section.pauseSlideshow(); });
				Flywet.Portal.section.slideShow.mouseleave(function(){ Flywet.Portal.section.startSlideshow(); });
			
				// 隐藏登录框
				$("#fly_login_wrapper").hide();
				
			}
		});
		
		// 4.加载portal的菜单
		Flywet.ab({
			type: "get",
			url: "rest/portalet/menus",
			onsuccess: function(data, status, xhr){
				if(data){
					Flywet.Portal.MENU_VAR = new Flywet.Portal.menu({
						id : "fly_portal_menus_ul",
						targetId : "fly_portal_menus",
						data : data
					});
				}
				
				Flywet.cw("Metro", "portal_content_var", {
					id : "fly_portal_content",
					rowNum : 4,
					columnNum : 7,
//					backgroundImgType : "tile",
					metros : [{
						id : "test1",
						backgroundCls : "roxo",
						colspan : 3,
						iconImg : "resources/images/desktop/bi.job.png",
						text : "带文字框",
						textBackgroundCls : "info"
					},{
						id : "test2",
						backgroundImg : "resources/images/metro/test.jpg",
						iconImg : "resources/images/desktop/bi.job.png",
						colspan : 2,
						text : "带文字框和背景图片",
						textBackgroundCls : "azul"
					},{
						backgroundCls : "roxo",
//						text : "roxo背景",
						iconImg : "resources/images/desktop/bi.job.png"
					},{
						backgroundCls : "verde",
						text : "verde背景",
						iconImg : "resources/images/desktop/bi.job.png"
					},{
						backgroundCls : "azul",
						text : "azul背景",
						colspan : 2,
						rowspan : 2,
						iconImg : "resources/images/desktop/bi.job.png"
					},{
						backgroundCls : "vermelho",
						text : "vermelho背景",
						iconImg : "resources/images/desktop/bi.job.png"
					}]
				});
				
				Flywet.Portal.initPageComplete();
			}
		});
	}
};

Flywet.Portal.menu = function(cfg){
	this.cfg = $.extend( {
		columnNum : 2,
		idMenuMap : {}
	}, cfg);
	
	this.id = cfg.id;
	if(!this.id){
		this.id = "menu_" + (Flywet.windex++);
	}
	
	this.targetId = cfg.targetId;
	if(!this.targetId){
		this.targetJq = $("body");
	}else{
		this.targetJq = $(Flywet.escapeClientId(this.targetId));
	}

	this.init();
};

Flywet.Portal.menu.prototype.getMenu=function(id){
	return this.cfg.idMenuMap["id_"+id];
}

Flywet.Portal.menu.prototype.init=function(){
	this.jq = $("<ul id='"+this.id+"'></ul>");
	if(this.cfg.data){
		// 第一级菜单
		for(var i=0;i<this.cfg.data.length;i++){
			this.jq.append(initFirstLevel(this.cfg.data[i], this.cfg));
		}
	}
	this.jq.appendTo(this.targetJq);
	
	initEvent(this.jq);
	
	function initEvent(jq){
		jq.find("li").each(function(){
			var li = $(this),
				subDiv = li.find("div.sub-menu");
			var time = null;
			
			li.bind("mouseover", function(){
				if (time){
					clearTimeout(time);
					time = null;
				}
				
				if(li.attr("initDivPos") != "Y"){
					var b = li.find("b"),
					liPos = Flywet.getElementDimensions(li);
				
					var liMidWidth = liPos.offsetLeft+(liPos.innerWidth/2);
					
					var secondWidth = 110, thirdWidth = 230, subDivWidth = secondWidth;
					
					// 如果没有第三级
					if(subDiv.find("dd").size() > 0){
						subDivWidth = secondWidth + thirdWidth;
					}
					
					if(liMidWidth<subDivWidth/2){
						b.css("left", (liMidWidth-6) +"px");
						subDiv.css("left", "0");
					}else{
						var bLeft = subDivWidth/2 + 12;
						b.css("left", bLeft +"px");
						subDiv.css("left", (liMidWidth-bLeft-6) +"px");
					}
					subDiv.width(subDivWidth);
					subDiv.find("dt").width(secondWidth);
					subDiv.find("dd").width(thirdWidth);
					li.attr("initDivPos", "Y");
				}
				
				li.addClass("highlight");
				subDiv.show();
			}).bind("mouseout", function(){
				if (!time){
					time = setTimeout(function(){
						li.removeClass("highlight");
						subDiv.hide();
					}, 100);
				}
			});
			
			subDiv.bind('mouseenter', function(){
				if (time){
					clearTimeout(time);
					time = null;
				}
			}).bind('mouseleave', function(){
				if (!time){
					time = setTimeout(function(){
						li.removeClass("highlight");
						subDiv.hide();
					}, 100);
				}
			});
		});
		
		jq.find("dl").each(function(){
			var dl = $(this);
			dl.bind("mouseover", function(){
				dl.addClass("highlight");
			}).bind("mouseout", function(){
				dl.removeClass("highlight");
			});
		});
	}
	
	function initFirstLevel(menu, cfg){
		var li = $("<li></li>");
		var lia = $("<a class='first-level-menu' href='javascript:void(0);' onclick='Flywet.Portal.openMenuDialog("+menu.id+","+menu.disabled+")'>"+menu.desc+"</a>");
		if(menu.disabled){
			lia.addClass("menu-disabled");
			lia.attr("title","该菜单项没有被授权。");
		}
		li.append(lia);
		cfg.idMenuMap["id_"+menu.id] = menu;
		
		// 第二级菜单
		if(menu.children && menu.children.length>0){
			var div = $("<div class='sub-menu'></div>");
			div.append("<b></b>");
			
			for(var i=0;i<menu.children.length;i++){
				div.append(initSecondLevel(menu.children[i], cfg));
			}
			
			li.append(div);
		}
		
		li.append("<div class='clear'></div>");
		return li;
	}
	
	function initSecondLevel(menu, cfg){
		var dl = $("<dl></dl>"),
			dt = $("<dt></dt>"),
			dta = $("<a href='javascript:void(0);' onclick='Flywet.Portal.openMenuDialog("+menu.id+","+menu.disabled+")'>"+menu.desc+"</a>");
		if(menu.disabled){
			dta.addClass("menu-disabled");
			dta.attr("title","该菜单项没有被授权。");
		}
		dt.append(dta);
		dl.append(dt);
		cfg.idMenuMap["id_"+menu.id] = menu;
		
		// 第三级菜单
		if(menu.children && menu.children.length>0){
			var dd = $("<dd></dd>");
			var i=0;
			for(;i<menu.children.length;i++){
				if(i!=0){
					if(i%cfg.columnNum==0){
						dd.append("<br/>");
					}else{
						dd.append("<i></i>");
					}
				}
				var subMenu = menu.children[i],
					dda = $("<a href='javascript:void(0);' onclick='Flywet.Portal.openMenuDialog("+subMenu.id+","+subMenu.disabled+")'>"+subMenu.desc+"</a>");
				if(subMenu.disabled){
					dda.addClass("menu-disabled");
					dda.attr("title","该菜单项没有被授权。");
				}
				dd.append(dda);
				cfg.idMenuMap["id_"+subMenu.id] = subMenu;
			}
			dl.append(dd);
		}
			
		return dl;
	}
	
};