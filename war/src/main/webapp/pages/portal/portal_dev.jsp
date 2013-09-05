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
		
		<!-- replace href="<%=path%>/resources/styles/login.compressed.css" type="style" -->
		<link href="<%=path%>/resources/styles/portal.css" rel="stylesheet"
			type="text/css" />
		<!-- replace end -->
		
		<!-- replace src="<%=path%>/resources/portal.pack.js" type="script" -->
		<script type="text/javascript"
			src="<%=path%>/resources/libs/jquery/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/libs/core/core.js"></script>
		<script type="text/javascript"
			src="<%=path%>/resources/scripts/portal.js"></script>
		<!-- replace end -->	
		
		<script type="text/javascript">
		
		</script>
			
	</head>
	<body>
		<table id="fly_portal">
		<tbody>
		<tr>
		<td id="fly_portal_cell">
			<div id="fly_portal_container">
				<div id="fly_portal_bgDiv">
					<img id="fly_portal_img" src="" style="width:800px;height:600px;">
				</div>
				
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
							</tr>
							</tbody>
							</table>
						</div>
					</div>
					
				</div>
				
				<div id="fly_portal_footer">
					<div id="fly_portal_desktop_ctrl">
						<div id="fly_portal_desktop_ctrl_btns">
							<a id="btn_next" title="下一页" href="javascript:void(0);">
								<div></div>
							</a>
							<a id="btn_previous" title="上一页" href="javascript:void(0);">
								<div></div>
							</a>
							<a id="btn_full" title="全屏" href="javascript:void(0);">
								<div></div>
							</a>
						</div>
					</div>
					<div id="fly_portal_footer_content"></div>
				</div>
			</div>
		</td>
		</tr>
		</tbody>
		</table>
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