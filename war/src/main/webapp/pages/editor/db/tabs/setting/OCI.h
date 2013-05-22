<fly:composition freeLayout="N">
	<fly:labelObject buddy="${formId}:databaseName" text="SID" width="30%" />
	<fly:inputText id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" width="60%" />
</fly:composition>