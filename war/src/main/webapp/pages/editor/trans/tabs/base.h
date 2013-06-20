<fly:composition freeLayout="N">
	<fly:gridLayout column="4" itemWidth="15%,35%,15%,35%">
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="转换名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:name" name="${formId}:name" type="text" 
				validate="required:true"
				value="${transMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:filename" text="转换文件名称" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:filename" name="${formId}:filename" type="text"
				value="${transMeta.filename}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:directoryId" text="目录" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:directoryId" name="${formId}:directoryId" type="text"
				value="" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:description" text="描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:description" name="${formId}:description" type="text"
				value="${transMeta.description}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:extendedDescription" text="扩展描述" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:extendedDescription" name="${formId}:extendedDescription" type="text"
				value="${transMeta.extendedDescription}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:transstatus" text="状态" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:transstatus" name="${formId}:transstatus" type="text"
				value="${transMeta.transstatus}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:transversion" text="版本" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			<fly:inputText id="${formId}:transversion" name="${formId}:transversion" type="text"
				value="${transMeta.name}" />
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="创建者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${transMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="创建日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${transMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="最后修改者" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${transMeta.name}
		</fly:gridLayoutItem>
		
		<fly:gridLayoutItem>
			<fly:labelObject buddy="${formId}:name" text="最后修改日期" />
		</fly:gridLayoutItem>
		<fly:gridLayoutItem>
			${transMeta.name}
		</fly:gridLayoutItem>
		
	</fly:gridLayout>
	
</fly:composition>
