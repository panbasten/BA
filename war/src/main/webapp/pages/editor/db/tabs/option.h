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
		</fly:dataGrid>
	</fly:verticalLayout>
</fly:composition>