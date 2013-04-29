<fly:composition>
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="转换名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				validate="required:true"
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="转换文件名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="目录" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="扩展描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="状态" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="版本" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				value="${dbMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="创建者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="创建日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="最后修改者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="最后修改日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${dbMeta.name}
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
</fly:composition>