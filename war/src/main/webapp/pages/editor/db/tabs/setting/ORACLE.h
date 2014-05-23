<fly:composition freeLayout="N">
	<fly:labelObject buddy="${formId}:dataTablespace" text="数据表空间" width="30%" style="margin-bottom:10px;" />
	<fly:inputText id="${formId}:dataTablespace" name="${formId}:dataTablespace" type="text" value="${dbMeta.dataTablespace}" width="65%" style="margin-bottom:10px;" />

	<fly:labelObject buddy="${formId}:indexTablespace" text="索引表空间" width="30%" style="margin-bottom:10px;" />
	<fly:inputText id="${formId}:indexTablespace" name="${formId}:indexTablespace" type="text" value="${dbMeta.indexTablespace}" width="65%" style="margin-bottom:10px;" />
</fly:composition>
