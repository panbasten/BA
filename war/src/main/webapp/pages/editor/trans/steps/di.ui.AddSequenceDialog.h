<form id="${formId}" class="fly-dialog-form">
	<fly:gridLayout column="2" itemWidth="30%,65%" itemMargin="10" marginBottom="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true"
				value="${stepMeta.name}" />
			<fly:inputText id="${formId}:transId" name="${formId}:transId" type="hidden"
				value="${transId}" />
			<fly:inputText id="${formId}:stepMetaName" name="${formId}:stepMetaName" type="hidden"
				value="${stepMetaName}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:valuename" text="值的名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:valuename" name="${formId}:valuename" type="text"
				validate="required:true" value="${stepMetaInterface.valuename}" />
		</fly:gridLayoutItem>
	</fly:gridLayout>

	<fly:fieldSet title="使用数据库来生成序列" margin="10">
		<fly:gridLayout column="2" itemWidth="40%,55%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:databaseUsed" text="使用数据库来生成序列？" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:databaseUsed" name="${formId}:databaseUsed" type="checkbox"
					value="${stepMetaInterface.databaseUsed}" />
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
				<fly:labelObject buddy="${formId}:schemaName" text="模式名称" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:schemaName" name="${formId}:schemaName" type="text"
					validate="required:true"
					value="${stepMetaInterface.schemaName}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:sequenceName" text="序列名称" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:sequenceName" name="${formId}:sequenceName" type="text"
					validate="required:true"
					value="${stepMetaInterface.sequenceName}" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>

	<fly:fieldSet title="使用转换计数器来生成序列" margin="10">
		<fly:gridLayout column="2" itemWidth="40%,55%" itemMargin="10">
			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:counterUsed" text="使用转换计数器来生成序列？" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:counterUsed" name="${formId}:counterUsed" type="checkbox"
					value="${stepMetaInterface.counterUsed}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:counterName" text="计数器名称(可选)" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:counterName" name="${formId}:counterName" type="text"
					validate="required:true"
					value="${stepMetaInterface.counterName}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:startAt" text="起始值" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:startAt" name="${formId}:startAt" type="text"
					validate="required:true"
					value="${stepMetaInterface.startAt}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:incrementBy" text="步长" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:incrementBy" name="${formId}:incrementBy" type="text"
					validate="required:true"
					value="${stepMetaInterface.incrementBy}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:maxValue" text="最大值" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:maxValue" name="${formId}:maxValue" type="text"
					validate="required:true"
					value="${stepMetaInterface.maxValue}" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>
</form>
