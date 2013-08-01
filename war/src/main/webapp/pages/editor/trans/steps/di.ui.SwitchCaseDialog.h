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
		<fly:tab id="access_input_tab_file" title="内容">
			<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="用于切换的字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="切换字段包含比较符?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="用例值数据类型" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="用例值转换掩码" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="用例值小数点符合" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="用例值分组标志" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>
		<fly:tab id="access_input_tab_case" title="用例">
			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:selectedFiles" text="用例值" />
				<fly:dataGrid id="${formId}:selectedFiles" singleSelect="true" data="" height="200">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="用例值" width="150" editor="text" align="center" />
							<fly:column field="value" title="目标步骤" width="100" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
			<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="默认目标步骤" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>
	</fly:tabView>
</form>
