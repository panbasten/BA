<fly:composition>
	<fly:labelObject for="${formId}:hostname" title="主机名称" width="30%" />
	<fly:inputText id="${formId}:hostname" name="${formId}:hostname" type="text" value="${dbMeta.hostname}" width="60%" />
	
	<fly:labelObject for="${formId}:SAPSystemNumber" title="系统编号" width="30%" />
	<fly:inputText id="${formId}:SAPSystemNumber" name="${formId}:SAPSystemNumber" type="text" value="${dbMeta.attributes['SAPSystemNumber']}" width="60%" />

	<fly:labelObject for="${formId}:SAPClient" title="SAP客户端" width="30%" />
	<fly:inputText id="${formId}:SAPClient" name="${formId}:SAPClient" type="text" value="${dbMeta.attributes['SAPClient']}" width="60%" />

	<fly:labelObject for="${formId}:SAPLanguage" title="语言" width="30%" />
	<fly:inputText id="${formId}:SAPLanguage" name="${formId}:SAPLanguage" type="text" value="${dbMeta.attributes['SAPLanguage']}" width="60%" />
</fly:composition>