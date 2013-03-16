<fly:composition>
	<fly:labelObject for="${formId}:username" title="用户名">
		<input id="${formId}:username" name="${formId}:username" type="text" value="${dbMeta.username}" />
	</fly:labelObject>
	<fly:labelObject for="${formId}:password" title="密码">
		<input id="${formId}:password" name="${formId}:password" type="password" value="${dbMeta.password}" />
	</fly:labelObject>
</fly:composition>