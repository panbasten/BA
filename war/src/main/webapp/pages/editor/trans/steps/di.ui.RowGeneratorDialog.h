<form id="${formId}" class="fly-dialog-form">
<fly:gridLayout column="4" itemWidth="15%,34%,15%,34%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true"
				value="${stepMeta.name}" />
			<fly:inputText id="${formId}:transId" name="${formId}:transId" type="hidden"
				value="${transId}" />
			<fly:inputText id="${formId}:stepMetaName" name="${formId}:stepMetaName" type="hidden"
				value="${stepMetaName}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:rowLimit" text="限制" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:rowLimit" name="${formId}:rowLimit" type="text"
				validate="required:true"
				value="${stepMetaInterface.rowLimit}" />
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:fields" text="字段" />
		<fly:dataGrid id="${formId}:fields" singleSelect="true" data="${di:createDGDataSet(stepMetaInterface.fieldsDGKeys,stepMetaInterface.fieldsDGValues)}" height="280">
			<fly:columns>
				<fly:row>
					<fly:column field="fieldName" title="名称" width="80" editor="text" align="center" />
					<fly:column field="fieldType" title="类型" width="80" editor="${di:getFieldType()}" align="center" />
					<fly:column field="fieldFormat" title="格式" width="150" editor="text" align="center" />
					<fly:column field="fieldLength" title="长度" width="80" editor="text" align="center" />
					<fly:column field="fieldPrecision" title="精度" width="80" editor="text" align="center" />
					<fly:column field="currency" title="货币类型" width="100" editor="text" align="center" />
					<fly:column field="decimal" title="小数" width="80" editor="text" align="center" />
					<fly:column field="group" title="分组" width="80" editor="text" align="center" />
					<fly:column field="value" title="值" width="80" editor="text" align="center" />
					<fly:column field="setEmptyString" title="是否为空" width="100" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:fields:add" iconCls="ui-icon-plusthick" onclick="Flywet.editors.toolbarButton.addRow('${formId}:fields');" title="添加" />
				<fly:pushbutton id="${formId}:fields:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:fields');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</form>
