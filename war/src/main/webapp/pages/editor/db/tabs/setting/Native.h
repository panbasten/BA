<fly:composition>
	<fly:labelObject for="${formId}:hostname" title="主机名称">
		<input id="${formId}:hostname" name="${formId}:hostname" type="text" value="${dbMeta.hostname}" />
	</fly:labelObject>
	<fly:labelObject for="${formId}:databaseName" title="数据库名称">
		<input id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" />
	</fly:labelObject>
	<fly:labelObject for="${formId}:databasePortNumberString" title="端口号">
		<input id="${formId}:databasePortNumberString" name="${formId}:databasePortNumberString" type="text" value="${dbMeta.databasePortNumberString}" />
	</fly:labelObject>
</fly:composition>