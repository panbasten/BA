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
			<fly:labelObject buddy="${formId}:name" text="值的名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true" value="" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:fieldSet title="使用数据库来生成序列" margin="10">
		<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:1" text="使用数据库来生成序列？" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="数据库连接" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="模式名称" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="序列名称" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>

	<fly:fieldSet title="使用转换计数器来生成序列" margin="10">
		<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:1" text="使用转换计数器来生成序列？" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:1" name="${formId}:1" type="checkbox"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="计数器名称(可选)" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="起始值" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="步长" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:2" text="最大值" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:2" name="${formId}:2" type="text"
					validate="required:true"
					value="" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>
</form>
