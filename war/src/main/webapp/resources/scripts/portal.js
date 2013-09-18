Flywet.Portal = {
	PIC_TOTILE_NUM : 10,
	PIC_NUM : 0,
	MENU_VAR : null,
	MAX_SCEEN : false,
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
		$("#errors").html("浏览器阻止弹出系统首页，请将该网站加入授信站点，并且允许浏览器弹出该网址窗口，然后再次登录。");
		$("#loginBtn").blur()
			.removeClass("ui-login-button-disabled")
			.removeClass("ui-login-button-hover")
			.bind("click", function(){
				Flywet.Portal.loginAction();
			});
	},
	
	loginAction : function(){
		Flywet.ab({
			formId: "login",
			formAction: "rest/identification",
			beforeSend : function(){
				$("#loginBtn").addClass("ui-login-button-disabled")
				.unbind("click");
				$("#errors").html("正在登陆...");
			},
			onsuccess: function(data, status, xhr){
				if(data.state == 0){
					var cookieJson = Flywet.parseJSON(data.data);
					for(var p in cookieJson){
						Flywet.CookieUtils.write(p,cookieJson[p]);
					}
					// 判断是否是子页面
					//window.location = "editor";
					
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
						
					window["editorPageHandle"].focus();
				}else{
					var msg = "";
					if(data.messages){
						for(var i=0;i<data.messages.length;i++){
							msg = msg + "&#9830;" + data.messages[i] + "<br/>";
						}
					}
					$("#errors").html(msg);
					$("#loginBtn").blur()
						.removeClass("ui-login-button-disabled")
						.removeClass("ui-login-button-hover");
				}
				return true;
			}
		});
	},
	
	resize: function(){
		var win = Flywet.getWindowScroll();
		var h = Math.max(0,(win.height-580)),
			w = Math.max(0,(win.width-1100));
		$("#fly_login_wrapper").css({
			"margin-top":h+"px",
			"margin-left":w+"px"
		});
		Flywet.Portal.PIC_NUM = Math.floor(Math.random()*(Flywet.Portal.PIC_TOTILE_NUM-0.001)+1);
		$(Flywet.escapeClientId("fly_portal_bg_img"))
			.attr("src", "resources/images/pics/wallpaper"+Flywet.Portal.PIC_NUM+".jpg")
			.width(win.width+20).height(win.height+20);
		
		$(Flywet.escapeClientId("fly_portal_header")).width(win.width);
		$(Flywet.escapeClientId("fly_portal_footer")).width(win.width).css("top", (win.height-95)+"px");
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
			$(Flywet.escapeClientId("fly_portal_footer")).animate({top:(win.height-95)+"px"},500);
			
			$(Flywet.escapeClientId("btn_full")).addClass("fly-full-sceen-out").removeClass("fly-full-sceen-in").attr("title", "全屏");
			Flywet.Portal.MAX_SCEEN = false;
		}else{
			$(Flywet.escapeClientId("fly_portal_header")).animate({height:"toggle"},500);
			$(Flywet.escapeClientId("fly_portal_footer")).animate({top:(win.height-35)+"px"},500);
			
			$(Flywet.escapeClientId("btn_full")).addClass("fly-full-sceen-in").removeClass("fly-full-sceen-out").attr("title", "恢复");
			Flywet.Portal.MAX_SCEEN = true;
		}
	},
	
	initPageComplete : function(){
		// 登录按钮
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
		
		// 设置按钮
		$("#btn_login").bind("click", function(){
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
		
		// 对于设置按钮的事件
		var settingDiv = $("#fly_portal_sub_menu_setting");
		var time = null;
		$("#btn_setting").bind("mouseover", function(){
			if (time){
				clearTimeout(time);
				time = null;
			}
			settingDiv.show();
		}).bind("mouseout", function(){
			time = setTimeout(function(){
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
				settingDiv.hide();
			}, 500);
		});
		
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
		
		$(document).keydown(function(e){
			if(e.keyCode==13){
				Flywet.Portal.loginAction();
			}
		});
		
		// 调整尺寸
		Flywet.Portal.resize();
		
		// 将背景置底
		$("#fly_portal_bg").removeClass("fly_portal_cover");
	},
	
	openMenuDialog: function(id){
		var menu = Flywet.Portal.MENU_VAR.getMenu(id);
		var optMenu = {};
		for(var i=0; i<menu.extAttrs.length;i++){
			optMenu[menu.extAttrs[i].name] = menu.extAttrs[i].value;
		}
		if(optMenu.cls){
			var dialogId = "dialog_" + menu.id;
			
			var opt = $.extend({},{
				id : dialogId,
				url : "rest/identification/protalmenu/"+menu.id,
				width : 700,
				height : 400,
				header : menu.desc,
				autoOpen : true,
				autoMaximized : true,
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
							url : btnsSetting[i].url,
							events: {
								click:function(event,params){
									Flywet.ab({
										type : "POST",
										url : params.url,
										source:"sys_tools_item_form",
										onsuccess:function(data, status, xhr) {
											if (data.state == 0) {
												window[dialogId + "_var"].hide();
											}
										}
									});
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
			url: "rest/identification/protalmenus",
			onsuccess: function(data, status, xhr){
				if(data){
					Flywet.Portal.MENU_VAR = new Flywet.Portal.menu({
						id : "fly_portal_menus_ul",
						targetId : "fly_portal_menus",
						data : data
					});
				}
				
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
				
				subDiv.show();
			}).bind("mouseout", function(){
				if (!time){
					time = setTimeout(function(){
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
						subDiv.hide();
					}, 100);
				}
			});
		});
	}
	
	function initFirstLevel(menu, cfg){
		var li = $("<li></li>");
		li.append("<a href='javascript:void(0);' onclick='Flywet.Portal.openMenuDialog("+menu.id+")'>"+menu.desc+"</a>");
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
		var dl = $("<dl></dl>");
		dl.append("<dt><a href='javascript:void(0);' onclick='Flywet.Portal.openMenuDialog("+menu.id+")'>"+menu.desc+"</a></dt>");
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
				var subMenu = menu.children[i];
				dd.append("<a href='javascript:void(0);' onclick='Flywet.Portal.openMenuDialog("+subMenu.id+")'>"+subMenu.desc+"</a>");
				cfg.idMenuMap["id_"+subMenu.id] = subMenu;
			}
			dl.append(dd);
		}
			
		return dl;
	}
	
};