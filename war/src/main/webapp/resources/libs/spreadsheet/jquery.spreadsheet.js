(function($){
	
	function _div(cls,text){
		if(text == undefined){
			text = "";
		}
		return $("<div class=\""+cls+"\">"+text+"</div>");
	}
	
	// 10进制数字转换为26进制A-Z
	function s10t26(n) {
		var s = "";
		while (n > 0) {
			var m = n % 26;
			if (m == 0){
				m = 26;
			}
			s = String.fromCharCode(m + 64) + s;
			n = (n - m) / 26;
		}
		return s;
	}
	
	// 26进制A-Z转换为10进制数字
	function s26t10(s) {
		if (s == "")
			return 0;
		var n = 0, j = 1;
		for ( var i = s.length - 1; i >= 0; i--) {
			var c = s[i].toUpperCase();
			if (c < "A" || c > "Z")
				return 0;
			n += (c.charCodeAt(0) - 64) * j;
			j *= 26;
		}
		return n;
	}
	
	// 获得列宽
	function _getColWidth(target,colIdx){
		var opts = $.data(target, "spreadsheet").options;
		if(opts.data.colsWidth["c_"+colIdx] != undefined){
			return opts.data.colsWidth["c_"+colIdx];
		}else{
			return opts.defaultColWidth;
		}
	}
	
	// 获得列宽累计值
	function _getColsWidth(target,sColIdx,eColIdx){
		var w = 0;
		for(var i=sColIdx;i<=eColIdx;i++){
			w = w + _getColWidth(target,i);
		}
		return w;
	}
	
	// 获得行高
	function _getRowHeight(target,rowIdx){
		var opts = $.data(target, "spreadsheet").options;
		if(opts.data.rowsHeight["r_"+rowIdx] != undefined){
			return opts.data.rowsHeight["r_"+rowIdx];
		}else{
			return opts.defaultRowHeight;
		}
	}
	
	// 获得行高累计值
	function _getRowsHeight(target,sRowIdx,eRowIdx){
		var h = 0;
		for(var i=sRowIdx;i<=eRowIdx;i++){
			h = h + _getRowHeight(target,i);
		}
		return h;
	}
	
	// 纵向滚动条
	function _initVsOC(target,parent,opts){
		var vsOC = _div("ui-spreadsheet-vsOC").appendTo(parent);
		$("<img class=\"ui-spreadsheet-vscroll-up\" width=\"17\" height=\"17\" src=\"images/default/s.gif\">").appendTo(vsOC);
		var vsOC_bg = _div("ui-spreadsheet-vscroll-bg").appendTo(vsOC);
		var vsOC_slider = _div("ui-spreadsheet-vscroll-slider").appendTo(vsOC_bg);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-top\" width=\"17\" height=\"4\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vfill\" width=\"17\" height=\"1\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vcenter\" width=\"17\" height=\"8\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vfill\" width=\"17\" height=\"1\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-bottom\" width=\"17\" height=\"4\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-vscroll-down\" width=\"17\" height=\"17\" src=\"images/default/s.gif\">").appendTo(vsOC);
		
		var vs1OC = _div("ui-spreadsheet-vsOC ui-spreadsheet-vs1OC").appendTo(parent);
		vs1OC.hide();
		return [vsOC,vs1OC];
	}
	
	function _initHsOC(target,parent,opts){
		var hs1OC = _div("ui-spreadsheet-hsOC ui-spreadsheet-hs1OC").appendTo(parent);
		hs1OC.hide();
		
		var hsOC = _div("ui-spreadsheet-hsOC").appendTo(parent);
		$("<img class=\"ui-spreadsheet-hscroll-left\" width=\"17\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC);
		var hsOC_bg = _div("ui-spreadsheet-hscroll-bg").appendTo(hsOC);
		var hsOC_slider = _div("ui-spreadsheet-hscroll-slider").appendTo(hsOC_bg);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-left\" width=\"4\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-hfill\" width=\"1\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-hcenter\" width=\"8\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-hfill\" width=\"1\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-right\" width=\"4\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-hscroll-right\" width=\"17\" height=\"17\" src=\"images/default/s.gif\">").appendTo(hsOC);
		
		$("<div class=\"x-resizable-handle x-resizable-handle-west x-unselectable\" style=\"-moz-user-select: none; opacity: 0;\"></div>").appendTo(hsOC);
		
		return [hsOC,hs1OC];
	}
	
	// 列头
	function _initColHdrs(target,parent,opts){
		var gridColHdrsOC = _div("ui-spreadsheet-gridColHdrsOC").appendTo(parent);
		var gridColHdrsIC = _div("ui-spreadsheet-gridColHdrsIC").appendTo(gridColHdrsOC);
		
		var col,width,left = 0;
		for(var i=0;i<opts.colNum;i++){
			width = _getColWidth(target,i);
			col = _div("ui-spreadsheet-gridColHdr",s10t26(i+1)).appendTo(gridColHdrsIC);
			col.css({
				left: left+"px",
				width: (width-5)+"px"
			});
			
			left = left + width;
		}
		
		return gridColHdrsOC;
	}
	
	// 行头
	function _initRowHdrs(target,parent,opts){
		var gridRowHdrsOC = _div("ui-spreadsheet-gridRowHdrsOC").appendTo(parent);
		var gridRowHdrsIC = _div("ui-spreadsheet-gridRowHdrsIC").appendTo(gridRowHdrsOC);
		
		var row,height,top = 0;
		for(var i=0;i<opts.rowNum;i++){
			height = _getRowHeight(target,i);
			row = _div("ui-spreadsheet-gridRowHdr",(i+1)).appendTo(gridRowHdrsIC);
			row.css({
				top: top+"px",
				height: (height-5)+"px"
			});
			top = top + height;
		}
		
		return gridRowHdrsOC;
	}
	
	// 主表格区
	function _initPane(target,parent,opts){
		var opts = $.data(target, "spreadsheet").options;
		
		var paneOC = _div("ui-spreadsheet-paneOC").appendTo(parent);
		var paneIC = _div("ui-spreadsheet-paneIC").appendTo(paneOC);
		
		// 分隔线
		var col,width,left = 0;
		for(var i=0;i<opts.colNum;i++){
			width = _getColWidth(target,i);
			col = _div("ui-spreadsheet-gridColSep").appendTo(paneIC);
			col.css({
				left: left+"px"
			});
			left = left + width;
		}
		
		// 行记录
		var row,cell,height,top=0;
		for(var i=0;i<opts.rowNum;i++){
			height = _getRowHeight(target,i);
			row = _div("ui-spreadsheet-gridRow").appendTo(paneIC);
			row.css({
				top: top+"px",
				height: (height-5)+"px"
			});
			
			top = top + height;
			left = 0;
			for(var j=0;j<opts.colNum;j++){
				width = _getColWidth(target,j);
				cell = _div("ui-spreadsheet-gridCell").appendTo(row);
				cell.css({
					left: left+"px",
					width: (width-5)+"px"
				});
				left = left + width;
			}
		}
		
		// 选中框
		var bv1 = _div("ui-spreadsheet-defaultRangeBorder ui-spreadsheet-vertical-normal-default").appendTo(paneIC).hide();
		var bv2 = _div("ui-spreadsheet-defaultRangeBorder ui-spreadsheet-vertical-normal-default").appendTo(paneIC).hide();
		var bh1 = _div("ui-spreadsheet-defaultRangeBorder ui-spreadsheet-horizontal-normal-default").appendTo(paneIC).hide();
		var bh2 = _div("ui-spreadsheet-defaultRangeBorder ui-spreadsheet-horizontal-normal-default").appendTo(paneIC).hide();
		// 激活cell
		var ac = _div("ui-spreadsheet-activeCellElement ui-spreadsheet-cursorField").appendTo(paneIC).hide();
		
		
		// 选中的虚线拖拽框
		var fv1 = _div("ui-spreadsheet-fillRangeBorder ui-spreadsheet-vertical-normal-fill").appendTo(paneIC).hide();
		var fv2 = _div("ui-spreadsheet-fillRangeBorder ui-spreadsheet-vertical-normal-fill").appendTo(paneIC).hide();
		var fh1 = _div("ui-spreadsheet-fillRangeBorder ui-spreadsheet-horizontal-normal-fill").appendTo(paneIC).hide();
		var fh2 = _div("ui-spreadsheet-fillRangeBorder ui-spreadsheet-horizontal-normal-fill").appendTo(paneIC).hide();
		
		// 选择点
		var re = _div("ui-spreadsheet-rangeEdge ui-spreadsheet-cur-mdcurrd-1").appendTo(paneIC).hide();
		
		// 选中框背景
		var rbg = _div("ui-spreadsheet-rangeBackground").appendTo(paneIC).hide();
		
		var win = Flywet.getWindowScroll();
		var rm = _div("ui-spreadsheet-rangeMask").css({
			left: (win.width*(-1))+"px"
			,top: (win.height*(-1))+"px"
			,width: (win.width*2)+"px"
			,height: (win.height*2)+"px"
		}).appendTo(rbg);
		
		// 事件
		paneIC.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			// 取得按下鼠标的坐标
			var pos = Flywet.getMousePosition(e,paneIC);
			var cpos = _getCellPositionByCoors(target,pos);
			
			opts.rangeHold = true;
			opts.rangeStartPosition = cpos;
			opts.rangeEndPosition = cpos;
			
			_showRange(cpos,cpos);
		})
		.mousemove(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			if(opts.rangeHold){
				var pos = Flywet.getMousePosition(e,paneIC);
				var cpos = _getCellPositionByCoors(target,pos);
				
				opts.rangeEndPosition = cpos;
				_showRange(opts.rangeStartPosition,cpos);
			}
		})
		.mouseup(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			if(opts.rangeHold){
				var pos = Flywet.getMousePosition(e,paneIC);
				var cpos = _getCellPositionByCoors(target,pos);
				
				opts.rangeEndPosition = cpos;
				opts.rangeHold = false;
				
				_showRange(opts.rangeStartPosition,cpos);
			}
		});
		
		// 通过Cell坐标获得Cell
		function _getCellCss(pos){
			var row = paneIC.find(".ui-spreadsheet-gridRow").get(pos.ridx);
			var cell = $(row).find(".ui-spreadsheet-gridCell").get(pos.cidx);
			return {
				top : Flywet.cssNum(row,"top")
				,left : Flywet.cssNum(cell,"left")
			};
		}
		
		// 显示选中框
		function _showRange(spos, epos){
			var startPos = {
				ridx : Math.min(spos.ridx,epos.ridx)
				,cidx : Math.min(spos.cidx,epos.cidx)
			};
			var endPos = {
				ridx : Math.max(spos.ridx,epos.ridx)
				,cidx : Math.max(spos.cidx,epos.cidx)
			};
			var scss = _getCellCss(startPos),
				ecss = _getCellCss(endPos),
				scss_o = _getCellCss(spos);
			bv1.css({
				left: (scss.left-2)+"px"
				,top: (scss.top-1)+"px"
				,width: "5px"
				,height: (_getRowsHeight(target,startPos.ridx,endPos.ridx)+2)+"px"
			}).show();
			
			bv2.css({
				left: (ecss.left+_getColWidth(target,endPos.cidx)-2)+"px"
				,top: (scss.top-1)+"px"
				,width: "5px"
				,height: (_getRowsHeight(target,startPos.ridx,endPos.ridx)+2)+"px"
			}).show();
			
			bh1.css({
				left: (scss.left-1)+"px"
				,top: (scss.top-2)+"px"
				,width: (_getColsWidth(target,startPos.cidx,endPos.cidx)+2)+"px"
				,height: "5px"
			}).show();
			
			bh2.css({
				left: (scss.left-1)+"px"
				,top: (ecss.top+_getRowHeight(target,endPos.ridx)-2)+"px"
				,width: (_getColsWidth(target,startPos.cidx,endPos.cidx)+1)+"px"
				,height: "5px"
			}).show();
			
			ac.css({
				left: scss_o.left+"px"
				,top: (scss_o.top+1)+"px"
				,width: (_getColWidth(target,spos.cidx)-5)+"px"
				,height: (_getRowHeight(target,spos.ridx)-6)+"px"
			}).show();
			
			re.css({
				left: (ecss.left+_getColWidth(target,endPos.cidx)-3)+"px"
				,top: (ecss.top+_getRowHeight(target,endPos.ridx)-3)+"px"
			}).show();
			
			rbg.css({
				left: (scss.left+3)+"px"
				,top: (scss.top+3)+"px"
				,width: (_getColsWidth(target,startPos.cidx,endPos.cidx)-5)+"px"
				,height: (_getRowsHeight(target,startPos.ridx,endPos.ridx)-5)+"px"
			}).show();
			
		}
		
		return paneOC;
	}
	
	// 获得鼠标点击位置的Cell坐标
	function _getCellPositionByCoors(target,pos){
		var opts = $.data(target, "spreadsheet").options;
		var tx = pos.x, ty = pos.y, ridx = 0, cidx = 0;
		while(tx>0){
			tx = tx - _getColWidth(target, cidx);
			cidx++;
		}
		while(ty>0){
			ty = ty - _getRowHeight(target, ridx);
			ridx++;
		}
		return {
			ridx : (ridx-1),
			cidx : (cidx-1)
		};
	}
	
	// 根据Cell坐标获得Cell对象
	function _getCellByPosition(target,pos){
		var opts = $.data(target, "spreadsheet").options;
		var sheet = $.data(target, "spreadsheet").sheet;
		var cs = sheet.get(opts.currentSheetIndex)
		var row = cs.find(".ui-spreadsheet-gridRow").get(pos.ridx);
		return $(row).find(".ui-spreadsheet-gridCell").get(pos.cidx);
	}
	
	function _initSheet(target,parent,opts){
		var sheet = _div("ui-spreadsheet-sheet").appendTo(parent);
		// 左上角全选区
		_div("ui-spreadsheet-gridSelectAll").appendTo(sheet);
		// 列头
		var colHdrs = _initColHdrs(target,sheet,opts);
		// 行头
		var rowHdrs = _initRowHdrs(target,sheet,opts);
		// 主表格区
		var pane = _initPane(target,sheet,opts);
		
		return sheet;
	}
	
	function _initSheets(target,parent,opts){
		return _div("ui-spreadsheet-sheets").appendTo(parent);
	}
	
	// sheet选择器
	function _initSelector(target,parent,opts){
		var selector = _div("ui-spreadsheet-sheetSelectorOC").appendTo(parent);
		var selectorTB = _div("ui-spreadsheet-sheetSelectorTB").appendTo(selector);
		var selectorTB = _div("ui-spreadsheet-sheetSelectorIC").appendTo(selector);
		return selector;
	}
	
	function _init(target){
		$(target).addClass("ui-spreadsheet-f").attr("id",null).hide();
		
		var opts = $.data(target, "spreadsheet").options;
		
		// workspace
		var workspace = _div("ui-spreadsheet ui-spreadsheet-workspace x-border-box").insertAfter(target);
		workspace.attr("id", (opts.id)?(opts.id):"");
		
		// book
		var book = _div("ui-spreadsheet-book").appendTo(workspace);
		
		// vs
		var vs = _initVsOC(target,book,opts);
		
		// sheets
		var sheets = _initSheets(target,book,opts);
		
		// br_spacer
		var br = _div("ui-spreadsheet-gridBRSpacer").appendTo(book);
		
		// selector
		var selector = _initSelector(target,book,opts);
		
		// hs
		var hs = _initHsOC(target,book,opts);
		
		workspace.bind("_resize", function() {
			var opts = $.data(target, "spreadsheet").options;
			if (opts.fit == true) {
				_resize(target);
			}
			return false;
		});
		
		return {
			workspace : workspace
			,book : book
			,vs : vs
			,sheets : sheets
			,br : br
			,selector : selector
			,hs : hs
		};
	}
	
	// 计算显示尺寸
	function _calSize(target){
		var opts = $.data(target, "spreadsheet").options;
		var dim, parent=$(target).parent();
		if(parent.get(0).tagName === "BODY"){
			dim = Flywet.getWindowScroll();
			dim = {width: dim.width, height: dim.height};
		}else{
			dim = Flywet.getElementDimensions(parent);
			dim = {width: dim.css.width, height: dim.css.height};
		}
		opts.width = dim.width;
		opts.height = dim.height;
		
		opts.workspaceWidth = opts.width - 2;
		opts.workspaceHeight = opts.height - 2;
		
		opts.paneWidth = opts.workspaceWidth - opts.vscrollWidth - opts.headRowWidth;
		opts.paneHeight = opts.workspaceHeight - opts.hscrollHeight - opts.headColHeight;
		
		var pos = _getCellPositionByCoors(target,{
			x:opts.paneWidth
			,y:opts.paneHeight
		});
		opts.colNum = pos.cidx + 1 + opts.offsetCellNumber;
		opts.rowNum = pos.ridx + 1 + opts.offsetCellNumber;
	}
	
	function _resizeVs(target,h){
		var ss = $.data(target, "spreadsheet");
		var opts = ss.options;
		
		var bgH = (h-opts.hscrollHeight*2),
			paneH = opts.paneHeight,
			allRowH = _getRowsHeight(target,0,(opts.rowNum-1));
		if(paneH>allRowH){
			ss.vs[1].height(h);
			ss.vs[0].hide();
			ss.vs[1].show();
		}else{
			ss.vs[0].height(h);
			ss.vs[0].find(".ui-spreadsheet-vscroll-bg").height(bgH);
			var elh = bgH * paneH / allRowH;
			var vfillh = parseInt((elh-16)/2);
			if(vfillh<1){
				vfillh = 1;
			}
			ss.vs[0].find(".ui-spreadsheet-gridScrollImg-el-vfill").height(vfillh);
			ss.vs[0].show();
			ss.vs[1].hide();
		}
		
	}
	
	function _resizeHs(target, w){
		var ss = $.data(target, "spreadsheet");
		var opts = ss.options;
		
		var bgW = (w-opts.vscrollWidth*2),
			paneW = opts.paneWidth,
			allColumnW = _getColsWidth(target,0,(opts.colNum-1));
		
		if(paneW>allColumnW){
			ss.hs[1].width(w);
			ss.hs[0].hide();
			ss.hs[1].show();
		}else{
			ss.hs[0].width(w);
			ss.hs[0].find(".ui-spreadsheet-hscroll-bg").width(bgW);
			var elh = bgW * paneW / allColumnW;
			var hfillh = parseInt((elh-16)/2);
			if(hfillh<1){
				hfillh = 1;
			}
			ss.hs[0].find(".ui-spreadsheet-gridScrollImg-el-hfill").width(hfillh);
			ss.hs[0].show();
			ss.hs[1].hide();
		}
		
	}
	
	function _resize(target){
		var ss = $.data(target, "spreadsheet");
		var opts = ss.options;
		
		_calSize(target);
		
		// workspace
		var w = opts.workspaceWidth, 
			h = opts.workspaceHeight;
		ss.workspace.width(w).height(h);
		
		// vs
		h = h - opts.hscrollHeight;
		_resizeVs(target, h);
		
		// sheets
		w = w - opts.vscrollWidth;
		ss.sheets.empty();
		
		// TODO 多个Sheet页
		var sheet = [];
		sheet.push(_initSheet(target,ss.sheets,opts));
		ss.sheet = sheet;
		
		// 当前坐标
		opts.currentSheetIndex = 0;
		
		ss.sheets.width(w).height(h);
		ss.sheets.find(".ui-spreadsheet-sheet").width(w).height(h);
		
		// selector
		var selectorWidth = parseInt( w * 0.6 );
		ss.selector.width(selectorWidth);
		
		// hs
		var hsWidth = w-selectorWidth;
		_resizeHs(target, hsWidth);
		
		// pane
		w = w - opts.headRowWidth;
		h = h - opts.headColHeight;
		ss.sheets.find(".ui-spreadsheet-gridColHdrsOC").css({
			left: opts.headRowWidth+"px"
			,width: w+"px"
		});
		ss.sheets.find(".ui-spreadsheet-gridRowHdrsOC").css({
			top: opts.headColHeight+"px"
			,height: h+"px"
		});
		ss.sheets.find(".ui-spreadsheet-paneOC").css({
			left: opts.headRowWidth+"px"
			,top: opts.headColHeight+"px"
			,width: w+"px"
			,height: h+"px"
		});
		
	}
	
	function _show(target){
		var opts = $.data(target, "spreadsheet").options;
		var workspace = $.data(target, "spreadsheet").workspace;
		if (opts.onBeforeShow.call(target) == false) {
			return;
		}
		workspace.show();
		opts.hidden = false;
		opts.onShow.call(target);
	}
	
	function _hide(target){
		var opts = $.data(target, "spreadsheet").options;
		var workspace = $.data(target, "spreadsheet").workspace;
		if (opts.onBeforeHide.call(target) == false) {
			return;
		}
		workspace.hide();
		opts.hidden = true;
		opts.onHide.call(target);
	}
	
	$.fn.spreadsheet = function(options, param) {
		if(typeof options == "string"){
			return $.fn.pushbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "spreadsheet");
			var opts;
			if(t){
				opts = $.extend(t.options, options);
			}else{
				opts = $.extend(
						{},
						$.fn.spreadsheet.defaults,
						$.fn.spreadsheet.parseOptions(this),
						options
					);
				$.data(this, "spreadsheet", {
					options : opts
				});
				
				t = _init(this);
				$.data(this, "spreadsheet", {
					options : 	opts
					,workspace : t.workspace
					,book : t.book
					,vs : t.vs
					,sheets : t.sheets
					,br : t.br
					,selector : t.selector
					,hs : t.hs
				});
				
				$.data(this, "componentType", "spreadsheet");
			}
			
			if (opts.fit == true) {
				t.workspace.css("display", "block");
				_resize(this);
			}
			if (opts.show) {
				_show(this);
			} else {
				_hide(this);
			}
			
		});
	};
	
	$.fn.spreadsheet.methods = {
		options : function(jq) {
			return $.data(jq[0], "spreadsheet").options;
		},
		workspace : function(jq) {
			return $.data(jq[0], "spreadsheet").workspace;
		}
	};
	
	$.fn.spreadsheet.parseOptions = function(target) {
		var t = $(target);
		return $.extend({},
			Flywet.parseOptions(target, ["id","width","height","resize","hidden"])
		);
	};
	
	$.fn.spreadsheet.defaults = {
		id : 		null
		,width :	"auto"
		,height :	"auto"
		,fit :		true
		,show :		true
		
		,defaultColWidth: 64
		,defaultRowHeight: 20
		
		,data : {
			colsWidth : {}
			,rowsHeight : {}
		}
		
		,headColHeight: 19
		,headRowWidth: 41
		
		,vscrollWidth: 17
		,hscrollHeight: 17
		
		,offsetCellNumber: 3
		
		,currentSheetIndex: 0
		
		// 滚动条起始cell位置
		,startRowIndex : 0
		,startColIndex : 0
		
		,rangeHold : false
		,rangeStartPosition : null
		,rangeEndPosition : null
		
		,onBeforeShow:	function(){}
		,onShow:	function(){}
		,onBeforeHide:	function(){}
		,onHide:	function(){}
	};
})(jQuery);
