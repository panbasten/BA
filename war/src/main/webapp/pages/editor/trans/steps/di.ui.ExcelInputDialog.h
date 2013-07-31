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
		<fly:tab id="access_input_tab_file" title="文件">
			<fly:verticalLayout>
				<fly:fieldSet title="从字段获得文件名" margin="10">
					<fly:gridLayout column="4" itemWidth="30%,20%,28%,20%" itemMargin="10">
						<fly:gridLayoutItem>
							<fly:labelObject buddy="${formId}:1" text="从前面步骤获取文件名" />
						</fly:gridLayoutItem>
						<fly:gridLayoutItem cols="3">
							<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
								value="" />
						</fly:gridLayoutItem>

						<fly:gridLayoutItem>
							<fly:labelObject buddy="${formId}:2" text="从哪个步骤读取文件名" />
						</fly:gridLayoutItem>
						<fly:gridLayoutItem>
							<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
								validate="required:true"
								value="" />
						</fly:gridLayoutItem>

						<fly:gridLayoutItem>
							<fly:labelObject buddy="${formId}:2" text="保存文件名的字段" />
						</fly:gridLayoutItem>
						<fly:gridLayoutItem>
							<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
								validate="required:true"
								value="" />
						</fly:gridLayoutItem>
					</fly:gridLayout>
				</fly:fieldSet>
				<fly:gridLayout column="4" itemWidth="25%,25%,23%,25%" itemMargin="10">
					<fly:gridLayoutItem>
						<fly:labelObject buddy="${formId}:1" text="表格类型(引擎)" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem cols="3">
						<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
							validate="required:true"
							value="" />
					</fly:gridLayoutItem>

					<fly:gridLayoutItem>
						<fly:labelObject buddy="${formId}:1" text="文件或者目录" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem cols="3">
						<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
							validate="required:true"
							value="" />
					</fly:gridLayoutItem>

					<fly:gridLayoutItem>
						<fly:labelObject buddy="${formId}:1" text="正则表达式" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem>
						<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
							validate="required:true"
							value="" />
					</fly:gridLayoutItem>

					<fly:gridLayoutItem>
						<fly:labelObject buddy="${formId}:1" text="正则表达式(排除)" />
					</fly:gridLayoutItem>
					<fly:gridLayoutItem>
						<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
							validate="required:true"
							value="" />
					</fly:gridLayoutItem>
				</fly:gridLayout>

				<fly:verticalLayout margin="10">
					<fly:labelObject buddy="${formId}:selectedFiles" text="选中的文件" />
					<fly:dataGrid id="${formId}:selectedFiles" singleSelect="true" data="" height="200">
						<fly:columns>
							<fly:row>
								<fly:column field="key" title="文件或路径" width="150" editor="text" align="center" />
								<fly:column field="value" title="通配符" width="100" editor="text" align="center" />
								<fly:column field="desc" title="通配符(排除)" width="100" editor="text" align="center" />
								<fly:column field="desc" title="要求" width="50" editor="text" align="center" />
								<fly:column field="desc" title="包含子目录" width="80" editor="text" align="center" />
							</fly:row>
						</fly:columns>
						<fly:toolbar>
							<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
						</fly:toolbar>
					</fly:dataGrid>
				</fly:verticalLayout>

			</fly:verticalLayout>
		</fly:tab>


		<fly:tab id="access_input_tab_sheet" title="工作簿">
			<fly:dataGrid id="${formId}:sheet" singleSelect="true" data="" height="300">
				<fly:columns>
					<fly:row>
						<fly:column field="key" title="工作薄名称" width="150" editor="text" align="center" />
						<fly:column field="value" title="起始行" width="150" editor="text" align="center" />
						<fly:column field="desc" title="起始列" width="150" editor="text" align="center" />
					</fly:row>
				</fly:columns>
				<fly:toolbar>
					<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
				</fly:toolbar>
			</fly:dataGrid>
		</fly:tab>


		<fly:tab id="access_input_tab_content" title="内容">
			<fly:gridLayout column="4" itemWidth="15%,35%,13%,35%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="头部" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="非空记录" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="停在空记录" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="添加文件名" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="限制" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="编码" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

			</fly:gridLayout>
		</fly:tab>


		<fly:tab id="access_input_tab_error" title="错误处理">
			<fly:gridLayout column="4" itemWidth="28%,40%,15%,15%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="严格类型?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem cols="3">
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="忽略错误?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem cols="3">
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="跳过错误行?" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem cols="3">
					<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="警告文件目录" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="扩展名" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="错误文件目录" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="扩展名" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="失败的记录数文件目录" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="扩展名" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>


		<fly:tab id="access_input_tab_parameter" title="字段">
			<fly:dataGrid id="${formId}:parameter" singleSelect="true" data="" height="300">
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
		<fly:tab id="access_input_tab_other_parameter" title="其他输出字段">
			<fly:gridLayout column="4" itemWidth="30%,20%,28%,20%" itemMargin="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="文件名字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="工作簿名称字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="表单的行号字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="行号字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="文件名字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="扩展名字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="路径字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="文件大小字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="是否为隐藏文件字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="最后修改时间字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="Uri字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:1" text="Root Uri字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
						value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>
	</fly:tabView>
</form>
