Plywet.Login = {
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
	
	closeWebPage : function() {  
        if (Plywet.browserDetect.msie) {  
            if (Plywet.browserDetect.isIE6) {  
                window.opener = null; 
                window.close();  
            }  
            else {  
                window.open('', '_top'); 
                window.top.close();  
            }  
        }  
        else if (Plywet.browserDetect.mozilla) {  
            window.location.href = 'about:blank ';  
            //window.history.go(-2);  
        }  
        else {  
            window.opener = null;   
            window.open('', '_self', '');  
            window.close();  
        }  
    },
	
	loginAction : function(){
		Plywet.ab({
			formId: "login",
			formAction: "rest/identification",
			onsuccess: function(data, status, xhr){
				if(data.state == 0){
					var cookieJson = Plywet.parseJSON(data.data);
					for(var p in cookieJson){
						Plywet.CookieUtils.write(p,cookieJson[p]);
					}
					//window.location = "editor";
					window.open("editor","","modal=1,dialog=1,fullscreen=1,toolbar=0,menubar=0,directries=0,location=0,scrollbars=0,status=0,resizable=0");
					setTimeout("Plywet.Login.closeWebPage();",500);
				}else{
					var msg = "";
					if(data.messages){
						for(var i=0;i<data.messages.length;i++){
							msg = msg + data.messages[i] + "<br/>";
						}
					}
					$("#errors").html(msg);
				}
				return true;
			}
		});
	},
	
	resize: function(){
		var win = Plywet.getWindowScroll();
		var h = Math.max(0,(win.height-580));
		$("#document").css("margin-top",h+"px");
	},
	
	initPage: function(){
		// 1.加载资源库名称
		Plywet.ab({
			type: "get",
			url: "rest/identification/repositoryNames",
			onsuccess: function(data, status, xhr){
				for(var i=0;i<data.length;i++){
					$("#repository").append("<option value=\""+data[i]+"\" >"+data[i]+"</option>");
				}
				return true;
			}
		});
		
		// 2.加载内容
		if ($.browser.msie == true && ($.browser.version == '7.0' || $.browser.version == '8.0')) {
			Plywet.Login.section.transitionEffect = 'none';
	    } else {
	    	Plywet.Login.section.transitionEffect = 'scrollLeft';
	    }
		
		Plywet.Login.section.slideShow = $("#section");
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
		
		
		// 登录按钮
		$("#loginBtn").bind("click", function(){
			Plywet.Login.loginAction();
		});
		
		$(document).keydown(function(e){
			if(e.keyCode==13){
				Plywet.Login.loginAction();
			}
		});
		
		// 调整尺寸
		Plywet.Login.resize();
	}
};