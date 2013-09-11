<%@ page contentType="text/html; charset=UTF-8"%>

<%
	//response.sendRedirect(request.getContextPath() + "/pages/editor/editor_dev.jsp");
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
        
		<link rel="shortcut icon" href="<%=path%>/resources/images/logo.ico" />
		<link rel="icon" href="<%=path%>/resources/images/logo.ico" />
		
		<link href="<%=path%>/resources/styles/themes/aristo/theme.css" rel="stylesheet"
			type="text/css" />
		
		<!-- replace href="<%=path%>/resources/login.lib.compressed.css" type="style" -->
		<link href="<%=path%>/resources/libs/jquery/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/core/core.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/dialog/dialog.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace href="<%=path%>/resources/styles/portal.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/portal.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace src="<%=path%>/resources/portal.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/jquery/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/cycle/jquery.cycle.all.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/core/core.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/button/button.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/dialog/dialog.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/portal.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/preload_for_portal.js"></script>
		<!-- replace end -->
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body id="idBody">
	
		<div id="fly_portal_bg" class="fly_portal_cover">
			<img id="fly_portal_bg_img" src="" style="width:800px;height:600px;">
		</div>
		
		<div id="fly_portal_wrapper">
			
			<!-- 头部区域 -->
			<div id="fly_portal_header">
				<div id="fly_portal_menus">
					<ul id="fly_portal_menus_ul">
						<li>
							<a href="javascript:void(0);">今日关注</a>
							<div class="clear"></div>
						</li>
						<li>
							<a href="javascript:void(0);">预测评估</a>
							<div class="clear"></div>
						</li>
						<li>
							<a href="javascript:void(0);">待办事务</a>
							<div class="clear"></div>
						</li>
					</ul>
					
					<div id="fly_portal_oper">
						<table>
						<tbody>
						<tr>
						<td>
							<a id="btn_login" href="javascript:void(0);">
							<div><span>登录</span></div>
							</a>
						</td>
						<td>
							<div id="btn_setting">
								<div></div>
							</div>
						</td>
						</tr>
						</tbody>
						</table>
					</div>
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
							<form id="login" style="margin:0;">
							<fieldset style="width:215px;">
								<div id="repositoryDiv" style="display:none;">
									<h1 class="ui-login-label"><label for="repository">资源库</label></h1>
									<select id="repository" name="repository" style="width:135px;" class="ui-login-widget">
									</select>
								</div>
								<div>
									<h1 class="ui-login-label"><label for="username">用户名</label></h1>
									<input id="username" name="username" class="ui-login-widget" value="guest" />
								</div>
								<div>
									<h1 class="ui-login-label"><label for="password">密　码</label></h1>
									<input id="password" name="password" type="password" class="ui-login-widget" value="" />
								</div>
								
								<div id="errors" class="errors"> 
									
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
		
		<iframe id="file-download-frame" class="fly-space-frame"></iframe>
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