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
	function _getColWidth(sheetOpts,colIdx){
		if(sheetOpts.data.colsWidth["c_"+colIdx] != undefined){
			return sheetOpts.data.colsWidth["c_"+colIdx];
		}else{
			return sheetOpts.defaultColWidth;
		}
	}
	
	// 获得列宽累计值
	function _getColsWidth(sheetOpts,sColIdx,eColIdx){
		var w = 0;
		for(var i=sColIdx;i<=eColIdx;i++){
			w = w + _getColWidth(sheetOpts,i);
		}
		return w;
	}
	
	// 获得行高
	function _getRowHeight(sheetOpts,rowIdx){
		if(sheetOpts.data.rowsHeight["r_"+rowIdx] != undefined){
			return sheetOpts.data.rowsHeight["r_"+rowIdx];
		}else{
			return sheetOpts.defaultRowHeight;
		}
	}
	
	// 获得行高累计值
	function _getRowsHeight(sheetOpts,sRowIdx,eRowIdx){
		var h = 0;
		for(var i=sRowIdx;i<=eRowIdx;i++){
			h = h + _getRowHeight(sheetOpts,i);
		}
		return h;
	}
	
	// 纵向滚动条
	function _initVsOC(target,parent,opts){
		var vsOC = _div("ui-spreadsheet-vsOC").appendTo(parent);
		$("<img class=\"ui-spreadsheet-vscroll-up\" width=\"17\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(vsOC);
		var vsOC_bg = _div("ui-spreadsheet-vscroll-bg").appendTo(vsOC);
		var vsOC_slider = _div("ui-spreadsheet-vscroll-slider").appendTo(vsOC_bg);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-top\" width=\"17\" height=\"4\" src=\""+opts.s_src+"\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vfill\" width=\"17\" height=\"1\" src=\""+opts.s_src+"\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vcenter\" width=\"17\" height=\"8\" src=\""+opts.s_src+"\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-vfill\" width=\"17\" height=\"1\" src=\""+opts.s_src+"\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-bottom\" width=\"17\" height=\"4\" src=\""+opts.s_src+"\">").appendTo(vsOC_slider);
		$("<img class=\"ui-spreadsheet-vscroll-down\" width=\"17\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(vsOC);
		
		var vs1OC = _div("ui-spreadsheet-vsOC ui-spreadsheet-vs1OC").appendTo(parent);
		
		vsOC_bg.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			opts.vscrollHold = false;
			
			// TODO 单击
			
		})
		.mousemove(function(e){
			if(opts.vscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveVs(target,opts,opts.scrollStartPosition,pos);
			}
		})
		.mouseup(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			if(opts.vscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveVs(target,opts,opts.scrollStartPosition,pos);
			}
			
			opts.vscrollHold = false;
		});
		
		vsOC_slider.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			var pos = Flywet.getMousePosition(e);
			
			opts.vscrollHold = true;
			opts.scrollStartPosition = pos;
			
			var sheetOpts = _getCurrentSheetOpts(opts);
			sheetOpts.tempTop = sheetOpts.top;
			
			e.stopPropagation();
            e.preventDefault();
		})
		.mousemove(function(e){
			
			if(opts.vscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveVs(target,opts,opts.scrollStartPosition,pos);
			}
			
			e.stopPropagation();
            e.preventDefault();
		})
		.mouseup(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			if(opts.vscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveVs(target,opts,opts.scrollStartPosition,pos);
			}
			
			opts.vscrollHold = false;
			
			e.stopPropagation();
            e.preventDefault();
		});
		
		
		vsOC.hide();
		vs1OC.hide();
		return [vsOC,vs1OC];
	}
	
	// 移动横向滚动条
	function _moveHs(target,opts,startPos,endPos){
		var sheetOpts = _getCurrentSheetOpts(opts),
			hscrollMaxLeft = opts.hscrollW - sheetOpts.hscrollFillW,
			paneMaxLeft = sheetOpts.allColumnW - opts.paneW;
		sheetOpts.left = sheetOpts.tempLeft + parseInt((endPos.x - startPos.x)*paneMaxLeft/hscrollMaxLeft);
		_resetHsOffset(target, sheetOpts.left);
	}
	
	// 移动纵向滚动条
	function _moveVs(target,opts,startPos,endPos){
		var sheetOpts = _getCurrentSheetOpts(opts),
			vscrollMaxTop = opts.vscrollH - sheetOpts.vscrollFillH,
			paneMaxTop = sheetOpts.allRowH - opts.paneH;
		sheetOpts.top = sheetOpts.tempTop + parseInt((endPos.y - startPos.y)*paneMaxTop/vscrollMaxTop);
		_resetVsOffset(target, sheetOpts.top);
	}
	
	// 横向滚动条
	function _initHsOC(target,parent,opts){
		var hs1OC = _div("ui-spreadsheet-hsOC ui-spreadsheet-hs1OC").appendTo(parent);
		
		var hsOC = _div("ui-spreadsheet-hsOC").appendTo(parent);
		$("<img class=\"ui-spreadsheet-hscroll-left\" width=\"17\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC);
		var hsOC_bg = _div("ui-spreadsheet-hscroll-bg").appendTo(hsOC);
		var hsOC_slider = _div("ui-spreadsheet-hscroll-slider").appendTo(hsOC_bg);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-left\" width=\"4\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-hfill\" width=\"1\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-hcenter\" width=\"8\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-hfill\" width=\"1\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-gridScrollImg-el-right\" width=\"4\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC_slider);
		$("<img class=\"ui-spreadsheet-hscroll-right\" width=\"17\" height=\"17\" src=\""+opts.s_src+"\">").appendTo(hsOC);
		
		$("<div class=\"x-resizable-handle x-resizable-handle-west x-unselectable\" style=\"-moz-user-select: none; opacity: 0;\"></div>").appendTo(hsOC);
		
		hsOC_bg.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			opts.hscrollHold = false;
			
			// TODO 单击
			
		})
		.mousemove(function(e){
			if(opts.hscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveHs(target,opts,opts.scrollStartPosition,pos);
			}
		})
		.mouseup(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			if(opts.hscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveHs(target,opts,opts.scrollStartPosition,pos);
			}
			
			opts.hscrollHold = false;
		});
		
		hsOC_slider.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			var pos = Flywet.getMousePosition(e);
			
			opts.hscrollHold = true;
			opts.scrollStartPosition = pos;
			
			var sheetOpts = _getCurrentSheetOpts(opts);
			sheetOpts.tempLeft = sheetOpts.left;
			
			e.stopPropagation();
            e.preventDefault();
		})
		.mousemove(function(e){
			
			if(opts.hscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveHs(target,opts,opts.scrollStartPosition,pos);
			}
			
			e.stopPropagation();
            e.preventDefault();
		})
		.mouseup(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			if(opts.hscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveHs(target,opts,opts.scrollStartPosition,pos);
			}
			
			opts.hscrollHold = false;
			
			e.stopPropagation();
            e.preventDefault();
		});
		
		hsOC.hide();
		hs1OC.hide();
		
		return [hsOC,hs1OC];
	}
	
	// 列头
	function _initColHdrs(target,parent,opts,sheetOpts){
		var gridColHdrsOC = _div("ui-spreadsheet-gridColHdrsOC").appendTo(parent);
		var gridColHdrsIC = _div("ui-spreadsheet-gridColHdrsIC").appendTo(gridColHdrsOC);
		
		var col,width;
		// 列头显示
		createColHdrs("ui-spreadsheet-gridColHdr","hc",true);
		
		// 列头选中
		createColHdrs("ui-spreadsheet-gridColHdr ui-spreadsheet-col-selected","hcs");
		
		// 列头激活
		createColHdrs("ui-spreadsheet-gridColHdr ui-spreadsheet-col-active","hca");
		
		function createColHdrs(cls,idPrefix,show){
			var left = 0;
			for(var i=0;i<sheetOpts.colNum;i++){
				width = _getColWidth(sheetOpts,i);
				col = _div(cls,s10t26(i+1)).appendTo(gridColHdrsIC);
				if(!show){
					col.hide();
				}
				col.css({
					left: left+"px",
					width: (width-5)+"px"
				}).attr("id",idPrefix+"_"+i);
				left = left + width;
			}
		}
		
		return gridColHdrsOC;
	}
	
	// 行头
	function _initRowHdrs(target,parent,opts,sheetOpts){
		var gridRowHdrsOC = _div("ui-spreadsheet-gridRowHdrsOC").appendTo(parent);
		var gridRowHdrsIC = _div("ui-spreadsheet-gridRowHdrsIC").appendTo(gridRowHdrsOC);
		
		var row,height,top = 0;
		// 行头显示
		createRowHdrs("ui-spreadsheet-gridRowHdr","hr",true);
		
		// 行头选中
		createRowHdrs("ui-spreadsheet-gridRowHdr ui-spreadsheet-row-selected","hrs");
		
		// 行头激活
		createRowHdrs("ui-spreadsheet-gridRowHdr ui-spreadsheet-row-active","hra");
		
		function createRowHdrs(cls,idPrefix,show){
			var top = 0;
			for(var i=0;i<sheetOpts.rowNum;i++){
				height = _getRowHeight(sheetOpts,i);
				row = _div(cls,(i+1)).appendTo(gridRowHdrsIC);
				if(!show){
					row.hide();
				}
				row.css({
					top: top+"px",
					height: (height-5)+"px"
				}).attr("id",idPrefix+"_"+i);
				top = top + height;
			}
		}
		
		return gridRowHdrsOC;
	}
	
	// 主表格区
	function _initPane(target,parent,opts,sheetOpts,saCell,colHdrs,rowHdrs){
		var opts = $.data(target, "spreadsheet").options;
		
		var paneOC = _div("ui-spreadsheet-paneOC").appendTo(parent);
		var paneIC = _div("ui-spreadsheet-paneIC").appendTo(paneOC);
		
		// 分隔线
		var col,width,left = 0;
		for(var i=0;i<sheetOpts.colNum;i++){
			width = _getColWidth(sheetOpts,i);
			col = _div("ui-spreadsheet-gridColSep").appendTo(paneIC);
			col.css({
				left: left+"px"
			});
			left = left + width;
		}
		
		// 行记录
		var row,cell,height,top=0;
		for(var i=0;i<sheetOpts.rowNum;i++){
			height = _getRowHeight(sheetOpts,i);
			row = _div("ui-spreadsheet-gridRow").appendTo(paneIC);
			row.css({
				top: top+"px",
				height: (height-5)+"px"
			}).attr("id","r_"+i);
			
			top = top + height;
			left = 0;
			for(var j=0;j<sheetOpts.colNum;j++){
				width = _getColWidth(sheetOpts,j);
				cell = _div("ui-spreadsheet-gridCell").appendTo(row);
				cell.css({
					left: left+"px",
					width: (width-5)+"px"
				}).attr("id","r_"+i+"_c_"+j);
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
		
		// 主区域事件
		paneIC.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			// 取得按下鼠标的坐标
			var pos = Flywet.getMousePosition(e,paneIC);
			var cpos = _getCellPositionByCoors(target,pos);
			
			opts.rangeHold = true;
			opts.vscrollHold = false;
			opts.hscrollHold = false;
			
			_showRange(cpos,cpos);
			
			e.stopPropagation();
            e.preventDefault();
		})
		.mousemove(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			if(opts.rangeEdgeHold){
				var pos = Flywet.getMousePosition(e,paneIC);
				var cpos = _getCellPositionByCoors(target,pos);
				var d = (Math.abs(Flywet.cssNum(re,"left")-pos.x)>Math.abs(Flywet.cssNum(re,"top")-pos.y));
				_showFillRange(opts.rangeStartPosition,opts.rangeEndPosition,cpos,d);
			}
			else if(opts.rangeHold){
				var pos = Flywet.getMousePosition(e,paneIC);
				var cpos = _getCellPositionByCoors(target,pos);
				
				_showRange(opts.rangeStartPosition,cpos);
			}
			
			// 如果横向滚动条hold
			if(opts.hscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveHs(target,opts,opts.scrollStartPosition,pos);
			}
			
			// 如果纵向滚动条hold
			if(opts.vscrollHold){
				var pos = Flywet.getMousePosition(e);
				_moveVs(target,opts,opts.scrollStartPosition,pos);
			}
			
			e.stopPropagation();
            e.preventDefault();
		})
		.mouseup(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			if(opts.rangeEdgeHold){
				var pos = Flywet.getMousePosition(e,paneIC);
				var cpos = _getCellPositionByCoors(target,pos);
				var d = (Math.abs(Flywet.cssNum(re,"left")-pos.x)>Math.abs(Flywet.cssNum(re,"top")-pos.y));
				var fr = _showFillRange(opts.rangeStartPosition,opts.rangeEndPosition,cpos,d);
				
				opts.rangeEdgeHold = false;
				
				_showRange(fr.spos,fr.epos,opts.rangeType);
				_hideFillRange();
			}
			else if(opts.rangeHold){
				var pos = Flywet.getMousePosition(e,paneIC);
				var cpos = _getCellPositionByCoors(target,pos);
				
				opts.rangeHold = false;
				
				_showRange(opts.rangeStartPosition,cpos);
			}
			
			opts.vscrollHold = false;
			opts.hscrollHold = false;
			
			e.stopPropagation();
            e.preventDefault();
		});
		
		re.mousedown(function(e){
			// 确保是鼠标左键
			if (e.which != 1) {return;}
			
			opts.rangeEdgeHold = true;
			
			e.stopPropagation();
            e.preventDefault();
		});
		
		// 列头事件
		if(sheetOpts.showColHead){
			var colHdrsIC = colHdrs.find(".ui-spreadsheet-gridColHdrsIC");
			
			colHdrsIC.mousedown(function(e){
				// 确保是鼠标左键
				if (e.which != 1) {return;}
				
				var pos = Flywet.getMousePosition(e,colHdrsIC);
				var cpos = _getColPositionByCoors(target,pos);
				
				if(cpos.neer){
					opts.colResizeHeadHold = true;
				}else{
					opts.colHeadHold = true;
					_showColsRange(cpos.cidx,cpos.cidx);
				}
			})
			.mousemove(function(e){
				var pos = Flywet.getMousePosition(e,colHdrsIC);
				var cpos = _getColPositionByCoors(target,pos);
				
				if(opts.colHeadHold){
					_showColsRange(opts.rangeStartColIndex,cpos.cidx);
				}else if(opts.colResizeHeadHold){
					console.log("colResizeHeadHold");
				}else{
					_showColHeadCursor(cpos);
				}
				
				e.stopPropagation();
	            e.preventDefault();
			})
			.mouseup(function(e){
				// 确保是鼠标左键
				if (e.which != 1) {return;}
				
				var pos = Flywet.getMousePosition(e,colHdrsIC);
				var cpos = _getColPositionByCoors(target,pos);
				
				if(opts.colHeadHold){
					_showColsRange(opts.rangeStartColIndex,cpos.cidx);
					opts.colHeadHold = false;
				}else if(opts.colResizeHeadHold){
					
					opts.colResizeHeadHold = false;
				}
				
				e.stopPropagation();
	            e.preventDefault();
			});
		}
		
		// 行头事件
		if(sheetOpts.showRowHead){
			var rowHdrsIC = rowHdrs.find(".ui-spreadsheet-gridRowHdrsIC");
			
			rowHdrsIC.mousedown(function(e){
				// 确保是鼠标左键
				if (e.which != 1) {return;}
				
				var pos = Flywet.getMousePosition(e,rowHdrsIC);
				var rpos = _getRowPositionByCoors(target,pos);
				
				if(rpos.neer){
					opts.rowResizeHeadHold = true;
				}else{
					opts.rowHeadHold = true;
					_showRowsRange(rpos.ridx,rpos.ridx);
				}
				
				e.stopPropagation();
	            e.preventDefault();
			})
			.mousemove(function(e){
				var pos = Flywet.getMousePosition(e,rowHdrsIC);
				var rpos = _getRowPositionByCoors(target,pos);
				
				if(opts.rowHeadHold){
					_showRowsRange(opts.rangeStartRowIndex,rpos.ridx);
				}else if(opts.rowResizeHeadHold){
					console.log("rowResizeHeadHold");
				}else{
					_showRowHeadCursor(rpos);
				}
				
				e.stopPropagation();
	            e.preventDefault();
			})
			.mouseup(function(e){
				// 确保是鼠标左键
				if (e.which != 1) {return;}
				
				var pos = Flywet.getMousePosition(e,rowHdrsIC);
				var rpos = _getRowPositionByCoors(target,pos);
				
				if(opts.rowHeadHold){
					_showRowsRange(opts.rangeStartRowIndex,rpos.ridx);
					opts.rowHeadHold = false;
				}else if(opts.rowResizeHeadHold){
					
					opts.rowResizeHeadHold = false;
				}
				
				e.stopPropagation();
	            e.preventDefault();
			});
		}
		
		// 改变列头鼠标
		function _showColHeadCursor(cpos){
			var col = colHdrs.find("#hc_"+cpos.cidx);
			if(cpos.neer){
				$(col).addClass("ui-spreadsheet-col-resize");
			}else{
				$(col).removeClass("ui-spreadsheet-col-resize");
			}
		}
		
		// 改变行头鼠标
		function _showRowHeadCursor(rpos){
			var row = rowHdrs.find("#hr_"+rpos.ridx);
			if(rpos.neer){
				$(row).addClass("ui-spreadsheet-row-resize");
			}else{
				$(row).removeClass("ui-spreadsheet-row-resize");
			}
		}
		
		// 通过Cell坐标获得Cell
		function _getCellCss(pos){
			var row = paneIC.find("#r_"+pos.ridx);
			var cell = $(row).find("#r_"+pos.ridx+"_c_"+pos.cidx);
			return {
				top : Flywet.cssNum(row,"top")
				,left : Flywet.cssNum(cell,"left")
			};
		}
		
		// 隐藏虚线框
		function _hideFillRange(){
			fv1.hide();
			fv2.hide();
			fh1.hide();
			fh2.hide();
		}
		
		// 显示虚线框
		function _showFillRange(spos, epos, pos, d){
			var sheetOpts = _getCurrentSheetOpts(opts);
			var spos_n = {
				ridx : Math.min(spos.ridx,epos.ridx)
				,cidx : Math.min(spos.cidx,epos.cidx)
			};
			var epos_n = {
				ridx : Math.max(spos.ridx,epos.ridx)
				,cidx : Math.max(spos.cidx,epos.cidx)
			};
			var startPos,endPos;
			var inset = false;
			// 内部
			if(pos.ridx>=spos_n.ridx && pos.ridx<=epos_n.ridx
				&& pos.cidx>=spos_n.cidx && pos.cidx<=epos_n.cidx){
				if(d){
					startPos = {
						ridx :spos_n.ridx
						,cidx : spos_n.cidx
					};
					endPos = {
						ridx : epos_n.ridx
						,cidx : pos.cidx
					};
				}else{
					startPos = {
						ridx : spos_n.ridx
						,cidx : spos_n.cidx
					};
					endPos = {
						ridx : pos.ridx
						,cidx : epos_n.cidx
					};
				}
				
				inset = true;
			}else{
				if(d){
					startPos = {
						ridx : spos_n.ridx
						,cidx : Math.min(spos_n.cidx,pos.cidx)
					};
					endPos = {
						ridx : epos_n.ridx
						,cidx : Math.max(epos_n.cidx,pos.cidx)
					};
				}else{
					startPos = {
						ridx : Math.min(spos_n.ridx,pos.ridx)
						,cidx : spos_n.cidx
					};
					endPos = {
						ridx : Math.max(epos_n.ridx,pos.ridx)
						,cidx : epos_n.cidx
					};
				}
			}
			
			var scss = _getCellCss(startPos),
			ecss = _getCellCss(endPos);
			
			fv1.css({
				left: (scss.left-1)+"px"
				,top: (scss.top-1)+"px"
				,width: "3px"
				,height: (_getRowsHeight(sheetOpts,startPos.ridx,endPos.ridx)+2)+"px"
			}).show();
			
			var fv2Width = 3;
			if(inset && d){
				fv2Width = fv2Width + _getColsWidth(sheetOpts,(endPos.cidx+1),epos_n.cidx);
			}
			fv2.css({
				left: (ecss.left+_getColWidth(sheetOpts,endPos.cidx)-1)+"px"
				,top: (scss.top-1)+"px"
				,width: fv2Width+"px"
				,height: (_getRowsHeight(sheetOpts,startPos.ridx,endPos.ridx)+2)+"px"
			}).show();
			
			fh1.css({
				left: (scss.left-1)+"px"
				,top: (scss.top-1)+"px"
				,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx))+"px"
				,height: "3px"
			}).show();
			
			var fh2Height = 3;
			if(inset && !d){
				fh2Height = fh2Height + _getRowsHeight(sheetOpts,(endPos.ridx+1),epos_n.ridx);
			}
			fh2.css({
				left: (scss.left-1)+"px"
				,top: (ecss.top+_getRowHeight(sheetOpts,endPos.ridx)-1)+"px"
				,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx)+3)+"px"
				,height: fh2Height+"px"
			}).show();
			
			return {spos:startPos,epos:endPos};
		}
		
		function _showRowsRange(srid,erid){
			var sheetOpts = _getCurrentSheetOpts(opts);
			var spos = {
				ridx : srid
				,cidx : 0
			};
			var epos = {
				ridx : erid
				,cidx : (sheetOpts.colNum-1)
			};
			_showRange(spos,epos,"row");
			opts.rangeStartRowIndex = srid;
			opts.rangeEndRowIndex = erid;
		}
		
		function _showColsRange(scid,ecid){
			var sheetOpts = _getCurrentSheetOpts(opts);
			var spos = {
				ridx : 0
				,cidx : scid
			};
			var epos = {
				ridx : (sheetOpts.rowNum-1)
				,cidx : ecid
			};
			_showRange(spos,epos,"col");
			opts.rangeStartColIndex = scid;
			opts.rangeEndColIndex = ecid;
		}
		
		// 显示选中框
		function _showRange(spos,epos,type){
			var sheetOpts = _getCurrentSheetOpts(opts);
			opts.rangeStartPosition = spos;
			opts.rangeEndPosition = epos;
			opts.rangeType = type || "cell";
			
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
				,height: (_getRowsHeight(sheetOpts,startPos.ridx,endPos.ridx)+2)+"px"
			}).show();
			
			if(type == "col"){
				bv2.css({
					left: (ecss.left+_getColWidth(sheetOpts,endPos.cidx)-2)+"px"
					,top: (scss.top-2)+"px"
					,width: "5px"
					,height: (_getRowsHeight(sheetOpts,startPos.ridx,endPos.ridx)+2)+"px"
				}).show();
			}else{
				bv2.css({
					left: (ecss.left+_getColWidth(sheetOpts,endPos.cidx)-2)+"px"
					,top: (scss.top-1)+"px"
					,width: "5px"
					,height: (_getRowsHeight(sheetOpts,startPos.ridx,endPos.ridx)+2)+"px"
				}).show();
			}
			
			bh1.css({
				left: (scss.left-1)+"px"
				,top: (scss.top-2)+"px"
				,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx)+2)+"px"
				,height: "5px"
			}).show();
			
			if(type == "row"){
				bh2.css({
					left: (scss.left-2)+"px"
					,top: (ecss.top+_getRowHeight(sheetOpts,endPos.ridx)-2)+"px"
					,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx)+2)+"px"
					,height: "5px"
				}).show();
			}else if(type == "col"){
				bh2.css({
					left: (scss.left-1)+"px"
					,top: (ecss.top+_getRowHeight(sheetOpts,endPos.ridx)-2)+"px"
					,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx)+2)+"px"
					,height: "5px"
				}).show();
			}else{
				bh2.css({
					left: (scss.left-1)+"px"
					,top: (ecss.top+_getRowHeight(sheetOpts,endPos.ridx)-2)+"px"
					,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx)+1)+"px"
					,height: "5px"
				}).show();
			}
			
			ac.css({
				left: scss_o.left+"px"
				,top: (scss_o.top+1)+"px"
				,width: (_getColWidth(sheetOpts,spos.cidx)-5)+"px"
				,height: (_getRowHeight(sheetOpts,spos.ridx)-6)+"px"
			}).show();
			
			if(type=="row"){
				re.css({
					left: "-1px"
					,top: (ecss.top+_getRowHeight(sheetOpts,endPos.ridx)-3)+"px"
				}).show();
			}else if(type=="col"){
				re.css({
					left: (ecss.left+_getColWidth(sheetOpts,endPos.cidx)-3)+"px"
					,top: "-1px"
				}).show();
			}else{
				re.css({
					left: (ecss.left+_getColWidth(sheetOpts,endPos.cidx)-3)+"px"
					,top: (ecss.top+_getRowHeight(sheetOpts,endPos.ridx)-3)+"px"
				}).show();
			}
			
			rbg.css({
				left: (scss.left+3)+"px"
				,top: (scss.top+3)+"px"
				,width: (_getColsWidth(sheetOpts,startPos.cidx,endPos.cidx)-5)+"px"
				,height: (_getRowsHeight(sheetOpts,startPos.ridx,endPos.ridx)-5)+"px"
			}).show();
			
			// 行头
			if(sheetOpts.showRowHead){
				rowHdrs.find(".ui-spreadsheet-row-active").hide();
				rowHdrs.find(".ui-spreadsheet-row-selected").hide();
				
				if(type=="row"){
					for(var i=startPos.ridx;i<=endPos.ridx;i++){
						$(rowHdrs.find("#hrs_"+i)).show();
					}
				}else{
					for(var i=startPos.ridx;i<=endPos.ridx;i++){
						$(rowHdrs.find("#hra_"+i)).show();
					}
				}
			}
			
			// 列头
			if(sheetOpts.showColHead){
				colHdrs.find(".ui-spreadsheet-col-active").hide();
				colHdrs.find(".ui-spreadsheet-col-selected").hide();
				if(type=="col"){
					for(var i=startPos.cidx;i<=endPos.cidx;i++){
						$(colHdrs.find("#hcs_"+i)).show();
					}
				}else{
					for(var i=startPos.cidx;i<=endPos.cidx;i++){
						$(colHdrs.find("#hca_"+i)).show();
					}
				}
			}
		}
		
		return paneOC;
	}
	
	// 获得鼠标点击位置的行坐标
	function _getRowPositionByCoors(target,pos){
		var opts = $.data(target, "spreadsheet").options;
		var sheetOpts = _getCurrentSheetOpts(opts);
		var ty = pos.y, ridx = 0;
		while(ty>0){
			ty = ty - _getRowHeight(sheetOpts, ridx);
			ridx++;
		}
		ridx--;
		
		ridx = Math.max(0,ridx);
		
		return {
			ridx : ridx
			,neer : (ridx!=0) && (Math.abs(ty)<=2 || (_getRowHeight(sheetOpts, ridx)+ty)<=2)
		};
	}
	
	// 获得鼠标点击位置的列坐标
	function _getColPositionByCoors(target,pos){
		var opts = $.data(target, "spreadsheet").options;
		var sheetOpts = _getCurrentSheetOpts(opts);
		var tx = pos.x, cidx = 0;
		while(tx>0){
			tx = tx - _getColWidth(sheetOpts, cidx);
			cidx++;
		}
		cidx--;
		
		cidx = Math.max(0,cidx);
		
		return {
			cidx : cidx
			,neer : (cidx!=0) && (Math.abs(tx)<=2 || (_getColWidth(sheetOpts, cidx)+tx)<=2)
		};
	}
	
	// 获得鼠标点击位置的Cell坐标
	function _getCellPositionByCoors(target,pos){
		var opts = $.data(target, "spreadsheet").options;
		var sheetOpts = _getCurrentSheetOpts(opts);
		var tx = pos.x, ty = pos.y, ridx = 0, cidx = 0;
		while(tx>0){
			tx = tx - _getColWidth(sheetOpts, cidx);
			cidx++;
		}
		while(ty>0){
			ty = ty - _getRowHeight(sheetOpts, ridx);
			ridx++;
		}
		
		return {
			ridx : Math.max(0,(ridx-1))
			,cidx : Math.max(0,(cidx-1))
		};
	}
	
	// 根据Cell坐标获得Cell对象
	function _getCellByPosition(target,pos){
		var cs = _getCurrentSheet(target);
		var row = cs.find("#r_"+pos.ridx);
		return $(row).find("#r_"+pos.ridx+"_c_"+pos.cidx);
	}
	
	function _initSheet(target,parent,opts,sheetOpts,w,h){
		var sheet = _div("ui-spreadsheet-sheet").appendTo(parent);
		sheetOpts.sheet = sheet;
		
		// 左上角全选区
		if(sheetOpts.showColHead && sheetOpts.showRowHead){
			var saCell = _div("ui-spreadsheet-gridSelectAll").appendTo(sheet);
			sheetOpts.saCell = saCell;
		}
		
		// 列头
		if(sheetOpts.showColHead){
			var colHdrs = _initColHdrs(target,sheet,opts,sheetOpts);
			sheetOpts.colHdrs = colHdrs;
			sheetOpts.colHdrs.css({
				left: ((sheetOpts.showRowHead)?opts.headRowWidth:0)+"px"
				,width: ((sheetOpts.showRowHead)?(w-opts.headRowWidth):w)+"px"
			});
		}
		
		// 行头
		if(sheetOpts.showRowHead){
			var rowHdrs = _initRowHdrs(target,sheet,opts,sheetOpts);
			sheetOpts.rowHdrs = rowHdrs;
			sheetOpts.rowHdrs.css({
				top: ((sheetOpts.showColHead)?opts.headColHeight:0)+"px"
				,height: ((sheetOpts.showColHead)?(h-opts.headColHeight):h)+"px"
			});
		}
		
		// 主表格区
		var pane = _initPane(target,sheet,opts,sheetOpts,saCell,colHdrs,rowHdrs);
		sheetOpts.pane = pane;
		sheetOpts.pane.css({
			left: ((sheetOpts.showRowHead)?opts.headRowWidth:0)+"px"
			,top: ((sheetOpts.showColHead)?opts.headColHeight:0)+"px"
			,width: ((sheetOpts.showRowHead)?(w-opts.headRowWidth):w)+"px"
			,height: ((sheetOpts.showColHead)?(h-opts.headColHeight):h)+"px"
		});
		
		sheet.hide();
		
		return sheet;
	}
	
	function _initSheets(target,parent,opts){
		return _div("ui-spreadsheet-sheets").appendTo(parent);
	}
	
	// sheet选择器
	function _initSelector(target,parent,opts){
		var selector = _div("ui-spreadsheet-sheetSelectorOC").appendTo(parent);
		var selectorTB = _div("ui-spreadsheet-sheetSelectorTB").appendTo(selector);
		_initToolbar(target,selectorTB,opts);
		
		var selectorIC = _div("ui-spreadsheet-sheetSelectorIC").appendTo(selector);
		_initSelectorPane(target,selectorIC,opts);
		
		selector.hide();
		
		return selector;
	}
	
	// selectorPane
	function _initSelectorPane(target,parent,opts){
		var tabpanel = _div("ui-spreadsheet-tabpanel").appendTo(parent);
		var tabpanelCt = _div("ui-spreadsheet-tabpanel-innerCt").appendTo(tabpanel);
		
		for(var i=0;i<opts.sheet.length;i++){
			_initSelectorItem(target,tabpanelCt,opts.sheet[i],(i==opts.currentSheetIndex));
		}
		
	}
	
	// selector item
	function _initSelectorItem(target,parent,sheetOpts,selected){
		var sheet = $("<div class='ui-spreadsheet-tabpanel-item'><em><button type='button'><span class='ui-spreadsheet-tab-inner'>"+sheetOpts.sheetName+"</span></button></em></div>");
		if(selected){
			sheet.addClass("ui-spreadsheet-tab-active");
		}
		sheet.appendTo(parent);
	}
	
	// toolbar
	function _initToolbar(target,parent,opts){
		var toolbar = _div("ui-spreadsheet-toolbar").appendTo(parent);
		var toolbarInnerCt = _div("ui-spreadsheet-toolbar-innerCt x-box-inner").appendTo(toolbar);
		
		var btns = [{
    		componentType : "fly:PushButton",
    		type : "button",
    		iconCls : "ui-icon-triangle-2-w-w"
    	},{
    		componentType : "fly:PushButton",
    		type : "button",
    		iconCls : "ui-icon-triangle-1-w"
    	},{
    		componentType : "fly:PushButton",
    		type : "button",
    		iconCls : "ui-icon-triangle-1-e"
    	},{
    		componentType : "fly:PushButton",
    		type : "button",
    		iconCls : "ui-icon-triangle-2-e-e"
    	}];
		Flywet.autocw(btns,toolbarInnerCt);
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
		br.hide();
		
		// selector
		var selector = _initSelector(target,book,opts);
		
		// hs
		var hs = _initHsOC(target,book,opts);
		
		workspace.bind("_resize", function() {
			var opts = $.data(target, "spreadsheet").options;
			if (opts.fit == true) {
				_rerender(target);
			}
			return false;
		});
		
		return {
			workspace : workspace
			,book : book
			,vs : vs
			,sheets : sheets
			,selector : selector
			,hs : hs
			,br : br
		};
	}
	
	// 计算显示尺寸
	function _calSize(target){
		var opts = $.data(target, "spreadsheet").options;
		
		var dim=Flywet.getElementDimensions(target);
		if(dim.css.width>0 && dim.css.height>0){
			dim = {width: dim.css.width, height: dim.css.height};
		}else{
			var parent=$(target).parent();
			if(parent.get(0).tagName === "BODY"){
				dim = Flywet.getWindowScroll();
				dim = {width: dim.width, height: dim.height};
			}else{
				dim = Flywet.getElementDimensions(parent);
				dim = {width: dim.css.width, height: dim.css.height};
			}
		}
		
		// 自定义尺寸，或者使用指定尺寸
		if(opts.width != "auto" && Flywet.isNumber(opts.width)){
			opts.width = parseInt(opts.width);
		}else{
			opts.width = dim.width;
		}
		
		if(opts.height != "auto" && Flywet.isNumber(opts.height)){
			opts.height = parseInt(opts.height);
		}else{
			opts.height = dim.height;
		}
		
		opts.workspaceWidth = opts.width - 2;
		opts.workspaceHeight = opts.height - 2;
		
		// 重置滚动条显示设置
		opts.showHScroll = false;
		opts.showVScroll = false;
		
		// 如果sheet多于1个，必须出现横向纵向滚动条
		if(_getSheetNum(opts)>1){
			opts.showHScroll = true;
			opts.showVScroll = true;
		}
		
		// 调整行列数
		for(var i=0;i<_getSheetNum(opts);i++){
			var sheetOpts = opts.sheet[i];
			// 用户指定的行列数
			sheetOpts.colNum = parseInt(sheetOpts.colNum);
			sheetOpts.rowNum = parseInt(sheetOpts.rowNum);
			
			// 对于没有设置行列数，采用默认的行列数
			var pos = _getCellPositionByCoors(target,{
				x:_getPaneWidth(opts,sheetOpts)
				,y:_getPaneHeight(opts,sheetOpts)
			});
			if(sheetOpts.colNum<1){
				sheetOpts.colNum = pos.cidx + opts.offsetCellNumber;
			}
			if(sheetOpts.rowNum<1){
				sheetOpts.rowNum = pos.ridx + opts.offsetCellNumber;
			}
		}
		
		// 对于只有一个sheet的情况，可以判断滚动条是否显示，如果多于1个sheet，则必须显示滚动条
		if(_getSheetNum(opts)==1){
			var sheetOpts = opts.sheet[0];
			// 判断是否显示滚动条 ,根据高度判断
			var allColsWidth = _getColsWidth(sheetOpts,0,(sheetOpts.colNum-1)),
				paneWidth = _getPaneWidth(opts,sheetOpts),
				allRowsHeight = _getRowsHeight(sheetOpts,0,(sheetOpts.rowNum-1)),
				paneHeight = _getPaneHeight(opts,sheetOpts);
			if(allColsWidth > paneWidth){
				opts.showHScroll = true;
			}
			if(allRowsHeight > paneHeight){
				opts.showVScroll = true;
			}
		}	
	}
	
	// 获得工作薄数量
	function _getSheetNum(opts){
		return opts.sheet.length;
	}
	
	// 获得当前sheet的opts
	function _getCurrentSheetOpts(opts){
		return opts.sheet[opts.currentSheetIndex];
	}
	
	// 获得当前sheet的opts
	function _getCurrentSheet(target){
		var sheet = $.data(target, "spreadsheet").sheet;
		return sheet.get(opts.currentSheetIndex);
	}
	
	// 切换Sheet页
	function _shiftSheet(target, sheetIdx){
		var ss = $.data(target, "spreadsheet"),
			opts = ss.options;
		// 切换选项卡 TODO
		// 切换sheet页
		if(opts.currentSheetIndex == sheetIdx){
			ss.sheet[sheetIdx].show();
		}else{
			ss.sheet[opts.currentSheetIndex].hide();
			ss.sheet[sheetIdx].show();
			opts.currentSheetIndex = sheetIdx;
		}
		
		// vs
		if(opts.showVScroll){
			_resizeVs(target);
		}
		
		// hs
		if(opts.showHScroll){
			_resizeHs(target);
		}
	}
	
	// 获得表格有效区域的宽度
	function _getPaneWidth(opts,sheetOpts){
		var paneWidth = opts.workspaceWidth;
		if(sheetOpts.showRowHead){
			paneWidth = paneWidth - opts.headRowWidth;
		}
		if(opts.showVScroll){
			paneWidth = paneWidth - opts.vscrollWidth;
		}
		return paneWidth;
	}
	
	// 获得表格有效区域的宽度
	function _getPaneHeight(opts,sheetOpts){
		var paneHeight = opts.workspaceHeight;
		if(sheetOpts.showColHead){
			paneHeight = paneHeight - opts.headColHeight;
		}
		if(opts.showHScroll){
			paneHeight = paneHeight - opts.hscrollHeight;
		}
		return paneHeight;
	}
	
	// 调整纵向滚动条大小
	function _resizeVs(target){
		var ss = $.data(target, "spreadsheet"),
			opts = ss.options,
			sheetOpts = _getCurrentSheetOpts(opts),
			h = opts.vscrollHeight;
		
		var vscrollH = (h-opts.hscrollHeight*2),
			paneH = _getPaneHeight(opts,sheetOpts),
			allRowH = _getRowsHeight(sheetOpts,0,(sheetOpts.rowNum-1));
		
		opts.vscrollH = vscrollH;// 滚动条去头尾有效高度
		opts.paneH = paneH;// 显示主区域有效高度
		sheetOpts.allRowH = allRowH; // 当前所有行高度
		
		if(paneH>allRowH){
			ss.vs[1].height(h);
			ss.vs[0].hide();
			ss.vs[1].hide();
		}else{
			ss.vs[0].height(h);
			ss.vs[0].find(".ui-spreadsheet-vscroll-bg").height(vscrollH);
			var elh = vscrollH * paneH / allRowH;
			var vfillh = parseInt((elh-16)/2);
			vfillh = Math.max(vfillh,1);
			ss.vs[0].find(".ui-spreadsheet-gridScrollImg-el-vfill").height(vfillh);
			ss.vs[0].show();
			ss.vs[1].hide();
			
			sheetOpts.vscrollFillH = 16 + vfillh*2;
			
			_resetVsOffset(target, sheetOpts.top);
		}
		
	}
	
	// 重置纵向滚动条浮动位置
	function _resetVsOffset(target,top){
		var ss = $.data(target, "spreadsheet"),
			opts = ss.options,
			sheetOpts = _getCurrentSheetOpts(opts);
		
		var vscrollMaxTop = opts.vscrollH - sheetOpts.vscrollFillH,
			paneMaxTop = sheetOpts.allRowH - opts.paneH;
		
		top = Math.max(0,top);
		top = Math.min(paneMaxTop,top);
		
		var sTop = parseInt(top * vscrollMaxTop / paneMaxTop);
		
		ss.vs[0].find(".ui-spreadsheet-vscroll-slider").css("top", sTop+"px");
		
		//pane
		sheetOpts.pane.find(".ui-spreadsheet-paneIC").css("top",(top*(-1))+"px");
		
		//rowHdrs
		if(sheetOpts.showRowHead){
			sheetOpts.rowHdrs.find(".ui-spreadsheet-gridRowHdrsIC").css("top",(top*(-1))+"px");
		}
		
	}
	
	// 调整横向滚动条大小
	function _resizeHs(target){
		var ss = $.data(target, "spreadsheet"),
			opts = ss.options,
			sheetOpts = _getCurrentSheetOpts(opts),
			w = opts.hscrollWidth - 2;
		
		var hscrollW = (w-opts.vscrollWidth*2),
			paneW = _getPaneWidth(opts,sheetOpts),
			allColumnW = _getColsWidth(sheetOpts,0,(sheetOpts.colNum-1));
		
		opts.hscrollW = hscrollW;// 滚动条去头尾有效宽度
		opts.paneW = paneW;// 显示主区域有效宽度
		sheetOpts.allColumnW = allColumnW; // 当前所有行宽度
		
		if(paneW>allColumnW){
			ss.hs[1].width(w);
			ss.hs[0].hide();
			ss.hs[1].show();
		}else{
			ss.hs[0].width(w);
			ss.hs[0].find(".ui-spreadsheet-hscroll-bg").width(hscrollW);
			var elh = hscrollW * paneW / allColumnW;
			var hfillw = parseInt((elh-16)/2);
			hfillw = Math.max(hfillw,1);
			ss.hs[0].find(".ui-spreadsheet-gridScrollImg-el-hfill").width(hfillw);
			ss.hs[0].show();
			ss.hs[1].hide();
			
			sheetOpts.hscrollFillW = 16 + hfillw*2;
			
			_resetHsOffset(target, sheetOpts.left);
		}
	}
	
	// 重置横向滚动条浮动位置
	function _resetHsOffset(target,left){
		var ss = $.data(target, "spreadsheet"),
			opts = ss.options,
			sheetOpts = _getCurrentSheetOpts(opts);
	
		var hscrollMaxLeft = opts.hscrollW - sheetOpts.hscrollFillW,
			paneMaxLeft = sheetOpts.allColumnW - opts.paneW;
		
		left = Math.max(0,left);
		left = Math.min(paneMaxLeft,left);
		
		var sLeft = parseInt(left * hscrollMaxLeft / paneMaxLeft);
		
		ss.hs[0].find(".ui-spreadsheet-hscroll-slider").css("left", sLeft+"px");
		
		//pane
		sheetOpts.pane.find(".ui-spreadsheet-paneIC").css("left",(left*(-1))+"px");
		
		//rowHdrs
		if(sheetOpts.showColHead){
			sheetOpts.colHdrs.find(".ui-spreadsheet-gridColHdrsIC").css("left",(left*(-1))+"px");
		}
	}
	
	function _rerender(target){
		var ss = $.data(target, "spreadsheet");
		var opts = ss.options;
		
		_calSize(target);
		
		// 总高度workspace
		var w = opts.workspaceWidth, 
			h = opts.workspaceHeight;
		ss.workspace.width(w).height(h);
		
		// 排除滚动条尺寸
		if(opts.showVScroll){
			w = w - opts.vscrollWidth;
		}
		if(opts.showHScroll){
			h = h - opts.hscrollHeight;
		}
		
		// vs
		if(opts.showVScroll){
			opts.vscrollHeight = h;
		}
		
		// hs
		if(opts.showHScroll){
			// 当多于1个sheet页时，要出现sheet页选择器
			if(_getSheetNum(opts)>1){
				// selector
				ss.selector.show();
				var selectorWidth = parseInt( w * 0.6 );
				ss.selector.width(selectorWidth);
				
				// hs
				opts.hscrollWidth = w-selectorWidth;
			}else{
				ss.selector.hide();
				// hs
				opts.hscrollWidth = w;
			}
		}
		
		// 右下角
		if(opts.showVScroll && opts.showHScroll){
			ss.br.show();
		}
		
		// sheets
		ss.sheets.width(w).height(h);
		ss.sheets.find(".ui-spreadsheet-sheet").width(w).height(h);
		
		// 多个Sheet页
		ss.sheets.empty();
		var sheet = [];
		for(var i=0;i<_getSheetNum(opts);i++){
			sheet.push(_initSheet(target,ss.sheets,opts,opts.sheet[i],w,h));
		}
		ss.sheet = sheet;
		
		// 当前sheet页显示
		_shiftSheet(target,opts.currentSheetIndex);
		
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
			return $.fn.spreadsheet.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var t = $.data(this, "spreadsheet");
			var opts,sheet;
			if(t){
				opts = $.extend(t.options, options);
			}else{
				opts = $.extend(
						{},
						$.fn.spreadsheet.defaults,
						$.fn.spreadsheet.parseOptions(this),
						options
					);
				
				// 初始化sheet配置
				sheet = opts.sheet;
				if(!sheet){
					sheet = [{}];
				}
				
				for(var i=0;i<sheet.length;i++){
					sheet[i] = $.extend(
							{},
							$.fn.spreadsheet.sheetDefaults,
							sheet[i]
						);
				}
				opts.sheet = sheet;
				
				$.data(this, "spreadsheet", {
					options : 	opts
				});
				
				// 初始化页面元素
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
			
			// 重新绘制
			t.workspace.css("display", "block");
			_rerender(this);
			
			
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
			Flywet.parseOptions(target, ["id","width","height","show"])
		);
	};
	
	$.fn.spreadsheet.sheetDefaults = {
		
		defaultColWidth: 64		// 默认列宽
		,defaultRowHeight: 20 	// 默认行高
		
		// 是否显示表头
		,showColHead: true
		,showRowHead: true
		
		// 实际显示单元格数
		,rowNum: 0
		,colNum: 0
		
		// 滚动条起始cell位置
		,top : 0
		,left : 0
		
		,data : {
			colsWidth : {}
			,rowsHeight : {}
		}
	
	};
	
	$.fn.spreadsheet.defaults = {
		id : 		null
		,width :	"auto"
		,height :	"auto"
		,fit :		true
		,show :		true
		
		,s_src : "resources/images/default/s.gif"
		
		,headColHeight: 19 	// 列头高
		,headRowWidth: 41	// 行头宽
		
		,vscrollWidth: 17	// 纵向滚动条宽
		,vscrollHeight: 0	// 纵向滚动条高
		,hscrollHeight: 19	// 横向滚动条高
		,hscrollWidth: 0	// 横向滚动条宽
		
		// 是否显示滚动条，不能设置，由程序自己控制
//		,showHScroll: false 
//		,showVScroll: false
		
		,hscrollHold : false // 横向滚动条是否hold住
		,vscrollHold : false // 纵向滚动条是否hold住
		
		,offsetCellNumber: 3
		,currentSheetIndex: 0
		
		,rangeHold : false
		,rangeEdgeHold : false
		,rangeStartPosition : null
		,rangeEndPosition : null
		,rangeStartRowIndex : null
		,rangeEndRowIndex : null
		,rangeStartColIndex : null
		,rangeEndColIndex : null
		
		,rowHeadHold : false
		,colHeadHold : false
		,rowResizeHeadHold : false
		,colResizeHeadHold : false
		
		,onBeforeShow:	function(){}
		,onShow:	function(){}
		,onBeforeHide:	function(){}
		,onHide:	function(){}
	};
})(jQuery);


Flywet.widget.SpreadSheet = function(cfg){
	this.cfg = cfg;
	this.id = this.cfg.id;
	
	this.init();
};

Flywet.extend(Flywet.widget.SpreadSheet, Flywet.widget.BaseWidget);

Flywet.widget.SpreadSheet.prototype={
	init:function(){
		var cfg = {};
		$(Flywet.escapeClientId(this.id)).spreadsheet(cfg);
	}
};