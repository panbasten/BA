<form id="${formId}" class="fly-dialog-form" method="post">
	<fly:gridLayout column="4" itemWidth="15%,34%,15%,34%" itemMargin="10">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="步骤名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text"
				validate="required:true"
				value="${stepMeta.name}" />
			<fly:inputText id="${formId}:transId" name="${formId}:transId" type="hidden"
				value="${transId}" />
			<fly:inputText id="${formId}:stepMetaName" name="${formId}:stepMetaName" type="hidden"
				value="${stepMetaName}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:processfield" text="运行程序字段" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="3">
			<fly:inputText id="${formId}:processfield" name="${formId}:processfield" type="text"
				validate="required:true"
				value="${stepMetaInterface.processField}" />
		</fly:gridLayoutItem>

		<fly:gridLayoutItem cols="2">
			<fly:labelObject buddy="${formId}:failwhennotsuccess" text="如果执行失败返回执行结果" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem cols="2">
			<fly:inputText id="${formId}:failwhennotsuccess" name="${formId}:failwhennotsuccess" type="checkbox"
				value="${stepMetaInterface.failWhenNotSuccess}" />
		</fly:gridLayoutItem>

	</fly:gridLayout>

	<fly:fieldSet title="输出字段" margin="10">
		<fly:gridLayout column="2" itemWidth="40%,60%" itemMargin="10">

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:resultfieldname" text="结果字段名" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:resultfieldname" name="${formId}:resultfieldname" type="text"
					validate="required:true"
					value="${stepMetaInterface.resultFieldName}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:errorfieldname" text="错误字段名" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:errorfieldname" name="${formId}:errorfieldname" type="text"
					validate="required:true"
					value="${stepMetaInterface.errorFieldName}" />
			</fly:gridLayoutItem>

			<fly:gridLayoutItem>
				<fly:labelObject buddy="${formId}:exitvaluefieldname" text="退出值字段名" />
			</fly:gridLayoutItem>
			<fly:gridLayoutItem>
				<fly:inputText id="${formId}:exitvaluefieldname" name="${formId}:exitvaluefieldname" type="text"
					validate="required:true"
					value="${stepMetaInterface.exitValueFieldName}" />
			</fly:gridLayoutItem>
		</fly:gridLayout>
	</fly:fieldSet>

</form>
