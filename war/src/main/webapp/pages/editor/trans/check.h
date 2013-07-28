<form id="${formId}" class="fly-dialog-form" method="post">

	<fly:verticalLayout margin="10">
		<fly:labelObject buddy="${formId}:check" text="警告和错误" />
		<fly:dataGrid id="${formId}:check" weightVar="trans_check_var" singleSelect="true" data="${remarks}" height="300">
			<fly:columns>
				<fly:row>
					<fly:column field="stepName" title="步骤名称" width="100" align="center" />
					<fly:column field="typeText" title="结果" width="100" align="center" />
					<fly:column field="text" title="备注" width="300" align="left" />
					<fly:column field="type" hidden="true" />
				</fly:row>
			</fly:columns>
			<fly:rowStylers>
				<fly:rowStyler field="type" opertion="==" value="1" style="background-color:green;"/>
				<fly:rowStyler field="type" opertion="==" value="4" style="background-color:red;"/>
			</fly:rowStylers>
			<fly:dataFilters>
				<fly:dataFilter field="type" opertion="!=" value="1"/>
			</fly:dataFilters>
		</fly:dataGrid>
		<fly:inputText id="${formId}:showSuccess" name="${formId}:showSuccess" type="checkbox"
				onclick="Flywet.editors.trans.action.checkShowSuccess(this.checked);"
				text="显示成功结果" marginTop="10" />
	</fly:verticalLayout>

</form>
