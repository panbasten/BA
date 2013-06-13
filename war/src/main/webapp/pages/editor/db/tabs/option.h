<fly:composition freeLayout="N">
	<fly:verticalLayout>
		<fly:labelObject text="命名参数" />
		
		<fly:dataGrid id="${formId}:options" singleSelect="true" selectable="false" data="${connectionProperties}" height="280">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="参数名" width="150" editor="text" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:options:append" icon="ui-icon-plusthick" onclick="Plywet.database.option_add_on_click('${formId}');" title="添加" />
				<fly:pushbutton id="${formId}:options:remove" icon="ui-icon-closethick" onclick="" title="添加" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>
