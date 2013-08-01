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

	<fly:tabView id="access_input_tab">

		<fly:tab id="access_input_tab_content" title="内容">
			<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="编码" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="输出值" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="根XML元素" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="忽略XML头部" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="忽略XML结果中的空值" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_parameter" title="字段">
			<fly:dataGrid id="${formId}:parameter" singleSelect="true" data="" height="200">
				<fly:columns>
					<fly:row>
						<fly:column field="key" title="名称" width="100" editor="text" align="center" />
						<fly:column field="value" title="列" width="100" editor="text" align="center" />
						<fly:column field="desc" title="类型" width="70" editor="text" align="center" />
						<fly:column field="desc" title="格式" width="100" editor="text" align="center" />
						<fly:column field="desc" title="长度" width="70" editor="text" align="center" />
						<fly:column field="desc" title="精度" width="70" editor="text" align="center" />
						<fly:column field="desc" title="货币" width="70" editor="text" align="center" />
						<fly:column field="desc" title="十进展" width="70" editor="text" align="center" />
						<fly:column field="desc" title="组" width="70" editor="text" align="center" />
						<fly:column field="desc" title="去除空字符的方式" width="120" editor="text" align="center" />
						<fly:column field="desc" title="重复" width="70" editor="text" align="center" />
					</fly:row>
				</fly:columns>
				<fly:toolbar>
					<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
				</fly:toolbar>
			</fly:dataGrid>
		</fly:tab>

	</fly:tabView>
</form>
