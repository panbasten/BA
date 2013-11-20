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
	
	// 纵向滚动条
	function _initVsOC(target,opts){
		var vsOC = _div("ui-spreadsheet-vsOC").appendTo(target);
		$("<img class=\"ui-spreadsheet-vscroll-up\" width=\"17\" height=\"17\" src=\"images/default/s.gif\">").appendTo(vsOC);
		var vsOC_bg = _div("ui-spreadsheet-vscroll-bg").appendTo(vsOC);
		var vsOC_slider = _div("ui-spreadsheet-vscroll-slider").appendTo(vsOC_bg);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-top\" width=\"17\" height=\"4\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vfill\" width=\"17\" height=\"1\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vcenter\" width=\"17\" height=\"8\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vfill\" width=\"17\" height=\"1\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-bottom\" width=\"17\" height=\"4\" src=\"images/default/s.gif\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-vscroll-down\" width=\"17\" height=\"17\" src=\"images/default/s.gif\">").appendTo(vsOC);
		
		var vs1OC = _div("ui-spreadsheet-vsOC ui-spreadsheet-vs1OC").appendTo(target);
		vs1OC.hide();
		return [vsOC,vs1OC];
	}
	
	function _initHsOC(target,opts){
		var hs1OC = _div("ui-spreadsheet-hsOC ui-spreadsheet-hs1OC").appendTo(target);
		hs1OC.hide();
		
		var hsOC = _div("ui-spreadsheet-hsOC").appendTo(target);
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
	function _initColHdrs(target,opts){
		var gridColHdrsOC = _div("ui-spreadsheet-gridColHdrsOC").appendTo(target);
		var gridColHdrsIC = _div("ui-spreadsheet-gridColHdrsIC").appendTo(gridColHdrsOC);
		
		var col;
		for(var i=0;i<opts.colNum;i++){
			col = _div("ui-spreadsheet-gridColHdr",s10t26(i+1)).appendTo(gridColHdrsIC);
			col.css({
				left: (i*opts.colHeadWidth)+"px",
				width: (opts.colHeadWidth-5)+"px"
			});
		}
		
		return gridColHdrsOC;
	}
	
	// 行头
	function _initRowHdrs(target,opts){
		var gridRowHdrsOC = _div("ui-spreadsheet-gridRowHdrsOC").appendTo(target);
		var gridRowHdrsIC = _div("ui-spreadsheet-gridRowHdrsIC").appendTo(gridRowHdrsOC);
		
		var row;
		for(var i=0;i<opts.rowNum;i++){
			row = _div("ui-spreadsheet-gridRowHdr",(i+1)).appendTo(gridRowHdrsIC);
			row.css({
				top: (i*opts.rowHeadHeight)+"px",
				height: (opts.rowHeadHeight-5)+"px"
			});
		}
		
		return gridRowHdrsOC;
	}
	
	// 主表格区
	function _initPane(target,opts){
		var paneOC = _div("ui-spreadsheet-paneOC").appendTo(target);
		var paneIC = _div("ui-spreadsheet-paneIC").appendTo(paneOC);
		
		// 分隔线
		var col;
		for(var i=0;i<opts.colNum;i++){
			col = _div("ui-spreadsheet-gridColSep").appendTo(paneIC);
			col.css({
				left: (i*opts.colHeadWidth)+"px"
			});
		}
		
		// 行记录
		var row,cell;
		for(var i=0;i<opts.rowNum;i++){
			row = _div("ui-spreadsheet-gridRow").appendTo(paneIC);
			row.css({
				top: (i*opts.rowHeadHeight)+"px",
				height: (opts.rowHeadHeight-5)+"px"
			});
			
			for(var j=0;j<opts.colNum;j++){
				cell = _div("ui-spreadsheet-gridCell").appendTo(row);
				cell.css({
					left: (j*opts.colHeadWidth)+"px",
					width: (opts.colHeadWidth-5)+"px"
				});
			}
		}
		
		return paneOC;
	}
	
	function _initSheet(target,opts){
		var sheet = _div("ui-spreadsheet-sheet").appendTo(target);
		// 左上角全选区
		_div("ui-spreadsheet-gridSelectAll").appendTo(sheet);
		// 列头
		var colHdrs = _initColHdrs(sheet,opts);
		// 行头
		var rowHdrs = _initRowHdrs(sheet,opts);
		// 主表格区
		var pane = _initPane(sheet,opts);
		return sheet;
	}
	
	function _initSheets(target,opts){
		return _div("ui-spreadsheet-sheets").appendTo(target);
	}
	
	// sheet选择器
	function _initSelector(target,opts){
		var selector = _div("ui-spreadsheet-sheetSelectorOC").appendTo(target);
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
		var vs = _initVsOC(book,opts);
		
		// sheets
		var sheets = _initSheets(book,opts);
		
		// br_spacer
		var br = _div("ui-spreadsheet-gridBRSpacer").appendTo(book);
		
		// selector
		var selector = _initSelector(book,opts);
		
		// hs
		var hs = _initHsOC(book,opts);
		
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
		
		opts.paneWidth = opts.workspaceWidth - opts.vscrollWidth - opts.rowHeadWidth;
		opts.paneHeight = opts.workspaceHeight - opts.hscrollHeight - opts.colHeadHeight;
		
		opts.colNum = parseInt(opts.paneWidth / opts.colHeadWidth) + opts.offsetCellNumber;
		opts.rowNum = parseInt(opts.paneHeight / opts.rowHeadHeight) + opts.offsetCellNumber;
	}
	
	function _resizeVs(target,h){
		var ss = $.data(target, "spreadsheet");
		var opts = ss.options;
		
		var bgH = (h-opts.hscrollHeight*2),
			paneH = opts.paneHeight,
			allRowH = (opts.rowNum * opts.rowHeadHeight); // TODO 通过计算单行获得
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
			allColumnW = (opts.colNum * opts.colHeadWidth);// TODO 通过计算单列获得
		
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
		_initSheet(ss.sheets,opts);
		ss.sheets.width(w).height(h);
		ss.sheets.find(".ui-spreadsheet-sheet").width(w).height(h);
		
		// selector
		var selectorWidth = parseInt( w * 0.6 );
		ss.selector.width(selectorWidth);
		
		// hs
		var hsWidth = w-selectorWidth;
		_resizeHs(target, hsWidth);
		
		// pane
		w = w - opts.rowHeadWidth;
		h = h - opts.colHeadHeight;
		ss.sheets.find(".ui-spreadsheet-gridColHdrsOC").css({
			left: opts.rowHeadWidth+"px"
			,width: w+"px"
		});
		ss.sheets.find(".ui-spreadsheet-gridRowHdrsOC").css({
			top: opts.colHeadHeight+"px"
			,height: h+"px"
		});
		ss.sheets.find(".ui-spreadsheet-paneOC").css({
			left: opts.rowHeadWidth+"px"
			,top: opts.colHeadHeight+"px"
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
		
		,colHeadWidth: 64
		,colHeadHeight: 19
		,rowHeadWidth: 41
		,rowHeadHeight: 20
		,vscrollWidth: 17
		,hscrollHeight: 17
		
		,offsetCellNumber: 3
		
		,onBeforeShow:	function(){}
		,onShow:	function(){}
		,onBeforeHide:	function(){}
		,onHide:	function(){}
	};
})(jQuery);
