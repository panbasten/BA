Plywet.Login = {
	messages : null,
	section: {
		slideShow : null,
		slideShowId : null,
		slideShowRunning : false,
		transitionEffect : null,
		resetSlideshowCounter: function(){
			Plywet.Login.section.pauseSlideshow();
			Plywet.Login.section.startSlideshow();
		},
		startSlideshow: function(){
			if (Plywet.Login.section.slideShowRunning) return true;
			Plywet.Login.section.slideShowRunning = true;
		    Plywet.Login.section.slideShowId = setInterval(function(){
		    	Plywet.Login.section.slideShow.cycle('next');
		    },7500);
		},
		pauseSlideshow: function(){
			Plywet.Login.section.slideShowRunning = false;
		    clearInterval(Plywet.Login.section.slideShowId);
		}
	},
	
	pageBlocked : function() {
		$("#errors").html("浏览器阻止弹出系统首页，请将该网站加入授信站点，并且允许浏览器弹出该网址窗口，然后再次登录。");
		$("#loginBtn").blur()
			.removeClass("ui-login-button-disabled")
			.removeClass("ui-login-button-hover")
			.bind("click", function(){
				Plywet.Login.loginAction();
			});
	},
	
	loginAction : function(){
		Plywet.ab({
			formId: "login",
			formAction: "rest/identification",
			beforeSend : function(){
				$("#loginBtn").addClass("ui-login-button-disabled")
				.unbind("click");
				$("#errors").html("正在登陆...");
			},
			onsuccess: function(data, status, xhr){
				if(data.state == 0){
					var cookieJson = Plywet.parseJSON(data.data);
					for(var p in cookieJson){
						Plywet.CookieUtils.write(p,cookieJson[p]);
					}
					// 判断是否是子页面
					//window.location = "editor";
					
					if (Plywet.browserDetect.msie){
						window["editorPageHandle"] = window.open("editor","","modal=1,dialog=1,fullscreen=1,toolbar=0,menubar=0,location=0,directries=0,location=0,scrollbars=0,status=0,resizable=0");
						var num = 0;
						function checkWebPageForIE(){
							if(window["editorPageHandle"]){
								clearInterval(interval);
								if (Plywet.browserDetect.isIE6) {  
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
									Plywet.Login.pageBlocked();
								}
							}
						}
						var interval = setInterval(checkWebPageForIE, 500);
					
					}else if (Plywet.browserDetect.webkit){
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
									Plywet.Login.pageBlocked();
								}
							}
						}
						var interval = setInterval(checkWebPageForWebkit, 500);
						
					}else if (Plywet.browserDetect.mozilla){
						try{
							window["editorPageHandle"] = window.open("editor","","modal=1,dialog=1,toolbar=0,menubar=0,location=0,personalbar=0,location=0,scrollbars=0,status=0,resizable=0");
							window["editorPageHandle"].moveTo(0, 0);
							window["editorPageHandle"].resizeTo(window.screen.availWidth, window.screen.availHeight);
					
							window.location.href = 'about:blank';
						}
						catch(e){
							Plywet.Login.pageBlocked();
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
		var win = Plywet.getWindowScroll();
		var h = Math.max(0,(win.height-580)),
			w = Math.max(0,(win.width-1160));
		$("#document").css({
			"margin-top":h+"px",
			"margin-left":w+"px"
		});
		var num = Math.floor(Math.random()*1.999+1);
		$(Plywet.escapeClientId("document-img"))
			.attr("src", "resources/images/pics/login"+num+".jpg")
			.width(win.width+20).height(win.height+20);
	},
	
	changeWebText : function(){
		document.title=Plywet.Login.messages["msg_page_title"];
		$("#companyName").html(Plywet.Login.messages["msg_page_company_name"]);
		$("#companyCopyright").html(Plywet.Login.messages["msg_page_company_copyright"]);
		$("#companyOthers").html(Plywet.Login.messages["msg_page_company_others"]);
	},
	
	initPage: function(){
		
		Plywet.env();
		
		// 1.替换标识文字
		Plywet.ab({
			type: "get",
			url: "rest/identification/messages",
			onsuccess: function(data, status, xhr){
				Plywet.Login.messages = data;
				Plywet.Login.changeWebText();
			}
		});
		
		
		// 2.加载资源库名称
		Plywet.ab({
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
		Plywet.ab({
			type: "get",
			url: "rest/identification/slides",
			onsuccess: function(data, status, xhr){
				if ($.browser.msie == true && ($.browser.version == '7.0' || $.browser.version == '8.0')) {
					Plywet.Login.section.transitionEffect = 'none';
			    } else {
			    	Plywet.Login.section.transitionEffect = 'scrollLeft';
			    }
				
				Plywet.Login.section.slideShow = $("#section");
				
				// 加载滑动内容
				Plywet.Login.section.slideShow.empty();
				Plywet.render(Plywet.Login.section.slideShow, data.dom, data.script);
				
				Plywet.Login.section.slideShow.after("<div id='section-paging' class='section-paging'>");
				Plywet.Login.section.slideShow.cycle({
			        fx          	: Plywet.Login.section.transitionEffect,
			        pager       	: "#section-paging",
			        timeout     	: 0,
			        cleartype   	: true,
			        cleartypeNoBg	: true,
			        onPagerEvent	: Plywet.Login.section.resetSlideshowCounter
			    });
				Plywet.Login.section.startSlideshow();
			    
				Plywet.Login.section.slideShow.mouseenter(function(){ Plywet.Login.section.pauseSlideshow(); });
				Plywet.Login.section.slideShow.mouseleave(function(){ Plywet.Login.section.startSlideshow(); });
			}
		});
		
		// 4.登录按钮
		$("#loginBtn").bind("click", function(){
			Plywet.Login.loginAction();
		}).live("mouseover", function(){
			$(this).addClass('ui-login-button-hover');
		}).live("mouseout", function(){
			$(this).removeClass('ui-login-button-hover');
		}).live("focus", function(){
			$(this).addClass('ui-login-button-hover');
		}).live("blur", function(){
			$(this).removeClass('ui-login-button-hover');
		});
		
		$(document).keydown(function(e){
			if(e.keyCode==13){
				Plywet.Login.loginAction();
			}
		});
		
		// 5.调整尺寸
		Plywet.Login.resize();
	}
};