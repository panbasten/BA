Flywet.Portal = {
	PIC_NUM : 10,

	resize : function() {
		var win = Flywet.getWindowScroll();

		// 更换图片
		var num = Math.floor(Math.random() * (Flywet.Portal.PIC_NUM-0.001) + 1);
		$(Flywet.escapeClientId("fly_portal_img")).attr("src",
				"resources/images/pics/wallpaper" + num + ".jpg").width(
				win.width + 20).height(win.height + 20);
	},

	initPage : function() {
		// 初始化浏览器
		Flywet.env();
		
		// 初始化按钮
		$(Flywet.escapeClientId("btn_login")).bind("click", function(){
			window.location = "login";
		});
		
		// 调整尺寸
		Flywet.Portal.resize();
	}

};