<%@ page contentType="text/html; charset=UTF-8"%>

<%
	//response.sendRedirect(request.getContextPath() + "/pages/editor/editor_dev.jsp");
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>享元 BA 操作平台</title>
        
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
		<div id="document-wrapper">
			<div id="document-bg">
				<img id="document-img" src="" style="width:800px;height:600px;">
			</div>
			
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
								<!--
								<div class="slide" style="background: url('resources/images/login/section/architecture.png') no-repeat scroll 0 0 transparent;">
									<p style="font-size: 36px;line-height:1em;margin:40px 0 0 40px;color:#D3D4D5;font-weight:bold;text-shadow:1px 1px 1px #C89F40;">享<br/>元<br/><font style="font-size:26px;">BA</font></p>
								</div>
								<div class="slide" style="background: url('resources/images/login/section/mascot.png') no-repeat scroll 0 0 transparent;">
									<p class="slide-p" style="margin: 25px 0 0 170px;">您的企业是否面临这样的数据？</p>
									<ul class="slide-ul" style="margin:15px 0 0 150px;color:#9CA839;">
										<li>数据量庞大</li>
										<li>数据格式复杂</li>
										<li>数据质量差</li>
										<li>数据来源众多</li>
									</ul>
								</div>
								<div class="slide" style="background: url('resources/images/login/section/mascot.png') no-repeat scroll 0 0 transparent;">
									<p class="slide-p" style="margin: 25px 0 0 170px;">您是否期望得到这样的数据？</p>
									<ul class="slide-ul" style="margin:15px 0 0 150px;color:#4066C4;">
										<li>全面的数据</li>
										<li>一致的数据</li>
										<li>完整的数据</li>
										<li>干净的数据</li>
										<li>可靠的数据</li>
									</ul>
								</div>
								<div class="slide">
									<p class="slide-single-p" style="margin:65px 0 0 40px;">享元BA平台全部帮您实现！</p>
								</div>
								-->
								
								<div class="slide" style="background: url('resources/images/login/custom/slide1.png') no-repeat scroll 0 0 transparent;">
									<p style="font-size: 36px;line-height:1em;margin:40px 0 0 40px;color:#D3D4D5;font-weight:bold;text-shadow:1px 1px 1px #C89F40;">气候预测<br/><br/>工作平台<br/></p>
								</div>
								<div class="slide" style="background: url('resources/images/login/section/mascot.png') no-repeat scroll 0 0 transparent;">
									<p class="slide-p" style="margin: 25px 0 0 170px;">预测平台网络共享</p>
									<ul class="slide-ul" style="margin:25px 15px 0 140px;color:#9CA839;list-style:none outside none;">
										<li>来宾guest用户（密码guest）登陆，不仅可以查看下载气候预测服务产品，还可以了解气候预测制作过程。</li>
									</ul>
								</div>
								<div class="slide" style="background: url('resources/images/login/section/mascot.png') no-repeat scroll 0 0 transparent;">
									<p class="slide-p" style="margin: 25px 0 0 170px;">Web工作平台优势 </p>
									<ul class="slide-ul" style="margin:25px 15px 0 150px;color:#9CA839;">
										<li>无需安装客户端软，可以随时随地打开网页使用；</li>
										<li>资源集中式管理；</li>
									</ul>
								</div>
								<div class="slide" style="background: url('resources/images/login/section/mascot.png') no-repeat scroll 0 0 transparent;">
									<p class="slide-p" style="margin: 25px 0 0 170px;">业务Schedule</p>
									<ul class="slide-ul" style="margin:25px 15px 0 140px;color:#9CA839;list-style:none outside none;">
										<li>根据业务时间表，自动提醒当前需要处理哪些常规业务工作。例如：每月6、7、8号提醒提交CIPAS使用情况调查表。</li>
									</ul>
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
							北京享元科技软件技术有限公司
							<br />
							版权所有(2013)©，如需转载，请注明来源
						</div>
					</div>
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