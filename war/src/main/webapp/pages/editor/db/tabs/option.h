<fly:composition>
	<fly:layout type="float">
		<fly:layoutItem>
			<fly:labelObject title="命名参数" type="labelSingle">
				<fly:grid id="${formId}:options" singleSelect="true" selectable="false" data="${connectionProperties}" height="280">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="参数名" width="150" editor="text" align="center" />
							<fly:column field="value" title="值" width="150" editor="text" align="center" />
						</fly:row>
					</fly:columns>
				</fly:grid>
			</fly:labelObject>
		</fly:layoutItem>
	</fly:layout>
</fly:composition>