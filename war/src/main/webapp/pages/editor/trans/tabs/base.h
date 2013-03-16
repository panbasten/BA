<fly:composition>
	<fly:layout type="float" column="2">
		<fly:layoutItem cols="2">
			<fly:labelObject for="${formId}:name" title="转换名称" labelDiv="2">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					validate="required:true"
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem cols="2">
			<fly:labelObject for="${formId}:name" title="转换文件名称" labelDiv="2">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem cols="2">
			<fly:labelObject for="${formId}:name" title="目录" labelDiv="2">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem cols="2">
			<fly:labelObject for="${formId}:name" title="描述" labelDiv="2">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem cols="2">
			<fly:labelObject for="${formId}:name" title="扩展描述" labelDiv="2">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject for="${formId}:name" title="状态">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject for="${formId}:name" title="版本">
				<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
					validate="required:true"
					value="${dbMeta.name}" />
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject title="创建者">
				${dbMeta.name}
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject title="创建日期">
				${dbMeta.name}
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject title="最后修改者">
				${dbMeta.name}
			</fly:labelObject>
		</fly:layoutItem>
		<fly:layoutItem>
			<fly:labelObject title="最后修改日期">
				${dbMeta.name}
			</fly:labelObject>
		</fly:layoutItem>
		
	</fly:layout>
</fly:composition>