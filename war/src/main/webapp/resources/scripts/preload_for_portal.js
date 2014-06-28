/**
 * 加载图片
 */
var preloaded = new Array(),
	preloadedKeys = new Array();

function preload_images() {
    for (var i = 0; i < arguments.length; i++) {
        var img = document.createElement('img');
        img.setAttribute('id', "id_"+preloaded.length);
        img.setAttribute('src', "resources/images/"+arguments[i]);
        preloaded.push(img);
        preloadedKeys.push(arguments[i]);
    }
}
function preload_images_with_check(){
	for (var i = 0; i < arguments.length; i++) {
		if(preload_images_match(arguments[i])){
			continue;
		}
        var img = document.createElement('img');
        img.setAttribute('id', "id_"+preloaded.length);
        img.setAttribute('src', "resources/images/"+arguments[i]);
        preloaded.push(img);
        preloadedKeys.push(arguments[i]);
    }
}
function preload_images_match(path){
	for(var i=0; i<preloadedKeys.length; i++){
		if(preloadedKeys[i] && preloadedKeys[i] == path){
			return true;
		}
	}
	return false;
}
/**
 * 加载背景图，在内网环境下可以使用
 * eg: preload_background_images(Flywet.Portal.PIC_TOTILE_NUM);
 * @param num
 * @return
 */
function preload_background_images(num) {
	for (var i=1;i<=num;i++) {
		preload_images("pics/wallpaper"+i+".jpg");
	}
}
preload_images(
	// portal
	'portal/loading.gif'
	,'portal/portal.png'
	// login
	,'login/bg.png'
	,'login/button.bg.png'
	,'login/center.bg.png'
	,'login/div.bg.png'
	,'login/hdr.bg.gif'
	,'login/hdr.bg.png'
	,'login/login.setting.png'
	,'login/login.star.png'
);

