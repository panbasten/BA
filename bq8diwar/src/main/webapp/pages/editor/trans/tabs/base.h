<bq:composition>
	<bq:layout type="float" column="2">
		<bq:layoutItem cols="2">
			<bq:labelObject for="${formId}:name" title="转换名称" labelDiv="2">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					validate="required:true"
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem cols="2">
			<bq:labelObject for="${formId}:name" title="转换文件名称" labelDiv="2">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem cols="2">
			<bq:labelObject for="${formId}:name" title="目录" labelDiv="2">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem cols="2">
			<bq:labelObject for="${formId}:name" title="描述" labelDiv="2">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem cols="2">
			<bq:labelObject for="${formId}:name" title="扩展描述" labelDiv="2">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject for="${formId}:name" title="状态">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject for="${formId}:name" title="版本">
				<bq:inputText id="${formId}:name" name="${formId}:name" type="text" 
					validate="required:true"
					value="${dbMeta.name}" />
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject title="创建者">
				${dbMeta.name}
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject title="创建日期">
				${dbMeta.name}
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject title="最后修改者">
				${dbMeta.name}
			</bq:labelObject>
		</bq:layoutItem>
		<bq:layoutItem>
			<bq:labelObject title="最后修改日期">
				${dbMeta.name}
			</bq:labelObject>
		</bq:layoutItem>
		
	</bq:layout>
</bq:composition>