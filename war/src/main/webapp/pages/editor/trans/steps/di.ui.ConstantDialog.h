<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:dataGrid id="${formId}:parameter" singleSelect="true" data="" height="300">
		<fly:columns>
			<fly:row>
				<fly:column field="key" title="名称" width="100" editor="text" align="center" />
				<fly:column field="desc" title="类型" width="70" editor="text" align="center" />
				<fly:column field="desc" title="格式" width="100" editor="text" align="center" />
				<fly:column field="desc" title="长度" width="70" editor="text" align="center" />
				<fly:column field="desc" title="精度" width="70" editor="text" align="center" />
				<fly:column field="desc" title="当前的" width="70" editor="text" align="center" />
				<fly:column field="desc" title="十进展" width="70" editor="text" align="center" />
				<fly:column field="desc" title="组" width="70" editor="text" align="center" />
				<fly:column field="desc" title="值" width="70" editor="text" align="center" />
				<fly:column field="desc" title="设为空串?" width="120" editor="text" align="center" />
			</fly:row>
		</fly:columns>
		<fly:toolbar>
			<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
		</fly:toolbar>
	</fly:dataGrid>
</form>
