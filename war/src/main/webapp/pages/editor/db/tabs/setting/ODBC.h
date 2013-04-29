<fly:composition>
	<fly:labelObject buddy="${formId}:databaseName" text="ODBC DSN 名称" width="30%" />
	<fly:inputText id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" width="60%" />
</fly:composition>