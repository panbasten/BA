<fly:composition freeLayout="N">
	<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<h1 style="font-size:18px;">月份：</h1>
				<fly:foreach items="${menus}" var="menu">
					<a href="#" onclick="Flywet.Portal.updateMenuDialog('${menuId}',20,'extend_predict_context','${menu[0]}');">${menu[1]}</a><br/>
				</fly:foreach>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0" id="extend_predict_context">
				<p style="text-align:center;font-size:1.5em;font-weight:bord">${xun_desc}</p>
				<p style="text-align:left;">${extend_desc}</p>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:includeJs src="page.js"/>

</fly:composition>
