<form method="post" id="portal_menu_form">

	<fly:gridLayout column="1" itemWidth="98%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:horizontalLayout id="data_show_files">
				<fly:browse id="data_show_browse" value="${files}" />
			</fly:horizontalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

</form>
