<form method="post" id="portal_menu_form">

	<fly:gridLayout column="2" itemWidth="20%,78%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<span style="float:right;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span style="float:left;">2013年10月上旬</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="2">
			<textarea id="content" name="content" style="width:100%;" rows="16">
			</textarea>
		</fly:gridLayoutItem>
	</fly:gridLayout>
	<fly:inputText id="id" name="id" type="hidden" 
				value="" />

	<fly:includeJs src="page.js"/>

</form>
