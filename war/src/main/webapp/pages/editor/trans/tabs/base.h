<fly:composition>
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="转换名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				validate="required:true"
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="转换文件名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="目录" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="扩展描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="状态" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="版本" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="创建者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="创建日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="最后修改者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject for="${formId}:name" title="最后修改日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
</fly:composition>