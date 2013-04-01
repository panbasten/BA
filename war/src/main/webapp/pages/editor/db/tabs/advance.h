<fly:composition>
	<fly:verticalLayout>
		<fly:fieldSet title="标识符">
			<fly:gridLayout column="4" itemWidth="35%,15%,35%,15%">
				<fly:gridLayoutItem>
					<fly:labelObject for="${formId}:SUPPORTS_BOOLEAN_DATA_TYPE" title="支持布尔数据类型" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:SUPPORTS_BOOLEAN_DATA_TYPE" name="${formId}:SUPPORTS_BOOLEAN_DATA_TYPE" type="checkbox" value="${dbMeta.attributes['SUPPORTS_BOOLEAN_DATA_TYPE']}" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject for="${formId}:QUOTE_ALL_FIELDS" title="标识符使用引号括起来" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:QUOTE_ALL_FIELDS" name="${formId}:QUOTE_ALL_FIELDS" type="checkbox" value="${dbMeta.attributes['QUOTE_ALL_FIELDS']}" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject for="${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE" title="强制标识符使用小写字母" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE" name="${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE" type="checkbox" value="${dbMeta.attributes['FORCE_IDENTIFIERS_TO_LOWERCASE']}" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject for="${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE" title="强制标识符使用大写字母" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE" name="${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE" type="checkbox" value="${dbMeta.attributes['FORCE_IDENTIFIERS_TO_UPPERCASE']}" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>
		
		<fly:labelObject for="${formId}:preferredSchemaName" title="默认模式名称（在没有其他模式名时使用）" />
		
		<fly:inputText id="${formId}:preferredSchemaName" name="${formId}:preferredSchemaName" type="text" value="${dbMeta.preferredSchemaName}" />
		
		<fly:labelObject for="${formId}:connectionType" title="请输入连接成功后要执行的SQL语句，用分号(;)隔开" />
		
		<textarea id="${formId}:connectSQL" name="${formId}:connectSQL" style="width:100%;" rows="6">
			${dbMeta.connectSQL}
		</textarea>
		
	</fly:verticalLayout>

</fly:composition>