/**
 * Clipboard Widget
 */
Plywet.widget.FlowChartClipboard = new function(){
	var steps = [];
	var hops = [];
	
	this.copyToClipboard = copyToClipboard;
	this.getFromClipboard = getFromClipboard;
	
	/**
	 * 复制元素到剪切板
	 */
	function copyToClipboard(el){
		steps = [];
		hops = [];
		if(el && el!=undefined){
			if(el.steps && el.steps!=undefined){
				steps = el.steps;
			}
			if(el.hops && el.hops!=undefined){
				hops = el.hops;
			}
		}
	}
	
	/**
	 * 从剪切板获得元素
	 */
	function getFromClipboard(){
		return {
			steps : steps,
			hops : hops
		};
	}
};

/**
 * Resources Widget
 */
Plywet.widget.FlowChartResources = new function(){
	// 资源ID的Index
	var resourceIndex = 0;
	
	// 资源ID的前缀
	var resourcePrex = "flowReource";
	
	// 资源Map
	var resourceMap = [];
	
	this.addAndFindResource = addAndFindResource;
	
	/**
	 * 增加一个资源凭证，同时返回资源ID
	 * 
	 * @param src
	 *            资源凭证
	 * @param d
	 *            插入资源到的Div对象
	 * @param o
	 *            插入资源的String
	 */
	function addAndFindResource(src){
		// 如果已经缓存
		for(var i=0;i<resourceMap.length;i++){
			var re = resourceMap[i];
			if(re.key == src){
				return re.value;
			}
		}
		
		// 如果没有缓存
		var id = resourcePrex + resourceIndex;
		resourceIndex = resourceIndex + 1;
		var value = new Image();
		value.id = id;
		value.src = src;
		resourceMap.push({
			key : src,
			value : value
		});
		return value;
	}
};

/**
 * FlowChart Widget
 */
