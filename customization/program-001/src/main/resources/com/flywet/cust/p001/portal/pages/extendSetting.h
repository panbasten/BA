<form method="post" id="portal_menu_form">

	<fly:gridLayout column="2" itemWidth="20%,78%" itemMargin="10" class="fly_portal_menu_content">
		<fly:gridLayoutItem>
			<span style="float:right;">填报日期：</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<span style="float:left;">${currentText}</span>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="2">
			<textarea id="content" name="content" style="width:100%;" rows="16">
			${content}
			</textarea>
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:inputText id="year" name="year" type="hidden" value="${year}" />
	<fly:inputText id="month" name="month" type="hidden" value="${month}" />
	<fly:inputText id="xun" name="xun" type="hidden" value="${xun}" />

</form>
