<form method="post" id="di_create_form">

	<fly:gridLayout column="2" itemWidth="30%,70%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="desc" text="转换名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="desc" name="desc" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:inputText id="dirId" name="dirId" type="hidden" value="${dirId}" />

</form>
