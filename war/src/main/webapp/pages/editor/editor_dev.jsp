<%@ page contentType="text/html; charset=UTF-8"%>

<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		
		<title>享元 BI 设计器平台</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="<%=path%>/resources/images/logo.ico" />
		<link rel="icon" href="<%=path%>/resources/images/logo.ico" />
		
		<!-- replace href="<%=path%>/resources/styles/editor.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/reset.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/styles/default.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<link href="<%=path%>/resources/styles/themes/aristo/theme.css" rel="stylesheet"
			type="text/css" />
		
		<!-- replace href="<%=path%>/resources/libs.compressed.css" type="style" -->
		<link href="<%=path%>/resources/libs/jquery/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/core/core.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/menu/menu.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/easytabs/easytabs.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/breadcrumb/breadcrumb.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/layout/layout.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/toolbar/toolbar.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/scrollbar/scrollbar.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/search/search.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/popup/popup.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/browsePanel/browsePanel.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/tree/tree.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/dialog/dialog.css" rel="stylesheet"
			type="text/css" />
		
		<link href="<%=path%>/resources/libs/panel/panel.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/combo/combo.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/combo/combobox.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/validate/validatebox.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/grid/pagination.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/grid/datagrid.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace href="<%=path%>/resources/styles/fly.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/fly.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace src="<%=path%>/resources/libs.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/jquery/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/core/core.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/menu/menu.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/layout/layout.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/breadcrumb/breadcrumb.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/easytabs/easytabs.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/scrollbar/scrollbar.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/search/search.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/popup/popup.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/flowchart/excanvas.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/flowchart/flowchart.utils.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/flowchart/flowchart.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/dialog/dialog.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/button/button.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/browsePanel/browsePanel.js"></script>
		

		<script type="text/javascript"
			src="<%=path%>/resources/libs/drag/jquery.draggable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/drag/jquery.droppable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/tree/jquery.tree.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/panel/jquery.panel.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/validate/jquery.validatebox.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/combo/jquery.combo.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/combo/jquery.combobox.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/number/jquery.numberbox.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/grid/jquery.pagination.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/grid/jquery.datagrid.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/form/jquery.form.js"></script>
		
		
		
					
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_browse.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_editor.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_editor_trans.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_editor_form.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_database.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_filesys.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/fly_sysTools.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/preload.js"></script>
		<!-- replace end -->
		
			
	</head>
	<body id="idBody">
		<div id="startingCover">
			<div id="startingLogo"></div>
			<div id="startingText">
				Starting ...
			</div>
			<div id="startingBarContainer">
				<div id="startingBar" style="width: 100%;"></div>
			</div>
			<div id="startingTips"></div>
		</div>

		<div id="banner" class="ui-layout-north">
		</div>
		
		<div class="ui-layout-south fly-south">
			<span style="margin-left:10px;font-weight:bold;">当前位置:&nbsp;</span><span>当前操作位置-转换区</span>
			<div style="float:right;"><span id="idUser" class="fly-userinfo">用户:Admin</span><div class="fly-exit"></div></div>
		</div>
		
		<div class="fly-container" >
			<div id="editorContent" class="ui-editorContent" style="top:12px;">
				<ul class="fly-tabs">
					<li id="navisTab"><a href="#navis" class="ui-tab-homepage" style="margin:0;padding:0;"></a><div class="ui-tab-homepage-text"></div></li>
				</ul>
				<div id="editorContentPanels" class="fly-panels">
					
				</div>
			</div>
		</div>

	</body>
	
	<script type="text/javascript">
		$(document).ready( function() {
			Plywet.desktop.initPage();
			$(window).bind('resize', function() {
				Plywet.desktop.resize();
			});
			$(document).bind('contextmenu', function(e) {
				return false;
			});
		});
	</script>
	
</html>