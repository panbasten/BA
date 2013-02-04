<bq:composition>
	<bq:labelObject for="${formId}:hostname" title="主机名称">
		<input id="${formId}:hostname" name="${formId}:hostname" type="text" value="${dbMeta.hostname}" />
	</bq:labelObject>
	<bq:labelObject for="${formId}:databaseName" title="数据库名称">
		<input id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" />
	</bq:labelObject>
	<bq:labelObject for="${formId}:databasePortNumberString" title="端口号">
		<input id="${formId}:databasePortNumberString" name="${formId}:databasePortNumberString" type="text" value="${dbMeta.databasePortNumberString}" />
	</bq:labelObject>
</bq:composition>