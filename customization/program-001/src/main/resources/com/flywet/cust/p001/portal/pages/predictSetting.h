<form method="post" id="portal_menu_form">

	<fly:gridLayout column="3" itemWidth="20%,20%,58%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<span style="float:right;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span style="float:left;">${currentMonthText}</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:pushbutton iconCls="ui-icon-plusthick" onclick="Flywet.PortalAction.openUploadDialog(5,'custom.portal.monthPredict.file.rootPath','${currentMonth}','custom.portal.monthPredict.file.category','predictSettingUpdate()')" label="添加" title="添加" />
			<fly:pushbutton iconCls="ui-icon-closethick" onclick="" label="删除" title="删除" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:horizontalLayout id="month_predict_files">
				<fly:browse value="${currentMonthFiles}" />
			</fly:horizontalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>
	<fly:inputText id="id" name="id" type="hidden" value="" />

	<fly:includeJs src="page.js"/>

</form>
