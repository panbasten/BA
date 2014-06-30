<fly:composition freeLayout="N">
	<fly:verticalLayout margin="10">
		<fly:labelObject class="ui-layout-no-bottom-margin" text="命名参数" buddy="${formId}:options" />
		
		<fly:dataGrid id="${formId}:options" singleSelect="true" data="${connectionProperties}" height="280">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="参数名" width="150" editor="text" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbuttongroup>
					<fly:pushbutton id="${formId}:options:append" btnStyle="primary" btnSize="mini" iconCls="glyphicon-plus" onclick="Flywet.editors.toolbarButton.addRow('${formId}:options',{'key':'','value':''});" title="添加" />
					<fly:pushbutton id="${formId}:options:remove" btnStyle="primary" btnSize="mini" iconCls="glyphicon-minus" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:options');" title="删除" />
				</fly:pushbuttongroup>
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>
