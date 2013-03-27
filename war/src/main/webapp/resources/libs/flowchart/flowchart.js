(function($) {
    // make sure undefined is undefined
    var undefined;
    
    $.flowChart = function(target, config, otarget) {
    	var flow = new FlowChart();
        flow.init(target, config, otarget);
        return flow;
    };
    
    $.pictureModel = function(config) {
    	var pic = new PictureModel();
    	pic.init(config);
    	return pic;
    };
    
    $.figureModel = function(config) {
    	var figure = new FigureModel();
    	figure.init(config);
    	return figure;
    };
    
    $.stepModel = function(type, config) {
		if(!type) type = config.type;
		
		if(type=="sys:step:picture"){
			return $.pictureModel(config);
		}else if(type=="sys:step:figure"){
			return $.figureModel(config);
		}else if(config.createFunction){
			return eval(config.createFunction+"(config)");
		}
		return "NO TYPE:"+type;
    };
    
    $.hopModel = function(config) {
    	var hop = new HopModel();
    	hop.init(config);
    	return hop;
    };
    
    $.flowObject = function(canvasObj,config) {
    	var flow = new FlowObject();
    	flow.init(canvasObj,config);
    	return flow;
    };
    
    function FigureModel() {
    	// 初始化
    	this.init = function(_config){
    		this.type = "Figure"; // 类型
    		this.state = "show"; // 状态
    		this.figureType = "Rectangular"; // 图形类型：默认是圆角框（圆角框-Rectangular、圆形-Circle、同心圆-Concentric）
    		this.provider = null; // 提供类型
    		this.accept = []; // 接受类型
    		this.acceptAll = false; // 接受所有类型
    		this.onStartHop = null; // 在该节点开始连线的判断方法
    		this.onEndHop = null; // 在该节点结束连线的判断方法
    		this.onAcceptHop = null; // 在该节点接受连线的方法
    		this.id = ""; // id
    		this.dx = 0; // 图形在画布上的x坐标
    		this.dy = 0; // 图形在画布上的y坐标
    		this.dWidth = 70; // 图形的宽度
    		this.dHeight = 30; // 图形的高度
    		this.pr = 10; // 圆角框边角默认半径
    		this.r = 0; // 圆形半径，或椭圆半径1
    		this.r2 = 0; // 圆形半径2，椭圆半径2
    		this.bText = []; // 下方的文字
    		this.bTextStyle = "black"; // 下方文字样式
    		this.cText = []; // 中间的文字
    		this.cTextStyle = "black"; // 中间文字样式
    		this.onClick = null; // 扩展单击事件
    		this.onDblClick = null; // 扩展双击事件
    		this.onContextMenu = null; // 扩展右键事件
    		this.onMove = null; // 扩展移动事件
    		this.onRope = null; // 扩展圈选事件
    		this.lineStyle = "black"; //边框颜色
    		this.lineWidth = 1; //边框宽度
    		this.fillStyle = "white"; //填充颜色
    		this.isDragDrop = "true"; //是否可拖拽
    		this.changable = false; // 是否可以改变大小
    		this.extendData = null; // 扩展的数据
    		
    		this.parameters = ["state","figureType","provider","accept","acceptAll","onStartHop","onEndHop",
    		                   "onAcceptHop","id","dx","dy","dWidth","dHeight","pr","r","r2","bText","cText",
    		                   "bTextStyle","cTextStyle","onClick","onDblClick","onContextMenu","onMove",
    		                   "onRope","lineStyle","lineWidth","fillStyle","isDragDrop","changable","extendData"];
    		
    		if(_config && _config!=undefined){
    			for(var i=0; i<this.parameters.length; i++){
    				if(_config[this.parameters[i]])eval("this."+this.parameters[i]+"=_config."+this.parameters[i]+";");
    			}
    		}
    	};
    	
    	// 销毁
    	this.destroy = function() {
    		for(var i=0; i<this.parameters.length; i++){
				eval("this."+this.parameters[i]+"=null;");
			}
    	};
    	
    	/**
    	 * 获得该元素的JSON值
    	 * 
    	 * @interface
    	 */
    	this.getElValue = function() {
    		var value = {type:"sys:step:figure"};
    		for(var i=0; i<this.parameters.length; i++){
    			if(this[this.parameters[i]])eval("value."+this.parameters[i]+"=this."+this.parameters[i]+";");
			}
    		return Plywet.toJSONString(value);
    	};
    	
    	/**
    	 * 画图
    	 * @param o Step的元数据更新
    	 * @param _offset Step的偏移量
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * 
    	 * @interface
    	 */
    	this.draw = function(o, _offset, canvasObj) {
    		jQuery.extend(this, o);

    		if (_offset != null) {
    			if (_offset.x != null && typeof _offset.x == "number") {
    				this.dx = this.dx + _offset.x;
    			}
    			if (_offset.y != null && typeof _offset.y == "number") {
    				this.dy = this.dy + _offset.y;
    			}
    		}
    		
    		var ctx = canvasObj.ctx;
    		var sd = Plywet.widget.FlowChartUtils.scalePoint( canvasObj.config, {
    			x : this.dx,
    			y : this.dy
    		});

    		var sdWidth = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,this.dWidth);
    		var sdHeight = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,this.dHeight);

    		ctx.save();
    		
    		// 画图形
    		ctx.strokeStyle = this.lineStyle;
    		ctx.lineWidth = this.lineWidth;
    		ctx.fillStyle = this.fillStyle;
    		ctx.lineCap = "round";
    		ctx.lineJoin = "round";
    		
    		eval("this.draw"+this.figureType+"(canvasObj,sd,sdWidth,sdHeight);");
    		
            // 写文字
    		Plywet.widget.FlowChartUtils.drawText(canvasObj.config,canvasObj.ctx,this.bText, sd.x + (sdWidth / 2),
    				sd.y + sdHeight + Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,10) + 4,
    				this.bTextStyle);
    		
    		Plywet.widget.FlowChartUtils.drawText( canvasObj.config, canvasObj.ctx, this.cText, sd.x + (sdWidth / 2),
					sd.y + (sdHeight / 2), this.cTextStyle);

    		ctx.restore();
            
    		if(this.state != "show"){
    			Plywet.widget.FlowChartUtils.drawSelectedRect(canvasObj.config,canvasObj.ctx,this.dx,this.dy,this.dWidth,this.dHeight,this.changable);
    		}

    		return this;
    	};
    	
    	/**
    	 * 画圆角框
    	 */
    	this.drawRectangular = function(canvasObj,sd,sdWidth,sdHeight){
    		var ctx = canvasObj.ctx;

    		var spr = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,this.pr);
    		
    		ctx.beginPath();
    		ctx.moveTo(sd.x + spr, sd.y);
    		ctx.lineTo(sd.x + sdWidth - spr, sd.y);
    		ctx.quadraticCurveTo(sd.x + sdWidth, sd.y, sd.x + sdWidth, sd.y + spr);
    		ctx.lineTo(sd.x + sdWidth, sd.y + sdHeight - spr);
    		ctx.quadraticCurveTo(sd.x + sdWidth, sd.y + sdHeight, sd.x + sdWidth - spr, sd.y + sdHeight);
    		ctx.lineTo(sd.x + spr, sd.y + sdHeight);
    		ctx.quadraticCurveTo(sd.x, sd.y + sdHeight, sd.x, sd.y + sdHeight - spr);
    		ctx.lineTo(sd.x, sd.y + spr);
    		ctx.quadraticCurveTo(sd.x, sd.y, sd.x + spr, sd.y);
    		ctx.fill();
    		ctx.stroke();
    	};
    	
    	/**
    	 * 画圆形
    	 */
    	this.drawCircle = function(canvasObj,sd,sdWidth,sdHeight){
    		var ctx = canvasObj.ctx;
    		
    		var c = {
    			x : Math.floor(sd.x + sdWidth/2),
    			y : Math.floor(sd.y + sdHeight/2)
    		};
    		
    		var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,this.r);
    		
    		ctx.beginPath();
    		ctx.arc(c.x, c.y, r, 0, Math.PI * 2, false);
    		ctx.fill();
    		ctx.stroke();

    	};
    	
    	/**
    	 * 画同心圆
    	 */
    	this.drawConcentric = function(canvasObj,sd,sdWidth,sdHeight){
    		var ctx = canvasObj.ctx;
    		
    		var c = {
    			x : Math.floor(sd.x + sdWidth/2),
    			y : Math.floor(sd.y + sdHeight/2)
    		};
    		
    		var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,this.r);
    		var r2 = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale,this.r2);
    		
    		ctx.beginPath();
    		ctx.arc(c.x, c.y, r, 0, Math.PI * 2, false);
    		ctx.stroke();
    		ctx.beginPath();
    		ctx.arc(c.x, c.y, r2, 0, Math.PI * 2, false);
    		ctx.fill();
    		ctx.stroke();

    	};
    	
    	/**
    	 * 克隆元素
    	 * 
    	 * @interface
    	 */
    	this.cloneEl = function(){
    		var cloneStep = $.figureModel();
    		jQuery.extend(cloneStep,this);
    		cloneStep.bText = [];
    		cloneStep.cText = [];
    		if(this.bText){
    			for(var i=0;i<this.bText.length;i++){
    				cloneStep.bText[i] = this.bText[i];
    			}
    		}
    		if(this.cText){
    			for(var i=0;i<this.cText.length;i++){
    				cloneStep.cText[i] = this.cText[i];
    			}
    		}
    		return cloneStep;
    	};
    	
    	/**
    	 * 单击事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealClick = function(canvasObj,flowObj){
    		// 表示显示状态，需要选中其
    		if(this.state=="show"){
    			this.state="selected";
    			flowObj.changeElSelectedState(this.id,"step",true);
    		}
    		// 表示选中状态，需要取消选中
    		else if(this.state=="selected"){
    			this.state="show";
    			flowObj.changeElSelectedState(this.id,"step",false);
    		}
    		
    		// 调用扩展事件
    		if(this.onClick) eval(this.onClick);
    		
    	};
    	
    	/**
    	 * 判断是否是选中状态
    	 * 
    	 * @interface
    	 */
    	this.isSelected = function(){
    		if(this.state=="show"){
    			return false;
    		}else{
    			return true;
    		}
    	};
    	
    	/**
    	 * 圈选事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealRope = function(canvasObj,flowObj){
    		// 如果是显示状态，需要选中其
    		if(this.state=="show"){
    			this.state="selected";
    			flowObj.changeElSelectedState(this.id,"step",true);
    		}
    		// 如果是选中状态，不操作
    		
    		// 调用扩展事件
    		if(this.onRope) eval(this.onRope);
    	};
    	
    	/**
    	 * 移动事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealMove = function(canvasObj,flowObj){
    		// 调用扩展事件
    		if(this.onMove) eval(this.onMove);
    	};
    	
    	/**
    	 * 双击事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealDblClick = function(canvasObj, flowObj) {
    		// 调用扩展事件
    		if(this.onDblClick) eval(this.onDblClick);
    	};
    	
    	/**
    	 * 右键事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealContextMenu = function(canvasObj, flowObj) {
    		// 调用扩展事件
    		if(this.onContextMenu) eval(this.onContextMenu);
    	};
    }
    
    function PictureModel() {
    	
    	// 初始化
    	this.init = function(_config){
    		this.type = "Picture"; // 类型固定值“Picture”
    		this.state = "show"; // 状态--show, selected
    		this.provider = null; // 提供类型
    		this.accept = []; // 接受类型
    		this.acceptAll = false; // 接受所有类型
    		this.onStartHop = null; // 在该节点开始连线的判断方法
    		this.onEndHop = null; // 在该节点结束连线的判断方法
    		this.onAcceptHop = null; // 在该节点接受连线的方法
    		this.id = ""; // Step的Id
    		this.imgSrc = ""; // 图片的路径
    		this.imgId = ""; // 图片的Id
    		this.sx = 0; // 截取图片的开始点x坐标
    		this.sy = 0; // 截取图片的开始点y坐标
    		this.sWidth = 32; // 截取图片的原始宽度
    		this.sHeight = 32; // 截取图片的原始高度
    		this.dx = 0; // 图片在画布的默认出现位置x坐标
    		this.dy = 0; // 图片在画布的默认出现位置y坐标
    		this.dWidth = 32; // 图片在画布的宽度
    		this.dHeight = 32; // 图片在画布的高度
    		this.changable = false; // 是否可以改变大小
    		this.bText = []; // 下方的文字
    		this.bTextStyle = "Green";
    		this.tlText = []; // 左上方的文字
    		this.tlTextStyle = "Red";
    		this.onClick = null; // 扩展单击事件
    		this.onDblClick = null; // 扩展双击事件
    		this.onContextMenu = null; // 扩展右键事件
    		this.onMove = null; // 扩展移动事件
    		this.onRope = null; // 扩展圈选事件
    		this.extendData = null; // 扩展的数据
    		
    		this.parameters = ["state","provider","accept","acceptAll","onStartHop","onEndHop","onAcceptHop","id","imgSrc"
    		                   ,"imgId","sx","sy","sWidth","sHeight","dx","dy","dWidth","dHeight","changable","bText"
    		                   ,"bTextStyle","tlText","tlTextStyle","onClick","onDblClick","onContextMenu","onMove","onRope","extendData"];
    		
    		if(_config && _config!=undefined){
    			for(var i=0; i<this.parameters.length; i++){
    				if(_config[this.parameters[i]])eval("this."+this.parameters[i]+"=_config."+this.parameters[i]+";");
    			}
    		}
    	};
    	
    	// 销毁
    	this.destroy = function() {
    		for(var i=0; i<this.parameters.length; i++){
				eval("this."+this.parameters[i]+"=null;");
			}
    	};
    	
    	/**
    	 * 获得该元素的JSON值
    	 * 
    	 * @interface
    	 */
    	this.getElValue = function() {
    		var value = {type:"sys:step:picture"};
    		for(var i=0; i<this.parameters.length; i++){
    			if(this[this.parameters[i]])eval("value."+this.parameters[i]+"=this."+this.parameters[i]+";");
			}
    		return Plywet.toJSONString(value);
    	};
    	
    	/**
    	 * 画Step
    	 * @param o Step的元数据更新
    	 * @param _offset Step的偏移量
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * 
    	 * @interface
    	 */
    	this.draw = function(o, _offset, canvasObj) {

    		jQuery.extend(this, o);

    		if (_offset != null) {
    			if (_offset.x != null && typeof _offset.x == "number") {
    				this.dx = this.dx + _offset.x;
    			}
    			if (_offset.y != null && typeof _offset.y == "number") {
    				this.dy = this.dy + _offset.y;
    			}
    		}

    		var ctx = canvasObj.ctx;

    		ctx.save();

    		var sd = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
    			x : this.dx,
    			y : this.dy
    		});
    		
    		var sdWidth = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, this.dWidth);
    		var sdHeight = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, this.dHeight);

    		// 画图
    		
    		var imgObj;
    		if(this.imgId && this.imgId != ""){
    			imgObj = $(this.imgId);
    		}else{
    			imgObj = Plywet.widget.FlowChartResources.addAndFindResource(this.imgSrc);
    		}
    		
    		try{
    			ctx.drawImage(imgObj, this.sx, this.sy,
    					this.sWidth, this.sHeight, sd.x, sd.y,
    					sdWidth, sdHeight);
    		}catch(e){
    			imgObj = new Image();
    			var isx = this.sx;
    			var isy = this.sy;
    			var isw = this.sWidth;
    			var ish = this.sHeight;
    			imgObj.onload = function(){
    				ctx.drawImage(this, isx, isy,
    						isw, ish, sd.x, sd.y,
    						sdWidth, sdHeight);
    			};
    			imgObj.src = this.imgSrc;
    			
    		}

    		Plywet.widget.FlowChartUtils.drawText(canvasObj.config, ctx, this.bText, sd.x + (sdWidth / 2), sd.y + sdHeight
    				+ Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 10) + 4, this.bTextStyle);
    		Plywet.widget.FlowChartUtils.drawText(canvasObj.config, ctx, this.tlText, sd.x, sd.y, this.tlTextStyle);

    		ctx.restore();
    		
    		if(this.state != "show"){
    			Plywet.widget.FlowChartUtils.drawSelectedRect(canvasObj.config,canvasObj.ctx,this.dx,this.dy,this.dWidth,this.dHeight,this.changable);
    		}

    		return this;
    	};
    	
    	/**
    	 * 克隆元素
    	 * 
    	 * @interface
    	 */
    	this.cloneEl = function(){
    		var cloneStep = $.pictureModel();
    		jQuery.extend(cloneStep,this);
    		cloneStep.bText = [];
    		cloneStep.tlText = [];
    		if(this.bText){
    			for(var i=0;i<this.bText.length;i++){
    				cloneStep.bText[i] = this.bText[i];
    			}
    		}
    		if(this.tlText){
    			for(var i=0;i<this.tlText.length;i++){
    				cloneStep.tlText[i] = this.tlText[i];
    			}
    		}
    		return cloneStep;
    	};
    	
    	/**
    	 * 单击事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealClick = function(canvasObj,flowObj){
    		// 表示显示状态，需要选中其
    		if(this.state=="show"){
    			this.state="selected";
    			flowObj.changeElSelectedState(this.id,"step",true);
    		}
    		// 表示选中状态，需要取消选中
    		else if(this.state=="selected"){
    			this.state="show";
    			flowObj.changeElSelectedState(this.id,"step",false);
    		}
    		
    		// 调用扩展事件
    		if(this.onClick) eval(this.onClick);
    	};
    	
    	/**
     	 * 双击事件
     	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 *
    	 * @interface
    	 */
    	this.dealDblClick = function(canvasObj, flowObj) {
    		// 调用扩展事件
    		if(this.onDblClick) eval(this.onDblClick);
    	};
    	
    	/**
     	 * 右键事件
     	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 *
    	 * @interface
    	 */
    	this.dealContextMenu = function(canvasObj, flowObj) {
    		// 调用扩展事件
    		if(this.onContextMenu) eval(this.onContextMenu);
    	};
    	
    	/**
    	 * 判断是否是选中状态
    	 * 
    	 * @interface
    	 */
    	this.isSelected = function(){
    		if(this.state=="show"){
    			return false;
    		}else{
    			return true;
    		}
    	};
    	
    	/**
    	 * 圈选事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealRope = function(canvasObj,flowObj){
    		// 如果是显示状态，需要选中其
    		if(this.state=="show"){
    			this.state="selected";
    			flowObj.changeElSelectedState(this.id,"step",true);
    		}
    		// 如果是选中状态，不操作
    		
    		// 调用扩展事件
    		if(this.onRope) eval(this.onRope);
    	};
    	
    	/**
    	 * 移动事件
    	 * @param canvasObj 该Step元素的父canvas对象
    	 * @param flowObj 该Step元素的父对象
    	 * 
    	 * @interface
    	 */
    	this.dealMove = function(canvasObj,flowObj){
    		// 调用扩展事件
    		if(this.onMove) eval(this.onMove);
    	};
    }
    
    function HopModel() {
    	this.init = function(_config){
			this.arrowStartType = ""; // 开始箭头类型“”，“default”，“circularity”，“simple”
			this.arrowEndType = ""; // 结束箭头类型
			this.lineType = "solid"; // 线条类型“solid”，“dotted”
			this.state = "show"; // 状态--show, selected
			this.id = ""; // 连线的Id，一般是"fromElId->toElId"
			this.x = [0,0]; // 连线的所有点的x坐标
			this.y = [0,0]; // 连线的所有点的y坐标
			this.fromElId = ""; // 开始元素的Id
			this.toElId = ""; // 结束元素的Id
			this.provider = null; // 提供连线的对象
			this.style = "blue"; // 连线的样式
			this.textStyle = "blue"; // 文字样式
			this.text = []; // 线间文字
			this.startText = []; // 开始文字
			this.endText = []; // 结束文字
			this.onClick = null; // 扩展单击事件
			this.onDblClick = null; // 扩展双击事件
			this.onContextMenu = null; // 扩展右键事件
			this.onMove = null; // 扩展移动事件
			this.onRope = null; // 扩展圈选事件
			this.extendData = null; // 扩展的数据
			
			this.parameters = ["arrowStartType","arrowEndType","lineType","state","id","x","y"
			                   ,"fromElId","toElId","provider","style","textStyle","text","startText"
			                   ,"endText","onClick","onDblClick","onContextMenu","onMove","onRope","extendData"];
			
			if(_config && _config!=undefined){
				for(var i=0; i<this.parameters.length; i++){
    				if(_config[this.parameters[i]])eval("this."+this.parameters[i]+"=_config."+this.parameters[i]+";");
    			}
			}
		};
		
		this.destroy = function(){
			for(var i=0; i<this.parameters.length; i++){
				eval("this."+this.parameters[i]+"=null;");
			}
		};
		
		/**
    	 * 获得该元素的JSON值
    	 * 
    	 * @interface
    	 */
    	this.getElValue = function() {
    		var value = {};
    		for(var i=0; i<this.parameters.length; i++){
    			if(this[this.parameters[i]])eval("value."+this.parameters[i]+"=this."+this.parameters[i]+";");
			}
    		return Plywet.toJSONString(value);
    	};
		
		/**
		 * 画线
		 * @param o 线的元数据
		 * @param _offset 画布的偏移量
		 * @param canvasObj 该Step元素的父对象的canvas容器
		 * @param flowObj 该Step元素的父对象
		 * 
		 * @interface
		 */
		this.draw = function(o, _offset, canvasObj, flowObj) {
			
			jQuery.extend(this, o);
			
			// 设置偏移量
			if(_offset!=null){
				var ox=null,oy=null;
				if(_offset.x!=null && typeof _offset.x == "number") {
					ox = jQuery.map(this.x, function(i){
							return i+_offset.x;
						});
				}
				if(_offset.y!=null && typeof _offset.y == "number") {
					oy = jQuery.map(this.y, function(i){
							return i+_offset.y;
						});
				}
				jQuery.extend(this, {
					x : (ox!=null)?ox:this.x,
					y : (oy!=null)?oy:this.y
				});
			}
			
			var len = this.x.length;
			// 更新起点和终点元素对象和中心点
			var fel,tel;
			var felc,telc;
			// 得到开始对象,更新开始点
			if(this.fromElId!=null&&this.fromElId!=""){
				fel = flowObj.getElById(this.fromElId,"step");
				felc = Plywet.widget.FlowChartUtils.getMidPoint(canvasObj.config,fel.el.dx,fel.el.dy,fel.el.dWidth,fel.el.dHeight);
				this.x[0]=felc.x;
				this.y[0]=felc.y;
			}
			// 得到结束对象,更新结束点
			if(this.toElId!=null&&this.toElId!=""){
				tel = flowObj.getElById(this.toElId,"step");
				telc = Plywet.widget.FlowChartUtils.getMidPoint(canvasObj.config,tel.el.dx,tel.el.dy,tel.el.dWidth,tel.el.dHeight);
				this.x[len-1]=telc.x;
				this.y[len-1]=telc.y;
			}
			var fc=null,tc=null;
			var nc;
			// 缩短开始，结束长度
			
			// 开始对象
			// 两个节点
			// 1.直线连接两个节点
			// 2.拖动的画线且没有中间节点
			if(len==2 ){
				nc = {
					x : this.x[1],
					y : this.y[1]
				};
			}
			// 大于两个节点
			else{
				nc = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
					x : this.x[1],
					y : this.y[1]
				});
			}
			
			fc = this.getLineShortedCoords(canvasObj.config.scale,Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, fel.el.dWidth),Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, fel.el.dHeight),{
				x : felc.x,
				y : felc.y,
				x2 : nc.x,
				y2 : nc.y
			});
			
			// 结束对象
			if(this.toElId!=null && this.toElId!=""){
				if(len==2){
					nc = {
						x : this.x[0],
						y : this.y[0]
					};
				}else{
					nc = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[len-2],
						y : this.y[len-2]
					});
				}
				tc = this.getLineShortedCoords(canvasObj.config.scale,Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, tel.el.dWidth),Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, tel.el.dHeight),{
					x : telc.x,
					y : telc.y,
					x2 : nc.x,
					y2 : nc.y
				});
			}
			
			// 开始画线，注意s是缩放比
			var ctx = canvasObj.ctx;
			ctx.save();
			ctx.strokeStyle = this.style;
			ctx.fillStyle = this.style;
			ctx.globalAlpha = 1;
			ctx.beginPath();
			// 如果是同一节点自循环，且没有中间节点
			if(len<=2 && this.fromElId == this.toElId){
				var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 20);
				ctx.arc( this.x[0],( this.y[0]-r),r,(Math.PI*0.25),(Math.PI*0.75),true);
				ctx.stroke();
				var ot = Math.floor(Math.sqrt(Math.pow(r,2)/2));
				if(this.arrowStartType && this.arrowStartType!=""){
					Plywet.widget.FlowChartUtils.drawArrow.factory( (this.x[0]), (this.y[0]), -1, false, canvasObj, this.arrowStartType);
				}
				if(this.arrowEndType && this.arrowEndType!=""){
					Plywet.widget.FlowChartUtils.drawArrow.factory( (this.x[0]-ot), (this.y[0]-r+ot), 1, true, canvasObj, this.arrowEndType);
				}
			}else{
				ctx.moveTo(fc.x,fc.y);
				
				var lastX = fc.x;
				var lastY = fc.y;
				
				for(var i=1;i<len-1;i++){
					nc = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[i],
						y : this.y[i]
					});
					
					
					Plywet.widget.FlowChartUtils.drawLine.factory(ctx, this.lineType,nc.x,nc.y,lastX,lastY);
					
					lastX = nc.x;
					lastY = nc.y;
				}
				
				if(tc!=null){
					Plywet.widget.FlowChartUtils.drawLine.factory(ctx, this.lineType,tc.x,tc.y,lastX,lastY);
					
					ctx.stroke();
					
					if(this.arrowStartType && this.arrowStartType!=""){
						
						if(len==2){
							Plywet.widget.FlowChartUtils.drawArrow.factory(fc.x, fc.y, (fc.y-tc.y)/(fc.x-tc.x), (fc.x>=tc.x), canvasObj, this.arrowStartType);
						}else{
							var nc2 = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
								x : this.x[1],
								y : this.y[1]
							});
							Plywet.widget.FlowChartUtils.drawArrow.factory(fc.x, fc.y, (fc.y-nc2.y)/(fc.x-nc2.x), (fc.x>=nc2.x), canvasObj, this.arrowStartType);
						}
					}
					
					if(this.arrowEndType && this.arrowEndType!=""){
						Plywet.widget.FlowChartUtils.drawArrow.factory(tc.x, tc.y, (tc.y-nc.y)/(tc.x-nc.x), (tc.x>=nc.x), canvasObj, this.arrowEndType);
					}
					
					
					if(this.startText && this.startText.length!=0){
						
						if(len==2){
							this.drawStartAndEndText(fc.x, fc.y, (fc.y-tc.y)/(fc.x-tc.x), (fc.x>=tc.x), canvasObj, this.startText);
						}else{
							var nc2 = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
								x : this.x[1],
								y : this.y[1]
							});
							this.drawStartAndEndText(fc.x, fc.y, (fc.y-nc2.y)/(fc.x-nc2.x), (fc.x>=nc2.x), canvasObj, this.startText);
						}
					}
					
					if(this.endText && this.endText.length!=0){
						this.drawStartAndEndText(tc.x, tc.y, (tc.y-nc.y)/(tc.x-nc.x), (tc.x>=nc.x), canvasObj, this.endText);
					}
				}else{
					Plywet.widget.FlowChartUtils.drawLine.factory(ctx, this.lineType,this.x[len-1],this.y[len-1],lastX,lastY);
				}
				
			}
			
			// 更新开始和结束点
			// 如果是同一节点自循环，且没有中间节点，不更新
			if(len<=2 && this.fromElId == this.toElId){
			}else{
				if(fc != null){
					this.x[0] = fc.x;
					this.y[0] = fc.y;
				}
				if(tc != null){
					this.x[len-1] = tc.x;
					this.y[len-1] = tc.y;
				}
			}
			
			// 写中间文字
			var nh,nh2;
			
			if(len<=2 && this.fromElId == this.toElId){
				var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 20);
				nh = {
					x : this.x[0],
					y : this.y[0]
				};
				nh2 = {
					x : this.x[1],
					y : this.y[1]
				};
				Plywet.widget.FlowChartUtils.drawText(canvasObj.config, canvasObj.ctx, this.text, (nh.x+nh2.x)/2, (nh.y+nh2.y)/2 - 2*r, this.textStyle);
			}else{
				var n2 = Math.floor(len/2);
				var fn,tn;
				if(n2 == len/2){
					fn = n2-1;
					tn = n2;
				}else{
					fn = n2;
					tn = n2;
				}
				
				if(fn!=0&&fn!=(len-1)){
					nh = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[fn],
						y : this.y[fn]
					});
				}else{
					nh = {
						x : this.x[fn],
						y : this.y[fn]
					};
				}
				
				if(tn!=0&&tn!=(len-1)){
					nh2 = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[tn],
						y : this.y[tn]
					});
				}else{
					nh2 = {
						x : this.x[tn],
						y : this.y[tn]
					};
				}
				
				Plywet.widget.FlowChartUtils.drawText(canvasObj.config, canvasObj.ctx, this.text, (nh.x+nh2.x)/2, (nh.y+nh2.y)/2, this.textStyle);
			}
			
			ctx.stroke();
			ctx.restore();
			
			if(this.state != "show"){
				this.drawSelectedLine(canvasObj,flowObj);
			}
			
			return this;
		};
		
		/**
		 * 克隆Hop
		 * 
		 * @interface
		 */
		this.cloneEl = function(){
			var cloneHop = $.hopModel();
			jQuery.extend(cloneHop,this);
			cloneHop.x = [];
			cloneHop.y = [];
			cloneHop.text = [];
			if(this.x){
				for(var i=0;i<this.x.length;i++){
					cloneHop.x.push(this.x[i]);
					cloneHop.y.push(this.y[i]);
				}
			}
			if(this.text){
				for(var i=0;i<this.text.length;i++){
					cloneHop.text.push(this.text[i]);
				}
			}
			return cloneHop;
		};
		
	    /**
	     * 获得hop的添加点
	     * @param o 元素对象
	     * @param c 鼠标坐标
	     * @return 插入点，如果没有返回-1
	     */
		this.getAddJoinBlockPoint = function(canvasObj,flowObj, c){
			var x = this.x;
			var y = this.y;
			var len = y.length;
			var d;
			var np,np2,r;
			if(this.fromElId==this.toElId && len<=2){
				r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 20);
				d = Plywet.widget.FlowChartUtils.getDistancePointToCircle(c,{
					x : x[0],
					y : y[0]-r
				},r);
				if(d<5){
					return 0;
				}
			}else {
				for(var j=1;j<len;j++){
					if((j-1)==0){
						np = {
							x : x[j-1],
							y : y[j-1]
						};
					}else{
						np = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
							x : x[j-1],
							y : y[j-1]
						});
					}
					
					if(j==(len-1)){
						np2 = {
							x : x[j],
							y : y[j]
						};
					}else{
						np2 = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
							x : x[j],
							y : y[j]
						});
					}
					if( 
						(((np.x-5<=c.x) && (c.x<=np2.x+5)) ||
						 ((np2.x-5<=c.x) && (c.x<=np.x+5))) &&
						(((np.y-5<=c.y) && (c.y<=np2.y+5)) ||
						 ((np2.y-5<=c.y) && (c.y<=np.y+5)))
					  ){
						
						d = Plywet.widget.FlowChartUtils.getDistancePointToLine(c,{
							x : np.x,
							y : np.y,
							x2 : np2.x,
							y2 : np2.y
						});
						if(d<10){
							return (j-1);
						}
					}
				}
			}
	    	return -1;
	    };
		
		/**
		 * 判断是否是选中状态
		 * 
		 * @interface
		 */
	    this.isSelected = function(){
			if(this.state=="show"){
				return false;
			}else{
				return true;
			}
		};
		
		/**
		 * 得到线条的最大坐标
		 */
		this.getLineMaxCoords = function(canvasObj){
			
			if(this.fromElId==this.toElId && this.x.length<=2){
				var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 20);
				return {
					x: this.x[0]-r,
					x2: this.x[0]+r,
					y: this.y[0]-2*r,
					y2: this.y[0]
				};
			}
			
			var np;
			var x=this.x[0],x2=this.x[0],y=this.y[0],y2=this.y[0];
			var len = this.x.length;
			for(var i=1;i<len-1;i++){
				np = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
					x : this.x[i],
					y : this.y[i]
				});
				x=Math.min(x,np.x);
				y=Math.min(y,np.y);
				x2=Math.max(x2,np.x);
				y2=Math.max(y2,np.y);
			}
			x=Math.min(x,this.x[len-1]);
			y=Math.min(y,this.y[len-1]);
			x2=Math.max(x2,this.x[len-1]);
			y2=Math.max(y2,this.y[len-1]);
			return {
				x : x,
				y : y,
				x2 : x2,
				y2 : y2
			}
		};
		
		/**
		 * 通过元素的长宽，得到线条的缩短坐标
		 * @param dWidth 元素的宽度
		 * @param dHeight 元素的高度
		 * @param line 待处理的线条
		 */
		this.getLineShortedCoords = function(scale,dWidth,dHeight,line){
			var dx = line.x2-line.x;
			var dy = line.y2-line.y;
			var buffer = Plywet.widget.FlowChartUtils.scaleLength(scale, 6);
			var x,y;
			var X,Y;
			// 竖线
			if(Math.abs(dx)<5){
				X=line.x;
				Y=line.y;
				if(dy<0){
					x = X;
					y = Y - (dHeight/2 + buffer);
				}else{
					x = X;
					y = Y + (dHeight/2 + buffer);
				}
			}else{
				var slope = dy/dx;
			
				X=line.x;
				Y=line.y;
				if(-1<=slope && slope<=1){
					//1:(-PI/4~PI/4)
					if(dx>0){
						x = X + (dWidth/2 + buffer);
					}
					//3:(PI*3/4~PI*5/4)
					else{
						x = X - (dWidth/2 + buffer);
					}
					y = Math.floor( slope * (x-X) + Y );
					x = Math.floor(x);
				}
				
				else if(slope<-1 || slope>1){
					//2:(PI/4~PI*3/4)
					if(dy<0){
						y = line.y - (dHeight/2 + buffer);
					}
					//4:(PI*5/4~PI*7/4)
					else{
						y = line.y + (dHeight/2 + buffer);
					}
					x = Math.floor( (y-Y) / slope + X );
					y = Math.floor(y);
				}
			}
			return {
				x : x,
				y : y
			};
		};
		
		/**
		 * 通过坐标获得连接块
		 * @param canvasObj
		 * @param c 点
		 * @return {
		 * 		el : Hop元素对象
		 * 		index ：连接块在Hop元素对象中的位置
		 * }
		 */
		this.getJoinBlockByCoords = function(canvasObj, c){
			
			var len = this.x.length;
			var ns;
			
			for(var j=1;j<len-1;j++){
				ns = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
					x : this.x[j],
					y : this.y[j]
				});
				if( Math.abs(ns.x-c.x)<=5 && Math.abs(ns.y-c.y)<=5 ){
					return j;
				}
			}
			
			return -1;
		};
		
		/**
		 * 写起始点的文字
		 * @param x 中心点x坐标
		 * @param y 中心点y坐标
		 * @param arc 箭头方向斜率
		 * @param direction 方向
		 * @param canvasObj 该Step元素的父对象的canvas容器
		 * @param text 文字
		 */
		this.drawStartAndEndText = function(x,y,arc,direction,canvasObj,text){
			
			var bufX=0,bufY=0;
			var buf = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 10);
			
			arc = isNaN(arc)?0:arc;
			var reverse = Math.atan(arc);
			bufX = Math.cos(reverse) * buf;
			bufY = Math.sin(reverse) * buf;
			
			if(direction){
				Plywet.widget.FlowChartUtils.drawText(canvasObj.config, canvasObj.ctx, text, x-bufX, y-bufY, this.textStyle);
			}else{
				Plywet.widget.FlowChartUtils.drawText(canvasObj.config, canvasObj.ctx, text, x+bufX, y+bufY, this.textStyle);
			}
		};
		
		/**
		 * 绘制连接块
		 */
		this.drawJoinRect = function(x,y,width,height,canvasObj){
			var o = canvasObj.ctx;
			o.save();
			o.fillStyle = "#09F";
			o.globalAlpha = 0.2;
			o.fillRect(x,y,width,height);
			o.restore();
		};
		
		/**
		 * 绘制选择的连线
		 */
		this.drawSelectedLine = function(canvasObj,flowObj){
			var len = this.x.length;
			var o = canvasObj.ctx;
			o.save();
			o.fillStyle = this.style;
			o.globalAlpha = 0.2;
			o.lineWidth = 8;
			o.lineCap = "round";
			o.lineJoin = "round";
			o.beginPath();
			var fel,tel;
			var felc,telc;
			// 得到开始对象的中点
			if(this.fromElId!=null&&this.fromElId!=""){
				fel = flowObj.getElById(this.fromElId,"step");
				felc = Plywet.widget.FlowChartUtils.getMidPoint(canvasObj.config,fel.el.dx,fel.el.dy,fel.el.dWidth,fel.el.dHeight);
			}
			// 得到结束对象的中点
			if(this.toElId!=null&&this.toElId!=""){
				tel = flowObj.getElById(this.toElId,"step");
				telc = Plywet.widget.FlowChartUtils.getMidPoint(canvasObj.config,tel.el.dx,tel.el.dy,tel.el.dWidth,tel.el.dHeight);
			}
			
			var nc;
			if(len<=2 && this.fromElId == this.toElId){
				var r = Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, 20);
				o.arc(this.x[0],(this.y[0]-r),r,(Math.PI*0.25),(Math.PI*0.75),true);
			}else{
				// 开始框体对象
				if(len==2){
					nc = {
						x : this.x[1],
						y : this.y[1]
					};
				}else{
					nc = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[1],
						y : this.y[1]
					});
				}
				fc = this.getLineShortedCoords(canvasObj.config.scale,Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, fel.el.dWidth),Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, fel.el.dHeight),{
					x : felc.x,
					y : felc.y,
					x2 : nc.x,
					y2 : nc.y
				});
				
				// 结束框体对象
				if(len==2){
					nc = {
						x : this.x[0],
						y : this.y[0]
					};
				}else{
					nc = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[len-2],
						y : this.y[len-2]
					});
				}
				tc = this.getLineShortedCoords(canvasObj.config.scale,Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, tel.el.dWidth),Plywet.widget.FlowChartUtils.scaleLength(canvasObj.config.scale, tel.el.dHeight),{
					x : telc.x,
					y : telc.y,
					x2 : nc.x,
					y2 : nc.y
				});
				o.moveTo(fc.x,fc.y);
				for(var i=1;i<len-1;i++){
					nc = Plywet.widget.FlowChartUtils.scalePoint(canvasObj.config, {
						x : this.x[i],
						y : this.y[i]
					});
					o.lineTo(nc.x,nc.y);
					
					this.drawJoinRect(nc.x-5,nc.y-5,10,10,canvasObj);
				}
				o.lineTo(tc.x,tc.y);
			}	
			o.stroke();
			o.restore();
		};
		
		/**
		 * 单击事件
		 * @param canvasObj 该Step元素的父canvas对象
		 * @param flowObj 该Step元素的父对象
		 */
		this.dealClick = function(canvasObj,flowObj){
			// 表示显示状态，需要选中其
			if(this.state=="show"){
				this.state="selected";
				flowObj.changeElSelectedState(this.id,"hop",true);
			}
			// 表示选中状态，需要取消选中
			else if(this.state=="selected"){
				this.state="show";
				flowObj.changeElSelectedState(this.id,"hop",false);
			}
			
			// 调用扩展事件
			if(this.onClick) eval(this.onClick);
		};
		
		/**
		 * 双击事件
		 * @param canvasObj 该Step元素的父canvas对象
		 * @param flowObj 该Step元素的父对象
		 *
		 * @interface
		 */
		this.dealDblClick = function(canvasObj, flowObj) {
			// 调用扩展事件
			if(this.onDblClick) eval(this.onDblClick);
		};
		
		/**
	 	 * 右键事件
	 	 * @param canvasObj 该Step元素的父canvas对象
		 * @param flowObj 该Step元素的父对象
		 *
		 * @interface
		 */
		this.dealContextMenu = function(canvasObj, flowObj) {
			// 调用扩展事件
			if(this.onContextMenu) eval(this.onContextMenu);
		};
		
		/**
		 * 圈选事件
		 * @param canvasObj 该Step元素的父canvas对象
		 * @param flowObj 该Step元素的父对象
		 * 
		 * @interface
		 */
		this.dealRope = function(canvasObj,flowObj){
			// 如果是显示状态，需要选中其
			if(this.state=="show"){
				this.state="selected";
				flowObj.changeElSelectedState(this.id,"hop",true);
			}
			// 如果是选中状态，不操作
			
			// 调用扩展事件
			if(this.onRope) eval(this.onRope);
		};
		
		/**
		 * 移动事件
		 * @param canvasObj 该Step元素的父canvas对象
		 * @param flowObj 该Step元素的父对象
		 * 
		 * @interface
		 */
		this.dealMove = function(canvasObj,flowObj){
			// 调用扩展事件
			if(this.onMove) eval(this.onMove);
		};
    }
    
    function FlowObject() {
		// 初始化
		this.init = function(_canvasObj, _config) {
			// 图形DIV
			this.canvasObj = _canvasObj;
			
			this.id = "";
			
			this.canvasType = "Flow";

			this.config = {
				// 取消选中时触发的事件
				onClearAll : "",
				// 发生改变时触发的事件
				onModify : "",
				// 默认属性
				defaultAttributes : {
					// 用于初始化Hop时
					onInitHop : {
					},
					// 用于初始化Step时
					onInitStep : {
					}
					
				},
				// 图形中的所有元素
				canvasEls : {
					steps : [],
					hops : []
				},
				// 图形中的所有选中的元素
				selectedEls : {
					steps : [],
					hops : []
				},
				// 捡起的对象
				pickupEl : {
					el : null,
					index : 0,
					selectedElOffsets : {
						steps : {},//存放偏移量
						hops : {}
					},
					type : ""// 两种：stepSingle、stepList和joinBlock
				},
				// 扩展数据
				extendData : null,
				// 内部画线保存的对象
				stateHop : null
				
			};
			
			if (_config.id) {
				this.id = _config.id;
			}
			
			if (_config.onClearAll) {
				this.config.onClearAll = _config.onClearAll;
			}
			
			if (_config.onModify) {
				this.config.onModify = _config.onModify;
			}
			
			if (_config.extendData) {
				this.config.extendData = _config.extendData;
			}
			
			if (_config.defaultAttributes) {
				var das = _config.defaultAttributes;
				if(das.onInitHop){
					this.config.defaultAttributes.onInitHop = das.onInitHop;
				}
				if(das.onInitStep){
					this.config.defaultAttributes.onInitStep = das.onInitStep;
				}
			}

			if (_config.canvasEls) {
				if (_config.canvasEls.steps) {
					for(var i=0;i<_config.canvasEls.steps.length;i++){
						var _stepOption = {};
						jQuery.extend(_stepOption, this.config.defaultAttributes.onInitStep);
						jQuery.extend(_stepOption, {
							dx : Math.floor(this.canvasObj.config.canvasConfig.width / 3),
							dy : Math.floor(this.canvasObj.config.canvasConfig.height / 3)
						});
						jQuery.extend(_stepOption, _config.canvasEls.steps[i]);
						// 在初始化图形是，对于Step要进行判断类型，根据不同的类型生成相应的Model
						var s = $.stepModel(_stepOption.type,_stepOption);
						this.config.canvasEls.steps.push(s);
					}
				}
				if (_config.canvasEls.hops) {
					for(var i=0;i<_config.canvasEls.hops.length;i++){
						var _hopOption = {};
						jQuery.extend(_hopOption, this.config.defaultAttributes.onInitHop);
						jQuery.extend(_hopOption, _config.canvasEls.hops[i]);
						this.config.canvasEls.hops.push($.hopModel(_hopOption));
					}
				}
			}
			
			this.validateAndChangeEl();

		};

		// 销毁
		this.destroy = function() {
			this.canvasObj = null;
			this.config = null;
		};
		
		this.getShortElsValue = function() {
			var canvasElsStr = "{";
			canvasElsStr += "\"steps\":[";
			for(var i=0;i<this.config.canvasEls.steps.length;i++){
				if(i>0){
					canvasElsStr += ",";
				}
				canvasElsStr += this.config.canvasEls.steps[i].getElValue();
			}
			canvasElsStr += "],";
			canvasElsStr += "\"hops\":[";
			for(var i=0;i<this.config.canvasEls.hops.length;i++){
				if(i>0){
					canvasElsStr += ",";
				}
				canvasElsStr += this.config.canvasEls.hops[i].getElValue();
			}
			canvasElsStr += "]";
			canvasElsStr += "}";
			
			return '{"canvasEls":'+canvasElsStr+'}';
		};
		
		/**
		 * 获得当前页面所有元素的JSON值
		 */
		this.getElsValue = function() {
			var defaultAttributes = Plywet.toJSONString(this.config.defaultAttributes);
			
			var canvasElsStr = "{";
			canvasElsStr += "\"steps\":[";
			for(var i=0;i<this.config.canvasEls.steps.length;i++){
				if(i>0){
					canvasElsStr += ",";
				}
				canvasElsStr += this.config.canvasEls.steps[i].getElValue();
			}
			canvasElsStr += "],";
			canvasElsStr += "\"hops\":[";
			for(var i=0;i<this.config.canvasEls.hops.length;i++){
				if(i>0){
					canvasElsStr += ",";
				}
				canvasElsStr += this.config.canvasEls.hops[i].getElValue();
			}
			canvasElsStr += "]";
			canvasElsStr += "}";
			
			var otherStr = "";
			if (this.id) {
				otherStr = otherStr + ',"id":"' + this.id + '"';
			}
			
			if (this.config.onClearAll) {
				otherStr = otherStr + ',"onClearAll":"' + this.config.onClearAll + '"';
			}
			
			if (this.config.onModify) {
				otherStr = otherStr + ',"onModify":"' + this.config.onModify + '"';
			}
			
			if (this.config.extendData) {
				otherStr = otherStr + ',"extendData":' +  Plywet.toJSONString(this.config.extendData);
			}
				
			
			return '{"defaultAttributes":'+defaultAttributes+','+'"canvasEls":'+canvasElsStr+otherStr+'}';
		};

		/**
		 * 清除配置
		 */
		this.cleanConfig = function() {
			this.config.canvasEls.steps.length = 0;
			this.config.canvasEls.hops.length = 0;
			this.config.selectedEls.steps.length = 0;
			this.config.selectedEls.hops.length = 0;
		};
		
		/**
		 * 初始化pickup，只针对step
		 */
		this.initPickup = function(el, type){
			this.config.pickupEl.el = el.cloneEl();
			this.config.pickupEl.type = type;
			var steps = this.config.selectedEls.steps;
			for(var i=0;i<steps.length;i++){
				var v = steps[i];
				if(v && v != undefined){
					var step = this.getElById(v, "step").el;
					var offset = {
						x : step.dx-el.dx-(el.dWidth / 2),
						y : step.dy-el.dy-(el.dHeight / 2)
					};
					eval("jQuery.extend(this.config.pickupEl.selectedElOffsets.steps, {'"+step.id+"':offset})");
				}
			}
			var hops = this.config.selectedEls.hops;
			for(var i=0;i<hops.length;i++){
				var v = hops[i];
				if(v && v != undefined){
					var hop = this.getElById(v, "hop").el;
					var offset = {
						x : [],
						y : []
					};
					for(var j=0;j<hop.x.length;j++){
						offset.x.push(hop.x[j]-el.dx-(el.dWidth / 2));
						offset.y.push(hop.y[j]-el.dy-(el.dHeight / 2));
					}
					eval("jQuery.extend(this.config.pickupEl.selectedElOffsets.hops, {'"+hop.id+"':offset});");
				}
			}
		};
		
		/**
		 * 扩展内部处理：创建Hop连接线
		 * (只有一个输入参数：mouseType)
		 */
		this.dealCreateHop = function(mouseType){
			// 鼠标按下，取得起始点
			if(mouseType == "mouseDown"){
				if(this.config.stateHop && this.config.stateHop!=null){
					return;
				}
				
				var clickTime = new Date();
				this.canvasObj.lastClickTime = clickTime.getTime();
				
				var mouseDown = this.canvasObj.config.mouseConfig.mouseDown;
				var el = this.getElByCoords(mouseDown, "step");
				
				if(el != null){
					// 是否可以连线
					if(!el.el.provider || el.el.provider == "")
						return;
					
					// 外部判断是否可以开始连线
					if(el.el.onStartHop!=null){
						eval("var rtnValue="+el.el.onStartHop);
						if(!rtnValue){
							return;
						}
					}
					
					var _hopOption = {
							x : [0,mouseDown.x],
							y : [0,mouseDown.y]
						};
					jQuery.extend(_hopOption, this.config.defaultAttributes.onInitHop);
					
					this.config.stateHop = $.hopModel(_hopOption);
					this.config.stateHop.fromElId = el.el.id;
					this.config.stateHop.provider = el.el.provider;
					
					// 关闭canvas中的圈选功能
					this.canvasObj.config.innerControlType = "withoutRope";
					
					return;
				}
			}
			// 鼠标双击，取消连线
			else if(mouseType == "dblclick"){
				this.config.stateHop = null;
			}
			// 鼠标移动，生成连接线
			else if(mouseType == "mouseMove"){
				var mouseMove = this.canvasObj.config.mouseConfig.mouseMove;
				var el = this.getElByCoords(mouseMove, "step");
				if(this.config.stateHop==null){
					// 判断是否可以创建连线，改变鼠标
					if(el == null){
						this.canvasObj.canvasDiv.css("cursor","auto");
					}else if(!el.el.provider || el.el.provider == ""){
						this.canvasObj.canvasDiv.css("cursor","not-allowed");
					}else{
						if(el.el.onStartHop!=null){
							eval("var rtnValue="+el.el.onStartHop);
							if(rtnValue){
								this.canvasObj.canvasDiv.css("cursor","alias");
							}else{
								this.canvasObj.canvasDiv.css("cursor","not-allowed");
							}
						}else{
							this.canvasObj.canvasDiv.css("cursor","alias");
						}
					}
					return;
				}else{
					// 判断是否可以接受连线，改变鼠标
					if(el == null){
						this.canvasObj.canvasDiv.css("cursor","auto");
					}else if(!this.acceptable(el.el,this.config.stateHop.provider)){
						this.canvasObj.canvasDiv.css("cursor","not-allowed");
					}else{
						if(el.el.onEndHop!=null){
							var setting = {
								fromElId : this.config.stateHop.fromElId,
								toElId : el.el.id
							};
							eval("var rtnValue="+el.el.onEndHop+";");
							if(rtnValue){
								this.canvasObj.canvasDiv.css("cursor","alias");
							}else{
								this.canvasObj.canvasDiv.css("cursor","not-allowed");
							}
						}else{
							this.canvasObj.canvasDiv.css("cursor","alias");
						}
					}
					var stateHopX = this.config.stateHop.x;
					var stateHopY = this.config.stateHop.y;
					var len = stateHopX.length;
					stateHopX[len-1] = mouseMove.x;
					stateHopY[len-1] = mouseMove.y;
					this.config.stateHop.x = stateHopX;
					this.config.stateHop.y = stateHopY;
					
					return;
				}
			}
			else if(mouseType == "mouseUp"){
				
				var mouseUp = this.canvasObj.config.mouseConfig.mouseUp;
				var el = this.getElByCoords(mouseUp, "step");
				// 添加Hop
				if(el != null){
					// 判断是否可以接受连线
					if(!this.acceptable(el.el,this.config.stateHop.provider)){
						return;
					}
					
					// 外部判断是否接受
					if(el.el.onEndHop!=null){
						var setting = {
							fromElId : this.config.stateHop.fromElId,
							toElId : el.el.id
						};
						eval("var rtnValue="+el.el.onEndHop+";");
						if(!rtnValue){
							return;
						}
					}
					
					this.config.stateHop.toElId = el.el.id;
					
					if(el.el.onAcceptHop!=null){
						eval(el.el.onAcceptHop);
					}
					
					// 添加Hop到canvasEls.hops
					this.config.canvasEls.hops.push(this.config.stateHop);
					this.validateAndChangeEl();
					
					this.modifyHandler();
					
					this.config.stateHop = null;
				}
				// 添加Hop连接点
				else {
					var om = Plywet.widget.FlowChartUtils.arcScalePoint(this.canvasObj.config, {
						x : mouseUp.x,
						y : mouseUp.y
					});
					var stateHopX = this.config.stateHop.x;
					var stateHopY = this.config.stateHop.y;
					var len = stateHopX.length;
					stateHopX[len-1] = om.x;
					stateHopY[len-1] = om.y;
					stateHopX.push(mouseUp.x);
					stateHopY.push(mouseUp.y);
					this.config.stateHop.x = stateHopX;
					this.config.stateHop.y = stateHopY;
				}
				return;
			}
		};
		
		this.acceptable = function(el,provider){
			var acceptable = el.acceptAll;
			if(el.accept && el.accept.length>0){
				for(var i=0;i<el.accept.length;i++){
					if(el.accept[i] == provider){
						acceptable = true;
						break;
					}
				}
			}
			return acceptable;
		};
		
		/**
		 * 接口：处理鼠标事件
		 * @Interface
		 */
		this.deal = function(mouseType,methodName){
			// 如果是方法调用
			if(methodName && methodName!=undefined && methodName!=""){
				try{
					eval("this."+methodName+"(mouseType);");
				}catch(e){
				}
				return;
			}
			
			// 如果是鼠标按下事件，如果是点击对象，表示捡起对象
			if(mouseType == "mouseDown"){
				// 1.首先判断是不是放大缩小的块
				var el = this.getChangeBlock(this.canvasObj.config.mouseConfig.mouseDown);
				if(el != null){
					this.config.pickupEl.el = el.el.cloneEl();
					this.config.pickupEl.type = "changeBlock";
					this.config.pickupEl.index = el.index;
					// 关闭canvas中的圈选功能
					this.canvasObj.config.innerControlType = "withoutRope";
					return;
				}
				
				// 2.判断是否是step
				el = this.getElByCoords(this.canvasObj.config.mouseConfig.mouseDown, "step");
				if(el != null){
					// 1.1如果是选中状态，表示拖拽所有选中的元素
					if(el.el.isSelected()){
						this.initPickup(el.el, "stepList");
						// 关闭canvas中的圈选功能
						this.canvasObj.config.innerControlType = "withoutRope";
						return;
					}
					// 1.2如果是未选中状态，表示拖拽单个元素
					else{
						this.config.pickupEl.el = el.el.cloneEl();
						this.config.pickupEl.type = "stepSingle";
						// 关闭canvas中的圈选功能
						this.canvasObj.config.innerControlType = "withoutRope";
						return;
					}
				}
				
				// 3.然后判断是否是hop
				el = this.getElByCoords(this.canvasObj.config.mouseConfig.mouseDown, "hop");
				
				if(el != null){
					// 3.1如果是选中状态，
					if(el.el.isSelected()){
						var jbel = el.el;
						var jbelIndex = jbel.getJoinBlockByCoords(this.canvasObj,
								this.canvasObj.config.mouseConfig.mouseDown);
						// 如果是joinBlock
						if(jbelIndex != -1){
							this.config.pickupEl.el = jbel.cloneEl();
							this.config.pickupEl.type = "joinBlock";
							this.config.pickupEl.index = jbelIndex;
						}
						// 如果不是joinBlock，添加一个连接块
						else {
							this.config.pickupEl.el = jbel.cloneEl();
							this.config.pickupEl.type = "addJoinBlock";
							this.config.pickupEl.index = jbel.getAddJoinBlockPoint(this.canvasObj, this, this.canvasObj.config.mouseConfig.mouseDown);
						}
						// 关闭canvas中的圈选功能
						this.canvasObj.config.innerControlType = "withoutRope";
						return;
					}
					// 3.2如果是未选中状态，
					else{
						this.config.pickupEl.el = el.el.cloneEl();
						this.config.pickupEl.type = "addJoinBlock";
						this.config.pickupEl.index = el.el.getAddJoinBlockPoint(this.canvasObj, this, this.canvasObj.config.mouseConfig.mouseDown);
					
						this.canvasObj.config.innerControlType = "withoutRope";
						return;
					}
				}
				
			}
			// 如果是鼠标抬起事件，判断选定内容
			else if(mouseType == "mouseUp"){
				var fc = this.canvasObj.config.mouseConfig.mouseDown;
				var tc = this.canvasObj.config.mouseConfig.mouseUp;
				if(fc!=null && tc!=null){
					// 认为是点击事件
					if((Math.abs(fc.x-tc.x))<5 && (Math.abs(fc.y-tc.y))<5){
						this.dealClick(fc);
					}
					// 认为是圈选事件，或移动事件
					else{
						if(this.config.pickupEl.el == null){
							this.dealRope(fc,tc);
						}else{
							this.dealMove(tc);
							
							// 如果是拖动连接块，判断连接块的斜率，
							// 如果在一定范围内，则删除该连接块
							if(this.config.pickupEl.type=="joinBlock"){
								var pickupEl = this.config.pickupEl.el;
								var hop = this.config.canvasEls.hops[this.getElById(pickupEl.id, "hop").index];
								var hopIndex = this.config.pickupEl.index;
								
								var hopX = [].concat(hop.x.slice(0,hopIndex),hop.x.slice(hopIndex+1));
								var hopY = [].concat(hop.y.slice(0,hopIndex),hop.y.slice(hopIndex+1));
								
								var _hopOption = {
										x : hopX,
										y : hopY
									};
								
								var tempHop = $.hopModel(_hopOption);
								
								var isdel = tempHop.getAddJoinBlockPoint(this.canvasObj,this,tc);
								
								if(isdel>=0){
									hop.x = hopX;
									hop.y = hopY;
								}
								
								tempHop = null;
							}
						}
					}
				}
			}
			// 如果是鼠标移动事件，并且是hold状态，判断移动选定内容
			else if(mouseType == "mouseMove"){
				var fc = this.canvasObj.config.mouseConfig.mouseDown;
				var tc = this.canvasObj.config.mouseConfig.mouseMove;
				// 如果没有pickupEl，则表示是圈选
				if(this.config.pickupEl.el == null){
					if(fc!=null){
						this.dealRope(fc,tc);
					}
				}
				// 否则表示移动对象
				else{
					this.dealMove(tc);
				}
			}
			// 如果是鼠标双击事件
			else if(mouseType == "dblclick"){
				var c = this.canvasObj.config.mouseConfig.mouseDown;
				// 调用双击事件
				this.dealDblClick(c);
			}
			// 如果是右键事件
			else if(mouseType == "contextMenu"){
				var c = this.canvasObj.config.mouseConfig.mouseUp;
				this.dealContextMenu(c);
			}
		};
		
		/**
		 * 接口：清除鼠标属性
		 */
		this.clearMouseConfig = function(){
			this.config.pickupEl.el = null;
			this.config.pickupEl.type = "";
			this.config.pickupEl.index = 0;
			this.config.pickupEl.selectedElOffsets.steps = {};
			this.config.pickupEl.selectedElOffsets.hops = {};
			// 打开canvas中的圈选功能
			this.canvasObj.config.innerControlType = "";
		};
		
		/**
		 * 改变Flow中该元素注册的选中状态
		 * @param id 该元素的id
		 * @param type 该元素的类型（step和hop）
		 * @param selected 该元素是否选中
		 */
		this.changeElSelectedState = function(id,type,selected){
			var els;
			if(type == "step"){
				els = this.config.selectedEls.steps;
			}else if(type == "hop"){
				els = this.config.selectedEls.hops;
			}
			if(selected){
				els.push(id);
			}else{
				for(var i=0;i<els.length;i++){
					if(els[i] == id){
						delete els[i];
						return;
					}
				}
			}
		};
		
		/**
		 * 移动元素
		 * 
		 * @param el 元素
		 * @param type 类型
		 * @param c 鼠标坐标
		 * @param o 偏移量
		 * 
		 */
		this.moveEl = function(el,type,c,o){
			if(type == "step"){
				if(!o || o == null){
					o = {
						x : 0-(el.dWidth/2),
						y : 0-(el.dHeight/2)
					};
				}
				var step = this.config.canvasEls.steps[this.getElById(el.id, "step").index];
				step.dx = c.x + o.x;
				step.dy = c.y + o.y;
				
				// 执行step的移动操作
				step.dealMove(this.canvasObj,this);
			}else if(type == "hop"){
				var hop = this.config.canvasEls.hops[this.getElById(el.id, "hop").index];
				for(var i=0;i<hop.x.length;i++){
					hop.x[i] = c.x + o.x[i];
					hop.y[i] = c.y + o.y[i];
				}
				// 执行hop的移动操作
				hop.dealMove(this.canvasObj,this);
			}
		};
		
		this.modifyHandler = function(){
			if(this.config.onModify){
				var canvasObj = this.canvasObj;
				var flowObj = this;
				eval(this.config.onModify);
			}
		};
		
		/**
		 * 移动事件
		 */
		this.dealMove = function(c){
			// 转换成正常比例下的鼠标
			var p = Plywet.widget.FlowChartUtils.getCloseGridPoint(c,this.canvasObj.config);
			var om = Plywet.widget.FlowChartUtils.arcScalePoint(this.canvasObj.config, {
				x : p.x,
				y : p.y
			});
			// 如果是未选中对象为stepSingle
			var pickupEl = this.config.pickupEl.el;
			if(this.config.pickupEl.type=="stepSingle"){
				this.moveEl(pickupEl, "step", om);
				
				this.modifyHandler();
			}
			// 如果是选中对象为stepList
			else if(this.config.pickupEl.type=="stepList"){
				// 移动选定的step
				var stepIds = this.config.selectedEls.steps;
				for(var i=0;i<stepIds.length;i++){
					var v = stepIds[i];
					if(v && v != undefined){
						var el = this.getElById(v, "step").el;
						// 其他选定对象到拖动对象的距离
						if(el.id == pickupEl.id){
							this.moveEl(pickupEl, "step", om);
						}else{
							this.moveEl(el, "step", om, this.config.pickupEl.selectedElOffsets.steps[v]);
						}
					}
				}
				
				// 移动选定的hop
				var hops = this.config.selectedEls.hops;
				for(var i=0;i<hops.length;i++){
					var v = hops[i];
					if(v && v != undefined){
						var hop = this.getElById(v, "hop").el;
						this.moveEl(hop, "hop", om, this.config.pickupEl.selectedElOffsets.hops[v]);
					}
				}
				
				this.modifyHandler();
			}
			// 判断是否是joinBlock
			else if(this.config.pickupEl.type=="joinBlock"){
				var hop = this.config.canvasEls.hops[this.getElById(pickupEl.id, "hop").index];
				var hopX = hop.x;
				var hopY = hop.y;
				hopX[this.config.pickupEl.index] = om.x;
				hopY[this.config.pickupEl.index] = om.y;
				hop.x = hopX;
				hop.y = hopY;
				
				hop.dealMove(this.canvasObj,this);
				this.modifyHandler();
			}
			// 判断是否是addJoinBlock
			else if(this.config.pickupEl.type=="addJoinBlock"){
				// 添加连接点
				if(this.config.pickupEl.index>=0){
					var hop = this.config.canvasEls.hops[this.getElById(pickupEl.id, "hop").index];
					var jbindex = this.config.pickupEl.index+1;
					var hopX = [].concat(hop.x.slice(0,jbindex),om.x,hop.x.slice(jbindex));
					var hopY = [].concat(hop.y.slice(0,jbindex),om.y,hop.y.slice(jbindex));
					hop.x = hopX;
					hop.y = hopY;
					
					this.config.pickupEl.index = jbindex;
					this.config.pickupEl.type = "joinBlock";
					
					hop.dealMove(this.canvasObj,this);
					this.modifyHandler();
				}
			}
			// 判断是不是缩放点
			else if(this.config.pickupEl.type=="changeBlock"){
				var pickupEl = this.config.pickupEl.el;
				var step = this.config.canvasEls.steps[this.getElById(pickupEl.id, "step").index];
				var stepIndex = this.config.pickupEl.index;
				
				// 上缩放块，改变起始点
				if(stepIndex == 0){
					var maxD = {
						x : pickupEl.dx+pickupEl.dWidth-32,
						y : pickupEl.dy+pickupEl.dHeight-32
					};
					step.dx = (om.x<maxD.x)?om.x:maxD.x;
					step.dy = (om.y<maxD.y)?om.y:maxD.y;
					step.dWidth = pickupEl.dWidth + pickupEl.dx - om.x;
					step.dHeight = pickupEl.dHeight + pickupEl.dy - om.y;
				}else{
					step.dWidth = om.x - pickupEl.dx;
					step.dHeight = om.y - pickupEl.dy;
				}
				
				if(step.dWidth<32){
					step.dWidth = 32;
				}
				if(step.dHeight<32){
					step.dHeight = 32;
				}
				this.modifyHandler();
			}
			
		};
		
		/**
		 * 点击事件
		 * @param c 点
		 */
		this.dealClick = function(c){
			// 如果单击step对象，调用其单击事件
			var o = this.getElByCoords(c, "step");
			if(o != null){
				o.el.dealClick(this.canvasObj,this);
				return;
			}
			
			// 如果单击step对象，调用其单击事件
			o = this.getElByCoords(c, "hop");
			if(o != null){
				o.el.dealClick(this.canvasObj,this);
				return;
			}
			
			// 如果是点击其他地方，取消选中
			this.clearAllSelectedElements();
		};
		
		/**
		 * 清除所有选择元素对象
		 */
		this.clearAllSelectedElements = function(){
			var stepIds = this.config.selectedEls.steps;
			for(var i=0;i<stepIds.length;i++){
				var v = stepIds[i];
				if(v && v != undefined){
					var el = this.getElById(v, "step").el;
					el.state="show";
				}
			}
			
			var hopIds = this.config.selectedEls.hops;
			for(var i=0;i<hopIds.length;i++){
				var v = hopIds[i];
				if(v && v != undefined){
					var el = this.getElById(v, "hop").el;
					el.state="show";
				}
			}
			
			this.config.selectedEls.steps.length = 0;
			this.config.selectedEls.hops.length = 0;
			
			if(this.config.onClearAll){
				var canvasObj = this.canvasObj;
				var flowObj = this;
				eval(this.config.onClearAll);
			}
		};
		
		/**
		 * 双击事件
		 * @param c 点
		 */
		this.dealDblClick = function(c){
			// 判断是否双击step，调用step的双击事件
			var el = this.getElByCoords(c, "step");
			if (el != null) {
				var jbel = el.el;
				jbel.dealDblClick(this.canvasObj,this);
			} else {
				// 通过坐标判断是点击选中hop的连接点
				var el = this.getElByCoords(c, "hop");
				if(el != null){
					var jbel = el.el;
					var jbelIndex = jbel.getJoinBlockByCoords(this.canvasObj,c);
					// 如果是joinBlock，取消joinBlock
					if(jbelIndex != -1){
						var jbindex = jbel.getAddJoinBlockPoint(this.canvasObj, this, c) + 1;
						
						var hopX = [].concat(jbel.x.slice(0,jbindex),jbel.x.slice(jbindex+1));
						var hopY = [].concat(jbel.y.slice(0,jbindex),jbel.y.slice(jbindex+1));
						jbel.x = hopX;
						jbel.y = hopY;
						
						// 调用移动方法
						jbel.dealMove(this.canvasObj,this);
					}
					// 如果不是joinBlock，调用hop的双击事件
					else {
						jbel.dealDblClick(this.canvasObj,this);
					}
				} else {
					var el = this.getElByCoords(c, "hop");
					if(el != null){
						var jbel = el.el;
						jbel.dealDblClick(this.canvasObj,this);
					}
				}
			}
		};
		
		/**
		 * 右键事件
		 * @param c 点
		 */
		this.dealContextMenu = function(c){
			// 判断是否右键step，调用step的右键事件
			var el = this.getElByCoords(c, "step");
			if (el != null) {
				var jbel = el.el;
				jbel.dealContextMenu(this.canvasObj,this);
			}else{
				el = this.getElByCoords(c, "hop");
				if(el != null){
					var jbel = el.el;
					jbel.dealContextMenu(this.canvasObj,this);
				}
			}
		};
		
		/**
		 * 圈选事件
		 */
		this.dealRope = function(fc,tc){
			var rope = {
				x : fc.x,
				y : fc.y,
				x2 : tc.x,
				y2 : tc.y
			};
			this.setElsByRope(rope);
		};
		
		/**
		 * 接口：重画缩略图标
		 */
		this.redrawOverview = function(){
			var pos,x,y,w,
				oconfig=this.canvasObj.oconfig;
			if (this.config.canvasEls.steps.length > 0) {
				for ( var i = 0; i < this.config.canvasEls.steps.length; i++) {
					var v = this.config.canvasEls.steps[i];
					if(v && v != undefined){
						pos = v.position;
						x = oconfig.editRect.x + pos.x*oconfig.scale;
						y = oconfig.editRect.y + pos.y*oconfig.scale;
						w = (pos.x2 - pos.x)*oconfig.scale;
						Plywet.widget.FlowChartUtils.drawRect(this.canvasObj.octx,x,y,w,w,"#dc003f");
					}
				}
			}
		};
		
		/**
		 * 接口：获得整体位置信息（step和hop）
		 */
		this.getSize = function(els){
			var pos;
			// 1.step位置
			if (this.config.canvasEls.steps.length > 0) {
				for ( var i = 0; i < this.config.canvasEls.steps.length; i++) {
					var v = this.config.canvasEls.steps[i];
					if(v && v != undefined){
						pos = v.position;
						els.x = Math.min(els.x, pos.x);
						els.y = Math.min(els.y, pos.y);
						els.x2 = Math.max(els.x2, pos.x2);
						els.y2 = Math.max(els.y2, pos.y2);
					}
				}
			}

			// 2.hop位置
			if (this.config.canvasEls.hops.length > 0) {
				for ( var i = 0; i < this.config.canvasEls.hops.length; i++) {
					var v = this.config.canvasEls.hops[i];
					if(v && v != undefined){
						pos = v.position;
						els.x = Math.min(els.x, pos.x);
						els.y = Math.min(els.y, pos.y);
						els.x2 = Math.max(els.x2, pos.x2);
						els.y2 = Math.max(els.y2, pos.y2);
					}
				}
			}
			return els;
		};

		/**
		 * 接口：重画图形（step和hop）
		 * 
		 * @param _offset
		 *            外部传入的偏移量
		 */
		this.redraw = function(_offset) {
			var offset = _offset;
			if (offset && offset != null) {
				this.canvasObj.config.offset = offset;
			}

			// 1.绘制step
			if (this.config.canvasEls.steps.length > 0) {
				for ( var i = 0; i < this.config.canvasEls.steps.length; i++) {
					var v = this.config.canvasEls.steps[i];
					if(v && v != undefined){
						v.draw(null, offset, this.canvasObj);
						v.position = this.getPosition(v, "step");
					}
				}
			}

			// 2.绘制hop
			if (this.config.canvasEls.hops.length > 0) {
				for ( var i = 0; i < this.config.canvasEls.hops.length; i++) {
					var v = this.config.canvasEls.hops[i];
					if(v && v != undefined){
						v.draw(null, offset, this.canvasObj, this);
						v.position = this.getPosition(v, "hop");
					}
				}
			}
			
			// 3.绘制画线的hop
			if (this.canvasObj.config.outerControlType == "inner:dealCreateHop") {
				if(this.config.stateHop && this.config.stateHop!=null){
					this.config.stateHop.draw(null, offset, this.canvasObj, this);
				}
			}
		};
		
		/**
		 * 通过Id和类型获得图形实体对象
		 * @param id
		 * @param type
		 */
		this.getElById = function(id, type) {
			if(type=="step"){
				var steps = this.config.canvasEls.steps;
				for(var i=0;i<steps.length;i++){
					var v = steps[i];
					if(v.id == id){
						return {
							el : v,
							index : i
						};
					}
				}
			}else if(type == "hop"){
				var hops = this.config.canvasEls.hops;
				for(var i=0;i<hops.length;i++){
					var v = hops[i];
					if(v.id == id){
						return {
							el : v,
							index : i
						};
					}
				}
			}
			return null;
		};
		
		/**
		 * 通过Id和类型获得图形实体对象
		 * @param id ID
		 * @param type 类型
		 * @return {
		 * 		el : 元素对象
		 * 		index ：元素对象在selectedEls中的位置
		 * }
		 */
		this.getSelectedElById = function(id, type) {
			var o, ids;
			if(type=="step"){
				ids = this.config.selectedEls.steps;
			}else if(type=="hop"){
				ids = this.config.selectedEls.hops;
			}
			for(var i=0;i<ids.length;i++){
				if(ids[i] && ids[i] != undefined){
					if(ids[i] == id){
						o = this.getElById(ids[i], type);
						return {
							el : o.el,
							index : i
						};
					}
				}
			}
			return null;
		};
		
		/**
		 * 通过坐标获得已选定的对象
		 * 倒序选择最后选定对象
		 * @param c 点
		 * @param type 类型
		 * @return {
		 * 		el : 元素对象
		 * 		index ：元素对象在selectedEls中的位置
		 * }
		 */
		this.getSelectedElByCoords = function(c, type){
			var o, ids;
			if(type=="step"){
				ids = this.config.selectedEls.steps;
			}else if(type=="hop"){
				ids = this.config.selectedEls.hops;
			}
			for(var i=ids.length-1;i>=0;i--){
				if(ids[i] && ids[i] != undefined){
					o = this.getElById(ids[i], type);
					if(this.shouldSelected(c, o.el, type)){
						return {
							el : o.el,
							index : i
						};
					}
				}
			}
			return null;
		};
		
		/**
		 * 通过坐标获得存在的对象
		 * 倒序选择最后生成的对象
		 * @param c 点
		 * @param type 类型
		 * @return {
		 * 		el : 元素对象
		 * 		index ：元素对象在canvasEls中的位置
		 * }
		 */
		this.getElByCoords = function(c, type){
			var no, o, els;
			
			if(type=="step"){
				els = this.config.canvasEls.steps;
			}else if(type=="hop"){
				els = this.config.canvasEls.hops;
			}
			
			for(var i=els.length-1;i>=0;i--){
				o = els[i];
				if(this.shouldSelected(c, o, type)){
					return {
						el : o,
						index : i
					};
				}
			}
			return null;
		};
		
		/**
		 * 设置圈选框中对象
		 * @param rope 圈选框
		 */
		this.setElsByRope = function(rope){
			if(rope!=null){
				// 得到圈选框起始点坐标
				var x = rope.x;
				var x2 = rope.x2;
				var y = rope.y;
				var y2 = rope.y2;
				if(x>x2){
					x=x+x2;
					x2=x-x2;
					x=x-x2;
				}
				if(y>y2){
					y=y+y2;
					y2=y-y2;
					y=y-y2;
				}
				// 遍历steps
				var nc,ndWidth,ndHeight;
				var steps = this.config.canvasEls.steps;
				for(var i=0;i<steps.length;i++){
					var v = steps[i];
					
					nc = Plywet.widget.FlowChartUtils.scalePoint(this.canvasObj.config, {
						x : v.dx,
						y : v.dy
					});
					ndWidth = Plywet.widget.FlowChartUtils.scaleLength(this.canvasObj.config.scale, v.dWidth);
					ndHeight = Plywet.widget.FlowChartUtils.scaleLength(this.canvasObj.config.scale, v.dHeight);
					// 表示在圈选框中
					if( ( ((x<=nc.x) && ((nc.x+ndWidth/2)<=x2)) || ((x<=nc.x+ndWidth/2) && ((nc.x+ndWidth)<=x2)) )
						&& ( ((y<=nc.y) && ((nc.y+ndHeight/2)<=y2)) || ((y<=nc.y+ndHeight/2) && ((nc.y+ndHeight)<=y2)) ) ){
						// 执行元素的圈选操作
						v.dealRope(this.canvasObj,this);
					}
				}
				
				// 遍历hops
				var hops = this.config.canvasEls.hops;
				for(var i=0;i<hops.length;i++){
					var v = hops[i];
					var max = v.getLineMaxCoords(this.canvasObj);
					if( (x<=max.x) && (max.x2<=x2) && (y<=max.y) && (max.y2<=y2) ){
						// 执行元素的圈选操作
						v.dealRope(this.canvasObj,this);
					}
				}
			}
		};
		
		/**
		 * 通过坐标获得改变块
		 * @param c 坐标
		 */
		this.getChangeBlock = function(c){
			var els,o,no,no1;
			els = this.config.selectedEls.steps;
			for(var i=els.length-1;i>=0;i--){
				
				if(els[i] && els[i]!=undefined){
					
					o = this.getElById(els[i], "step").el;
					if(!o.changable){
						continue;
					}
					
					no = Plywet.widget.FlowChartUtils.scalePoint(this.canvasObj.config, {
						x : o.dx,
						y : o.dy
					});
					
					if( ( (no.x-10)<=c.x && c.x<=no.x ) && ( (no.y-10)<=c.y && c.y<=no.y ) ){
						return {
							el : o,
							index : 0
						};
					}
					
					no1 = {
						x : no.x+Plywet.widget.FlowChartUtils.scaleLength(this.canvasObj.config.scale, o.dWidth),
						y : no.y+Plywet.widget.FlowChartUtils.scaleLength(this.canvasObj.config.scale, o.dHeight)
					};
					
					if( ( no1.x <=c.x && c.x<=(no1.x+10) ) && ( no1.y <=c.y && c.y<=(no1.y+10) ) ){
						return {
							el : o,
							index : 1
						};
					}
				}
				
			}
			return null;
		};
		
	    /**
	     * 通过坐标判断是否应该被选中
	     * @param c 坐标
	     * @param o 对象
	     * @param type 类型
	     */
		this.shouldSelected = function(c, o, type){
	    	if(type=="step"){
	    		if (o){
	    			if ( ( (o.position.x-3)<=c.x) && (c.x<=(o.position.x2+3) ) 
							&& ( (o.position.y-3)<=c.y) && (c.y<=(o.position.y2+3) ) ){
						return true;
					}
	    		}
			}else if(type=="hop"){
				if ( ( (o.position.x-3)<=c.x) && (c.x<=(o.position.x2+3) ) 
						&& ( (o.position.y-3)<=c.y) && (c.y<=(o.position.y2+3) ) ){
					var index = o.getAddJoinBlockPoint(this.canvasObj,this,c);
					return (index >= 0)?true:false;
				}
				
			}
			return false;
	    };
	    
	    /**
	     * 获得对象的屏幕位置
	     */
	    this.getPosition = function(o, type){
	    	if(type=="step"){
	    		if (o){
	    			var no = Plywet.widget.FlowChartUtils.scalePoint(this.canvasObj.config, {
						x : o.dx,
						y : o.dy
					});
	    			
	    			no.x2 = no.x + Plywet.widget.FlowChartUtils.scaleLength(this.canvasObj.config.scale, o.dWidth);
	    			no.y2 = no.y + Plywet.widget.FlowChartUtils.scaleLength(this.canvasObj.config.scale, o.dHeight);
	    			
	    			return no;
	    		}
			}else if(type=="hop"){
				if(o) {
					return o.getLineMaxCoords(this.canvasObj);
				}
			}
	    	return {
	    		x:0,
	    		y:0,
	    		x2:0,
	    		y2:0
	    	};
	    };
	    
		/**
		 * 添加元素，通过复制、剪切的方式，其中如果是复制el中的el.cloneEl为true。
		 * 
		 */
	    this.appendEl = function(el,type){
			var oraId = el.id;
			var newId = null;
			var newel = null;
			
			if(type == "step"){
				if(el.cloneEl){
					newel = el.cloneEl();
				}else{
					var _el = {};
					
					jQuery.extend(_el, this.config.defaultAttributes.onInitStep);
					jQuery.extend(_el, el);
					
					var _offset = this.canvasObj.config.offset;
					
					var newp = Plywet.widget.FlowChartUtils.arcScalePoint(this.canvasObj.config,{
						x : Math.floor(this.canvasObj.config.canvasConfig.width / 3),
						y : Math.floor(this.canvasObj.config.canvasConfig.height / 3)
					});
					
					if(!_el.dx)_el.dx=newp.x;
					if(!_el.dy)_el.dy=newp.y;
					
					// 调用工厂创建
					newel = $.stepModel(_el.type,_el);
				}
				
				this.config.canvasEls.steps.push(newel);
				newId = this.validateDuplicateId(newel, this.config.canvasEls.steps.length-1, "step");
			}else if(type == "hop"){
				if(el.cloneEl){
					newel = el.cloneEl();
				}else{
					var _el = {};
					
					jQuery.extend(_el, this.config.defaultAttributes.onInitHop);
					jQuery.extend(_el, el);
					
					newel = $.hopModel(_el);
				}
				
				this.config.canvasEls.hops.push(newel);
			}
			this.validateAndChangeEl();
			this.modifyHandler();
			return {
				oraId : oraId,
				newId : newId
			};
		};
		
		/**
		 * 显示网格
		 */
		this.showGrid = function(show){
			this.canvasObj.config.showGrid = show;
			this.canvasObj.redraw();
		};
		
		/**
		 * 贴近网格
		 */
		this.closeGrid = function(close){
			this.canvasObj.config.closeGrid = close;
		};
		
		/**
		 * 100%显示
		 */
		this.showZoom100 = function(){
			this.canvasObj.config.scale = 1;
			this.canvasObj.config.offset = {
				x : 0,
				y : 0
			};
			this.canvasObj.redraw();
			this.modifyHandler();
		};
		
		/**
		 * 设置合适的尺寸
		 */
		this.fixSize = function(){
			var els = {
				x:Number.MAX_VALUE,
				y:Number.MAX_VALUE,
				x2:Number.NEGATIVE_INFINITY,
				y2:Number.NEGATIVE_INFINITY
			};
			// 调用子元素的总尺寸
			jQuery.each(this.canvasObj.childCanvas, function(k, v){
				if(v != undefined){
					v.getSize(els);
				}
			});
			var wScale = this.canvasObj.config.canvasConfig.width/(els.x2-els.x),
			hScale = this.canvasObj.config.canvasConfig.height/(els.y2-els.y),
			oldScale = this.canvasObj.config.scale
			theScale = Math.min(wScale,hScale,this.canvasObj.config.scale);
			var cx = this.canvasObj.config.canvasConfig.width/2,
			cy = this.canvasObj.config.canvasConfig.height/2;
			this.canvasObj.setCanvasScale(theScale);
			
			if(oldScale != theScale){
				this.canvasObj.config.offset.x =  cx - (els.x+els.x2)/2;
				this.canvasObj.config.offset.y =  cy - (els.y+els.y2)/2;
				this.canvasObj.config.offset.x = cx - (cx-this.canvasObj.config.offset.x)*theScale;
				this.canvasObj.config.offset.y = cy - (cy-this.canvasObj.config.offset.y)*theScale;
			}else{
				this.canvasObj.config.offset.x =  this.canvasObj.config.offset.x + cx - (els.x+els.x2)/2;
				this.canvasObj.config.offset.y =  this.canvasObj.config.offset.y + cy - (els.y+els.y2)/2;
			}
			
			this.canvasObj.redraw();
			this.modifyHandler();
		};
		
		/**
		 * 剪切选中元素
		 */
		this.cutEls = function(){
			this.copyEls();
			this.deleteSelectedEl();
			this.validateAndChangeEl();
			this.modifyHandler();
		};
		
		/**
		 * 复制选中元素
		 */
		this.copyEls = function(){
			var steps = [];
			var hops = [];
			var selectedStepIds = this.config.selectedEls.steps;
			for(var i=0;i<selectedStepIds.length;i++){
				var v = selectedStepIds[i];
				if(v && v!=undefined){
					var el = this.getElById(v, "step").el;
					steps.push(el.cloneEl());
				}
			}
			
			var selectedHopIds = this.config.selectedEls.hops;
			for(var i=0;i<selectedHopIds.length;i++){
				var v = selectedHopIds[i];
				if(v && v!=undefined){
					var el = this.getElById(v, "hop").el;
					hops.push(el.cloneEl());
				}
			}
			Plywet.widget.FlowChartClipboard.copyToClipboard({
				steps : steps,
				hops : hops
			});
		};
		
		/**
		 * 粘贴
		 */
		this.pasteEls = function(){
			var el = Plywet.widget.FlowChartClipboard.getFromClipboard();
			var steps = el.steps;
			var hops = [];
			for(var i=0;i<el.hops.length;i++){
				hops.push(el.hops[i].cloneEl());
			}
			for(var i=0;i<steps.length;i++){
				var cid = this.appendEl(steps[i], "step");
				if(cid.oraId != cid.newId && cid.newId != null){
					// 将hops中的所有fromElId和toElId是oraId的换成newId
					for(var j=0;j<hops.length;j++){
						var h = hops[j];
						if(h.fromElId == cid.oraId){
							h.fromElId = cid.newId;
						}
						if(h.toElId == cid.oraId){
							h.toElId = cid.newId;
						}
						hops[j] = h;
					}
				}
			}
			for(var i=0;i<hops.length;i++){
				this.appendEl(hops[i], "hop");
			}
			this.validateAndChangeEl();
		};
		
		/**
		 * 删除元素
		 */
		this.deleteEl = function(elId,type,isValid){
			// 删除step
			if(type == "step"){
				var step = this.getElById(elId, "step");
				if(step != null){
					step.el.destroy();
					this.config.canvasEls.steps = 
						[].concat(this.config.canvasEls.steps.slice(0,step.index),
								this.config.canvasEls.steps.slice(step.index+1));
				}
			}
			// 删除hop
			else if(type == "hop"){
				var hop = this.getElById(elId, "hop");
				if(hop != null){
					hop.el.destroy();
					this.config.canvasEls.hops = 
						[].concat(this.config.canvasEls.hops.slice(0,hop.index),
								this.config.canvasEls.hops.slice(hop.index+1));
				}
			}
			
			if(isValid == undefined || isValid){
				this.validateAndChangeEl();
			}
			this.modifyHandler();
		};
		
		/**
		 * 改变选中Hop的箭头样式
		 */
		this.changeSelectedHopStyle = function(startStyle, endStyle){
			var hops = this.config.selectedEls.hops;
			for(var i=0;i<hops.length;i++){
				var v = hops[i];
				if(v && v!=undefined){
					var hop = this.getElById(v, "hop");
					if(hop != null){
						hop.el.arrowStartType = startStyle;
						hop.el.arrowEndType = endStyle;
					}
				}
			}
			this.modifyHandler();
		};
		
		/**
		 * 删除选中的元素
		 */
		this.deleteSelectedEl = function(){
			// 删除选择的step
			var steps = this.config.selectedEls.steps;
			for(var i=0;i<steps.length;i++){
				var v = steps[i];
				if(v && v!=undefined){
					this.deleteEl(v, "step", false);
				}
			}
			// 删除选择的hop
			var hops = this.config.selectedEls.hops;
			for(var i=0;i<hops.length;i++){
				var v = hops[i];
				if(v && v!=undefined){
					this.deleteEl(v, "hop", false);
				}
			}
			
			this.validateAndChangeEl();
		};
		
		/**
		 * 判断是否有选中元素
		 */
		this.hasSelectedEl = function(type){
			if(type==undefined || type=="step"){
				var steps = this.config.selectedEls.steps;
				for(var i=0;i<steps.length;i++){
					var v = steps[i];
					if(v && v!=undefined){
						return true;
					}
				}
			}
			
			if(type==undefined || type=="hop"){
				var hops = this.config.selectedEls.hops;
				for(var i=0;i<hops.length;i++){
					var v = hops[i];
					if(v && v!=undefined){
						return true;
					}
				}
			}
			return false;
		};
		
		/**
		 * 校验重复ID
		 * @return 新名称
		 */
		this.validateDuplicateId = function(el,i,type){
			var defaultStepName = "defaultStep";
			var defaultHopName = "defaultHop";
			var newId = null;
			if(type == "step"){
				var elId = el.id;
				newId = elId;
				if(!elId || elId=="" || elId==null){
					elId = defaultStepName;
					newId = elId;
					el.id = newId;
					this.config.canvasEls.steps[i] = el;
				}
				
				var index = this.getElById(newId, "step").index;
				var exIndex = 2;
				while(i != index){
					newId = elId+(exIndex++);
					el.id = newId;
					this.config.canvasEls.steps[i] = el;
					index = this.getElById(newId, "step").index;
				}
			}else if(type = "hop"){
				var elId = el.id;
				newId = elId;
				if(!elId || elId=="" || elId==null){
					elId = defaultHopName;
					newId = elId;
					el.id = newId;
					this.config.canvasEls.hops[i] = el;
				}
				
				var index = this.getElById(newId, "hop").index;
				var exIndex = 2;
				while(i != index){
					newId = elId+(exIndex++);
					el.id = newId;
					this.config.canvasEls.hops[i] = el;
					index = this.getElById(newId, "hop").index;
				}
			}
			return newId;
		};
		
		/**
		 * 校验的更改ID
		 * 1.如果发现重复ID，自动在ID后面加2，如果还是重复加3，以此类推
		 * 2.如果发现hop的两端节点有其中之一不存在，删除该连接
		 * 3.同时验证selectedEl中的元素是否存在，不存在的删除
		 */
		this.validateAndChangeEl = function(){
			// 1.校验重复ID
			var steps = this.config.canvasEls.steps;
			for(var i=0;i<steps.length;i++){
				this.validateDuplicateId(steps[i], i, "step");
			}
			
			var hops = this.config.canvasEls.hops;
			for(var i=0;i<hops.length;i++){
				this.validateDuplicateId(hops[i], i, "hop");
			}
			
			// 2.校验hop的两端节点
			hops = this.config.canvasEls.hops;
			var delHopIds = [];
			for(var i=0;i<hops.length;i++){
				var el = hops[i];
				var fromEl = this.getElById(el.fromElId, "step");
				var toEl = this.getElById(el.toElId, "step");
				if(fromEl==null || toEl==null){
					delHopIds.push(el.id);
				}
			}
			
			for(var i=0;i<delHopIds.length;i++){
				this.deleteEl(delHopIds[i], "hop", false);
			}
			
			
			// 3.验证selected，同时清除undefined
			steps = this.config.selectedEls.steps;
			var newSelectedStepEls = [];
			for(var i=0;i<steps.length;i++){
				var v = steps[i];
				if(v && v!=undefined){
					var el = this.getElById(v, "step");
					if(el != null){
						newSelectedStepEls.push(v);
					}
				}
			}
			this.config.selectedEls.steps = newSelectedStepEls;
			
			hops = this.config.selectedEls.hops;
			var newSelectedHopEls = [];
			for(var i=0;i<hops.length;i++){
				var v = hops[i];
				if(v && v!=undefined){
					var el = this.getElById(v, "hop");
					if(el != null){
						newSelectedHopEls.push(v);
					}
				}
			}
			this.config.selectedEls.hops = newSelectedHopEls;
		};
	}
    
    function FlowChart() {
    	/**
    	 * 初始化
    	 */
	    this.init = function(_targetId, _config, _otargetId){
	    	if(!_config){
				_config = {};
			}
			this.clientId = _targetId;
			this.oclientId = _otargetId;
			this._config = _config;
			
			// 图形DIV
			this.canvasDiv = null;
			this.ocanvasDiv = null;
			
			// 图形容器
			this.canvas = null;
			this.ocanvas = null;
			
			// 图形内容，缩略图内容
			this.ctx = null;
			this.octx = null;
			
			this.childCanvas = [];
			
			this.lastClickTime = 0;
			
			this.oconfig = {
				scale : 1,
				mouseConfig : {
					hold : false,
					mouseDown : null,
					mouseUp : null,
					mouseMove : null
				},
				canvasConfig : {
					width : 0,
					height : 0
				},
				wholeRect : {
					x : 0,
					y : 0,
					width : 0,
					height : 0
				},
				editRect : {
					x : 0,
					y : 0,
					width : 0,
					height : 0
				}
			};
			
			this.config = {
				// 是否显示网格
				showGrid : false,
				// 是否贴近网格
				closeGrid : false,
				// 缩略图的Id
				overlayId : "",
				// 网格显示数据，包括间隔
				gridPoints : {
					distance : 50,
					points : []
				},
				// 是否外部控制
				outerControl : false,
				// 外部控制类型，类型有：
				// partMagnify -- 局部放大
				// screenMove -- 屏幕移动
				// magnify -- 放大
				// lessen -- 缩小
				// notMove -- 禁止移动
				outerControlType : "",
				// 内部控制类型：
				// withoutRope -- 不进行圈选
				innerControlType : "",
				// 图形的属性，长宽
				canvasConfig : {
					width : 0,
					height : 0
				},
				// 整体偏移量
				offset : {
					x : 0,
					y : 0
				},
				// 备份的整体偏移量
				tmpOffset : {
					x : 0,
					y : 0
				},
				// 整体缩放比
				scale : 1,
				perScale : 0.1,
				// 鼠标配置
				mouseConfig : {
					hold : false,
					mouseDown : null,
					mouseUp : null,
					mouseMove : null
				}
			};
			
			$.extend(this.config,_config);
		
			// 构造Canvas
			this.constructCanvas();
			// 构造CanvasDiv
			this.constructCanvasDiv();
	    };
    
    	this.constructCanvas = function(){
			this.canvasDiv = jQuery(Plywet.escapeClientId(this.clientId));
			this.canvas = this.canvasDiv.find(Plywet.escapeClientId(this.clientId+"_canvas")).get(0);
			if(this.canvas){
			}else{
				this.canvas = jQuery("<canvas id='"+this.clientId+"_canvas' width='"+this.config.canvasConfig.width+"' height='"+this.config.canvasConfig.height+"'></canvas>");
				this.canvas.appendTo(this.canvasDiv);
				this.canvas = this.canvas.get(0);
			}
			
			if(this.oclientId){
				this.hasOverview = true;
				this.ocanvasDiv = jQuery(Plywet.escapeClientId(this.oclientId));
				this.ocanvas = this.ocanvasDiv.find("canvas").get(0);
				var ocanvasSize = Plywet.getElementDimensions(this.ocanvasDiv);
				this.oconfig.canvasConfig.width=ocanvasSize.css.width;
				this.oconfig.canvasConfig.height=ocanvasSize.css.height;
				if(this.ocanvas){
				}else{
					this.ocanvas = jQuery("<canvas id='"+this.clientId+"_ocanvas' width='"+this.oconfig.canvasConfig.width+"' height='"+this.oconfig.canvasConfig.height+"'></canvas>");
					this.ocanvas.appendTo(this.ocanvasDiv);
					this.ocanvas = this.ocanvas.get(0);
				}
			}else{
				this.hasOverview = false;
			}
			
			
			if (jQuery.browser.msie && jQuery.browser.version < 9){ // excanvas hack
				this.canvas = window.G_vmlCanvasManager.initElement(this.canvasDiv.get(0));
				if(this.hasOverview){
					this.ocanvas = window.G_vmlCanvasManager.initElement(this.ocanvasDiv.get(0));
				}
		    }
			
			this.ctx = this.canvas.getContext('2d');
			if(this.hasOverview)this.octx = this.ocanvas.getContext('2d');
			
			// 初始化
			jQuery(this.canvas).css("margin","0");
			jQuery(this.canvasDiv).css({"position":"reltive","padding":"0"});
			if(this.hasOverview){
				jQuery(this.ocanvas).css("margin","0");
				jQuery(this.ocanvasDiv).css({"position":"reltive","padding":"0"});
			}
			
		};
	
		this.constructCanvasDiv = function(){
			var canvasObj = this;
			
			this.canvasDiv.width(this.config.canvasConfig.width);
			this.canvasDiv.height(this.config.canvasConfig.height);
			
			// 鼠标按下事件
			this.canvasDiv.mousedown(function(e){
				// 确保是外部可改
				if(!canvasObj.config.outerControl){return;}
				// 确保是鼠标左键
				if (e.which != 1) {return;}
				// 取得按下鼠标的坐标
				var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
				canvasObj.config.mouseConfig.hold = true;
				canvasObj.config.mouseConfig.mouseDown = mouseCoords;
				
				canvasObj.deal("mouseDown");
			});
			
			// 鼠标移动事件
			this.canvasDiv.mousemove(function(e){
				// 修改鼠标指针
				canvasObj.changeCursor();
				// 确保鼠标在该区域内按下
				if(!canvasObj.config.mouseConfig.hold && 
						canvasObj.config.outerControlType.indexOf("inner:") != 0){return;};
				// 确保是外部可改
				if(!canvasObj.config.outerControl){return;}
				// 确保是鼠标左键
				if (e.which != 1) {return;}
				
				// 取得当前鼠标的坐标
				var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
				canvasObj.config.mouseConfig.mouseMove = mouseCoords;
				
				canvasObj.deal("mouseMove");
			});
			
			// 鼠标抬起事件
			this.canvasDiv.mouseup(function(e){
				// 确保是外部可改
				if(!canvasObj.config.outerControl){return;}
				
				var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords(e);
				
				// 如果是鼠标右键，调用右键方法
				if (e.which != 1) {
					canvasObj.config.mouseConfig.mouseUp = mouseCoords;
					canvasObj.deal("contextMenu");
					return;
				}
				// 如果距离上次鼠标抬起事件时间很短，调用双击事件方法
				var clickTime = new Date();
				var delayTime = clickTime.getTime()-canvasObj.lastClickTime;
				if ( delayTime < 250) {
					canvasObj.config.mouseConfig.mouseDown = mouseCoords;
					canvasObj.deal("dblclick");
					// 清空鼠标属性
					canvasObj.clearMouseConfig();
					return;
				}
				canvasObj.lastClickTime = clickTime.getTime();
				
				// 确保鼠标在该区域内按下或者是内部方法
				if(!canvasObj.config.mouseConfig.hold){return;};
				
				// 取得按下鼠标的坐标
				canvasObj.config.mouseConfig.hold = false;
				canvasObj.config.mouseConfig.mouseUp = mouseCoords;
				
				canvasObj.deal("mouseUp");
				
				// 清空鼠标属性
				canvasObj.clearMouseConfig();
			});
			
			// 鼠标移出事件
			this.canvasDiv.mouseout(function(e){
				canvasObj.deal("mouseOut");
				// 清空鼠标属性
				canvasObj.clearMouseConfig();
			});
			
			if(this.hasOverview){
				// 鼠标按下事件
				this.ocanvasDiv.mousedown(function(e){
					// 确保是外部可改
					if(!canvasObj.config.outerControl){return;}
					// 确保是鼠标左键
					if (e.which != 1) {return;}
					// 取得按下鼠标的坐标
					var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords2(e);
					
					if((canvasObj.oconfig.editRect.x-3) < mouseCoords.x 
						&& mouseCoords.x < (canvasObj.oconfig.editRect.x+canvasObj.oconfig.editRect.width+3)
						&& (canvasObj.oconfig.editRect.y-3) < mouseCoords.y 
						&& mouseCoords.y < (canvasObj.oconfig.editRect.y+canvasObj.oconfig.editRect.height+3)){
						canvasObj.oconfig.mouseConfig.hold = true;
						canvasObj.oconfig.mouseConfig.mouseDown = mouseCoords;
						// 修改编辑区
						canvasObj.config.tmpOffset.x = canvasObj.config.offset.x;
						canvasObj.config.tmpOffset.y = canvasObj.config.offset.y;
					}
				});
				
				// 鼠标移动事件
				this.ocanvasDiv.mousemove(function(e){
					// 确保是外部可改
					if(!canvasObj.config.outerControl){return;}
					
					// 取得当前鼠标的坐标
					var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords2(e);
					
					if((canvasObj.oconfig.editRect.x-3) < mouseCoords.x 
							&& mouseCoords.x < (canvasObj.oconfig.editRect.x+canvasObj.oconfig.editRect.width+3)
							&& (canvasObj.oconfig.editRect.y-3) < mouseCoords.y 
							&& mouseCoords.y < (canvasObj.oconfig.editRect.y+canvasObj.oconfig.editRect.height+3)){
						canvasObj.ocanvasDiv.css("cursor","move");
					}else{
						canvasObj.ocanvasDiv.css("cursor","auto");
					}
					
					// 确保鼠标在该区域内按下
					if(!canvasObj.oconfig.mouseConfig.hold){return;};
					// 确保是鼠标左键
					if (e.which != 1) {return;}
					
					
					canvasObj.oconfig.mouseConfig.mouseMove = mouseCoords;
					
					// 修改编辑区
					canvasObj.config.offset.x = canvasObj.config.tmpOffset.x 
						+ (canvasObj.oconfig.mouseConfig.mouseDown.x 
						- canvasObj.oconfig.mouseConfig.mouseMove.x)/canvasObj.oconfig.scale;
					canvasObj.config.offset.y = canvasObj.config.tmpOffset.y 
						+ (canvasObj.oconfig.mouseConfig.mouseDown.y 
						- canvasObj.oconfig.mouseConfig.mouseMove.y)/canvasObj.oconfig.scale;
					
					canvasObj.redraw();
				});
				
				// 鼠标抬起事件
				this.ocanvasDiv.mouseup(function(e){
					// 确保是外部可改
					if(!canvasObj.config.outerControl){return;}
					
					var mouseCoords = Plywet.widget.FlowChartUtils.getMouseCoords2(e);
					
					// 如果是鼠标右键，调用右键方法
					if (e.which != 1) {return;}
					
					// 确保鼠标在该区域内按下或者是内部方法
					if(!canvasObj.oconfig.mouseConfig.hold){
						canvasObj.clearOMouseConfig();
						return;
					};
					
					// 取得按下鼠标的坐标
					canvasObj.oconfig.mouseConfig.hold = false;
					canvasObj.oconfig.mouseConfig.mouseUp = mouseCoords;
					
					// 修改编辑区
					canvasObj.config.offset.x = canvasObj.config.tmpOffset.x 
						+ (canvasObj.oconfig.mouseConfig.mouseDown.x 
						- canvasObj.oconfig.mouseConfig.mouseUp.x)/canvasObj.oconfig.scale;
					canvasObj.config.offset.y = canvasObj.config.tmpOffset.y 
						+ (canvasObj.oconfig.mouseConfig.mouseDown.y 
						- canvasObj.oconfig.mouseConfig.mouseUp.y)/canvasObj.oconfig.scale;
					
					// 清空鼠标属性
					canvasObj.clearOMouseConfig();
					
					canvasObj.redraw();
				});
				
				// 鼠标移出事件
				this.ocanvasDiv.mouseout(function(e){
					// 清空鼠标属性
					canvasObj.clearOMouseConfig();
				});
			}
		};
		
		this.clearOMouseConfig = function(){
			this.oconfig.mouseConfig.hold = false;
			this.oconfig.mouseConfig.mouseDown = null;
			this.oconfig.mouseConfig.mouseUp = null;
			this.oconfig.mouseConfig.mouseMove = null;
			
			this.ocanvasDiv.css("cursor","auto");
		};
		
		/**
		 * 清除鼠标属性
		 */
		this.clearMouseConfig = function(){
			this.config.mouseConfig.hold = false;
			this.config.mouseConfig.mouseDown = null;
			this.config.mouseConfig.mouseUp = null;
			this.config.mouseConfig.mouseMove = null;
			
			// 调用子元素的clearMouseConfig
			jQuery.each(this.childCanvas, function(k, v){
				if(v != undefined){
					v.clearMouseConfig();
				}
			});
		};
		
		/**
		 * 修改指针
		 */
		this.changeCursor = function(){
			if(this.config.outerControlType == 'screenMove'){
				this.canvasDiv.css("cursor","move");
			}else if(this.config.outerControlType == 'partMagnify'){
				this.canvasDiv.css("cursor","crosshair");
			}else if(this.config.outerControlType == 'magnify'){
				this.canvasDiv.css("cursor","-moz-zoom-in");
			}else if(this.config.outerControlType == 'lessen'){
				this.canvasDiv.css("cursor","-moz-zoom-out");
			}else{
				this.canvasDiv.css("cursor","auto");
			}
		};
		
		this.modifyHandler = function(){
			// 调用子元素的deal
			jQuery.each(this.childCanvas, function(k, v){
				if(v != undefined){
					v.modifyHandler();
				}
			});
		};
		
		/**
		 * 处理操作
		 * 
		 * @param mouseType
		 *            鼠标类型
		 */
		this.deal = function(mouseType){
			// 执行相应的操作
			// partMagnify -- 局部放大
			// screenMove -- 屏幕移动
			// magnify -- 放大
			// lessen -- 缩小
			// inner: -- 内部方法
			// notMove -- 禁止移动
			if(this.config.outerControlType == 'partMagnify'){
				this.dealPartMagnify(mouseType);
			}else if(this.config.outerControlType == 'screenMove'){
				this.dealScreenMove(mouseType);
			}else if(this.config.outerControlType == 'magnify'){
				this.dealMagnify(mouseType);
			}else if(this.config.outerControlType == 'lessen'){
				this.dealLessen(mouseType);
			}else if(this.config.outerControlType == 'notMove'){
				this.dealNotMove(mouseType);
			}else{
				// 内部方法：
				var methodName;
				if(this.config.outerControlType && this.config.outerControlType!=undefined 
						&& this.config.outerControlType.indexOf("inner:") == 0){
					methodName = this.config.outerControlType.substring(6);
				}
				// 调用子元素的deal
				jQuery.each(this.childCanvas, function(k, v){
					if(v != undefined){
						v.deal(mouseType, methodName);
					}
				});
			}
			this.redraw();
		};
		
		/**
		 * 通过Id获得子Canvas
		 * 
		 * @param id 子Canvas的Id
		 */
		this.getChildCanvasById = function(id){
			var sub;
			
			for(var i=0;i<this.childCanvas.length;i++){
				sub = this.childCanvas[i];
				if(sub.id == id){
					return sub;
				}
			}
			
			return sub;
		};
		
		/**
		 * 通过Id获得子Canvas
		 * 
		 * @param i 子Canvas的序号
		 */
		this.getChildCanvasByIndex = function(i){
			var sub;
			
			if(this.childCanvas && this.childCanvas.length>i){
				return this.childCanvas[i];
			}
			
			return null;
		};
		
		/**
		 * 通过类型获得子Canvas对象组
		 * 
		 * @param type 类型
		 */
		this.getChildCanvasSetByType = function(type){
			var rtn = [];
			var sub;
			for(var i=0;i<this.childCanvas.length;i++){
				sub = this.childCanvas[i];
				if(sub.canvasType == type){
					rtn.push(sub);
				}
			}
			return rtn;
		};
		
		/**
		 * 处理局部放大操作
		 * 
		 * @param mouseType
		 *            鼠标类型
		 */
		this.dealPartMagnify = function(mouseType){
			// 在鼠标抬起事件到达后，处理放大操作
			if(mouseType == "mouseUp"){
				var mouseDown = this.config.mouseConfig.mouseDown;
				var mouseUp = this.config.mouseConfig.mouseUp;
				var ns = Plywet.widget.FlowChartUtils.arcScalePoint(this.config, {
					x : mouseDown.x,
					y : mouseDown.y
				});
				var ne = Plywet.widget.FlowChartUtils.arcScalePoint(this.config, {
					x : mouseUp.x,
					y : mouseUp.y
				});
				
				this.setPartScale({
					x : ns.x,
					y : ns.y,
					x2 : ne.x,
					y2 : ne.y
				});
				
				this.modifyHandler();
			}
		};
		
		/**
		 * 局部放大
		 * 
		 * @param r
		 *            选择的矩形
		 * @return 是否成功设置
		 */
		this.setPartScale = function(r){
			var x = Math.min(r.x,r.x2);
			var y = Math.min(r.y,r.y2);
			var x2 = Math.max(r.x,r.x2);
			var y2 = Math.max(r.y,r.y2);
			
			// 所有元素的整体长宽
			var ropeWidth = x2-x;
			var ropeHeight = y2-y;
			if(ropeWidth<50 || ropeHeight<50){
				return;
			}
			ropeWidth = (ropeWidth<50)?50:ropeWidth;
			ropeHeight = (ropeHeight<50)?50:ropeHeight;
			var ropeWidthScale = this.config.canvasConfig.width/ropeWidth;
			var ropeHeightScale = this.config.canvasConfig.height/ropeHeight;
			var theScale = Math.min(ropeWidthScale,ropeHeightScale);
			
			// 缩放
			var isScale = this.setCanvasScale(theScale);
			// 如果超过最大值返回
			if(!isScale){return false;}
			
			var cx = this.config.canvasConfig.width/2,
			cy = this.config.canvasConfig.height/2;
			
			this.config.offset.x =  cx - (x+x2)/2;
			this.config.offset.y =  cy - (y+y2)/2;
			this.config.offset.x = cx - (cx-this.config.offset.x)*theScale;
			this.config.offset.y = cy - (cy-this.config.offset.y)*theScale;
			
			return true;
		};
		
		/**
		 * 设置缩放率
		 */
		this.setCanvasScale = function(s){
			if(this.config.scale>10 && s>this.config.scale){return false;}
			if(this.config.scale<0.2 && s<this.config.scale){return false;}
			this.config.scale = s;
			return true;
		};
		
		/**
		 * 处理屏幕移动操作
		 * 
		 * @param mouseType
		 *            鼠标类型
		 */
		this.dealScreenMove = function(mouseType){
			if(mouseType == "mouseDown"){
				this.config.tmpOffset.x = this.config.offset.x;
				this.config.tmpOffset.y = this.config.offset.y;
				this.modifyHandler();
			}
			else if(mouseType == "mouseMove"){
				this.config.offset.x = this.config.tmpOffset.x 
					+ this.config.mouseConfig.mouseMove.x 
					- this.config.mouseConfig.mouseDown.x;
				this.config.offset.y = this.config.tmpOffset.y 
					+ this.config.mouseConfig.mouseMove.y 
					- this.config.mouseConfig.mouseDown.y;
			}
			else if(mouseType == "mouseUp"){
				this.config.offset.x = this.config.tmpOffset.x 
					+ this.config.mouseConfig.mouseUp.x 
					- this.config.mouseConfig.mouseDown.x;
				this.config.offset.y = this.config.tmpOffset.y 
					+ this.config.mouseConfig.mouseUp.y 
					- this.config.mouseConfig.mouseDown.y;
			}
		};
		
		/**
		 * 处理禁止移动操作
		 * 
		 * @param mouseType
		 *            鼠标类型
		 */
		this.dealNotMove = function(mouseType){
			if(this.config.mouseConfig.mouseUp == null){
				if(mouseType == "mouseUp"){
					this.config.offset.x = 0;
					this.config.offset.y = 0;
			}
			}else{
				var methodName;
				if(this.config.outerControlType && this.config.outerControlType!=undefined 
						&& this.config.outerControlType.indexOf("inner:") == 0){
					methodName = this.config.outerControlType.substring(6);
				}
				// 调用子元素的deal
				jQuery.each(this.childCanvas, function(k, v){
					if(v != undefined){
						v.deal(mouseType, methodName);
					}
				});
				this.config.innerControlType = "withoutRope";
			}
		};
		
		/**
		 * 处理放大操作
		 * 
		 * @param mouseType
		 *            鼠标类型
		 */
		this.dealMagnify = function(mouseType){
			if(mouseType == "mouseUp"){
				var tscale = this.config.scale*(1+this.config.perScale);
				
				var tx0 = this.config.offset.x - 
					(this.config.mouseConfig.mouseUp.x - this.config.offset.x) * this.config.perScale;
				var ty0 = this.config.offset.y - 
					(this.config.mouseConfig.mouseUp.y - this.config.offset.y) * this.config.perScale;
				
				this.setCanvasScale(tscale);
				this.config.offset.x = tx0;
				this.config.offset.y = ty0;
				
				this.modifyHandler();
			}
		};
		
		/**
		 * 处理缩小操作
		 * 
		 * @param mouseType
		 *            鼠标类型
		 */
		this.dealLessen = function(mouseType){
			if(mouseType == "mouseUp"){
				var tscale = this.config.scale/(1+this.config.perScale);
				
				var tx0 = (this.config.mouseConfig.mouseUp.x * this.config.perScale + this.config.offset.x) / (1+this.config.perScale);
				var ty0 = (this.config.mouseConfig.mouseUp.y * this.config.perScale + this.config.offset.y) / (1+this.config.perScale);
				
				this.setCanvasScale(tscale);
				this.config.offset.x = tx0;
				this.config.offset.y = ty0;
				
				this.modifyHandler();
			}
		},
		
		// 重画对象
		this.redraw = function(){
			this.ctx.clearRect(0,0,this.config.canvasConfig.width,this.config.canvasConfig.height);
			// 重画网格
			this.redrawGrid();
			
			// 调用子元素的redraw
			jQuery.each(this.childCanvas, function(k, v){
				if(v != undefined){
					v.redraw();
				}
			});
			// 重画圈选框
			Plywet.widget.FlowChartUtils.drawRopeRect(this.config,this.ctx);
			
			this.redrawOverview();
		};
		
		this.redrawGrid = function(){
			if(this.config.showGrid){
				var distance = Math.floor(this.config.gridPoints.distance*this.config.scale);
				this.config.gridPoints.points.length=0;
				
				var tx0=this.config.offset.x,ty0=this.config.offset.y;
				
				while(tx0>0)tx0-=distance;
				while(ty0>0)ty0-=distance;
				
				for(var i=tx0;i<=this.config.canvasConfig.width;i=i+distance){
					var c = [];
					for(var j=ty0;j<=this.config.canvasConfig.height;j=j+distance){
						Plywet.widget.FlowChartUtils.drawCross({
							x : i,
							y : j
						},this.ctx);
						c.push({
							x : i,
							y : j
						});
					}
					this.config.gridPoints.points.push(c);
				}
			}
		};
		
		// 重画预览对象
		this.redrawOverview = function(){
			this.octx.clearRect(0,0,this.oconfig.canvasConfig.width,this.oconfig.canvasConfig.height);
			var els = {
				x:0,
				y:0,
				x2:0,
				y2:0
			};
			// 调用子元素的总尺寸
			jQuery.each(this.childCanvas, function(k, v){
				if(v != undefined){
					v.getSize(els);
				}
			});
			// 重画覆盖框
			var oconfig = Plywet.widget.FlowChartUtils.drawRemarkRect(this.oconfig.canvasConfig,this.config.canvasConfig,els,this.octx);
			this.oconfig.scale=oconfig.scale;
			this.oconfig.wholeRect=oconfig.wholeRect;
			this.oconfig.editRect=oconfig.editRect;
			
			// 调用子元素的redraw
			jQuery.each(this.childCanvas, function(k, v){
				if(v != undefined){
					v.redrawOverview();
				}
			});
		};
		
		/**
		 * 设置外部控制
		 * 
		 * @param b
		 *            是否进行控制
		 * @param t
		 *            控制类型
		 */
		this.setOuterControl = function(b,t){
			if(b!=undefined && b!=null){
				this.config.outerControl = b;
			}
			if(t!=undefined && t!=null){
				this.config.outerControlType = t;
			}
		};
		
    };
})( jQuery );

