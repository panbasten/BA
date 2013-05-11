<%@ page contentType="text/html; charset=UTF-8"%>

<%
	//response.sendRedirect(request.getContextPath() + "/pages/editor/editor_dev.jsp");
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>享元 BI 操作平台</title>
        
		<link rel="shortcut icon" href="<%=path%>/resources/images/logo.ico" />
		<link rel="icon" href="<%=path%>/resources/images/logo.ico" />
		
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
			src="<%=path%>/resources/scripts/login.js"></script>
		<!-- replace end -->	
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body id="idBody">
		<div id="document">
			<div class="ui-login-star"></div>
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
							<div id="slide1" class="slide">
								<p>享<br/>元<br/><font class="slide-ba">BA</font></p>
							</div>
							<div id="slide2" class="slide">
								<p>您的企业是否面临这样的数据？</p>
								<ul>
									<li>数据量庞大</li>
									<li>数据格式复杂</li>
									<li>数据质量差</li>
									<li>数据来源众多</li>
								</ul>
							</div>
							<div id="slide3" class="slide">
								<p>您是否期望得到这样的数据？</p>
								<ul>
									<li>全面的数据</li>
									<li>一致的数据</li>
									<li>完整的数据</li>
									<li>干净的数据</li>
									<li>可靠的数据</li>
								</ul>
							</div>
							<div id="slide4" class="slide">
								<p>享元BA平台全部帮您实现！</p>
							</div>
						</div>
						
						
					</div>
					
					<div id="sidebar" style="position: relative; top: -50%;">
						<div class="ui-loging-title"></div>
						<form id="login" style="margin:0;">
						<fieldset style="width:215px;">
							<div>
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
							
							<div id="errors" class="errors"> 
								
							</div>
							<div class="buttonBox">
								<input id="loginBtn" class="ui-login-button ui-login-button-default" 
									onmouseover="$(this).addClass('ui-login-button-hover');" 
									onmouseout="$(this).removeClass('ui-login-button-hover');"
									onfocus="$(this).addClass('ui-login-button-hover');" 
									onblur="$(this).removeClass('ui-login-button-hover');"
									value="" type="button" />
							</div>
						</fieldset>
						</form>
					</div>
					
				</div>
				<div id="footer">
					北京享元科技软件技术有限公司&#160;&#160;&#160;&#160;&#160;&#160;
					<br />
					版权所有(2012)©，如需转载，请注明来源&#160;&#160;&#160;&#160;&#160;&#160;
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	$(document).ready( function() {
		Plywet.Login.initPage();
		$(window).bind('resize', function() {
			Plywet.Login.resize();
		});
	});
	</script>
</html>