<fly:composition freeLayout="N">
	<fly:verticalLayout margin="10">
		<fly:labelObject text="命名参数" buddy="${formId}:options" />

		<fly:dataGrid id="${formId}:options" singleSelect="true" data="${connectionProperties}" height="280">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="参数名" width="150" editor="text" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
					<fly:column field="desc" title="描述" width="300" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:options:append" icon="ui-icon-plusthick" onclick="Plywet.editors.toolbarButton.addRow('${formId}:options');" title="添加" />
				<fly:pushbutton id="${formId}:options:remove" icon="ui-icon-closethick" onclick="Plywet.editors.toolbarButton.deleteRow('${formId}:options');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>
