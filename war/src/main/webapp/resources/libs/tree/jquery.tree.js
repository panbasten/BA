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
                var jsonObj = $.extend({}, Plywet.parseOptions(this, ["id", "iconCls", "state"]), {
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
            node.addClass("ui-tree-node-hover");
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
            node.removeClass("ui-tree-node-hover");
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
                $(this).next("ul").find("div.ui-tree-node").droppable({
                    accept: "no-accept"
                });
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
                $(this).next("ul").find("div.ui-tree-node").droppable({
                    accept: "div.ui-tree-node"
                });
            }
        }).droppable({
            accept: "div.ui-tree-node",
            onDragOver: function(e, node){
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
                var data = $(target).tree("pop", sNode);
                $(target).tree("append", {
                    parent: tNode,
                    data: [data]
                });
                options.onDrop.call(target, tNode, data, "append");
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
            var data = $(target).tree("pop", sNode);
            opts.data = data;
            $(target).tree("insert", opts);
            options.onDrop.call(target, tNode, data, position);
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
        var _4f = [];
        var len = $(ul).prev("div.ui-tree-node").find("span.ui-tree-indent, span.ui-tree-hit").length;
        _51(ul, data, len);
        if (opts.dnd) {
            _enableDnd(target);
        }
        else {
            _disableDnd(target);
        }
        for (var i = 0; i < _4f.length; i++) {
            _check(target, _4f[i], true);
        }
        setTimeout(function(){
            _59(target, target);
        }, 0);
        var node = null;
        if (target != ul) {
            var prevUl = $(ul).prev();
            node = _getNode(target, prevUl[0]);
        }
        opts.onLoadSuccess.call(target, node, data);
        
        function _51(ul, _54, _55){
            for (var i = 0; i < _54.length; i++) {
                var li = $("<li></li>").appendTo(ul);
                var _56 = _54[i];
                if (_56.state != "open" && _56.state != "closed") {
                    _56.state = "open";
                }
                var _57 = $("<div class=\"ui-tree-node\"></div>").appendTo(li);
                _57.attr("node-id", _56.id);
                $.data(_57[0], "ui-tree-node", {
                    id: _56.id,
                    text: _56.text,
                    iconCls: _56.iconCls,
                    attributes: _56.attributes
                });
                $("<span class=\"ui-tree-title\"></span>").html(_56.text).appendTo(_57);
                if (opts.checkbox) {
                    if (opts.onlyLeafCheck) {
                        if (_56.state == "open" && (!_56.children || !_56.children.length)) {
                            if (_56.checked) {
                                $("<span class=\"ui-tree-checkbox ui-tree-checkbox1\"></span>").prependTo(_57);
                            }
                            else {
                                $("<span class=\"ui-tree-checkbox ui-tree-checkbox0\"></span>").prependTo(_57);
                            }
                        }
                    }
                    else {
                        if (_56.checked) {
                            $("<span class=\"ui-tree-checkbox ui-tree-checkbox1\"></span>").prependTo(_57);
                            _4f.push(_57[0]);
                        }
                        else {
                            $("<span class=\"ui-tree-checkbox ui-tree-checkbox0\"></span>").prependTo(_57);
                        }
                    }
                }
                if (_56.children && _56.children.length) {
                    var _58 = $("<ul></ul>").appendTo(li);
                    if (_56.state == "open") {
                        $("<span class=\"ui-tree-icon ui-icon ui-tree-folder ui-tree-folder-open\"></span>").addClass(_56.iconCls).prependTo(_57);
                        $("<span class=\"ui-tree-hit ui-icon ui-tree-expanded\"></span>").prependTo(_57);
                    }
                    else {
                        $("<span class=\"ui-tree-icon ui-icon ui-tree-folder\"></span>").addClass(_56.iconCls).prependTo(_57);
                        $("<span class=\"ui-tree-hit ui-icon ui-tree-collapsed\"></span>").prependTo(_57);
                        _58.css("display", "none");
                    }
                    _51(_58, _56.children, _55 + 1);
                }
                else {
                    if (_56.state == "closed") {
                        $("<span class=\"ui-tree-icon ui-icon ui-tree-folder\"></span>").addClass(_56.iconCls).prependTo(_57);
                        $("<span class=\"ui-tree-hit ui-icon ui-tree-collapsed\"></span>").prependTo(_57);
                    }
                    else {
                        $("<span class=\"ui-tree-icon ui-icon ui-tree-file\"></span>").addClass(_56.iconCls).prependTo(_57);
                        $("<span class=\"ui-tree-indent\"></span>").prependTo(_57);
                    }
                }
                for (var j = 0; j < _55; j++) {
                    $("<span class=\"ui-tree-indent\"></span>").prependTo(_57);
                }
            }
        }
    }
    
    function _59(target, ul, isRoot){
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
                _59(target, ul, isRoot);
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
    function _expand(top, target, callback){
        var options = $.data(top, "tree").options;
        var hit = $(target).children("span.ui-tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("ui-tree-expanded")) {
            return;
        }
        var targetNode = _getNode(top, target);
        if (options.onBeforeExpand.call(top, targetNode) == false) {
            return;
        }
        hit.removeClass("ui-tree-collapsed ui-tree-collapsed-hover").addClass("ui-tree-expanded");
        hit.next().addClass("ui-tree-folder-open");
        var ul = $(target).next();
        if (ul.length) {
            if (options.animate) {
                ul.slideDown("normal", function(){
                    options.onExpand.call(top, targetNode);
                    if (callback) {
                        callback();
                    }
                });
            }
            else {
                ul.css("display", "block");
                options.onExpand.call(top, targetNode);
                if (callback) {
                    callback();
                }
            }
        }
        else {
            var ul = $("<ul style=\"display:none\"></ul>").insertAfter(target);
            _67(top, ul[0], {
                id: targetNode.id
            }, function(){
                if (ul.is(":empty")) {
                    ul.remove();
                }
                if (options.animate) {
                    ul.slideDown("normal", function(){
                        options.onExpand.call(top, targetNode);
                        if (callback) {
                            callback();
                        }
                    });
                }
                else {
                    ul.css("display", "block");
                    options.onExpand.call(top, targetNode);
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
    function _collapse(top, target){
        var options = $.data(top, "tree").options;
        var hit = $(target).children("span.ui-tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("ui-tree-collapsed")) {
            return;
        }
        var targetNode = _getNode(top, target);
        if (options.onBeforeCollapse.call(top, targetNode) == false) {
            return;
        }
        hit.removeClass("ui-tree-expanded ui-tree-expanded-hover").addClass("ui-tree-collapsed");
        hit.next().removeClass("ui-tree-folder-open");
        var ul = $(target).next();
        if (options.animate) {
            ul.slideUp("normal", function(){
                options.onCollapse.call(top, targetNode);
            });
        }
        else {
            ul.css("display", "none");
            options.onCollapse.call(top, targetNode);
        }
    }
    
    function _toggle(top, target){
        var hit = $(target).children("span.ui-tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("ui-tree-expanded")) {
            _collapse(top, target);
        }
        else {
            _expand(top, target);
        }
    }
    
    function _expandAll(top, target){
        var arr = _getChildren(top, target);
        if (target) {
            arr.unshift(_getNode(top, target));
        }
        for (var i = 0; i < arr.length; i++) {
            _expand(top, arr[i].target);
        }
    }
    
    function _expandTo(top, target){
        var arr = [];
        var p = _getParent(top, target);
        while (p) {
            arr.unshift(p);
            p = _getParent(top, p.target);
        }
        for (var i = 0; i < arr.length; i++) {
            _expand(top, arr[i].target);
        }
    }
    
    function _collapseAll(top, target){
        var arr = _getChildren(top, target);
        if (target) {
            arr.unshift(_getNode(top, target));
        }
        for (var i = 0; i < arr.length; i++) {
            _collapse(top, arr[i].target);
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
    
    function _getChildren(top, target){
        var arr = [];
        if (target) {
            _children($(target));
        }
        else {
            var roots = _getRoots(top);
            for (var i = 0; i < roots.length; i++) {
                arr.push(roots[i]);
                _children($(roots[i].target));
            }
        }
        
        function _children(parentNode){
            parentNode.next().find("div.ui-tree-node").each(function(){
                arr.push(_getNode(top, this));
            });
        }
        
        return arr;
    }
    
    function _getParent(top, target){
        var ul = $(target).parent().parent();
        if (ul[0] == top) {
            return null;
        }
        else {
            return _getNode(top, ul.prev()[0]);
        }
    }
    
    function _getChecked(top, type){
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
        $(top).find(selector).each(function(){
            var node = $(this).parent();
            result.push(_getNode(top, node[0]));
        });
        return result;
    }
    
    function _getSelected(top){
        var nodes = $(top).find("div.ui-tree-node-selected");
        if (nodes.length) {
            return _getNode(top, nodes[0]);
        }
        else {
            return null;
        }
    }
    
    function _append(top, target){
        var parent = $(target.parent);
        var ul;
        if (parent.length == 0) {
            ul = $(top);
        }
        else {
            ul = parent.next();
            if (ul.length == 0) {
                ul = $("<ul></ul>").insertAfter(parent);
            }
        }
        if (target.data && target.data.length) {
            var span = parent.find("span.ui-tree-icon");
            if (span.hasClass("ui-tree-file")) {
                span.removeClass("ui-tree-file").addClass("ui-tree-folder");
                var hit = $("<span class=\"ui-tree-hit ui-icon ui-tree-expanded\"></span>").insertBefore(span);
                if (hit.prev().length) {
                    hit.prev().remove();
                }
            }
        }
        _loadData(top, ul[0], target.data, true);
        _dealCheckbox(top, ul.prev());
    }
    
    function _insert(top, target){
        var ref = target.before || target.after;
        var parent = _getParent(top, ref);
        var li;
        if (parent) {
            _append(top, {
                parent: parent.target,
                data: [target.data]
            });
            li = $(parent.target).next().children("li:last");
        }
        else {
            _append(top, {
                parent: null,
                data: [target.data]
            });
            li = $(top).children("li:last");
        }
        if (target.before) {
            li.insertBefore($(ref).parent());
        }
        else {
            li.insertAfter($(ref).parent());
        }
    }
    
    function _remove(top, target){
        var parent = _getParent(top, target);
        var jqtarget = $(target);
        var li = jqtarget.parent();
        var ul = li.parent();
        li.remove();
        if (ul.children("li").length == 0) {
            var jqtarget = ul.prev();
            jqtarget.find(".ui-tree-icon").removeClass("ui-tree-folder").addClass("ui-tree-file");
            jqtarget.find(".ui-tree-hit").remove();
            $("<span class=\"ui-tree-indent\"></span>").prependTo(jqtarget);
            if (ul[0] != top) {
                ul.remove();
            }
        }
        if (parent) {
            _dealCheckbox(top, parent.target);
        }
        _59(top, top);
    }
    
    function _getData(top, target){
        function getSubData(sub, ul){
            ul.children("li").each(function(){
                var n = $(this).children("div.ui-tree-node");
                var node = _getNode(top, n[0]);
                var sub = $(this).children("ul");
                if (sub.length) {
                	node.children = [];
                	getSubData(node.children, sub);
                }
                sub.push(node);
            });
        }
        
        if (target) {
            var node = _getNode(top, target);
            node.children = [];
            getSubData(node.children, $(target).next());
            return node;
        }
        else {
            return null;
        }
    }
    
    function _update(top, jsondata){
        var jqtarget = $(jsondata.target);
        var targetNode = _getNode(top, jsondata.target);
        if (targetNode.iconCls) {
            jqtarget.find(".ui-tree-icon").removeClass(targetNode.iconCls);
        }
        var _bf = $.extend({}, targetNode, jsondata);
        $.data(jsondata.target, "ui-tree-node", _bf);
        jqtarget.attr("node-id", _bf.id);
        jqtarget.find(".ui-tree-title").html(_bf.text);
        if (_bf.iconCls) {
            jqtarget.find(".ui-tree-icon").addClass(_bf.iconCls);
        }
        if (targetNode.checked != _bf.checked) {
            _check(top, jsondata.target, _bf.checked);
        }
    }
    
    function _getNode(top, target){
        var node = $.extend({}, $.data(target, "ui-tree-node"), {
            target: target,
            checked: $(target).find(".ui-tree-checkbox").hasClass("ui-tree-checkbox1")
        });
        if (!_isLeaf(top, target)) {
        	node.state = $(target).find(".ui-tree-hit").hasClass("ui-tree-expanded") ? "open" : "closed";
        }
        return node;
    }
    
    function _find(top, id){
        var results = $(top).find("div.ui-tree-node[node-id=" + id + "]");
        if (results.length) {
            return _getNode(top, results[0]);
        }
        else {
            return null;
        }
    }
    
    function _select(top, target){
        var options = $.data(top, "tree").options;
        var targetNode = _getNode(top, target);
        if (options.onBeforeSelect.call(top, targetNode) == false) {
            return;
        }
        $("div.ui-tree-node-selected", top).removeClass("ui-tree-node-selected");
        $(target).addClass("ui-tree-node-selected");
        options.onSelect.call(top, targetNode);
    }
    
    function _isLeaf(top, target){
        var jqTarget = $(target);
        var hit = jqTarget.children("span.ui-tree-hit");
        return hit.length == 0;
    }
    
    function _beginEdit(top, target){
        var options = $.data(top, "tree").options;
        var targetNode = _getNode(top, target);
        if (options.onBeforeEdit.call(top, targetNode) == false) {
            return;
        }
        $(target).css("position", "relative");
        var nt = $(target).find(".ui-tree-title");
        var nwidth = nt.outerWidth();
        nt.empty();
        var input = $("<input class=\"ui-tree-editor\">").appendTo(nt);
        input.val(targetNode.text).focus();
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
                _updataNode(top, target);
                return false;
            }
            else {
                if (e.keyCode == 27) {
                    _cancelEdit(top, target);
                    return false;
                }
            }
        }).bind("blur", function(e){
            e.stopPropagation();
            _updataNode(top, target);
        });
    }
    
    function _updataNode(top, target){
        var options = $.data(top, "tree").options;
        $(target).css("position", "");
        var input = $(target).find("input.ui-tree-editor");
        var val = input.val();
        input.remove();
        var targetNode = _getNode(top, target);
        targetNode.text = val;
        _update(top, targetNode);
        options.onAfterEdit.call(top, targetNode);
    }
    
    function _cancelEdit(top, target){
        var options = $.data(top, "tree").options;
        $(target).css("position", "");
        $(target).find("input.ui-tree-editor").remove();
        var targetNode = _getNode(top, target);
        _update(top, targetNode);
        options.onCancelEdit.call(top, targetNode);
    }
    
    $.fn.tree = function(options, param){
        if (typeof options == "string") {
            return $.fn.tree.methods[options](this, param);
        }
        options = options || {};
        return this.each(function(){
            var treedata = $.data(this, "tree");
            var temp;
            if (treedata) {
                temp = $.extend(treedata.options, options);
                treedata.options = temp;
            }
            else {
                temp = $.extend({}, $.fn.tree.defaults, $.fn.tree.parseOptions(this), options);
                $.data(this, "tree", {
                    options: temp,
                    tree: _addRootCss(this)
                });
                var optionArr = _getOptionArr(this);
                if (optionArr.length && !temp.data) {
                    temp.data = optionArr;
                }
            }
            _initEvents(this);
            if (temp.lines) {
                $(this).addClass("ui-tree-lines");
            }
            if (temp.data) {
                _loadData(this, this, temp.data);
            }
            else {
                if (temp.dnd) {
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
        getNode: function(jq, _e6){
            return _getNode(jq[0], _e6);
        },
        getData: function(jq, _e7){
            return _getData(jq[0], _e7);
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
        getParent: function(jq, target){
            return _getParent(jq[0], target);
        },
        getChildren: function(jq, target){
            return _getChildren(jq[0], target);
        },
        getChecked: function(jq, type){
            return _getChecked(jq[0], type);
        },
        getSelected: function(jq){
            return _getSelected(jq[0]);
        },
        isLeaf: function(jq, _ed){
            return _isLeaf(jq[0], _ed);
        },
        find: function(jq, id){
            return _find(jq[0], id);
        },
        select: function(jq, _ee){
            return jq.each(function(){
                _select(this, _ee);
            });
        },
        check: function(jq, _ef){
            return jq.each(function(){
                _check(this, _ef, true);
            });
        },
        uncheck: function(jq, _f0){
            return jq.each(function(){
                _check(this, _f0, false);
            });
        },
        collapse: function(jq, _f1){
            return jq.each(function(){
                _collapse(this, _f1);
            });
        },
        expand: function(jq, _f2){
            return jq.each(function(){
                _expand(this, _f2);
            });
        },
        collapseAll: function(jq, _f3){
            return jq.each(function(){
                _collapseAll(this, _f3);
            });
        },
        expandAll: function(jq, _f4){
            return jq.each(function(){
                _expandAll(this, _f4);
            });
        },
        expandTo: function(jq, _f5){
            return jq.each(function(){
                _expandTo(this, _f5);
            });
        },
        toggle: function(jq, _f6){
            return jq.each(function(){
                _toggle(this, _f6);
            });
        },
        append: function(jq, _f7){
            return jq.each(function(){
                _append(this, _f7);
            });
        },
        insert: function(jq, _f8){
            return jq.each(function(){
                _insert(this, _f8);
            });
        },
        remove: function(jq, _f9){
            return jq.each(function(){
                _remove(this, _f9);
            });
        },
        pop: function(jq, target){
            var _pop = jq.tree("getData", target);
            jq.tree("remove", target);
            return _pop;
        },
        update: function(jq, _fc){
            return jq.each(function(){
                _update(this, _fc);
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
        beginEdit: function(jq, _fd){
            return jq.each(function(){
                _beginEdit(this, _fd);
            });
        },
        endEdit: function(jq, _fe){
            return jq.each(function(){
                _updataNode(this, _fe);
            });
        },
        cancelEdit: function(jq, _ff){
            return jq.each(function(){
                _cancelEdit(this, _ff);
            });
        }
    };
    $.fn.tree.parseOptions = function(target){
        var t = $(target);
        return $.extend({}, Plywet.parseOptions(target, ["url", "method", {
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
        onBeforeEdit: function(target){
        },
        onAfterEdit: function(target){
        },
        onCancelEdit: function(target){
        }
    };
})(jQuery);
Plywet.widget.EasyTree=function(cfg){
	this.cfg=cfg;    
	this.treedata=this.transdata(cfg.els?cfg.els:[]);
	this.id = cfg.id;
    this.jqId = Plywet.escapeClientId(this.id);
    this.jq = $(this.jqId);
	$("#"+this.id).tree(cfg);
	$("#"+this.id).tree("loadData",this.treedata);
};
Plywet.extend(Plywet.widget.EasyTree, Plywet.widget.BaseWidget);
Plywet.widget.EasyTree.prototype.transdata=function(initdata){
	if(initdata){
		for(var i=0; i<initdata.length;i++){
				initdata[i].text=initdata[i].displayName;
				initdata[i].iconCls=initdata[i].icon;
				initdata[i].children=initdata[i].els;
				if(initdata[i].els){
					recursion(initdata[i].els);
				}	
		}
	}
	function recursion(array){
		for(var i=0;i<array.length;i++ ){
				array[i].text=array[i].displayName;
				array[i].iconCls=array[i].icon;
				array[i].children=array[i].els;
				if(array[i].els&&array[i].els.length>0){
					recursion(array[i].els);
				}
		}
	}
	return initdata;
};
