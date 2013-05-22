<fly:composition freeLayout="N">
	<fly:labelObject buddy="${formId}:databaseName" text="JNDI名称" width="30%" />
	<fly:inputText id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" width="60%" />
</fly:composition>