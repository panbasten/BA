<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="文件名" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:fieldSet title="从字段获得文件名" margin="10">
		<fly:gridLayout column="4" itemWidth="22%,28%,20%,28%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:1" text="文件名来自字段？" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem cols="3">
				<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="获取文件名的步骤" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="获取文件名的字段" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>

	<fly:gridLayout column="4" itemWidth="20%,15%,20%,43%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="大小限制" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="增加记录行数?" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="包含记录的字段" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="输出文件名?" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="checkbox"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="包含文件名的字段" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="使用的字符集" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
</form>
