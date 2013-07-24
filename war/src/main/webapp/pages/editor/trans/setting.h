<form id="${formId}" class="fly-dialog-form" method="post">
	<fly:tabView id="${formId}_tab" >
		<fly:tab id="${formId}_tab_base" title="${message('msg_trans_setting_label_base')}">
			<fly:include src="settingtabs/base.h" />
		</fly:tab>
		<fly:tab id="${formId}_tab_parameter" title="命名参数">
			<fly:include src="settingtabs/parameter.h" />
		</fly:tab>
	</fly:tabView>
</form>
