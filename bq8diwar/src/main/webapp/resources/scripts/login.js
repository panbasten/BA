YonYou.Login = {
	section: {
		slideShow : null,
		slideShowId : null,
		slideShowRunning : false,
		transitionEffect : null,
		resetSlideshowCounter: function(){
			YonYou.Login.section.pauseSlideshow();
			YonYou.Login.section.startSlideshow();
		},
		startSlideshow: function(){
			if (YonYou.Login.section.slideShowRunning) return true;
			YonYou.Login.section.slideShowRunning = true;
		    YonYou.Login.section.slideShowId = setInterval(function(){
		    	YonYou.Login.section.slideShow.cycle('next');
		    },7500);
		},
		pauseSlideshow: function(){
			YonYou.Login.section.slideShowRunning = false;
		    clearInterval(YonYou.Login.section.slideShowId);
		}
	},
	
	loginAction : function(){
		YonYou.ab({
			formId: "login",
			formAction: "rest/identification",
			onsuccess: function(data, status, xhr){
				if(data.state == 0){
					var cookieJson = YonYou.parseJSON(data.data);
					for(var p in cookieJson){
						YonYou.setCookie(p,cookieJson[p]);
					}
					window.location = "editor";
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
		var win = YonYou.getWindowScroll();
		var h = Math.max(0,(win.height-580));
		$("#document").css("margin-top",h+"px");
	},
	
	initPage: function(){
		// 1.加载资源库名称
		YonYou.ab({
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
			YonYou.Login.section.transitionEffect = 'none';
	    } else {
	    	YonYou.Login.section.transitionEffect = 'scrollLeft';
	    }
		
		YonYou.Login.section.slideShow = $("#section");
		YonYou.Login.section.slideShow.after("<div id='section-paging' class='section-paging'>");
		YonYou.Login.section.slideShow.cycle({
	        fx          	: YonYou.Login.section.transitionEffect,
	        pager       	: "#section-paging",
	        timeout     	: 0,
	        cleartype   	: true,
	        cleartypeNoBg	: true,
	        onPagerEvent	: YonYou.Login.section.resetSlideshowCounter
	    });
		YonYou.Login.section.startSlideshow();
	    
		YonYou.Login.section.slideShow.mouseenter(function(){ YonYou.Login.section.pauseSlideshow(); });
		YonYou.Login.section.slideShow.mouseleave(function(){ YonYou.Login.section.startSlideshow(); });
		
		
		// 登录按钮
		$("#loginBtn").bind("click", function(){
			YonYou.Login.loginAction();
		});
		
		$(document).keydown(function(e){
			if(e.keyCode==13){
				YonYou.Login.loginAction();
			}
		});
		
		// 调整尺寸
		YonYou.Login.resize();
	}
};