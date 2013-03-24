Plywet.widget.Scrollbar = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Plywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	
	this.$container = this.jq;
	this.$tabGroup = $('#'+cfg.tabGroup);
	this.stepSize = cfg.step;
	this.type = cfg.scrollType;
	
	this._createDom();
	
	this.reinit();
	
};

Plywet.extend(Plywet.widget.Scrollbar, Plywet.widget.BaseWidget);

Plywet.widget.Scrollbar.prototype.reinit = function(){
	this.currentTop = 0;
	this.currentLeft = 0;
	this._rePosition();
	this.switchScroll();
};

Plywet.widget.Scrollbar.prototype._isVScroll = function() {
	return this.type == 'vertical';
};

Plywet.widget.Scrollbar.prototype._createDom = function(){
	this.$container.addClass('ui-scrollbar-container');
	
	var _self = this;
		
	this.$scrollbar1 = $("<div></div>");
	this.$scrollbar1.addClass('ui-scrollbar-div1-'+this.type);
	this.$scrollbar1.click(function(e){
		_self._moveScrollbar1();
		e.preventDefault();
	});
	this.$container.append(this.$scrollbar1);
	
	this.$scrollbar2 = $("<div></div>");
	this.$scrollbar2.addClass('ui-scrollbar-div2-'+this.type);
	this.$scrollbar2.click(function(e){
		_self._moveScrollbar2();
		e.preventDefault();
	});
	this.$container.append(this.$scrollbar2);
};


Plywet.widget.Scrollbar.prototype._moveScrollbar2=function() {
	if(this._isVScroll()){
		this.currentTop=this.currentTop-this.stepSize;
		this.switchScroll();
		this.$tabGroup.animate({top:this.currentTop},"slow");
	}else{
		this.currentLeft=this.currentLeft-this.stepSize;
		this.switchScroll();
		this.$tabGroup.animate({left:this.currentLeft},"slow");
	}
	
};

Plywet.widget.Scrollbar.prototype._moveScrollbar1 = function(){
	if(this._isVScroll()){
		this.currentTop=this.currentTop+this.stepSize;
		this.switchScroll();
		this.$tabGroup.animate({top:this.currentTop},"slow");
	}else{
		this.currentLeft=this.currentLeft+this.stepSize;
		this.switchScroll();
		this.$tabGroup.animate({left:this.currentLeft},"slow");
	}
};

Plywet.widget.Scrollbar.prototype.switchScroll = function(){
	if (this._isVScroll()) {
		if (this.containerHeight >= this.allTabsHeight) {
			this.$scrollbar1.hide();
			this.$scrollbar2.hide();
			return;
		}
	
		var roof = 0,
			floor = this.containerHeight,
			tabsTop = this.currentTop,
			tabsButtom = this.currentTop + this.allTabsHeight;
	
		(tabsTop >= roof) ? this.$scrollbar1.hide() : this.$scrollbar1.show();
		(tabsButtom >= floor) ? this.$scrollbar2.show() : this.$scrollbar2.hide();

	} else {
		if (this.containerWidth >= this.allTabsWidth) {
			this.$scrollbar1.hide();
			this.$scrollbar2.hide();
			return;
		}
		
		var left = 0,
			right = this.containerWidth,
			tabsLeft = this.currentLeft,
			tabsRight = this.currentLeft + this.allTabsWidth;
		
		(tabsLeft >= left) ? this.$scrollbar1.hide() : this.$scrollbar1.show();
		(tabsRight >= right) ? this.$scrollbar2.show() : this.$scrollbar2.hide();	
	}
};

Plywet.widget.Scrollbar.prototype._rePosition = function(){
	if (this._isVScroll()) {
		this.containerHeight = this.$container.height();
		
		this.allTabsHeight = 0;// 有效高度，通过所有tab高度相加计算
		var tabArr = this.$tabGroup.children(':visible');
		for ( var i = 0; i < tabArr.length; i++) {
			this.allTabsHeight += $(tabArr[i]).height();
		}
		
		var roof = 0,
			floor = this.containerHeight,
			left = 0;
		
		var topPositon = roof,
			buttomPostion = floor - this.$scrollbar2.height(),
			leftPosition = left + this.$tabGroup.width() / 2
				- this.$scrollbar2.width() / 2;
		
		this.$scrollbar1.css({
			top: topPositon+"px",
			left: leftPosition+"px"
		});
		this.$scrollbar2.css({
			top: buttomPostion+"px",
			left: leftPosition+"px"
		});
	} else {
		this.containerWidth = this.$container.width();
		this.allTabsWidth = 0;// 有效宽度，通过所有tab宽度相加计算
		var tabArr = this.$tabGroup.children(':visible');
		for ( var i = 0; i < tabArr.length; i++) {
			this.allTabsWidth += $(tabArr[i]).width();
		}
		
		var left = this.$container.offset().left,
			right = this.$container.offset().left + this.containerWidth,
			roof = this.$tabGroup.offset().top;
	
		var leftPosition = left,
			rightPosition = right - this.$scrollbar2.width(),
			topPosition = roof + this.$tabGroup.height() / 2
				- this.$scrollbar2.height() / 2;
	
		this.$scrollbar1.css( {
			top : topPosition+"px",
			left : leftPosition+"px"
		});
		this.$scrollbar2.css( {
			top : topPosition+"px",
			left : rightPosition+"px"
		});
	}
};