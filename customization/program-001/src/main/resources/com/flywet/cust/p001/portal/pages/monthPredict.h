<fly:composition freeLayout="N">
	<fly:gridLayout column="2" itemWidth="20%,75%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<h1>月份：</h1>
				<a href="#" onclick="monthPredictClick('201312');">2013年12月</a>
				<a href="#" onclick="monthPredictClick('201312');">2013年11月</a>
				<a href="#" onclick="">2013年10月</a>
				<a href="#" onclick="">2013年9月</a>
				<a href="#" onclick="">2013年8月</a>
				<a href="#" onclick="">2013年7月</a>
				<a href="#" onclick="">2013年6月</a>
				<a href="#" onclick="">2013年5月</a>
				<a href="#" onclick="">2013年4月</a>
				<a href="#" onclick="">2013年3月</a>
				<a href="#" onclick="">2013年2月</a>
				<a href="#" onclick="">2013年1月</a>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="0">
				<fly:horizontalLayout>
					<img src="" style="width:300px;width:100px;">
					<img src="" style="width:300px;width:100px;">
					<img src="" style="width:300px;width:100px;">
					<img src="" style="width:300px;width:100px;">
				</fly:horizontalLayout>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>
	<script typt="text/javascript">
		function monthPredictClick(key){
			console.log(key);
		}
	</script>
</fly:composition>
