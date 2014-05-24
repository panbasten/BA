<form method="post" id="portal_menu_form">

	<fly:gridLayout column="1" itemWidth="98%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:pushbutton style="float:left;" iconCls="ui-icon-plusthick" btnStyle="success" onclick="Flywet.PortalAction.openUploadDialog(null,{'filesNum':5,'rootDir':'custom.portal.buzNorm.file.rootPath','workDir':'','category':'custom.portal.buzNorm.file.category'},'buzNormsUpdate()')" label="添加" title="添加" />
			<fly:pushbutton style="float:left;" iconCls="ui-icon-closethick" btnStyle="danger" onclick="Flywet.PortalAction.deleteFile('norm_browse_var','buzNormsUpdate()')" label="删除" title="删除" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:horizontalLayout id="norm_files">
				<fly:browse id="norm_browse" value="${normFiles}" />
			</fly:horizontalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:includeJs src="page.js"/>

</form>
