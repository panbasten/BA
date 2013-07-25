<form id="${formId}" class="fly-dialog-form" method="post">

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:check" text="分析结果：（8行）" />
		<fly:dataGrid id="${formId}:check" singleSelect="true" data="" height="350">
			<fly:columns>
				<fly:row>
					<fly:column field="type" title="类型" width="50" editor="text" align="center" />
					<fly:column field="transName" title="转换" width="150" editor="text" align="center" />
					<fly:column field="stepName" title="步骤" width="150" editor="text" align="center" />
					<fly:column field="dbName" title="数据库" width="100" editor="text" align="center" />
					<fly:column field="tableName" title="表" width="100" editor="text" align="center" />
					<fly:column field="fieldName" title="字段" width="150" editor="text" align="center" />
					<fly:column field="val" title="值" width="150" editor="text" align="center" />
					<fly:column field="oraVal" title="原始值" width="150" editor="text" align="center" />
					<fly:column field="sql" title="SQL语句" width="200" editor="text" align="center" />
					<fly:column field="node" title="注解" width="200" editor="text" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
	</fly:verticalLayout>

</form>
