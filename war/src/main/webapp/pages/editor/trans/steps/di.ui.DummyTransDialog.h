<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
</form>
