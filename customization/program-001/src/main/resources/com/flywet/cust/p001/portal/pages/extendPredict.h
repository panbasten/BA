<fly:composition freeLayout="N">
	<fly:gridLayout column="3" itemWidth="20%,75%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<h1 style="font-size:18px;">月份：</h1>
				<fly:foreach items="${monthes}" var="month">
					<a href="#" onclick="extendPredictClick('${menuId}','${month.name.baseName}');">${month.name.baseName}</a><br/>
				</fly:foreach>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0" id="extend_predict_context">
					<p>2～5日，全省大部分地区有阵雨或雷雨，东北部地区有中到大雨；</p>
					<p>10～12日，全省有中到大雨，局部暴雨；</p>
					<p>14～15日，全省有小到中雨；</p>
					<p>22～24日，全省有小到中雨，局部大雨；</p>
					<p>29～31日，全省大部分地区有小到中雨</p>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:includeJs src="page.js"/>

</fly:composition>
