<form id="${formId}" class="fly-dialog-form" method="post">
	<fly:tabView id="${formId}_tab" >
		<fly:tab id="${formId}_tab_base" title="基本">
			<fly:include src="tabs/base.h" />
		</fly:tab>
		<fly:tab id="${formId}_tab_parameter" title="参数">
			<fly:include src="tabs/parameter.h" />
		</fly:tab>
	</fly:tabView>
</form>