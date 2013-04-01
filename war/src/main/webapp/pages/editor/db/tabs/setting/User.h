<fly:composition>
	<fly:labelObject for="${formId}:username" title="用户名" width="30%" />
	<fly:inputText id="${formId}:username" name="${formId}:username" type="text" value="${dbMeta.username}" width="60%" />

	<fly:labelObject for="${formId}:password" title="密码" width="30%" />
	<fly:inputText id="${formId}:password" name="${formId}:password" type="password" value="${dbMeta.password}" width="60%" />
</fly:composition>