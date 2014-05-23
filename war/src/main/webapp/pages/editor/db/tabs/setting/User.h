<fly:composition freeLayout="N">
	<fly:labelObject buddy="${formId}:username" text="用户名" width="30%" style="margin-bottom:10px;" />
	<fly:inputText id="${formId}:username" name="${formId}:username" type="text" value="${dbMeta.username}" width="65%" style="margin-bottom:10px;" />

	<fly:labelObject buddy="${formId}:password" text="密码" width="30%" style="margin-bottom:10px;" />
	<fly:inputText id="${formId}:password" name="${formId}:password" type="password" value="${dbMeta.password}" width="65%" style="margin-bottom:10px;" />
</fly:composition>
