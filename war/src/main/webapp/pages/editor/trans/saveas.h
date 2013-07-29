<form method="post" id="trans_saveas_form">

	<fly:gridLayout column="2" itemWidth="30%,70%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="dirId" text="目录" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="dirId" name="dirId" type="text" validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="transName" text="转换名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="transName" name="transName" type="text" validate="required:true" value="${transName}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:inputText id="transId" name="transId" type="hidden" value="${transId}" />

</form>
