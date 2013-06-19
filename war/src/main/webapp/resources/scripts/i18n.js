var $messages = {};

function preload_messages() {
	Plywet.ab({
		type: "get",
		url: "rest/identification/messages",
		onsuccess: function(data, status, xhr){
		}
	});
}

preload_messages();