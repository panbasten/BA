/**
 * 加载图片
 */
var preloaded = new Array();
function preload_images() {
    for (var i = 0; i < arguments.length; i++) {
        preloaded[i] = document.createElement('img');
        preloaded[i].setAttribute('id', arguments[i]);
        preloaded[i].setAttribute('src', "resources/images/entities/"+arguments[i]+".png");
    }
}
preload_images(
	// Input
	'TFI'
	,'XLI'
	,'ACI'
	,'XBI'
	,'JSI'
	,'XIN'
	,'TFI'
	,'GEN'
	,'RVA'
	,'TIP'
	// Output
	,'XLO'
	,'ACO'
	,'JSO'
	,'XOU'
	,'TOP'
	,'TFO'
	,'sScript'
	,'SCR_mod'
	,'SQL'
	,'RGE'
	,'RFO'
	// Lookup
	,'DLU'
	,'PRC'
	,'WSL'
	,'REST'
	// Transform
	,'CST'
	,'SEQ'
	,'CSM'
	,'add_xml'
	,'SEL'
	,'XJN'
	,'WSL'
	// Bulk
	,'OBL'
	// Cryptography
	,'SCE'
	,'SCG'
	,'WSL'
	,'GPGEncrypt'
	,'GPGDecrypt'
	// Flow
	,'DUM'
	,'APP'
	,'ABR'
	,'FLT'
	,'JLT'
	,'BLK'
	,'WFS'
	,'MAP'
	,'JOB'
	,'SWC'
);
