<bq:composition>
	<bq:labelObject for="${formId}:dataTablespace" title="数据表空间">
		<input id="${formId}:dataTablespace" name="${formId}:dataTablespace" type="text" value="${dbMeta.dataTablespace}" />
	</bq:labelObject>
	<bq:labelObject for="${formId}:indexTablespace" title="索引表空间">
		<input id="${formId}:indexTablespace" name="${formId}:indexTablespace" type="text" value="${dbMeta.indexTablespace}" />
	</bq:labelObject>
</bq:composition>