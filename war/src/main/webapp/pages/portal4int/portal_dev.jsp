<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		
		<title></title>
        
		<link rel="shortcut icon" href="<%=path%>/resources/images/logo.ico" />
		<link rel="icon" href="<%=path%>/resources/images/logo.ico" />
		
		<!-- replace href="<%=path%>/resources/styles/default.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/reset.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/styles/default.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
			
		<!-- replace href="<%=path%>/resources/libs.compressed.css" type="style" -->
		<link href="<%=path%>/resources/libs/jquery/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/core/core.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/menu/menu.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/metro/metro.css" rel="stylesheet"
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
		<link href="<%=path%>/resources/libs/gridlayout/gridlayout.css" rel="stylesheet"
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
		<link href="<%=path%>/resources/libs/spreadsheet/spreadsheet.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<link href="<%=path%>/resources/styles/themes/aristo/theme.css" rel="stylesheet"
			type="text/css" />
			
		<link href="<%=path%>/resources/styles/portal4int.css" rel="stylesheet"
			type="text/css" />
		
			
			
			
		<!-- replace src="<%=path%>/resources/libs.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/jquery/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/core/core.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/form/form.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/menu/menu.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/metro/metro.js"></script>
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
			src="<%=path%>/resources/libs/gridlayout/gridlayout.js"></script>
		

		<script type="text/javascript"
			src="<%=path%>/resources/libs/drag/jquery.draggable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/drag/jquery.droppable.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/resizable/jquery.resizable.js"></script>
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
			src="<%=path%>/resources/libs/treegrid/jquery.treegrid.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/fusioncharts/FusionCharts.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/fusioncharts/fly.fusioncharts.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/spreadsheet/jquery.spreadsheet.js"></script>
		<!-- replace end -->
			
			
		<!-- replace src="<%=path%>/resources/portal4int.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/portal4int.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/preload_for_portal4int.js"></script>
		<!-- replace end -->
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body id="idBody">
	
		<div id="fly_portal_cover" class="fly_portal_cover" style="display:none;">
			<div id="fly_portal_cover_img">
				<img src="resources/images/portal/loading.gif" />
			</div>
		</div>

		<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <span class="navbar-brand">经营数据管理平台</span>
	        </div>
	        
	        <div class="navbar-collapse collapse">
	          <ul id="fly_portal_menu_1st_level" class="nav navbar-nav navbar-right">
	          	<!-- 1st level menu -->
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </div>
	
	
	    <div class="container" id="idMainContainer">
	
	      <div class="row">
	      
	        <div class="col-xs-6 col-md-3" id="fly_portal_menu_2ed_level" role="navigation">
	          <!-- 2ed & 3th level menu -->
	        </div><!--/span-->

	        <div class="col-xs-12 col-md-9" style="height:3850px;" id="idMainView" role="view">
	          
	          <div id="idMainView2"></div>
	          
	        </div><!--/span-->
	
	      </div><!--/row-->
	
	      <hr>
	
	      <footer>
	      	<div class="container">
	      	  <div class="row">
	      	    <div class="col-md-2 text-muted">
	              <p>经营数据管理平台</p>
	            </div>
	            <div class="col-md-3 text-muted">
	              <a href="">收藏本站</a>
	              |
	              <a href="">联系我们</a>
	            </div>
	            <div class="col-md-5 text-muted">
	              <p>系统意见反馈联系 927363 &copy;畅游天下2014</p>
	            </div>
	        </div>
	      </footer>
	
	    </div>
		
	</body>
	<script type="text/javascript">
		$(document).ready( function() {
        
        	Flywet.Portal4int.initPage();
        	$(window).bind('resize', function() {
				Flywet.Portal4int.resize();
			});

        });
	</script>
</html>