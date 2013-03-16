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
	this._rePosition();
	this.switchScroll();
	this.currentTop = 0;
	this.currentLeft = 0;
};

Plywet.widget.Scrollbar.prototype._isVScroll = function() {
	return this.type == 'vertical';
};

Plywet.widget.Scrollbar.prototype._createDom = function(){
	this.$container.addClass('scroll-container');
		
	var divMax = document.createElement('div');
	this.$divMax = $(divMax);
	this.$divMax.addClass('div-max-'+this.type);
	
	var _self = this;
	this.$divMax.click(function(e){
		_self._moveMax();
		e.preventDefault();
	});
	
	this.$container.append(this.$divMax);
	
	var divMin = document.createElement('div');
	this.$divMin = $(divMin);
	this.$divMin.addClass('div-min-'+this.type);
	this.$divMin.click(function(e){
		_self._moveMin();
		e.preventDefault();
	});
	
	this.$container.append(this.$divMin);
};


Plywet.widget.Scrollbar.prototype._moveMin=function() {
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

Plywet.widget.Scrollbar.prototype._moveMax = function(){
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
		var containerHeight = this.$container.height();
		var validHeight = 0;// 有效高度，通过所有tab高度相加计算
		var tabArr = this.$tabGroup.children(':visible');
		for ( var i = 0; i < tabArr.length; i++) {
			validHeight += $(tabArr[i]).height();
		}
	
		if (containerHeight >= validHeight) {
			this.$divMin.hide();
			this.$divMax.hide();
			return;
		}
	
		var roof = 0,
			floor = this.$container.height(),
			tabsTop = this.currentTop,
			tabsButtom = this.currentTop + validHeight;
	
		(tabsTop >= roof) ? this.$divMax.hide() : this.$divMax.show();
		(tabsButtom >= floor) ? this.$divMin.show() : this.$divMin.hide();
	} else {
		var containerWidth = this.$container.width();
		var validWidth = 0;// 有效宽度，通过所有tab宽度相加计算
		var tabArr = this.$tabGroup.children(':visible');
		for ( var i = 0; i < tabArr.length; i++) {
			validWidth += $(tabArr[i]).width();
		}

		if (containerWidth >= validWidth) {
			this.$divMin.hide();
			this.$divMax.hide();
			return;
		}
		
		var left = 0,
			right = this.$container.width(),
			tabsLeft = this.currentLeft,
			tabsRight = this.currentLeft + validWidth;
		
		(tabsLeft >= left) ? this.$divMax.hide() : this.$divMax.show();
		(tabsRight >= right) ? this.$divMin.show() : this.$divMin.hide();	
	}
};

Plywet.widget.Scrollbar.prototype._rePosition = function(){
	if (this._isVScroll()) {
		var roof = 0,
			floor = this.$container.height(),
			left = 0;
		
		var topPositon = roof,
			buttomPostion = floor - this.$divMin.height(),
			leftPosition = left + this.$tabGroup.width() / 2
				- this.$divMin.width() / 2;
		
		this.$divMax.css({
			top: topPositon+"px",
			left: leftPosition+"px"
		});
		this.$divMin.css({
			top: buttomPostion+"px",
			left: leftPosition+"px"
		});
	} else {
		var left = this.$container.offset().left,
			right = this.$container.offset().left + this.$container.width(),
			roof = this.$tabGroup.offset().top;
	
		var leftPosition = left,
			rightPosition = right - this.$divMin.width(),
			topPosition = roof + this.$tabGroup.height() / 2
				- this.$divMin.height() / 2;
	
		this.$divMax.css( {
			top : topPosition+"px",
			left : leftPosition+"px"
		});
		this.$divMin.css( {
			top : topPosition+"px",
			left : rightPosition+"px"
		});
	}
};