<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
	//response.sendRedirect(request.getContextPath() + "/pages/editor/editor_dev.jsp");
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
		
		<link href="<%=path%>/resources/styles/themes/aristo/theme.css" rel="stylesheet"
			type="text/css" />
		
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
		
		<!-- replace href="<%=path%>/resources/styles/portal.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/portal.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace href="<%=path%>/resources/styles/files.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/files.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
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
			src="<%=path%>/resources/libs/spreadsheet/jquery.spreadsheet.js"></script>
		<!-- replace end -->
			
		<!-- replace src="<%=path%>/resources/portal.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/cycle/jquery.cycle.all.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/portal.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/preload_for_portal.js"></script>
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
	
		<div id="fly_portal_bg" class="fly_portal_cover">
			<img id="fly_portal_bg_img" src="" style="display:none;">
		</div>
		
		<div id="fly_portal_content">
		
		</div>
		
		<div id="fly_portal_wrapper">
			
			<!-- 头部区域 -->
			<div id="fly_portal_header">
			</div>
			
			<div id="fly_portal_menus">
				<div id="fly_portal_oper">
					<table>
					<tbody>
					<tr>
					<td>
						<a id="btn_login" href="javascript:void(0);">
							<div><div class="oper-text" id="btn_login_text">登录</div></div>
						</a>
					</td>
					<td>
						<div id="btn_setting">
						</div>
						<div id="fly_portal_sub_menu_setting" 
							class="sub-menu"
							style="right:0;width:340px;top:34px;">
							<b style="right: 27px;"></b>
							<dl>
								<dt style="width:110px;">系统激活管理</dt>
								<dd style="width:230px;">
									<a id="createPriKey" href="javascript:void(0);">生成并下载密钥</a>
									<i></i>
									<a id="downloadPriKey" href="javascript:void(0);">重新下载密钥</a>
									<br />
									<a id="uploadLicense" href="javascript:void(0);">上传注册码</a>
									<i></i>
									<a href="javascript:void(0);">在线注册</a>
								</dd>
							</dl>
							<dl>
								<dt style="width:110px;">资源库管理</dt>
								<dd style="width:230px;">
									<a id="createRep" href="javascript:void(0);">创建资源库</a>
									<i></i>
									<a id="manageRep" href="javascript:void(0);">管理资源库</a>
								</dd>
							</dl>
							<dl>
								<dt style="width:110px;">登录</dt>
								<dd style="width:230px;">
									<a id="loginPortal" href="javascript:void(0);">登入平台</a>
									<i></i>
									<a id="logoutPortal" href="javascript:void(0);">登出平台</a>
								</dd>
							</dl>
						</div>
					</td>
					</tr>
					</tbody>
					</table>
				</div>
			</div>
			
			<!-- 底部区域 -->
			<div id="fly_portal_footer">
				<div id="fly_portal_desktop_ctrl">
					<div id="fly_portal_desktop_ctrl_btns">
						<a id="btn_next" title="下一页" href="javascript:void(0);">
							<div></div>
						</a>
						<a id="btn_previous" title="上一页" href="javascript:void(0);">
							<div></div>
						</a>
						<a id="btn_full" class="fly-full-sceen-out" title="全屏" href="javascript:void(0);">
							<div></div>
						</a>
					</div>
				</div>
				<div id="fly_portal_footer_content">
					<div id="fly_portal_footer_left">
						<div id="fly_portal_footer_logo">
							<img src="resources/images/default/logo.large.png" />
						</div>
						
						<div id="fly_portal_footer_title">
							<div id="footerCompanyName"></div>
							<div id="footerCompanyCopyright"></div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 登录区域 -->
			<div id="fly_login_wrapper">
				<div class="ui-login-star"><div id="btn_login_close" title="系统设置" class="ui-login-close"></div></div>
				<div id="fly_login">
					<div id="fly_login_header">
						<div id="title">
							<img src="resources/images/default/logo.large.png" />
						</div>
					</div>
					<div id="fly_login_container">
					
						<div id="fly_login_content" class="content">
							<!-- 内容区域 -->
							<div id="section" class="section">
								
								<div class="slide">
									<p style="font-size: 36px;line-height:1em;margin:40px 0 0 40px;color:#D3D4D5;font-weight:bold;text-shadow:1px 1px 1px #C89F40;">辅稳BA平台</p>
								</div>
								
							</div>
							
							
						</div>
						
						<div id="fly_login_sidebar" style="position: relative; top: -50%;">
							<div class="ui-loging-title"></div>
							<form id="loginForm" style="margin:0;">
							<fieldset style="width:215px;">
								<div id="repositoryDiv" style="display:none;">
									<h1 class="ui-login-label"><label for="repository">资源库</label></h1>
									<select id="repository" name="repository" style="width:135px;" class="ui-login-widget">
									</select>
								</div>
								<div>
									<h1 class="ui-login-label"><label for="username">用户名</label></h1>
									<input id="username" name="username" class="ui-login-widget" value="" />
								</div>
								<div>
									<h1 class="ui-login-label"><label for="password">密　码</label></h1>
									<input id="password" name="password" type="password" class="ui-login-widget" value="" />
								</div>
								
								<div class="ui-toeditor-div"> 
									<input id="toeditor" name="toeditor" type="checkbox" checked="checked" style="float:left;" />
									<label for="toeditor">管理控制台</label>
								</div>
								<div class="buttonBox">
									<div id="loginBtn" class="ui-login-button ui-login-button-default"></div>
								</div>
							</fieldset>
							</form>
						</div>
						
					</div>
					
					<div id="fly_login_footer">
						<div id="fly_login_footer_box">
							<font id="companyName"></font>
							<br />
							<font id="companyCopyright"></font>
							<br />
							<font id="companyOthers"></font>
						</div>
					</div>
				</div>
			</div>
		
		
		</div>
		
		<iframe id="file-download-frame" name="file-download-frame" class="fly-space-frame"></iframe>
	</body>
	<script type="text/javascript">
	$(document).ready( function() {
		Flywet.Portal.initPage();
		$(window).bind('resize', function() {
			Flywet.Portal.resize();
		});
	});
	</script>
</html>