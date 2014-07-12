<form method="post" id="portal_menu_form">

	<fly:gridLayout column="2" itemWidth="20%,78%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<span style="float:right;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:selectMenu id="esu_title" name="esu_title" style="float:left;" value="${currentText}" onchange="extendSettingUpdata()">
                <fly:options value="0" label="1" items="${menus}" />
            </fly:selectMenu>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
            <span style="float:right;">备注：</span>
        </fly:gridLayoutItem>
        <fly:gridLayoutItem>
            <fly:inputText id="esu_other_title" name="esu_other_title" style="float:left;" value="" />
        </fly:gridLayoutItem>
		<fly:gridLayoutItem cols="2">
			<textarea id="content" name="content" style="width:100%;" rows="16">
			${content}
			</textarea>
		</fly:gridLayoutItem>
	</fly:gridLayout>


	<fly:includeJs src="page.js"/>

</form>
