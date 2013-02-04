<bq:composition>
	<bq:layout type="float">
		<bq:layoutItem>
			<bq:labelObject title="命名参数" type="labelSingle">
				<bq:grid id="${formId}:options" singleSelect="true" selectable="false" data="${connectionProperties}" height="280">
					<bq:columns>
						<bq:row>
							<bq:column field="key" title="参数名" width="150" editor="text" align="center" />
							<bq:column field="value" title="值" width="150" editor="text" align="center" />
						</bq:row>
					</bq:columns>
				</bq:grid>
			</bq:labelObject>
		</bq:layoutItem>
	</bq:layout>
</bq:composition>