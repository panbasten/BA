/**
 * 加载图片
 */
var preloaded = new Array();
function preload_images() {
    for (var i = 0; i < arguments.length; i++) {
        preloaded[i] = document.createElement('img');
        preloaded[i].setAttribute('id', arguments[i]);
        preloaded[i].setAttribute('src', "resources/images/pics/"+arguments[i]+".jpg");
    }
}
preload_images(
	// wallpaper
	'wallpaper1'
	,'wallpaper2'
	,'wallpaper3'
	,'wallpaper4'
	,'wallpaper5'
	,'wallpaper6'
	,'wallpaper7'
	,'wallpaper8'
	,'wallpaper9'
	,'wallpaper10'
);
