<form id="${formId}" class="fly-dialog-form" method="post">

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:check" text="警告和错误" />
		<fly:dataGrid id="${formId}:check" singleSelect="true" data="" height="300">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="步骤名称" width="100" editor="text" align="center" />
					<fly:column field="value" title="结果" width="100" editor="text" align="center" />
					<fly:column field="desc" title="备注" width="300" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:options:append" icon="ui-icon-plusthick" onclick="Flywet.editors.toolbarButton.addRow('${formId}:variables');" title="添加" />
				<fly:pushbutton id="${formId}:options:remove" icon="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:variables');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
		<fly:inputText id="${formId}:showSuccess" name="${formId}:showSuccess" type="checkbox"
				text="显示成功结果" marginTop="10" />
	</fly:verticalLayout>

</form>
