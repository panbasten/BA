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
		<fly:tab id="access_input_tab_setting" title="设置">
			<fly:gridLayout column="4" itemWidth="25%,25%,23%,25%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="数据库连接" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem cols="3">
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>

			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:metaField" text="SQL脚本(语句间用';'分隔, 语句中的'?'将会用参数替换)" />
				<fly:inputTextarea id="${formId}:name" name="${formId}:name" rows="7"
					validate="required:true" value="" />
			</fly:verticalLayout>

			<fly:gridLayout column="4" itemWidth="25%,25%,23%,25%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="绑定参数?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="执行每一行?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="当做单一语句执行?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="替换变量?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>
		<fly:tab id="access_input_tab_parameter" title="参数">
			<fly:dataGrid id="${formId}:parameter" singleSelect="true" data="" height="300">
				<fly:columns>
					<fly:row>
						<fly:column field="key" title="作为参数的字段" width="200" editor="text" align="center" />
					</fly:row>
				</fly:columns>
				<fly:toolbar>
					<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
				</fly:toolbar>
			</fly:dataGrid>
		</fly:tab>
		<fly:tab id="access_input_tab_other_parameter" title="其他输出字段">
			<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="包含插入状态的字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="包含更新状态的字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="包含删除状态的字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:2" text="包含读取状态的字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
						validate="required:true"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>
	</fly:tabView>
</form>
