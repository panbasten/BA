(function($){
	/**
	 * 添加跟节点样式
	 */
    function _addRootCss(target){
        var root = $(target);
        root.addClass("ui-tree");
        return root;
    }
    
    function _getOptionArr(target){
        var arr = [];
        _addOption(arr, $(target));
        function _addOption(aa, jqobj){
            jqobj.children("li").each(function(){
                var temp = $(this);
                var jsonObj = $.extend({}, Flywet.parseOptions(this, ["id", "iconCls", "state"]), {
                    checked: (temp.attr("checked") ? true : undefined)
                });
                jsonObj.text = temp.children("span").html();
                if (!jsonObj.text) {
                    jsonObj.text = temp.html();
                }
                var ul = temp.children("ul");
                if (ul.length) {
                    jsonObj.children = [];
                    _addOption(jsonObj.children, ul);
                }
                aa.push(jsonObj);
            });
        };
        return arr;
    }
    
    function _initEvents(target){
        var opts = $.data(target, "tree").options;
        $(target).unbind().bind("mouseover", function(e){
            var tt = $(e.target);
            var node = tt.closest("div.ui-tree-node");
            if (!node.length) {
                return;
            }
            node.addClass("hover");
            if (tt.hasClass("ui-tree-hit")) {
                if (tt.hasClass("ui-tree-expanded")) {
                    tt.addClass("ui-tree-expanded-hover");
                }
                else {
                    tt.addClass("ui-tree-collapsed-hover");
                }
            }
            e.stopPropagation();
        }).bind("mouseout", function(e){
            var tt = $(e.target);
            var node = tt.closest("div.ui-tree-node");
            if (!node.length) {
                return;
            }
            node.removeClass("hover");
            if (tt.hasClass("ui-tree-hit")) {
                if (tt.hasClass("ui-tree-expanded")) {
                    tt.removeClass("ui-tree-expanded-hover");
                }
                else {
                    tt.removeClass("ui-tree-collapsed-hover");
                }
            }
            e.stopPropagation();
        }).bind("click", function(e){
            var tt = $(e.target);
            var node = tt.closest("div.ui-tree-node");
            if (!node.length) {
                return;
            }
            if (tt.hasClass("ui-tree-hit")) {
                _toggle(target, node[0]);
                return false;
            }
            else {
                if (tt.hasClass("ui-tree-checkbox")) {
                    _check(target, node[0], !tt.hasClass("ui-tree-checkbox1"));
                    return false;
                }
                else {
                    _select(target, node[0]);
                    opts.onClick.call(target, _getNode(target, node[0]));
                }
            }
            e.stopPropagation();
        }).bind("dblclick", function(e){
            var node = $(e.target).closest("div.ui-tree-node");
            if (!node.length) {
                return;
            }
            _select(target, node[0]);
            opts.onDblClick.call(target, _getNode(target, node[0]));
            e.stopPropagation();
        }).bind("contextmenu", function(e){
            var node = $(e.target).closest("div.ui-tree-node");
            if (!node.length) {
                return;
            }
            opts.onContextMenu.call(target, e, _getNode(target, node[0]));
            e.stopPropagation();
        });
    }
    
    /**
     * 禁用拖拽
     */
    function _disableDnd(target){
        var node = $(target).find("div.ui-tree-node");
        node.draggable("disable");
        node.css("cursor", "pointer");
    }
    
    /**
     * 启用拖拽
     */
    function _enableDnd(target){
        var options = $.data(target, "tree").options;
        var treeCon = $.data(target, "tree").tree;
        treeCon.find("div.ui-tree-node").draggable({
            disabled: false,
            revert: true,
            cursor: "pointer",
            proxy: function(proxyDom){
                var p = $("<div class=\"ui-tree-node-proxy ui-tree-dnd-no\"></div>").appendTo("body");
                p.html($(proxyDom).find(".ui-tree-title").html());
                p.hide();
                return p;
            },
            deltaX: 15,
            deltaY: 15,
            onBeforeDrag: function(e){
                if ($(e.target).hasClass("ui-tree-hit") || $(e.target).hasClass("ui-tree-checkbox")) {
                    return false;
                }
                if (e.which != 1) {
                    return false;
                }
                
                // 设置节点不能被拖放
                if(options.onBeforeDrag){
                	options.onBeforeDrag.call(target,target,this);
                }else{
	                $(this).next("ul").find("div.ui-tree-node").droppable({
	                    accept: "no-accept"
	                });
                }
                
                var indent = $(this).find("span.ui-tree-indent");
                if (indent.length) {
                    e.data.startLeft += indent.length * indent.width();
                }
            },
            onStartDrag: function(){
                $(this).draggable("proxy").css({
                    left: -10000,
                    top: -10000
                });
            },
            onDrag: function(e){
                var x1 = e.pageX, y1 = e.pageY, x2 = e.data.startX, y2 = e.data.startY;
                var d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                if (d > 3) {
                    $(this).draggable("proxy").show();
                }
                this.pageY = e.pageY;
            },
            onStopDrag: function(){
            	// 恢复节点接受拖放属性
            	if(options.onStopDrag){
                	options.onStopDrag.call(target,target,this);
                }else{
	                $(this).next("ul").find("div.ui-tree-node").droppable({
	                    accept: "div.ui-tree-node"
	                });
                }
            }
        }).droppable({
            accept: "div.ui-tree-node",
            onDragOver: function(e, node){
	        	if(options.onDragOver){
	            	options.onDragOver.call(target,target,node,this);
	            }else{
	                var pageY = node.pageY;
	                var top = $(this).offset().top;
	                var top2 = top + $(this).outerHeight();
	                $(node).draggable("proxy").removeClass("ui-tree-dnd-no").addClass("ui-tree-dnd-yes");
	                $(this).removeClass("ui-tree-node-append ui-tree-node-top ui-tree-node-bottom");
	                if (pageY > top + (top2 - top) / 2) {
	                    if (top2 - pageY < 5) {
	                        $(this).addClass("ui-tree-node-bottom");
	                    }
	                    else {
	                        $(this).addClass("ui-tree-node-append");
	                    }
	                }
	                else {
	                    if (pageY - top < 5) {
	                        $(this).addClass("ui-tree-node-top");
	                    }
	                    else {
	                        $(this).addClass("ui-tree-node-append");
	                    }
	                }
	            }
            },
            onDragLeave: function(target, node){
                $(node).draggable("proxy").removeClass("ui-tree-dnd-yes").addClass("ui-tree-dnd-no");
                $(this).removeClass("ui-tree-node-append ui-tree-node-top ui-tree-node-bottom");
            },
            onDrop: function(target, node){
                var _self = this;
                var method, position;
                if ($(this).hasClass("ui-tree-node-append")) {
                	method = append;
                }
                else {
                	method = insert;
                    position = $(this).hasClass("ui-tree-node-top") ? "top" : "bottom";
                }
                method(node, _self, position);
                $(this).removeClass("ui-tree-node-append ui-tree-node-top ui-tree-node-bottom");
            }
        });
        
        if(options.initDnd){
        	options.initDnd.call(target,target);
        }
        
        function append(sNode, tNode){
            if (_getNode(target, tNode).state == "closed") {
                _expand(target, tNode, function(){
                    doAppend();
                });
            }
            else {
                doAppend();
            }
            function doAppend(){
                var sData = $(target).tree("pop", sNode);
                $(target).tree("append", {
                    parent: tNode,
                    data: [sData]
                });
                options.onDrop.call(target, tNode, sData, "append");
            };
        }
        
        function insert(sNode, tNode, position){
            var opts = {};
            if (position == "top") {
            	opts.before = tNode;
            }
            else {
            	opts.after = tNode;
            }
            var sData = $(target).tree("pop", sNode);
            opts.data = sData;
            $(target).tree("insert", opts);
            options.onDrop.call(target, tNode, sData, position);
        }
    }
    
    function _check(top, target, toChecked){
        var options = $.data(top, "tree").options;
        if (!options.checkbox) {
            return;
        }
        var targetNode = _getNode(top, target);
        if (options.onBeforeCheck.call(top, targetNode, toChecked) == false) {
            return;
        }
        var jqTarget = $(target);
        var ck = jqTarget.find(".ui-tree-checkbox");
        ck.removeClass("ui-tree-checkbox0 ui-tree-checkbox1 ui-tree-checkbox2");
        if (toChecked) {
            ck.addClass("ui-tree-checkbox1");
        }
        else {
            ck.addClass("ui-tree-checkbox0");
        }
        if (options.cascadeCheck) {
            _checkParent(jqTarget);
            _checkChildren(jqTarget);
        }
        options.onCheck.call(top, targetNode, toChecked);
        
        function _checkChildren(target){
            var children = target.next().find(".ui-tree-checkbox");
            children.removeClass("ui-tree-checkbox0 ui-tree-checkbox1 ui-tree-checkbox2");
            if (target.find(".ui-tree-checkbox").hasClass("ui-tree-checkbox1")) {
                children.addClass("ui-tree-checkbox1");
            }
            else {
                children.addClass("ui-tree-checkbox0");
            }
        }
        
        function _checkParent(target){
            var parent = _getParent(top, target[0]);
            if (parent) {
                var ck = $(parent.target).find(".ui-tree-checkbox");
                ck.removeClass("ui-tree-checkbox0 ui-tree-checkbox1 ui-tree-checkbox2");
                if (_isChecked(target)) {
                    ck.addClass("ui-tree-checkbox1");
                }
                else {
                    if (_isUnchecked(target)) {
                        ck.addClass("ui-tree-checkbox0");
                    }
                    else {
                        ck.addClass("ui-tree-checkbox2");
                    }
                }
                _checkParent($(parent.target));
            }
            
            function _isChecked(target){
                var checkbox = target.find(".ui-tree-checkbox");
                if (checkbox.hasClass("ui-tree-checkbox0") || checkbox.hasClass("ui-tree-checkbox2")) {
                    return false;
                }
                var b = true;
                target.parent().siblings().each(function(){
                    if (!$(this).children("div.ui-tree-node").children(".ui-tree-checkbox").hasClass("ui-tree-checkbox1")) {
                        b = false;
                    }
                });
                return b;
            }
            
            function _isUnchecked(n){
                var ck = n.find(".ui-tree-checkbox");
                if (ck.hasClass("ui-tree-checkbox1") || ck.hasClass("ui-tree-checkbox2")) {
                    return false;
                }
                var b = true;
                n.parent().siblings().each(function(){
                    if (!$(this).children("div.ui-tree-node").children(".ui-tree-checkbox").hasClass("ui-tree-checkbox0")) {
                        b = false;
                    }
                });
                return b;
            }
            
    	}
    }
    
    function _dealCheckbox(target, node){
        var opts = $.data(target, "tree").options;
        var $node = $(node);
        if (_isLeaf(target, node)) {
            var ck = $node.find(".ui-tree-checkbox");
            if (ck.length) {
                if (ck.hasClass("ui-tree-checkbox1")) {
                    _check(target, node, true);
                }
                else {
                    _check(target, node, false);
                }
            }
            else {
                if (opts.onlyLeafCheck) {
                    $("<span class=\"ui-tree-checkbox ui-tree-checkbox0\"></span>").insertBefore($node.find(".ui-tree-title"));
                }
            }
        }
        else {
            var ck = $node.find(".ui-tree-checkbox");
            if (opts.onlyLeafCheck) {
                ck.remove();
            }
            else {
                if (ck.hasClass("ui-tree-checkbox1")) {
                    _check(target, node, true);
                }
                else {
                    if (ck.hasClass("ui-tree-checkbox2")) {
                        var _46 = true;
                        var _47 = true;
                        var subNodes = _getChildren(target, node);
                        for (var i = 0; i < subNodes.length; i++) {
                            if (subNodes[i].checked) {
                                _47 = false;
                            }
                            else {
                                _46 = false;
                            }
                        }
                        if (_46) {
                            _check(target, node, true);
                        }
                        if (_47) {
                            _check(target, node, false);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 加载数据
     * @param target
     * @param ul
     * @param data
     * @param append 是否添加
     */
    function _loadData(target, ul, data, append){
        var opts = $.data(target, "tree").options;
        data = opts.loadFilter.call(target, data, $(ul).prev("div.ui-tree-node")[0]);
        if (!append) {
            $(ul).empty();
        }
        var nodeDoms = [];
        var len = $(ul).prev("div.ui-tree-node").find("span.ui-tree-indent, span.ui-tree-hit").length;
        _loadNodes(ul, data, len);
        if (opts.dnd) {
            _enableDnd(target);
        }
        else {
            _disableDnd(target);
        }
        for (var i = 0; i < nodeDoms.length; i++) {
            _check(target, nodeDoms[i], true);
        }
        setTimeout(function(){
            _setNodes(target, target);
        }, 0);
        var node = null;
        if (target != ul) {
            var prevUl = $(ul).prev();
            node = _getNode(target, prevUl[0]);
        }
        opts.onLoadSuccess.call(target, node, data);
        
        function _loadNodes(_ul, nodeDatas, len){
            for (var i = 0; i < nodeDatas.length; i++) {
                var li = $("<li></li>").appendTo(_ul);
                var nodeData = nodeDatas[i];
                if (nodeData.state != "open" && nodeData.state != "closed") {
                	nodeData.state = "open";
                }
                var nodeDom = $("<div class=\"ui-tree-node\"></div>").appendTo(li);
                nodeDom.attr("node-id", nodeData.id);
                $.data(nodeDom[0], "ui-tree-node", {
                    id: nodeData.id,
                    text: nodeData.text,
                    iconCls: nodeData.iconCls,
                    attributes: nodeData.attributes
                });
                $("<span class=\"ui-tree-title\"></span>").html(nodeData.text).appendTo(nodeDom);
                if (opts.checkbox) {
                    if (opts.onlyLeafCheck) {
                        if (nodeData.state == "open" && (!nodeData.children || !nodeData.children.length)) {
                            if (nodeData.checked) {
                                $("<span class=\"ui-tree-checkbox ui-tree-checkbox1\"></span>").prependTo(nodeDom);
                            }
                            else {
                                $("<span class=\"ui-tree-checkbox ui-tree-checkbox0\"></span>").prependTo(nodeDom);
                            }
                        }
                    }
                    else {
                        if (nodeData.checked) {
                            $("<span class=\"ui-tree-checkbox ui-tree-checkbox1\"></span>").prependTo(nodeDom);
                            nodeDoms.push(nodeDom[0]);
                        }
                        else {
                            $("<span class=\"ui-tree-checkbox ui-tree-checkbox0\"></span>").prependTo(nodeDom);
                        }
                    }
                }
                var nodeIcon;
                if (nodeData.children && nodeData.children.length) {
                    var _sub_ul = $("<ul></ul>").appendTo(li);
                    if (nodeData.state == "open") {
                        nodeIcon = $("<span class=\"ui-tree-icon ui-icon ui-tree-folder ui-tree-folder-open\"></span>").addClass(nodeData.iconCls);
                        nodeIcon.prependTo(nodeDom);
                        $("<span class=\"ui-tree-hit ui-icon ui-tree-expanded\"></span>").prependTo(nodeDom);
                    }
                    else {
                    	nodeIcon = $("<span class=\"ui-tree-icon ui-icon ui-tree-folder\"></span>").addClass(nodeData.iconCls);
                    	nodeIcon.prependTo(nodeDom);
                        $("<span class=\"ui-tree-hit ui-icon ui-tree-collapsed\"></span>").prependTo(nodeDom);
                        _sub_ul.css("display", "none");
                    }
                    _loadNodes(_sub_ul, nodeData.children, len + 1);
                }
                else {
                    if (nodeData.state == "closed") {
                    	nodeIcon = $("<span class=\"ui-tree-icon ui-icon ui-tree-folder\"></span>").addClass(nodeData.iconCls);
                    	nodeIcon.prependTo(nodeDom);
                        $("<span class=\"ui-tree-hit ui-icon ui-tree-collapsed\"></span>").prependTo(nodeDom);
                    }
                    else {
                    	nodeIcon = $("<span class=\"ui-tree-icon ui-icon ui-tree-file\"></span>").addClass(nodeData.iconCls);
                    	nodeIcon.prependTo(nodeDom);
                        $("<span class=\"ui-tree-indent\"></span>").prependTo(nodeDom);
                    }
                }
                
                var showIcon = (nodeData.showIcon == undefined) ? ((opts.showIcon == undefined) ? true : opts.showIcon) : nodeData.showIcon;
                if(!showIcon && nodeIcon){
                	nodeIcon.hide();
                }
                
                for (var j = 0; j < len; j++) {
                    $("<span class=\"ui-tree-indent\"></span>").prependTo(nodeDom);
                }
            }
        }
    }
    
    /**
     * 设置节点的缩进选择框等样式
     */
    function _setNodes(target, ul, isRoot){
        var opts = $.data(target, "tree").options;
        if (!opts.lines) {
            return;
        }
        if (!isRoot) {
        	isRoot = true;
            $(target).find("span.ui-tree-indent").removeClass("ui-tree-line ui-tree-join ui-tree-joinbottom");
            $(target).find("div.ui-tree-node").removeClass("ui-tree-node-last ui-tree-root-first ui-tree-root-one");
            var roots = $(target).tree("getRoots");
            if (roots.length > 1) {
                $(roots[0].target).addClass("ui-tree-root-first");
            }
            else {
                $(roots[0].target).addClass("ui-tree-root-one");
            }
        }
        $(ul).children("li").each(function(){
            var node = $(this).children("div.ui-tree-node");
            var ul = node.next("ul");
            if (ul.length) {
                if ($(this).next().length) {
                	treeLine(node);
                }
                _setNodes(target, ul, isRoot);
            }
            else {
            	treeJoin(node);
            }
        });
        var lastNode = $(ul).children("li:last").children("div.ui-tree-node").addClass("ui-tree-node-last");
        lastNode.children("span.ui-tree-join").removeClass("ui-tree-join").addClass("ui-tree-joinbottom");
        
        function treeJoin(node){
            var treeIcon = node.find("span.ui-tree-icon");
            treeIcon.prev("span.ui-tree-indent").addClass("ui-tree-join");
        }
        
        function treeLine(node){
            var len = node.find("span.ui-tree-indent, span.ui-tree-hit").length;
            node.next().find("div.ui-tree-node").each(function(){
                $(this).children("span:eq(" + (len - 1) + ")").addClass("ui-tree-line");
            });
        }
    }
    
    function _67(target, ul, _69, _6a){
        var opts = $.data(target, "tree").options;
        _69 = _69 || {};
        var prevNode = null;
        if (target != ul) {
            var prevUl = $(ul).prev();
            prevNode = _getNode(target, prevUl[0]);
        }
        if (opts.onBeforeLoad.call(target, prevNode, _69) == false) {
            return;
        }
        var treeFolder = $(ul).prev().children("span.ui-tree-folder");
        treeFolder.addClass("ui-tree-loading");
        var _6f = opts.loader.call(target, _69, function(data){
        	treeFolder.removeClass("ui-tree-loading");
            _loadData(target, ul, data);
            if (_6a) {
                _6a();
            }
        }, function(){
        	treeFolder.removeClass("ui-tree-loading");
            opts.onLoadError.apply(target, arguments);
            if (_6a) {
                _6a();
            }
        });
        if (_6f == false) {
            treeFolder.removeClass("ui-tree-loading");
        }
    }
    
    /**
     * 展开
     */
    function _expand(target, node, callback){
        var options = $.data(target, "tree").options;
        var hit = $(node).children("span.ui-tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("ui-tree-expanded")) {
            return;
        }
        var nodeData = _getNode(target, node);
        if (options.onBeforeExpand.call(target, nodeData) == false) {
            return;
        }
        hit.removeClass("ui-tree-collapsed ui-tree-collapsed-hover").addClass("ui-tree-expanded");
        hit.next().addClass("ui-tree-folder-open");
        var ul = $(node).next();
        if (ul.length) {
            if (options.animate) {
                ul.slideDown("normal", function(){
                    options.onExpand.call(target, nodeData);
                    if (callback) {
                        callback();
                    }
                });
            }
            else {
                ul.css("display", "block");
                options.onExpand.call(target, nodeData);
                if (callback) {
                    callback();
                }
            }
        }
        else {
            var ul = $("<ul style=\"display:none\"></ul>").insertAfter(node);
            _67(target, ul[0], {
                id: nodeData.id
            }, function(){
                if (ul.is(":empty")) {
                    ul.remove();
                }
                if (options.animate) {
                    ul.slideDown("normal", function(){
                        options.onExpand.call(target, nodeData);
                        if (callback) {
                            callback();
                        }
                    });
                }
                else {
                    ul.css("display", "block");
                    options.onExpand.call(target, nodeData);
                    if (callback) {
                        callback();
                    }
                }
            });
        }
    }
    
    /**
     * 闭合
     */
    function _collapse(target, node){
        var options = $.data(target, "tree").options;
        var hit = $(node).children("span.ui-tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("ui-tree-collapsed")) {
            return;
        }
        var nodeData = _getNode(target, node);
        if (options.onBeforeCollapse.call(target, nodeData) == false) {
            return;
        }
        hit.removeClass("ui-tree-expanded ui-tree-expanded-hover").addClass("ui-tree-collapsed");
        hit.next().removeClass("ui-tree-folder-open");
        var ul = $(node).next();
        if (options.animate) {
            ul.slideUp("normal", function(){
                options.onCollapse.call(target, nodeData);
            });
        }
        else {
            ul.css("display", "none");
            options.onCollapse.call(target, nodeData);
        }
    }
    
    function _toggle(target, node){
        var hit = $(node).children("span.ui-tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("ui-tree-expanded")) {
            _collapse(target, node);
        }
        else {
            _expand(target, node);
        }
    }
    
    function _expandAll(target, node){
        var arr = _getChildren(target, node);
        if (node) {
            arr.unshift(_getNode(target, node));
        }
        for (var i = 0; i < arr.length; i++) {
            _expand(target, arr[i].target);
        }
    }
    
    function _expandTo(target, node){
        var arr = [];
        var p = _getParent(target, node);
        while (p) {
            arr.unshift(p);
            p = _getParent(target, p.target);
        }
        for (var i = 0; i < arr.length; i++) {
            _expand(target, arr[i].target);
        }
    }
    
    function _collapseAll(target, node){
        var arr = _getChildren(target, node);
        if (node) {
            arr.unshift(_getNode(target, node));
        }
        for (var i = 0; i < arr.length; i++) {
            _collapse(target, arr[i].target);
        }
    }
    
    function _getRoot(target){
        var arrs = _getRoots(target);
        if (arrs.length) {
            return arrs[0];
        }
        else {
            return null;
        }
    }
    
    function _getRoots(target){
        var arr = [];
        $(target).children("li").each(function(){
            var children = $(this).children("div.ui-tree-node");
            arr.push(_getNode(target, children[0]));
        });
        return arr;
    }
    
    function _getChildren(target, node){
        var arr = [];
        if (node) {
            _children($(node));
        }
        else {
            var roots = _getRoots(target);
            for (var i = 0; i < roots.length; i++) {
                arr.push(roots[i]);
                _children($(roots[i].target));
            }
        }
        
        function _children(parentNode){
            parentNode.next().find("div.ui-tree-node").each(function(){
                arr.push(_getNode(target, this));
            });
        }
        
        return arr;
    }
    
    function _getParent(target, node){
        var ul = $(node).parent().parent();
        if (ul[0] == target) {
            return null;
        }
        else {
            return _getNode(target, ul.prev()[0]);
        }
    }
    
    function _getChecked(target, type){
        type = type || "checked";
        var selector = "";
        if (type == "checked") {
            selector = "span.ui-tree-checkbox1";
        }
        else {
            if (type == "unchecked") {
                selector = "span.ui-tree-checkbox0";
            }
            else {
                if (type == "indeterminate") {
                    selector = "span.ui-tree-checkbox2";
                }
            }
        }
        var result = [];
        $(target).find(selector).each(function(){
            var node = $(this).parent();
            result.push(_getNode(target, node[0]));
        });
        return result;
    }
    
    function _getSelected(target){
        var nodes = $(target).find("div.active");
        if (nodes.length) {
            return _getNode(target, nodes[0]);
        }
        else {
            return null;
        }
    }
    
    function _append(target, cfg){
        var parent = $(cfg.parent);
        var ul;
        if (parent.length == 0) {
            ul = $(target);
        }
        else {
            ul = parent.next();
            if (ul.length == 0) {
                ul = $("<ul></ul>").insertAfter(parent);
            }
        }
        if (cfg.data && cfg.data.length) {
            var span = parent.find("span.ui-tree-icon");
            if (span.hasClass("ui-tree-file")) {
                span.removeClass("ui-tree-file").addClass("ui-tree-folder");
                var hit = $("<span class=\"ui-tree-hit ui-icon ui-tree-expanded\"></span>").insertBefore(span);
                if (hit.prev().length) {
                    hit.prev().remove();
                }
            }
        }
        _loadData(target, ul[0], cfg.data, true);
        _dealCheckbox(target, ul.prev());
    }
    
    function _insert(target, cfg){
        var ref = cfg.before || cfg.after;
        var parent = _getParent(target, ref);
        var li;
        if (parent) {
            _append(target, {
                parent: parent.target,
                data: [cfg.data]
            });
            li = $(parent.target).next().children("li:last");
        }
        else {
            _append(target, {
                parent: null,
                data: [cfg.data]
            });
            li = $(target).children("li:last");
        }
        if (cfg.before) {
            li.insertBefore($(ref).parent());
        }
        else {
            li.insertAfter($(ref).parent());
        }
    }
    
    function _remove(target, node){
        var parent = _getParent(target, node);
        var jqtarget = $(node);
        var li = jqtarget.parent();
        var ul = li.parent();
        li.remove();
        if (ul.children("li").length == 0) {
            var jqtarget = ul.prev();
            jqtarget.find(".ui-tree-icon").removeClass("ui-tree-folder").addClass("ui-tree-file");
            jqtarget.find(".ui-tree-hit").remove();
            $("<span class=\"ui-tree-indent\"></span>").prependTo(jqtarget);
            if (ul[0] != target) {
                ul.remove();
            }
        }
        if (parent) {
            _dealCheckbox(target, parent.target);
        }
        _setNodes(target, target);
    }
    
    function _getData(target, node){
        function getSubData(sub, ul){
            ul.children("li").each(function(){
                var n = $(this).children("div.ui-tree-node");
                var node = _getNode(target, n[0]);
                var sub = $(this).children("ul");
                if (sub.length) {
                	node.children = [];
                	getSubData(node.children, sub);
                }
                sub.push(node);
            });
        }
        
        if (node) {
            var nodeData = _getNode(target, node);
            nodeData.children = [];
            getSubData(nodeData.children, $(node).next());
            return nodeData;
        }
        else {
            return null;
        }
    }
    
    function _update(target, cfg){
        var jqtarget = $(cfg.target);
        var targetNode = _getNode(target, cfg.target);
        if (targetNode.iconCls) {
            jqtarget.find(".ui-tree-icon").removeClass(targetNode.iconCls);
        }
        var new_cfg = $.extend({}, targetNode, cfg);
        $.data(cfg.target, "ui-tree-node", new_cfg);
        jqtarget.attr("node-id", new_cfg.id);
        jqtarget.find(".ui-tree-title").html(new_cfg.text);
        if (new_cfg.iconCls) {
            jqtarget.find(".ui-tree-icon").addClass(new_cfg.iconCls);
        }
        if (targetNode.checked != new_cfg.checked) {
            _check(target, cfg.target, new_cfg.checked);
        }
    }
    
    function _getNode(target, node){
        var nodeData = $.extend({}, $.data(node, "ui-tree-node"), {
            target: node,
            checked: $(node).find(".ui-tree-checkbox").hasClass("ui-tree-checkbox1")
        });
        if (!_isLeaf(target, node)) {
        	nodeData.state = $(node).find(".ui-tree-hit").hasClass("ui-tree-expanded") ? "open" : "closed";
        }
        return nodeData;
    }
    
    function _find(target, id){
        var results = $(target).find("div.ui-tree-node[node-id=" + id + "]");
        if (results.length) {
            return _getNode(target, results[0]);
        }
        else {
            return null;
        }
    }
    
    function _select(target, node){
        var options = $.data(target, "tree").options;
        var nodeData = _getNode(target, node);
        if (options.onBeforeSelect.call(target, nodeData) == false) {
            return;
        }
        $("div.active", target).removeClass("active");
        $(node).addClass("active");
        options.onSelect.call(target, nodeData);
    }
    
    function _isLeaf(target, node){
        var jqTarget = $(node);
        var hit = jqTarget.children("span.ui-tree-hit");
        return hit.length == 0;
    }
    
    function _beginEdit(target, node){
        var options = $.data(target, "tree").options;
        var nodeData = _getNode(target, node);
        if (options.onBeforeEdit.call(target, nodeData) == false) {
            return;
        }
        $(node).css("position", "relative");
        var nt = $(node).find(".ui-tree-title");
        var nwidth = nt.outerWidth();
        nt.empty();
        var input = $("<input class=\"ui-tree-editor\">").appendTo(nt);
        input.val(nodeData.text).focus();
        input.width(nwidth + 20);
        input.height(document.compatMode == "CSS1Compat" ? (18 - (input.outerHeight() - input.height())) : 18);
        input.bind("click", function(e){
            return false;
        }).bind("mousedown", function(e){
            e.stopPropagation();
        }).bind("mousemove", function(e){
            e.stopPropagation();
        }).bind("keydown", function(e){
            if (e.keyCode == 13) {
                _updataNode(target, node);
                return false;
            }
            else {
                if (e.keyCode == 27) {
                    _cancelEdit(target, node);
                    return false;
                }
            }
        }).bind("blur", function(e){
            e.stopPropagation();
            _updataNode(target, node);
        });
    }
    
    function _updataNode(target, node){
        var options = $.data(target, "tree").options;
        $(node).css("position", "");
        var input = $(node).find("input.ui-tree-editor");
        var val = input.val();
        input.remove();
        var targetNode = _getNode(target, node);
        targetNode.text = val;
        _update(target, targetNode);
        options.onAfterEdit.call(target, targetNode);
    }
    
    function _cancelEdit(target, node){
        var options = $.data(target, "tree").options;
        $(node).css("position", "");
        $(node).find("input.ui-tree-editor").remove();
        var nodeData = _getNode(target, node);
        _update(target, nodeData);
        options.onCancelEdit.call(target, nodeData);
    }
    
    $.fn.tree = function(options, param){
        if (typeof options == "string") {
            return $.fn.tree.methods[options](this, param);
        }
        options = options || {};
        return this.each(function(){
            var treedata = $.data(this, "tree");
            var opts;
            if (treedata) {
            	opts = $.extend(treedata.options, options);
                treedata.options = opts;
            }
            else {
            	opts = $.extend({}, $.fn.tree.defaults, $.fn.tree.parseOptions(this), options);
                $.data(this, "tree", {
                    options: opts,
                    tree: _addRootCss(this)
                });
                var optionArr = _getOptionArr(this);
                if (optionArr.length && !opts.data) {
                	opts.data = optionArr;
                }
            }
            _initEvents(this);
            if (opts.lines) {
                $(this).addClass("ui-tree-lines");
            }
            if (opts.data) {
                _loadData(this, this, opts.data);
            }
            else {
                if (opts.dnd) {
                    _enableDnd(this);
                }
                else {
                    _disableDnd(this);
                }
            }
            _67(this, this);
        });
    };
    
    $.fn.tree.methods = {
        options: function(jq){
            return $.data(jq[0], "tree").options;
        },
        loadData: function(jq, data){
            return jq.each(function(){
                _loadData(this, this, data);
            });
        },
        getNode: function(jq, node){
            return _getNode(jq[0], node);
        },
        getData: function(jq, node){
            return _getData(jq[0], node);
        },
        reload: function(jq, _e8){
            return jq.each(function(){
                if (_e8) {
                    var _e9 = $(_e8);
                    var hit = _e9.children("span.ui-tree-hit");
                    hit.removeClass("ui-tree-expanded ui-tree-expanded-hover").addClass("ui-tree-collapsed");
                    _e9.next().remove();
                    _expand(this, _e8);
                }
                else {
                    $(this).empty();
                    _67(this, this);
                }
            });
        },
        getRoot: function(jq){
            return _getRoot(jq[0]);
        },
        getRoots: function(jq){
            return _getRoots(jq[0]);
        },
        getParent: function(jq, node){
            return _getParent(jq[0], node);
        },
        getChildren: function(jq, node){
            return _getChildren(jq[0], node);
        },
        getChecked: function(jq, type){
            return _getChecked(jq[0], type);
        },
        getSelected: function(jq){
            return _getSelected(jq[0]);
        },
        isLeaf: function(jq, node){
            return _isLeaf(jq[0], node);
        },
        find: function(jq, id){
            return _find(jq[0], id);
        },
        select: function(jq, node){
            return jq.each(function(){
                _select(this, node);
            });
        },
        check: function(jq, node){
            return jq.each(function(){
                _check(this, node, true);
            });
        },
        uncheck: function(jq, node){
            return jq.each(function(){
                _check(this, node, false);
            });
        },
        collapse: function(jq, node){
            return jq.each(function(){
                _collapse(this, node);
            });
        },
        expand: function(jq, node){
            return jq.each(function(){
                _expand(this, node);
            });
        },
        collapseAll: function(jq, node){
            return jq.each(function(){
                _collapseAll(this, node);
            });
        },
        expandAll: function(jq, node){
            return jq.each(function(){
                _expandAll(this, node);
            });
        },
        expandTo: function(jq, node){
            return jq.each(function(){
                _expandTo(this, node);
            });
        },
        toggle: function(jq, node){
            return jq.each(function(){
                _toggle(this, node);
            });
        },
        append: function(jq, node){
            return jq.each(function(){
                _append(this, node);
            });
        },
        insert: function(jq, node){
            return jq.each(function(){
                _insert(this, node);
            });
        },
        remove: function(jq, node){
            return jq.each(function(){
                _remove(this, node);
            });
        },
        pop: function(jq, target){
            var _pop = jq.tree("getData", target);
            jq.tree("remove", target);
            return _pop;
        },
        update: function(jq, data){
            return jq.each(function(){
                _update(this, data);
            });
        },
        enableDnd: function(jq){
            return jq.each(function(){
                _enableDnd(this);
            });
        },
        disableDnd: function(jq){
            return jq.each(function(){
                _disableDnd(this);
            });
        },
        beginEdit: function(jq, node){
            return jq.each(function(){
                _beginEdit(this, node);
            });
        },
        endEdit: function(jq, node){
            return jq.each(function(){
                _updataNode(this, node);
            });
        },
        cancelEdit: function(jq, node){
            return jq.each(function(){
                _cancelEdit(this, node);
            });
        }
    };
    $.fn.tree.parseOptions = function(target){
        var t = $(target);
        return $.extend({}, Flywet.parseOptions(target, ["url", "method", {
            checkbox: "boolean",
            cascadeCheck: "boolean",
            onlyLeafCheck: "boolean"
        }, {
            animate: "boolean",
            lines: "boolean",
            dnd: "boolean"
        }]));
    };
    $.fn.tree.defaults = {
        url: null,
        method: "post",
        animate: false,
        checkbox: false,
        cascadeCheck: true,
        onlyLeafCheck: false,
        lines: false,
        dnd: false,
        showIcon: true,
        data: null,
        loader: function(data, onsuccess, onerror){
            var opts = $(this).tree("options");
            if (!opts.url) {
                return false;
            }
            $.ajax({
                type: opts.method,
                url: opts.url,
                data: data,
                dataType: "json",
                success: function(data){
            		onsuccess(data);
                },
                error: function(){
                    onerror.apply(this, arguments);
                }
            });
        },
        loadFilter: function(target, node){
            return target;
        },
        onBeforeLoad: function(target, node){
        },
        onLoadSuccess: function(target, node){
        },
        onLoadError: function(){
        },
        onClick: function(target){
        },
        onDblClick: function(target){
        },
        onBeforeExpand: function(target){
        },
        onExpand: function(target){
        },
        onBeforeCollapse: function(target){
        },
        onCollapse: function(target){
        },
        onBeforeCheck: function(target, node){
        },
        onCheck: function(target, node){
        },
        onBeforeSelect: function(target){
        },
        onSelect: function(target){
        },
        onContextMenu: function(target, node){
        },
        onDrop: function(target, node, position){
        },
        onBeforeDrag: function(target, node){
        },
        onStopDrag: function(target, node){
        },
        onDragOver: function(target, snode, tnode){
        },
        initDnd: function(target){
        },
        onBeforeEdit: function(target){
        },
        onAfterEdit: function(target){
        },
        onCancelEdit: function(target){
        }
    };
})(jQuery);

Flywet.widget.EasyTree=function(cfg){
	this.cfg=cfg;
	this.id = cfg.id;
    this.jqId = Flywet.escapeClientId(this.id);
    this.jq = $(this.jqId);
	this.jq.tree(cfg);
	this.loadData(cfg.data);
};

Flywet.extend(Flywet.widget.EasyTree, Flywet.widget.BaseWidget);

Flywet.widget.EasyTree.prototype.loadData=function(data){
	this.treedata=data||[];
	this.jq.tree("loadData",this.treedata);
};

Flywet.widget.EasyTree.prototype.select=function(id){
	var node = this.jq.tree("find",id);
	this.jq.tree("select",node.target);
};

Flywet.widget.EasyTree.prototype.getData=function(node){
	return this.jq.tree("getData",node);
};
