/**
 * @author yanghyc
 * @date 2012-10-10
 */
Plywet.widget.Popup=function(cfg){
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.target = $(Plywet.escapeClientId(this.cfg.targetId));
	this.init();
};

Plywet.extend(Plywet.widget.Popup, Plywet.widget.BaseWidget);

Plywet.widget.Popup.prototype={
	init:function(){
		var _self=this;
		if(this.jq.length==0){
			this._createContainer();
		}
		this.setPosiotn();
		
		this.target.click(function(){
			if(_self.isShow()){
				_self.hide();
			}else{
				_self.show();
			}
		});
		
		return this.jq;
	},
	_createContainer:function(){
		var _self=this;
		this.jq=$("<div></div>");
		this.jq.addClass('ui-popup-con');
		this.jq.blur(function(){
			_self.hide();
		});
		if(this.parent){
			this.parent.append(this.jq);
		}else{
			$(document.body).append(this.jq);
		}
	},	
	setPosiotn:function(){
		var offset=this.target.offset();
		this.jq.css({
			'left':offset.left-this.jq.outerWidth()+this.target.outerWidth(),
			'top':offset.top-this.jq.outerHeight()-3
		});	
		if(offset.top<this.jq.outerHeight()){
			this.jq.css({
				'top':offset.top+this.target.outerHeight()+3
			});	
		}
		if(offset.left<(this.jq.outerWidth()-this.target.outerWidth())){
			this.jq.css({
				'left':offset.left
			});	
		}
	},
	isShow:function(){
		return this.jq.is(":visible");
	},
	hide:function(){
		this.jq.hide("fast");
	},
	show:function(){
		var _self=this;
		this.jq.show("fast",function(){
			_self.jq.attr("tabindex",-1).focus();
			_self.setPosiotn();
		});
	},
	disctroy:function(){
		this.jq.remove();
	}
};
