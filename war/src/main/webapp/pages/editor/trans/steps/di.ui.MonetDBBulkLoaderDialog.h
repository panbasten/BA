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
			<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="数据库连接" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="目标Schema" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="目标表" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="MClient文件位置" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="缓存大小(行数)" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="日志文件" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="编码" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_parameter" title="加载字段">
			<fly:dataGrid id="${formId}:metaField" singleSelect="true" data="" height="200">
				<fly:columns>
					<fly:row>
						<fly:column field="key" title="表名字段" width="100" editor="text" align="center" />
						<fly:column field="key" title="流字段" width="100" editor="text" align="center" />
						<fly:column field="key" title="是否判断字段格式正确" width="150" editor="text" align="center" />
					</fly:row>
				</fly:columns>
				<fly:toolbar>
					<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:field');" title="删除" />
				</fly:toolbar>
			</fly:dataGrid>
		</fly:tab>
	</fly:tabView>
</form>
