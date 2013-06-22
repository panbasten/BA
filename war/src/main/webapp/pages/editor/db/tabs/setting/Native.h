<fly:composition freeLayout="N">
	<fly:labelObject buddy="${formId}:hostname" text="主机名称" width="30%" />
	<fly:inputText id="${formId}:hostname" name="${formId}:hostname" type="text" value="${dbMeta.hostname}" width="65%" class="ui-helper-clearfix" />
	
	<fly:labelObject buddy="${formId}:databaseName" text="数据库名称" width="30%" />
	<fly:inputText id="${formId}:databaseName" name="${formId}:databaseName" type="text" value="${dbMeta.databaseName}" width="65%" class="ui-helper-clearfix" />
	
	<fly:labelObject buddy="${formId}:databasePortNumberString" text="端口号" width="30%" />
	<fly:inputText id="${formId}:databasePortNumberString" name="${formId}:databasePortNumberString" type="text" value="${dbMeta.databasePortNumberString}" width="65%" class="ui-helper-clearfix" />

</fly:composition>
