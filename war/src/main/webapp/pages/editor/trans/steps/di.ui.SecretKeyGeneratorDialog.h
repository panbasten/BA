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

	<fly:fieldSet title="输出字段" margin="10">
		<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:1" text="密钥字段" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="算法字段" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="密钥长度字段" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="以二进制输出密钥" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="checkbox"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:metaField" text="密钥" />
		<fly:dataGrid id="${formId}:metaField" singleSelect="true" data="" height="200">
			<fly:columns>
				<fly:row>
					<fly:column field="key" title="算法" width="100" editor="text" align="center" />
					<fly:column field="key" title="方案" width="100" editor="text" align="center" />
					<fly:column field="key" title="密钥长度" width="100" editor="text" align="center" />
					<fly:column field="key" title="多少" width="100" editor="text" align="center" />
				</fly:row>
			</fly:columns>
			<fly:toolbar>
				<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:field');" title="删除" />
			</fly:toolbar>
		</fly:dataGrid>
	</fly:verticalLayout>
</form>
