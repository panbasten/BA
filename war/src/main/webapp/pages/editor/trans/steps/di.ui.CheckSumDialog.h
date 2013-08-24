<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="4" itemWidth="20%,30%,20%,28%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true"
				value="${stepMeta.name}" />
			<fly:inputText id="${formId}:transId" name="${formId}:transId" type="hidden"
				value="${transId}" />
			<fly:inputText id="${formId}:stepMetaName" name="${formId}:stepMetaName" type="hidden"
				value="${stepMetaName}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:checkSumType" text="类型" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:checkSumType" name="${formId}:checkSumType" type="text"
				validate="required:true" value="${stepMetaInterface.checkSumType}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:resultType" text="结果类型" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:resultType" name="${formId}:resultType" type="text"
				validate="required:true" value="${stepMetaInterface.resultType}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:resultFieldName" text="结果字段" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:resultFieldName" name="${formId}:resultFieldName" type="text"
				validate="required:true" value="${stepMetaInterface.resultFieldName}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:compatibilityMode" text="兼容模式" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:compatibilityMode" name="${formId}:compatibilityMode" type="checkbox"
				validate="required:true" value="${stepMetaInterface.compatibilityMode}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:checkFields" text="校验使用的字段" />
		<fly:dataGrid id="${formId}:checkFields" singleSelect="true" data="${di:createDGDataSet(stepMetaInterface.checkFieldsDGKeys,stepMetaInterface.checkFieldsDGValues)}" height="200">
			<fly:columns>
				<fly:row>
					<fly:column field="fieldName" title="字段" width="200" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:checkFields:add" iconCls="ui-icon-plusthick" onclick="Flywet.editors.toolbarButton.addRow('${formId}:checkFields');" title="增加" />
				<fly:pushbutton id="${formId}:checkFields:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:checkFields');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</form>
