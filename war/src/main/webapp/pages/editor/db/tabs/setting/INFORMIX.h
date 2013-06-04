<fly:composition freeLayout="N">
	<fly:labelObject buddy="${formId}:databaseName" text="服务器名称" width="30%" />
	<fly:inputText id="${formId}:servername" name="${formId}:servername" type="text" value="${dbMeta.servername}" width="60%" class="ui-layout-div ui-helper-clearfix" />
</fly:composition>
