/**
 * 搜索框
 * yanghyc
 */
Plywet.widget.Search=function(cfg){
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.parent = cfg.parent;
	this.defaultText = (cfg.defaultText)?cfg.defaultText:"Search";

	this.init();
};
Plywet.extend(Plywet.widget.Search, Plywet.widget.BaseWidget);
Plywet.widget.Search.prototype={
	init:function(){
		if(this.jq.length==0){
			this._creatContainer();
			if(this.parent) {
				this.parent.append(this.jq);
			}else{
				$(document.body).append(this.jq);
			}
		};
		return this.jq;
	},
	_creatContainer:function(){
		var _self=this;
		this.jq = $("<div></div>");
		var styleClass = (this.cfg.styleClass)?"ui-search-con "+this.cfg.styleClass:"ui-search-con";
		this.jq.attr("id",this.id);
		this.jq.addClass(styleClass);
		this.jqInput = $("<input/>");
		this.jqInput.addClass('ui-search-input-normal');
		this.jqInput.val(this.defaultText);
		this.toggle(this.jqInput);
		this.jq.append(this.jqInput);
	},
	toggle:function(input){
		var _self=this;
		input.focus(function(){
			input.val("");			
		});
		input.blur(function(){
			if(input.val()=="")
				input.val(_self.defaultText);
		});
		$(document).keydown(function(e){
			if(e.keyCode==13&&document.activeElement==input.get(0)){
				if(_self.cfg.onsearch){
					_self.cfg.onsearch.call(this,input.val());
				}
			}
		});
	}
}
