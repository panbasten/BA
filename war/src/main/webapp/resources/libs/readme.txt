组件通用调用方法：

resize(target, size)


var resizeSub = function(target){
	target.find("div.ui-panel:visible,div.tabs-container:visible,div.layout:visible")
		.each(function() {
			$(this).triggerHandler("_resize", [ true ]);
		});
};


$.fn.datagrid.methods


resize : function(jq, size) {
	return jq.each(function() {
		_resize(this, size);
	});
},