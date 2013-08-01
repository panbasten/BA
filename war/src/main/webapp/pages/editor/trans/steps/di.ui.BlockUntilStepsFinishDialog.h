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

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:metaField" text="监控下面的步骤" />
		<fly:dataGrid id="${formId}:metaField" singleSelect="true" data="" height="200">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="步骤名称" width="150" editor="text" align="center" />
					<fly:column field="key" title="复制次数" width="150" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:field');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</form>
