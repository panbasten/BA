<fly:composition freeLayout="N">
	<fly:verticalLayout margin="10">
		<fly:fieldSet title="标识符">
			<fly:gridLayout column="4" itemWidth="34%,15%,34%,15%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:SUPPORTS_BOOLEAN_DATA_TYPE" text="支持布尔数据类型" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:SUPPORTS_BOOLEAN_DATA_TYPE" name="${formId}:SUPPORTS_BOOLEAN_DATA_TYPE" type="checkbox" value="${dbMeta.attributes['SUPPORTS_BOOLEAN_DATA_TYPE']}" class="clearfix" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:QUOTE_ALL_FIELDS" text="标识符使用引号括起来" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:QUOTE_ALL_FIELDS" name="${formId}:QUOTE_ALL_FIELDS" type="checkbox" value="${dbMeta.attributes['QUOTE_ALL_FIELDS']}" class="clearfix" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE" text="强制标识符使用小写字母" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE" name="${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE" type="checkbox" value="${dbMeta.attributes['FORCE_IDENTIFIERS_TO_LOWERCASE']}" class="clearfix" />
				</fly:gridLayoutItem>
				
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE" text="强制标识符使用大写字母" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE" name="${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE" type="checkbox" value="${dbMeta.attributes['FORCE_IDENTIFIERS_TO_UPPERCASE']}" class="clearfix" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:fieldSet>
		
		<fly:labelObject buddy="${formId}:preferredSchemaName" class="ui-layout-no-bottom-margin" text="默认模式名称（在没有其他模式名时使用）" marginTop="10" />
		
		<fly:inputText id="${formId}:preferredSchemaName" name="${formId}:preferredSchemaName" type="text" value="${dbMeta.preferredSchemaName}" />
		
		<fly:labelObject buddy="${formId}:connectSQL" class="ui-layout-no-bottom-margin" text="请输入连接成功后要执行的SQL语句，用分号(;)隔开" marginTop="10" />
		
		<textarea id="${formId}:connectSQL" name="${formId}:connectSQL" style="width:100%;" rows="6">
			${dbMeta.connectSQL}
		</textarea>
		
	</fly:verticalLayout>

</fly:composition>
