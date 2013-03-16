<fly:composition>
	<fly:labelObject for="${formId}:hostname" title="主机名称">
		<input id="${formId}:hostname" name="${formId}:hostname" type="text" value="${dbMeta.hostname}" />
	</fly:labelObject>
	<fly:labelObject for="${formId}:SAPSystemNumber" title="系统编号">
		<input id="${formId}:SAPSystemNumber" name="${formId}:SAPSystemNumber" type="text" value="${dbMeta.attributes['SAPSystemNumber']}" />
	</fly:labelObject>
	<fly:labelObject for="${formId}:SAPClient" title="SAP客户端">
		<input id="${formId}:SAPClient" name="${formId}:SAPClient" type="text" value="${dbMeta.attributes['SAPClient']}" />
	</fly:labelObject>
	<fly:labelObject for="${formId}:SAPLanguage" title="语言">
		<input id="${formId}:SAPLanguage" name="${formId}:SAPLanguage" type="text" value="${dbMeta.attributes['SAPLanguage']}" />
	</fly:labelObject>
</fly:composition>