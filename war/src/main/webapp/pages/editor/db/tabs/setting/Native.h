<fly:composition>
	<fly:labelObject for="${formId}:hostname" title="主机名称" width="30%" />
	<fly:inputText id="${formId}:hostname" name="${formId}:hostname" type="text" value="${dbMeta.hostname}" width="60%" />
	
	<fly:labelObject for="${formId}:databaseName" title="数据库名称" width="30%" />
	<fly:inputText id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" width="60%" />
	
	<fly:labelObject for="${formId}:databasePortNumberString" title="端口号" width="30%" />
	<fly:inputText id="${formId}:databasePortNumberString" name="${formId}:databasePortNumberString" type="text" value="${dbMeta.databasePortNumberString}" width="60%" />

</fly:composition>