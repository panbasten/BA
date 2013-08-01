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

	<fly:fieldSet title="作业" margin="10">
		<fly:verticalLayout margin="10">
			<fly:inputText id="${formId}:1" name="${formId}:1" type="radio"
				value="" text="文件方式" />
			<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
				value="" />

			<fly:inputText id="${formId}:1" name="${formId}:1" type="radio"
				value="" text="资源库方式" marginTop="10" />
			<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
				value="" />

		</fly:verticalLayout>
	</fly:fieldSet>

	<fly:tabView id="access_input_tab">
		<fly:tab id="access_input_tab_parameter" title="命名参数">
			<fly:verticalLayout margin="10">
				<fly:dataGrid id="${formId}:selectedFiles" singleSelect="true" data="" height="200">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="变量/参数名" width="150" editor="text" align="center" />
							<fly:column field="value" title="使用字段" width="150" editor="text" align="center" />
							<fly:column field="desc" title="静态输入值" width="150" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
				<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
						value="" text="继承转换的所有变量" marginTop="10"/>
			</fly:verticalLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_row_group" title="行分组">
			<fly:gridLayout column="2" itemWidth="40%,55%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="发送到作业的行数" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="分组的字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="执行前等待收集行的时间(毫秒)" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_run_result" title="执行结果">
			<fly:gridLayout column="2" itemWidth="40%,55%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="接收执行结果的步骤" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存执行时间(毫秒)的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存执行结果的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存错误数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存读取行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存写入行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存输入行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存输出行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存丢弃行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存更新行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存删除行数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存重新获取的文件数的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存退出状态的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存执行日志文件的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="保存日志路径的字段名称" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_result_row" title="结果行">
			<fly:gridLayout column="2" itemWidth="40%,55%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="接收结果行的目标步骤" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>

			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:selectedFiles" text="期望的结果行" />
				<fly:dataGrid id="${formId}:selectedFiles" singleSelect="true" data="" height="200">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="字段名" width="150" editor="text" align="center" />
							<fly:column field="value" title="数据类型" width="100" editor="text" align="center" />
							<fly:column field="desc" title="长度" width="100" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:selectedFiles:remove" iconCls="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:selectedFiles');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
		</fly:tab>

		<fly:tab id="access_input_tab_result_file" title="结果文件">
			<fly:gridLayout column="2" itemWidth="40%,55%" itemMargin="10" marginBottom="10">
				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="接收结果文件信息的步骤" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>

				<fly:gridLayoutItem>
					<fly:labelObject buddy="${formId}:name" text="结果文件名字段" />
				</fly:gridLayoutItem>
				<fly:gridLayoutItem>
					<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
						validate="required:true" value="" />
				</fly:gridLayoutItem>
			</fly:gridLayout>
		</fly:tab>
	</fly:tabView>
</form>