Plywet.widget.FlowChartUtils = {
		
	/**
	 * 画文字
	 */
	drawText : function(config, ctx, text, x, y, style) {
		if (text) {
			var textRect = {
				x : config.canvasConfig.width,
				y : config.canvasConfig.height,
				x2 : 0,
				y2 : 0
			};

			var nf = Plywet.widget.FlowChartUtils.scaleLength(config.scale, 10);

			var texts;
			if(Object.prototype.toString.apply(text) === "[object Array]"){
				texts = text;
			}else{
				texts = [];
				texts.push(text);
			}
			for ( var i = 0; i < texts.length; i++) {
				ctx.save();

				ctx.fillStyle = (style) ? style : "Red";

				ctx.font = nf + "px Arial";
				var len = ctx.measureText(texts[i]).width;
				var sx = Math.floor(x - (len / 2));
				var sy = y + (nf + 4) * i;
				ctx.fillText(texts[i],sx,sy);
				ctx.restore();

				textRect.x = (sx < textRect.x) ? sx : textRect.x;
				textRect.y = ((sy - nf) < textRect.y) ? (sy - nf) : textRect.y;
				textRect.x2 = ((sx - len) > textRect.x2) ? (sx - len)
						: textRect.x2;
				textRect.y2 = (sy > textRect.y2) ? sy : textRect.y2;
			}
			return textRect;
		}
	},
	
	/**
	 * 画矩形
	 */
	drawRect : function(ctx,pos,conf,type,off){
		ctx.save();
		
		conf = conf || {};
		off = off || {x:0,y:0};
		
		ctx.lineWidth = conf.lineWidth || 1;
		ctx.strokeStyle = conf.strokeStyle || '#00f';
		
		ctx.globalAlpha = 1;
		ctx.beginPath();
		Plywet.widget.FlowChartUtils.drawLine.factory(ctx,type,pos.x+off.x,pos.y+off.y,pos.x+pos.width+off.x,pos.y+off.y);
		Plywet.widget.FlowChartUtils.drawLine.factory(ctx,type,pos.x+pos.width+off.x,pos.y+off.y,pos.x+pos.width+off.x,pos.y+pos.height+off.y);
		Plywet.widget.FlowChartUtils.drawLine.factory(ctx,type,pos.x+pos.width+off.x,pos.y+pos.height+off.y,pos.x+off.x,pos.y+pos.height+off.y);
		Plywet.widget.FlowChartUtils.drawLine.factory(ctx,type,pos.x+off.x,pos.y+pos.height+off.y,pos.x+off.x,pos.y+off.y);
		ctx.stroke();
		
		if (conf.isFill) {
			ctx.globalAlpha = conf.globalAlpha || 0.2;
			ctx.fillStyle = conf.fillStyle || '#00f';
			ctx.fillRect(pos.x+off.x+1,pos.y+off.y+1,pos.width-2,pos.height-2);
		}
		
		ctx.restore();
	},
	
	/**
	 * 画比例覆盖框
	 * @param targetSize 缩略区配置
	 * @param sourceSize 编辑区配置
	 * @param elsSize 所有元素位置配置
	 * @param ctx 缩略区对象
	 */
	drawRemarkRect : function(targetSize,sourceSize,elsSize,ctx) {
		var x,y,w,h,
			targetRate=targetSize.width/targetSize.height,
			sourceRate=sourceSize.width/sourceSize.height,
			sWidth=Math.max(sourceSize.width,elsSize.x2)-Math.min(0,elsSize.x),
			sHeight=Math.max(sourceSize.height,elsSize.y2)-Math.min(0,elsSize.y),
			sRate=sWidth/sHeight;
		
		if(targetRate>sRate){
			h=targetSize.height;
			w=h*sRate;
			y=0;
			x=(targetSize.width-w)/2;
		}else{
			w=targetSize.width;
			h=w/sRate;
			x=0;
			y=(targetSize.height-h)/2;
		}
		// 画整体元素区域
		console.log([x,y,w,h]);
		Plywet.widget.FlowChartUtils.drawRect(ctx,{x:x,y:y,width:w,height:h},{strokeStyle:"#999"},"dotted");
		
		var s = w/sWidth,
		rtn={
			scale:s,
			wholeRect:{
				x:x,
				y:y,
				width:w,
				height:h
			},
			editRect:{
				x:x-elsSize.x*s,
				y:y-elsSize.y*s,
				width:sourceSize.width*s,
				height:sourceSize.height*s
			}
		};
		
		// 画编辑区域
		ctx.save();
		ctx.fillStyle = "#7187D6";
		ctx.globalAlpha = 0.3;
		ctx.fillRect(rtn.editRect.x,rtn.editRect.y,rtn.editRect.width,rtn.editRect.height);
		
		ctx.restore();
		
		return rtn;
	},
	
	/**
	 * 画调整块
	 */
	drawResizer : function(ctx, pos, off, conf) {
		ctx.save();
		
		conf = conf || {};
		
		ctx.globalAlpha = conf.globalAlpha || 1;
		ctx.lineWidth = conf.lineWidth || 1;
		ctx.fillStyle = conf.fillStyle || '#000';
		ctx.strokeStyle = conf.strokeStyle || '#00f';
		
		var resizerSize = conf.resizerSize || 6; 
		
		off = off || {x:0, y:0};
		
		off.x = off.x - (resizerSize / 2);
		off.y = off.y - (resizerSize / 2);
		
		var rtn = {
			nw : {
				x : pos.x + off.x
				,y : pos.y + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, ne : {
				x : pos.x + pos.width + off.x
				,y : pos.y + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, sw : {
				x : pos.x + off.x
				,y : pos.y + pos.height + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, se : {
				x : pos.x + pos.width + off.x
				,y : pos.y + pos.height + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, n : {
				x : pos.x + (pos.width / 2) + off.x
				,y : pos.y + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, s : {
				x : pos.x + (pos.width / 2) + off.x
				,y : pos.y + pos.height + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, w : {
				x : pos.x + off.x
				,y : pos.y + (pos.height / 2) + off.y
				,width : resizerSize
				,height : resizerSize
			}
			, e : {
				x : pos.x + pos.width + off.x
				,y : pos.y + (pos.height / 2) + off.y
				,width : resizerSize
				,height : resizerSize
			}
		};
		
		for (var p in rtn) {
			var ps = rtn[p];
			ctx.fillRect(ps.x+1, ps.y+1, ps.width-2, ps.height-2);
			ctx.strokeRect(ps.x, ps.y, ps.width, ps.height);
		}
		
		ctx.restore();
		
		return rtn;
	},
	
	/**
	 * 画十字
	 */
	drawCross : function(c,ctx){
		ctx.save();
	  	ctx.beginPath();
	  	ctx.strokeStyle = "#999";
		ctx.globalAlpha = 1;
	  	ctx.moveTo(c.x-3,c.y);
	  	ctx.lineTo(c.x+3,c.y);
	  	ctx.moveTo(c.x,c.y-3);
	  	ctx.lineTo(c.x,c.y+3);
	  	ctx.stroke();
	  	ctx.restore();
	},
	
	/**
	 * 画圈选框
	 */
	drawRopeRect : function(config, ctx) {
		// 首先判断鼠标按下属性是否为null
		if(config.mouseConfig.mouseDown == null){return;}
		// 如果不是局部放大或者圈选类型，不出现圈选框
		if(config.outerControlType!="partMagnify" && config.outerControlType!=""){return;}
		// 如果是内部关闭，不出现圈选框
		if(config.innerControlType=="withoutRope"){return;}
		
		var r1 = config.mouseConfig.mouseDown;
		var r2;
		// 如果hold为true，取mouseMove的值
		if(config.mouseConfig.hold){
			r2 = config.mouseConfig.mouseMove;
		}
		// 如果为false，不画
		else {
			return;
		}
		// 画图
		if(r2 && r2 != null){
			var x = Math.min(r1.x,r2.x);
			var y = Math.min(r1.y,r2.y);
			var w = Math.abs(r1.x - r2.x);
			var h = Math.abs(r1.y - r2.y);
			
			ctx.save();
			ctx.fillStyle = "#FD0";
			ctx.globalAlpha = 0.3;
			ctx.fillRect(x,y,w,h);
			ctx.restore();
			
			if(config.outerControlType=="partMagnify"){
				Plywet.widget.FlowChartUtils.drawText(config,ctx,"+",x,y);
			}
		}
	},
	
	/**
	 * 画选择框
	 * @param canvasObj 该Step元素的父canvas对象
	 */
	drawSelectedRect : function(config, ctx, dx, dy, dWidth, dHeight,changable){
		var nc = Plywet.widget.FlowChartUtils.scalePoint(config, {
			x : dx,
			y : dy
		});
		var w = Plywet.widget.FlowChartUtils.scaleLength(config.scale, dWidth);
		var h = Plywet.widget.FlowChartUtils.scaleLength(config.scale, dHeight);
		if(changable){
			ctx.strokeStyle = "#B6B6B6";
			ctx.beginPath();
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3,nc.y-3,nc.x-3,nc.y-3+h+6,2,2);
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3,nc.y-3+h+6,nc.x-3+w+6,nc.y-3+h+6,2,2);
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3+w+6,nc.y-3+h+6,nc.x-3+w+6,nc.y-3,2,2);
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3+w+6,nc.y-3,nc.x-3,nc.y-3,2,2);
		    ctx.stroke();

		    ctx.strokeStyle = "#5581B9";
		    ctx.beginPath();
		    Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
		    		nc.x-6-1,nc.y-6,nc.x,nc.y-6,2,2);
		    Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
		    		nc.x-6,nc.y-6-1,nc.x-6,nc.y,2,2);
		    Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
		    		nc.x-3+w+6+3+1,nc.y-3+h+6+3,nc.x-3+w+6+3-6,nc.y-3+h+6+3,2,2);
		    Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
		    		nc.x-3+w+6+3,nc.y-3+h+6+3-6+1,nc.x-3+w+6+3,nc.y-3+h+6+3,2,2);
		    ctx.stroke();

		}else{
			ctx.strokeStyle = "#B6B6B6";
			ctx.beginPath();
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3,nc.y-3,nc.x-3,nc.y-3+h+6,2,2);
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3,nc.y-3+h+6,nc.x-3+w+6,nc.y-3+h+6,2,2);
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3+w+6,nc.y-3+h+6,nc.x-3+w+6,nc.y-3,2,2);
			Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx,
					nc.x-3+w+6,nc.y-3,nc.x-3,nc.y-3,2,2);
		    ctx.stroke();

		}
	},
	
	/**
	 * 画箭头
	 * @param x 中心点x坐标
	 * @param y 中心点y坐标
	 * @param arc 箭头方向斜率
	 * @param direction 方向
	 * @param canvasObj 该Step元素的父对象的canvas容器
	 * @param arrowType 箭头类型
	 */
	drawArrow : {
		
		factory : function(x,y,arc,direction,canvasObj,arrowType){
			// type1:圆形箭头
			if(arrowType == "circularity"){
				Plywet.widget.FlowChartUtils.drawArrow.circularity(x,y,arc,direction,canvasObj);
			}
			
			// type2:简单箭头
			else if(arrowType == "simple"){
				Plywet.widget.FlowChartUtils.drawArrow.simple(x,y,arc,direction,canvasObj);
			}
			
			// type3:实心菱形箭头
			else if(arrowType == "solid-diamond"){
				Plywet.widget.FlowChartUtils.drawArrow.solidDiamond(x,y,arc,direction,canvasObj);
			}
			
			// type4:空心菱形箭头
			else if(arrowType == "hollow-diamond"){
				Plywet.widget.FlowChartUtils.drawArrow.hollowDiamond(x,y,arc,direction,canvasObj);
			}
			
			// 通用箭头
			else {
				Plywet.widget.FlowChartUtils.drawArrow.normal(x,y,arc,direction,canvasObj);
			}
		},
		
		// type1:圆形箭头
		circularity : function(x,y,arc,direction,canvasObj){
			var ctx = canvasObj.ctx;
			
			ctx.beginPath();
			var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 3);
			ctx.arc(x,y,r,0,Math.PI*2,true);
			ctx.fill();
		},
		// type2:简单箭头
		simple : function(x,y,arc,direction,canvasObj){
			var ctx = canvasObj.ctx;
			
			ctx.save();
			ctx.translate(x,y);
			arc = isNaN(arc)?0:arc;
			ctx.rotate(Math.atan(arc));
	
			var side = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 6);
			var r = side/2/Math.cos(Math.PI/6);
			var reverse = side/2*Math.tan(Math.PI/6);
			
			ctx.beginPath();
			if(direction){
				ctx.moveTo(0,0);
				ctx.lineTo(r,0);
				ctx.lineTo(-reverse,side/2);
				ctx.moveTo(r,0);
				ctx.lineTo(-reverse,-side/2);
			}else{
				ctx.moveTo(0,0);
				ctx.lineTo(-r,0);
				ctx.lineTo(reverse,side/2);
				ctx.moveTo(-r,0);
				ctx.lineTo(reverse,-side/2);
			}
			ctx.stroke();
			ctx.restore();
		},
		// type3:实心菱形箭头
		solidDiamond : function(x,y,arc,direction,canvasObj){
			var ctx = canvasObj.ctx;
			
			// TODO
		},
		// type4:空心菱形箭头
		hollowDiamond : function(x,y,arc,direction,canvasObj){
			var ctx = canvasObj.ctx;
			
			// TODO
		},
		// 通用箭头
		normal : function(x,y,arc,direction,canvasObj){
			var ctx = canvasObj.ctx;
			
			ctx.save();
			ctx.translate(x,y);
			arc = isNaN(arc)?0:arc;
			ctx.rotate(Math.atan(arc));
	
			var side = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 6);
			var r = side/2/Math.cos(Math.PI/6);
			var reverse = side/2*Math.tan(Math.PI/6);
			
			if(direction){
				ctx.beginPath();
				ctx.moveTo(r,0);
				ctx.lineTo(-reverse,side/2);
				ctx.lineTo(0,0);
				ctx.lineTo(-reverse,-side/2);
				ctx.closePath();
				ctx.fill();
			}else{
				ctx.beginPath();
				ctx.moveTo(-r,0);
				ctx.lineTo(reverse,side/2);
				ctx.lineTo(0,0);
				ctx.lineTo(reverse,-side/2);
				ctx.closePath();
				ctx.fill();
			}
			ctx.restore();
		}
	},
	
	drawLine : {
		/**
		 * 画线工厂方法
		 */
		factory : function(ctx, type,x,y,lastX,lastY){
			if (typeof lastX == "undefined"){
				ctx.lineTo(x,y);
				return;
			}
			if(type == "dotted"){
				Plywet.widget.FlowChartUtils.drawLine.dashTo_(ctx, lastX, lastY, x, y, 6, 2);
			}else{
				ctx.moveTo(x,y);
				ctx.lineTo(lastX,lastY);
			}
			
		},
		/**
		 * 画折线
		 */
		dashTo_ : function(ctx, x, y, x2, y2, onLength, offLength){
			var overflow = 0;
			var dashLength = onLength + offLength;
			var dx = x2-x;
		    var dy = y2-y;
			var a = Math.atan2(dy, dx);
			var ca = Math.cos(a), sa = Math.sin(a);
			var segLength = Plywet.widget.FlowChartUtils.getLineLength(dx, dy);
			
			var tmpX = x;
			var tmpY = y;
			var onLengthX = ca*onLength;
			var onLengthY = sa*onLength;
			var offLengthX = ca*offLength;
			var offLengthY = sa*offLength;
			
			while(overflow < segLength){
				ctx.moveTo(tmpX,tmpY);
				
				overflow += onLength;
				if(overflow < segLength){
					tmpX += onLengthX;
					tmpY += onLengthY;
					ctx.lineTo(tmpX,tmpY);
				}else{
					ctx.lineTo(x2,y2);
				}
				
				tmpX += offLengthX;
				tmpY += offLengthY;
				overflow += offLength;
			}
		}
	},
	
	/**
	 * 获得线的长度
	 */
	getLineLength : function(x, y, x2, y2){
		if (typeof x2 == "undefined")
			return Math.sqrt(x*x + y*y);
		var dx = x2 - x;
		var dy = y2 - y;
		return Math.sqrt(dx*dx + dy*dy);
	},
	
	/**
	 * 点到线的距离
	 * 
	 * @param c : 点
	 *      	{
	 *            x:x轴坐标, 
	 *            y:y轴坐标 
	 *          } 
	 * @param line : 线
	 * 			{
	 *            x:起点x轴坐标, 
	 *            y:起点y轴坐标, 
	 *            x2:终点x轴坐标, 
	 *            y2:终点y轴坐标 
	 *          } 
	 * @return number 点到线的距离
	 */
	getDistancePointToLine : function(c, line){
		// 两个坐标直线公式Ax+By+C=0
		// A = (y2-y1)/(x2-x1)
		// B = -1
		// C = y1 - (y2-y1)/(x2-x1)*x1
		// d = |Ax0+By0+C|/sqrt(A^2+B^2)
		var A,B,C,d;
		if(Math.abs(line.x-line.x2)<20){
			d=Math.min(Math.abs(c.x-line.x),Math.abs(c.x-line.x2));
		}else{
			A = (line.y2-line.y)/(line.x2-line.x);
			B = -1;
			C = line.y - A*line.x;
			d=Math.floor(Math.abs(A*c.x+B*c.y+C) / Math.sqrt(A*A+B*B));
		}
		return d;
	},
	
	/**
	 * 点到圆的距离
	 * 
	 * @param p : {
	 *            x:x轴坐标, y:y轴坐标 } 点
	 * @param c : {
	 *            x:圆心x坐标, y:圆心y坐标 } 圆心
	 * @param r :
	 *            半径
	 * @return number 点到线的距离
	 */
	getDistancePointToCircle : function(p,c,r){
		// 点到圆心距离
		var D;
		D = Math.floor(
			Math.sqrt( Math.pow((p.x-c.x),2) + Math.pow((p.y-c.y),2))
		);
		return Math.abs(D-r);
	},
	
	/**
	 * 缩放点 1.参照元素位置进行缩放 2.根据现在的起点进行修正
	 */
	scalePoint : function(config, p){
		return {
			x : Math.floor(config.offset.x+p.x*config.scale),
			y : Math.floor(config.offset.y+p.y*config.scale)
		};
		
	},
	
	/**
	 * 反向缩放点
	 */
	arcScalePoint : function(config, p){
		return {
			x : Math.floor( (p.x-config.offset.x)/config.scale ),
			y : Math.floor( (p.y-config.offset.y)/config.scale )
		}
	},
	
	/**
	 * 缩放长度
	 */
	scaleLength : function(scale, l){
		return Math.floor(l*scale);
	},
	
	/**
	 * 反向缩放长度
	 */
	arcScaleLength : function(scale, l){
		return Math.floor(l/scale);
	},
	
	getLeft : function(obj) {
        if (obj == null)
            return 0;
        var mendingObj = obj;
        var mendingLeft = mendingObj.offsetLeft;
        while (mendingObj != null && mendingObj.offsetParent != null && mendingObj.offsetParent.tagName != "BODY") {
            mendingLeft = mendingLeft + mendingObj.offsetParent.offsetLeft;
            mendingObj = mendingObj.offsetParent;
        }

        return mendingLeft;
    },
    
    getTop : function(obj) {
        if (obj == null)
            return 0;
        var mendingObj = obj;
        var mendingTop = mendingObj.offsetTop;
        while (mendingObj != null && mendingObj.offsetParent != null && mendingObj.offsetParent.tagName != "BODY") {
            mendingTop = mendingTop + mendingObj.offsetParent.offsetTop;
            mendingObj = mendingObj.offsetParent;
        }
        return mendingTop;
    },
    
    /**
     * 得到接近的网格
     */
    getCloseGridPoint : function(mouseCoord,config){
    	if(config.showGrid && config.closeGrid){
	    	var distance = Math.floor(config.gridPoints.distance*config.scale);
	    	
	    	var tx0=config.offset.x,ty0=config.offset.y;
				
			while(tx0>0)tx0-=distance;
			while(ty0>0)ty0-=distance;
	    	
	    	var xIndex = Math.floor((mouseCoord.x - tx0)/distance + 0.5);
	    	var yIndex = Math.floor((mouseCoord.y - ty0)/distance + 0.5);
	    	xIndex=(xIndex<0)?0:xIndex;
	    	yIndex=(yIndex<0)?0:yIndex;
	    	
	    	var rt = config.gridPoints.points[xIndex];
	    	return rt[yIndex];
    	}else{
    		return mouseCoord;
    	}
    },
	
	/**
	 * 得到鼠标的坐标
	 */
	getMouseCoords : function(e){
		var position = {
			x : 0,
			y : 0
		};
		var oe = e.originalEvent;
		
		position.x = oe.x||oe.layerX||0;
		position.y = oe.y||oe.layerY||0;
		
		var target = oe.target || oe.srcElement;
		
		if(jQuery.browser.msie) {
			position.x = position.x - Plywet.widget.FlowChartUtils.getLeft(target);
			position.y = position.y - Plywet.widget.FlowChartUtils.getTop(target);
		} else {
			var canvasPos = $(target).position();
			position.x = position.x - canvasPos.left;
			position.y = position.y - canvasPos.top;
		}
		
		return position;
	},
	
	getMouseCoords2 : function(e){
		var position = {
				x : 0,
				y : 0
			};
		var oe = e.originalEvent;
		
		position.x = oe.x||oe.clientX||0;
		position.y = oe.y||oe.clientY||0;
		
		var target = oe.target || oe.srcElement;
		
		if(jQuery.browser.msie) {
			position.x = position.x - Plywet.widget.FlowChartUtils.getLeft(target);
			position.y = position.y - Plywet.widget.FlowChartUtils.getTop(target);
		} else {
			var canvasPos = $(target).position();
			position.x = position.x - canvasPos.left;
			position.y = position.y - canvasPos.top;
		}
		
		return position;
	},
	
	/**
     * 得到对象的中点坐标
     * 
     * @interface
     */
	getMidPoint : function(config,dx,dy,dWidth,dHeight) {
    	var nel = Plywet.widget.FlowChartUtils.scalePoint(config,{
    		x : dx,
    		y : dy
    	});
    	return {
    		x : nel.x + Plywet.widget.FlowChartUtils.scaleLength(config.scale,dWidth) / 2,
        	y : nel.y + Plywet.widget.FlowChartUtils.scaleLength(config.scale,dHeight) / 2
    	};
    }
};
