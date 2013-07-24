/**
 * @author yanghyc
 * @date 2012-10-10
 */
Flywet.widget.Popup=function(cfg){
	this.cfg = cfg || {};
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.target = $(Flywet.escapeClientId(this.cfg.targetId));
	this.init();
};

Flywet.extend(Flywet.widget.Popup, Flywet.widget.BaseWidget);

Flywet.widget.Popup.prototype={
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
		if(this.cfg.width){
			this.jq.width(this.cfg.width);
		}
		if(this.cfg.height){
			this.jq.height(this.cfg.height);
		}
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
	},
	render:function(dom, script){
		Flywet.render(this.jq, dom, script);
	}
};