Plywet.widget.FlowChart = function(cfg) {
    this.cfg = cfg;
    this.id = this.cfg.id;
    this.oid = this.cfg.oid;
    this.jqId = Plywet.escapeClientId(this.id);
    this.oraData = this.cfg.data;
    
    this.flowChart = $.flowChart(this.id,this.cfg,this.oid);
    this.flowObject = $.flowObject(this.flowChart,this.oraData);
    this.flowChart.childCanvas.push(this.flowObject);
    this.flowChart.redraw();
    
};

Plywet.extend(Plywet.widget.FlowChart,Plywet.widget.BaseWidget);

/**
 * 改变尺寸
 */
Plywet.widget.FlowChart.prototype.changeSize = function(w, h, ow, oh){
	$(this.flowChart.canvas).attr({"width":w,"height":h});
	this.flowChart.config.canvasConfig.width=w;
	this.flowChart.config.canvasConfig.height=h;
	if (ow) {
		this.flowChart.oconfig.canvasConfig.width=ow;
	}
	if (oh) {
		this.flowChart.oconfig.canvasConfig.height=oh;
	}
	
	console.log(this.flowChart.config);
	
	this.flowChart.redraw();
};

/**
 * 刷新内容
 */
Plywet.widget.FlowChart.prototype.flush = function(exdata){
	 this.oraData = exdata;
	 this.flowObject.init(this.flowChart,exdata);
	 this.flowChart.redraw();
};
