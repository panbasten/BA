<form method="post" id="portal_menu_form">

	<fly:gridLayout column="3" itemWidth="20%,20%,58%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<span style="float:right;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span style="float:left;">${currentMonthText}</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:pushbutton style="float:right;" iconCls="ui-icon-closethick" onclick="Flywet.PortalAction.deleteFile('month_predict_browse_var','predictSettingUpdate()')" label="删除" title="删除" />
			<fly:pushbutton style="float:right;" iconCls="ui-icon-plusthick" onclick="Flywet.PortalAction.openUploadDialog(null,{'filesNum':5,'rootDir':'custom.portal.monthPredict.file.rootPath','workDir':'${currentMonth}','category':'custom.portal.monthPredict.file.category'},'predictSettingUpdate()')" label="添加" title="添加" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:horizontalLayout id="month_predict_files">
				<fly:browse id="month_predict_browse" value="${currentMonthFiles}" />
			</fly:horizontalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:includeJs src="page.js"/>

</form>
