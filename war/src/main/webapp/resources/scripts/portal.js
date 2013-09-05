Flywet.Portal = {

	resize : function() {
		var win = Flywet.getWindowScroll();

		// 更换图片
		var num = Math.floor(Math.random() * 9.999 + 1);
		$(Flywet.escapeClientId("fly_portal_img")).attr("src",
				"resources/images/pics/bing" + num + ".jpg").width(
				win.width + 20).height(win.height + 20);
	},

	initPage : function() {
		// 初始化浏览器
		Flywet.env();
		
		
		
		// 调整尺寸
		Flywet.Portal.resize();
	}

};