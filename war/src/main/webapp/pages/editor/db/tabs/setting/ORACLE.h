<fly:composition>
	<fly:labelObject for="${formId}:dataTablespace" title="数据表空间" width="30%" />
	<fly:inputText id="${formId}:dataTablespace" name="${formId}:dataTablespace" type="text" value="${dbMeta.dataTablespace}" width="60%" />

	<fly:labelObject for="${formId}:indexTablespace" title="索引表空间" width="30%" />
	<fly:inputText id="${formId}:indexTablespace" name="${formId}:indexTablespace" type="text" value="${dbMeta.indexTablespace}" width="60%" />
</fly:composition>