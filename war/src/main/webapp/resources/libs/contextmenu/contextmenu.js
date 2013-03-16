/**
 * 右键菜单
 * 
 * @author yanghyc
 * @date 2012-10-23
 */
Plywet.widget.ContextMenu = function(cfg) {
	this.cfg = cfg;
	this.id = cfg.id;
	this.els = this.cfg.els;
	this.jqId = Plywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.container;
	this.init();
};
Plywet.extend(Plywet.widget.ContextMenu, Plywet.widget.BaseWidget);
Plywet.widget.ContextMenu.prototype = {
	init : function() {
		if (!this.container) {
			this.container = this._createContainer(this.els);
		}
		;
		this.stopSysMenu();
		this.addEvent();
		return this.container;
	},
	_createContainer : function(items) {
		var con = this.createdom('div', 'ui-contextmenu-bg');
		if (!this.els)
			return;
		if (!this.zindex) {
			this.zindex = 100;
			this.level = 0;
		} else {
			this.zindex = this.zindex + 10;
			this.level = this.level + 1;
		}
		$(con).attr('level' ,this.level);
		$(con).addClass('level'+this.level);
		$(con).css({
			'z-index' : this.zindex
		});
		for ( var i = 0; i < items.length; i++) {
			var item = items[i];
			this.addItem(con, item);
		}
		return con;
	},
	addItem : function(con, item) {
		var _self = this;
		var temp = this.createdom("div", "block");
		var left = this.createdom("div", "left");
		var right = this.createdom("div", "right");
		var img = this.createdom("img");
		$(img).attr({
			'src' : item.icon
		});
		$(left).append(img);
		$(right).html(item.displayName);
		$(temp).append(left);
		$(temp).append(right);
		$(con).append(temp);
		var num = _self.level + 1;
		if (item.els && item.els.length > 0) {
			var more = this.createdom('div', 'more');
			$(temp).append(more);
			$(temp).mouseover(function(e) {
				var tempcon = $("#" + item.id);
				if (tempcon && tempcon.length > 0) {
					$(tempcon).css({
						"display" : 'block'
					});
				} else {
					tempcon = _self._createContainer(item.els);
					$(tempcon).attr('id', item.id);
					$(document.body).append(tempcon);
				};
				$(tempcon).css({
					left : $(temp).offset().left + $(temp).outerWidth(),
					top : $(temp).offset().top
				});
			});
		}
		$(temp).mouseover(function() {
			$(".level1").css({
				'display' : 'none'
			});
		});

	},
	stopSysMenu : function() {
		$(document).bind('contextmenu', function(e) {
			return false;
		});
	},
	addEvent : function() {
		var _self = this;
		$(document.body).append(_self.container);
		$(_self.container).css({'display':'none'});
		$("#" + this.id).mouseup(function(e) {
			if (e.which == 3) {
				_self.show(e, _self.container);
			};
		});
		$(this.container).bind('blur', function() {
			$(".ui-contextmenu-bg").css({
				'display' : 'none'
			});
		});
	},
	show : function(e, dom) {
		if (!e)
			return;
		$(this.container).css({
			'display' : 'block'
		});
		var left = e.pageX, top = e.pageY;
		var totalheight = $(window).height();
		var totalwidth = $(window).width();
		if (e.pageX + $(dom).outerWidth() > totalwidth
				&& e.pageX > totalheight / 2)
			left = e.pageX - $(dom).outerWidth();
		if (e.pageY + $(dom).outerHeight() > totalheight
				&& e.pageY > totalwidth / 2)
			top = e.pageY - $(dom).outerHeight();
		$(this.container).css({
			left : left,
			top : top
		});
		$(dom).attr('tabindex', -1).focus();
	},
	createdom : function(tagname, classnames) {
		if (!tagname)
			return;
		var dom = document.createElement(tagname);
		if (classnames)
			$(dom).addClass(classnames);
		return dom;
	},
	getDom : function() {
		return this.container;
	}
};