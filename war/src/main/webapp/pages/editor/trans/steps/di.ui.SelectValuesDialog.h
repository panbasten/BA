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
		<fly:tab id="access_input_tab_select_modify" title="选择和修改">
			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:field" text="字段" />
				<fly:dataGrid id="${formId}:field" singleSelect="true" data="" height="200">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="字段名称" width="100" editor="text" align="center" />
							<fly:column field="value" title="改名为" width="100" editor="text" align="center" />
							<fly:column field="desc" title="长度" width="100" editor="text" align="center" />
							<fly:column field="desc" title="精度" width="100" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:field');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_remove" title="移除">
			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:removeField" text="移除的字段" />
				<fly:dataGrid id="${formId}:removeField" singleSelect="true" data="" height="200">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="字段名称" width="200" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:field');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_meta" title="元数据">
			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:metaField" text="需要改变原数据的字段" />
				<fly:dataGrid id="${formId}:metaField" singleSelect="true" data="" height="200">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="字段名称" width="100" editor="text" align="center" />
							<fly:column field="key" title="改名为" width="100" editor="text" align="center" />
							<fly:column field="key" title="类型" width="70" editor="text" align="center" />
							<fly:column field="key" title="长度" width="70" editor="text" align="center" />
							<fly:column field="key" title="精度" width="70" editor="text" align="center" />
							<fly:column field="key" title="二进制?" width="70" editor="text" align="center" />
							<fly:column field="key" title="格式" width="100" editor="text" align="center" />
							<fly:column field="key" title="日期格式宽松" width="100" editor="text" align="center" />
							<fly:column field="key" title="编码" width="70" editor="text" align="center" />
							<fly:column field="key" title="十进制" width="70" editor="text" align="center" />
							<fly:column field="key" title="分组" width="70" editor="text" align="center" />
							<fly:column field="key" title="货币" width="70" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:field');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
		</fly:tab>

	</fly:tabView>
</form>
