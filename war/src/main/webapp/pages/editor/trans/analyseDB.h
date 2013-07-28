<form id="${formId}" class="fly-dialog-form" method="post">

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:check" text="分析结果： ${impactsNum} 行" />
		<fly:dataGrid id="${formId}:check" singleSelect="true" data="${impacts}" height="350">
			<fly:columns>
				<fly:row>
					<fly:column field="typeDesc" title="类型" width="50" editor="text" align="center" />
					<fly:column field="transName" title="转换" width="150" editor="text" align="center" />
					<fly:column field="stepName" title="步骤" width="150" editor="text" align="center" />
					<fly:column field="databaseName" title="数据库" width="100" editor="text" align="center" />
					<fly:column field="table" title="表" width="100" editor="text" align="center" />
					<fly:column field="field" title="字段" width="150" editor="text" align="center" />
					<fly:column field="value" title="值" width="150" editor="text" align="center" />
					<fly:column field="valueOrigin" title="原始值" width="150" editor="text" align="center" />
					<fly:column field="sQL" title="SQL语句" width="200" editor="text" align="center" />
					<fly:column field="remark" title="注解" width="200" editor="text" align="center" />
				</fly:row>
			</fly:columns>
		</fly:dataGrid>
	</fly:verticalLayout>

</form>
