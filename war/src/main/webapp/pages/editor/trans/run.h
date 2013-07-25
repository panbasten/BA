<form id="${formId}" class="fly-dialog-form" method="post">
	<fly:gridLayout column="6" itemWidth="20%,13%,20%,13%,20%,13%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="启动安全模式" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox" 
				value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="收集性能指标" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
				value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="执行前清除日志" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
				value="" />
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
	<fly:gridLayout column="2" itemWidth="45%,54%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="日志级别" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="重做日期(yyyy/MM/dd HH:mm:ss)" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
	
	<fly:gridLayout column="2" itemWidth="49%,49%">
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:parameters" text="命名参数" />
				<fly:dataGrid id="${formId}:parameters" singleSelect="true" data="" height="240">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="参数名" width="50" editor="text" align="center" />
							<fly:column field="value" title="参数值" width="100" editor="text" align="center" />
							<fly:column field="desc" title="默认值" width="50" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:options:append" icon="ui-icon-plusthick" onclick="Flywet.editors.toolbarButton.addRow('${formId}:variables');" title="添加" />
						<fly:pushbutton id="${formId}:options:remove" icon="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:variables');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:verticalLayout margin="10">
				<fly:labelObject buddy="${formId}:variables" text="变量" />
				<fly:dataGrid id="${formId}:variables" singleSelect="true" data="" height="240">
					<fly:columns>
						<fly:row>
							<fly:column field="key" title="变量名" width="50" editor="text" align="center" />
							<fly:column field="value" title="变量值" width="100" editor="text" align="center" />
						</fly:row>
					</fly:columns>
					<fly:toolbar>
						<fly:pushbutton id="${formId}:options:append" icon="ui-icon-plusthick" onclick="Flywet.editors.toolbarButton.addRow('${formId}:variables');" title="添加" />
						<fly:pushbutton id="${formId}:options:remove" icon="ui-icon-closethick" onclick="Flywet.editors.toolbarButton.deleteRow('${formId}:variables');" title="删除" />
					</fly:toolbar>
				</fly:dataGrid>
			</fly:verticalLayout>
		</fly:gridLayoutItem>
	</fly:gridLayout>
</form>
