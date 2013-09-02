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
		
		<!-- replace href="<%=path%>/resources/styles/login.lib.compressed.css" type="style" -->
		<link href="<%=path%>/resources/libs/jquery/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/core/core.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/resources/libs/dialog/dialog.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace href="<%=path%>/resources/styles/login.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/login.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace src="<%=path%>/resources/login.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/jquery/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/cycle/jquery.cycle.all.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/core/core.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/dialog/dialog.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/login.js"></script>
		<!-- replace end -->	
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body id="idBody">
		<div id="document-wrapper">
			<div id="document-bg">
				<img id="document-img" src="" style="width:800px;height:600px;">
			</div>
			
			<div id="document">
				<div class="ui-login-star"><div id="loginSettingBtn" title="系统设置" class="ui-login-setting"></div></div>
				<div id="document2">
					<div id="header">
						<div id="title">
							<img src="resources/images/default/logo.large.png" />
						</div>
					</div>
					<div id="container">
					
						<div id="content" class="content">
							<!-- 内容区域 -->
							<div id="section" class="section">
								
								<div class="slide">
									<p style="font-size: 36px;line-height:1em;margin:40px 0 0 40px;color:#D3D4D5;font-weight:bold;text-shadow:1px 1px 1px #C89F40;">辅稳BA平台</p>
								</div>
								
							</div>
							
							
						</div>
						
						<div id="sidebar" style="position: relative; top: -50%;">
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
					<div id="footer">
						<div id="footerBox">
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
	</body>
	<script type="text/javascript">
	$(document).ready( function() {
		Flywet.Login.initPage();
		$(window).bind('resize', function() {
			Flywet.Login.resize();
		});
	});
	</script>
</html>