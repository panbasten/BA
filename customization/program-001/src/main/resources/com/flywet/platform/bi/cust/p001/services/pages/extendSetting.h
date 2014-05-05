<form method="post" id="portal_menu_form">

	<fly:gridLayout column="2" itemWidth="20%,78%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<span style="float:right;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:selectMenu id="extend_setting_select" style="float:left;" value="${currentText}" onchange="extendSettingUpdata()">
                <fly:options value="0" label="1" items="${menus}" />
            </fly:selectMenu>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="2">
			<textarea id="content" name="content" style="width:100%;" rows="16">
			${content}
			</textarea>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:inputText id="esu_title" name="esu_title" type="hidden" value="${currentText}" />

	<fly:includeJs src="page.js"/>

</form>
