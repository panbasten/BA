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

	<fly:fieldSet title="GPG设置" margin="10">
		<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:1" text="GPG文件位置" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:1" name="${formId}:1" type="text"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="键值" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="从字段读取键值?" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="checkbox"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="读取键值的字段" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>

	<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="数据字段名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="结果字段名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>
</form>
