<fly:composition freeLayout="N">
	<fly:gridLayout column="3" itemWidth="20%,55%,20%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<h1 style="font-size:18px;">月份：</h1>
				<fly:foreach items="${monthes}" var="month">
					<a href="#" onclick="Flywet.PortalAction.updateMenuDialog('${menuId}',19,'month_predict_imgs,month_predict_files','${month[0]}');">${month[1]}</a><br/>
				</fly:foreach>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<fly:gridLayout id="month_predict_imgs" heightAuto="true" itemMargin="20" column="2" itemWidth="49%,49%">
					<fly:gridLayoutItem>
						<img style="width:100%;" src="rest/portalet/getfile?rootPath=custom.portal.monthPredict.file.rootPath&amp;category=custom.portal.monthPredict.file.category&amp;path=${currentMonth}%2F%E6%B0%94%E6%B8%A9%E9%A2%84%E6%B5%8B.jpg" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem>
						<img style="width:100%;" src="rest/portalet/getfile?rootPath=custom.portal.monthPredict.file.rootPath&amp;category=custom.portal.monthPredict.file.category&amp;path=${currentMonth}%2F%E9%99%8D%E6%B0%B4%E9%A2%84%E6%B5%8B.jpg" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem>
						<img style="width:100%;" src="rest/portalet/getfile?rootPath=custom.portal.monthPredict.file.rootPath&amp;category=custom.portal.monthPredict.file.category&amp;path=${currentMonth}%2F%E6%B0%94%E5%80%99%E5%B9%B3%E5%9D%87%E6%B0%94%E6%B8%A9.jpg" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem>
						<img style="width:100%;" src="rest/portalet/getfile?rootPath=custom.portal.monthPredict.file.rootPath&amp;category=custom.portal.monthPredict.file.category&amp;path=${currentMonth}%2F%E6%B0%94%E5%80%99%E5%B9%B3%E5%9D%87%E9%99%8D%E6%B0%B4.jpg" />
					</fly:gridLayoutItem>
				</fly:gridLayout>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:horizontalLayout id="month_predict_files">
				<fly:browse value="${currentMonthFiles}" />
			</fly:horizontalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

</fly:composition>
