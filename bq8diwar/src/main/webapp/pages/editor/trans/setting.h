<form id="${formId}" class="hb-dialog-form" method="post">
	<bq:tabView id="${formId}_tab" >
		<bq:tab id="${formId}_tab_base" title="基本">
			<bq:include src="tabs/base.h" />
		</bq:tab>
		<bq:tab id="${formId}_tab_parameter" title="参数">
			<bq:include src="tabs/parameter.h" />
		</bq:tab>
	</bq:tabView>
</form>