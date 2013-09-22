<fly:composition freeLayout="N">
	<fly:gridLayout column="2" itemWidth="20%,75%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<h1>月份：</h1>
				<a href="#" onclick="monthPredictClick('201312');">2013年12月</a>
				<a href="#" onclick="monthPredictClick('201311');">2013年11月</a>
				<a href="#" onclick="monthPredictClick('201310');">2013年10月</a>
				<a href="#" onclick="monthPredictClick('201309');">2013年9月</a>
				<a href="#" onclick="monthPredictClick('201308');">2013年8月</a>
				<a href="#" onclick="monthPredictClick('201307');">2013年7月</a>
				<a href="#" onclick="monthPredictClick('201306');">2013年6月</a>
				<a href="#" onclick="monthPredictClick('201305');">2013年5月</a>
				<a href="#" onclick="monthPredictClick('201304');">2013年4月</a>
				<a href="#" onclick="monthPredictClick('201303');">2013年3月</a>
				<a href="#" onclick="monthPredictClick('201302');">2013年2月</a>
				<a href="#" onclick="monthPredictClick('201301');">2013年1月</a>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<fly:horizontalLayout id="month_predict_imgs">

				</fly:horizontalLayout>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:includeJs src="page.js"/>
	<fly:includeCss src="page.js"/>

</fly:composition>
