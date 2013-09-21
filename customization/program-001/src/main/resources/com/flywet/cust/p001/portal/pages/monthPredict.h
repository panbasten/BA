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
					<img src="rest/portalet/getfile?data=%7B%22rootId%22%3A%221%22%2C%22path%22%3A%22%2F_D%2Fphotos%2F%E5%85%AC%E5%8F%B8%E5%90%8C%E4%BA%8B%2FIMG_9087.JPG%22%2C%22category%22%3A%22local%22%7D" style="height:400px;width:300px;float:left;"></img>
					<img src="rest/portalet/getfile?data=%7B%22rootId%22%3A%221%22%2C%22path%22%3A%22%2F_D%2Fphotos%2F%E5%85%AC%E5%8F%B8%E5%90%8C%E4%BA%8B%2FIMG_9088.JPG%22%2C%22category%22%3A%22local%22%7D" style="height:400px;width:300px;float:left;"></img>
					<img src="rest/portalet/getfile?data=%7B%22rootId%22%3A%221%22%2C%22path%22%3A%22%2F_D%2Fphotos%2F%E5%85%AC%E5%8F%B8%E5%90%8C%E4%BA%8B%2FIMG_9089.JPG%22%2C%22category%22%3A%22local%22%7D" style="height:400px;width:300px;float:left;"></img>
					<img src="rest/portalet/getfile?data=%7B%22rootId%22%3A%221%22%2C%22path%22%3A%22%2F_D%2Fphotos%2F%E5%85%AC%E5%8F%B8%E5%90%8C%E4%BA%8B%2FIMG_9111.JPG%22%2C%22category%22%3A%22local%22%7D" style="height:400px;width:300px;float:left;"></img>
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
